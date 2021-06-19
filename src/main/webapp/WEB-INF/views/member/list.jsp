<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/inc/asset.jsp" flush="false"/>

<link rel="stylesheet" href="/resources/css/member/main.css">
<link rel="stylesheet" href="/resources/css/member/board.css">

<body>

    <div class="container">

        <header>

            <h1>오늘 뭐 입지?</h1>

            <div id="now">
                <span class="glyphicon glyphicon-map-marker"></span>
                <span id="currentLocation">위치찾는 중</span>
            </span>

        </header>

        <section>

            <div id="search" class="mainbox">

                <h4><%=request.getAttribute("location")%>의 옷차림</h4>

                <form method="get">
                    <div>
                        <div class="input-group">
                        <input type="text" class="form-control" id="lcation" name="location" placeholder="위치를 입력해주세요.">
                        <span class="input-group-btn">
                            <input type="submit" class="btn btn-default" id="searchBtn" value="검색">
                        </span>
                        </div>
                    </div>
                </form>

            </div>

            <div id="btns">

                <button class="btn btn-general" data-toggle="modal"  data-target="#writeModal">글쓰기</button>
                <button class="btn btn-general" onclick="location.href='/'">메인으로</button>

            </div>
            <c:if test="${totalPosts.totalPages == 0}">
                <div id="outfit" class="mainbox">
                    <div>게시물이 없습니다</div>
                </div>
            </c:if>

            <c:if test="${totalPosts.totalPages != 0}">
                <c:forEach items="${totalPosts.content}" var="post">
                    <div id="outfit" class="mainbox">
                        <div>${post.content}</div>
                        <small>${post.writedate}</small>
                        <div id="eachBtn">
                            <button id="recommend" class="glyphicon glyphicon-thumbs-up" data-seq=${post.id} value="${post.recommendcnt}">${post.recommendcnt}</button>
                            <button id="declare" class="glyphicon glyphicon-ban-circle" data-seq=${post.id}></button>
                        </div>
                    </div>

                </c:forEach>
            </c:if>

            <nav aria-label="Page navigation example" id="pagination">
                <ul class="pagination">

                    <!-- 이전버튼 -->
                    <!-- <li class="page-item"><a class="page-link" href="/board/list.action?location=${location}&page=${totalPosts.number-1}">&laquo;</a></li> -->

                    <!-- 페이지그룹 -->
                    <c:forEach begin="${startPage}" end="${endPage}" var="i">
                        <c:choose>
                            <c:when test="${totalPosts.pageable.pageNumber+1 == i}">
                                <li class="page-item"><a class="page-link" href="/board/list.action?location=${location}&page=${i}">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link" href="/board/list.action?location=${location}&page=${i}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!-- 다음버튼 -->
                    <!-- <li class="page-item"><a class="page-link" href="/board/list.action?location=${location}&page=${totalPosts.number+2}">&raquo;</a></li> -->
                </ul>
            </nav>

        </section>

        <footer>

            <div>
                <span class="glyphicon glyphicon-envelope"></span>
                <a href="mailto:joara9566@naver.com">관리자에게 메일쓰기</a>
            </div>

            <copyright>Copyright 2021.조아라. All rights reserved.</copyright>

        </footer>

    </div>

    <!-- 옷차림 글 작성하는 모달 -->
    <div class="modal fade" id="writeModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">오늘 당신의 옷차림을 알려주세요</h5>
          </div>
          <form action="add.action" method="POST">
          <div class="modal-body">
            <textarea id="content" name="content" class="form-control"></textarea>
            <input type="hidden" name="nowLocation" id="nowLocation" value=""/>
          </div>
          <div class="modal-footer">
            <input type="submit" class="btn btn-general" value="저장">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
          </div>
          </form>
        </div>
      </div>
    </div>

    <script>

        if (navigator.geolocation) { // GPS를 지원하면
                navigator.geolocation.getCurrentPosition(function(position) {

                // 좌표
                const point_x=position.coords.longitude;
                const point_y=position.coords.latitude;

                // 좌표정보 -> 위치정보 ajax
                $.ajax({
                    url:"https://dapi.kakao.com/v2/local/geo/coord2address.json?x="+point_x+"&y="+point_y+"&input_coord=WGS84",
                    type:'GET',
                    headers: {'Authorization' : 'KakaoAK efc1f6089ac80c3fdbe8f867eadb5c47'},
                    success: function(data) {

                        $("#currentLocation").html(data.documents[0].address["region_2depth_name"]);
                        $("#nowLocation").val(data.documents[0].address["region_2depth_name"]);


                    }

                });

                }, function(error) {
                    console.error(error);
                }, {
                    enableHighAccuracy: false,
                    maximumAge: 0,
                    timeout: Infinity
                });
            } else {
                alert('GPS를 지원하지 않습니다');
            }

        // 게시물 좋아요 수 올리는 ajax
        $("#outfit > #eachBtn > #recommend").on('click', function() {
            var me = this;
            $.ajax({
                url:"/board/recommendup.action",
                data:{seq: $(this).data("seq")},
                success: function(data) {
                    $(me).text(data);
                }
            });
        });

        // 게시물 신고하는 ajax
        $("#outfit > #eachBtn > #declare").on('click', function() {
            var me = this;
            $.ajax({
                url:"/board/declare.action",
                data:{seq: $(this).data("seq")},
                success: function(data) {
                    console.log("declare succeeded");
                }
            });
        });

    </script>

</body>
</html>