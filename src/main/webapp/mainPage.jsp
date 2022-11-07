<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book 24</title>
    <link rel="stylesheet" href="css/mainPage.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
<body>
<div id="container">
    <header>
        <div id="logo">
            <a href="mainPage.jsp">
                <h1>Book 24</h1>
            </a>
        </div>
        <nav>
            <ul id="topMenu">
                <li><a href="#">도서 보기 <span>▼</span></a>
                </li>
                <li><a href="#">공지사항</a></li>
                <li><a href="#">게시판</a></li>
                <li><a href="#">장바구니</a></li>
            </ul>
        </nav>
        <div class="my-page">
            <a href="#">
                마이페이지
            </a>
        </div>
    </header>

    <div class="middle-wrap">
        <div class="picture">
            <img src="./assets/bookstore.png">
        </div>

    </div>

    <div id="contents">
        <div id="tabMenu">
            <input type="radio" id="tab1" name="tabs" checked>
            <label for="tab1">공지사항</label>
            <input type="radio" id="tab2" name="tabs">
            <label for="tab2">게시판</label>
            <div id="notice" class="tabContent">
                <ul>
                    <li><a href="#">09/06 추석 기간 배송은 마감되었습니다.</a></li>
                    <li><a href="#">08/22 새로운 도서 입고 알림</a></li>
                    <li><a href="#">08/01 새로운 도서 입고 알림</a></li>
                </ul>
            </div>
            <div id="bulletin-board" class="tabContent">
                <ul>
                    <li><a href="#">[배송] 수령예상일이 지났는데 아직 못 받았습니다.</a></li>
                    <li><a href="#">[상품] 품절, 절판 상품은 구입할 수 없습니까?</a></li>
                    <li><a href="#">[교환] 다른 상품으로 교환이 가능한가요?</a></li>
                </ul>
            </div>
        </div>

        <div id="links">
            <ul>
                <li><a href="#">
                        <span id="quick-icon1"><i class="fas fa-envelope"></i></span>
                        <p>문의하기</p>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span id="quick-icon2"><i class="fas fa-couch"></i></span>
                        <p>쉬어가기</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>

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
