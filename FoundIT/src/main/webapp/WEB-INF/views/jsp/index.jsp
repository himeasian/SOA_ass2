<%@ include file="head.jsp" %>
<body>
	<h1>Index Page</h1>
	<c:if test = "${not empty link}">
		<a href="${link}">${link}</a>
	</c:if>
</body>
</html>