package Utils;

import Calculation.CraftbookVariableHelper;
import Interface.FileAdderInterface;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileInputReader {

    @SneakyThrows
    public static void readYmlData(String path) {
        String variableWritePath = FileAdderInterface.getDirectory() + "/variables.txt";
        BufferedReader variablesYmlReader = new BufferedReader(new FileReader(path));
        BufferedWriter variablesYmlWriter = new BufferedWriter(new FileWriter(variableWritePath));
        String regex = " {4}global\\|(?<id>[a-zA-Z0-9_]{1,13}): (?<items>[a-zA-Z0-9_,]+)"; // valid regex for lines in config

        variablesYmlReader.lines().forEach(line -> {
            try {
                Matcher matcher = Pattern.compile(regex).matcher(line);
                if (matcher.matches()) {
                    variablesYmlWriter.write(matcher.group("id") + ":" + matcher.group("items"));
                    variablesYmlWriter.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        variablesYmlWriter.close();
        variablesYmlReader.close();
    }

    public static List<String> getViableIDs(@NonNull String path) {
        try (InputStream in = CraftbookVariableHelper.class.getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException | NullPointerException e) {
            return null;
        }
    }
}
