package com.github.ldaniels528.exams.dao;

import com.github.ldaniels528.exams.models.Exam;

/**
 * Exam File Data Access Object
 * @author lawrence.daniels@gmail.com
 */
public interface ExamDAO {

	/**
	 * Returns the examination
	 * @return the examination
	 */
	Exam getExam() throws DAOException;

}
