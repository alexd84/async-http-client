package test;


import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpResponse;


public class HttpClientHandler extends SimpleChannelUpstreamHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        if (e.getMessage() instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) e.getMessage();
            ChannelBuffer buf = response.getContent();
            byte[] bytes = new byte[buf.capacity()];
            buf.readBytes(bytes);
            System.out.println("\n==>>response: " + new String(bytes));
        } else {
            System.out.println("\n==>>response: " + e.getMessage());
        }
    }
}
