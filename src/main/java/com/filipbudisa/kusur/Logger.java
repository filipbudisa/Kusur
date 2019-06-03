package com.filipbudisa.kusur;

import org.springframework.web.context.request.WebRequest;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private static Logger instance = null;

	public static Logger getInstance() {
		return instance;
	}

	private PrintWriter info;
	private PrintWriter error;

	public Logger() throws IOException {
		instance = this;

		File logDir = new File("log");
		if(!logDir.exists() || !logDir.isDirectory()){
			logDir.mkdir();
		}

		info = new PrintWriter(new FileOutputStream("log/info.log", true), true);
		error = new PrintWriter(new FileOutputStream("log/error.log", true), true);

		info.println();
		error.println();
	}

	public static void info(String data){
		Logger innstance = getInstance();
		instance.info.println(innstance.formatOutput(data));
	}

	public static void error(String type){
		Logger instance = getInstance();
		Logger.instance.error.println(instance.formatOutput(type));
	}

	public static void error(String type, String body){
		Logger instance = getInstance();
		Logger.instance.error.println(instance.formatOutput(String.format("[%s] %s", type, body)));
	}

	private String formatOutput(String data){
		return String.format("[%s] %s",
				new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").format(new Date()),
				data);
	}
}
