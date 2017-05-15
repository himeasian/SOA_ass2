<%@ include file="head.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="/FoundIT/manager">FoundIT</a>
    </div>
    <ul class="nav navbar-nav">
      
      <li><a href="${pageContext.servletContext.contextPath}/manager/jobposting">Create JobPostings</a></li>
      <li><a href="${pageContext.servletContext.contextPath}/manager/jobupdate">Update JobPosting</a>
      <li><a href="${pageContext.servletContext.contextPath}/manager/application">Applications</a></li>
      <li><a href="${pageContext.servletContext.contextPath}/manager/review">Reviews</a></li>
    </ul>
  </div>
</div>
</body>
</html>