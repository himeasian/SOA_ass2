<%@ include file="navbar.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<body>
<div class="container" >
	<h2>Application</h2>           
		<table class="table" id="maintable">
			<tr>
        		<th>_AppID</th>
        		<td><c:out value='${model.application._appID}'/></td>    
      		</tr>
      		<tr>
        		<th>_JobID</th>
        		<td><c:out value='${model.application._jobID}'/></td>    
      		</tr>
      		<tr>
        		<th>Candidates Details</th>
        		<td><c:out value='${model.application.candidatesDetails}'/></td>
      		</tr>
    		<tr>
    			<th>Cover Letter</th>
    			<td><c:out value='${model.application.coverLetter}'/></td>
    		</tr>
    		<tr>
    			<th>Status</th>
    			<td><c:out value="${model.application.status}"/></td>
    		</tr>
        	<tr>
	        	<th>Attachment 1</th>
	        	<td><c:out value="${model.application.attachment1}"/></td>
	        </tr>
	        <tr>
		        <th>Attachment 2</th>
		        <td><c:out value="${model.application.attachment2}"/></td>
		       </tr>
		    
  	</table>
</div>

<div class="panel panel-primary">
	<div class="panel-heading">Reviewer 1</div>
    <div class="panel-body">${model.reviewer1.reviewerDetails}</div>
</div>

<div class="panel panel-primary">
	<div class="panel-heading">Reviewer 2</div>
    <div class="panel-body">${model.reviewer2.reviewerDetails}</div>
</div>

</body>
</html>