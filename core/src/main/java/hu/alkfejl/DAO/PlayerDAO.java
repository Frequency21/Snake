package hu.alkfejl.DAO;

import hu.alkfejl.model.PlayerModel;

import java.util.List;

public interface PlayerDAO {

    void save(PlayerModel player);

    PlayerModel get(String name);

    void update(String name);

    void delete(String name);

    List<PlayerModel> getAll();

}
