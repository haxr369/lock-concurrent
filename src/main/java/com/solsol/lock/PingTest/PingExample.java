package com.solsol.lock.PingTest;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class PingExample {
    public static void main(String[] args){
        List<String> checkingIPs = Arrays.asList(
                "192.168.255.150",
                "192.168.255.100",
                "192.168.255.50",
                "192.168.255.51",
                "192.168.255.52");
        checkingIPs.forEach(
                ip -> {
                    if(isReachable(ip)) log.info(ip+" is reachable!");
                    else log.info(ip+" is not reachable...");
                }
        );
    }
    public static boolean isReachable(String ipAddress) {
        try (Socket socket = new Socket()) {
            // Timeout 설정: 1000ms
            socket.connect(new InetSocketAddress(ipAddress, 80), 1000);
            return true;
        } catch (IOException e) {
            // 연결 실패
            log.warn(e.getMessage());
            return false;
        }
    }
}