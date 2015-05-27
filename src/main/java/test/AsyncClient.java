package test;


import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.*;

import java.net.InetSocketAddress;

public class AsyncClient {
    private static final String HOST = "www.google.com";
    private static final String PATH = "/404/";
    private static final int PORT = 80;

    public static void main(String args[]) throws InterruptedException {
        ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory());
        bootstrap.setPipeline(Channels.pipeline(new HttpClientCodec(), new HttpClientHandler()));
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(HOST, PORT));
        Channel channel = future.await().getChannel();

        for (int i = 0; i < 5; i++) {
            makeAsyncGetRequst(channel); // request reusing existing http connection
        }
    }

    public static void makeAsyncGetRequst(Channel channel) throws InterruptedException {
        HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, PATH);
        HttpHeaders headers = request.headers();
        headers.set(HttpHeaders.Names.HOST, HOST);
        headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        channel.write(request);
        System.out.println("===request submitted===");
    }
}
