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
 * Id.........: $Id: JavadocTagTests.java,v 1.1.1.1 2004/12/21 14:00:05 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:05 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.tokenizer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;
import org.dbdoclet.tokenizer.MLToken;
import org.dbdoclet.tokenizer.MLTokenizer;


public class JavadocTagTests extends TestCase {

    private static Log logger = LogFactory.getLog(JavadocTagTests.class);

    public JavadocTagTests(String name) {
        super(name);
    }

    public static Test suite() {

        return new TestSuite(JavadocTagTests.class);
    }

    public void testJavaTag1() throws Exception {

        String code = "Hallo dies ein javadoc Tag <javadoc:link ref=\"String\">String</javadoc:link>";

        MLTokenizer tokenizer = new MLTokenizer(code);
        MLToken token = null;

        boolean found = false;

        while (tokenizer.hasNext()) {

            token = tokenizer.next();

            if (token.isJavadoc()) {

                found = true;
                logger.debug("javadoc token=" + token.getToken() + " tagname=" +
                    token.getTagName());
            } else {

                logger.debug("token=" + token.getToken());
            } // end of else
        }

        assertTrue("Couldn't find a javadoc tag!", found);
    }

    static public void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }
}

/*
 * $Log: JavadocTagTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:05  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:29  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:57:03  cvs
 * dbdoclet
 *
 * Revision 1.1.1.1  2003/08/01 13:19:21  cvs
 * DocBook Doclet
 *
 * Revision 1.1.1.1  2003/07/31 17:05:40  mfuchs
 * DocBook Doclet since 0.46
 *
 * Revision 1.1.1.1  2003/05/30 11:09:40  mfuchs
 * dbdoclet
 *
 * Revision 1.1  2003/04/18 12:22:41  mfuchs
 * Changes for release 0.41.
 *
 */
