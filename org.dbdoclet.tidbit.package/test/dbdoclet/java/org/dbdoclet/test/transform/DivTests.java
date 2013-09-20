/*
 * ### Copyright (C) 2001-2003 Michael Fuchs ###
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * Author: Michael Fuchs
 * E-Mail: mfuchs@unico-consulting.com
 *
 * RCS Information:
 * ---------------
 * Id.........: $Id: DivTests.java,v 1.1.1.1 2004/12/21 14:00:02 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:02 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.transform;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class DivTests extends XmlTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(DivTests.class);

    public static Test suite() {

        return new TestSuite(DivTests.class);
    }

    public void testTransformDivFormalExample() throws Exception {

        String code = "<div id=\"example_1\" title=\"Formal Example\">\n" +
            "<pre>\n" + "public class FormalExample\n" + "{\n" +
            "  private int n;\n" + "  .\n" + "  .\n" + "  .\n" + "}\n" +
            "</pre>\n" + "</div>\n";

        checkDocBook(code, true);
    }

    public void testTransformDivInformalExample() throws Exception {

        String code = "<div id=\"example_1\">\n" + "<pre>\n" +
            "public class InformalExample\n" + "{\n" + "  private int n;\n" +
            "  .\n" + "  .\n" + "  .\n" + "}\n" + "</pre>\n" + "</div>\n";

        checkDocBook(code, true);
    }
}

/*
 * $Log: DivTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:02  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:24  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:56:58  cvs
 * dbdoclet
 *
 * Revision 1.2  2003/11/30 20:43:44  cvs
 * Many fixes. Command line options.
 *
 * Revision 1.1.1.1  2003/08/01 13:18:23  cvs
 * DocBook Doclet
 *
 * Revision 1.1.1.1  2003/07/31 17:05:40  mfuchs
 * DocBook Doclet since 0.46
 *
 * Revision 1.1.1.1  2003/05/30 11:09:40  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2003/03/18 07:41:37  mfuchs
 * DocBook Doclet 0.40
 *
 * Revision 1.1.1.1  2003/03/17 20:50:38  cvs
 * dbdoclet
 *
 */