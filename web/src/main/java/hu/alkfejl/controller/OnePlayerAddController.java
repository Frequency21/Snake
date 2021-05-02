package hu.alkfejl.controller;


import hu.alkfejl.model.PlayerModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/OnePlayerAddController")
public class OnePlayerAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PlayerModel player = new PlayerModel();

        req.setAttribute("player", player);

        req.getRequestDispatcher("pages/add-or-edit-one-player.jsp").forward(req, resp);
    }
}
