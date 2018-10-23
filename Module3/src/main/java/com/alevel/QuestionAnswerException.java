package com.alevel;

public class QuestionAnswerException extends Exception {
    QuestionAnswerException(Throwable cause){
        super(cause.getMessage(), cause);
    }
}
