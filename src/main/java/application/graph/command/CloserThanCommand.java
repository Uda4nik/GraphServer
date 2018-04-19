package application.graph.command;

import application.ports.command.Command;

import java.util.Objects;

public class CloserThanCommand implements Command {

    private final String node;
    private final int weight;

    public CloserThanCommand(String node, int weight) {
        this.node = node;
        this.weight = weight;
    }

    public String getNode() {
        return node;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CloserThanCommand that = (CloserThanCommand) o;
        return weight == that.weight &&
                Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {

        return Objects.hash(node, weight);
    }

    @Override
    public String toString() {
        return "CloserThanCommand{" +
                "node='" + node + '\'' +
                ", weight=" + weight +
                '}';
    }
}
