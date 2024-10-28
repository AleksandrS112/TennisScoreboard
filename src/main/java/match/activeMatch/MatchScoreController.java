package match.activeMatch;

import exceptions.RespException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import player.PlayerService;
import util.JspPathHelper;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreController extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreCalculationService matchScoreCalculationService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init() throws ServletException {
        super.init();
        ongoingMatchesService = OngoingMatchesService.getInstance();
        matchScoreCalculationService = MatchScoreCalculationService.getInstance();
        finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UUID uuid = validationUuid(req.getParameter("uuid"));
            ActiveMatch activeMatch = ongoingMatchesService.getActiveMatch(uuid)
                    .orElseThrow(() -> new RespException("400", "Матча с таким uuid не существует."));
            req.setAttribute("activeMatch", activeMatch);
            req.getRequestDispatcher(JspPathHelper.getPath("activeMatchPage")).forward(req, resp);
        } catch (RespException respException) {
            handleException(req, resp, respException);
        } catch (Throwable throwable) {
            handleException(req, resp, new RespException("500", "Неизвестная ошибка."));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ActiveMatch activeMatch = null;
        try {
            UUID uuid = validationUuid(req.getParameter("uuid"));
            activeMatch = ongoingMatchesService.getActiveMatch(uuid)
                    .orElseThrow(() -> new RespException("400", "Матча с таким uuid не существует."));
            PlayerNumber playerNumber = validationPlayerNumber(req.getParameter("winningPointPlayerNumber"));
            matchScoreCalculationService.addPoint(activeMatch, playerNumber);
            MatchStatus matchStatus = finishedMatchesPersistenceService.getStatus(activeMatch);
            if(matchStatus == MatchStatus.COMPLETED) {
                resp.sendRedirect(req.getContextPath() + "/matches?page=1&&filter_by_player_name="
                        + activeMatch.getPlayer1Score().getPlayer().getName());
            } else if (matchStatus == MatchStatus.ACTIVE) {
                req.setAttribute("activeMatch", activeMatch);
                req.getRequestDispatcher(JspPathHelper.getPath("activeMatchPage")).forward(req, resp);
            }

        } catch (RespException respException) {
            handleException(req, resp, respException);
        } catch (Throwable throwable) {
            handleException(req, resp, new RespException("500", "Неизвестная ошибка."));
        }
    }

    private PlayerNumber validationPlayerNumber(String playerNumberParam) throws RespException {
        try {
            if (playerNumberParam == null || playerNumberParam.isBlank())
                throw new RespException("404", "Не указан норме игрока выйгравшего очко");
            int playerNumber = Integer.parseInt(playerNumberParam);
            if(playerNumber == 1)
                return PlayerNumber.ONE;
            if (playerNumber == 2)
                return  PlayerNumber.TWO;
            throw new RespException("400", "Не верно указан номер игрока.");
        } catch (NumberFormatException e) {
            throw new RespException("400", "Не валидный номер игрока.");
        }
    }

    private UUID validationUuid(String uuidParam) throws RespException {
        try {
            if(uuidParam == null || uuidParam.isBlank())
                throw new RespException("404", "Отсутствует UUID.");
            return UUID.fromString(uuidParam);
        } catch (IllegalArgumentException e) {
            throw new RespException("400", "Указан невалидный UUID.");
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, RespException exception) throws ServletException, IOException {
        req.setAttribute("respException", exception);
        req.getRequestDispatcher(JspPathHelper.getPath("activeMatchPage")).forward(req, resp);
    }
}
