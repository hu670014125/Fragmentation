
package com.android.networktools.ping;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * PING 返回结果
 * @author huqs
 */
public class PingResult implements Serializable {
    public final InetAddress ia;
    public boolean isReachable;
    public String error = null;
    public float timeTaken;
    public String fullString;
    public String result;

    public PingResult(InetAddress ia) {
        this.ia = ia;
    }

    public boolean isReachable() {
        return this.isReachable;
    }

    public boolean hasError() {
        return this.error != null;
    }

    public float getTimeTaken() {
        return this.timeTaken;
    }

    public String getError() {
        return this.error;
    }

    public InetAddress getAddress() {
        return this.ia;
    }

    @Override
    @NotNull
    public String toString() {
        return "PingResult{ia=" + this.ia + ", isReachable=" + this.isReachable + ", error='" + this.error + '\'' + ", timeTaken=" + this.timeTaken + ", fullString='" + this.fullString + '\'' + ", result='" + this.result + '\'' + '}';
    }
}
