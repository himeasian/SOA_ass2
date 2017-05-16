<%@ include file="../default/nav.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jobposting</title>
</head>
<body>
Create Job Posting
<form:form id="createJobPostingForm" modelAttribute="JobPosting" action="${pageContext.servletContext.contextPath}/manager/createJobPosting" method="post" class="form-horizontal">
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="companyName">Company Name</form:label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" path="companyName" placeholder="Enter company name." />
    </div>
  </div>
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="salaryRate">Salary Rate:</form:label>
    <div class="col-sm-10"> 
      <form:input type="text" pattern="[0-9]+" class="form-control" path="salaryRate" placeholder="Enter salary rate."/>
    </div>
  </div>
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="positionType">Position Type:</form:label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" path="positionType" placeholder="Enter position type."/>
    </div>
  </div>
    <div class="form-group">
    <form:label class="control-label col-sm-2" path="location">Location:</form:label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" path="location" placeholder="Enter location."/>
    </div>
  </div>
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="jobDescription">Job Description:</form:label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" path="jobDescription" placeholder="Enter job description."/>
    </div>
  </div>
  <div class="form-group">
    <form:label class="control-label col-sm-2" path="classification">Classification:</form:label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" path="classification" placeholder="Enter classification."/>
    </div>
  </div>

  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" href="createJobPosting" class="btn btn-default">Submit</button>
    </div>
  </div>
</form:form>

Delete/Archive Posting



</body>
</html>