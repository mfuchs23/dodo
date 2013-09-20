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
 * Id.........: $Id: EntitiesTests.java,v 1.1.1.1 2004/12/21 14:00:04 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:04 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.transform;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class EntitiesTests extends XmlTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(EntitiesTests.class);

    public static Test suite() {

        return new TestSuite(EntitiesTests.class);
    }

    public void testTransformLtGtMixed() throws Exception {

        String code = "<p> 1<3  1 > 3 1<=3 1 >= 3 var<<8 var>>8 1<>3 1 <> 3 </p>";
        checkDocBook(code, true);
    }

    public void testAmpersand1() throws Exception {

        String code = "<p>Rock & Roll</p>";
        checkDocBook(code, true);
    }

    public void testAmpersand2() throws Exception {

        String code = "<p>Rock&Roll</p>";
        checkDocBook(code, true);
    }

    public void testAmpersand3() throws Exception {

        String code = "<p>&&&&&</p>";
        checkDocBook(code, true);
    }

    public void testAmpersand4() throws Exception {

        String code = "<p>&;&;&;&;&;</p>";
        checkDocBook(code, true);
    }
}

/*
 * $Log: EntitiesTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:04  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:27  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:57:01  cvs
 * dbdoclet
 *
 * Revision 1.2  2003/11/30 20:43:44  cvs
 * Many fixes. Command line options.
 *
 * Revision 1.1.1.1  2003/08/01 13:18:50  cvs
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
 * Revision 1.1.1.1  2003/03/17 20:51:04  cvs
 * dbdoclet
 *
 */
