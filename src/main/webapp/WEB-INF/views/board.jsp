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
                <span>강남구</span>
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

                <button class="btn btn-general">글쓰기</button>
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

</body>
</html>