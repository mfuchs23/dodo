<?xml version='1.0'?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY RE "&#10;">
<!ENTITY nbsp "&#160;">
]>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version="1.0">

  <xsl:output method="html" indent="no"/>
  
  <xsl:strip-space elements="*"/>

  <xsl:template match="processing-instruction('line-break')">
    <br/>
  </xsl:template>

  <xsl:template match="classsynopsisinfo" mode="java">
    <xsl:if test="@role = 'comment'">
      <xsl:text>&RE;&nbsp;&nbsp;</xsl:text>
    </xsl:if>
    <span xsl:use-attribute-sets="synopsis.comment"><xsl:apply-templates mode="java"/></span>
    <xsl:if test="@role = 'comment'">
      <xsl:text>&RE;</xsl:text>
    </xsl:if>
  </xsl:template>

  <xsl:template match="classsynopsis" mode="java">
    <pre>
      <xsl:apply-templates select="ooclass[1]" mode="java"/>
      <xsl:if test="ooclass[position() &gt; 1]">
	<span xsl:use-attribute-sets="synopsis.keyword">
	  <xsl:text> extends </xsl:text>
	</span>
	<xsl:apply-templates select="ooclass[position() &gt; 1]" mode="java"/>
	<xsl:if test="oointerface|ooexception">
	  <xsl:text>&RE;&nbsp;&nbsp;</xsl:text>
	</xsl:if>
      </xsl:if>
      <xsl:if test="oointerface"><span xsl:use-attribute-sets="synopsis.keyword"><xsl:text> implements </xsl:text></span>
      <xsl:apply-templates select="oointerface" mode="java"/>
      <xsl:if test="ooexception">
	<xsl:text>&RE;&nbsp;&nbsp;&nbsp;&nbsp;</xsl:text>
      </xsl:if>
      </xsl:if>
      <xsl:if test="ooexception"><span xsl:use-attribute-sets="synopsis.keyword"><xsl:text>throws</xsl:text></span>
      <xsl:apply-templates select="ooexception" mode="java"/>
      </xsl:if>
      <xsl:text>&nbsp;{&RE;</xsl:text>
      <xsl:apply-templates select="constructorsynopsis
                                   |destructorsynopsis
                                   |fieldsynopsis
                                   |methodsynopsis
                                   |classsynopsisinfo" mode="java"/>
      <xsl:text>}</xsl:text>
    </pre>
  </xsl:template>

  <xsl:template match="fieldsynopsis" mode="java">
    <xsl:choose>
      <xsl:when test="ancestor::classsynopsis">
	<xsl:text>&nbsp;&nbsp;</xsl:text>
	<xsl:apply-templates mode="java"/>
	<xsl:text>;</xsl:text>
	<xsl:call-template name="synop-break"/>
      </xsl:when>
      <xsl:otherwise>
	<pre>
	  <xsl:text>&nbsp;&nbsp;</xsl:text>
	  <xsl:apply-templates mode="java"/>
	  <xsl:text>;</xsl:text>
	  <xsl:call-template name="synop-break"/>
	</pre>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template mode="java"
                match="constructorsynopsis|destructorsynopsis|methodsynopsis">
    <xsl:choose>
      <xsl:when test="ancestor::classsynopsis">
	<xsl:call-template name="write-method-synopsis"/>
      </xsl:when>
      <xsl:otherwise>
	<pre>
	  <xsl:call-template name="write-method-synopsis"/>
	</pre>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:call-template name="synop-break"/>
  </xsl:template>

  <xsl:template match="ooclass" mode="java"><xsl:if test="position() &gt; 1"><xsl:text>, </xsl:text></xsl:if><xsl:apply-templates mode="java"/></xsl:template>
  
  <xsl:template match="oointerface" mode="java">
    <xsl:choose>
      <xsl:when test="name(preceding-sibling::*[1]) = 'oointerface'">
        <xsl:text>,&RE;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text></xsl:text>
      </xsl:otherwise>
      </xsl:choose><xsl:apply-templates mode="java"/>
  </xsl:template>
  
  <xsl:template match="classname" mode="java">
    <xsl:if test="name(preceding-sibling::*[1]) = 'classname'">
      <xsl:text>, </xsl:text>
      </xsl:if><span xsl:use-attribute-sets="synopsis.name"><xsl:apply-templates mode="java"/></span>
  </xsl:template>

  <xsl:template match="interfacename" mode="java">
    <xsl:if test="name(preceding-sibling::*[1]) = 'interfacename'">
      <xsl:text>, </xsl:text>
      </xsl:if><span xsl:use-attribute-sets="synopsis.name"><xsl:apply-templates mode="java"/></span>
  </xsl:template>

  <xsl:template match="exceptionname" mode="java">
    <xsl:if test="name(preceding-sibling::*[1]) = 'exceptionname'">
      <xsl:text>,&RE;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</xsl:text>
      </xsl:if><span xsl:use-attribute-sets="synopsis.name"><xsl:apply-templates mode="java"/></span>
  </xsl:template>

  <xsl:template match="methodname" mode="java">
    <span xsl:use-attribute-sets="synopsis.name"><xsl:apply-templates mode="java"/></span>
  </xsl:template>

  <xsl:template match="methodparam" mode="java">
    <xsl:param name="indent">0</xsl:param>
    <xsl:param name="decl-length">0</xsl:param>
    <xsl:if test="preceding-sibling::methodparam">
      <xsl:choose>
        <xsl:when test="$decl-length &gt; 80">
          <xsl:text>,&RE;</xsl:text>
          <xsl:if test="$indent &gt; 0">
            <xsl:call-template name="copy-string">
              <xsl:with-param name="string">&nbsp;</xsl:with-param>
              <xsl:with-param name="count" select="$indent + 1"/>
            </xsl:call-template>
          </xsl:if>
        </xsl:when>
        <xsl:otherwise>
          <xsl:text>, </xsl:text>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>
    <xsl:apply-templates mode="java"/>
  </xsl:template>

  <xsl:template name="get-decl-length">
    <xsl:param name="indent">0</xsl:param>
    <xsl:variable name="buffer">
      <xsl:for-each select="methodparam">
        <xsl:if test="position()>1">
          <xsl:text>, </xsl:text>
        </xsl:if>
        <xsl:value-of select="type"/>
        <xsl:text> </xsl:text>
        <xsl:value-of select="parameter"/>
        <xsl:text> </xsl:text>
      </xsl:for-each>
    </xsl:variable>
    <xsl:value-of select="$indent + string-length($buffer)"/>
  </xsl:template>

  <xsl:template match="type" mode="java">
    <span xsl:use-attribute-sets="synopsis.type"><xsl:apply-templates mode="java"/></span>
    <xsl:text>&nbsp;</xsl:text>
  </xsl:template>

  <xsl:template match="varname" mode="java">
    <span xsl:use-attribute-sets="synopsis.name"><xsl:apply-templates mode="java"/></span>
    <xsl:choose>
      <xsl:when test="name(.. ) = 'fieldsynopsis'">
        <xsl:if test="count(../initializer) &gt; 0">
          <xsl:text>&nbsp;</xsl:text>
        </xsl:if>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>&nbsp;</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="parameter" mode="java">
    <span xsl:use-attribute-sets="synopsis.name"><xsl:apply-templates mode="java"/></span>
  </xsl:template>

  <xsl:template match="modifier" mode="java">
    <xsl:choose>
      <xsl:when test="@role = 'annotation'">
	<span xsl:use-attribute-sets="synopsis.annotation">
	  <xsl:apply-templates mode="java"/>
	</span>
      </xsl:when>
      <xsl:otherwise>
	<span xsl:use-attribute-sets="synopsis.keyword">
	  <xsl:apply-templates mode="java"/>
	</span>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:choose>
      <xsl:when test="@role = 'annotation'">
        <xsl:choose>
          <xsl:when test="local-name(..)='ooclass'"><xsl:text>&RE;</xsl:text>
          </xsl:when>
          <xsl:otherwise><xsl:text>&RE;&nbsp;&nbsp;</xsl:text></xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise><xsl:text>&nbsp;</xsl:text></xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template name="write-method-synopsis">
    <xsl:variable name="start-modifiers" select="modifier[following-sibling::*[name(.) != 'modifier']]"/>
    <xsl:variable name="notmod" select="*[name(.) != 'modifier']"/>
    <xsl:variable name="end-modifiers" select="modifier[preceding-sibling::*[name(.) != 'modifier']]"/>
    <xsl:variable name="decl">
      <xsl:text>&nbsp;&nbsp;</xsl:text>
      <xsl:apply-templates select="$start-modifiers" mode="java"/>
      <!-- type -->
      <xsl:if test="name($notmod[1]) != 'methodname'">
        <xsl:apply-templates select="$notmod[1]" mode="java"/>
      </xsl:if>
      <xsl:apply-templates select="methodname" mode="java"/>
    </xsl:variable>
    <xsl:copy-of select="$decl"/>
    <xsl:text>(</xsl:text>
    <xsl:variable name="decl-length">
      <xsl:call-template name="get-decl-length">
	<xsl:with-param name="indent" select="string-length($decl)"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:apply-templates select="methodparam" mode="java">
      <xsl:with-param name="indent" select="string-length($decl)"/>
      <xsl:with-param name="decl-length" select="$decl-length"/>
    </xsl:apply-templates>
    <xsl:text>)</xsl:text>
    <xsl:if test="exceptionname">
      <span xsl:use-attribute-sets="synopsis.keyword"><xsl:text>&RE;&nbsp;&nbsp;&nbsp;&nbsp;throws&nbsp;</xsl:text></span>
      <xsl:apply-templates select="exceptionname" mode="java"/>
    </xsl:if>
    <xsl:if test="modifier[preceding-sibling::*[name(.) != 'modifier']]">
      <xsl:text> </xsl:text>
      <xsl:apply-templates select="$end-modifiers" mode="java"/>
    </xsl:if>
    <xsl:text>;</xsl:text>
  </xsl:template>

</xsl:stylesheet>
