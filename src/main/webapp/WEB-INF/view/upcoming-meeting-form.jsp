<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<html>

<head>
	<title>List Customers</title>
	
	<!-- reference our style sheet -->

	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/style.css" />

</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>
	
	<div id="container">
	
		<div id="content">
	
		
			<!--  add our html table here -->
		
			<table>
				<tr>
					<th>Title</th>
					<th>Host</th>
					<th>Type</th>
					<th>Description</th>
					<th>Participants</th>
					<th>Date and Time</th>
					<th>Action</th>
					
				</tr>
				
				<!-- loop over and print our customers -->
				<c:forEach var="nextMeeting" items="${upcomingMeetings}">					
					
					<c:url var="updateLink" value="/manageMeeting/showUpdateForm">
						<c:param name="meetingId" value="${nextMeeting.id}" />
					</c:url>
					
					<tr>
						<td> ${nextMeeting.title} </td>
						<td> ${nextMeeting.initializer.userName} </td>
						<td> ${nextMeeting.meetingType.typeName} </td>
						<td> ${nextMeeting.description} </td>
						
						<td> 
							<c:forEach var="participant" items="${nextMeeting.users}">
								<span>${participant.userName}&ensp;</span>
							</c:forEach>
						</td>
						<td> ${nextMeeting.startTime.toString()} </td>
						<td><a href="${updateLink}">Update</a></td>
										
					</tr>
				
				</c:forEach>
						
			</table>
				
		</div>
	
	</div>
	
	<p></p>

</body>

</html>









