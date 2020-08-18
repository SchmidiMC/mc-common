package de.schmidimc.mc.common.annotation.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to declare a list of plugins that your plugin to have full functionality. Use the name attribute
 * of the desired plugins in order to specify the dependency. Your plugin will load after any plugins listed here.
 * Circular soft dependencies are loaded arbitrarily.
 * Make sure to only use this annotation together with {@link Plugin}
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface SoftDependsOn {
    String[] value();
}
