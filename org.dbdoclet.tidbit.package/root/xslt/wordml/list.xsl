<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
  xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
  xmlns:sl="http://schemas.microsoft.com/schemaLibrary/2003/core"
  xmlns:aml="http://schemas.microsoft.com/aml/2001/core" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint"
  xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">

  <xsl:template match="orderedlist | itemizedlist">
    <xsl:if test="parent::para">
      <xsl:text disable-output-escaping="yes"><![CDATA[</w:p>]]></xsl:text>
    </xsl:if>
    <xsl:apply-templates/>
    <xsl:if test="parent::para">
      <xsl:text disable-output-escaping="yes"><![CDATA[<w:p>]]></xsl:text>
    </xsl:if>
  </xsl:template>

  <xsl:template match="para[parent::listitem]">
    <xsl:variable name="level">
      <xsl:value-of select="count(ancestor::listitem)"/>
    </xsl:variable>
    <w:p>
      <w:pPr>
        <xsl:choose>
          <xsl:when test="$level='5'">
            <w:ind w:left="3545" w:hanging="709"/>
          </xsl:when>
          <xsl:when test="$level='5'">
            <w:ind w:left="2836" w:hanging="709"/>
          </xsl:when>
          <xsl:when test="$level='5'">
            <w:ind w:left="2125" w:hanging="709"/>
          </xsl:when>
          <xsl:when test="$level='5'">
            <w:ind w:left="1418" w:hanging="709"/>
          </xsl:when>
          <xsl:otherwise>
            <w:ind w:left="709" w:hanging="709"/>
          </xsl:otherwise>
        </xsl:choose>
      </w:pPr>
      <w:r>
        <xsl:if test="not(preceding-sibling::para)">
          <xsl:choose>
            <xsl:when test="parent::*/parent::orderedlist">
              <w:t><xsl:number count="listitem"/>.</w:t>
            </xsl:when>
            <xsl:otherwise>
              <w:t>• </w:t>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:if>
      </w:r>
      <xsl:apply-templates select="text() | emphasis | orderedlist | itemizedlist"/>
    </w:p>
  </xsl:template>

  <xsl:template match="para[parent::listitem]/text()">
    <xsl:if test="preceding-sibling::*[1][self::orderedlist] | preceding-sibling::*[1][self::itemizedlist]">
      <xsl:variable name="level">
        <xsl:value-of select="count(ancestor::listitem)"/>
      </xsl:variable>
      <w:pPr>
        <xsl:choose>
          <xsl:when test="$level='5'">
            <w:ind w:left="3545" w:hanging="709"/>
          </xsl:when>
          <xsl:when test="$level='5'">
            <w:ind w:left="2836" w:hanging="709"/>
          </xsl:when>
          <xsl:when test="$level='5'">
            <w:ind w:left="2125" w:hanging="709"/>
          </xsl:when>
          <xsl:when test="$level='5'">
            <w:ind w:left="1418" w:hanging="709"/>
          </xsl:when>
          <xsl:otherwise>
            <w:ind w:left="709" w:hanging="709"/>
          </xsl:otherwise>
        </xsl:choose>
      </w:pPr>
    </xsl:if>
    <w:r><w:tab/></w:r>
    <w:r><w:t><xsl:text> </xsl:text><xsl:value-of select="normalize-space(.)"/></w:t></w:r>
  </xsl:template>

  <xsl:template match="variablelist">
    <xsl:if test="parent::para">
      <xsl:text disable-output-escaping="yes"><![CDATA[</w:p>]]></xsl:text>
    </xsl:if>
    <xsl:apply-templates select="title" />
    <xsl:apply-templates select="varlistentry" />
    <xsl:if test="parent::para">
      <xsl:text disable-output-escaping="yes"><![CDATA[<w:p>]]></xsl:text>
    </xsl:if>
  </xsl:template>

  <xsl:template match="variablelist/title">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'Para'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="varlistentry/listitem">
    <xsl:apply-templates />
  </xsl:template>

</xsl:stylesheet>
