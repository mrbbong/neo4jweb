<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h2>Spring MVC and List Example</h2>

	<c:if test="${not empty ratingList}">

		<ul>
			<c:forEach var="listValue" items="${ratingList}">
				<li>${listValue}</li>
			</c:forEach>
		</ul>

	</c:if>
</body>
</html>