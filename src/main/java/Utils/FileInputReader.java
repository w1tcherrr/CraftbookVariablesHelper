package Utils;

import Calculation.CraftbookVariableHelper;
import Interface.FileAdderInterface;
import lombok.SneakyThrows;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileInputReader {

    @SneakyThrows
    public static void readYmlData(String path) {
        String variableWritePath = FileAdderInterface.getDirectory() + "/variables.txt";
        BufferedReader variablesYmlReader = new BufferedReader(new FileReader(path));
        BufferedWriter variablesYmlWriter = new BufferedWriter(new FileWriter(variableWritePath));

        variablesYmlReader.lines().forEach(line -> {
            try {
                String regex = " {4}global\\|[a-zA-Z0-9_]{1,13}: .*"; // valid regex for lines in config
                if (line.length() > 11 && line.matches(regex)) {
                    variablesYmlWriter.write(line.substring(11).replace(" ", ""));
                    variablesYmlWriter.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        variablesYmlWriter.close();
        variablesYmlReader.close();
    }

    public static List<String> getViableIDs(String path) {
        List<String> minecraftIDs;

        try (InputStream in = CraftbookVariableHelper.class.getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)))) {
            minecraftIDs = reader.lines().map(s -> s.replace("minecraft:", "").trim()).filter(s -> !s.equals("// contains all viable ID's as of version 1.17.1")).collect(Collectors.toList());
        } catch (IOException | NullPointerException e) {
            return null;
        }

        return minecraftIDs;
    }
}
