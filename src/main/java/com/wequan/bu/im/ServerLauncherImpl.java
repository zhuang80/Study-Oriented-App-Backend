package com.wequan.bu.im;

import java.io.IOException;

import com.wequan.bu.im.event.MessageQoSEventS2CListnerImpl;
import com.wequan.bu.im.event.ServerEventListenerImpl;
import com.wequan.bu.im.qos.QoS4ReciveDaemonC2S;
import com.wequan.bu.im.qos.QoS4SendDaemonS2C;

/**
 * @author zhen
 */
public class ServerLauncherImpl extends ServerLauncher {
    static {

        ServerLauncherImpl.PORT = 7901;

        QoS4SendDaemonS2C.getInstance().setDebugable(true);
        QoS4ReciveDaemonC2S.getInstance().setDebugable(true);
        ServerLauncher.debug = true;

//		ServerToolKits.setSenseMode(SenseMode.MODE_10S);

        ServerLauncher.bridgeEnabled = false;
    }

    public ServerLauncherImpl() throws IOException {
        super();
    }

    @Override
    protected void initListeners() {
        this.setServerEventListener(new ServerEventListenerImpl());
        this.setServerMessageQoSEventListener(new MessageQoSEventS2CListnerImpl());
    }

    public static void main(String[] args) throws Exception {
        final ServerLauncherImpl sli = new ServerLauncherImpl();

        sli.startup();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                sli.shutdown();
            }
        });
    }
}
