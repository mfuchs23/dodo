/* 
 * ### Copyright (C) 2006-2009 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 */
package org.dbdoclet.tidbit.project.driver;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class HtmlDriver extends AbstractDriver {

    public HtmlDriver(File homeDir, File driverFile, File paramFile) throws ParserConfigurationException, SAXException, IOException {
        super(homeDir, driverFile, paramFile);
    }
}
