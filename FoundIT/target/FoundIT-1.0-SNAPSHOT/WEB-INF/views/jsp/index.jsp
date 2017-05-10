<%@ include file="head.jsp"%>
	<div class="container">
		<div class="row">
			<div class="container">
				<h1>Welcome</h1>
			</div>
		</div>
		<br/>
		<div class="row">
			<div class="container">
				<form:form id="loginForm" modelAttribute="login" action="login" method="post">
					<div class="form-group row">
						<label for="inputEmail" class="col-sm-2 col-form-label">Email</label>
						<div class="col-sm-10">
							<input type="email" class="form-control" id="inputEmail"
								placeholder="Email">
						</div>
					</div>
					<div class="form-group row">
						<label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="inputPassword"
								placeholder="Password">
						</div>
					</div>
					<div class="form-group row">
						<div class="offset-sm-2 col-sm-10">
							<button type="submit" class="btn btn-default">Log in</button>
							<a class="btn btn-default" href="register" role="button">Sign
						up</a>
						</div>
					</div>
				</form:form>
			</div>
			<!-- container -->
		</div>
	</div>

</body>
</html>