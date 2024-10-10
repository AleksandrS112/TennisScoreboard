package servlets;

import dao.PlayerDao;
import dto.ActiveMatch;
import entity.PlayerEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.OngoingMatchesService;
import util.JspPathHelper;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/new-match")
public class newMatch extends HttpServlet {
    private PlayerDao playerDao;
    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init() throws ServletException {
        super.init();
        playerDao = PlayerDao.getInstance();
        ongoingMatchesService = OngoingMatchesService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspPathHelper.getPath("newMatchPage")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player1Name = req.getParameter("player1_name");
        Optional<PlayerEntity> player1EntityOptional = playerDao.findByName(player1Name);
        PlayerEntity player1Entity;
        if(player1EntityOptional.isEmpty()) {
            PlayerEntity mewPlayer = PlayerEntity.builder().name(player1Name).build();
            playerDao.save(mewPlayer);
            player1Entity = playerDao.findByName(player1Name).get();
        } else {
            player1Entity = player1EntityOptional.get();
        }

        String player2Name = req.getParameter("player2_name");
        if (player1Name.equals(player2Name))
            throw new RuntimeException("Игрок не может играть сам с собой");
        Optional<PlayerEntity> player2EntityOptional = playerDao.findByName(player2Name);
        PlayerEntity player2Entity;
        if(player2EntityOptional.isEmpty()) {
            PlayerEntity mewPlayer = PlayerEntity.builder().name(player2Name).build();
            playerDao.save(mewPlayer);
            player2Entity = playerDao.findByName(player2Name).get();
        } else {
            player2Entity = player2EntityOptional.get();
        }
        ActiveMatch newActiveMatch = new ActiveMatch(player1Entity.getId(), player2Entity.getId());
        ongoingMatchesService.addActiveMatch(newActiveMatch.getUuid(), newActiveMatch);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + newActiveMatch.getUuid().toString());
    }
}
