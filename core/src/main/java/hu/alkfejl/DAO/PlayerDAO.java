package hu.alkfejl.DAO;

import hu.alkfejl.model.PlayerModel;

import java.util.List;

public interface PlayerDAO {

    void save(PlayerModel player);

    void update(PlayerModel player, String newName);

    void delete(PlayerModel player);

    List<PlayerModel> getAll();

}
