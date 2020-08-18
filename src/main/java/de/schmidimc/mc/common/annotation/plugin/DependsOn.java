package de.schmidimc.mc.common.annotation.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to declare a list of plugins that your plugin requires to load.  Use the name attribute
 * of the desired plugins in order to specify the target. if any plugin listed here is not found your plugin will fail
 * to load. If multiple plugins list each other as depend, so that there are no plugins without an unloadable dependency,
 * all will fail to load.
 * Make sure to only use this annotation together with {@link Plugin}
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface DependsOn {
    String[] value();
}
