<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Insert title here</title>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>
<form action="registration" method="post">
  <%--@declare id="firstname"--%><label for="firstName">First Name:</label> <input name="firstName">
  <br>
    <%--@declare id="lastname"--%><label for="lastName">Last Name:</label> <input name="lastName">
  <br>
    <%--@declare id="email"--%><label for="email">Email:</label> <input name="email">
  <br>
  <%--@declare id="password"--%><label for="password">Password:</label> <input name="password">
  <br>
  <input type="submit" value="submit">

</form>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
