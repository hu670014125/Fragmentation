
package com.android.networktools.ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * Android 本地 ping
 * @author huqs
 */
public class NativePing {
    private NativePing() {
    }

    public static PingResult doNativePing(InetAddress host, int timeOutMillis) throws IOException, InterruptedException {
        PingResult pingResult = new PingResult(host);
        StringBuilder echo = new StringBuilder();
        Runtime runtime = Runtime.getRuntime();
        int timeoutSeconds = timeOutMillis / 1000;
        if (timeoutSeconds < 0) {
            timeoutSeconds = 1;
        }

        String address = host.getHostAddress();
        String pingCommand = "ping";
        if (address != null) {
            if (IPTools.isIPv6Address(address)) {
                pingCommand = "ping6";
            } else if (!IPTools.isIPv4Address(address)) {
            }
        } else {
            address = host.getHostName();
        }

        Process proc = runtime.exec(pingCommand + " -c 1 -w " + timeoutSeconds + " " + address);
        proc.waitFor();
        int exit = proc.exitValue();
        if (exit != 0) {
            String pingError;
            if (exit == 1) {
                pingError = "failed, exit = 1";
            } else {
                pingError = "error, exit = 2";
            }

            pingResult.error = pingError;
            return pingResult;
        } else {
            InputStreamReader reader = new InputStreamReader(proc.getInputStream());
            BufferedReader buffer = new BufferedReader(reader);

            String line;
            while((line = buffer.readLine()) != null) {
                echo.append(line).append("\n");
            }

            return getPingStats(pingResult, echo.toString());
        }
    }

    public static PingResult getPingStats(PingResult pingResult, String s) {
        String pingError;
        if (s.contains("0% packet loss")) {
            int start = s.indexOf("/mdev = ");
            int end = s.indexOf(" ms\n", start);
            pingResult.fullString = s;
            if (start != -1 && end != -1) {
                s = s.substring(start + 8, end);
                String[] stats = s.split("/");
                pingResult.isReachable = true;
                pingResult.result = s;
                pingResult.timeTaken = Float.parseFloat(stats[1]);
                return pingResult;
            }

            pingError = "Error: " + s;
        } else if (s.contains("100% packet loss")) {
            pingError = "100% packet loss";
        } else if (s.contains("% packet loss")) {
            pingError = "partial packet loss";
        } else if (s.contains("unknown host")) {
            pingError = "unknown host";
        } else {
            pingError = "unknown error in getPingStats";
        }

        pingResult.error = pingError;
        return pingResult;
    }
}
