<%@ include file="head.jsp"%>
<body>
	<div class="container">
		<div class="row">
			<h1>Register An Account</h1>
		</div>
		<br />
		<div class="container">
			<div class="row">
				<form:form modelAttribute="register" action="${pageContext.servletContext.contextPath}/register" method="post">
					<div class="form-group row">
						<form:label path="fname" class="col-sm-2 col-form-label">First Name</form:label>
						<div class="col-sm-10">
							<form:input path="fname" type="text" class="form-control"
								placeholder="First Name" required="required" />
						</div>
					</div>
					<div class="form-group row">
						<form:label path="lname" class="col-sm-2 col-form-label">Surname</form:label>
						<div class="col-sm-10">
							<form:input path="lname" type="text" class="form-control"
								placeholder="Surname" required="required" />
						</div>
					</div>
					<div class="form-group row">
						<form:label path="email" class="col-sm-2 col-form-label">Email</form:label>
						<div class="col-sm-10">
							<form:input path="email" type="email" class="form-control"
								placeholder="Email" required="required" />
						</div>
					</div>
					<div class="form-group row">
						<form:label path="password" class="col-sm-2 col-form-label">Password</form:label>
						<div class="col-sm-10">
							<form:input path="password" type="password" class="form-control"
								placeholder="Password" required="required" />
						</div>
					</div>
					<div class="form-group row">
						<form:label path="confirmPassword" class="col-sm-2 col-form-label">Confirm Password</form:label>
						<div class="col-sm-10">
							<form:input path="confirmPassword" type="password" class="form-control"
								placeholder="Password" required="required" />
						</div>
					</div>
					<div class="form-group row">
						<form:label path="role" for="inputRole"
							class="col-sm-2 col-form-label">Role</form:label>
						<div class="col-sm-10 pull-right">
							<form:select path="role" class="form-control" required="required">
								<option value="applicant" selected="selected">Applicant</option>
								<option value="manager">Manager</option>
							</form:select>
						</div>
					</div>
					<div class="form-group row">
						<form:label path="company" class="col-sm-2 col-form-label">Company</form:label>
						<div class="col-sm-10">
							<form:input path="company" type="text" class="form-control"
								placeholder="Company" />
						</div>
					</div>
					<div class="form-group row">
						<div class="offset-sm-2 col-sm-10">
							<button type="submit" class="btn btn-default">Register</button>
						</div>
						<c:if test="${not empty errmsg}">
							<div class="row">
								<header style="color: red;"><h6>${errmsg}</h6></header>
							</div>
						</c:if>
					</div>
				</form:form>
			</div>
		</div>
		<!-- container -->
	</div>
</body>
</html>