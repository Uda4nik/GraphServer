package infrastructure.command;

import application.graph.command.AddEdgeCommand;
import application.graph.command.AddNodeCommand;
import application.graph.command.RemoveEdgeCommand;
import application.graph.command.RemoveNodeCommand;
import application.ports.command.Command;
import org.testng.annotations.Test;

import static application.ports.command.Command.NOT_SUPORTED;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleCommandFactoryTest {
    SimpleCommandFactory cut = new SimpleCommandFactory();

    @Test
    public void shouldCreateUnknownCommandIfGivenUnsupportedMessage() throws Exception {
        Command result = cut.create("bla bla bla");
        assertThat(result).isEqualTo(NOT_SUPORTED);
    }

    @Test
    public void shouldCreateAddNodeCommand() throws Exception {
        Command result = cut.create("ADD NODE test");
        assertThat(result).isEqualTo(new AddNodeCommand("test"));
    }

    @Test
    public void shouldCreateRemoveNodeCommand() throws Exception {
        Command result = cut.create("REMOVE NODE test");
        assertThat(result).isEqualTo(new RemoveNodeCommand("test"));
    }

    @Test
    public void shouldCreateAddEdgeCommand() throws Exception {
        Command result = cut.create("ADD EDGE node1 node2 42");
        assertThat(result).isEqualTo(new AddEdgeCommand("node1","node2",42));
    }


    @Test
    public void shouldCreateRemoveEdgeCommand() throws Exception {
        Command result = cut.create("REMOVE EDGE node1 node2");
        assertThat(result).isEqualTo(new RemoveEdgeCommand("node1","node2"));
    }
}