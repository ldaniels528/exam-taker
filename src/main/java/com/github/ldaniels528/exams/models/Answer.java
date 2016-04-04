package com.github.ldaniels528.exams.models;

/**
 * Represents a potential Answer to a Question
 *
 * @author lawrence.daniels@gmail.com
 */
public class Answer {
    private String text;
    private boolean acceptable;

    /**
     * Default Constructor
     */
    public Answer() {
        super();
    }

    /**
     * Returns the questions text
     *
     * @return the questions text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the question text
     *
     * @param text given the question text
     */
    public void setText(final String text) {
        this.text = text;
    }

    public boolean isAcceptable() {
        return acceptable;
    }

    public void setAcceptable(final boolean acceptable) {
        this.acceptable = acceptable;
    }

}
