<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

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
			<h2>Profile</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Save Profile Info</h3>
		
		<form:form action="saveProfile" modelAttribute="customer" method="POST">
		
			<!-- need to associate this data with customer id -->
			<form:hidden path="id" />
			
			<div class="form-group">
				<div class="col-xs-15">
					 <div>
								
						<!-- Check for registration error -->
							<c:if test="${updateError != null}">
								
								<div class="alert alert-danger col-xs-offset-1 col-xs-10">
									${updateError}
								</div>
		
							</c:if>
																			
					 </div>
				 </div>
			</div>
		
			<table>
				<tbody>
					<tr>
						<td><label>First Name:</label></td>
						<td><form:input path="firstName"/></td>
					</tr>
					
					<tr>
						<td><label>Last Name:</label></td>
						<td><form:input path="lastName"/></td>
					</tr>
					
					<tr>
						<td><label>Email:</label></td>
						<td><form:input path="email"/></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save"/></td>
					</tr>
					
				</tbody>
			</table>
		
		</form:form>
		
		<div style="clear; both;"></div>
		
		<p>
			<a href="${pageContext.request.contextPath}/">Home</a>
		</p>
	
	</div>
</body>
</html>