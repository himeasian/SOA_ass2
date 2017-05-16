<%@ include file="head.jsp" %>

<div class="container">
	<div class="row">
		<h1>${logoutmsg}</h1>
	</div>
	<div class="row">
		<h3>Click here to sign in again! <a class="btn btn-default" href="${pageContext.servletContext.contextPath}/login" role="button">Log in</a></h3>
	</div>
</div>

</body>
</html>