package hu.alkfejl.DAO;

import hu.alkfejl.model.PTuple;
import hu.alkfejl.model.PlayerModel;
import hu.alkfejl.model.Tuple;

import java.util.List;

public interface MultiPlayerDAO {

    PTuple<PlayerModel, PlayerModel> get(String name1, String name2);

    void save(PTuple<PlayerModel, PlayerModel> players);

    void update(PTuple<PlayerModel, PlayerModel> players, Tuple<String, String> newNames);

    void delete(PTuple<PlayerModel, PlayerModel> players);

    void delete(String name1, String name2);

    List<PTuple<PlayerModel, PlayerModel>> getAll();

}
