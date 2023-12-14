package com.solsol.lock.connect;


import com.solsol.lock.socket.GreetClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocketServerClientTest {

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
        GreetClient client = new GreetClient(); // 서버와 연결을 생성
        client.startConnection("0.0.0.0", 6666); // 소캣을 이용해서 연결
        String response = client.sendMessage("hello server"); // 요청을 통해 응답 받기
        client.stopConnection(); // 클라이언트 소캣을 명시적으로 닫음
        assertEquals("hello client", response);
    }
}
