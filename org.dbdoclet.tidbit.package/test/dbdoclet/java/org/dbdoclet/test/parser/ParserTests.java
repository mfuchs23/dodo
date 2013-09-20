/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.test.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class ParserTests extends TestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(ParserTests.class);

    public ParserTests(String name) {
        super(name);
    }

    public static Test suite() {

        TestSuite suite = new TestSuite("HTMLParser");
        suite.addTest(org.dbdoclet.test.parser.AbbrTests.suite());
        suite.addTest(org.dbdoclet.test.parser.AcronymTests.suite());
        suite.addTest(org.dbdoclet.test.parser.AddressTests.suite());
        suite.addTest(org.dbdoclet.test.parser.BigTests.suite());
        suite.addTest(org.dbdoclet.test.parser.BTests.suite());
        suite.addTest(org.dbdoclet.test.parser.BrTests.suite());
        suite.addTest(org.dbdoclet.test.parser.CommentTests.suite());
        suite.addTest(org.dbdoclet.test.parser.DivTests.suite());
        suite.addTest(org.dbdoclet.test.parser.EntitiesTests.suite());
        suite.addTest(org.dbdoclet.test.parser.FontTests.suite());
        suite.addTest(org.dbdoclet.test.parser.HeaderTests.suite());
        suite.addTest(org.dbdoclet.test.parser.HrTests.suite());
        suite.addTest(org.dbdoclet.test.parser.ImgTests.suite());
        suite.addTest(org.dbdoclet.test.parser.ListTests.suite());
        suite.addTest(org.dbdoclet.test.parser.MiscTests.suite());
        suite.addTest(org.dbdoclet.test.parser.PTests.suite());
        suite.addTest(org.dbdoclet.test.parser.SmallTests.suite());
        suite.addTest(org.dbdoclet.test.parser.StrikeTests.suite());
        suite.addTest(org.dbdoclet.test.parser.TableTests.suite());

        return suite;
    }
}
