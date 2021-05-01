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

    @Override
    public void save(PlayerModel player) {
        String INSERT = "insert into one_player values(?, ?)";
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
        String UPDATE_SCORE = "update one_player set score = ? where name = ? and score < ?";
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
        String UPDATE = "update one_player set name = ?, score = ? where name = ?";
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
        String DELETE = "delete from one_player where name = ?";
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
    public List<PlayerModel> getAll() {
        String sqlQuery = "select * from one_player";
        List<PlayerModel> players = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sqlQuery);
             ResultSet rs = stmt.executeQuery()) {
            PlayerModel player;
            while (rs.next()) {
                player = new PlayerModel();
                player.setName(rs.getString("name"));
                player.setScore(rs.getInt("score"));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

}
