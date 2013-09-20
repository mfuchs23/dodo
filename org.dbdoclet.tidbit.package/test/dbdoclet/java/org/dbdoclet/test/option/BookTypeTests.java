package org.dbdoclet.test.option;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.XmlServices;
import org.dbdoclet.service.XmlValidationResult;

public class BookTypeTests extends OptionTestCase {

    private static Log logger = LogFactory.getLog(BookTypeTests.class.getName());

    public BookTypeTests(String name) {
        super(name);
    }

    public static Test suite() {

        return new TestSuite(BookTypeTests.class);
    }

    public void testBookTypeBook() {

        try {

            String sourceFileName = FileServices.appendFileName(sourcePath, "TestOption.java");

            String destPath = FileServices.appendPath(tmpPath, getName());
            
            String[] cmd = { "dbdoclet", "-d" , destPath, 
                             "--book-type", "book", 
                             "--style-type", "listing",
                             sourceFileName };
            
            ExecResult result = ExecServices.exec(cmd);
            
            if (result.getExitCode() != 0) {
                logger.fatal(result.getOutput());
                fail("Execution of dbdoclet failed!");
            }
            
            logger.info(result.getOutput());
            
            String fileName = FileServices.appendFileName(destPath, "Reference.xml");
            
            XmlValidationResult vres = XmlServices.validate(fileName);
            
            if (vres.failed() == true) {
                fail("Validation of " + fileName + " failed! " + vres.createTextReport());
            }
            
            String buffer = FileServices.readToString(fileName);
            
            if (buffer.indexOf("<refnamediv>") != -1) {
                fail("A book document should be created!");
            }

        } catch (Exception oops) {
        
            fail("Exception: " + oops.getClass().getName());
        }
    }

    public void testBookTypeReference() {

        try {

            String sourceFileName = FileServices.appendFileName(sourcePath, "TestOption.java");

            String destPath = FileServices.appendPath(tmpPath, getName());
            
            String[] cmd = { "dbdoclet", "-d" , destPath, 
                             "--book-type", "reference", 
                             "--style-type", "strict",
                             sourceFileName };
            
            ExecResult result = ExecServices.exec(cmd);

            if (result.getExitCode() != 0) {
                logger.fatal(result.getOutput());
                fail("Execution of dbdoclet failed!");
            }

            logger.info(result.getOutput());
            
            String fileName = FileServices.appendFileName(destPath, "Reference.xml");

            XmlValidationResult vres = XmlServices.validate(fileName);

            if (vres.failed() == true) {
                fail("Validation of " + fileName + " failed! " + vres.createTextReport());
            }

            String buffer = FileServices.readToString(fileName);

            if (buffer.indexOf("<refnamediv>") == -1) {
                fail("A reference book should be created!");
            }

        } catch (Exception oops) {
        
            fail("Exception: " + oops.getClass().getName());
        }
    }

}
