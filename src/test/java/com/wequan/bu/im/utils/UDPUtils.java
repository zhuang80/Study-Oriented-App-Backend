package com.wequan.bu.im.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author zhen
 */
public class UDPUtils {
    private final static String TAG = UDPUtils.class.getSimpleName();

    public static boolean send(DatagramSocket skt, byte[] d, int dataLen) {
        if (skt != null && d != null) {
            try {
                return send(skt, new DatagramPacket(d, dataLen));
            } catch (Exception e) {
                Log.e(TAG, "【IMCORE】send方法中》》发送UDP数据报文时出错了：remoteIp=" + skt.getInetAddress()
                        + ", remotePort=" + skt.getPort() + ".原因是：" + e.getMessage(), e);
                return false;
            }
        } else {
            Log.e(TAG, "【IMCORE】send方法中》》无效的参数：skt=" + skt);//
            return false;
        }
    }

    public synchronized static boolean send(DatagramSocket skt, DatagramPacket p) {
        boolean sendSucess = true;
        if (skt != null && p != null) {
            if (skt.isConnected()) {
                try {
                    skt.send(p);
                } catch (Exception e) {
                    sendSucess = false;
                    Log.e(TAG, "【IMCORE】send方法中》》发送UDP数据报文时出错了，原因是：" + e.getMessage(), e);
                }
            }
        } else {
            Log.w(TAG, "【IMCORE】在send()UDP数据报时没有成功执行，原因是：skt==null || p == null!");
        }

        return sendSucess;
    }
}
