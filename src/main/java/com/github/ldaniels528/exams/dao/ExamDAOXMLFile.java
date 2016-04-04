package com.github.ldaniels528.exams.dao;

import com.github.ldaniels528.exams.models.Answer;
import com.github.ldaniels528.exams.models.Exam;
import com.github.ldaniels528.exams.models.Question;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

/**
 * Exam File Data Access Object Implementation
 *
 * @author lawrence.daniels@gmail.com
 */
public class ExamDAOXMLFile implements ExamDAO {
    private final Logger logger = Logger.getLogger(getClass());
    private final File examFile;

    /**
     * Creates a new Exam File DAO instance
     *
     * @param examFile the given exam {@link File file}
     */
    public ExamDAOXMLFile(final File examFile) {
        this.examFile = examFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Exam getExam() throws DAOException {
        final ExamXMLParser parser = new ExamXMLParser();

        // open the file
        logger.info(format("Loading Exam file '%s'...", examFile.getAbsolutePath()));
        try (InputStream in = new FileInputStream(examFile)) {
            // create a new SAX parser factory
            final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            //saxParserFactory.setNamespaceAware( true );
            saxParserFactory.setValidating(true);

            // setup the XML parser
            final SAXParser saxParser = saxParserFactory.newSAXParser();
            logger.info(format("Using SAX parser '%s'...", saxParser.getClass().getName()));
            saxParser.parse(in, parser);
            return parser.exam;
        } catch (final Exception e) {
            throw new DAOException(e);
        }
    }

    /**
     * Exam XML Parser
     *
     * @author lawrence.daniels@gmail.com
     */
    private class ExamXMLParser extends DefaultHandler {
        private Question currentQuestion;
        private Answer currentAnswer;
        private final Exam exam;
        private String text;

        /**
         * Default Constructor
         */
        ExamXMLParser() {
            this.exam = new Exam();
        }

        /**
         * {@inheritDoc}
         */
        public void characters(char[] chars, int start, int length) {
            this.text = String.copyValueOf(chars, start, length).trim();
        }

        /**
         * {@inheritDoc}
         */
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {

            // get the attributes as properties
            final Properties props = toMap(attributes);
            switch (qName) {
                // <answer acceptable="true"> ... </answer>
                case "answer":
                    startElementAnswer(props);
                    break;

                case "entry":
                    startElementEntry(props);
                    break;

                case "exam":
                    startElementExam(props);
                    break;

                case "question":
                    startElementQuestion(props);
                    break;

                default:
                    logger.debug(format("Element not handled (uri = %s, localName = %s, qName = %s)", uri, localName, qName));
                    break;
            }
        }

        /**
         * {@inheritDoc}
         */
        public void endElement(String uri, String localName, String qName)
                throws SAXException {

            switch (qName) {
                case "answer":
                    endElementAnswer(text);
                    break;

                case "entry":
                    endElementEntry();
                    break;

                case "exam":
                    break;

                case "question":
                    endElementQuestion(text);
                    break;

                default:
                    logger.debug(format("endElement: uri = [%s], localName = [%s], qName = [%s]\n", uri, localName, qName));
                    break;
            }
        }

        /**
         * Handles the "answer" start tag
         * <pre><answer acceptable="true">Trade discount.</answer></pre>
         *
         * @param props the given {@link Properties attributes}
         */
        private void startElementAnswer(final Properties props) {
            // create a new answer
            currentAnswer = new Answer();
            currentAnswer.setAcceptable(Boolean.parseBoolean(props.getProperty("acceptable", "false")));

            // add the answer to the current question
            currentQuestion.add(currentAnswer);
        }

        /**
         * Handles the "answer" end tag
         * <pre><answer acceptable="true">Trade discount.</answer></pre>
         *
         * @param text the given text for the tag
         */
        private void endElementAnswer(final String text) {
            currentAnswer.setText(text);
            currentAnswer = null;
        }

        /**
         * Handles the "question" start tag
         * <pre><answer acceptable="true">Trade discount.</answer></pre>
         *
         * @param props the given {@link Properties attributes}
         */
        private void startElementQuestion(final Properties props) {
            // do nothing
        }

        /**
         * Handles the "question" end tag
         * <pre><question>Sales-oriented pricing objectives include:</question></pre>
         *
         * @param text the given text for the tag
         */
        private void endElementQuestion(final String text) {
            currentQuestion.setText(text);
        }

        /**
         * Handles the "Exam" start tag
         *
         * @param props the given {@link Properties attributes}
         */
        private void startElementExam(final Properties props) {
            // get the name of the exam
            final String name = props.getProperty("name", "Untitled");

            // set the exam name
            exam.setName(name);
        }

        /**
         * Handles the "entry" start tag
         * <pre><entry> .. </entry></pre>
         *
         * @param props the given {@link Properties attributes}
         */
        private void startElementEntry(final Properties props) {
            // create a new question
            currentQuestion = new Question();

            // add the question to the exam
            exam.add(currentQuestion);
        }

        private void endElementEntry() {
            currentQuestion = null;
        }

        /**
         * Converts the given attributes to properties
         *
         * @param attributes the given {@link Attributes attributes}
         * @return the {@link Properties properties}
         */
        private Properties toMap(final Attributes attributes) {
            final Properties props = new Properties();
            for (int index = 0; index < attributes.getLength(); index++) {
                final String key = attributes.getQName(index);
                final String value = attributes.getValue(index);
                props.put(key, value);
            }
            return props;
        }

    }

}