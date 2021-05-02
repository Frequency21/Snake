package hu.alkfejl.DAO;

import hu.alkfejl.model.PlayerModel;

import java.util.List;

public interface PlayerDAO {

    PlayerModel get(String name);

    void save(PlayerModel player);

    void update(PlayerModel player, String newName);

    void delete(PlayerModel player);

    void delete(String name);

    List<PlayerModel> getAll();

}
