<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="drzewo">
        <html>
            <head>
                <title><xsl:value-of select="dnazwa"/></title>
                <link rel="stylesheet" type="text/css" href="projekt_html.css" media="screen" />
            </head>
            <body>
                <xsl:apply-templates select="okresy"/>
                <xsl:apply-templates select="nisze"/>
                <xsl:apply-templates select="klady"/>
            </body>
        </html>
    </xsl:template>
    
    <!-- OKRESY -->
    <xsl:template name="okresy" match="okresy">
        <div class="grupa">
            <xsl:for-each select="okres">
                <xsl:sort select="poczatek" data-type="number" order="descending"/>
                <xsl:call-template name="okres"/>
            </xsl:for-each>
        </div>
    </xsl:template>
    <xsl:template name="okres" match="okres">
        <div>
            <xsl:value-of select="onazwa"/>
        </div>
    </xsl:template>
    
    <!-- NISZE -->
    <xsl:template name="nisze" match="nisze">
        <div class="grupa">
            <xsl:for-each select="nisza">
                <xsl:call-template name="nisza"/>
            </xsl:for-each>
        </div>
    </xsl:template>
    <xsl:template name="nisza" match="nisza">
        <div>
            <xsl:value-of select="nnazwa"/>
            <xsl:variable name="isrc" select="@img"/>
            <img src="{$isrc}"/>
        </div>
    </xsl:template>
    
    <!-- KLADY -->
    <xsl:template name="klady" match="klady">
        <div class="grupa">
            <xsl:for-each select="klad[not(@ojciec)]">
                <xsl:call-template name="klad"/>
            </xsl:for-each>
        </div>
    </xsl:template>
    <xsl:template name="klad" match="klad">
        <div>
            <xsl:value-of select="knazwa"/>
            <xsl:variable name="cid" select="@id"/>
            <ul>
                <xsl:for-each select="parent::klady/klad[@ojciec=$cid]">
                    <li>
                        <xsl:call-template name="klad"/>
                    </li>
                </xsl:for-each>
            </ul>
        </div>
    </xsl:template>
</xsl:stylesheet>
