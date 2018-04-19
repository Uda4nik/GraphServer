package application.graph.command;

import application.ports.command.Command;

import java.util.Objects;

public class AddEdgeCommand implements Command {
    private final String node1;
    private final String node2;
    private final int weight;

    public AddEdgeCommand(String node1, String node2, int weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }

    public String getNode1() {
        return node1;
    }

    public String getNode2() {
        return node2;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddEdgeCommand that = (AddEdgeCommand) o;
        return weight == that.weight &&
                Objects.equals(node1, that.node1) &&
                Objects.equals(node2, that.node2);
    }

    @Override
    public int hashCode() {

        return Objects.hash(node1, node2, weight);
    }

    @Override
    public String toString() {
        return "AddEdgeCommand{" +
                "node1='" + node1 + '\'' +
                ", node2='" + node2 + '\'' +
                ", weight=" + weight +
                '}';
    }
}
