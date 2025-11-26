<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" />
    <xsl:template match="/">
        <html>
            <head>
                <title>Órarend</title>
                <meta charset="UTF-8" />
            </head>
            <body>
                <h2>Balogh DÁvid Órarend - 225. I. félév</h2>
                <table border="1" cellpadding="5">
                    <tr bgcolor="#90EE90">
                        <th>ID</th>
                        <th>Tárgy</th>
                        <th>Típus</th>
                        <th>Nap</th>
                        <th>Időpont</th>
                        <th>Helyszín</th>
                        <th>Oktató</th>
                    </tr>
                    <!--  Minden student bejárása  -->
                    <xsl:for-each select="HPQ9EO_orarend/ora">
                        <tr>
                            <td>
                                <xsl:value-of select="@id" />
                            </td>
                            <td>
                                <xsl:value-of select="targy" />
                            </td>
                            <td>
                                <xsl:value-of select="@tipus" />
                            </td>
                            <td>
                                <xsl:value-of select="idopont/nap"></xsl:value-of>
                            </td>
                            <td>
                                <xsl:value-of select="idopont/tol">-</xsl:value-of>
                                <xsl:value-of select="idopont/ig"></xsl:value-of>
                            </td>
                            <td>
                                <xsl:value-of select="helyszin" />
                            </td>
                            <td>
                                <xsl:value-of select="oktato" />
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
    <xsl:output></xsl:output>
</xsl:stylesheet>