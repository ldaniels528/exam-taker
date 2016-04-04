package com.github.ldaniels528.exams.ui;

import com.github.ldaniels528.exams.models.Answer;
import com.github.ldaniels528.exams.models.Exam;
import com.github.ldaniels528.exams.models.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static java.lang.String.format;

/**
 * Exam-Taker Content Pane
 *
 * @author lawrence.daniels@gmail.com
 */
@SuppressWarnings("serial")
public class ExamContentPane extends ExamPanel {
    private final List<ButtonGroup> groups;
    private final Exam exam;
    private final Icon correctIcon;
    private final Icon wrongIcon;

    /**
     * Default Constructor
     */
    public ExamContentPane(final Exam exam) {
        this.exam = exam;
        this.groups = new ArrayList<>(exam.length());

        // load the images
        this.correctIcon = ContentLoader.getInstance().loadIcon("/images/correct.gif");
        this.wrongIcon = ContentLoader.getInstance().loadIcon("/images/incorrect.png");

        // create the actual content panel
        final ExamPanel contentPanel = new ExamPanel();
        final JScrollPane jsp = new JScrollPane(contentPanel);
        jsp.setPreferredSize(new Dimension(1024, 600));

        // attach the scroll pane
        super.gbc.anchor = GridBagConstraints.NORTHWEST;
        super.attach(0, 0, jsp, GridBagConstraints.NORTHWEST);

        // attach the questions
        attachQuestions(contentPanel, exam);
    }

    /**
     * Attaches the given questions to the given form
     *
     * @param panel the given {@link ExamPanel form}
     * @param exam  the given of {@link Exam exam}
     */
    private void attachQuestions(final ExamPanel panel, final Exam exam) {
        // randomize the questions
        exam.shuffleQuestions();
        groups.clear();

        // build the form
        int index = 0;
        int row = 0;
        for (final Question question : exam.getQuestions()) {
            // attach a question
            final JLabel label = new JLabel(format("%d. %s", ++index, question.getText()));
            //label.
            panel.attach(0, row++, label);

            // attach the answers
            final ButtonGroup group = new ButtonGroup();
            for (final Answer answer : question.getAnswers()) {
                final JRadioButton radio = new JRadioButton(answer.getText());
                group.add(radio);
                panel.attach(0, row++, radio);
            }

            // capture the button group
            groups.add(group);

            // add a separator
            panel.attach(0, row++, new JSeparator());
        }

        // anchor the text
        panel.attach(0, row, new JLabel(), GridBagConstraints.NORTHWEST);

        // attach the "Check Answers" button
        super.attach(0, row, new CheckAnswersButton(), GridBagConstraints.NORTHWEST);
    }

    /**
     * Scores and updates the test
     *
     * @param answers   the correct answers
     * @param responses the user's answers
     */
    private void scoreAndUpdateTest(final int[] answers, final int[] responses) {
        int missed = 0;
        for (int index = 0; index < answers.length; index++) {
            scoreAnswer(index, answers[index], responses[index]);
            if (answers[index] != responses[index]) {
                missed++;
            }
        }
        System.out.printf("You missed %d answers\n", missed);
    }

    private void scoreAnswer(final int index, int correctItem, int answeredItem) {
        // get the corresponding button group
        final ButtonGroup group = groups.get(index);

        // get the enumeration of buttons
        final List<AbstractButton> buttons = Collections.list(group.getElements());

        // highlight the correct answer
        if(answeredItem != -1) {
            final JRadioButton button0 = (JRadioButton) buttons.get(correctItem);
            button0.setIcon(correctIcon);
        }

        // highlight the wrong answer
        if ((answeredItem != correctItem) && (answeredItem != -1)) {
            final JRadioButton button1 = (JRadioButton) buttons.get(answeredItem);
            button1.setIcon(wrongIcon);
        }
    }

    /**
     * Returns the matrix of user responses
     *
     * @return the matrix of user responses
     */
    private int[] getUserResponsesMatrix() {
        final int[] matrix = new int[groups.size()];
        int index = 0;
        for (final ButtonGroup group : groups) {
            matrix[index++] = determineSelection(group);
        }
        return matrix;
    }

    /**
     * Determines the current selection of the button group
     *
     * @param group the given {@link ButtonGroup button group}
     * @return the selected index or <code>-1</code> if not selected
     */
    private int determineSelection(final ButtonGroup group) {
        // get the enumeration of buttons
        final Enumeration<AbstractButton> e = group.getElements();

        // determine the selected index
        int result = 0;
        while (e.hasMoreElements()) {
            final AbstractButton button = e.nextElement();
            if (button.isSelected()) {
                return result;
            }
            result++;
        }
        return -1;
    }

    /**
     * The "Check Answers" Button
     *
     * @author lawrence.daniels@gmail.com
     */
    private class CheckAnswersButton extends JButton {

        /**
         * Default Constructor
         */
        CheckAnswersButton() {
            super("Check Answers");
            super.addActionListener(new CheckAnswersAction());
        }
    }

    /**
     * The "Check Answers" Action
     *
     * @author lawrence.daniels@gmail.com
     */
    private class CheckAnswersAction implements ActionListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(final ActionEvent event) {
            // create the response matrix
            final int[] responses = getUserResponsesMatrix();
            final int[] answers = exam.getAnswersMatrix();

            // score & update the test
            scoreAndUpdateTest(answers, responses);
        }
    }

}