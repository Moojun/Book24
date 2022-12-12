<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="userEmail" value="${sessionScope.userEmail}" />
<c:set var="userID" value="${sessionScope.userID}" />
<c:set var="interestList" value="${requestScope.interestList}" />
<c:set var="myPostList" value="${requestScope.myPostList}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Stock 24</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/myPage.css">
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
            <strong>마이페이지</strong>
            <p>내 정보</p>
            <table>
                <tr>
                    <th>닉네임</th>
                    <td><c:out value="${userID}" /> </td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td><c:out value="${userEmail}" /></td>
                </tr>
                <%--
                <tr>
                    <th>가입 일자 </th>
                    <td>2022.11.07</td>
                </tr>
                --%>
            </table>
        </div>
        <div class="main-myPage">
            <div class="left-myPage">
                <p>관심 목록</p>
                <table>
                    <thead>
                    <tr>
                        <th>명칭</th>
                        <th>시장 규모</th>
                        <th>등록 일자</th>
                        <th>Control</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="n" items="${interestList}">
                        <tr>
                            <td>${n.stockName}</td>
                            <td>${n.stockClass}</td>
                            <td>${n.date}</td>
                            <td>
                                <form method="post" action="../move/myPage">
                                    <button type="submit" class="view">보기</button>
                                        <%-- 주식 명칭을 담아서 보냄--%>
                                    <input type="hidden" name="stockName" value="${n.stockName}" />
                                </form>
                                <form method="post" action="../del/myPage">
                                    <button type="submit" class="delete">삭제하기</button>
                                        <%-- 주식 명칭을 담아서 보냄--%>
                                    <input type="hidden" name="stockName" value="${n.stockName}" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="5" class="tableFoot"></td>
                        </tr>
                    </tfoot>
                </table>
            </div>

            <div class="right-myPage">
                <p>내가 작성한 게시글</p>
                <table>
                    <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>조회수</th>
                        <th>Control</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="m" items="${myPostList}">
                        <tr>
                            <td>${m.id}</td>
                            <td>${m.title}</td>
                            <td>${m.hit}</td>
                            <td>
                                <button class="view">보기</button>
                                <button class="delete">삭제하기</button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <td colspan="5" class="tableFoot"></td>
                    </tfoot>
                </table>
            </div>

        </div>
    </div>

    <%--include footer--%>
    <jsp:include page="../inner/footer.jsp" />
</div>
</body>
</html>


