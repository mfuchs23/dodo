<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

  <xsl:output method="xml"/>

  <xsl:attribute-set name="classsynopsis.properties">
    <xsl:attribute name="font-family">
      <xsl:value-of select="$monospace.font.family"/>
    </xsl:attribute>
    <xsl:attribute name="font-size">
      <xsl:value-of select="$body.font.master * 0.9"/>
      <xsl:text>pt</xsl:text>
    </xsl:attribute>  
    <!--
    <xsl:attribute name="border-style">solid</xsl:attribute>
    <xsl:attribute name="border-width">1mm</xsl:attribute>
    -->
    <xsl:attribute name="space-before.minimum">1.0em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">1.0em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">1.0em</xsl:attribute>
    <!--
    <xsl:attribute name="space-after.minimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-after.optimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-after.maximum">0.0em</xsl:attribute>
    -->
  </xsl:attribute-set> 

  <xsl:attribute-set name="synopsis.properties">
    <xsl:attribute name="font-family">
      <xsl:value-of select="$monospace.font.family"/>
    </xsl:attribute>
    <xsl:attribute name="font-size">
      <xsl:value-of select="$body.font.master * 0.9"/>
      <xsl:text>pt</xsl:text>
    </xsl:attribute>  
    <!--
    <xsl:attribute name="border-style">solid</xsl:attribute>
    <xsl:attribute name="border-width">1mm</xsl:attribute>
    -->
    <xsl:attribute name="space-before.minimum">0.33em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">0.33em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">0.33em</xsl:attribute>
    <!--
    <xsl:attribute name="space-after.minimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-after.optimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-after.maximum">0.0em</xsl:attribute>
    -->
  </xsl:attribute-set> 

  <xsl:attribute-set name="synopsis.name">
    <xsl:attribute name="font-family">
      <xsl:value-of select="$monospace.font.family"/>
    </xsl:attribute>
    <xsl:attribute name="font-weight">normal</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="color">#111111</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.keyword">
    <xsl:attribute name="font-family">
      <xsl:value-of select="$monospace.font.family"/>
    </xsl:attribute>
    <xsl:attribute name="font-weight">bold</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="color">#7f0055</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.annotation">
    <xsl:attribute name="font-family">
      <xsl:value-of select="$monospace.font.family"/>
    </xsl:attribute>
    <xsl:attribute name="font-weight">normal</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="color">#e75480</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.type">
    <xsl:attribute name="font-family">
      <xsl:value-of select="$monospace.font.family"/>
    </xsl:attribute>
    <xsl:attribute name="font-weight">normal</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="color">#0000c0</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.comment">
    <xsl:attribute name="font-family">
      <xsl:value-of select="$monospace.font.family"/>
    </xsl:attribute>
    <xsl:attribute name="font-weight">normal</xsl:attribute>
    <xsl:attribute name="font-style">italic</xsl:attribute>
    <xsl:attribute name="color">#62837a</xsl:attribute>
  </xsl:attribute-set>

</xsl:stylesheet>
