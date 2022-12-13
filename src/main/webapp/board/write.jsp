<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>Stock 24</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/write.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
<body>
<div class="board_wrap">
    <div class="board_title">
        <strong>게시판</strong>
        <p>게시글을 작성할 수 있습니다.</p>
    </div>
    <div class="board_write_wrap">
        <div class="board_write">
            <form method="post" action="writeAction.jsp">
            <div class="title">
                <dl>
                    <dt>제목</dt>
                    <dd><input type="text" name="writeTitle" placeholder="제목 입력"></dd>
                </dl>
            </div>
            <div class="info">
                <%--
                <dl>
                    <dt>글쓴이</dt>
                    <dd><input type="text" placeholder="글쓴이 입력"></dd>
                </dl>
                <dl>
                    <dt>비밀번호</dt>
                    <dd><input type="password" placeholder="비밀번호 입력"></dd>
                </dl>
                --%>
            </div>
            <div class="cont">
                <textarea name="writeContent" placeholder="내용 입력"></textarea>
            </div>
        <div class="bt_wrap">
            <button type="input" class="on">등록</button>
            <a href="list">취소</a>
        </div>
        </form>
    </div>
    </div>

    <%--include footer--%>
    <jsp:include page="../inner/footer.jsp" />
</div>
</body>
</html>