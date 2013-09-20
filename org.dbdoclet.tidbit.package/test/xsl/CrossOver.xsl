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
 Id.........: $Id: CrossOver.xsl,v 1.1.1.1 2004/12/21 13:59:28 mfuchs Exp $
 Author.....: $Author: mfuchs $
 Date.......: $Date: 2004/12/21 13:59:28 $
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

  <xsl:template match="elements">
    <xsl:apply-templates select="element"/>
    <xsl:call-template name="write-ant"/>
  </xsl:template>

  <xsl:template match="element">
    <xsl:param name="tag" select="."/>
    <xsl:param name="tagLabel" select="concat(translate(substring(.,0,2),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),substring(.,2))"/>
    <xsl:param name="file" select="concat(concat('java/org/dbdoclet/test/parser/CrossOver',$tagLabel),'Tests.java')"/>
    <redirect:write select="$file">
    <xsl:text>package org.dbdoclet.test.parser;
</xsl:text>

import junit.framework.Test;
import junit.framework.TestSuite;

public class CrossOver<xsl:value-of select="$tagLabel"/>Tests extends ParserTestCase 
{
    public static Test suite()
    {
	return new TestSuite(CrossOver<xsl:value-of select="$tagLabel"/>Tests.class);
    }

<xsl:for-each select="/elements/element">
  <xsl:call-template name="write-test">
    <xsl:with-param name="tag1" select="$tag"/>
  </xsl:call-template>
</xsl:for-each>
}
    </redirect:write>
  </xsl:template>

  <xsl:template name="write-test">
    <xsl:param name="tag1" select="''"/>
    <xsl:param name="tag1Upper" select="concat(translate(substring($tag1,0,2),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),substring($tag1,2))"/>
    <xsl:param name="tag2" select="."/>
    <xsl:param name="tag2Upper" select="concat(translate(substring(.,0,2),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),substring(.,2))"/>
    public void testCrossOver<xsl:value-of select="$tag1Upper"/>In<xsl:value-of select="$tag2Upper"/>() 
    {
        String code="&lt;<xsl:value-of select="$tag2"/>&gt;&lt;<xsl:value-of select="$tag1"/>&gt;&lt;/<xsl:value-of select="$tag1"/>&gt;&lt;/<xsl:value-of select="$tag2"/>&gt;";
        // treeView(code);
        checkHtml(code,"<xsl:value-of select="$tag1Upper"/>");
    }
  </xsl:template>

  <xsl:template name="write-ant">
    <redirect:write file="java/org/dbdoclet/test/parser/crossover.xml">
      <project name="crossover-tests">
      </project>
    </redirect:write>
  </xsl:template>

</xsl:stylesheet>
<!--
$Log: CrossOver.xsl,v $
Revision 1.1.1.1  2004/12/21 13:59:28  mfuchs
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