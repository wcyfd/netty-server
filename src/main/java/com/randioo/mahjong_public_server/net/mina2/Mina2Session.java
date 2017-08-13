package com.randioo.mahjong_public_server.net.mina2;

import org.apache.mina.core.session.IoSession;

import com.randioo.mahjong_public_server.net.RPCSession;

public class Mina2Session extends RPCSession {

    private IoSession session;

    public Mina2Session(IoSession session) {
        this.session = session;
    }

    @Override
    public void write(Object obj) {
        session.write(obj);
    }

    @Override
    public boolean isConnected() {
        return session.isConnected();
    }

    @Override
    public <T> T getValue(String key) {
        @SuppressWarnings("unchecked")
        T t = (T) session.getAttribute(key);
        return t;
    }

    @Override
    public <T> void setValue(String key, T value) {
        session.setAttribute(key, value);
    }

}
