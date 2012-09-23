<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" encoding="utf-8" />
	<xsl:template match="incomeReportDocument">
		<html>
			<head>
				<title>Laporan Pendapatan Per Periode</title>
				<!--  
				The Layout need to change regarding to the request
				-->
				<link type="text/css" rel="stylesheet" href="/acustic-application/javax.faces.resource/css/screen.css.jsf" media="print"/>
			</head>
			<body>
				<div id="reportContent">
					<div id="reportHeader">
						<div class="leftInfo">
							<h3><xsl:value-of select="companyInformation/companyName"></xsl:value-of></h3>
						</div>
						<div class="rightInfo">
							<h3><xsl:text>Periode : </xsl:text><xsl:value-of select="period/start" /><xsl:text> - </xsl:text><xsl:value-of select="period/end" /></h3>
						</div>
					</div>
					<div class="separator">
						<hr />
					</div>
					<div id="reportItemTable" style="width: 100%;">
						<table class="report-table" style="width: 100%;">
							<thead>
								<tr>
									<th style="text-align: left;">Invoice Number</th>
									<th style="text-align: left;">Invoice Date</th>
									<th style="text-align: left;">Pembeli</th>
									<th style="width: 15%;">Total</th>
									<th style="width: 15%;">Profit</th>
								</tr>
								<tr>
									<th colspan="6"><hr /></th>
								</tr>
							</thead>
							<tfoot>
								<xsl:apply-templates select="summary" />
							</tfoot>
							<tbody>
								<xsl:apply-templates select="reportItems/reportItem" />
							</tbody>
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="reportItems/reportItem">
		<tr>
			<td style="text-align: left;"><xsl:value-of select="invoiceNumber" /></td>
			<td><xsl:value-of select="invoiceDate" /></td>
			<td><xsl:value-of select="buyerName" /></td>
			<td style="text-align: right;"><xsl:value-of select="format-number(invoiceTotal, '###,###.00')" /></td>
			<td style="text-align: right;"><xsl:value-of select="format-number(invoiceProfit, '###,###.00')" /></td>
		</tr>
	</xsl:template>
	<xsl:template match="summary">
		<tr>
			<td colspan="5"><hr /></td>
		</tr>
		<tr>
			<td colspan="3">Grand Total</td>
			<td colspan="1"><xsl:value-of select="format-number(totalInvoice, '###,###.00')" /></td>
			<td colspan="1"><xsl:value-of select="format-number(totalProfit, '###,###.00')" /></td>
		</tr>
	</xsl:template>
</xsl:stylesheet>