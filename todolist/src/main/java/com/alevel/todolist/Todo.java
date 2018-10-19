package com.alevel.todolist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class Todo {

    private final Long id;

    private final String text;

    private final Boolean done;

    public Todo(String text) {
        this.id = null;
        this.text = text;
        this.done = false;
    }

    @JsonCreator
    public Todo(
            @JsonProperty("id") Long id,
            @JsonProperty("text") String text,
            @JsonProperty("done") Boolean done
    ) {
        this.id = id;
        this.text = text;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Boolean isDone() {
        return done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id) &&
                Objects.equals(text, todo.text) &&
                Objects.equals(done, todo.done);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, done);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", done=" + done +
                '}';
    }
}