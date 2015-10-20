package com.rabbit.gui.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated type or field can be used in the gui layout
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface LayoutComponent {}
