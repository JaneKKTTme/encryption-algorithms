import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Bob {
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 8080;

    private static boolean isConnected_;
    private static Socket socket_;

    public Bob(){
        isConnected_ = false;
    }

    public static void connectToServer() throws IOException {
        socket_ = new Socket(IP_ADDRESS,  PORT);
        isConnected_ = true;
    }

    public static void closeConnection() {
        if (!isConnected_) {
            return;
        }
        try {
            socket_.close();
        } catch (IOException e) {
            /* cannot happen */
        }
        isConnected_ = false;
    }

    public static void main(String[] args) {
        try {
            // Open connection to Alice
            connectToServer();
            DataInputStream in = new DataInputStream(socket_.getInputStream());
            DataOutputStream out = new DataOutputStream(socket_.getOutputStream());

            // Get from Bob two random number and Alice's modulo
            String message = in.readUTF();
            System.out.println("Bob read from Alice : " + message);
            int p = Integer.parseInt(message.split(" ")[0]);
            int g = Integer.parseInt(message.split(" ")[1]);
            int A = Integer.parseInt(message.split(" ")[2]);

            // Create own random simple number
            Random rnd = new Random(System.currentTimeMillis());
            int b = 3 + rnd.nextInt(50 - 1);

            // Count modulo
            int B = (int) Math.pow(g % p, b) % p;

            // Transfer to Alice Bob's public key
            out.writeUTF(Integer.toString(B));
            System.out.println("Bob wrote to Alice : " + B);

            // Canculate key
            int K = (int) Math.pow(A, b) % p;
            System.out.println("Find common key : " + K);

            in.close();
            out.close();
            closeConnection();
        }
        catch (IOException ignored) {
            /* cannot happen */
        }
    }
}