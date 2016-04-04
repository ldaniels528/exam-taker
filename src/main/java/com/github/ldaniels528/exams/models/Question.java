package com.github.ldaniels528.exams.models;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a Question
 *
 * @author lawrence.daniels@gmail.com
 */
public class Question {
    private final List<Answer> answers = new LinkedList<>();
    private String text;

    /**
     * Default Constructor
     */
    public Question() {
        super();
    }

    /**
     * Returns the index of the correct answer
     *
     * @return the answer index or -1, if not found
     */
    int getAnswerIndex() {
        int index = 0;
        for (final Answer answer : answers) {
            if (answer.isAcceptable()) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Indicates whether the given index is an acceptable answer
     *
     * @param index the given answer index
     * @return true, if the given index is an acceptable answer
     */
    public boolean isCorrect(int index) {
        return (answers.size() > index) && answers.get(index).isAcceptable();
    }

    /**
     * Adds a new potential answer
     *
     * @param answer the given {@link Answer answers}
     */
    public void add(final Answer answer) {
        answers.add(answer);
    }

    /**
     * Returns the potential answers
     *
     * @return an array of the potential {@link Answer answers}
     */
    public Answer[] getAnswers() {
        return answers.toArray(new Answer[answers.size()]);
    }

    /**
     * Returns the question text
     *
     * @return the question text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the question text
     *
     * @param text the question text
     */
    public void setText(final String text) {
        this.text = text;
    }

}
