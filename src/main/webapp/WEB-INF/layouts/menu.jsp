<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url value="/res/img/home.png" var="imgHome" />
<spring:url value="/home" var="urlHome"/>
<spring:message code="menu.home" var="mHome" />

<spring:url value="/res/img/pencil.png" var="imgEdit" />
<spring:url value="/edit" var="urlEdit"/>
<spring:message code="menu.edit" var="mEdit" />

<spring:url value="/res/img/bug.png" var="imgBug" />
<spring:url value="/bug" var="urlBug"/>
<spring:message code="menu.bug" var="mBug" />

<spring:url value="/res/img/report.png" var="imgReport" />
<spring:url value="/report" var="urlReport"/>
<spring:message code="menu.report" var="mReport" />

<spring:url value="/res/img/stream.png" var="imgStream" />
<spring:url value="/stream" var="urlStream"/>
<spring:message code="menu.stream" var="mStream" />

<spring:url value="/res/img/database.png" var="imgDatabase" />
<spring:message code="menu.database" var="mDatabase" />


<div class="row">
	<div class="col-sm-2">
		<img alt="Home" src="${imgHome}">
	</div>
	<div class="col-sm-10">
		<a href="${urlHome}">${mHome}</a>
	</div>
</div>
<div class="row">
	<div class="col-sm-2">
		<img alt="Edit" src="${imgEdit}">
	</div>
	<div class="col-sm-10">
		<a href="${urlEdit}">${mEdit}</a>
	</div>
</div>
<div class="row">
	<div class="col-sm-2">
		<img alt="Bug" src="${imgBug}">
	</div>
	<div class="col-sm-10">
		<a href="${urlBug}">${mBug}</a>
	</div>
</div>
<div class="row">
	<div class="col-sm-2">
		<img alt="Report" src="${imgReport}">
	</div>
	<div class="col-sm-10">
		<a href="${urlReport}">${mReport}</a>
	</div>
</div>
<div class="row">
	<div class="col-sm-2">
		<img alt="Stream" src="${imgStream}">
	</div>
	<div class="col-sm-10">
		<a href="${urlStream}">${mStream}</a>
	</div>
</div>
<div class="row">
	<div class="col-sm-2">
		<img alt="Database" src="${imgDatabase}">
	</div>
	<div class="col-sm-10">
		<a target="_blank" href="http://localhost:8082">${mDatabase}</a>
	</div>
</div>
