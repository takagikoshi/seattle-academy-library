<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>一括登録｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/thumbnail.js"></script>
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
    
        <div>
        <form action="<%=request.getContextPath()%>/bulkRegistBook" method="post" enctype="multipart/form-data" id="data_upload_form">
            <h1>一括登録</h1>
            <div class="bulk_form">
            <p class="caution">CSVファイルをアップロードすることで書籍を一括登録できます。</p>
             <div class="caution p">
            <p>「書籍名,著者名,出版日,出版社,ISBN」の形式で記載してください。</p>           
            <p>※サムネイル画像は一括登録できません。編集画面で一冊編集してください。</p>
            </div>
             <div>
              <div><input type="file" name = "file" accept=".csv"></div>
             </div>
            <div class="bulkBookBtn_box">
             <button type="submit" id="edit-btn" class="btn_bulkRegist">一括登録</button>
            </div> 
            <c:if test="${!empty emptyFail}">
                <div class="error"><p>${emptyFail}</p></div>
            </c:if>
            <c:if test="${!empty error}">
                <div class="error"><p>${error}</p></div>
            </c:if>
            <c:if test="${!empty countError}">
            <div class="error">
            <c:forEach var="countError" items="${countError}">
            <c:if test="${!empty countError}">
                <p>${countError}</p>
            </c:if>
            </c:forEach>
            </div> 
            </c:if>  
            </div> 
        </form>
        </div>
        
    </main>
</body>
</html>