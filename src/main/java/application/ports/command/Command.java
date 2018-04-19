package application.ports.command;

public interface Command {
    Command NOT_SUPORTED = new NotSupportedCommand();

    class NotSupportedCommand implements Command {
        private NotSupportedCommand() {
        }
    }
}
