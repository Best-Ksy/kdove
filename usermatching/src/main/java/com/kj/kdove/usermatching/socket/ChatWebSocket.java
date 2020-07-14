package com.kj.kdove.usermatching.socket;

import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket/{socketid}/{userid}")
@Component
public class ChatWebSocket {

    public static AtomicInteger onlineNumber = new AtomicInteger(0);

    public static List<ChatWebSocket> webSockets = new CopyOnWriteArrayList<ChatWebSocket>();

    private Session session;

    private String userid;
    private String socketid;


    /**
     * 建立连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("socketid") String socketid,@PathParam("userid") String userid){
        if (socketid == null || "".equals(socketid) || userid == null || "".equals(userid)){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        for (ChatWebSocket chatWebSocket : webSockets) {
            if (socketid.equals(chatWebSocket.socketid) && userid.equals(chatWebSocket.userid)) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        onlineNumber.incrementAndGet();
        this.session = session;
        this.userid = userid;
        this.socketid = socketid;
        webSockets.add(this);
        System.out.println("有新连接加入！ 当前在线人数" + onlineNumber.get() + "socket对象数"+webSockets.size());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber.decrementAndGet();
        webSockets.remove(this);
        System.out.println("有连接关闭！ 当前在线人数" + onlineNumber.get());
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("socketid") String socketid , @PathParam("userid") String userid) {
        System.out.println("来自" + userid + "消息：" + message);
        pushMessage(userid, message, socketid);
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息推送
     */
    public static void pushMessage(String userid, String message, String socketid) {

        if (socketid == null || "".equals(socketid) || userid == null || "".equals(userid)){
            return;
        } else {
            String[] userids = socketid.split("_");
            for (String otherid:userids){
                //找到两个人userid和matching对应的webSocket实例
                for (ChatWebSocket chatWebSocket:webSockets) {
                    if (otherid.equals(chatWebSocket.userid)){
                        if (socketid.equals(chatWebSocket.socketid)){
                            chatWebSocket.sendMessage(message);
                            break;
                        }
                    }
                }
            }
        }
    }

}
