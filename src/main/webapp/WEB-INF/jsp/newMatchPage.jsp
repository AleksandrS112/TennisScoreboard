<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title>Новый матч</title>
  </head>
  <body>
    <c:if test="${!requestScope.validationResult.isValid()}">
      <c:forEach items="${requestScope.validationResult.errors}" var="error">
          <a>${error.code} ${error.message}</a><br>
      </c:forEach>
    </c:if>

    <form action="${pageContext.request.contextPath}/new-match"
          method="post"
          enctype="application/x-www-form-urlencoded">

      <label for="player1Id">Имя игрока 1:
        <input id="player1Id" name="player1_name" type="text" required>

      </label><br>
      <label for="player2Id">Имя игрока 2:
        <input id="player2Id" name="player2_name" type="text" required>

      </label><br>
      <button type="submit">Начать</button>
    </form>

  </body>
</html>
