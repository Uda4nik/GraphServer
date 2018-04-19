package application.ports.command;

import java.util.Objects;

public class CommandResult {

    public static final CommandResult EMPTY = of(null);

    public final String complitionStatus;

    public static CommandResult of(String status) {
        return new CommandResult(status);
    }

    private CommandResult(String complitionStatus) {
        this.complitionStatus = complitionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandResult that = (CommandResult) o;
        return Objects.equals(complitionStatus, that.complitionStatus) &&
                Objects.equals(EMPTY, that.EMPTY);
    }

    @Override
    public int hashCode() {

        return Objects.hash(complitionStatus, EMPTY);
    }

    @Override
    public String toString() {
        return "CommandResult{" +
                "complitionStatus='" + complitionStatus + '\'' +
                ", EMPTY=" + EMPTY +
                '}';
    }
}
