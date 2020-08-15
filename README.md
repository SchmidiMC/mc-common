# mc-common

<!-- ABOUT THE PROJECT -->
## About The Project
This library makes your life as a Minecraft Plugin Developer a lot easier. It faces different tasks and challanages that you have as a MC developer and simplifies them a lot. It covers things like automatically creating your `plugin.yml` file, registering your commands and listeners and provides utility such as a `AbstractCommand` class, localization of messages and other stuff. 
To get a closer look at the different topics have a look at the example section at the bottom of this readme.

This project is build with
- Maven
- Java 8
- Googles [AutoService](https://github.com/google/auto/tree/master/service) Library

## Getting Started

### Prerequisites
To get started using this library you need to fulfil some prerequisites. Firstly you should have a relativly good understanding of basic minecraft plugin development.
Secondly you should have a fully functional Maven installation on your local machine because we use Maven as our build and dependency management tool as well as at least Java 8 installed.

### How to start

DISLAIMER:
If you don't want to setup this by yourself you can use the [mc-common-skeleton project](https://github.com/Robatsky/mc-common-skeleton). The only thing you have to do with that setup is described in `1. Getting the jar`

1. Getting the jar

To start using this library you simply need to clone it and install it into your local maven repository using
```bash
mvn clean compile package
mvn install:install-file -Dfile=<path-to-jar> -DgroupId=<group-id> -DartifactId=<artifact-id> -Dversion=<version> -Dpackaging=jar
```

2. Setting up the pom.xml

If you've done that you can refer to that dependency in your pom file using the groupid, artifactid and version you provided above. In order to compile and generate the sources properly you should use the maven-compiler-plugin and set up the annotation processing configuration to use `de.schmidimc.mc.common.annotation.processors.PluginProcessor` as a annotation processor. Otherwise no resources will be generated.
Another plugin we recommend using is the maven-shade-plugin to make sure the dependency sources are packaged in your jar properly. We used recommend the following confgiuration but you can edit them ofc.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>2.1</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <outputDirectory>${mcCommonOutputDir}</outputDirectory>
                <outputFile>${mcCommonOutputDir}\${project.artifactId}.jar</outputFile>
            </configuration>
        </execution>
    </executions>
    <configuration>
        <filters>
            <filter>
                <artifact>*:*</artifact>
                <excludes>
                    <exclude>**/*.java</exclude>
                    <exclude>**/*.SF</exclude>
                    <exclude>**/*.DSA</exclude>
                </excludes>
            </filter>
        </filters>
    </configuration>
</plugin>
```

`${mcCommonOutputDir}` is a custom property that should be defined at the top of your pom. This is used to specify the path your jar should be build to. We recommend to override this property when packaging the jar using `-DmcCommonOutputDir=<path-to-jar>` instead of hardcoding it. If you work alone on your project feel free to ignore this property and hardcode it into your pom xml. This property is meant for multi-user teams where the location of the jar is not always the same.

3. IDE configuration

Lastly make sure to setup your IDE for annotation processing. This step depends on the IDE you are using.

## Features / Examples

### 1. plugin.yml generation

In order to generate the plugin.yml automatically you need to have your Plugin class (the class extending `JavaPlugin`) to be annotated with the `@Plugin` annotation. This annotation requires some additional parameters as well as offers optional values to be used.
```java
@Plugin(name = "Skeleton-Project", author="Schmidi", main="main.java.Start")
public class Start extends JavaPlugin {
    ...
}
```

