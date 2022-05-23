<%@page import="jp.co.seattle.library.dto.HistoryInfo"%>
<%@page import="jp.co.seattle.library.controller.HistoryController"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
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
        <h1>書籍貸出履歴</h1>
        <div class="contaeiner">
            <div class="row">
                <div class="col-md-8 offset-md-2">
                    <table class="table table-bordered">
                        <tr class="table-info">
                            <th class="">書籍名</th>
                            <th class="">貸出日</th>
                            <th class="">返却日</th>
                        </tr>
                        <c:forEach var="historyInfo" items="${bookHistory}">
                            <tr class="">
                                <td class="">
                                    <form method="post" class="" action="<%=request.getContextPath()%>/details">
                                        <a href="javascript:void(0)" onclick="this.parentNode.submit();">${historyInfo.title}</a><input type="hidden" name="bookId" value="${historyInfo.bookId}">
                                    </form>
                                </td>
                                <td class="">${historyInfo.reg}</td>
                                <td class="">${historyInfo.upd}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </main>
</body>
</html>