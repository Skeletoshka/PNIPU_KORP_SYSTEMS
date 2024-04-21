package biz.bna.project.task;

import biz.bna.project.model.Appendix;
import biz.bna.project.model.Repeat;
import biz.bna.project.model.Word;
import biz.bna.project.repository.AppendixRepository;
import biz.bna.project.repository.RepeatRepository;
import biz.bna.project.repository.WordRepository;
import biz.bna.project.utils.FileUtils;
import biz.bna.project.utils.WordUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class WordCounter implements Runnable{
    private final String fileLocation;
    private final AppendixRepository appendixRepository;
    private final WordRepository wordRepository;
    private final RepeatRepository repeatRepository;

    public WordCounter(String fileLocation){
        this.fileLocation = fileLocation;
        this.appendixRepository = new AppendixRepository();
        this.wordRepository = new WordRepository();
        this.repeatRepository = new RepeatRepository();
    }

    @Override
    public void run(){
        System.out.printf("Scanning %s start.\n", fileLocation);
        countWord(FileUtils.getFilePathInDirectory(fileLocation));
        System.out.printf("Scanning %s end.\n", fileLocation);
    }

    /**Подсчет всех слов в файле и добавляет запись в бд*/
    private void countWord(List<String> filePaths){
        filePaths.stream().forEach(filePath -> {
            System.out.printf("Scanning %s start.\n", filePath);
            Map<String, Integer> words = new HashMap<>();
            try (FileInputStream reader = new FileInputStream (filePath)){
                String text = new String(reader.readAllBytes(), StandardCharsets.UTF_8);
                text = text.replaceAll("[\n\r\t\\.\\)\\(\\?\"\\[\\]—,0-9«»:/]", " ").trim();
                while(text.contains("  ")){
                    text = text.replace("  ", " ");
                }
                List<String> wordsInText = Arrays.stream(text.split(" ")).sorted().collect(Collectors.toList());
                wordsInText.stream()
                        .filter(word -> word.length() > 1)
                        .map(word -> word.length()>3?WordUtils.stem(word):word)
                        .forEach(word -> {
                    if(words.containsKey(word)){
                        words.put(word, words.get(word) + 1);
                    }else{
                        words.put(word, 1);
                    }
                });
                insertFileInDb(filePath, words);
                System.out.printf("Scanning %s end.\n", filePath);
                Thread.yield();
            }catch (Exception e){
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }

    private void insertFileInDb(String filePath, Map<String, Integer> wordsRepeat) {
        String wordWhere = "word_text = ?";
        String repeatWhere = "word_id = ? AND appendix_id = ?";
        Appendix appendix = new Appendix(null, filePath.substring(filePath.lastIndexOf(File.separator) + 1), filePath);
        List<Appendix> appendices = appendixRepository.findWhere(
                "appendix_name = ? AND appendix_path = ?",
                Map.of(
                        1, appendix.getAppendixName(),
                        2, appendix.getAppendixPath()
                ));
        if(appendices.isEmpty()) {
            appendixRepository.insert(appendix);
        }else {
            appendix.setAppendixId(appendices.get(0).getAppendixId());
        }
        wordsRepeat.keySet().stream().parallel().forEach(wordText -> {
            List<Word> words = wordRepository.findWhere(wordWhere, wordText);
            Word word;
            if (words.isEmpty()) {
                word = new Word(null, wordText);
                wordRepository.insert(word);
            } else {
                word = words.get(0);
            }
            Map<Integer, Object> params = Map.of(
                    1, word.getWordId(),
                    2, appendix.getAppendixId()
            );
            List<Repeat> repeats = repeatRepository.findWhere(repeatWhere, params);
            if (repeats.isEmpty()) {
                Repeat repeat = new Repeat(null, word.getWordId(), appendix.getAppendixId(), wordsRepeat.get(wordText));
                repeatRepository.insert(repeat);
            }
        });
    }
}
