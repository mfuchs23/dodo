<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

  <xsl:output method="xml"/>

  <xsl:attribute-set name="synopsis.name">
    <xsl:attribute name="style">color:#111111</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.keyword">
    <xsl:attribute name="style">font-weight:bold; color:purple</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.annotation">
    <xsl:attribute name="style">color:#e75480</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.type">
    <xsl:attribute name="style">color:darkblue</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.comment">
    <xsl:attribute name="style">color:darkcyan</xsl:attribute>
  </xsl:attribute-set>

</xsl:stylesheet>
