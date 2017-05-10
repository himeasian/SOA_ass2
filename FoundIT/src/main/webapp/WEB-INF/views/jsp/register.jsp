<%@ include file="head.jsp"%>
<body>
	<div class="container">
		<div class="row">
			<h1>Register An Account</h1>
		</div>
		<br/>
		<div class="container">
			<form:form id="registerForm" modelAttribute="register" action="register" method="post">
				<div class="form-group row">
					<label for="inputGName" class="col-sm-2 col-form-label">First Name</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputGName"
							placeholder="First Name" required>
					</div>
				</div>
				<div class="form-group row">
					<label for="inputSName" class="col-sm-2 col-form-label">Surname</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputSName"
							placeholder="Surname" required>
					</div>
				</div>
				<div class="form-group row">
					<label for="inputEmail" class="col-sm-2 col-form-label">Email</label>
					<div class="col-sm-10">
						<input type="email" class="form-control" id="inputEmail"
							placeholder="Email" required>
					</div>
				</div>
				<div class="form-group row">
					<label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" id="inputPassword"
							placeholder="Password" required>
					</div>
				</div>
				<div class="form-group row">
					<label for="inputRole" class="col-sm-2 col-form-label">Role</label>
					<div class="col-sm-10 pull-right">
						<select class="form-control" id="inputRole" required>
							<option value="applicant">Applicant</option>
							<option value="manager">Manager</option>
						</select>
					</div>
				</div>
				<div class="form-group row">
					<label for="inputCompany" class="col-sm-2 col-form-label">Company</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputCompany"
							placeholder="Company">
					</div>
				</div>				
				<div class="form-group row">
					<div class="offset-sm-2 col-sm-10">
						<button type="submit" class="btn btn-default">Register</button>
					</div>
				</div>	
			</form:form>
		</div>
		<!-- container -->
	</div>
</body>
</html>