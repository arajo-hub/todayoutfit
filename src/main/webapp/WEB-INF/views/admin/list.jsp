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

            <button onclick="location.href='/admin/logout.action'" class="form form-control" id="logoutbtn">로그아웃</button>

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
                            <button type="button" id="delbtn" class="form form-control btn-general" data-toggle="modal" data-target="#delModal" data-seq="${post.id}">삭제</button>
                            <c:if test="${post.declare eq 'DECLARED'}">
                                <button type="button" id="cancelbtn" class="form form-control btn-general" data-seq="${post.id}">신고취소</button>
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

    <!-- 글 삭제하는 모달 -->
        <div class="modal fade" id="delModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">삭제 확인</h5>
                </div>
                <form action="del.action" method="GET">
                    <div class="modal-body">
                        <div>정말 삭제하시겠습니까?</div>
                        <input type="hidden" id="id" name="id">
                    </div>
                    <div class="modal-footer">
                        <input type="submit" class="btn btn-general" value="삭제">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                    </div>
                </form>
            </div>
          </div>
        </div>

    <script>

        // 삭제 확인 모달로 글번호 보내는 기능
        $("#btns > #delbtn").on('click', function(){
            $('#id').val($(this).data("seq"));
        });

        // 게시물 신고 취소하는 ajax
        $("#btns > #cancelbtn").on('click', function(){
            var me = this;
            $.ajax({
                url:"/admin/board/canceldeclare.action",
                data:{id: $(this).data("seq")},
                success: function(data) {
                    if (data == 'NOT_DECLARED') {
                        $(me).hide();
                        console.log("cancel succeeded");
                    } else {
                        console.log("cancel failed");
                    }
                }
            });
        });

    </script>

</body>
</html>