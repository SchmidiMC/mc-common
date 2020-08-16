package de.schmidimc.mc.common.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommentConfigReader {
	
	private final File file;
	private int commentCounter; 
	
	public CommentConfigReader(final File file) {
		this.file = file;
	}
	
	public File readConfigFile() {
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			final String content= lines.stream().map(this::maskComment).collect(Collectors.joining("\n"));
			Files.write(file.toPath(), Arrays.asList(content), StandardCharsets.UTF_8);
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return file;
		}
	}
	
	private String maskComment(final String comment) {
		return comment.replaceAll("#", "COMMENT_#_" + commentCounter++);
	}
}
