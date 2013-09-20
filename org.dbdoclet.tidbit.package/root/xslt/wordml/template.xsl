<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
	xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
	xmlns:sl="http://schemas.microsoft.com/schemaLibrary/2003/core"
	xmlns:aml="http://schemas.microsoft.com/aml/2001/core" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint"
	xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">

	<xsl:import href="styles.xsl" />
	<xsl:output method="xml" version="1.0" encoding="UTF-8"
		indent="no" />

	<xsl:template match="/">
		<xsl:processing-instruction name="mso-application">
			progid="Word.Document"
		</xsl:processing-instruction>
		<w:wordDocument xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
			xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
			xmlns:sl="http://schemas.microsoft.com/schemaLibrary/2003/core"
			xmlns:aml="http://schemas.microsoft.com/aml/2001/core" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint"
			xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"
			w:macrosPresent="no" w:embeddedObjPresent="no" w:ocxPresent="no"
			xmlns:doc="http://docbook.org/ns/5.0">
        
        <xsl:call-template name="styles" />
        <w:body>
          <xsl:apply-templates />
          <w:sectPr>
            <w:pgSz w:w="11906" w:h="16838" />
            <w:pgMar w:top="1417" w:right="1417" w:bottom="1134"
			w:left="1417" w:header="708" w:footer="708" w:gutter="0" />
            <w:cols w:space="708" />
            <w:docGrid w:line-pitch="360" />
          </w:sectPr>
        </w:body>
      </w:wordDocument>
	</xsl:template>

</xsl:stylesheet>

