package application.graph;

import application.graph.command.*;
import application.ports.command.CommandResult;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.traverse.ClosestFirstIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphService.class);
    private final DirectedWeightedMultigraph<String, DefaultWeightedEdge> graph;
    private final ShortestPathAlgorithm<String, DefaultWeightedEdge> shortestPathAlgorithm;

    public GraphService(DirectedWeightedMultigraph<String, DefaultWeightedEdge> graph) {
        this.graph = graph;
        this.shortestPathAlgorithm = new DijkstraShortestPath(this.graph);

    }

    public CommandResult process(AddNodeCommand command) {
        boolean result = graph.addVertex(command.getNodeName());
        return result ? CommandResult.of("NODE ADDED") : CommandResult.of("ERROR: NODE ALREADY EXISTS");
    }

    public CommandResult process(AddEdgeCommand command) {
        try {
            DefaultWeightedEdge edge = graph.addEdge(command.getNode1(), command.getNode2());
            graph.setEdgeWeight(edge, command.getWeight());
        } catch (IllegalArgumentException exception) {
            return CommandResult.of("ERROR: NODE NOT FOUND");
        }
        return CommandResult.of("EDGE ADDED");
    }

    public CommandResult process(RemoveNodeCommand command) {
        boolean result = graph.removeVertex(command.getNodeName());
        return result ? CommandResult.of("NODE REMOVED") : CommandResult.of("ERROR: NODE NOT FOUND");
    }

    public CommandResult process(RemoveEdgeCommand command) {
        DefaultWeightedEdge removed = graph.removeEdge(command.getNode1(), command.getNode2());
        if(removed == null){
            return CommandResult.of("ERROR: NODE NOT FOUND");
        } else {
            while (removed != null){
                removed = graph.removeEdge(command.getNode1(), command.getNode2());
            }
            return CommandResult.of("EDGE REMOVED");
        }
    }

    public CommandResult process(ShortestPathCommand command) {
        double result = shortestPathAlgorithm.getPathWeight(command.getNode1(), command.getNode2());
        return result == Double.POSITIVE_INFINITY ? CommandResult.of("" + Integer.MAX_VALUE) : CommandResult.of("" + (int) result);
    }

    public CommandResult process(CloserThanCommand command) {
        List<String> closeNodes = getMatchingNodes(command);
        if (closeNodes.isEmpty()) {
            return CommandResult.of("ERROR: NODE NOT FOUND");
        } else {
            return CommandResult.of(closeNodes.stream()
                    .filter(node -> !node.equals(command.getNode()))
                    .sorted( )
                    .collect(Collectors.joining(",")));
        }
    }

    private List<String> getMatchingNodes(CloserThanCommand command) {
        ClosestFirstIterator<String, DefaultWeightedEdge> iterator = new ClosestFirstIterator<>(this.graph, command.getNode(), command.getWeight());
        List<String> accumulator = new ArrayList<>();
        iterator.forEachRemaining(accumulator::add);
        return accumulator;
    }

}
