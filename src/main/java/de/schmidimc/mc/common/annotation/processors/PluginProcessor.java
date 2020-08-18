package de.schmidimc.mc.common.annotation.processors;

import com.google.auto.service.AutoService;
import de.schmidimc.mc.common.annotation.Command;
import de.schmidimc.mc.common.annotation.Listener;
import de.schmidimc.mc.common.annotation.plugin.Plugin;
import de.schmidimc.mc.common.helper.ReflectionHelper;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SupportedAnnotationTypes("de.schmidimc.mc.common.annotation.plugin.Plugin")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class PluginProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {

            final Set<? extends Element> plugins = roundEnv.getElementsAnnotatedWith(annotation);
            if (plugins.isEmpty()) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "No Plugin class was found");
                return false;
            }

            if (plugins.size() > 1) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "You should only specify one plugin class for your project.");
                return false;
            }

            final List<Command> commands = roundEnv.getElementsAnnotatedWith(Command.class).stream()
                    .map(element -> element.getAnnotation(Command.class))
                    .collect(Collectors.toList());

            plugins.stream()
                    .findFirst()
                    .map(plugin -> plugin.getAnnotation(Plugin.class))
                    .ifPresent(pluginAnnotation -> {

                        writePluginYamlFile(pluginAnnotation, commands);
                        writePluginInitializerClass(roundEnv, pluginAnnotation, commands);
                    });
        }


        return false;
    }

    private void writePluginInitializerClass(RoundEnvironment roundEnv, Plugin pluginAnnotation, List<Command> commands) {
        try {
            final JavaFileObject source = processingEnv.getFiler().createSourceFile("PluginInitializer");
            try (PrintWriter out = new PrintWriter(source.openWriter())) {

                out.printf("package %s;\n", pluginAnnotation.main().substring(0, pluginAnnotation.main().lastIndexOf(".")));

                out.println("public final class PluginInitializer {");
                out.println();

                out.printf("\tpublic static final void init(final %s plugin) {\n", pluginAnnotation.main());

                writeCommandRegistrationLines(roundEnv, commands, out);
                writeListenerRegistrationLines(roundEnv, out);

                out.println("\t\t");
                out.println("\t}");

                out.println("}");

            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "FAILED " + e.toString());
            e.printStackTrace();
        }
    }

    private void writeCommandRegistrationLines(RoundEnvironment roundEnv, List<Command> commands, PrintWriter out) {
        List<String> commandClassNames = roundEnv.getElementsAnnotatedWith(Command.class).stream()
                .map(TypeElement.class::cast)
                .map(ReflectionHelper::getFullyQualifiedName)
                .collect(Collectors.toList());

        // register different commands
        // plugin.getCommand("<commandName>").setExecutor(new fully.qualified.Name(plugin));
        IntStream.range(0, commandClassNames.size())
                .mapToObj(idx -> String.format("\t\tplugin.getCommand(\"%s\").setExecutor(new %s(plugin));\n", commands.get(idx).name(), commandClassNames.get(idx)))
                .forEach(out::println);
    }

    private void writeListenerRegistrationLines(RoundEnvironment roundEnv, PrintWriter out) {
        roundEnv.getElementsAnnotatedWith(Listener.class).stream()
                .map(TypeElement.class::cast)
                .map(ReflectionHelper::getFullyQualifiedName)
                .forEach(name -> out.printf("\t\tplugin.getServer().getPluginManager().registerEvents(new %s(plugin), plugin);\n", name));
    }

    private void writePluginYamlFile(Plugin pluginAnnotation, List<Command> commands) {
        try {
            final FileObject resource = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "plugin.yml");
            try (PrintWriter out = new PrintWriter(resource.openWriter())) {
                out.println("name: " + pluginAnnotation.name());
                out.println("version: " + pluginAnnotation.version());
                out.println("description: Hello 1234");
                out.println("author: " + pluginAnnotation.author());
                out.println("main: " + pluginAnnotation.main());
                out.println();

                out.println("commands:");
                commands.forEach(command -> {
                    out.println(" " + command.name() + ":");
                    out.println("    description: " + command.description());
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, Arrays.stream(command.aliases()).collect(Collectors.joining(", ", "[", "]")) );
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, command.aliases().length + "" );
                    String aliasString = Arrays.stream(command.aliases()).collect(Collectors.joining(", ", "[", "]"));
                    if(!aliasString.equals("[]")) {
                        out.println("    aliases: " + aliasString);
                    }
                    out.println("    usage: " + command.usage());
                });
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "FAILED " + e.toString());
            e.printStackTrace();
        }
    }
}
