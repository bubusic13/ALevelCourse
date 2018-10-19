package com.alevel.todolist;

public class TodoException extends  Exception {
    TodoException(Throwable cause){
        super(cause.getMessage(), cause);
    }
}
