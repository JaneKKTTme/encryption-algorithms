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

public class EncryptionByCaesarCode {
    public static void doEncryption(String fileName, Integer key) throws Exception, IOException {
        FileReader inputText = new FileReader(new File(fileName.substring(0, fileName.indexOf(".txt")) + "[part].txt"));
        encryptByCaesarCode(fileName, inputText, key);
        inputText.close();
    }

    private static void encryptByCaesarCode(String fileName, FileReader inputText, Integer key) throws IOException {
        FileWriter encryptedText = new FileWriter(fileName.substring(0, fileName.indexOf(".txt")) + "[encrypted].txt");
        BufferedReader reader = new BufferedReader(inputText);
        int c;
        while ((c = reader.read()) != -1) {
            encryptedText.write(offsetByKey((char) c, key));
        }
        encryptedText.close();
    }

    private static char offsetByKey(char symbol, Integer key) {
        final String ALPHABET_LOWERCASE = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        final String ALPHABET_UPPERCASE = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

        if (ALPHABET_UPPERCASE.contains(String.valueOf(symbol))) {
            return ALPHABET_UPPERCASE.charAt(doOffset(ALPHABET_UPPERCASE.indexOf(symbol), key));
        } else if (ALPHABET_LOWERCASE.contains(String.valueOf(symbol))) {
            return ALPHABET_LOWERCASE.charAt(doOffset(ALPHABET_LOWERCASE.indexOf(symbol), key));
        } else {
            return symbol;
        }
    }

    private static Integer doOffset(Integer index, Integer key) {
        while (Math.abs(key) > 32) {
            if (key > 0) {
                key -= 33;
            }
            else if (key < 0) {
                key += 33;
            }
        }
        if (index + key > 32) {
            return index + key - 33;
        }
        else if (index + key < 0) {
            return index + key + 33;
        }
        else {
            return index + key;
        }
    }
}
