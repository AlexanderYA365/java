package com.getjavajob.training.yakovleva.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class tetst {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            try (FileReader fileReader = new FileReader("input.txt")) {
                try (BufferedReader file = new BufferedReader(fileReader)) {
                    String readFile = file.readLine();
                    try (FileWriter writer = new FileWriter("output.txt")) {
                        String res = sum(readFile);
                        writer.write(res);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String sum(String input) {
        String[] splitInput = input.split(" ");
        int A = Integer.parseInt(splitInput[0]);
        int B = Integer.parseInt(splitInput[1]);
        return String.valueOf(A + B);
    }

}

//    public static void main(String[] args) {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
//            try(FileReader fileReader = new FileReader("input.txt")) {
//                try(BufferedReader file = new BufferedReader(fileReader)) {
//                    String readFile = file.readLine();
//                    try (FileWriter writer = new FileWriter("output.txt")){
//                        String res = sum(readFile);
//                        writer.write(res);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }