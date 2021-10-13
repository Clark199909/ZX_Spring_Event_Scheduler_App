<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
	<title>ZX Event Scheduler Home</title>
</head>

<body>
	<h2>ZX Event Scheduler</h2>
	
	<p>
	Feel free to schedule meetings and other events!
	</p>
	
	<hr>
	
	<!-- display user name and role -->
	
	<p>
		Hello and Welcome <security:authentication property="principal.username" />!
	</p>
	
	
	<input type="button" value="Update Profile" 
		onclick="window.location.href='customer/homeShowFormForUpdate'; return false;"
		class="add-button"
	/>
	
	
	<hr>
	
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
	
	<security:authorize access="hasRole('ADMIN')">
		<input type="button" value="Manage User Accounts" 
				onclick="window.location.href='customer/list'; return false;"
				class="add-button"
		/>
	</security:authorize>
	
	<hr>
	
	
	<!-- Add a logout button -->
	<form:form action="${pageContext.request.contextPath}/logout" 
			   method="POST">
	
		<input type="submit" value="Logout" />
	
	</form:form>
	
</body>

</html>