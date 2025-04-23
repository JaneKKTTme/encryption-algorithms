/*
 * Author: Jane Khomenko
 *
 * Github: JaneKKTTme
 *
 * Email: tyryry221@gmail.com
 *
 * Date: 22.09.2020
 */

public class CaesarCodeApplication {
    public static void main(String[] args) throws Exception {
        EncryptionByCaesarCode.doEncryption(args[0], Integer.valueOf(args[1]));
        LetterFrequency.doLetterFrequency(args[0], "War");
        DecryptionByLetterFrequency.doDecryption(args[0], "War");
    }
}