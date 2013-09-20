/* 
 * $Id$
 *
 * ### Copyright (C) 2005 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 *
 * RCS Information
 * Author..........: $Author$
 * Date............: $Date$
 * Revision........: $Revision$
 * State...........: $State$
 */
package org.dbdoclet.test.sample;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;
import org.dbdoclet.service.ExecResult;
import org.dbdoclet.service.ExecServices;
import org.dbdoclet.service.FileServices;

public class ReferenceStyleTests extends SampleTestCase {

    private static Log logger = LogFactory.getLog(ReferenceStyleTests.class);

    public static Test suite() {
        return new TestSuite(ReferenceStyleTests.class);
    }

    public void testPackageHtml() {
        
        try {

            String sourcePath = "java";

            String destPath = FileServices.appendPath(tmpPath, "samplePackageHtml");

            String[] cmd = { "dbdoclet", "-d" , destPath, "-sourcepath", sourcePath, "-subpackages", "org.dbdoclet" };
            
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
}
/*
 * $Log$
 */
