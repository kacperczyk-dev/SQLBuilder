package com.sql;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;



@SpringBootApplication
public class SqlBuilderApplication {
	static ConnectionClass cc;

	public static void main(String[] args) {
		System.loadLibrary("sqljdbc_auth");
		new SpringApplicationBuilder(SqlBuilderApplication.class).headless(false).run(args);
		cc = new ConnectionClass();
		new MainWindow();
	}
}
