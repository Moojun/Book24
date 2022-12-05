<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    // 로그아웃 뒤로가기 처리
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0L);

    String userName = (String) session.getAttribute("userName");
    String status = "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Stock 24</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
<body>
<div class="container">
    <header>
        <h2><a href="#"><i class="ion-plane"></i>Stock 24</a></h2>
        <nav>
            <ul>
                <li>
                    <a href="view/stock.jsp" title="Stocks">주가 확인</a>
                </li>
                <li>
                    <a href="#" title="Interests">관심 목록</a>
                </li>
                <li>
                    <a href="board/list" title="Bullet-Board">게시판</a>
                </li>
                <li>
                    <!-- <a class="btn" href="#" title="Login">로그인 / 회원가입</a> -->
                    <% if (userName == null) {
                        status = "로그인/회원가입";
                    %>
                    <input type="button" class="btn"
                           onclick="location.href='login.jsp'" value=<%=status%> />
                    <% } else {
                        status = userName;
                    %>
                    <form action="logout.do" method="post">
                        <input type="button" class="btn" value="<%=status%>님 환영합니다" />
                        <input type="submit" class="btn" value="로그아웃" />
                    </form>
                    <% } %>
                </li>
            </ul>
        </nav>
    </header>

    <div class="cover">
        <h1>검색할 주식을 입력하세요</h1>
        <form class="flex-form" method="post" action="stock/view">
            <label for="from">
                <i class="ion-location"></i>
            </label>
            <input type="search" name="stockName" placeholder="입력하기">
            <input type="submit" value="Search">
        </form>
    </div>
</div>
    <%--include footer--%>
    <jsp:include page="inner/footer.jsp" />
</body>
</html>