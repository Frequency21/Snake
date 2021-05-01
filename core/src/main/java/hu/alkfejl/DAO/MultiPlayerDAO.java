package hu.alkfejl.DAO;

import hu.alkfejl.model.PlayerModel;
import hu.alkfejl.model.Tuple;

import java.util.List;

public interface MultiPlayerDAO {

    void save(Tuple<PlayerModel, PlayerModel> players);

    void update(Tuple<PlayerModel, PlayerModel> players, Tuple<String, String> newNames);

    void delete(Tuple<PlayerModel, PlayerModel> players);

    List<Tuple<PlayerModel, PlayerModel>> getAll();

}
