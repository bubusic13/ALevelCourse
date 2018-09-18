package com.alevel.practice.hangman;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    NumberOfAttempt noa = new NumberOfAttempt();
    GetTheList gtl = new GetTheList();
    Word word;
    WordList wordList;

    public void startGame(){

        noa.setAtempts();
        gtl.setUrl();
        gtl.getList();
        List<String> list = gtl.list;
        wordList = new WordList(list);
        String randomWord = wordList.getRandomWord();
        word = new Word(randomWord);
        word.createHidenWord();
        System.out.println(word.getHidenWord());

        while (noa.haveAtempts() && word.getNumberOfHiddenLetter()> 0){
            System.out.println("You have " + noa.getAtempts() + " atempts.");
            System.out.println("Type letter, you want to check.");
            if(!word.guessingLetter(setLetter())){
                noa.decrementAtempts();
            }
            if(word.getNumberOfHiddenLetter() == 0) {
                System.out.println("You won!");
            }
            else if(!noa.haveAtempts()){
                System.out.println("You loose! Word is: " + word.getWord());
            }

        }

    }

    public String setLetter(){

        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

}
