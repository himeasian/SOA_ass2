<%@ include file="../default/nav.jsp"%>
<div class="container">
	<c:if test="${not empty authmsg}">
		<div class="row">
			<div class="col-lg-10 col-lg-offset-1">
				<div class="panel panel-danger">
					<div class="panel-heading">${authmsg}</div>
				</div>
			</div>
		</div>
	</c:if>
</div>
</body>
</html>