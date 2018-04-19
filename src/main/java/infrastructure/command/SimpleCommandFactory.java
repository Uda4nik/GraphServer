package infrastructure.command;

import application.graph.command.*;
import application.ports.command.Command;

import static application.ports.command.Command.NOT_SUPORTED;

public class SimpleCommandFactory implements CommandFactory {

    private static final String ADD_NODE_MESSAGE_HEAD = "ADD NODE ";
    private static final String ADD_EDGE_MESSAGE_HEAD = "ADD EDGE ";
    private static final String REMOVE_NODE_MESSAGE_HEAD = "REMOVE NODE ";
    private static final String REMOVE_EDGE_MESSAGE_HEAD = "REMOVE EDGE ";
    private static final String SHORTEST_PATH_MESSAGE_HEAD = "SHORTEST PATH ";
    private static final String CLOSER_THAN_MESSAGE_HEAD = "CLOSER THAN ";

    @Override
    public Command create(String message) {
        if (message.startsWith(ADD_NODE_MESSAGE_HEAD)) {
            return new AddNodeCommand(message.substring(ADD_NODE_MESSAGE_HEAD.length()));
        } else if (message.startsWith(ADD_EDGE_MESSAGE_HEAD)) {
            return createAddEdgeCommand(message.substring(ADD_EDGE_MESSAGE_HEAD.length()));
        } else if (message.startsWith(REMOVE_NODE_MESSAGE_HEAD)) {
            return new RemoveNodeCommand(message.substring(REMOVE_NODE_MESSAGE_HEAD.length()));
        } else if (message.startsWith(REMOVE_EDGE_MESSAGE_HEAD)) {
            return createRemoveEdgeCommand(message.substring(REMOVE_EDGE_MESSAGE_HEAD.length()));
        } else if (message.startsWith(SHORTEST_PATH_MESSAGE_HEAD)) {
            return createShortestPathCommand(message.substring(SHORTEST_PATH_MESSAGE_HEAD.length()));
        } else if (message.startsWith(CLOSER_THAN_MESSAGE_HEAD)) {
            return createCloserThanCommand(message.substring(CLOSER_THAN_MESSAGE_HEAD.length()));
        }
        return NOT_SUPORTED;
    }

    private Command createCloserThanCommand(String arguments) {
        String[] split = arguments.split(" ");
        return new CloserThanCommand(split[1], Integer.parseInt(split[0]));
    }

    private ShortestPathCommand createShortestPathCommand(String arguments) {
        String[] split = arguments.split(" ");
        return new ShortestPathCommand(split[0], split[1]);
    }

    private AddEdgeCommand createAddEdgeCommand(String arguments) {
        String[] split = arguments.split(" ");
        return new AddEdgeCommand(split[0], split[1], Integer.parseInt(split[2]));
    }

    private RemoveEdgeCommand createRemoveEdgeCommand(String arguments) {
        String[] split = arguments.split(" ");
        return new RemoveEdgeCommand(split[0], split[1]);
    }
}
