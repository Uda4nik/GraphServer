package infrastructure.command;

import application.ports.command.Command;
import application.ports.command.Command.NotSupportedCommand;
import application.ports.command.CommandResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SimpleCommandGatewayTest {

    private SimpleCommandGateway cut;

    @BeforeMethod
    public void setUp() throws Exception {
        cut = new SimpleCommandGateway();
    }

    @Test
    public void canRegisterAndInvokeHandler() throws Exception {
        cut.register(c -> CommandResult.EMPTY, NotSupportedCommand.class);
        CommandResult result = cut.dispatchCommand(Command.NOT_SUPORTED);
        assertThat(result).isEqualTo(CommandResult.EMPTY);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void canntDispatNull() throws Exception {
        cut.dispatchCommand(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void canntRegisterNullHandler() throws Exception {
        cut.register(null, NotSupportedCommand.class);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void canntRegisterNullCommandClass() throws Exception {
        cut.register(c -> CommandResult.EMPTY, null);
    }
}