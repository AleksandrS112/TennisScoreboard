<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Главная</title>
</head>
<body>
<div style="justify-content: center">
  <h1>Проект теннисное табло</h1>
</div>

<div style="justify-content: center">
  <a href="${pageContext.request.contextPath}/new-match">
    <button>Начать новый матч</button>
  </a>
</div>

<div>
  <a href="${pageContext.request.contextPath}/matches">
    <button>Завершённые матчи</button>
  </a>
</div>
</body>
</html>