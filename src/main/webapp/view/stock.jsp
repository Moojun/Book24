<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        </div>
    </div>
    <div id="chart"></div>
    <script>
        anychart.onDocumentReady(function () {
            // add data
            let data = [
                ["10:00:00", 1],
                ["10:00:05", 4],
                ["10:00:10", 6],
                ["10:00:15", 9],
                ["10:00:20", 12],
                ["10:00:25", 13],
                ["10:00:30", 27],
                ["10:00:35", 16],
                ["10:00:40", 16],
                ["10:00:45", 17],
                ["10:00:50", 38],
                ["10:00:55", 16],
                ["10:01:00", 17],
            ];
            // create a data set
            let dataSet = anychart.data.set(data);
            // map the data for all series
            let firstSeriesData = dataSet.mapAs({x: 0, value: 1});
            // create a line chart
            let chart = anychart.line();
            // create the series and name them
            let firstSeries = chart.line(firstSeriesData);
            firstSeries.name("Roger Federer");
            // add a legend
            chart.legend().enabled(true);
            // add a title
            chart.title("Big Three's Grand Slam Title Race");
            // specify where to display the chart
            chart.container("chart");
            // draw the resulting chart
            chart.draw();
        });
    </script>

    <footer>
        <div class="social-media">
            <a href="https://www.instagram.com" target="_blank"><i class="fab fa-instagram"></i></a>
            <a href="https://www.github.com/Moojun" target="_blank"><i class="fab fa-github"></i></a>
            <a href="https://www.facebook.com" target="_blank"><i class="fab fa-facebook-f"></i></a>
        </div>
        <ul class="list">
            <li>
                <a href="#">Home</a>
            </li>
            <li>
                <a href="#">Services</a>
            </li>
            <li>
                <a href="#">About</a>
            </li>
            <li>
                <a href="#">Privacy Policy</a>
            </li>
        </ul>
        <p class="copyright">copyright (c) mjkim@seoultech.ac.kr</p><br><br>
    </footer>
</div>
</body>
</html>

