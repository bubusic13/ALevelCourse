package com.alevel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class QuestionAnswer {

    private final String question;

    private final String answer;

    @JsonCreator
    public QuestionAnswer(
            @JsonProperty("question") String question,
            @JsonProperty("answer") String answer) {
        this.question = question;
        this.answer = answer;
    }




    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionAnswer that = (QuestionAnswer) o;
        return Objects.equals(question, that.question) &&
                Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(question, answer);
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
