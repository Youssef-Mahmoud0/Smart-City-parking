package com.databaseProject.backend.handler;

import com.databaseProject.backend.entity.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Component
@Scope("singleton")
public class NotificationHandler extends TextWebSocketHandler {

    private final Map<Integer, WebSocketSession> sessions;
    private final ObjectMapper objectMapper;

    @Autowired
    public NotificationHandler(Map<Integer, WebSocketSession> sessions, ObjectMapper objectMapper) {
        this.sessions = sessions;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery();
        String driverID = query != null && query.contains("=") ? query.split("=")[1] : null;
        if (driverID != null) {
            sessions.put(Integer.parseInt(driverID), session);
            System.out.println("Driver ID: " + driverID + " connected.");
        } else {
            System.err.println("Driver ID not provided in query parameters");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.entrySet().removeIf(entry -> entry.getValue().equals(session));
        System.out.println("Session closed. Remaining sessions: " + sessions.size());
    }

    public void sendNotification(Notification notification) throws Exception {
        WebSocketSession session = sessions.get(notification.getDriverID());
        if (session != null && session.isOpen()) {
            String payload = objectMapper.writeValueAsString(notification);
            session.sendMessage(new TextMessage(payload));
//            System.out.println("Notification sent to driver ID: " + notification.getDriverID());
        } else {
            System.err.println("No open session found for driver ID: " + notification.getDriverID());
        }
    }
}
