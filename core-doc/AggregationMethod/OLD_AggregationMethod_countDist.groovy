java.util.List<com.netsteadfast.greenstep.po.hbm.BbMeasureData> measureDatas = ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas();
java.util.List<java.lang.Float> scores = new java.util.ArrayList<java.lang.Float>();
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
      		if ( !scores.contains(nowScore) ) {
			scores.add( nowScore );
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
score = java.lang.Float.valueOf( scores.size() );
