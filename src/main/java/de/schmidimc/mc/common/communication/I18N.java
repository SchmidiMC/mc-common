package de.schmidimc.mc.common.communication;

import org.bukkit.ChatColor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class I18N {

    private static Properties properties;

    public static void initialize(final String path) throws IOException {
        properties = new Properties();
        try (final FileReader reader = new FileReader(new File(path))) {
            properties.load(reader);
        }
    }

    public static String get(final String key, final String... params) {
        String value = properties.getProperty(key, key);

        for (int i = 0; i < params.length; i++) {
            value = value.replace("{" + i + "}", params[i]);
        }

        final ChatColor[] colors = ChatColor.values();
        for(ChatColor color : colors) {
            value = value.replace("{" + color.name() + "}", color.toString());
        }

        return value;
    }

}