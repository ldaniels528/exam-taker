package com.github.ldaniels528.exams.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents an Exam
 *
 * @author lawrence.daniels@gmail.com
 */
public class Exam {
    private final List<Question> questions = new LinkedList<>();
    private String name;

    /**
     * Default Constructor
     */
    public Exam() {
        this("Untitled");
    }

    /**
     * Creates a new exam instance
     *
     * @param name the name of the exam
     */
    public Exam(final String name) {
        this.name = name;
    }

    /**
     * Returns the name of the exam
     *
     * @return the name of the exam
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the exam
     *
     * @param name the name of the exam
     */
    public void setName(final String name) {
        this.name = name;
    }

    public void add(Question question) {
        questions.add(question);
    }

    /**
     * Returns the questions currently associated to the exam
     *
     * @return the questions currently associated to the exam
     */
    public Question[] getQuestions() {
        return questions.toArray(new Question[questions.size()]);
    }

    /**
     * Sets the questions currently associated to the exam
     *
     * @param questions the questions currently associated to the exam
     */
    public void setQuestions(final Question[] questions) {
        this.questions.clear();
        this.questions.addAll(Arrays.asList(questions));
    }

    /**
     * Randomizes the exam question
     */
    public void shuffleQuestions() {
        Collections.shuffle(questions);
    }

    public int[] getAnswersMatrix() {
        int[] matrix = new int[questions.size()];
        int index = 0;
        for (final Question question : questions) {
            matrix[index++] = question.getAnswerIndex();
        }
        return matrix;
    }

    /**
     * Returns the length (number of questions) of the exam
     *
     * @return the length
     */
    public int length() {
        return questions.size();
    }

}
