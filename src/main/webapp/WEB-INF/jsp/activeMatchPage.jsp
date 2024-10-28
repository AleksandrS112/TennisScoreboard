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
      <td>${requestScope.activeMatch.player1Score.player.name}</td>
      <td>${requestScope.activeMatch.player1Score.set}</td>
      <td>${requestScope.activeMatch.player1Score.game}</td>
      <td>${requestScope.activeMatch.player1Score.points}</td>
      <td>
        <form action="${pageContext.request.contextPath}/match-score"
              method="post"
              enctype="application/x-www-form-urlencoded">
          <input name="winningPointPlayerNumber" value="${1}" type="hidden">
          <input name="uuid" value="${requestScope.activeMatch.uuid}" type="hidden">
          <button type="submit">ОЧКО</button>
        </form>
      </td>
    </tr>
    <tr>
      <td>${requestScope.activeMatch.player2Score.player.name}</td>
      <td>${requestScope.activeMatch.player2Score.set}</td>
      <td>${requestScope.activeMatch.player2Score.game}</td>
      <td>${requestScope.activeMatch.player2Score.points}</td>
      <td>
        <form action="${pageContext.request.contextPath}/match-score"
              method="post"
              enctype="application/x-www-form-urlencoded">
          <input name="winningPointPlayerNumber" value="${2}" type="hidden">
          <input name="uuid" value="${requestScope.activeMatch.uuid}" type="hidden">
          <button type="submit">ОЧКО</button>
        </form>
      </td>
    </tr>
  </tbody>
</table>

</body>
</html>
