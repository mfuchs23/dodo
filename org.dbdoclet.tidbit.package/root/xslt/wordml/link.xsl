<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
  xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
  xmlns:sl="http://schemas.microsoft.com/schemaLibrary/2003/core"
  xmlns:aml="http://schemas.microsoft.com/aml/2001/core" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint"
  xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">

  <xsl:template match="ulink">
    <w:hlink w:dest="{@url}">
      <w:r>
        <w:rPr>
          <w:rStyle w:val="Hyperlink"/>
        </w:rPr>
        <w:t><xsl:value-of select="."/></w:t>
      </w:r>
    </w:hlink>
  </xsl:template>

  <xsl:template match="xref">
    <w:hlink w:bookmark="{@linkend}">
      <w:r>
        <w:rPr>
          <w:rStyle w:val="Hyperlink"/>
        </w:rPr>
        <xsl:choose>
          <xsl:when test="key('id', @linkend)/title">
            <w:t><xsl:value-of select="key('id', @linkend)/title"/></w:t>
          </xsl:when>
          <xsl:otherwise>
            <w:t>→</w:t>
          </xsl:otherwise>
        </xsl:choose>
      </w:r>
    </w:hlink>
  </xsl:template>

</xsl:stylesheet>
