package servlets;

import dto.MatchDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MatchesService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/matches")
public class matches extends HttpServlet {

    private static final MatchesService matchService = MatchesService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageNumberParam = req.getParameter("page");
        int pageNumber = pageNumberParam == null ? 1 : Integer.parseInt(pageNumberParam);
        req.setAttribute("pageNumber", pageNumber);

        String playerNameToFilterParam = req.getParameter("filter_by_player_name");
        req.setAttribute("playerNameToFilterParam", playerNameToFilterParam);

        Optional<String> playerNameToFilter = playerNameToFilterParam == null || playerNameToFilterParam.isBlank()
                ? Optional.empty() : Optional.of(playerNameToFilterParam);
        List<MatchDto> matchesPage = matchService.getOnePageOfMatchesDto(pageNumber, playerNameToFilter);
        req.setAttribute("matchesPage", matchesPage);
        req.setAttribute("numberOfPages", matchService.getNumberOfPages(playerNameToFilter));

        req.getRequestDispatcher("/WEB-INF/jsp/matchesPage.jsp").forward(req, resp);
    }

}
