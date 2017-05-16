<%@ include file="../default/nav.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<body>
<div class="container">
<div class="panel panel-info">
      <div class="panel-heading">Candidate Shortlist</div>
      <div class="panel-body">
<div class="container">
  <c:if test="${not empty errmsg}">
	<div class="row">
		<header style="color: red;"><h6>${errmsg}</h6></header>
	</div>
  </c:if> 
  </div>
  <table class="table" id="maintable">
  
  <tr>
  	<th>AppID</th>
  	<th>Decision</th>
  </tr>
  		<c:forEach var="row" items="${reviewlist}">
			<tr>
				<td>${row._appID}</td>
				<td>${row.decision}</td>
				
			</tr>
		</c:forEach>
  </table>
  </div>     
  </div>
  </div>
  </div>
  

</body>
</html>