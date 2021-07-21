package Utils;

import Interface.FileAdderInterface;
import lombok.SneakyThrows;

import java.io.*;

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
}
