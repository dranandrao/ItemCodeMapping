package com.itemcodemapping.actions;

import java.util.ArrayList;

import com.itemcodemapping.dao.RecordDAO;
import com.itemcodemapping.dao.UserDAO;
import com.itemcodemapping.models.CartonDetail;

public class Login {
	private String userName;
	private String passWord;
	UserDAO userDAO = null;
	RecordDAO recordDAO = null;
	ArrayList<CartonDetail> cartonDetails;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String execute() {
		userDAO = new UserDAO();
		recordDAO =  new RecordDAO();
		String result = validateCredentials();
		return result;
	}

	public String validateCredentials() {
		if (userDAO.userValidation(this.userName, this.passWord)) {
			cartonDetails = recordDAO.getRecords();
			System.out.println("Login successful");
			return "success";
		} else {
			System.out.println("Login Failure");
			return "failure";
		}

	}
}
