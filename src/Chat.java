import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Chat {

    private static final int PORT = 6778;

    public static void main(String[] args) {
        Socket socket;
        try {
            if (args.length == 0) {
                ServerSocket serverSocket = new ServerSocket(PORT);
                socket = serverSocket.accept();
            } else {
                socket = new Socket(args[0], PORT);
            }
            receiveMessages(socket);
            sendMessage(socket);
        } catch (IOException ex) {
            handleException(ex);
        }
    }

    private static void sendMessage(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        OutputStreamWriter printWriter = new OutputStreamWriter(socket.getOutputStream());
        while (true) {
            StringBuilder message = new StringBuilder();
            message.append(bufferedReader.readLine()).append("\n");
            printWriter.write(message.toString(), 0, message.length());
            printWriter.flush();
        }
    }

    private static void receiveMessages(Socket socket) throws IOException {
        new Thread(() -> {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while (true) {
                    System.out.println(bufferedReader.readLine());
                }
            } catch (IOException ex) {
                handleException(ex);
            }
        }).start();
    }

    private static void handleException(Exception ex) {
        ex.printStackTrace();
        System.exit(-1);
    }

}
