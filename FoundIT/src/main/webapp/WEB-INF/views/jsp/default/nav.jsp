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
				<c:choose>
					<c:when test="${user.role == 'applicant'}">
						<li><a href="${pageContext.servletContext.contextPath}/applicant">Home</a></li>
						<li><a href="${pageContext.servletContext.contextPath}/applicant/notifications">Notifications 
							<c:if test="${not empty numNotifs}">
								<span class="badge">${numNotifs}</span>
							</c:if>
						</a></li>
					</c:when>
					<c:when test="${user.role == 'manager'}">
						<li><a href="${pageContext.servletContext.contextPath}/manager/jobposting">JobPostings</a></li>
						<li><a href="${pageContext.servletContext.contextPath}/manager/application">Applications</a></li>
						<li><a href="${pageContext.servletContext.contextPath}/manager/review">Reviews</a></li>
						<li><a href="${pageContext.servletContext.contextPath}/manager/hiringteam">Hiring Teams</a></li>
					</c:when>
					<c:when test="${user.role == 'reviewer'}">
						
					</c:when>
				</c:choose>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:if test="${user.role != 'applicant'}">
					<li><a class="disabled">${user.company}</a></li>
				</c:if>
				<c:if test="${user.role == 'applicant'}">
					<li><a class="disabled">${user.fname} ${user.lname}</a></li>
				</c:if>
				<li><a href="${pageContext.servletContext.contextPath}/logout">Sign	out</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</nav>