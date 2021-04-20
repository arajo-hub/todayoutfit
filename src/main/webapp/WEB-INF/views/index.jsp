<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="inc/asset.jsp" flush="false"/>

<link rel="stylesheet" href="/resources/css/main.css">

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

            <div id="outfit" class="mainbox">
                <div> 오늘은 니트, 가디건, 후드티, 맨투맨, 청바지, 면바지, 슬랙스, 원피스를 추천합니다.
                일교차가 크니 겉옷을 챙기시는 게 좋겠어요.
                </div>
            </div>

            <div id="weather" class="mainbox">

                <h3><small>현재</small> 19도</h3>

                <div>
                    <div>최고기온 20도 / 최저기온 15도</div>
                    <div>강수확률 15%</div>
                </div>

            </div>

            <div id="search" class="mainbox">

                <h4>다른 사람들은 뭘 입었을까 궁금하다면?</h4>

                <form method="get" action="/board.action">
                    <div>
                        <div class="input-group">
                        <input type="text" class="form-control" placeholder="위치를 입력해주세요.">
                        <span class="input-group-btn">
                            <input type="submit" class="btn btn-default" id="searchBtn" value="검색">
                        </span>
                        </div>
                    </div>
                </form>

            </div>

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