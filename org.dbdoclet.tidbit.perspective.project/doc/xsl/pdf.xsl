<?xml version ="1.0" encoding ="UTF-8"?>
<xsl:stylesheet exclude-result-prefixes="#default" version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/fo/docbook.xsl"></xsl:import>

  <xsl:include href="http://docbook.sourceforge.net/release/xsl/current/fo/dbdoclet-titlepage.xsl"></xsl:include>
  <xsl:include href="http://dbdoclet.org/xsl/functions.xsl"></xsl:include>
  <xsl:include href="http://dbdoclet.org/xsl/fo/fop1.xsl"></xsl:include>
  <xsl:include href="http://dbdoclet.org/xsl/fo/themes/color.xsl"></xsl:include>
  <xsl:include href="http://dbdoclet.org/xsl/fo/synopsis.xsl"></xsl:include>

  
  <xsl:param name="generate.section.toc.level">2</xsl:param>
<xsl:param name="simplesect.in.toc">0</xsl:param>
<xsl:param name="bridgehead.in.toc">0</xsl:param>
<xsl:param name="admon.graphics">1</xsl:param>
  <xsl:param name="admon.graphics.extension">.gif</xsl:param>
  <xsl:param name="admon.graphics.path">/usr/share/dbdoclet//docbook/xsl/images/</xsl:param>
  <xsl:param name="admon.textlabel">1</xsl:param>
  <xsl:param name="callout.graphics">1</xsl:param>
  <xsl:param name="callout.graphics.extension">.gif</xsl:param>
  <xsl:param name="callout.graphics.path">/usr/share/dbdoclet//docbook/xsl/images/callouts/</xsl:param>
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
  <xsl:param name="draft.mode">0</xsl:param>
  <xsl:param name="draft.watermark.image">/usr/share/dbdoclet//docbook/xsl/images/draft.png</xsl:param>
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
    <xsl:attribute name="font-size">12</xsl:attribute>
  <xsl:attribute name="font-family">serif</xsl:attribute>
<xsl:attribute name="font-weight">normal</xsl:attribute>
<xsl:attribute name="font-style">normal</xsl:attribute>
<xsl:attribute name="color">#101010</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="section.title.level1.properties"><xsl:attribute name="font-family">serif</xsl:attribute>
<xsl:attribute name="font-weight">bold</xsl:attribute>
<xsl:attribute name="font-style">normal</xsl:attribute>
<xsl:attribute name="font-size">21</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.title.level2.properties"><xsl:attribute name="font-family">serif</xsl:attribute>
<xsl:attribute name="font-weight">bold</xsl:attribute>
<xsl:attribute name="font-style">normal</xsl:attribute>
<xsl:attribute name="font-size">17</xsl:attribute>
<xsl:attribute name="color">#1a1a1a</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.title.level3.properties"><xsl:attribute name="font-family">serif</xsl:attribute>
<xsl:attribute name="font-weight">bold</xsl:attribute>
<xsl:attribute name="font-style">normal</xsl:attribute>
<xsl:attribute name="font-size">14</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.title.level4.properties"><xsl:attribute name="font-family">serif</xsl:attribute>
<xsl:attribute name="font-weight">bold</xsl:attribute>
<xsl:attribute name="font-style">normal</xsl:attribute>
<xsl:attribute name="font-size">12</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="section.title.level5.properties"><xsl:attribute name="font-family">serif</xsl:attribute>
<xsl:attribute name="font-weight">bold</xsl:attribute>
<xsl:attribute name="font-style">normal</xsl:attribute>
<xsl:attribute name="font-size">10</xsl:attribute>
</xsl:attribute-set>
</xsl:stylesheet>
