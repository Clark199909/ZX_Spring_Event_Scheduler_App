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
			<h2>Upcoming Events and Meetings</h2>
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
					
					<c:url var="deleteLink" value="/manageMeeting/delete">
						<c:param name="meetingId" value="${nextMeeting.id}" />
					</c:url>
					
					<tr>
						<td> ${nextMeeting.getTitle()} </td>
						<td> ${nextMeeting.getInitializer().getUserName()} </td>
						<td> ${nextMeeting.getMeetingType().getTypeName()} </td>
						<td> ${nextMeeting.getDescription()} </td>
						
						<td> 
							<c:forEach var="participant" items="${nextMeeting.getUsers()}">
								<span>${participant.userName}&ensp;</span>
							</c:forEach>
						</td>
						<td> ${nextMeeting.getStartTime().toString()} </td>
						<td>
							<c:if test="${nextMeeting.getInitializer().getUserName().equals(myName)}">
								<a href="${updateLink}">Update</a>
							</c:if>
							
							<c:if test="${nextMeeting.getInitializer().getUserName().equals(myName)}">
								<a href="${deleteLink}"
								   onclick="if(!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
							</c:if>
						</td>
										
					</tr>
				
				</c:forEach>
						
			</table>
				
		</div>
	
	</div>
	
	<p></p>
	
	<form:form action="${pageContext.request.contextPath}/" 
			   method="GET">
	
		<input type="submit" value="Home" class="add-button" />
	
	</form:form>

</body>

</html>









