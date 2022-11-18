<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Stock 24</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/list.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
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
            <strong>게시판</strong>
            <p>이용자 간에 자유롭게 의견을 나눌 수 있는 게시판입니다.</p>
        </div>
        <table>
            <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>조회수</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>1</td>
                <td>test</td>
                <td>test</td>
                <td>test</td>
                <td>1</td>
            </tr>
            <tr>
                <td>2</td>
                <td>test</td>
                <td>test</td>
                <td>test</td>
                <td>1</td>
            </tr>
            <tr>
                <td>3</td>
                <td>test</td>
                <td>test</td>
                <td>test</td>
                <td>1</td>
            </tr>
            <tr>
                <td>4</td>
                <td>test</td>
                <td>test</td>
                <td>test</td>
                <td>1</td>
            </tr>
            <tr>
                <td>5</td>
                <td>test</td>
                <td>test</td>
                <td>test</td>
                <td>1</td>
            </tr>
            <tr>
                <td>6</td>
                <td>test</td>
                <td>test</td>
                <td>test</td>
                <td>1</td>
            </tr>
            <tr>
                <td>7</td>
                <td>test</td>
                <td>test</td>
                <td>test</td>
                <td>1</td>
            </tr>
            <tr>
                <td>8</td>
                <td>test</td>
                <td>test</td>
                <td>test</td>
                <td>1</td>
            </tr>
            <tr>
                <td>9</td>
                <td>test</td>
                <td>test</td>
                <td>test</td>
                <td>1</td>
            </tr>
            <tr>
                <td>10</td>
                <td>test</td>
                <td>test</td>
                <td>test</td>
                <td>1</td>
            </tr>
            </tbody>
        </table>
        <div class="board_page">
            <a href="#" class="bt first"><<</a>
            <a href="#" class="bt prev"><</a>
            <a href="#" class="num on">1</a>
            <a href="#" class="num">2</a>
            <a href="#" class="num">3</a>
            <a href="#" class="num">4</a>
            <a href="#" class="num">5</a>
            <a href="#" class="bt next">></a>
            <a href="#" class="bt last">>></a>
        </div>
    </div>

    <%--include footer--%>
    <jsp:include page="../inner/footer.jsp" />
</div>
</body>
</html>