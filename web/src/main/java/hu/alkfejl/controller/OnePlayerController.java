package hu.alkfejl.controller;

import hu.alkfejl.DAO.PlayerDAO;
import hu.alkfejl.DAO.SimplePlayerDAO;
import hu.alkfejl.model.PlayerModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/OnePlayerController")
public class OnePlayerController extends HttpServlet {
    private final PlayerDAO playerDAO = new SimplePlayerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String oldName = req.getParameter("oldName");
        String name = req.getParameter("name");
        String score = req.getParameter("score");

        PlayerModel player = new PlayerModel();
        player.setScore(Integer.parseInt(score));

        /* save */
        if (oldName == null || oldName.isEmpty()) {
            player.setName(name);
            playerDAO.save(player);
        } else {    // update
            player.setName(oldName);
            playerDAO.update(player, name);
        }

        resp.sendRedirect("toplist/one");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PlayerModel> players =playerDAO.getAll();
        req.setAttribute("players", players);
    }
}
