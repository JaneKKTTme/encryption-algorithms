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
import java.util.Map;

public class DecryptionByLetterFrequency {
    public static void doDecryption(String fileName, String stopPlace) throws IOException {
        HashMap<String, Double> letterFrequencyOfEncryptedText = readFile("LetterFrequencyOfEncryptedText", fileName.substring(0, fileName.indexOf(stopPlace)));
        HashMap<String, Double> letterFrequencyOfAllText = readFile("LetterFrequencyOfAllText", fileName.substring(0, fileName.indexOf(stopPlace)));
        decryptByLetterFrequency(fileName, letterFrequencyOfEncryptedText, letterFrequencyOfAllText);
    }

    private static HashMap<String, Double> readFile(String letterName, String filePath) throws IOException {
        HashMap<String, Double> letterFrequency = new HashMap<>();
        FileReader inputText = new FileReader(new File( filePath + letterName + ".txt"));
        BufferedReader reader = new BufferedReader(inputText);
        String line = reader.readLine();
        while (line != null) {
            String[] parameters = line.split(" ");
            letterFrequency.put(parameters[0], Double.parseDouble(parameters[1]));
            line = reader.readLine();
        }
        inputText.close();

        return letterFrequency;
    }

    private static void decryptByLetterFrequency(String fileName, HashMap<String, Double> letterFrequencyOfEncryptedText, HashMap<String, Double> letterFrequencyOfAllText) throws IOException {
        FileWriter decryptedText = new FileWriter(fileName.substring(0, fileName.indexOf(".txt")) + "[decrypted].txt");
        FileReader inputText = new FileReader(new File(fileName.substring(0, fileName.indexOf(".txt")) + "[encrypted].txt"));
        BufferedReader reader = new BufferedReader(inputText);
        HashMap<String, String> keys = findOffset(letterFrequencyOfEncryptedText, letterFrequencyOfAllText);

        int c;
        while ((c = reader.read()) != -1) {
            decryptedText.write(offsetByKey((char) c, keys));
        }

        decryptedText.close();
        inputText.close();
    }

    private static char offsetByKey(char symbol, HashMap<String, String> keys) {
        final String ALPHABET_LOWERCASE = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        final String ALPHABET_UPPERCASE = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

        if (ALPHABET_UPPERCASE.contains(String.valueOf(symbol))) {
            for (Map.Entry<String, String> entry : keys.entrySet()) {
                String k = entry.getKey();
                String v = entry.getValue();
                if (k.contains(String.valueOf(symbol))) {
                    return v.charAt(0);
                }
            }
        } else if (ALPHABET_LOWERCASE.contains(String.valueOf(symbol))) {
            for (Map.Entry<String, String> entry : keys.entrySet()) {
                String k = entry.getKey();
                String v = entry.getValue();
                if (k.contains(String.valueOf(symbol))) {
                    return v.charAt(1);
                }
            }
        }

        return symbol;
    }

    private static HashMap<String, String> findOffset(HashMap<String, Double> letterFrequencyOfEncryptedText, HashMap<String, Double> letterFrequencyOfAllText) {
        HashMap<String, String> offsets = new HashMap<>();
        for (int i = 'А'; i <= 'Я'; i++) {
            offsets.put((char) i + String.valueOf((char) (i + 32)), "");
        }
        offsets.put("Ёё", "");

        letterFrequencyOfEncryptedText.forEach((encryptedKey, encrypedValue) -> {
            final double[] min = {1};
            letterFrequencyOfAllText.forEach((key, value) -> {
                if (Math.abs((double) Math.round((encrypedValue - value) * 100000) / 100000) < min[0] && !key.substring(0, 1).equals(encryptedKey.substring(0, 1)) && offsets.get(encryptedKey).equals("")) {
                    offsets.put(encryptedKey, key);
                    min[0] = Math.abs((double) Math.round((encrypedValue - value) * 100000) / 100000);
                }
                else if (Math.abs((double) Math.round((encrypedValue - value) * 100000) / 100000) < min[0] && !key.substring(0, 1).equals(encryptedKey.substring(0, 1))) {
                    offsets.replace(encryptedKey, key);
                    min[0] = Math.abs((double) Math.round((encrypedValue - value) * 100000) / 100000);
                }
            });
        });

        offsets.forEach((k, v) -> {
            if (v.equals("")) {
                offsets.replace(k, k);
            }
        });

        return offsets;
    }
}