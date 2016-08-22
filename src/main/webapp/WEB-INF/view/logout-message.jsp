<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url value="/res/img/logout.png" var="imgLogout"/>


<div class="panel-group">
	<div class="panel panel-default">
		<img alt="Logout" src="${imgLogout}" width="320" height="400">
	</div>
	<div class="panel panel-info">
    	<div class="panel-heading text-center"><h3><spring:message code="logout.title" /></h3></div>
      	<div class="panel-body text-center"><h4><spring:message code="logout.subtitle" /></h4></div>
    </div>
</div>
