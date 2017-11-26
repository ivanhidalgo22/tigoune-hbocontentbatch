/**
 * App.java
 *
 * @description: this class loads and pick ups the spring context.
 * @author IVAN HIDALGO.
 * @version 1.0
 * @date 10-03-2017.
 *
 **/

package com.tigoune.hbo.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * this class loads and pick ups the spring context.
 * @author IVAN HIDALGO.
 *
 */
public class App {

    /**
     * loads and pick ups the spring context.
     * @param args
     */
	public static void main(String[] args) {
		String springConfig = "spring/batch/config/job.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
	}
}