<%@ include file="../default/nav.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>application</title>

<body>

<div class="container">
<div class="panel panel-info">
      <div class="panel-heading">All Applications</div>
      <div class="panel-body">          
  <table class="table" id="maintable">
    <thead>
      <tr>
        <th>_AppID</th>
        <th>_JobID</th>
        <th>Candidate Details</th>
        <th>Cover Letter</th>
        <th>Status</th>
        <th>Attachment 1</th>
        <th>Attachment 2</th>
        <th>Detailed View</th>
      </tr>
    </thead>
    <tbody>
		<c:forEach var="row" items="${applications}">
			<tr>
				<td>${row._appID}</td>
				<td>${row._jobID}</td>
				<td>${row.candidatesDetails}</td>
				<td>${row.coverLetter}</td>
				<td>${row.status}</td>
				<td>${row.attachment1}</td>
				<td>${row.attachment2}</td>
				<td ><a href="${pageContext.servletContext.contextPath}/manager/${row._jobID}/detailedapplication/${row._appID}">Link</td>
			</tr>
		</c:forEach>
      
    </tbody>
  </table>
</div>
</div>
</div>



</body>
</html>