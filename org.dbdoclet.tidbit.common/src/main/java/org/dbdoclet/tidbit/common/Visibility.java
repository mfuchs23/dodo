package org.dbdoclet.tidbit.common;

public enum Visibility {
    PRIVATE, PROTECTED, PACKAGE, PUBLIC;
    
    public String getValue() {
        return toString().toLowerCase();
    }
}
