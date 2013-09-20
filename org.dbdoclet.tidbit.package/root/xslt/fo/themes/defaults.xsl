<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

  <xsl:output method="xml"/>

  <xsl:attribute-set name="classsynopsis.properties">
     <xsl:attribute name="font-family">
      <xsl:value-of select="$monospace.font.family"/>
    </xsl:attribute>
  </xsl:attribute-set> 

  <xsl:attribute-set name="synopsis.properties">
  </xsl:attribute-set> 

  <xsl:attribute-set name="synopsis.name">
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.keyword">
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.annotation">
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.type">
  </xsl:attribute-set>

  <xsl:attribute-set name="synopsis.comment">
  </xsl:attribute-set>

</xsl:stylesheet>
