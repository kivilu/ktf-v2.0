package com.kivi.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpClientMain {

	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
					.handler(new EchoClientHandler());

			// Start the client.
			ChannelFuture f = b.connect("::1", 1122).sync();

			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Shut down the event loop to terminate all threads.
			group.shutdownGracefully();
		}

	}

	private static class EchoClientHandler extends ChannelInboundHandlerAdapter {

		private final ByteBuf firstMessage;

		/**
		 * Creates a client-side handler.
		 */
		public EchoClientHandler() {
			firstMessage = Unpooled.buffer(256);
			for (int i = 0; i < firstMessage.capacity(); i++) {
				firstMessage.writeByte((byte) i);
			}
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) {

			ctx.writeAndFlush(firstMessage);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			ctx.write(msg);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			// Close the connection when an exception is raised.
			cause.printStackTrace();
			ctx.close();
		}

	}

}
