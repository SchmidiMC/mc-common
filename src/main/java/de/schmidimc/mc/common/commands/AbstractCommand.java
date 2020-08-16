package de.schmidimc.mc.common.commands;

import de.schmidimc.mc.common.commands.error.CommandExecutionEvent;
import de.schmidimc.mc.common.commands.error.CommandExecutionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommand implements CommandExecutor {

    private final int requiredArgumentsCount;

    public AbstractCommand(final int requiredArgumentsCount) {
        this.requiredArgumentsCount = requiredArgumentsCount;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args == null || args.length < requiredArgumentsCount) {
            onCommandError(new CommandExecutionEvent(CommandExecutionType.WRONG_ARGUMENTS, sender, command, label, args));
            return false;
        }

        if(!hasPermissions(sender)) {
            onCommandError(new CommandExecutionEvent(CommandExecutionType.MISSING_PERMISSION, sender, command, label, args));
            return false;
        }

        return execute(sender, command, label, args);
    }

    protected abstract boolean execute(CommandSender sender, Command command, String label, String[] args);
    protected abstract void onCommandError(CommandExecutionEvent event);

    protected boolean hasPermissions(final CommandSender sender) {
        return true;
    }
}
