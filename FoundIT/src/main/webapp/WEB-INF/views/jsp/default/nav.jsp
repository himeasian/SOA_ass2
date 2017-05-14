<%@ include file="head.jsp"%>
<nav class="navbar navbar-default navbar-static-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="">FoundIT Co.</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.servletContext.contextPath}/applicant">Home</a></li>
				<li>
					<a href="${pageContext.servletContext.contextPath}/applicant/notifications">
						Notifications 
						<c:if test="${not empty numNotifs}">
							<span class="badge">${numNotifs}</span>
						</c:if>
					</a>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:if test="${user.role != 'applicant'}">
					<li>${user.company}</li>
				</c:if>
				<c:if test="${user.role == 'applicant'}">
					<li><a class="disabled">${user.fname} ${user.lname}</a></li>
				</c:if>
				<li><a href="${pageContext.servletContext.contextPath}/logout">Sign out</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</nav>