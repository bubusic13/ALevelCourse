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
        Connection connection = connectionSupplier.get();
        try {
            try {
                String saveGame = "INSERT INTO games (mvp_id, score) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(saveGame)) {
                    preparedStatement.setLong(1, entity.getWinnerId());
                    preparedStatement.setLong(2, entity.getScore());
                    preparedStatement.executeUpdate();
                }

                String addPointsToPlayer = "UPDATE players SET score = score + ? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(addPointsToPlayer)) {
                    preparedStatement.setLong(1, entity.getScore());
                    preparedStatement.setLong(2, entity.getWinnerId());
                    preparedStatement.executeUpdate();
                }

                String addParticipants = "INSERT INTO participants VALUES (?, LAST_INSERT_ID());";
                try (PreparedStatement preparedStatement = connection.prepareStatement(addParticipants)) {
                    for (Long player : entity.getPlayers()) {
                        preparedStatement.setLong(1, player);
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                }

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw new StorageException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Game> list() throws StorageException {
        Connection connection = connectionSupplier.get();
        try {
            try {
                String sql = "SELECT id, id_winner, score, id_p FROM games INNER JOIN participants on games.id = participants.id_g ORDER BY id";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    ResultSet resultSet = statement.executeQuery();
                    Long currentId = null;
                    Long winner;
                    List<Long> players = null;
                    Long score;
                    List<Game> games = new LinkedList<>();
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
                }


            } catch (SQLException e) {
                connection.rollback();
                throw new StorageException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Game get(Long aLong) throws StorageException {
        Connection connection = connectionSupplier.get();
        try {
            try {


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
                            game = new Game(currentId, winner, score, players);
                        }
                        players.add(resultSet.getLong("id_p"));

                    }
                    return game;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new StorageException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

    @Override
    public void delete(Game entity) throws StorageException {
        Connection connection = connectionSupplier.get();
        try {
            try {
                String sql = "DELETE FROM games WHERE id = ?";
                try (PreparedStatement statement = connectionSupplier.get().prepareStatement(sql)) {
                    statement.setLong(1, entity.getId());
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new StorageException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
