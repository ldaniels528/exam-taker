package com.github.ldaniels528.exams.ui;

import java.awt.Font;

/**
 * Exam-Taker Font Manager
 * @author lawrence.daniels@gmail.com
 */
public class ExamFontManager {
	private static final Font BOLD_FONT		= new Font( "Arial", Font.BOLD, 16 );
	private static final Font DEFAULT_FONT	= new Font( "Arial", Font.PLAIN, 16 );
	
	/**
	 * Sets the default font for the given panel
	 * @param panel the given {@link ExamPanel panel}
	 */
	public static void setBoldFont( final ExamPanel panel ) {
		panel.setFont(BOLD_FONT);
	}
	
	
	/**
	 * Sets the default font for the given panel
	 * @param panel the given {@link ExamPanel panel}
	 */
	public static void setDefaultFont( final ExamPanel panel ) {
		panel.setFont(DEFAULT_FONT);
	}

}
