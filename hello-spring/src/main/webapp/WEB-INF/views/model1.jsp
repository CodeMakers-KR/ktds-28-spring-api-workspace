<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList" %>

<% 
List<String> list = new ArrayList<>(); 
list.add("str");
String name = "aaa";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%=name%>
    <% for(String s : list) { 
    // Scriptlet
    %>
    <%=s%>
    <% } %>
</body>
</html>