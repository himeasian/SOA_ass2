<%@ include file="../default/nav.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-lg-10 col-lg-offset-1">
			<div class="panel panel-default">
				<div class="panel-heading">Search for job postings</div>
				<div class="panel-body">
					<form:form modelAttribute="jobSearch" action="${pageContext.servletContext.contextPath}/applicant/search" method="get">
						<div class="form-group row">
							<div class="col-lg-3">
								<div class="input-group">
									<span class="input-group-addon">Salary</span>
									<form:select path="salaryRate" class="form-control" items="${salaryList}"/>
								</div>
							</div>
							<div class="col-lg-5">
								<form:input path="jobDescription" type="search" class="form-control" placeholder="Search for keywords, title, description.." />
							</div>
							<div class="col-lg-4">
								<div class="input-group">
									<form:input path="location" type="search" class="form-control" placeholder="Location (e.g. Sydney, Japan..)" />
									<span class="input-group-btn">
										<button class="btn btn-default" type="submit">Go!</button>
									</span>
								</div>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-10 col-lg-offset-1">
			<div class="panel panel-info">
				<div class="panel-heading">Current Applications</div>
				<div class="panel-body">
					<c:forEach var="application" items="${applications}">
						
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>