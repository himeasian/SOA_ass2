<%@ include file="../default/nav.jsp" %>
<div class="container" >
  <h2>Job Results</h2>           
  <table class="table table-hover" id="maintable">
    <thead>
      <tr>
        <th>Company Name</th>
        <th>Salary Rate</th>
        <th>Position Type</th>
        <th>Location</th>
        <th>Status</th>      
      </tr>
    </thead>
    <tbody>
		<c:forEach var="row" items="${jobpostings}">
			<tr class='clickable-row' data-href="${pageContext.servletContext.contextPath}/applicant/job/${row._jobID}">
				<td>${row.companyName}</td>
		        <td>${row.salaryRate}</td>
		        <td>${row.positionType}</td>
				<td>${row.location}</td>
				<td>${row.status}</td>
 			</tr>
		</c:forEach>
    </tbody>
  </table>
</div>

<script>
jQuery(document).ready(function($) {
    $(".clickable-row").click(function() {
        window.location = $(this).data("href");
    });
});
</script>