<%@ include file="../default/nav.jsp" %>
<div class="container">
<div class="panel panel-info">
      <div class="panel-heading">Add Reviewers</div>
      <div class="panel-body">
<div class="container">
<c:if test="${not empty errmsg}">
	<div class="row">
		<header style="color: red;"><h6>${errmsg}</h6></header>
	</div>
  </c:if>  
  </div>   

<c:if test="${fn:length(reviewers) gt 0}">
	<form:form id="createHiringTeam" modelAttribute="HiringTeam" action="${pageContext.servletContext.contextPath}/manager/hiringteam/assign" method="post" class="form-horizontal">
	  <div class="form-group-row">
	    <form:label class="col-sm-2 col-form-label" path="email1">Member 1 Email:</form:label>
	    <div class="col-sm-10">
	      <form:select path="email1" class="form-control" items="${reviewers}" />
	    </div>
	    
	  </div>
	  <div class="form-group-row">
	    <form:label class="col-sm-2 col-form-label" path="email2">Member 2 Email:</form:label>
	    <div class="col-sm-10"> 
	      <form:select path="email2" class="form-control" items="${reviewers}" />
	    </div>
	  </div>
	  <div class="form-group-row"> 
	    <div class="col-sm-offset-2 col-sm-10">
	      
	      <button name="assignteam" value ="${jobID}" class="btn btn-default">Submit</button>
	
	    </div>
	  </div>
	</form:form>
</c:if>
</div>
</div>
</div>


</body>
</html>