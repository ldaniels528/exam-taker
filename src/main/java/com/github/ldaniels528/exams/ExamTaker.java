package com.github.ldaniels528.exams;

import com.github.ldaniels528.exams.dao.ExamDAOXMLFile;
import com.github.ldaniels528.exams.models.Exam;
import com.github.ldaniels528.exams.ui.ExamContentPane;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

import static java.lang.String.format;

/**
 * Exam-Taker Application
 *
 * @author lawrence.daniels@gmail.com
 */
@SuppressWarnings("serial")
public class ExamTaker extends JFrame {
    private static final String VERSION = "0.10";

    /**
     * Default Constructor
     */
    public ExamTaker(final Exam exam) {
        super(format("Exam*Taker v%s - %s", VERSION, exam.getName()));
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setContentPane(new ExamContentPane(exam));
        super.pack();
        super.setResizable(false);
        super.setVisible(true);
    }

    /**
     * Starts up the application
     *
     * @param args the given command line arguments
     */
    public static void main(final String[] args) {
        try {
            // check the command line arguments
            if (args.length < 1) {
                throw new IllegalArgumentException(format("%s <examFile.xml>", ExamTaker.class.getName()));
            }

            // get a reference to the exam file - it must exist
            final File examFile = new File(args[0]);
            if (!examFile.exists()) {
                throw new FileNotFoundException(examFile.getAbsolutePath());
            }

            // load the exam file
            final Exam exam = new ExamDAOXMLFile(examFile).getExam();

            // start the application client
            new ExamTaker(exam);

        } catch (final Throwable cause) {
            cause.printStackTrace();
        }
    }

}
