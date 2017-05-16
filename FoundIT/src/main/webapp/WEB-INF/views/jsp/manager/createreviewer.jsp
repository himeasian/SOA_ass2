<%@include file="../default/nav.jsp"%>

<body>
	<div class="container">
		<c:if test="${not empty successmsg}">
			<div class="panel panel-success">
				<div class="panel-heading">${successmsg}</div>
			</div>
		</c:if>
		<c:if test="${not empty errmsg}">
			<div class="panel panel-danger">
				<div class="panel-heading">${errmsg}</div>
			</div>
		</c:if>
		<div class="panel panel-info">
			<div class="panel-heading">Create Reviewer Account</div>
			<div class="panel-body">
				<form:form id="createReviewer" modelAttribute="register"
					action="${pageContext.servletContext.contextPath}/manager/revieweraddition"
					method="post" class="form-horizontal">
					<div class="form-group">
						<form:label class="control-label col-sm-2" path="fname">First Name:</form:label>
						<div class="col-sm-10">
							<form:input type="text" class="form-control" path="fname"
								placeholder="Enter first name." />
						</div>
					</div>
					<div class="form-group">
						<form:label class="control-label col-sm-2" path="lname">Last Name:</form:label>
						<div class="col-sm-10">
							<form:input type="text" class="form-control" path="lname"
								placeholder="Enter last name." />
						</div>
					</div>
					<div class="form-group">
						<form:label class="control-label col-sm-2" path="email">Email:</form:label>
						<div class="col-sm-10">
							<form:input type="email" class="form-control" path="email"
								placeholder="Enter email" />
						</div>
					</div>
					<div class="form-group">
						<form:label class="control-label col-sm-2" path="password">Password:</form:label>
						<div class="col-sm-10">
							<form:input type="password" class="form-control" path="password"
								placeholder="Enter password." />
						</div>
					</div>

					<div class="form-group">
						<form:label class="control-label col-sm-2" path="confirmPassword">Confirm Password:</form:label>
						<div class="col-sm-10">
							<form:input type="password" class="form-control"
								path="confirmPassword"
								placeholder="Enter password again to Confirm." />
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">

							<button type="submit" class="btn btn-default">Submit</button>

						</div>
					</div>
					<form:input path="company" type="hidden" value="${sessionScope.user.company}" />
				</form:form>
			</div>
		</div>
	</div>

</body>
</html>