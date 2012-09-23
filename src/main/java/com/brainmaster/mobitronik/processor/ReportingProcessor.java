package com.brainmaster.mobitronik.processor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.brainmaster.mobitronik.dto.IncomeReportData;
import com.brainmaster.mobitronik.dto.StockReportData;
import com.brainmaster.mobitronik.dto.StoreData;

public interface ReportingProcessor {

	public List<IncomeReportData> getSellerIncomeReports(StoreData sellerStore,
			Date start, Date end);

	public String getTransformationResult(String uuidParam,
			String documentTypeParam);

	public List<StockReportData> getSellerProductReports(StoreData sellerStore,
			Date start, Date end);
	
	public String getReportTransformationResult(String sellerName, Date startDate, Date endDate, List<IncomeReportData> details, BigDecimal totalProfit);
}
