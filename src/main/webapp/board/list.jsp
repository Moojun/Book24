<%@ page import="com.seoultech.stock24.Entity.Notice" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            <c:forEach var="n" items="${list}" begin="0" end="9">
            <tr>
                <td>${n.id}</td>
                <td><a href="detail?id=${n.id}">${n.title}</a></td>
                <td>${n.writerId}</td>
                <td><fmt:formatDate value="${n.regDate}" pattern="yyyy-MM-dd" /> </td>
                <td><fmt:formatNumber type="number" value="${n.hit}" /></td>
            </tr>
            </c:forEach>

            </tbody>
        </table>
        <div class="board_page">

            <c:set var="page" value="${param.p == null ? 1 : param.p}" />
            <c:set var="startNum" value="${page - (page - 1) % 5}" />

<%--            임의로 lastNum 지정--%>
            <c:set var="lastNum" value="23" />

            <c:if test="${startNum - 1 > 0}">
                <a href="?p=${startNum - 1}&t=&q=" class="bt prev"><</a>
            </c:if>
            <c:if test="${startNum - 1 <= 0}">
                <a href="#" class="bt prev" onclick="alert('이전 페이지가 없습니다.');"><</a>
            </c:if>

            <c:forEach var="i" begin="0" end="4">
                <a href="?p=${startNum+i}&t=&q=" class="num">${startNum + i}</a>
            </c:forEach>

            <c:if test="${startNum + 5 < lastNum}">
                <a href="?p=${startNum+5}&t=&q=" class="bt next">> </a>
            </c:if>
            <c:if test="${startNum + 5 >= lastNum}">
                <a href="#" class="bt next" onclick="alert('다음 페이지가 없습니다.');">> </a>
            </c:if>

        </div>
    </div>

    <%--include footer--%>
    <jsp:include page="../inner/footer.jsp" />
</div>
</body>
</html>
