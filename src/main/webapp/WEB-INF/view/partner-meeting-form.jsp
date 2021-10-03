<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!doctype html>
<html lang="en">

<head>
	
	<title>Save Customer</title>

	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
    
    <script>
    	$(function () {
        	$("#datepicker").datepicker();
        });
    	
    	$(function() {
    	    $('#timepicker').timepicker();

    	});
    </script>

	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/style.css">

	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/add-customer-style.css">
	
	<style>
		.error {color:red}
	</style>

</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Schedule a Meeting with Your Partner!</h2>
		</div>
	</div>

	<div id="container">
		<h3>Save Meeting Info</h3>
	
		<form:form action="processPartnerMeetingForm" 
				   modelAttribute="partnerMeeting" 
				   method="POST">
			
			<!-- Place for messages: error, alert etc ... -->
			<div class="form-group">
				<div class="col-xs-15">
					<div>
								
						<!-- Check for registration error -->
						<c:if test="${FindPartnerError != null}">
								
							<div class="alert alert-danger col-xs-offset-1 col-xs-10">
								${FindPartnerError}
							</div>
		
						</c:if>
																			
					 </div>
				</div>
			</div>

			<!-- need to associate this data with customer id -->
			<form:hidden path="initializerName"/>
			<form:hidden path="id"/>
					
			<table>
				<tbody>
					<tr>
						<td><label>Title:</label></td>
						<td>
							<form:input path="title" />
							<form:errors path="title" cssClass="error" />
						</td>
					</tr>
				
					<tr>
						<td><label>Description:</label></td>
						<td>
							<form:textarea path="description" />
							<form:errors path="description" cssClass="error"/>
						</td>
					</tr>
					
					<tr>
						<td><label>Participants:</label></td>
						<td>
							<form:input path="participantName" />
							<form:errors path="participantName" cssClass="error" />
						</td>
					</tr>
					
					<tr>
						<td><label>Date:</label></td>
						<td>
							<form:input path="startDate" type="text" id="datepicker" />
							<form:errors path="startDate" cssClass="error"/>
						<td>
					</tr>
					
					<tr>
						<td><label>Time:</label></td>
						<td>
							<form:input path="startTime" type="text" id="timepicker" />
							<form:errors path="startTime" cssClass="error"/>
						<td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
				
				</tbody>
			</table>
		
		
		</form:form>
	
		<div style="clear; both;"></div>
		
		<p>
			<a href="${pageContext.request.contextPath}/">Back to home</a>
		</p>
	
	</div>

</body>
</html>