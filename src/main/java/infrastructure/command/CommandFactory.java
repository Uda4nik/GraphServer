package infrastructure.command;

import application.ports.command.Command;

public interface CommandFactory {
    Command create(String message);
}
