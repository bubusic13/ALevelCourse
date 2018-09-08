package com.alevel.practice.hangman;

public class Word {
    private String word;
    private StringBuilder hidenWord = new StringBuilder();
    private int numberOfHiddenLetter;

    public Word(String word) {

        this.word = word;

    }

    public void createHidenWord(){
        numberOfHiddenLetter = word.length();
        for(int i = 0; i < word.length(); i++){
            hidenWord.append("*");
        }

    }

    public boolean guessingLetter(String letter){

        boolean marker = false;
        for(int i = 0; i < word.length(); i++){
            char c = letter.charAt(0);
            if(c == word.charAt(i)){
                hidenWord.setCharAt(i, c);
                numberOfHiddenLetter--;
                marker = true;
            }
        }
        System.out.println(hidenWord);
        return marker;

    }

    public int getNumberOfHiddenLetter() {
        return numberOfHiddenLetter;
    }

    public String getWord() {
        return word;
    }

    public StringBuilder getHidenWord() {
        return hidenWord;
    }


}
