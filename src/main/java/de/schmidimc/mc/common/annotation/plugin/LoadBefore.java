package de.schmidimc.mc.common.annotation.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to declare a list of plugins that should be loaded after your plugin. Use the name attribute
 * of the desired plugins in order to specify the target. Your plugin will load before any plugins listed here.
 * Circular soft dependencies are loaded arbitrarily.
 * Make sure to only use this annotation together with {@link Plugin}
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface LoadBefore {
    String[] value();
}
