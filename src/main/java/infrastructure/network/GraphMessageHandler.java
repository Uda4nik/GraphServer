package infrastructure.network;

import application.ports.command.Command;
import application.ports.command.CommandResult;
import infrastructure.command.CommandFactory;
import infrastructure.command.SimpleCommandGateway;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class GraphMessageHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphMessageHandler.class);

    private static final String NOT_SUPPORTED_MESSAGE = "SORRY, I DIDN'T UNDERSTAND THAT\n";
    private final CommandFactory commandFactory;
    private final SimpleCommandGateway commandGateway;

    public GraphMessageHandler(CommandFactory commandFactory, SimpleCommandGateway commandGateway) {
        this.commandFactory = commandFactory;
        this.commandGateway = commandGateway;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        LOGGER.debug("RECEIVED IN GRAPH MESSAGE HANDLER: {}", message);
//        message = message.trim().replaceAll("\n", "");
        Command command = commandFactory.create(message);
        LOGGER.info("CREATED COMMAND: {}", command);
        if (command == Command.NOT_SUPORTED) {
            LOGGER.info("SEND NOT SUPPORTED TEMPLATE");
            ctx.writeAndFlush(NOT_SUPPORTED_MESSAGE);
        } else {
            CommandResult result = commandGateway.dispatchCommand(command);
            LOGGER.info("SEND {}", result.complitionStatus);
            ctx.writeAndFlush(result.complitionStatus + "\n");
        }

        super.channelRead(ctx, msg);
    }
}

