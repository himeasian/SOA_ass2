<%@ include file="../default/nav.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>review</title>

<body>
<div class="container" >
	<h2>Review</h2>           
		<table class="table" id="maintable">
			<tr>
        		<th>_ReviewID</th>
        		<td><c:out value='${review._reviewID}'/></td>    
      		</tr>
      		<tr>
        		<th>_AppID</th>
        		<td><c:out value='${review._appID}'/></td>    
      		</tr>
      		<tr>
        		<th>Reviewer Details</th>
        		<td><c:out value='${review.reviewerDetails}'/></td>
      		</tr>
    		<tr>
    			<th>Comments</th>
    			<td><c:out value='${review.comments}'/></td>
    		</tr>
    		<tr>
    			<th>Decision</th>
    			<td><c:out value="${review.decision}"/></td>
    		</tr>
        	
		    
  	</table>
</div>
</body>
</html>