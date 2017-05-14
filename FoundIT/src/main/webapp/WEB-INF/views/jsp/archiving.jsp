<%@ include file="navbar.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<body>

<form:form id="archiveJobPosting" modelAttribute="JobPosting" action="${pageContext.servletContext.contextPath}/manager/archiveobPosting" method="post" class="form-horizontal">
  <div class="form-group">
    <label class="control-label col-sm-2" for="email">Job ID</label>
    <div class="col-sm-10"><input type="text" class="form-control" id="jobID"></div>
  </div>
	<div class="col-sm-offset-2 col-sm-10">
  <button type="submit" class="btn btn-default">Delete</button>
  </div>
</form:form>
</body>
</html>