package com.solsol.lock.socket;

import lombok.extern.slf4j.Slf4j;
import java.net.*;
import java.io.*;


@Slf4j
public class SocketExample {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        // 클라이언트로 보낼 메세지 쓰기
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        // 클라이언트로 부터 받은 메세지 읽기
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        log.info("클라이언트에게 받은 메세지 : "+greeting);
        if ("hello server".equals(greeting)) {
            out.println("hello client");
        }
        else {
            out.println("unrecognised greeting");
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        SocketExample server = new SocketExample();
        server.start(6666);
    }
}
