<%@ include file="../default/nav.jsp"%>
<div class="container">
	<div class="row">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h2>Job Posting</h2>
			</div>
			<div class="panel-body">
				<table class="table">
					<tr>
						<th>Company Name</th>
						<td><c:out value='${jobPosting.companyName}' /></td>
					</tr>
					<tr>
						<th>Salary Rate</th>
						<td><c:out value='${jobPosting.salaryRate}' /></td>
					</tr>
					<tr>
						<th>Position Type</th>
						<td><c:out value="${jobPosting.positionType}" /></td>
					</tr>
					<tr>
						<th>Location</th>
						<td><c:out value="${jobPosting.location}" /></td>
					</tr>
					<tr>
						<th>Job Description</th>
						<td><c:out value="${jobPosting.jobDescription}" /></td>
					</tr>
					<tr>
						<th>Status</th>
						<td><c:out value="${jobPosting.status}" /></td>
					</tr>
				</table>
				<c:if test="${empty apply and empty update}">
					<a class="btn btn-default pull-right"
						href="${pageContext.servletContext.contextPath}/applicant/job/${jobPosting._jobID}/application"
						role="button">Apply</a>
				</c:if>
			</div>

		</div>
	</div>
</div>
<c:if test="${not empty apply}">
	<%@ include file="jobapplication.jsp" %>
</c:if>
<c:if test="${not empty update}">
	<%@ include file="jobupdate.jsp" %>
</c:if>