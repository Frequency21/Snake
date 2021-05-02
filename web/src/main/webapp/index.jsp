<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="WEB-INF/header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <title>Főoldal</title>
</head>

<body>

<nav class="navbar navbar-expand navbar-dark bg-dark justify-content-center">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="home">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="toplist/one">Egy személyes toplista</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="toplist/two">Két személyes toplista</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

</body>
</html>
