<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="app.utils.CustomerSortUtils" %>

<!DOCTYPE html>

<html>

<head>
	<title>List Customers</title>

	<!-- reference on style sheet -->
	
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
			
			<!-- put new button: Add Customer -->
			
			<input type="button" value="Add Customer"
				   onclick="window.location.href='showFormForAdd'; return false;"
				   class="add-button"
			/>
			
			<!-- add a search box -->
			<form:form action="search" method="GET">
				Search customer: <input type="text" name="theSearchName" />
				
				<input type="submit" value="Search" class="add-button" />
			</form:form>
			
			<!-- add out html table here -->
			
			<c:url var="sortLinkFirstName" value="/customer/list">
					<c:param name="sorting" value="<%= Integer.toString(CustomerSortUtils.FIRST_NAME) %>" />
			</c:url>
			
			<c:url var="sortLinkLastName" value="/customer/list">
					<c:param name="sorting" value="<%= Integer.toString(CustomerSortUtils.LAST_NAME) %>" />
			</c:url>
			
			<c:url var="sortLinkEmail" value="/customer/list">
					<c:param name="sorting" value="<%= Integer.toString(CustomerSortUtils.EMAIL) %>" />
			</c:url>
			
			<table>
			
				<tr>
					<th><a href="${sortLinkFirstName}">First Name</a></th>
					<th><a href="${sortLinkLastName}">Last Name</a></th>
					<th><a href="${sortLinkEmail}">Email</a></th>
					<th>Action</th>
				</tr>
				
				<!-- loop over and print out customers -->
				<c:forEach var="tempCustomer" items="${customers}">
					
					<!-- construct an "update" link with customer id -->
					<c:url var="updateLink" value="/customer/showFormForUpdate">
						<c:param name="customerId" value="${tempCustomer.id}"/>
					</c:url>
					
					<tr>
						<td> ${tempCustomer.firstName} </td>
						<td> ${tempCustomer.lastName} </td>
						<td> ${tempCustomer.email} </td>
						
						<td>
							<!-- display the update link -->
							<a href="${updateLink}">Update</a>
						</td>
					</tr>
				
				</c:forEach>
			
			</table>
		</div>
		
	</div>
	
	<br>
		
	<form:form action="${pageContext.request.contextPath}/" 
			   method="GET">
	
		<input type="submit" value="Home" class="add-button" />
	
	</form:form>
			
		

</body>

</html>