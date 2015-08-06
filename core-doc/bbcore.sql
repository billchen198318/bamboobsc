-- MySQL dump 10.13  Distrib 5.6.19, for Win32 (x86)
--
-- Host: localhost    Database: bbcore
-- ------------------------------------------------------
-- Server version	5.6.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bb_aggregation_method`
--

DROP TABLE IF EXISTS `bb_aggregation_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_aggregation_method` (
  `OID` char(36) NOT NULL,
  `AGGR_ID` varchar(14) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `EXPRESSION1` varchar(4000) NOT NULL,
  `EXPRESSION2` varchar(4000) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`AGGR_ID`),
  KEY `IDX_1` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_aggregation_method`
--

LOCK TABLES `bb_aggregation_method` WRITE;
/*!40000 ALTER TABLE `bb_aggregation_method` DISABLE KEYS */;
INSERT INTO `bb_aggregation_method` VALUES ('23eeecb4-6a6c-4d06-92fb-5d702ab53b06','MAX_001','Max','GROOVY','/* for Max */\njava.util.List<com.netsteadfast.greenstep.po.hbm.BbMeasureData> measureDatas = ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas();\nscore = 0.0f;\nsize = 0;\nnowScore = 0.0f;\nfor (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : measureDatas) {\n	com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n	data.setActual( measureData.getActual() );\n	data.setTarget( measureData.getTarget() );\n	Object value = null;\n	try {\n		value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n		if ( size < 1 ) {\n			score = nowScore;\n		} else { // Max\n			if ( score < nowScore ) {\n				score = nowScore;\n			}\n		}\n		size++;\n	} catch (Exception e) {\n		e.printStackTrace();\n	}\n}\n','/* for Max */\nString QUARTER_1 = \"Q1\";\nString QUARTER_2 = \"Q2\";\nString QUARTER_3 = \"Q3\";\nString QUARTER_4 = \"Q4\";\nString HALF_YEAR_FIRST = \"first\";\nString HALF_YEAR_LAST = \"last\";\ncom.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.loadExpression();\nfor (com.netsteadfast.greenstep.vo.DateRangeScoreVO dateScore : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getDateRangeScores()) {\n	float score = 0.0f;\n	float nowScore = 0.0f;\n	int size = 0;\n	for (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas()) {\n		String date = dateScore.getDate().replaceAll(\"/\", \"\");\n		if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(QUARTER_1)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // Q1\n					continue;\n				}\n			}\n			if (date.endsWith(QUARTER_2)) {\n				if (!(yyyy+\"0401\").equals(measureData.getDate())) { // Q2\n					continue;\n				}						\n			}\n			if (date.endsWith(QUARTER_3)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // Q3\n					continue;\n				}							\n			}\n			if (date.endsWith(QUARTER_4)) {\n				if (!(yyyy+\"1001\").equals(measureData.getDate())) { // Q4\n					continue;\n				}						\n			}					\n		} else if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(HALF_YEAR_FIRST)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // first-half\n					continue;\n				}						\n			}\n			if (date.endsWith(HALF_YEAR_LAST)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // last-half\n					continue;\n				}							\n			}										\n		} else { // DAY, WEEK, MONTH, YEAR\n			if (!measureData.getDate().startsWith(date)) {\n				continue;\n			}					\n		}				\n		com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n		data.setActual( measureData.getActual() );\n		data.setTarget( measureData.getTarget() );\n		Object value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n		if ( size < 1 ) {\n			score = nowScore;\n		} else { // Max\n			if ( score < nowScore ) {\n				score = nowScore;\n			}\n		}\n		size++;\n	}\n	dateScore.setScore(score);\n	dateScore.setFontColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getFontColor(score) );\n	dateScore.setBgColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getBackgroundColor(score) );\n	dateScore.setImgIcon( com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.getHtmlIcon(kpi, score) );\n}	\n','get max score!','admin','2015-03-12 12:16:19','admin','2015-04-18 12:46:30'),('422456ae-a8da-4337-a26e-82d108abf86d','CNT_002','Count Dist (count distinct)','GROOVY','java.util.List<com.netsteadfast.greenstep.po.hbm.BbMeasureData> measureDatas = ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas();\njava.util.List<java.lang.Float> scores = new java.util.ArrayList<java.lang.Float>();\nfor (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : measureDatas) {\n	com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n	data.setActual( measureData.getActual() );\n	data.setTarget( measureData.getTarget() );\n	Object value = null;\n	try {\n		value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n      		if ( !scores.contains(nowScore) ) {\n			scores.add( nowScore );\n		}\n	} catch (Exception e) {\n		e.printStackTrace();\n	}\n}\nscore = java.lang.Float.valueOf( scores.size() );\n','String QUARTER_1 = \"Q1\";\nString QUARTER_2 = \"Q2\";\nString QUARTER_3 = \"Q3\";\nString QUARTER_4 = \"Q4\";\nString HALF_YEAR_FIRST = \"first\";\nString HALF_YEAR_LAST = \"last\";\ncom.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.loadExpression();\nfor (com.netsteadfast.greenstep.vo.DateRangeScoreVO dateScore : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getDateRangeScores()) {\n	java.util.List<java.lang.Float> scores = new java.util.ArrayList<java.lang.Float>();\n	float score = 0.0f;\n	int size = 0;\n	for (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas()) {\n		String date = dateScore.getDate().replaceAll(\"/\", \"\");\n		if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(QUARTER_1)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // Q1\n					continue;\n				}\n			}\n			if (date.endsWith(QUARTER_2)) {\n				if (!(yyyy+\"0401\").equals(measureData.getDate())) { // Q2\n					continue;\n				}						\n			}\n			if (date.endsWith(QUARTER_3)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // Q3\n					continue;\n				}							\n			}\n			if (date.endsWith(QUARTER_4)) {\n				if (!(yyyy+\"1001\").equals(measureData.getDate())) { // Q4\n					continue;\n				}						\n			}					\n		} else if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(HALF_YEAR_FIRST)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // first-half\n					continue;\n				}						\n			}\n			if (date.endsWith(HALF_YEAR_LAST)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // last-half\n					continue;\n				}							\n			}										\n		} else { // DAY, WEEK, MONTH, YEAR\n			if (!measureData.getDate().startsWith(date)) {\n				continue;\n			}					\n		}		\n		com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n		data.setActual( measureData.getActual() );\n		data.setTarget( measureData.getTarget() );\n		Object value = null;\n		try {\n			value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n			if (value == null) {\n				continue;\n			}\n			if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n				continue;\n			}\n			nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n      			if ( !scores.contains(nowScore) ) {\n				scores.add( nowScore );\n			}\n		} catch (Exception e) {\n			e.printStackTrace();\n		}		\n	}\n    	score = java.lang.Float.valueOf( scores.size() );\n	dateScore.setScore(score);\n	dateScore.setFontColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getFontColor(score) );\n	dateScore.setBgColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getBackgroundColor(score) );\n	dateScore.setImgIcon( com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.getHtmlIcon(kpi, score) );\n}\n','For example, the count of (1, 1, 2, 3) is 4, but the distinct count of (1, 1, 2, 3) is 3.','admin','2015-04-22 14:51:07','admin','2015-04-22 15:09:16'),('627ffc6c-c24d-4bf7-a7fa-02c2c7ec03fa','AVG_002','Avg Dist (average distinct)','GROOVY','java.util.List<com.netsteadfast.greenstep.po.hbm.BbMeasureData> measureDatas = ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas();\njava.util.List<java.lang.Float> scores = new java.util.ArrayList<java.lang.Float>();\nscore = 0.0f;\nsize = 0;\nfor (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : measureDatas) {\n	com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n	data.setActual( measureData.getActual() );\n	data.setTarget( measureData.getTarget() );\n	Object value = null;\n	try {\n		value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n      		if ( !scores.contains(nowScore) ) {\n			scores.add( nowScore );\n			score += nowScore;\n			size++;\n		}\n	} catch (Exception e) {\n		e.printStackTrace();\n	}\n}\nif ( score != 0.0f && size>0 ) {\n	score = score / size;\n}\n','String QUARTER_1 = \"Q1\";\nString QUARTER_2 = \"Q2\";\nString QUARTER_3 = \"Q3\";\nString QUARTER_4 = \"Q4\";\nString HALF_YEAR_FIRST = \"first\";\nString HALF_YEAR_LAST = \"last\";\ncom.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.loadExpression();\nfor (com.netsteadfast.greenstep.vo.DateRangeScoreVO dateScore : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getDateRangeScores()) {\n	java.util.List<java.lang.Float> scores = new java.util.ArrayList<java.lang.Float>();\n	float score = 0.0f;\n	int size = 0;\n	for (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas()) {\n		String date = dateScore.getDate().replaceAll(\"/\", \"\");\n		if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(QUARTER_1)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // Q1\n					continue;\n				}\n			}\n			if (date.endsWith(QUARTER_2)) {\n				if (!(yyyy+\"0401\").equals(measureData.getDate())) { // Q2\n					continue;\n				}						\n			}\n			if (date.endsWith(QUARTER_3)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // Q3\n					continue;\n				}							\n			}\n			if (date.endsWith(QUARTER_4)) {\n				if (!(yyyy+\"1001\").equals(measureData.getDate())) { // Q4\n					continue;\n				}						\n			}					\n		} else if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(HALF_YEAR_FIRST)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // first-half\n					continue;\n				}						\n			}\n			if (date.endsWith(HALF_YEAR_LAST)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // last-half\n					continue;\n				}							\n			}										\n		} else { // DAY, WEEK, MONTH, YEAR\n			if (!measureData.getDate().startsWith(date)) {\n				continue;\n			}					\n		}				\n		com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n		data.setActual( measureData.getActual() );\n		data.setTarget( measureData.getTarget() );\n		Object value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n      		if ( !scores.contains(nowScore) ) {\n			scores.add( nowScore );\n			score += nowScore;\n			size++;\n		}\n	}\n	if ( score != 0.0f && size>0 ) {\n		score = score / size;\n	}\n	dateScore.setScore(score);\n	dateScore.setFontColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getFontColor(score) );\n	dateScore.setBgColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getBackgroundColor(score) );\n	dateScore.setImgIcon( com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.getHtmlIcon(kpi, score) );\n}	\n','For example, the average of (10, 10, 20) is 40/3, but the distinct average of (10, 10, 20) is 30/3.','admin','2015-04-22 14:39:02','admin','2015-04-22 14:46:46'),('72399a9c-8e2f-4ca1-ba49-baae21f50526','SUM_002','Sum Dist (sum distinct)','GROOVY','java.util.List<com.netsteadfast.greenstep.po.hbm.BbMeasureData> measureDatas = ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas();\njava.util.List<java.lang.Float> scores = new java.util.ArrayList<java.lang.Float>();\nscore = 0.0f;\nsize = 0;\nfor (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : measureDatas) {\n	com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n	data.setActual( measureData.getActual() );\n	data.setTarget( measureData.getTarget() );\n	Object value = null;\n	try {\n		value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n	      	nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n      		if ( !scores.contains(nowScore) ) {\n			scores.add( nowScore );\n			size++;\n			score += nowScore;\n		}\n	} catch (Exception e) {\n		e.printStackTrace();\n	}\n}\n','String QUARTER_1 = \"Q1\";\nString QUARTER_2 = \"Q2\";\nString QUARTER_3 = \"Q3\";\nString QUARTER_4 = \"Q4\";\nString HALF_YEAR_FIRST = \"first\";\nString HALF_YEAR_LAST = \"last\";\ncom.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.loadExpression();\nfor (com.netsteadfast.greenstep.vo.DateRangeScoreVO dateScore : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getDateRangeScores()) {\n	java.util.List<java.lang.Float> scores = new java.util.ArrayList<java.lang.Float>();\n	float score = 0.0f;\n	int size = 0;\n	for (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas()) {\n		String date = dateScore.getDate().replaceAll(\"/\", \"\");\n		if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(QUARTER_1)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // Q1\n					continue;\n				}\n			}\n			if (date.endsWith(QUARTER_2)) {\n				if (!(yyyy+\"0401\").equals(measureData.getDate())) { // Q2\n					continue;\n				}						\n			}\n			if (date.endsWith(QUARTER_3)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // Q3\n					continue;\n				}							\n			}\n			if (date.endsWith(QUARTER_4)) {\n				if (!(yyyy+\"1001\").equals(measureData.getDate())) { // Q4\n					continue;\n				}						\n			}					\n		} else if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(HALF_YEAR_FIRST)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // first-half\n					continue;\n				}						\n			}\n			if (date.endsWith(HALF_YEAR_LAST)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // last-half\n					continue;\n				}							\n			}										\n		} else { // DAY, WEEK, MONTH, YEAR\n			if (!measureData.getDate().startsWith(date)) {\n				continue;\n			}					\n		}				\n		com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n		data.setActual( measureData.getActual() );\n		data.setTarget( measureData.getTarget() );\n		Object value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n	      	nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n      		if ( !scores.contains(nowScore) ) {\n			scores.add( nowScore );\n			size++;\n			score += nowScore;\n		}\n	}\n	dateScore.setScore(score);\n	dateScore.setFontColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getFontColor(score) );\n	dateScore.setBgColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getBackgroundColor(score) );\n	dateScore.setImgIcon( com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.getHtmlIcon(kpi, score) );\n}	\n','For example, the sum of (10,10) is 20, but the distinct sum of (10,10) is 10.','admin','2015-04-22 14:21:18','admin','2015-04-22 14:48:56'),('8027026c-77bc-497e-bb44-1cf9e98a1570','SUM_001','Sum','GROOVY','java.util.List<com.netsteadfast.greenstep.po.hbm.BbMeasureData> measureDatas = ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas();\nscore = 0.0f;\nsize = 0;\nfor (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : measureDatas) {\n	com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n	data.setActual( measureData.getActual() );\n	data.setTarget( measureData.getTarget() );\n	Object value = null;\n	try {\n		value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		score += org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n		size++;\n	} catch (Exception e) {\n		e.printStackTrace();\n	}\n}\n','String QUARTER_1 = \"Q1\";\nString QUARTER_2 = \"Q2\";\nString QUARTER_3 = \"Q3\";\nString QUARTER_4 = \"Q4\";\nString HALF_YEAR_FIRST = \"first\";\nString HALF_YEAR_LAST = \"last\";\ncom.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.loadExpression();\nfor (com.netsteadfast.greenstep.vo.DateRangeScoreVO dateScore : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getDateRangeScores()) {\n	float score = 0.0f;\n	int size = 0;\n	for (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas()) {\n		String date = dateScore.getDate().replaceAll(\"/\", \"\");\n		if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(QUARTER_1)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // Q1\n					continue;\n				}\n			}\n			if (date.endsWith(QUARTER_2)) {\n				if (!(yyyy+\"0401\").equals(measureData.getDate())) { // Q2\n					continue;\n				}						\n			}\n			if (date.endsWith(QUARTER_3)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // Q3\n					continue;\n				}							\n			}\n			if (date.endsWith(QUARTER_4)) {\n				if (!(yyyy+\"1001\").equals(measureData.getDate())) { // Q4\n					continue;\n				}						\n			}					\n		} else if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(HALF_YEAR_FIRST)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // first-half\n					continue;\n				}						\n			}\n			if (date.endsWith(HALF_YEAR_LAST)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // last-half\n					continue;\n				}							\n			}										\n		} else { // DAY, WEEK, MONTH, YEAR\n			if (!measureData.getDate().startsWith(date)) {\n				continue;\n			}					\n		}				\n		com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n		data.setActual( measureData.getActual() );\n		data.setTarget( measureData.getTarget() );\n		Object value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		score += org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n		size++;\n	}\n	dateScore.setScore(score);\n	dateScore.setFontColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getFontColor(score) );\n	dateScore.setBgColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getBackgroundColor(score) );\n	dateScore.setImgIcon( com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.getHtmlIcon(kpi, score) );\n}	\n','for Sum!','admin','2015-03-11 09:04:13','admin','2015-04-22 14:02:46'),('abc66064-41c9-4d30-9f2a-b7cee9bedd3e','AVG_001','Average','GROOVY','java.util.List<com.netsteadfast.greenstep.po.hbm.BbMeasureData> measureDatas = ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas();\nscore = 0.0f;\nsize = 0;\nfor (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : measureDatas) {\n	com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n	data.setActual( measureData.getActual() );\n	data.setTarget( measureData.getTarget() );\n	Object value = null;\n	try {\n		value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		score += org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n		size++;\n	} catch (Exception e) {\n		e.printStackTrace();\n	}\n}\nif ( score != 0.0f && size>0 ) {\n	score = score / size;\n}\n','String QUARTER_1 = \"Q1\";\nString QUARTER_2 = \"Q2\";\nString QUARTER_3 = \"Q3\";\nString QUARTER_4 = \"Q4\";\nString HALF_YEAR_FIRST = \"first\";\nString HALF_YEAR_LAST = \"last\";\ncom.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.loadExpression();\nfor (com.netsteadfast.greenstep.vo.DateRangeScoreVO dateScore : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getDateRangeScores()) {\n	float score = 0.0f;\n	int size = 0;\n	for (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas()) {\n		String date = dateScore.getDate().replaceAll(\"/\", \"\");\n		if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(QUARTER_1)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // Q1\n					continue;\n				}\n			}\n			if (date.endsWith(QUARTER_2)) {\n				if (!(yyyy+\"0401\").equals(measureData.getDate())) { // Q2\n					continue;\n				}						\n			}\n			if (date.endsWith(QUARTER_3)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // Q3\n					continue;\n				}							\n			}\n			if (date.endsWith(QUARTER_4)) {\n				if (!(yyyy+\"1001\").equals(measureData.getDate())) { // Q4\n					continue;\n				}						\n			}					\n		} else if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(HALF_YEAR_FIRST)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // first-half\n					continue;\n				}						\n			}\n			if (date.endsWith(HALF_YEAR_LAST)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // last-half\n					continue;\n				}							\n			}										\n		} else { // DAY, WEEK, MONTH, YEAR\n			if (!measureData.getDate().startsWith(date)) {\n				continue;\n			}					\n		}				\n		com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n		data.setActual( measureData.getActual() );\n		data.setTarget( measureData.getTarget() );\n		Object value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		score += org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n		size++;\n	}\n	if ( score != 0.0f && size>0 ) {\n		score = score / size;\n	}\n	dateScore.setScore(score);\n	dateScore.setFontColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getFontColor(score) );\n	dateScore.setBgColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getBackgroundColor(score) );\n	dateScore.setImgIcon( com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.getHtmlIcon(kpi, score) );\n}	\n','for Average!','admin','2015-03-11 09:05:24','admin','2015-04-18 12:46:13'),('ce7ea3a2-c9af-4ffc-bd3d-eeca557874da','MIN_001','Min','GROOVY','/* for Min */\njava.util.List<com.netsteadfast.greenstep.po.hbm.BbMeasureData> measureDatas = ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas();\nscore = 0.0f;\nsize = 0;\nnowScore = 0.0f;\nfor (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : measureDatas) {\n	com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n	data.setActual( measureData.getActual() );\n	data.setTarget( measureData.getTarget() );\n	Object value = null;\n	try {\n		value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n		if ( size < 1 ) {\n			score = nowScore;\n		} else { // Min\n			if ( score > nowScore ) {\n				score = nowScore;\n			}\n		}\n		size++;\n	} catch (Exception e) {\n		e.printStackTrace();\n	}\n}\n','/* for Min */\nString QUARTER_1 = \"Q1\";\nString QUARTER_2 = \"Q2\";\nString QUARTER_3 = \"Q3\";\nString QUARTER_4 = \"Q4\";\nString HALF_YEAR_FIRST = \"first\";\nString HALF_YEAR_LAST = \"last\";\ncom.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.loadExpression();\nfor (com.netsteadfast.greenstep.vo.DateRangeScoreVO dateScore : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getDateRangeScores()) {\n	float score = 0.0f;\n	float nowScore = 0.0f;\n	int size = 0;\n	for (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas()) {\n		String date = dateScore.getDate().replaceAll(\"/\", \"\");\n		if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(QUARTER_1)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // Q1\n					continue;\n				}\n			}\n			if (date.endsWith(QUARTER_2)) {\n				if (!(yyyy+\"0401\").equals(measureData.getDate())) { // Q2\n					continue;\n				}						\n			}\n			if (date.endsWith(QUARTER_3)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // Q3\n					continue;\n				}							\n			}\n			if (date.endsWith(QUARTER_4)) {\n				if (!(yyyy+\"1001\").equals(measureData.getDate())) { // Q4\n					continue;\n				}						\n			}					\n		} else if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(HALF_YEAR_FIRST)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // first-half\n					continue;\n				}						\n			}\n			if (date.endsWith(HALF_YEAR_LAST)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // last-half\n					continue;\n				}							\n			}										\n		} else { // DAY, WEEK, MONTH, YEAR\n			if (!measureData.getDate().startsWith(date)) {\n				continue;\n			}					\n		}				\n		com.netsteadfast.greenstep.bsc.model.BscMeasureData data = new com.netsteadfast.greenstep.bsc.model.BscMeasureData();\n		data.setActual( measureData.getActual() );\n		data.setTarget( measureData.getTarget() );\n		Object value = com.netsteadfast.greenstep.bsc.util.BscFormulaUtils.parse(kpi.getFormula(), data);\n		if (value == null) {\n			continue;\n		}\n		if ( !org.apache.commons.lang3.math.NumberUtils.isNumber( java.lang.String.valueOf(value) ) ) {\n			continue;\n		}\n		nowScore = org.apache.commons.lang3.math.NumberUtils.toFloat( java.lang.String.valueOf(value), 0.0f);\n		if ( size < 1 ) {\n			score = nowScore;\n		} else { // Min\n			if ( score > nowScore ) {\n				score = nowScore;\n			}\n		}\n		size++;\n	}\n	dateScore.setScore(score);\n	dateScore.setFontColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getFontColor(score) );\n	dateScore.setBgColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getBackgroundColor(score) );\n	dateScore.setImgIcon( com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.getHtmlIcon(kpi, score) );\n}	\n','get min score!','admin','2015-03-12 12:15:47','admin','2015-04-18 12:46:55'),('d416ea4c-8f64-47a3-a56c-ade0f86006b5','CNT_001','Count','GROOVY','java.util.List<com.netsteadfast.greenstep.po.hbm.BbMeasureData> measureDatas = ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas();\nscore = java.lang.Float.valueOf( measureDatas.size() );\n','String QUARTER_1 = \"Q1\";\nString QUARTER_2 = \"Q2\";\nString QUARTER_3 = \"Q3\";\nString QUARTER_4 = \"Q4\";\nString HALF_YEAR_FIRST = \"first\";\nString HALF_YEAR_LAST = \"last\";\ncom.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.loadExpression();\nfor (com.netsteadfast.greenstep.vo.DateRangeScoreVO dateScore : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getDateRangeScores()) {\n	float score = 0.0f;\n	int size = 0;\n	for (com.netsteadfast.greenstep.po.hbm.BbMeasureData measureData : ( (com.netsteadfast.greenstep.vo.KpiVO) kpi ).getMeasureDatas()) {\n		String date = dateScore.getDate().replaceAll(\"/\", \"\");\n		if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency)) { // Q1, Q2, Q3, Q4\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(QUARTER_1)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // Q1\n					continue;\n				}\n			}\n			if (date.endsWith(QUARTER_2)) {\n				if (!(yyyy+\"0401\").equals(measureData.getDate())) { // Q2\n					continue;\n				}						\n			}\n			if (date.endsWith(QUARTER_3)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // Q3\n					continue;\n				}							\n			}\n			if (date.endsWith(QUARTER_4)) {\n				if (!(yyyy+\"1001\").equals(measureData.getDate())) { // Q4\n					continue;\n				}						\n			}					\n		} else if (com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)) { // first, last\n			String yyyy = date.substring(0, 4);\n			if (date.endsWith(HALF_YEAR_FIRST)) {\n				if (!(yyyy+\"0101\").equals(measureData.getDate())) { // first-half\n					continue;\n				}						\n			}\n			if (date.endsWith(HALF_YEAR_LAST)) {\n				if (!(yyyy+\"0701\").equals(measureData.getDate())) { // last-half\n					continue;\n				}							\n			}										\n		} else { // DAY, WEEK, MONTH, YEAR\n			if (!measureData.getDate().startsWith(date)) {\n				continue;\n			}					\n		}				\n		size++;\n	}\n    score = java.lang.Float.valueOf(size);\n	dateScore.setScore(score);\n	dateScore.setFontColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getFontColor(score) );\n	dateScore.setBgColor( com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils.getBackgroundColor(score) );\n	dateScore.setImgIcon( com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils.getHtmlIcon(kpi, score) );\n}	\n','returns the count records of measure data. \nDoes not require the calculation formula.','admin','2015-04-22 13:59:41','admin','2015-04-22 14:06:16');
/*!40000 ALTER TABLE `bb_aggregation_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_employee`
--

DROP TABLE IF EXISTS `bb_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_employee` (
  `OID` char(36) NOT NULL,
  `ACCOUNT` varchar(24) NOT NULL,
  `EMP_ID` varchar(10) NOT NULL,
  `FULL_NAME` varchar(100) NOT NULL,
  `JOB_TITLE` varchar(100) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`EMP_ID`,`ACCOUNT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_employee`
--

LOCK TABLES `bb_employee` WRITE;
/*!40000 ALTER TABLE `bb_employee` DISABLE KEYS */;
INSERT INTO `bb_employee` VALUES ('08cc3dc8-9b81-4ebd-beb3-b4ae29157621','admin','0001','administrator','MIS','admin','2014-11-11 00:00:00','admin','2014-12-12 20:50:09'),('e620ab39-c7b3-471d-af7c-4f0db44de93d','tester','0002','Bill chen','HR','admin','2015-04-23 11:26:53',NULL,NULL);
/*!40000 ALTER TABLE `bb_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_employee_orga`
--

DROP TABLE IF EXISTS `bb_employee_orga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_employee_orga` (
  `OID` char(36) NOT NULL,
  `EMP_ID` varchar(10) NOT NULL,
  `ORG_ID` varchar(10) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`EMP_ID`,`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_employee_orga`
--

LOCK TABLES `bb_employee_orga` WRITE;
/*!40000 ALTER TABLE `bb_employee_orga` DISABLE KEYS */;
INSERT INTO `bb_employee_orga` VALUES ('342f4f63-a4c4-4704-b971-27a5b7f3b3a9','0001','0002','admin','2014-12-12 20:50:10',NULL,NULL),('8470275f-98bd-410e-b324-19770d348cb1','0001','0001','admin','2014-12-12 20:50:10',NULL,NULL);
/*!40000 ALTER TABLE `bb_employee_orga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_formula`
--

DROP TABLE IF EXISTS `bb_formula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_formula` (
  `OID` char(36) NOT NULL,
  `FOR_ID` varchar(14) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `RETURN_MODE` varchar(1) NOT NULL DEFAULT 'D',
  `RETURN_VAR` varchar(50) NOT NULL,
  `EXPRESSION` varchar(8000) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`FOR_ID`),
  KEY `IDX_1` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_formula`
--

LOCK TABLES `bb_formula` WRITE;
/*!40000 ALTER TABLE `bb_formula` DISABLE KEYS */;
INSERT INTO `bb_formula` VALUES ('bfc7c7df-0e7a-448b-b666-a9a2f7a7f950','F999','F999 Example for jython','PYTHON','C','ans','import math;\nans = math.sqrt( actual ÷ target ) × 100','is a sample!','admin','2014-11-20 10:03:12','admin','2014-11-20 15:49:10'),('d382b6ab-fe55-4c93-8c8e-79896ed09f7a','F001','F001 actual ÷ target','BSH','C','ans','if (actual == 0 ) {\n   ans = 0;\n   return;\n}\nif (target == 0) {\n   ans = actual;\n   return;\n}\nans = Math.max( actual ÷ target ,  0 ) × 100','for actual ÷ target !','admin','2014-11-19 11:39:03','admin','2014-11-20 11:46:17'),('e2d2dc04-ed37-471b-ac73-7de51dfa4721','F002','F002 for return actual','BSH','D','','actual','for return actual !','admin','2014-11-19 11:45:13',NULL,NULL);
/*!40000 ALTER TABLE `bb_formula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_kpi`
--

DROP TABLE IF EXISTS `bb_kpi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_kpi` (
  `OID` char(36) NOT NULL,
  `ID` varchar(14) NOT NULL,
  `OBJ_ID` varchar(14) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `WEIGHT` decimal(5,2) NOT NULL,
  `UNIT` varchar(20) NOT NULL,
  `FOR_ID` varchar(14) NOT NULL,
  `MAX` float NOT NULL,
  `TARGET` float NOT NULL,
  `MIN` float NOT NULL,
  `MANAGEMENT` varchar(1) NOT NULL DEFAULT '1',
  `COMPARE_TYPE` varchar(1) NOT NULL DEFAULT '1',
  `CAL` varchar(14) NOT NULL,
  `DATA_TYPE` varchar(1) NOT NULL DEFAULT '1',
  `ORGA_MEASURE_SEPARATE` varchar(1) NOT NULL DEFAULT 'N',
  `USER_MEASURE_SEPARATE` varchar(1) NOT NULL DEFAULT 'N',
  `QUASI_RANGE` int(3) NOT NULL DEFAULT '0',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`),
  KEY `IDX_1` (`OBJ_ID`,`NAME`,`FOR_ID`,`CAL`),
  KEY `IDX_2` (`FOR_ID`),
  KEY `IDX_3` (`CAL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_kpi`
--

LOCK TABLES `bb_kpi` WRITE;
/*!40000 ALTER TABLE `bb_kpi` DISABLE KEYS */;
INSERT INTO `bb_kpi` VALUES ('0775e107-756b-4934-848c-6e4b1235af3b','KPI0003','OBJ20141127001','Investment projects in developing countries','',50.00,'%','F001',150,100,50,'1','1','AVG_001','3','Y','Y',0,'admin','2014-11-27 09:12:15','admin','2015-03-12 13:38:05'),('71b4417c-6c8c-4ef6-b8d4-fbb4d56863bb','KPI0006','OBJ20141127004','Education and Training','',100.00,'number of times','F002',150,70,30,'3','1','MAX_001','2','Y','Y',5,'admin','2014-11-27 09:43:20','admin','2015-03-12 13:35:47'),('9aa1c537-402f-4ccc-8d77-f472f98a3192','KPI0001','OBJ20141117001','Sales','aa bb',100.00,'%','F001',200,50,10,'1','1','AVG_001','1','Y','Y',0,'admin','2014-11-23 17:14:39','admin','2015-08-06 10:06:28'),('b4ebcdd0-1944-432e-8bb2-85df54f854d8','KPI0002','OBJ20141127001','Stock market investment fund','',50.00,'%','F001',150,100,50,'1','1','AVG_001','1','Y','Y',0,'admin','2014-11-27 09:09:32','admin','2014-12-14 15:18:15'),('b888607c-6d52-4163-82ac-a1ae03c9cba6','KPI0004','OBJ20141127002','Expanding Asia-Pacific market','',100.00,'%','F001',150,100,60,'1','1','AVG_001','1','Y','Y',0,'admin','2014-11-27 09:21:36','admin','2015-03-12 13:39:02'),('c12b2c99-4b28-4ce9-94bc-314161b9b7e7','KPI0005','OBJ20141127003','Loss stronghold transformation','',100.00,'Point','F002',150,100,50,'2','1','MIN_001','1','Y','Y',0,'admin','2014-11-27 09:30:48','admin','2015-03-12 13:35:30');
/*!40000 ALTER TABLE `bb_kpi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_kpi_empl`
--

DROP TABLE IF EXISTS `bb_kpi_empl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_kpi_empl` (
  `OID` char(36) NOT NULL,
  `KPI_ID` varchar(14) NOT NULL,
  `EMP_ID` varchar(14) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`KPI_ID`,`EMP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_kpi_empl`
--

LOCK TABLES `bb_kpi_empl` WRITE;
/*!40000 ALTER TABLE `bb_kpi_empl` DISABLE KEYS */;
INSERT INTO `bb_kpi_empl` VALUES ('02de21a0-e4ca-4567-8a63-3577a385d176','KPI0001','0001','admin','2015-08-06 10:06:28',NULL,NULL),('27c64855-c5ba-4065-8b38-5ff44c6bf481','KPI0003','0002','admin','2015-03-12 13:38:05',NULL,NULL),('2c3aae7f-0db6-4059-a755-bffd4f8a7eb9','KPI0006','0001','admin','2015-03-12 13:35:47',NULL,NULL),('49840f0a-eab6-46c2-abe4-f2cd6c2ed5f3','KPI0003','0001','admin','2015-03-12 13:38:05',NULL,NULL),('744bf146-d596-4f32-8825-c3fc2e26fb82','KPI0002','0002','admin','2014-12-14 15:18:15',NULL,NULL),('ad72aa48-d458-43aa-bb51-70cd642d6f8a','KPI0002','0001','admin','2014-12-14 15:18:15',NULL,NULL),('cf99a8af-b2d4-4d7f-a12c-a281634a18fd','KPI0006','0002','admin','2015-03-12 13:35:47',NULL,NULL),('d122dfbe-a54e-491c-b7d6-10a2aeb7d297','KPI0001','0002','admin','2015-08-06 10:06:28',NULL,NULL);
/*!40000 ALTER TABLE `bb_kpi_empl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_kpi_orga`
--

DROP TABLE IF EXISTS `bb_kpi_orga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_kpi_orga` (
  `OID` char(36) NOT NULL,
  `KPI_ID` varchar(14) NOT NULL,
  `ORG_ID` varchar(14) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`KPI_ID`,`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_kpi_orga`
--

LOCK TABLES `bb_kpi_orga` WRITE;
/*!40000 ALTER TABLE `bb_kpi_orga` DISABLE KEYS */;
INSERT INTO `bb_kpi_orga` VALUES ('035f940b-2da9-4cac-bd90-88b96aaf7375','KPI0004','0002','admin','2015-03-12 13:39:02',NULL,NULL),('5bf8e230-ee06-43ab-a6d9-e42fb75ea814','KPI0005','0001','admin','2015-03-12 13:35:31',NULL,NULL),('9bfbe378-6cc4-46b4-bbc9-343dcff2453d','KPI0001','0001','admin','2015-08-06 10:06:28',NULL,NULL),('be02aadf-2fd7-4cfb-a2f6-2ac09d2c953a','KPI0003','0001','admin','2015-03-12 13:38:05',NULL,NULL),('cd706b32-9cca-40f7-97c3-2f6cc1d3a6d2','KPI0005','0002','admin','2015-03-12 13:35:30',NULL,NULL),('db5935d2-85cd-4518-8feb-d97b55fefae5','KPI0002','0001','admin','2014-12-14 15:18:15',NULL,NULL),('ef822c5e-22e4-4174-a9c0-4a94d00ebfee','KPI0001','0002','admin','2015-08-06 10:06:28',NULL,NULL);
/*!40000 ALTER TABLE `bb_kpi_orga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_measure_data`
--

DROP TABLE IF EXISTS `bb_measure_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_measure_data` (
  `OID` char(36) NOT NULL,
  `KPI_ID` varchar(14) NOT NULL,
  `DATE` varchar(8) NOT NULL,
  `TARGET` float NOT NULL DEFAULT '0',
  `ACTUAL` float NOT NULL DEFAULT '0',
  `FREQUENCY` varchar(1) NOT NULL,
  `ORG_ID` varchar(10) NOT NULL DEFAULT '*',
  `EMP_ID` varchar(10) NOT NULL DEFAULT '*',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`KPI_ID`,`DATE`,`FREQUENCY`,`ORG_ID`,`EMP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_measure_data`
--

LOCK TABLES `bb_measure_data` WRITE;
/*!40000 ALTER TABLE `bb_measure_data` DISABLE KEYS */;
INSERT INTO `bb_measure_data` VALUES ('06eb8c80-5f3b-4243-ae8c-4b6f01468430','KPI0003','20140101',100,71,'6','*','*','admin','2014-12-14 16:28:58',NULL,NULL),('0bc8d078-81cf-4a8b-a6ce-9e33e7c091b6','KPI0001','20150101',100,60,'3','*','*','admin','2015-04-14 12:22:54',NULL,NULL),('1165b729-81cc-4920-a3af-f5e61a503804','KPI0006','20150101',100,81,'6','*','*','admin','2015-01-05 19:06:20',NULL,NULL),('1414abd2-a05d-4dce-9405-1487e9b96025','KPI0001','20140701',100,65,'5','0001','*','admin','2014-12-14 15:19:51',NULL,NULL),('1614689d-52ea-4cca-9af2-2589518be90e','KPI0001','20140101',100,72,'6','0002','*','admin','2014-12-14 15:20:41',NULL,NULL),('17c150da-b202-4a77-b812-8cfc82f5d593','KPI0003','20140701',100,70,'5','*','0001','admin','2014-12-13 09:02:32',NULL,NULL),('1be25fdb-65d7-4059-9992-dafd6425bc30','KPI0001','20150101',100,56,'6','0001','*','tester','2015-01-22 19:10:15',NULL,NULL),('1fc614d5-8c2d-42aa-9893-293debdc18da','KPI0003','20150101',100,52,'3','*','*','admin','2015-01-05 19:04:38',NULL,NULL),('2147e78e-c38d-4f56-bb4e-d74fb2c705c3','KPI0005','20140101',100,90,'6','*','*','admin','2014-12-14 16:29:25',NULL,NULL),('23d724d1-97bc-4f35-860e-82ac37cbd447','KPI0004','20150101',100,65,'3','*','*','admin','2015-01-05 19:05:09',NULL,NULL),('2e373413-1db4-405e-be2b-8deeb1ac3ba4','KPI0006','20140101',100,97,'6','*','*','admin','2014-12-14 16:29:37',NULL,NULL),('31e45287-40a9-40af-ae92-1b3b9d665607','KPI0005','20150101',100,72,'3','*','*','admin','2015-01-05 19:06:10',NULL,NULL),('3f98c8ab-a15d-4051-85fe-f6c1c262e018','KPI0006','20140101',100,87,'6','*','0001','admin','2014-12-12 21:33:34',NULL,NULL),('43a07cf3-969e-49f8-a602-b335081871c4','KPI0002','20150101',100,65,'3','*','*','admin','2015-01-05 19:04:05',NULL,NULL),('5758fa25-fd80-4d21-ad17-3e017bf3f34b','KPI0003','20140101',100,67,'6','*','0001','admin','2014-12-13 09:02:21',NULL,NULL),('591a4395-8ed9-4c18-9e84-0b00d3f04e4d','KPI0005','20150101',100,75,'6','0001','*','tester','2015-01-22 19:11:09',NULL,NULL),('5a44df97-be72-44a0-979c-dd869b28be9e','KPI0006','20140101',100,82,'5','*','0001','admin','2014-12-12 21:33:53',NULL,NULL),('5f58aada-85cd-41e4-b53f-19f2efc090f4','KPI0001','20140101',100,56,'5','0001','*','admin','2014-12-14 15:19:51',NULL,NULL),('663b8ef4-be75-4ebd-abc2-0904cb56bb09','KPI0006','20140701',100,90,'5','*','0001','admin','2014-12-12 21:33:53',NULL,NULL),('889a7701-e2c1-467b-b849-7630ebbf2e6c','KPI0004','20150101',100,66,'6','*','*','admin','2015-01-05 19:04:52',NULL,NULL),('92d50c2f-a838-4bfd-aa19-85e1d684be7c','KPI0002','20140101',100,75,'6','*','*','admin','2014-12-14 16:28:47',NULL,NULL),('98e23dcc-3749-4a25-bc27-e6ed7c5c935a','KPI0002','20150101',100,61,'6','0001','*','tester','2015-01-22 19:10:27',NULL,NULL),('b8ac4db3-6e78-4521-b1e6-83c123a63644','KPI0003','20150101',100,53,'6','0001','*','tester','2015-01-22 19:10:42',NULL,NULL),('bc6d54fd-38ee-4818-8945-73947a4d0866','KPI0003','20150101',100,48,'6','*','*','admin','2015-01-05 19:04:23',NULL,NULL),('bdc67b1b-c313-46c1-a099-7623906f3640','KPI0001','20150101',100,55,'6','*','*','admin','2015-04-23 14:44:58',NULL,NULL),('bff452f4-90d6-46e6-9151-b99714c344e4','KPI0001','20140101',100,73,'5','0002','*','admin','2014-12-14 15:20:32',NULL,NULL),('c31e0f53-5a91-4d58-a5b8-7b57ea9dc877','KPI0004','20140101',100,81,'6','*','*','admin','2014-12-14 16:29:12',NULL,NULL),('c47f1f21-3a2b-43bb-adf5-a65c88346b8c','KPI0006','20150101',100,83,'3','*','*','admin','2015-01-05 19:06:27',NULL,NULL),('cc755370-d42e-4636-8997-a0dc39261bf2','KPI0001','20140101',100,61,'6','0001','*','admin','2014-12-14 15:19:35',NULL,NULL),('d4f6403a-0d67-4dc9-8b00-34ef1c3a9ba0','KPI0003','20140101',100,55,'5','*','0001','admin','2014-12-13 09:02:32',NULL,NULL),('de665c71-4f84-4ac2-9ed9-be04426f3002','KPI0001','20140101',100,64,'6','*','*','admin','2014-12-14 16:28:29',NULL,NULL),('e2517d83-6a87-4110-9cac-48cdad44e7ab','KPI0005','20150101',100,70,'6','*','*','admin','2015-01-05 19:06:02',NULL,NULL),('f1c276b4-2dfb-48b6-992f-8ef3ad860c20','KPI0002','20150101',100,63,'6','*','*','admin','2015-01-05 19:03:56',NULL,NULL),('fa308b4f-f915-4636-942b-e95a1c17a85d','KPI0001','20140701',100,70,'5','0002','*','admin','2014-12-14 15:20:32',NULL,NULL);
/*!40000 ALTER TABLE `bb_measure_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_objective`
--

DROP TABLE IF EXISTS `bb_objective`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_objective` (
  `OID` char(36) NOT NULL,
  `OBJ_ID` varchar(14) NOT NULL,
  `PER_ID` varchar(14) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `WEIGHT` decimal(5,2) NOT NULL,
  `TARGET` float NOT NULL,
  `MIN` float NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`OBJ_ID`),
  KEY `IDX_1` (`PER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_objective`
--

LOCK TABLES `bb_objective` WRITE;
/*!40000 ALTER TABLE `bb_objective` DISABLE KEYS */;
INSERT INTO `bb_objective` VALUES ('38e7c1cf-6526-4c0b-986c-387041b3ee99','OBJ20141127001','PER20141115001','Investment',50.00,100,60,'','admin','2014-11-27 09:07:43',NULL,NULL),('5b7d1217-6696-4341-9f46-3031ad214954','OBJ20141127003','PER20141115003','Upgrade organization',100.00,100,50,'','admin','2014-11-27 09:27:16',NULL,NULL),('9c0120f3-ad31-401e-a9f6-cc61be0ca2fc','OBJ20141127004','PER20141115004','Capacity building objectives',100.00,100,50,'','admin','2014-11-27 09:40:22',NULL,NULL),('a667cea5-8f2e-4909-aed0-f484a6428807','OBJ20141117001','PER20141115001','Enhance turnover',50.00,70,30,'','admin','2014-11-17 13:21:24',NULL,NULL),('e2bfdd9a-470b-46cb-8a09-00ed84dd2d10','OBJ20141127002','PER20141115002','Increase customer',100.00,100,50,'','admin','2014-11-27 09:18:16',NULL,NULL);
/*!40000 ALTER TABLE `bb_objective` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_organization`
--

DROP TABLE IF EXISTS `bb_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_organization` (
  `OID` char(36) NOT NULL,
  `ORG_ID` varchar(10) NOT NULL,
  `NAME` varchar(200) NOT NULL,
  `ADDRESS` varchar(500) NOT NULL,
  `LAT` varchar(20) NOT NULL,
  `LNG` varchar(20) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_organization`
--

LOCK TABLES `bb_organization` WRITE;
/*!40000 ALTER TABLE `bb_organization` DISABLE KEYS */;
INSERT INTO `bb_organization` VALUES ('1b2ac208-345c-4f93-92c5-4b26aead31d2','0001','USA office','101-199 North Post Street, Spokane, WA 99201, USA','47.65855726287328','-117.42373103376627','test data!\n~~','admin','2014-11-10 11:33:29','admin','2014-11-11 14:07:26'),('3ba52439-6756-45e8-8269-ae7b4fb6a3dc','0002','Taiwan office','Taipei main station, Zhongzheng District, Taipei City, Taiwan 100','25.046286835220773','121.5174406255494','','admin','2014-11-11 09:58:28','admin','2014-11-20 11:43:47'),('826d8ebd-4eb4-4e0d-a4c6-c025d265fc60','0003','Kaohsiung branch','No. 496, Zhongshan 2nd Road, Xinxing District, Kaohsiung City, Taiwan 800','22.62117648628209','120.30187976360321','','admin','2014-12-21 13:54:25',NULL,NULL);
/*!40000 ALTER TABLE `bb_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_organization_par`
--

DROP TABLE IF EXISTS `bb_organization_par`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_organization_par` (
  `OID` char(36) NOT NULL,
  `ORG_ID` varchar(10) NOT NULL,
  `PAR_ID` varchar(10) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ORG_ID`,`PAR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_organization_par`
--

LOCK TABLES `bb_organization_par` WRITE;
/*!40000 ALTER TABLE `bb_organization_par` DISABLE KEYS */;
INSERT INTO `bb_organization_par` VALUES ('112699ab-14f8-43d7-ab1c-43e3d58cc4f0','0002','0000000000','admin','2014-11-11 19:33:19',NULL,NULL),('1616dff6-2052-4f5d-a57a-821509cfcd75','0003','0002','admin','2014-12-21 13:54:31',NULL,NULL),('954780f4-0f77-4d6c-ad32-41d0ab9ab313','0001','0000000000','admin','2014-11-11 19:33:24',NULL,NULL);
/*!40000 ALTER TABLE `bb_organization_par` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_perspective`
--

DROP TABLE IF EXISTS `bb_perspective`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_perspective` (
  `OID` char(36) NOT NULL,
  `PER_ID` varchar(14) NOT NULL,
  `VIS_ID` varchar(14) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `WEIGHT` decimal(5,2) NOT NULL,
  `TARGET` float NOT NULL,
  `MIN` float NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`PER_ID`),
  KEY `IDX_1` (`VIS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_perspective`
--

LOCK TABLES `bb_perspective` WRITE;
/*!40000 ALTER TABLE `bb_perspective` DISABLE KEYS */;
INSERT INTO `bb_perspective` VALUES ('a7473cdc-e0eb-4b81-8f94-f3cac5aeccae','PER20141115002','VIS20141114001','Customer',25.00,100,70,'','admin','2014-11-15 12:23:23',NULL,NULL),('dd3f6f6b-a851-4a36-a647-e1473a85060c','PER20141115003','VIS20141114001','Internal business processes',25.00,90,40,'','admin','2014-11-15 12:23:45',NULL,NULL),('e6670f2f-0462-45c9-b0de-2682e7a5f08c','PER20141115001','VIS20141114001','Financial',25.00,100,50,'','admin','2014-11-15 12:21:09',NULL,NULL),('f1410495-88a9-4c6a-ab32-f896956f8827','PER20141115004','VIS20141114001','Learning and growth',25.00,100,55,'','admin','2014-11-15 12:24:01',NULL,NULL);
/*!40000 ALTER TABLE `bb_perspective` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_report_role_view`
--

DROP TABLE IF EXISTS `bb_report_role_view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_report_role_view` (
  `OID` char(36) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `ROLE` varchar(50) NOT NULL,
  `ID_NAME` varchar(24) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TYPE`,`ROLE`,`ID_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_report_role_view`
--

LOCK TABLES `bb_report_role_view` WRITE;
/*!40000 ALTER TABLE `bb_report_role_view` DISABLE KEYS */;
/*!40000 ALTER TABLE `bb_report_role_view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_score_color`
--

DROP TABLE IF EXISTS `bb_score_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_score_color` (
  `OID` char(36) NOT NULL,
  `SCORE_MIN` int(12) NOT NULL,
  `SCORE_MAX` int(12) NOT NULL,
  `FONT_COLOR` varchar(7) NOT NULL,
  `BG_COLOR` varchar(7) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`SCORE_MIN`,`SCORE_MAX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_score_color`
--

LOCK TABLES `bb_score_color` WRITE;
/*!40000 ALTER TABLE `bb_score_color` DISABLE KEYS */;
INSERT INTO `bb_score_color` VALUES ('3968f87f-b81f-4789-9d17-1286a31cd7fc',30,39,'#0077e7','#fde68a','admin','2014-11-30 10:51:40','admin','2014-12-03 22:16:48'),('53ce5dac-77ed-441c-97c0-fdd6581c6ed5',60,69,'#a0a0a0','#97e2ff','admin','2014-11-30 10:53:11','admin','2014-12-03 22:14:29'),('64e48f59-39ff-49f5-b573-f2853e0f3c6b',80,89,'#7e945d','#e8fde2','admin','2014-11-30 10:53:40','admin','2014-12-01 13:27:21'),('65bdfea6-96f0-4b2d-96da-c30fe519ca6d',90,100,'#526d29','#e8fde2','admin','2014-11-30 10:54:38','admin','2014-12-01 13:27:05'),('67b91f49-9dcf-4e57-a21c-7a216396812d',70,79,'#9e6816','#97e2ff','admin','2014-11-30 10:53:22','admin','2014-12-03 22:14:45'),('6efed02a-a4ef-4107-be8e-cd408ad5e767',10,19,'#c40000','#ffdddd','admin','2014-11-30 10:51:14','admin','2014-12-01 13:31:57'),('97c107a5-92d0-40f2-a274-5990590917b4',-999999998,-1,'#ffffff','#f80c0c','admin','2014-11-30 10:48:44',NULL,NULL),('9b7ee58f-85af-4466-b3e9-f4d31bb133d5',201,999999999,'#1b2c04','#cbdfac','admin','2014-12-01 13:18:51','admin','2014-12-01 13:34:20'),('9f12a751-7f0a-42f0-b01d-5c7f0a29407a',101,200,'#1e2e04','#e8fde2','admin','2014-11-30 10:55:39','admin','2014-12-01 13:26:28'),('a856983d-70d4-45d2-9b1b-9a5bed4f89af',20,29,'#800000','#ffdddd','admin','2014-11-30 10:51:25','admin','2014-12-01 13:32:20'),('c2c4a4e9-9a84-433d-98ef-efbe9bac0195',40,49,'#0043a7','#fde68a','admin','2014-11-30 10:51:54','admin','2014-12-03 22:16:56'),('d2213680-cad3-4f6a-ac41-777341c43070',0,9,'#ff0000','#ffdddd','admin','2014-11-30 10:49:37','admin','2014-12-01 13:32:14'),('e7d4d8ba-950f-4d31-9f04-eb32b798ad42',50,59,'#00224e','#fde68a','admin','2014-11-30 10:52:15','admin','2014-12-03 22:17:03');
/*!40000 ALTER TABLE `bb_score_color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_strategy_map`
--

DROP TABLE IF EXISTS `bb_strategy_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_strategy_map` (
  `OID` char(36) NOT NULL,
  `VIS_ID` varchar(14) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`VIS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_strategy_map`
--

LOCK TABLES `bb_strategy_map` WRITE;
/*!40000 ALTER TABLE `bb_strategy_map` DISABLE KEYS */;
INSERT INTO `bb_strategy_map` VALUES ('9d8ba8da-84ea-4605-90a9-63daaa24ed7d','VIS20141114001','tester','2014-12-22 19:40:10',NULL,NULL);
/*!40000 ALTER TABLE `bb_strategy_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_strategy_map_conns`
--

DROP TABLE IF EXISTS `bb_strategy_map_conns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_strategy_map_conns` (
  `OID` char(36) NOT NULL,
  `MASTER_OID` char(36) NOT NULL,
  `CONNECTION_ID` varchar(20) NOT NULL,
  `SOURCE_ID` varchar(14) NOT NULL,
  `TARGET_ID` varchar(14) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`MASTER_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_strategy_map_conns`
--

LOCK TABLES `bb_strategy_map_conns` WRITE;
/*!40000 ALTER TABLE `bb_strategy_map_conns` DISABLE KEYS */;
INSERT INTO `bb_strategy_map_conns` VALUES ('2362ed05-2599-4af7-9d5b-e6a2dbf81c18','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','con_23','OBJ20141117001','OBJ20141127001','tester','2014-12-22 19:40:10',NULL,NULL),('29c149af-9320-4da3-90e4-7696d67567a2','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','con_11','OBJ20141127002','OBJ20141117001','tester','2014-12-22 19:40:10',NULL,NULL),('322fbf80-c41f-41a7-87e9-0589ad51d88c','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','con_29','OBJ20141127004','OBJ20141127003','tester','2014-12-22 19:40:11',NULL,NULL),('407aabd4-db35-4ab2-9eb4-2e3c983070c2','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','con_5','OBJ20141127002','OBJ20141127001','tester','2014-12-22 19:40:10',NULL,NULL),('c2be3d62-ed6c-416f-b71b-664298f3e73a','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','con_17','OBJ20141127003','OBJ20141127002','tester','2014-12-22 19:40:10',NULL,NULL),('ed659e3a-f1a5-4c40-a577-0cd35c60ac5b','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','con_35','OBJ20141127001','OBJ20141117001','tester','2014-12-22 19:40:11',NULL,NULL);
/*!40000 ALTER TABLE `bb_strategy_map_conns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_strategy_map_nodes`
--

DROP TABLE IF EXISTS `bb_strategy_map_nodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_strategy_map_nodes` (
  `OID` char(36) NOT NULL,
  `MASTER_OID` char(36) NOT NULL,
  `ID` varchar(14) NOT NULL,
  `TEXT` varchar(100) NOT NULL,
  `POSITION_X` int(5) NOT NULL,
  `POSITION_Y` int(5) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`MASTER_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_strategy_map_nodes`
--

LOCK TABLES `bb_strategy_map_nodes` WRITE;
/*!40000 ALTER TABLE `bb_strategy_map_nodes` DISABLE KEYS */;
INSERT INTO `bb_strategy_map_nodes` VALUES ('0d2c2df7-bf62-498c-9a64-eb38050d2437','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','OBJ20141117001','Enhance turnover',13,10,'tester','2014-12-22 19:40:11',NULL,NULL),('2ccaac06-09db-4ed1-b43d-a8dd37c47ea9','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','OBJ20141127004','Capacity building objectives',146,318,'tester','2014-12-22 19:40:11',NULL,NULL),('94134af5-1f93-41dc-a45b-549f7542d5a1','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','OBJ20141127002','Increase customer',146,128,'tester','2014-12-22 19:40:11',NULL,NULL),('b33f7983-ac24-46fa-842d-b87ac872b33e','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','OBJ20141127003','Upgrade organization',146,221,'tester','2014-12-22 19:40:11',NULL,NULL),('f5de2d18-e7e1-4ba4-b63b-2c5dea3345b6','9d8ba8da-84ea-4605-90a9-63daaa24ed7d','OBJ20141127001','Investment',311,13,'tester','2014-12-22 19:40:11',NULL,NULL);
/*!40000 ALTER TABLE `bb_strategy_map_nodes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_swot`
--

DROP TABLE IF EXISTS `bb_swot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_swot` (
  `OID` char(36) NOT NULL,
  `VIS_ID` varchar(14) NOT NULL,
  `PER_ID` varchar(14) NOT NULL,
  `ORG_ID` varchar(10) NOT NULL,
  `TYPE` varchar(1) NOT NULL,
  `ISSUES` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`VIS_ID`,`PER_ID`,`ORG_ID`,`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_swot`
--

LOCK TABLES `bb_swot` WRITE;
/*!40000 ALTER TABLE `bb_swot` DISABLE KEYS */;
INSERT INTO `bb_swot` VALUES ('16ed281c-d83c-4bc6-a4a5-8a58eddbbb28','VIS20141114001','PER20141115001','0002','W','standard language!','admin','2014-12-20 10:28:38','admin','2014-12-21 13:50:43'),('364b6f58-6cbe-455c-bb9c-c9dcef20c4bd','VIS20141114001','PER20141115002','0002','W','発達中の低気圧の影響で北日本を中心に風や雪が強まっています。','admin','2014-12-20 10:46:14','admin','2014-12-21 13:50:43'),('3cbf978c-bb93-4291-8132-ad7b51d07639','VIS20141114001','PER20141115003','0002','O','Español','admin','2014-12-21 13:45:36','admin','2014-12-21 13:50:43'),('4daf02b1-5ef4-4b5b-ae98-6af32ff5c5fa','VIS20141114001','PER20141115002','0002','O','QQQ','admin','2014-12-20 10:46:14',NULL,NULL),('53563f0b-43a1-4dd2-adf6-2647a4f96148','VIS20141114001','PER20141115001','0002','O','TEST','admin','2014-12-20 10:46:14','tester','2014-12-22 19:39:50'),('bf24c13f-62e9-4309-8c48-1d00466467f3','VIS20141114001','PER20141115003','0002','S','العربية‎ al-ʻarabīyah','admin','2014-12-21 13:46:53','admin','2014-12-23 13:58:56'),('e8b510fd-1530-450d-ac5d-d30da1e177c8','VIS20141114001','PER20141115002','0002','S','der Großen Koalition','admin','2014-12-20 10:46:14','admin','2014-12-23 13:58:56'),('f9a4a565-1f9b-43a1-9610-6d138aef78f5','VIS20141114001','PER20141115001','0002','S','新屋是一個靠海的鄉鎮，位處於桃園縣的西南邊。沿著縣道，走在新屋的路上，映入眼簾的盡是綠波蕩漾的稻田，辛勤的農家們，正在勤勞的耕種著，在現代的都會生活中，還保留著珍貴的農村風貌。而事實上，新屋鄉是全台最大的農業鄉鎮，所產的稻米，更是全國重要的供應來源。','admin','2014-12-20 10:46:14','admin','2014-12-21 13:50:43');
/*!40000 ALTER TABLE `bb_swot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_swot_report_dtl`
--

DROP TABLE IF EXISTS `bb_swot_report_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_swot_report_dtl` (
  `OID` char(36) NOT NULL,
  `REPORT_ID` char(36) NOT NULL,
  `SEQ` int(5) NOT NULL,
  `LABEL` varchar(100) NOT NULL,
  `ISSUES1` varchar(500) NOT NULL,
  `ISSUES2` varchar(500) NOT NULL,
  `ISSUES3` varchar(500) NOT NULL,
  `ISSUES4` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`REPORT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_swot_report_dtl`
--

LOCK TABLES `bb_swot_report_dtl` WRITE;
/*!40000 ALTER TABLE `bb_swot_report_dtl` DISABLE KEYS */;
/*!40000 ALTER TABLE `bb_swot_report_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_swot_report_mst`
--

DROP TABLE IF EXISTS `bb_swot_report_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_swot_report_mst` (
  `OID` char(36) NOT NULL,
  `REPORT_ID` char(36) NOT NULL,
  `VISION_TITLE` varchar(100) NOT NULL,
  `ORG_NAME` varchar(200) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`REPORT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_swot_report_mst`
--

LOCK TABLES `bb_swot_report_mst` WRITE;
/*!40000 ALTER TABLE `bb_swot_report_mst` DISABLE KEYS */;
/*!40000 ALTER TABLE `bb_swot_report_mst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_vision`
--

DROP TABLE IF EXISTS `bb_vision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_vision` (
  `OID` char(50) NOT NULL,
  `VIS_ID` varchar(14) NOT NULL,
  `TITLE` varchar(100) NOT NULL,
  `CONTENT` blob,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`VIS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_vision`
--

LOCK TABLES `bb_vision` WRITE;
/*!40000 ALTER TABLE `bb_vision` DISABLE KEYS */;
INSERT INTO `bb_vision` VALUES ('1089abb5-3faf-445d-88ff-cd7690ac6743','VIS20141114001','Example vision',0x3C683120636C6173733D2268656164696E672D786C61726765207468656D652D6D61696E207468656D652D666F6E742D6D61696E206B6E6F636B6F75742D6267636F6C6F72223E3C7370616E20636C6173733D222220646174612D6F766572726964652D73697A653D222220646174612D7469746C653D227B2671756F743B6465736B746F702671756F743B3A2671756F743B266C743B7374726F6E6720636C6173733D5C2671756F743B7468656D652D7365636F6E64617279206B6E6F636B6F75742D6267636F6C6F725C2671756F743B2667743B4D697373696F6E2C266C743B2F7374726F6E672667743B20566973696F6E2026616D703B2056616C7565732671756F743B2C2671756F743B7461626C65742671756F743B3A2671756F743B266C743B7374726F6E6720636C6173733D5C2671756F743B7468656D652D7365636F6E64617279206B6E6F636B6F75742D6267636F6C6F725C2671756F743B2667743B4D697373696F6E2C266C743B2F7374726F6E672667743B20566973696F6E2026616D703B2056616C7565732671756F743B2C2671756F743B6D6F62696C652671756F743B3A2671756F743B266C743B7374726F6E6720636C6173733D5C2671756F743B7468656D652D7365636F6E64617279206B6E6F636B6F75742D6267636F6C6F725C2671756F743B2667743B4D697373696F6E2C266C743B2F7374726F6E672667743B20566973696F6E2026616D703B2056616C7565732671756F743B7D22206974656D70726F703D226E616D65223E3C6220636C6173733D227468656D652D7365636F6E64617279206B6E6F636B6F75742D6267636F6C6F72223E4D697373696F6E2C3C2F623E20566973696F6E2026616D703B2056616C7565733C2F7370616E3E2028206E6F74206F7665722041342070617065722077696474682120293C2F68313E3C703E3C6272202F3E3C2F703E3C64697620636C6173733D226C6561642D6D656469612D636F6E7461696E6572223E0A202020202020202020202020202020202020202020202020202020203C64697620636C6173733D2276696577706F727420696D616765223E0A20202020202020202020202020202020202020202020202020202020202020203C6120636C6173733D22696D6167652220687265663D2268747470733A2F2F656E2E77696B6970656469612E6F72672F77696B692F46696C653A42616D626F6F5F666F726573745F30312E6A7067223E3C696D6720616C743D222220636C6173733D227468756D62696D6167652220646174612D66696C652D6865696768743D22333236342220646174612D66696C652D77696474683D223439323822206865696768743D2231343622207372633D2268747470733A2F2F75706C6F61642E77696B696D656469612E6F72672F77696B6970656469612F636F6D6D6F6E732F7468756D622F382F38652F42616D626F6F5F666F726573745F30312E6A70672F32323070782D42616D626F6F5F666F726573745F30312E6A7067222077696474683D2232323022202F3E3C2F613EC2A0C2A00A202020202020202020202020202020202020202020202020202020203C2F6469763E0A2020202020202020202020202020202020202020202020203C2F6469763E3C68333E0A094F7572204D697373696F6E3C2F68333E0A3C703E0A094F757220526F61646D6170207374617274732077697468206F7572206D697373696F6E2C20776869636820697320656E647572696E672E204974206465636C61726573206F75720A20707572706F7365206173206120636F6D70616E7920616E642073657276657320617320746865207374616E64617264203C6272202F3E616761696E7374207768696368207765207765696768200A6F757220616374696F6E7320616E64206465636973696F6E732E3C2F703E0A3C756C3E3C6C693E0A0909546F20726566726573682074686520776F726C642E2E2E3C2F6C693E3C6C693E0A0909546F20696E7370697265206D6F6D656E7473206F66206F7074696D69736D20616E642068617070696E6573732E2E2E3C2F6C693E3C6C693E0A0909546F206372656174652076616C756520616E64206D616B65206120646966666572656E63652E3C2F6C693E3C2F756C3E,'admin','2014-11-14 20:28:14','admin','2015-06-27 15:54:15');
/*!40000 ALTER TABLE `bb_vision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_workspace`
--

DROP TABLE IF EXISTS `bb_workspace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_workspace` (
  `OID` char(36) NOT NULL,
  `SPACE_ID` varchar(20) NOT NULL,
  `TEMPLATE_ID` varchar(20) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`SPACE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_workspace`
--

LOCK TABLES `bb_workspace` WRITE;
/*!40000 ALTER TABLE `bb_workspace` DISABLE KEYS */;
INSERT INTO `bb_workspace` VALUES ('b27c6700-a4b3-4087-a3fb-21e497496bba','WK001','TEMPLATE0003','Performance view','for test!','admin','2015-01-05 19:02:56',NULL,NULL);
/*!40000 ALTER TABLE `bb_workspace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_workspace_compoment`
--

DROP TABLE IF EXISTS `bb_workspace_compoment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_workspace_compoment` (
  `OID` char(36) NOT NULL,
  `COMP_ID` varchar(20) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `IMAGE` varchar(50) NOT NULL,
  `CLASS_NAME` varchar(255) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`COMP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_workspace_compoment`
--

LOCK TABLES `bb_workspace_compoment` WRITE;
/*!40000 ALTER TABLE `bb_workspace_compoment` DISABLE KEYS */;
INSERT INTO `bb_workspace_compoment` VALUES ('7f20fb14-a8d5-4612-9dcf-aa8ecaa2e033','COMP_GRID001','Grid report','1419765647_ic_grid_on_48px-128.png','com.netsteadfast.greenstep.bsc.compoments.impl.GridReportCompoment','admin','2014-12-29 10:47:00',NULL,NULL),('9e755c72-6897-438a-8a60-f819370aa522','COMP_PIE001','Pie chart','1419765635_pie_chart.png','com.netsteadfast.greenstep.bsc.compoments.impl.PieChartCompoment','admin','2014-12-28 13:29:13',NULL,NULL),('bd46d3d3-beff-44dd-9a78-0cd61018e782','COMP_BAR001','Bar chart','1419765641_Stats.png','com.netsteadfast.greenstep.bsc.compoments.impl.BarChartCompoment','admin','2014-12-28 13:28:24',NULL,NULL),('f9be02c8-9248-4f99-a874-c078dce74752','COMP_LINE001','Line chart','1419765629_line_chart.png','com.netsteadfast.greenstep.bsc.compoments.impl.LineChartCompoment','admin','2014-12-28 13:30:03',NULL,NULL);
/*!40000 ALTER TABLE `bb_workspace_compoment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_workspace_config`
--

DROP TABLE IF EXISTS `bb_workspace_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_workspace_config` (
  `OID` char(36) NOT NULL,
  `SPACE_ID` varchar(20) NOT NULL,
  `COMP_ID` varchar(20) NOT NULL,
  `POSITION` int(3) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`SPACE_ID`,`COMP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_workspace_config`
--

LOCK TABLES `bb_workspace_config` WRITE;
/*!40000 ALTER TABLE `bb_workspace_config` DISABLE KEYS */;
INSERT INTO `bb_workspace_config` VALUES ('7c1030b4-ae08-4eaa-a55a-83532dc3f01b','WK001','COMP_PIE001 ',0,'admin','2015-01-05 19:02:56',NULL,NULL),('c3c58f56-e94e-4a4a-a848-82628f2a2c63','WK001','COMP_BAR001 ',1,'admin','2015-01-05 19:02:56',NULL,NULL),('eb6396fc-8a3b-4326-878d-5de17f0649c2','WK001','COMP_LINE001 ',2,'admin','2015-01-05 19:02:56',NULL,NULL);
/*!40000 ALTER TABLE `bb_workspace_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_workspace_label`
--

DROP TABLE IF EXISTS `bb_workspace_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_workspace_label` (
  `OID` char(36) NOT NULL,
  `SPACE_ID` varchar(20) NOT NULL,
  `LABEL` varchar(100) NOT NULL,
  `POSITION` int(3) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`SPACE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_workspace_label`
--

LOCK TABLES `bb_workspace_label` WRITE;
/*!40000 ALTER TABLE `bb_workspace_label` DISABLE KEYS */;
INSERT INTO `bb_workspace_label` VALUES ('3b9bc2d6-11fd-4395-a118-885979e2601f','WK001','   ',1,'admin','2015-01-05 19:02:57',NULL,NULL),('48d1bc48-f191-466d-b6b1-c921c499cba8','WK001','   ',0,'admin','2015-01-05 19:02:57',NULL,NULL),('d24462fd-a1c7-44f5-8c83-7161a5ceed60','WK001','Date score',2,'admin','2015-01-05 19:02:57',NULL,NULL);
/*!40000 ALTER TABLE `bb_workspace_label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bb_workspace_template`
--

DROP TABLE IF EXISTS `bb_workspace_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bb_workspace_template` (
  `OID` char(36) NOT NULL,
  `TEMPLATE_ID` varchar(20) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `RESOURCE` varchar(50) NOT NULL,
  `RESOURCE_CONF` varchar(50) NOT NULL,
  `POSITION_SIZE` int(3) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TEMPLATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bb_workspace_template`
--

LOCK TABLES `bb_workspace_template` WRITE;
/*!40000 ALTER TABLE `bb_workspace_template` DISABLE KEYS */;
INSERT INTO `bb_workspace_template` VALUES ('524dcf20-e191-4182-9d71-f399581d9a5d','TEMPLATE0002','Two Rows','workspace-template-0002-body.ftl','workspace-template-0002-conf.ftl',2,'admin','2014-12-29 10:37:55',NULL,NULL),('5d92a0e1-dfb5-40eb-a3d1-f206da7bdafc','TEMPLATE0001','Two Columns','workspace-template-0001-body.ftl','workspace-template-0001-conf.ftl',2,'admin','2014-12-29 10:37:24',NULL,NULL),('96685fd5-6777-4d79-972c-e9702c1c9b9a','TEMPLATE0003','Three Squares Bottom','workspace-template-0003-body.ftl','workspace-template-0003-conf.ftl',3,'admin','2014-12-29 10:39:04',NULL,NULL),('ab0e730d-6ea3-4935-85b7-4695f62a4f42','TEMPLATE0005','Four Squares','workspace-template-0005-body.ftl','workspace-template-0005-conf.ftl',4,'admin','2014-12-29 10:39:51',NULL,NULL),('d3841e49-e526-47ef-b484-e71c33443312','TEMPLATE0004','Three Squares Top','workspace-template-0004-body.ftl','workspace-template-0004-conf.ftl',3,'admin','2014-12-29 10:42:52',NULL,NULL);
/*!40000 ALTER TABLE `bb_workspace_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qc_data_query`
--

DROP TABLE IF EXISTS `qc_data_query`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qc_data_query` (
  `OID` char(36) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `CONF` varchar(20) NOT NULL,
  `QUERY_EXPRESSION` blob NOT NULL,
  `MAPPER_OID` char(36) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`NAME`),
  KEY `IDX_1` (`CONF`),
  KEY `IDX_2` (`MAPPER_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qc_data_query`
--

LOCK TABLES `qc_data_query` WRITE;
/*!40000 ALTER TABLE `qc_data_query` DISABLE KEYS */;
INSERT INTO `qc_data_query` VALUES ('086b547f-4dd3-4876-9dcf-6b94ea00f297','KPIs 2015 year Actual value.','BBCORE01',0x73656C656374204B2E4E414D452C204D442E41435455414C2C204D442E5441524745542C204D442E444154452066726F6D2062625F6D6561737572655F64617461204D442C2062625F4B5049204B0A7768657265204B2E49443D4D442E4B50495F494420616E64204D442E4652455155454E43593D27362720616E64204D442E4F52475F49443D272A2720616E64204D442E454D505F49443D272A270A616E64204D442E44415445204C494B452027323031352527,'6055d048-ef73-4b7f-a227-f54d933f3adc','admin','2015-01-16 21:58:31','admin','2015-01-17 10:12:27'),('374e197e-e9fc-49aa-a742-7b19488abd47','KPIs 2014 year Actual value.','BBCORE01',0x73656C656374204B2E4E414D452C204D442E41435455414C2C204D442E5441524745542C204D442E444154452066726F6D2062625F6D6561737572655F64617461204D442C2062625F4B5049204B0A7768657265204B2E49443D4D442E4B50495F494420616E64204D442E4652455155454E43593D27362720616E64204D442E4F52475F49443D272A2720616E64204D442E454D505F49443D272A270A616E64204D442E44415445204C494B452027323031342527,'6055d048-ef73-4b7f-a227-f54d933f3adc','admin','2015-01-17 10:24:36','tester','2015-01-17 10:41:05');
/*!40000 ALTER TABLE `qc_data_query` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qc_data_query_mapper`
--

DROP TABLE IF EXISTS `qc_data_query_mapper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qc_data_query_mapper` (
  `OID` char(36) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qc_data_query_mapper`
--

LOCK TABLES `qc_data_query_mapper` WRITE;
/*!40000 ALTER TABLE `qc_data_query_mapper` DISABLE KEYS */;
INSERT INTO `qc_data_query_mapper` VALUES ('6055d048-ef73-4b7f-a227-f54d933f3adc','KPIs actual mapper - 01','for example!','admin','2015-01-14 22:47:01','admin','2015-01-16 13:33:46');
/*!40000 ALTER TABLE `qc_data_query_mapper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qc_data_query_mapper_set`
--

DROP TABLE IF EXISTS `qc_data_query_mapper_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qc_data_query_mapper_set` (
  `OID` char(36) NOT NULL,
  `MAPPER_OID` char(36) NOT NULL,
  `LABEL_FIELD` varchar(50) NOT NULL,
  `VALUE_FIELD` varchar(50) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`MAPPER_OID`,`LABEL_FIELD`,`VALUE_FIELD`),
  KEY `IDX_1` (`MAPPER_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qc_data_query_mapper_set`
--

LOCK TABLES `qc_data_query_mapper_set` WRITE;
/*!40000 ALTER TABLE `qc_data_query_mapper_set` DISABLE KEYS */;
INSERT INTO `qc_data_query_mapper_set` VALUES ('3f7512dc-87da-451c-bddd-120c72067879','6055d048-ef73-4b7f-a227-f54d933f3adc','NAME','TARGET','admin','2015-01-16 13:33:46',NULL,NULL),('7469a688-18db-4c74-b203-04f23b71ca6a','6055d048-ef73-4b7f-a227-f54d933f3adc','NAME','ACTUAL','admin','2015-01-16 13:33:46',NULL,NULL);
/*!40000 ALTER TABLE `qc_data_query_mapper_set` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qc_data_source_conf`
--

DROP TABLE IF EXISTS `qc_data_source_conf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qc_data_source_conf` (
  `OID` char(36) NOT NULL,
  `ID` varchar(20) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `DRIVER_ID` varchar(20) NOT NULL,
  `JDBC_URL` varchar(500) NOT NULL,
  `DB_ACCOUNT` varchar(50) NOT NULL,
  `DB_PASSWORD` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`),
  KEY `IDX_1` (`DRIVER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qc_data_source_conf`
--

LOCK TABLES `qc_data_source_conf` WRITE;
/*!40000 ALTER TABLE `qc_data_source_conf` DISABLE KEYS */;
INSERT INTO `qc_data_source_conf` VALUES ('a80f84b5-b716-4ad0-9c7b-f9f5416522a2','BBCORE01','datasource-01','MYSQL','jdbc:mysql://localhost/bbcore?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull','root','password','test','admin','2015-01-12 13:12:50','admin','2015-01-13 11:33:54');
/*!40000 ALTER TABLE `qc_data_source_conf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qc_data_source_driver`
--

DROP TABLE IF EXISTS `qc_data_source_driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qc_data_source_driver` (
  `OID` char(36) NOT NULL,
  `ID` varchar(20) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `CLASS_NAME` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qc_data_source_driver`
--

LOCK TABLES `qc_data_source_driver` WRITE;
/*!40000 ALTER TABLE `qc_data_source_driver` DISABLE KEYS */;
INSERT INTO `qc_data_source_driver` VALUES ('c9028cdf-fd91-457a-8bde-172d586ec058','MYSQL','MySQL DataSource Driver','com.mysql.jdbc.jdbc2.optional.MysqlDataSource','MySQL JDBC DataSource Driver','admin','2015-01-11 12:22:44',NULL,NULL);
/*!40000 ALTER TABLE `qc_data_source_driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qc_olap_catalog`
--

DROP TABLE IF EXISTS `qc_olap_catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qc_olap_catalog` (
  `OID` char(36) NOT NULL,
  `ID` varchar(20) NOT NULL,
  `NAME` varchar(150) NOT NULL,
  `CONTENT` blob,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qc_olap_catalog`
--

LOCK TABLES `qc_olap_catalog` WRITE;
/*!40000 ALTER TABLE `qc_olap_catalog` DISABLE KEYS */;
INSERT INTO `qc_olap_catalog` VALUES ('6823a09e-9bc7-4680-9af3-02e7db429b3d','C01','Example-Catalog!',0x3C536368656D61206E616D653D224D656173757265536368656D61223E0D0A20203C43756265206E616D653D224D65617375726543756265222076697369626C653D2274727565222063616368653D22747275652220656E61626C65643D2274727565223E0D0A202020203C5461626C65206E616D653D2262625F6D6561737572655F646174612220616C6961733D22223E0D0A202020203C2F5461626C653E0D0A202020203C44696D656E73696F6E20747970653D225374616E6461726444696D656E73696F6E222076697369626C653D227472756522206E616D653D2241637475616C44696D223E0D0A2020202020203C486965726172636879206E616D653D2241637475616C222076697369626C653D22747275652220686173416C6C3D2274727565223E0D0A20202020202020203C4C6576656C206E616D653D2241637475616C4C6576656C222076697369626C653D22747275652220636F6C756D6E3D2241435455414C22206E616D65436F6C756D6E3D2241435455414C2220747970653D224E756D657269632220756E697175654D656D626572733D2266616C736522206C6576656C547970653D22526567756C6172223E0D0A20202020202020203C2F4C6576656C3E0D0A2020202020203C2F4869657261726368793E0D0A202020203C2F44696D656E73696F6E3E0D0A202020203C44696D656E73696F6E20747970653D225374616E6461726444696D656E73696F6E222076697369626C653D227472756522206E616D653D2254617267657444696D223E0D0A2020202020203C486965726172636879206E616D653D22546172676574222076697369626C653D22747275652220686173416C6C3D2274727565223E0D0A20202020202020203C4C6576656C206E616D653D225461726765744C6576656C222076697369626C653D22747275652220636F6C756D6E3D2254415247455422206E616D65436F6C756D6E3D225441524745542220756E697175654D656D626572733D2266616C7365223E0D0A20202020202020203C2F4C6576656C3E0D0A2020202020203C2F4869657261726368793E0D0A202020203C2F44696D656E73696F6E3E0D0A202020203C44696D656E73696F6E20747970653D225374616E6461726444696D656E73696F6E222076697369626C653D227472756522206E616D653D224461746544696D223E0D0A2020202020203C486965726172636879206E616D653D2244617465222076697369626C653D22747275652220686173416C6C3D2274727565223E0D0A20202020202020203C4C6576656C206E616D653D22446174654C6576656C222076697369626C653D22747275652220636F6C756D6E3D224441544522206E616D65436F6C756D6E3D22444154452220756E697175654D656D626572733D2266616C7365223E0D0A20202020202020203C2F4C6576656C3E0D0A2020202020203C2F4869657261726368793E0D0A202020203C2F44696D656E73696F6E3E0D0A202020203C44696D656E73696F6E20747970653D225374616E6461726444696D656E73696F6E222076697369626C653D227472756522206E616D653D224B706944696D223E0D0A2020202020203C486965726172636879206E616D653D224B5049222076697369626C653D22747275652220686173416C6C3D2274727565223E0D0A20202020202020203C4C6576656C206E616D653D224B70694C6576656C222076697369626C653D22747275652220636F6C756D6E3D224B50495F494422206E616D65436F6C756D6E3D224B50495F49442220756E697175654D656D626572733D2266616C7365223E0D0A20202020202020203C2F4C6576656C3E0D0A2020202020203C2F4869657261726368793E0D0A202020203C2F44696D656E73696F6E3E0D0A20203C2F437562653E0D0A3C2F536368656D613E0D0A,'test!','admin','2015-03-04 21:16:16','admin','2015-03-04 22:09:28');
/*!40000 ALTER TABLE `qc_olap_catalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qc_olap_conf`
--

DROP TABLE IF EXISTS `qc_olap_conf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qc_olap_conf` (
  `OID` char(36) NOT NULL,
  `ID` varchar(20) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `JDBC_DRIVERS` varchar(50) NOT NULL,
  `JDBC_URL` varchar(500) NOT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qc_olap_conf`
--

LOCK TABLES `qc_olap_conf` WRITE;
/*!40000 ALTER TABLE `qc_olap_conf` DISABLE KEYS */;
INSERT INTO `qc_olap_conf` VALUES ('ed303713-e520-4de6-bf20-7cd572637111','SOURCE01','Config-01','com.mysql.jdbc.Driver','jdbc:mysql://localhost:3306/bbcore?user=root&password=password&useUnicode=true&characterEncoding=utf8','for test !','admin','2015-03-03 14:02:59','admin','2015-03-03 20:04:04');
/*!40000 ALTER TABLE `qc_olap_conf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qc_olap_mdx`
--

DROP TABLE IF EXISTS `qc_olap_mdx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qc_olap_mdx` (
  `OID` char(36) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `EXPRESSION` blob NOT NULL,
  `CONF_OID` char(36) NOT NULL,
  `CATALOG_OID` char(36) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`NAME`),
  KEY `IDX_1` (`CONF_OID`),
  KEY `IDX_2` (`CATALOG_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qc_olap_mdx`
--

LOCK TABLES `qc_olap_mdx` WRITE;
/*!40000 ALTER TABLE `qc_olap_mdx` DISABLE KEYS */;
INSERT INTO `qc_olap_mdx` VALUES ('959f0f2c-beee-46b5-9c29-be69aefed622','Sample - 01',0x2053454C4543542041637475616C44696D2E4368696C6472656E204F4E20524F57532C20204B706944696D2E4368696C6472656E204F4E20434F4C554D4E532046524F4D205B4D656173757265437562655D20,'ed303713-e520-4de6-bf20-7cd572637111','6823a09e-9bc7-4680-9af3-02e7db429b3d','admin','2015-03-06 14:20:01','tester','2015-03-12 20:16:25');
/*!40000 ALTER TABLE `qc_olap_mdx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_cron_triggers` VALUES ('scheduler','core.job.SendMailHelperJobCronTrigger','DEFAULT','0 0/1 * * * ?','Asia/Taipei');
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
INSERT INTO `qrtz_job_details` VALUES ('scheduler','core.job.SendMailHelperJob','DEFAULT',NULL,'com.netsteadfast.greenstep.job.impl.SendMailHelperJobImpl','1','0','0','0',0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
INSERT INTO `qrtz_locks` VALUES ('scheduler','STATE_ACCESS'),('scheduler','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
INSERT INTO `qrtz_scheduler_state` VALUES ('scheduler','4753PC1438825559658',1438826843158,7500);
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_triggers` VALUES ('scheduler','core.job.SendMailHelperJobCronTrigger','DEFAULT','core.job.SendMailHelperJob','DEFAULT',NULL,1438826880000,1438826820000,0,'WAITING','CRON',1438825559000,0,NULL,0,'');
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_account`
--

DROP TABLE IF EXISTS `tb_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_account` (
  `OID` char(36) NOT NULL,
  `ACCOUNT` varchar(24) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `ON_JOB` varchar(50) NOT NULL DEFAULT 'Y',
  `CUSERID` varchar(24) DEFAULT NULL,
  `CDATE` datetime DEFAULT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`,`ACCOUNT`),
  UNIQUE KEY `UK_1` (`ACCOUNT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_account`
--

LOCK TABLES `tb_account` WRITE;
/*!40000 ALTER TABLE `tb_account` DISABLE KEYS */;
INSERT INTO `tb_account` VALUES ('0','admin','b7f1acbdc67d3b2a68d36a5d7a29b228','Y','admin','2012-11-11 10:56:23','admin','2014-04-19 11:32:04'),('15822da5-25dc-490c-bdfb-be75f5ff4843','tester','5c06e78482c0a8b9cefb34e5cad64941','Y','admin','2015-04-23 11:26:53',NULL,NULL);
/*!40000 ALTER TABLE `tb_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role` (
  `OID` char(36) NOT NULL,
  `ROLE` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(50) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ROLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` VALUES ('4b1796ad-0bb7-4a65-b45e-439540ba5dbd','admin','administrator role!','admin','2014-10-09 15:02:24',NULL,NULL),('6967035a-d7d6-4b12-87d6-9ae66beef01d','BSC_STANDARD','Balanced Scorecard role, not modify it!','admin','2014-12-22 19:16:25','admin','2014-12-22 19:21:51'),('c7c69396-e5e6-48ca-b09c-9445b69e2ad5','*','all role','admin','2014-10-09 15:02:54',NULL,NULL);
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role_permission`
--

DROP TABLE IF EXISTS `tb_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role_permission` (
  `OID` char(36) NOT NULL,
  `ROLE` varchar(50) NOT NULL,
  `PERMISSION` varchar(255) NOT NULL,
  `PERM_TYPE` varchar(15) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(50) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ROLE`,`PERMISSION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role_permission`
--

LOCK TABLES `tb_role_permission` WRITE;
/*!40000 ALTER TABLE `tb_role_permission` DISABLE KEYS */;
INSERT INTO `tb_role_permission` VALUES ('01cd73cd-598c-469d-9357-3d2396388734','BSC_STANDARD','BSC_PROG001D0003Q','CONTROLLER','','admin','2014-12-22 19:18:32',NULL,NULL),('031c0559-f666-4320-a94d-643d00d3e974','BSC_STANDARD','BSC_PROG003D0001Q','CONTROLLER','','admin','2014-12-22 19:21:06',NULL,NULL),('03432ce8-a5c7-4b33-b3ba-54b899f2d830','BSC_STANDARD','BSC_PROG002D0002Q','CONTROLLER','','admin','2014-12-22 19:19:36',NULL,NULL),('03d88c76-a1d0-4fba-a84f-660651a123ba','BSC_STANDARD','bsc.service.logic.WeightLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:33:04',NULL,NULL),('041c7ec9-d5e6-488b-a79d-3ab0fa239a60','BSC_STANDARD','bsc.service.logic.KpiLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:31:54',NULL,NULL),('04b60bff-ff8b-48b5-85dc-9c7f9aef5d70','BSC_STANDARD','BSC_PROG003D0004Q','CONTROLLER','','admin','2015-03-31 10:41:49',NULL,NULL),('055c2d59-4f7b-4158-9719-210e03d4bd1d','BSC_STANDARD','bsc.service.logic.EmployeeLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:28:47',NULL,NULL),('06e7239c-3fc1-420e-867c-41d79f7a755e','BSC_STANDARD','qcharts.service.logic.DataQueryLogicService#UPDATE','COMPOMENT','','admin','2015-01-17 10:39:28',NULL,NULL),('082f19f7-b199-4da6-bbc9-866265b80f36','BSC_STANDARD','qcharts.service.logic.DataQueryMapperLogicService#DELETE','COMPOMENT','','admin','2015-01-16 14:35:14',NULL,NULL),('087e90f7-91ff-43d1-b240-789cfbe48258','BSC_STANDARD','BSC_PROG002D','CONTROLLER','','admin','2014-12-22 19:18:54',NULL,NULL),('0887f671-d734-4f0a-8d51-b1869fda5510','BSC_STANDARD','bsc.service.logic.VisionLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:29:18',NULL,NULL),('0ae3df58-b4a2-435e-ba99-42f70c832d2a','BSC_STANDARD','bsc.service.logic.ObjectiveLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:31:01',NULL,NULL),('0e56aff2-7f23-402f-9879-e004aebecbd3','BSC_STANDARD','bsc.service.logic.ReportRoleViewLogicService#INSERT','COMPOMENT','','admin','2015-03-24 15:28:14',NULL,NULL),('0f323dae-2ed6-4a5d-a80e-f59ca9ed2f38','BSC_STANDARD','BSC_PROG001D0008A','CONTROLLER','','admin','2015-03-11 20:32:01',NULL,NULL),('0f9472c5-0c3c-488c-b03b-2961c7e5ea3a','BSC_STANDARD','qcharts.service.logic.AnalyticsMDXLogicService#SELECT','COMPOMENT','','admin','2015-03-06 14:29:25',NULL,NULL),('103d1b11-ab9b-4dd1-a076-2ec12cd467f7','BSC_STANDARD','BSC_PROG002D0008Q','CONTROLLER','','admin','2014-12-22 19:20:55',NULL,NULL),('10de4175-2676-435a-9327-7b38a30a9924','BSC_STANDARD','BSC_PROG002D0002A','CONTROLLER','','admin','2014-12-22 19:19:24',NULL,NULL),('128794b5-46a5-4c4a-93f8-7a7ef329374d','BSC_STANDARD','bsc.service.logic.StrategyMapLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:34:25',NULL,NULL),('14831116-14f2-4815-b337-8f4f5253f5b5','BSC_STANDARD','QCHARTS_PROG002D0002A','CONTROLLER','','admin','2015-03-06 14:28:44',NULL,NULL),('15e0399d-40ef-4605-9de2-f81ae375c90a','BSC_STANDARD','CORE_PROG001D0004Q','CONTROLLER','','admin','2014-12-23 10:13:20',NULL,NULL),('1973eedb-2c09-4dc6-9752-9a75a789e394','BSC_STANDARD','qcharts.service.logic.DataQueryMapperLogicService#UPDATE','COMPOMENT','','admin','2015-01-16 14:35:08',NULL,NULL),('1b1f1d39-813a-4a37-be9b-33a4a73c8cb1','BSC_STANDARD','bsc.service.logic.KpiLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:32:01',NULL,NULL),('1b7145f0-ad1e-46a8-86e9-8b50ea27c807','BSC_STANDARD','BSC_PROG003D0005Q','CONTROLLER','','admin','2015-04-01 19:38:48',NULL,NULL),('1de002f4-a22c-40da-b4f5-7b01c5a723c8','BSC_STANDARD','BSC_PROG004D','CONTROLLER','','admin','2014-12-22 19:21:25',NULL,NULL),('23e11d4c-77ba-49f1-872b-df3a15e69b60','BSC_STANDARD','BSC_PROG001D0008E','CONTROLLER','','admin','2015-03-11 20:32:08',NULL,NULL),('272c2687-bd46-4d44-855a-96e430a74e6b','BSC_STANDARD','bsc.service.logic.AggregationMethodLogicService#SELECT','COMPOMENT','','admin','2015-03-12 11:24:07',NULL,NULL),('2c5bd538-558e-4cb0-995d-28e74b2eb251','BSC_STANDARD','bsc.service.logic.PerspectiveLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:29:55',NULL,NULL),('2cd5db5f-e907-439b-bf38-b6fa9f5cc2f7','BSC_STANDARD','bsc.service.logic.SwotLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:34:46',NULL,NULL),('2fb53391-9d11-48a2-a222-06cd56084f68','BSC_STANDARD','BSC_PROG004D0001Q','CONTROLLER','','admin','2014-12-22 19:21:32',NULL,NULL),('317fa618-c840-475a-971d-69ec31051c2d','BSC_STANDARD','BSC_PROG001D0002Q','CONTROLLER','','admin','2014-12-22 19:18:06',NULL,NULL),('39cc9800-ad1d-4510-9d27-d1d8767c686c','BSC_STANDARD','BSC_PROG001D0008Q','CONTROLLER','','admin','2015-03-11 20:31:54',NULL,NULL),('3c73200e-bbd6-40ca-b911-25b8bdec687d','BSC_STANDARD','bsc.service.logic.StrategyMapLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:34:11',NULL,NULL),('3d66e504-700b-43bd-9007-ed3feb2717c3','BSC_STANDARD','CORE_PROGCOMM0003Q','CONTROLLER','','admin','2015-02-14 11:53:31',NULL,NULL),('3dc1c911-e3ce-4e25-8e72-24bdfe1194a6','BSC_STANDARD','bsc.service.logic.ImportDataLogicService#UPDATE','COMPOMENT','','admin','2015-05-26 13:49:01',NULL,NULL),('3e5aa669-5e25-4e53-b670-7ad94ba9f822','BSC_STANDARD','bsc.service.logic.ReportRoleViewLogicService#DELETE','COMPOMENT','','admin','2015-03-24 15:28:31',NULL,NULL),('3ff36a2c-9793-4382-84be-abed8c18449c','BSC_STANDARD','bsc.service.logic.PerspectiveLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:29:50',NULL,NULL),('4024c47f-8e6d-480f-8628-785150782cd0','BSC_STANDARD','BSC_PROG001D0001Q_S00','CONTROLLER','','admin','2014-12-22 19:17:59',NULL,NULL),('446cd01e-d89a-4d5b-b414-f37f5b884cbc','BSC_STANDARD','core.service.logic.SystemCalendarNoteLogicService#INSERT','COMPOMENT','','admin','2014-12-23 10:27:22',NULL,NULL),('4692193a-60fc-4a9c-8d87-e6ea29b4ede2','BSC_STANDARD','bsc.service.logic.OrganizationLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:28:17',NULL,NULL),('48a76bc5-d4e8-4ab3-a0ef-da4c8bb62f98','BSC_STANDARD','BSC_PROG001D0002E','CONTROLLER','','admin','2015-05-26 17:33:35',NULL,NULL),('49268083-50d7-45ee-b84d-8a6569635e7d','BSC_STANDARD','bsc.service.logic.WeightLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:33:24',NULL,NULL),('4c03577a-6915-4e97-b6f3-118f0e7c706e','BSC_STANDARD','bsc.service.logic.ObjectiveLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:31:18',NULL,NULL),('4d453421-e92d-48c0-968b-0b560f5fc156','BSC_STANDARD','BSC_PROG001D','CONTROLLER','','admin','2014-12-22 19:17:07',NULL,NULL),('52d25b54-130c-4538-ad3f-7629f6db6c2b','BSC_STANDARD','bsc.service.logic.FormulaLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:32:21',NULL,NULL),('535e9088-9df4-4eb3-989b-c89ae2440d60','BSC_STANDARD','bsc.service.logic.StrategyMapLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:34:18',NULL,NULL),('5367569b-52c9-4fea-80dc-26d0c5900140','BSC_STANDARD','bsc.service.logic.FormulaLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:32:28',NULL,NULL),('53c4bcbf-6fc2-45e9-9f91-62bbceebd9ca','BSC_STANDARD','BSC_PROG003D0002Q','CONTROLLER','','admin','2014-12-22 19:21:14',NULL,NULL),('5b39c53f-f841-4684-bab8-2fccdda5fcb3','BSC_STANDARD','bsc.service.logic.VisionLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:29:33',NULL,NULL),('5b7ea5a4-e74b-49e6-9147-56f65775180d','BSC_STANDARD','BSC_PROG001D0001E','CONTROLLER','','admin','2014-12-22 19:17:22',NULL,NULL),('5f592c6e-78c8-44b4-9937-016067eb9b07','BSC_STANDARD','bsc.service.logic.MeasureDataLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:33:36',NULL,NULL),('60353f87-e88b-499d-8f5e-e376617efa8c','BSC_STANDARD','CORE_PROG001D0004A','CONTROLLER','','admin','2014-12-23 10:06:42',NULL,NULL),('62c51823-a195-4e9b-9307-18c89399411e','BSC_STANDARD','bsc.service.logic.EmployeeLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:28:34',NULL,NULL),('65bc3d3d-d7cb-4847-a162-b46518f8af3c','BSC_STANDARD','bsc.service.logic.PerspectiveLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:30:29',NULL,NULL),('6782597a-fb61-43f2-8d46-05565f4aae12','BSC_STANDARD','bsc.service.logic.ImportDataLogicService#INSERT','COMPOMENT','','admin','2015-05-26 13:48:53',NULL,NULL),('67b2be9a-8f79-4f9c-a2cd-3d2a223eb2fe','BSC_STANDARD','qcharts.service.logic.AnalyticsMDXLogicService#UPDATE','COMPOMENT','','admin','2015-03-06 14:29:39',NULL,NULL),('6de2c74a-fbb9-4cc8-94aa-4ca9aea8f3be','BSC_STANDARD','BSC_PROG002D0003E','CONTROLLER','','admin','2014-12-22 19:19:52',NULL,NULL),('6f87e102-1d15-4588-a3e0-9ed947178fc4','BSC_STANDARD','BSC_PROG002D0003Q','CONTROLLER','','admin','2014-12-22 19:20:03',NULL,NULL),('728a4820-97d6-4e9a-ac77-745e1de7fcc0','BSC_STANDARD','qcharts.service.logic.AnalyticsMDXLogicService#DELETE','COMPOMENT','','admin','2015-03-06 14:29:32',NULL,NULL),('72b93d05-f8fe-4a52-9ea6-3cd071878951','BSC_STANDARD','BSC_PROG002D0005Q','CONTROLLER','','admin','2014-12-22 19:20:37',NULL,NULL),('7486cc15-8027-4615-8852-c54dc0e25c72','BSC_STANDARD','CORE_PROGCOMM0002Q','CONTROLLER','','admin','2014-12-22 19:16:59',NULL,NULL),('7622e787-db2c-4010-89d4-b76446c595b6','BSC_STANDARD','bsc.service.logic.VisionLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:29:40',NULL,NULL),('790476cd-3141-4523-af34-e09983f15351','BSC_STANDARD','qcharts.service.logic.DataQueryMapperLogicService#SELECT','COMPOMENT','','admin','2015-01-16 14:34:52',NULL,NULL),('7a3fd642-0ca1-4dbe-a1a6-649ddb95e33a','BSC_STANDARD','bsc.service.logic.KpiLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:31:47',NULL,NULL),('7bfa5f18-9da1-4ec2-8478-0bcae2e0d907','BSC_STANDARD','BSC_PROG001D0003A','CONTROLLER','','admin','2014-12-22 19:18:19',NULL,NULL),('7d8a6f2d-8423-46dc-8f8a-d754f32c0874','BSC_STANDARD','BSC_PROG002D0006Q','CONTROLLER','','admin','2014-12-22 19:20:43',NULL,NULL),('7dbf40a9-335c-4891-8ca2-20938a3ff4b6','BSC_STANDARD','bsc.service.logic.ReportRoleViewLogicService#UPDATE','COMPOMENT','','admin','2015-03-24 15:28:23',NULL,NULL),('7e1bc08a-a1ce-49f3-8645-3a28b1f09060','BSC_STANDARD','BSC_PROG002D0007Q','CONTROLLER','','admin','2014-12-22 19:20:48',NULL,NULL),('802282a0-831a-4235-93eb-135c2eefe308','BSC_STANDARD','bsc.service.logic.VisionLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:29:27',NULL,NULL),('8154adb9-bc6c-400e-849f-2e92b2dde87f','BSC_STANDARD','QCHARTS_PROG001D0002A','CONTROLLER','','admin','2015-01-16 14:34:24',NULL,NULL),('817a6cf2-7ef8-4ecf-989d-ebd30e161c18','BSC_STANDARD','BSC_PROG001D0001E_S00','CONTROLLER','','admin','2014-12-22 19:17:40',NULL,NULL),('82d63316-08e0-4d8e-a6df-efd78273ec30','BSC_STANDARD','BSC_PROG002D0001E','CONTROLLER','','admin','2014-12-22 19:19:07',NULL,NULL),('85f8f531-d94c-4713-a4e5-2d13e94a2350','BSC_STANDARD','bsc.service.logic.OrganizationLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:28:01',NULL,NULL),('8661f3f8-8669-434f-9b80-e73325e57cdf','BSC_STANDARD','qcharts.service.logic.AnalyticsMDXLogicService#INSERT','COMPOMENT','','admin','2015-03-06 14:29:18',NULL,NULL),('86d95a1c-012c-4d97-b4a8-b4200e255824','BSC_STANDARD','BSC_PROG003D0003Q','CONTROLLER','','admin','2014-12-22 19:21:19',NULL,NULL),('8946c9a0-0008-420f-9142-cd7f251f3143','BSC_STANDARD','qcharts.service.logic.DataQueryLogicService#DELETE','COMPOMENT','','admin','2015-01-17 10:39:42',NULL,NULL),('89ba9428-e931-4f09-a93a-e371e05f8ec7','BSC_STANDARD','BSC_PROG002D0001Q','CONTROLLER','','admin','2014-12-22 19:19:13',NULL,NULL),('8cbc4499-5fbd-4b94-891e-09cad3e136ea','BSC_STANDARD','qcharts.service.logic.DataQueryMapperLogicService#INSERT','COMPOMENT','','admin','2015-01-16 14:35:00',NULL,NULL),('8e07a2e5-1d14-4e66-94b5-e2da14b17d01','BSC_STANDARD','bsc.service.logic.OrganizationLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:28:24',NULL,NULL),('8ec7224b-ad44-4ce6-97b9-576bb13cd718','BSC_STANDARD','QCHARTS_PROG002D0001Q','CONTROLLER','','admin','2015-01-12 20:08:02',NULL,NULL),('8ff7dc7f-5f3b-41a5-9b39-21cbaabddd7a','BSC_STANDARD','BSC_PROG001D0003E','CONTROLLER','','admin','2014-12-22 19:18:25',NULL,NULL),('91714e4e-05ab-4956-ae48-37c108c5a98b','BSC_STANDARD','bsc.service.logic.SwotLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:34:39',NULL,NULL),('91a8763b-37f4-4858-85dc-78fc1903a302','BSC_STANDARD','BSC_PROG002D0004E','CONTROLLER','','admin','2014-12-22 19:20:15',NULL,NULL),('924919e3-a8ec-4a5e-a34a-536fabfd08e6','BSC_STANDARD','BSC_PROG001D0001Q','CONTROLLER','','admin','2014-12-22 19:17:48',NULL,NULL),('92f51b72-f2e3-4de5-b21b-e5ccc6af33e4','BSC_STANDARD','qcharts.service.logic.DataQueryLogicService#SELECT','COMPOMENT','','admin','2015-01-17 10:39:21',NULL,NULL),('940849df-16a5-43bf-aaa8-49705aaf0b5b','BSC_STANDARD','bsc.service.logic.OrganizationLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:28:08',NULL,NULL),('96ba94c6-120b-4652-99e7-ea204016bcf7','BSC_STANDARD','BSC_PROG002D0004A','CONTROLLER','','admin','2014-12-22 19:20:10',NULL,NULL),('97895517-5741-4d60-a9ab-1e9ced5d3aad','BSC_STANDARD','bsc.service.logic.MeasureDataLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:33:52',NULL,NULL),('979e87cd-c036-4a24-bcc3-2d37a44f2c63','BSC_STANDARD','bsc.service.logic.AggregationMethodLogicService#UPDATE','COMPOMENT','','admin','2015-03-12 11:24:23',NULL,NULL),('99067ca3-46fd-4ac1-b00f-f3550bb6291e','BSC_STANDARD','bsc.service.logic.FormulaLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:32:13',NULL,NULL),('9a6d4cd3-6f1a-4b45-ae82-cb7f2cb8b89d','BSC_STANDARD','BSC_PROG001D0002A','CONTROLLER','','admin','2015-05-26 17:33:22',NULL,NULL),('9c98ca2a-77eb-42c4-9974-53bcf4778b2c','BSC_STANDARD','bsc.service.logic.AggregationMethodLogicService#INSERT','COMPOMENT','','admin','2015-03-12 11:24:15',NULL,NULL),('9f192ead-71be-4723-9f75-a430b001740f','BSC_STANDARD','BSC_PROG001D0006Q','CONTROLLER','','admin','2015-01-22 19:08:45',NULL,NULL),('a0b83f02-d3b9-4e5e-a5ee-b0eedd859b85','BSC_STANDARD','bsc.service.logic.AggregationMethodLogicService#DELETE','COMPOMENT','','admin','2015-03-12 11:24:31',NULL,NULL),('a17ede81-71ae-4022-af99-d6193a0d58b6','BSC_STANDARD','bsc.service.logic.ImportDataLogicService#DELETE','COMPOMENT','','admin','2015-05-26 13:49:16',NULL,NULL),('a2fbec4a-a6da-484a-932a-d04a1825ff29','BSC_STANDARD','bsc.service.logic.ObjectiveLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:30:46',NULL,NULL),('a3222dae-bb99-4d06-ac88-8e2fec55b47d','BSC_STANDARD','BSC_PROG002D0002E','CONTROLLER','','admin','2014-12-22 19:19:30',NULL,NULL),('ac87eed8-d24c-4c55-b2c5-f8af43136feb','BSC_STANDARD','bsc.service.logic.FormulaLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:32:38',NULL,NULL),('ae88d086-0f39-4cab-a071-3db6c43e8ddf','BSC_STANDARD','bsc.service.logic.StrategyMapLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:34:32',NULL,NULL),('b13e88c0-7f7e-479b-ba6a-777152c94f77','BSC_STANDARD','BSC_PROG003D','CONTROLLER','','admin','2014-12-22 19:21:01',NULL,NULL),('b5aa807f-5e6d-435f-bdbb-4005334f5a87','BSC_STANDARD','bsc.service.logic.ImportDataLogicService#SELECT','COMPOMENT','','admin','2015-05-26 13:49:08',NULL,NULL),('b620b865-5f62-43cf-9474-07252fd0ab11','BSC_STANDARD','BSC_PROG002D0001A','CONTROLLER','','admin','2014-12-22 19:19:01',NULL,NULL),('b6fa4e94-8bb7-464c-9141-399f1dedb6f6','BSC_STANDARD','core.service.logic.SystemCalendarNoteLogicService#DELETE','COMPOMENT','','admin','2014-12-23 10:27:29',NULL,NULL),('b90eefc1-0818-4c1c-9ed0-986149c6e82e','BSC_STANDARD','bsc.service.logic.WeightLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:33:18',NULL,NULL),('b93b9bed-67e2-4e18-9b72-28867fa4326b','BSC_STANDARD','bsc.service.logic.KpiLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:32:07',NULL,NULL),('bd31fe78-a2a1-4bf4-9d71-4d49586e799f','BSC_STANDARD','CORE_PROGCOMM0004Q','CONTROLLER','','admin','2015-03-12 15:43:17',NULL,NULL),('be4efd05-f2a3-4a66-8dab-5e4a13adbb3a','BSC_STANDARD','QCHARTS_PROG001D0002E','CONTROLLER','','admin','2015-01-16 14:34:31',NULL,NULL),('be64923f-efcb-4fae-b0dd-4bf2b10685eb','BSC_STANDARD','bsc.service.logic.PerspectiveLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:30:02',NULL,NULL),('c3d28b40-5ff6-4342-87df-ba738c464462','BSC_STANDARD','core.service.logic.SystemCalendarNoteLogicService#UPDATE','COMPOMENT','','admin','2014-12-23 10:27:15',NULL,NULL),('ca0168c5-5b04-4f78-828b-d7d287f7f109','BSC_STANDARD','bsc.service.logic.WeightLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:33:11',NULL,NULL),('cd9ba3e9-d4a3-458e-82af-d8ba72569b2c','BSC_STANDARD','bsc.service.logic.MeasureDataLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:33:45',NULL,NULL),('cdb5a32a-786f-4ebe-a5bd-4d9a83d83325','BSC_STANDARD','BSC_PROG001D0007Q','CONTROLLER','','admin','2015-02-03 19:12:28',NULL,NULL),('cdbb3479-9d64-4813-b2ac-275c4dd77943','BSC_STANDARD','bsc.service.logic.SwotLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:34:59',NULL,NULL),('cebce1ca-5170-45c6-bc18-0dea19d6919b','BSC_STANDARD','BSC_PROG001D0004Q','CONTROLLER','','admin','2014-12-22 19:18:48',NULL,NULL),('cec644a1-5657-4a1e-ac68-9893e802777a','BSC_STANDARD','QCHARTS_PROG001D0002Q','CONTROLLER','','admin','2015-01-16 14:34:17',NULL,NULL),('cfac38fb-f75c-4da2-9eca-41d5a30aca6f','BSC_STANDARD','bsc.service.logic.SwotLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:34:52',NULL,NULL),('d344a52a-6ae6-41c8-a6e0-1c6748ddc412','BSC_STANDARD','CORE_PROG001D0008Q_S00','CONTROLLER','','admin','2015-04-20 11:38:38',NULL,NULL),('d9027726-3a1c-4b09-9e80-51325d505d79','BSC_STANDARD','BSC_PROG002D0003A','CONTROLLER','','admin','2014-12-22 19:19:44',NULL,NULL),('d93b5c25-e3a3-4991-af06-fcc4dadec2f0','BSC_STANDARD','core.service.logic.SystemCalendarNoteLogicService#SELECT','COMPOMENT','','admin','2014-12-23 10:27:07',NULL,NULL),('d9ccdaeb-8e9c-4ab5-af1f-c4641e1dda99','BSC_STANDARD','qcharts.service.logic.DataQueryLogicService#INSERT','COMPOMENT','','admin','2015-01-17 10:39:34',NULL,NULL),('dcd765bf-3830-45ca-8618-3c679cd57610','BSC_STANDARD','BSC_PROG001D0002Q_S00','CONTROLLER','','admin','2014-12-22 19:18:13',NULL,NULL),('dff8526c-5f4b-4896-bb82-c61391ba0da1','BSC_STANDARD','bsc.service.logic.MeasureDataLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:33:59',NULL,NULL),('e5f03cdc-6be2-4d28-87d3-0c58d2a724a6','BSC_STANDARD','QCHARTS_PROG002D0001A','CONTROLLER','','admin','2015-01-16 22:54:06',NULL,NULL),('e92a1b3b-596d-4aa3-a637-32d8bd596847','BSC_STANDARD','BSC_PROG001D0001A','CONTROLLER','','admin','2014-12-22 19:17:15',NULL,NULL),('ea5b1405-ffe9-4cca-bcae-f6ad05ed90da','BSC_STANDARD','CORE_PROG001D0004E','CONTROLLER','','admin','2014-12-23 10:13:25',NULL,NULL),('eb8b5122-6093-40d3-9a42-aa7022a44ccc','BSC_STANDARD','CORE_PROGCOMM0001Q','CONTROLLER','','admin','2014-12-22 19:16:51',NULL,NULL),('ed1539ab-cb92-442d-951b-132fa13b15b6','BSC_STANDARD','QCHARTS_PROG002D0002Q','CONTROLLER','','admin','2015-03-05 10:03:54',NULL,NULL),('efa2dbef-be81-4a0c-8b7f-92301cc53d61','BSC_STANDARD','BSC_PROG003D0006Q','CONTROLLER','','admin','2015-04-04 15:56:45',NULL,NULL),('f569dd50-dde8-4f75-bf60-b856646671f6','BSC_STANDARD','bsc.service.logic.ReportRoleViewLogicService#SELECT','COMPOMENT','','admin','2015-03-24 15:28:06',NULL,NULL),('f81d9aa1-0c4c-4d4f-8dc9-1c14f47e6511','BSC_STANDARD','bsc.service.logic.EmployeeLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:28:41',NULL,NULL),('fb83381d-69bb-49ea-9fc7-68bd4880fcf1','BSC_STANDARD','bsc.service.logic.ObjectiveLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:31:10',NULL,NULL),('fd9abf71-f97a-4a87-abb8-8c6707567ceb','BSC_STANDARD','BSC_PROG002D0004Q','CONTROLLER','','admin','2014-12-22 19:20:23',NULL,NULL);
/*!40000 ALTER TABLE `tb_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys`
--

DROP TABLE IF EXISTS `tb_sys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys` (
  `OID` char(36) NOT NULL,
  `SYS_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `HOST` varchar(200) NOT NULL,
  `CONTEXT_PATH` varchar(100) NOT NULL,
  `IS_LOCAL` varchar(1) NOT NULL DEFAULT 'Y',
  `ICON` varchar(20) NOT NULL DEFAULT ' ',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`SYS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys`
--

LOCK TABLES `tb_sys` WRITE;
/*!40000 ALTER TABLE `tb_sys` DISABLE KEYS */;
INSERT INTO `tb_sys` VALUES ('8e4f5372-d3f0-415c-9c6a-075e0fc0bdc7','QCHARTS','B. Query Charts','127.0.0.1:8080','qcharts-web','N','CHART_PIE','admin','2015-01-10 10:19:46','admin','2015-02-12 20:00:27'),('c6643182-85a5-4f91-9e73-10567ebd0dd5','CORE','Z. Core','127.0.0.1:8080','core-web','Y','SYSTEM','admin','2014-09-25 00:00:00','admin','2015-02-12 20:00:32'),('f0ad30f5-a23e-4fa8-aa16-ae2763a4a75a','BSC','A. Balanced scorecard','127.0.0.1:8080','gsbsc-web','N','STAR','admin','2014-11-03 15:23:11','admin','2015-05-29 19:13:59');
/*!40000 ALTER TABLE `tb_sys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_bean_help`
--

DROP TABLE IF EXISTS `tb_sys_bean_help`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_bean_help` (
  `OID` char(36) NOT NULL,
  `BEAN_ID` varchar(255) NOT NULL,
  `METHOD` varchar(100) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `ENABLE_FLAG` varchar(1) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`BEAN_ID`,`METHOD`,`SYSTEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_bean_help`
--

LOCK TABLES `tb_sys_bean_help` WRITE;
/*!40000 ALTER TABLE `tb_sys_bean_help` DISABLE KEYS */;
INSERT INTO `tb_sys_bean_help` VALUES ('8be79c68-e309-4405-9ee3-78c4bdddf0f0','bsc.service.logic.MeasureDataLogicService','saveOrUpdate','BSC','N','Christopher Hayden\'s require','admin','2015-04-14 09:11:05','admin','2015-04-14 12:23:03'),('b4cff34e-c8db-4d5a-859d-79bd080de7e3','bsc.service.logic.KpiLogicService','create','BSC','Y','KPI create expression support!','admin','2014-11-23 14:28:37',NULL,NULL),('e129ddc5-0d8e-4e5f-bb9d-9de0a8c827cc','bsc.service.logic.KpiLogicService','update','BSC','Y','KPI update expression support!','admin','2014-11-23 16:49:15',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_bean_help` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_bean_help_expr`
--

DROP TABLE IF EXISTS `tb_sys_bean_help_expr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_bean_help_expr` (
  `OID` char(36) NOT NULL,
  `HELP_OID` char(36) NOT NULL,
  `EXPR_ID` varchar(20) NOT NULL,
  `EXPR_SEQ` varchar(10) NOT NULL,
  `RUN_TYPE` varchar(10) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`EXPR_ID`,`HELP_OID`,`RUN_TYPE`),
  KEY `IDX_1` (`HELP_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_bean_help_expr`
--

LOCK TABLES `tb_sys_bean_help_expr` WRITE;
/*!40000 ALTER TABLE `tb_sys_bean_help_expr` DISABLE KEYS */;
INSERT INTO `tb_sys_bean_help_expr` VALUES ('07eba8be-d580-4b72-87a9-b482bf5faf76','b4cff34e-c8db-4d5a-859d-79bd080de7e3','BSC_KPI_EXPR0001','0000000000','BEFORE','admin','2014-11-23 14:29:11',NULL,NULL),('75be6d97-ccd8-4822-bd71-739f3fbfe31a','b4cff34e-c8db-4d5a-859d-79bd080de7e3','BSC_KPI_EXPR0002','7777777777','AFTER','admin','2014-11-23 14:29:23',NULL,NULL),('90497cc0-ddce-4c9e-ae5c-0e078d2b0b70','e129ddc5-0d8e-4e5f-bb9d-9de0a8c827cc','BSC_KPI_EXPR0001','0000000000','BEFORE','admin','2014-11-23 17:07:04',NULL,NULL),('a9a6683d-69f0-41f0-89a4-22e2a7c0b735','8be79c68-e309-4405-9ee3-78c4bdddf0f0','BSC_MD_EXPR0001','7777777777','BEFORE','admin','2015-04-14 09:11:45',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_bean_help_expr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_bean_help_expr_map`
--

DROP TABLE IF EXISTS `tb_sys_bean_help_expr_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_bean_help_expr_map` (
  `OID` char(36) NOT NULL,
  `HELP_EXPR_OID` char(36) NOT NULL,
  `METHOD_RESULT_FLAG` varchar(1) NOT NULL DEFAULT 'N',
  `METHOD_PARAM_CLASS` varchar(255) NOT NULL DEFAULT ' ',
  `METHOD_PARAM_INDEX` int(3) NOT NULL DEFAULT '0',
  `VAR_NAME` varchar(255) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`VAR_NAME`,`HELP_EXPR_OID`),
  KEY `IDX_1` (`HELP_EXPR_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_bean_help_expr_map`
--

LOCK TABLES `tb_sys_bean_help_expr_map` WRITE;
/*!40000 ALTER TABLE `tb_sys_bean_help_expr_map` DISABLE KEYS */;
INSERT INTO `tb_sys_bean_help_expr_map` VALUES ('049196f6-665c-4e46-9051-e334fb99d72a','a9a6683d-69f0-41f0-89a4-22e2a7c0b735','N','java.lang.String',0,'kpiOid','admin','2015-04-14 10:25:05',NULL,NULL),('17aaaea0-65b2-4d39-acba-0ec8fe6fca36','07eba8be-d580-4b72-87a9-b482bf5faf76','N','com.netsteadfast.greenstep.vo.KpiVO',0,'kpi','admin','2014-11-23 14:32:06',NULL,NULL),('1cb05771-318e-49a5-bcbd-8d7340513ba0','75be6d97-ccd8-4822-bd71-739f3fbfe31a','Y','',0,'resultObj','admin','2014-11-23 14:33:54',NULL,NULL),('5a654814-f42c-4c0d-9390-b2ef7c175a49','a9a6683d-69f0-41f0-89a4-22e2a7c0b735','N','java.lang.String',5,'employeeId','admin','2015-04-14 10:40:36',NULL,NULL),('71ce59f8-cbcf-4b9e-94ae-1e2caa80bd41','a9a6683d-69f0-41f0-89a4-22e2a7c0b735','N','java.lang.String',2,'frequency','admin','2015-04-14 09:12:15',NULL,NULL),('756a005e-3948-48b0-af8a-4eef655b35c0','a9a6683d-69f0-41f0-89a4-22e2a7c0b735','N','java.util.ArrayList',6,'measureDatas','admin','2015-04-14 09:31:26',NULL,NULL),('8626832b-4d9f-4e6d-9b1a-d4a44b5330a6','a9a6683d-69f0-41f0-89a4-22e2a7c0b735','N','java.lang.String',4,'organizationId','admin','2015-04-14 10:40:30',NULL,NULL),('d95f48cb-f131-46a8-b779-72ee3ea75e95','90497cc0-ddce-4c9e-ae5c-0e078d2b0b70','N','com.netsteadfast.greenstep.vo.KpiVO',0,'kpi','admin','2014-11-23 17:13:46',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_bean_help_expr_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_calendar_note`
--

DROP TABLE IF EXISTS `tb_sys_calendar_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_calendar_note` (
  `OID` char(36) NOT NULL,
  `ACCOUNT` varchar(24) NOT NULL,
  `CALENDAR_ID` varchar(11) NOT NULL,
  `TITLE` varchar(100) NOT NULL,
  `NOTE` varchar(1000) NOT NULL,
  `DATE` varchar(8) NOT NULL,
  `TIME` varchar(9) NOT NULL,
  `ALERT` varchar(1) NOT NULL DEFAULT 'N',
  `CONTACT` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`CALENDAR_ID`,`ACCOUNT`),
  KEY `IDX_1` (`DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_calendar_note`
--

LOCK TABLES `tb_sys_calendar_note` WRITE;
/*!40000 ALTER TABLE `tb_sys_calendar_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_calendar_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_code`
--

DROP TABLE IF EXISTS `tb_sys_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_code` (
  `OID` char(36) NOT NULL,
  `CODE` varchar(25) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `PARAM1` varchar(100) DEFAULT NULL,
  `PARAM2` varchar(100) DEFAULT NULL,
  `PARAM3` varchar(100) DEFAULT NULL,
  `PARAM4` varchar(100) DEFAULT NULL,
  `PARAM5` varchar(100) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_code`
--

LOCK TABLES `tb_sys_code` WRITE;
/*!40000 ALTER TABLE `tb_sys_code` DISABLE KEYS */;
INSERT INTO `tb_sys_code` VALUES ('009476ee-5950-40d7-be2f-5343ea42fba8','BSC_CONF001','BSC','Employee default role for create','BSC_STANDARD',NULL,NULL,NULL,NULL,'admin','2014-12-23 10:36:23',NULL,NULL),('0badf3df-6382-47f4-921e-ad6b4fe81dc2','CNF_CONF004','CNF','enable system template file re-write always!','Y',NULL,NULL,NULL,NULL,'admin','2015-05-13 10:09:01','admin','2015-05-13 19:24:28'),('10046e27-4c0a-40ed-89c5-6530525b421f','MSG_DOS0006','MSG','insert data fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30','admin','2014-07-18 11:41:37'),('10a68d94-4ddd-4d38-a54f-30fa470fff66','BSC_RPTC002','RPT','font color','#ffffff',NULL,NULL,NULL,NULL,'admin','2014-12-07 08:58:52',NULL,NULL),('13cd63cd-61ea-4553-9ea6-1518406bb8f0','KMD_MEASURE03','KMD','Month','3',NULL,NULL,NULL,NULL,'admin','2014-11-26 09:19:11',NULL,NULL),('182f43a3-8a05-4456-9a90-bed2573afc96','BSC_KPIMA02','KPI','Smaller is better','2',NULL,NULL,NULL,NULL,'admin','2014-11-21 15:12:58',NULL,NULL),('1e44ed67-2628-4c39-bc75-b301eae235f9','BSC_KPICA02','KPI','Total','2',NULL,NULL,NULL,NULL,'admin','2014-11-21 15:16:47',NULL,NULL),('24948b53-f279-4d0b-88a6-0819abdbcb45','BSC_KPIMA03','KPI','Quasi is better','3',NULL,NULL,NULL,NULL,'admin','2014-11-21 15:13:45',NULL,NULL),('2c084902-051d-4918-a3b1-bd70c0617435','BSC_KPIDT02','KPI','Personal','2',NULL,NULL,NULL,NULL,'admin','2014-11-21 15:18:05',NULL,NULL),('2d9c84e4-a956-42ac-96cb-1f6292d182a9','CNF_CONF002','CNF','enable mail sender!','Y',NULL,NULL,NULL,NULL,'admin','2014-12-25 09:09:57','admin','2015-05-13 19:24:28'),('328849f7-446d-415e-a598-9477caa5b69c','MSG_UOS0002','MSG','parameter is incorrect!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('340b666b-f44b-49f8-b62e-3818d27bdaa4','BSC_KPICT01','KPI','Compared with target','1',NULL,NULL,NULL,NULL,'admin','2014-11-21 15:14:35',NULL,NULL),('41fe7e52-306c-44eb-92e8-8b9fefa4c0d3','KMD_MEASURE06','KMD','Year','6',NULL,NULL,NULL,NULL,'admin','2014-11-26 09:21:03',NULL,NULL),('44bf5446-b972-47a2-b0a1-9556861af07e','MSG_DOS0003','MSG','update data success!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('45cab4b8-add1-44be-8cab-7627403c389c','MSG_DOS0001','MSG','data no exist!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('4df770a6-6a9c-4d25-bdcd-1dee819d2ba6','CNF_CONF001','CNF','default mail from account!','root@localhost',NULL,NULL,NULL,NULL,'admin','2014-12-24 21:51:16','admin','2015-05-13 19:24:28'),('520a61eb-4bb0-4d0f-a0d5-4bbdc56f4202','MSG_DOS0005','MSG','insert data success!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('659b7cb1-6fb8-4bd2-a902-461379b38ce7','BSC_KPICA01','KPI','Average','1',NULL,NULL,NULL,NULL,'admin','2014-11-21 15:16:07',NULL,NULL),('6cec1a23-d2b9-48ac-b720-13fcf9f8d39b','MSG_UOS0001','MSG','parameter cann\'t blank!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('6fb72e96-43fe-45b3-8a45-9f5898f61b4f','MSG_DOS0009','MSG','search no data!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('73e6e324-a605-4dd7-af98-5bf7d5175171','BSC_RPTC004','RPT','Objective title','Objectives',NULL,NULL,NULL,NULL,'admin','2014-12-07 08:58:52',NULL,NULL),('750b16c0-9058-435c-b76f-9cb312d2f383','MSG_STD0004','MSG','Please upload a image file!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('76a4cc16-e324-4a94-8c62-9c78643f074a','MSG_STD0005','MSG','Data errors!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('7887053a-b1f7-412d-9e97-a0767f3bc2bc','KMD_MEASURE04','KMD','Quarter','4',NULL,NULL,NULL,NULL,'admin','2014-11-26 09:19:52',NULL,NULL),('7e8c56a4-d3dc-4768-9b2b-77dd03554814','MSG_DOS0008','MSG','delete data fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('83e1289f-e6dc-40e3-bd88-e567f3b2b346','KMD_MEASURE05','KMD','Half of year','5',NULL,NULL,NULL,NULL,'admin','2014-11-26 09:20:29',NULL,NULL),('8682e8cb-1241-4919-9094-30b869e6364d','BSC_KPIMA01','KPI','Bigger is better','1',NULL,NULL,NULL,NULL,'admin','2014-11-21 15:12:03',NULL,NULL),('87d59514-8d96-4b1f-a0ce-0ed444bb3006','BSC_RPTC003','RPT','Perspective title','Perspectives',NULL,NULL,NULL,NULL,'admin','2014-12-07 08:58:52',NULL,NULL),('8a2a8d70-99f8-42bd-a531-a9e4350fa753','MSG_UOS0004','MSG','Please try again!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('8c9f183a-d843-4686-806a-706bb45c5e2f','BSC_KPIDT01','KPI','Department','1',NULL,NULL,NULL,NULL,'admin','2014-11-21 15:17:28',NULL,NULL),('959f6d8a-cbae-4f52-950d-1c7de962ca36','MSG_DOS0004','MSG','update data fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('96c3a7cb-966a-406a-8365-0e79a5862bf2','MSG_DOS0010','MSG','Data to be used, and can not be deleted!',NULL,NULL,NULL,NULL,NULL,'admin','2012-11-12 12:42:16',NULL,NULL),('a5f7ee37-f33f-48a6-b448-92ccb8cdf96a','CNF_CONF003','CNF','first load javascript','BSC_PROG001D0007Q_TabShow();',NULL,NULL,NULL,NULL,'admin','2014-12-25 09:09:57',NULL,NULL),('a714afb2-e28c-4450-8f16-725ee2a3df22','MSG_UOS0003','MSG','object null!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30','admin','2015-04-27 21:16:19'),('b015d5c2-3278-419f-bcfb-43d1b3b80dfb','BSC_KPICT02','KPI','Compared with min','2',NULL,NULL,NULL,NULL,'admin','2014-11-21 15:15:19',NULL,NULL),('c2131e8e-c801-4367-b9a1-2b5e39473eae','BSC_RPTC001','RPT','background color','#444444',NULL,NULL,NULL,NULL,'admin','2014-12-07 08:58:10',NULL,NULL),('c5a5da5b-27c3-46cc-b5d3-ced1a168df0c','MSG_STD0003','MSG','Please select a file!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('c81bfed6-2814-4ada-82e2-f6d25e065eb0','MSG_STD0002','MSG','Upload a file type error!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('cca41808-0781-4d11-8303-93369471e901','MSG_DOS0002','MSG','data is exist!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('cec13700-4687-49c0-a89e-ae444388a318','MSG_STD0001','MSG','Login fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('d1322f32-12a3-4b1b-be98-e01acc6180bc','MSG_DOS0007','MSG','delete data success!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('d36aeec8-1b1b-4306-bbee-4d1aba53c1d8','BSC_RPTC006','RPT','Personal report class level','  Class A (500+)   Class B (300+)\n  Class C (200+)   Class D (200-)',NULL,NULL,NULL,NULL,'admin','2014-12-07 08:58:52',NULL,NULL),('d6a7cc5f-272b-44be-a7e5-8d307fed0342','BSC_FORMD02','BFM','Custom mode','C',NULL,NULL,NULL,NULL,'admin','2014-11-17 22:30:39',NULL,NULL),('dc0f2f81-8f04-47ca-b1fb-7810e4fe9d81','MSG_BSE0002','MSG','No sign-on system access denied!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('e271a8ab-af42-4334-8cfa-e39e2b8ec7b8','BSC_KPIDT03','KPI','Both','3',NULL,NULL,NULL,NULL,'admin','2014-11-21 15:18:38',NULL,NULL),('e6d6a389-d9ce-41ac-a3a8-ddbca23a98fa','BSC_RPTC005','RPT','KPI title','KPI',NULL,NULL,NULL,NULL,'admin','2014-12-07 08:58:52',NULL,NULL),('f0e2ee47-38df-490c-bbae-aba7b0b61dcc','MSG_BSE0001','MSG','No permission!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('f511ac0b-b40d-4f6c-92c2-1a03ebe32cca','KMD_MEASURE02','KMD','Week','2',NULL,NULL,NULL,NULL,'admin','2014-11-26 09:18:10',NULL,NULL),('f9107a97-af7e-45f5-a11a-2059de595687','BSC_FORMD01','BFM','Default mode','D',NULL,NULL,NULL,NULL,'admin','2014-11-17 22:30:39',NULL,NULL),('fc3e6505-e0eb-491d-9c79-8d9f18f47e38','MSG_BSE0003','MSG','error. dozer mapper id blank!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('fcbcd724-d0e3-49e8-ad1a-abd6c4a965ca','KMD_MEASURE01','KMD','Day','1',NULL,NULL,NULL,NULL,'admin','2014-11-26 09:17:35',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ctx_bean`
--

DROP TABLE IF EXISTS `tb_sys_ctx_bean`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ctx_bean` (
  `OID` char(36) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `CLASS_NAME` varchar(255) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`SYSTEM`,`CLASS_NAME`,`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ctx_bean`
--

LOCK TABLES `tb_sys_ctx_bean` WRITE;
/*!40000 ALTER TABLE `tb_sys_ctx_bean` DISABLE KEYS */;
INSERT INTO `tb_sys_ctx_bean` VALUES ('0de6977f-8638-492f-a797-46e69ec7dc9e','CORE','com.netsteadfast.greenstep.support.CleanTempUploadForContextInitAndDestroy','DESTROY','clean uplaod type is temp data!','admin','2014-11-03 10:49:59',NULL,NULL),('142c4cdb-35b4-4b54-a57d-1f3fecb63233','BSC','com.netsteadfast.greenstep.support.ConfigureAppSiteHostForContextInitialized','INITIALIZE','modify app-site\'s host-address! only first run.','admin','2015-01-23 09:36:13',NULL,NULL),('2888cff0-2886-4788-9e1f-fa8c6c7d5a6f','QCHARTS','com.netsteadfast.greenstep.support.ConfigureAppSiteHostForContextInitialized','INITIALIZE','modify app-site\'s host-address! only first run.','admin','2015-01-23 09:36:25',NULL,NULL),('5efe46f0-6b5c-4aa6-a57f-701a28af5377','CORE','com.netsteadfast.greenstep.support.CleanTempUploadForContextInitAndDestroy','INITIALIZE','clean uplaod type is temp data!','admin','2014-11-03 10:49:46',NULL,NULL),('6b00712f-0271-4f93-8bb4-61a6e4d0222b','BSC','com.netsteadfast.greenstep.bsc.support.CleanJasperReportTempDataForContextInitAndDestroy','INITIALIZE','clean SWOT jasperreport data!','admin','2014-12-21 14:15:03',NULL,NULL),('a51af85a-aff9-4b38-b944-5ba421ffa0bf','CORE','com.netsteadfast.greenstep.support.ConfigureAppSiteHostForContextInitialized','INITIALIZE','modify app-site\'s host-address! only first run.','admin','2015-01-23 09:36:00',NULL,NULL),('a757a8e3-f38e-4b97-80f1-6393b4bf27c9','BSC','com.netsteadfast.greenstep.bsc.support.CleanJasperReportTempDataForContextInitAndDestroy','DESTROY','clean SWOT jasperreport data!','admin','2014-12-21 14:15:22',NULL,NULL),('e18f4e78-9f4f-41dd-a9dd-7d721a0a0c3b','CORE','com.netsteadfast.greenstep.support.DeployJreportForContextInitialized','INITIALIZE','for deploy jasperreport to report-dir!','admin','2014-11-02 17:03:48',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_ctx_bean` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_event_log`
--

DROP TABLE IF EXISTS `tb_sys_event_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_event_log` (
  `OID` char(36) NOT NULL,
  `USER` varchar(24) NOT NULL,
  `SYS_ID` varchar(10) NOT NULL,
  `EXECUTE_EVENT` varchar(255) NOT NULL,
  `IS_PERMIT` varchar(1) NOT NULL DEFAULT 'N',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_event_log`
--

LOCK TABLES `tb_sys_event_log` WRITE;
/*!40000 ALTER TABLE `tb_sys_event_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_event_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_expression`
--

DROP TABLE IF EXISTS `tb_sys_expression`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_expression` (
  `OID` char(36) NOT NULL,
  `EXPR_ID` varchar(20) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `CONTENT` varchar(8000) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`EXPR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_expression`
--

LOCK TABLES `tb_sys_expression` WRITE;
/*!40000 ALTER TABLE `tb_sys_expression` DISABLE KEYS */;
INSERT INTO `tb_sys_expression` VALUES ('07ddaf7e-2acf-492f-a522-39fe686d0e59','BSC_RPT_EXPR0003','GROOVY','KPI Report(html) UP/DOWN icon status!','icon = \"\";\nrange = 0.0f;\nif (score==null || kpi==null || kpi.compareType==null) {\n	icon = \" \";\n}\nif (kpi.management == \"3\") {\n	if (kpi.compareType == \"1\" ) {\n		if ( kpi.quasiRange != 0 ) {\n			range = kpi.target * ( kpi.quasiRange / 100.0 );\n		}\n	}	\n	if (kpi.compareType == \"2\" ) {\n		if ( kpi.quasiRange != 0 ) {\n			range = kpi.min * ( kpi.quasiRange / 100.0 );\n		}\n	}\n}\nif ( range != 0.0f ) {\n	if (kpi.compareType == \"1\" ) {\n		if ( score >= kpi.target-range && score <= kpi.target+range ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n	if (kpi.compareType == \"2\" ) {\n		if ( score >= kpi.min-range && score <= kpi.min+range ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n} else {\n	if (kpi.compareType == \"1\" ) {\n		if ( score >= kpi.target) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n	if (kpi.compareType == \"2\" ) {\n		if ( score >= kpi.min ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n}\n','provide KPI Report UP/DOWN icon status!','admin','2014-12-04 20:36:43','admin','2014-12-22 19:14:17'),('2305b1d3-34be-47f2-b0a2-61c91dc54064','BSC_MD_EXPR0001','GROOVY','Measure datas update/input settings for (Christopher Hayden)!','// bsc.service.logic.MeasureDataLogicService\n// saveOrUpdate\n// frequency args index 2\n// measureDatas args index 6\n// kpiOid args index 0\n// organizationId args index 4\n// employeeId args index 5\n\n/*\n * The require for Christopher Hayden\n */\nif ( measureDatas==null || !(measureDatas instanceof java.util.ArrayList) ) {\n	return;\n}\n\ndef denyUsers = [ \"need-deny-user-id\" ]; // the is need deny users\n\ndef nowYear = com.netsteadfast.greenstep.util.SimpleUtils.getStrYMD( com.netsteadfast.greenstep.util.SimpleUtils.IS_YEAR ); // not modify me\ndef nowMonth = com.netsteadfast.greenstep.util.SimpleUtils.getStrYMD( com.netsteadfast.greenstep.util.SimpleUtils.IS_MONTH ); // not modify me\ndef nowUserId = ((java.lang.String)org.apache.shiro.SecurityUtils.getSubject().getPrincipal());\ncom.netsteadfast.greenstep.vo.KpiVO kpi = com.netsteadfast.greenstep.bsc.util.MeasureDataCalendarUtils.findKpi(kpiOid);\njava.util.List<com.netsteadfast.greenstep.vo.MeasureDataVO> newMeasureDatas = new java.util.ArrayList<com.netsteadfast.greenstep.vo.MeasureDataVO>();\n\nfor ( com.netsteadfast.greenstep.vo.MeasureDataVO measureData : measureDatas ) {	\n	\n	/*\n	 * frequency 1 is Day\n	 * frequency 2 is Week\n	 * frequency 3 is Month\n	 * frequency 4 is QUARTER\n	 * frequency 5 is HALF OF YEAR\n	 * frequency 6 is Year\n	 */\n	// At the end of March, I would not want anyone to be able to update KPIs for January and February. \n	if ( \"3\".equals( frequency ) ) { // the frequency 3 is of month\n		//System.out.println( \"measureData.date=\" + measureData.date);\n		//System.out.println( \"nowYear+nowMonth=\" + nowYear+nowMonth);	\n		if ( !measureData.date.startsWith(nowYear+nowMonth) ) {\n			//throw new com.netsteadfast.greenstep.base.exception.ServiceException( \"Only can update current month measures data. please contact system administrator!\" );\n			com.netsteadfast.greenstep.vo.MeasureDataVO beforeMeasureData = \n				com.netsteadfast.greenstep.bsc.util.MeasureDataCalendarUtils.findMeasureData(\n					kpi.id,\n					measureData.date,\n					frequency,\n					organizationId,\n					employeeId\n				);\n			if ( beforeMeasureData !=null ) {\n				measureData.setActual( beforeMeasureData.actual );\n				measureData.setTarget( beforeMeasureData.target );\n				newMeasureDatas.add( measureData );				\n			} 	\n		} else {\n			newMeasureDatas.add( measureData );\n		}\n	}	\n\n	// Also another scenario maybe how to deny updates of specific KPIs(measured data) per month per user.\n	// an example ( deny admin update March data )\n	for (String denyUser : denyUsers) {\n		if ( \"3\".equals( frequency ) && denyUser.equals( nowUserId ) /* && denyUser.equals( measureData.empId ) */ ) { // the frequency 3 is of month\n			if ( measureData.date.startsWith(nowYear+nowMonth) ) { // cannot update current month\n				throw new com.netsteadfast.greenstep.base.exception.ServiceException( denyUser + \" you cannot update measures data. please contact system administrator!\" );\n			}\n		}\n	}\n	\n}\n\nmeasureDatas.clear();\nmeasureDatas.addAll( newMeasureDatas );\n','Christopher Hayden\'s require','admin','2015-04-14 09:10:15','admin','2015-04-14 12:22:24'),('37e304bc-b3e7-4ade-ba7d-924de187ecc7','BSC_KPI_EXPR0003','GROOVY','KPI create/update check Max target min','import com.netsteadfast.greenstep.vo.KpiVO;\nimport com.netsteadfast.greenstep.base.exception.ControllerException;\n\nif (kpi.getMax() <= kpi.getTarget()) {\n	fieldsId.add(\"max\");\n	fieldsId.add(\"target\");\n	throw new ControllerException( msg1 );			\n}\nif (kpi.getTarget() <= kpi.getMin()) {\n	fieldsId.add(\"target\");\n	fieldsId.add(\"min\");			\n	throw new ControllerException( msg2 );\n}\n','check KPI max/target/min value for KpiSaveOrUpdateAction use.','admin','2015-08-06 09:50:28',NULL,NULL),('43f012d3-988b-49c5-82af-441aa7be3cd0','BSC_RPT_EXPR0006','GROOVY','KPI Report(EXCEL/PDF) UP/DOWN icon status for content!','icon = \"\";\nrange = 0.0f;\nif (score==null || target==null || min==null) {\n	icon = \" \";\n}\nif (!\"KPI\".equals(mode) ) { // for Perspectives, Objectives\n  \n	if (score>=target) {\n		icon = \"good.png\";\n	} else if (score<target && score>min) {\n		icon = \"nogood.png\";\n	} else {\n		icon = \"poor.png\";\n	}  \n  \n} else { // for KPIs\n\n	if (management == \"3\") {\n		if (compareType == \"1\" ) {\n			if ( quasiRange != 0 ) {\n				range = target * ( quasiRange / 100.0 );\n			}\n		}	\n		if (compareType == \"2\" ) {\n			if ( quasiRange != 0 ) {\n				range = min * ( quasiRange / 100.0 );\n			}\n		}\n	}\n\n	if ( range != 0.0f ) {\n		if (compareType == \"1\" ) {\n			if ( score >= target-range && score <= target+range ) {\n				icon = \"good.png\";\n			} else {\n				if ( score >= target-(range*2) && score <= target+(range*2) ) {\n					icon = \"nogood.png\";\n				} else {\n					icon = \"poor.png\";\n				}\n			}\n		}\n		if (compareType == \"2\" ) {\n			if ( score >= min-range && score <= min+range ) {\n				icon = \"good.png\";\n			} else {\n				if ( score >= min-(range*2) && score <= min+(range*2) ) {\n					icon = \"nogood.png\";\n				} else {\n					icon = \"poor.png\";\n				}\n			}\n		}\n	} else {\n		if (compareType == \"1\" ) {\n			if ( score >= target) {\n				icon = \"good.png\";\n			} else if (score<target && score>min) {\n				icon = \"nogood.png\";\n			} else {\n				icon = \"poor.png\";\n			}\n		}\n		if (compareType == \"2\" ) {\n			float p = min;\n			if (p!=0.0f) {\n				p = p /2.0f;\n			}\n			if ( score >= min ) {\n				icon = \"good.png\";\n			} else if ( score<min && score>p) {\n				icon = \"nogood.png\";\n			} else {\n				icon = \"poor.png\";\n			}\n		}\n	}\n\n}\n\nif ( icon.endsWith(\"png\") ) {\n	icon = \"META-INF/resource/\" + icon;\n}\n','UP/DOWN icon status for Perspectives/Objectives/KPIs!','admin','2015-07-24 22:05:59','admin','2015-07-25 14:40:44'),('5fcfa058-1148-40c1-ad1a-27d0a4ea01b5','BSC_RPT_EXPR0005','GROOVY','KPI Report(html) UP/DOWN icon status for content!','icon = \"\";\nrange = 0.0f;\nif (score==null || target==null || min==null) {\n	icon = \" \";\n}\nif (!\"KPI\".equals(mode) ) { // for Perspectives, Objectives\n  \n	if (score>=target) {\n		icon = \"good.png\";\n	} else if (score<target && score>min) {\n		icon = \"nogood.png\";\n	} else {\n		icon = \"poor.png\";\n	}  \n  \n} else { // for KPIs\n\n	if (management == \"3\") {\n		if (compareType == \"1\" ) {\n			if ( quasiRange != 0 ) {\n				range = target * ( quasiRange / 100.0 );\n			}\n		}	\n		if (compareType == \"2\" ) {\n			if ( quasiRange != 0 ) {\n				range = min * ( quasiRange / 100.0 );\n			}\n		}\n	}\n\n	if ( range != 0.0f ) {\n		if (compareType == \"1\" ) {\n			if ( score >= target-range && score <= target+range ) {\n				icon = \"good.png\";\n			} else {\n				if ( score >= target-(range*2) && score <= target+(range*2) ) {\n					icon = \"nogood.png\";\n				} else {\n					icon = \"poor.png\";\n				}\n			}\n		}\n		if (compareType == \"2\" ) {\n			if ( score >= min-range && score <= min+range ) {\n				icon = \"good.png\";\n			} else {\n				if ( score >= min-(range*2) && score <= min+(range*2) ) {\n					icon = \"nogood.png\";\n				} else {\n					icon = \"poor.png\";\n				}\n			}\n		}\n	} else {\n		if (compareType == \"1\" ) {\n			if ( score >= target) {\n				icon = \"good.png\";\n			} else if (score<target && score>min) {\n				icon = \"nogood.png\";\n			} else {\n				icon = \"poor.png\";\n			}\n		}\n		if (compareType == \"2\" ) {\n			float p = min;\n			if (p!=0.0f) {\n				p = p /2.0f;\n			}\n			if ( score >= min ) {\n				icon = \"good.png\";\n			} else if ( score<min && score>p) {\n				icon = \"nogood.png\";\n			} else {\n				icon = \"poor.png\";\n			}\n		}\n	}\n\n}\n','UP/DOWN icon status for Perspectives/Objectives/KPIs!','admin','2015-07-24 22:03:25','admin','2015-07-25 14:40:36'),('771301f0-6b3d-46c2-8323-28fe8a34786f','BSC_RPT_EXPR0004','GROOVY','KPI Report(EXCEL/PDF) UP/DOWN icon status!','icon = \"\";\nrange = 0.0f;\nif (score==null || kpi==null || kpi.compareType==null) {\n	icon = \" \";\n}\nif (kpi.management == \"3\") {\n	if (kpi.compareType == \"1\" ) {\n		if ( kpi.quasiRange != 0 ) {\n			range = kpi.target * ( kpi.quasiRange / 100.0 );\n		}\n	}	\n	if (kpi.compareType == \"2\" ) {\n		if ( kpi.quasiRange != 0 ) {\n			range = kpi.min * ( kpi.quasiRange / 100.0 );\n		}\n	}\n}\nif ( range != 0.0f ) {\n	if (kpi.compareType == \"1\" ) {\n		if ( score >= kpi.target-range && score <= kpi.target+range ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n	if (kpi.compareType == \"2\" ) {\n		if ( score >= kpi.min-range && score <= kpi.min+range ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n} else {\n	if (kpi.compareType == \"1\" ) {\n		if ( score >= kpi.target) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n	if (kpi.compareType == \"2\" ) {\n		if ( score >= kpi.min ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n}\nif ( icon.endsWith(\"png\") ) {\n	icon = \"META-INF/resource/\" + icon;\n}\n','provide KPI Report UP/DOWN icon status!','admin','2014-12-10 19:54:03','admin','2014-12-22 19:14:38'),('79cf3dcb-1346-46c6-9ed5-bd17debf9502','BSC_KPI_EXPR0002','BSH','KPI create success message plus!','if (resultObj==null || !(resultObj instanceof com.netsteadfast.greenstep.base.model.DefaultResult ) ) {\n	return;\n}\nif (resultObj.getValue()==null || resultObj.getSystemMessage().getValue()==null ) {\n	return;\n}\nresultObj.setSystemMessage(\n	new com.netsteadfast.greenstep.base.model.SystemMessage(\n		resultObj.getSystemMessage().getValue() + \n		\"<BR/>Please to maintain measure data of the KPI.<BR/>\")\n);\n','message plus!','admin','2014-11-23 14:27:08','admin','2014-11-24 09:16:21'),('dc17c074-916b-4f62-a50a-9ab8e1a919c8','BSC_KPI_EXPR0001','BSH','KPI\'s weight check for create/update!','if ( kpi==null || !(kpi instanceof com.netsteadfast.greenstep.vo.KpiVO ) ) {\n	return;\n}\nif (kpi.getWeight()==null || kpi.getWeight().longValue() < 1 ) {\n	kpi.setWeight( new java.math.BigDecimal(\"1\") );\n}\n','KPI\'s weight check for create or update!','admin','2014-11-23 14:26:14','admin','2014-11-23 16:48:41');
/*!40000 ALTER TABLE `tb_sys_expression` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_form`
--

DROP TABLE IF EXISTS `tb_sys_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_form` (
  `OID` char(36) NOT NULL,
  `FORM_ID` varchar(50) NOT NULL,
  `TEMPLATE_ID` varchar(50) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`FORM_ID`),
  KEY `IDX_1` (`TEMPLATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_form`
--

LOCK TABLES `tb_sys_form` WRITE;
/*!40000 ALTER TABLE `tb_sys_form` DISABLE KEYS */;
INSERT INTO `tb_sys_form` VALUES ('4ba2a09e-de4b-4c31-a078-0b6014b38355','FORM001','TPL001','Basic message form','for CORE_PROG001D0015Q (15 - Basic message)','admin','2015-04-26 12:35:02','admin','2015-04-27 14:19:02'),('784540df-25dd-4c56-89e0-b2ab03d1daeb','FORM002','TPL002','Basic message form for edit','for CORE_PROG001D0015E (Basic message management)','admin','2015-04-27 20:25:54',NULL,NULL),('ad85ebd7-5b8d-4c9f-b50d-bf4a1b4bf3e7','FORM_CORE_4D_002_01','TPL_CORE_4D_002_01','Login log form','for CORE_PROG004D0002Q','admin','2015-04-28 10:57:15','admin','2015-04-28 10:57:30'),('cfac915c-83a5-4a9e-8612-0a37f3067b48','FORM_CORE_4D_001_01','TPL_CORE_4D_001_01','Event log form','for CORE_PROG004D0001Q','admin','2015-04-28 10:00:45',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_form_method`
--

DROP TABLE IF EXISTS `tb_sys_form_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_form_method` (
  `OID` char(36) NOT NULL,
  `FORM_ID` varchar(50) NOT NULL,
  `NAME` varchar(25) NOT NULL,
  `RESULT_TYPE` varchar(25) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `EXPRESSION` blob NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`FORM_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_form_method`
--

LOCK TABLES `tb_sys_form_method` WRITE;
/*!40000 ALTER TABLE `tb_sys_form_method` DISABLE KEYS */;
INSERT INTO `tb_sys_form_method` VALUES ('0190b6c5-d316-4b06-b7f7-a9c18b8c8e4f','FORM_CORE_4D_001_01','init','default','GROOVY',0x2F2A206E6F7468696E67202A2F,'','admin','2015-04-28 10:01:56',NULL,NULL),('0ce25d69-70f3-4fa5-8c1d-58e430cb6ca1','FORM_CORE_4D_001_01','deleteAll','json','GROOVY',0x64617461732E70757428226A736F6E53756363657373222C20224E22293B0A64617461732E707574280A09226A736F6E4D657373616765222C200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E44454C4554455F4641494C2920293B0A6F72672E737072696E676672616D65776F726B2E6A6462632E636F72652E6E616D6564706172616D2E4E616D6564506172616D657465724A64626354656D706C617465206A64626354656D706C617465203D200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E417070436F6E746578742E6765744265616E28226E616D6564506172616D657465724A64626354656D706C61746522293B0A6A6176612E7574696C2E4D61703C537472696E672C204F626A6563743E20706172616D4D6170203D206E6577206A6176612E7574696C2E486173684D61703C537472696E672C204F626A6563743E28293B0A6A64626354656D706C6174652E757064617465282244454C4554452066726F6D2074625F7379735F6576656E745F6C6F6720222C20706172616D4D6170293B0A64617461732E70757428226A736F6E53756363657373222C20225922293B0A64617461732E707574280A09226A736F6E4D657373616765222C200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E44454C4554455F535543434553532920293B0A,'','admin','2015-04-28 10:24:45',NULL,NULL),('0dc99fa8-742c-4ecb-ba2e-7864276adab5','FORM001','init','default','GROOVY',0x2F2A206E6F7468696E67202A2F,'init event','admin','2015-04-26 18:01:47','admin','2015-04-27 14:08:38'),('385ca997-53f5-40f0-a09b-258482fac92e','FORM_CORE_4D_002_01','init','default','GROOVY',0x2F2A206E6F7468696E67202A2F,'','admin','2015-04-28 11:01:05',NULL,NULL),('8c44e465-c683-4fe3-a040-40412a68381b','FORM002','update','json','GROOVY',0x64617461732E70757428226A736F6E53756363657373222C20224E22293B0A69662028206F72672E6170616368652E636F6D6D6F6E732E6C616E67332E537472696E675574696C732E6973426C616E6B282028537472696E67296669656C64732E67657428226E616D65222920292029207B0A096669656C647349642E61646428226E616D6522293B0A097468726F77206E657720636F6D2E6E65747374656164666173742E677265656E737465702E626173652E657863657074696F6E2E436F6E74726F6C6C6572457863657074696F6E28224E616D65206973207265717569726564213C42522F3E22293B0A7D0A636F6D2E6E65747374656164666173742E677265656E737465702E766F2E537973436F6465564F20737973436F6465203D200A096E657720636F6D2E6E65747374656164666173742E677265656E737465702E766F2E537973436F6465564F28293B0A737973436F64652E7365744F6964282028537472696E67296669656C64732E67657428226F6964222920293B0A636F6D2E6E65747374656164666173742E677265656E737465702E736572766963652E49537973436F6465536572766963653C636F6D2E6E65747374656164666173742E677265656E737465702E766F2E537973436F6465564F2C20636F6D2E6E65747374656164666173742E677265656E737465702E706F2E68626D2E5462537973436F64652C20537472696E673E20737973436F646553657276696365203D200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E417070436F6E746578742E6765744265616E2822636F72652E736572766963652E537973436F64655365727669636522293B0A636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E44656661756C74526573756C7420726573756C74203D200A09737973436F6465536572766963652E66696E644F626A65637442794F69642820737973436F646520293B0A6966202820726573756C742E67657456616C75652829203D3D206E756C6C2029207B0A097468726F77206E657720636F6D2E6E65747374656164666173742E677265656E737465702E626173652E657863657074696F6E2E53657276696365457863657074696F6E28726573756C742E67657453797374656D4D65737361676528292E67657456616C75652829293B0A7D0A737973436F6465203D20726573756C742E67657456616C756528293B0A737973436F64652E7365744E616D65282028537472696E67296669656C64732E67657428226E616D65222920293B0A636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E44656661756C74526573756C742075526573756C74203D200A09737973436F6465536572766963652E7570646174654F626A6563742820737973436F646520293B0A64617461732E70757428226A736F6E4D657373616765222C2075526573756C742E67657453797374656D4D65737361676528292E67657456616C75652829293B0A696620282075526573756C742E67657456616C75652829213D6E756C6C2029207B0A0964617461732E70757428226A736F6E53756363657373222C20225922293B0A7D0A,'','admin','2015-04-27 21:02:50','admin','2015-04-27 21:16:07'),('9d4dd6f2-d94c-4349-bb03-b6e95de95436','FORM_CORE_4D_001_01','delete','json','GROOVY',0x64617461732E70757428226A736F6E53756363657373222C20224E22293B0A64617461732E707574280A09226A736F6E4D657373616765222C200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E44454C4554455F4641494C2920293B0A537472696E67206F6964203D2028537472696E67296669656C64732E67657428226F696422293B0A69662028206F72672E6170616368652E636F6D6D6F6E732E6C616E67332E537472696E675574696C732E6973426C616E6B28206F696420292029207B0A097468726F77206E657720636F6D2E6E65747374656164666173742E677265656E737465702E626173652E657863657074696F6E2E436F6E74726F6C6C6572457863657074696F6E28226572726F72213C42522F3E22293B0A7D0A6F72672E737072696E676672616D65776F726B2E6A6462632E636F72652E6E616D6564706172616D2E4E616D6564506172616D657465724A64626354656D706C617465206A64626354656D706C617465203D200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E417070436F6E746578742E6765744265616E28226E616D6564506172616D657465724A64626354656D706C61746522293B0A6A6176612E7574696C2E4D61703C537472696E672C204F626A6563743E20706172616D4D6170203D206E6577206A6176612E7574696C2E486173684D61703C537472696E672C204F626A6563743E28293B0A706172616D4D61702E70757428226F6964222C206F6964293B0A6A64626354656D706C6174652E757064617465282244454C4554452066726F6D2074625F7379735F6576656E745F6C6F67205748455245204F4944203D203A6F696420222C20706172616D4D6170293B0A64617461732E70757428226A736F6E53756363657373222C20225922293B0A64617461732E707574280A09226A736F6E4D657373616765222C200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E44454C4554455F535543434553532920293B0A,'','admin','2015-04-28 10:21:30',NULL,NULL),('b67ef618-4d61-4072-b58c-7a525da24c62','FORM_CORE_4D_002_01','deleteAll','json','GROOVY',0x64617461732E70757428226A736F6E53756363657373222C20224E22293B0A64617461732E707574280A09226A736F6E4D657373616765222C200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E44454C4554455F4641494C2920293B0A6F72672E737072696E676672616D65776F726B2E6A6462632E636F72652E6E616D6564706172616D2E4E616D6564506172616D657465724A64626354656D706C617465206A64626354656D706C617465203D200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E417070436F6E746578742E6765744265616E28226E616D6564506172616D657465724A64626354656D706C61746522293B0A6A6176612E7574696C2E4D61703C537472696E672C204F626A6563743E20706172616D4D6170203D206E6577206A6176612E7574696C2E486173684D61703C537472696E672C204F626A6563743E28293B0A6A64626354656D706C6174652E757064617465282244454C4554452066726F6D2074625F7379735F6C6F67696E5F6C6F6720222C20706172616D4D6170293B0A64617461732E70757428226A736F6E53756363657373222C20225922293B0A64617461732E707574280A09226A736F6E4D657373616765222C200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E44454C4554455F535543434553532920293B0A,'','admin','2015-04-28 11:04:03',NULL,NULL),('dc74d358-5fe2-4280-9bf0-6ac9d1981f9f','FORM_CORE_4D_002_01','query','json','GROOVY',0x2F2F64617461732E70757428226A736F6E53756363657373222C20224E22293B0A64617461732E70757428226A736F6E53756363657373222C20225922293B202F2F20E58FAAE8A681E69FA5E8A9A2E6B292E795B6E68E89E983BDE7AE972073756363657373203D202259220A6F72672E737072696E676672616D65776F726B2E6A6462632E636F72652E6E616D6564706172616D2E4E616D6564506172616D657465724A64626354656D706C617465206A64626354656D706C617465203D200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E417070436F6E746578742E6765744265616E28226E616D6564506172616D657465724A64626354656D706C61746522293B0A6A6176612E7574696C2E4D61703C537472696E672C204F626A6563743E20706172616D4D6170203D206E6577206A6176612E7574696C2E486173684D61703C537472696E672C204F626A6563743E28293B0A537472696E672073716C203D20222046524F4D2074625F7379735F6C6F67696E5F6C6F6720574845524520313D3120223B0A6966202820216F72672E6170616368652E636F6D6D6F6E732E6C616E67332E537472696E675574696C732E6973426C616E6B282028537472696E672973656172636856616C75652E676574506172616D6574657228292E676574282275736572222920292029207B0A0973716C202B3D202220616E642055534552203D203A7573657220223B0A09706172616D4D61702E707574282275736572222C202028537472696E672973656172636856616C75652E676574506172616D6574657228292E676574282275736572222920293B0A7D0A696E742073656C656374203D20496E74656765722E7061727365496E742820706167654F662E67657453656C65637428292029202D313B0A73656C656374203D2073656C656374202A20496E74656765722E7061727365496E742820706167654F662E67657453686F77526F77282920293B0A4C6F6E6720636F756E7453697A65203D206A64626354656D706C6174652E7175657279466F724F626A656374282253454C45435420636F756E74282A292022202B2073716C2C20706172616D4D61702C204C6F6E672E636C617373293B0A6A6176612E7574696C2E4C6973743C537472696E672C204F626A6563743E207365617263684C697374203D206A64626354656D706C6174652E7175657279466F724C697374280A2020092253454C454354204F49442C20555345522C2043444154452022202B2073716C202B2022206F7264657220627920434441544520646573632022202B2022206C696D69742022202B2073656C656374202B20222C2022202B20706167654F662E67657453686F77526F7728292C200A202009706172616D4D6170293B0A706167654F662E736574436F756E7453697A6528537472696E672E76616C75654F6628636F756E7453697A6529293B0A706167654F662E746F43616C63756C61746553697A6528293B0A666F722028204D61703C537472696E672C204F626A6563743E20646174614D6170203A207365617263684C6973742029207B0A096974656D732E6164642820646174614D617020293B0A7D0A6966202820636F756E7453697A65203C20312029207B0A0964617461732E707574280A0909226A736F6E4D657373616765222C200A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A090909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E5345415243485F4E4F5F444154412920293B0A7D20656C7365207B0A0964617461732E70757428226A736F6E53756363657373222C20225922293B0A7D0A,'','admin','2015-04-28 10:58:50',NULL,NULL),('e4fa1151-c082-4b89-ab55-ff687c63fc08','FORM_CORE_4D_001_01','query','json','GROOVY',0x2F2F64617461732E70757428226A736F6E53756363657373222C20224E22293B0A64617461732E70757428226A736F6E53756363657373222C20225922293B202F2F20E58FAAE8A681E69FA5E8A9A2E6B292E795B6E68E89E983BDE7AE972073756363657373203D202259220A6F72672E737072696E676672616D65776F726B2E6A6462632E636F72652E6E616D6564706172616D2E4E616D6564506172616D657465724A64626354656D706C617465206A64626354656D706C617465203D200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E417070436F6E746578742E6765744265616E28226E616D6564506172616D657465724A64626354656D706C61746522293B0A6A6176612E7574696C2E4D61703C537472696E672C204F626A6563743E20706172616D4D6170203D206E6577206A6176612E7574696C2E486173684D61703C537472696E672C204F626A6563743E28293B0A537472696E672073716C203D20222046524F4D2074625F7379735F6576656E745F6C6F6720574845524520313D3120223B0A6966202820216F72672E6170616368652E636F6D6D6F6E732E6C616E67332E537472696E675574696C732E6973426C616E6B282028537472696E672973656172636856616C75652E676574506172616D6574657228292E676574282275736572222920292029207B0A0973716C202B3D202220616E642055534552203D203A7573657220223B0A09706172616D4D61702E707574282275736572222C202028537472696E672973656172636856616C75652E676574506172616D6574657228292E676574282275736572222920293B0A7D0A6966202820216F72672E6170616368652E636F6D6D6F6E732E6C616E67332E537472696E675574696C732E6973426C616E6B282073656172636856616C75652E676574506172616D6574657228292E67657428227379734964222920292029207B0A0973716C202B3D202220616E64205359535F4944203D203A737973496420223B0A09706172616D4D61702E70757428227379734964222C202028537472696E672973656172636856616C75652E676574506172616D6574657228292E67657428227379734964222920293B0A7D0A696E742073656C656374203D20496E74656765722E7061727365496E742820706167654F662E67657453656C65637428292029202D313B0A73656C656374203D2073656C656374202A20496E74656765722E7061727365496E742820706167654F662E67657453686F77526F77282920293B0A4C6F6E6720636F756E7453697A65203D206A64626354656D706C6174652E7175657279466F724F626A656374282253454C45435420636F756E74282A292022202B2073716C2C20706172616D4D61702C204C6F6E672E636C617373293B0A6A6176612E7574696C2E4C6973743C537472696E672C204F626A6563743E207365617263684C697374203D206A64626354656D706C6174652E7175657279466F724C697374280A2020092253454C454354204F49442C205359535F49442C20555345522C20455845435554455F4556454E542C2049535F5045524D49542022202B2073716C202B2022206F7264657220627920434441544520646573632022202B2022206C696D69742022202B2073656C656374202B20222C2022202B20706167654F662E67657453686F77526F7728292C200A202009706172616D4D6170293B0A706167654F662E736574436F756E7453697A6528537472696E672E76616C75654F6628636F756E7453697A6529293B0A706167654F662E746F43616C63756C61746553697A6528293B0A666F722028204D61703C537472696E672C204F626A6563743E20646174614D6170203A207365617263684C6973742029207B0A096974656D732E6164642820646174614D617020293B0A7D0A6966202820636F756E7453697A65203C20312029207B0A0964617461732E707574280A0909226A736F6E4D657373616765222C200A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A090909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E5345415243485F4E4F5F444154412920293B0A7D20656C7365207B0A0964617461732E70757428226A736F6E53756363657373222C20225922293B0A7D0A,'','admin','2015-04-28 10:04:56','admin','2015-04-28 10:12:23'),('eb5c33d8-25a9-499c-92d4-d48fd270295e','FORM002','edit','default','GROOVY',0x636F6D2E6E65747374656164666173742E677265656E737465702E766F2E537973436F6465564F20737973436F6465203D200A096E657720636F6D2E6E65747374656164666173742E677265656E737465702E766F2E537973436F6465564F28293B0A737973436F64652E7365744F6964282028537472696E67296669656C64732E67657428226F6964222920293B0A636F6D2E6E65747374656164666173742E677265656E737465702E736572766963652E49537973436F6465536572766963653C636F6D2E6E65747374656164666173742E677265656E737465702E766F2E537973436F6465564F2C20636F6D2E6E65747374656164666173742E677265656E737465702E706F2E68626D2E5462537973436F64652C20537472696E673E20737973436F646553657276696365203D200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E417070436F6E746578742E6765744265616E2822636F72652E736572766963652E537973436F64655365727669636522293B0A636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E44656661756C74526573756C7420726573756C74203D200A09737973436F6465536572766963652E66696E644F626A65637442794F69642820737973436F646520293B0A6966202820726573756C742E67657456616C75652829203D3D206E756C6C2029207B0A097468726F77206E657720636F6D2E6E65747374656164666173742E677265656E737465702E626173652E657863657074696F6E2E53657276696365457863657074696F6E28726573756C742E67657453797374656D4D65737361676528292E67657456616C75652829293B0A7D0A737973436F6465203D20726573756C742E67657456616C756528293B0A64617461732E7075742822737973436F6465222C20737973436F6465293B0A,'','admin','2015-04-27 20:26:56','admin','2015-04-27 20:34:34'),('f4acff66-a0bd-4e8d-beb4-ecdf0baef2a4','FORM001','query','json','GROOVY',0x2F2F64617461732E70757428226A736F6E53756363657373222C20224E22293B0A64617461732E70757428226A736F6E53756363657373222C20225922293B202F2F20E58FAAE8A681E69FA5E8A9A2E6B292E795B6E68E89E983BDE7AE972073756363657373203D202259220A6F72672E737072696E676672616D65776F726B2E6A6462632E636F72652E6E616D6564706172616D2E4E616D6564506172616D657465724A64626354656D706C617465206A64626354656D706C617465203D200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E417070436F6E746578742E6765744265616E28226E616D6564506172616D657465724A64626354656D706C61746522293B0A6A6176612E7574696C2E4D61703C537472696E672C204F626A6563743E20706172616D4D6170203D206E6577206A6176612E7574696C2E486173684D61703C537472696E672C204F626A6563743E28293B0A537472696E672073716C203D20222046524F4D2074625F7379735F636F646520574845524520545950453D274D53472720223B0A6966202820216F72672E6170616368652E636F6D6D6F6E732E6C616E67332E537472696E675574696C732E6973426C616E6B282028537472696E672973656172636856616C75652E676574506172616D6574657228292E6765742822636F6465222920292029207B0A0973716C202B3D202220616E6420434F4445203D203A636F646520223B0A09706172616D4D61702E7075742822636F6465222C202028537472696E672973656172636856616C75652E676574506172616D6574657228292E6765742822636F6465222920293B0A7D0A6966202820216F72672E6170616368652E636F6D6D6F6E732E6C616E67332E537472696E675574696C732E6973426C616E6B282073656172636856616C75652E676574506172616D6574657228292E67657428226E616D65222920292029207B0A0973716C202B3D202220616E64204E414D45206C696B65203A6E616D6520223B0A09706172616D4D61702E70757428226E616D65222C20202225222B28537472696E672973656172636856616C75652E676574506172616D6574657228292E67657428226E616D6522292B22252220293B0A7D0A696E742073656C656374203D20496E74656765722E7061727365496E742820706167654F662E67657453656C65637428292029202D313B0A73656C656374203D2073656C656374202A20496E74656765722E7061727365496E742820706167654F662E67657453686F77526F77282920293B0A4C6F6E6720636F756E7453697A65203D206A64626354656D706C6174652E7175657279466F724F626A656374282253454C45435420636F756E74282A292022202B2073716C2C20706172616D4D61702C204C6F6E672E636C617373293B0A6A6176612E7574696C2E4C6973743C537472696E672C204F626A6563743E207365617263684C697374203D206A64626354656D706C6174652E7175657279466F724C697374280A2020092253454C454354204F49442C20434F44452C204E414D452022202B2073716C202B2022206F7264657220627920434F4445206173632022202B2022206C696D69742022202B2073656C656374202B20222C2022202B20706167654F662E67657453686F77526F7728292C200A202009706172616D4D6170293B0A706167654F662E736574436F756E7453697A6528537472696E672E76616C75654F6628636F756E7453697A6529293B0A706167654F662E746F43616C63756C61746553697A6528293B0A666F722028204D61703C537472696E672C204F626A6563743E20646174614D6170203A207365617263684C6973742029207B0A096974656D732E6164642820646174614D617020293B0A7D0A6966202820636F756E7453697A65203C20312029207B0A0964617461732E707574280A0909226A736F6E4D657373616765222C200A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A090909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E5345415243485F4E4F5F444154412920293B0A7D20656C7365207B0A0964617461732E70757428226A736F6E53756363657373222C20225922293B0A7D0A,'for CORE_PROG001D0015Q query result!','admin','2015-04-27 14:44:47','admin','2015-04-27 21:23:25'),('f7fccc4b-f280-47c6-bcb7-cc7977be8868','FORM_CORE_4D_002_01','delete','json','GROOVY',0x64617461732E70757428226A736F6E53756363657373222C20224E22293B0A64617461732E707574280A09226A736F6E4D657373616765222C200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E44454C4554455F4641494C2920293B0A537472696E67206F6964203D2028537472696E67296669656C64732E67657428226F696422293B0A69662028206F72672E6170616368652E636F6D6D6F6E732E6C616E67332E537472696E675574696C732E6973426C616E6B28206F696420292029207B0A097468726F77206E657720636F6D2E6E65747374656164666173742E677265656E737465702E626173652E657863657074696F6E2E436F6E74726F6C6C6572457863657074696F6E28226572726F72213C42522F3E22293B0A7D0A6F72672E737072696E676672616D65776F726B2E6A6462632E636F72652E6E616D6564706172616D2E4E616D6564506172616D657465724A64626354656D706C617465206A64626354656D706C617465203D200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E417070436F6E746578742E6765744265616E28226E616D6564506172616D657465724A64626354656D706C61746522293B0A6A6176612E7574696C2E4D61703C537472696E672C204F626A6563743E20706172616D4D6170203D206E6577206A6176612E7574696C2E486173684D61703C537472696E672C204F626A6563743E28293B0A706172616D4D61702E70757428226F6964222C206F6964293B0A6A64626354656D706C6174652E757064617465282244454C4554452066726F6D2074625F7379735F6C6F67696E5F6C6F67205748455245204F4944203D203A6F696420222C20706172616D4D6170293B0A64617461732E70757428226A736F6E53756363657373222C20225922293B0A64617461732E707574280A09226A736F6E4D657373616765222C200A09636F6D2E6E65747374656164666173742E677265656E737465702E626173652E5379734D6573736167655574696C2E676574280A0909636F6D2E6E65747374656164666173742E677265656E737465702E626173652E6D6F64656C2E477265656E537465705379734D7367436F6E7374616E74732E44454C4554455F535543434553532920293B0A,'','admin','2015-04-28 11:03:48',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_form_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_form_template`
--

DROP TABLE IF EXISTS `tb_sys_form_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_form_template` (
  `OID` char(36) NOT NULL,
  `TPL_ID` varchar(50) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `CONTENT` mediumblob NOT NULL,
  `FILE_NAME` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TPL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_form_template`
--

LOCK TABLES `tb_sys_form_template` WRITE;
/*!40000 ALTER TABLE `tb_sys_form_template` DISABLE KEYS */;
INSERT INTO `tb_sys_form_template` VALUES ('1d34ec03-8345-44c0-a0f7-8770d56ac3b3','TPL_CORE_4D_002_01','Login log query template',0x3C25402070616765206C616E67756167653D226A6176612220696D706F72743D226A6176612E7574696C2E2A222070616765456E636F64696E673D225554462D3822253E0A3C2540207461676C6962207572693D222F7374727574732D7461677322207072656669783D22732220253E0A3C2540207461676C6962207072656669783D226322207572693D22687474703A2F2F6A6176612E73756E2E636F6D2F6A73702F6A73746C2F636F72652220253E0A3C2540207461676C6962207072656669783D22677322207572693D22687474703A2F2F7777772E67737765622E6F72672F636F6E74726F6C6C65722F7461672220253E0A3C250A537472696E672070617468203D20726571756573742E676574436F6E746578745061746828293B0A537472696E67206261736550617468203D20726571756573742E676574536368656D6528292B223A2F2F222B726571756573742E6765745365727665724E616D6528292B223A222B726571756573742E676574536572766572506F727428292B706174682B222F223B0A0A253E0A3C21646F63747970652068746D6C3E0A3C68746D6C206974656D73636F70653D226974656D73636F706522206974656D747970653D22687474703A2F2F736368656D612E6F72672F57656250616765223E0A3C686561643E0A0A3C6D65746120687474702D65717569763D22436F6E74656E742D547970652220636F6E74656E743D22746578742F68746D6C3B20636861727365743D7574662D3822202F3E0A0A202020203C6261736520687265663D223C253D6261736550617468253E223E0A202020200A202020203C7469746C653E62616D626F6F434F52453C2F7469746C653E0A093C6D65746120687474702D65717569763D22707261676D612220636F6E74656E743D226E6F2D6361636865223E0A093C6D65746120687474702D65717569763D2263616368652D636F6E74726F6C2220636F6E74656E743D226E6F2D6361636865223E0A093C6D65746120687474702D65717569763D22657870697265732220636F6E74656E743D2230223E202020200A093C6D65746120687474702D65717569763D226B6579776F7264732220636F6E74656E743D2262616D626F6F434F5245223E0A093C6D65746120687474702D65717569763D226465736372697074696F6E2220636F6E74656E743D2262616D626F6F434F5245223E0A090A3C7374796C6520747970653D22746578742F637373223E0A0A3C2F7374796C653E0A0A3C73637269707420747970653D22746578742F6A617661736372697074223E0A0A66756E6374696F6E20434F52455F50524F473030344430303032515F477269644669656C645374727563747572652829207B0A0972657475726E205B0A0909097B206E616D653A202256696577266E6273703B2F266E6273703B45646974222C206669656C643A20224F4944222C20666F726D61747465723A20434F52455F50524F473030344430303032515F47726964427574746F6E436C69636B2C2077696474683A202231302522207D2C20200A0909097B206E616D653A202255736572222C206669656C643A202255534552222C2077696474683A202235352522207D2C0A0909097B206E616D653A202244617465222C206669656C643A20224344415445222C2077696474683A202233352522207D0A09095D3B090A7D0A0A66756E6374696F6E20434F52455F50524F473030344430303032515F47726964427574746F6E436C69636B286974656D4F696429207B0A097661722072643D22223B0A097264202B3D20223C696D67207372633D5C2222202B205F67657453797374656D49636F6E55726C282752454D4F56452729202B20225C2220626F726465723D5C22305C2220616C743D5C2264656C6574655C22206F6E636C69636B3D5C22434F52455F50524F473030344430303032515F636F6E6669726D44656C657465282722202B206974656D4F6964202B202227293B5C22202F3E223B0A0972657475726E2072643B090A7D0A0A66756E6374696F6E20434F52455F50524F473030344430303032515F636F6E6669726D44656C657465286F696429207B0A09636F6E6669726D4469616C6F67280A09090922247B70726F6772616D49647D5F6D616E6167656D656E744469616C6F674964303030222C200A0909095F6765744170706C69636174696F6E50726F6772616D4E616D65427949642827247B70726F6772616D49647D27292C200A0909092264656C6574653F20222C200A09090966756E6374696F6E287375636365737329207B0A0909090969662028217375636365737329207B0A090909090972657475726E3B0A090909097D090A0909090978687253656E64506172616D65746572280A09090909090927636F72652E636F6D6D6F6D4C6F6164466F726D2E616374696F6E3F70726F675F69643D434F52455F50524F4730303444303030325126666F726D5F69643D464F524D5F434F52455F34445F3030325F303126666F726D5F6D6574686F643D64656C657465272C200A0909090909097B20276669656C64732E6F696427203A206F6964207D2C200A090909090909276A736F6E272C200A0909090909095F6773636F72655F646F6A6F5F616A61785F74696D656F75742C0A0909090909095F6773636F72655F646F6A6F5F616A61785F73796E632C200A090909090909747275652C200A09090909090966756E6374696F6E286461746129207B0A09090909090909616C6572744469616C6F67285F6765744170706C69636174696F6E50726F6772616D4E616D65427949642827247B70726F6772616D49647D27292C20646174612E6D6573736167652C2066756E6374696F6E28297B7D2C20646174612E73756363657373293B0A090909090909096765745175657279477269645F247B70726F6772616D49647D5F6772696428293B0A0909090909097D2C200A09090909090966756E6374696F6E286572726F7229207B0A09090909090909616C657274286572726F72293B0A0909090909097D0A09090909293B090A0909097D2C200A0909092877696E646F772E6576656E74203F2077696E646F772E6576656E74203A206E756C6C29200A09293B090A7D0A0A66756E6374696F6E20434F52455F50524F473030344430303032515F636C6561722829207B0A0964696A69742E627949642827434F52455F50524F473030344430303032515F7573657227292E736574282776616C7565272C202727293B0A09636C65617251756572795F247B70726F6772616D49647D5F6772696428293B090A7D0A0A66756E6374696F6E20434F52455F50524F473030344430303032515F72656D6F7665416C6C2829207B0A09636F6E6669726D4469616C6F67280A09090922247B70726F6772616D49647D5F6D616E6167656D656E744469616C6F674964303030222C200A0909095F6765744170706C69636174696F6E50726F6772616D4E616D65427949642827247B70726F6772616D49647D27292C200A0909092264656C6574653F20222C200A09090966756E6374696F6E287375636365737329207B0A0909090969662028217375636365737329207B0A090909090972657475726E3B0A090909097D090A0909090978687253656E64506172616D65746572280A09090909090927636F72652E636F6D6D6F6D4C6F6164466F726D2E616374696F6E3F70726F675F69643D434F52455F50524F4730303444303030325126666F726D5F69643D464F524D5F434F52455F34445F3030325F303126666F726D5F6D6574686F643D64656C657465416C6C272C200A0909090909097B207D2C200A090909090909276A736F6E272C200A0909090909095F6773636F72655F646F6A6F5F616A61785F74696D656F75742C0A0909090909095F6773636F72655F646F6A6F5F616A61785F73796E632C200A090909090909747275652C200A09090909090966756E6374696F6E286461746129207B0A09090909090909616C6572744469616C6F67285F6765744170706C69636174696F6E50726F6772616D4E616D65427949642827247B70726F6772616D49647D27292C20646174612E6D6573736167652C2066756E6374696F6E28297B7D2C20646174612E73756363657373293B0A090909090909096765745175657279477269645F247B70726F6772616D49647D5F6772696428293B0A0909090909097D2C200A09090909090966756E6374696F6E286572726F7229207B0A09090909090909616C657274286572726F72293B0A0909090909097D0A09090909293B090A0909097D2C200A0909092877696E646F772E6576656E74203F2077696E646F772E6576656E74203A206E756C6C29200A09293B090A7D0A0A2F2F2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D0A66756E6374696F6E20247B70726F6772616D49647D5F706167655F6D6573736167652829207B0A0976617220706167654D6573736167653D273C733A70726F70657274792076616C75653D22706167654D65737361676522206573636170654A6176615363726970743D2274727565222F3E273B0A09696620286E756C6C213D706167654D657373616765202626202727213D706167654D65737361676520262620272027213D706167654D65737361676529207B0A0909616C65727428706167654D657373616765293B0A097D090A7D0A2F2F2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D0A0A3C2F7363726970743E0A0A3C2F686561643E0A0A3C626F647920636C6173733D22636C61726F22206267636F6C6F723D222345454545454522203E0A0A093C67733A746F6F6C4261720A090969643D22247B70726F6772616D49647D22200A090963616E63656C456E61626C653D225922200A090963616E63656C4A734D6574686F643D22247B70726F6772616D49647D5F546162436C6F736528293B22200A09096372656174654E6577456E61626C653D224E220A09096372656174654E65774A734D6574686F643D22220909200A090973617665456E6162656C3D224E22200A0909736176654A734D6574686F643D22220A090972656672657368456E61626C653D225922200909200A0909726566726573684A734D6574686F643D22247B70726F6772616D49647D5F5461625265667265736828293B222009090A09093E3C2F67733A746F6F6C4261723E0A093C6A73703A696E636C75646520706167653D222E2E2F6865616465722E6A7370223E3C2F6A73703A696E636C7564653E090A090A093C7461626C6520626F726465723D2230222077696474683D223130302522206865696768743D2235307078222063656C6C70616464696E673D2231222063656C6C73706163696E673D223022203E0A09093C74723E0A2020202009093C7464206865696768743D2232357078222077696474683D22313025222020616C69676E3D227269676874223E4163636F756E743A3C2F74643E0A2020202009093C7464206865696768743D2232357078222077696474683D22343025222020616C69676E3D226C656674223E3C67733A74657874426F78206E616D653D22434F52455F50524F473030344430303032515F75736572222069643D22434F52455F50524F473030344430303032515F75736572222076616C75653D22222077696474683D2232303022206D61786C656E6774683D223530223E3C2F67733A74657874426F783E3C2F74643E0A2020202009093C7464206865696768743D2232357078222077696474683D22313025222020616C69676E3D227269676874223E266E6273703B3C2F74643E0A2020202009093C7464206865696768743D2232357078222077696474683D22343025222020616C69676E3D226C656674223E266E6273703B3C2F74643E202009090909090A20202020093C2F74723E0A20202020093C74723E0A2020202009093C746420206865696768743D2232357078222077696474683D2231303025222020616C69676E3D2263656E7465722220636F6C7370616E3D2234223E0A202020200909093C67733A627574746F6E206E616D653D22434F52455F50524F473030344430303032515F7175657279222069643D22434F52455F50524F473030344430303032515F717565727922206F6E436C69636B3D226765745175657279477269645F247B70726F6772616D49647D5F6772696428293B220A202020200909090968616E646C6541733D226A736F6E220A202020200909090973796E633D224E220A202020200909090978687255726C3D22636F72652E636F6D6D6F6D4C6F6164466F726D2E616374696F6E3F70726F675F69643D434F52455F50524F4730303444303030325126666F726D5F69643D464F524D5F434F52455F34445F3030325F303126666F726D5F6D6574686F643D7175657279220A2020202009090909706172616D65746572547970653D22706F737444617461220A2020202009090909786872506172616D657465723D22200A2020202009090909097B200A202020200909090909092773656172636856616C75652E706172616D657465722E7573657227093A2064696A69742E627949642827434F52455F50524F473030344430303032515F7573657227292E676574282776616C756527292C200A2020202009090909090927706167654F662E73697A652709090909093A20676574477269645175657279506167654F6653697A655F247B70726F6772616D49647D5F6772696428292C0A2020202009090909090927706167654F662E73656C6563742709090909093A20676574477269645175657279506167654F6653656C6563745F247B70726F6772616D49647D5F6772696428292C0A2020202009090909090927706167654F662E73686F77526F7727090909093A20676574477269645175657279506167654F6653686F77526F775F247B70726F6772616D49647D5F6772696428290A2020202009090909097D200A2020202009090909220A20202020090909096572726F72466E3D22636C65617251756572795F247B70726F6772616D49647D5F6772696428293B220A20202020090909096C6F6164466E3D2264617461477269645F247B70726F6772616D49647D5F677269642864617461293B22200A202020200909090970726F6772616D49643D22247B70726F6772616D49647D220A20202020090909096C6162656C3D22517565727922200A202020200909090969636F6E436C6173733D2264696A697449636F6E536561726368223E3C2F67733A627574746F6E3E0A202020200909093C67733A627574746F6E206E616D653D22434F52455F50524F473030344430303032515F636C656172222069643D22434F52455F50524F473030344430303032515F636C65617222206F6E436C69636B3D22434F52455F50524F473030344430303032515F636C65617228293B22200A20202020090909096C6162656C3D22436C65617222200A202020200909090969636F6E436C6173733D2264696A697449636F6E436C656172223E3C2F67733A627574746F6E3E0A202020200909093C67733A627574746F6E206E616D653D22434F52455F50524F473030344430303032515F72656D6F7665222069643D22434F52455F50524F473030344430303032515F72656D6F766522206F6E436C69636B3D22434F52455F50524F473030344430303032515F72656D6F7665416C6C28293B22200A20202020090909096C6162656C3D2252656D6F766522200A202020200909090969636F6E436C6173733D2264696A697449636F6E44656C657465223E3C2F67733A627574746F6E3E090909090A2020202009093C2F74643E0A20202020093C2F74723E20202020200920202020090A202020203C2F7461626C653E090A202020200A202020203C67733A6772696420677269644669656C645374727563747572653D22434F52455F50524F473030344430303032515F477269644669656C6453747275637475726528292220636C6561725175657279466E3D22222069643D225F247B70726F6772616D49647D5F67726964222070726F6772616D49643D22247B70726F6772616D49647D223E3C2F67733A677269643E090A090A3C73637269707420747970653D22746578742F6A617661736372697074223E247B70726F6772616D49647D5F706167655F6D65737361676528293B3C2F7363726970743E090A3C2F626F64793E0A3C2F68746D6C3E0A,'TPL_CORE_4D_002_01.jsp','for 02 - Login log CORE_PROG004D0002Q','admin','2015-04-28 10:56:09','admin','2015-04-28 11:40:36'),('1e805de4-1c20-4752-860e-76856270ad51','TPL002','Basic message template for edit',0x3C25402070616765206C616E67756167653D226A6176612220696D706F72743D226A6176612E7574696C2E2A222070616765456E636F64696E673D225554462D3822253E0A3C2540207461676C6962207572693D222F7374727574732D7461677322207072656669783D22732220253E0A3C2540207461676C6962207072656669783D226322207572693D22687474703A2F2F6A6176612E73756E2E636F6D2F6A73702F6A73746C2F636F72652220253E0A3C2540207461676C6962207072656669783D22677322207572693D22687474703A2F2F7777772E67737765622E6F72672F636F6E74726F6C6C65722F7461672220253E0A3C250A537472696E672070617468203D20726571756573742E676574436F6E746578745061746828293B0A537472696E67206261736550617468203D20726571756573742E676574536368656D6528292B223A2F2F222B726571756573742E6765745365727665724E616D6528292B223A222B726571756573742E676574536572766572506F727428292B706174682B222F223B0A0A253E0A3C21646F63747970652068746D6C3E0A3C68746D6C206974656D73636F70653D226974656D73636F706522206974656D747970653D22687474703A2F2F736368656D612E6F72672F57656250616765223E0A3C686561643E0A0A3C6D65746120687474702D65717569763D22436F6E74656E742D547970652220636F6E74656E743D22746578742F68746D6C3B20636861727365743D7574662D3822202F3E0A0A202020203C6261736520687265663D223C253D6261736550617468253E223E0A202020200A202020203C7469746C653E62616D626F6F434F52453C2F7469746C653E0A093C6D65746120687474702D65717569763D22707261676D612220636F6E74656E743D226E6F2D6361636865223E0A093C6D65746120687474702D65717569763D2263616368652D636F6E74726F6C2220636F6E74656E743D226E6F2D6361636865223E0A093C6D65746120687474702D65717569763D22657870697265732220636F6E74656E743D2230223E202020200A093C6D65746120687474702D65717569763D226B6579776F7264732220636F6E74656E743D2262616D626F6F434F5245223E0A093C6D65746120687474702D65717569763D226465736372697074696F6E2220636F6E74656E743D2262616D626F6F434F5245223E0A090A3C7374796C6520747970653D22746578742F637373223E0A0A3C2F7374796C653E0A0A3C73637269707420747970653D22746578742F6A617661736372697074223E0A0A76617220434F52455F50524F473030314430303135455F6669656C64734964203D206E6577204F626A65637428293B0A434F52455F50524F473030314430303135455F6669656C647349645B276E616D65275D200909093D2027434F52455F50524F473030314430303135455F6E616D65273B0A0A66756E6374696F6E20434F52455F50524F473030314430303135455F75706461746553756363657373286461746129207B0A097365744669656C64734261636B67726F756E6444656661756C7428434F52455F50524F473030314430303135455F6669656C64734964293B0A09616C6572744469616C6F67285F6765744170706C69636174696F6E50726F6772616D4E616D65427949642827247B70726F6772616D49647D27292C20646174612E6D6573736167652C2066756E6374696F6E28297B7D2C20646174612E73756363657373293B0A096966202827592720213D20646174612E7375636365737329207B0909090909090A09097365744669656C64734261636B67726F756E64416C65727428646174612E6669656C647349642C20434F52455F50524F473030314430303135455F6669656C64734964293B09090A090972657475726E3B0A097D09090A7D0A0A66756E6374696F6E20434F52455F50524F473030314430303135455F636C6561722829207B0A097365744669656C64734261636B67726F756E6444656661756C7428434F52455F50524F473030314430303135455F6669656C64734964293B0A0964696A69742E627949642827434F52455F50524F473030314430303135455F6E616D6527292E736574282276616C7565222C202222293B090A7D0A0A2F2F2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D0A66756E6374696F6E20247B70726F6772616D49647D5F706167655F6D6573736167652829207B0A0976617220706167654D6573736167653D273C733A70726F70657274792076616C75653D22706167654D65737361676522206573636170654A6176615363726970743D2274727565222F3E273B0A09696620286E756C6C213D706167654D657373616765202626202727213D706167654D65737361676520262620272027213D706167654D65737361676529207B0A0909616C65727428706167654D657373616765293B0A097D090A7D0A2F2F2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D0A0A3C2F7363726970743E0A0A3C2F686561643E0A0A3C626F647920636C6173733D22636C61726F22206267636F6C6F723D222345454545454522203E0A0A093C67733A746F6F6C4261720A090969643D22247B70726F6772616D49647D22200A090963616E63656C456E61626C653D225922200A090963616E63656C4A734D6574686F643D22247B70726F6772616D49647D5F546162436C6F736528293B22200A09096372656174654E6577456E61626C653D224E220A09096372656174654E65774A734D6574686F643D22220909200A090973617665456E6162656C3D225922200A0909736176654A734D6574686F643D22434F52455F50524F473030314430303135455F75706461746528293B220A090972656672657368456E61626C653D225922200909200A0909726566726573684A734D6574686F643D22247B70726F6772616D49647D5F5461625265667265736828293B222009090A09093E3C2F67733A746F6F6C4261723E0A093C6A73703A696E636C75646520706167653D222E2E2F6865616465722E6A7370223E3C2F6A73703A696E636C7564653E0A090A093C7461626C6520626F726465723D2230222077696474683D223130302522206865696768743D223130307078222063656C6C70616464696E673D2231222063656C6C73706163696E673D223022203E0A09093C74723E0A2020202009093C7464206865696768743D2235307078222077696474683D2231303025222020616C69676E3D226C656674223E0A202020200909093C666F6E7420636F6C6F723D27524544273E2A3C2F666F6E743E3C623E4E616D653C2F623E3A0A202020200909093C62722F3E0A202020200909093C67733A74657874426F78206E616D653D22434F52455F50524F473030314430303135455F6E616D65222069643D22434F52455F50524F473030314430303135455F6E616D65222076616C75653D2264617461732E737973436F64652E6E616D65222077696474683D2234303022206D61786C656E6774683D22323030223E3C2F67733A74657874426F783E0A2020202009093C2F74643E0A20202020093C2F74723E0909200920200920202020090A20202020093C74723E0A2020202009093C7464206865696768743D2235307078222077696474683D2231303025222020616C69676E3D226C656674223E0A202020200909093C67733A627574746F6E206E616D653D22434F52455F50524F473030314430303135455F757064617465222069643D22434F52455F50524F473030314430303135455F75706461746522206F6E436C69636B3D22434F52455F50524F473030314430303135455F75706461746528293B220A202020200909090968616E646C6541733D226A736F6E220A202020200909090973796E633D224E220A202020200909090978687255726C3D22636F72652E636F6D6D6F6D4C6F6164466F726D2E616374696F6E3F70726F675F69643D434F52455F50524F4730303144303031354526666F726D5F69643D464F524D30303226666F726D5F6D6574686F643D757064617465220A2020202009090909706172616D65746572547970653D22706F737444617461220A2020202009090909786872506172616D657465723D22200A2020202009090909097B200A20202020090909090909276669656C64732E6F6964270909093A2027247B64617461732E737973436F64652E6F69647D272C0A20202020090909090909276669656C64732E6E616D65270909093A2064696A69742E627949642827434F52455F50524F473030314430303135455F6E616D6527292E676574282776616C756527290A2020202009090909097D200A2020202009090909220A20202020090909096572726F72466E3D22220A20202020090909096C6F6164466E3D22434F52455F50524F473030314430303135455F757064617465537563636573732864617461293B22200A202020200909090970726F6772616D49643D22247B70726F6772616D49647D220A20202020090909096C6162656C3D225361766522200A202020200909090969636F6E436C6173733D2264696A697449636F6E53617665223E3C2F67733A627574746F6E3E202020200909090A202020200909093C67733A627574746F6E206E616D653D22434F52455F50524F473030314430303135455F636C656172222069643D22434F52455F50524F473030314430303135455F636C65617222206F6E436C69636B3D22434F52455F50524F473030314430303135455F636C65617228293B22200A20202020090909096C6162656C3D22436C65617222200A202020200909090969636F6E436C6173733D2264696A697449636F6E436C656172223E3C2F67733A627574746F6E3E202020200909090A2020202009093C2F74643E0A20202020093C2F74723E202020202009200920200920202020090A093C2F7461626C653E090A090A3C73637269707420747970653D22746578742F6A617661736372697074223E247B70726F6772616D49647D5F706167655F6D65737361676528293B3C2F7363726970743E0A3C2F626F64793E0A3C2F68746D6C3E0A,'TPL002.jsp','for CORE_PROG001D0015E (Basic message management)','admin','2015-04-27 20:24:13','admin','2015-04-27 20:42:56'),('7e6caf27-05b9-450f-ba97-2bd62d3002c0','TPL001','Basic message template',0x3C25402070616765206C616E67756167653D226A6176612220696D706F72743D226A6176612E7574696C2E2A222070616765456E636F64696E673D225554462D3822253E0D0A3C2540207461676C6962207572693D222F7374727574732D7461677322207072656669783D22732220253E0D0A3C2540207461676C6962207072656669783D226322207572693D22687474703A2F2F6A6176612E73756E2E636F6D2F6A73702F6A73746C2F636F72652220253E0D0A3C2540207461676C6962207072656669783D22677322207572693D22687474703A2F2F7777772E67737765622E6F72672F636F6E74726F6C6C65722F7461672220253E0D0A3C250D0A537472696E672070617468203D20726571756573742E676574436F6E746578745061746828293B0D0A537472696E67206261736550617468203D20726571756573742E676574536368656D6528292B223A2F2F222B726571756573742E6765745365727665724E616D6528292B223A222B726571756573742E676574536572766572506F727428292B706174682B222F223B0D0A0D0A253E0D0A3C21646F63747970652068746D6C3E0D0A3C68746D6C206974656D73636F70653D226974656D73636F706522206974656D747970653D22687474703A2F2F736368656D612E6F72672F57656250616765223E0D0A3C686561643E0D0A0D0A3C6D65746120687474702D65717569763D22436F6E74656E742D547970652220636F6E74656E743D22746578742F68746D6C3B20636861727365743D7574662D3822202F3E0D0A0D0A202020203C6261736520687265663D223C253D6261736550617468253E223E0D0A202020200D0A202020203C7469746C653E62616D626F6F434F52453C2F7469746C653E0D0A093C6D65746120687474702D65717569763D22707261676D612220636F6E74656E743D226E6F2D6361636865223E0D0A093C6D65746120687474702D65717569763D2263616368652D636F6E74726F6C2220636F6E74656E743D226E6F2D6361636865223E0D0A093C6D65746120687474702D65717569763D22657870697265732220636F6E74656E743D2230223E202020200D0A093C6D65746120687474702D65717569763D226B6579776F7264732220636F6E74656E743D2262616D626F6F434F5245223E0D0A093C6D65746120687474702D65717569763D226465736372697074696F6E2220636F6E74656E743D2262616D626F6F434F5245223E0D0A090D0A3C7374796C6520747970653D22746578742F637373223E0D0A0D0A3C2F7374796C653E0D0A0D0A3C73637269707420747970653D22746578742F6A617661736372697074223E0D0A0D0A66756E6374696F6E20434F52455F50524F473030314430303135515F477269644669656C645374727563747572652829207B0D0A0972657475726E205B0D0A0909097B206E616D653A202256696577266E6273703B2F266E6273703B45646974222C206669656C643A20224F4944222C20666F726D61747465723A20434F52455F50524F473030314430303135515F47726964427574746F6E436C69636B2C2077696474683A202231352522207D2C20200D0A0909097B206E616D653A2022436F6465222C206669656C643A2022434F4445222C2077696474683A202233302522207D2C0D0A0909097B206E616D653A20224E616D65222C206669656C643A20224E414D45222C2077696474683A202235352522207D0D0A09095D3B090D0A7D0D0A0D0A66756E6374696F6E20434F52455F50524F473030314430303135515F47726964427574746F6E436C69636B286974656D4F696429207B0D0A097661722072643D22223B0D0A097264202B3D20223C696D67207372633D5C2222202B205F67657453797374656D49636F6E55726C282750524F504552544945532729202B20225C2220626F726465723D5C22305C2220616C743D5C22656469745C22206F6E636C69636B3D5C22434F52455F50524F473030314430303135515F65646974282722202B206974656D4F6964202B202227293B5C22202F3E223B0D0A0972657475726E2072643B090D0A7D0D0A0D0A66756E6374696F6E20434F52455F50524F473030314430303135515F636C6561722829207B0D0A0964696A69742E627949642827434F52455F50524F473030314430303135515F636F646527292E736574282776616C7565272C202727293B0D0A0964696A69742E627949642827434F52455F50524F473030314430303135515F6E616D6527292E736574282776616C7565272C202727293B0D0A09636C65617251756572795F247B70726F6772616D49647D5F6772696428293B090D0A7D0D0A0D0A66756E6374696F6E20434F52455F50524F473030314430303135515F65646974286F696429207B0D0A09434F52455F50524F473030314430303135455F54616253686F77286F6964293B0D0A7D0D0A0D0A2F2F2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D0D0A66756E6374696F6E20247B70726F6772616D49647D5F706167655F6D6573736167652829207B0D0A0976617220706167654D6573736167653D273C733A70726F70657274792076616C75653D22706167654D65737361676522206573636170654A6176615363726970743D2274727565222F3E273B0D0A09696620286E756C6C213D706167654D657373616765202626202727213D706167654D65737361676520262620272027213D706167654D65737361676529207B0D0A0909616C65727428706167654D657373616765293B0D0A097D090D0A7D0D0A2F2F2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D0D0A0D0A3C2F7363726970743E0D0A0D0A3C2F686561643E0D0A0D0A3C626F647920636C6173733D22636C61726F22206267636F6C6F723D222345454545454522203E0D0A0D0A093C67733A746F6F6C4261720D0A090969643D22247B70726F6772616D49647D22200D0A090963616E63656C456E61626C653D225922200D0A090963616E63656C4A734D6574686F643D22247B70726F6772616D49647D5F546162436C6F736528293B22200D0A09096372656174654E6577456E61626C653D224E220D0A09096372656174654E65774A734D6574686F643D22220909200D0A090973617665456E6162656C3D224E22200D0A0909736176654A734D6574686F643D22220D0A090972656672657368456E61626C653D225922200909200D0A0909726566726573684A734D6574686F643D22247B70726F6772616D49647D5F5461625265667265736828293B222009090D0A09093E3C2F67733A746F6F6C4261723E0D0A093C6A73703A696E636C75646520706167653D222E2E2F6865616465722E6A7370223E3C2F6A73703A696E636C7564653E090D0A090D0A093C7461626C6520626F726465723D2230222077696474683D223130302522206865696768743D2235307078222063656C6C70616464696E673D2231222063656C6C73706163696E673D223022203E0D0A09093C74723E0D0A2020202009093C7464206865696768743D2232357078222077696474683D22313025222020616C69676E3D227269676874223E436F64653A3C2F74643E0D0A2020202009093C7464206865696768743D2232357078222077696474683D22343025222020616C69676E3D226C656674223E3C67733A74657874426F78206E616D653D22434F52455F50524F473030314430303135515F636F6465222069643D22434F52455F50524F473030314430303135515F636F6465222076616C75653D22222077696474683D2232303022206D61786C656E6774683D223530223E3C2F67733A74657874426F783E3C2F74643E0D0A2020202009093C7464206865696768743D2232357078222077696474683D22313025222020616C69676E3D227269676874223E4E616D653A3C2F74643E0D0A2020202009093C7464206865696768743D2232357078222077696474683D22343025222020616C69676E3D226C656674223E3C67733A74657874426F78206E616D653D22434F52455F50524F473030314430303135515F6E616D65222069643D22434F52455F50524F473030314430303135515F6E616D65222076616C75653D22222077696474683D2232303022206D61786C656E6774683D22313030223E3C2F67733A74657874426F783E3C2F74643E202009090909090D0A20202020093C2F74723E0D0A20202020093C74723E0D0A2020202009093C746420206865696768743D2232357078222077696474683D2231303025222020616C69676E3D2263656E7465722220636F6C7370616E3D2234223E0D0A202020200909093C67733A627574746F6E206E616D653D22434F52455F50524F473030314430303135515F7175657279222069643D22434F52455F50524F473030314430303135515F717565727922206F6E436C69636B3D226765745175657279477269645F247B70726F6772616D49647D5F6772696428293B220D0A202020200909090968616E646C6541733D226A736F6E220D0A202020200909090973796E633D224E220D0A202020200909090978687255726C3D22636F72652E636F6D6D6F6D4C6F6164466F726D2E616374696F6E3F70726F675F69643D434F52455F50524F4730303144303031355126666F726D5F69643D464F524D30303126666F726D5F6D6574686F643D7175657279220D0A2020202009090909706172616D65746572547970653D22706F737444617461220D0A2020202009090909786872506172616D657465723D22200D0A2020202009090909097B200D0A202020200909090909092773656172636856616C75652E706172616D657465722E636F646527093A2064696A69742E627949642827434F52455F50524F473030314430303135515F636F646527292E676574282776616C756527292C200D0A202020200909090909092773656172636856616C75652E706172616D657465722E6E616D6527093A2064696A69742E627949642827434F52455F50524F473030314430303135515F6E616D6527292E676574282776616C756527292C0D0A2020202009090909090927706167654F662E73697A652709090909093A20676574477269645175657279506167654F6653697A655F247B70726F6772616D49647D5F6772696428292C0D0A2020202009090909090927706167654F662E73656C6563742709090909093A20676574477269645175657279506167654F6653656C6563745F247B70726F6772616D49647D5F6772696428292C0D0A2020202009090909090927706167654F662E73686F77526F7727090909093A20676574477269645175657279506167654F6653686F77526F775F247B70726F6772616D49647D5F6772696428290D0A2020202009090909097D200D0A2020202009090909220D0A20202020090909096572726F72466E3D22636C65617251756572795F247B70726F6772616D49647D5F6772696428293B220D0A20202020090909096C6F6164466E3D2264617461477269645F247B70726F6772616D49647D5F677269642864617461293B22200D0A202020200909090970726F6772616D49643D22247B70726F6772616D49647D220D0A20202020090909096C6162656C3D22517565727922200D0A202020200909090969636F6E436C6173733D2264696A697449636F6E536561726368223E3C2F67733A627574746F6E3E0D0A202020200909093C67733A627574746F6E206E616D653D22434F52455F50524F473030314430303135515F636C656172222069643D22434F52455F50524F473030314430303135515F636C65617222206F6E436C69636B3D22434F52455F50524F473030314430303135515F636C65617228293B22200D0A20202020090909096C6162656C3D22436C65617222200D0A202020200909090969636F6E436C6173733D2264696A697449636F6E436C656172223E3C2F67733A627574746F6E3E0D0A2020202009093C2F74643E0D0A20202020093C2F74723E20202020200920202020090D0A202020203C2F7461626C653E090D0A202020200D0A202020203C67733A6772696420677269644669656C645374727563747572653D22434F52455F50524F473030314430303135515F477269644669656C6453747275637475726528292220636C6561725175657279466E3D22222069643D225F247B70726F6772616D49647D5F67726964222070726F6772616D49643D22247B70726F6772616D49647D223E3C2F67733A677269643E090D0A090D0A3C73637269707420747970653D22746578742F6A617661736372697074223E247B70726F6772616D49647D5F706167655F6D65737361676528293B3C2F7363726970743E090D0A3C2F626F64793E0D0A3C2F68746D6C3E0D0A,'TPL001.jsp','for CORE_PROG001D0015Q (Basic message management)','admin','2015-04-26 12:27:21','admin','2015-04-28 10:48:41'),('9fcf9085-047d-4129-8247-f69ab5383294','TPL_CORE_4D_001_01','Event log query template',0x3C25402070616765206C616E67756167653D226A6176612220696D706F72743D226A6176612E7574696C2E2A222070616765456E636F64696E673D225554462D3822253E0A3C2540207461676C6962207572693D222F7374727574732D7461677322207072656669783D22732220253E0A3C2540207461676C6962207072656669783D226322207572693D22687474703A2F2F6A6176612E73756E2E636F6D2F6A73702F6A73746C2F636F72652220253E0A3C2540207461676C6962207072656669783D22677322207572693D22687474703A2F2F7777772E67737765622E6F72672F636F6E74726F6C6C65722F7461672220253E0A3C250A537472696E672070617468203D20726571756573742E676574436F6E746578745061746828293B0A537472696E67206261736550617468203D20726571756573742E676574536368656D6528292B223A2F2F222B726571756573742E6765745365727665724E616D6528292B223A222B726571756573742E676574536572766572506F727428292B706174682B222F223B0A0A253E0A3C21646F63747970652068746D6C3E0A3C68746D6C206974656D73636F70653D226974656D73636F706522206974656D747970653D22687474703A2F2F736368656D612E6F72672F57656250616765223E0A3C686561643E0A0A3C6D65746120687474702D65717569763D22436F6E74656E742D547970652220636F6E74656E743D22746578742F68746D6C3B20636861727365743D7574662D3822202F3E0A0A202020203C6261736520687265663D223C253D6261736550617468253E223E0A202020200A202020203C7469746C653E62616D626F6F434F52453C2F7469746C653E0A093C6D65746120687474702D65717569763D22707261676D612220636F6E74656E743D226E6F2D6361636865223E0A093C6D65746120687474702D65717569763D2263616368652D636F6E74726F6C2220636F6E74656E743D226E6F2D6361636865223E0A093C6D65746120687474702D65717569763D22657870697265732220636F6E74656E743D2230223E202020200A093C6D65746120687474702D65717569763D226B6579776F7264732220636F6E74656E743D2262616D626F6F434F5245223E0A093C6D65746120687474702D65717569763D226465736372697074696F6E2220636F6E74656E743D2262616D626F6F434F5245223E0A090A3C7374796C6520747970653D22746578742F637373223E0A0A3C2F7374796C653E0A0A3C73637269707420747970653D22746578742F6A617661736372697074223E0A0A66756E6374696F6E20434F52455F50524F473030344430303031515F477269644669656C645374727563747572652829207B0A0972657475726E205B0A0909097B206E616D653A202256696577266E6273703B2F266E6273703B45646974222C206669656C643A20224F4944222C20666F726D61747465723A20434F52455F50524F473030344430303031515F47726964427574746F6E436C69636B2C2077696474683A202231302522207D2C20200A0909097B206E616D653A202253797374656D222C206669656C643A20225359535F4944222C2077696474683A202232302522207D2C0A0909097B206E616D653A202255736572222C206669656C643A202255534552222C2077696474683A202232302522207D2C0A0909097B206E616D653A20224576656E74222C206669656C643A2022455845435554455F4556454E54222C2077696474683A202234302522207D2C0A0909097B206E616D653A20225065726D6974222C206669656C643A202249535F5045524D4954222C2077696474683A202231302522207D0A09095D3B090A7D0A0A66756E6374696F6E20434F52455F50524F473030344430303031515F47726964427574746F6E436C69636B286974656D4F696429207B0A097661722072643D22223B0A097264202B3D20223C696D67207372633D5C2222202B205F67657453797374656D49636F6E55726C282752454D4F56452729202B20225C2220626F726465723D5C22305C2220616C743D5C2264656C6574655C22206F6E636C69636B3D5C22434F52455F50524F473030344430303031515F636F6E6669726D44656C657465282722202B206974656D4F6964202B202227293B5C22202F3E223B0A0972657475726E2072643B090A7D0A0A66756E6374696F6E20434F52455F50524F473030344430303031515F636F6E6669726D44656C657465286F696429207B0A09636F6E6669726D4469616C6F67280A09090922247B70726F6772616D49647D5F6D616E6167656D656E744469616C6F674964303030222C200A0909095F6765744170706C69636174696F6E50726F6772616D4E616D65427949642827247B70726F6772616D49647D27292C200A0909092264656C6574653F20222C200A09090966756E6374696F6E287375636365737329207B0A0909090969662028217375636365737329207B0A090909090972657475726E3B0A090909097D090A0909090978687253656E64506172616D65746572280A09090909090927636F72652E636F6D6D6F6D4C6F6164466F726D2E616374696F6E3F70726F675F69643D434F52455F50524F4730303444303030315126666F726D5F69643D464F524D5F434F52455F34445F3030315F303126666F726D5F6D6574686F643D64656C657465272C200A0909090909097B20276669656C64732E6F696427203A206F6964207D2C200A090909090909276A736F6E272C200A0909090909095F6773636F72655F646F6A6F5F616A61785F74696D656F75742C0A0909090909095F6773636F72655F646F6A6F5F616A61785F73796E632C200A090909090909747275652C200A09090909090966756E6374696F6E286461746129207B0A09090909090909616C6572744469616C6F67285F6765744170706C69636174696F6E50726F6772616D4E616D65427949642827247B70726F6772616D49647D27292C20646174612E6D6573736167652C2066756E6374696F6E28297B7D2C20646174612E73756363657373293B0A090909090909096765745175657279477269645F247B70726F6772616D49647D5F6772696428293B0A0909090909097D2C200A09090909090966756E6374696F6E286572726F7229207B0A09090909090909616C657274286572726F72293B0A0909090909097D0A09090909293B090A0909097D2C200A0909092877696E646F772E6576656E74203F2077696E646F772E6576656E74203A206E756C6C29200A09293B090A7D0A0A66756E6374696F6E20434F52455F50524F473030344430303031515F636C6561722829207B0A0964696A69742E627949642827434F52455F50524F473030344430303031515F7573657227292E736574282776616C7565272C202727293B0A0964696A69742E627949642827434F52455F50524F473030344430303031515F737973496427292E736574282776616C7565272C202727293B0A09636C65617251756572795F247B70726F6772616D49647D5F6772696428293B090A7D0A0A66756E6374696F6E20434F52455F50524F473030344430303031515F72656D6F7665416C6C2829207B0A09636F6E6669726D4469616C6F67280A09090922247B70726F6772616D49647D5F6D616E6167656D656E744469616C6F674964303030222C200A0909095F6765744170706C69636174696F6E50726F6772616D4E616D65427949642827247B70726F6772616D49647D27292C200A0909092264656C6574653F20222C200A09090966756E6374696F6E287375636365737329207B0A0909090969662028217375636365737329207B0A090909090972657475726E3B0A090909097D090A0909090978687253656E64506172616D65746572280A09090909090927636F72652E636F6D6D6F6D4C6F6164466F726D2E616374696F6E3F70726F675F69643D434F52455F50524F4730303444303030315126666F726D5F69643D464F524D5F434F52455F34445F3030315F303126666F726D5F6D6574686F643D64656C657465416C6C272C200A0909090909097B207D2C200A090909090909276A736F6E272C200A0909090909095F6773636F72655F646F6A6F5F616A61785F74696D656F75742C0A0909090909095F6773636F72655F646F6A6F5F616A61785F73796E632C200A090909090909747275652C200A09090909090966756E6374696F6E286461746129207B0A09090909090909616C6572744469616C6F67285F6765744170706C69636174696F6E50726F6772616D4E616D65427949642827247B70726F6772616D49647D27292C20646174612E6D6573736167652C2066756E6374696F6E28297B7D2C20646174612E73756363657373293B0A090909090909096765745175657279477269645F247B70726F6772616D49647D5F6772696428293B0A0909090909097D2C200A09090909090966756E6374696F6E286572726F7229207B0A09090909090909616C657274286572726F72293B0A0909090909097D0A09090909293B090A0909097D2C200A0909092877696E646F772E6576656E74203F2077696E646F772E6576656E74203A206E756C6C29200A09293B090A7D0A0A2F2F2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D0A66756E6374696F6E20247B70726F6772616D49647D5F706167655F6D6573736167652829207B0A0976617220706167654D6573736167653D273C733A70726F70657274792076616C75653D22706167654D65737361676522206573636170654A6176615363726970743D2274727565222F3E273B0A09696620286E756C6C213D706167654D657373616765202626202727213D706167654D65737361676520262620272027213D706167654D65737361676529207B0A0909616C65727428706167654D657373616765293B0A097D090A7D0A2F2F2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D2D0A0A3C2F7363726970743E0A0A3C2F686561643E0A0A3C626F647920636C6173733D22636C61726F22206267636F6C6F723D222345454545454522203E0A0A093C67733A746F6F6C4261720A090969643D22247B70726F6772616D49647D22200A090963616E63656C456E61626C653D225922200A090963616E63656C4A734D6574686F643D22247B70726F6772616D49647D5F546162436C6F736528293B22200A09096372656174654E6577456E61626C653D224E220A09096372656174654E65774A734D6574686F643D22220909200A090973617665456E6162656C3D224E22200A0909736176654A734D6574686F643D22220A090972656672657368456E61626C653D225922200909200A0909726566726573684A734D6574686F643D22247B70726F6772616D49647D5F5461625265667265736828293B222009090A09093E3C2F67733A746F6F6C4261723E0A093C6A73703A696E636C75646520706167653D222E2E2F6865616465722E6A7370223E3C2F6A73703A696E636C7564653E090A090A093C7461626C6520626F726465723D2230222077696474683D223130302522206865696768743D2235307078222063656C6C70616464696E673D2231222063656C6C73706163696E673D223022203E0A09093C74723E0A2020202009093C7464206865696768743D2232357078222077696474683D22313025222020616C69676E3D227269676874223E4163636F756E743A3C2F74643E0A2020202009093C7464206865696768743D2232357078222077696474683D22343025222020616C69676E3D226C656674223E3C67733A74657874426F78206E616D653D22434F52455F50524F473030344430303031515F75736572222069643D22434F52455F50524F473030344430303031515F75736572222076616C75653D22222077696474683D2232303022206D61786C656E6774683D223530223E3C2F67733A74657874426F783E3C2F74643E0A2020202009093C7464206865696768743D2232357078222077696474683D22313025222020616C69676E3D227269676874223E53797374656D2049643A3C2F74643E0A2020202009093C7464206865696768743D2232357078222077696474683D22343025222020616C69676E3D226C656674223E3C67733A74657874426F78206E616D653D22434F52455F50524F473030344430303031515F7379734964222069643D22434F52455F50524F473030344430303031515F7379734964222076616C75653D22222077696474683D2232303022206D61786C656E6774683D22313030223E3C2F67733A74657874426F783E3C2F74643E202009090909090A20202020093C2F74723E0A20202020093C74723E0A2020202009093C746420206865696768743D2232357078222077696474683D2231303025222020616C69676E3D2263656E7465722220636F6C7370616E3D2234223E0A202020200909093C67733A627574746F6E206E616D653D22434F52455F50524F473030344430303031515F7175657279222069643D22434F52455F50524F473030344430303031515F717565727922206F6E436C69636B3D226765745175657279477269645F247B70726F6772616D49647D5F6772696428293B220A202020200909090968616E646C6541733D226A736F6E220A202020200909090973796E633D224E220A202020200909090978687255726C3D22636F72652E636F6D6D6F6D4C6F6164466F726D2E616374696F6E3F70726F675F69643D434F52455F50524F4730303444303030315126666F726D5F69643D464F524D5F434F52455F34445F3030315F303126666F726D5F6D6574686F643D7175657279220A2020202009090909706172616D65746572547970653D22706F737444617461220A2020202009090909786872506172616D657465723D22200A2020202009090909097B200A202020200909090909092773656172636856616C75652E706172616D657465722E7573657227093A2064696A69742E627949642827434F52455F50524F473030344430303031515F7573657227292E676574282776616C756527292C200A202020200909090909092773656172636856616C75652E706172616D657465722E737973496427093A2064696A69742E627949642827434F52455F50524F473030344430303031515F737973496427292E676574282776616C756527292C0A2020202009090909090927706167654F662E73697A652709090909093A20676574477269645175657279506167654F6653697A655F247B70726F6772616D49647D5F6772696428292C0A2020202009090909090927706167654F662E73656C6563742709090909093A20676574477269645175657279506167654F6653656C6563745F247B70726F6772616D49647D5F6772696428292C0A2020202009090909090927706167654F662E73686F77526F7727090909093A20676574477269645175657279506167654F6653686F77526F775F247B70726F6772616D49647D5F6772696428290A2020202009090909097D200A2020202009090909220A20202020090909096572726F72466E3D22636C65617251756572795F247B70726F6772616D49647D5F6772696428293B220A20202020090909096C6F6164466E3D2264617461477269645F247B70726F6772616D49647D5F677269642864617461293B22200A202020200909090970726F6772616D49643D22247B70726F6772616D49647D220A20202020090909096C6162656C3D22517565727922200A202020200909090969636F6E436C6173733D2264696A697449636F6E536561726368223E3C2F67733A627574746F6E3E0A202020200909093C67733A627574746F6E206E616D653D22434F52455F50524F473030344430303031515F636C656172222069643D22434F52455F50524F473030344430303031515F636C65617222206F6E436C69636B3D22434F52455F50524F473030344430303031515F636C65617228293B22200A20202020090909096C6162656C3D22436C65617222200A202020200909090969636F6E436C6173733D2264696A697449636F6E436C656172223E3C2F67733A627574746F6E3E0A202020200909093C67733A627574746F6E206E616D653D22434F52455F50524F473030344430303031515F72656D6F7665222069643D22434F52455F50524F473030344430303031515F72656D6F766522206F6E436C69636B3D22434F52455F50524F473030344430303031515F72656D6F7665416C6C28293B22200A20202020090909096C6162656C3D2252656D6F766522200A202020200909090969636F6E436C6173733D2264696A697449636F6E44656C657465223E3C2F67733A627574746F6E3E090909090A2020202009093C2F74643E0A20202020093C2F74723E20202020200920202020090A202020203C2F7461626C653E090A202020200A202020203C67733A6772696420677269644669656C645374727563747572653D22434F52455F50524F473030344430303031515F477269644669656C6453747275637475726528292220636C6561725175657279466E3D22222069643D225F247B70726F6772616D49647D5F67726964222070726F6772616D49643D22247B70726F6772616D49647D223E3C2F67733A677269643E090A090A3C73637269707420747970653D22746578742F6A617661736372697074223E247B70726F6772616D49647D5F706167655F6D65737361676528293B3C2F7363726970743E090A3C2F626F64793E0A3C2F68746D6C3E0A,'TPL_CORE_4D_001_01.jsp','01 - Event log CORE_PROG004D0001Q','admin','2015-04-28 09:58:57','admin','2015-04-28 10:27:22');
/*!40000 ALTER TABLE `tb_sys_form_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ftp`
--

DROP TABLE IF EXISTS `tb_sys_ftp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ftp` (
  `OID` char(36) NOT NULL,
  `TYPE` varchar(5) NOT NULL DEFAULT 'FTP',
  `ID` varchar(10) NOT NULL,
  `ADDRESS` varchar(50) NOT NULL,
  `NAME` varchar(20) NOT NULL,
  `PORT` int(5) NOT NULL DEFAULT '0',
  `USER` varchar(50) NOT NULL,
  `PASS` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`),
  KEY `IDX_1` (`TYPE`,`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ftp`
--

LOCK TABLES `tb_sys_ftp` WRITE;
/*!40000 ALTER TABLE `tb_sys_ftp` DISABLE KEYS */;
INSERT INTO `tb_sys_ftp` VALUES ('111223344','FTP','FTP0001','127.0.0.1','test-ftp',0,'root','password123','for test','admin','2015-05-15 10:12:22',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_ftp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ftp_tran`
--

DROP TABLE IF EXISTS `tb_sys_ftp_tran`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ftp_tran` (
  `OID` char(36) NOT NULL,
  `FTP_ID` varchar(10) NOT NULL,
  `TRAN_ID` varchar(10) NOT NULL,
  `TRAN_TYPE` varchar(10) NOT NULL DEFAULT 'GET',
  `CWD` varchar(50) NOT NULL,
  `XML_CLASS_NAME` varchar(255) DEFAULT NULL,
  `USE_SEGM` varchar(1) NOT NULL,
  `SEGM_MODE` varchar(10) NOT NULL DEFAULT 'TXT',
  `SEGM_SYMBOL` varchar(1) NOT NULL DEFAULT ',',
  `ENCODING` varchar(10) NOT NULL DEFAULT 'utf-8',
  `EXPR_TYPE` varchar(10) NOT NULL,
  `NAME_EXPRESSION` varchar(8000) NOT NULL,
  `HELP_EXPRESSION` varchar(8000) NOT NULL,
  `BEGIN_LEN` int(1) NOT NULL DEFAULT '0',
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TRAN_ID`),
  KEY `IDX_1` (`FTP_ID`,`TRAN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ftp_tran`
--

LOCK TABLES `tb_sys_ftp_tran` WRITE;
/*!40000 ALTER TABLE `tb_sys_ftp_tran` DISABLE KEYS */;
INSERT INTO `tb_sys_ftp_tran` VALUES ('22222','FTP0001','TRAN0001','GET-TXT','/TEST_DIR',NULL,'Y','TXT',',','utf-8','GROOVY','fileName.add( \"test.txt\" );','/* nothing */',0,'test','admin','2015-05-15 10:17:26',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_ftp_tran` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ftp_tran_segm`
--

DROP TABLE IF EXISTS `tb_sys_ftp_tran_segm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ftp_tran_segm` (
  `OID` char(36) NOT NULL,
  `FTP_ID` varchar(10) NOT NULL,
  `TRAN_ID` varchar(10) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `BEGIN` int(4) NOT NULL DEFAULT '0',
  `END` int(4) NOT NULL DEFAULT '0',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`FTP_ID`,`TRAN_ID`,`NAME`),
  KEY `IDX_1` (`FTP_ID`,`TRAN_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ftp_tran_segm`
--

LOCK TABLES `tb_sys_ftp_tran_segm` WRITE;
/*!40000 ALTER TABLE `tb_sys_ftp_tran_segm` DISABLE KEYS */;
INSERT INTO `tb_sys_ftp_tran_segm` VALUES ('223132133','FTP0001','TRAN0001','ID',0,3,'admin','2015-05-15 10:27:09',NULL,NULL),('3453452','FTP0001','TRAN0001','PAY_NO',3,6,'admin','2015-05-15 10:28:00',NULL,NULL),('46456456','FTP0001','TRAN0001','NAME',6,9,'admin','2015-05-15 10:28:29',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_ftp_tran_segm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_icon`
--

DROP TABLE IF EXISTS `tb_sys_icon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_icon` (
  `OID` char(36) NOT NULL,
  `ICON_ID` varchar(20) NOT NULL,
  `FILE_NAME` varchar(200) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ICON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_icon`
--

LOCK TABLES `tb_sys_icon` WRITE;
/*!40000 ALTER TABLE `tb_sys_icon` DISABLE KEYS */;
INSERT INTO `tb_sys_icon` VALUES ('00a11813-0fbd-481f-bab8-11bdf0df1a7e','HELP_ABOUT','help-about.png','admin','2014-09-29 00:00:00',NULL,NULL),('01a5ebc8-b79a-4960-8dac-c6543c7fa56b','REFRESH','view-refresh.png','admin','2014-09-29 00:00:00',NULL,NULL),('035d1d3b-7223-448b-a7e8-660d47c5c1a2','STOCK_HOME','stock_home.png','admin','2014-09-29 00:00:00',NULL,NULL),('140a343b-f276-49c4-9bce-69b6c1b81736','GO_LEFT','stock_left.png','admin','2014-09-29 00:00:00',NULL,NULL),('3f70f89b-6ef9-4112-916f-e9994519557c','SIGNATURE','text_signature.png','admin','2015-02-14 10:17:50',NULL,NULL),('4471df15-4346-4c58-9f19-572ceeca4d9d','IMPORTANT','important.png','admin','2014-09-29 00:00:00',NULL,NULL),('45cd5523-c08e-45bd-9461-67064b41052a','GWENVIEW','gwenview.png','admin','2014-09-29 00:00:00',NULL,NULL),('4b84ee11-4e89-45b0-aca2-04db58ccdfd2','DIAGRAM','x-dia-diagram.png','admin','2014-10-22 12:15:02',NULL,NULL),('4c664a5c-69cd-4e4e-8d55-050423d4e0f1','PROPERTIES','document-properties.png','admin','2014-09-29 00:00:00',NULL,NULL),('4f875ea6-6d30-4e72-ba91-56b07890325d','CHART_AREA','area_chart.png','admin','2015-01-17 12:05:49',NULL,NULL),('547c4780-4c26-4552-b5dd-f41ed3fbf6f1','REMOVE','list-remove.png','admin','2014-09-29 00:00:00',NULL,NULL),('5a5279f9-f2ab-471a-a83d-8e5e8019de90','LOCK','lock.png','admin','2014-09-29 00:00:00',NULL,NULL),('5df6aac2-03b9-45e5-b4f0-b6b549813e7d','GIMP','gimp.png','admin','2014-12-02 10:11:46',NULL,NULL),('65b10fb0-9140-4490-9d15-94148be067a8','FOLDER','folder_home.png','admin','2014-09-29 00:00:00',NULL,NULL),('7492a872-520d-48ef-bfa3-518502d48d3b','CALENDAR','xfcalendar.png','admin','2014-09-29 00:00:00',NULL,NULL),('77c3c562-b85c-4a72-b8ac-c8bc94f58df3','EXCEL','excel.png','admin','2014-09-29 00:00:00',NULL,NULL),('793989f2-4818-49d6-ab2b-44bc9ee75b43','CHART_PIE','chart-pie.png','admin','2015-01-15 22:52:29',NULL,NULL),('80d813f6-0c91-4e0b-95ef-a17bcc02e8ce','GO_FIRST','stock_first.png','admin','2014-09-29 00:00:00',NULL,NULL),('81100942-0cda-43c1-84f9-034d39ac8c58','PERSON','stock_person.png','admin','2014-09-29 00:00:00',NULL,NULL),('81959e3c-7205-4fff-8b2b-6bad5770e8c1','IMPORT','document-import.png','admin','2014-09-29 00:00:00',NULL,NULL),('92f6e3b4-e757-414f-a512-9eb53d7d7b90','INTER_ARCHIVE','internet-archive.png','admin','2014-09-29 00:00:00',NULL,NULL),('966b2e28-2168-4172-ac2d-31b429336c1c','CHART_LINE','charts-line-chart-icon.png','admin','2015-01-16 14:05:52',NULL,NULL),('9eac6e4e-3796-4e0a-b9c4-fa337d01517b','APPLICATION_PDF','application-pdf.png','admin','2014-10-30 14:26:56',NULL,NULL),('9f3b3020-b76c-4af0-ad69-7a15c4e5d022','SHARED','emblem-shared.png','admin','2014-09-29 00:00:00',NULL,NULL),('9fb99e36-6ee9-4e1c-9629-8336ede133da','PEOPLE','emblem-people.png','admin','2014-09-29 00:00:00',NULL,NULL),('a9fd2f30-4960-42fd-a175-a462c6f281fb','EXPORT','document-export.png','admin','2014-09-29 00:00:00',NULL,NULL),('ad1d0d5e-111d-4020-ade4-6bcf167fed0e','WWW','www.png','admin','2014-10-22 12:14:29',NULL,NULL),('b4f3acb8-72bc-49c1-a7a5-50106181facf','SYSTEM','system-run.png','admin','2014-09-29 00:00:00',NULL,NULL),('b5d46f00-8146-4e0e-8812-6e5b56843e5b','TWITTER','twitter.png','admin','2014-12-18 19:51:48',NULL,NULL),('bbff137e-7be8-4e8f-a1d2-db41444345d3','TEXT_SOURCE','text-x-source.png','admin','2014-10-23 10:08:48',NULL,NULL),('c739a407-82d2-4e2e-be95-3df87e280bfd','COMPUTER','computer.png','admin','2014-09-29 00:00:00',NULL,NULL),('cb219bbc-db12-4765-8922-ffd76773d907','STAR','star.png','admin','2014-09-29 00:00:00',NULL,NULL),('d1727475-258b-44f6-8f4a-f36cff81fb85','GO_LAST','stock_last.png','admin','2014-09-29 00:00:00',NULL,NULL),('d1fb350e-e2f6-439a-9934-65e1b26ada3e','GO_RIGHT','stock_right.png','admin','2014-09-29 00:00:00',NULL,NULL),('d24d1ac9-bdb3-40ef-95ef-9845b47b0182','G_APP_INSTALL','gnome-app-install.png','admin','2014-09-29 00:00:00',NULL,NULL),('e5d8eec7-2063-4806-b401-5d415dbd6c25','TEMPLATE','libreoffice-template.png','admin','2014-10-21 13:40:26',NULL,NULL),('ed735416-7d0e-4df0-aa1c-7787bbc5953e','VIEW_LIST','view-list-icons.png','admin','2014-11-28 08:54:06',NULL,NULL),('f05b7819-b9ee-4409-8d2f-b9e067280acd','USERS','system-users.png','admin','2014-09-29 00:00:00',NULL,NULL),('feddb360-0b6f-4ee2-8206-b961bcb2a76d','CHART_BAR','chart-graph-2d-1.png','admin','2015-01-16 13:27:00',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_icon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_jreport`
--

DROP TABLE IF EXISTS `tb_sys_jreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_jreport` (
  `OID` char(36) NOT NULL,
  `REPORT_ID` varchar(50) NOT NULL,
  `FILE` varchar(100) NOT NULL,
  `IS_COMPILE` varchar(50) NOT NULL DEFAULT 'N',
  `CONTENT` mediumblob NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`REPORT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_jreport`
--

LOCK TABLES `tb_sys_jreport` WRITE;
/*!40000 ALTER TABLE `tb_sys_jreport` DISABLE KEYS */;
INSERT INTO `tb_sys_jreport` VALUES ('3833dfa6-4cb9-43df-9264-ef98f1e90bd5','BSC_RPT001','BSC_RPT001.jasper','N',0x504B03040A0000000000E51CD4460000000000000000000000000B0000004253435F5250543030312F504B0304140000000800E51CD4468751AF1D812A0000607F00001C0000004253435F5250543030312F4253435F5250543030312E6A6173706572B55D09605C45199E799B6393BCA469D2B47D4D8FD08692A46DD2F4A0B4E54AD2946EBBD9846CD2D2060CDB64932CDDEC6E77376D82272A28DEB7E02D1E28A29643DA021E7880E2858A88880AA8888A88F77D7CFFCCBC7DEFED6EB22FA80166DEFBFF7FFEF967E69F6FCEB7DCFC6B569C4AB2A65838DD9A1A6BBD22944A8493C970229E4CA75AC3B1F1482CDCBA5710FB059189BFB31ED698C7CF2A46E293894834BC2B940EA5D962FF15A163A1B648BC2D184E4642D1C895A1C3D1F04E3F5BA8C402A1C970706A6C2C329D663552381A8A8DB705D3C9486C1C82954A30D9150DA55247D98B98369D641BE6B2ED70280503FB3B11651BD8E263465FB07B7057EF70B0BBDFD7E11FDEDFDD1FF4F50686077DBB7C4C3F1C4FA7E3933DA12414F9A82CD1A9C958577C2A96F6B14AF9164C8446605A867B20329A9E38C4AA23E3B17832DC1742CA503A128F1D62B591D4EE683C94EE1272BBE3F174384982A9E0D4E464283913081F8778F8106BC8900E44D21344DB130E8D86931DB1513355552435104947C32A8D8F9547C36369696827AB882723E1585AE40B564268888C4FC0EA327A11367682814A4DF726A11AE62749C02C6A593A9E30B5551D9F08C702716AC1819944D8CFCA0F87468E8C27510DA369D6E847DDB7A5C6DA1C75DF26EBBE8DAA3D368A765B206B675724191E21B3D26CF39C09D3C8A9AD7FCA4AD01D9B9A841E7DC4567BD4FC45191A5592A20D31EF28CC4D85D3A9346B1A2A60E22E294ADA47C363A1A9683A989E8986D3ECCC0209851892958C86D3A14854995329DF826641CF2AA04509424FCD583C39194AEF0E8DA4E3C919CBC351E59149912A184EA7D902D931A6D291685B50D8EDA54E32859655E255489A26C770D453C564281253852D6C96552B45B1D0A4A9B924261C4169ACB639DAFE50740A75B6A970B3F6AA4456AB96279CB62A8AAD45E14196B7AAACDA0B67D5974923721A6225296A3378C5DA21BFBBC62D4DC9BEA81CAB2C1D9E4C444369D2D1564887C49B0199829415A7A9D7AA12D53A3B962AD5D6C2A53AE048274A369D60EC4BBB99FCE332F2EC65B63F2DE8A4245E9464ED73E12665D59ADD05CDD4358C0177ABC8115BC9F95AB3998934E07FA03F417F183D5A5D22B4EA0C1644F321567C181882EA3E73C80DD64C27A692243A57864AF4AD3F7CD1D3AFB9FFEAAD1AD94BF50643D7B9349414585696CE3D90944C08F815B81D4C4423E98E68347E3C3C0A1F105E4D0DDA3D9D4886532901192D050A6AC9D2909848C621948E84533D2154FB860289FBECE2485F96228BC893D2ACDA36E676CE0897AD926CCB41371676505146CB37936CB3CB5AED8E8627810D1761704958B5AB01E1462622D1D12478B0D1823F7F2425703B6CA5430DAC2F547D36E99DD3E49EB5C29149656B4732199A21BDD3577D7BC5F5F785DEED61DCC78A52912BC3D2498E172957D9E6B2505D7180770C19AA7C1D052B1B31B985002D23D8662A1445CF90F78567D26CCB7C5520D5CE69F7FD33A708F52E3D7F492425C0D817DB1D49A6E0F1F1A8E84387589DE2002CC340C951E166A9436C19E866E7D82586D4DE63E1E4183A4E4A24EA0F4FC68F85FD3090243AE1B3473A999E88A7228420E47D9809A5D2C9707A6482DE7CACF8384D7C7C8C4FE3BF19D43D4D6430798827335E153A8EBA210AAA76917D36805E732C827104555C68E0CC932ADB47690438030660C00F0B0388D0EC679E23E11935D2164DC647C548B102727836BBDFBAC2DDAF07D26A64AD4884D06984314259A99F2DCD90E47CBB3F3C160661C41CE217DAAA5066EA6AB2D667A59299E70737CAA4DECFEA322C511F5D13409C70AAF0944B48E7801E295D3E84FC2471C6CA0E2A370FB903C41907AA56DB3CC7FDD426A812597550343515197582D6207A4666C0D6AA18E772B8AE43B0849E4479AA29589DA03F7743B5BD01B24763D2559366B5BB7D970CF777FB3B067CFBBB87077AF16F9FD2BFB1A07E55B639D49707000203FDDD035D7B008F5516A65289EFBEDEF3E7EB26AF6E03EAED657A348C196A3032DE1949A7F662661ACFBC4D275E72FDD0C4D5ED37FDA5E84BDFF7DEFEE39E8BA16B535EEB4C38C3E3447A32DABA074106D8CCB990C6BC18C446A291446FCC0410D4847DA08BC7610E357AE90846DFF034A0F89CF9E268974C494B9DF031F80B4D6FA56F9B73728B3C10997437D1EBB627313D6AC10466DD5722BB50B4231A194749CF2EAC698F338D525547D54696A300395DB42C35128A86D1DCEEA6DA4192F64D86C64D3B2B8F51FF1CC958B9A5B08EFD328565A31C9D175AD34CD5565F6F48BEE4BBEFFADB33981F1E62C5544B189C694CDE31971B679AADD51A96ADA6B3CD39E1090AAD0B4D77F6F6678FEE3BA7458BB753A7DA5CB053395A78B67EE509F41E80B62D05B565B5F22CEA8AFCDDBB075057ED6EC7FC8C67585554E4635A6414EBA99189A9D89114AA69C8EF76CEDA4549686629DA4D2C7303D60AB3C6A2F68743D10C474CBECAA7805473CFED73739A3CF8CD1F45F67D60D09CE67B50F6B3E75D76A1CB3675EB644554E74078721E65224FB38A58F87883DCAF6A12CBF77E4F9A9576F50606BA03039200A9B2F50DABA7D263E7AC6E16E8DB56B069ADEE355BAB76F97D7D50B5A9A02A472F9BCDE5303210EEBAEB4EF6D9A46DFBCFB9755046CF296C9799840519425F328C2D3F5587280AC112C6DB89743AB1A3ADCD99792A3E951C0963E2341E6E258F2359DB1C5A9B18C90CAD6BB91CDB5A0B5689B96099AD724BD5B096807525D8FA8A1F9B99CFAA56EDA55855B370AE59F3215612C1AC86F6DE96D016417F5896B927924AC1ABC84C74BCB148383AEA6623633709D28C662C12C5064B16CE0F89F24C25DC6832275F56AB0ED1464D126F500C0D2D8534F499C2F9A770D820393A154ECE149E075E4C6284214955399DD88D8C66F6BF5223C948228D3D3C8123A6A919AA1B5383A6F04E4A099EA84757294D6173FA47F96FC4D6D1B11036BD0F8BEDA7E6424AF62B59E8589ECF09AC69E9F985C7D503B90AB2768E3803B2AE2D84ACB254DAB93FBE2FD0F2BB8489A75EF484F52E7B82D060F58362AC5046C3B25D94476A76FFCAEB25F31F39D0F30069BDBE5D853678B276479C5846C5300DB0ACA2AD02A23664DBEADCA80501558D3FAC05ACE98C1C2712C243D3C2CA92FDBEE0B06F97A04C17162F1EF00DF8BBDD4A9BC3507EF9DEC35760F7CD293F08789A87355DBB3A06ECD6D408E9D4D1682B4D6F52E9D064C2A17F709EFA075DE8277A7967B06BB8BF6F60E3C676F8754B21BFB6606935D35FDBF285CBCF307DBB0E1EB3D1A56F67B4586E5326B6FC76C79370ABC9441AC539C416600F7026856DE45D18F462B409B8586D34880E9D85CF053B48552C0C65A3D49F03FFE36EC339D565557F775F6FFFC0B0F09D4B0612B6EA6F9EAB66FAE9D59C5CCB5437488D754A635F477F474FF70046BEE19E8E3EBBE24A6BED08F31D8917EFED08F675F70F4B1DC17C666D747B5499CA675DA53D03BB5EF747A00E7D0BADFA0B74770D6090B7EBACCD78306C89C9DD6F47F26A95BCA7E312A8180CD82DB2AF8C7C28C97838E9485BA3D2A2D7740C077B07FBBBBADD97479C0005C59891D7A06057BFAF0FE0E330A86D6E951D87B1AD82E32D7370CDAA79A5D9DFDBD5E177585A6DF9833F4E3361A74BA874FDDDB28CC39D83815D4E054B2D05FDF61943FEA20DF87ABA870FF5061C2A6A4C15126C0E61C299D7AB77F7F6F77420EAE81AE8ED3F68D330E774512ADE6D3F007468AF35BDC8DF110CA2863A7675F7DB75D7599E20BAB23F1E1ACDF286654AC560BF7F784F07D5507F3E2B1B4893301582B4E9139ADC13A2AA4AE6B36B91596A9FBF5B34807FBFD3B00D850B8DF37C6A9428360CF23747774F9F1FC01FB4EB5D643507366CA3797A4E45501AD6EDDF654F69DF98A2C1DB91A604E50020B9063935B34711D2B3F4BDFDBEFE81C10EBFEFD03CB462FA1749A6A7E8824496D645343BB828D0DBDF0DF4BCC817E8C8C693DCBD12333D13710FC3DF143DF5E1816716A4DA6ADA6B94B3A444E1F1785A466B68392D0E44B0C8D523B218AD57C6E39358E29AAFD3B6E79969DACFB11A604F283501842F2E7DE49E2F2CBEFC5B1EA6EDC65D06F8AEF435BA893081E110A705A3D3890B2E941BA5C7BD0845A664C3BFD38C6FA4877FC2FAF656F1F82FA24DCF635E2AD613B6D5BD6D6763DD909BB588D81EC8398A178791EBE69E7E38145CF85EF6FEA7EEDCF24173FEA1CD639FC65263DBA7716E5398738674FC4838160CD3DA0DB50CF4B06D87764D803A4293215482104425D40EE5DEC611DB1DAB5361EA7B0D2D0D6398E2341C3E3C7C2C429397062C5A92E10678D37972FCE6E59AF42EE16518F0120935B09F73E2D2B75DF3C18F3DC807CF5C53FBE017037F469D3517A8336B7D74F8C997BDE0BAF52777A91A137795DA5CD698A9C4E5C21CE749187B46A6A262C3AE935546622349B1E947B3AFDC995D272B83EF860517EB6F99769E9725BACC44D6658970F60CB1CAB4C33A5CDA80799DC33A91A3AB4D589F3D99CAB33EA32B330A644F2357E58AE4CE2931D78DC4704A118AE69BEB3A27B6E554778E125511659E47D1FD489229C973DA743CFE0CDDA87075446235D66CFB6A25C183410C678984AB9D3A7B53CCBAEF17A0794A2203EE3C0FB82FA53DC99CA962537BB318263190740F07067B3ABBFBDDED9665EA74B642CAA18FDEEE947DBFCEAC467AA94F24285A69335A9BAFD1955DBDFEC19E40C66C5278262A83CA922F5F21B00C2B483185CEE4EB995FBE397617B94FBF51D8ADAB59813043A85B3B5B35B5677229769F4B5E2B4BDCA79756965335DA6C6C2D6C63A9FB3CF2DAE8759F5ED5A4F4006965C6014A2455D84BCE7C6E4167CEB33736676F1BF4FBE9F9BDEE0E4D6D77DD66D158E6C7543CD8D5D1D7EDF224C0BAD3368B462F86AC011F9650E600BB4C846BACCB2BF3D8D418C0AC216BD3AE6EEE2B1F9587E3F123B83177C41F3E168E76661F7C76E2007306594623B1230321ECE2A731905A143C88DB1DEAB4996EC09AA7B6B891118A8DE0F413F09C3D74641DBB5A6346AD2377397010EBBC7C03A991B1A343649493C9928C00DDAECCD94AAFCDB0337B416E2E20ECC94945BBBE195DE6058DDC322FB3EA0D73FD74243187C1F9AE61D08C051767243330158D1299A61856E3A801D26B368E7A2F4D84D2B033A65E17CAD7EC0CA631C8CDC3CF722E17AD9DCBD37019F5709CBA81BA245321DFBAACEB34E605EE4EBBD84245CB11AE948CBED0E82830C0794540410FCDEFC7B06594991FA8F760E44A7AE797ABF714DEB114B3291037BFE94A4CD6E13D955719B6340F2BE3AEE7A3B4682B2C8354137923291F242323EABD12B7A546C7BA270F874747C3A6D002D18F2247C20313E812E3138AAC834CB77F46A9D215AD22921A8C8D0A57094B92BC53EEA8BB0544C9ADB90A22AB7A53D5504A7A3AE3D385EF19FBA520994069D47D7A9561B58DE47ECEECB712A9595F09E1D15422E3BC0E5BCB6819349E0C2526B02C7771802544C5DD2A54786C244EAA846649DAED701079B1DE598DD59294EBAE44CFAA486F322E870F95B6D27C57D5D156B83AFAE3F6CBD6E26EBFC39C2A10728D2907D569CA4279A123D773179B8C5CBFBD500C4A4DEA72F21A4C1B9C779DCA33779DF85082FEC4FB66F3E16CB940FC68C9EF8FBCB4F10F8FFFED60C5576F3F6FCF77481D24C4EC752C82C3E3E84C6A2A364E47B20BACF982E8738FFCFA2B37FCE0DC679FC6AD8EDDE64D91A47D572330853E93BCE6E6B7AEA878CB63AF16ABC88ECFD1F568CA792FCD28BA03D811A2B7AE840CE77BA959B9B8856B1516DA383D5111C3B142F7FCA09DDC115A21BC5320DD7417610798C9C237603B6DD23BF37760224137BDC673BA8C27A138B338ADA059A9737DA90414933F8DCA747F4518A6779AB564D5289BC75740D0E048CB2DB8227B0E43558B4B550E3D25738F56659487D5C3144100B1D9114DC23CD6B77E3389E8DD52A9FCC087CA1242FBA0A9E6E1197D36697536AC3A233F9A09C612F3D89642B5FAE14B59CD456A52B9DA13A95919EEE712C8B11F0EE83A4BF7B361681E88275CEA4D24E6A33A33B258AACB31E28ED16D696A645F6C54222EBF5C0DCD364ACEE0C94790D646A2198A72889ACC7067738B4D7E97E39EE51C6A68739AA1A7647E1D63980E2A5AA5A27586E9BAB329980E1D0E020484AB9AB0A16802F41BA613F427DD4D9DEAFA28A7487A66C39E84F527C616F527478D3D99556559F6AAD2631E9927EC7F99AF67367035F0845DDDDE737E8833DB720CB762693D1D2C74ED0CE7BC183E5BCDCF86BAAC2F271DF7106A4792F1540A556513A0AC66D0B4EAABB75CCE62F3DBAF5C2E9F969D8EA9EDF5EAE35E0AE9717A56C654127BE49D37FF7EC95F4ABC038FA9BDD75DBFBCFF5F777E06EC76ED059CADB2CEE087DBB7B46F6FDFBC7DFBF62DE7B4B70F6FDEB67DF3D95B4AE9E2736B5EB7333FF24CB55D24EE5AA95B92F16429F370B6745C10C5C45A09C8AB0BA5AC98B316B9CD4AD2C3052CE06C49E6F6D2B0E3EC9117E810B84A15A5AB5588AC351B6795963A6C743BD43B0E95395B6E7172CF89385B66B1B38E24615A9DC5B49D7071B6D2A2E71ECA6569CD3EC3457359CCBCC7F39CD55B123947DA68956CAE3A9987EA15162BE71493EC6AB4F8B31F513AEA2CF75CDB51FCEC43326961B52540A77C94738345CA7BAD20A75E720F781DD59A7D56EF60669F663AB2CF7B848D5295897B7ED29D5ADDFAA4D8B0217F9489C52D1B52A0CB7779C5C864CBA292AD15F25D5CB2C9BC75D19B252B5565B884A5E02E32AFB60D673693A97ADBDD1A6C1EC4C0E6C519558E2D5EB8BDC9507565D670AD236F935A97A5C6A45737A668353422F6FA7DB1B13810C38FCBA4C09CD1F044682AD5A6D005AB0A75A8DC96918579A5C3C3D0D0D3C571F31CAD83C60B09363ABBDF8E4B3D2603692A8687D3D87B0AD2F524A4DBCB59B38D323CBC69F3F6E158188BA83DA104666B19A8DADABE751B6725E7D291C9F99C799A9AF7EBEC6CB6AD1C879FE770B6B011FB333872880623E9B0F86C0DB0D5D43C94BF385331CAB16D04F229C8B7990977EA6C07DB59CE34762E671B5DB65706F44A190C6B9F2B4379D5833E2624898ED1504224BB90B3F2117C033210A7A193B36D4DB6DD1209E73B6D145995CD3932B0BE937595B30EB64B675E56568672ECD659B97CDAA3B30AF9B45767BA7CF2EBAC523E057456259FFA74B6403EF5EBAC5A3E0DE86CA17C42A5D7C8A74B74562B9F0EE96C917CBA546775F2E9793A5B2C9F2ED7D912F97458674BE5D3A8CE0CF934A6B365F2694267F5F2E90A9D2D974F519DAD904F31CED6BB6C0FD1E14B5942672BD92A4A9BD459837C4AEBEC0CF9744C67ABE5D3B4CED6C8A72B75D6289F5EA0B333E5D38B74B6563EBD84B3369716983DB894BD546767B1264AFE729D35CBA76B74D6229F5EA9B375F2E9553A5B2F9F5E834E4CBE1C143DB3C7EA524653F32C9D4A67AF63AF27A77D83CEDAD926D2F2268E33EE8908929DE12F30F01334C27D27E2A36AC79FB3739A720FB873BC2D9F037AE417D71BE6D5E94AD9F5800522774D25E96B3CCEFA9BFCB3CD6B720D71679ACEDEC9DE55C1DEC1DECDE5C92ACFF7A31AE061A73765F12C0DE0558D4CA5B0D8C6F261D4071802443465DD6A9DFBB5793F2A88667A17CEAB7CB394E6C3549A1B49A3868026835A6292E75CB405796C12C13104E5849E02AFA8884D79CD2BE25977CFBCEC56540BB50F79C673AEFEDBD9A7C8E03B288F622E6E3F78D9697A2BE159F70CBDEC6EA297F27C1787BCECB3C4F4F29C1B6AB0F4F3C42AE38EEB4E5EF625A296F37CF7B348DF97895DC1732EF3C18EAF124BE7B3DCD2F4B26F10BF92E7B9D7E865DF225E15CFBA330AA50F126301CF738D8DACF91E71ABF95C37D4BCEC11125AC8F35D6AF4B21F12B386E7DE8F92763D46EC5A2EAF77518E3F21C2229E7D9BD432F84912A8E359B7FA2C6B7E41FCC53CE77A26923E4DAC253CF7FA9A97FD86584BF92C3716518EDF4987551F1B90B441C1329EB92BED657F264A3D9757C589F037222CE7EA7A3799F04FA2ACE0EAC234FC86DE57E2BD4BBE7BE87D15A5504A7909511AB85C1D43020EE5254380EBC28C33B8E3A2809757127535374FE233F46AA2AFE1CE936E2FAF2572239747CB26713111CFE4E659AE493690B73AB60BA3059A7CCDF990A9752EC435E71B7470931CC32746E79472D4475924D58B4D0B50C574EA90CE57F18672BE92A378A59207DA1A496BD4591BDB88A185AFC550341A49D1F8368A412A361A4A8E66C6229D3749F9664E5D741D95090916E69C1E95F256E422B6A07BC7E00354AEDC23269D6FE4EDE5BC8D6F02AE891182BE624E4ED18D1A9D6F1188C2B752266751D0444133052D1488DCD753B0810264B8B6C92D58F10B48F58570DCEC31A2947792B6360A360218E9CB352FC747237B5D61A33B03F816BE870CF0512EED146CA26033055B2810853E9B826D149C43C1760A765040A01F19C5F4DAC7D53E3F3CD6F4A2DEE828C99C4BC179149C4FC105145C48410705A28C5D14ECA2A09B82DD145C44C11E0A846D7B29D847819F821E0A0214F452D047C1C5687F33EFEE143C12B1B0A09F82200503140C522086A003145C42C1410A0E513044C1A5145C46C1F32818A6E0720A42141CA6608402A13E4CC11805E3004A9A0D35C26FD38D91D8315CE46BDCCCD95973F59A8B6473FBF07B35A5FC950E47503B2CFC5558BC340DE5365F9EFB8162B5C25F53CEAFE5AF451FC7F6160E8743A374E796B3F3E65662917CCD43F99CE5F5FC0D98F5F337A21FC9C2F588F95C6F4C4DA8B0DE1F6DCA5940B89A8228F3875CBAED9BF95BC892B7A2BB5A158E6D3651DBEE6695FB31702185CD2039006056E86659349F52A151F8F5FC1D64F13B1D168F4B8BD7E6B1382FFC2E1ACF6BF0C5FF5DA5E7ADE1F7F2F791BDEFC7B0804CD5DAA0B6A9393B27887E807FB09CBF9A7F08E3128D5EB6B5C4BA3C3539EBEA82DFC83F42597E54671B582B0D011FE3ACC9ED364129FF38FC1DA66628E4EFB9AB59F7FB0E3AFF243F51CE3FC16F918A6D83CF6DECF5C4B81D8C54D85EE0654DF94B27DC4DB7771AACA3287D5D5EF1527E67419C9FCF7A49E7A7F8DD15FC2EFE69E0338A62FEFA46C189F65C1EF259FE3952790F54A6EC2AB7BB529923237AC917F81749E797A093F67C8E8712720154D538E2DC735961A5A7D66B0BC6C7D2993B3B30EECBFC2BE441F7C37DCF1D898ACD1C9DDFC7B651B57F5D675BD916627FB318CBA4BD47061FAAD3D96649FA0E10383585A2346E6AB4E643E737B9FED23E5F65B9DB54B076DCF9F7745ECF1F2AE70FF3EF67EC696F4CC765E501089A9AF32D2ABDA684CE7FC81FA54EF923EC91C9E49B1B93E171804E38291D90D6EC4E6F451930CD8A3B9D7641562A9D3FC11F2FC7EAED276815D3B0583C1D1943AB94C8079D3FC9B651EE3FC790EC94E9A0D55D59E659E7BF9492BFC26CCFACF69C415CE7095919CF606BD82C0D7C2EDF67659C6D9DBBADF22512255D328B469DFF8EFF964AFC7B7461D3C68870CBD8FCD6E5854F96E7B818296C2C42BE30E8CFFC4F541D7F4137310DC274B5F0409271140FC475FE0FFE7752F34FE096524323D2EEA99880C2E0540206A2945D7960347F51C654D2545B9612645993AB5AD7F04B31B040D33074987E3212C5475ECAC373FB51B160EB5A89560CB7D14A21683A849939EDF7100A3DD7D9055AD9AE4AD72AB47234BFA663FCCB76D1DE289CF3A0704E6D01129A85381E22F728428442D60817D76A617DD35E5FF37E50B43AA22C068E991A27E8732FB3CD4D17E06CE7DC9399EEE991B0F848357F33D7E5D5AA6BCB34832CAEB7614B6690B75C6A3205A4B9D4D56CCA0D25BF2322135D6BD056913D6760D07B8ED9E5514E6AD790DA465B31F18D88F8C20B58693EEADA59B2819A8088A633E1E2CC6ED309A2F1D454329C41CD0B9BE6DCEC97722A51DEC920A06E56F5BAB65E5B47DEB6E139B9B0ADE86D54745A3836ED952EB789CAB8F9B9ECBADAB46E25AD67DBA03F7C149FCCA568C7208F8E4318128400CAB543DB4E06ECB435C6043E44EB42358A7D011F9AC424E8DA05DAF9247D21961A8D2349FA8D3AC7A1CB703BADE5F3AE7FBC5A85977DD2CBFEE0E515500CBFF66A7B308E9DDB38622DEA1BCFF76A7BC5D4565E9BF36A7E39334287568400A0255FD69C9DDDD43CBFE31E914E6C5A4FE85AA7D685498816C4B9D1FC95946A83D602C7C2E3595782DA81726DBF7609CE91E7982E956A87746D0F00551BD22ED5B53EED6232F07979DB54A9BD9C644300B490AC928DF33E00D346B4D132D886C62F19A132A0979E5960F832CFF61664914AB5083679C6E425755D3BA24D946B57681804565A8281B810C55762BBE94794BB9349CCB3B418CE83B39405E269219181D6522D812D34B10048A542E3C82089B9957654135E3F5BC54F956B71ED18BA9F74F01AFA843A7360334001CA41778CE45D48452997DF7ED3D7B93841D270E688EA614D78C2391B6388CB555CA1625DC5952AAE52F1021557AB78A18A6B545CABE2452AAE53F162152F51F152151B2A5EA6E27A152F57F10A19D35998881B547C868A57AB788D8A1B557CA68AD7CA988EB144DCACE21615AF53F17A8AABCB698988F85ADA2D44FC523A90425C46D378116F1571392D0418038FD3212E7E996E1A4AFE4EF922FE61CB49B6FD143E4BE03DEB6B2E38C5BA3FC72E6819FE14BB68FD011ED860A7F8361CE03B8A8C223B6D9F510462B1516C27F618C5209618257662AF510262A9516A275E6C9482E835BC7662D0F082586694D98983461988E546B99D78C02807B1C2A8B0130F1A1520EA866E270E193A889546A59D7899510962955165270E1B55202E3016D88921630188D546B59D38625483B8D0586827868D8520D6183576E2B8510362AD516B27468C5A1017198BECC423C62210EB8C3A3B71D2A80371B1B1D84E8C1B8B415C622CA9392A88478998329680B8D4586A274E194B41340CC34E3C6E18202E3396D98933C63210EB8D7A3BF1F9463D88CB8DE576E20B8DE520AE3056D8892F365680B8D25869275E65AC047195B1AAE66582F832225E6DAC02B1C168B0135F613480788671869D78AD710688AB8DD576E2AB8DD520AE31D6D889AF35D61C68B983BD7147A3D148EF6F361A0FDCC2E887186710EA70FE22ED61F616F656A2B1B7B1B7CB8EC02E6645E29BEE5A740238FC759B5AD6AD3FCDDE53C44EF013D4DB44F2067190B15026174FEF65EF831A7A7A3FBB015257E2B98432618B117E807D50A97F21521623DE00F5A2F77C08199C661FF1B00378F9E8A696F599979B36B56C902FB764326E11B6AD97198BA78FB19B91013D7D1C4F9A78FA049E3CC2842A919D0F10B71760B20F929F6427A4299EB5E02D85E21B600A3AFB2D9BD6D5DC769A9DF430D3AF021BA8E22EDA70607DCD2930EF7430C97862FB8C22F0EF02FFD30E3EA100F1F719C5E07F06FCCF39F80408C4EF314AC0BF07FC2F38F8840D12284AC1FF22F8F73AF804131233BCE0DF07FE571C7C420C091F65E0DF0FFED71C7C020F8924E5E07F1DFC6F3AF8842312542AC07F00FC6F3BF80429125F74F0BF03FE771D7C4217093595E03F04FEC30E3E018D449D2AF0BF0FFE0F1C7CC21C09400BC07F14FC1F39F8043F128BAAC1FF31F88F3BF8844412961682FF04F83F75F009942442D580FF33F07FEEE0133E49B0AA05FF29F07FE9E0135449DC5A04FEAFC0FFB5834FA82521AC0EFC67C07FD6C127009368B618FCDF82FF7B079FB04C02DB129BFF2F260FD7DE2FFD9F9EA4FF0B5FDFCFEAD03B0630BC0EB20EBC85D8017615BB041DF220BB871D628FB3218EC51B3F835DC63BD8F378880DF3ABD8E5FCFD2CC4EF6187F9E36C048BEE51ED0C16D63AD8981662E3DA556C027DE60F669FE107D1672A1097C93EF3C74CB730314EF69914F5993F81F91707D3EC3353A2CFFC15FCBF3BF8669F392EFACC3FC0FF97836FF69919D167FEBD695D2DE70E01B3D33C9F3A4D2DD748A2C82161769B171A5E92282689528784D9715E6C9491849724CA1D1266D7B9CA28CF6E1AEE954D239E3E9E699AD5CC8BA6398AA649A26952689A349A660A48798C7D911D674FB269DCCDA830AB993D8B6AF622BE99AA1946E864449530C2C47859D1576F201B17107BA1836D56F52B8C2292A82189450E09B3B2AF358A49A28E24963824CCEA7EB55142124B49629943C2ACEFD71AA5D935C13E266B423C7D42D684E015A326AEC29CEFA5AC9BBD8C8DB397B36BD9D5287D3D5F2E4B5F740212CB00D09FA1D263D675E0145F7D3D5B7A8A9F793DABB9959FF50EA69FE22DEF602537F207EA3DEF63A54537B222CFF56C612D5F4FE50EA0CC37F25BEB8B2CD62AD8BF61532D6F2B3AC5379FE638BDA4E2078C62085E5B5FEC14DCE610A45A08182510BCBCBEC429788E43902A23609442705B7DA953703B097A4C41F2C180E1856055BDD729B8C3A1915C310037BC91FDACBE3A2BEF9D0E95E49201A31C9277D55767657EAE4327E17AC0A880E41BEBABB3723FCFA193103E60E8901CAFAF2EB324CF84E4F99B5A6E67579DE61D5835F02E7206DE45881F302A21BFADBEBADC926F86FCAE4D1043DC4DE96654BA8B4EF3BD458C0682805185DAF87A7663D218103016E46BCC7D8E42D1601030AAF335A6DF2148A342C05898AF317B1C82343C048C9A7C8D1970D4128D1301A3365F63F63A34D280113016E56DCC3E874A1A3A02465DDEC6BCD8A193069180B1386F63F63B74D270123096E46DCC609EC6A45973C0589AB7310754630EE63626CDAB03984B6FC8992D7AEE9678404F7C3F3F40AB58CF1DFC1276839AAADDC95AD1EF5F07647803D66F6F62E7437E1F7B1B10E23AA0E53B407D177B377B0F3B8D99E6BD48F504E694BF611F066A7E841BEC26BE95DDCCBBD827F83066AA93B837762D6E52BC9DDD8101E2144E79EEE4CFB237F07FB2376915EC2DDA52F6366D0BBB0EC3DC3BB4CBD8BBB42BD87BB46BD8FBB437B31BB44FB20F6A77B30F6372FC11ED4976934763377BAAD9273CEBD909CF0E76AB673FBBDD338ABB64D7B0539EB7B03B3D77B24F03BD0EFEBFD1EB905BF41A728B5E97BA45AFCBDCA2D7F35CA3D7B06BF4BADC357A855CA3D7E179A2D78872F8D1FF157A85DDA2D7985BF41A778B5E136ED12BE21ABDAE708D5E475CA357D4357A4DCE13BD62AA31E3FF07F4BA17E8F565A0D7FD40AFAF01BDBE01F47A00E8F56DA0D78340AF87805E0F43EA11A0D7A340AF1F01BD1E037A3D01F4FA29D0EB49A0D75340AF5F02BD9E067A3D03F47A16E8F53BA0D797815EF703BDBE06F4FA06D0EB01A0D7B7815E0F02BD1E027A3D0CF47A04E8F528D0EB4740AFC7805E4F00BD7E0AF47A12E8F514D0EB9740AFA7815ECF00BD9E057AFD0EE8F547A057E2FF8D5E47DDA257D22D7AA5DCA257DA2D7A4DB946AF63AED1EBB86BF49A768D5E33F344AF2B95C33FFF7F855E2F708B5E2F748B5E2F728B5E2F768B5E2F718D5E57B946AF97BA46AF97B946AF97CF13BDAE568D79CDFF01BDFE85FBC274B154C3C744456C1F2F61E358C5A67939664E3A3E5EA862A77935BB97D700AF16B1DFF0C5384D5B8A5BC2CBF856BE9C77E1EAED306FE0937C356E1A36F2B7E3BAEE69DCC9BD8FB7F06739C395024DABE045DA525EA26DE15EAD03BF447819D7B52B7895760DAFD6DECC6BB44FF245DADD7CB1F6305FAA3DC9977934BEDC53CD577AD6F306CF0EBEDAB39F377A46F95ACF35BCC9F316DEE2B9936FA8E6FC15ECED99DDC532B141B88F362F6BB496BBF8B5078B3ECD5F7DD0B32E58FC69DE75D003CF0E1E2CA2F0247F5DA6C63D909172EB834577971FF4787A4FF1379DE26F9355C96D9B9EBECCB2D697D9F4F4C94DCF6A8E425FA74C9964E562A373279922A0711EF69CE2EF028EDE9295F58E4CD63B3259EFE097A8ACDFCDDFA3B2BE8C9509F9AD84E1F3CAF506B3BCD6327E7326D3CD32D36A9AA4BF9EB212344D489DD57207FF708DF621E66D39C56F3A712BBFD9BFEE3EB680A8A7F8AD07FDB7F19BD7DDC13F7582F4C0D6DB320AD62039956309DCF9CD07EFC50074A0E520B6ADB1614D1433C11DFCA44A50A3722C695907995B14FF54C6050C56240A5F89BC4FD3DE35FF4C1133D57C3653474B21446A7429769A7FDE9391FA02BF6E5665F716319967195D1D5362758C8B42D0AED83E7E1BFF2A89D025F4AF65445623B722AAD453FC1B6517ACBC8D3FB0F29245FC5B1D0DB7F1071B2E318BF15DFE506E314FF2474CCB7EC01F55FC852ACBE29693FCC726FB31FE78BEE43F35D5FF0CE6E4267FCA64FF222FFB6993FDEBFCC6FDC6CCFD59FEDB7CFC3F98E9FFC8FF447CB90924FA4639AA7483517492FFD594F91BFF7B3E1DFF32F3F83748B97CCDA3F85A91569C5B04CD6BB2CBB472C5AE651ED1B2A530E1A456690A54E52DA3566DF217E6AB226DD12D265BABCBE82FC23FA47FD58A93DA125360A966E43560B999C10AFEC13C6DF06193BD525B354B1D6AAB2D99354A669132C24B3227B5334D89B5794BD16C1AD9A2ADCB6B64EB2D991CDAF20AB49FC8D4C326B31A954049CBAA93DA164BC1D6BC0AB6990ACED1B6E76B87736F56FCF3B4F3F394A043B2CBE942853AE46983768E784D4BCD75B5DAAE604BCD876AB56E441FADD57623BAA956BB08D12DB59A0FD12919DD25A3CFC8E81E197D5146F7C9E87E197D5D460FC8E83B327A4846DF97D1A332FAB18C9E90D1CF64F4948C7E25A36764F45B19FD51467F92D15F65F40F19FD5B44D8E05671B18ABD2AD655BC40C5352AAE53F152156FA8D5F651BC4DC5E7A878BB8A77A878A78ACF55F1792A3EBF56EBA178977AEF56EFFBD4BB5FC53D2A0EA8B857C57D2ABE58C5FD2A0E2A3D03EA7D50BD1F52EF432ABE54C597A9F8792A1E56F1E52A0EA9F870ADD64BF1887A1F55EF61F53EA6E271154FA838A2E22B547C44C551154F2A3D31F51E57EF47D57B52C52915A7553CA5E2632A3EAEE26915CFA8725FA9DE9FAFDE5FA0DE5FA8E217A9F8C52A7E898AAF52F14B55FC3215BF5CE9B95ABD5F43EFB7C8CE43977D32835691E85B8B6BB57ECC1BF6B59CD206EED2F61FACD1D0D90E9E90F2F4D9BA92DF223B1B6BBA957FF55EB610E11DDA659F43827DF7B19A53DAF0BEBBB4A183487A184364CBEDDA98D4E0D5C6B509A561AD1A966B90D5E409FF5D5AFCE0BA3BB4F449EDF8674933619E7654CC509E2FAE7E94E237B3F37E5A3CDBFF0221F39326F87D17F90D4497A2FC07504B0304140000000800B51CD446E444B36CB10300000E0B00001B0000004253435F5250543030312F4253435F5250543030312E6A72786D6CBD56DB6ED340107D86AF58563C00C2B9DAC1A992226883A85468D5B8808450B5B6C7F1566BAF59AF7329EABF336BBB2609A1545CFAE26467CF9CB97A3CA397CB449039A89CCB744CBBAD0E25900632E4E96C4CCFBD37964B5FEE3F1C5DB23C03750699549AA04A9A8F69AC75B6D76E5757AABCCA5BB92C540091543368A5A0376F69A5BAB7CC79A3BE582C5A8B7E0B15DABD4EA7DBFEF4EE781AC490308BA7B966690094207E2F2F85C732601A5DFD13EBE44E2ACB3CDC40B4504049CA1218D3D7D3838BB3530FDDA444B07456B0194A674ACAF98A920C4F1F79A8E33175ED5E757E0B7C16EB3175860E2552714875EDFF314BC33C60198617485124E9BAAA8048BF636AC61188155148B27ED6325B3BF9526B99AC098A828768D2E939BD7EB76F8576605B36442F2CBFE304D670E00E98CDFCD0C630F61F3E18654A62B47A5507C9EBB8AFA44C2899335140D518ED5BC0CB06793B6EF5138E2904685035F0E4E8101322588E0D76C9E6AC65F2DC9A6A850D69BC7D300A216285D01F0CCD649929C84DEFEE8F1E7D3E387CE5BDFA4CE9972FFBA3F66E9831D96E6C9AD3D702D4AAE237F40D4D0E02024D9E9148C984F8FEC59C1B02B28841014137C78F4FBFE1CF355A33A49B3CA3888308EF1C53893E843C503CD31BD194B16C5FA34E2DDC36F5E1687A717FD6BC23EF78725FC60E4EDE7B93F7DE0E7327FE2596EA5F9B3B9F4ECEEE2F9546692B95F957D1F27802380593EC1F9B3BBFDFE8CEFF7F749A6B01258D8F9395C4F5E0B571EEE699E0DA5B65E80846073A884B7BA8034BFDA62630E76A484D042490E2676E4C7B7D4A5638FD70A82EAAF1FCC21DD286BCEFDC0CDB8839C38E1FF8966FF743CB76C3BEC5869D8115F507BEDBEBD9910DDD72E0D5661B23E6FF2BC167A939611BE01354E59EC988448879BC2FD318E1188DC42A2FD21946C5AF50D4B329E1F96B29D009AD0AC0CF4E184D9A2FF85188845CAFACB706768A57890F610835BA71A9BDE65325FA919C5D43F6F19B6FE5CB7F5D566617F46145FB23C3E6684A5396ADAE9699E69A71F173DD1CA7734BE10299643285F4C6E5DFD4EF17E5737A83322BD8F0FA630CE961E9CB096E4391908BBC4AD14D853B76E084C38E8B150E23CB0E9DAEE5768643CBF107AEEBF4BB8C456E93CE38D88B7522EA75270EEEB8AE189D26B4BF587B3678EEBEF6ACABD57B0F6E2802AA0A1C089E61EE7089B9C28664C274ADD9637055A16683D43C68849E2C5FE9F55C1CA012D2EE6AA61416A41A3B4FB0AFEA397FFD9CD042634E9F962DF62B96CA4A735FB7DD767F6C75DF4DD38DDA6B7B2D82BE03504B010214000A0000000000E51CD4460000000000000000000000000B00000000000000000010000000000000004253435F5250543030312F504B01021400140000000800E51CD4468751AF1D812A0000607F00001C00000000000000000020000000290000004253435F5250543030312F4253435F5250543030312E6A6173706572504B01021400140000000800B51CD446E444B36CB10300000E0B00001B00000000000000000020000000E42A00004253435F5250543030312F4253435F5250543030312E6A72786D6C504B05060000000003000300CC000000CE2E00000000,'vision jasper report!','admin','2014-12-20 14:25:10','admin','2015-07-08 19:28:12'),('9cddb65a-9f3b-4984-847d-c93baa1c357f','BSC_RPT002','BSC_RPT002.jasper','N',0x504B03040A0000000000194BE8460000000000000000000000000B0000004253435F5250543030322F504B0304140000000800A648E84672D23AF31E300000049500001C0000004253435F5250543030322F4253435F5250543030322E6A6173706572C51D09605C4575E6EF26D96C7ED25C3D7ED3236D4349D33669D383420B3427DD76B34977931E49216C924DB274B3BBDDDDB46941E4BE4144E44601413C505B28B49C022208A2828A222297A8A8888028888ABE37337FFFFFBB7FB37FF18A76E6FFF7DEBC79F3E6CD7B73FDE58E3F90BC788CD4860389FAF870FD29FE7834108B05A29158225E1F088F04C381FA8D0CE86540C2FE8E7C5E223637291A8C8C4583A140AB3FE14F9069EE53FCBBFD0DC148832F100BFA43C17DFE815060AD9B9409328F7F2CE01B1F1E0E4E244839270EF9C3230DBE442C181E01C26241186B09F9E3F15DE474224DC4C8D2C9641BF0C741406F3364A902D6B988D2E56BEB69EDECF7B5795D4DEEFE2D6D5E9FABD3D3DFE36A75117920924844C63AFC3160E4C2B684C6C7C22D91F170C2458AF99B2FEA1F04D192D8ADC1A1C4682F290D8E8423B140971F4AFA13C148B8975404E3EDA1883FD1C2E8DA23914420868471DFF8D8983FB6D713D803E4815E529D046D0D264611B621E01F0AC49AC2436AA99260BC3B980805441917718602C3092E6833298AC482817082D50BA828E3101C1905A90BF185C9D80C08506AA23306AC41FC1812A84D2D4C44A22AB7923DA381B027823DD8BD371A7013E7807F70E7480CD4309420356ED07D437CB8C1A0FB06AEFB06547B7808FA6D0AD74E6B30161844B11264C5A40513505383775C2BD0161E1F033EF2A04E7BD8FDF6240C9524607DC43104E2C603897882D4F66511B1959322F7A1C0B07F3C94F025F6860209724496828C0C8AE50F0512FE60488853CCDF7C6A438FCCC24510029FF2E1486CCC9F68F70F2622B1BD9A8583CA8363AC942F904890297C608C2782A1061F93DB8183641C7A56909740D1041A86414F4563FE60583436BB589A56EC61FF98CA393FCC0C41702CD519DA167F681C74D698BD5B3B4521AD579D51A3AC02A2EB51B020CD5A4555CBB357D5952CC36AEA23F971EC33B08A857D6E6B9D5B10E763511856612230160DF913C8A3211B0FEE6FBA7909649697C0512B5A54611C58A255ABB2B76AABA11C6BD94494906FB713FE477966DB48747F92CF08899E1E23CB27F39B58557DEA10544B9713027EB7040DB11E8DAF3E15194D80FBEFF64621722CB6E89DD15568BEB96072DF9C3FCA3C1A7385BE682898680A85227B0243A0566628A8A3B689682C108FB3515897A5AB345A8C32D158048812C140BCC30F2D599AA570979E1CCA17C65122EC9C0429D585B1E6BDCC0A4A385AEBF365D9FB9CB551EBEE18596151AB6DA1C0180CB713C05F4793DAC5A1EC181C0D868662800319358FE20EC6992B0C68E540034BB2A94F47BD76027BBD82D906B2AC6F8AC5FC7B91EFC4993F9C7DED13FE1B6D84BA883D1EDC179840998AF7D8318542CB2D36CA87EE63B03B30A10BE6143C550220EAA4A0D1222FE422C4D7982D9CCCFAC00D0E4462C235CD00C7CADF20B04315AA32FD7B120D0CB2D6AD4E249AF585CA042C599421E683F17144977F680826160952A133205738111809204BC73058B54775CCE2DD17DCC7DE178877D4708294E918B0190814AF1805D7BD0F48FCA1A610CC55B0F542B019262861A7ABB3DBE9064361D5C1E707E3CD91D090B131CD914828E00F636382711794080EA2080B4105C178D7D070DBD8406068283024805382719C06EE0C748F82958D8C0AB00C6074D543D88F0256148CF78441AD21108B83F804C9D001531092AEFE22040BE50B5D16209FE6C844F6A0E9E6846B910D3C8AC9A1A8B05407B21E2DDD5A215599108E768E4745C71744F5B2E2042FE61F89F9A3A309B2289BDB524951DE28283C3C185159491CD46EB0323E4B34AAB19481D2F528233C45918E5884C77D51B6587D17EA68C8AE0E6F443F73601355833825004817C60950A32865BBD1610FA69BFFB43484106E6576E1B6E88BAA8EBADEA21F4AF341551623E0F4609CCD735CE1F6602C0E912F1262B1B4974C1518988704600232C45A12EF253301AE06C956365BED84560F43008DB342DEC0586477002D0F299A61B4EE6C267234120FA2EA310AC122239E88051283A3F8E622797B704DE1227402FEED857EC135C2A0AE072AF5536B8897BB83D045A0D46CB3509352A9D1096B9805550EC3824B5FA56D6760AFB05BFB586428203AB8109F459F2ECEDEA71D402D8CAD08C616548AC2306605E02C75201C27DEC070000083EA8029D3298D576A69E5D3A595E2959B4F6BB092696E32358962FA681905EF1A88675FBF30EAB4E90E329DDE07F571E05EAD3A60B9A2CF6D692AB4D7309F2AD5D98A75CFE71385341DD8C7C78343C6E94A0F8C056DF63B579DFB92AFC2BF6AF827C5D5692A04E47AE613E86DD3CF2EFDD48A7189E4B593FC617F283AEA070BDE8D82B9893408CDACD26238B3281EC9D10D07D6F681A5C5460676F376487DED7DC004DF589F1F05B2C0DF3FDF7BEFBD6894E9B21493D9F00213EE455927DC686EA9736964509E20F99D5D4D9B7BDAA2D666EE3A13CAC4B0A2DDB5ADDFDBE66EEA766D69EBEFEE84FF7745B9A0CBB2F217BD33097BA7071C57B7B7ADBB65037682361FC43E7BE05ADBFBD78C9DDB0053D18D448679401C662F23CDC1447C232C5423C9B789E88D7F7AFA8203839D4FF61F77E77DAB7FD2FA47ECEA28FE8121B4064EF16F19AFF6F9C3F1EA8E48388213CF29DAA284CD765EF8C393B7FC7CDDDB6FC20CB15DF43278E5528DCA330E138DD879777C7676D195AF5C2CE1E2A5693D5F20ADCCAA8694F94EA6AE6B69F374B77941BA32AD5E31FFF95E75EC8CE76EF8F02D90AF57952F4A99E9B8A3EC2F6E3D8888098816448AB409A5719E20808170362F08DC7160035720E693D989166C312063D95706CD3AEAB5E6D32B060A306FE6499BD0D8A20293614AC1605AE9F4489F0F10153F01CAB4BE7402D19B552D691A25396C3872A5E9D628DA6412E5391258D5596465E0933FF9F2A410EBD0E63F02C082943A4D5201392C41DDA208F7C69C29DF4B44A635D03FD055395846978E5AB8F0A8F0995DC9646534876521A8D50DB694D25DC8C69BCE3D1ACF88B0BE78841ABD608096AB04CF6A9D7377246A916F349A0BEBE4BC5F63ED84F5D030CE1EB1935DE121311F5E20164E3A48FAD2A616CAEA40B8081506519E5C8CE8CCA2D16D7155A21987587818C590E3BCBEA661D83614B062016B0EE06450254CF8077CE00498A9AA6E83C3B80D544D44557DAE1491C5E9C29A8289BD4B3744937FA7835D640D087C0D90251C74B85A5BDD6D0922770562C008B6D77607E2396C95612FE9CE5926F306CDA47048DDC183719F7CB6BEAAC1CAD44DC0E4AA669545494FC09E0C0EA62D6EF226F760F6E16028A44ED8F1D9FA84BD1DA89994C2D7B210B7C09DD517ADD562CD0CD6514BC4A472BE3AA9647935E6FA895DD2768E551FD633AB6AFECACABBD6D495FF303E6BDBEF173FF2CEC6EB09D9F356945B9C4F3F8677F2D9AB25EBD2F74526EB72C034AEBFB573AB8755352C5A4193DBC24539B5E2E2AA29D7C5E69D3FFD8373E6BC3A738EFBCC4CADD8C55B818F11B5E274F5D9EAAC571CFAE12F0E9CB4E6D30F5D2DDDBDFBE6CED332559CD0559CA5C5D5962ABEB06A4A89D470FF73770D9CF1EEC54ACB9B992ADE935A71B5A8784EB2C5D7C3BFA7E19FC4F0AB32AD0C303FC65C987B2E2BF9655C7EE7EAC76E6A26232B5C5B269DFDB2226D6CF28A4F2E4CDC8699243E77F06CB3BE2D9F4C26FB38766B3A5604CC4C88DE4C881DE988A42C27A9F07D991CAF4A30982085B89AF09CD0BDC19741DDD740A78FFE5BEABEE1E5E3126F1C71B667CBEA354FBC30BAF185FF92BA2F4D26170875A76385BA33217A332176A423D2D57D8105753BB7B6356DF2B4F97C6D99F47D35A89AFE5BFABEC65FFA9DA68E67361EF8ED4D7FBFE0A7EB2BFF4BFABE3E995C25F49D8E15FACE84E8CD84D8918E48D7F75516F45DDCD9D5D5E9EDEEF1B8BA5D1955FE69D0F6B5FF96CA6F5FD7A97CF45AD35B9F69909FCBAB6979F6BFA4F22F27935B85CAD3B142E59910BD99103BD211E92ABFD582CA0BBA3778DB9ABA7D990316F9AEE58075F5AE6B5EDF7FEA7BF5A75CF0C0390F7CD85195296EDC692952D211CB155F7462E31FEF397273FDC1A9256B3AFB5C05992ABEDB52C512B15CF19D3FFAFACC2FD535C85F7B7AC7CE359FDF4033557CD85AC5D758AEF8B275D32B2FB9F2F6AB9D0F5C26BDF1D7CED9992ABE5F577172937001E573F9FAACB3ADE4A16B86995681D8DECA6D8F865FF8D0EF0AF491BC01D8B0C72DDB3EB785FB3413D1F118924E56A120FDEC2F4E7FF392A7CE5DC5B6B550BF4C4953593A07DFF7D831CD61F5E81B1FE048AD0185B0BF3F18098759C3D2F7A72BF1328E2F321E1B0CA4E19C8154880C87777158C3B5F8074703EA215F449C4F4067583B27EAD40AE096AD1357A2630158295AD9C44E36B18B97624BD0E94916B84B9ED2903E22C376E8788C2FABA08E5596EBF06AE5D842773CDC1DE1DB4EACF1FAA5087D2C39A5758A292DD1C6CBE399C7CBA54FCE99B3A2EFF92BA35736EFFA76E3A6D539ECA9680DD5FADBEE22527008F6BD0747C7C33BA1B5CBFADC56AF57B46011BC04C1763AD92527DD0163B906F506FCA12486DF1318072B9DDCEAD36B1ADBFE8397829BBED0931C00D0F6D539B79DF1D25D9F6826763433C39583A80467EDDE368CE1FD2D9D1E4F5B4B37AC67D5B8F47D285A328E4FCF26C3F78FA044B1AFA759146A7579399826C88CEAC5D5F39B7D2DFDDEAEEE65CB1AFB970B61E7033F1CFD2B26D783A90DF7D96EF8C2F90F3FD4AC57C5D1165591CECEB8237A94453EFC5A99191729DD19E8EF9E4D686A94936AA44935160A1DBA5AA351E35B5475FCF43D2A46C53791261FAE304676EFCDC5730BE13599CB26DBC5E8C56B0F1D7EBC43391DAF7A790371E6033B8271F46FE899F0E82818080D59B990D68E84789806DB21A0B85407C4DA331EB5C2493DF7D3949BE220EBFADCD9F7E818B1F9E9215C74DB351E88EDCD7E04B919C9D01BC484729AE1566928798F313E180B4613701793790455D424D48AA83E95782D96041CD3A3A5922AB17AF288F5AF80E3BFDD7EB8BC3CC0AE112ECAC6648BA0051EB3CC8C403B113D2E7B50DB9ACE20E5062025E01B1666F30DBC55D2BA979FF0D4BD1B55DD810346C2128B238171D0EDE641E01A0AF07E111629E9EDCBD44A728F0130F2E05A61A7AB35DB5669CA953CC395743B36431540930AEFA7B1CDE054598D176E0100AA86BF84FE0891DF4FE71EEA4F4C4ACD0331E0FBD94BC85B5CCC7374BBBADD6D560B393ABD27F47B9A3A2C172868E9014F655DAABC96D6A66E3DF772461DDF15AAEF0E8EC110F08F450DFC7B72E4DF63813F5BC669C1104CBC2E9B896B1E6A3E912FADFBD6C9F354339F96C35CD7244C15B22BA7ED911858D8583401CDE96557D3F6C6E166305C57010E43708B485C7761633BC555671D2B25E100301BC2A1EDF90F8F204A519725DA2CA5BB6D5B775453FFE43714BCEC15CE6570D2C34AD1799CE354C1B1ABC90BC6D80D41B0BFA3A94BCFB8583BFF07F10D85A76D6CF275B579FB390F9F9958CBAC7E7D123793AE585F819EAFF5AF5A0CFCD267797A9E15490B6E49AE890CC54B45F18EA66DC0A2C7A397487F4140DCF634942D176561D434F5FB3A7BBC2D6DD6DBE36D4D2EC44C05F2B5785D5DE07D0C02354CCEB269002EF7C0170B6A9C4DD1BCE0ECEE6C69721B242DD5ECC11D81F332A344D344396F1B6F637F738FA7D5C86086C6C0AB9F3C9837ADDBD5D1D6DFDBE931B02857597067D31B09074CADBABDD3DBD10459534B77A777BB8EC3A43347CEB85DFF4D87817B856A45EE269F0F34D4D4DAE6D5F39EAA59021BCAEE887F28C51A660A163D5E77FF8626D490D74CCA6AE4C4440542BCB8E31FDBE04755C5CCE4AA545BED72B7B10E706F310AB6347BA3E1132DEC9410ACC2CDBBA3ADA3CB0D8EDFA7E75B99EC0EBCA815321939453E2E589BBBD550B2442B8971DC50261FDA010EC9B29313937C684222C3D8DBE2825DDA26B7AB3707AE30130CC612E3F8CD5B0AD74A97AFDF7582A7D3DB06DEF30497A729D59FA45F1952CB93E49288684BA2E412529A3F3F9AB218CA1A950D4CA90953FBFCFA06649BBA70CDCE1A90C95D27DB1E3BA630E909723DD5EF8B44C64054F57542F7BC7702EF4D693DBCC11F1F85109257F0C223DF9A76F2B33622B5C32D05181CDC98F1EBB55188B7A370E17C227A3C6C5DB30A1D90B24AB195E78176D61C8D4FE740B72EAB3F7A99EE7FCB57AE42CCB90942974DE43027666B19DD7A56B73FB2B8CFCA3A886D32A47DCEC536FB164F3EDF313058FF7972F31BF7ADBC559DF04839ECF6686C74BB3D269B1DECA2F5CE40D817C07523681DDC95EE6A7FCB28400771F6054A6084A0848ABEF42F3A614602FB71F1000EF6EABAEA619853550F0CF4C7F74412FD5CCAFEB178A21A564EB14075D28EAB8FAD1633884FA72DFD6D1078938701CDD70EB61CB8EDE28E171FE959FBDEC15D1B5E02552ECAA24A6DC936F0EBB34FBB66C9A156A148F6196C834545AA4C2CEE15C07D6A888183E32176B9BD19BE80080FC6D895099C05A6CF30E1560798788061614B8097CDF13BBC16AD105B419AEDBF94A8726857AD97C1FCD2209DF59B242E7D315167559257321AA54E67E7A693A4CF6D61CE1D0C0713F055B1D99CDB38C176A2EE0C2D2A41488E9F6479A148B2251F6B4773CF5B78F061E9BAADD659192F15F9B6FB20AC5ABCA664E88A0C1CED1E9C2F69FB6E92493C98110EECA94E9BB2D62E5FC4C23504B4B67E4F4F47739BD7DA894B52A7991AC9C73A0EFDA3B907B8415523BEDC128D62769B4E685BAE4217B774BA7B3A3C49B191E11DA00C6C8B59BD8CE06658C9B2A97CB25E7B6EF5A6C99D67BDFC3226B72CDC201383B1FB5A26351D4ED6926FBD1653290BAC97E7523A518D3A19EFCE2EA3C37A1DA632165A2F2F34C92D804B9934807C0E65F2A231AFCB6ACC26DB75938EB61EB71BB9CFB176015FF71975068E856E5812F85A9ABADAA229A77ED21E3BA639EC84E0D775299B7E5327FD3E09AF9D4776E2A76AEEC0EE40087E4720809E90C98B8BAD663265746F947DA3B7B31B7E6A209080A8A741E0817D8A243E33C05F42508FF4E040D11F1E84AF14C197A6FBF9295A357A075F61A83D878FADDB0CE532064A25297A13932D4DAEE94902FC303F6DF7BE4243E77252B921AD146E342779253F474A1367A6A66A58532482D1490436FBE8086724F06118477AC64321F53B4BAD3FD52F53D5FED43E584C809C61F15AC65F532B60363C4F9CD857A6DDE49B9B3CF6A42F673EF67CA87EE98D3F3BF8F43F4B66FFE5C6572E6FB3E9AEBA140DC3F26238B4373EAE2E4FDA9A4ECEF9960B7D2399BCCEB15BD3B11CE1CD84E8CD84D8918E30DE7261D54E76CB851F7C893F36315891D5AD18EC3D93AFB2793AB726FDAB33D5BFDA74FBD23A61C494FDD58C9D5A68A9532F9CE53DB5FAE5B3AF7CE640F413DD2FB70F66E9D43372EFD4BF2593F745A7A66345A76642F46642EC4847A477EAFB39742AD2BF93EC8BA2B4BE301E2C18FB237925661D15F7DB1BB2DA87F6931A99AE1F4314E876B53481962D9A9CF1F72C32B185AFC970EEE0CB767E0F7BEB70F3BE5EFDF58D16ED07880CC7401583B1481C8E1706740458D5073065173F1E938E99A6FE844A3A964E70F33E5FEC3894EE71608A8F131911B01C95FA9AEFF8D3F40FF21DDDAF8875E6A60D4F7D74DF43805E2E5D4DC91CFD2580952B56AF587ED4AAE5CB562C5BD37FF4AA6547AD2A2094927AD320A1FE5452BCE10476D22D46762456406C94CCE0C7DF6C352E083A074E81657801C9A364115F512275FFE40250DDC5987EC36E2FCDB208C56BFD78AE0D9916BD0CEC0CDBF694CCD230E93B7194CCD4D0299BBE20CA540DA9DB4304F56AF0F46DCF14AE29BBE420D15C0D697A0042499546917668009DA0610D4732C07AB6864ADB2746B96A347CE64D6083CED24F0E0CCD4FDD86E412966A047C1FD5D041FA2D40C0546B18D3239D348DA56FAE1B149E7A4E6240A6EE241BAA373D3E80F656A491B85A2929649730FA3BF1B9DEAACDF243744A8A796176EE890C4AF8BB08805067912000C56B6F2DFCAD58BCA985CBF9BBCE6303D11406D40B5CA95E41E84FAEB0B167965B955DDD9D02F1A7255919D6BD30629208FDC2125468A85B854E4D61A3C24B6BE2ECB75AD806882B3C1CA1F0533C91D80878A7A1C0A87F3CDE20FC10046EB1E3DF90A405F10AFAFB8143470B85AF63A1A3A0EBFC0C0D66E8D67BB00E1501658AFAFB133077F1E1D93194DB08EE4C07E9EF6F5C71747F3800AB890D701019086B4E6D75E3324AF2D7E13ED27194D86A176D91C951648D13368E8FA6A4AC0626B5B00F13F2051301F69B36E0E16A17F59937673C8C35360C027D1CE81BD4826B65B296AC73C235DB63295966B1BF92FEB1801C0FDD3C5985FC1C0E7F6908299A86FC5156AC8912E7207C64DD1DC1184BC951B5BACD58EEF8D7EA205C958BD26840FA16D2EA24CDA44D260E525808ED3841264EFEE49249117FDA2413993F75C8A4983F75CAA4843F6D96C914FEE49349297FEA9149197FDA2A9372FEB45D2615FCA94F2695FCE944994CE54FFD3299C69FFC3299CE9F066532833F0564A2F0A71199CCE44F419954F1A79D3299C59FC664329B3F456432873FEDA26489C59E615EA080C465F8ED816A2C3B2E9379FC698F4CE6F3A7BD3259C09F4E95490D7FFA844C8EE04F9F94C942FE74A64C8EE44F6753D2605102752C17907365524B1661F1F36552C79F2E94C962FE74B14C96F0A74B65B2943F7D0A86335A35FF3DA50E6D7029B58B320C2F193E41B802CDF7333269242B90CB67299C0C8C06A158B57BF2C902BA4BB0E3D1C890D80FA1644D6DFAA9409AD99959A28D5F1B5F9AD3E82B2037807F4070CB780C7FB983126FAD3BD354285D106BA2C9E473E4F345E4467213E5FBCED4ECD72C0107EBE438E0D2394018191C8FC31D60382E1C72813F025F519B720D69F2D7455B404138395C9F53FB32B4E6CBD89AAF204709129C3F4AD1319A76330AC0C36390EC86C4896E94392E6C62ADA978769A7243C041EE06B560FFA0657C6CF51F228751E07BB18E3C9A72FFC3411E40783E353BD075906F22B280A6DD1C00D91E4594831A8EA11DE4DB082DA466E7E6C8EF3B8876D2B44B1620C77711554433DC9E7190EF235EA626F74D1CE459C415D394BB3CC0F4C78828A126D70B509A9F22760A9DECE68083BC8044A5D4ECB28983FC02916534FDDC9ACBF50AA2CB293F76C71A7F89800A6A3C2F06F8AF115E49536FFF680DF92D124CA529B7303429DF44FC349A769D068AFE1151D369FA7503077917513368861B26D0BE3F23814275C78A0EF20137677177140966C2FCA013517FC7B72A2A2EBE21E49F089945B53D100765836636E5B7DDE03D0FDFE7C07B0B7F77E0FB5C60D22298D022845453C3E21D084B103C0F78A334E0F9992CF3A9E1A4C5412B10BA80AA471949F83484D750E35181832A083E82F2BD7915380B810BA9BA19AE82E742DD622B35005D53EB5A64E6BCEA2773CAEADCC485E779C3F0933A6B0A28085B188CC396F60840D9D4AB57A647D0854E5A438F04BD701CC01671589D4C9691E5107CE812085643C13846C0210863E1217F6C2819AD645ACFE91B280EDCE5E09AEA1B1C7405B60DD896A5FDB25E015D0DB5B123BBCE6130126C5F1AD15A99AEA1473BE951F41870812C98E08F23C5C6F16852A6EB98F3A1C76225B5982CC2A40E93C5982CC1642926F59880680B6BADFA357A02B2DE00969D1A4E0AE846E4B60C93E59834820B60447020DEA0DD442BA09D88652A58092426DF1838A80FB1AB30598DC95198ACC1E4684C8EC184C5DF7598B0861E87C9F198ACC7A40993668A9F89C00CDC45C50FDB80A1AAC6D3191A429A164C5A3169C3A41D931330D980890B13D6AE4D98B831E9C0C483096B4817269B31F162C224EFC6A407131665B662B20D93ED98F462D287C90E4C4EC4E424300655B2B6389829E44CBE7E4C4EC6C48FC900268398306C0093614C463019C52488C92998ECC42484C91826614C22984431D985490C13368813988C63B21B933D984C60B217937DE08C718A5503A69EA8098677C39D8A9A15941C39D9403B815B860B7E7EB680DE6CB019B1D543BF006BA3DABE745333B9AAC11643F43627BD857E91E24F25C761C3DE3F84F7AD28397672261AC8B5C8042AD32FD12F3BC15EBE02438E37AE83CD123BC3629A063B1143B569EB134B131B217E9FB52146EFA05F4349BE0E235B53386CF7316D5B9BAB6E81E008257402F1C001734D2BABAE5C5A059D42EFA477A1C4070D128F7089179A486CEAB12B474C05DEFCEF29DD54C387E9BD28EF7D1049A052B1E0A8A85D945A13903E401F74D25BE94310CA30E0E916288B4D349971C9421FA68F60958FCAA49E3460D4788C925AABBB1005F471B077103509417B4F5F2C5BDFD690E977E8934EFA047D8A33D6C5ABA7C91588F81E20E2017D8367D69AB78E999BAC1F34B038C3F2534DC90BE88F28D968A23C4B966DD69DCFD2E78AE88FE94FC0B74353D4DFFFCB3A7D9FCC429EA73F43962F00CBB89EE5D19658A6D1B051F222FD05F27C0978E296D21E7F942FAB4A6A068D5B3AB3B5F2D87B0DBEC87022798E0AC2BD4A5F430BFA2598EFBAC110DB2B92E92B640DAAFDD732594D5621FA8D3C587C79762EFFFD8B3259C9416F82078E8F43536A1A6BB429D471B5963F9D345396B59D0A6DEB9FBE2DC31DCF3F3AE93BF4DDA43CCB6B1211AE3C7004B58BCC96AA0E9542A67FA17FC641F93E6CC1F1E22B6A628111703A81183740B0D679466B8536C0CC2C6234DA2929A564FA37FAA113D6847F875E51050B4712C161E8957CFE20D37F82A629DC6D84806DA469C2356361F259966C9CD20E134455ED69215EA667336548B0E09BABB6066CCEEC93024A564DDE576685584BA767E0284B4592135A2CC93084551983CC2CC3B9ADF6B3DF6398E4320A93D10EF58240A5D2145447190C13552098D9660F244943B101B92C4D952A91CD34F05B820D46A4F6F1307385BEF1280808AD6C3171A3E64D191645E30D294CA0CAF234D6D090999282125441E850ED64300417FC8585A78FA33C8696A5B9D21C349B6A20540D42AD1C7791D00B7DDCD905F4B29E952CD5480BB0FB8F80F8976AA29D2130CE616E9CB550506DC41E3F9A871D3268E4626EE24B40FADA8DAE455B0022D523A401FC98CA7114AFFAAB7DAE9A00256B279FCCB44D0C06D8074AE6DD3CD594AB2CAD901A51E2953ADF920CF29A498DC5C1D3ECB0349BB202313744A844968E96D6A03CC740D0FB98D5993047B6EB90EDB1BA66C2BD5C76BB1F7CA5FA284BEB7907358147548D097E9CB25D358250243E1E0B24BDE6FADA49CF12389D28643A1904579791BD2CB549AD686DED1FCB84754DDF804D778111D66EE426B709DBE8FE387BB93AAE1EE4DAA973FD815DF0B9441C37194C78F442486004D02E9FE44501BA759D310ADF08B4801AD9560288EA5001B2B44DDA8AD4DB61A9513318C3DFC5369CE9F42FC765BFE9FAC721D538C89D0EF2A183960163B06B87E48738B6AE66505BFFD71CE79006D9D4967FFCE79040087B34341E7748237C8E04435BA082E064CC84A06475EDA2DCCE955839B6283F5596FAA41D301D91C27040953B930229AA2D7534CF9C714D28C59CD22E093AAA6A92895381342E4B7E70ADD26E698F2CED944228E05ED3DE156C4F45DAD3C0B5F9B94A96E57CD2269D2E7DB210643B038C6510DB007B5F47640964EA21E294145081740EEC0C0DF35B87B2749E74B6533A573A1F8ED035424F8491C21DFD76FCAF23B5C56230E3922E84739114669E488251249D6C817431ECBFB1A5403CEE1F810A2E85599674897499A6A174C55FEE942E923E0DE6C54DBD1C3FA44B9E07756302EDC09F0CE43F712C204EFE05207EA3054755121C6E827AC8227882033D4220778ABC48E4B2C88B455E22F229222F157999C8CB455E21F24A914F15F934914F17F90C912B229F29F22A91CF12F96C91CFE1391EB9B17C9EC8E78B7C81C86B447E84C8178AFC489EE36919CBEB44BE58E44B44BE14F352272E1A21BF10B71C213F0BCFBD202FC4893DCB57B3DC894B0342004BF1D49850E9337052528AF502B377EB0E91630EC357FEB46349F9FAC3A4FD61B2BEAEFF6EB261C956EA59AA876C5CBA951E6357EC7A985BB103304FC9D3033D4A1E00F3957C3DB04BC907608152A0077A9502003A14871ED8AD380058A814EA815B9442003A15A71EB84D7102B04829D2037B952200CA8AAC07EE506400162BC57AE0494A31004B94123DF064A504805394297AE080320580A54AA91E38A49402B04C29D30387953200962BE57AE0A8520EC00AA5420F3C45A90060A552A90786944A004E55A6EA8161652A00A729D3F4C0A8320D80D395E97A604C990EC019CA8CF204032610B85B9901404551F4C0094501E04C65A61EB84F9909C02AA54A0F3C4DA902E02C65961E78BA320B80B395D97AE019CA6C00CE51E6E88167297300385799AB079EA3CC0560B5525D7E1E039E87C00B946A00CE53E6E9811729F300385F99AF075EA2CC07E00265811E7899B20080354A8D1E78B952B3B5EE1E72E531472847E0FB55CA115B0F100263E04A4865181176E90FE46AFC854678BE965CC74707DCBBB4B3AFF22A6064C028B8BEB16EF1927BC9CD76B29FEEC721C88A57130C6B65BC387BBA857C01D8E0D3ADE436A0FA2C3CE76325641AA45F24B70BF69F809279902F05F66C487D092AB8977CD546B6C2CB1D8D754B922F5F6BAC5BCA5F0E242BAE63B22DE115B3A7AF936F4005F8B41F9E24F674009E6C4C841256DD46F07F9BC037B981F24E7297F0031B01A700E3474114F000071B1797DF732FB9CF4654BBF22C45C56D58BA7549F9FD807CD08044E111BD51B103FE21C03F6CC0A36B40BC5BC903FC2380FF96018F5E02F11E251FF08F01FE71031E1D06E2BB9402C03F01F8270D78F41DDC913800FF14E09F36E0D18D709F5208F8EF01FE07063C7A14EE5E9C807F06F03F34E0D1B9704F5304F81F01FE39031EFD0C773A32E07F02F8E70D787439DCFF1403FE6780FFB9018FDE87BBA212C0BF08F8970C787444DC2B4D01FCCB807FD580479FC41D5429E05F03FCEB063CBA27EEABCA00FF2BC0FFC680474FC5DD5639E0DF00FCEF0C78745ADC835500FEF780FF83018FFE8B3BB34AC0BF05F8B70D787465DCAF4D05FC3B80FF93018F5E8DBBB869807F0FF07F31E0D1C1716F371DF0EF03FEAF063CFA3AEEF866E8C6C7341C01D2237C7CE0131F1F6C2CF402D64E7A20366F214D7083DA4FB69133C9767233601E217DE455B2032E279C48E7C1D94B13E9A77E72323D93F8E9CD64803E4206E19AF810FCB668409A4786A5263222F9C9A87426094A379353605C7DA88E2B7A128CAB22C84BF8B8FA1B88FE0F26BAEA07F9B8DA8DE3EAA3C6C570106DC0AA036B02075605A548613350A8436B9F92871476A4C83750A883EB34251F290A90A2D040A10EAFD39502A47022856CA05007D8198A03298A91628A81421D6267298548512AFA482350C7D8398A33B58F6831EF23F6B43FD947F38903FA280E7D94801B5DE36488EC26E7913DE44B64823C45F69237C93EB84152A6EA9ABC0DBA76407E07EA1A642847292B99106A30E0DABE60298A3815D1D30D6855DD177175CF408A99060A55DD9770755721C56C0385AAEECBB8BAE72045B5814255F7E54A41AA26C857B926D8D301AE0986CB034D9C0D33C773481B39978C801E2E24E743EBE7D1F9BCF5F9DDD0FAF9709F643EB61EE66C5B0FD3DA6BC98CC374F1B5A4FC4EBAF43A221FA6CBAE23F9B74B6755D96E2205F6DB89DD762D29ABA08DD86E0FB4F97669A8CA6E40ADC406C34C0E50AEAA3C0D35179AB6AAB1821E653F4CD7DE4BE17C15DB0DFE1B08ABABF28D84C71B08B1F91E68FAEDF4FDAA0223E17A03211A9D477100E1F7AB1C46C22624B4A984687B1EA510086FAB2A4DA9BBD9C0128DD0A33881F2D4AAD294CA5B0C3CD1E57B9422A0DC5C559A527BAB81273A7F8F2203E59155A58546CA36034F0C031E0801B7937F54953A35CA2380B2BDB1EE20D9772F75C1BA846E4243A19B302C789412A0FF5E55699191DE8DF46719E9314C789429407F6355A96CA4EF40FA98911EC386472905FAB1AAD26223BD07E9B709FA2E46DF8561C4A39401FDB155A5251A7D03D06F6E84C48B85868D9554D06E66FD18633C4A39D8C72753CD0FC38B47A930333F8C2C1EA5D2CCFC7A0CBD8021C6A34C3533BF2D06428C351E659A99F96D351062D0F128D3CDCC6F9BA15B31FA789419A6E6B7DDC012A7DF1E453135BF5E034F9C937B9499A6E6D767E08913758F52656A7E3B0C3C71F6EE5166999ADF8926E687137B8F32DBD4FC4E32313F9CF37B9439A6E6D76F627EB81CF028734DCDEF6413F3C3958247A936353F3F9ADFC024E6872B0A0FAC2296A64DDDF3AAB9CFC5273A488770BF216F260D90DBC4BCF943721C78DFCBC1FB5E016BEC2BE1ED2AB2094A6C8775C230B9819C0537103F456E028F7D0B390C53FE1FC314FF558852FF205FA145E40E7A24F93A5D054B86CDE44EDA4F0ED253C93DF4427298DE46EEA3779107E873E421FA4BF2B094471E952AC863D22AF2B8D442BE2305C853529C3C2DDD42AE90BE41AE941E2457494F916BA4E7C975D2AFC80D70CBF173B6127213FC470B6EB11D456EB5F9C8176D27932FD94E83AB9017913B6C5F245FB71D24FB6DCF903B6D2F9183B60FC93D7607396CAF21F7D91BC903F66EF2907D803C6C3F873C6ABF823C663F481EB73F4ABE637F9D3C657F973C9D3787FC0022CCF0FF31C28C588D30A356234CD06A8439C57284D96939C2842C479831CB11266C39C244728C30D11C23CCAE1C234C2CC70813C7219EF85F469871AB1166B7D508B3C76A8499B01C61F65A8E30FB2C4798532D4798D32C47984FE418614ECF31C27C32C70873468E11E64C34BFB3FE9B11E67988302F40847911DE5E8208F30A4498D720C2BC0E11E6D71061DE8008F33B88306F4284790B22CCDB1061DE8508F31E4498BF4084F90022CC871061FE0E11E6230AFBFAF4362AD1BBA89D3E47F3E1468843CAA34E099676D22A5A22B5D05238FB298793914A88302F4084791122CC4B10615E8108F31A4498D721C2FC1A22CC1B10617E0711E64D88306F4184791B22CCBB1061DE8308F31788301F4084F91022CCDF21C27C6477C03E570D95EC8DD46EEFA6F9F601EAB09F439DF62BA86C3F484BEC8FD252FBEBB4DCFE2EADCC9B43A7438439FBFF1861CEB11A61CEB51A61CEB31A61CEB71C612EB01C612EB41C612EB21C612EB61C612EC931C25C9A6384B92CC708F3A91C23CCE538C43FFDBF8C3057588D309FB11A61AEB41A613E6B39C25C6539C25C6D39C25C6339C25C6B39C25C976384B93EC70873438E11E6C61C23CCE7D0FC3EFF5F8C30B49A2C005FBB8CD690E3E842B289D692EDB48E0CD325E42C5A0F9FDB2D235FA58DB03259497E4C579357E91AF20FF888A088AE83AF1C8EA3ABE87ABA9936D37EDA4A4FA5ED1061364084D90811C60D11C60311A60B228C57027121C26C8108B30D224C2F44981DD22D74BEF40D5A233D48174A4FD15AE9795A27FD8A2EB1515A6F2BA1CB6C75B4D176145D69F3D1D5B693E91ADB69F418DB45749DED8BD0E907E97ADB33B4D9F6126DB57D48DB21C26C8008B311228C1B228C07224C1744182F44986E88305B20C26C8308D30B1166074498FE524A6F22D7258F5C0AD9A9C9263CD12997EAEEA7B76CB73F486FDD6E5BECCB7B906EDA6E83B1ECDB6EC7F410BD3DD90536A0E1744B7CF6079CDB6DB6CEC3F4AB87E937785750DD49908B77053E254F825CFC240844D94F0F0851C688939DFEAC45519660F8CA419EC3F46E88750752AA3E2659F531C9AA8FA10151F53DF490A8FA4452C8E857619CCDA9D6FBD5F66A5B962B9295AEE09596DAF1234CAC8AC124467564DD3DF49BE5D26DC45177987E6BFF9DF4DBEEC54F9029083D4CBFBBDD7D17FDF6E27BE8F7F7231F90F5E9248305501CDB311DC6C555DB1F27F6FD5BEBB6C3591E9CE221442DF003FA8C28502E6ACCAF5B0C340704FED9A40928C4CE1A5F0C75FF100FF4E84FED4465F37C5247338008D9C89CEC5EFA735B92EA457A2023B397ED84D75988D76E05D954F19FFB2B04856F8201F33A92E0373FBF4A92CC87DAECA8D4C3F43785C7CFB98BFE76CEB64AFABBA6EABBE81FAAB7A9CD788BFE31BD9987E89F54C9DEA37F16F83251655EDD21FA818AFE2BFDD0ACF83F54F61F9135E9C5252AD092648ACE53D1F9A6C2498EFD025F2839CDF0C56AF912690AE2F986371B1B4E50E952C57E482A57692AA44A331ED3D53A66488A197E968A9F2DCD3169C23C153D5F5A20D015C4C67AB6004438242D54098E346FE322155F67AAA2A50754B4549FE46F87FF21FFB9B30F49CB5482E552A3A900ABD40A56D3074D7AF89B2AFA28694D261DAED568D6099A4A218403690E49C7A914C79BB6A25915B2456A3515F28403C91A3698126CDC9FD4C326558D8220BF6EEE21A94363E03165D0A532D82C79CDFAA1E70E81DF226D3569412F473BF10A9A38F97E12B853C81FAC2BBFBE423AD15757FEA50AE924C8EEA890FA21FB5A85743264072BA401C8EEE7D9433C7B84678FF1EC099E3DC5B3EFF1EC199EFD88673FE1D9CF78F622CF5EE6D96B3CFB15CFDEE0D9EF79F616CFDEE1D97B3C7B9F677FE3D9472C83334291DB455E2072A7C88B455E2AF272914F15F90C9157897C8EC857554843981F2FF2F5226F1279B3C85B44DE2AF23691B75748C398BB45DE21728FC8375748A3987BC57B8F28B745E45B45BE4DE4DB45DE2BF23E91EF10F98982CF4922EF17F9C922F78BFA06C4FB8828372AF2A0C84F11F94E9187443E26F2B0C82315D229984745BE4BF08D893C2EEA4B88F771516EB7C8F7887C42E47B45BE4FE4A78AFC34917F42D473BAC83F29F89E21F233457D6789F77344B973457E9EC8CF17F90522BF50E41789FC62915F22F85C2AF2CB44FE29915F2EEAFBB478BF4294FB8CC8AF14F967457E95C8AF16F93522BF56E4D7093ED78BFC0691DF28F2CF89FA3E8FEF07F8B0C68B9BC9706A67A37E5A853406339A4D7587A5C8FDD2AEEDE512B881C47E4E8FBF7522E8577237406AEFA4AF3F4ECA20BD479A78180A6C7A82941F96F66DBA5FDABD1D8A7E028277DD41E94CCEC1219D259D2D382C14138672A8EA82FDEEFBA58BB62FBE47FAD421E98A6F2267F4C6D2256CEE7415BBBE57003FAE6EF62B1499FE9B06C91FCC821F0FE31FB6B508C8BF00504B03041400000008004947E846F7CADD4C40060000071900001B0000004253435F5250543030322F4253435F5250543030322E6A72786D6CDD59E96ED34010FE0D4F610CBF409BECE90325208E00155050120E09A16A8F716B70EC603B94827877C6894993504AC229A14849777776BF9DF9768EDDF66E7E9864DE7B28ABB4C8FB3EEB50DF83DC162ECD0FFBFEB3F13D12F9376F5CECBDD1D514CA214C8BB2F6704A5EF5FDA3BA9E5EEF761743E57CA8EA54C5ACB49014E5217472A8D747FDC5D4EB1FAA7439FDF8F8B8732C3A38A1CB2965DD978F1F8DEC114C3449F3AAD6B905DF43F9EBD5BCF35161758D5BFD19746FAB291F2AB726D1C10EDFCBF504FAFEEDD19D83E1D331A5DCF7329D1FCEF421F61E9645F1FEC4F7A6D87A91BAFAA8EF47922FDA0F203D3CAAFBBE8A95EF15650A79DDEEFF91CE5D65F514D5B345369BE4AB533348EAC7BA3C4C511019297191D5765D4C575AA6A8EB62B2D2319BA5AEEF5BA69430892446868CC8C80189E34010290CD32A32D239EBDFB878A1372D0BD4B63E69954C5BBD3F16C5C4F7DEEB6C869DB413D3950F93CAEF9E33F7C3E9C473E54E967251DC0AEA12256A285BC9E1E0E993E1F860EF2E9A29D3151EBB37FABDEE34D6EF8CEA128F69A3C3859E8344CFB2FA79B3DAE0C3B484AA39D1377A975EDDB97B6B7CEB95EFBF7E7DA3D73D5BAC01EE2E91CFDAC6E8D9ED762777F786BFBA954E77B7CDBC9B4179B2406800960B559081ADBDAB5E521613CF9883EAB8A80F16A63D9854B5777C0425784B137A7DEFCAD34FCBE6E7D7AFE758EBCBF7921432D7AAFDE487769F4BDF85CA96E9B45E5373AEE2E630CE693B97503B12FDDB009FEF8DF69EEC1F8CF7C68F067F0BF3C9F0FEC1FEADC77F0DEFCEB3D160F8F74CDA4CDAD0AD7A9775C6E90430944FA6BF19EED9DFD5EED99FD76E9132B48372BE96C11CE11DB52924A00D00F6D6F0A1BED7CE68DA0B871F6430811C9373DF1798414EF087F9DEF122A98461EC2F17E2CB144181B924881D010CEA442A2A88010E84BBD8B9D8486B93641E965BD82548F3F7AD2C3DCC9B16F28EDF502EB6D798A04091E66B7F6EB704A37D929D543364C4ABD28FD8C5A4EFA5D5ED22C34DD4E50C3059BA64B0AC3BF61C2E98D627E44123F6148726069C83567AB9A5EECA9E165DA7C6392BF25EB9F7E9AB077E9EB37186F47C9D9591DD6C1EFFC8E48E1A16D2D011255D42648009398A99258A878653974422967FC6E4C1BF32F94AA0DDCDECBD6EE300730F59738CDEA260FA9EA308853A4FB3B41E9F4C51718C0550DBA3D679AAA6FEB263843993C949E19A303DD5EF50E7155EF9925716B2AFBC2E5CCC68FB163754947DFFF23DD97CBE72AD1201861B4D44E00222252424768920D64691618190898D77E0BA29D371EF3A5B197A9C3A97C1F78EC15D78A39FCFBC91CE2BEF719117CBB3C0FFE4593825FF29DE2BA658A2A4EFA15A12DF32BD41442F4B73F881739DC6B3550A96E66656458DBD89650916BC3A36446B81F5AF1011D78C3B1E0ABFDBC237703F01BCE6D5EC2B721245920130C23403226DC408126C084D621D72CEA290853B22478C6EA3B38138A4DC2812061A754E428BC881258E3B67980281C16547E4B9CECDCF0F943622E0114D78A36A8CB70A40E878AE3405CA79628C536BD03FE7809CCA4D0F546AC514F43C0FB4525389D74A1284026F3EB10A8809222014408482DA84C5E17FED81A3F170B07F7FFC60F47DF7FB395A4440BFA1856F4B8B0A4420221B12A783083DD569125B4C8702420911579190F05FD3F26270EBE1FE60341AFC765E1413DFF0C2B6E5C53088354DD09123171249992471146058A316031C3E1850A9FF6B5E9E3C6D2EA1CFF6F7C67BBF9F9A20509BD4C8AD23592C7842AD53842A2E31DA3A4674282C9118C212008EF9E7FF7699F183E1E0D6F887A49C97D93815DBE4D440637986079E189B68221DE6B4380C19BA024843437C52E3B06B4E55F136C8463245316723ABCA60BAA22189B4724470484CC043C57646566CABDA493B6B950D0CC18201AF251165C470278806A635564FCDE1DB113908E436C854A3ADB16020102571739C1D89000F36D6D932B2B1D461BC5E3B6DDC0CD6EF02CDEB5BADD3ECDB5B81A2F4BC6BC1CC2C14F8415544578AA2E8541DA9F8577D421A4BC9F1B858253891582591C8D99830602084322E3ABDE09DC23EFDEEB3274A7E47F4ACEBD6E6FB5EAF7BDEBCC5DA67C9B423B6C873ACDF51F47CB03B4FF6F70777C678CD5B809E356F53E5EFACB8F6D2FAD9BBE6ADBCBA1FB0CEE269DE5F576D03646564E3B07C3D1CBD6EFB0F8D56E80B504B03041400000008009248E846F9F4307EA42D00000F8D00001E0000004253435F5250543030322F4253435F5250543030325F312E6A6173706572B51D09601BC571F724DBB27DFEF35D9CC7499CC4761E3BCE4B12207E1325B2EC58768C13C0C8B66C8BC89290E4C40E7F5BA0E10DE50D85F2342D94421BBE3C3C2D140A2DA5FF4F5BBEB61428504A5BA0F49DD9DDD3DD4927EB44A9819DBB99D9D9D9DDD9D9D9E7C43D6F91AC688454057DB1E5D1A1E5677AA3615F24E20B8722B1E8725F70D81FF42DDFCA909D0C49D8DFE25F48C4E622F903A1D1B03FE06BF6C6BC3132CD75A6778FB7D61FAAF5F8227E6FC0BFCFDB1FF06D709112C1E6F68EFA3C634343FEF11829E5CC016F70B8D6138BF883C3C0582018234D016F347A16398F48E311B26C32DDFABD5150B0B31140A282354EA274785ABA9BDBFB3C2D9DCE0657DF8E964E8FB3DDDDD7ED6C7612B93F148B8546DBBC1110E4C4BA04C646834DA1B160CC490AF89B27EC1D00D5E2D41EFF606C642729F60F0743115F8717727A63FE50702729F3475B03216FAC89F1B58642315F0419A39EB1D1516F64C2EDDB0BECBE9DA4228EEAF1C74610B7C5E71DF4451A82836AAE427FB4CB1F0BF8441E27C90BF886625CD146921F8AF87DC1182B17486126C13F3C025AE7E20BD3B11108D0A8B1F6088806F523C8A0563537160AABD20AF78EF882EE10F660D744D8E72279FDDE81DDC3116886C118A97441DBD746876A0D6D5FCBDBBE169B3D3808FD56C45BA7D91FF10DA05A31B272D28C3128A9B6734CCBD0121C1B0539F280AEF5B0FBED711C3692C0ED228E415037EA8B4563A46A571A159B392B4A1FF40D79C702314F6C22E08B918569323236C8963DE88B79FD01A14E017FF3A8155D9C468A600439A543A1C8A837D6EA1D888522139A854393FB47592E8F2F1623457C608CC5FC815A0FD3DB8183640C7A56B01742D6181A86A19DF247BDFEA0A86C7AB5B456B107BDA3AAE4EC20330421B15867683BBC813168B3FAF4DDDA2E3269BD9A1736EA2A30BA1E050BD2AC5514B5227D511DF13CACA45D243B8A7D0656B16897CB5AE7E644F9581486951BF38D8603DE18CAA84D2783FB9B2E9E038565C570D48A1A95190796A8D5EAF4B5EA31E463351B0F13F2CD56C2FF2807B63CA2FF0B1A31E1F32264C5647E138B5A9E3804D5DCA58480DF2D44435C8EC6B73C91188E81FBEFEA0CE31FCC1ECB2D7A68311834174D7791AC7EF021D0DC0B7759F135E3E1B108B24E56A060BDEE37E7BD79F9B317AD96505F6C3750748945455180A665CEE41349F60873BFCC6F7BC2017FAC211008EDF50D820D30ABC60E6D190F477CD128731935692AAAF1E294188E848029E6F745DBBCD0ECCBD264EED0B343FEDC286A84961423C5BA39B77182996C21276B065A97DE40591D35DB8C9095165BB525E01B05DFB0192697B0D6BA1278B881117F60300234D051737F2E7F94F96D9F960F5A6069BAE6D3716F1847F32C63868C2297374422DE09943B7EE18F661F7CC67B8B8D5027B147FDFB7CCC48E4BD764C21539DC54A75F9C663AD7E5F40672F5327B79782FE506837389DDD2EDF1E5F00A660DF1E687BE631BBFCA3BE46523432014506FCC1DD5D304BFB628DA440C3C0C34E32152C2D16F1C50646308868DFE38B0C81C9B9C8146F70602414C1404BB3227448E5E062B56258DBA49F01791382191B14CCC095B518F2A9D3812F5133255EBB06A67E92EAD3E30C38ED25907791321D3902558789061CCACA740E654B522ED06E565C56A76FC807263990DC9233B5DE088502317F78128571F02777853FDA18F072A27B2C10889132FDD004A13E6F109B4AB30131433B541B10EF39616F0C140F8AD712FE9A58228CD1FA0CCC598C20CDA0174D66D01036F48722622A9F0D81087F8340188A50C7B3776FAC966136B8D4C0BB519FA944E0E259196109F83F4EE8F00E0E42206E6C286730E61B66BDE61802C7EA560319F1EEF1EF63EF4BC53B0EF21829D10960113B1A38189D7F1FB078030D0188EDB1F642B11926243100D6A41F005B8C99C508C886EE0F0506B1846AD0CD1F7502837F40BC17F8A31D83432DA3FDBEC1419FCA54C406BC7FB7AF6B0406E5F08840CB80C6486610BB4DE0F2FDD1EEE020B33E1F47F1F583A1BD8B1093DCDAF988166D2D9A2E07E53486C6D3C7942ECEB801C5C0A3583B89028B7528EBC1A44BCBA4B61D3ACEB170DCFC0DBAE68661240F47BCE19118A94E3751AAACA86F181A3C38105245491CD56A302ABE88323663314325B7A38CF884867444423C2C16790BD477D11CB5E99BA333A40FACD93ACEA04E21209295C903AC5195923D18220C245BFBB42482506E557AE576E8B3F2D0C07A6498E472CA2DC65CD3FD51B60C70065BFDE0F57B46420116BDC11C292810A6FB203E1F643589EE2433051E9D6F335BCCA9F3679465EAF48D86F6F8D0F29083F9E946228743513F9BC8A0AAB0068FF2D917DF9C246B2F2EB99D848EC37F13D02FB8841ED0F5C014FDCA1322B43D7EE82268D4748B34935C89F11096300F8A84C5A54F5FA46DB76F42D8AD7D3434E8131D9C8BCFA24F97A4EFD336E016C6960F630B0A456598B01CF08D3A148E93F88C199F8DB446E3855ADA18E8D07289C2B5403A792E9D1A27B1F6681A01C7EE632BEB4589D133226741B82090139A2C2BE14247522E50AC586708D6DD9A88DD7415B48F8DF9078DD16F3718BAB6F2F3ABEB3EF2650129AB4F3126F3C3F8676DCDA76FDDC4651DCA2A8539B6D5794A5F678BABA1CBB9A3A5AFAB1DFEED10F2EBD2CA17759B447C9E1BC67457674B57D39628AC32B5E01C6BFCE841DBFB378E5E540BEB82AD448638280AF3F870A33F16DD0A5B1CA1F8DB78F8E17F4DC49EDA38B0AFE2D27B2A3E98DD1EC28662CD1023F943B0AA1D0A4C44C782C3B80028D256B26CCA7FFEAD6FDFF1AB8DEFBC090BD05692B507FB0D7C55B1C6E51E83E93772F13DD7CDCEBFF6A5CBD80AB2A18EAFAA57A56D8184493F452B6437B5B8BB5A3A41BB12AD5C11FA7DB72272C14F6FFEF06DD06FA7AA5F98F7B72B9CE9A25B4CCB9A6BCDD7A22AE3EC2990BE603ADF00D27144805460E611DD7813D6188891F42BB4461DF706F3A083A17C6C8CBB93A6795B5850524CB40CA7E54E9EFFB201A3D2C7A131AD2F6141F546B595B4162519EC528304435EAA8558A84F2D88AAB128CA20277BF2183D17CBD0A2028160AE5B0D1E5444065B012E9185BB312E946F40A3D065D03FD055195846878E1B7C9F3AA45158473CD9801DB6C2621B41B3BAC09612BA0BC574264B0F475312ACAFA0A0C44E3040CB455ADF6A00C95DA1B045B9E17026A2E3D1B0263A0F560943185361273B8383224A5C2A96133A4C72C05F077975285C890983288D87E83AB3A877598CD535E310E1B8510D39CACB6B188245B0C015085CA30F43249531E6EDF7801360A6AABA0D8EE33650311E56DB73839854F29C58923F36B16C0B22E2536E7DDA098147C669A683366773B3AB85CDF6E28F495F9956BA71932585789BBBBD278351A3C5389A3DD89D44F20FC226FBC0C858707714FCC32E97D58DCC26CC82DB8D6C2E63671FBA8555A986EDF47903710ADB91CB1D032B9E7CC337B9A4D1DEEFBFE0DFF6F96EFDDEEF9A8CEBCE64E9362A1B891D5BDC050096DB42455B8C64B91A1A5B5C61DD1F37A28549B1DBD5F0DFBD66B11B7B3F517DD8C4B2375E79D7EBC75DB3F7B91F78F077B58F9FB0E9B454110EE36E6928264415E0D269D1C6C176BD8F88C513C1D4934C15CE301561672AC2A9C984B82EA7ABF870AA41A5320CE84702470D3111E8A1F2C6F06937EB587C1A854EC8717A3CDD2D9E1516BB813A2D77C343979E639BBEE7821B9E3EF9707BD70B9B7EFFF175C3A7E2C905A21B92A9A21B521176A6229C9A4C48EE860BFEA76EC84FDD0DF556BBE115CBDDF0F09C3FDFBDFF8C9FAD7BE8ADF7FCDDE734ECFEF8BAE1DA7872407443325574432AC2CE5484539309C9DD70E07FEA06397537ACB4D20D070891F659EE8603ED1D7B5CE517647FE3A6E7FFDC7ECDD6DF7E7CDD70289EDC26BA21992ABA211561672AC2A9C984E46EB8ED7FEA8682D4DDB0CAD00D199C4362E8A5BB71335988DF487207D5B35C9868E3CFD637F0B030F53838BE81B7DAA2A69B313CF30F24EDE3654DBE2CB10FF90301756F0A9FADEF4DB50237D3522CA0D8BA75812BED026383B6809C3D8EE92AD1AB417544C421B13422EE79FD9D87EE7D7A85FCA3239E2B66FD6AEB6F08D9FBB6302D8FDEEC1EE27B399642467D5FA40A191DB02DD3D7DCDEE366451D36ADC5972DD7E2D0AC97BFDE9AB59BF45FB4A6C635E6BC3C552D1EE1B5C0C7E3A90BA69B2D17FCA983EE8B8BFBCF58F0F2E1BFDD7968C7E6375215FC356B05BF64BDE0D173AE9E371EDAFEB7AEAB36FC7846F3CC54053F61A96069DC72C10F0E5EFBF69C7D477A7B5EFDF0BDDBC30DE3A90A7ECA52C1B63CCB055FD2BFF9FDEB9F9976DFB955B7E77C62FAB6675215FC4CAA8269FCF649AE30B0514B053FB8F4DDBD8DBD39CFDEBE74F8C84D776C3B9AAAE0677505C7B73E83942FB496A71D35F19B0929464C8ED87644279F0D77DC427B2632D9491397A634E756329973DB89E77C6D5EBC64371DEF0275FAA2A1B1C880AFCD1F8DC27A14D584C5D4105E1DB0726389DD31C01D67F092B0BA4D3C0467F5190B5B91A41EEB6B37BF76E18D2CEDE4BC66972BFD7A9C1F989B6EB1C34DA8B3C67C9189F4370CB6231BAE0B23A2711AE1DA61207ED12D3A10F1876370598FAD0D5555E3582BAA7A54E60D981368AC1D2DE55499D5ED792C7F3DDC11DBE385DBADFDEC9E59753A213B042FDE2C48610462C63B29FD8CD7932C20E18A1825B05A5E946EB5CC6B256D7CF11977CDBB61B64616975E965A1C0909175EB2603766D0C7FB4558A4A4B72F532BC97C3700461EEC64B43B9BD3455009D7A00C7796ED580D55014D2BBC13C4367E127535DEC804043435FCC5F4C7057834AF86BCBF615AE676B674B47776F5399B19F2E5F4396C9E96ED1AAFFE50405C73D098D51D07ABA2D5A57186FCF519F2AFCC907F9565FEA66E70AFD69B32ABA9B9A1AB45C75DCAB8A3670596E3165934E61D0D1BE4776728BFDB827CC4CB8D9EA6BECE8EAEBABAFABE1530326BD28D4CCDB1CE27F215354F9E314F1D9D5333DCCC655234C3CF65B7135B43111818A3E118546827BB533211851BAF70CE0C1206E1F85F9C5333979430C3A41DE285411F081B448FE4FE98073EA5D89A85624C35B5C3C1D9295D615D07544FD6329DEC15B68E71D78EE5A273B9C4A94262474367435B4B17CCDD7D6D0D1D7AC105DAE924A86FC83C6D6B83A7A3A5B38FCBF098A95567F5AB8AA8997605FA02F472AD7FAD619057A2B59FBBA5A90BC214BDCCB2B80D832E41BEF030642F16D9DB1A4E0111DDEEAE707A77A5E62D157961DC34F479DABB3B9B5AACD7875D56F7B059CF54214F53A7B3A3CBD56250A87672910DFD70700F37F1D5F020A1E58564577B5383CBA069B1660FAE106CE91B359A26F275B6F03AF63576BB9B8D026668023AF5318F79D5BA9C6D2D7D3BDBDD0611A5AA08EE6E7686823E53AB6E6DEF6C6B00D0D0D4D5DED9AB933069C0CB05B7EABF5530482F53ADC8D5E0F1400B3534B774EA654FD52C810D6557C83B98600D338588EE4E57DF96066CA14E332D2B5012531518F15A8177748B179B2A62A6D714B5D64E570BEB00D70EA362CBD2571A3E3DC24E09C07D20F3EE6869EB7081EBF7E8E54ED1BA03CE5703262327DFC3156B71351B72166A3931FC30E4C9867A8043B2ECE4C4DA04AA104B31F676383BBBBA1B5CCE9D19488500D61F898DE1B75C0952A7383D7DCECDEEF6CE16F09E9B9DEE86447F927CAB41CD4FE25B662471CB8CC688341FD791FA082A9C7E5E7E998305B832DD6BC714265F3FAFCCF27DA1D028C8535FC775CF13E378FF42EB862DDEE808F8F9AC9CE79F7872DA193FB411A9154E3BC182B9C5E1A7532330298EC0EDCDF1F0C99BF85278AF03525628D6EF9C18A175F8B00FDAA07EF9CAD56B4F58B576CD092BEAF0AF1E096723C77806D1365B25E9CEA17467704B76595961B183ACA42F89D8B7144B260F490C0236DD4A6E7FEDE15587D498C496C189A2264677A26872A0C62E31EEF6053D3E5C91429B8347D1DDD86D1A01EC000648D0088C111AA16C57F2C7841034C09DAEA80FC763454DC510843D15FDFD7DD1BDA1581FD7B26F3016A8803559C4571137B58A132BC4247F89A437411509324B2AD805CB8AFE890A88D82BBCD101B44B1B4C9CF10DE5C6E37F38F4DEEB67166F69BB74F71581FD9BF2A19DABD3B4B3B652EC7FF593E7DCB8F468B36865F67966ADC556568558DCA2808B8C30870D8C05D8F171235C3D0E0E44D8062E4671C91122EC3183F5FB1815762278DE0CBF0F6BD232A5FC20A050D543BBE3B808E2438376D6F7B59DFA6CA2CCF2B8ACF86C92188ECE4D66498E4D2166F607FD31F8DAD52C663606C879D876861A152226C3AF6F3A214BBC261FE9487DEFDBB87567E9329FD65929EF2D787A3D302D5ABC0961E88A1412ED6E8C77C2F149829A4C123382BEBD15492167D58A6A36DDC284D4D2E7EE6E6B6CE9B4B66F186FD35495E48E005DC05AEE09AE559B115F6E0A8711DCA2535ACA54E982A67657779B3BAE360A3C048D8175312B97311C84B5280BC5E3E5DA322B37496FBBF5FC754C6F59F848A60613F78554CD74385E4A96F5524CB5CCB69E9F6B9987CDA8D3F1DEF43AE6582FC3544787F5FCA225B905702DE30690CDB14C5F34E68D698DD9649770D2D1D6ED72A1F459D6AEF7EA3EEF4D2131D70521BDA7A9A1A3853985DAB422B5CF78531D74C194D5E584A558386CF14E94F11BDA5462E11E32DA8527DDE520D8F7808B5BCBD52F7E9BB41F3D30EC2C960D444251D8FCE9D7316051AF803B161FAC2753A6A99F6D2753E93837D07345A059BCD781293E8EA7248C45203E6CBCE72FD33FC87674BD246288AD2F3CFBEF87BF06E415D2672999A7DF95EA5BB16AE59A952BD6AE5ABB6AF5BA957D2B4E5855B76E6D0EA1942C379D8AD45F6888D66E66E727E2F6592892436C94CCE0872A2C12130CEDFD6742089643B22859CA0306E4EE4BAB0325D3E367127D86F5384D1366E031321E9800D0F6D30CE20C1B2B94CCD228C96B254A666AE4846539A8325523EA567994CCD1F0C90BD304A909FB18A0D15C8D68BA454549B9C691B4AD03FDA0510D9B66207AB6464A5AC9A35E951A3DF532DDD066C97B3B86EA272E14B986C51A03AE74B1E40A0D65BAB596D42EC99B1C86664DDCAF32101357F486E24DB771A05665492CCE664A72D9195E5F3B3E2FB76A99FC0C8692029E59EC40C7DFC58E3D25F9FC9DED38275057C5A9EC3420AE082C4B1238EB13DE57C6733619E436A95A14B1777D25A7A8A75E7DF1E80AFB6C85D5FAAA2B13A8F2B4B82843CC0363294ED00715D0EC86B255ECD404312ABEB8328AF3D3000B7E9DC1A110852FEE439161705D83BE11EF58B4563829B8AB24766B6AE3BCA05E4E5F1F48686B82B3EE9DD0A6D0DD5E46060FE2D2BBB736950079F2FBFA62B015E7C19D7FC8B795926A1DA6AFAF7EE5097D411F6C2B6D814D645F50F3776BD7ADA1247B23AE214EA2C45655BD4326EBC9863CD84FD8484949257CF90B3178C0E38FF9D8A7EBE0FBAAAA779957672C8825D60E007F14F86BD58C1B64721239398F486413257516FB2BEE3973482374F36405F23D54FC4101E46818F4865936309ABC01F87CA72B847330256BAB74AB743E2B6CD06178535627F180F6AD64731E69215B64E220B9B9508FAD32C9E34F2E99E4F327B74C64FED4219302FED4299342FED4259322FE040D5CCC9F4E9149097FDA299352FE74AA4CCAF8D3E93299C29FCE90C954FED42F9369FC695026D3F9D3904C66F0A7119928FCE94C99CCE44F019994F3A7A04C66F1A7B04C66F3A708CC8916FB83F98B1C1293C91C3217F3EE9149057F1A97C93CFEB44F26F3F9D3393259C09FCE9349257FBA40260BF9D32764B2883F7D4A268BF9D3C532A9E24F9F9649357FBA542635FCE9724A6A2D6AAA8EF41C72A54C9690A598FD804C96F1A7CFC864397FBA5626B5FCE97A99D4F1A71B61B0A3CD7BD8086ED3869E52559D62F0C9E426F25934EE9B65B29AAC41299FA3B0A134E2876C0B5C69E30CF4C160E823A141112C53B2AE2A793F29C92ECD4CD5C6AFE42CCB6878E6902F80034174D35804BFA7A4A4B3CA952A904A56C49A6A32B993DC954FBE48BE44F9A60435FB092EA0C1AF0D44359A26016885036351F8F409F682079DE0B0C09954251C8D4FFE5ABD031A0843CB4D19D52F456DEEC7DA3C8012254830FA94C2A334E9B41ED043A390EC81240FFD2CF36C58C52A53F5EC34E1F8C741BE06CD82FD8396F1919BFF71F2042AFC0D2C238B261CEE39C837119F4DCD76EB1DE45B48CCA149C742A0DB7790E4A086330607F91E6273A9D9A108CAFB2192F368D2091AE8F11324E5D31447A30EF273A4CBD4E430D1419E475A014D38A805A1BF41422135393B426D5E426A119DEC58C8417E8B4CC5D4EC24D1415E4562094D3E94E07ABD8EE452CACF54B0C437115146138F703585FF840C5368C2519AA6CDBB489F4A93CE4421EBDF90348D269F1939C807489A4E531C13423DFE810C33A86EE3D941FECDCD56DC5B420685B24B300ECAEC7E2615F71710938598721ABFF1E1A00EC4CCA2E2CE8283E6E3FBEC38C72A072D44CC1C2AEE94386809BECFA5EC428A834EC1B78A387FBD834E47CCBC3866A583CE44CC7C90D0C4CB988DEF0B80A349D5AB0231959438B0263041B07A2CA486AD3A075D88D84554DD0B8BE3AB10BF981AF79A1C7409A2AB28DFDC5191CB11594DD5DD1415BD02CA16BF62E3836EAD72569B39B8E593396E35C071E286F09077C0B72E87AE86BEF247614F6418B02C7EDB29D3B5745D1E5D434F8006E034C06DE0B88D32594956C11C454F82396DD01FC589721066BBE0A03732189FD464BA89F337501CDC4D58A71A1873493F7D92435BA114B6D7DB3E048685F54A62DA20D32DD4994737635C5AC4261AFCE23D32867BDA327531C744DBB010D6A44B315986096BCC5A4CEA30598109AC25165559F579B407459F02A32171AAC9A13B51DA4A4C5661B21A933598ACC5641D262760B21E1336B56EC4E4444C4EC2E4644C3661D2804923264D143F528310DC49C587D360646AC7B7070691A71993164C5A31D98CC9164C9C986CC5641B262E4C58B3B83169C7A40393ED987462E2C1A40B936E4CD804D283C92998F462C2AAB90B935331390DBA51D5A7250A86059069753A267D989C818917937E4C0630612C3E4C863019C66404133F266762B21B930026A3980431096112C6E42C4C2298B0B117C3640C933D98EC05C78A1153259864ACD21FDC03276B95B04A5C3CD980D8CC7BD209BF7F9743EF34F4B1D8F4A15F828550D5AE64D33039B0632B1FFAE53C7A17BD07862FECB985E1C47F100FC629397172211ACA596D8295E957E8576105410FC310E1956B63115F7B50845CB021315895B418B114A408F577591B12F47EFA006AF2208C44ADC161EF8FB5B6B5B873074C749043A7109F1C206EB4B2C4CAA456D029F4183D8E1A3F6CD078986BBCC8446353CF3A65D854E1EDFF5BA39BB6F0D7E8D751DFC7C1E343A162FD5056559D5812B07E833E9947EFA64FC1948313936EBDB1C4A42553AE40E8D3F4192CF25B325941EAD1BB3F4B4995D52D871CFA1CD83BA81AC7A0BD27AF8CADEF61C8F47BF4FB79F4BBF4075CB06E5EF911F92C127E0C84A84F5FE19955E6B563E626EB070DACB530FF5453F61CFA4B4AB69A349E25CB36EBCE9FD35FE5D3E7E9AFC18F4355D41F61491B8A4F66212FD01751E44B2032AA1779822591493C6C94BC427F8B327F073271FF68AF37CC9748859503C6FD9BD95A7EECBD5A4F682816FFE11C50EE0FF435B4A0D7C17C370E04D8C6904C5F251BB0D9DF94C909641D92DFCE8285947BF78ABE5764B296A3DE050F1C1D83AA54D6576AA1CE4955963FD2366B2C6B1B14DA21007D4FA62BE9DFF2E8FBF483B83E2B2A6321DE78E008AAAACD969D0E9543A6FFA4FFC041F92FD86FE3D95756467CC3E0747C116E8060ADF38CD60A7580082A6434DAA2845CB24425021B6B9204BDA22A160CC5FC43D02BD9FC4196B2C806285DCA8669DAC8D380EBBFDCF8B32CE572CE3C08E4D4664F9AD8657A196B0C09564573D5DA80CD99DDFDA464F5E47D659689D5747A0A89B2542215638D4B6108AB3AFA995906335BB9A7BF9630C9A923D3D10EE58242D3A4A9D81CD36198A80A41249A7E22891B8A0DD865A95C9A89626681DF126270466A1D0B3257E8190B838250CB2613376A5E952191355A9B20048A2C4D120D15A990E6A206F360EA50ED64200037318585278FA32C4696A58552259ACD2260540D422D1C7784D00B7DD4E8027A592F4A966AA46AECFE2530FF259A687B008C33CC8D733964542BB1D78BE6610700955CC14DBC1EB4AFDAEAACDE011869156256831F53258EE09D4CB5CF5513A064C3E4C14CCBF8808FDD2437EFE6A9A65265E904691D6ABC5EE75BE293BC6652A351F034A75A8AA6AC60CC0D110A91A593A593509F4D30E97DC4E24C84A3D84614DBA4AB265CC062D730C157AA8FB2D4CA3B68337844D598E0878E5A55230884A263115FDC6B6EAA9AF4E080F3894CA6C120B8BA94E265699BB415ADCDF5914C5857753756BD1D8CB06A2B37B9ED58C7CE8FB22FAB93DA8552BB75AEDF7716DC6B8DE26680898C9D30253006A857AF740A2AB053D71923704FB4099A912DF99DD0252A42964E974E43EE3E586A540E44F097070D07387D2B70996EBAFE7148350EF28803A4D1052018ECDA21811D176DAC1CD0D6EB952739243F0B6DF9571A0E69378F8C60400BC428B816B3A2295953559DD9D111CBC7D6DEE3B2E495FA210891C05BD7672E244702B7B238C91FA75C094A7BF2A43109D6A5E593844B3912CCD8C3E050A57DD2D9B21492C2A8E0B9A67D2AC49E8FBC178043F3F226A9CBF8304DFA84F4C95CD0ED536022035807D8D75A9866FA52CF098B125039D2A761FF6688FF62B32C5D2A5D9227ED972E83F3738DD11D62AC7005B315FFA70C2D9108C459D21570609D20CC1D8A318EB86BCD91AE82DD31B6008846BDC350C0D5105B4907A4CF682D94DCF0D7E649574AD7C1F0E3065E8ADF39C40F75BA30817AE0F7E9FC47F204268F7FA08157E8E1344A82F34B681EB2149EE0CC8E10807902E60B280B582060A1804502160B582260A98065024E1170AA80D3049C2EE00C011501670A582EE02C01677388E76A0C5608384FC0F9022E10B052C085022E1270B1805502560B58C3211E8531B84CC0E502D60A5887B0380F979000F7E34621C04FE0A116C05C0CF3193C81C13C5C281002BC140F8C09956E20B02D8CE5025C5173949C788C34B868DBD2D2A663C4F93869AAE97B886C5BDA43DDCBF498B6653D74BD5DB1EB71ED8A1D90594A961EB95DC90264B692AD477A946C40E628397A64B792034887E2D0237B14072073955C3DB257C905649E92A747EE52F20099AFE4EB91A729F9809415598FEC5364401628057AA457290064A152A8470E2885802C528AF4489F5204C862A5588F1C568A0159A294E8917EA50490A54AA91EB95B2905649952A6478E2A65809CA24CD12343CA14404E55A6EA9167295301394D99A647469569809CAE4C2F1D63C83144EE55A603728632438F9C5066005251143DF26C4501E44C65A61E79AE321390E54AB91E79BE520EC859CA2C3DF242651620672BB3F5C84F2AB301394799A3475EA4CC01E45C65AE1E7989321790154A851EB95FA900E43C659E1E7999320F90F395F97AE415CA7C402E5016945EC5905721F26A6501202B954A3DF21AA512900B95857AE475CA42402E5216E99137288B00B95859AC471E5416F7D41C21B7ACAF52AAF0FD56A5AAE73E426008DD08A90C03CA6E5B4A6E23B7238EDC413ECF0717FC588D9D7DE65106030B06D1A1FA9A254B8F93BBEDE4303D8C239865AF20385B96F0ECECE9CBE41E10834FF792AF00D74178CEC642C83448BF4A0E0BF1E742CE2C80CB403C1B91F74101C7C98336D2032F0FD5D72C8DBF1CA9AF59C65FEE8B175CC374137AB3A7A3E41814804FC7E149624F0FC3938DA950C88A6B03B7EA0607D50E9C8F9047851B5904B41920F80E50051CC863F54B4ABF7E9C3C6923AAADBA9761C36D5BD6B3B4F429203E6D20A2F2486E53EC407F06E8DF36D0D1B320BD5DC902FAB3407FCE40472783F4ED4A36D0BF0BF4EF1BE8E86F90EE517280FE03A0FFC84047D783F46EC501F41F03FDA7063A7A21A4F728B940FF19D07F61A0A343427AAF9207F45F02FD57063AFA26EEA8F281FE6BA0BF60A0A39BE23E4B06FA8B407FD940478FC5DD5701D05F01FAEF0C74745EDC931502FDF740FF83818E7E8C3BB522A0BF06F4370C747469DCBF1503FD8F407FCB4047EFC65D5D09D0DF06FA3B063A3A3AEEF54A81FE67A0FFC540479FC71D6019D0FF0AF4F70C74747FDC174E01FAFB40FFBB818E9E90BBC5A940FF10E8FF34D0D129720F390DE8FF02FA7F0C74F48FDC594ED7D9FF34B470E9766EFFF8C4ED9FD9FA0E321546C72930A5F79206F891282FD9452E24A702E769E409723A7999F4C1AD8433E83CE2A50DA49F7AC900BD900CD2DB898F3E4186E8CB6418368D46A479C42F359033252FD92D5D48027895411D33F4EF30668A001E6463A68CD2FA2565D4C61457BD1C1F357B97F500D98EE46C03591D37138A1D39729023D7C0A18E9CB3952CE4C8430ED9C0A18E9D73956CE428408E2203873A7ACE577290A318394A0D1CEAF8B95071204719724C3570A823E8934A2E724C438E19060E750C5DA4E42187821CE5060E75145DA2E423C72CE49863E050C7D17E45468EB9C831CFC0A18EA4CB9402E4982F4C45635087D2154A61A2A9D01BB9A9B0A7E37153A9273298CA18447B7BE0CAD95E324CC6C97E32015E7C1FF92E399BFC899C434BC8B97425DCBFEE26E7D3BDE402308305AA199077C00C1C00EF116650895A2F624AA973103783AB99192C4672B581AC9AC135DC0C6A9063A981433583EBB8192C438E5A03876A06377033A8438E7A03876A0607959CC496215FE62DC39E1EE62DC36859D032FBA1652E8596B90C5AE67278BB026ABF92AEE2B5CF7A176A5F0177687E80B58748B3E7185D7F90CC38464F3C484AEFA727DF44E463B4F126927DA7D45B6EBB8DE4D8EF84C9F6202929A3CD586F37D4F94E6943B95D23CD05FD5BEACBE866FB31BAED3885A358ACBE5BC902C6D2F22C2363BB81115BC10D2D70277DBD3CDBC8D86160C4C6702B39C0F878798E91713B32DA54461C1A6EC5018C37943B8C8C9D06893842DC4A2E3006CB8B13CAF61844E248712B79C0D95C5E9C50789741268E18B7920F9C33CB8B134AEF36C8C491E38651732779A7BC3857E35C089C3BEA6B1E24171EA7BDB092A2BBD018E82E1C476EA500F81F2F2FCE33F29F8AFC671BF97158B99542E03F505E9C6FE43F0DF92F32F2E38CE5568A80FF8CF262D9C87F3AF25F62E4C719CCAD14037F5D797181C63F1FF8FB90FF7C233FCE686EA5042C6247A255E164E6564ACDACEA0C43EBE2ACE656CACCACCA6B60C4E9CDAD4C31B3AA7E0323CE736E65AA99550D18BA0B273CB732CDCCAA060D1271E6732BD34DADCA671089CB05B732C3D4AA860C32710DE1561453AB1A36C8C485855B99696A55232656856B0EB7526E6A557E13ABC2E5885B99656A55679A5815AE54DCCA6C53ABDA6D6255B888712B734CAD2A606255B8BE71C39A6659527C6FFF1EF790F844476910F73AECDFA221F215115CBF41D683AFBC1E7CE58DB0AEBF09EE5FDF4CB691CF9161C8178325C5017288DC42BE08F3CE5DE46972377985DC0333CB57683EAC2514723F5D4D1EA44DE408ED23C7E8287998EE278FD21BE01EE271B852F30CF9067D9D3C45DF274F4B0AF996B4983C2B39C973520FF99E34416E942E263749D7929B2100FA9CF418B94D7A96DC21BD4A0E497F215FB41591BB6CB3C8DDB675E41E5B0BF98ACD4B0EDB42E47EDB65E441688823B687C831DB93E461DB8BE451DB5BE46BF622F2B87D36F986FD64F294BD8D3C6D0F916FD9CF25CFDA6F25CFD9EF25DFB33F477E08FE3FFCFFF6FF6759F5FF11ABFE3F6AD5FFC7ACFAFF31CBFE7F8F65FFBFD7B2FF1FB7ECFF2732F4FFFB32F4FF6767E8FFCFC9D0FF9FFBB1F9FFF3ACFAFFF3ADFAFF0BACFAFF0BADFAFF4F58F6FF9FB4ECFF3F65D9FF5F64D9FF5F9CA1FFBF2443FFFFE90CFDFFFE0CFDFFA5FF0FFFFF3CF8FF5F83FF7F01FCFF4BE0FF5F01FFFF3BF0FFAF82FF7F0DFCFF1BE0FFDF04FFFF3650DE01FFFF2EF8FFBF82FF7F0FFCFF07E0FF3F04FFFF4FF0FFFFA6FBE146E30D54A2C7A99D3E43B3E17688036E58E4490A95A5C5B45072D262A9879682FFFF35F8FF17C0FFBF04FEFF15F0FFBF03FFFF2AF8FFD7C0FFBF01FEFF4DF0FF6F83FF7F07FCFFBBE0FFFF0AFEFF3DF0FF1F40437C08FEFF9FE0FFFF6D7B11CE9FDEA292BD88DAEDB369B6FD64EAB0B7D13C7B88CAF67369A1FD565A6CBF9796DA9FA353C1FF5FF6FFF6FF975BF5FF5758F5FF575AF5FF5759F5FF072CFBFFAB2DFBFFCF58F6FFD758F6FFD766E8FFAFCBD0FF5F9FA1FFBF2143FF7FE3C7E6FF0F5AF5FF3759F5FF9FB5EAFF6FB6EAFF6FB1ECFF3F67D9FFDF6AD9FFDF66D9FFDF9EA1FFBF2343FFFFF90CFDFFA10CFDFF17FE0FFE9FCE818F202A481D9D4F4EA295641B5D4486691589D11A72802E25B7D0E5E438AD234FD37AF20A78D03FC17DFF7CBA8E2A743D7C57B09136C137027D7413C86F04FFDF0CFEBF15FCFF16F0FF5BC1FFBBC0FFBBC1FF7780FFEF04FFDF05FE7F8734412BA48BE97CE95A5A29DD4E17498FD12AE9595A23BD4A974A7F816F238A689D6D16ADB7ADA3AB6C2D748DCD4BD7D94274BDED32BAD176909E647B886EB23D491BC1FF3783FF6F05FFBF05FCFF56F0FF2E3BF873F0FF1DE0FF3BC1FF7781FFDF01FEBFB798D22F92CFC7CF3072D931C4363C2229956A1EA177F5DA1FA377F7DA9678B21EA3BB7A6D30243DBD764C8FD27BE34D6D031ECEB7D4637F34AFD7666B3F46EF3B461FE24D4F75472B4EDEF4F8143F5A71F2A31550E5083D2A54192579EC386503AAB21427970CF439461F8199E8BE84A2D7C78B5E1F2F7A3D0D89A21FA58F89A24F23B98C7F35CE821995FA845A5F6D336E65BCD095BCD0623B7EF18845319CC4B816D71CA1DF2C95BE401C35C7E8B70FDF4FBFE35AF20C2942EC31FAC35ED703F43B4B8ED09F1C4639A0EB8FE202164076ACC774B0FF5B7B9F26F6C33D35BD703806C762885133FC94FE4C6428152566D72C019EFB04FDE7711350889D55BE00CAFE059E90D1DFD8892AE685781BCD0026142373B6E3F4655B9CEB157A34A5B0DFDB092F33172FC50AB6A9E287C973A1C1B7D107E81BC8825FCEFC31CE321F4AB363A31EA36FE59E3CE701FAA739A74CA1EF34543C40FF52718A5A8DBFD2BF2557F328FDBBAAD987F41F825E228ACCAA394AFFAD92FF033992B34B36215EB2930DC9D9A51C95EC3025E7AB64D95439A9F0B0A01749C566F43235FF14692AD2F9562E1B1B79D0A4CB14FB516986CAA34833CD64CC56CB9823CD35A3CF57E90BA44A932A2C56C95552B52097111BEBD91C50E1A8B4546558665EC75A955E67DA442BEF53C9D2AAB87C3BFC83F2E7CE3E2AAD5119D64AEB4C15D8A016B0913E69D2C3DF54C9274A27A56AC3068DA751F04C114A3890E7A8D4AC72B498D6628BAAA453DA6AAA64DB7DF112DCA60C1D87E3EDB05D6D46C1905D33F7A8E4D10474990AD8A10AE8914E31EB875DF708FAA9D2692635388393F3F0AA98384A7E10A4538077D5941E2A93063C35A5F7954983001E2A937C008E944943001E2B9346003CC5C1331C3CCBC17739F801073FE6E0671CFC92835F73F02207AF70F07B0E5EE3E08F1CBCCDC19F39F82B07EF73F02107FF62004EF004B40B9823609E800502160B5826E03401150167093857C0F902560AB858C01A0197095827604B997426C276013B04DC2E60A7801E01BB04EC16704799144078AA80A70978BA807D029E21F8BD02F60B3820E0A0803E0187041C167044C8F10B78A680BB050C087896E08F0818153026E098807B04DC2BE0B88013655210E13E01CF16F01C01CF15F03CC17FBE80170878A1809F10F093027E4AC08B04BC58C8B944C04F0BB85FC04B05BC5CF05F21E095025E25E00101AF16F033025E23E0B5A25DAE13F07A016F10F046010F0AFE9B04FCAC80370B788B809F13F056016F13F07621E70E013F2FE02101BF80F03E3E82F12E657CE6B4B3010E667D16042FDB6A8E49D147A4B1DE520946FCF861CE8FBF3022F857F1114FAAEEA76F3C4D4A203D229DF3386480FFDF48E931E9BC6D8F48FB7A21EB85304FD73C285DC42538A48BA54B84844522362885A22E3FEC7A44BAB277C911E99AA3D2F55F47C9E878A5032C4CBA89DD9CCB81DF0835FD6D87543F031DFF112BF84D2FFE895993C0FC17504B03041400000008009148E846C7E04839CD040000A51300001D0000004253435F5250543030322F4253435F5250543030325F312E6A72786D6CDD58DB8ED330107D86AF08114F486E6DC7B113D42EE25204D272DBEE0212422B5FDBA0342989BB6D41FC3B93B65BBA50418B4A1FD8872AB667E6F81CCFCE38E93C988DF2E0CA56755616DD90B47018D84297262B06DDF0E2FC294AC20727B73B9F643DB6D5991D97950FC0A5A8BBE1D0FBF1FD767BB9542D96EA565D4E2A6D5D590D6CABB0FEE66AB874BD3FABB3B5FB743A6D4DA31638B429C6A4FDFEC5695F0FED48A2ACA8BD2CB40D03B0BF5F2F264F4B2D3D6CF56FD0839D5C66B5B961D182893028E4C876C347FDC79767AFCF31A697240C72590C267200F383AA2CAFE6613086D1BBCCF861371422598E9FD96C30F4202D0169CB2AB3855F31389585A9B51C03415DE69351B1E99A5BE75FC86A9081213856106473ECCBF1C64895DE97A38D89C92433DD909944729518C475922286B1422AB50249E6B84DB9249825E1C9ED5B9D7155025F3F5FD1CC56CCBF94E5280CAE643E8149DA8A629132C153829B3F1AB67FE33A5BFBE1DFDACD7FB1931518785BAD0CCF7AAF5F9D9D5F3E7F0222E5B286B4FB24AF64ABD1BED5F715A469C3E056C7582727B97FDB04EBCDC695AD9B8C3EE9DCF9F0F8C9C3F3871FC2F0E3C7934E7BBB5903DC5E2337A3CF135BCD97F19BF0EB30B5CDADF6C1BDC055E52850EAB29E96FE7249E6D2F83C980E6D6583F5AE836E70F7F5D7F5F01B6480016E6A1EF47B6F0259EB8F1F17E037F13A2EB3B95929F0EA8FDC17D64F6CADAB6CEC6FB05E70FE79197C56936BA83DC53E182088B005EA79E1EDC05607C63A7DF8A8777A2C62CFFBFD8B5E9F1C178E1E172E3A2E1C3B16DCE38B7EEFEC78FF038D53EF2658FD396F9D67230BFD6F343E30DCC571D95DFC7B76C67A99E58B380A5A6A30DCECB8F538CFFCF97C0C5B017ED6EB618308A6DECEFCD3558866BCACE1BDDC8EA04307B3451B9D439030982EDB3211F0BC8E8DE3EB2E4B7122A5A618B9841804ADD521259D4122154CB348F348C8456F5BA1AE319AE7877936289A11E401FC42D16B2E633ED332DF587A911993DBE5C61BB54AF06E7E5E2E2476D04C5D3EAF27C500F8665F608AC056B3FA5199C3FE7C35B1701531AEB7BED73D371036F373F4AC317B0D4B23658DB12BEBF56EDB1BDB5D4EFD906D5B93BDFBF4EBA2CA7E5B9CDA16D345908D951D8F8288E8E7C360F1D6C3509162965187348E9A2B4F6C506265826CAA52A6891192D96D877110D5D3E3C9BCEA2F87163AA2E98E42435A3B85994331331684B6315271229100E189103CC1A9F87F84A687169A257447A1556C8DE33C45712C3462842B282FC42247A44C528E0D8F92FF47E8E8D042F388FD2234DB9ED13495463A02253C8D10933642E922C1B1E0503A548231FF7F84667B0A9D6785FD7DA7C43F140E377BF0B5BE862A1E47F0364AB451A0AF31486A9B2287552C0DD4138275D85EA137707F068696BC1334A7491C712A11D322462C4D34BC03138D2C518E711C7385F19ED011153B41030476B16C5EBA638E98B012252C85D664134E30A71CD3684F6896EC26B88B6396B8482113910431461A686EA044A78412917017933DA1794477828E85E54E3A8E2411183100438A718652662DB1424226A47B42C357919DA085A1097CD91028018E88290D4F825390DE89946B0105745FD6CB2423385D41379B111BE0D7D09250CD85E228959202B412C09A6AE4A8108CC629165AFE04DD3C3597D6C58DF6FA22DB696F7E6E3BB9FD1D504B010214000A0000000000194BE8460000000000000000000000000B00000000000000000010000000000000004253435F5250543030322F504B01021400140000000800A648E84672D23AF31E300000049500001C00000000000000000020000000290000004253435F5250543030322F4253435F5250543030322E6A6173706572504B010214001400000008004947E846F7CADD4C40060000071900001B00000000000000000020000000813000004253435F5250543030322F4253435F5250543030322E6A72786D6C504B010214001400000008009248E846F9F4307EA42D00000F8D00001E00000000000000000020000000FA3600004253435F5250543030322F4253435F5250543030325F312E6A6173706572504B010214001400000008009148E846C7E04839CD040000A51300001D00000000000000000020000000DA6400004253435F5250543030322F4253435F5250543030325F312E6A72786D6C504B0506000000000500050063010000E26900000000,'SWOT jasper report!','admin','2014-12-21 11:59:02','admin','2015-07-08 19:28:24');
/*!40000 ALTER TABLE `tb_sys_jreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_jreport_param`
--

DROP TABLE IF EXISTS `tb_sys_jreport_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_jreport_param` (
  `OID` char(36) NOT NULL,
  `REPORT_ID` varchar(50) NOT NULL,
  `URL_PARAM` varchar(100) NOT NULL,
  `RPT_PARAM` varchar(100) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`REPORT_ID`,`RPT_PARAM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_jreport_param`
--

LOCK TABLES `tb_sys_jreport_param` WRITE;
/*!40000 ALTER TABLE `tb_sys_jreport_param` DISABLE KEYS */;
INSERT INTO `tb_sys_jreport_param` VALUES ('d9276dc6-a5fe-4a10-aa1f-54e7e1355a82','BSC_RPT001','oid','OID','admin','2014-12-20 14:25:37',NULL,NULL),('ff4cb8e9-ec8a-4db0-99e7-d49d56ce450a','BSC_RPT002','reportId','REPORT_ID','admin','2014-12-21 11:59:28',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_jreport_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_login_log`
--

DROP TABLE IF EXISTS `tb_sys_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_login_log` (
  `OID` char(36) NOT NULL,
  `USER` varchar(24) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_login_log`
--

LOCK TABLES `tb_sys_login_log` WRITE;
/*!40000 ALTER TABLE `tb_sys_login_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_mail_helper`
--

DROP TABLE IF EXISTS `tb_sys_mail_helper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_mail_helper` (
  `OID` char(36) NOT NULL,
  `MAIL_ID` varchar(17) NOT NULL,
  `SUBJECT` varchar(200) NOT NULL,
  `TEXT` blob,
  `MAIL_FROM` varchar(100) NOT NULL,
  `MAIL_TO` varchar(100) NOT NULL,
  `MAIL_CC` varchar(1000) DEFAULT NULL,
  `MAIL_BCC` varchar(1000) DEFAULT NULL,
  `SUCCESS_FLAG` varchar(1) NOT NULL DEFAULT 'N',
  `SUCCESS_TIME` datetime DEFAULT NULL,
  `RETAIN_FLAG` varchar(1) NOT NULL DEFAULT 'N',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`MAIL_ID`),
  KEY `IDX_1` (`MAIL_ID`),
  KEY `IDX_2` (`SUCCESS_FLAG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_mail_helper`
--

LOCK TABLES `tb_sys_mail_helper` WRITE;
/*!40000 ALTER TABLE `tb_sys_mail_helper` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_mail_helper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_menu`
--

DROP TABLE IF EXISTS `tb_sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_menu` (
  `OID` char(36) NOT NULL,
  `PROG_ID` varchar(50) NOT NULL,
  `PARENT_OID` char(36) NOT NULL,
  `ENABLE_FLAG` varchar(1) NOT NULL DEFAULT 'Y',
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`PROG_ID`,`PARENT_OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_menu`
--

LOCK TABLES `tb_sys_menu` WRITE;
/*!40000 ALTER TABLE `tb_sys_menu` DISABLE KEYS */;
INSERT INTO `tb_sys_menu` VALUES ('008f1268-ff67-4ea5-b7fe-e296d18ac22c','CORE_PROG004D','00000000-0000-0000-0000-000000000000','Y','admin','2015-04-28 10:07:01',NULL,NULL),('0138f47a-9f8d-4074-a7ea-74594de5b098','BSC_PROG002D0007Q','b71100ad-c832-42c8-84e8-2c53b71a7d27','Y','admin','2014-12-19 11:24:55',NULL,NULL),('07c30112-219d-4053-a637-d5677503281a','BSC_PROG002D0008Q','b71100ad-c832-42c8-84e8-2c53b71a7d27','Y','admin','2014-12-19 11:24:55',NULL,NULL),('0a503fc9-d6fb-4d6f-8360-dcd369cc447c','CORE_PROG001D0007Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('10721f51-e349-438c-b14e-3fff5bf15987','BSC_PROG002D0002Q','b71100ad-c832-42c8-84e8-2c53b71a7d27','Y','admin','2014-12-19 11:24:55',NULL,NULL),('1167653a-5ac6-4d9e-a241-257a140f2f74','BSC_PROG002D0003Q','b71100ad-c832-42c8-84e8-2c53b71a7d27','Y','admin','2014-12-19 11:24:55',NULL,NULL),('17a66964-fd4a-4ab4-b486-2ca2deb16047','CORE_PROG001D0015Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('1afc0c6e-f6ec-4fe3-93c4-6d3fc0ffe9c2','QCHARTS_PROG002D0001Q','9f27397b-4ba3-4e8b-97c9-39037a62b752','Y','admin','2015-03-05 10:03:11',NULL,NULL),('1bcf2507-20a0-4b6f-83e4-882375011bc0','CORE_PROG001D0009Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('1f2600fa-04e5-4391-8ee3-1bd998443d05','BSC_PROG001D0007Q','66e7f870-ebe8-4af9-97fa-ae99d7f3e453','Y','admin','2015-03-11 20:35:37',NULL,NULL),('1f54fa19-41cc-47ca-9702-2b0ae96e72e3','CORE_PROG001D0010Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('2483abdb-f7a3-4387-b406-d071d412ee11','BSC_PROG003D0002Q','53a8cd35-6b39-45c0-9f20-22571bc8b29f','Y','admin','2015-04-04 15:56:10',NULL,NULL),('299b88e0-e796-45c3-999c-e17d7a5e2a8f','BSC_PROG003D0004Q','53a8cd35-6b39-45c0-9f20-22571bc8b29f','Y','admin','2015-04-04 15:56:10',NULL,NULL),('2cf42862-8d7d-4fe7-9a78-2e847aed8615','BSC_PROG004D0001Q','efb5e12b-3fa8-4727-97b1-d0c591e186aa','Y','admin','2015-03-23 20:44:20',NULL,NULL),('338753e5-2922-43d6-9a39-717c1cb6fb84','CORE_PROG001D0003Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('35f40945-0f8f-4ef3-a85d-94511517c91e','CORE_PROG003D0002Q','873371c0-a655-445b-9a63-b24a6cd582cf','Y','admin','2014-10-26 10:19:54',NULL,NULL),('37b9e980-c1ae-4abf-91df-d74407007b0c','CORE_PROG001D0005Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('3c4d827f-b053-42bf-acf1-100bf1b60c1a','CORE_PROG001D0012Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('3e32e835-6ff7-4c06-aeb8-103416f3676d','CORE_PROG001D0013Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('402c8ef9-bdcf-40f9-b3a4-b2fc785f16ac','QCHARTS_PROG002D0002Q','9f27397b-4ba3-4e8b-97c9-39037a62b752','Y','admin','2015-03-05 10:03:11',NULL,NULL),('432ac364-9042-4641-a045-6c55bd3f922f','CORE_PROG001D0008Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('50d8888a-b945-4d00-b192-9b64f7929890','BSC_PROG004D0003Q','efb5e12b-3fa8-4727-97b1-d0c591e186aa','Y','admin','2015-03-23 20:44:20',NULL,NULL),('53a8cd35-6b39-45c0-9f20-22571bc8b29f','BSC_PROG003D','00000000-0000-0000-0000-000000000000','Y','admin','2014-12-01 16:04:15',NULL,NULL),('593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','CORE_PROG001D','00000000-0000-0000-0000-000000000000','Y','admin','2014-10-02 00:00:00',NULL,NULL),('5eb6660c-515c-44e7-a01e-1bc12acd047f','BSC_PROG003D0006Q','53a8cd35-6b39-45c0-9f20-22571bc8b29f','Y','admin','2015-04-04 15:56:10',NULL,NULL),('5ef0a2f9-14df-42c6-8261-7c33f459b260','BSC_PROG001D0003Q','66e7f870-ebe8-4af9-97fa-ae99d7f3e453','Y','admin','2015-03-11 20:35:37',NULL,NULL),('649e29ca-740a-4e76-a8f3-8f47ad8af183','BSC_PROG002D0006Q','b71100ad-c832-42c8-84e8-2c53b71a7d27','Y','admin','2014-12-19 11:24:55',NULL,NULL),('64e6c66d-90cf-46ad-98bb-3f2e0125ad8b','CORE_PROG001D0011Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('65497037-6535-4a23-81d4-9aef0dca9dfb','QCHARTS_PROG001D0002Q','d54505b8-8a6e-4b09-9797-1fd3e61877c9','Y','admin','2015-03-03 16:16:40',NULL,NULL),('66e7f870-ebe8-4af9-97fa-ae99d7f3e453','BSC_PROG001D','00000000-0000-0000-0000-000000000000','Y','admin','2014-11-03 15:25:25',NULL,NULL),('6ae8d617-eb52-441a-9f32-adaca576aa98','CORE_PROG002D','00000000-0000-0000-0000-000000000000','Y','admin','2014-10-02 00:00:00',NULL,NULL),('6b05d54f-ef22-48b5-a1ec-cd16163be082','BSC_PROG004D0002Q','efb5e12b-3fa8-4727-97b1-d0c591e186aa','Y','admin','2015-03-23 20:44:20',NULL,NULL),('6df085f9-2535-4a12-baf8-633b29987778','CORE_PROG002D0003Q','6ae8d617-eb52-441a-9f32-adaca576aa98','Y','admin','2014-10-13 22:10:16',NULL,NULL),('6ffa156b-c720-4150-93a4-39040f19adc0','BSC_PROG001D0004Q','66e7f870-ebe8-4af9-97fa-ae99d7f3e453','Y','admin','2015-03-11 20:35:37',NULL,NULL),('710c6da1-3e94-4d10-aecb-e2f2adff7ce6','CORE_PROG001D0006Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('7488450d-fbbc-458a-b011-deecfe37fdce','CORE_PROG004D0002Q','008f1268-ff67-4ea5-b7fe-e296d18ac22c','Y','admin','2015-04-28 11:00:18',NULL,NULL),('7621ac94-3447-4343-b117-02f45c316ff4','CORE_PROG001D0002Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:35',NULL,NULL),('81ae3a61-4526-4073-a320-3d9000aa2c1b','CORE_PROG001D0004Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('84bd4cad-ad96-4893-a46e-befc7c96096c','CORE_PROG003D0003Q','873371c0-a655-445b-9a63-b24a6cd582cf','Y','admin','2014-10-26 10:19:54',NULL,NULL),('873371c0-a655-445b-9a63-b24a6cd582cf','CORE_PROG003D','00000000-0000-0000-0000-000000000000','Y','admin','2014-10-22 12:00:56',NULL,NULL),('99b2423f-949b-4144-9660-9e0854ab8474','BSC_PROG003D0005Q','53a8cd35-6b39-45c0-9f20-22571bc8b29f','Y','admin','2015-04-04 15:56:10',NULL,NULL),('9af0c201-6740-442a-8fb7-51c81283a25d','BSC_PROG003D0003Q','53a8cd35-6b39-45c0-9f20-22571bc8b29f','Y','admin','2015-04-04 15:56:10',NULL,NULL),('9b9defeb-9fa2-4665-8bc7-7beff3490141','BSC_PROG001D0008Q','66e7f870-ebe8-4af9-97fa-ae99d7f3e453','Y','admin','2015-03-11 20:35:37',NULL,NULL),('9f27397b-4ba3-4e8b-97c9-39037a62b752','QCHARTS_PROG002D','00000000-0000-0000-0000-000000000000','Y','admin','2015-01-12 20:06:35',NULL,NULL),('a0ab7c50-aa21-48ec-9846-ade426b6f69c','CORE_PROG002D0001Q','6ae8d617-eb52-441a-9f32-adaca576aa98','Y','admin','2014-10-13 22:10:16',NULL,NULL),('a18f33d8-2413-434c-8108-1a7dc3c6b1da','CORE_PROG001D0001Q','593e655d-aa2a-4b1b-97b2-c1d8dafc76ea','Y','admin','2015-04-27 14:18:36',NULL,NULL),('b0f80bf7-8e21-4621-86e7-e4167095c74a','QCHARTS_PROG001D0003Q','d54505b8-8a6e-4b09-9797-1fd3e61877c9','Y','admin','2015-03-03 16:16:40',NULL,NULL),('b29a3e4d-9a9d-4dea-bf0c-1e28cd53119c','BSC_PROG003D0001Q','53a8cd35-6b39-45c0-9f20-22571bc8b29f','Y','admin','2015-04-04 15:56:10',NULL,NULL),('b2debfef-c0a8-45fe-817d-15fc93c2ff52','CORE_PROG002D0002Q','6ae8d617-eb52-441a-9f32-adaca576aa98','Y','admin','2014-10-13 22:10:16',NULL,NULL),('b6541a16-dac9-4fb3-9ae1-ff3cfde8f4de','CORE_PROG004D0001Q','008f1268-ff67-4ea5-b7fe-e296d18ac22c','Y','admin','2015-04-28 11:00:18',NULL,NULL),('b71100ad-c832-42c8-84e8-2c53b71a7d27','BSC_PROG002D','00000000-0000-0000-0000-000000000000','Y','admin','2014-11-13 11:38:16',NULL,NULL),('c065e695-2053-4f68-9fd7-49e3f7135dd3','BSC_PROG002D0004Q','b71100ad-c832-42c8-84e8-2c53b71a7d27','Y','admin','2014-12-19 11:24:55',NULL,NULL),('c1669fc6-50f6-47d1-816f-1ddb58e7f891','BSC_PROG002D0001Q','b71100ad-c832-42c8-84e8-2c53b71a7d27','Y','admin','2014-12-19 11:24:55',NULL,NULL),('c20c638f-47d3-4897-8dff-39c45d684226','QCHARTS_PROG001D0001Q','d54505b8-8a6e-4b09-9797-1fd3e61877c9','Y','admin','2015-03-03 16:16:40',NULL,NULL),('c5540e07-697f-47c0-91b1-60b57419f184','BSC_PROG002D0005Q','b71100ad-c832-42c8-84e8-2c53b71a7d27','Y','admin','2014-12-19 11:24:55',NULL,NULL),('cccc79bc-a2ec-4084-aa73-bd8d60f04546','BSC_PROG001D0001Q','66e7f870-ebe8-4af9-97fa-ae99d7f3e453','Y','admin','2015-03-11 20:35:37',NULL,NULL),('cea69db7-e656-47f7-96d6-3939472a3afc','QCHARTS_PROG001D0004Q','d54505b8-8a6e-4b09-9797-1fd3e61877c9','Y','admin','2015-03-03 16:16:40',NULL,NULL),('d54505b8-8a6e-4b09-9797-1fd3e61877c9','QCHARTS_PROG001D','00000000-0000-0000-0000-000000000000','Y','admin','2015-01-10 10:23:54',NULL,NULL),('e6d1b11d-4d66-44d7-92ff-5ccfa1ed3115','CORE_PROG003D0001Q','873371c0-a655-445b-9a63-b24a6cd582cf','Y','admin','2014-10-26 10:19:54',NULL,NULL),('ed72a372-54b4-41b6-b518-bda9b775711e','BSC_PROG001D0005Q','66e7f870-ebe8-4af9-97fa-ae99d7f3e453','Y','admin','2015-03-11 20:35:37',NULL,NULL),('efb5e12b-3fa8-4727-97b1-d0c591e186aa','BSC_PROG004D','00000000-0000-0000-0000-000000000000','Y','admin','2014-12-07 10:09:09',NULL,NULL),('f9f07b48-dad7-469e-ad7d-c7b6d5b2a4a9','BSC_PROG001D0006Q','66e7f870-ebe8-4af9-97fa-ae99d7f3e453','Y','admin','2015-03-11 20:35:37',NULL,NULL),('fcb4cfdf-03bf-4298-a6d6-4aaf4ecac33b','BSC_PROG001D0002Q','66e7f870-ebe8-4af9-97fa-ae99d7f3e453','Y','admin','2015-03-11 20:35:37',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_menu_role`
--

DROP TABLE IF EXISTS `tb_sys_menu_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_menu_role` (
  `OID` char(36) NOT NULL,
  `PROG_ID` varchar(50) NOT NULL,
  `ROLE` varchar(50) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`PROG_ID`,`ROLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_menu_role`
--

LOCK TABLES `tb_sys_menu_role` WRITE;
/*!40000 ALTER TABLE `tb_sys_menu_role` DISABLE KEYS */;
INSERT INTO `tb_sys_menu_role` VALUES ('00aa5344-c5c9-4938-a0ad-43238cad5cf5','CORE_PROG001D','BSC_STANDARD','admin','2014-12-23 10:11:38',NULL,NULL),('0308cdba-9fa8-4004-907a-caaf3289b226','BSC_PROG003D0002Q','BSC_STANDARD','admin','2014-12-22 19:37:45',NULL,NULL),('069ef66c-3d01-405c-bffc-df7f87b42af9','BSC_PROG002D0007Q','BSC_STANDARD','admin','2014-12-22 19:37:26',NULL,NULL),('0a793161-8562-453b-93fa-63b6ec0141d8','BSC_PROG003D','BSC_STANDARD','admin','2014-12-22 19:37:36',NULL,NULL),('0e1b93c8-f651-4d6b-b6e7-d2d84b6f6c94','BSC_PROG001D0007Q','BSC_STANDARD','admin','2015-02-03 19:13:53',NULL,NULL),('0f6c240d-f526-4446-a380-4c4d5cdda3cc','QCHARTS_PROG001D','BSC_STANDARD','admin','2015-01-16 14:18:32',NULL,NULL),('129350e9-dcbb-420e-8102-c9c0ec9a19a6','BSC_PROG004D0001Q','BSC_STANDARD','admin','2014-12-22 19:38:00',NULL,NULL),('1deb69a0-47bd-42c4-95c4-e8826363cfa8','BSC_PROG001D0004Q','BSC_STANDARD','admin','2014-12-22 19:36:47',NULL,NULL),('1e177e7d-0ae1-48fe-915c-88af4b154568','BSC_PROG002D0002Q','BSC_STANDARD','admin','2014-12-22 19:37:02',NULL,NULL),('20893428-31be-4cc0-9e06-ca1978016ed6','BSC_PROG003D0004Q','BSC_STANDARD','admin','2015-03-31 10:41:29',NULL,NULL),('23b9fa5a-8ce0-4852-b221-5b46ad018bdd','BSC_PROG003D0003Q','BSC_STANDARD','admin','2014-12-22 19:37:50',NULL,NULL),('29fb4f8c-4492-4ba3-a1c6-ca70c5ff915f','BSC_PROG002D0008Q','BSC_STANDARD','admin','2014-12-22 19:37:31',NULL,NULL),('321a8ac9-5679-47e0-8254-2d66c0d81b34','BSC_PROG003D0001Q','BSC_STANDARD','admin','2014-12-22 19:37:40',NULL,NULL),('3cc26866-fcd3-4a4f-ad77-bd28a140ff31','BSC_PROG002D0005Q','BSC_STANDARD','admin','2014-12-22 19:37:17',NULL,NULL),('41bd849d-dd78-4142-8914-ceadd4b8fdf4','BSC_PROG001D0003Q','BSC_STANDARD','admin','2014-12-22 19:36:43',NULL,NULL),('4bca30b5-1c99-48cc-b8e4-74e917805d64','QCHARTS_PROG001D0002Q','BSC_STANDARD','admin','2015-01-16 14:18:28',NULL,NULL),('620ff50b-5f1a-4ff3-b4af-c7d170f3f113','BSC_PROG002D0003Q','BSC_STANDARD','admin','2014-12-22 19:37:08',NULL,NULL),('762846ae-92d2-417b-96dd-b110f4a661d4','BSC_PROG001D','BSC_STANDARD','admin','2014-12-22 19:36:29',NULL,NULL),('7b5ab6fe-a44a-4037-8c3d-f25db2a10b53','BSC_PROG002D0004Q','BSC_STANDARD','admin','2014-12-22 19:37:12',NULL,NULL),('837e149f-1585-4e95-a84b-1a60772eac21','BSC_PROG002D0006Q','BSC_STANDARD','admin','2014-12-22 19:37:21',NULL,NULL),('8a4078b4-dbae-42d8-9278-4176515af14a','BSC_PROG001D0002Q','BSC_STANDARD','admin','2014-12-22 19:36:37',NULL,NULL),('8d334afc-e25a-4cb9-9d60-a89877d02336','QCHARTS_PROG002D','BSC_STANDARD','admin','2015-01-12 20:07:24',NULL,NULL),('905d67ec-acb9-4af5-a14a-8aa6a37c2c38','QCHARTS_PROG002D0001Q','BSC_STANDARD','admin','2015-01-12 20:07:28',NULL,NULL),('90e996c9-dfb7-42ba-979b-21710287c6ce','BSC_PROG004D','BSC_STANDARD','admin','2014-12-22 19:37:55',NULL,NULL),('9c422fd9-51d0-4f57-9c2d-f4e903df6aa6','BSC_PROG001D0001Q','BSC_STANDARD','admin','2014-12-22 19:36:33',NULL,NULL),('aafe7fdd-6dae-49a2-bfd2-d5da61020056','BSC_PROG002D','BSC_STANDARD','admin','2014-12-22 19:36:52',NULL,NULL),('af8cc548-64d6-41ca-aa25-33277cfc6082','BSC_PROG001D0005Q','BSC_STANDARD','admin','2015-01-03 12:10:36',NULL,NULL),('b088d9d9-4c46-4903-b55d-367964a98365','CORE_PROG001D0004Q','BSC_STANDARD','admin','2014-12-23 10:23:37',NULL,NULL),('b1382d42-268b-42ae-9cbc-52e58d360cea','BSC_PROG001D0006Q','BSC_STANDARD','admin','2015-01-22 19:08:05',NULL,NULL),('c9833b85-f825-4f78-8932-d4d94b2ea6ec','BSC_PROG002D0001Q','BSC_STANDARD','admin','2014-12-22 19:36:57',NULL,NULL),('cdf4d453-5532-4aa7-be0d-82489742812b','QCHARTS_PROG002D0002Q','BSC_STANDARD','admin','2015-03-05 10:03:29',NULL,NULL),('e4e8cbcb-67a6-436d-9738-c3b4831311aa','BSC_PROG001D0008Q','BSC_STANDARD','admin','2015-03-12 20:17:34',NULL,NULL),('ea4e3476-ba7d-422a-93c0-7abf8d62b7f5','BSC_PROG003D0005Q','BSC_STANDARD','admin','2015-04-01 19:38:30',NULL,NULL),('ff351ce7-9ebc-4691-ba83-727a1422122f','BSC_PROG003D0006Q','BSC_STANDARD','admin','2015-04-04 15:57:09',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_menu_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_msg_notice`
--

DROP TABLE IF EXISTS `tb_sys_msg_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_msg_notice` (
  `OID` char(36) NOT NULL,
  `NOTICE_ID` varchar(13) NOT NULL,
  `MSG_ID` varchar(10) NOT NULL,
  `TITLE` varchar(100) NOT NULL,
  `MESSAGE` varchar(1000) NOT NULL,
  `DATE` varchar(17) NOT NULL,
  `TIME` varchar(9) NOT NULL,
  `IS_GLOBAL` varchar(1) NOT NULL,
  `TO_ACCOUNT` varchar(24) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`NOTICE_ID`,`MSG_ID`),
  KEY `IDX_1` (`DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_msg_notice`
--

LOCK TABLES `tb_sys_msg_notice` WRITE;
/*!40000 ALTER TABLE `tb_sys_msg_notice` DISABLE KEYS */;
INSERT INTO `tb_sys_msg_notice` VALUES ('3a60e27e-2efb-479f-bb48-8bdb30d7d76a','MSG001','PUBMSG0001','Welcome!','bambooBSC is an opensource Balanced Scorecard (BSC)\n\nproject contact: \nchen.xin.nien@gmail.com\n\nproject site:\nhttp://sourceforge.net/projects/bamboobsc/\n','20141001-20501231','0001-2359','Y','*','admin','2014-01-01 00:00:01','admin','2014-10-21 12:22:11');
/*!40000 ALTER TABLE `tb_sys_msg_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_msg_notice_config`
--

DROP TABLE IF EXISTS `tb_sys_msg_notice_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_msg_notice_config` (
  `OID` char(36) NOT NULL,
  `MSG_ID` varchar(10) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `CLASS_NAME` varchar(255) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_msg_notice_config`
--

LOCK TABLES `tb_sys_msg_notice_config` WRITE;
/*!40000 ALTER TABLE `tb_sys_msg_notice_config` DISABLE KEYS */;
INSERT INTO `tb_sys_msg_notice_config` VALUES ('50c8038b-5494-4a1f-8d5d-aaa9d895ef43','PUBMSG0001','CORE','com.netsteadfast.greenstep.publish.impl.SystemMessagePublishServiceImpl','admin','2011-10-01 00:00:01','admin','2014-10-20 16:11:24');
/*!40000 ALTER TABLE `tb_sys_msg_notice_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_prog`
--

DROP TABLE IF EXISTS `tb_sys_prog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_prog` (
  `OID` char(36) NOT NULL,
  `PROG_ID` varchar(50) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `URL` varchar(255) NOT NULL,
  `EDIT_MODE` varchar(1) NOT NULL DEFAULT 'N',
  `IS_DIALOG` varchar(1) NOT NULL DEFAULT 'N',
  `IS_WINDOW` varchar(1) NOT NULL DEFAULT 'N',
  `DIALOG_W` int(4) NOT NULL DEFAULT '0',
  `DIALOG_H` int(4) NOT NULL DEFAULT '0',
  `PROG_SYSTEM` varchar(10) NOT NULL,
  `ITEM_TYPE` varchar(10) NOT NULL,
  `ICON` varchar(20) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`PROG_ID`),
  KEY `IDX_1` (`PROG_SYSTEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_prog`
--

LOCK TABLES `tb_sys_prog` WRITE;
/*!40000 ALTER TABLE `tb_sys_prog` DISABLE KEYS */;
INSERT INTO `tb_sys_prog` VALUES ('00294662-68df-4097-aac6-5406ff2ee78f','CORE_PROG001D0009Q','09 - Web Context bean','core.systemContextBeanManagmentAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-11-02 12:43:06',NULL,NULL),('004ba165-39b2-43ee-97b9-54d34eb3821a','CORE_PROG001D0010E','10 - Twitter pane (Edit)','core.systemTwitterEditAction.action','Y','N','N',0,0,'CORE','ITEM','TWITTER','admin','2014-12-18 22:08:34','admin','2014-12-18 22:10:11'),('01f4523c-0383-41fa-a6f8-a4b58d4bcaa9','CORE_PROG001D0008A','08 - Report management (Create)','core.systemJreportCreateAction.action','N','N','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2014-10-30 20:06:08',NULL,NULL),('03513766-a2a1-48ae-be36-409749a8a3d4','CORE_PROG001D0006A','06 - Message notice (Create)','core.systemMessageNoticeCreateAction.action','N','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2014-10-20 18:45:13',NULL,NULL),('04660c5c-674b-4b1d-af35-c95a93db4300','QCHARTS_PROG001D0001A','01 - Data source config (Create)','qcharts.dataSourceConfCreateAction.action','N','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-01-11 12:56:39',NULL,NULL),('04dc11fd-9f85-4c6f-b150-e2d6e2e7b311','CORE_PROG001D0007E','07 - Template settings (Edit)','core.systemTemplateEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2014-10-21 16:08:44',NULL,NULL),('04febc0b-34ee-4b2e-9da5-80f065103e21','CORE_PROG003D0002E','02 - Expression (Edit)','core.systemExpressionEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2014-10-23 14:02:20',NULL,NULL),('067fd09e-9fc5-4482-8f8c-97bcf57c7436','CORE_PROG001D0013A','13 - Form (Create)','core.systemFormCreateAction.action','N','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-25 16:54:52',NULL,NULL),('076b4b1e-0ba3-451d-9530-11367341ee7f','CORE_PROG001D0013E','13 - Form (Edit)','core.systemFormEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-26 12:52:27',NULL,NULL),('093038ce-6635-4139-bdda-36983d92bca4','BSC_PROG001D0003E','03 - Formula (Edit)','bsc.formulaEditAction.action','Y','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2014-11-19 16:41:39',NULL,NULL),('0a16584c-ed8b-4ada-bbd5-3a9e957dd604','CORE_PROG003D0002Q','02 - Expression','core.systemExpressionManagementAction.action','N','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2014-10-23 10:01:11','admin','2014-10-23 10:10:48'),('0e1be4e0-e4a0-40c9-9e86-2293360ea342','BSC_PROG002D0002E','02 - Perspective (Edit)','bsc.perspectiveEditAction.action','Y','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-15 12:40:53','admin','2014-11-16 09:33:42'),('0f9ebd8d-c935-4f24-ba04-315647c40bc7','BSC_PROG001D0004Q','04 - Score color','bsc.scoreColorManagementAction.action','N','N','N',0,0,'BSC','ITEM','GIMP','admin','2014-11-29 11:26:13','admin','2014-12-02 10:12:50'),('13639357-b390-4841-887f-81c997eb5e99','BSC_PROG002D0003E','03 - Strategy objective (Edit)','bsc.objectiveEditAction.action','Y','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-17 11:21:12','admin','2014-11-24 20:58:30'),('1597bf11-fb9a-4de9-8e7e-e2de6a6a32a8','BSC_PROG001D0001A','01 - Employee (Create)','bsc.employeeCreateAction.action','N','N','N',0,0,'BSC','ITEM','PERSON','admin','2014-11-11 21:59:29',NULL,NULL),('16f5a921-e73a-4abd-b060-45322f0f34eb','CORE_PROG001D0005Q','05 - Message config','core.systemMessageNoticeConfigManagementAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-19 14:05:19','admin','2014-10-20 15:42:51'),('1b11c7eb-6133-48fb-87f0-dfbd098ce914','CORE_PROG001D0001E','01 - Application site (Edit)','core.applicationSystemEditAction.action','Y','N','N',0,0,'CORE','ITEM','COMPUTER','admin','2014-10-02 00:00:00',NULL,NULL),('1fed314f-7e29-4be1-a05e-a03f68a84d70','QCHARTS_PROG001D0001Q','01 - Data source config','qcharts.dataSourceConfManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-01-10 10:23:28','admin','2015-01-11 10:00:49'),('20f31670-bd3c-4749-8ad1-a10e3b6e4958','CORE_PROG001D0012Q','12 - Form Template','core.systemFormTemplateManagementAction.action','N','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2015-04-24 09:31:18',NULL,NULL),('2412398b-5a40-46e5-944d-232331c29941','BSC_PROG002D0006Q','06 - Weight settings','bsc.weightManagementAction.action','N','N','N',0,0,'BSC','ITEM','VIEW_LIST','admin','2014-11-27 22:42:13','admin','2014-11-28 08:56:55'),('2546b34f-735f-4ae5-b923-bf56b38dd817','QCHARTS_PROG001D0002E','02 - Data query mapper (Edit)','qcharts.dataQueryMapperEditAction.action','Y','N','N',0,0,'QCHARTS','ITEM','PROPERTIES','admin','2015-01-14 22:40:54',NULL,NULL),('27b61c4c-b3ef-485b-a03d-694ea6725750','BSC_PROG003D0002Q','02 - Personal Report','bsc.personalReportAction.action','N','N','N',0,0,'BSC','ITEM','APPLICATION_PDF','admin','2014-12-11 10:21:53',NULL,NULL),('292b02f6-24dc-41b9-b254-4b4820c1c7ee','BSC_PROG004D0002A_C01','02 - Workspace settings (Create Content)','bsc.workspaceSettingsCreateContentAction.action','N','N','N',0,0,'BSC','ITEM','SYSTEM','admin','2014-12-29 20:48:53',NULL,NULL),('2a041c0c-5183-47c7-b919-51b2a4d2b3e8','BSC_PROG001D','Basic data','','N','N','N',0,0,'BSC','FOLDER','PROPERTIES','admin','2014-11-03 15:23:44','admin','2014-11-03 21:24:01'),('2c750670-c4af-4de2-a5c4-40b23eecea4f','CORE_PROG001D0011Q','11 - Settings','core.settingsManagementAction.action','N','N','N',0,0,'CORE','ITEM','PROPERTIES','admin','2014-12-25 22:24:52',NULL,NULL),('2fdecdc5-bcf2-41c7-9e1f-949fabe9553c','QCHARTS_PROG001D0003E','03 - Analytics config (Edit)','qcharts.analyticsConfigEditAction.action','Y','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-03-03 14:38:33','admin','2015-03-03 14:44:59'),('3630ee1b-6169-452f-821f-5c015dfb84d5','CORE_PROG001D','Config',' ','N','N','N',0,0,'CORE','FOLDER','PROPERTIES','admin','2014-10-02 00:00:00','admin','2014-10-08 09:04:26'),('3c472616-2e0d-4acb-9775-24cbbdd40e47','QCHARTS_PROG001D0002A','02 - Data query mapper (Create)','qcharts.dataQueryMapperCreateAction.action','N','N','N',0,0,'QCHARTS','ITEM','PROPERTIES','admin','2015-01-14 15:02:36',NULL,NULL),('3c79b99d-a630-4449-9e87-a85614acabb8','BSC_PROG004D0002Q_S00','Workspace layout view','bsc.workspaceSettingsViewAction.action','Y','Y','N',480,480,'BSC','ITEM','SYSTEM','admin','2014-12-31 10:05:57','admin','2014-12-31 20:02:15'),('40878892-4aad-45a2-8318-09b86372e377','CORE_PROG004D0002Q','02 - Login log','core.commomLoadForm.action?form_id=FORM_CORE_4D_002_01&form_method=init','N','N','N',0,0,'CORE','ITEM','PROPERTIES','admin','2015-04-28 10:59:59',NULL,NULL),('4102f56c-94c1-4901-bcee-9b51ada60a69','CORE_PROG001D0007E_S00','07 - Template parameter (Edit)','core.systemTemplateParamEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2014-10-21 19:08:11',NULL,NULL),('417f62db-a856-4ccd-a48f-57c540aac190','CORE_PROG001D0009A','09 - Web Context bean (Create)','core.systemContextBeanCreateAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-11-02 16:11:26','admin','2014-11-02 16:13:54'),('46563229-5b03-48dd-8c43-0b89faf8c2bd','QCHARTS_PROG001D0003A','03 - Analytics config (Create)','qcharts.analyticsConfigCreateAction.action','N','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-03-03 12:21:09',NULL,NULL),('48df9cb1-3615-4b96-adbd-e2524d81b937','BSC_PROG001D0003A','03 - Formula (Create)','bsc.formulaCreateAction.action','N','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2014-11-17 22:47:32','admin','2014-11-19 10:00:16'),('4a3524e9-9fce-4b2f-ad93-77ce9d650267','BSC_PROG001D0001E','01 - Employee (Edit)','bsc.employeeEditAction.action','Y','N','N',0,0,'BSC','ITEM','PERSON','admin','2014-11-12 21:30:07',NULL,NULL),('4a5370bd-c09f-496a-89b5-90b6fab96acd','CORE_PROG001D0003Q','03 - Menu settings','core.systemMenuManagementAction.action','N','N','N',0,0,'CORE','ITEM','FOLDER','admin','2014-10-08 09:31:51',NULL,NULL),('4e68492b-eb52-4fca-9fcf-cf4bcaf9ffaf','BSC_PROG002D0001A','01 - Vision (Create)','bsc.visionCreateAction.action','N','N','N',0,0,'BSC','ITEM','GWENVIEW','admin','2014-11-13 13:28:54',NULL,NULL),('4ec04618-edfc-4279-a506-b89b27fc41a8','CORE_PROG001D0007Q','07 - Template settings','core.systemTemplateManagementAction.action','N','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2014-10-21 13:52:53','admin','2014-10-21 14:05:40'),('50129a01-8cf8-44ed-a2d3-063bd9a0e162','BSC_PROG002D0002Q','02 - Perspective','bsc.perspectiveManagementAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-14 16:31:16','admin','2014-11-16 09:33:34'),('50a3a071-5036-446f-93c8-cef17581a746','CORE_PROG001D0007A','07 - Template settings (Create)','core.systemTemplateCreateAction.action','N','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2014-10-21 15:01:11',NULL,NULL),('530f6713-4059-42b0-87ae-04a99b270e04','BSC_PROG001D0008E','08 - Aggregation Method (Edit)','bsc.aggregationMethodEditAction.action','Y','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2015-03-12 11:50:14',NULL,NULL),('5372677a-92f3-4aa0-86a8-aa5a8987b7d0','QCHARTS_PROG001D0004Q','04 - Analytics Catalog','qcharts.analyticsCatalogManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','TEXT_SOURCE','admin','2015-03-03 16:16:25',NULL,NULL),('552343eb-10cb-4e67-8585-54443dfcd8b5','BSC_PROG002D0001E','01 - Vision (Edit)','bsc.visionEditAction.action','Y','N','N',0,0,'BSC','ITEM','GWENVIEW','admin','2014-11-13 20:42:28','admin','2014-11-13 20:47:20'),('566abf01-247c-48f3-b466-d398d2cbb5bb','CORE_PROG001D0010Q','10 - Twitter pane','core.systemTwitterManagementAction.action','N','N','N',0,0,'CORE','ITEM','TWITTER','admin','2014-12-18 19:41:23','admin','2014-12-18 21:10:39'),('56db2eea-746d-47e6-9e9f-3ec034877d22','BSC_PROG004D0002A_C00','02 - Workspace settings (Create Base)','bsc.workspaceSettingsCreateBaseAction.action','N','N','N',0,0,'BSC','ITEM','SYSTEM','admin','2014-12-29 16:31:10',NULL,NULL),('5be72761-ccb9-4250-ad81-8c6a594e9a37','CORE_PROG003D0001E','01 - WebService registration (Edit)','core.systemWsConfigEditAction.action','Y','N','N',0,0,'CORE','ITEM','WWW','admin','2014-10-22 15:53:46',NULL,NULL),('5d5ea262-703b-4d01-839a-29a60a85699b','BSC_PROG004D','Settings','','N','N','N',0,0,'BSC','FOLDER','PROPERTIES','admin','2014-12-07 10:07:10','admin','2014-12-07 10:10:42'),('62712bf3-22a3-47d6-8a70-cfd3177cfbde','BSC_PROG001D0007Q','07 - Introduction','/groovy/introduction.html','N','N','N',0,0,'BSC','ITEM','HELP_ABOUT','admin','2015-02-03 19:11:29',NULL,NULL),('67a3d99a-771f-464c-854a-1766c9fa4962','CORE_PROGCOMM0001Q','About!','./pages/about.htm','N','Y','N',300,180,'CORE','ITEM','HELP_ABOUT','admin','2014-10-29 11:41:22','admin','2014-10-29 16:39:29'),('689523ce-1641-43c7-b473-0155c701f0b4','CORE_PROG001D0013Q','13 - Form','core.systemFormManagementAction.action','N','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-25 15:15:59',NULL,NULL),('6937c0ca-c44b-47bf-913e-0b0cae521d0f','CORE_PROG003D0003E','03 - Script support settings (Edit)','core.systemBeanHelpEditAction.action','Y','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-27 14:52:36',NULL,NULL),('6a7eec4a-6cc7-432c-9626-d9e588a9be48','CORE_PROG003D0002A','02 - Expression (Create)','core.systemExpressionCreateAction.action','N','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2014-10-23 11:36:46',NULL,NULL),('6f4ba0b0-e0bd-4743-9a6a-d16e7093ccf3','CORE_PROG002D0002Q','02 - User\'s role','core.userRoleManagementAction.action','N','N','N',0,0,'CORE','ITEM','PEOPLE','admin','2014-10-13 20:11:14','admin','2014-10-13 22:10:47'),('6f5f2b43-a84c-4ff6-b837-452d9824ac29','CORE_PROG003D0003Q','03 - Script support settings','core.systemBeanHelpManagementAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-26 10:19:32','admin','2014-10-26 10:20:50'),('72ca21b1-9121-49a6-86ab-978c37a98acb','CORE_PROG001D0006Q','06 - Message notice','core.systemMessageNoticeManagementAction.action','N','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2014-10-20 12:33:03','admin','2014-10-20 12:48:14'),('74b8bdd5-93c2-408e-85dd-b112086608bd','QCHARTS_PROG001D','Config','','N','N','N',0,0,'QCHARTS','FOLDER','PROPERTIES','admin','2015-01-10 10:22:08',NULL,NULL),('75863ede-bf79-4b8e-a33b-df01bb24acda','CORE_PROG001D0008Q','08 - Report management','core.systemJreportManagementAction.action','N','N','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2014-10-30 16:14:22','admin','2014-10-30 16:22:02'),('77336175-f8e1-43ce-9ba8-169d1f7aeb68','QCHARTS_PROG001D0004A','04 - Analytics Catalog (Create)','qcharts.analyticsCatalogCreateAction.action','N','N','N',0,0,'QCHARTS','ITEM','TEXT_SOURCE','admin','2015-03-03 21:50:27',NULL,NULL),('790a73c4-b22f-4130-9dde-2a501765d4da','CORE_PROG001D0008E_S00','08 - Report paramter (Edit)','core.systemJreportEditParamAction.action','Y','N','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2014-10-31 21:13:32',NULL,NULL),('7b3ab428-b170-49fd-b784-01d88fe02d15','CORE_PROG001D0015Q','15 - Basic message','core.commomLoadForm.action?form_id=FORM001&form_method=init','N','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2015-04-27 14:17:46','admin','2015-04-27 14:18:21'),('7ba4e131-3438-4956-a6f9-c3166128206e','QCHARTS_PROG001D0003Q','03 - Analytics config','qcharts.analyticsConfigManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-03-03 10:40:25',NULL,NULL),('7d0e66e5-b46c-4523-8956-d6e97a907416','BSC_PROG004D0002A','02 - Workspace settings (Create)','bsc.workspaceSettingsCreateAction.action','N','N','N',0,0,'BSC','ITEM','SYSTEM','admin','2014-12-29 11:30:22',NULL,NULL),('80c61b4d-4385-42b1-b139-4a4ad9435d41','BSC_PROG001D0002Q','02 - Organization','bsc.organizationManagementAction.action','N','N','N',0,0,'BSC','ITEM','INTER_ARCHIVE','admin','2014-11-06 08:54:15','admin','2014-11-06 09:10:10'),('828e7bca-4bc4-44b2-8327-de4550e7e22c','QCHARTS_PROG001D0001E','01 - Data source config (Edit)','qcharts.dataSourceConfEditAction.action','Y','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-01-12 13:37:24',NULL,NULL),('82908e56-8433-4dd0-add2-f232de32422b','CORE_PROG001D0012A','12 - Form Template (Create)','core.systemFormTemplateCreateAction.action','N','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2015-04-24 14:41:57',NULL,NULL),('8409a87b-d81a-4243-9d92-99df82aa355d','BSC_PROG001D0003Q','03 - Formula','bsc.formulaManagementAction.action','N','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2014-11-17 15:10:28',NULL,NULL),('84dcdbfb-f7fb-4f7b-822b-a9a2d09b0f5f','CORE_PROG001D0009E','09 - Web Context bean (Edit)','core.systemContextBeanEditAction.action','Y','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-11-02 16:56:35','admin','2014-11-02 16:58:24'),('870ff407-876d-4352-90c2-a970ca0615fc','QCHARTS_PROG002D','Basic','','N','N','N',0,0,'QCHARTS','FOLDER','STAR','admin','2015-01-12 20:04:01',NULL,NULL),('87d21f6b-34d2-4d4a-b134-2b41fabb8577','CORE_PROGCOMM0002Q','Upload file','core.commonUploadAction.action','N','Y','N',550,120,'CORE','ITEM','IMPORT','admin','2014-10-29 22:47:01',NULL,NULL),('8d3cafee-94b9-439a-ac89-ac0e04ba0482','CORE_PROGCOMM0004Q','Code editor','core.commonCodeEditorAction.action','Y','N','Y',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-03-12 15:44:31',NULL,NULL),('8f25dd05-72b5-48af-8a60-84c1cb7b7447','CORE_PROG004D0001Q','01 - Event log','core.commomLoadForm.action?form_id=FORM_CORE_4D_001_01&form_method=init','N','N','N',0,0,'CORE','ITEM','PROPERTIES','admin','2015-04-28 10:06:41',NULL,NULL),('8f72667b-d5ae-4009-9bbd-c7ca5a35c8d1','CORE_PROG001D0014E','14 - Form method (Edit)','core.systemFormMethodEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-26 19:49:07',NULL,NULL),('8f8a7e96-6163-4c0a-8a72-a22028661ff8','BSC_PROG003D0001Q','01 - KPI Report','bsc.kpiReportAction.action','N','N','N',0,0,'BSC','ITEM','APPLICATION_PDF','admin','2014-12-01 16:03:32','admin','2014-12-01 16:04:03'),('90fdac4f-264b-4271-a4b8-5163d0c7734e','BSC_PROG002D0005Q','05 - Measure data','bsc.measureDataManagementAction.action','N','N','N',0,0,'BSC','ITEM','CALENDAR','admin','2014-11-24 09:19:15','admin','2014-11-24 09:21:00'),('9190b070-8fa4-4c5e-9a07-3d89b91aee3b','BSC_PROG002D','Scorecard','','N','N','N',0,0,'BSC','FOLDER','STAR','admin','2014-11-13 11:36:58',NULL,NULL),('9220f559-1572-4a52-8692-a6272a4b050e','CORE_PROG001D0005A','05 - Message config (Create)','core.systemMessageNoticeConfigCreateAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-19 22:49:59','admin','2014-10-20 15:42:30'),('92bb4944-b459-47fa-9dc3-750b5d72d931','CORE_PROG002D','Role authority management',' ','N','N','N',0,0,'CORE','FOLDER','PEOPLE','admin','2014-10-02 00:00:00',NULL,NULL),('9331da1e-7edd-436f-a0da-49c540e31678','BSC_PROG002D0008Q','08 - SWOT','bsc.swotManagementAction.action','N','N','N',0,0,'BSC','ITEM','EXCEL','admin','2014-12-19 11:24:00',NULL,NULL),('943bbbe1-f9e4-4c60-9296-46c6335c78f4','CORE_PROG003D0003E_S01','03 - Script support settings (Edit expression mapper)','core.systemBeanHelpEditExpressionMapperAction.action','Y','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-28 15:55:24',NULL,NULL),('94578851-a20c-4421-95f5-6e4020fc1af1','QCHARTS_PROG002D0001Q','01 - Basic query','qcharts.queryManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','CHART_BAR','admin','2015-01-12 20:06:21','admin','2015-01-16 14:13:42'),('947d5caa-1f9b-402f-bc76-802be35d69d2','CORE_PROG001D0014A','14 - Form method (Create)','core.systemFormMethodCreateAction.action','Y','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-26 16:58:48',NULL,NULL),('94ad2c63-89b5-46ac-95d3-aa183cd0cade','BSC_PROG001D0005Q','05 - Workspace','bsc.loadWorkspaceAction.action','N','N','N',0,0,'BSC','ITEM','VIEW_LIST','admin','2015-01-03 12:07:51',NULL,NULL),('95f9a7c8-72c5-4457-8728-ef49f6ef70df','CORE_PROG001D0014Q','14 - Form method','core.systemFormMethodManagementAction.action','Y','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-26 16:31:28',NULL,NULL),('9a4003c6-cebe-4f3b-8d77-f18d6a50e80c','CORE_PROG002D0003Q','03 - Role for program (menu)','core.systemProgramMenuRoleManagementAction.action','N','N','N',0,0,'CORE','ITEM','FOLDER','admin','2014-10-13 22:09:19','admin','2014-10-14 12:15:29'),('9eb67bb0-ad8c-47af-a7f4-98f08835aa0c','CORE_PROG001D0006E','06 - Message notice (Edit)','core.systemMessageNoticeEditAction.action','Y','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2014-10-21 11:34:53',NULL,NULL),('9f301759-781d-4f46-9b7f-547b0665c283','CORE_PROG001D0004A','04 - My calendar note (Create)','core.systemCalendarNoteCreateAction.action','N','N','N',0,0,'CORE','ITEM','CALENDAR','admin','2014-10-15 22:14:58','admin','2014-10-16 08:45:36'),('a8309d51-5920-4d6b-af95-3e15d6805b5b','QCHARTS_PROG001D0002Q','02 - Data query mapper','qcharts.dataQueryMapperManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','PROPERTIES','admin','2015-01-14 13:40:29','admin','2015-01-14 14:43:30'),('a94f96b0-ed4b-4111-97b0-2829141c45df','CORE_PROG001D0005E','05 - Message config (Edit)','core.systemMessageNoticeConfigEditAction.action','Y','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-20 00:05:28','admin','2014-10-20 15:42:41'),('a9d1dd82-e65f-4d07-9561-b489e729455d','CORE_PROG001D0002A','02 - Program registration (Create)','core.systemProgramCreateAction.action','N','N','N',0,0,'CORE','ITEM','G_APP_INSTALL','admin','2014-10-07 11:09:08',NULL,NULL),('abade404-fc33-4ae6-891a-094c3dae024a','BSC_PROG004D0002Q','02 - Workspace settings','bsc.workspaceSettingsManagementAction.action','N','N','N',0,0,'BSC','ITEM','SYSTEM','admin','2014-12-28 08:56:31',NULL,NULL),('ac5bcfd0-4abd-11e4-916c-0800200c9a66','CORE_PROG001D0001A','01 - Application site (Create)','core.applicationSystemCreateAction.action','N','N','N',0,0,'CORE','ITEM','COMPUTER','admin','2014-10-03 13:26:36','admin','2014-10-08 15:57:31'),('ad16c4bb-d742-4ffd-83b2-95931b4eff4b','CORE_PROG003D0003A','03 - Script support settings (Create)','core.systemBeanHelpCreateAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-27 10:43:11',NULL,NULL),('b37213ca-3ff4-47e5-9183-4e930485811d','BSC_PROG002D0003Q','03 - Strategy objective','bsc.objectiveManagementAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-16 13:11:01','admin','2014-11-24 20:58:41'),('b4006581-eff2-4f42-9316-028a084fa82e','QCHARTS_PROG001D0004E','04 - Analytics Catalog (Edit)','qcharts.analyticsCatalogEditAction.action','Y','N','N',0,0,'QCHARTS','ITEM','TEXT_SOURCE','admin','2015-03-04 22:05:09','admin','2015-03-04 22:05:31'),('b4220f50-70c4-4c79-82e4-5934a2692b5f','BSC_PROG004D0003Q','03 - Report limit role','bsc.reportRoleViewManagementAction.action','N','N','N',0,0,'BSC','ITEM','SHARED','admin','2015-03-23 20:43:55','admin','2015-03-23 21:21:24'),('b453b796-9ef2-40b1-899c-ac1a8edf2cd1','CORE_PROG002D0001A','01 - Role (Create)','core.roleCreateAction.action','N','N','N',0,0,'CORE','ITEM','SHARED','admin','2014-10-13 08:42:30','admin','2014-10-13 08:45:09'),('b6b89559-6864-46ab-9ca9-0992dcf238f1','CORE_PROG001D0001Q','01 - Application site','core.applicationSystemManagementAction.action','N','N','N',0,0,'CORE','ITEM','COMPUTER','admin','2014-10-02 00:00:00',NULL,NULL),('b79dfcab-7163-4b21-b763-dfdef1bb9ffa','BSC_PROG001D0001Q_S00','Employee','bsc.employeeSelectAction.action','Y','Y','N',750,600,'BSC','ITEM','PERSON','admin','2014-11-22 12:25:25','admin','2014-11-22 12:33:17'),('b7a73743-112f-4612-afb1-4c83fe447e18','CORE_PROG001D0010A','10 - Twitter pane (Create)','core.systemTwitterCreateAction.action','N','N','N',0,0,'CORE','ITEM','TWITTER','admin','2014-12-18 21:11:15','admin','2014-12-18 22:08:17'),('bc077f57-b217-4d78-8989-78db5f3070a5','CORE_PROG001D0004Q','04 - My calendar note','core.systemCalendarNoteManagementAction.action','N','N','N',0,0,'CORE','ITEM','CALENDAR','admin','2014-10-15 11:29:44','admin','2014-10-16 08:45:46'),('bf965d90-06d4-4683-a8d8-ef6c934c9a27','CORE_PROG002D0001E','01 - Role (Edit)','core.roleEditAction.action','Y','N','N',0,0,'CORE','ITEM','SHARED','admin','2014-10-13 08:42:51','admin','2014-10-13 08:45:21'),('c074a3ee-13bf-4a1b-908c-18e435e0f557','CORE_PROG001D0008Q_S00','Report preview settings','core.systemJreportPreviewParamAction.action','Y','Y','N',650,410,'CORE','ITEM','APPLICATION_PDF','admin','2015-04-20 11:38:00','admin','2015-04-21 09:07:14'),('c0a8696d-cd34-4d4c-9be3-24d20711a3a4','CORE_PROG004D','Log','','N','N','N',0,0,'CORE','FOLDER','VIEW_LIST','admin','2015-04-28 09:56:32',NULL,NULL),('c1b2abc6-7d7c-4337-ad17-e206e65fb5dc','BSC_PROG001D0008A','08 - Aggregation Method (Create)','bsc.aggregationMethodCreateAction.action','N','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2015-03-12 09:49:10',NULL,NULL),('c20ad36f-4fb7-4b98-8fde-594c4a9de7c4','BSC_PROG001D0006Q','06 - Region view','bsc.regionMapViewAction.action','N','N','N',0,0,'BSC','ITEM','STOCK_HOME','admin','2015-01-20 20:08:48','admin','2015-01-22 19:06:47'),('c33f14f6-06d7-4b37-8d11-97450f33971c','CORE_PROG001D0002Q','02 - Program registration','core.systemProgramManagementAction.action','N','N','N',0,0,'CORE','ITEM','G_APP_INSTALL','admin','2014-10-06 15:17:20',NULL,NULL),('c9c8972b-2203-4cf0-89fe-a6b00bb803ac','BSC_PROG004D0001Q','01 - Report property','bsc.reportPropertyManagementAction.action','N','N','N',0,0,'BSC','ITEM','PROPERTIES','admin','2014-12-07 10:08:03','admin','2014-12-07 10:09:03'),('ca013410-0164-4b41-a9d0-d3147111c408','CORE_PROG003D0003E_S00','03 - Script support settings (Edit expression)','core.systemBeanHelpEditExpressionAction.action','Y','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-28 09:24:06','admin','2014-10-28 09:28:35'),('cbc92f50-0a61-497c-bccb-69cab4875402','BSC_PROG003D','Report','','N','N','N',0,0,'BSC','FOLDER','TEMPLATE','admin','2014-12-01 16:01:42','admin','2014-12-02 14:29:18'),('cdb68c68-1c5a-4833-8601-ce42ce2a6da9','QCHARTS_PROG002D0002Q','02 - Analytics','qcharts.analyticsManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','VIEW_LIST','admin','2015-03-05 10:02:59',NULL,NULL),('ce0c120b-6ca2-455e-a6d1-650a5fcfc3c4','BSC_PROG001D0008Q','08 - Aggregation Method','bsc.aggregationMethodManagementAction.action','N','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2015-03-11 20:35:16',NULL,NULL),('ce49a49c-edcf-45f2-b237-95e6e6e1ef33','CORE_PROG001D0008E','08 - Report management (Edit)','core.systemJreportEditAction.action','Y','N','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2014-10-31 14:39:31',NULL,NULL),('cf2de76b-4a3d-4d19-a638-d28c705748b9','CORE_PROG003D0001A','01 - WebService registration (Create)','core.systemWsConfigCreateAction.action','N','N','N',0,0,'CORE','ITEM','WWW','admin','2014-10-22 14:38:19','admin','2014-10-22 14:40:27'),('d454755c-1a37-4d21-8ff9-284a42482d11','BSC_PROG002D0004E','04 - KPI (Edit)','bsc.kpiEditAction.action','Y','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-23 17:16:00',NULL,NULL),('d5dfeb66-9c47-4cc7-9fd8-8395dba1333e','CORE_PROG001D0004E','04 - My calendar note (Edit)','core.systemCalendarNoteEditAction.action','Y','N','N',0,0,'CORE','ITEM','CALENDAR','admin','2014-10-16 11:54:10',NULL,NULL),('d6339624-da34-4854-958f-a4c3c871863a','BSC_PROG003D0003Q','03 - Department Report','bsc.departmentReportAction.action','N','N','N',0,0,'BSC','ITEM','APPLICATION_PDF','admin','2014-12-14 09:55:08',NULL,NULL),('d6592509-197c-4ae6-8a33-72c9b70e39ab','CORE_PROG001D0012E','12 - Form Template (Edit)','core.systemFormTemplateEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2015-04-24 20:23:55',NULL,NULL),('d7054c4d-75b3-4531-b339-2f029075eaf9','BSC_PROG002D0002A','02 - Perspective (Create)','bsc.perspectiveCreateAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-15 09:54:52','admin','2014-11-16 09:33:47'),('d80cb826-3598-4cf8-bdfe-acf645791d04','CORE_PROGCOMM0003Q','Signature','core.commonSignatureAction.action','N','Y','N',480,320,'CORE','ITEM','SIGNATURE','admin','2015-02-13 20:06:06','admin','2015-02-16 19:19:25'),('d999d561-0521-46c8-b5d4-e63b4b6d486c','BSC_PROG001D0002Q_S00','Department','bsc.organizationSelectAction.action','Y','Y','N',640,550,'BSC','ITEM','INTER_ARCHIVE','admin','2014-11-12 09:14:31','admin','2014-11-12 11:07:53'),('df3f38e0-cf25-4712-aae8-78d748219d63','CORE_PROG003D','BO Service config','','N','N','N',0,0,'CORE','FOLDER','DIAGRAM','admin','2014-10-22 11:51:48','admin','2014-10-22 12:16:31'),('df8a9ea3-7ca0-4bf2-82fb-bf6c7f3eda7a','BSC_PROG002D0004A','04 - KPI (Create)','bsc.kpiCreateAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-22 10:25:04',NULL,NULL),('e2426bc7-e9f3-40b6-9b24-7cdc26121a97','BSC_PROG002D0003A','03 - Strategy objective (Create)','bsc.objectiveCreateAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-17 09:28:00','admin','2014-11-24 20:58:19'),('e252148d-eceb-42b6-b55f-1637b9f80ec4','BSC_PROG003D0005Q','05 - Objectives Dashboard','bsc.objectivesDashboardAction.action','N','N','N',0,0,'BSC','ITEM','CHART_PIE','admin','2015-04-01 19:37:10',NULL,NULL),('e4e0e29d-b638-461e-8779-48d0e031646f','BSC_PROG001D0001Q','01 - Employee','bsc.employeeManagementAction.action','N','N','N',0,0,'BSC','ITEM','PERSON','admin','2014-11-03 15:25:03','admin','2014-11-03 21:48:28'),('e6a6a097-5dc7-41b8-999c-96f5121acc1f','BSC_PROG002D0007Q','07 - Strategy Map','bsc.strategyMapManagementAction.action','N','N','Y',0,0,'BSC','ITEM','DIAGRAM','admin','2014-12-15 15:27:43','admin','2014-12-15 15:28:28'),('eb05899b-4bdb-448a-b24d-2431efa8a990','CORE_PROG002D0001Q','01 - Role','core.roleManagementAction.action','N','N','N',0,0,'CORE','ITEM','SHARED','admin','2014-10-02 00:00:00','admin','2014-10-13 08:45:30'),('eb59b40c-010d-4318-968a-918f5c179cd4','BSC_PROG001D0001E_S00','01 - Employee (password)','bsc.employeePasswordEditAction.action','Y','Y','N',800,260,'BSC','ITEM','PERSON','admin','2014-11-12 23:08:49','admin','2014-11-12 23:14:17'),('ecb5be95-15be-4f9a-aa3a-a3a1c7315a2a','CORE_PROG002D0001E_S00','Role\'s permitted settings','core.rolePermittedEditAction.action','Y','N','N',0,0,'CORE','ITEM','SHARED','admin','2014-10-13 09:17:42','admin','2014-10-22 08:47:52'),('ed286ac7-9fb1-4053-814c-ce4f1b9a85ac','CORE_PROG003D0001Q','01 - WebService registration','core.systemWsConfigManagementAction.action','N','N','N',0,0,'CORE','ITEM','WWW','admin','2014-10-22 11:53:21','admin','2014-10-22 14:40:42'),('ed838fc5-f83d-4386-95dd-03846f439f58','BSC_PROG002D0004Q','04 - KPI','bsc.kpiManagementAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-20 15:44:50','admin','2014-11-20 15:45:16'),('efaf9edd-19cd-4fc7-81d5-9784378997b5','CORE_PROG001D0002E','02 - Program registration (Edit)','core.systemProgramEditAction.action','Y','N','N',0,0,'CORE','ITEM','G_APP_INSTALL','admin','2014-10-08 08:50:29',NULL,NULL),('f0c4f5ac-4da3-4218-a14d-7ee6f3ebdce9','CORE_PROG001D0015E','15 - Basic message (Edit)','core.commomLoadForm.action?form_id=FORM002&form_method=edit','Y','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2015-04-27 20:28:03','admin','2015-04-27 20:50:35'),('f336c30b-cc22-439a-b951-950584a8b7e9','BSC_PROG002D0001Q','01 - Vision','bsc.visionManagementAction.action','N','N','N',0,0,'BSC','ITEM','GWENVIEW','admin','2014-11-13 11:38:02','admin','2014-11-13 11:40:18'),('f4c757b5-f70c-420a-bda9-a6831ce5f50c','BSC_PROG003D0004Q','04 - Perspectives Dashboard','bsc.perspectivesDashboardAction.action','N','N','N',0,0,'BSC','ITEM','CHART_PIE','admin','2015-03-31 10:40:54',NULL,NULL),('fa12b194-7a86-465a-a448-1a577eb05f46','BSC_PROG003D0006Q','06 - KPIs Dashboard','bsc.kpisDashboardAction.action','N','N','N',0,0,'BSC','ITEM','CHART_PIE','admin','2015-04-04 15:55:51',NULL,NULL),('fe08215e-bb9f-4b36-b90c-bead9afaafa7','CORE_PROG002D0001A_S00','01 - Role (Copy as new)','core.roleCopyAsNewAction.action','Y','Y','N',750,500,'CORE','ITEM','SHARED','admin','2015-03-22 12:07:49','admin','2015-03-22 12:28:03');
/*!40000 ALTER TABLE `tb_sys_prog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_template`
--

DROP TABLE IF EXISTS `tb_sys_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_template` (
  `OID` char(36) NOT NULL,
  `TEMPLATE_ID` varchar(10) NOT NULL,
  `TITLE` varchar(200) NOT NULL,
  `MESSAGE` varchar(4000) NOT NULL,
  `DESCRIPTION` varchar(200) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TEMPLATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_template`
--

LOCK TABLES `tb_sys_template` WRITE;
/*!40000 ALTER TABLE `tb_sys_template` DISABLE KEYS */;
INSERT INTO `tb_sys_template` VALUES ('25dba92d-07a5-40ea-946b-a4f149a4b13c','TPLMSG0001','${title}','<table bgcolor=\"#F2F2F2\" border=\"0\" width=\"100%\">\n<tbody><tr>\n<td align=\"center\" bgcolor=\"#E6E6E6\"><font size=\"2\"><b>${date}</b></font>  <font size=\"2\"><b>${time}</b></font></td>\n</tr>\n<tr>\n<td align=\"left\" bgcolor=\"#E6E6E6\"><font size=\"2\"><b>${title}</b></font></td>\n</tr>\n<tr>\n<td align=\"left\"><font size=\"2\">${message}</font></td>\n</tr>\n</tbody></table>\n<hr color=\"#e1eefc\" size=\"2\" width=\"100%\" />','The template for show user\'s calendar note history!','admin','2014-10-17 00:00:00','admin','2014-10-21 17:15:15'),('65686236-3d79-4ce7-9b49-e616f07ae7ba','TPLMSG0002','${title}','<table bgcolor=\"#F2F2F2\" border=\"0\" width=\"100%\">\n<tbody><tr>\n<td align=\"left\" bgcolor=\"#E6E6E6\"><font size=\"2\"><b>${title}</b></font></td>\n</tr>\n<tr>\n<td align=\"left\"><font size=\"2\">${message}</font></td>\n</tr>\n</tbody></table>\n<hr color=\"#e1eefc\" size=\"2\" width=\"100%\" />','The template for notice message!','admin','2014-10-18 19:07:50','admin','2014-10-21 17:15:31');
/*!40000 ALTER TABLE `tb_sys_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_template_param`
--

DROP TABLE IF EXISTS `tb_sys_template_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_template_param` (
  `OID` char(36) NOT NULL,
  `TEMPLATE_ID` varchar(10) NOT NULL,
  `IS_TITLE` varchar(1) NOT NULL DEFAULT 'N',
  `TEMPLATE_VAR` varchar(100) NOT NULL,
  `OBJECT_VAR` varchar(100) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TEMPLATE_ID`,`TEMPLATE_VAR`,`IS_TITLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_template_param`
--

LOCK TABLES `tb_sys_template_param` WRITE;
/*!40000 ALTER TABLE `tb_sys_template_param` DISABLE KEYS */;
INSERT INTO `tb_sys_template_param` VALUES ('08f51ee5-016f-4727-84df-49a4554222de','TPLMSG0001','Y','title','title','admin','2014-10-17 11:04:06',NULL,NULL),('0e9f9c4a-37f3-4dfd-af99-78008946f55f','TPLMSG0001','N','title','title','admin','2014-10-17 00:00:00',NULL,NULL),('0f606201-6630-4574-af85-7fa2ee70e855','TPLMSG0002','N','message','message','admin','2014-10-18 19:07:50',NULL,NULL),('afee51ed-8463-4ee3-bf99-8c15cf1b9f8a','TPLMSG0001','N','date','date','admin','2014-10-17 11:04:31',NULL,NULL),('c7875967-40fe-4b04-8638-f8aad4219e34','TPLMSG0002','N','title','title','admin','2014-10-18 19:07:50',NULL,NULL),('ce46d507-e615-4980-8c0f-b331812200a5','TPLMSG0002','Y','title','title','admin','2014-10-18 19:07:50',NULL,NULL),('d296664b-026e-47fb-b6bf-fab5426224e3','TPLMSG0001','N','time','time','admin','2014-10-17 11:05:03',NULL,NULL),('e9ef43c5-bf93-4cf8-9e6b-ca70a12182e0','TPLMSG0001','N','message','note','admin','2014-10-17 00:00:00',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_template_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_twitter`
--

DROP TABLE IF EXISTS `tb_sys_twitter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_twitter` (
  `OID` char(36) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `TITLE` varchar(30) NOT NULL,
  `ENABLE_FLAG` varchar(1) NOT NULL,
  `CONTENT` blob,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`SYSTEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_twitter`
--

LOCK TABLES `tb_sys_twitter` WRITE;
/*!40000 ALTER TABLE `tb_sys_twitter` DISABLE KEYS */;
INSERT INTO `tb_sys_twitter` VALUES ('762b40c9-c247-4345-a450-1736a8683729','CORE','FAQ twitter','Y',0x3C6120636C6173733D22747769747465722D74696D656C696E65222020687265663D2268747470733A2F2F747769747465722E636F6D2F62696C6C6368656E3139383331382220646174612D7769646765742D69643D22353435333939313830313831353136323838223E4062696C6C6368656E3139383331382073656E64206D6573736167653C2F613E0A3C7363726970743E2166756E6374696F6E28642C732C6964297B766172206A732C666A733D642E676574456C656D656E747342795461674E616D652873295B305D2C703D2F5E687474703A2F2E7465737428642E6C6F636174696F6E293F2768747470273A276874747073273B69662821642E676574456C656D656E744279496428696429297B6A733D642E637265617465456C656D656E742873293B6A732E69643D69643B6A732E7372633D702B223A2F2F706C6174666F726D2E747769747465722E636F6D2F776964676574732E6A73223B666A732E706172656E744E6F64652E696E736572744265666F7265286A732C666A73293B7D7D28646F63756D656E742C22736372697074222C22747769747465722D776A7322293B3C2F7363726970743E,'admin','2014-12-18 00:00:01','admin','2014-12-18 22:34:02');
/*!40000 ALTER TABLE `tb_sys_twitter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_upload`
--

DROP TABLE IF EXISTS `tb_sys_upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_upload` (
  `OID` char(36) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `SUB_DIR` varchar(4) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `FILE_NAME` varchar(50) NOT NULL,
  `SHOW_NAME` varchar(255) NOT NULL,
  `IS_FILE` varchar(1) NOT NULL DEFAULT 'Y',
  `CONTENT` mediumblob,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  KEY `IDX_1` (`SYSTEM`,`TYPE`,`SUB_DIR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_upload`
--

LOCK TABLES `tb_sys_upload` WRITE;
/*!40000 ALTER TABLE `tb_sys_upload` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_upload` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_upload_tran`
--

DROP TABLE IF EXISTS `tb_sys_upload_tran`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_upload_tran` (
  `OID` char(36) NOT NULL,
  `TRAN_ID` varchar(10) NOT NULL,
  `SEGM_MODE` varchar(10) NOT NULL DEFAULT 'BYTE',
  `SEGM_SYMBOL` varchar(1) NOT NULL DEFAULT ',',
  `ENCODING` varchar(10) NOT NULL,
  `EXPR_TYPE` varchar(10) NOT NULL,
  `HELP_EXPRESSION` varchar(8000) NOT NULL,
  `BEGIN_LEN` int(1) NOT NULL DEFAULT '0',
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TRAN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_upload_tran`
--

LOCK TABLES `tb_sys_upload_tran` WRITE;
/*!40000 ALTER TABLE `tb_sys_upload_tran` DISABLE KEYS */;
INSERT INTO `tb_sys_upload_tran` VALUES ('107588e6-32e5-4afe-bd31-3f7800bfde7c','TRAN004','SYMBOL',';','utf-8','GROOVY','import java.util.HashMap;\r\nimport java.util.LinkedList;\r\nimport java.util.List;\r\nimport java.util.Map;\r\nimport com.netsteadfast.greenstep.util.SimpleUtils;\r\n\r\nif (datas == null) {\r\n	return;\r\n}\r\nfor (int i=0; i<datas.size(); i++) {\r\n	Map<String, String> dataMap = datas.get(i);\r\n	for (Map.Entry<String, String> entry : dataMap.entrySet()) {\r\n		if ( entry.getValue() != null ) {\r\n			dataMap.put( entry.getKey(), SimpleUtils.unEscapeCsv2(entry.getValue()) );\r\n		}\r\n	}\r\n}\r\n',1,'for import csv file to bb_kpi.','admin','2015-05-26 10:19:26',NULL,NULL),('145a5d6a-7281-4bc6-859f-d1e5ecc510c0','TRAN003','SYMBOL',';','utf-8','GROOVY','import java.util.HashMap;\r\nimport java.util.LinkedList;\r\nimport java.util.List;\r\nimport java.util.Map;\r\nimport com.netsteadfast.greenstep.util.SimpleUtils;\r\n\r\nif (datas == null) {\r\n	return;\r\n}\r\nfor (int i=0; i<datas.size(); i++) {\r\n	Map<String, String> dataMap = datas.get(i);\r\n	for (Map.Entry<String, String> entry : dataMap.entrySet()) {\r\n		if ( entry.getValue() != null ) {\r\n			dataMap.put( entry.getKey(), SimpleUtils.unEscapeCsv2(entry.getValue()) );\r\n		}\r\n	}\r\n}\r\n',1,'for import csv file to bb_objective.','admin','2015-05-26 08:53:59',NULL,NULL),('3e525db9-1645-4459-84d8-7fe16f799501','TRAN001','SYMBOL',';','utf-8','GROOVY','import java.util.HashMap;\r\nimport java.util.LinkedList;\r\nimport java.util.List;\r\nimport java.util.Map;\r\nimport com.netsteadfast.greenstep.util.SimpleUtils;\r\n\r\nif (datas == null) {\r\n	return;\r\n}\r\nfor (int i=0; i<datas.size(); i++) {\r\n	Map<String, String> dataMap = datas.get(i);\r\n	for (Map.Entry<String, String> entry : dataMap.entrySet()) {\r\n		if ( entry.getValue() != null ) {\r\n			dataMap.put( entry.getKey(), SimpleUtils.unEscapeCsv2(entry.getValue()) );\r\n		}\r\n	}\r\n}\r\n',1,'for import csv file to bb_vision.','admin','2015-05-25 13:25:07',NULL,NULL),('3e525db9-1645-4459-84d8-7fe16f799502','TRAN002','SYMBOL',';','utf-8','GROOVY','import java.util.HashMap;\r\nimport java.util.LinkedList;\r\nimport java.util.List;\r\nimport java.util.Map;\r\nimport com.netsteadfast.greenstep.util.SimpleUtils;\r\n\r\nif (datas == null) {\r\n	return;\r\n}\r\nfor (int i=0; i<datas.size(); i++) {\r\n	Map<String, String> dataMap = datas.get(i);\r\n	for (Map.Entry<String, String> entry : dataMap.entrySet()) {\r\n		if ( entry.getValue() != null ) {\r\n			dataMap.put( entry.getKey(), SimpleUtils.unEscapeCsv2(entry.getValue()) );\r\n		}\r\n	}\r\n}\r\n',1,'for import csv file to bb_perspective.','admin','2015-05-25 15:04:15',NULL,NULL),('a7b0b61e-2b33-4659-8740-d3ed21d4410e','TRAN005','SYMBOL',';','utf-8','GROOVY','import java.util.HashMap;\r\nimport java.util.LinkedList;\r\nimport java.util.List;\r\nimport java.util.Map;\r\nimport com.netsteadfast.greenstep.util.SimpleUtils;\r\n\r\nif (datas == null) {\r\n	return;\r\n}\r\nfor (int i=0; i<datas.size(); i++) {\r\n	Map<String, String> dataMap = datas.get(i);\r\n	for (Map.Entry<String, String> entry : dataMap.entrySet()) {\r\n		if ( entry.getValue() != null ) {\r\n			dataMap.put( entry.getKey(), SimpleUtils.unEscapeCsv2(entry.getValue()) );\r\n		}\r\n	}\r\n}\r\n',1,'for import csv file to bb_measure_data.','admin','2015-05-26 11:56:38',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_upload_tran` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_upload_tran_segm`
--

DROP TABLE IF EXISTS `tb_sys_upload_tran_segm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_upload_tran_segm` (
  `OID` char(36) NOT NULL,
  `TRAN_ID` varchar(10) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `BEGIN` int(4) NOT NULL,
  `END` int(4) NOT NULL,
  `CUSERID` varchar(50) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`TRAN_ID`,`NAME`),
  KEY `IDX_1` (`TRAN_ID`,`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_upload_tran_segm`
--

LOCK TABLES `tb_sys_upload_tran_segm` WRITE;
/*!40000 ALTER TABLE `tb_sys_upload_tran_segm` DISABLE KEYS */;
INSERT INTO `tb_sys_upload_tran_segm` VALUES ('0ec39f8f-eb83-44c7-92d4-a7d5086d16e6','TRAN004','OBJ_ID',1,1,'admin','2015-05-26 10:29:13',NULL,NULL),('0f62a211-ad92-4ea9-97d4-cfae69261a55','TRAN004','TARGET',4,4,'admin','2015-05-26 10:30:54',NULL,NULL),('107a63ab-ef5a-4520-86a3-c9dfcba5f938','TRAN004','MANAGEMENT',8,8,'admin','2015-05-26 10:33:31',NULL,NULL),('1eb75881-8a4d-41be-84da-48b5f6af0cc7','TRAN003','NAME',2,2,'admin','2015-05-26 08:56:16',NULL,NULL),('1f13fe26-bac5-4018-a5f8-f3d10479c389','TRAN001','VIS_ID',0,0,'admin','2015-05-25 13:26:52',NULL,NULL),('2070c0ab-5d2d-4224-9e5f-14b0af5c26c4','TRAN003','TARGET',4,4,'admin','2015-05-26 08:57:31',NULL,NULL),('22f47521-ce31-4abf-8b59-9bba62c6b70f','TRAN004','COMPARE_TYPE',9,9,'admin','2015-05-26 10:34:03',NULL,NULL),('23097189-9295-4cc5-b2b8-96c7ff40e4b0','TRAN005','FREQUENCY',4,4,'admin','2015-05-26 11:59:15',NULL,NULL),('23547ccb-54df-4500-b043-e5043cac2232','TRAN004','DESCRIPTION',15,15,'admin','2015-05-26 10:37:56',NULL,NULL),('241d818e-030c-4b68-b2db-cefcdb08d3c0','TRAN002','WEIGHT',3,3,'admin','2015-05-25 15:06:50',NULL,NULL),('2f502cb8-0952-4bb9-b13c-af7442c2b0e4','TRAN004','USER_MEASURE_SEPARATE',13,13,'admin','2015-05-26 10:36:44',NULL,NULL),('40ae97d2-5feb-4d65-ae26-af2adea2e728','TRAN004','MIN',5,5,'admin','2015-05-26 10:31:34',NULL,NULL),('42893686-1809-44e3-a369-0003dfdd75bb','TRAN003','WEIGHT',3,3,'admin','2015-05-26 08:56:56',NULL,NULL),('5245baf9-72f8-419f-8864-492b80ad4081','TRAN003','PER_ID',1,1,'admin','2015-05-26 08:55:39',NULL,NULL),('568759ae-bf25-4c9d-ad31-44c0fba7da27','TRAN003','MIN',5,5,'admin','2015-05-26 08:58:03',NULL,NULL),('573f3a7e-be78-4adc-b895-031d2e70adb9','TRAN002','VIS_ID',1,1,'admin','2015-05-25 15:05:54',NULL,NULL),('59df7630-ff32-4146-87f1-00db9ec6978f','TRAN005','KPI_ID',0,0,'admin','2015-05-26 11:57:24',NULL,NULL),('6046f795-afcb-485b-904e-3f80c7d13484','TRAN005','DATE',1,1,'admin','2015-05-26 11:57:50',NULL,NULL),('6483cc80-0b7d-460c-9708-c791d478b422','TRAN004','QUASI_RANGE',14,14,'admin','2015-05-26 10:37:16',NULL,NULL),('6510741d-6876-4ce6-b41f-9b64f8c61c0b','TRAN005','ACTUAL',3,3,'admin','2015-05-26 11:58:46',NULL,NULL),('6a047983-02e7-400b-a54e-fe2b7e812458','TRAN003','DESCRIPTION',6,6,'admin','2015-05-26 08:58:42',NULL,NULL),('6d0eea62-f8d4-4941-97e4-3504231f3508','TRAN005','EMP_ID',6,6,'admin','2015-05-26 12:00:05',NULL,NULL),('742d699f-3ddc-44af-b00d-f5826c7ca38f','TRAN004','NAME',2,2,'admin','2015-05-26 10:29:45',NULL,NULL),('77c32725-91c0-4407-a9b9-25b36d64743d','TRAN004','CAL',10,10,'admin','2015-05-26 10:34:35',NULL,NULL),('8b638d00-74b0-4bf8-92f1-7c795ac24de0','TRAN004','ORGA_MEASURE_SEPARATE',12,12,'admin','2015-05-26 10:36:09',NULL,NULL),('94e35504-24bd-4a9e-851b-789a396e02d2','TRAN005','TARGET',2,2,'admin','2015-05-26 11:58:14',NULL,NULL),('9b64b137-1fe9-442d-b9d5-1fb19a98e8a5','TRAN004','MAX',16,16,'admin','2015-08-03 14:11:06',NULL,NULL),('9fbab5cc-a24d-4db1-babd-206bf6fe09bb','TRAN004','FOR_ID',7,7,'admin','2015-05-26 10:32:56',NULL,NULL),('a62a7541-cb57-43b0-a257-22525912a868','TRAN003','OBJ_ID',0,0,'admin','2015-05-26 08:55:04',NULL,NULL),('a7b0a3e8-dffd-4657-9270-d9f2c8781091','TRAN002','PER_ID',0,0,'admin','2015-05-25 15:05:24',NULL,NULL),('b0e1cd0d-f9a0-4344-a127-71ab7622cf6b','TRAN004','WEIGHT',3,3,'admin','2015-05-26 10:30:14',NULL,NULL),('b1f1d1c5-3c15-4612-a2e9-c4c230c5c6bd','TRAN004','ID',0,0,'admin','2015-05-26 10:28:45',NULL,NULL),('bc38b23f-b5e2-410e-9dcc-a5cc1e17c4ed','TRAN002','DESCRIPTION',6,6,'admin','2015-05-25 15:14:55',NULL,NULL),('cd88c4ce-b849-4d1e-bcaf-556e75f918a5','TRAN001','CONTENT',2,2,'admin','2015-05-25 13:27:47',NULL,NULL),('dd25a684-4775-4fdb-9d72-675d97180244','TRAN002','MIN',5,5,'admin','2015-05-25 15:14:26',NULL,NULL),('e41e29a6-f749-4628-a024-b988ad422a39','TRAN005','ORG_ID',5,5,'admin','2015-05-26 11:59:40',NULL,NULL),('e6240b19-2f1a-45c1-9f92-d2544ec73e9e','TRAN004','DATA_TYPE',11,11,'admin','2015-05-26 10:35:33',NULL,NULL),('ed1f0035-c9fa-413d-a198-b171098a499f','TRAN004','UNIT',6,6,'admin','2015-05-26 10:32:09',NULL,NULL),('f1ed8d54-8042-4ab9-9d6d-a0bac03273da','TRAN002','TARGET',4,4,'admin','2015-05-25 15:14:00',NULL,NULL),('f336c34f-b939-46de-99df-8755ef794a7e','TRAN001','TITLE',1,1,'admin','2015-05-25 13:27:21',NULL,NULL),('f625df4a-19b0-4a38-975a-ee33953e5721','TRAN002','NAME',2,2,'admin','2015-05-25 15:06:20',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_upload_tran_segm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_usess`
--

DROP TABLE IF EXISTS `tb_sys_usess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_usess` (
  `OID` char(36) NOT NULL,
  `SESSION_ID` varchar(64) NOT NULL,
  `ACCOUNT` varchar(24) NOT NULL,
  `CURRENT_ID` varchar(36) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`,`SESSION_ID`),
  UNIQUE KEY `UK_1` (`ACCOUNT`,`SESSION_ID`),
  KEY `IDX_1` (`CURRENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_usess`
--

LOCK TABLES `tb_sys_usess` WRITE;
/*!40000 ALTER TABLE `tb_sys_usess` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_sys_usess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_ws_config`
--

DROP TABLE IF EXISTS `tb_sys_ws_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_ws_config` (
  `OID` char(36) NOT NULL,
  `WS_ID` varchar(10) NOT NULL,
  `SYSTEM` varchar(10) NOT NULL,
  `TYPE` varchar(4) NOT NULL,
  `BEAN_ID` varchar(255) NOT NULL,
  `PUBLISH_ADDRESS` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`WS_ID`),
  KEY `IDX_1` (`SYSTEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_ws_config`
--

LOCK TABLES `tb_sys_ws_config` WRITE;
/*!40000 ALTER TABLE `tb_sys_ws_config` DISABLE KEYS */;
INSERT INTO `tb_sys_ws_config` VALUES ('17175451-80ed-4a9d-a490-6ae5857becb4','WS-CORE002','CORE','REST','core.webservice.HelloService','','example sample for REST!','admin','2014-10-22 19:55:03',NULL,NULL),('6d8ece45-b11a-45fb-9671-e2fbc84c3aa2','WS-CORE003','CORE','SOAP','core.webservice.SendMailService','/sendmail','send mail webService.','admin','2015-05-02 18:35:44','admin','2015-05-02 18:35:58'),('b4da1c48-4b6e-40cf-8232-aa23611b3cf7','WS-CORE001','CORE','SOAP','core.webservice.HelloService','/hello','example sample for SOAP!','admin','2014-10-22 19:54:31',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_ws_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_role`
--

DROP TABLE IF EXISTS `tb_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_role` (
  `OID` char(36) NOT NULL,
  `ROLE` varchar(50) NOT NULL,
  `ACCOUNT` varchar(24) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(50) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ROLE`,`ACCOUNT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_role`
--

LOCK TABLES `tb_user_role` WRITE;
/*!40000 ALTER TABLE `tb_user_role` DISABLE KEYS */;
INSERT INTO `tb_user_role` VALUES ('9243c7de-43b1-46ef-ac4b-2620697f319e','admin','admin','Administrator','admin','2014-09-23 00:00:00',NULL,NULL),('b3fab16d-9d9e-46dc-b28b-5668a17f3a6f','BSC_STANDARD','tester','tester `s role!','admin','2015-04-23 11:26:53',NULL,NULL);
/*!40000 ALTER TABLE `tb_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-08-06 10:10:00
