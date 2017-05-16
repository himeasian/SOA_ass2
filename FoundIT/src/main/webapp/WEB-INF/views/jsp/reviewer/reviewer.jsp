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
	<div class="row">
		<div class="col-lg-10 col-lg-offset-1">
			<div class="panel panel-info">
				<div class="panel-heading">Current Applications</div>
				<div class="panel-body">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Company Name</th>
								<th>Position Type</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="row" items="${applications}">
								<tr class='clickable-row'
									data-href="${pageContext.servletContext.contextPath}/reviewer/review/job/${row._jobID}/application/${row._appID}">
									<td>${row.companyName}</td>
									<td>${row.positionType}</td>
									<td>${row.status}</td>
								</tr>
							</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<script>
jQuery(document).ready(function($) {
    $(".clickable-row").click(function() {
        window.location = $(this).data("href");
    });
});
</script>
</html>