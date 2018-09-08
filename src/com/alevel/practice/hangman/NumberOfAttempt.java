package com.alevel.practice.hangman;

import java.util.Scanner;

public class NumberOfAttempt {

    private int atempts;

    public void decrementAtempts(){

        atempts--;
    }

    public boolean haveAtempts(){

        return atempts > 0;
    }

    public void setAtempts(){
        System.out.println("Set number of atemts:");
        Scanner scanner = new Scanner(System.in);
        atempts = Integer.parseInt(scanner.nextLine());
    }

    public int getAtempts() {
        return atempts;
    }
}
