<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<spring:url var="urlPicture" value="/users/picture" />
<spring:url var="urlLogout" value="/logout" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Home</title>

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

<script src="//cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.2/stomp.min.js"></script>

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

.container {
	margin: 30px;
}

.facebook-menu img{
	padding-bottom: 10px;
}
</style>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<strong><spring:message code="navbar.title" /></strong>
			</div>
			<div class="nav navbar-right">
				<form action="${urlLogout}" method="post" role="form">
					<sec:csrfInput/>
					<button type="submit" class="btn btn-info"><spring:message code="logout" /></button>
				</form>
			</div>
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<div class="col-sm-3 facebook-menu">
 				<tiles:insertAttribute name="left"/>
			</div>
			<div class="col-sm-9">
				<tiles:insertAttribute name="content"/>
			</div>
	<%-- 		<div class="col-sm-2">
				<tiles:insertAttribute name="right"/> --%>
			</div>
		</div>
	</div>

</body>
</html>