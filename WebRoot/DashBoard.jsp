<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="css/bootstrap-responsive.css">
<link rel="stylesheet" href="css/bootstrap-responsive.min.css">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<title>Mojix</title>
<style>
body {
	font-family: inherit;
}

div.include {
	position: absolute;
	top: 20%;
	right: 5%;
	width: 40%;
	height: 50%;
}

button.btn-primary {
	border: none;
	margin: 3%;
}

div.custom {
	float: left;
	width: 50%;
	padding: 5% 0;
}

footer {
	position: absolute;
	bottom: 0;
	width: 100%;
}
</style>
<script type="text/javascript">
	/*barcode can have any characters.*/
	var barCodeValidator_string = /^62927+[0-9]+$/;
	/*But should start with AEA2*/
	var rfIdValidator_string = /^AEA2+[0-9]+$/;
	/*barcode can have any characters.*/
	var cartonIdValidator_string = /^[1-9]+/;
	/*Validation of barcode id*/
	function validationofBarCode() {
		$("#rfid").focus();
		if (barcodeIdValidator() == "true") {
			$("#barcode_status").attr({
				"style" : "display:visible",
				"src" : "sucess_check.png"
			});
			formValidation();
			return true;
		} else {
			$("#barcode_status").attr({
				"style" : "display:visible",
				"src" : "failure_check.png"
			});
			return false;
		}

	}
	/*Validation of rfid*/
	function validationofrfid() {
		if (rfidValidator() == "true") {
			$("#rfid_status").attr({
				"style" : "display:visible",
				"src" : "sucess_check.png"
			});
			formValidation();
			return true;
		} else {
			$("#rfid_status").attr({
				"style" : "display:visible",
				"src" : "failure_check.png"
			});
			return false;
		}
	}
	/*validates barcodeid and rfid and cartonid if validation is successful then it send the data to action.*/
	function formValidation() {
		if (barcodeIdValidator() == "true" && rfidValidator() == "true"
				&& cartonIdValidator() == "true") {

			var xhttp = new XMLHttpRequest();

			var cartonId = $("#cartonId").val();
			var rfid = $("#rfid").val();
			var barCodeId = $("#barCodeId").val();
			var params = "cartonId=" + cartonId + "&barCodeId=" + barCodeId
					+ "&rfid=" + rfid;

			url = "${pageContext.request.contextPath}/recordinsertion";
			xhttp.open("POST", url, true);
			xhttp.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			xhttp.send(params);

			xhttp.onreadystatechange = function() {
				var result = "";
				switch (this.status) {
				case 200:
					/* status 200 is success condition.*/
					result = "Success";
					$("#error_div")
							.attr(
									'style',
									'border: 1px solid-red;border-radius: 10px;padding: 5%;background-color: #adebad;text-align: justify;display:visible');
					$("#recordValidation").attr('src', 'validation_succes.png');
					refresh(cartonId);
					break;
				case 600:
					/* status 600 is generated when RFID or BARCODE is already present in database.*/
					result = "Barcode ID/RFID already present in Records";
					$("#error_div")
							.attr(
									'style',
									'border: 1px solid-red;border-radius: 10px;padding: 5%;background-color: #ff9980;text-align: justify;display:visible')
					$("#recordValidation")
							.attr('src', 'validation_failure.png');
					break;
				case 601:
					/* status 601 is generated when barcode is not present in item_master table. */
					result = "Barcode not present in item_masster record.";
					$("#error_div")
							.attr(
									'style',
									'border: 1px solid-red;border-radius: 10px;padding: 5%;background-color: #ff9980;text-align: justify;display:visible')
					$("#recordValidation")
							.attr('src', 'validation_failure.png');
					break;
				default:
					/* any other status is generated when unknown exception is thrown. */
					result = "Error Undefined..";
					$("#error_div")
							.attr(
									'style',
									'border: 1px solid-red;border-radius: 10px;padding: 5%;background-color: #ff9980;text-align: justify;display:visible')
					$("#recordValidation")
							.attr('src', 'validation_failure.png');
					break;

				}
				$("#error_msg").text(result);
				$("#recordValidation").attr('style', 'display:visible');

			};
		}
	}
	function barcodeIdValidator() {
		/* gets the value from textfield and checks the length and matches the regularexpression.*/
		var str = $("#barCodeId").val();
		if (str.match(barCodeValidator_string)) {
			return "true";
		} else {
			return "false";
		}
	}
	function rfidValidator() {
		/* gets the value from textfield and checks the length and matches the regularexpression.*/
		var str = $("#rfid").val();
		if (str.match(rfIdValidator_string)) {
			return "true";
		} else {
			return "false";
		}

	}
	function cartonIdValidator() {
		/* gets the value from textfield and checks the length and matches the regularexpression.*/
		var str = $("#cartonId").val();
		if (str.match(cartonIdValidator_string)) {
			return "true";
		} else {
			return "false";
		}
	}
	/*Exports the data to webservice provided.*/
	function closeCarton() {
		importExport("Export");
		console.log("after export import");
		$.ajax({
			url : "${pageContext.request.contextPath}/redirectAction",
			type : "get",
			success : function(response) {
				$("#body").html(response);
				$("#cartonId").val("");
				$("#cartonId").focus();
			},
			error : function(xhr) {
				console.log("error");
			}
		});
	}
	/*clear the form data.*/
	function clearEntry() {
		$("#cartonId").val("");
		$("#barCodeId").val("");
		$("#rfid").val("");
		$.ajax({
			url : "${pageContext.request.contextPath}/redirectAction",
			type : "get",
			success : function(response) {
				$("#body").html(response)
				$("#cartonId").focus();
			},
			error : function(xhr) {
				console.log("error");
			}
		});
	}
	/*Validation of cartonId*/
	function validationOfCartonId() {
		if (cartonIdValidator() == "true") {
			$("#error_div").attr('style', 'display:none');
			formValidation();
		} else {
			$("#error_msg").text("Carton Id can't be blank..");
			$("#error_div")
					.attr(
							'style',
							'border: 1px solid-red;border-radius: 10px;padding: 5%;background-color: #ff9980;text-align: justify;display:visible')
		}
		initailisation();
	}
	function refresh(cartonId) {
		/* refreshes the page sends a get request to page with sets the cartonid to previous cartonid and shifts the focus 
		 *  to barcodeId.
		 */
		$.ajax({
			url : "${pageContext.request.contextPath}/redirectAction",
			type : "get",
			success : function(response) {
				$("#body").html(response)
				$("#cartonId").val(cartonId);
				$("#barCodeId").focus();
			},
			error : function(xhr) {
				console.log("error");
			}
		});
	}
	/* this function is called when page is loaded. cartonId has the focus. barcodeId and rfId is disabled till we fill the data to cartonId.*/
	function initailisation() {
		console.log("Initialization called");
		$("#cartonId").focus();
		if (cartonIdValidator() == "true") {
			$("#barCodeId").attr("disabled", false);
			$("#rfid").attr("disabled", false);
			$("#barCodeId").focus();
		} else {
			$("#barCodeId").attr("disabled", true);
			$("#rfid").attr("disabled", true);
		}
		setInterval(importItemMaster, 1800000);
		importExport("import");
	}
	/*checks for enter key pressed.*/
	function onKeyUpCartonId() {
		var key = window.event.keyCode;
		if (key == 13) {
			validationOfCartonId();
			$("#barCodeId").focus();
		}

	}
	/*checks for enter key pressed.*/
	function onKeyUpBarCodeId() {
		var key = window.event.keyCode;
		if (key == 13) {
			validationofBarCode();
			$("#rfid").focus();
		}
	}
	/*checks for enter key pressed.*/
	function onKeyUpRfId() {
		var key = window.event.keyCode;
		if (key == 13) {
			validationofrfid();
		}
	}
	function importExport(str) {
		$.ajax({
			url : "${pageContext.request.contextPath}/exportData",
			type : "get",
			data : {
				'requestAction' : str
			},
			success : function(response) {

			},
			error : function(xhr) {

			}

		});
	}
	function importItemMaster() {
		console.log("importItemMaster called");
		importExport("import");
	}
</script>
</head>
<body id="body" onload="initailisation()" style="overflow-x: hidden">
	<div style="background-color: #80aaff; color: white; padding: 2%;">
		<table align="right" style="margin-right: 2%">
			<tr>
				<td><button onclick="" class="btn-primary">Update/Import
						ITEM MASTER</button></td>
				<td><button onclick="closeCarton()" class="btn-primary">Close
						Carton</button></td>
				<td><button onclick="clearEntry()" class="btn-primary">Clear
						Entry</button></td>
			</tr>
		</table>
		<h2>Record Insertion and Retrieval.</h2>
	</div>

	<div class="container">
		<div class="jumbotron" style="padding-top: 2%">
			<h1>Insert a record</h1>
		</div>
		<div class="custom">
			<s:form theme="simple" action="recordinsertion" id="record">
				<table class="table-condensed">
					<tr>
						<td><h4>
								<s:label>Carton ID:</s:label>
							</h4></td>
						<td colspan="2"><s:textfield name="cartonId" id="cartonId"
								onkeyup="onKeyUpCartonId()" /></td>
						<td>&nbsp;</td>
						<td rowspan="3" style="width: 80"><img class="validation"
							alt="recordValidation" src="validation_succes.png"
							id="recordValidation" height="60" width="60" align="right"
							style="display: none" /></td>
					</tr>
					<tr>
						<td><h4>
								<s:label>Item Code :</s:label>
							</h4></td>
						<td colspan="2"><s:textfield name="barCodeId" id="barCodeId"
								onkeyup="onKeyUpBarCodeId()" /></td>
						<td><img alt="barcode_status"
							src="<s:url value='sucess_check.png'/>" height="30" width="30"
							id="barcode_status" class="item_barcode_validator"
							style="display: none"></td>

					</tr>
					<tr>
						<td><h4>
								<s:label>RF ID:</s:label>
							</h4></td>
						<td colspan="2"><s:textfield name="rfid" id="rfid"
								onkeyup="onKeyUpRfId()" /></td>
						<td><img alt="barcode_status"
							src="<s:url value='sucess_check.png'/>" height="30" width="30"
							id="rfid_status" class="item_barcode_validator"
							style="display: none"></td>
					</tr>
					<tr>
						<td colspan="4" style="padding: 5%"><div id="error_div"
								style="border: 1px solid-red; border-radius: 10px; padding: 5%; background-color: #ff9980; text-align: justify; display: none">
								<p>
								<h6 style="color: #595959" id="error_msg">There was a
									error..</h6>
								</p>
							</div></td>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
	<div class="include" id="list_of_records"><%@include
			file="Records.jsp"%></div>
	<footer align="center"
		style="display: block; background-color: #80aaff; color: white">
		<p>Mojix. Inc.</p>
		<p>Version 1.0.26</p>
	</footer>
	<script src="js/jquery-1.11.3.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>

