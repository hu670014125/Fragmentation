package com.android.networktools.ping

import android.content.Context
import android.content.Intent
import me.yokeyword.demo2.BuildConfig
import java.net.InetAddress
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit

/**
 * 网络监测工具
 * @author huqs
 */
object NetworkTools {
    private var mPingFutureTask: FutureTask<Unit>? = null
    private val mPingExecutorService=Executors.newSingleThreadExecutor()
    fun start(content: Context, address: String) {
        if (mPingFutureTask == null) {
            mPingFutureTask = FutureTask {
                while (true) {
                    val ping = timeoutMethod(500, address)
                        if (BuildConfig.DEBUG){
                            println("-----ping:$ping")
                        }
                    val pingIntent = Intent()
                    pingIntent.action=content.packageName+".NETWORK.PING"
                    content.sendBroadcast(pingIntent)
                    val hostName = ping.address.hostName
                    if (hostName != null && hostName.contentEquals("ip6-localhost")) {
                        Thread.sleep(500)
                    } else {
                        Thread.sleep(1000)
                    }
                }
            }

            mPingFutureTask?.let {
                Executors.newCachedThreadPool().execute(it)
            }
        }
    }
    fun stop() {
        mPingFutureTask?.cancel(true)
        mPingFutureTask = null
    }
    /**
     * 有超时时间的方法
     * @param timeout Int
     * @return
     */
    private fun timeoutMethod(timeout: Int,address: String): PingResult {
        val futureTask = FutureTask {
            Ping.onAddress(address).setTimeOutMillis(timeout).doPing()
        }
        mPingExecutorService.execute(futureTask)
        try {
            futureTask.get(timeout.toLong(), TimeUnit.MILLISECONDS)
        } catch (e: Throwable) {
            futureTask.cancel(true)
            return PingResult(InetAddress.getByName(""))
        }
        futureTask.isCancelled
        return futureTask.get()
    }
}