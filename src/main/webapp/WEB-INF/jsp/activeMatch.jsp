<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Активный матч</title>
</head>
<body>

<table>
  <thead>
  <tr>
    <th>Игроки</th>
    <th>Сет</th>
    <th>Гейм</th>
    <th>Очки</th>
  </tr>
  </thead>
  <tbody>
    <tr>
      <td>${requestScope.player1.name}</td>
      <td>${requestScope.activeMatch.player1Set}</td>
      <td>${requestScope.activeMatch.player1Game}</td>
      <td>${requestScope.activeMatch.player1Points}</td>
      <td>
        <form action="${pageContext.request.contextPath}/match-score"
              method="post"
              enctype="application/x-www-form-urlencoded">
          <input name="winnerPointId" value="${requestScope.activeMatch.player1Id}" type="hidden">
          <input name="uuid" value="${requestScope.activeMatch.uuid}" type="hidden">
          <button type="submit">ОЧКО</button>
        </form>
      </td>
    </tr>
    <tr>
      <td>${requestScope.player2.name}</td>
      <td>${requestScope.activeMatch.player2Set}</td>
      <td>${requestScope.activeMatch.player2Game}</td>
      <td>${requestScope.activeMatch.player2Points}</td>
      <td>
        <form action="${pageContext.request.contextPath}/match-score"
              method="post"
              enctype="application/x-www-form-urlencoded">
          <input name="winnerPointId" value="${requestScope.activeMatch.player2Id}" type="hidden">
          <input name="uuid" value="${requestScope.activeMatch.uuid}" type="hidden">
          <button type="submit">ОЧКО</button>
        </form>
      </td>
    </tr>
  </tbody>
</table>

</body>
</html>
