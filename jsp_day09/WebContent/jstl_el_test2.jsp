<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL TEST2(제어문-조건식)</title>
</head>
<body>
	<c:set var="id" value= "admin"></c:set>
	<c:if test="${id=='admin'}">
		현재 로그인 된 계정은 <c:out value="${id}"></c:out>계정입니다.
	</c:if>
	<br>
	<c:if test ="${id eq 'member'}">
		현재 로그인된 계정은 일반 계정입니다.
	</c:if>
	<hr>
	<c:set var = "id">fsfs</c:set>
	choose문 사용(else if. else는 choose로 표현한다.)<br>
	<c:choose>
		<c:when test="${id =='admin'}">관리자</c:when>
		<c:when test="${id =='member'}">일반</c:when>
		<c:otherwise>비회원</c:otherwise>
	</c:choose>
</body>
</html>