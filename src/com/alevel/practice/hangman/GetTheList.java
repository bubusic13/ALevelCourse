package com.alevel.practice.hangman;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetTheList {

    private String url;
    List<String> list = new ArrayList<>();

    public void getList(){
        try {
            list = Files.readAllLines(Paths.get(url), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setUrl(){
        System.out.println("Type file URL:");
        Scanner scanner = new Scanner(System.in);
        url = scanner.nextLine();
    }





}
