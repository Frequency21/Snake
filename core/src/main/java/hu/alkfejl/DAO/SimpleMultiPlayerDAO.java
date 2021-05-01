package hu.alkfejl.DAO;

import hu.alkfejl.model.PlayerModel;
import hu.alkfejl.model.Tuple;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleMultiPlayerDAO implements MultiPlayerDAO {

    private static final String SAVE_QUERY = "insert into two_player(name_1, score_1, name_2, score_2) values (?, ?, ?, ?)";
    private static final String SELECT_SCORES_QUERY = "select score_1, score_2 from two_player " +
            "where name_1 = ? and name_2 = ?";
    private static final String UPDATE_SCORES_QUERY = "update two_player set score_1 = ?, score_2 = ? " +
            "where name_1 = ? and name_2 = ?";
    private static final String UPDATE_QUERY = "update two_player set name_1 = ?, score_1 = ?, name_2 = ?, score_2 = ? " +
            "where name_1 = ? and name_2 = ?";
    private static final String DELETE_QUERY = "delete from two_player where name_1 = ? and name_2 = ?";
    private static final String SELECT_ALL_QUERY = "select * from two_player";

    /**
     * this method used in game (scores saved only, when top score has beaten)
     */
    @Override
    public void save(Tuple<PlayerModel, PlayerModel> players) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE_QUERY);
        ) {
            stmt.setString(1, players.getFirst().getName());
            stmt.setInt(2, players.getFirst().getScore());
            stmt.setString(3, players.getSecond().getName());
            stmt.setInt(4, players.getSecond().getScore());
            stmt.executeUpdate();
        } catch (SQLiteException e) {
            /* if they are in the db already update their scores */
            if (e.getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_PRIMARYKEY)
                update(players);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* update only scores (if max score beats old top score) */
    private void update(Tuple<PlayerModel, PlayerModel> players) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_SCORES_QUERY);
        ) {
            stmt.setString(1, players.getFirst().getName());
            stmt.setString(2, players.getSecond().getName());
            ResultSet rs = stmt.executeQuery();
            int score_1, score_2;
            /* this is not dangerous, we used names which cause primary key constraint exception in save */
            rs.next();
            score_1 = rs.getInt("score_1");
            score_2 = rs.getInt("score_2");
            rs.close();
            if (Math.max(players.getFirst().getScore(), players.getSecond().getScore()) < Math.max(score_1, score_2))
                return;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_SCORES_QUERY);
        ) {
            stmt.setInt(1, players.getFirst().getScore());
            stmt.setInt(2, players.getSecond().getScore());
            stmt.setString(3, players.getFirst().getName());
            stmt.setString(4, players.getSecond().getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method used in top list (names and scores freely change)
     *
     * @param newNames if null, then only scores are updated.
     */
    @Override
    public void update(Tuple<PlayerModel, PlayerModel> players, Tuple<String, String> newNames) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_QUERY);
        ) {
            stmt.setString(1, newNames.getFirst());
            stmt.setInt(2, players.getFirst().getScore());
            stmt.setString(3, newNames.getSecond());
            stmt.setInt(4, players.getSecond().getScore());
            stmt.setString(5, players.getFirst().getName());
            stmt.setString(6, players.getSecond().getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Tuple<PlayerModel, PlayerModel> players) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_QUERY);
        ) {
            stmt.setString(1, players.getFirst().getName());
            stmt.setString(2, players.getSecond().getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Tuple<PlayerModel, PlayerModel>> getAll() {
        List<Tuple<PlayerModel, PlayerModel>> playersTuple = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = stmt.executeQuery()) {
            Tuple<PlayerModel, PlayerModel> playerTuple;
            while (rs.next()) {
                playerTuple = new Tuple<>(new PlayerModel(), new PlayerModel());
                playerTuple.getFirst().setName(rs.getString("name_1"));
                playerTuple.getFirst().setScore(rs.getInt("score_1"));
                playerTuple.getSecond().setName(rs.getString("name_2"));
                playerTuple.getSecond().setScore(rs.getInt("score_2"));
                playersTuple.add(playerTuple);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playersTuple;
    }
}
