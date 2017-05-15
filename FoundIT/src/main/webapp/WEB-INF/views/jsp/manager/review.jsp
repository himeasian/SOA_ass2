<%@ include file="../default/navbar.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container" >
  <h2>All Reviews</h2>           
  <table class="table" id="maintable">
    <thead>
      <tr>
        <th>_ReviewID</th>
        <th>_AppID</th>
        <th>Reviewer Details</th>
        <th>Comments</th>
        <th>Decision</th>
        <th>Detailed View</th>
      </tr>
    </thead>
    <tbody>
		<c:forEach var="row" items="${reviews}">
			<tr>
				<td>${row._reviewID}</td>
				<td>${row._appID}</td>
				<td>${row.reviewerDetails}</td>
				<td>${row.comments}</td>
				<td>${row.decision}</td>
				<td ><a href="${pageContext.servletContext.contextPath}/manager/${row._appID}/detailedreview/${row._reviewID}">Link</td>
			</tr>
		</c:forEach>
      
    </tbody>
  </table>
</div>
</body>
</html>