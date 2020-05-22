<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="drzewo">
        <xsl:text disable-output-escaping="yes">&lt;!DOCTYPE html&gt;</xsl:text>
        <xsl:text>
            
        </xsl:text>
        <html lang="pl">
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
                    <td>W Sumie</td>
                    <td><xsl:value-of select="max(okres/poczatek)"/></td>
                    <td><xsl:value-of select="min(okres/koniec)"/></td>
                    <td><xsl:value-of select="format-number(max(okres/poczatek) - min(okres/koniec), '#.##')"/></td>
                    <td>-</td>
                </tr>
            </table>
        </div>
    </xsl:template>
    <xsl:template name="okres" match="okres">
        <tr>
            <td><xsl:value-of select="onazwa"/></td>
            <td><xsl:value-of select="poczatek"/></td>
            <td><xsl:value-of select="koniec"/></td>
            <td><xsl:value-of select="format-number(poczatek - koniec, '#.##')"/></td>
            <td><xsl:value-of select="@skrot"/></td>
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
            <img alt="(no image)" class="symbol" src="{$isrc}"/>
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
                <xsl:choose>
                    <xsl:when test="not(@ranga)">
                        Klad
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:variable name="ranga" select="@ranga"/>
                        <xsl:value-of select="/drzewo/rangi/ranga[@id=$ranga]"/>
                    </xsl:otherwise>
                </xsl:choose>
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
        <xsl:variable name="nisza" select="@nisza"/>
        <xsl:variable name="psrc" select="@img"/>
        <xsl:variable name="isrc" select="/drzewo/nisze/nisza[@id=$nisza]/@img"/>
        <div class="gatunek">
            <img alt="(no image)" class="symbol" src="{$isrc}"/>
            <h4 class="gatunek">
                <xsl:value-of select="/drzewo/klady/klad[@id=$rodzaj]/knazwa"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="gnazwa"/>
                <xsl:if test="@wymarly=1">&#x271D;</xsl:if>
            </h4>
            <img alt="(no image)" class="portret" src="{$psrc}"/>
            <div class="gdane">
                <table>
                    <tr>
                        <td>Data odkrycia: </td>
                        <td><xsl:value-of select="data_odkrycia"/></td>
                    </tr>
                    <tr>
                        <td>Najwcześniejsze datowanie: </td>
                        <td><xsl:value-of select="najwcz_datowanie"/></td>
                    </tr>
                </table>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>
