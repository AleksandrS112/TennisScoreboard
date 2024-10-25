package match.newMatch;

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
    private NewMatchDtoValidator newMatchDtoValidator;

    @Override
    public void init() throws ServletException {
        super.init();
        ongoingMatchesService = OngoingMatchesService.getInstance();
        newMatchDtoValidator = NewMatchDtoValidator.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspPathHelper.getPath("newMatchPage")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewMatchDto newMatchDto = new NewMatchDto(new PlayerDto(req.getParameter("player1_name")),
                new PlayerDto(req.getParameter("player2_name")));
        ValidationResult validationResult = newMatchDtoValidator.isValid(newMatchDto);
        if(!validationResult.isValid()) {
            req.setAttribute("validationResult", validationResult);
            req.getRequestDispatcher(JspPathHelper.getPath("newMatchPage")).forward(req, resp);
        }
     //   ongoingMatchesService.addActiveMatch(newActiveMatch.getUuid(), newActiveMatch);
   //     resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + newActiveMatch.getUuid().toString());

        }
    }
