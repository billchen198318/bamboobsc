/* for Min */
String QUARTER_1 = "Q1";
String QUARTER_2 = "Q2";
String QUARTER_3 = "Q3";
String QUARTER_4 = "Q4";
String HALF_YEAR_FIRST = "first";
String HALF_YEAR_LAST = "last";
com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.loadExpression();
for (com.netsteadfast.greenstep.vo.DateRangeScoreVO dateScore : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getDateRangeScores()) {
	float score = 0.0f;
	float nowScore = 0.0f;
	int size = 0;
	for (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas()) {
		String date = dateScore.getDate().replaceAll("/", "");
		if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4
			String yyyy = date.substring(0, 4);
			if (date.endsWith(QUARTER_1)) {
				if (!(yyyy+"0101").equals(measureData.getDate())) { // Q1
					continue;
				}
			}
			if (date.endsWith(QUARTER_2)) {
				if (!(yyyy+"0401").equals(measureData.getDate())) { // Q2
					continue;
				}						
			}
			if (date.endsWith(QUARTER_3)) {
				if (!(yyyy+"0701").equals(measureData.getDate())) { // Q3
					continue;
				}							
			}
			if (date.endsWith(QUARTER_4)) {
				if (!(yyyy+"1001").equals(measureData.getDate())) { // Q4
					continue;
				}						
			}					
		} else if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last
			String yyyy = date.substring(0, 4);
			if (date.endsWith(HALF_YEAR_FIRST)) {
				if (!(yyyy+"0101").equals(measureData.getDate())) { // first-half
					continue;
				}						
			}
			if (date.endsWith(HALF_YEAR_LAST)) {
				if (!(yyyy+"0701").equals(measureData.getDate())) { // last-half
					continue;
				}							
			}										
		} else { // DAY, WEEK, MONTH, YEAR
			if (!measureData.getDate().startsWith(date)) {
				continue;
			}					
		}				
		com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();
		data.setActual( measureData.getActual() );
		data.setTarget( measureData.getTarget() );
		Object value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);
		if (value == null) {
			continue;
		}
		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {
			continue;
		}
		nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);
		if ( size < 1 ) {
			score = nowScore;
		} else { // Min
			if ( score > nowScore ) {
				score = nowScore;
			}
		}
		size++;
	}
	dateScore.setScore(score);
	dateScore.setFontColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getFontColor(score) );
	dateScore.setBgColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getBackgroundColor(score) );
	dateScore.setImgIcon( com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.getHtmlIcon(kpi, score) );
}	
