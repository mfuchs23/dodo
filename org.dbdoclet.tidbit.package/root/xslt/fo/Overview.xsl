<?xml version='1.0'?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY RE "&#10;">
<!ENTITY nbsp "&#160;">
]>

<xsl:stylesheet xmlns:xsl ="http://www.w3.org/1999/XSL/Transform"
                version ="1.0"
                xmlns:fo ="http://www.w3.org/1999/XSL/Format"
                exclude-result-prefixes ="#default"
>

  <xsl:import href ="http://docbook.sourceforge.net/release/xsl/current/fo/docbook.xsl"/>

  <xsl:include href="http://dbdoclet.org/xsl/functions.xsl"/>
  <xsl:include href="http://dbdoclet.org/xsl/fo/fop1.xsl"/>
  <xsl:include href="http://dbdoclet.org/xsl/fo/themes/color.xsl"/>
  <xsl:include href="http://dbdoclet.org/xsl/fo/synopsis.xsl"/>


  <xsl:param name="admon.graphics">1</xsl:param>
  <xsl:param name="admon.graphics.extension">.gif</xsl:param>
  <xsl:param name="admon.graphics.path">@dbdoclet_home@/docbook/xsl/images/</xsl:param>
  <xsl:param name="alignment">left</xsl:param>
  <xsl:param name="autotoc.label.separator"> </xsl:param>
  <xsl:param name="body.font.family">sans-serif</xsl:param>
  <xsl:param name="body.font.master">10</xsl:param>
  <xsl:param name="chapter.autolabel">1</xsl:param>
  <xsl:param name="column.count.back">1</xsl:param>
  <xsl:param name="column.count.body">1</xsl:param>
  <xsl:param name="column.count.front">1</xsl:param>
  <xsl:param name="column.count.index">1</xsl:param>
  <xsl:param name="double.sided">0</xsl:param>
  <xsl:param name="draft.mode">no</xsl:param>
  <xsl:param name="draft.watermark.image">@dbdoclet_home@/docbook/xsl/images/draft.png</xsl:param>
  <xsl:param name="fop1.extensions">1</xsl:param>
  <xsl:param name="generate.index">1</xsl:param>
  <xsl:param name="insert.xref.page.number">1</xsl:param>
  <xsl:param name="page.orientation">portrait</xsl:param>
  <xsl:param name="paper.type">A4</xsl:param>
  <xsl:param name="section.autolabel">1</xsl:param>
  <xsl:param name="section.label.includes.component.label">1</xsl:param>
  <xsl:param name="shade.verbatim">1</xsl:param>
  <xsl:param name="tablecolumns.extension">0</xsl:param>
  <xsl:param name="title.margin.left">0pt</xsl:param>
  <xsl:param name="toc.section.depth">0</xsl:param>
  <xsl:param name="use.extensions">1</xsl:param>

  <xsl:template match="/">
    <fo:root>
      <fo:layout-master-set>
	<fo:simple-page-master master-name="Overview"
			       margin-left="12mm"
			       margin-right="12mm"
			       margin-top="20mm"
			       margin-bottom="20mm">
	  <fo:region-body/>
	</fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="Overview">
	<fo:flow flow-name="xsl-region-body">
	  <xsl:apply-templates select="//classsynopsis/.."/>
	</fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>

  <xsl:template  match="//classsynopsis/..">
    <fo:block>
      <xsl:value-of select="classsynopsis/ooclass/classname"/>
    </fo:block>
  </xsl:template>

</xsl:stylesheet>

