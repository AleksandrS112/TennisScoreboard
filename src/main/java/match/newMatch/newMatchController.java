package match.newMatch;

import match.activeMatch.ActiveMatch;
import match.activeMatch.OngoingMatchesService;
import player.PlayerDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.JspPathHelper;
import commons.validation.ValidationResult;

import java.io.IOException;

@WebServlet("/new-match")
public class newMatchController extends HttpServlet {
    private OngoingMatchesService ongoingMatchesService;
    private NewMatchValidator newMatchDtoValidator;

    @Override
    public void init() throws ServletException {
        super.init();
        ongoingMatchesService = OngoingMatchesService.getInstance();
        newMatchDtoValidator = NewMatchValidator.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspPathHelper.getPath("newMatchPage")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewMatch newMatchDto = new NewMatch(new PlayerDto(req.getParameter("player1_name")),
                new PlayerDto(req.getParameter("player2_name")));
        ValidationResult validationResult = newMatchDtoValidator.isValid(newMatchDto);
        if(!validationResult.isValid()) {
            req.setAttribute("validationResult", validationResult);
            req.getRequestDispatcher(JspPathHelper.getPath("newMatchPage")).forward(req, resp);
        }
        ActiveMatch activeMatch = new ActiveMatch(newMatchDto);
        ongoingMatchesService.addActiveMatch(activeMatch);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + activeMatch.getUuid());
        }
    }
