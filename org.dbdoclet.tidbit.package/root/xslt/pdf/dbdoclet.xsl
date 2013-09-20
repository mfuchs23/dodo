<?xml version='1.0'?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY RE "&#10;">
<!ENTITY nbsp "&#160;">
]>

<xsl:stylesheet xmlns:xsl ="http://www.w3.org/1999/XSL/Transform"
                version ="1.0"
                xmlns:fo ="http://www.w3.org/1999/XSL/Format"
                exclude-result-prefixes ="#default">

  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/fo/docbook.xsl"/>
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/fo/dbdoclet-titlepage.xsl"/>
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/fo/autoidx-kosek.xsl"/>
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/fo/autoidx-kimber.xsl"/>

  <xsl:include href="http://dbdoclet.org/xsl/functions.xsl"/>
  <xsl:include href="http://dbdoclet.org/xsl/fo/fop1.xsl"/>
  <xsl:include href="http://dbdoclet.org/xsl/fo/themes/color.xsl"/>
  <xsl:include href="http://dbdoclet.org/xsl/fo/synopsis.xsl"/>

  <!-- SECTION Parameter -->
  <xsl:param name="admon.graphics">1</xsl:param>
  <xsl:param name="admon.graphics.extension">.gif</xsl:param>
  <xsl:param name="admon.graphics.path">@dbdoclet_home@/docbook/xsl/images/</xsl:param>
  <xsl:param name="admon.textlabel">1</xsl:param>
  <xsl:param name="callout.graphics">1</xsl:param>
  <xsl:param name="callout.graphics.extension">.gif</xsl:param>
  <xsl:param name="callout.graphics.path">@dbdoclet_home@/docbook/xsl/images/callouts/</xsl:param>
  <xsl:param name="alignment">left</xsl:param>
  <xsl:param name="autotoc.label.separator"> </xsl:param>
  <xsl:param name="body.font.family">sans-serif</xsl:param>
  <xsl:param name="body.font.master">10</xsl:param>
  <xsl:param name="body.start.indent">2pt</xsl:param>
  <xsl:param name="chapter.autolabel">1</xsl:param>
  <xsl:param name="column.count.back">1</xsl:param>
  <xsl:param name="column.count.body">1</xsl:param>
  <xsl:param name="column.count.front">1</xsl:param>
  <xsl:param name="column.count.index">1</xsl:param>
  <xsl:param name="double.sided">0</xsl:param>
  <xsl:param name="draft.mode">no</xsl:param>
  <xsl:param name="draft.watermark.image">@dbdoclet_home@/docbook/xsl/images/draft.png</xsl:param>
  <xsl:param name="fop.extensions">0</xsl:param>
  <xsl:param name="fop1.extensions">1</xsl:param>
  <xsl:param name="generate.index">1</xsl:param>
  <xsl:param name="insert.xref.page.number">1</xsl:param>
  <xsl:param name="page.orientation">portrait</xsl:param>
  <xsl:param name="paper.type">A4</xsl:param>
  <xsl:param name="section.autolabel">1</xsl:param>
  <xsl:param name="section.label.includes.component.label">0</xsl:param>
  <xsl:param name="shade.verbatim">0</xsl:param>
  <xsl:param name="tablecolumns.extension">1</xsl:param>
  <xsl:param name="title.margin.left">0in</xsl:param>
  <xsl:param name="toc.section.depth">1</xsl:param>
  <xsl:param name="use.extensions">1</xsl:param>
  <xsl:param name="variablelist.as.blocks">0</xsl:param>

  <xsl:attribute-set name="admonition.title.properties">
    <xsl:attribute name="font-size">21pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:template match="para">
    <xsl:variable name="keep.together">
      <xsl:call-template name="pi.dbfo_keep-together"/>
    </xsl:variable>
    <fo:block xsl:use-attribute-sets="para.properties">
      <xsl:if test="$keep.together != ''">
	<xsl:attribute name="keep-together.within-column"><xsl:value-of
	select="$keep.together"/></xsl:attribute>
      </xsl:if>
      <!--
        ABWEICHENDE IMPLEMENTIERUNG 
	Kein Zeileneinzug der ersten Zeile, wenn der vorangegangene Knoten kein para war. 
      -->
      <xsl:if test="count(preceding-sibling::para) = 0">
	<xsl:attribute name="text-indent">0</xsl:attribute>
      </xsl:if>
      <!-- END -->
      <xsl:call-template name="anchor"/>
      <xsl:apply-templates/>
    </fo:block>
  </xsl:template>

</xsl:stylesheet>

