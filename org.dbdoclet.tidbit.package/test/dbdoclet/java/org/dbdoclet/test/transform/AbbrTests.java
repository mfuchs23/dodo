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
package org.dbdoclet.test.transform;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class AbbrTests extends XmlTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(AbbrTests.class);

    public static Test suite() {

        return new TestSuite(AbbrTests.class);
    }

    public void testTransformAbbr() throws Exception {

        String code = "This is an abbr tag example: <abbr>UNICO</abbr>.";
        checkDocBook(code, true);
    }

    public void testTransformAbbrP() throws Exception {

        String code = "This is an abbr tag example: <abbr>UNICO <p>Invalid Paragraph</p></abbr>.";
        checkDocBook(code, true);
    }

    public void testTransformAbbrI() throws Exception {

        String code = "This is an abbr tag example: <abbr>UNICO <i>Media GmbH</i></abbr>.";
        checkDocBook(code, true);
    }
}
/*
 * $Log$
 */
