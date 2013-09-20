package org.dbdoclet.test.option;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.FileServices;

public class UseAppendixTests extends OptionTestCase {

    private static Log logger = LogFactory.getLog(UseAppendixTests.class.getName());

    public UseAppendixTests(String name) {
        super(name);
    }

    public static Test suite() {

        return new TestSuite(UseAppendixTests.class);
    }

    public void testUseAppendix1() {

        try {

            String sourceFileName = FileServices.appendFileName(sourcePath, "UseAppendix.java");

            String destPath = FileServices.appendPath(tmpPath, "UseAppendix1");

            String[] cmd = { "dbdoclet", "-d" , destPath, sourceFileName };
            
            ExecResult result = ExecServices.exec(cmd);
            logger.info(result.getOutput());
            
            String fileName = FileServices.appendFileName(destPath, "Reference.xml");
            String buffer = FileServices.readToString(fileName);
            
            if (buffer.indexOf("<appendix>") != -1) {
                fail("Appendices shouldn't be created!");
            }

        } catch (Exception oops) {

            logger.fatal(getName() + " failed!", oops);
            fail("Exception: " + oops.getClass().getName());
        }

    }

    public void testUseAppendix2() {

        try {

            String sourceFileName = FileServices.appendFileName(sourcePath, "UseAppendix.java");

            String destPath = FileServices.appendPath(tmpPath, getName());

            String[] cmd = { "dbdoclet", "-d" , destPath, "--use-appendix", sourceFileName };
            
            ExecResult result = ExecServices.exec(cmd);
            logger.info(result.getOutput());
            
            String fileName = FileServices.appendFileName(destPath, "Reference.xml");
            String buffer = FileServices.readToString(fileName);

            if (buffer.indexOf("<appendix>") == -1) {
                fail("Appendices should be created!");
            }

        } catch (Exception oops) {
        
            fail("Exception: " + oops.getClass().getName());
        }
    }

}
