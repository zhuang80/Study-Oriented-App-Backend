package com.wequan.bu.im.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.wequan.bu.im.ClientCore;
import com.wequan.bu.im.utils.Log;

//import org.jdesktop.swingworker.SwingWorker;

/**
 * @author zhen
 */
public class AutoReLoginDaemon {
    private final static String TAG = AutoReLoginDaemon.class.getSimpleName();

    private static AutoReLoginDaemon instance = null;

    public static int AUTO_RE$LOGIN_INTERVAL = 2000;

    private boolean autoReLoginRunning = false;
    private boolean _excuting = false;
    private Timer timer = null;

    public static AutoReLoginDaemon getInstance() {
        if (instance == null)
            instance = new AutoReLoginDaemon();
        return instance;
    }

    private AutoReLoginDaemon() {
        init();
    }

    private void init() {
        timer = new Timer(AUTO_RE$LOGIN_INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });
    }

    public void run() {
        if (!_excuting) {
            _excuting = true;
            if (ClientCore.DEBUG)
                Log.p(TAG, "【IMCORE】自动重新登陆线程执行中, autoReLogin?" + ClientCore.autoReLogin + "...");
            int code = -1;
            if (ClientCore.autoReLogin) {
                LocalUDPSocketProvider.getInstance().closeLocalUDPSocket();
                code = LocalUDPDataSender.getInstance().sendLogin(
                        ClientCore.getInstance().getCurrentLoginUserId()
                        , ClientCore.getInstance().getCurrentLoginToken()
                        , ClientCore.getInstance().getCurrentLoginExtra());
            }

            if (code == 0) {
                LocalUDPDataReciever.getInstance().startup();
            }

            _excuting = false;
        }
    }

    public void stop() {
        if (timer != null)
            timer.stop();
        autoReLoginRunning = false;
    }

    public void start(boolean immediately) {
        stop();

        if (immediately)
            timer.setInitialDelay(0);
        else
            timer.setInitialDelay(AUTO_RE$LOGIN_INTERVAL);
        timer.start();

        autoReLoginRunning = true;
    }

    public boolean isautoReLoginRunning() {
        return autoReLoginRunning;
    }
}
