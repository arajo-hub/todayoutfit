<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/resources/css/main.css">

<body>

<form method="POST" action="/admin/login.action">
    <input type="text" id="name" name="name">
    <input type="password" id="pw" name="pw">
    <input type="submit" value="로그인">
</form>

<c:forEach items="${all}" var="admin">
    <div>${admin.id}</div>
    <div>${admin.pw}</div>
</c:forEach>

</body>