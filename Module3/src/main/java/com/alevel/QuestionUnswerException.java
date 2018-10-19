package com.alevel;

public class QuestionUnswerException extends Exception {
    QuestionUnswerException(Throwable cause){
        super(cause.getMessage(), cause);
    }
}
