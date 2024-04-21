package biz.bna.project.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {

    public static List<String> getFilePathInDirectory(String directoryPath){
        List<String> paths = new ArrayList<>();
        try(Stream<Path> fileStream = Files.walk(Path.of(directoryPath))){
            fileStream.forEach(filePath -> {
                if(filePath.toFile().isFile()){
                    paths.add(filePath.toAbsolutePath().toString());
                }
            });
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
        return paths;
    }

}
