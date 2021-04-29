package hu.alkfejl.DAO;

import hu.alkfejl.model.PlayerModel;

import java.util.List;

public interface PlayerDAO {

    int getScore(PlayerModel player);

    void save(PlayerModel player);

    void save(PlayerModel player1, PlayerModel player2);

    void delete(String name);

    List<PlayerModel> getAll();

}
