<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rock Scissor Paper!</title>
<style>
	* {
		background-color: black;
		text-align: center;
	}
	h1 {
		color: green;
	}
	button {
		text-align: center;
		color: green;
		margin-top: 20px;
		font-size: 20px;
		width: 220px;
		height: 220px;
	}
	img {
		width: 200px;
		height: 200px;
	}
	input {
		color: blue;
		border: 2px solid blue;
		font-size: 15px;
	}
	p {
		color: green;
	}
</style>
<script>
	function selectJanken(janken) {
	    document.getElementById("selectedJanken").value = janken;
	    document.getElementById("janken").submit();
	}
</script>
</head>
<body>
	<h1>じゃんけんにようこそ！出す形をクリックしてください</h1>
	<form action="janken" method="post" name="test">
		<input type="hidden" id="selectedJanken" name="selectedJanken" value="">
		<p> ボットの数</p>
		<input type="number" name="numBot" value="1">
		<div>
			<button type="submit" name="paper" onclick="selectJanken('paper')">
				<img src="Images/paper.png">
			</button>
			<button type="submit" name="rock" onclick="selectJanken('rock')">
				<img src="Images/rock.png">
			</button>
			<button type="submit" name="scissors" onclick="selectJanken('scissors')"> 
				<img src="Images/scissors.png">
			</button>
		</div>
	</form>
</body>
</html>