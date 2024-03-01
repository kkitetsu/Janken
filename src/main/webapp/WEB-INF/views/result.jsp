<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	.images {
		text-align: center;
	}
</style>
</head>
<body>
	<h1>結果は ${result} です！</h1>
	<div class="images">
		<img src="${userMove}">
		<img src="${botMove}">
	</div>
	<form action="janken" method="get">
		<button type="submit">もう一度遊ぶ</button>
	</form>
</body>
</html>