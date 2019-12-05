package com.android.networktools.ping;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ping {
    private int timeOutMillis = 1000;
    private String addressString = "";

    private Ping() {
    }

    public static Ping onAddress(String address) {
        Ping ping = new Ping();
        ping.setAddressString(address);
        return ping;
    }


    public Ping setTimeOutMillis(int timeOutMillis) {
        if (timeOutMillis < 0) {
            throw new IllegalArgumentException("Times cannot be less than 0");
        } else {
            this.timeOutMillis = timeOutMillis;
            return this;
        }
    }

    private void setAddressString(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.addressString = address;
    }

    public  PingResult doPing() throws UnknownHostException {
        InetAddress  ia = InetAddress.getByName(addressString);
        try {
            return NativePing.doNativePing(ia, timeOutMillis);
        } catch (InterruptedException e) {
            PingResult pingResult = new PingResult(ia);
            pingResult.isReachable = false;
            pingResult.error = "Interrupted";
            return pingResult;
        } catch (Exception e) {
            return JavaPing.doJavaPing(ia, timeOutMillis);
        }
    }

}
