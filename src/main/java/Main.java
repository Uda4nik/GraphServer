import infrastructure.dicontext.SpringConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static final String MY_LINE_SEPARATOR = System.getProperty("line.separator");

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(SpringConfig.class);
        ServerBootstrap serverBootstrap = config.getBean(ServerBootstrap.class);
        ChannelFuture f = serverBootstrap.bind(50_000).sync();
        f.channel().closeFuture().sync();;
    }
}

