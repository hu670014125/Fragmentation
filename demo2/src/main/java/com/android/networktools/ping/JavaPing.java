package com.android.networktools.ping;


import java.io.IOException;
import java.net.InetAddress;

public class JavaPing {
    private JavaPing(){}
    public static PingResult doJavaPing(InetAddress ia, int timeOutMillis) {
       PingResult pingResult = new PingResult(ia);

        try {
            long startTime = System.nanoTime();
            boolean reached = ia.isReachable(timeOutMillis);
            pingResult.timeTaken = (float)(System.nanoTime() - startTime) / 1000000.0F;
            pingResult.isReachable = reached;
            if (!reached) {
                pingResult.error = "Timed Out";
            }
        } catch (IOException var6) {
            pingResult.isReachable = false;
            pingResult.error = "IOException: " + var6.getMessage();
        }

        return pingResult;
    }
}
