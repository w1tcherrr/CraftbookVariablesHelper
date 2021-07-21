package Utils;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.Collectors;

public class Utils {
    @SneakyThrows
    public static String getStringResource(String resource) {
        var inputStream = Utils.class.getClassLoader().getResourceAsStream(resource);
        if (inputStream == null)
            throw new MissingResourceException("Missing resource", Utils.class.getName(), resource);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().filter(line -> !line.isEmpty()).collect(Collectors.joining(System.lineSeparator()));
    }

    public static File findFile(String name, File directory) {
        File[] list = directory.listFiles();
        if (list != null)
            for (File file : list) {
                if (file.isDirectory()) {
                    findFile(name, file);
                } else if (name.equalsIgnoreCase(file.getName())) {
                    return file;
                }
            }
        return null;
    }

    @SneakyThrows
    public static List<String> readStringsFromFile(String path) {
        List<String> list;
        File file = new File(path);

        if (!file.exists()) {
            return null;
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        list = bufferedReader.lines().collect(Collectors.toList());

        return list;
    }

    @SneakyThrows
    public static List<String[]> readStringsFromFile(String path, String split) {
        List<String[]> list;
        File file = new File(path);

        if (!file.exists()) {
            return null;
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        list = bufferedReader.lines().map(v -> v.split(split)).collect(Collectors.toList());

        return list;
    }
}
