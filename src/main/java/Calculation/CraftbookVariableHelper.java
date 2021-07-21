package Calculation;

import Interface.FileAdderInterface;
import Utils.Utils;
import lombok.SneakyThrows;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static Utils.FileInputReader.getViableIDs;

public class CraftbookVariableHelper {

    @SneakyThrows
    // This method returns all Variables that are used more than once in the file
    public static String[] getDupedVariables() {

        // the file that was created first by the FileInputReader is read
        String path = FileAdderInterface.getDirectory() + "/variables.txt";
        List<String> pipeIDs = Utils.readStringsFromFile(path);
        List<String[]> csv;

        // if file doesn't exist we return an error, else we read all the variables in the file
        if (pipeIDs == null) {
            return new String[]{"The file was not found!"};
        } else {
            csv = pipeIDs.stream().map(v -> v.split(":")).collect(Collectors.toList());
        }

        // we only add all non-redundant lines into our list of pipeIDs
        pipeIDs = csv.stream().filter(v -> v[0].length() > 0).map(v -> v[0]).collect(Collectors.toList());

        List<String> dupedVariables = new ArrayList<>();

        // filters all IDs that are in the list more than once and adds them to the dupedVariables list.
        List<String> finalPipeIDs = pipeIDs;
        pipeIDs.stream().filter(a -> finalPipeIDs.stream().filter(a::equals).count() > 1).distinct().forEach(dupedVariables::add);

        return dupedVariables.toArray(new String[0]);
    }


    @SneakyThrows
    // this method returns all two-letter variables that are not used in the file yet
    public static String[] getUnusedVariables() {

        // if file doesn't exist we return an error, else we read all the variables in the file
        File file = new File(FileAdderInterface.getDirectory() + "/variables.txt");
        List<String> pipeIDs = new ArrayList<>();
        List<String[]> csv;


        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            csv = reader.lines().map(v -> v.split(":")).collect(Collectors.toList());
        } else {
            return new String[]{"The file was not found!"};
        }

        // we only add all non-redundant lines into our list of pipeIDs
        for (String[] line : csv) {
            if (line[0].length() > 0)
                pipeIDs.add(line[0].trim());
        }

        // creates List of all possible two-letter-combinations.
        List<String> unusedCombinations = new ArrayList<>(26 * 26);

        for (char i = 'a'; i <= 'z'; i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                unusedCombinations.add(Character.toString(i) + j);
            }
        }

        // removes all two-letter-combinations that are already used
        unusedCombinations.removeAll(pipeIDs);

        // returns all two-letter-variables not yet used
        return unusedCombinations.toArray(new String[0]);
    }

    @SneakyThrows
    public static List<String[]> findTyposInConfig() {
        List<String[]> errorMessageList = new ArrayList<>();

        List<String[]> returnList = new ArrayList<>();

        List<String> minecraftIDs = getViableIDs("/minecraft_ids.txt");

        if (minecraftIDs == null) {
            errorMessageList.add(new String[]{"An error occurred while \nexecuting this query! \nPlease ask EmielRegis for support."});
            return errorMessageList;
        }

        System.out.println(minecraftIDs);

        File file = new File(FileAdderInterface.getDirectory() + "/variables.txt");

        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = reader.lines().collect(Collectors.toList());

            for (String line : lines) {
                String[] splitLine = line.split(":");
                String[] ids = splitLine[1].split(",");

                if (line.contains(",,") || line.endsWith(",")) {
                    returnList.add(new String[]{"-Formatting Error-", splitLine[0]});
                }

                for (String id : ids) {
                    if (!minecraftIDs.contains(id.trim())) {
                        returnList.add(new String[]{id, splitLine[0]});
                    }
                }
            }
        } else {
            errorMessageList.add(new String[]{"An error occurred reading the \nnecessary file! \nPlease ask EmielRegis for support."});
            return errorMessageList;
        }

        return returnList;
    }
}
