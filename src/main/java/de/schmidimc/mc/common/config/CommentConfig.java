package de.schmidimc.mc.common.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CommentConfig {

	private int commentsCounter;
	private String[] header;
	
	private File file;
	private FileConfiguration configuration;

	private CommentConfigReader reader;
	private CommentConfigWriter writer;

	public CommentConfig(final File configFile) {
		reader = new CommentConfigReader(configFile);
		writer = new CommentConfigWriter(configFile);
		file = reader.readConfigFile();
		configuration = YamlConfiguration.loadConfiguration(file);
	}

	public Object get(final String path) {
		return configuration.get(path);
	}

	public Object get(final String path, final Object defaultValue) {
		return configuration.get(path, defaultValue);
	}

	public String getString(final String path) {
		return configuration.getString(path);
	}

	public String getString(final String path, final String defaultValue) {
		return configuration.getString(path, defaultValue);
	}

	public int getInt(final String path) {
		return configuration.getInt(path);
	}

	public int getInt(final String path, final int defaultValue) {
		return configuration.getInt(path, defaultValue);
	}

	public boolean getBoolean(final String path) {
		return configuration.getBoolean(path);
	}

	public boolean getBoolean(final String path, final boolean defaultValue) {
		return configuration.getBoolean(path, defaultValue);
	}

	public void createSection(final String path) {
		this.configuration.createSection(path);
	}

	public ConfigurationSection getConfigurationSection(final String path) {
		return configuration.getConfigurationSection(path);
	}

	public double getDouble(final String path) {
		return configuration.getDouble(path);
	}

	public double getDouble(final String path, final double defaultValue) {
		return configuration.getDouble(path, defaultValue);
	}

	public List<?> getList(final String path) {
		return configuration.getList(path);
	}

	public List<?> getList(final String path, final List<?> defaultValue) {
		return configuration.getList(path, defaultValue);
	}

	public boolean contains(final String path) {
		return configuration.contains(path);
	}

	public void removeKey(final String path) {
		configuration.set(path, null);
	}

	public void set(final String path, final Object value) {
		configuration.set(path, value);
	}

	public void set(final String path, final Object value, final String... comments) {
		for (String comment : comments) {
			if (!configuration.contains(path)) {
				configuration.set("COMMENT_#_" + commentsCounter, comment);
				commentsCounter++;
			}
		}

		configuration.set(path, value);
	}
	
	public void reloadConfig() {
		file = reader.readConfigFile();
        configuration = YamlConfiguration.loadConfiguration(file);
    }
 
    public void saveConfig() {
        try {
        	String config = configuration.saveToString();
            writer.write(header, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
    public Set<String> getKeys() {
        return configuration.getKeys(false);
    }
    
    public void setHeader(String[] header) {
		this.header = header;
	}

}
