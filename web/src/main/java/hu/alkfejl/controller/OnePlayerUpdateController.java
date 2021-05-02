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


@WebServlet("/OnePlayerUpdateController")
public class OnePlayerUpdateController extends HttpServlet {
    private final PlayerDAO playerDAO = new SimplePlayerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("playerName");
        PlayerModel player = playerDAO.get(playerName);

        req.setAttribute("player", player);
        req.getRequestDispatcher("pages/add-or-edit-one-player.jsp").forward(req, resp);
    }
}
