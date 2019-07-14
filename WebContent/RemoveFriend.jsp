<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.Account"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<%
		Account acc = (Account) request.getSession().getAttribute("user");
		if (acc == null) {
			response.sendRedirect("login.jsp");
			return;
		}
	%>
	<form action="BackToProfileServlet" method="post">
		<input type="submit" value="Back to profile">
	</form>

	<br>

	<script>
		var xhr = null;

		function removeFriend() {
			console.log("wow");
			try {
				xhr = new XMLHttpRequest();
			} catch (e) {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}

			if (xhr == null) {
				alert('ajax not supported');
				return;
			}

			var url = 'RemoveFriendServlet';

			xhr.onreadystatechange = handler;
			xhr.open('POST', url, true);
			var receiver = document.getElementById('friendusername').value;
			xhr.send('{"friendusername" :  "'
					+ document.getElementById('friendusername').value + '"}');
		};

		function handler() {
			if (xhr.readyState == 4) {
				if (xhr.status == 200) {
					console.log(xhr.responseText);
					var jsonResponse = JSON.parse(xhr.responseText);
					alert(jsonResponse.responseText);
					if (jsonResponse.success) {
						sendNotificationMessage(jsonResponse.notificationLog);
					}
				} else {
					alert('something went wrong');
				}
			}
		};
	</script>


	<form action="#" method="post" onsubmit="removeFriend(); return false;">
		<input id="friendusername" type="text" name="friendusername">
		<input type="submit" value="Remove Friend">
	</form>
</body>
</html>