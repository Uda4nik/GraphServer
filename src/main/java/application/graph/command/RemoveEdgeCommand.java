package application.graph.command;

import application.ports.command.Command;

import java.util.Objects;

public class RemoveEdgeCommand implements Command {
    private final String node1;
    private final String node2;

    public RemoveEdgeCommand(String node1, String node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public String getNode1() {
        return node1;
    }

    public String getNode2() {
        return node2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoveEdgeCommand that = (RemoveEdgeCommand) o;
        return Objects.equals(node1, that.node1) &&
                Objects.equals(node2, that.node2);
    }

    @Override
    public int hashCode() {

        return Objects.hash(node1, node2);
    }

    @Override
    public String toString() {
        return "RemoveEdgeCommand{" +
                "node1='" + node1 + '\'' +
                ", node2='" + node2 + '\'' +
                '}';
    }
}
