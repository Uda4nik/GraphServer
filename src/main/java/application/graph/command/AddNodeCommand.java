package application.graph.command;

import application.ports.command.Command;

import java.util.Objects;

public class AddNodeCommand implements Command {
    private final String nodeName;

    public AddNodeCommand(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddNodeCommand that = (AddNodeCommand) o;
        return Objects.equals(nodeName, that.nodeName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nodeName);
    }

    @Override
    public String toString() {
        return "AddNodeCommand{" +
                "nodeName='" + nodeName + '\'' +
                '}';
    }
}
