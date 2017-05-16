<%@ include file="../default/nav.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JobPosting</title>
</head>
<body>

<div class="container" >
	<h2>Job Posting</h2>           
		<table class="table" id="maintable">
			<tr>
        		<th>_JobID</th>
        		<td><c:out value='${jobposting._jobID}'/></td>    
      		</tr>
      		<tr>
        		<th>Company Name</th>
        		<td><c:out value='${jobposting.companyName}'/></td>
      		</tr>
    		<tr>
    			<th>Salary Rate</th>
    			<td><c:out value='${jobposting.salaryRate}'/></td>
    		</tr>
    		<tr>
    			<th>Position Type</th>
    			<td><c:out value="${jobposting.positionType}"/></td>
    		</tr>
        	<tr>
	        	<th>Location</th>
	        	<td><c:out value="${jobposting.location}"/></td>
	        </tr>
	        <tr>
		        <th>Job Description</th>
		        <td><c:out value="${jobposting.jobDescription}"/></td>
		       </tr>
		    <tr>
		        <th>Status</th> 
		        <td><c:out value="${jobposting.status}"/></td>
		    </tr>
		    <tr>
		    	
		    	<td><form:form action="${pageContext.servletContext.contextPath}/manager/hiringteam" method="post">
		    		<button name="jobbutton" value='${jobposting._jobID}' >Add reviewers</button>
		    	</form:form>
		    	</td>
		    	<td><form:form action="${pageContext.servletContext.contextPath}/manager/jobupdate" method="post">
		    		<button name="jobupdatebutton" value='${jobposting._jobID}' >Update Job Post</button>
		    	</form:form>
		    	</td>
		    	
		    </tr>
		    <tr>
		    	<td><form:form action="${pageContext.servletContext.contextPath}/manager/jobarchive" method="post">
		    		<button name="jobarchivebutton" value='${jobposting._jobID}' >Archive Job Post</button>
		    	</form:form>
		    	</td>
		    </tr>
  	</table>
</div>


</body>
</html>