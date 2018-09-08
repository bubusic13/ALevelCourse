package com.alevel.practice.hangman;

import java.util.ArrayList;
import java.util.Random;

public class WordList {

    ArrayList<String> list;

    public WordList(ArrayList<String> list) {
        this.list = list;
    }

    public String getRandomWord(){
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

}
