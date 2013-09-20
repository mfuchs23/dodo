package org.dbdoclet.test.option;

import junit.framework.TestCase;

import org.dbdoclet.log.Logger;
import org.dbdoclet.service.FileServices;

public class OptionTestCase extends TestCase {

    private static Log logger = LogFactory.getLog(OptionTestCase.class.getName());

    protected String sourcePath;
    protected String tmpPath;

    public OptionTestCase(String name) {
        super(name);
    }

    protected void setUp() {
 
        String testPath = System.getProperty("test.dir");

        sourcePath = FileServices.appendPath(testPath, "options");
        sourcePath = FileServices.appendPath(sourcePath, "java");
        sourcePath = FileServices.appendPath(sourcePath, "org");
        sourcePath = FileServices.appendPath(sourcePath, "dbdoclet");
        sourcePath = FileServices.appendPath(sourcePath, "test");
        sourcePath = FileServices.appendPath(sourcePath, "option");

        tmpPath = FileServices.appendPath(testPath, "tmp");
        tmpPath = FileServices.appendPath(tmpPath, "options");
        
    }
}
