package com.alevel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionUnswerRepository {

    private final DataSource dataSource;

    public QuestionUnswerRepository(DataSource dataSource)  {
        this.dataSource = dataSource;
    }

    public void save(QuestionUnswer entity) throws QuestionUnswerException{
        String sql = "INSERT INTO question_answer (question, unswer) VALUES (?,?)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getQuestion());
            preparedStatement.setString(2, entity.getUnswer());
            preparedStatement.executeUpdate();


        }catch (SQLException e) {
            throw new QuestionUnswerException(e);
        }
    }

    public String getUnswer(String inputQuestion) throws QuestionUnswerException{
        String unswer = "";

        String sql = "SELECT unswer FROM question_answer where question = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, inputQuestion);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.first();
            unswer = resultSet.getString("unswer");
            return unswer;
        }

        catch (SQLException e) {
            throw new QuestionUnswerException(e);
        }
    }
}
