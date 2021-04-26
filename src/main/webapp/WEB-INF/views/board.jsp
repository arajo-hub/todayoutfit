<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="inc/asset.jsp" flush="false"/>

<link rel="stylesheet" href="/resources/css/main.css">
<link rel="stylesheet" href="/resources/css/board.css">

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

                <h4>'강남구'의 옷차림</h4>

                <form method="get">
                    <div>
                        <div class="input-group">
                        <input type="text" class="form-control" placeholder="강남구">
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

            <div id="outfit" class="mainbox">
                <div> 오늘은 니트, 가디건, 후드티, 맨투맨, 청바지, 면바지, 슬랙스, 원피스를 추천합니다.
                일교차가 크니 겉옷을 챙기시는 게 좋겠어요.
                </div>
                <div id="eachBtn">
                    <span id="recommend" class="glyphicon glyphicon-thumbs-up"></span>
                    <span>10</span>
                    <span id="declare" class="glyphicon glyphicon-ban-circle"></span>
                </div>
            </div>

            <div id="outfit" class="mainbox">
                <div> 오늘은 니트, 가디건, 후드티, 맨투맨, 청바지, 면바지, 슬랙스, 원피스를 추천합니다.
                일교차가 크니 겉옷을 챙기시는 게 좋겠어요.
                </div>
                <div id="eachBtn">
                    <span id="recommend" class="glyphicon glyphicon-thumbs-up"></span>
                    <span>10</span>
                    <span id="declare" class="glyphicon glyphicon-ban-circle"></span>
                </div>
            </div>

            <div id="outfit" class="mainbox">
                <div> 오늘은 니트, 가디건, 후드티, 맨투맨, 청바지, 면바지, 슬랙스, 원피스를 추천합니다.
                일교차가 크니 겉옷을 챙기시는 게 좋겠어요.
                </div>
                <div id="eachBtn">
                    <span id="recommend" class="glyphicon glyphicon-thumbs-up"></span>
                    <span>10</span>
                    <span id="declare" class="glyphicon glyphicon-ban-circle"></span>
                </div>
            </div>

              <nav aria-label="Page navigation example" id="pagination">
                <ul class="pagination">
                  <li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
                  <li class="page-item"><a class="page-link" href="#">1</a></li>
                  <li class="page-item"><a class="page-link" href="#">2</a></li>
                  <li class="page-item"><a class="page-link" href="#">3</a></li>
                  <li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
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
          <form action="board/add.action" method="POST">
          <div class="modal-body">
            <textarea id="content" name="content" class="form-control"></textarea>
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

    </script>

</body>
</html>