<!--
 ### Copyright (C) 2001-2003 Michael Fuchs ###
 
 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2, or (at your option)
 any later version.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the
 Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 Boston, MA 02111-1307, USA.
 	
 Author: Michael Fuchs
 E-Mail: mfuchs@unico-consulting.com
 
 RCS INFORMATION
 Id.........: $Id: ParserFileTests.xsl,v 1.1.1.1 2004/12/21 13:59:27 mfuchs Exp $
 Author.....: $Author: mfuchs $
 Date.......: $Date: 2004/12/21 13:59:27 $
 Revision...: $Revision: 1.1.1.1 $
 State......: $State: Exp $
-->

<xsl:stylesheet 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:redirect="org.apache.xalan.xslt.extensions.Redirect"
  extension-element-prefixes="redirect"
  version="1.0">

  <xsl:output 
    method="text"
    encoding="iso-8859-1"/>

  <xsl:template match="test-files">
    <xsl:text>package org.dbdoclet.test.parser;
</xsl:text>
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringWriter;

import junit.framework.Test;
import junit.framework.TestSuite;

public class FileTests extends ParserTestCase 
{
    private String m_fsep = System.getProperty("file.separator");
    private String m_filePath = System.getProperty("junit.data.dir");

    public static Test suite()
    {
      return new TestSuite(FileTests.class);
    }

    <xsl:apply-templates select="file"/>

    private String getBuffer(String fname) 
        throws Exception
    {
	fname = m_filePath + m_fsep + fname;
	
        FileReader in = new FileReader(fname);      
	StringWriter code = new StringWriter();

	char[] buffer = new char[4096];

	int length = 0;
        while ((length = in.read(buffer,0,4096)) != -1 )
	    code.write(buffer,0,length);

        return code.toString();
    }
}
  </xsl:template>

  <xsl:template match="file">
    public void testFile<xsl:value-of select="substring-before(.,'.html')"/>()
        throws Exception
    {
        String code = getBuffer("<xsl:value-of select="."/>");
        checkHtml(code,false);
    }
  </xsl:template>

</xsl:stylesheet>
<!--
$Log: ParserFileTests.xsl,v $
Revision 1.1.1.1  2004/12/21 13:59:27  mfuchs
Reimport

Revision 1.1.1.1  2004/02/17 22:48:39  mfuchs
dbdoclet

Revision 1.1.1.1  2004/01/05 14:56:13  cvs
dbdoclet

Revision 1.1.1.1  2003/08/01 13:17:57  cvs
DocBook Doclet

Revision 1.1.1.1  2003/07/31 17:05:39  mfuchs
DocBook Doclet since 0.46

Revision 1.1.1.1  2003/05/30 11:09:40  mfuchs
dbdoclet

Revision 1.1.1.1  2003/03/18 07:41:37  mfuchs
DocBook Doclet 0.40

Revision 1.1.1.1  2003/03/17 20:50:18  cvs
dbdoclet

-->