package com.wequan.bu.im;

import java.io.IOException;

import com.wequan.bu.im.event.MessageQoSEventS2CListnerImpl;
import com.wequan.bu.im.event.ServerEventListenerImpl;
import com.wequan.bu.im.qos.QoS4ReciveDaemonC2S;
import com.wequan.bu.im.qos.QoS4SendDaemonS2C;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhen
 */
public class ServerLauncherImpl extends ServerLauncher {

    public ServerLauncherImpl() throws IOException {
    }

    @Override
    protected void initListeners() throws IOException {}


    public static void main(String[] args) throws Exception {
//        final ServerLauncherImpl sli = new ServerLauncherImpl();
//
//
//
//        sli.startup();
//
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                sli.shutdown();
//            }
//        });
    }
}
