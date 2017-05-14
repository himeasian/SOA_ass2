<%@ include file="navbar.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>JobID: <c:out value="${jobID}"/></h1>
<form:form id="createHiringTeam" modelAttribute="HiringTeam" action="${pageContext.servletContext.contextPath}/manager/hiringteam/assign" method="post" class="form-horizontal">
  <div class="form-group">
    <label class="control-label col-sm-2" for="Member1">Member 1:</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="member1" placeholder="Enter hiring member 1.">
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="Member2">Member 2:</label>
    <div class="col-sm-10"> 
      <input type="text" class="form-control" id="member2" placeholder="Enter hiring member 2.">
    </div>
  </div>
  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      
      <button name="assignteam" value ="${jobID}" class="btn btn-default">Submit</button>

    </div>
  </div>
</form:form>
</body>
</html>