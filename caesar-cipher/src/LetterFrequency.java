/*
 * Author: Jane Khomenko
 *
 * Github: JaneKKTTme
 *
 * Email: tyryry221@gmail.com
 *
 * Date: 22.09.2020
 */

import java.io.*;
import java.util.HashMap;

public class LetterFrequency {
    public static void doLetterFrequency(String fileName, String stopPlace) throws IOException {
        HashMap<String, Double> letterFrequencyOfEncryptedText = createEmptyLetterFrequency();
        HashMap<String, Double> letterFrequencyOfAllText = createEmptyLetterFrequency();
        readAndFindMatchingInEmptyLetter(fileName.substring(0, fileName.indexOf(".txt")) + "[encrypted].txt", letterFrequencyOfEncryptedText);
        readAndFindMatchingInEmptyLetter(fileName, letterFrequencyOfAllText);
        saveLetterFrequency(fileName.substring(0, fileName.indexOf(stopPlace)), letterFrequencyOfEncryptedText, "LetterFrequencyOfEncryptedText");
        saveLetterFrequency(fileName.substring(0, fileName.indexOf(stopPlace)), letterFrequencyOfAllText, "LetterFrequencyOfAllText");
    }

    private static HashMap<String, Double> createEmptyLetterFrequency() {
        HashMap<String, Double> letterFrequency = new HashMap<>();
        for (int i = 'А'; i <= 'Я'; i++) {
            letterFrequency.put((char) i + String.valueOf((char) (i + 32)), (double) 0);
        }
        letterFrequency.put("Ёё", 0.0);

        return letterFrequency;
    }

    private static void readAndFindMatchingInEmptyLetter(String fileName, HashMap<String, Double> letterFrequency) throws IOException {
        FileReader inputText = new FileReader(new File(fileName));

        BufferedReader reader = new BufferedReader(inputText);
        int c;
        final int[] amountOfSymbols = {0};
        while ((c = reader.read()) != -1) {
            int finalC = c;
            letterFrequency.forEach((key, value) -> {
                if (key.contains(String.valueOf((char) finalC))) {
                    letterFrequency.replace(key, value + 1);
                    amountOfSymbols[0] += 1;
                }
            });
        }
        inputText.close();

        letterFrequency.forEach((key, value) -> {
            letterFrequency.replace(key, (double) Math.round(value / amountOfSymbols[0] * 10000) / 10000);
        });
    }

    private static void saveLetterFrequency(String filePath, HashMap<String, Double> letterFrequency, String letterName) throws IOException {
        FileWriter letterFile = new FileWriter(filePath + letterName + ".txt");

        letterFrequency.forEach((key, value) -> {
            try {
                letterFile.write(key + " " + value + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        letterFile.close();
    }
}