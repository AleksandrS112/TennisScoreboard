package match.matches;

import exceptions.RespException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import match.MatchDto;
import util.JspPathHelper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/matches")
public class matchesController extends HttpServlet {

    static private final int MINIMUM_POSSIBLE_PAGE_NUMBER = 1;
    static private final int FIRST = 1;
    private static final int BASE_PAGE_SIZE = 10; //для гибкости пусть будет пусть будет в контролере

    private static final MatchesService matchService = MatchesService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String playerNameToFilterParam = req.getParameter("filter_by_player_name");
            int pageNumber = validationPageNumber(req.getParameter("page"));
            int numberOfPages = matchService.getNumberOfPages(BASE_PAGE_SIZE, Optional.ofNullable(playerNameToFilterParam));
            req.setAttribute("numberOfPages", numberOfPages);
            if (numberOfPages == 0)
                req.getRequestDispatcher(JspPathHelper.getPath("matchesPage")).forward(req, resp);
            if (pageNumber > numberOfPages)
                throw new RespException("400", "Указанный номер страницы превышает возможный.");
            List<MatchDto> matches = matchService.getMatchesPage(pageNumber, BASE_PAGE_SIZE, Optional.ofNullable(playerNameToFilterParam));
            req.setAttribute("playerNameToFilterParam", playerNameToFilterParam);
            req.setAttribute("pageNumber", pageNumber);
            req.setAttribute("matches", matches);
            req.getRequestDispatcher(JspPathHelper.getPath("matchesPage")).forward(req, resp);

        } catch (RespException respException) {
            req.setAttribute("respException", respException);
            req.getRequestDispatcher(req.getRequestURI()).forward(req, resp);
        } catch (Throwable e) {
            req.setAttribute("respException", new RespException("500", "Неизвестная ошибка"));
            req.getRequestDispatcher(req.getRequestURI()).forward(req, resp);
        }
    }

    private int validationPageNumber(String pageNumberParam) throws RespException {
        int pageNumber;
        if (pageNumberParam == null || pageNumberParam.isBlank()) {
            pageNumber = FIRST;
            return pageNumber;
        } else {
            try {
                pageNumber = Integer.parseInt(pageNumberParam);
            } catch (NumberFormatException e) {
                throw new RespException("400", "Указан неверный формат номера страницы");
            }
            if (pageNumber < MINIMUM_POSSIBLE_PAGE_NUMBER)
                throw new RespException("400", "Указанный номер страницы меньше 1.");
        }
        return pageNumber;
    }

}
