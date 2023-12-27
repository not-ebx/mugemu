package net.mugeemu.ms.connection.netty;

import net.mugeemu.ms.connection.packet.Login;
import net.mugeemu.ms.client.Client;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.mugeemu.ms.connection.crypto.MapleCrypto;
import net.mugeemu.ms.world.Channel;
import org.apache.log4j.LogManager;
import net.mugeemu.ms.handlers.EventManager;

import static net.mugeemu.ms.connection.netty.NettyClient.CLIENT_KEY;

public class ChannelAcceptor implements Runnable {

    public Channel channel;
    private static final org.apache.log4j.Logger log = LogManager.getRootLogger();

    @Override
    public void run() {
        // Taken from http://netty.io/wiki/user-guide-for-4.x.html

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) {

                    ch.pipeline().addLast(new PacketDecoder(), new ChannelHandler(), new PacketEncoder());

                    byte[] siv = new byte[]{70, 114, 122, 82};
                    byte[] riv = new byte[]{82, 48, 120, 115};

                    Client c = new Client(ch, siv, riv);
                    // remove after debug stage
                    c.write(Login.sendConnect(riv, siv));

                    ch.attr(CLIENT_KEY).set(c);
                    ch.attr(Client.CRYPTO_KEY).set(new MapleCrypto());

                    EventManager.addFixedRateEvent(c::sendPing, 0, 10000);
                }
            });

            b.childOption(ChannelOption.TCP_NODELAY, true);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(channel.getPort()).sync();
            log.info(String.format("Channel %d-%d listening on port %d", channel.getWorldId(), channel.getChannelId(), channel.getPort()));
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
