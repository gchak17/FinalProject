<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Welcome</title>
</head>
<body>
	<h1>Welcome to Our Game!</h1>
	
	<p>Please log in.</p>
	
    <form action="LoginServlet" method="post">
		User Name: <input type="text" name="userName" required="required"><br>
		Password: <input type="password" name="password" required="required">
		<input type="submit" value="Login">
	</form>
    
    <a href="CreateAccount.jsp">Create new Account</a>
</body>
</html>