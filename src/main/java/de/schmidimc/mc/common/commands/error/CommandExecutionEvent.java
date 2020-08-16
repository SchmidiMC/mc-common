package de.schmidimc.mc.common.commands.error;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandExecutionEvent {
    private final CommandExecutionType type;
    private final CommandSender sender;
    private final Command command;
    private final String label;
    private final String[] args;

    public CommandExecutionEvent(CommandExecutionType type, CommandSender sender, Command command, String label, String[] args) {
        this.type = type;
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
    }

    public CommandExecutionType getType() {
        return type;
    }

    public CommandSender getSender() {
        return sender;
    }

    public Command getCommand() {
        return command;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArgs() {
        return args;
    }
}
