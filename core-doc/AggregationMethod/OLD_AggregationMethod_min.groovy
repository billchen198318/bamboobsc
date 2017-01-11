/* for Min */
java.util.List<com.netsteadfast.greenstep.po.hbm.BbMeasureData> measureDatas = ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas();
score = 0.0f;
size = 0;
nowScore = 0.0f;
for (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : measureDatas) {
	com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();
	data.setActual( measureData.getActual() );
	data.setTarget( measureData.getTarget() );
	Object value = null;
	try {
		value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);
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
	} catch (Exception e) {
		e.printStackTrace();
	}
}
