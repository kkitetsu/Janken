<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Result</title>
<style>
	* {
		background-color: black;
	}
	h1 {
		color: green;
		text-align: center;
	}
	form {
		text-align: center;
		margin-top: 20px;
	}
	button {
		color: green;
		font-size: 20px;
	}
	.userImg {
		border: 2px solid green;
	}
	.images {
		text-align: center;
	}
	img {
		width: 5%;
		height: 5%;
	}
</style>
</head>
<body>
	<h1>結果は ${result} です！</h1>
	<div class="images">
		<img class="userImg" src="${userMove}">
		<p><% request.getAttribute("botsMoveList"); %></p>
		<% ArrayList<String> imagePaths = (ArrayList<String>) request.getAttribute("botsMoveList"); 
		   for (String eachImg : imagePaths) { %>
			<img src="<%= eachImg %>">
		<% } %>
	</div>
	<form action="janken" method="get">
		<button type="submit">もう一度遊ぶ</button>
	</form>
</body>
</html>