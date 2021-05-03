<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <jsp:include page="../WEB-INF/header.jsp"/>
    <title>Játékos felvitele</title>
</head>
<body>
<jsp:useBean id="player" class="hu.alkfejl.model.PlayerModel" scope="request"/>


<div class="container">
    <form action="${pageContext.request.contextPath}/OnePlayerController" method="post">
        <input type="hidden" name="oldName" value="${player.name}" />
        <div class="form-group">
            <label for="name">Név</label>
            <input required name="name" type="text" class="form-control" id="name"
                   placeholder="Python" value="${player.name}"/>
        </div>
        <div class="form-group">
            <label for="score">Pontszám</label>
            <input required name="score" type="number" class="form-control" id="score"
                   placeholder="25" value="${player.score}"/>
        </div>
        <button id="submit" type="submit" class="btn btn-primary">Küldés</button>
    </form>
</div>



</body>
</html>
