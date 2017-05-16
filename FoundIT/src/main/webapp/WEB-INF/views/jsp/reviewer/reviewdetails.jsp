<%@ include file="../default/nav.jsp"%>
<div class="container">
	<div class="row">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h2>Job Details</h2>
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
			</div>
		</div>
	</div>
</div>
<div class="container">
	<div class="row">
		<div class="col-lg-6">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h4>Personal Details</h4>
				</div>
				<div class="panel-body">
					<div class="form-group row">
						<label for="email" class="col-sm-3 col-form-label">Email</label>
						<div class="col-sm-9">${appEmail}</div>
					</div>
					<div class="form-group row">
						<label for="name" class="col-sm-3 col-form-label">Name</label>
						<div class="col-sm-9">${appFName}${appLName}</div>
					</div>
					<div class="form-group row">
						<label for="phoneNo" class="col-sm-3 col-form-label">Phone
							Number</label>
						<div class="col-sm-9">${jobApplication.phoneNo}</div>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-heading">
					<h4>Additional Information</h4>
				</div>
				<div class="panel-body">
					<div class="form-group row">
						<label for="attachment2" class="col-sm-3 col-form-label">Resume</label>
						<div class="col-sm-9">
							<textarea id="attachment2" rows="10" cols="50"
								class="form-control" disabled>
								${jobApplication.attachment2}
							</textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-6">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h4>Additional Information</h4>
				</div>
				<div class="panel-body">
					<div class="form-group row">
						<label for="coverLetter" class="col-sm-3 col-form-label">Cover
							Letter</label>
						<div class="col-sm-9">
							<textarea id="coverLetter" rows="10" cols="50"
								class="form-control" disabled>
								${jobApplication.coverLetter}
							</textarea>
						</div>
					</div>
					<div class="form-group row">
						<label for="attachment1" class="col-sm-3 col-form-label">Additional
							Documents</label>
						<div class="col-sm-9">
							<textarea id="attachment1" rows="10" cols="50"
								class="form-control" disabled>
								${jobApplication.attachment1}
							</textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="container">
	<form:form modelAttribute="reviewForm" action="${pageContext.servletContext.contextPath}/review/job/${jobPosting._jobID}/application/${jobApplication._appID}"
		method="post">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h4>Review  form</h4>
			</div>
			<div class="panel-body">
				<div class="form-group row">
					<form:label path="comments" class="col-sm-3 col-form-label">Comments</form:label>
					<div class="col-sm-9">
						<form:textarea path="comments" rows="10" cols="50"
							class="form-control" placeholder="Comments"
							value="${reviewForm.comments}" />
					</div>
				</div>
				<div class="form-group row">
					<form:label path="decision" class="col-sm-3 col-form-label">Decision</form:label>
					<div class="col-sm-3">
						<form:select path="decision" class="form-control">
							<form:option path="decision" value="" selected="selected"></form:option>
							<form:option path="decision" value="accept">Accept</form:option>
							<form:option path="decision" value="reject">Reject</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-sm-2 col-sm-offset-10">
						<button type="submit" class="btn btn-default pull-right">Submit</button>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</div>