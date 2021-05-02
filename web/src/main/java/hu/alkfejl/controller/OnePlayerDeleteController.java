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


@WebServlet("/OnePlayerDeleteController")
public class OnePlayerDeleteController extends HttpServlet {
    PlayerDAO playerDAO = new SimplePlayerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        playerDAO.delete(req.getParameter("playerName"));
        resp.sendRedirect("toplist/one");
    }
}
