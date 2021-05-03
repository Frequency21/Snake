<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="../WEB-INF/header.jsp"/>
    <title>Kétszemélyes top lista</title>
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
                    <a class="nav-link" href="one">Egy személyes toplista</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="two" aria-current="page">Két személyes toplista</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<jsp:include page="/TwoPlayerController"/>

<div class="container">


    <div class="d-flex justify-content-center my-4">
        <a href="../TwoPlayerAddController" class="btn btn-outline-primary"><i class="fas fa-plus"></i> Felvitel</a>
    </div>

    <table class="table table-hover">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Első játékos</th>
            <th scope="col">Pontszám</th>
            <th scope="col">Második játékos</th>
            <th scope="col">Pontszámok</th>
            <th scope="col">Törlés/Frissítés</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.playersTuples}">
            <tr>
                <td>${item.first.name}</td>
                <td>${item.first.score}</td>
                <td>${item.second.name}</td>
                <td>${item.second.score}</td>
                <td class="actions">
                    <a href="../TwoPlayerUpdateController?name_1=${item.first.name}&name_2=${item.second.name}"
                       class="me-2">
                        <i class="fas fa-edit"></i>
                    </a>
                    <a href="../TwoPlayerDeleteController?name_1=${item.first.name}&name_2=${item.second.name}">
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
