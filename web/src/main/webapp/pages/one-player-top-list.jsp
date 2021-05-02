<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="../WEB-INF/header.jsp"/>
    <title>Egy játékos top lista</title>
</head>
<body>

<nav class="navbar navbar-expand navbar-dark bg-dark justify-content-center">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" href="../home">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="one" aria-current="page">Egy személyes toplista</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="two">Két személyes toplista</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<jsp:include page="/OnePlayerController"/>

<div class="container topList">

    <div class="d-flex justify-content-center mb-2">
        <a href="../OnePlayerAddController" class="btn btn-outline-primary mx-auto">
            <i class="fas fa-plus"></i>
            Felvitel
        </a>
    </div>

    <table class="table table-hover">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Név</th>
            <th scope="col">Pontszámok</th>
            <th scope="col">Törlés/Frissítés</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.players}">
            <tr>
                <td>${item.name}</td>
                <td>${item.score}</td>
                <td>
                    <a href="../OnePlayerUpdateController?playerName=${item.name}" class="me-2">
                        <i class="fas fa-edit"></i>
                    </a>
                    <a href="../OnePlayerDeleteController?playerName=${item.name}">
                        <i class="fas fa-trash"></i>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


</body>
</html>
