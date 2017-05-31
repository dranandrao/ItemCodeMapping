package com.itemcodemapping.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.opencsv.CSVWriter;

public class ExportDAO {
	public static void exportData() {
		Connection connection;
		String url = "jdbc:mysql://localhost:3306/mojix";
		String filename = "E:/Company/export.csv";
		File file = new File(filename);
		ResultSet resultset = null;
		//StringWriter writer = new StringWriter();
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(file,true), ',');
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			connection = DriverManager.getConnection(url, "root", "");
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from records");
			resultset = preparedStatement.executeQuery();
			csvWriter.writeAll(resultset, true);
			csvWriter.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
