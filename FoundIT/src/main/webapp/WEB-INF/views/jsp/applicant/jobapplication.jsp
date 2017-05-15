<div class="container">
	<form:form modelAttribute="jobApplication" action="${pageContext.servletContext.contextPath}/applicant/job/${jobPosting._jobID}/apply" method="post">
		<div class="row">
			<div class="col-lg-6">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h4>Personal Details</h4>
					</div>
					<div class="panel-body">
						<div class="form-group row">
							<label for="email" class="col-sm-3 col-form-label">Email</label>
							<div class="col-sm-9">${sessionScope.user.email}</div>
						</div>
						<div class="form-group row">
							<label for="name" class="col-sm-3 col-form-label">Name</label>
							<div class="col-sm-9">${sessionScope.user.fname}
								${sessionScope.user.lname}</div>
						</div>
						<div class="form-group row">
							<form:label path="phoneNo" class="col-sm-3 col-form-label">Phone Number (Optional)</form:label>
							<div class="col-sm-9">
								<form:input path="phoneNo" type="text" class="form-control"
									placeholder="Phone number" />
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-heading">
						<h4>Additional Information</h4>
					</div>
					<div class="panel-body">
						<div class="form-group row">
							<form:label path="attachment2" class="col-sm-3 col-form-label">Resume</form:label>
							<div class="col-sm-9">
								<form:textarea path="attachment2" rows="10" cols="50"
									class="form-control"
									placeholder="Use this space to detail your work history and/or experience" />
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
							<form:label path="coverLetter" class="col-sm-3 col-form-label">Cover Letter</form:label>
							<div class="col-sm-9">
								<form:textarea path="coverLetter" rows="10" cols="50"
									class="form-control" placeholder="Optional cover letter.." />
							</div>
						</div>
						<div class="form-group row">
							<form:label path="attachment1" class="col-sm-3 col-form-label">Additional Documents</form:label>
							<div class="col-sm-9">
								<form:textarea path="attachment1" rows="10" cols="50"
									class="form-control"
									placeholder="Use this space to add any additional documents" />
							</div>
						</div>
						<div class="form-group row">
							<div class="col-sm-2 col-sm-offset-10">
								<button type="submit" class="btn btn-default pull-right">Apply Now</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<form:input path="companyName" type="hidden" value="${jobPosting.companyName}" />
		<form:input path="positionType" type="hidden" value="${jobPosting.positionType}" />
	</form:form>
</div>
