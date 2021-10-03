<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!doctype html>
<html lang="en">

<head>
	
	<title>Save Customer</title>

	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/style.css">

	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/add-customer-style.css">

</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>

	<div id="container">
		<h3>Save Customer</h3>
	
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
					
			<table>
				<tbody>
					<tr>
						<td><label>Title:</label></td>
						<td>
							<form:input path="title" />
							<form:errors path="title" />
						</td>
					</tr>
				
					<tr>
						<td><label>Description:</label></td>
						<td>
							<form:textarea path="description" />
							<form:errors path="description" />
						</td>
					</tr>
					
					<tr>
						<td><label>Participants:</label></td>
						<td>
							<form:input path="participantName" />
							<form:errors path="participantName" />
						</td>
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