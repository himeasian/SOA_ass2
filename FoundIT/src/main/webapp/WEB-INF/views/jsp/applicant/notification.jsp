<%@ include file="../default/nav.jsp" %>
<div class="container" >
  <h2>Job Results</h2>           
  <table class="table table-hover" id="maintable">
	<c:forEach var="row" items="${notifications}">
		<tr class='clickable-row' data-href="#">
			<td>${row.message}</td>
			<td>${row.from}</td>
		</tr>
	</c:forEach>
  </table>
</div>
<script>
jQuery(document).ready(function($) {
    $(".clickable-row").click(function() {
        window.location = $(this).data("href");
    });
});
</script>