<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:url value="/post" var="urlPost"/>
<spring:url value="/like" var="urlLike"/>
<spring:url value="/res/img/like32.png" var="imgLike"/>
<spring:url value="/ws" var="urlWs"/>

<script>
	var app = angular.module('home',[]);
	app.controller('HomeController',function($scope){
		$scope.text = '';
		$scope.checkDisabled = function (){
			if ($scope.text.length == 0 || $scope.text.length > 4000)
				return true;
			return false;
		}
		
		// websocket konekcija na server i primanje novih postova
		// koje unose drugi korisnici u ovom trenutku
		$scope.posts = [];
		userId = ${user.id};
		
		var websocket = new SockJS("${urlWs}");
		var stomp = Stomp.over(websocket);
		
		var showPost = function(frame){
			post = JSON.parse(frame.body);
			if (post.posterId != userId){
				$scope.posts.unshift(post);
				$scope.$apply();  // obavijesti ng engine da je došlo do promjene (ng-repeat)
			}
		}
		
		var connectCallback = function(){
			stomp.subscribe("/topic/post", showPost); // pretplata na temu koju kreira kontroler
		}
		var errorCallback = function(error){
			alert(error.headers.message);
		}
		
		stomp.connect("guest","guest", connectCallback, errorCallback);
		
	});
	
</script>

<div ng-app="home" ng-controller="HomeController">
<form:form modelAttribute="post" action="${urlPost}" method="post" class="form" role="form">
	<div class="form-group">
		<spring:message code="post.placeholder" var="mPostPlaceholder"/>
		<form:textarea path="text" rows="2" class="form-control" placeholder="${mPostPlaceholder}" ng-model="text"></form:textarea>
	</div>
	<div class="form-group text-right">
		<button type="submit" class="btn btn-primary" ng-disabled="checkDisabled()"><spring:message code="post.button" /></button>
	</div>
	<!-- Da bi kreirani post povezali sa korisnikom, dovoljno je referencirati njegov id -->
	<form:hidden path="poster.id" value="${user.id}" />
</form:form>

<!-- web socket podaci - postovi koje unose drugi korisnici u ovom trenutku -->
<div ng-repeat="p in posts">
	<div class="panel panel-default">
		<div clas="panel-header">
			<div class="row">
				<div class="col-sm-1">
					<img width="40" height="40" src="<spring:url value="/users/picture"/>/{{p.posterPictureId}}" > 
				</div>
				<div class="col-sm-11">
					<strong>{{p.posterName}}</strong> <spring:message code="post.text.now" />
				</div>
			</div> 
		</div>
		<div class="panel-body">
			{{p.text}}
		</div>
		<div class="panel-footer text-right">
			<a href="${urlLike}?id={{p.id}}"><img alt="Like" src="${imgLike}"></a>
		</div>
	</div>
</div>

<!-- podaci iz baze -->
<c:forEach var="p" items="${posts}">
	<div class="panel panel-default">
		<div clas="panel-header">
			<div class="row">
				<div class="col-sm-1">
					<img width="40" height="40" src="<spring:url value="/users/picture"/>/${p.poster.pictures[0].id}" > 
				</div>
				<div class="col-sm-11">
					<strong>${p.poster.firstName} ${p.poster.lastName}</strong> <spring:message code="post.text" /> <fmt:formatDate value="${p.dateTime}" pattern="dd.MM.yyyy hh:mm:ss"/>
				</div>
			</div> 
		</div>
		<div class="panel-body">
			${p.text}
		</div>
		<c:set var="showLike" value="1" />
		<div class="panel-footer text-right">
			<c:forEach var="l" items="${p.usersLikingThis}">
				<spring:message code="post.like" arguments="${l.username}"/> |	
				<spring:eval expression="l.id == user.id ? 0 : showLike * 1" var="showLike" />
			</c:forEach>
			<c:if test="${showLike == '1'}">
				<a href="${urlLike}?id=${p.id}"><img alt="Like" src="${imgLike}"></a>
			</c:if>
		</div>
	</div>
</c:forEach>
<p class="text-right"><small><fmt:formatDate value="${dateTime}" pattern="dd.MM.yyyy hh:mm:ss"/></small></p>
</div>