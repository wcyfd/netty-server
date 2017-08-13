package com.randioo.mahjong_public_server.net;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认使用的监听器
 * 
 * @author AIM
 *
 */
public class DefaultRPCListener implements RPCListener {

    private Logger logger = LoggerFactory.getLogger(RPCListener.class);

    @Override
    public void execute(RPCChannel channel) {
        InetSocketAddress address = (InetSocketAddress) channel.getSocketAddress();
        logger.info("server start => socket bind on port : " + address.getPort());
    }

}
