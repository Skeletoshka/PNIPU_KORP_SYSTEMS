package biz.bna.project;

import biz.bna.project.task.WordCounter;

import java.util.*;

public class Main {
    private static boolean insertDataFlag = false;

    public static void main(String[] args) {
        if(insertDataFlag) {
            Arrays.stream(args).parallel().forEach(path -> {
                Thread t = new Thread(new WordCounter(path));
                t.start();
            });
        }
    }
}