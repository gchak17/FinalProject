<%@ page import="dao.Account"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="notificationSocket.js"></script>
</head>
<body>

	<%
		Account acc = (Account) request.getSession().getAttribute("user");
		if (acc == null) {
			response.sendRedirect("login.jsp");
			return;
		}
	%>

 <form action="PublishRoom" method="post">
 
 
	Rounds:
	<select name = "Rounds">	 
			  <option value="1">1</option>
			  <option value="3">3</option>
			  <option value="4">4</option>
			  <option value="5">5</option>
			  <option value="6">6</option>
			  <option value="7">7</option>
			  <option value="8">8</option>
			  <option value="9">9</option>
			  <option value="10">10</option>
	</select> <br><br>
	 
 	Round Duration in seconds:
	<select name = "time">	 
			  <option value="5">5</option>
			  <option value="15">15</option>
			  <option value="30">30</option>
			  <option value="40">40</option>
			  <option value="60">60</option>
			  <option value="100">100</option>
			  <option value="120">120</option>
	</select> <br><br>
	
	Max Players: 
		<select name = "MaxPlayers">	 
			  <option value="3">3</option>
			  <option value="4">4</option>
			  <option value="5">5</option>
			  <option value="6">6</option>
			  <option value="7">7</option>
			  <option value="8">8</option>
			  <option value="9">9</option>
			  <option value="10">10</option>
	</select> <br><br>
	
		<input type="submit" value="Publish Room">
	</form>

  
</body>
</html>