<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" encoding="utf-8" />
	<xsl:template match="invoiceDocument">
		<html>
			<head>
				<title>Faktur Penjualan</title>
				<!--  
				The Layout need to change regarding to the request
				-->
				<link type="text/css" rel="stylesheet" href="/acustic-application/javax.faces.resource/css/screen.css.jsf" media="print"/>
			</head>
			<body>
				<div id="invoiceContent">
					<div id="invoiceHeader">
						<div class="leftInfo">
							<h3><xsl:text>Faktur #</xsl:text><xsl:value-of select="invoiceSummary/invoiceNumber" /></h3>
						</div>
						<div class="rightInfo">
							<h3><xsl:value-of select="substring-before(@createdDate,'T')" /></h3>
						</div>
					</div>
					<!--  
					<xsl:apply-templates select="seller" />
					-->
					<div style="height:110px;width:100%">
						<xsl:apply-templates select="buyer" />
					</div>
					<div class="separator">
						<hr />
					</div>
					<div id="invoiceItemTable" style="width: 100%;">
						<!--  
						<div class="table">
							<div class="th">
								<div class="td-5">No</div>
								<div class="td">Nama Barang</div>
								<div class="td">Serial Number</div>
								<div class="td-5-num">Jumlah</div>
								<div class="td-15">Harga Satuan</div>
								<div class="td-num">Total</div>
								<div style="clear: both;"></div>
							</div>
						</div>
						-->
						<table class="inv-table" style="width: 100%;">
							<thead>
								<tr>
									<th style="width: 5%">No</th>
									<th style="text-align: left;">Nama Barang</th>
									<th style="text-align: left;">Serial Number</th>
									<th style="width: 15%;">Jumlah</th>
									<th style="width: 15%;">Harga Satuan</th>
									<th style="width: 20%;">Total</th>
								</tr>
								<tr>
									<th colspan="6"><hr /></th>
								</tr>
							</thead>
							<tfoot>
								<xsl:apply-templates select="invoiceSummary" />
							</tfoot>
							<tbody>
								<xsl:apply-templates select="tradeItem" />
							</tbody>
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="buyer">
		<div id="supplier_information">
			<table>
				<tr><td>Pembeli</td><td>:</td><td><xsl:value-of select="name" /></td></tr>
				<tr><td>Alamat</td><td>:</td><td><xsl:value-of select="streetAddressOne" /></td></tr>
				<tr><td>No. Telp</td><td>:</td><td><xsl:value-of select="phoneNumber" /></td></tr>
			</table>
		</div>
	</xsl:template>
	<!--  
	<xsl:template match="seller">
		<div id="supplier_information">
			<table>
				<tr>
					<td>Penjual</td>
					<td>:</td>
					<td><xsl:value-of select="name" /></td>
				</tr>
				<tr>
					<td>Alamat</td>
					<td>:</td>
					<td><xsl:value-of select="streetAndAddressOne" /></td>
				</tr>
				<tr>
					<td>No. Telp</td>
					<td>:</td>
					<td><xsl:value-of select="phoneNumber" /></td>
				</tr>
			</table>
		</div>
	</xsl:template>
	-->
	<xsl:template match="tradeItem">
		<xsl:variable name="number">
			<xsl:value-of select="@number + 1" />
		</xsl:variable>
		<xsl:variable name="serialNumber">
			<xsl:value-of select="replace(additionalTradeInformation/tradeItemInformation[tradeItemInformationType='serial_number']/tradeItemInformationValue, ',',', ')" />
		</xsl:variable>
		<tr>
			<td style="text-align: center;"><xsl:value-of select="$number" /></td>
			<td><xsl:value-of select="additionalTradeInformation/tradeItemInformation[tradeItemInformationType='product_name']/tradeItemInformationValue" /></td>
			<td><xsl:value-of select="$serialNumber" /></td>
			<td style="text-align: right;"><xsl:value-of select="additionalTradeInformation/tradeItemInformation[tradeItemInformationType='quantity']/tradeItemInformationValue" /></td>
			<td style="text-align: right;"><xsl:value-of select="format-number(additionalTradeInformation/tradeItemInformation[tradeItemInformationType='price']/tradeItemInformationValue, '###,###.00')" /></td>
			<td style="text-align: right;"><xsl:value-of select="format-number(total, '###,###.00')" /></td>
		</tr>
	</xsl:template>
	<xsl:template match="invoiceSummary">
		<tr>
			<td colspan="6"><hr /></td>
		</tr>
		<tr>
			<td colspan="4">Grand Total</td>
			<td colspan="2"><xsl:value-of select="format-number(invoiceTotal, '###,###.00')" /></td>
		</tr>
	</xsl:template>
</xsl:stylesheet>