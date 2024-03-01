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
		width: 30%;
		height: 30%;
	}
</style>
</head>
<body>
	<h1>じゃんけんにようこそ！出す形をクリックしてください</h1>
	<form action="janken" method="post" name="test">
		<div>
			<button type="submit" name="paper">
				<img src="Images/paper.png">
			</button>
			<button type="submit" name="rock">
				<img src="Images/rock.png">
			</button>
			<button type="submit" name="scissors">
				<img src="Images/scissors.png">
			</button>
		</div>
	</form>
</body>
</html>