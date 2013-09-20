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
 * Id.........: $Id: MiscTests.java,v 1.1.1.1 2004/12/21 14:00:02 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:02 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.transform;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class MiscTests extends XmlTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(MiscTests.class);

    public static Test suite() {

        return new TestSuite(MiscTests.class);
    }

    public void testTransformCaseRoole1() throws Exception {

        String code = "<h1>First section</h1>\n" + "\n" + "<p>\n" +
            "Here is an unordered list:\n" + "<ul>\n" + "<li>first item\n" +
            "<li>second item\n" + "</ul>\n" +
            "Having a text here after the &lt;/UL&gt; prevents the rest of the doc from being truncated.\n" +
            "</p>\n" + "\n" + "<h1>Second section</h1>\n" + "<p>\n" +
            "Here is another unordered list:\n" + "<ul>\n" +
            "<li>first item\n" + "<li>second item\n" + "</ul>\n" + "</p>\n" +
            "<p>\n" +
            "There is nothing between the &lt;/UL&gt; and the &lt;/P&gt; above and everything after it is truncated.\n" +
            "</p>\n" + "\n" + "<h1>Third section</h1>\n" + "\n" + "<p>\n" +
            "This section is not processed.\n" + "</p>\n" + "\n";

        checkDocBook(code, true);
    }

    public void testTransformGregorJasny1() throws Exception {

        String code =
            "Meldet einen Benutzer mittels Benutzer-Id und Passwort an.\n" +
            "<br>\n" +
            "Wenn der UserId oder der UserPasswort Parameter nicht existiert bzw. die\n" +
            "L&auml;nge Null hat, wird nicht angemeldet und auch kein Fehler ausgegeben.\n" +
            "<br><br>\n" + "Parameter:<ul>\n" +
            "<li><b>ifOK</b> - Legt die Seite fest, zu der bei erfolgreichem Login redirected wird.\n" +
            "<li><b>userIdParam</b> - Legt den Namen des Parameters fest, der die UserId enth&auml;lt.\n" +
            "<li><b>userPwParam</b> - Legt den Namen der Parameters fest, der das Passwort enth&auml;lt.\n" +
            "<li>myDateParam - Legt den Namen des Parameters fest, in dem die lokale Zeit &uuml;bergeben wird. Das Format ist das ISO-Format.\n" +
            "</ul>\n" + "R&uuml;ckgabewerte:<ul>\n" +
            "<li>null bei erfolgreicher Anmeldung.\n" +
            "<li>\"\" bei einem fehlenden Parameter\n" +
            "<li>Die i18n Fehlermeldung, falls die Anmeldung fehlschlug.\n" +
            "</ul>\n" + "Beispiel:<br>\n" + "<code><pre>\n" +
            "&lt;VidSoft:Logon ifOK=\"FrameIt.jsp\"\n" +
            "userIdParam=\"LoginUserId\"\n" +
            "userPwParam=\"UserPassword\"&gt;\n" +
            "&lt;VidSoft:IfNotNull&gt;\n" + "\n" +
            "&lt;form method=\"post\" action=\"Logon.jsp\"&gt;\n" + "\n" +
            "&lt;!-- Um die eventuelle Fehlermeldung auszugeben: --&gt;\n" +
            "&lt;VidSoft:Insert/&gt;\n" +
            "&lt;input type=\"text\" name=\"LoginUserId\"&gt;\n" +
            "&lt;input type=\"text\" name=\"UserPassword\"&gt;\n" +
            "&lt;/form&gt;\n" + "&lt;/VidSoft:IfNotNull&gt;\n" +
            "&lt;/VidSoft:Logon&gt;\n" + "</pre></code>\n" + "@since 3.0\n" +
            "@author Gregor Jasny\n" + "\n";

        checkDocBook(code, true);
    }
}

/*
 * $Log: MiscTests.java,v $
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
 * Revision 1.1.1.1  2003/08/01 13:18:26  cvs
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
 * Revision 1.1.1.1  2003/03/17 20:50:41  cvs
 * dbdoclet
 *
 */
