<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Новый матч</title>
</head>
<body>
<c:if test="${requestScope.validationResult != null && !requestScope.validationResult.isValid()}">
  <div style="display: flex; flex-direction: column; align-items: center; color: red">
    <c:forEach items="${requestScope.validationResult.errors}" var="error">
      <span>${error.code} ${error.message}</span>
    </c:forEach>
  </div>
</c:if>
<div style="display: flex; justify-content: center">
  <form action="${pageContext.request.contextPath}/new-match"
        method="post"
        enctype="application/x-www-form-urlencoded">
    <div>
      <label for="player1Id">Имя игрока 1:
        <input id="player1Id" name="player1_name" type="text" required>
      </label>
    </div>
    <div>
      <label for="player2Id">Имя игрока 2:
        <input id="player2Id" name="player2_name" type="text" required>
      </label>
    </div>
    <button type="submit">Начать</button>

  </form>
</div>

</body>
</html>
