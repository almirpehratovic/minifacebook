<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script>
	var app = angular.module('profile',[]);
	app.controller('ProfileController',function($scope,$filter){
		$scope.password = "${profile.password}";
		$scope.password2 = "${profile.password}";
		
		$scope.checkDisabled = function(){
			if (angular.isDefined($scope.password) && $scope.password != ""
				&& angular.isDefined($scope.password2) && $scope.password2 != ""
				&& $scope.password == $scope.password2)
					return false;
			return true;
		};
		
		$scope.submit = function(){
			$scope.birthDate = ($scope.day == "" ? "01" : $scope.day) + "."
				+ ($scope.month == "" ? "01" : $scope.month) + "."
				+ ($scope.year == "" ? "1990" : $scope.year);
		}
	});
</script>

<c:if test="${messageError != null}">
	<div class="alert alert-danger">
		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		<strong>${messageError}</strong>
	</div>
</c:if>

<c:if test="${messageSuccess!= null}">
	<div class="alert alert-success">
		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
		<strong>${messageSuccess}</strong>
	</div>
</c:if>

<form:form ng-app="profile" ng-controller="ProfileController" modelAttribute="profile" role="form" 
		class="form form-horizontal" method="post" enctype="multipart/form-data">
	<c:set var="errorFirstName"> <form:errors path="firstName" /> </c:set>
	<c:set var="errorLastName"> <form:errors path="lastName" /> </c:set>
	<c:set var="errorBirthDate"> <form:errors path="birthDate" /> </c:set>
	<c:set var="errorPassword"> <form:errors path="password" /> </c:set>
	<c:set var="errorEnabled"> <form:errors path="enabled" /> </c:set>
	<c:set var="errorGender"> <form:errors path="gender" /> </c:set>
	<c:set var="errorCountry"> <form:errors path="country" /> </c:set>
	<form:hidden path="id"/>
	<form:hidden path="username"/>
	<form:hidden path="role"/>
	<div class="form-group">
		<div class="col-sm-2">
			<form:label path="firstName"><spring:message code="user.firstName" /></form:label>
		</div>
		<div class="col-sm-10 <spring:eval expression="errorFirstName!=''?'has-error has-feedback':''" />">
			<form:input path="firstName" class="form-control"/>
			<c:if test="${not empty errorFirstName}">
				<span class="help-block">${errorFirstName}</span>
				<span class="glyphicon glyphicon-remove form-control-feedback"></span>
			</c:if>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2">
			<form:label path="lastName"><spring:message code="user.lastName" /></form:label>
		</div>
		<div class="col-sm-10 <spring:eval expression="errorLastName!=''?'has-error has-feedback':''" />">
			<form:input path="lastName" class="form-control"/>
			<c:if test="${not empty errorLastName}">
				<span class="help-block">${errorLastName}</span>
				<span class="glyphicon glyphicon-remove form-control-feedback"></span>
			</c:if>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2">
			<form:label path="birthDate"><spring:message code="user.birthDate" /></form:label>
		</div>
		<div class="col-sm-10 <spring:eval expression="errorBirthDate!=''?'has-error has-feedback':''" />">
			<form:input path="birthDate" class="form-control"/>
			<c:if test="${not empty errorBirthDate}">
				<span class="help-block">${errorBirthDate}</span>
				<span class="glyphicon glyphicon-remove form-control-feedback"></span>
			</c:if>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2">
			<label><spring:message code="user.password2" /></label>
		</div>
		<div class="col-sm-10 <spring:eval expression="errorPassword!=''?'has-error has-feedback':''" />">
			<input type="password" id="password2" class="form-control" ng-model="password2"/>
			<c:if test="${not empty errorPassword}">
				<span class="help-block">${errorPassword}</span>
				<span class="glyphicon glyphicon-remove form-control-feedback"></span>
			</c:if>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2">
			<form:label path="password"><spring:message code="user.password" /></form:label>
		</div>
		<div class="col-sm-10 <spring:eval expression="errorPassword!=''?'has-error has-feedback':''" />">
			<form:password path="password" class="form-control" ng-model="password"/>
			<c:if test="${not empty errorPassword}">
				<span class="help-block">${errorPassword}</span>
				<span class="glyphicon glyphicon-remove form-control-feedback"></span>
			</c:if>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2">
			<label><spring:message code="user.picture" /></label>
		</div>
		<div class="col-sm-10">
			<input type="file" name="picture"/>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2">
			<form:label path="gender"><spring:message code="user.gender" /></form:label>
		</div>
		<div class="radio col-sm-10 <spring:eval expression="errorGender!=''?'has-error has-feedback':''" />">
			<label class="radio-inline"><form:radiobutton path="gender" value="M"/><spring:message code="login.register.gender.male"/></label>
			<label class="radio-inline"><form:radiobutton path="gender" value="F"/><spring:message code="login.register.gender.female"/></label>
			<c:if test="${not empty errorGender}">
				<span class="help-block">${errorGender}</span>
				<span class="glyphicon glyphicon-remove form-control-feedback"></span>
			</c:if>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2">
			<form:label path="country"><spring:message code="user.country" /></form:label>
		</div>
		<div class="col-sm-10 <spring:eval expression="errorCountry!=''?'has-error has-feedback':''" />">
			<form:select path="country" class="form-control">
				<form:option value="" label="-" />
				<form:options items="${countries}"/>
			</form:select>
			<c:if test="${not empty errorCountry}">
				<span class="help-block">${errorCountry}</span>
				<span class="glyphicon glyphicon-remove form-control-feedback"></span>
			</c:if>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-2">
			<form:label path="enabled"><spring:message code="user.enabled" /></form:label>
		</div>
		<div class="col-sm-10 <spring:eval expression="errorEnabled!=''?'has-error has-feedback':''" />">
			<form:checkbox path="enabled" class="checkbox"/>
			<c:if test="${not empty errorEnabled}">
				<span class="help-block">${errorEnabled}</span>
				<span class="glyphicon glyphicon-remove form-control-feedback"></span>
			</c:if>
		</div>
	</div>
	<button class="btn btn-primary" type="submit" ng-disabled="checkDisabled()"><spring:message code="user.submit" /></button>
</form:form>