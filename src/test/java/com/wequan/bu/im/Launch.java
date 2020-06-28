package com.wequan.bu.im;

import com.wequan.bu.im.core.LocalUDPDataReciever;
import com.wequan.bu.im.core.LocalUDPDataSender;
import com.wequan.bu.im.protocal.Protocal;
import com.wequan.bu.im.protocal.ProtocalFactory;

import java.util.UUID;

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

        while(true) {

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(ClientCore.getInstance().isLoginHasInit()) {
                Protocal p = ProtocalFactory.createCommonData("hello", ClientCore.getInstance().getCurrentLoginUserId(), "1", true, UUID.randomUUID().toString(), -1);
                LocalUDPDataSender.getInstance().sendCommonData(p);
            }
        }
    }

    private int doLoginImpl() {
        return LocalUDPDataSender.getInstance().sendLogin("tom", "123", "111");
    }
}
