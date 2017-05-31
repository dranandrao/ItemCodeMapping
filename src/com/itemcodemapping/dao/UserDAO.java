package com.itemcodemapping.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	Connection connection = null;
	String url = "jdbc:mysql://localhost:3306/mojix";
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public boolean userValidation(String username, String password) {
		boolean result = false;

		try {

			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			connection = DriverManager.getConnection(url, "root", "");
			preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
