<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>Матчи</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/matches" method="get">
  <label for="playerNameId">Имя:
    <input id="playerNameId" type="text" name="filter_by_player_name">
  </label>
  <button type="submit">Искать</button>
</form>

<table>
  <thead>
  <tr>
    <th>Игрок №1</th>
    <th>Игрок №2</th>
    <th>Победитель</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${requestScope.matchesPage}" var="match">
    <tr>
      <td>${match.player1.name}</td>
      <td>${match.player2.name}</td>
      <td>${match.winner.name}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<!-- Пагинация -->
<div>
  <!-- Назад, появляется после первой страницы -->
  <c:if test="${requestScope.pageNumber > 1}">
    <a href="${pageContext.request.contextPath}/matches?page=${requestScope.pageNumber - 1}&filter_by_player_name=${requestScope.playerNameToFilterParam}">Назад</a>
  </c:if>
  <!-- Первая страница, появляется когда её нельзя выбрать -->
  <c:if test="${requestScope.pageNumber > 5}">
    <a href="${pageContext.request.contextPath}/matches?page=1&filter_by_player_name=${requestScope.playerNameToFilterParam}">1..</a>
  </c:if>

  <!-- Предыдущие 4 страницы если есть -->
  <c:forEach begin="1" end="4" var="i">
    <c:if test="${requestScope.pageNumber-(5-i) < requestScope.pageNumber && requestScope.pageNumber-(5-i) > 0}">
      <a href="${pageContext.request.contextPath}/matches?page=${requestScope.pageNumber-(5-i)}&filter_by_player_name=${requestScope.playerNameToFilterParam}">${requestScope.pageNumber-(5-i)}</a>
    </c:if>
  </c:forEach>
  <!--Сама запрашиваемая страница без ссылки -->
  <a>${requestScope.pageNumber}</a>
  <!-- Следующие 4 страницы если есть -->
  <c:forEach begin="1" end="4" var="i">
    <c:if test="${requestScope.pageNumber + i <= requestScope.numberOfPages}">
      <a href="${pageContext.request.contextPath}/matches?page=${requestScope.pageNumber + i}&filter_by_player_name=${requestScope.playerNameToFilterParam}">${requestScope.pageNumber + i}</a>
    </c:if>
  </c:forEach>

  <!-- Последняя страница, исчезает когда её можно выбрать -->
  <c:if test="${requestScope.pageNumber + 4 < requestScope.numberOfPages}">
    <a href="${pageContext.request.contextPath}/matches?page=${requestScope.numberOfPages}&filter_by_player_name=${requestScope.playerNameToFilterParam}">..${requestScope.numberOfPages}</a>
  </c:if>
  <!-- Вперед, убирается на последней странице -->
  <c:if test="${requestScope.pageNumber < requestScope.numberOfPages}">
    <a href="${pageContext.request.contextPath}/matches?page=${requestScope.pageNumber + 1}&filter_by_player_name=${requestScope.playerNameToFilterParam}">Вперед</a>
  </c:if>
</div>

</body>
</html>