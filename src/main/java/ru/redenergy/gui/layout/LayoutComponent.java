package ru.redenergy.gui.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated classes can be used in the gui layout
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LayoutComponent {
    /**
     * Returns variable names which can be changed thought json layout
     */
    String[] value();
}
