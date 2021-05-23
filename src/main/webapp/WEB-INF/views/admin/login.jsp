<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/inc/asset.jsp" flush="false"/>

<link rel="stylesheet" href="/resources/css/admin/login.css">

<body>

    <div class="container">

        <header>

            <h1>오늘 뭐 입지?</h1>

        </header>

        <section>

            <form action="login.action" method="POST" id="loginform">
                <label>아이디<input type="text" id="name" name="name" class="form form-control"></label>
                <label>비밀번호<input type="password" id="pw" name="pw" class="form form-control"></label>
                <input type="submit" class="form form-control btn-general" id="loginbtn" value="로그인">
            </form>

        </section>

        <footer>

            <copyright>Copyright 2021.조아라. All rights reserved.</copyright>

        </footer>

    </div>

</body>
</html>