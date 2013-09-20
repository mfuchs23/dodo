package org.dbdoclet.test.format;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;
import org.dbdoclet.doclet.docbook.StyleBase;
import org.dbdoclet.log.Logger;
import org.dbdoclet.tag.docbook.Emphasis;
import org.dbdoclet.tag.docbook.IndexTerm;
import org.dbdoclet.tag.docbook.Primary;
import org.dbdoclet.tag.docbook.ProgramListing;

public class ListingFormatTests extends TestCase {

    private static Log logger = LogFactory.getLog(ListingFormatTests.class, Logger.INFO);

    private static String LSEP = System.getProperty("line.separator");

    public ListingFormatTests(String name) {
        super(name);
    }

    protected void setUp() {
        Logger.readConfiguration(new File("logger.properties"));
    }


}
