<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="game.Room"%>
<%@ page import="game.Player"%>
<%@ page import="game.GameManager"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="notificationSocket.js"></script>
</head>
<body>

	<button onclick="startGame()">Start Game</button>

	<p id="demo"></p>

	<script>
		//var wsUri = "ws://" + document.location.host + document.location.pathname + "websocket";

		var websocket = new WebSocket(
				"ws://localhost:8888/OOPFinalProject/PublishRoom");
		websocket.onmessage = function(evt) {
			updatePage(evt)
		};

		function updatePage(evt) {

			var json = JSON.parse(evt.data);

			if (json.type === "playersList") {
				document.getElementById("demo").innerHTML = json.players;
			} else if (json.type === "start") {
				if (json.admin) {
					if (json.forward) {
						location.replace("http://localhost:8888/OOPFinalProject/client.html");
					}
				} else {
					alert("ar xar adzmini dzmao");
				}
			}
		}

		function startGame() {
			var json = JSON.stringify({
				"type" : "start",
				"forward" : false
			});
			websocket.send(json);
		}
	</script>
</body>
</html>