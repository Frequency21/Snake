<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <jsp:include page="../WEB-INF/header.jsp"/>
    <title>Játékos felvitele</title>
</head>
<body>
<jsp:useBean id="playersTuple" class="hu.alkfejl.model.PTuple" scope="request"/>

<div class="container">
    <form action="${pageContext.request.contextPath}/TwoPlayerController" method="post">
        <input type="hidden" name="oldName_1" value="${playersTuple.first.name}"/>
        <input type="hidden" name="oldName_2" value="${playersTuple.second.name}"/>
        <div class="form-group">
            <label for="name_1">Első játékos</label>
            <input required name="name_1" type="text" class="form-control" id="name_1"
                   placeholder="Python" value="${playersTuple.first.name}"/>
        </div>
        <div class="form-group">
            <label for="score_1">Pontszám</label>
            <input required name="score_1" type="number" class="form-control" id="score_1"
                   placeholder="25" value="${playersTuple.first.score}"/>
        </div>
        <div class="form-group">
            <label for="name_2">Második játékos</label>
            <input required name="name_2" type="text" class="form-control" id="name_2"
                   placeholder="VIPera" value="${playersTuple.second.name}"/>
        </div>
        <div class="form-group">
            <label for="score_2">Pontszám</label>
            <input required name="score_2" type="number" class="form-control" id="score_2"
                   placeholder="25" value="${playersTuple.second.score}"/>
        </div>
        <button id="submit" type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<body>

</body>
</html>
