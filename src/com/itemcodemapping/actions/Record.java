package com.itemcodemapping.actions;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.itemcodemapping.dao.RecordDAO;

public class Record implements ServletResponseAware {

	private String cartonId;
	private String barCodeId;
	private String rfid;
	private int exportStatus = 0;
	private HttpServletResponse response;
	private RecordDAO recordDAO = null;

	public String getBarCodeId() {
		return barCodeId;
	}

	public void setBarCodeId(String barCodeId) {
		this.barCodeId = barCodeId;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public int getExportStatus() {
		return exportStatus;
	}

	public void setExportStatus(int exportStatus) {
		this.exportStatus = exportStatus;
	}

	public String getCartonId() {
		return cartonId;
	}

	public void setCartonId(String cartonId) {
		this.cartonId = cartonId;
	}

	public String execute() throws Exception {
		recordDAO = new RecordDAO();
		int record = recordDAO.combinationValidation(getBarCodeId(), getRfid());
		int errorStatus = 0;
		if (record > 0) {
			switch (record) {
			case 1:
				int insertion = recordDAO.insertData(this);
				if (insertion == 1) {
					errorStatus = 200;
				} else {
					errorStatus = 600;
				}
				break;
			case 2:
				errorStatus = 601;
				break;

			case 3:
				errorStatus = 600;
				break;
			default:
				errorStatus = 602;
				break;
			}
		}
		response.setStatus(errorStatus);
		return "success";
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

}
