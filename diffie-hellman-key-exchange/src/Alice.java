/*
 * Author : Jane Khomenko
 *
 * Github : JaneKKTTme
 *
 * Email : tyryry221@gmail.com
 *
 * Date : 09.10.2020
 *
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Alice {
    private static ServerSocket serverSocket_;
    private static Socket socket_;
    private static final Integer port_ = 8080;

    public static void closeConnection() {
        try {
            serverSocket_.close();
        } catch (IOException e) { /* cannot happen */ }
    }

    public static void start() {
        try {
            socket_ = serverSocket_.accept();
        } catch (IOException e) { /* cannot happen */ }
    }

    public static void main(String[] args) throws Exception {
        // Server started ...
        serverSocket_ = new ServerSocket(port_);

        // Server connected ...
        start();

        try {
            // DataInputStream and DataOutputStream created
            DataInputStream in = new DataInputStream(socket_.getInputStream());
            DataOutputStream out = new DataOutputStream(socket_.getOutputStream());

            // Create for Bob two random numbers using sieve of Eratosthenes and algorithm of searching antiderivative root
            String pairOfRandomSimpleAndOddNumbers = GenerationOfSimpleOddRandomNumbers.doGenerationOfSimpleOddRandomNumbers();
            int p = Integer.parseInt(pairOfRandomSimpleAndOddNumbers.split(" ")[0]);
            int g = Integer.parseInt(pairOfRandomSimpleAndOddNumbers.split(" ")[1]);

            // Create an own random simple number
            Random rnd = new Random(System.currentTimeMillis());
            int a = 3 + rnd.nextInt(50 - 1);

            // Count modulo
            int A = (int) Math.pow(g, a) % p;

            // Transfer to Bob Alice's public key
            String request = p + " " + g + " " + A;
            out.writeUTF(request);
            System.out.println("Alice wrote to Bob : " + request);

            // Get Bob's modulo
            int B = Integer.parseInt(in.readUTF());
            System.out.println("Alice read from Bob : " + B);

            // Canculate key
            int K = (int) Math.pow(B, a) % p;
            System.out.println("Common key by Alice : " + K);

            in.close();
            out.close();
            closeConnection();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}