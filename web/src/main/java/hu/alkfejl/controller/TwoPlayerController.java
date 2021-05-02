package hu.alkfejl.controller;

import hu.alkfejl.DAO.MultiPlayerDAO;
import hu.alkfejl.DAO.SimpleMultiPlayerDAO;
import hu.alkfejl.model.PTuple;
import hu.alkfejl.model.PlayerModel;
import hu.alkfejl.model.Tuple;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/TwoPlayerController")
public class TwoPlayerController extends HttpServlet {
    private MultiPlayerDAO mpDAO = new SimpleMultiPlayerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String oldName_1 = req.getParameter("oldName_1");
        String oldName_2 = req.getParameter("oldName_2");
        String name_1 = req.getParameter("name_1");
        String name_2 = req.getParameter("name_2");
        String score_1 = req.getParameter("score_1");
        String score_2 = req.getParameter("score_2");

        PTuple<PlayerModel, PlayerModel> playersTuple = new PTuple<>(new PlayerModel(), new PlayerModel());
        playersTuple.getFirst().setScore(Integer.parseInt(score_1));
        playersTuple.getSecond().setScore(Integer.parseInt(score_2));

        /* save */
        if ( oldName_1 == null || oldName_2 == null || oldName_1.isEmpty() || oldName_2.isEmpty()) {
            playersTuple.getFirst().setName(name_1);
            playersTuple.getSecond().setName(name_2);
            mpDAO.save(playersTuple);
        } else {    // update
            playersTuple.getFirst().setName(oldName_1);
            playersTuple.getSecond().setName(oldName_2);
            mpDAO.update(playersTuple, new Tuple<>(name_1, name_2));
        }

        resp.sendRedirect("toplist/two");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PTuple<PlayerModel, PlayerModel>> playersTuples = mpDAO.getAll();
        req.setAttribute("playersTuples", playersTuples);
    }
}
