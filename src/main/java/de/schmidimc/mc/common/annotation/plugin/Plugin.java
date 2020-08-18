package de.schmidimc.mc.common.annotation.plugin;

import de.schmidimc.mc.common.annotation.ApiVersion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes extending {@link org.bukkit.plugin.java.JavaPlugin} and that are annotated with the {@link Plugin}
 * annotation will automatically generate a {@code plugin.yml} filled with the values provided by this annotation.
 * Additionally it creates a {@code command:} and {@code permissions:} block if they are added via other annotations.
 *
 * @see DependsOn
 * @see SoftDependsOn
 * @see LoadBefore
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Plugin {

    /**
     * This attribute is the name of your plugin. It must consist of all alphanumeric characters and underscores.
     * This is used to determine the name of the plugin's data folder.
     */
    String name();

    /**
     *  This attribute points to the class of your plugin that extends {@link org.bukkit.plugin.java.JavaPlugin}.
     *  This must contain the full namespace including the class file itself.
     */
    String main();

    /**
     * This is the version of your plugin. If you do not specify a version it defaults to 1.0.0
     */
    String version() default "1.0.0";

    /**
     * Uniquely identifies who developed this plugin. Used in some server error messages to provide helpful feedback
     * on who to contact when an error occurs. A SpigotMC.org forum handle or email address is recommended.
     */
    String author() default "";

    /**
     * Allows you to list multiple authors, if it is a collaborative project. See {@link #author()}.
     */
    String[] authors() default "";

    /**
     * The version of the API your plugin uses.
     * This will signal to the server that your plugin has been coded with a specific server version in mind, and that
     * it should not apply any sort of backwards compatibility measures. As a result you will also need to make sure
     * that you have programmed your code to account for reading of older configurations, data, etc... .
     * Each server version can decide how compatibility is achieved, unknown or future versions will prevent the
     * plugin from enabling. As of the 1.14 release, the api-version 1.13 is still allowed - however future versions
     * may drop backwards support based on this version.
     */
    ApiVersion apiVersion() default ApiVersion.NONE;

    /**
     * This is a human friendly description of the functionality your plugin provides.
     * The description can have multiple lines.
     */
    String description() default "";

    /**
     * The plugin's or author's website. If you have no dedicated website, a link to the page where this plugin is listed is recommended.
     */
    String website() default "";

    /**
     * The name to use when logging to console instead of the plugin's name.
     */
    String prefix() default "";
}
