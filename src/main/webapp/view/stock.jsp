<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="today" value="<%=new java.util.Date()%>" />
<c:set var="stockViewDate">
    <fmt:formatDate value="${today}" pattern="yyyy년 MM월 dd일"/>
</c:set>
<c:set var="stockViewClock">
    <fmt:formatDate value="${today}" pattern="HH:mm:ss" />
</c:set>
<c:set var="stockName" value="${sessionScope.stockName}" />
<c:set var="stockInfoMap" value="${sessionScope.stockInfoMap}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Stock 24</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/stock.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <script src="https://cdn.anychart.com/releases/8.11.0/js/anychart-base.min.js"></script>

</head>
<body>
<div class="container">
    <header>
        <h2><a href="#"><i class="ion-plane"></i>Stock 24</a></h2>
        <nav>
            <ul>
                <li>
                    <a href="../index.jsp" title="Stocks">메인화면으로</a>
                </li>
            </ul>
        </nav>
    </header>

    <div class="notice-list">
        <div class="board_title">
            <strong>주식 차트</strong>
            <p>주식의 현재가를 실시간으로 보여주는 그래프에요</p>
            <p>현재 날짜: <c:out value="${stockViewDate}" /></p>
            <p>현재 시간: <c:out value="${stockViewClock}" /></p>
            <form method="post" action="stock">
                <button type="submit" class="w-btn w-btn-green">차트 갱신하기</button>

                <%-- 주식 명칭을 담아서 보냄--%>
                <input type="hidden" name="stockName" value="${stockName}" />
            </form>
        </div>
    </div>
    <div id="chart"></div>
    <script>
        let stockName = "<c:out value="${stockName}"/>";
        let mapData = [];

        <c:forEach var="map" items="${stockInfoMap}" >
            mapData.push(["${map.key}", ${map.value}]);
        </c:forEach>

        // 위의 forEach 에서 reverse 가 힘들기 때문에 여기서 reverse() 절차 필요
        mapData.reverse();
        console.log(mapData);

        anychart.onDocumentReady(function () {

            let data = mapData;

            // create a data set
            let dataSet = anychart.data.set(data);

            // map the data for all series
            let firstSeriesData = dataSet.mapAs({x: 0, value: 1});

            // create a line chart
            let chart = anychart.line();

            // create the series and name them
            let firstSeries = chart.line(firstSeriesData);
            firstSeries.name(stockName);

            // add a legend
            chart.legend().enabled(true);

            // add a title
            chart.title("주식의 현재 가격");

            // specify where to display the chart
            chart.container("chart");

            // name the axes
            chart.xAxis().title("시간(단위: 시:분:초)");
            chart.yAxis().title("현재 가격");

            // draw the resulting chart
            chart.draw();
        });

    </script>

    <%--include footer--%>
    <jsp:include page="../inner/footer.jsp" />
</div>
</body>
</html>

