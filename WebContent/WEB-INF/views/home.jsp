<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Weather Search</h1>

<br>
<br>

	<form action="user" method="get">
		<input type="text" name="userName"><br> <br><input
			type="submit" value="Get Weather">
	</form>
	
	<br>
	
	<h3>${errorMessage}</h3>
</body>
</html>
