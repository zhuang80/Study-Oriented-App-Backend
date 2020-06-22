package com.wequan.bu.im.core;

import java.net.DatagramSocket;
import java.net.InetAddress;

import com.wequan.bu.im.ClientCore;
import com.wequan.bu.im.conf.ConfigEntity;
import com.wequan.bu.im.utils.Log;
import com.wequan.bu.im.utils.UDPUtils;

//import org.jdesktop.swingworker.SwingWorker;

import com.wequan.bu.im.protocal.ProtocalFactory;
import com.wequan.bu.im.protocal.ErrorCode;
import com.wequan.bu.im.protocal.Protocal;

/**
 * @author zhen
 */
public class LocalUDPDataSender {
    private final static String TAG = LocalUDPDataSender.class.getSimpleName();

    private static LocalUDPDataSender instance = null;

    public static LocalUDPDataSender getInstance() {
        if (instance == null)
            instance = new LocalUDPDataSender();
        return instance;
    }

    private LocalUDPDataSender() {
    }

    public int sendLogin(String loginUserId, String loginToken, String extra) {
        byte[] b = ProtocalFactory.createPLoginInfo(loginUserId, loginToken, extra).toBytes();
        int code = send(b, b.length);
        if (code == 0) {
            ClientCore.getInstance().setCurrentLoginUserId(loginUserId);
            ClientCore.getInstance().setCurrentLoginToken(loginToken);
            ClientCore.getInstance().setCurrentLoginExtra(extra);
        }

        return code;
    }

    public int sendLoginout() {
        int code = ErrorCode.COMMON_CODE_OK;
        if (ClientCore.getInstance().isLoginHasInit()) {
            byte[] b = ProtocalFactory.createPLoginoutInfo(
                    ClientCore.getInstance().getCurrentLoginUserId()).toBytes();
            code = send(b, b.length);
            if (code == 0) {
                // do nothing
            }
        }
        ClientCore.getInstance().release();
        return code;
    }

    int sendKeepAlive() {
        byte[] b = ProtocalFactory.createPKeepAlive(
                ClientCore.getInstance().getCurrentLoginUserId()).toBytes();
        return send(b, b.length);
    }

    public int sendCommonData(String dataContentWidthStr, String to_user_id) {
        return sendCommonData(dataContentWidthStr, to_user_id, -1);
    }

    public int sendCommonData(String dataContentWidthStr, String to_user_id, int typeu) {
        return sendCommonData(dataContentWidthStr, to_user_id, null, typeu);
    }

    public int sendCommonData(String dataContentWidthStr, String to_user_id
            , String fingerPrint, int typeu) {
        return sendCommonData(dataContentWidthStr, to_user_id, true, fingerPrint, typeu);
    }

    public int sendCommonData(String dataContentWidthStr, String to_user_id
            , boolean QoS, String fingerPrint, int typeu) {
        return sendCommonData(ProtocalFactory.createCommonData(dataContentWidthStr
                , ClientCore.getInstance().getCurrentLoginUserId(), to_user_id, QoS, fingerPrint, typeu));
    }

    public int sendCommonData(Protocal p) {
        if (p != null) {
            byte[] b = p.toBytes();
            int code = send(b, b.length);
            if (code == 0) {
                if (p.isQoS() && !QoS4SendDaemon.getInstance().exist(p.getFp()))
                    QoS4SendDaemon.getInstance().put(p);
            }
            return code;
        } else
            return ErrorCode.COMMON_INVALID_PROTOCAL;
    }

    private int send(byte[] fullProtocalBytes, int dataLen) {
        if (!ClientCore.getInstance().isInitialed())
            return ErrorCode.ForC.CLIENT_SDK_NO_INITIALED;

        DatagramSocket ds = LocalUDPSocketProvider.getInstance().getLocalUDPSocket();
        if (ds != null && !ds.isConnected()) {
            try {
                if (ConfigEntity.serverIP == null) {
                    Log.w(TAG, "【IMCORE】send数据没有继续，原因是ConfigEntity.server_ip==null!");
                    return ErrorCode.ForC.TO_SERVER_NET_INFO_NOT_SETUP;
                }

                ds.connect(InetAddress.getByName(ConfigEntity.serverIP), ConfigEntity.serverUDPPort);
            } catch (Exception e) {
                Log.w(TAG, "【IMCORE】send时出错，原因是：" + e.getMessage(), e);
                return ErrorCode.ForC.BAD_CONNECT_TO_SERVER;
            }
        }
        return UDPUtils.send(ds, fullProtocalBytes, dataLen) ? ErrorCode.COMMON_CODE_OK : ErrorCode.COMMON_DATA_SEND_FAILD;
    }

//	public static abstract class SendCommonDataAsync extends SwingWorker<Integer, Object>
//	{
//		protected Protocal p = null;
//
//		public SendCommonDataAsync(String dataContentWidthStr, String to_user_id)
//		{
//			this(dataContentWidthStr, to_user_id, null, -1);
//		}
//		public SendCommonDataAsync(String dataContentWidthStr, String to_user_id
//				, int typeu)
//		{
//			this(dataContentWidthStr, to_user_id, null, typeu);
//		}
//		public SendCommonDataAsync(String dataContentWidthStr, String to_user_id
//				, String fingerPrint, int typeu)
//		{
//			this(ProtocalFactory.createCommonData(dataContentWidthStr
//					, ClientCoreSDK.getInstance().getCurrentLoginUserId(), to_user_id
//					, true, fingerPrint, typeu));
//		}
//
//		public SendCommonDataAsync(Protocal p)
//		{
//			if(p == null)
//			{
//				Log.w(TAG, "【IMCORE】无效的参数p==null!");
//				return;
//			}
//			this.p = p;
//		}
//
//		@Override
//		protected Integer doInBackground()
//		{
//			if(p != null)
//				return LocalUDPDataSender.getInstance().sendCommonData(p);//dataContentWidthStr, to_user_id);
//			return -1;
//		}
//
//		@Override
//		protected void done()
//		{
//			int code = -1;
//			try
//			{
//				code = get();
//			}
//			catch (Exception e)
//			{
//				Log.w(TAG, e.getMessage());
//			}
//
//			onPostExecute(code);
//		}
//
//		protected abstract void onPostExecute(Integer code);
//	}
//
//	public static class SendLoginDataAsync extends SwingWorker<Integer, Object>
//	{
//		protected String loginUserId = null;
//		protected String loginToken = null;
//		protected String extra = null;
//
//		public SendLoginDataAsync(String loginUserId, String loginToken)
//		{
//			this(loginUserId, loginToken, null);
//		}
//
//		public SendLoginDataAsync(String loginUserId, String loginToken, String extra)
//		{
//			this.loginUserId = loginUserId;
//			this.loginToken = loginToken;
//			this.extra = extra;
//
//			ClientCoreSDK.getInstance().init();
//		}
//
//		@Override
//		protected Integer doInBackground()
//		{
//			int code = LocalUDPDataSender.getInstance().sendLogin(
//					this.loginUserId, this.loginToken, this.extra);
//			return code;
//		}
//
//		@Override
//		protected void done()
//		{
//			int code = -1;
//			try
//			{
//				code = get();
//			}
//			catch (Exception e)
//			{
//				Log.w(TAG, e.getMessage());
//			}
//
//			onPostExecute(code);
//		}
//
//		protected void onPostExecute(Integer code)
//		{
//			if(code == 0)
//			{
//				LocalUDPDataReciever.getInstance().startup();
//			}
//			else
//			{
//				Log.d(TAG, "【IMCORE】数据发送失败, 错误码是："+code+"！");
//			}
//
//			fireAfterSendLogin(code);
//		}
//
//		protected void fireAfterSendLogin(int code)
//		{
//			// default do nothing
//		}
//	}
}
