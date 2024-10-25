package match.activeMatch;

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
    private PlayerService playerService;
    private FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init() throws ServletException {
        super.init();
        ongoingMatchesService = OngoingMatchesService.getInstance();
        matchScoreCalculationService = MatchScoreCalculationService.getInstance();
        playerService = PlayerService.getInstance();
        finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidString = req.getParameter("uuid");
        ActiveMatchDto activeMatch = ongoingMatchesService.getActiveMatch(UUID.fromString(uuidString)).orElseThrow(() -> new RuntimeException("Матча с таким uuid не существует"));
        req.setAttribute("activeMatch", activeMatch);
        req.setAttribute("player1", playerService.findByID(activeMatch.getPlayer1Id()).get());
        req.setAttribute("player2", playerService.findByID(activeMatch.getPlayer2Id()).get());
        req.getRequestDispatcher(JspPathHelper.getPath("activeMatch")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidString = req.getParameter("uuid");
        ActiveMatchDto activeMatch = ongoingMatchesService.getActiveMatch(UUID.fromString(uuidString)).get();
        int winnerPointId = Integer.parseInt(req.getParameter("winnerPointId"));
        matchScoreCalculationService.addPoint(activeMatch, winnerPointId);
        if (finishedMatchesPersistenceService.checkOrSave(activeMatch)) {
            ongoingMatchesService.deleteActiveMatch(UUID.fromString(uuidString));
            resp.sendRedirect(req.getContextPath() + "/matches?page=1&&filter_by_player_name="
                    + playerService.findByID(winnerPointId).get().getName());
        } else {
            req.setAttribute("activeMatch", activeMatch);
            req.setAttribute("player1", playerService.findByID(activeMatch.getPlayer1Id()).get());
            req.setAttribute("player2", playerService.findByID(activeMatch.getPlayer2Id()).get());
            req.getRequestDispatcher(JspPathHelper.getPath("activeMatch")).forward(req, resp);
        }
    }
}
