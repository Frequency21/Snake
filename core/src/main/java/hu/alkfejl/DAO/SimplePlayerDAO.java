package hu.alkfejl.DAO;

import hu.alkfejl.model.PlayerModel;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimplePlayerDAO implements PlayerDAO {

    private final String INSERT = "insert into one_player values(?, ?)";
    private final String UPDATE_SCORE = "update one_player set score = ? where name = ? and score < ?";
    private final String UPDATE = "update one_player set name = ?, score = ? where name = ?";
    private final String DELETE = "delete from one_player where name = ?";
    private final String SELECT_ALL = "select * from one_player";
    private final String SELECT_BY_NAME = "select name, score from one_player where name = ?";

    @Override
    public PlayerModel get(String name) {
        PlayerModel player = new PlayerModel();
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(SELECT_BY_NAME)
        ) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                player.setName(rs.getString(1));
                player.setScore(rs.getInt(2));
                return player;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(PlayerModel player) {
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(INSERT);
        ) {
            stmt.setString(1, player.getName());
            stmt.setInt(2, player.getScore());
            stmt.executeUpdate();
        } catch (SQLiteException e) {
            /* if player is already in db, then update his score */
            if (e.getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_PRIMARYKEY)
                update(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** update in game --> update score if player beats his record */
    private void update(PlayerModel player) {
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(UPDATE_SCORE);
        ) {
            stmt.setInt(1, player.getScore());
            stmt.setString(2, player.getName());
            stmt.setInt(3, player.getScore());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** update in top list --> name and score freely change */
    @Override
    public void update(PlayerModel player, String newName) {
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(UPDATE);
        ) {
            stmt.setString(1, newName);
            stmt.setInt(2, player.getScore());
            stmt.setString(3, player.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(PlayerModel player) {
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(DELETE);
        ) {
            stmt.setString(1, player.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String name) {
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(DELETE);
        ) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        List<PlayerModel> players = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            PlayerModel player;
            while (rs.next()) {
                player = new PlayerModel();
                player.setName(rs.getString("name"));
                player.setScore(rs.getInt("score"));
                players.add(player);
            }
            return players;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
