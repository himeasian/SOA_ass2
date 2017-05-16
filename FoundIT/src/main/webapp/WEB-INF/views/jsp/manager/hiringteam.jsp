<%@ include file="../default/navbar.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:if test="${not empty errmsg}">
	<div class="row">
		<header style="color: red;"><h6>${errmsg}</h6></header>
	</div>
  </c:if>     
<h1>JobID: <c:out value="${jobID}"/></h1>
<form:form id="createHiringTeam" modelAttribute="HiringTeam" action="${pageContext.servletContext.contextPath}/manager/hiringteam/assign" method="post" class="form-horizontal">
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="email1">Member 1 Email:</form:label>
    <div class="col-sm-10">
      <form:input type="email" class="form-control" path="email1" placeholder="Enter email of hiring member 1."/>
    </div>
  </div>
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="email2">Member 2:</form:label>
    <div class="col-sm-10"> 
      <form:input type="email" class="form-control" path="email2" placeholder="Enter email of hiring member 2."/>
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