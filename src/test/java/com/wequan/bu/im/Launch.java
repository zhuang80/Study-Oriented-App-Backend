package com.wequan.bu.im;

import com.wequan.bu.im.core.LocalUDPDataReciever;
import com.wequan.bu.im.core.LocalUDPDataSender;

/**
 * @author zhen
 */
public class Launch {
    public static void main(String[] args) {
        new Launch().run();
    }

    public void run() {
        IMClientManager.getInstance().initIMSDK();
        ClientCore.getInstance().init();
        int code = doLoginImpl();
        if (code == 0) {
            LocalUDPDataReciever.getInstance().startup();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int doLoginImpl() {
        return LocalUDPDataSender.getInstance().sendLogin("tom", "123", "111");
    }
}
