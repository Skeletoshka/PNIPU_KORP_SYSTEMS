package biz.bna.project;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> words = new HashMap<>();
        try (FileInputStream reader = new FileInputStream ("E:\\PSTU\\Java\\KorpSystems\\src\\main\\resources\\text.txt")){
            String text = new String(reader.readAllBytes(), StandardCharsets.UTF_8);
            text = text.replaceAll("[\n\r\\.\\)\\(\\?—,0-9«»:/]", " ").trim();
            while(text.contains("  ")){
                text = text.replace("  ", " ");
            }
            List<String> wordsInText = Arrays.stream(text.split(" ")).sorted().collect(Collectors.toList());
            wordsInText.stream().forEach(word -> {
                String wordInMap = word.toLowerCase(Locale.ROOT);
                if(words.containsKey(wordInMap)){
                    words.put(wordInMap, words.get(wordInMap) + 1);
                }else{
                    words.put(wordInMap, 1);
                }
            });
            System.out.println("Количество слов в тексте: " + words.keySet().stream()
                    .map(key -> key + " = " + words.get(key))
                    .collect(Collectors.joining("\n", "{", "}")));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}