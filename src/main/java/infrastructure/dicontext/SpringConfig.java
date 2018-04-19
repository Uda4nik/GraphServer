package infrastructure.dicontext;

import application.graph.GraphService;
import application.graph.command.*;
import infrastructure.command.SimpleCommandFactory;
import infrastructure.command.SimpleCommandGateway;
import infrastructure.network.ConnectionHandler;
import infrastructure.network.GraphMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public GraphService graphService() {
        return new GraphService(new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class));
    }

    @Bean
    public SimpleCommandGateway commandGateway() {
        return new SimpleCommandGateway();
    }

    @Bean
    public InitializingBean docFlowInitializer(GraphService graphService, SimpleCommandGateway commandGateway) {
        return () -> {
            commandGateway.register(graphService::process, AddNodeCommand.class);
            commandGateway.register(graphService::process, AddEdgeCommand.class);
            commandGateway.register(graphService::process, RemoveNodeCommand.class);
            commandGateway.register(graphService::process, RemoveEdgeCommand.class);
            commandGateway.register(graphService::process, ShortestPathCommand.class);
            commandGateway.register(graphService::process, CloserThanCommand.class);
        };
    }

    @Bean
    public GraphMessageHandler graphMessageHandler(SimpleCommandGateway commandGateWay) {
        return new GraphMessageHandler(new SimpleCommandFactory(), commandGateWay);
    }

    @Bean
    public ServerBootstrap serverBootstrap(GraphMessageHandler handler) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
//                .handler()
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(
                                new LineBasedFrameDecoder(90),
                                new StringDecoder(),
                                new StringEncoder(),
                               // new LoggingHandler(LogLevel.INFO),
                                new IdleStateHandler(30, 0, 0),
                                new ConnectionHandler(),
                                handler);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }
}
