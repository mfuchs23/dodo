<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
  xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
  xmlns:sl="http://schemas.microsoft.com/schemaLibrary/2003/core"
  xmlns:aml="http://schemas.microsoft.com/aml/2001/core" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint"
  xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">

  <xsl:template match="informaltable">
    <w:tbl>
      <w:tblPr>
        <w:tblStyle w:val="Tabellengitternetz" />
        <w:tblW w:w="0" w:type="auto" />
        <w:tblLook w:val="01E0" />
      </w:tblPr>
      <xsl:apply-templates />
    </w:tbl>
  </xsl:template>

  <xsl:template match="table">
    <w:p>
      <w:tbl>
        <w:tblPr>
          <w:tblStyle w:val="Tabellengitternetz" />
          <w:tblW w:w="0" w:type="auto" />
          <w:tblLook w:val="01E0" />
        </w:tblPr>
        <xsl:apply-templates />
      </w:tbl>
    </w:p>
  </xsl:template>

  <xsl:template match="tbody">
    <xsl:apply-templates select="row" />
  </xsl:template>

  <xsl:template match="tgroup">
    <w:tblGrid>
      <xsl:apply-templates select="colspec" />
    </w:tblGrid>
    <xsl:apply-templates select="tbody" />
  </xsl:template>

  <xsl:template match="tr">
    <w:tr>
      <xsl:apply-templates select="td" />
    </w:tr>
  </xsl:template>

  <xsl:template match="td">
    <w:tc>
      <w:tcPr>
      </w:tcPr>
      <xsl:apply-templates />
    </w:tc>
  </xsl:template>

  <xsl:template match="td/text()">
    <w:p>
      <w:pPr>
        <w:pStyle w:val="STandard" />
      </w:pPr>
      <w:r>
        <w:t>
          <xsl:value-of select="." />
        </w:t>
      </w:r>
    </w:p>
  </xsl:template>

  <xsl:template match="row">
    <w:tr>
      <xsl:apply-templates select="entry" />
    </w:tr>
  </xsl:template>

  <xsl:template match="entry">
    <w:tc>
      <xsl:if test="@namest != ''">
        <xsl:variable name="namest">
          <xsl:value-of select="@namest" />
        </xsl:variable>
        <xsl:variable name="nameend">
          <xsl:value-of select="@nameend" />
        </xsl:variable>
        <xsl:variable name="spanstart">
          <xsl:value-of
            select="count(../../../colspec[@colname=$namest]/preceding-sibling::*) + 1" />
        </xsl:variable>
        <xsl:variable name="spanend">
          <xsl:value-of
            select="count(../../../colspec[@colname=$nameend]/preceding-sibling::*) + 1" />
        </xsl:variable>
        <xsl:variable name="span">
          <xsl:value-of select="$spanend - $spanstart + 1" />
        </xsl:variable>
        <w:tcPr>
          <w:gridSpan w:val="{$span}" />
        </w:tcPr>
      </xsl:if>
      <xsl:apply-templates />
    </w:tc>
  </xsl:template>

</xsl:stylesheet>
