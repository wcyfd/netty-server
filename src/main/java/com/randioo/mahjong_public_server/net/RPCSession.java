package com.randioo.mahjong_public_server.net;

public abstract class RPCSession {

    public abstract <T> void setValue(String key, T value);

    public abstract <T> T getValue(String key);

    public abstract void write(Object obj);

    public abstract boolean isConnected();
}
