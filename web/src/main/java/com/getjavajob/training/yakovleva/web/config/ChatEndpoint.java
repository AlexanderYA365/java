package com.getjavajob.training.yakovleva.web.config;

import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.common.utils.MessageDecoder;
import com.getjavajob.training.yakovleva.common.utils.MessageEncoder;
import com.getjavajob.training.yakovleva.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@ServerEndpoint(value = "/goChat/{username}", configurator = SpringConfigurator.class, encoders = MessageEncoder.class,
        decoders = MessageDecoder.class)
public class ChatEndpoint {
    private static final Logger logger = LogManager.getLogger(ChatEndpoint.class);
    private static List<Session> sessions = new LinkedList<>();
    private Session session;
    private String username = "anonimus";
    private MessageService messageService;

    @Autowired
    public ChatEndpoint(MessageService messageService) {
        logger.info("ChatEndpoint()");
        this.messageService = messageService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        logger.info("onOpen(account = {})", username);
        this.session = session;
        this.username = username;
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        logger.info("onMessage( message = {})", message);
        message.setUsernameSender(this.username);
        sessions.forEach(s -> {
                    if (s == this.session) return;
                    try {
                        s.getBasicRemote().sendObject(message);
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                }
        );
        messageService.createMassage(message);
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("onClose()");
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("onError()");
        throwable.printStackTrace();
    }

}
