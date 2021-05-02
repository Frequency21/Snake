package hu.alkfejl.controller;

import hu.alkfejl.DAO.MultiPlayerDAO;
import hu.alkfejl.DAO.SimpleMultiPlayerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/TwoPlayerDeleteController")
public class TwoPlayerDeleteController extends HttpServlet {
    MultiPlayerDAO mpDAO = new SimpleMultiPlayerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mpDAO.delete(req.getParameter("name_1"), req.getParameter("name_2"));
        resp.sendRedirect("toplist/two");
    }
}
