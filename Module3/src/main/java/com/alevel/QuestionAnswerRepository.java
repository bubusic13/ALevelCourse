package com.alevel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionAnswerRepository {

    private final DataSource dataSource;

    public QuestionAnswerRepository(DataSource dataSource)  {
        this.dataSource = dataSource;
    }

    public void save(QuestionAnswer entity) throws QuestionAnswerException {
        String sql = "INSERT INTO question_answer (question, answer) VALUES (?,?)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getQuestion());
            preparedStatement.setString(2, entity.getAnswer());
            preparedStatement.executeUpdate();


        }catch (SQLException e) {
            throw new QuestionAnswerException(e);
        }
    }

    public String getAnswer(String inputQuestion) throws QuestionAnswerException {
        String answer;

        String sql = "SELECT answer FROM question_answer WHERE question = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, inputQuestion);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.first();
            answer = resultSet.getString("answer");

        }

        catch (SQLException e) {
            throw new QuestionAnswerException(e);
        }
        return answer;
    }
}
