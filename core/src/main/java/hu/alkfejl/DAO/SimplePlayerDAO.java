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
    public int getScore(PlayerModel player) {
        String SELECT = "select score from one_player where name = ?";
        int score = 0;

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(SELECT);
        ) {
            stmt.setString(1, player.getName());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) score = rs.getInt("score");

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return score;
    }

    /**
     * Player record updated if and only if
     * he beats his last score or if its a new record
     */
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
            if (e.getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_PRIMARYKEY)
                update(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(PlayerModel player1, PlayerModel player2) {
        String INSERT = "insert into two_player(name_1, score_1, name_2, score_2) values(?, ?, ?, ?)";
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(INSERT);
        ) {
            stmt.setString(1, player1.getName());
            stmt.setInt(2, player1.getScore());
            stmt.setString(3, player2.getName());
            stmt.setInt(4, player2.getScore());
            stmt.executeUpdate();
        } catch (SQLiteException e) {
            if (e.getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_PRIMARYKEY)
                update(player1, player2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void update(PlayerModel player1, PlayerModel player2) {
        String UPDATE = "update two_player set score_1 = ?, score_2 = ?" +
                " where name_1 = ? and name_2 = ? and (score_1 < ? or score_2 < ?)";
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(UPDATE);
        ) {
            stmt.setInt(1, player1.getScore());
            stmt.setInt(2, player2.getScore());
            stmt.setString(3, player1.getName());
            stmt.setString(4, player2.getName());
            stmt.setInt(5, player1.getScore());
            stmt.setInt(6, player2.getScore());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void update(PlayerModel player) {
        String UPDATE = "update one_player set score = ? where name = ? and score < ?";
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(UPDATE);
        ) {
            stmt.setInt(1, player.getScore());
            stmt.setString(2, player.getName());
            stmt.setInt(3, player.getScore());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String name) {
        String DELETE = "delete from one_player where name = ?";
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
