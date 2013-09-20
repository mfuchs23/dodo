<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
  xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
  xmlns:sl="http://schemas.microsoft.com/schemaLibrary/2003/core"
  xmlns:aml="http://schemas.microsoft.com/aml/2001/core" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint"
  xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">

  <xsl:template match="literal">
    <xsl:call-template name="insert-text-with-style">
      <xsl:with-param name="style" select="'ProgrammListing'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="screen">
    <w:p>
      <w:pPr>
        <w:pStyle>
          <xsl:attribute name="w:val">ProgrammListing</xsl:attribute>
	</w:pStyle>
      </w:pPr>
      <w:r>
        <w:rPr>
          <w:rStyle>
            <xsl:attribute name="w:val">ProgrammListing</xsl:attribute>
          </w:rStyle>
        </w:rPr>
        <w:t>
	  <xsl:attribute name="xml:space">preserve</xsl:attribute>
	  <xsl:call-template name="insert-line-breaks">
	    <xsl:with-param name="string" select="."/>
	  </xsl:call-template>
	</w:t>
      </w:r>
    </w:p>
  </xsl:template>

  <xsl:template name="insert-line-breaks">
    <xsl:param name="string"/>
    <xsl:choose>
        <xsl:when test="contains($string,'&#10;')">
            <xsl:value-of select="substring-before($string,'&#10;')"/>
            <w:br/>
            <xsl:call-template name="insert-line-breaks">
                <xsl:with-param name="string"
select="substring-after($string,'&#10;')"/>
            </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="$string"/>
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>
</xsl:stylesheet>
