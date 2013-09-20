<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

  <xsl:template name="removeSoftHyphen">
    <xsl:param name="string" select="''"/>
    <xsl:if test="contains($string, '&#x00ad;')">
      <xsl:value-of select="substring-before($string,  '&#x00ad;')"/>
      <xsl:call-template name="removeSoftHyphen">
        <xsl:with-param name="string">
          <xsl:value-of select="substring-after($string,  '&#x00ad;')"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:if>
    <xsl:if test="not(contains($string, '&#x00ad;'))">
      <xsl:value-of select="$string"/>
    </xsl:if>
  </xsl:template>

</xsl:stylesheet>
