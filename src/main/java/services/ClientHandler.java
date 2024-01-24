package services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());

            String clientSentence = inFromClient.readLine();
//            System.out.println("Received: " + clientSentence);
            String capitalizedSentence = "Conectado al server" + '\n';
            outToClient.writeBytes(capitalizedSentence);
        } catch (IOException e) {
            throw new RuntimeException("Error handling client communication", e);
        }
    }
}