<%@ include file="head.jsp"%>
<div class="container">
	<div class="row">
		<div class="container">
			<h1>Welcome</h1>
		</div>
	</div>
	<br />
	<div class="row">
		<div class="container">
			<form:form id="loginForm" modelAttribute="login" action="${pageContext.servletContext.contextPath}/login"
				method="post">
				<div class="form-group row">
					<form:label path="email" class="col-sm-2 col-form-label">Email</form:label>
					<div class="col-sm-10">
						<form:input path="email" type="email" class="form-control"
							placeholder="Email" />
					</div>
				</div>
				<div class="form-group row">
					<form:label path="password" class="col-sm-2 col-form-label">Password</form:label>
					<div class="col-sm-10">
						<form:input path="password" type="password" class="form-control"
							placeholder="Password" />
					</div>
				</div>
				<div class="form-group row">
					<div class="offset-sm-2 col-sm-10">
						<button type="submit" class="btn btn-default">Log in</button>
						<a class="btn btn-default" href="${pageContext.servletContext.contextPath}/register" role="button">Sign
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