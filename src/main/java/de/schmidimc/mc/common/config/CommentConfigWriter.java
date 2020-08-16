package de.schmidimc.mc.common.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.StringJoiner;

public class CommentConfigWriter {
	private final File configFile;
	
	public CommentConfigWriter(final File configFile) {
		this.configFile = configFile;
	}

	public void write(final String[] header, final String content) throws IOException {
		final String preparedContent = prepareContent(header, content);
		Files.write(configFile.toPath(), Arrays.asList(preparedContent), StandardCharsets.UTF_8);
	}
	
	private String prepareContent(final String[] header, final String content) {
		final StringJoiner joiner = new StringJoiner("\n");
		if(header != null && header.length > 0) {
			for(String line : header) {
				joiner.add("# " + line);
			}
			joiner.add("");
		}
		
		for(String line : content.split("\n")) {
			System.out.println(line.replaceAll("COMMENT_#_\\d:\\s+", "# "));
			joiner.add(line.replaceAll("COMMENT_#_\\d:\\s+", "# "));
		}
		return joiner.toString();
	}
	
}
