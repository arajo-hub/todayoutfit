<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/inc/asset.jsp" flush="false"/>

<link rel="stylesheet" href="/resources/css/admin/board.css">

<body>

    <div class="container">

        <header>

            <h1>오늘 뭐 입지?</h1>

        </header>

        <section>

            <table id="posttable" class="table">

                <tr>
                    <th class="col-md-1">번호</th>
                    <th class="col-md-5">내용</th>
                    <th class="col-md-1">지역</th>
                    <th class="col-md-1">추천수</th>
                    <th class="col-md-2">작성일</th>
                    <th class="col-md-1">조정</th>
                </tr>

                <c:forEach items="${posts}" var="post">
                <tr>
                    <td>${post.id}</td>
                    <td>${post.content}</td>
                    <td>${post.location}</td>
                    <td>${post.recommendcnt}</td>
                    <td>${post.writedate}</td>
                    <td>
                        <div id="btns">
                            <button type="button" class="form form-control btn-general">삭제</button>
                            <c:if test="${post.declare eq 'DECLARED'}">
                                <button type="button" class="form form-control btn-general">신고취소</button>
                            </c:if>
                        </div>
                    </td>
                </tr>
                </c:forEach>

            </table>

        </section>

        <footer>

            <copyright>Copyright 2021.조아라. All rights reserved.</copyright>

        </footer>

    </div>

</body>
</html>