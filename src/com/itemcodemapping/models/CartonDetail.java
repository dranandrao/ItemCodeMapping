/**
 * Gets the details of product present in records table.
 */
package com.itemcodemapping.models;

/**
 * @author Anand
 *
 */
public class CartonDetail {
	private int Id;
	private String cartonId;
	private String barCodeId;
	private String rfid;
	private int exportStatus;

	/**
	 * @return the cartonId
	 */
	public String getCartonId() {
		return cartonId;
	}

	/**
	 * @param cartonId
	 *            the cartonId to set
	 */
	public void setCartonId(String cartonId) {
		this.cartonId = cartonId;
	}

	/**
	 * @return the barCodeId
	 */
	public String getBarCodeId() {
		return barCodeId;
	}

	/**
	 * @param barCodeId
	 *            the barCodeId to set
	 */
	public void setBarCodeId(String barCodeId) {
		this.barCodeId = barCodeId;
	}

	/**
	 * @return the rfid
	 */
	public String getRfid() {
		return rfid;
	}

	/**
	 * @param rfid
	 *            the rfid to set
	 */
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		Id = id;
	}

	/**
	 * @return the exportStatus
	 */
	public int getExportStatus() {
		return exportStatus;
	}

	/**
	 * @param exportStatus the exportStatus to set
	 */
	public void setExportStatus(int exportStatus) {
		this.exportStatus = exportStatus;
	}

}
