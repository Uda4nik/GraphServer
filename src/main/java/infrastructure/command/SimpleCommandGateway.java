package infrastructure.command;

import application.ports.command.Command;
import application.ports.command.CommandResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class SimpleCommandGateway {
    private final Map<Class<?>, CommandHandler> handlers = new HashMap<>();

    public synchronized CommandResult dispatchCommand(Command command) {
        Objects.requireNonNull(command);
        return handlers.get(command.getClass()).handle(command);
    }

    public <C extends Command> void register(CommandHandler<C> handler, Class<C> cClass) {
        requireNonNull(handler);
        requireNonNull(cClass);
        handlers.put(cClass, handler);
    }
}
