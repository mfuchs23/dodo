package org.dbdoclet.test.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.dbdoclet.service.XmlServices;


public class StringServicesTests extends TestCase {

    public static Test suite() {

        return new TestSuite(StringServicesTests.class);
    }

    public void testGetTextLength1() {

        int length = XmlServices.getXmlTextLength("1234567890");
        assertTrue(length == 10);
    }

    public void testGetTextLength2() {

        int length = XmlServices.getXmlTextLength("12345<em>67890");
        assertTrue(length == 10);
    }

    public void testGetTextLength3() {

        int length = XmlServices.getXmlTextLength("12345<em67890");
        assertTrue(length == 5);
    }

    public void testGetTextLength4() {

        int length = XmlServices.getXmlTextLength("<<<<<<<");
        assertTrue(length == 0);
    }

    public void testGetTextLength5() {

        int length = XmlServices.getXmlTextLength("<<<<<<<>");
        assertTrue(length == 0);
    }

    public void testGetTextLength6() {

        int length = XmlServices.getXmlTextLength("");
        assertTrue(length == 0);
    }

    public void testFindTextLength1() {

        int index = XmlServices.findXmlTextIndex("1234567890", 5);
        assertTrue("Index is " + index, index == 5);
    }

    public void testFindTextLength2() {

        int index = XmlServices.findXmlTextIndex("12<em/>34567890", 5);
        assertTrue("Index is " + index, index == 10);
    }
}
