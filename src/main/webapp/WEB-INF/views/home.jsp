<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/search.js"></script>
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>Home</h1>
        <form method="post" class="search" action="<%=request.getContextPath()%>/search">
            <div class="addBotton">
                <a href="<%=request.getContextPath()%>/addBook" class="btn_add_book">書籍の追加</a> <a href="<%=request.getContextPath()%>/bulkRegist" class="btn_bulk_book">一括追加</a><a href="<%=request.getContextPath()%>/history" class="btn_history">貸出履歴一覧</a>
            </div>
            <p class="radioBotton">
                <input type="radio" name="radiobutton" value="1" checked="checked">完全一致 <input type="radio" name="radiobutton" value="2">部分一致
            </p>
            <p class="searchBotton">
                <input id="inputBox" class="input" type="search" name="search" placeholder="キーワードを入力"> <input id="inputBotton" class="button" type="submit" name="submit" value="検索" style="width: 70px; background: #7fbfff; color: #fff; font-size: 16px">
            </p>
        </form>
        <c:if test="${!empty select}">
            <div class="error">${select}</div>
        </c:if>
        <c:if test="${empty bookList}">
            <div class="error">書籍がありません</div>
        </c:if>
        <div class="content_body">
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
            <div>
                <div class="booklist">
                    <c:forEach var="bookInfo" items="${bookList}">
                        <div class="books">
                            <form method="post" class="book_thumnail" action="<%=request.getContextPath()%>/details">
                                <a href="javascript:void(0)" onclick="this.parentNode.submit();"> <c:if test="${bookInfo.thumbnail == 'null'}">
                                        <img class="book_noimg" src="resources/img/noImg.png">
                                    </c:if> <c:if test="${bookInfo.thumbnail != 'null'}">
                                        <img class="book_noimg" src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </form>
                            <ul>
                                <li class="book_title">${bookInfo.title}</li>
                                <li class="book_author">${bookInfo.author}（著）</li>
                                <li class="book_publisher">出版社：${bookInfo.publisher}</li>
                                <li class="book_publish_Date">出版日：${bookInfo.publishDate}</li>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
