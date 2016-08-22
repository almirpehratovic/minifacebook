<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/register" var="urlRegister" />
<spring:url value="/api/check" var="urlCheck"/>

<fmt:formatDate value="${user.birthDate}" pattern="yyyy-MM-dd" var="birthDateFormatted"/>

<script>
	var app = angular.module('login',[]);
	app.controller('LoginController',function($scope,$filter,$http){
		$scope.firstName = "${user.firstName}";
		$scope.lastName = "${user.lastName}";
		$scope.username = "${user.username}";
		$scope.gender = "${user.gender}";
		$scope.password = "${user.password}";
		$scope.password2 = "${user.password}";
		
		
		$scope.day = $filter('date')("${birthDateFormatted}","dd");
		$scope.month= $filter('date')("${birthDateFormatted}","MM");
		$scope.year = $filter('date')("${birthDateFormatted}","yyyy");
		
		
		$scope.checkDisabled = function(){
			if (angular.isDefined($scope.firstName) && $scope.firstName != ""
					&& angular.isDefined($scope.lastName) && $scope.lastName != ""
					&& angular.isDefined($scope.username) && $scope.username != ""
					&& angular.isDefined($scope.password) && $scope.password != ""
					&& angular.isDefined($scope.password2) && $scope.password2 != ""
					&& $scope.password == $scope.password2
					&& angular.isDefined($scope.gender) && $scope.gender != "")
				return false;
			return true;
		};
		
		$scope.submit = function(){
			$scope.birthDate = ($scope.day == "" ? "01" : $scope.day) + "."
				+ ($scope.month == "" ? "01" : $scope.month) + "."
				+ ($scope.year == "" ? "1990" : $scope.year);
		}
		
		$scope.usernameReserved = false;
		
		$scope.$watch('username',function(newValue,oldValue){
			console.log('Promjena');
			$http({
				method: 'GET',
				url: '${urlCheck}?username=' + newValue
			}).then(function successCallback(response){
				if (response.data == true){
					$scope.usernameReserved = true;
				} else {
					$scope.usernameReserved = false;
				}
			},function errorCallback(response){
				alert(response.status)
			});
		});
	});
</script>

 
<div ng-app="login" ng-controller="LoginController">
	<h1><spring:message code="login.register.title"/></h1>
	<h3><spring:message code="login.register.subtitle"/></h3>

	<form:form modelAttribute="user" action="${urlRegister}" method="post" role="form" class="form form-horizontal">
		<form:hidden path="id"/>
		<c:set var="errorFirstName"> <form:errors path="firstName" /> </c:set>
		<c:set var="errorLastName"> <form:errors path="lastName" /> </c:set>
		<c:set var="errorUsername"> <form:errors path="username" /> </c:set>
		<c:set var="errorPassword"> <form:errors path="password" /> </c:set>
		<c:set var="errorGender"> <form:errors path="gender" /> </c:set>
		<c:set var="errorDate"> <form:errors path="birthDate" /> </c:set>
		
		<div class="form-group">
			<div class="col-sm-6 <spring:eval expression="errorFirstName!=''?'has-error has-feedback':''" />">
				<spring:message code="login.register.firstName.placeholder" var="mFirstName"/>
				<form:input path="firstName" type="text" id="firstName" name="firstName" placeholder="${mFirstName}" class="form-control" ng-model="firstName"/>
					<c:if test="${not empty errorFirstName}">
						<span class="help-block">${errorFirstName}</span>
						<span class="glyphicon glyphicon-remove form-control-feedback"></span>
					</c:if>
			</div>
			<div class="col-sm-6  <spring:eval expression="errorLastName!=''?'has-error has-feedback':''" />">
				<spring:message code="login.register.lastName.placeholder" var="mLastName"/>
				<form:input path="lastName" type="text" id="lastName" name="lastName" placeholder="${mLastName}" class="form-control" ng-model="lastName"/>
				<c:if test="${not empty errorLastName}">
						<span class="help-block">${errorLastName}</span>
						<span class="glyphicon glyphicon-remove form-control-feedback"></span>
				</c:if>
			</div>
		</div>
		<div class="form-group  <spring:eval expression="errorUsername!=''?'has-error has-feedback':''" />">
			<div class="col-sm-12">
				<spring:message code="login.register.username.placeholder" var="mUsername"/>
				<form:input path="username" type="text" id="username" name="username" placeholder="${mUsername}" class="form-control" ng-model="username"/>
				<c:if test="${not empty errorUsername}">
						<span class="help-block">${errorUsername}</span>
						<span class="glyphicon glyphicon-remove form-control-feedback"></span>
				</c:if>
				<span class="help-block" ng-show="usernameReserved"><spring:message code="login.register.username.reserved"/></span>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-6  <spring:eval expression="errorPassword!=''?'has-error has-feedback':''" />">
				<spring:message code="login.register.password2.placeholder" var="mPassword2"/>
				<input type="password" id="password2" name="password2" placeholder="${mPassword2}" class="form-control" ng-model="password2"/>
				<c:if test="${not empty errorPassword}">
						<span class="help-block">${errorPassword}</span>
						<span class="glyphicon glyphicon-remove form-control-feedback"></span>
				</c:if>
			</div>
			<div class="col-sm-6  <spring:eval expression="errorPassword!=''?'has-error has-feedback':''" />">
				<spring:message code="login.register.password.placeholder" var="mPassword"/>
				<form:password path="password" id="password" name="password" placeholder="${mPassword}" class="form-control" ng-model="password"/>
				<c:if test="${not empty errorPassword}">
						<span class="help-block">${errorPassword}</span>
						<span class="glyphicon glyphicon-remove form-control-feedback"></span>
				</c:if>
			</div>
		</div>
		<div class="form-group">
			<div class="form-group col-sm-6 nospacing">
				<div class="col-sm-4">
					<select class="form-control" id="day" ng-model="day">
						<c:forEach begin="1" end="31" var="x">
							<option><c:if test="${x<10}">0</c:if>${x}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4">
					<select class="form-control" id="month" ng-model="month">
						<c:forEach begin="1" end="12" var="x">
							<option><c:if test="${x<10}">0</c:if>${x}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4">
					<select class="form-control" id="year" ng-model="year">
						<c:forEach begin="0" end="100" var="x" step="1">
							<option>${2000 - x}</option>
						</c:forEach>
					</select>
				</div>
				<c:if test="${not empty errorDate}">
						<span class="help-block">${errorDate}</span>
						<span class="glyphicon glyphicon-remove form-control-feedback"></span>
				</c:if>
			</div>
			<div class="col-sm-6 <spring:eval expression="errorGender!=''?'has-error has-feedback':''" />">
				<label class="radio-inline"><form:radiobutton path="gender" name="gender" value="M" ng-model="gender"/><spring:message code="login.register.gender.male"/></label>
				<label class="radio-inline"><form:radiobutton path="gender" name="gender" value="F" ng-model="gender"/><spring:message code="login.register.gender.female"/></label>
				<c:if test="${not empty errorGender}">
						<span class="help-block">${errorGender}</span>
						<span class="glyphicon glyphicon-remove form-control-feedback"></span>
				</c:if>
			</div>
			
			<form:hidden path="birthDate" value="{{birthDate}}"></form:hidden>
		</div>
		
		<div class="col-sm-8">
			<small><spring:message code="login.register.terms"/></small>
		</div>
		
		<div class="col-sm-4">
			<button id="signup" type="submit" class="btn btn-primary btn-block" ng-disabled="checkDisabled()" ng-click="submit()"><spring:message code="login.register.submit"/></button>
		</div>
	</form:form>
	
	

</div>