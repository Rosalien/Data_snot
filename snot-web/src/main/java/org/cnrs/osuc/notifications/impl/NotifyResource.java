package org.inra.ecoinfo.notifications.impl;

import org.inra.ecoinfo.notifications.entity.Notification;
import org.primefaces.push.EventBus;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PathParam;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;
import org.primefaces.push.impl.JSONEncoder;

/**
 *
 * @author tcherniatinsky
 */
@PushEndpoint("/notify/{user}")
@Singleton
public class NotifyResource {

    @PathParam(value = "user")
    String login;

    /**
     *
     */
    @OnOpen
    public void onOpen() {
    }

    /**
     *
     * @param r
     */
    @OnOpen
    public void onOpen(RemoteEndpoint r) {
    }

    /**
     *
     * @param r
     * @param e
     */
    @OnOpen
    public void onOpen(RemoteEndpoint r, EventBus e) {
    }

    /**
     *
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @param notification
     * @return
     */
    @OnMessage(encoders = {JSONEncoder.class})
    public String onMessage(Notification notification) {
        return notification.getMessage();
    }
}
