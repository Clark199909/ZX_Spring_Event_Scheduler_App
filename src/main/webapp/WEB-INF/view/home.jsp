<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
	<title>ZX Event Scheduler Home</title>
</head>

<body>
	<h2>ZX Event Scheduler</h2>
	<hr>
	
	<p>
	Feel free to schedule meetings and other events!
	</p>
	
	<hr>
	
	<!-- display user name and role -->
	
	<p>
		User: <security:authentication property="principal.username" />
		<br><br>
		Role(s): <security:authentication property="principal.authorities" />
	</p>
	
	<input type="button" value="Add personal meeting" 
			onclick="window.location.href='manageMeeting/showPersonalMeetingForm'; return false;"
			class="add-button"
	/>
	
	<input type="button" value="Add partner meeting" 
			onclick="window.location.href='manageMeeting/showPartnerMeetingForm'; return false;"
			class="add-button"
	/>
	
	<security:authorize access="hasRole('MANAGER')">
		<input type="button" value="Add team meeting" 
				onclick="window.location.href='manageMeeting/showTeamMeetingForm'; return false;"
				class="add-button"
		/>
	</security:authorize>
	
	<hr>
	
	<input type="button" value="Check Past Meetings" 
			onclick="window.location.href='manageMeeting/showPastMeetings'; return false;"
			class="add-button"
	/>
	
	<input type="button" value="Check Upcoming Meetings" 
			onclick="window.location.href='manageMeeting/showFutureMeetings'; return false;"
			class="add-button"
	/>
	
	<hr>
	
	<security:authorize access="hasRole('MANAGER')">
	
		<!-- Add a link to point to /leaders ... this is for the managers -->
		
		<p>
			<a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a>
			(Only for Manager peeps)
		</p>

	</security:authorize>	
	
	
	<security:authorize access="hasRole('ADMIN')">  

		<!-- Add a link to point to /systems ... this is for the admins -->
		
		<p>
			<a href="${pageContext.request.contextPath}/systems">IT Systems Meeting</a>
			(Only for Admin peeps)
		</p>
	
	</security:authorize>
	
	<hr>
	
	
	<!-- Add a logout button -->
	<form:form action="${pageContext.request.contextPath}/logout" 
			   method="POST">
	
		<input type="submit" value="Logout" />
	
	</form:form>
	
</body>

</html>