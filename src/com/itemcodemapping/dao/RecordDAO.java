package com.itemcodemapping.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.itemcodemapping.actions.Record;
import com.itemcodemapping.models.CartonDetail;

public class RecordDAO {
	Connection connection = null;
	String url = "jdbc:mysql://localhost:3306/mojix";
	PreparedStatement preparedStatement = null;
	ResultSet resultset = null;

	public int insertData(Record record) {

		int result = 0;
		int status = 0;
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			connection = DriverManager.getConnection(url, "root", "");
			preparedStatement = connection.prepareStatement("INSERT into records values (?,?,?,?)");
			preparedStatement.setString(1, record.getCartonId());
			preparedStatement.setString(2, record.getBarCodeId());
			preparedStatement.setString(3, record.getRfid());
			preparedStatement.setInt(4, record.getExportStatus());
			status = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			result = 3;
			return result;
		} finally {
			try {
				if (connection != null && connection.isClosed()) {
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (status > 0) {
			result = 1;
		} else {
			result = 3;
		}

		return result;
	}

	public int combinationValidation(String barCodeId, String rfid) {

		int status = 0;
		try {

			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			connection = DriverManager.getConnection(url, "root", "");
			preparedStatement = connection.prepareStatement("SELECT * from item_master WHERE BARCODE = ?");
			preparedStatement.setDouble(1, Double.valueOf(barCodeId));
			resultset = preparedStatement.executeQuery();
			if (resultset.next()) {
				preparedStatement = connection
						.prepareStatement("SELECT * from records WHERE barCodeId = ? and rfid = ?");
				preparedStatement.setString(1, barCodeId);
				preparedStatement.setString(2, rfid);
				ResultSet childResultset = preparedStatement.executeQuery();
				if (childResultset.next()) {
					status = 3;
				} else {
					status = 1;
				}
			} else {
				status = 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null && connection.isClosed()) {
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return status;
	}

	public ArrayList<CartonDetail> getRecords() {
		ArrayList<CartonDetail> cartonDetails = new ArrayList<CartonDetail>();
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			connection = DriverManager.getConnection(url, "root", "");
			preparedStatement = connection.prepareStatement("SELECT * from records");
			resultset = preparedStatement.executeQuery();
			while (resultset.next()) {
				CartonDetail cartonDetail = new CartonDetail();
				cartonDetail.setId(resultset.getInt("Id"));
				cartonDetail.setCartonId(resultset.getString("cartonId"));
				cartonDetail.setBarCodeId(resultset.getString("barCodeId"));
				cartonDetail.setExportStatus(resultset.getInt("exportStatus"));
				cartonDetails.add(cartonDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null && connection.isClosed()) {
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cartonDetails;
	}
}
