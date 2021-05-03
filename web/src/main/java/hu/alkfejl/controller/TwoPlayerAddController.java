package hu.alkfejl.controller;

import hu.alkfejl.DAO.MultiPlayerDAO;
import hu.alkfejl.DAO.SimpleMultiPlayerDAO;
import hu.alkfejl.model.PTuple;
import hu.alkfejl.model.PlayerModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/TwoPlayerAddController")
public class TwoPlayerAddController extends HttpServlet {
    MultiPlayerDAO mpDAO = new SimpleMultiPlayerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PTuple<PlayerModel, PlayerModel> playersTuple = new PTuple<>(new PlayerModel(), new PlayerModel());

        req.setAttribute("playersTuple", playersTuple);
        req.getRequestDispatcher("pages/add-or-edit-two-player.jsp").forward(req, resp);
    }
}
