<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url value="/res/img/see.png" var="imgSee"/>
<spring:url value="/res/img/share.png" var="imgShare"/>
<spring:url value="/res/img/find.png" var="imgFind"/>


<div class="row">
	<div class="col-sm-12">
		<h2><spring:message code="login.intro.title" /></h2>
	</div>
</div>
<div class="row top">
	<div class="col-sm-2">
		<img alt="See" src="${imgSee}">
	</div>
	<div class="col-sm-10">
		<h4><strong><spring:message code="login.intro.photos.title" /></strong></h4> <spring:message code="login.intro.photos.text" />
	</div>
</div>
<div class="row top">
	<div class="col-sm-2">
		<img alt="See" src="${imgShare}">
	</div>
	<div class="col-sm-10">
		<h4><strong><spring:message code="login.intro.share.title" /></strong></h4> <spring:message code="login.intro.share.text" />
	</div>
</div>
<div class="row top">
	<div class="col-sm-2">
		<img alt="See" src="${imgFind}">
	</div>
	<div class="col-sm-10">
		<h4><strong><spring:message code="login.intro.find.title" /></strong></h4> <spring:message code="login.intro.find.text" />
	</div>
</div>