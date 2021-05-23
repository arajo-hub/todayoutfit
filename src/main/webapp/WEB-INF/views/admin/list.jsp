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

                <tr>
                    <td>1</td>
                    <td>내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.</td>
                    <td>강남구</td>
                    <td>10</td>
                    <td>2021-05-23</td>
                    <td>
                        <div id="btns">
                            <button type="button" class="form form-control btn-general">삭제</button>
                            <button type="button" class="form form-control btn-general">신고취소</button>
                        </div>
                    </td>
                </tr>

            </table>

        </section>

        <footer>

            <copyright>Copyright 2021.조아라. All rights reserved.</copyright>

        </footer>

    </div>

</body>
</html>