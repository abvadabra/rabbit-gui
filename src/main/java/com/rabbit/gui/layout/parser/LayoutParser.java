package com.rabbit.gui.layout.parser;

import java.net.URI;

import com.rabbit.gui.show.LayoutShow;

/**
 * Parser of gui layout of any type
 *
 */
public interface LayoutParser {
    
    /**
     * Returns parsed show
     * @param path - location of parsable file
     * @return parsed show
     */
    LayoutShow from(URI path);

}
