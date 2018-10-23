package com.alevel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;


@WebServlet("/question")
public class QuestionAnswerServlet extends HttpServlet {

    private ObjectMapper objectMapper;

    private HikariDataSource dataSource;

    private QuestionAnswerRepository questionAnswerRepository;


    @Override
    public void init() {
        HikariConfig hikariConfig = new HikariConfig("/hikari.properties");
        dataSource = new HikariDataSource(hikariConfig);
        questionAnswerRepository = new QuestionAnswerRepository(dataSource);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String answer = "";

        try {
            String question = req.getParameter("question");
            answer = questionAnswerRepository.getAnswer(question);
            System.out.println(question);
            System.out.println(answer);


        } catch (QuestionAnswerException e) {
            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=utf8");
        Map<String, String> responseData = Collections.singletonMap("answer", answer);
        objectMapper.writeValue(resp.getOutputStream(), responseData);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        QuestionAnswer questionAnswer = objectMapper.readValue(req.getInputStream(), QuestionAnswer.class);
        try {
            questionAnswerRepository.save(questionAnswer);
        } catch (QuestionAnswerException e) {
            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
            return;
        }
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    public void destroy() {
        dataSource.close();
    }
}
