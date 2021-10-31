<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Event Logs</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
         >
</head>
<body>

<%
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	
		if(session.getAttribute("username")==null)
		{
			response.sendRedirect("Login.jsp");
		}
			 
	%>
	
<main class="m-3">
	<div class="row col-md-6">
		<table class="table table-striped table-bordered table-sm">
	            <tr>
	                <th>EventType</th>
	                <th>TimeGenerated</th>
	                <th>Message</th>
	                <th>EventId</th>
	                <th>Source</th>
	            </tr>
	
	            <c:forEach items="${eventLogs}" var="eventLogs">
	                <tr>
	                    <td>${eventLogs.getEventType()}</td>
	                    <td>${eventLogs.getTimeGenerated()}</td>
	                    <td>${eventLogs.getMessage()}</td>
	                    <td>${eventLogs.getEventId()}</td>
	                    <td>${eventLogs.getSource()}</td>
	                </tr>
	            </c:forEach>
	        </table>
     </div>
    
        
        <nav aria-label="Navigation for eventLogs">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link"
                    href="ReadData?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&eventId=${eventId}&logEvent=${logEvent}">Previous</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link">
                                ${i} <span class="sr-only">(current)</span></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link"
                            href="ReadData?recordsPerPage=${recordsPerPage}&currentPage=${i}&eventId=${eventId}&logEvent=${logEvent}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item"><a class="page-link"
                    href="ReadData?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&eventId=${eventId}&logEvent=${logEvent}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
<a href="welcome.jsp">Go to Home Page</a>
<form action="Logout">
		<input type="submit" value="logout">
</form>
</main> 
</body>
</html>