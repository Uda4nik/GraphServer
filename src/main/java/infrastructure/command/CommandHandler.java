package infrastructure.command;

import application.ports.command.Command;
import application.ports.command.CommandResult;

@FunctionalInterface
public interface CommandHandler<C extends Command> {
    CommandResult handle(C command);
}
