package hu.alkfejl.controller;

import hu.alkfejl.DAO.MultiPlayerDAO;
import hu.alkfejl.DAO.SimpleMultiPlayerDAO;
import hu.alkfejl.model.PlayerModel;
import hu.alkfejl.model.PTuple;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/TwoPlayerUpdateController")
public class TwoPlayerUpdateController extends HttpServlet {
    MultiPlayerDAO mpDAO = new SimpleMultiPlayerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name_1 = req.getParameter("name_1");
        String name_2 = req.getParameter("name_2");
        PTuple<PlayerModel, PlayerModel> playersTuple = mpDAO.get(name_1, name_2);

        req.setAttribute("playersTuple", playersTuple);
        req.getRequestDispatcher("pages/add-or-edit-two-player.jsp").forward(req, resp);
    }
}
