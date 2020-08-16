package de.schmidimc.mc.common.communication;

import org.bukkit.command.CommandSender;

import static org.bukkit.ChatColor.*;

public final class ChatHelper {
    public static String PREFIX ="";

    public static void setPrefix(final String prefix) {
        PREFIX = prefix;
    }

    public static final void say(final CommandSender sender, final String message) {
        sender.sendMessage(PREFIX + message);
    }

}
