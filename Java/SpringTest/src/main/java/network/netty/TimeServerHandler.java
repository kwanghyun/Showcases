package network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	/*
	 * the channelActive() method will be invoked when a connection is
	 * established and ready to generate traffic
	 */
	@Override
	public void channelActive(final ChannelHandlerContext ctx) { // (1)
		System.out.println("channelActive() started...");
		System.out.println(new UnixTime());
		ChannelFuture f = ctx.writeAndFlush(new UnixTime());
	    f.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
