<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8" indent="yes"/>
  <xsl:template match="/">
    <html lang="hu">
      <head>
        <meta charset="UTF-8"/>
        <title>Órarend</title>
        <link rel="stylesheet" href="orarend.css" type="text/css"/>
      </head>
      <body>
        <h2>Órarend</h2>
        <table>
          <tr>
            <th>Idő</th>
            <th>Hétfő</th>
            <th>Kedd</th>
            <th>Szerda</th>
            <th>Csütörtök</th>
          </tr>
          <!-- 8-10 -->
          <xsl:call-template name="row">
            <xsl:with-param name="from" select="'8'"/>
            <xsl:with-param name="to" select="'10'"/>
          </xsl:call-template>
          <!-- 10-12 -->
          <xsl:call-template name="row">
            <xsl:with-param name="from" select="'10'"/>
            <xsl:with-param name="to" select="'12'"/>
          </xsl:call-template>
          <!-- 12-14 -->
          <xsl:call-template name="row">
            <xsl:with-param name="from" select="'12'"/>
            <xsl:with-param name="to" select="'14'"/>
          </xsl:call-template>
          <!-- 14-16 -->
          <xsl:call-template name="row">
            <xsl:with-param name="from" select="'14'"/>
            <xsl:with-param name="to" select="'16'"/>
          </xsl:call-template>
          <!-- 16-18 -->
          <xsl:call-template name="row">
            <xsl:with-param name="from" select="'16'"/>
            <xsl:with-param name="to" select="'18'"/>
          </xsl:call-template>
          <!-- 18-20 -->
          <xsl:call-template name="row">
            <xsl:with-param name="from" select="'18'"/>
            <xsl:with-param name="to" select="'20'"/>
          </xsl:call-template>
        </table>
      </body>
    </html>
  </xsl:template>

  <xsl:template name="row">
    <xsl:param name="from"/>
    <xsl:param name="to"/>
    <tr>
      <th><xsl:value-of select="$from"/>-<xsl:value-of select="$to"/></th>
      <xsl:call-template name="cell">
        <xsl:with-param name="nap" select="'hetfo'"/>
        <xsl:with-param name="from" select="$from"/>
        <xsl:with-param name="to" select="$to"/>
      </xsl:call-template>
      <xsl:call-template name="cell">
        <xsl:with-param name="nap" select="'kedd'"/>
        <xsl:with-param name="from" select="$from"/>
        <xsl:with-param name="to" select="$to"/>
      </xsl:call-template>
      <xsl:call-template name="cell">
        <xsl:with-param name="nap" select="'szerda'"/>
        <xsl:with-param name="from" select="$from"/>
        <xsl:with-param name="to" select="$to"/>
      </xsl:call-template>
      <xsl:call-template name="cell">
        <xsl:with-param name="nap" select="'csutortok'"/>
        <xsl:with-param name="from" select="$from"/>
        <xsl:with-param name="to" select="$to"/>
      </xsl:call-template>
    </tr>
  </xsl:template>

  <xsl:template name="cell">
    <xsl:param name="nap"/>
    <xsl:param name="from"/>
    <xsl:param name="to"/>
    <xsl:variable name="lesson" select="/HPQ9EO_orarend/ora[idopont/nap=$nap and idopont/tol=$from and idopont/ig=$to]"/>
    <td>
      <xsl:if test="$lesson">
        <xsl:attribute name="class">
          <xsl:value-of select="$lesson/@tipus"/>
        </xsl:attribute>
        <xsl:value-of select="$lesson/targy"/><br/>
        <small>
          <xsl:value-of select="$lesson/helyszin"/><br/>
          <xsl:value-of select="$lesson/oktato"/>
        </small>
      </xsl:if>
    </td>
  </xsl:template>
</xsl:stylesheet>