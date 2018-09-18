package com.alavel.jdbcbox.game;

import com.alavel.jdbcbox.common.Repository;
import com.alavel.jdbcbox.common.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class GameRepository implements Repository<Game, Long> {

    private final Supplier<Connection> connectionSupplier;

    public GameRepository(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }


    @Override
    public void save(Game entity) throws StorageException {
        Long id = entity.getId();
        Long score = entity.getScore();
        Long id_winner = entity.getWinnerId();
        List<Long> players = entity.getPlayers();
        String sql = "INSERT INTO games (id_winner, score) VALUES (?, ?)";
        String updateSQL = "UPDATE players SET score = score + ? WHERE id = ?";
        String participantsSQL = "INSERT INTO participants (id_p, id_g) VALUES (?, ?)";
        try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql);
             PreparedStatement scoreStatement = connectionSupplier.get().prepareStatement(updateSQL);
             PreparedStatement participantsStatement = connectionSupplier.get().prepareStatement(participantsSQL)) {
            statement.setLong(1, id_winner);
            statement.setLong(2, score);
            statement.executeUpdate();
            scoreStatement.setLong(1, score);
            scoreStatement.setLong(2, id_winner);
            statement.executeUpdate();
            for(Long player : players){
                participantsStatement.setLong(1, player);
                participantsStatement.setLong(2, id);
                participantsStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Game> list() throws StorageException {
        String sql = "SELECT id, id_winner, score, id_p FROM games INNER JOIN participants on games.id = participants.id_g ORDER BY id";
        try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Game> games = new LinkedList<>();
            Long currentId = null;
            Long winner;
            List<Long> players = null;
            Long score;
            while (resultSet.next()) {

                Long id = resultSet.getLong("id");
                if (!id.equals(currentId)) {
                    currentId = id;
                    players = new LinkedList<>();
                    score = resultSet.getLong("score");
                    winner = resultSet.getLong("id_winner");
                    games.add(new Game(currentId, winner, score, players));
                }
                players.add(resultSet.getLong("id_p"));

            }
            return games;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Game get(Long aLong) throws StorageException {
        String sql = "SELECT id, id_winner, score, id_p FROM games INNER JOIN participants on games.id = participants.id_g WHERE id = ? ORDER BY id";
        try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql)) {
            statement.setLong(1, aLong);
            ResultSet resultSet = statement.executeQuery();
            Game game = null;
            Long currentId = null;
            Long winner;
            List<Long> players = null;
            Long score;
            while (resultSet.next()) {

                Long id = resultSet.getLong("id");
                if (!id.equals(currentId)) {
                    currentId = id;
                    players = new LinkedList<>();
                    score = resultSet.getLong("score");
                    winner = resultSet.getLong("id_winner");
                    game =  new Game(currentId, winner, score, players);
                }
                players.add(resultSet.getLong("id_p"));

            }
            return game;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(Game entity) throws StorageException {
        String sql = "DELETE FROM games WHERE id = ?";
        try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql)) {
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
