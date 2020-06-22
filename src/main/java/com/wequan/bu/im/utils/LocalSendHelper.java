package com.wequan.bu.im.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import com.wequan.bu.im.bridge.QoS4SendDaemonB2C;
import com.wequan.bu.im.netty.Observer;
import com.wequan.bu.im.ServerCoreHandler;
import com.wequan.bu.im.processor.OnlineProcessor;
import com.wequan.bu.im.protocal.ErrorCode;
import com.wequan.bu.im.protocal.Protocal;
import com.wequan.bu.im.protocal.ProtocalFactory;
import com.wequan.bu.im.qos.QoS4SendDaemonS2C;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhen
 */
public class LocalSendHelper {
    private static Logger logger = LoggerFactory.getLogger(ServerCoreHandler.class);

    public static void sendData(String to_user_id
            , String dataContent, Observer resultObserver) throws Exception {
        sendData(to_user_id, dataContent, true, null, -1, resultObserver);
    }

    public static void sendData(String to_user_id, String dataContent
            , int typeu, Observer resultObserver) throws Exception {
        sendData(to_user_id, dataContent, true, null, typeu, resultObserver);
    }

    public static void sendData(String to_user_id, String dataContent
            , boolean QoS, int typeu, Observer resultObserver) throws Exception {
        sendData(to_user_id, dataContent, QoS, null, typeu, resultObserver);
    }

    public static void sendData(String to_user_id, String dataContent
            , boolean QoS, String fingerPrint, Observer resultObserver) throws Exception {
        sendData(to_user_id, dataContent, QoS, fingerPrint, -1, resultObserver);
    }

    public static void sendData(String to_user_id, String dataContent
            , boolean QoS, String fingerPrint, int typeu, Observer resultObserver) throws Exception {
        sendData(
                ProtocalFactory.createCommonData(dataContent, "0", to_user_id, QoS, fingerPrint, typeu)
                , resultObserver);
    }

    public static void sendData(Protocal p, Observer resultObserver) throws Exception {
        if (p != null) {
            if (!"0".equals(p.getTo())) {
                sendData(OnlineProcessor.getInstance().getOnlineSession(p.getTo()), p, resultObserver);
            } else {
                logger.warn("[IMCORE-netty]【注意】此Protocal对象中的接收方是服务器(user_id==0)（而此方法本来就是由Server调用，自已发自已不可能！），数据发送没有继续！" + p.toGsonString());

                if (resultObserver != null) {
                    resultObserver.update(false, null);
                }
            }
        } else {
            if (resultObserver != null) {
                resultObserver.update(false, null);
            }
        }
    }

    public static void sendData(final Channel session
            , final Protocal p, final Observer resultObserver) throws Exception {
        if (session == null) {
            logger.info("[IMCORE-netty]toSession==null >> id=" + p.getFrom() + "的用户尝试发给客户端" + p.getTo()
                    + "的消息：str=" + p.getDataContent() + "因接收方的id已不在线，此次实时发送没有继续(此消息应考虑作离线处理哦).");
        } else {
            if (session.isActive()) {
                if (p != null) {
                    final byte[] res = p.toBytes();

                    ByteBuf to = Unpooled.copiedBuffer(res);
                    ChannelFuture cf = session.writeAndFlush(to);//.sync();

                    cf.addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) {
                            if (future.isSuccess()) {
//		 		    			logger.info("[IMCORE-netty] >> 给客户端："+ServerToolKits.clientInfoToString(session)
//		 		    					+"的数据->"+p.toGsonString()+",已成功发出["+res.length+"].");

                                if ("0".equals(p.getFrom())) {
                                    if (p.isQoS() && !QoS4SendDaemonS2C.getInstance().exist(p.getFp())) {
                                        QoS4SendDaemonS2C.getInstance().put(p);
                                    }
                                } else if (p.isBridge()) {
                                    if (p.isQoS() && !QoS4SendDaemonB2C.getInstance().exist(p.getFp())) {
                                        QoS4SendDaemonB2C.getInstance().put(p);
                                    }
                                }
                            } else {
                                logger.warn("[IMCORE-netty]给客户端：" + ServerToolKits.clientInfoToString(session) + "的数据->" + p.toGsonString() + ",发送失败！[" + res.length + "](此消息应考虑作离线处理哦).");
                            }

                            if (resultObserver != null) {
                                resultObserver.update(future.isSuccess(), null);
                            }
                        }
                    });

                    // ## Bug FIX: 20171226 by JS, 上述数据的发送结果直接通过ChannelFutureListener就能知道，
                    //            如果此处不return，则会走到最后的resultObserver.update(false, null);，就会
                    //            出现一个发送方法的结果回调先是失败（错误地走到下面去了），一个是成功（真正的listener结果）
                    return;
                    // ## Bug FIX: 20171226 by JS END
                } else {
                    logger.warn("[IMCORE-netty]客户端id=" + p.getFrom() + "要发给客户端" + p.getTo()
                            + "的实时消息：str=" + p.getDataContent() + "没有继续(此消息应考虑作离线处理哦).");
                }
            }
        }

        if (resultObserver != null) {
            resultObserver.update(false, null);
        }
    }

    public static void replyDataForUnlogined(Channel session, Protocal p
            , Observer resultObserver) throws Exception {
        logger.warn("[IMCORE-netty]>> 客户端" + ServerToolKits.clientInfoToString(session) + "尚未登陆，" + p.getDataContent() + "处理未继续.");

        Protocal perror = ProtocalFactory.createPErrorResponse(
                ErrorCode.ForS.RESPONSE_FOR_UNLOGIN, p.toGsonString(), "-1"); // 尚未登陆则user_id就不存在了,用-1表示吧，目前此情形下该参数无意义
        sendData(session, perror, resultObserver);
    }

    public static void replyDelegateRecievedBack(Channel session, Protocal pFromClient
            , Observer resultObserver) throws Exception {
        if (pFromClient.isQoS() && pFromClient.getFp() != null) {
            Protocal receivedBackP = ProtocalFactory.createRecivedBack(
                    pFromClient.getTo()
                    , pFromClient.getFrom()
                    , pFromClient.getFp());

            sendData(session, receivedBackP, resultObserver);
        } else {
            logger.warn("[IMCORE-netty]收到" + pFromClient.getFrom()
                    + "发过来需要QoS的包，但它的指纹码却为null！无法发伪应答包哦！");

            if (resultObserver != null) {
                resultObserver.update(false, null);
            }
        }
    }
}
