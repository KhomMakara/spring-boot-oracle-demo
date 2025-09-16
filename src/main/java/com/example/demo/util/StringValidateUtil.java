package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;


@UtilityClass
public class StringValidateUtil extends StringUtils  {

	/**
	 * <pre>
	 *	Checks if the string is blank or null
	 * </pre>
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty( String s ) {

		return StringUtils.isEmpty( s );
	}

}
