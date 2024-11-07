<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Главная</title>
</head>
<body>
<div style= "display: flex; justify-content: center">
  <h1 style="display: inline-block">Проект теннисное табло</h1>
</div>

<div style="display: flex; justify-content: center">
  <a href="${pageContext.request.contextPath}/new-match" style="display: inline-block">
    <button>Начать новый матч</button>
  </a>
</div>

<div style="display: flex; justify-content: center">
  <a href="${pageContext.request.contextPath}/matches" style="display: inline-block">
    <button>Завершённые матчи</button>
  </a>
</div>
</body>
</html>