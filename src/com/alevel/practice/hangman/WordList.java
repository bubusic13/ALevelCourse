package com.alevel.practice.hangman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordList {

    List<String> list;

    public WordList(List<String> list) {
        this.list = list;
    }

    public String getRandomWord(){
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

}
