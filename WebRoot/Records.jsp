<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="css/bootstrap-responsive.css">
<link rel="stylesheet" href="css/bootstrap-responsive.min.css">
<style type="text/css">
th.records {
	background-color: #e6b800;
	color: #000000;
}

td.records, th.records {
	border: 1px solid #dddddd;
	text-align: left;
	border-collapse: collapse;
}

td.records {
	background-color: #cccccc;
}

di.container {
	width: 50%;
}
</style>
</head>
<body>
	<sql:setDataSource var="myDS" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/mojix" user="root" password="" />

	<sql:query var="records" dataSource="${myDS}">
        SELECT * FROM records;
    </sql:query>
	<div class="container" id="list_of_records">
		<div class="jumbotron">
			<h1>List of Records</h1>
		</div>
		<h3>
			Total number of records:
			<c:out value="${records.rowCount}" />
		</h3>
		<table class="table-condensed" cellspacing="5">
			<tr>
				<th class="records">Carton ID</th>
				<th class="records">Bar Code ID</th>
				<th class="records">RF ID</th>
				<th class="records">Export Status</th>

			</tr>
			<c:forEach var="record" items="${records.rows}">
				<tr>
					<td class="records"><c:out value="${record.cartonId}" />
					<td class="records"><c:out value="${record.barCodeId}" /></td>
					<td class="records"><c:out value="${record.rfId}" /></td>
					<td class="records"><c:out value="${record.exportStatus}" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script src="js/jquery-1.11.3.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>
