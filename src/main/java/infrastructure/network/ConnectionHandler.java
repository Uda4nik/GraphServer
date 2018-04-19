package infrastructure.network;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.LineSeparator;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.util.UUID;

import static java.lang.String.format;


public class ConnectionHandler extends ChannelDuplexHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionHandler.class);
    private final static String CLIENT_GREET_PHRASE = "HI, I'M ";
    private final static String CLIENT_FAREWELL_PHRASE = "BYE MATE!";
    private static final String SERVER_GREETINGS = "HI, I'M %s%n";
    private final static String SERVER_GREET_RESPONSE = "HI %s%n";
    private final static String SERVER_FAREWELL_PHRASE = "BYE %s, WE SPOKE FOR %d MS%n";
    private long startTime;
    private String clientName;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(format(SERVER_GREETINGS, UUID.randomUUID()));
        startTime = System.currentTimeMillis();
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.debug("RECEIVED in connection handler: {}", msg);
        String message = (String) msg;
        message = message.trim().replaceAll("\n", "");
        if (clientName == null && message.startsWith(CLIENT_GREET_PHRASE)) {
            clientName = message.substring(CLIENT_GREET_PHRASE.length());
            LOGGER.info("STARTED CHANNEL WITH {}", clientName);
            ctx.writeAndFlush(format(SERVER_GREET_RESPONSE, clientName));
        } else if (CLIENT_FAREWELL_PHRASE.equals(message)) {
            ctx.writeAndFlush(format(SERVER_FAREWELL_PHRASE, clientName, System.currentTimeMillis() - startTime));
            ctx.close();
        } else {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            LOGGER.info("WAS WAITING FOR 30 seconds with {}", clientName);
            ctx.writeAndFlush(format(SERVER_FAREWELL_PHRASE, clientName, System.currentTimeMillis() - startTime));
            ctx.close();
        }
    }

}
