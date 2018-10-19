package com.alevel.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@WebServlet("/todo")
public class TodoServlet extends HttpServlet {

    private ObjectMapper objectMapper;

    private HikariDataSource dataSource;

    private TodoRepository todoRepository;

    @Override
    public void init() {
        HikariConfig hikariConfig = new HikariConfig("/hikari.properties");
        dataSource = new HikariDataSource(hikariConfig);
        todoRepository = new TodoRepository(dataSource);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Todo> todos;
        try {
            todos = todoRepository.listAllNotDone();
        } catch (TodoException e) {
            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
            return;
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=utf8");
        objectMapper.writeValue(resp.getOutputStream(), todos);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Todo todo = objectMapper.readValue(req.getInputStream(), Todo.class);
        Long id;
        try {
            id = todoRepository.save(todo);
        } catch (TodoException e) {
            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
            return;
        }
        Map<String, Long> responseData = Collections.singletonMap("id", id);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json;charset=utf8");
        objectMapper.writeValue(resp.getOutputStream(), responseData);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Todo[] todos = objectMapper.readValue(req.getInputStream(), Todo[].class);
        try {
            todoRepository.batchUpdate(Arrays.asList(todos));
        } catch (TodoException e) {
            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
            return;
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void destroy() {
        dataSource.close();
    }
}