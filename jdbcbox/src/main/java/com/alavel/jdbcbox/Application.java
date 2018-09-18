package com.alavel.jdbcbox;

import com.alavel.jdbcbox.common.SingleConnectionPool;
import com.alavel.jdbcbox.common.StorageException;
import com.alavel.jdbcbox.game.Game;
import com.alavel.jdbcbox.game.GameRepository;
import com.alavel.jdbcbox.player.Player;
import com.alavel.jdbcbox.player.PlayerRepository;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;

public final class Application {
    public static void main(String[] args) {
        Properties connectionProps = new Properties();

        try (InputStream props = Application.class.getResourceAsStream("/datasource.properties")) {
            connectionProps.load(props);
        } catch (IOException e) {
            panic(e);
        }

        String url = connectionProps.getProperty("url");
        try (Connection connection = DriverManager.getConnection(url, connectionProps)) {

            Supplier<Connection> connectionSupplier = new SingleConnectionPool(connection);

            //create and save players
            /*PlayerRepository playerRepository = new PlayerRepository(connectionSupplier);
            for (String playerName : inputPlayerNames()) {
                playerRepository.save(new Player(playerName));
            }
            for (Player player : playerRepository.list()) {
                System.out.println(player);
            }

            //select single player by id
            PlayerRepository playerRepository = new PlayerRepository(connectionSupplier);
            Player player = playerRepository.get(3L);
            System.out.println(player);
            */

            GameRepository gameRepository = new GameRepository(connectionSupplier);
            /*Game game = gameRepository.get(2L);
            System.out.println(game);

            for (Game game1 : gameRepository.list()) {
                System.out.println(game1);
            }*/

            List<Long> list = new ArrayList<>();
            list.add(2L);
            list.add(3L);
            Game game2 = new Game(4L, 3L, 30L, list);
            gameRepository.save(game2);


        } catch (SQLException | StorageException e) {
            panic(e);
        }
    }

    private static List<String> inputPlayerNames() {
        System.out.println("Input player names:");
        Scanner scanner = new Scanner(System.in);
        List<String> names = new LinkedList<>();
        String name;
        while (!(name = scanner.nextLine()).isEmpty()) {
            names.add(name);
        }
        return names;
    }

    private static void panic(Throwable e) {
        e.printStackTrace();
        System.exit(1);
    }
}