package com.randioo.mahjong_public_server.net.mina2;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.randioo.mahjong_public_server.net.RPCChannel;
import com.randioo.mahjong_public_server.net.RPCServer;
import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.randioo_server_base.error.ActionSupportFakeException;
import com.randioo.randioo_server_base.navigation.Navigation;
import com.randioo.randioo_server_base.protocol.protobuf.ProtoCodecFactory;
import com.randioo.randioo_server_base.template.IActionSupport;

public class Mina2RPCServer extends RPCServer {
    private NioSocketAcceptor ioAcceptor;

    @Override
    public void init() {
        ioAcceptor = new NioSocketAcceptor();

        ioAcceptor.getSessionConfig().setReadBufferSize(4096);

        ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        ioAcceptor.getSessionConfig().setSendBufferSize(4096);
        ioAcceptor.getSessionConfig().setTcpNoDelay(true);

        DefaultIoFilterChainBuilder chain = ioAcceptor.getFilterChain();

        chain.addLast("codec", new ProtocolCodecFilter(new ProtoCodecFactory(cs, null)));
        // if (heartFilter != null)
        // chain.addLast("keepalive", heartFilter);

        chain.addLast("threadpool", new ExecutorFilter(Executors.newCachedThreadPool()));

        ioAcceptor.setHandler(new IoHandler() {

            private Mina2Session createSession(IoSession session) {
                return new Mina2Session(session);
            }

            @Override
            public void sessionCreated(IoSession session) throws Exception {
                rpcHandler.open(createSession(session));
            }

            @Override
            public void sessionOpened(IoSession session) throws Exception {
            }

            @Override
            public void sessionClosed(IoSession session) throws Exception {
                rpcHandler.close(createSession(session));
            }

            @Override
            public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

            }

            @Override
            public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
                rpcHandler.exception(createSession(session), cause);
            }

            @Override
            public void messageReceived(IoSession session, Object messageObj) throws Exception {

                rpcHandler.read(createSession(session), messageObj);
            }

            @Override
            public void messageSent(IoSession session, Object message) throws Exception {
                rpcHandler.sent(createSession(session), message);

            }

        });
    }

    @Override
    public void stop() {
        ioAcceptor.dispose(true);
    }

    @Override
    protected void fireStart() {
        try {
            ioAcceptor.setReuseAddress(true);

            SocketAddress socketAddress = new InetSocketAddress(port);

            RPCChannel channel = new RPCChannel();
            channel.setSocketAddress(socketAddress);

            ioAcceptor.bind(socketAddress);
            if (rpcListener != null) {
                rpcListener.execute(channel);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
