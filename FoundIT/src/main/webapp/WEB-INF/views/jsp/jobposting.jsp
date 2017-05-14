<%@ include file="navbar.jsp"%>
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
    <label class="control-label col-sm-2" for="companyName">Company Name</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="companyName" placeholder="Enter company name.">
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="salaryRate">Salary Rate:</label>
    <div class="col-sm-10"> 
      <input type="number" class="form-control" id="salaryRate" placeholder="Enter salary rate.">
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="positionType">Position Type:</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="positionType" placeholder="Enter position type.">
    </div>
  </div>
    <div class="form-group">
    <label class="control-label col-sm-2" for="location">Location:</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="location" placeholder="Enter location.">
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="jobDescription">Job Description:</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="jobDescription" placeholder="Enter job description.">
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