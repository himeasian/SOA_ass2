<%@include file="../default/navbar.jsp" %>

<body>
<c:if test="${not empty errmsg}">
	<div class="row">
		<header style="color: red;"><h6>${errmsg}</h6></header>
	</div>
  </c:if>     
<form:form id="createReviewer" modelAttribute="register" action="${pageContext.servletContext.contextPath}/manager/revieweraddition" method="post" class="form-horizontal">
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="fname">First Name:</form:label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" path="fname" placeholder="Enter first name."/>
    </div>
  </div>
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="lname">Last Name:</form:label>
    <div class="col-sm-10"> 
      <form:input type="text" class="form-control" path="lname" placeholder="Enter last name."/>
    </div>
  </div>
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="email">Email:</form:label>
    <div class="col-sm-10"> 
      <form:input type="text" class="form-control" path="email" placeholder="Enter email"/>
    </div>
  </div>
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="password">Password:</form:label>
    <div class="col-sm-10"> 
      <form:input type="text" class="form-control" path="password" placeholder="Enter password."/>
    </div>
  </div>
  
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="confirmPassword">Confirm Password:</form:label>
    <div class="col-sm-10"> 
      <form:input type="text" class="form-control" path="confirmPassword" placeholder="Enter password again to Confirm."/>
    </div>
  </div>
  
  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      
      <button type="submit" class="btn btn-default">Submit</button>

    </div>
  </div>
</form:form>

</body>
</html>