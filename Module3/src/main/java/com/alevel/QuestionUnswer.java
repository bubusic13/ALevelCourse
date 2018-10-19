package com.alevel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class QuestionUnswer {

    private final String question;

    private final String unswer;

    @JsonCreator
    public QuestionUnswer(
            @JsonProperty("question") String question,
            @JsonProperty("unswer") String unswer) {
        this.question = question;
        this.unswer = unswer;
    }




    public String getQuestion() {
        return question;
    }

    public String getUnswer() {
        return unswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionUnswer that = (QuestionUnswer) o;
        return Objects.equals(question, that.question) &&
                Objects.equals(unswer, that.unswer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(question, unswer);
    }

    @Override
    public String toString() {
        return "QuestionUnswer{" +
                "question='" + question + '\'' +
                ", unswer='" + unswer + '\'' +
                '}';
    }
}
