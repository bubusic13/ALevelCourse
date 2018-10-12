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
            connection.setAutoCommit(false);
            PlayerRepository playerRepository = new PlayerRepository(connectionSupplier);
            GameRepository gameRepository = new GameRepository(connectionSupplier);

            Scanner scanner = new Scanner(System.in);
            String command;
            String commandList = "Commands: " +
                    "\npa  - add players" +
                    "\npls - list players" +
                    "\nga  - add games" +
                    "\ngls - list games" +
                    "\nq   - exit";
            System.out.println(commandList);
            while (true) {
                System.out.print("> ");
                command = scanner.next().trim().toLowerCase();

                if ("pa".equals(command)) {
                    System.out.println("enter player name: ");
                    String name = scanner.nextLine();
                    Player player = new Player(name);
                    playerRepository.save(player);
                } else if ("pls".equals(command)) {
                    for (Player player : playerRepository.list()) {
                        System.out.printf("%d: name = %s; score = %d; rank = %s\n",
                                player.getId(), player.getNickname(), player.getScore(), player.getRank());
                    }
                } else if ("ga".equals(command)) {
                    System.out.println("Enter number of participants: ");
                    int partNumber = scanner.nextInt();
                    System.out.println("Enter participants ids: ");
                    List<Long> players = new ArrayList<>(partNumber);
                    for (int i = 0; i < partNumber; i++) {
                        players.add(scanner.nextLong());
                    }
                    System.out.println("Enter winner id: ");
                    Long winnerId = scanner.nextLong();
                    System.out.println("Enter score: ");
                    Long score = scanner.nextLong();

                    gameRepository.save(new Game(null, winnerId, score, players));

                } else if ("gls".equals(command)) {
                    for (Game game : gameRepository.list()) {
                        String gameString = String.valueOf(game.getId()) + ": " +
                                "winner = " +
                                game.getWinnerId() +
                                "; score = " +
                                game.getScore() +
                                "; participants = " +
                                game.getPlayers();
                        System.out.println(gameString);
                    }
                } else if ("q".equals(command)) {
                    System.out.println("bye!");
                    break;
                } else {
                    System.out.println(commandList);
                }

            }


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