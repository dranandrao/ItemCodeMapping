package com.itemcodemapping.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import com.opencsv.CSVWriter;

public class ExportImportService {
	String url = "jdbc:mysql://localhost:3306/mojix";
	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement = null;

	public void exportData() {
		/*
		 * This function exports the data present in our database to a file and
		 * ships it to the webservice.
		 */
		String filename = "E:/Company/export.csv";
		File file = new File(filename);
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(file, true), ',');
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			connection = DriverManager.getConnection(url, "root", "");
			preparedStatement = connection.prepareStatement("SELECT * from records");
			resultSet = preparedStatement.executeQuery();
			csvWriter.writeAll(resultSet, true);
			csvWriter.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void importData() {
		/*
		 * This function gets the file list in the folder and checks if that
		 * file is present in our database if that file is present in database
		 * it skips the process of else it loads the contents into database.
		 */
		String catalinaBase = System.getProperty("catalina.base");
		System.out.println("server file storage path" + catalinaBase);
		File itemasterFolderContents = new File(catalinaBase + "/webapps/item_master/");
		File[] filesInFolder = itemasterFolderContents.listFiles();
		for (File file : filesInFolder) {
			String filename = file.getName();
			/* If the file is not found in database.Executes the below logic. */
			if (!fileInDatabase(filename)) {
				saveFileNameInDatabase(filename);
				/* getting the file contents */
				File destinationFile = new File(catalinaBase + "/webapps/item_master/" + filename);
				saveFileContentsIntoDatabase(destinationFile);
			}
		}
	}

	public boolean fileInDatabase(String filename) {
		/* Finds the file name present in database. */
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			connection = DriverManager.getConnection(url, "root", "");
			preparedStatement = connection.prepareStatement("SELECT * FROM item_masters_imported WHERE file_name = ?");
			preparedStatement.setString(1, filename);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveFileNameInDatabase(String filename) {
		/* saves the file name in database */
		boolean result = false;
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			connection = DriverManager.getConnection(url, "root", "");
			java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
			preparedStatement = connection.prepareStatement("INSERT INTO item_masters_imported VALUES (?,?)");
			preparedStatement.setString(1, filename);
			preparedStatement.setTimestamp(2, timestamp);
			if (preparedStatement.executeUpdate() > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean saveFileContentsIntoDatabase(File itemMasterCSV) {
		/*
		 * Saves the contents of the file in database.
		 */
		boolean result = false;
		String query = "INSERT INTO item_master (ITEM, BARCODE, VPN, ITEM_DESC, ITEM_LEVEL, TRAN_LEVEL, DIFF_1, DIFF_2, DIFF_3, DIFF_4, DEPT, CLASS, SUBCLASS, SELLABLE_IND, PACK_IND, STANDARD_UOM, STATUS, SEASON_ID, PHASE_ID, SEASON_DESC, PHASE_DESC) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(itemMasterCSV));
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				/* reads line by line and saves the contents into database. */
				if (line != null) {
					String[] array = line.split(
							",");/* splitting the line based on , delimiter. */
					preparedStatement = connection.prepareStatement(query);
					if (!array[0].equals("")) {
						preparedStatement.setInt(1, Integer.valueOf(array[0]));
					} else {
						preparedStatement.setInt(1, 0);
					}
					if (!array[1].equals("")) {
						preparedStatement.setDouble(2, Double.valueOf(array[1]));
					} else {
						preparedStatement.setDouble(2, 0);
					}

					preparedStatement.setString(3, array[2]);
					preparedStatement.setString(4, array[3]);
					if (!array[4].equals("")) {
						preparedStatement.setInt(5, Integer.valueOf(array[4]));
					} else {
						preparedStatement.setInt(5, 0);
					}
					if (!array[5].equals("")) {
						preparedStatement.setInt(6, Integer.valueOf(array[5]));
					} else {
						preparedStatement.setInt(6, 0);
					}

					preparedStatement.setString(7, array[6]);
					preparedStatement.setString(8, array[7]);
					preparedStatement.setString(9, array[8]);
					preparedStatement.setString(10, array[9]);
					if (!array[10].equals("")) {
						preparedStatement.setInt(11, Integer.valueOf(array[10]));
					} else {
						preparedStatement.setInt(11, 0);
					}
					if (!array[11].equals("")) {
						preparedStatement.setInt(12, Integer.valueOf(array[11]));
					} else {
						preparedStatement.setInt(12, 0);
					}

					preparedStatement.setString(13, array[12]);
					preparedStatement.setString(14, array[13]);
					preparedStatement.setString(15, array[14]);
					preparedStatement.setString(16, array[15]);
					preparedStatement.setString(17, array[16]);
					if (!array[17].equals("")) {
						preparedStatement.setInt(18, Integer.valueOf(array[17]));
					} else {
						preparedStatement.setInt(18, 0);
					}
					if (!array[18].equals("")) {
						preparedStatement.setInt(19, Integer.valueOf(array[18]));
					} else {
						preparedStatement.setInt(19, 0);
					}
					preparedStatement.setString(20, array[19]);
					preparedStatement.setString(21, array[20]);

					if (preparedStatement.executeUpdate() > 0) {
						result = true;
					}

				}

			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
