<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="inc/asset.jsp" flush="false"/>

<link rel="stylesheet" href="/resources/css/main.css">

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

            <div id="outfit" class="mainbox">
                <div> 위치를 찾으면 옷차림을 추천합니다.</div>
            </div>

            <div id="weather" class="mainbox">

                <small>현재</small><h3 id="nowTemp">-도</h3>

                <div>
                    <div>최고기온 <span id="maxTemp">-</span>도 / 최저기온 <span id="minTemp">-</span>도</div>
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

            getWeather(point_x, point_y);


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

        function getWeather(point_x, point_y) {

            fetch("https://api.openweathermap.org/data/2.5/weather?lat="+point_y+"&lon="+point_x+"&appid=e7b4c588445f7aa66ba2455335a97e25&units=metric")
            .then(res => res.json())
            .then(data => {
                const temp = data.main.temp;
                const maxTemp = data.main.temp_max;
                const minTemp = data.main.temp_min;
                $("#nowTemp").html(temp+"도");
                $("#maxTemp").html(maxTemp);
                $("#minTemp").html(minTemp);

                // 기온에 따라 옷차림 추천해주는 ajax
                $.ajax({
                    url:"/recommend.action",
                    data: {temp: temp},
                    dataType: "json",
                    success: function(data) {
                        $("#outfit > div").html(data.temp);
                    }

                });
            })

        }


    </script>

</html>