<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="css/bootstrap-responsive.css">
<link rel="stylesheet" href="css/bootstrap-responsive.min.css">
<style type="text/css">
div.container {
	float: center;
	width: 50%;
	padding: 7% 0;
}
s.custom {
	background-color: "#ff4000";
}
</style>
</head>
<body>
	<div class="container" align="center">
		<div class="jumbotron text-center">
			<h1>Login</h1>
		</div>
		<s:form action="login" theme="simple">
			<table class="table-condensed">
				<tr>
					<td><h4 class="jumbotron text-center">
							<s:label>Username:</s:label>
						</h4></td>
					<td><s:textfield name="userName" /></td>
				</tr>
				<tr>
					<td><h4 class="jumbotron text-center">
							<s:label>Password:</s:label>
						</h4></td>
					<td><s:password name="passWord" /></td>
				</tr>
				<tr>
					<td colspan="4" align="center"><s:submit value="Login" cssClass="custom"/></td>
				</tr>

			</table>
		</s:form>
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="js/jquery-1.11.3.min.js"></script>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.js"></script>
</body>
</html>