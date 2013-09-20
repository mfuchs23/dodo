package org.dbdoclet.test.sample;

import java.lang.annotation.Documented;

/**
 * Annotation type @Todo something to do
 **/
@Documented
public @interface Todo {

    /** Standardwert <code>value</code> */
    String value();
}
