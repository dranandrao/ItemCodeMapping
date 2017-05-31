package com.itemcodemapping.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.itemcodemapping.service.ExportImportService;

public class ExportData implements ServletRequestAware {
	ExportImportService exportImportService = null;
	HttpServletRequest httpServletRequest = null;

	public String execute() {
		exportImportService = new ExportImportService();
		String action = httpServletRequest.getParameter("requestAction");
		switch (action) {
		case "import":
			System.out.println("import Data");
			exportImportService.importData();
			break;
		case "export":
			System.out.println("Export Data");
			exportImportService.exportData();
			break;
		default:
			break;
		}
		return "success";
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.httpServletRequest = request;

	}
}
