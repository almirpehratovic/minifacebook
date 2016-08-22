<!-- Template stranica za login varijante (login i logout) -->


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<spring:url var="urlLogin" value="/login" />
<spring:url var="jsCookies" value="/res/js/js-cookie.js"/>

<spring:message var="mTitle" code="navbar.title" />
<spring:message var="mUsername" code="login.form.username" />
<spring:message var="mUsernamePlaceholder" code="login.form.username.placeholder" />
<spring:message var="mPassword" code="login.form.password" />
<spring:message var="mPasswordPlaceholder" code="login.form.password.placeholder" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Login</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
	
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
	
<script	src="${jsCookies}"></script>

<!-- javascript koji omogućava pamćenje zadnjeg unijetog username-a, što je jako zgodno za error situacije -->
<script>
	$(document).ready(function(){
		$('#username').val(Cookies.get('login-username'));
		$('#loginForm').submit(function(event){
			Cookies.set('login-username',$('#username').val());
		});
		
	});
</script>

<style>
.navbar {
	padding: 15px;
	padding-bottom: 30px;
	background-color: #365899;
	color: white;
}
.navbar-header {
	font-size: 2em;
}

.top {
	margin-top: 20px;
}
</style>

</head>
<body>
    <c:set var="formClass" value="" />
	<c:if test="${messageError != null}">
		<div class="alert alert-danger">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>${messageError}</strong>
		</div>
		<c:set var="formClass" value="has-error has-feedback" />
	</c:if>
	<c:if test="${messageSuccess != null}">
		<div class="alert alert-success">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>${messageSuccess}</strong>
		</div>
	</c:if>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<strong>${mTitle}</strong>
			</div>
			<div class="nav navbar-right">
				<form id="loginForm" action="${urlLogin}" method="post" class="form-inline"
					role="form">
					<div class="form-group ${formClass}">
						<label for="username">${mUsername}</label> <input type="text"
							id="username" name="username" class="form-control" 
							placeholder="${mUsernamePlaceholder}"/>
						<c:if test="${messageError != null }">
							<span class="glyphicon glyphicon-remove form-control-feedback"></span>
						</c:if>
					</div>
					<div class="form-group ${formClass}">
						<label for="password">${mPassword}</label> <input type="password"
							id="password" name="password" class="form-control"
							placeholder="${mPasswordPlaceholder}" />
						<c:if test="${messageError != null }">
							<span class="glyphicon glyphicon-remove form-control-feedback"></span>
						</c:if>
					</div>
					<sec:csrfInput />
					<button id="login" type="submit" class="btn btn-primary">Login</button>
				</form>
			</div>
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<div class="col-sm-6">
				<tiles:insertAttribute name="left" />
			</div>
			<div class="col-sm-6">
				<tiles:insertAttribute name="right" />
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<small>
					<a href="?lang=en"><spring:message code="login.lang.en"/></a> <a href="?lang=bs"><spring:message code="login.lang.bs"/></a>
					
				</small>
			</div>
		</div>
	</div>
</body>
</html>