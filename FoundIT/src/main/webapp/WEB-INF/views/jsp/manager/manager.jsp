<%@ include file="../default/nav.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>managerdashboard</title>
<body>

<div class="container" >
  <h2>All Job Postings</h2>  
  <c:if test="${not empty errmsg}">
	<div class="row">
		<header style="color: red;"><h6>${errmsg}</h6></header>
	</div>
  </c:if>         
  <table class="table" id="maintable">
    <thead>
      <tr>
        <!-- <th>_JobID</th>
        <th>Company Name</th>
        <th>Salary Rate</th>
        <th>Position Type</th> -->
        <th>Location</th>
        <th>Job Description</th>
        <th>Status</th> 
        <th>Detailed View</th>      
      </tr>
    </thead>
    <tbody>
		<c:forEach var="row" items="${jobpostings}">
			<tr>
				<!-- <td>${row._jobID}</td>
				 <td>${row.companyName}</td>
				<td>${row.salaryRate}</td>
				<td>${row.positionType}</td>-->
				<td>${row.location}</td>
				<td>${row.jobDescription}</td>
				<td>${row.status}</td>
				<td ><a href="${pageContext.servletContext.contextPath}/manager/detailedjob/${row._jobID}">Link</td>
			</tr>
		</c:forEach>
      
    </tbody>
  </table>
</div>


</body>
</html>