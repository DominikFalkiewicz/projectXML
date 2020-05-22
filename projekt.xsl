<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="drzewo">
        <!--
        <xsl:text disable-output-escaping="yes">&lt;!DOCTYPE html&gt;</xsl:text>
        <xsl:text>
            
        </xsl:text>
        -->
        <html>
            <head>
                <title><xsl:value-of select="dnazwa"/></title>
                <link rel="stylesheet" type="text/css" href="projekt_html.css" media="screen" />
            </head>
            <xsl:variable name="main_bcg" select="@bcg"/>
            <body style="background:url({$main_bcg})">
                <h1>
                    <xsl:value-of select="dnazwa"/>
                </h1>
                <div class="legenda">
                    <h2>Legenda</h2>
                    <xsl:apply-templates select="okresy"/>
                    <xsl:apply-templates select="nisze"/>
                </div>
                <xsl:apply-templates select="klady"/>
            </body>
        </html>
    </xsl:template>
    
    <!-- OKRESY -->
    <xsl:template name="okresy" match="okresy">
        <div class="pod_legenda">
            <h3>Okresy</h3>
            <table>
                <tr>
                    <th>Okres</th>
                    <th>Początek</th>
                    <th>Koniec</th>
                    <th>Czas Trwania</th>
                    <th>Symbol</th>
                </tr>
                <xsl:for-each select="okres">
                    <xsl:sort select="poczatek" data-type="number" order="descending"/>
                    <xsl:call-template name="okres"/>
                </xsl:for-each>
                <tr>
                    <th>W Sumie</th>
                    <th><xsl:value-of select="max(okres/poczatek)"/></th>
                    <th><xsl:value-of select="min(okres/koniec)"/></th>
                    <th><xsl:value-of select="format-number(max(okres/poczatek) - min(okres/koniec), '#.##')"/></th>
                    <th>-</th>
                </tr>
            </table>
        </div>
    </xsl:template>
    <xsl:template name="okres" match="okres">
        <tr>
            <th><xsl:value-of select="onazwa"/></th>
            <th><xsl:value-of select="poczatek"/></th>
            <th><xsl:value-of select="koniec"/></th>
            <th><xsl:value-of select="format-number(poczatek - koniec, '#.##')"/></th>
            <th><xsl:value-of select="@skrot"/></th>
        </tr>
    </xsl:template>
    
    <!-- NISZE -->
    <xsl:template name="nisze" match="nisze">
        <div class="pod_legenda">
            <h3>Nisze</h3>
            <xsl:for-each select="nisza">
                <xsl:call-template name="nisza"/>
            </xsl:for-each>
        </div>
    </xsl:template>
    <xsl:template name="nisza" match="nisza">
        <xsl:variable name="isrc" select="@img"/>
        <div class="nisza">
            <img class="symbol" src="{$isrc}"/>
            <h4 class="nisza"><xsl:value-of select="nnazwa"/></h4>
            <div class="opis"><xsl:value-of select="nopis"/></div>
        </div>
    </xsl:template>
    
    <!-- KLADY -->
    <xsl:template name="klady" match="klady">
        <div class="organizmy">
            <h2>Organizmy</h2>
            <xsl:for-each select="klad[not(@ojciec)]">
                <xsl:call-template name="klad"/>
            </xsl:for-each>
        </div>
    </xsl:template>
    <xsl:template name="klad" match="klad">
        <xsl:variable name="cid" select="@id"/>
        <xsl:variable name="oksy" select="@okresy"/>
        <div class="klad">
            <h4>
                <xsl:value-of select="knazwa"/>
                <xsl:if test="@wymarly=1">&#x271D;</xsl:if>
                (<xsl:value-of select="/drzewo/okresy/okres[contains($oksy, @id)]/@skrot"/>)
            </h4>
            <xsl:if test="count(parent::klady/klad[@ojciec=$cid])>0">
                <dl>
                    <dt>Podklady</dt>
                    <xsl:for-each select="parent::klady/klad[@ojciec=$cid]">
                        <dd>
                            <xsl:call-template name="klad"/>
                        </dd>
                    </xsl:for-each>
                </dl>
            </xsl:if>
            <xsl:if test="count(/drzewo/gatunki/gatunek[@rodzaj=$cid])>0">
                <dl>
                    <dt>Gatunki</dt>
                    <xsl:for-each select="/drzewo/gatunki/gatunek[@rodzaj=$cid]">
                        <dd>
                            <xsl:call-template name="gatunek"/>
                        </dd>
                    </xsl:for-each>
                </dl>
            </xsl:if>
        </div>
    </xsl:template>
    
    <!-- GATUNKI -->
    <xsl:template name="gatunek" match="gatunek">
        <xsl:variable name="rodzaj" select="@rodzaj"/>
        <xsl:variable name="isrc" select="@img"/>
        <div class="gatunek">
            <h4 class="gatunek">
                <xsl:value-of select="/drzewo/klady/klad[@id=$rodzaj]/knazwa"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="gnazwa"/>
                <xsl:if test="@wymarly=1">&#x271D;</xsl:if>
            </h4>
            <img class="portret" src="{$isrc}"/>
        </div>
    </xsl:template>
</xsl:stylesheet>
