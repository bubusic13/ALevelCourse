package com.alavel.jdbcbox.game;

import java.util.List;
import java.util.Objects;

public class Game {

    private final Long id;

    private final Long winnerId;

    private final Long score;

    private  final List<Long> players;

    public Game(Long id, Long winnerId, Long score, List<Long> players) {
        this.id = id;
        this.winnerId = winnerId;
        this.score = score;
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public Long getScore() {
        return score;
    }

    public List<Long> getPlayers() {
        return players;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, winnerId, score, players);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", winner id='" + winnerId + '\'' +
                ", score='" + score + '\'' +
                ", players=" + players.toString() +
                '}';
    }
}
