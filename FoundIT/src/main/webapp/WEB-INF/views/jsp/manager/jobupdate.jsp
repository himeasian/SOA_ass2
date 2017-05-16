<%@ include file="../default/nav.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<body>
<div class="container">
<div class="panel panel-info">
      <div class="panel-heading">Updating Job: ${JobPosting._jobID}</div>
      <div class="panel-body">
<form:form id="updateJobPostingForm" modelAttribute="JobPosting" action="${pageContext.servletContext.contextPath}/manager/updateJobPosting/${JobPosting._jobID}" class="form-horizontal">
  <!-- <div class="form-group">
    <label for="_jobID" class="control-label col-sm-2" >Job ID:</label>
    <div class="col-sm-10">
      <input id="_jobID" type="text" pattern="[0-9]+" class="form-control"  placeholder="Enter Job ID." />
    </div>
  </div> -->
  <!-- <form:input path="_jobID" value="${JobPosting._jobID}" type="hidden"/> -->
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
    <form:label class="control-label col-sm-2" path="status">Status:</form:label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" path="status" placeholder="Enter new status."/>
    </div>
  </div>

  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" href="createJobPosting" class="btn btn-default">Submit</button>
    </div>
  </div>
</form:form>
</div>
</div>
</div>

</body>
</html>