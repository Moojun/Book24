<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Stock 24</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/detail.css">
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

        <div class="board_view_wrap">
            <div class="board_view">
                <div class="title">
                        ${n.title}
                </div>
                <div class="info">
                    <dl>
                        <dt>번호</dt>
                        <dd>${n.id}</dd>
                    </dl>
                    <dl>
                        <dt>작성자</dt>
                        <dd>${n.writerId}</dd>
                    </dl>
                    <dl>
                        <dt>작성일</dt>
                        <dd>${n.regDate}</dd>
                    </dl>
                    <dl>
                        <dt>조회수</dt>
                        <dd>${n.hit}</dd>
                    </dl>
                </div>
                <div class="cont">
                    ${n.content}
                </div>
            </div>
            <div class="bt_wrap">
                <a href="list" class="on">목록</a>
                <a href="#">수정</a>
            </div>
        </div>
    </div>

    <%--include footer--%>
    <jsp:include page="../inner/footer.jsp" />
</div>
</body>
</html>


