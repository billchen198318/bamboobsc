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
INSERT INTO `bb_kpi` VALUES ('0775e107-756b-4934-848c-6e4b1235af3b','KPI0003','OBJ20141127001','Investment projects in developing countries','',50.00,'%','F001',100,50,'1','1','AVG_001','3','Y','Y',0,'admin','2014-11-27 09:12:15','admin','2015-03-12 13:38:05'),('71b4417c-6c8c-4ef6-b8d4-fbb4d56863bb','KPI0006','OBJ20141127004','Education and Training','',100.00,'number of times','F002',70,30,'3','1','MAX_001','2','Y','Y',5,'admin','2014-11-27 09:43:20','admin','2015-03-12 13:35:47'),('9aa1c537-402f-4ccc-8d77-f472f98a3192','KPI0001','OBJ20141117001','Sales','aa bb',100.00,'%','F001',50,10,'1','1','AVG_001','1','Y','Y',0,'admin','2014-11-23 17:14:39','admin','2015-04-22 15:09:40'),('b4ebcdd0-1944-432e-8bb2-85df54f854d8','KPI0002','OBJ20141127001','Stock market investment fund','',50.00,'%','F001',100,50,'1','1','AVG_001','1','Y','Y',0,'admin','2014-11-27 09:09:32','admin','2014-12-14 15:18:15'),('b888607c-6d52-4163-82ac-a1ae03c9cba6','KPI0004','OBJ20141127002','Expanding Asia-Pacific market','',100.00,'%','F001',100,60,'1','1','AVG_001','1','Y','Y',0,'admin','2014-11-27 09:21:36','admin','2015-03-12 13:39:02'),('c12b2c99-4b28-4ce9-94bc-314161b9b7e7','KPI0005','OBJ20141127003','Loss stronghold transformation','',100.00,'Point','F002',100,50,'2','1','MIN_001','1','Y','Y',0,'admin','2014-11-27 09:30:48','admin','2015-03-12 13:35:30');
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
INSERT INTO `bb_kpi_empl` VALUES ('1b4d43c4-0863-4b60-9ac8-d01f4098e9dc','KPI0001','0001','admin','2015-04-22 15:09:40',NULL,NULL),('27c64855-c5ba-4065-8b38-5ff44c6bf481','KPI0003','0002','admin','2015-03-12 13:38:05',NULL,NULL),('2c3aae7f-0db6-4059-a755-bffd4f8a7eb9','KPI0006','0001','admin','2015-03-12 13:35:47',NULL,NULL),('49840f0a-eab6-46c2-abe4-f2cd6c2ed5f3','KPI0003','0001','admin','2015-03-12 13:38:05',NULL,NULL),('744bf146-d596-4f32-8825-c3fc2e26fb82','KPI0002','0002','admin','2014-12-14 15:18:15',NULL,NULL),('ad72aa48-d458-43aa-bb51-70cd642d6f8a','KPI0002','0001','admin','2014-12-14 15:18:15',NULL,NULL),('cf99a8af-b2d4-4d7f-a12c-a281634a18fd','KPI0006','0002','admin','2015-03-12 13:35:47',NULL,NULL),('e37a151e-700f-49ae-bc03-4f6017c470ca','KPI0001','0002','admin','2015-04-22 15:09:40',NULL,NULL);
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
INSERT INTO `bb_kpi_orga` VALUES ('035f940b-2da9-4cac-bd90-88b96aaf7375','KPI0004','0002','admin','2015-03-12 13:39:02',NULL,NULL),('08f1b43b-e023-412c-872d-51cfa66d3e5b','KPI0001','0002','admin','2015-04-22 15:09:40',NULL,NULL),('1c9a7eba-955c-467e-b74c-12ecb0c9f6d5','KPI0001','0001','admin','2015-04-22 15:09:40',NULL,NULL),('5bf8e230-ee06-43ab-a6d9-e42fb75ea814','KPI0005','0001','admin','2015-03-12 13:35:31',NULL,NULL),('be02aadf-2fd7-4cfb-a2f6-2ac09d2c953a','KPI0003','0001','admin','2015-03-12 13:38:05',NULL,NULL),('cd706b32-9cca-40f7-97c3-2f6cc1d3a6d2','KPI0005','0002','admin','2015-03-12 13:35:30',NULL,NULL),('db5935d2-85cd-4518-8feb-d97b55fefae5','KPI0002','0001','admin','2014-12-14 15:18:15',NULL,NULL);
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
INSERT INTO `bb_vision` VALUES ('1089abb5-3faf-445d-88ff-cd7690ac6743','VIS20141114001','Example vision',0x3C683120636C6173733D2268656164696E672D786C61726765207468656D652D6D61696E207468656D652D666F6E742D6D61696E206B6E6F636B6F75742D6267636F6C6F72223E3C7370616E20636C6173733D222220646174612D6F766572726964652D73697A653D222220646174612D7469746C653D227B2671756F743B6465736B746F702671756F743B3A2671756F743B266C743B7374726F6E6720636C6173733D5C2671756F743B7468656D652D7365636F6E64617279206B6E6F636B6F75742D6267636F6C6F725C2671756F743B2667743B4D697373696F6E2C266C743B2F7374726F6E672667743B20566973696F6E2026616D703B2056616C7565732671756F743B2C2671756F743B7461626C65742671756F743B3A2671756F743B266C743B7374726F6E6720636C6173733D5C2671756F743B7468656D652D7365636F6E64617279206B6E6F636B6F75742D6267636F6C6F725C2671756F743B2667743B4D697373696F6E2C266C743B2F7374726F6E672667743B20566973696F6E2026616D703B2056616C7565732671756F743B2C2671756F743B6D6F62696C652671756F743B3A2671756F743B266C743B7374726F6E6720636C6173733D5C2671756F743B7468656D652D7365636F6E64617279206B6E6F636B6F75742D6267636F6C6F725C2671756F743B2667743B4D697373696F6E2C266C743B2F7374726F6E672667743B20566973696F6E2026616D703B2056616C7565732671756F743B7D22206974656D70726F703D226E616D65223E3C6220636C6173733D227468656D652D7365636F6E64617279206B6E6F636B6F75742D6267636F6C6F72223E4D697373696F6E2C3C2F623E20566973696F6E2026616D703B2056616C7565733C2F7370616E3E2028206E6F74206F7665722041342070617065722077696474682120293C6272202F3E3C2F68313E636F70792066726F6D3AC2A020687474703A2F2F7777772E636F63612D636F6C61636F6D70616E792E636F6D2F6F75722D636F6D70616E792F6D697373696F6E2D766973696F6E2D76616C7565733C6272202F3E3C64697620636C6173733D226C6561642D6D656469612D636F6E7461696E6572223E0A202020202020202020202020202020202020202020202020202020203C64697620636C6173733D2276696577706F727420696D616765223E0A20202020202020202020202020202020202020202020202020202020202020203C64697620636C6173733D22223E0A2020202020202020202020202020202020202020202020202020202020202020202020203C696D6720616C743D224D697373696F6E2C20566973696F6E2026616D703B2056616C7565732220646174612D7372633D227B2671756F743B6465736B746F702671756F743B3A2671756F743B687474703A2F2F64316C776674306630717A7961312E636C6F756466726F6E742E6E65742F64696D73342F434F4B452F633738373737372F323134373438333634372F7468756D626E61696C2F353936783333342F7175616C6974792F37352F3F75726C3D687474702533412532462532466173736574732E636F63612D636F6C61636F6D70616E792E636F6D25324662302532463435253246356638666434613234373165383130316232336136376263653965312532464D697373696F6E2D56616C7565732D4E65772D3630346D6B3132303631322E6A70672671756F743B2C2671756F743B7461626C65742671756F743B3A2671756F743B687474703A2F2F64316C776674306630717A7961312E636C6F756466726F6E742E6E65742F64696D73342F434F4B452F613235666565632F323134373438333634372F7468756D626E61696C2F343435783235302F7175616C6974792F37352F3F75726C3D687474702533412532462532466173736574732E636F63612D636F6C61636F6D70616E792E636F6D25324662302532463435253246356638666434613234373165383130316232336136376263653965312532464D697373696F6E2D56616C7565732D4E65772D3630346D6B3132303631322E6A70672671756F743B2C2671756F743B6D6F62696C652671756F743B3A2671756F743B687474703A2F2F64316C776674306630717A7961312E636C6F756466726F6E742E6E65742F64696D73342F434F4B452F623261383430632F323134373438333634372F7468756D626E61696C2F333036783137322F7175616C6974792F37352F3F75726C3D687474702533412532462532466173736574732E636F63612D636F6C61636F6D70616E792E636F6D25324662302532463435253246356638666434613234373165383130316232336136376263653965312532464D697373696F6E2D56616C7565732D4E65772D3630346D6B3132303631322E6A70672671756F743B7D22207372633D22687474703A2F2F64316C776674306630717A7961312E636C6F756466726F6E742E6E65742F64696D73342F434F4B452F633738373737372F323134373438333634372F7468756D626E61696C2F353936783333342F7175616C6974792F37352F3F75726C3D687474702533412532462532466173736574732E636F63612D636F6C61636F6D70616E792E636F6D25324662302532463435253246356638666434613234373165383130316232336136376263653965312532464D697373696F6E2D56616C7565732D4E65772D3630346D6B3132303631322E6A706722207469746C653D224D697373696F6E2C20566973696F6E2026616D703B2056616C75657322202F3E3C2F6469763E0A202020202020202020202020202020202020202020202020202020203C2F6469763E0A2020202020202020202020202020202020202020202020203C2F6469763E3C68333E0A094F7572204D697373696F6E3C2F68333E0A3C703E0A094F757220526F61646D6170207374617274732077697468206F7572206D697373696F6E2C20776869636820697320656E647572696E672E204974206465636C61726573206F75720A20707572706F7365206173206120636F6D70616E7920616E642073657276657320617320746865207374616E64617264203C6272202F3E616761696E7374207768696368207765207765696768200A6F757220616374696F6E7320616E64206465636973696F6E732E3C2F703E0A3C756C3E3C6C693E0A0909546F20726566726573682074686520776F726C642E2E2E3C2F6C693E3C6C693E0A0909546F20696E7370697265206D6F6D656E7473206F66206F7074696D69736D20616E642068617070696E6573732E2E2E3C2F6C693E3C6C693E0A0909546F206372656174652076616C756520616E64206D616B65206120646966666572656E63652E3C2F6C693E3C2F756C3E,'admin','2014-11-14 20:28:14','admin','2014-12-20 22:16:37');
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
INSERT INTO `qrtz_fired_triggers` VALUES ('scheduler','4753PC14326191820591432619182024','core.job.SendMailHelperJobCronTrigger','DEFAULT','4753PC1432619182059',1432619400000,0,'ACQUIRED',NULL,NULL,'0','0');
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
INSERT INTO `qrtz_scheduler_state` VALUES ('scheduler','4753PC1432619182059',1432619392463,7500),('scheduler','4753PC1432619217409',1432619397878,7500),('scheduler','4753PC1432619255333',1432619398224,7500);
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
INSERT INTO `qrtz_triggers` VALUES ('scheduler','core.job.SendMailHelperJobCronTrigger','DEFAULT','core.job.SendMailHelperJob','DEFAULT',NULL,1432619400000,1432619340000,0,'ACQUIRED','CRON',1432619255000,0,NULL,0,'');
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
INSERT INTO `tb_role_permission` VALUES ('01cd73cd-598c-469d-9357-3d2396388734','BSC_STANDARD','BSC_PROG001D0003Q','CONTROLLER','','admin','2014-12-22 19:18:32',NULL,NULL),('031c0559-f666-4320-a94d-643d00d3e974','BSC_STANDARD','BSC_PROG003D0001Q','CONTROLLER','','admin','2014-12-22 19:21:06',NULL,NULL),('03432ce8-a5c7-4b33-b3ba-54b899f2d830','BSC_STANDARD','BSC_PROG002D0002Q','CONTROLLER','','admin','2014-12-22 19:19:36',NULL,NULL),('03d88c76-a1d0-4fba-a84f-660651a123ba','BSC_STANDARD','bsc.service.logic.WeightLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:33:04',NULL,NULL),('041c7ec9-d5e6-488b-a79d-3ab0fa239a60','BSC_STANDARD','bsc.service.logic.KpiLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:31:54',NULL,NULL),('04b60bff-ff8b-48b5-85dc-9c7f9aef5d70','BSC_STANDARD','BSC_PROG003D0004Q','CONTROLLER','','admin','2015-03-31 10:41:49',NULL,NULL),('055c2d59-4f7b-4158-9719-210e03d4bd1d','BSC_STANDARD','bsc.service.logic.EmployeeLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:28:47',NULL,NULL),('06e7239c-3fc1-420e-867c-41d79f7a755e','BSC_STANDARD','qcharts.service.logic.DataQueryLogicService#UPDATE','COMPOMENT','','admin','2015-01-17 10:39:28',NULL,NULL),('082f19f7-b199-4da6-bbc9-866265b80f36','BSC_STANDARD','qcharts.service.logic.DataQueryMapperLogicService#DELETE','COMPOMENT','','admin','2015-01-16 14:35:14',NULL,NULL),('087e90f7-91ff-43d1-b240-789cfbe48258','BSC_STANDARD','BSC_PROG002D','CONTROLLER','','admin','2014-12-22 19:18:54',NULL,NULL),('0887f671-d734-4f0a-8d51-b1869fda5510','BSC_STANDARD','bsc.service.logic.VisionLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:29:18',NULL,NULL),('0ae3df58-b4a2-435e-ba99-42f70c832d2a','BSC_STANDARD','bsc.service.logic.ObjectiveLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:31:01',NULL,NULL),('0e56aff2-7f23-402f-9879-e004aebecbd3','BSC_STANDARD','bsc.service.logic.ReportRoleViewLogicService#INSERT','COMPOMENT','','admin','2015-03-24 15:28:14',NULL,NULL),('0f323dae-2ed6-4a5d-a80e-f59ca9ed2f38','BSC_STANDARD','BSC_PROG001D0008A','CONTROLLER','','admin','2015-03-11 20:32:01',NULL,NULL),('0f9472c5-0c3c-488c-b03b-2961c7e5ea3a','BSC_STANDARD','qcharts.service.logic.AnalyticsMDXLogicService#SELECT','COMPOMENT','','admin','2015-03-06 14:29:25',NULL,NULL),('103d1b11-ab9b-4dd1-a076-2ec12cd467f7','BSC_STANDARD','BSC_PROG002D0008Q','CONTROLLER','','admin','2014-12-22 19:20:55',NULL,NULL),('10de4175-2676-435a-9327-7b38a30a9924','BSC_STANDARD','BSC_PROG002D0002A','CONTROLLER','','admin','2014-12-22 19:19:24',NULL,NULL),('128794b5-46a5-4c4a-93f8-7a7ef329374d','BSC_STANDARD','bsc.service.logic.StrategyMapLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:34:25',NULL,NULL),('14831116-14f2-4815-b337-8f4f5253f5b5','BSC_STANDARD','QCHARTS_PROG002D0002A','CONTROLLER','','admin','2015-03-06 14:28:44',NULL,NULL),('15e0399d-40ef-4605-9de2-f81ae375c90a','BSC_STANDARD','CORE_PROG001D0004Q','CONTROLLER','','admin','2014-12-23 10:13:20',NULL,NULL),('1973eedb-2c09-4dc6-9752-9a75a789e394','BSC_STANDARD','qcharts.service.logic.DataQueryMapperLogicService#UPDATE','COMPOMENT','','admin','2015-01-16 14:35:08',NULL,NULL),('1b1f1d39-813a-4a37-be9b-33a4a73c8cb1','BSC_STANDARD','bsc.service.logic.KpiLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:32:01',NULL,NULL),('1b7145f0-ad1e-46a8-86e9-8b50ea27c807','BSC_STANDARD','BSC_PROG003D0005Q','CONTROLLER','','admin','2015-04-01 19:38:48',NULL,NULL),('1de002f4-a22c-40da-b4f5-7b01c5a723c8','BSC_STANDARD','BSC_PROG004D','CONTROLLER','','admin','2014-12-22 19:21:25',NULL,NULL),('23e11d4c-77ba-49f1-872b-df3a15e69b60','BSC_STANDARD','BSC_PROG001D0008E','CONTROLLER','','admin','2015-03-11 20:32:08',NULL,NULL),('272c2687-bd46-4d44-855a-96e430a74e6b','BSC_STANDARD','bsc.service.logic.AggregationMethodLogicService#SELECT','COMPOMENT','','admin','2015-03-12 11:24:07',NULL,NULL),('2c5bd538-558e-4cb0-995d-28e74b2eb251','BSC_STANDARD','bsc.service.logic.PerspectiveLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:29:55',NULL,NULL),('2cd5db5f-e907-439b-bf38-b6fa9f5cc2f7','BSC_STANDARD','bsc.service.logic.SwotLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:34:46',NULL,NULL),('2fb53391-9d11-48a2-a222-06cd56084f68','BSC_STANDARD','BSC_PROG004D0001Q','CONTROLLER','','admin','2014-12-22 19:21:32',NULL,NULL),('317fa618-c840-475a-971d-69ec31051c2d','BSC_STANDARD','BSC_PROG001D0002Q','CONTROLLER','','admin','2014-12-22 19:18:06',NULL,NULL),('39cc9800-ad1d-4510-9d27-d1d8767c686c','BSC_STANDARD','BSC_PROG001D0008Q','CONTROLLER','','admin','2015-03-11 20:31:54',NULL,NULL),('3c73200e-bbd6-40ca-b911-25b8bdec687d','BSC_STANDARD','bsc.service.logic.StrategyMapLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:34:11',NULL,NULL),('3d66e504-700b-43bd-9007-ed3feb2717c3','BSC_STANDARD','CORE_PROGCOMM0003Q','CONTROLLER','','admin','2015-02-14 11:53:31',NULL,NULL),('3dc1c911-e3ce-4e25-8e72-24bdfe1194a6','BSC_STANDARD','bsc.service.logic.ImportDataLogicService#UPDATE','COMPOMENT','','admin','2015-05-26 13:49:01',NULL,NULL),('3e5aa669-5e25-4e53-b670-7ad94ba9f822','BSC_STANDARD','bsc.service.logic.ReportRoleViewLogicService#DELETE','COMPOMENT','','admin','2015-03-24 15:28:31',NULL,NULL),('3ff36a2c-9793-4382-84be-abed8c18449c','BSC_STANDARD','bsc.service.logic.PerspectiveLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:29:50',NULL,NULL),('4024c47f-8e6d-480f-8628-785150782cd0','BSC_STANDARD','BSC_PROG001D0001Q_S00','CONTROLLER','','admin','2014-12-22 19:17:59',NULL,NULL),('446cd01e-d89a-4d5b-b414-f37f5b884cbc','BSC_STANDARD','core.service.logic.SystemCalendarNoteLogicService#INSERT','COMPOMENT','','admin','2014-12-23 10:27:22',NULL,NULL),('4692193a-60fc-4a9c-8d87-e6ea29b4ede2','BSC_STANDARD','bsc.service.logic.OrganizationLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:28:17',NULL,NULL),('49268083-50d7-45ee-b84d-8a6569635e7d','BSC_STANDARD','bsc.service.logic.WeightLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:33:24',NULL,NULL),('4c03577a-6915-4e97-b6f3-118f0e7c706e','BSC_STANDARD','bsc.service.logic.ObjectiveLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:31:18',NULL,NULL),('4d453421-e92d-48c0-968b-0b560f5fc156','BSC_STANDARD','BSC_PROG001D','CONTROLLER','','admin','2014-12-22 19:17:07',NULL,NULL),('52d25b54-130c-4538-ad3f-7629f6db6c2b','BSC_STANDARD','bsc.service.logic.FormulaLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:32:21',NULL,NULL),('535e9088-9df4-4eb3-989b-c89ae2440d60','BSC_STANDARD','bsc.service.logic.StrategyMapLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:34:18',NULL,NULL),('5367569b-52c9-4fea-80dc-26d0c5900140','BSC_STANDARD','bsc.service.logic.FormulaLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:32:28',NULL,NULL),('53c4bcbf-6fc2-45e9-9f91-62bbceebd9ca','BSC_STANDARD','BSC_PROG003D0002Q','CONTROLLER','','admin','2014-12-22 19:21:14',NULL,NULL),('5b39c53f-f841-4684-bab8-2fccdda5fcb3','BSC_STANDARD','bsc.service.logic.VisionLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:29:33',NULL,NULL),('5b7ea5a4-e74b-49e6-9147-56f65775180d','BSC_STANDARD','BSC_PROG001D0001E','CONTROLLER','','admin','2014-12-22 19:17:22',NULL,NULL),('5f592c6e-78c8-44b4-9937-016067eb9b07','BSC_STANDARD','bsc.service.logic.MeasureDataLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:33:36',NULL,NULL),('60353f87-e88b-499d-8f5e-e376617efa8c','BSC_STANDARD','CORE_PROG001D0004A','CONTROLLER','','admin','2014-12-23 10:06:42',NULL,NULL),('62c51823-a195-4e9b-9307-18c89399411e','BSC_STANDARD','bsc.service.logic.EmployeeLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:28:34',NULL,NULL),('65bc3d3d-d7cb-4847-a162-b46518f8af3c','BSC_STANDARD','bsc.service.logic.PerspectiveLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:30:29',NULL,NULL),('6782597a-fb61-43f2-8d46-05565f4aae12','BSC_STANDARD','bsc.service.logic.ImportDataLogicService#INSERT','COMPOMENT','','admin','2015-05-26 13:48:53',NULL,NULL),('67b2be9a-8f79-4f9c-a2cd-3d2a223eb2fe','BSC_STANDARD','qcharts.service.logic.AnalyticsMDXLogicService#UPDATE','COMPOMENT','','admin','2015-03-06 14:29:39',NULL,NULL),('6de2c74a-fbb9-4cc8-94aa-4ca9aea8f3be','BSC_STANDARD','BSC_PROG002D0003E','CONTROLLER','','admin','2014-12-22 19:19:52',NULL,NULL),('6f87e102-1d15-4588-a3e0-9ed947178fc4','BSC_STANDARD','BSC_PROG002D0003Q','CONTROLLER','','admin','2014-12-22 19:20:03',NULL,NULL),('728a4820-97d6-4e9a-ac77-745e1de7fcc0','BSC_STANDARD','qcharts.service.logic.AnalyticsMDXLogicService#DELETE','COMPOMENT','','admin','2015-03-06 14:29:32',NULL,NULL),('72b93d05-f8fe-4a52-9ea6-3cd071878951','BSC_STANDARD','BSC_PROG002D0005Q','CONTROLLER','','admin','2014-12-22 19:20:37',NULL,NULL),('7486cc15-8027-4615-8852-c54dc0e25c72','BSC_STANDARD','CORE_PROGCOMM0002Q','CONTROLLER','','admin','2014-12-22 19:16:59',NULL,NULL),('7622e787-db2c-4010-89d4-b76446c595b6','BSC_STANDARD','bsc.service.logic.VisionLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:29:40',NULL,NULL),('790476cd-3141-4523-af34-e09983f15351','BSC_STANDARD','qcharts.service.logic.DataQueryMapperLogicService#SELECT','COMPOMENT','','admin','2015-01-16 14:34:52',NULL,NULL),('7a3fd642-0ca1-4dbe-a1a6-649ddb95e33a','BSC_STANDARD','bsc.service.logic.KpiLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:31:47',NULL,NULL),('7bfa5f18-9da1-4ec2-8478-0bcae2e0d907','BSC_STANDARD','BSC_PROG001D0003A','CONTROLLER','','admin','2014-12-22 19:18:19',NULL,NULL),('7d8a6f2d-8423-46dc-8f8a-d754f32c0874','BSC_STANDARD','BSC_PROG002D0006Q','CONTROLLER','','admin','2014-12-22 19:20:43',NULL,NULL),('7dbf40a9-335c-4891-8ca2-20938a3ff4b6','BSC_STANDARD','bsc.service.logic.ReportRoleViewLogicService#UPDATE','COMPOMENT','','admin','2015-03-24 15:28:23',NULL,NULL),('7e1bc08a-a1ce-49f3-8645-3a28b1f09060','BSC_STANDARD','BSC_PROG002D0007Q','CONTROLLER','','admin','2014-12-22 19:20:48',NULL,NULL),('802282a0-831a-4235-93eb-135c2eefe308','BSC_STANDARD','bsc.service.logic.VisionLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:29:27',NULL,NULL),('8154adb9-bc6c-400e-849f-2e92b2dde87f','BSC_STANDARD','QCHARTS_PROG001D0002A','CONTROLLER','','admin','2015-01-16 14:34:24',NULL,NULL),('817a6cf2-7ef8-4ecf-989d-ebd30e161c18','BSC_STANDARD','BSC_PROG001D0001E_S00','CONTROLLER','','admin','2014-12-22 19:17:40',NULL,NULL),('82d63316-08e0-4d8e-a6df-efd78273ec30','BSC_STANDARD','BSC_PROG002D0001E','CONTROLLER','','admin','2014-12-22 19:19:07',NULL,NULL),('85f8f531-d94c-4713-a4e5-2d13e94a2350','BSC_STANDARD','bsc.service.logic.OrganizationLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:28:01',NULL,NULL),('8661f3f8-8669-434f-9b80-e73325e57cdf','BSC_STANDARD','qcharts.service.logic.AnalyticsMDXLogicService#INSERT','COMPOMENT','','admin','2015-03-06 14:29:18',NULL,NULL),('86d95a1c-012c-4d97-b4a8-b4200e255824','BSC_STANDARD','BSC_PROG003D0003Q','CONTROLLER','','admin','2014-12-22 19:21:19',NULL,NULL),('8946c9a0-0008-420f-9142-cd7f251f3143','BSC_STANDARD','qcharts.service.logic.DataQueryLogicService#DELETE','COMPOMENT','','admin','2015-01-17 10:39:42',NULL,NULL),('89ba9428-e931-4f09-a93a-e371e05f8ec7','BSC_STANDARD','BSC_PROG002D0001Q','CONTROLLER','','admin','2014-12-22 19:19:13',NULL,NULL),('8cbc4499-5fbd-4b94-891e-09cad3e136ea','BSC_STANDARD','qcharts.service.logic.DataQueryMapperLogicService#INSERT','COMPOMENT','','admin','2015-01-16 14:35:00',NULL,NULL),('8e07a2e5-1d14-4e66-94b5-e2da14b17d01','BSC_STANDARD','bsc.service.logic.OrganizationLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:28:24',NULL,NULL),('8ec7224b-ad44-4ce6-97b9-576bb13cd718','BSC_STANDARD','QCHARTS_PROG002D0001Q','CONTROLLER','','admin','2015-01-12 20:08:02',NULL,NULL),('8ff7dc7f-5f3b-41a5-9b39-21cbaabddd7a','BSC_STANDARD','BSC_PROG001D0003E','CONTROLLER','','admin','2014-12-22 19:18:25',NULL,NULL),('91714e4e-05ab-4956-ae48-37c108c5a98b','BSC_STANDARD','bsc.service.logic.SwotLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:34:39',NULL,NULL),('91a8763b-37f4-4858-85dc-78fc1903a302','BSC_STANDARD','BSC_PROG002D0004E','CONTROLLER','','admin','2014-12-22 19:20:15',NULL,NULL),('924919e3-a8ec-4a5e-a34a-536fabfd08e6','BSC_STANDARD','BSC_PROG001D0001Q','CONTROLLER','','admin','2014-12-22 19:17:48',NULL,NULL),('92f51b72-f2e3-4de5-b21b-e5ccc6af33e4','BSC_STANDARD','qcharts.service.logic.DataQueryLogicService#SELECT','COMPOMENT','','admin','2015-01-17 10:39:21',NULL,NULL),('940849df-16a5-43bf-aaa8-49705aaf0b5b','BSC_STANDARD','bsc.service.logic.OrganizationLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:28:08',NULL,NULL),('96ba94c6-120b-4652-99e7-ea204016bcf7','BSC_STANDARD','BSC_PROG002D0004A','CONTROLLER','','admin','2014-12-22 19:20:10',NULL,NULL),('97895517-5741-4d60-a9ab-1e9ced5d3aad','BSC_STANDARD','bsc.service.logic.MeasureDataLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:33:52',NULL,NULL),('979e87cd-c036-4a24-bcc3-2d37a44f2c63','BSC_STANDARD','bsc.service.logic.AggregationMethodLogicService#UPDATE','COMPOMENT','','admin','2015-03-12 11:24:23',NULL,NULL),('99067ca3-46fd-4ac1-b00f-f3550bb6291e','BSC_STANDARD','bsc.service.logic.FormulaLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:32:13',NULL,NULL),('9c98ca2a-77eb-42c4-9974-53bcf4778b2c','BSC_STANDARD','bsc.service.logic.AggregationMethodLogicService#INSERT','COMPOMENT','','admin','2015-03-12 11:24:15',NULL,NULL),('9f192ead-71be-4723-9f75-a430b001740f','BSC_STANDARD','BSC_PROG001D0006Q','CONTROLLER','','admin','2015-01-22 19:08:45',NULL,NULL),('a0b83f02-d3b9-4e5e-a5ee-b0eedd859b85','BSC_STANDARD','bsc.service.logic.AggregationMethodLogicService#DELETE','COMPOMENT','','admin','2015-03-12 11:24:31',NULL,NULL),('a17ede81-71ae-4022-af99-d6193a0d58b6','BSC_STANDARD','bsc.service.logic.ImportDataLogicService#DELETE','COMPOMENT','','admin','2015-05-26 13:49:16',NULL,NULL),('a2fbec4a-a6da-484a-932a-d04a1825ff29','BSC_STANDARD','bsc.service.logic.ObjectiveLogicService#SELECT','COMPOMENT','','admin','2014-12-22 19:30:46',NULL,NULL),('a3222dae-bb99-4d06-ac88-8e2fec55b47d','BSC_STANDARD','BSC_PROG002D0002E','CONTROLLER','','admin','2014-12-22 19:19:30',NULL,NULL),('ac87eed8-d24c-4c55-b2c5-f8af43136feb','BSC_STANDARD','bsc.service.logic.FormulaLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:32:38',NULL,NULL),('ae88d086-0f39-4cab-a071-3db6c43e8ddf','BSC_STANDARD','bsc.service.logic.StrategyMapLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:34:32',NULL,NULL),('b13e88c0-7f7e-479b-ba6a-777152c94f77','BSC_STANDARD','BSC_PROG003D','CONTROLLER','','admin','2014-12-22 19:21:01',NULL,NULL),('b5aa807f-5e6d-435f-bdbb-4005334f5a87','BSC_STANDARD','bsc.service.logic.ImportDataLogicService#SELECT','COMPOMENT','','admin','2015-05-26 13:49:08',NULL,NULL),('b620b865-5f62-43cf-9474-07252fd0ab11','BSC_STANDARD','BSC_PROG002D0001A','CONTROLLER','','admin','2014-12-22 19:19:01',NULL,NULL),('b6fa4e94-8bb7-464c-9141-399f1dedb6f6','BSC_STANDARD','core.service.logic.SystemCalendarNoteLogicService#DELETE','COMPOMENT','','admin','2014-12-23 10:27:29',NULL,NULL),('b90eefc1-0818-4c1c-9ed0-986149c6e82e','BSC_STANDARD','bsc.service.logic.WeightLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:33:18',NULL,NULL),('b93b9bed-67e2-4e18-9b72-28867fa4326b','BSC_STANDARD','bsc.service.logic.KpiLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:32:07',NULL,NULL),('bd31fe78-a2a1-4bf4-9d71-4d49586e799f','BSC_STANDARD','CORE_PROGCOMM0004Q','CONTROLLER','','admin','2015-03-12 15:43:17',NULL,NULL),('be4efd05-f2a3-4a66-8dab-5e4a13adbb3a','BSC_STANDARD','QCHARTS_PROG001D0002E','CONTROLLER','','admin','2015-01-16 14:34:31',NULL,NULL),('be64923f-efcb-4fae-b0dd-4bf2b10685eb','BSC_STANDARD','bsc.service.logic.PerspectiveLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:30:02',NULL,NULL),('c3d28b40-5ff6-4342-87df-ba738c464462','BSC_STANDARD','core.service.logic.SystemCalendarNoteLogicService#UPDATE','COMPOMENT','','admin','2014-12-23 10:27:15',NULL,NULL),('ca0168c5-5b04-4f78-828b-d7d287f7f109','BSC_STANDARD','bsc.service.logic.WeightLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:33:11',NULL,NULL),('cd9ba3e9-d4a3-458e-82af-d8ba72569b2c','BSC_STANDARD','bsc.service.logic.MeasureDataLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:33:45',NULL,NULL),('cdb5a32a-786f-4ebe-a5bd-4d9a83d83325','BSC_STANDARD','BSC_PROG001D0007Q','CONTROLLER','','admin','2015-02-03 19:12:28',NULL,NULL),('cdbb3479-9d64-4813-b2ac-275c4dd77943','BSC_STANDARD','bsc.service.logic.SwotLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:34:59',NULL,NULL),('cebce1ca-5170-45c6-bc18-0dea19d6919b','BSC_STANDARD','BSC_PROG001D0004Q','CONTROLLER','','admin','2014-12-22 19:18:48',NULL,NULL),('cec644a1-5657-4a1e-ac68-9893e802777a','BSC_STANDARD','QCHARTS_PROG001D0002Q','CONTROLLER','','admin','2015-01-16 14:34:17',NULL,NULL),('cfac38fb-f75c-4da2-9eca-41d5a30aca6f','BSC_STANDARD','bsc.service.logic.SwotLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:34:52',NULL,NULL),('d344a52a-6ae6-41c8-a6e0-1c6748ddc412','BSC_STANDARD','CORE_PROG001D0008Q_S00','CONTROLLER','','admin','2015-04-20 11:38:38',NULL,NULL),('d9027726-3a1c-4b09-9e80-51325d505d79','BSC_STANDARD','BSC_PROG002D0003A','CONTROLLER','','admin','2014-12-22 19:19:44',NULL,NULL),('d93b5c25-e3a3-4991-af06-fcc4dadec2f0','BSC_STANDARD','core.service.logic.SystemCalendarNoteLogicService#SELECT','COMPOMENT','','admin','2014-12-23 10:27:07',NULL,NULL),('d9ccdaeb-8e9c-4ab5-af1f-c4641e1dda99','BSC_STANDARD','qcharts.service.logic.DataQueryLogicService#INSERT','COMPOMENT','','admin','2015-01-17 10:39:34',NULL,NULL),('dcd765bf-3830-45ca-8618-3c679cd57610','BSC_STANDARD','BSC_PROG001D0002Q_S00','CONTROLLER','','admin','2014-12-22 19:18:13',NULL,NULL),('dff8526c-5f4b-4896-bb82-c61391ba0da1','BSC_STANDARD','bsc.service.logic.MeasureDataLogicService#DELETE','COMPOMENT','','admin','2014-12-22 19:33:59',NULL,NULL),('e5f03cdc-6be2-4d28-87d3-0c58d2a724a6','BSC_STANDARD','QCHARTS_PROG002D0001A','CONTROLLER','','admin','2015-01-16 22:54:06',NULL,NULL),('e92a1b3b-596d-4aa3-a637-32d8bd596847','BSC_STANDARD','BSC_PROG001D0001A','CONTROLLER','','admin','2014-12-22 19:17:15',NULL,NULL),('ea5b1405-ffe9-4cca-bcae-f6ad05ed90da','BSC_STANDARD','CORE_PROG001D0004E','CONTROLLER','','admin','2014-12-23 10:13:25',NULL,NULL),('eb8b5122-6093-40d3-9a42-aa7022a44ccc','BSC_STANDARD','CORE_PROGCOMM0001Q','CONTROLLER','','admin','2014-12-22 19:16:51',NULL,NULL),('ed1539ab-cb92-442d-951b-132fa13b15b6','BSC_STANDARD','QCHARTS_PROG002D0002Q','CONTROLLER','','admin','2015-03-05 10:03:54',NULL,NULL),('efa2dbef-be81-4a0c-8b7f-92301cc53d61','BSC_STANDARD','BSC_PROG003D0006Q','CONTROLLER','','admin','2015-04-04 15:56:45',NULL,NULL),('f569dd50-dde8-4f75-bf60-b856646671f6','BSC_STANDARD','bsc.service.logic.ReportRoleViewLogicService#SELECT','COMPOMENT','','admin','2015-03-24 15:28:06',NULL,NULL),('f81d9aa1-0c4c-4d4f-8dc9-1c14f47e6511','BSC_STANDARD','bsc.service.logic.EmployeeLogicService#INSERT','COMPOMENT','','admin','2014-12-22 19:28:41',NULL,NULL),('fb83381d-69bb-49ea-9fc7-68bd4880fcf1','BSC_STANDARD','bsc.service.logic.ObjectiveLogicService#UPDATE','COMPOMENT','','admin','2014-12-22 19:31:10',NULL,NULL),('fd9abf71-f97a-4a87-abb8-8c6707567ceb','BSC_STANDARD','BSC_PROG002D0004Q','CONTROLLER','','admin','2014-12-22 19:20:23',NULL,NULL);
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
INSERT INTO `tb_sys` VALUES ('8e4f5372-d3f0-415c-9c6a-075e0fc0bdc7','QCHARTS','B. Query Charts','127.0.0.1:8080','qcharts-web','N','CHART_PIE','admin','2015-01-10 10:19:46','admin','2015-02-12 20:00:27'),('c6643182-85a5-4f91-9e73-10567ebd0dd5','CORE','Z. Core','127.0.0.1:8080','core-web','Y','SYSTEM','admin','2014-09-25 00:00:00','admin','2015-02-12 20:00:32'),('f0ad30f5-a23e-4fa8-aa16-ae2763a4a75a','BSC','A. Balance Source Card','127.0.0.1:8080','gsbsc-web','N','STAR','admin','2014-11-03 15:23:11','admin','2015-02-12 20:00:23');
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
INSERT INTO `tb_sys_expression` VALUES ('07ddaf7e-2acf-492f-a522-39fe686d0e59','BSC_RPT_EXPR0003','GROOVY','KPI Report(html) UP/DOWN icon status!','icon = \"\";\nrange = 0.0f;\nif (score==null || kpi==null || kpi.compareType==null) {\n	icon = \" \";\n}\nif (kpi.management == \"3\") {\n	if (kpi.compareType == \"1\" ) {\n		if ( kpi.quasiRange != 0 ) {\n			range = kpi.target * ( kpi.quasiRange / 100.0 );\n		}\n	}	\n	if (kpi.compareType == \"2\" ) {\n		if ( kpi.quasiRange != 0 ) {\n			range = kpi.min * ( kpi.quasiRange / 100.0 );\n		}\n	}\n}\nif ( range != 0.0f ) {\n	if (kpi.compareType == \"1\" ) {\n		if ( score >= kpi.target-range && score <= kpi.target+range ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n	if (kpi.compareType == \"2\" ) {\n		if ( score >= kpi.min-range && score <= kpi.min+range ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n} else {\n	if (kpi.compareType == \"1\" ) {\n		if ( score >= kpi.target) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n	if (kpi.compareType == \"2\" ) {\n		if ( score >= kpi.min ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n}\n','provide KPI Report UP/DOWN icon status!','admin','2014-12-04 20:36:43','admin','2014-12-22 19:14:17'),('2305b1d3-34be-47f2-b0a2-61c91dc54064','BSC_MD_EXPR0001','GROOVY','Measure datas update/input settings for (Christopher Hayden)!','// bsc.service.logic.MeasureDataLogicService\n// saveOrUpdate\n// frequency args index 2\n// measureDatas args index 6\n// kpiOid args index 0\n// organizationId args index 4\n// employeeId args index 5\n\n/*\n * The require for Christopher Hayden\n */\nif ( measureDatas==null || !(measureDatas instanceof java.util.ArrayList) ) {\n	return;\n}\n\ndef denyUsers = [ \"need-deny-user-id\" ]; // the is need deny users\n\ndef nowYear = com.netsteadfast.greenstep.util.SimpleUtils.getStrYMD( com.netsteadfast.greenstep.util.SimpleUtils.IS_YEAR ); // not modify me\ndef nowMonth = com.netsteadfast.greenstep.util.SimpleUtils.getStrYMD( com.netsteadfast.greenstep.util.SimpleUtils.IS_MONTH ); // not modify me\ndef nowUserId = ((java.lang.String)org.apache.shiro.SecurityUtils.getSubject().getPrincipal());\ncom.netsteadfast.greenstep.vo.KpiVO kpi = com.netsteadfast.greenstep.bsc.util.MeasureDataCalendarUtils.findKpi(kpiOid);\njava.util.List<com.netsteadfast.greenstep.vo.MeasureDataVO> newMeasureDatas = new java.util.ArrayList<com.netsteadfast.greenstep.vo.MeasureDataVO>();\n\nfor ( com.netsteadfast.greenstep.vo.MeasureDataVO measureData : measureDatas ) {	\n	\n	/*\n	 * frequency 1 is Day\n	 * frequency 2 is Week\n	 * frequency 3 is Month\n	 * frequency 4 is QUARTER\n	 * frequency 5 is HALF OF YEAR\n	 * frequency 6 is Year\n	 */\n	// At the end of March, I would not want anyone to be able to update KPIs for January and February. \n	if ( \"3\".equals( frequency ) ) { // the frequency 3 is of month\n		//System.out.println( \"measureData.date=\" + measureData.date);\n		//System.out.println( \"nowYear+nowMonth=\" + nowYear+nowMonth);	\n		if ( !measureData.date.startsWith(nowYear+nowMonth) ) {\n			//throw new com.netsteadfast.greenstep.base.exception.ServiceException( \"Only can update current month measures data. please contact system administrator!\" );\n			com.netsteadfast.greenstep.vo.MeasureDataVO beforeMeasureData = \n				com.netsteadfast.greenstep.bsc.util.MeasureDataCalendarUtils.findMeasureData(\n					kpi.id,\n					measureData.date,\n					frequency,\n					organizationId,\n					employeeId\n				);\n			if ( beforeMeasureData !=null ) {\n				measureData.setActual( beforeMeasureData.actual );\n				measureData.setTarget( beforeMeasureData.target );\n				newMeasureDatas.add( measureData );				\n			} 	\n		} else {\n			newMeasureDatas.add( measureData );\n		}\n	}	\n\n	// Also another scenario maybe how to deny updates of specific KPIs(measured data) per month per user.\n	// an example ( deny admin update March data )\n	for (String denyUser : denyUsers) {\n		if ( \"3\".equals( frequency ) && denyUser.equals( nowUserId ) /* && denyUser.equals( measureData.empId ) */ ) { // the frequency 3 is of month\n			if ( measureData.date.startsWith(nowYear+nowMonth) ) { // cannot update current month\n				throw new com.netsteadfast.greenstep.base.exception.ServiceException( denyUser + \" you cannot update measures data. please contact system administrator!\" );\n			}\n		}\n	}\n	\n}\n\nmeasureDatas.clear();\nmeasureDatas.addAll( newMeasureDatas );\n','Christopher Hayden\'s require','admin','2015-04-14 09:10:15','admin','2015-04-14 12:22:24'),('771301f0-6b3d-46c2-8323-28fe8a34786f','BSC_RPT_EXPR0004','GROOVY','KPI Report(EXCEL/PDF) UP/DOWN icon status!','icon = \"\";\nrange = 0.0f;\nif (score==null || kpi==null || kpi.compareType==null) {\n	icon = \" \";\n}\nif (kpi.management == \"3\") {\n	if (kpi.compareType == \"1\" ) {\n		if ( kpi.quasiRange != 0 ) {\n			range = kpi.target * ( kpi.quasiRange / 100.0 );\n		}\n	}	\n	if (kpi.compareType == \"2\" ) {\n		if ( kpi.quasiRange != 0 ) {\n			range = kpi.min * ( kpi.quasiRange / 100.0 );\n		}\n	}\n}\nif ( range != 0.0f ) {\n	if (kpi.compareType == \"1\" ) {\n		if ( score >= kpi.target-range && score <= kpi.target+range ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n	if (kpi.compareType == \"2\" ) {\n		if ( score >= kpi.min-range && score <= kpi.min+range ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n} else {\n	if (kpi.compareType == \"1\" ) {\n		if ( score >= kpi.target) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n	if (kpi.compareType == \"2\" ) {\n		if ( score >= kpi.min ) {\n			icon = \"go-up.png\";\n		} else {\n			icon = \"go-down.png\";\n		}\n	}\n}\nif ( icon.endsWith(\"png\") ) {\n	icon = \"META-INF/resource/\" + icon;\n}\n','provide KPI Report UP/DOWN icon status!','admin','2014-12-10 19:54:03','admin','2014-12-22 19:14:38'),('79cf3dcb-1346-46c6-9ed5-bd17debf9502','BSC_KPI_EXPR0002','BSH','KPI create success message plus!','if (resultObj==null || !(resultObj instanceof com.netsteadfast.greenstep.base.model.DefaultResult ) ) {\n	return;\n}\nif (resultObj.getValue()==null || resultObj.getSystemMessage().getValue()==null ) {\n	return;\n}\nresultObj.setSystemMessage(\n	new com.netsteadfast.greenstep.base.model.SystemMessage(\n		resultObj.getSystemMessage().getValue() + \n		\"<BR/>Please to maintain measure data of the KPI.<BR/>\")\n);\n','message plus!','admin','2014-11-23 14:27:08','admin','2014-11-24 09:16:21'),('dc17c074-916b-4f62-a50a-9ab8e1a919c8','BSC_KPI_EXPR0001','BSH','KPI\'s weight check for create/update!','if ( kpi==null || !(kpi instanceof com.netsteadfast.greenstep.vo.KpiVO ) ) {\n	return;\n}\nif (kpi.getWeight()==null || kpi.getWeight().longValue() < 1 ) {\n	kpi.setWeight( new java.math.BigDecimal(\"1\") );\n}\n','KPI\'s weight check for create or update!','admin','2014-11-23 14:26:14','admin','2014-11-23 16:48:41');
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
INSERT INTO `tb_sys_jreport` VALUES ('3833dfa6-4cb9-43df-9264-ef98f1e90bd5','BSC_RPT001','BSC_RPT001.jasper','N',0x504B03040A0000000000867294450000000000000000000000000B001C004253435F5250543030312F55540900031C1595547611955475780B000104000000000400000000504B0304140000000800186C9545E444B36CB70300000E0B00001B001C004253435F5250543030312F4253435F5250543030312E6A72786D6C5554090003805B9654225B965475780B000104000000000400000000BD56EF4FDB3818FE3CFE0A9FB50F77A74B93B64997A2B61383A2213140346C274D137212A73172E2CC76FA8389FFFD5E2769D48E8238DD8E2F05DBEFEFE7E9D377F47E9571B4A05231918F71B7E36044F348C42C9F8FF14D706AF9F8FDE46074475441E5352D84D4085C7235C6A9D6C5A16DD74FB27A521D254A19D144C839EDE454EFBEE2DAF570A558EBBE5C2E3BCB7E071CEC9EE374EDBF3F9DCFA29466C462B9D2248F28782976A8AACB7311115D95FAEFB3A317B9AC54BC63D1810B8C7292D131FE303BBEBDBE0AA04C8C38C9E72599C3ED5C0AB1586354C0E90B8B753AC6BEDBABCF1F299BA77A8CBDA18791908CE6BAA9FF9CE4B18A4801ED45829759BEEDCA69A23F113967600888481364EBAC45B1750A85D622DBBA284B16434AAFE7F5FADDBE15BB916BB9347967858E1759C3813F202E096317DA981CBC19155240B77ADD34C99ABEEF85C8305A105ED29A18F633C6ABD6F279BBF5233B22C14053D9185E9E9DC040385140B03BB2201D33E7CE4C4B20A4A9F6CD28A60929B9FE6CC24C5785A4CA707732FAEDEBF1C95170F415E36FDF2623FB09334869B739CDE97B49E5BA8E5F85DF845194D348A33F51224586C2F076C14C00B44CA9A408CA1CBFBDFA017F1E209B09BA1B679430CAE317F754599F50154956E89D6EAA5E1E3D9B84D5E5CFA93E9FCD6E5F2F5B70169C4F5F2BD9F1E54530BD08F6A4BB0CEF00AA5F9DEE6636BD7EBD511AA79F46A9BEF34EC0320A2A9815BF38DDCDEB7677F3FF77A799E6B40A1382B2A2B4115E177457159CE9605D4021D01DD5515AE5031FBAD2A74D0073AE456ACA69063A8D5663DCEB63B406F503515DD6F2FCCE1FE23678DFDB886D42BCA11346A115BAFDD872FDB86F91A133B092FE20F47B3D377169B712BC4DDA4D12F3FF1167F3DC9C8006F049655D9E99880013F371518D3101194DF85A95001052EC1EAE7A2E464C7D101C8AD0B2845F93224EA6ED2FF8590C01995E5B1F8DD9153C65218D63BAB1DE94646FD5B45565359C7D22FBF6F447F5E57FA890D9677AD0866D273CB20D34156C1BB440CD3561FC316E9EE73C035C24B242E450EB6EC94FE1F7047C5E6F504D0508AFBFA4343FA96AB9846D28E162A93603AD1176DCC88B878E0F08C789E5C65ED7F29DE1D0F2C281EF7BFD2E2189DF8E338D0E530D8B55BDEEA4D10BD715E3D3B6F61FD69E9D382F5F7B76DCEABD0736144E6B048E392B6076B0C4DC032109AF580B7B0CAC2AD86C909A45ED65208A96C3CD2C8EC1C900B6874C395DA25A767E075E353AFFF017C2A58699FE5151ECC9280D7F9BF78676FBF8B1C5BE0DE946F6F65E3B39F807504B0304140000000800286C95458751AF1D942A0000607F00001C001C004253435F5250543030312F4253435F5250543030312E6A617370657255540900039B5B96545C51965475780B000104000000000400000000AC3C6D6C64D755E7DA5E7B6C3F7BBDEBF5EE66379B38DB7CD89BC45E67F3BD69C9CC781CCF763C7667C69BC4AE983CCFBCF1BCEC9B7993F7DE78C7511495422B04E54388A2065A55940A04F40742014454F155A2422B408522A0A8B408A902D416D422B5AAD4C239F7DEF735F33C6FAC6225F6FD3CF7DC73CFD73DF7BCFDD437E0846DC142537396ECDAD2CBAADDD22C4B6B9996632F69CD7DBDA92DDDE08D05DE08FCE7817F1C82E11C4C56CC464B37B455D5511D389B7B593D50977573B9A859BA6AE8AFAA7B86763D07A7E4B0BCDAD08AED5A4DEF38705A0C36D4E6FE72D1B1F4E63E0E9C9203ADB4A1DAF62BF03A0C752C78B81F6E7BAA8D081652F8A71BC12B59B863AB98D95EDD2C1733856C3257BE992914B39BF9F27676350BCA9EE938666343B5105096F662B41BCDB4D96E3A59C2846AC5965A41D4BCDEE7F5AA53DF81197DBF695ADA968A335547379B3B30ABDB6B86A93A693E6ECD341DCDA28176B1DD68A8D6615EBB8DC3B51D98F79A9ED79D3AB5AD6B6A55B392CDAA3B6B5AB74BBA83F41273B23061683547209A8249D3D2B5A6C3D7C5AE1687A0EFD711EB71AA701C53D8814475362D048DE85B34C0DDEAB863B65C68D3B7EB5A336FD209960E5B5A0E26F6D4CAAD7D0BC95075E0DE1CD27ED9AE2D8768BF2C68BF4C646F56F1DC4E0AEAACEA965621B41CB8D677A2832B2D17DAFE844CB3DD40384A25403D3AFE11AF4D1089B7ED42A28AE8DA9A633BB0B01B83E2AA184AD0AB5A4D6D1B4ED139343407EE8B99C887E1B4D1AAE6A8BA21D19912B5A2BBD107E2A0888108E774CDB41AAAB3A6561CD33AF4391C49AE37F8ACA2E63870520846DBD10D9423C23B4142D2C69395C3A771AA438C11A2D36443D59B72B3F168F9541969A2544AC8A34DCE0812E24C80D16EAA461B69F648FCB16EFA93E4A972160DE12A99D63B51E4209F5BE5522BF14B6D7973F84ABB306AD3992157DC1FC715EEE18ED9421625638D3B5AA365A80EC1588E8321F44D49CE4060271C925AB9A3D9B060C95D3D16BFABE743F3F8CE3A2D80B7D784660326FE0CDF80C0CF5031DCD27ADD82957E7A93965AEA164177F66900D4BBD3C4884BC47C4BDD9D2D07D57FA9D0A21FB41E4B036A68290CBE8A66BB70620F750892FBBE3872735DD369B52D39F4687325867EF8CBAF7FFD439FFBC06343842FD10D117D70404409808FE5587F43325AE7EA97EBED62CBD09DA46198B7B52AF200E76A3AD04CA76569B6CD55C695988DFA63C924B62C130739BA666FA848F68763266F0587E3FC719B30224E726026607353879C65A7BD6EC9A057E319B4E84E11BC69C1B501A99A31B406EA86E7D0B8B47CEAA2DE4954EABA51B5B0CFC591ABBF9C6E73BDAD05E639F0501CF902A3915FF0D467392313C8A5A465A98704B7F3637F7BE98DCFAA1F1B069685115B7F55134C727B44B2CA13036E2A8D2E8BD9C405E5BAA18D8D57DCDE3885E60D5CF6000A83282BEFD60E1D78F4B82070161DD0A0F2D9B3858B0372FE39DDE6CA38DB5CD32D1B39DE34B80CEDC09CEC4165A9A196AC7236B377E0826C27E158E52675F340B36A2838369F54D01AE68196430469440A79F6560A949669EBA44188FBD013B21D4B732A75AA65E1C46D727CB2C03AF8FF21D29E1C19741EC827945CA5DE26DA600B92F64CD01B40A939D0D18EC491180D67C4AC6E1E250B700F2280065FE30850C3620E866F6987D2D28E34CC2AB71497701C95A5F83D182F7E1B385A5AD6C9964A42C39121606339381F68227FBBA0D5346CA8B826FE54908472D1019CB5ADC02CB9789472A3452EE660CEEBE2F448D751E390498D73B984CC762B3D027AE72EADC71B0FFDE510E4B538B3B1D5330B17980970CEE0AE4DD19F246930D26E23D38594D6364A8667B087A681097B3D3C87BFCE71D34CFB99A15F97B9FD1CCC54771F40D01A13ACD30ECCAE655F281732B964297B33532E6DE27F5B12FED558F85D7B8B003F914725502A644AE975DB750EB84EA51D7FFA8DE1EF7CA4F18165D47A37403134F4508BFA7E4A77EC1BE8999A5EADD37ADF1BBBF50FACFCD67747DEFEA7C49B5FD9780FC27A24123B4F9DD94B75A7612CADE32F4FB1B9BED01024D088550CBDB5D9741508522268E84CD4452A1DFA5805ADAFD64155FCE471F5685ACCA4AB8E7680FCC2DD5B4FD6C927F79B4B7A6330472F139A2239EA641DBDEE577139D5481A78CD74E0F17848EBE13912D45C9D530C3147E3D42DA2E376453534E1170CE06A176974B6816EBB043E7540F259F1B0ECAF37398C9BC119D2AFC5D33FE5BB99F2ACFE6ADE7ADFDF7FF47BDF44FF70074E1095D038934D7EBA1F1B7BC7B61432CBF2E8023E277282D4D671EECE8D42B775479489802B24547DDD1E2E54BD271C2157C3F9CDE711DAA3B1D0224E3902DC482EB356B263544AD0E67B9CE19368240B437A15EF53957ABB790BB5ECD5382DEB4349D314F22CF9B9F16B6EDEBF619EF65B0B9A6A783DDCF99A40DFFE6A9C6FDFBD52E3C5BFF917FDDDBFB6EDBAF9C3B8F7C78FBD770E2BE0BAA560C4E1E19011621E89227360B2A9DD9E17F1AA057E7D2F0C3B3096DECC9732F99268C051E30FCD5F6E3BB5272F2F72EDBB1CAF7D43E21575AAE95C76EBF5A3546510548F9445B11C5A8623F56E8F3805BD499F44C3E1D0C13895ED96EA391A27BD862D4BABE91D4943DC0AA925B4B775C7693DBD1C66A825DB6C5B150D1DA77D8D9CD6651A1BF0A187EA15CFB4DECF846DEBEBDC0AEA062F2C11E4189366AD85D88DEE5BA67970789C5BAD8CA5F8A439D5CF6BDE81511DBD1A8ABD9DA3104141137BDED091119BFB84260A5E4DD78CEA20818C351A481E4D4D371CCDEAD2F3BB7C3FEDD620905CE7CB3FD55D0AD4585843C008E14AACABE50E8E76E17270E295B6661DC6FB81EFA161A4432C499C54BB5935BCF8975DB1F49663688E1F444354BDD641502DBA83AFD34CEC5B93048F9FE90E76DD3F5AFFEA2E8C1FA8964E016F04B21807E4A61C8B30EE3C8209A45BFAAEC10246110002912306A859EF8FD3AC625743CF7CE5B3F92BDF6AB9FA348192F0D08092C021F87270026F28554D9C8BE4C8A1B0D688E492E35A0E943C54699BD9D5B8004F577424ACCB681B2E023E56142AA0D6F9DE484C30504BB1960EF9DA7817F0DD1961275A9C431D8EE5E8CD6CB18C88524B277EF88952B694CB0C3A3A64867AC66FEEBDAC559CF0F86D524F8363935E4D9682D89CE6A3ED578C25726F6C476DB442F0B78F097F7B00F8D43E912AA6CB85ADD2D5AB2BC8D757E2F8DA574B9741F9992B9F79E91E97B7E7EC98AB5180B73D283EDB8CF390DF9A69215B3590C59BFB3B7052B78B87B6A33556D1E83529087856061AB840F7F8E1310232DDC47D6B5592E7FCFFB3D83046B49C2E64B6360BA532E79D174AAD00F917FB514604BFA5732D667D42409C9310B79285E446A68496AFBC91DC0A029EF2EF8E887E68F2D91BC9E256A65016308A5168F53DB0E053A51D85DD54688120DC819F4043F04EF9F4CB67D22534F24198B31E07232E4D11FD0E4D9F91D337922F2088ED7C08A3C0CD288B3BD9D7ACD0DCD3722E4A4DB25CDCDC2EA43383EF87BF0015B9CD8844A8982E64B750F98410EAEBC8DE2824F76CC7522B8E675CC3949790739BE9642E84E98CCF0F39933CE1304BC879858CD86339B59D5F0D0338EF0328843C86C8AD95B21B99F2CE663E04E2B40F8294CD0E3A9C915CBDB659D848E29F64BAB459783108A1AFBBC801AF051F0043D0675D2ECA258B45A45072355308C29EF339818B72CEA4B7B310880B12C47621575E4F12850A5158CE7348842A0EA4A08FDA5857895456145E67DC5D6773197E00B99B61C4FABECD8B4DEB864687621C7421EC1D4766632B878ABF18847BC63F8EB469181192335914886572ABA19981C01419EFD09C51DC4729BC81BE4A4E7AF66BDCB58E94BD9BD942693B99CBEE1C032ABA7FBAE5B42941A20BEA19F20E9ECB6F1632A83D9FCBE693DDFAA43756E2CE07FE77834C539B4A5BFC19C3BD900E5DBECCE79397D42B793DF6B823FEBC83AED3FC41042FB98A2EB6B1F4AA6936F08AEB563B81F261C78BE7F0035857ED3A6AF813635FFAD3CF9C7DE90BC330B4061306F2AEE035CA44A8A339AC9B46B5D3FA916745A0F476027FF3450987FF75805DA5C2F711FB95255EFC01B5758EE197F2FB44E0761F886C3C18E7AAF3B96E5023FC14CF1F231F8C733F02009EFD38FCEABFBFF5E8275DFF63E818711A1F4C204E1311A64047C0316F69CDA2467737875E3FE602E1D0745D25158DCE1012810F4422CCEEF666E3F070C7655B23D99BBF325F4317677E6FAF7CA093F3328F97164B9B476E7AA7B0DF6C6248721771191A3CFE324C083DF93BEFFDA50F7EF2B7BFC8B6EF7BC7EC17FF3CFF1DA4D9621CCDBCFBD1DED77EFCB58F3CF407AB2EC52857A9AF150A50CC0532E0C53C0593687B2A6D8307EC5230A5372B160FFA91F7D5EBD9A5601C79577344DACC4C60EEE02F0A697F929B2CA1757B88D31E1E5EC0F961F4EB42D8C9150708C26683D3E49A173D589E15E87623EF3E6248C8A7445F576FEA8EAE1AD1BE6ED0B19DE0B40BEE68DAA3E6E04FD105778ADCC9F1838EB7BF491915033D91741D56442069B4F86211CD190F4BC547EA7A8E222AEE97273FA5E5297716A1DCCF534CB2C7555C5859E466120D49A69CDFDE48A1851A285A16A269D42685E9A3DA5B42F6E75C3252E5628B0E94DD15407AE8B8484FA53773DB1B790F6D02781F1283F612B52E1F70816EA8E4427BEB0E1F6FDD1EBC47069F7F95E3AD789701F2E409DCFD479169C55BE5C40F89E5E871B19CE02CE1E3B8148FE3D80F8963E2D894941C20B074196054B40A7C91999F8965E63EB1B14869DBCEE5A8FCF1C11E4DBB72DD22208EE7D0152FA6935B99015F02C2396D11101368B24A59BC42B906F682E7ABF9C92B8306354AE8357405EDE6FAA77C4CED99E6AD866ADDCA69079A91EA7EF84CC1C93A6EC332F4E6AD926AED6B0E1A52BF851BD23934A4E2B5993260DD57DB1C9C519B95BA69917AEE311D51CFAE683366C3AB73C3415DEF8C32A477787824F9423D8B9CF3065076654F287D36D0ED47C5631310D67B6651D4D783E52568F4A073C1A71BFAFA8EDEEA8370641A067A2C3C71866714B60D9EC24A2E867F38D24026DCC391F5B196EA209E6E40EA94AC762DD08931725D7CD6935C747F3F4ECBC1E89E69C904D14B399814B574209D462670A782C34E05DB8283A744C7965AADA20E08A70848D543FE7DCD6C3A01CF81D78B784F2305F492AC53B2185EC5020078E637A5C4743DDED37E2562E723BA3C767D578E1E8552780D924794D0ED2C8ED42BB23EA5DB5BD55AA6B1A755AB9A3BE8249723FD9656AAA348ECD765B342CD8786562DC92B01250AE9F676B3CA5945F3B840AB3921DA9DF45B82949BA46649374986318293323BF179C639319050A039329F5E2E3813681ADC67CEF993A4D7374AFAC84BC540E60DE23A4ED7A07D4B6DD5F15A3EC003161FCA73AB90E0CD8A294111646A5A0B338848AC0F917126D0146257DE1E2664C23285F99073A7DCBA24C7F2002EB0194AB6A6DCFE103AD35E431099096C0DA3722A94D011E0DCB33D1D1EDF3ECB8DD2824C4E7E87979AECE63A4D78B94E6C57E43A51FD9A5B785C5C107F73F4DBB7DE7FEFFFFCEBF75E9CFCFC9BEF5CFF3B02D71291FEC99A6E6935E3D06E5368C28293BEBFC065EE4BDFF8CB4FFCF333FFFDF521606B6EA688158C6AE4DB2833D6073FF5E14B93BFF8D59FE6B7C8E49F507A34AD7C833C8A4CBE9429502DDD12BF8F9BD42C59DCD76B93DDDAC6E544D9484AB07F9E1F41A78C67B38383AF734DD7A160B68A9D567C066C2A30FAFA11024C4D1AB11D337B4466B8E5F544332D6FF3C6F4F2D228B5C8FE0E27E6A029C2887ACAA5924F5138C657408268FE5CE6AB2BC2670F415D19F4C9270867B4BFB51AA7358212C61BBCCC4C1244AF61F0FB6DCE9DE24A37C1E01FF8D05E543C1F3CAA6370C65660B47C1B96C2C85EF17ED55AC7084B215973829782C74560EC5EE8E28E11D931B82F812B1624030EB4E4E0DE30422E71C61D04AE5012C7793DE496C5073D8116B746D9D274C859B4CE5CE392F870D31C6AE9329EAC42D6DA6F723D146488D39EB90BB0457FAB1AB07B21B5C1C53C8486628BF59235F12D0FB54DC9B61465EDB87E92E2A87B4554021EAB92DA906D5CE9CF775C7380EC265F75B3B492EE1C3EBCDEF27F021FBE80B01AEBDEAD72BCFB5639EC3E99B7823FDED7330F336978B481B2F77A3FC489BA8EE53729569F29C6A59D55351BCDE792FBD950DAFF72D267896174212B9669DB48AAC0005AEA108F567EF5D6DB7336F0ED57572F13B17C0632BC3EC3C3EB3354EC1CD9D1B6606837F5A96F9FFBEE68A2F455197B5DFDCFCFFDE0AD3FC2EE95A1D718DCEDBFC197571E5D796AE5DA534F3DF5E8932B2BE56B4F3C75EDF147C728F1792992EDDC8F3CEDE5E778AE95CC9234AD311866705E246009C75A0C10A90B637082C11537CC6A5AE5180C189CF3B297CAA1B74716231035DD30786A9561F8773606533EB8CDEC6A087CE85199C19D3D0B07DE89185CE8E9F69E2419CCF99D81172E0677F54C0A3DCA4541F5DE70F1B87A3AC3CFF30C2EF68CF09FB419FFACA0A7975EE6195CEAED0ABC6232B8B7A73FE289328A668177EDD0F6A31EC918CCF803C42B1F83F9230EA8E823DF4B97F0036F1459BDB7FAC893745F3343CB473F613318E7797E829D9606E54991A785FC2826CB2C1B068AA88B1423AF5B260531F2A5F9704AB2F16A695173C7BAA0642FD7A50CCEB8A96DE5403099C5A48D0710F65E73185E265C50A1102FB2BDD7118CA132980DAF2D5BE7BAC1C8F6997B6DBA0D5578AC3FDBAC99A83172A6B58F3AA7AAD5D5B6BD2CB50BDD2AC4A3F2B23716D11B2B9711C2469A01DBC1D3C1C35379370A7B2EA89736DC0E9C33592E3B7A03DD34B5D1C27937182C065ACAE547AE3D556E6A78895A575BE8AD79AAEAB195C79E6030FA0C3D99BC8BC1F0C2E24D051E87272660049E6470EADE7D54E6AA61147547E39FADA1DA5A58DC8DDE4EBB492B2EA376346C1CBFEC4EBCAEC0D3707D0286E019C69D956329BD3140C456FA2D28523DE863421A91ACAA2D3EED59061315D5764A26994E064F2C04A225429D5F0F3E4A72522EF68E512005E90948C2AA0209181FC77DAC2930214AEB0A4C8AD20D051451CA2930254A7905A645694B8193A25450D0C0F152498153A284443F2D4A2F28302B4A3B0A9C11A5F72A30274A3FAAC059517A49C1CB2D2FED29705E94AA0ADC214A35052E88525D818BA2F4B202778A92A1C025516A32FE7C3DB0C08F414B81BBE06E9A6B29302F4A8E02F788D281029745A9A3E0559C975E55E05E517A4D81FB44E97505EE17A5F7311E8F3E8E048FC1FB1578001668FA4F28B0284A1F54E08A28FDA4020F8AD24F29F090287D08859878B9C82573C317A93B16168F102A057E167E8E98F6E71558814708CA2F301871EA3A4EBB271763F8493522FBD6CDAA8CF833787221E29F9BE8E1B608061C267F88C1C3C712BA317883F1C76423DDB6E86B3C068585DC517E4D042203A1A6C0AFC04727E197E1634CBCACB2A87F5403FB546BDF0EF749080CA62B6D1B2FDB787DA866510DA18A58E8CA6AED5F5DBC4904424FEFD963EDEF88DDFC3AEDE63708E210FD426770A8D5603D89B6D85CC3E6A103FC3541DA93EB2BDA6237F202BD11D6957B9680DF45B2D0F97471C6F1107E137E8F10FE7D5A035D53CA7E48C01F526D9475E51926E0D3D43EC6A2128712F0C7D49960BD196A09F833EA1A67A174A704BC4DAD132C3A3F2B017F41DD93AC37992F019FA72E851D91A59980BFA6FE291691D798802F50DF34EBCE194DC017A9E3248B4C634BC03F50EF0CEB97A196802FD1A0532C2AA931015FA6CED32C3A3F2A015FA5EE59E6A67725E0DFA8E10C3B2A9B34015FA30173EC88ACBE04FC07F59F65BDE99909F83A759D63BDE96B09F82FEA3ACF8ECA584CC0B724C38A8F0D68F41DF4EB02F372A513F01D6AB9C8DC54F1047C8F1AEE645E7A7702BE4F2D97984C9846BEA1FA5D4C266827D830D5EF665E8277828D52CB3C93B7E30443864A1022A8D7391AF7B0FFABEDCA03ABA8CEFDF9CEBDC9BDB91942726F1232849D00E126110920822CA51135042E224B088810B31159025940B4D4E2828F676D55AC5BD5AA55E382CAA2242C6E4F1F58AB96A7968796BAF094FAD42A5AF5D5E7D6DF37E7CCCD4C18E0C6D67FCEEFCEF77DF39DED3BBFB3CC4CE27A512048DD583A803A3D890F523ACB0792FB497790222CCE23E7A3E52065B37010B99FE506C944DEFAB11D282B2BBFD4637061357C3CC6B5D71BFCE0A6B1B6B2AAE6D400A13D52EA9BA637D6D79532E1623935CFA0BED42F447D08D50B281D6403952CCF10C3C4C9985A6830A6A2EAFA269EDFAA31492DAFAE6CAC8ECF4506E52BFBA1C443B480EB841B328E7A7A14A093908B75043DBD1631E0AE97FD88C9A09369788886513178CD9A21F82BE6C6167EA3C6A09116A3D028CE640827F99C0CE524CA89957B2127459C20C3C11EF4E54D5634915D636196DE798E08D08FD9DB304E4E0631F2976B413A83C49484B831B102D0483A8B0B50CAB90CE7A49893119C8CE4C4AAF4299C8CE6E4544EC67032961326FDFA6A2CAF4B499FF32362ED289ABEB49A6DC671329E93099C4CE4E4479C4CE2C4AA630927A7733299933338399393B338B1CA368593324EA672328D931827D339399B9319E87F3BEFC94D8848FEC306AC388793999CCCE2643627D61454CEC95C4E2A3899C7C97C4ECEE5640127E771B29093459C5472723E27559C58EE6B38A9E5044B9A30AF86F210B7CD79F5CB57352CA9C91B4162C8F146CD99AABB4B97AD581AA07F7305823E61A17FC7E6257FFED1DDE7F17EA0B55BA19F8768035D4DFCB7959A5634D65456F33BB724C69FC8892D2A1DEA2135E897740D56FD742DF11B655CB969D67A6EFA72BDA0C27EBFDA199C6AD998D0124417DF235BCFB0DD48D773497E85E1DAD1E04D35CD566B27B6AA9C83894BBDEB162F90350158ABC2136F8BBA522B740ADD4CB770897FED2A719D2AF1608F127BD26F669D678167FC738DEED9C2BFA13BB8BC77625AA8D39FF46125913FF4A89C0CFA2DDD1DA2ABE81EE29733EB9B1D7B89024F1E3CC6EE825AE93ECEF27E43148993780A7890447EA2C704017A08F16E17551D328C3FBA5D86267EEE60D023B439440FD316E5D831F96CC33E088A47A168AA7156B867FE316AC7E16638070DF6517C7F96A77980769E90E7BBB25F32A89D76A7D22E7A1CFC8CAAD87F7DE3840BEDE345C893F414BB7C1A2E9B9C2EC77CDF7263943C43CFB2CFFFB4CE6FF29A5657AE501BA0B4BC2AF7994B6FC7FDE8BD61331B6A9BE3EFECA070CFD1EF38829E47F88EAB5A6A1DE618B4978F7124BD6888516224ABFF90846DD29425B3F76719628412BD02066E6A4155F28AF33AD64313F213FFEB509E23379143858E1377FA6F8372697F880ED06BF1F20CCF6B6E508D0722700DC4F8A632685B18F4673AC883F20D123DD4ED23F21A6BEA403A358D2A0079CFEE8E56D401CBAC0677D076EF74974187E8ED10766FFF835EB10BB6BCA1B9BE16BD92AC7E1874182D8DDCFF8229D96D3389777729F1DF06BDAF2C3FC06AAF73B3C727718356A8C6F888445FBB368839AFCFCA488C3A415F79DC64D5B4C7313C1AF4297DC235FE1B0F775DC67A2B2C97776D5FFECF7D34CC65F473BE06FD1F7DC1CDF1771E7ABA4058AE9E782289078A0FE6067D4D5FB19B6FC05BB61B90C3192DCB2D2A9CD9B2820B48A2C48B463DAB52AB6F6D1AD6C909B20C1FEDDA9024B15F3E2025A60E3B4EAA96362CAFE91CE1F1719464A90D992C93103632C093B80E083BF312EB04618C475324B6BAE0237B872B43A6CA10BA5F627B1EE91CA258E31A546105A7EC1E0F8FE179AB2B393CFC0C860C5B212EB1154ECA9F520A9A936199C5126C657BDB1E175B9F7BE93EB74380C469C75FCC4CBEB0AAC6FA48D5BB9BB33CBD1AB2A734B9C4B90E6EE998E4E321B5AC094C736E42ABA9AEEE3F3A02119918B29FECCBE5E9DF69D2EB42761ECED9ED40769BE7A8666DFD72EB0B2F70A5FDD390435407E58311ED60AAACAEB683B5646943534B634D9C357F947FDCC37E65A76FF25C0C82EA8EE9DE9085B280A3ADE87B85B0A3EAC3B8EABC71CC9FA242AE98EB38E2FB9CBA3ABC8E62AFA738A8BF66654BE5D2263E31F0F0310F53823230E45839860B709AA3331657362D2E41335AE702D842066D812127CA096C8D8D71765E5523FF8D3AD7439785C3792FEFB9FF09CAD4A07824283E0B522A1C23AE83F22CCC63E3F2AA3A36F5791382728A5ADA5AAFFD04E554B532C280D6026C6632BDB226714AFE315691C73A79B6EEB30EAD171BF2C7B2048B1039934471D79D04E4EC4E1B1CC5C7C7DA09CAF2909C23E792C83DCE722920E719F22C10AA9C2FCF35E4D9720617F0BC63F4A9E57611DB5682D02A55939CDCE50760B24A56A7A06CE8FCE42AAE0346E9A0134C5FF6B3BDEE9D4401594F2250AB5E5237E412B938242F909804FA7418C61A2CD3D36B6ACFE03FA23CB9B111EB2CB99CD4A7B70E67B186666561536B40AE2011B236004D4D9575C8A0116B2BB952768E7A57C3B78464835C85E1A7023C6C7D426D3FB099C5091FA163E257EF426A49487DFBCD5FE78AFE420ABF40F3887CFC0A8A143E81E7A76C16A66A343476D398A6B1BBC6748D191AC31A231A33356669CCD6D843638E4653634F8DB91A7B69ECADB18FE86B613F8DFD350ED03850639EC6411A076B1CC22FAE02876A8C6A2CD058C8981EE22D2270039F16022FE50752C0145EC65B38CAC2106F04F8540BED788A182D485E08275F59F90AF9E7689B18D32EC64FA56985E189ED62F253626274E163E2CCC2728A153925A545E534D66FFA9DB232D30F619299E4144E3393204C36939DC2E96632840133E014CE30031006CDA05338D30C429862A63885B3CD14084366C8292C374310A69AA94E6185990AA1611A4EE17CD380B09BD9CD295C60768330CD4C730A179A69107637BB3B8595667708D3CD74A7B0CA4C8730C3CC700A6BCC0C08C366D829AC33C31046CC8853586F4620CC34339DC22566268459669653B8CCCC8230DBCC760A1BCC6C087B983DC22B2DE14A1636993D20CC31739CC216330742D3349DC2D5A609614FB3A753B8C6EC0961AE99EB145E6CE642D8CBECE514AE357B41D8DBECED145E62F686B08FD9C7295C67F681B0AFD9377C9925BC8C8557987D21EC67F6730AAF34FB41D8DFECEF146E30FB4338C01CE0145E650E8070A039D029BCDA1C581EDD2EAE1D9B67E6F1F54633AF7C8BE03FC4C8DF571B087EBF3C20AE17BF6299B841DCA80682980182E18F9322180408F89B8AA305853BC4ED7EB19936F368B36EEF673DC8C8D0B7F3AFDF883BAC97CA33C49DE22E585D84DFC99C09D883C46FC5DDDAFD5ADC99C4C7E2706F8D9E7B90C10E719F0FD1EB0FDF5F1C2D8C5F3C501C2D52175BE21947ADB215C6332E140F8A4D56C685E221FC92D6AF87F1CB671521CDCAAE14143705645206CB47C4665514DF60E872E0F82E1405837D4B714178DB0ED1E613765CC58AAC915F545E186E8772A74BC985B7680043BE30BC0BFAC7DD7AB080A28424E89F80FE29B71E84A0D82119FAA7A17FC6AD073728A20840FF2CF47BDC7AD084E28C20F47BA1FF9D5B0FC650F49102FDF3D0BFE0D6833C149384A07F11FA3FB8F5E011452AA9D0EF83FE65B71E94A2F8C580FE15E8FFE8D6835D14D574837E3FF407DC7A108D629D34E85F83FE4F6E3D3847115077E80F42FF865B0FFA515C940EFD9BD0BFEDD68389142D65407F08FA77DC7A909262A830F4EF42FF17B71EFCA4C82A02FD7BD0BFEFD683AA146F6542FF01F47F75EBC15A8AC2B2A0FF08FA236E3D084CB15936F49F40FF37B71E5CA688AD8723FEB339C2E59D76FCE39715FF56ACCFC184ED17B330BDCE1693705529CAC53A311703B2423C2DE689B7C57CC2E68DFA8B0534499C47956221AD138BE84E51494F8BF3E96D51854D77B5EC2F6AE424512B2B459D5C271663CC7C668F19AAC0984905A6A831F379C7B0D01CA7C64C138F992FA0FCBB4B698F99166BCC7C09FD576EBD1E33ABAD31F335F4DFBAF57ACCACB1C6CC77C5051122B7811E3417F3A08990640BBFDB420F9BB53C6C2294C41601B7851E3897F0C08950902D426E0B3D74D661E874EA1A0ADA5D835F0FC5BB6600D66A7EB1125DD388AE6942D734A36B5AC094ABC4B362B5382C2E1444A976338B2368667EC97793D5CC1132B81069AA109AE355435F51C465ECCEEA0C97DA6EEA2BB9A92314668B4CB7856EEC0DDCD811CA628B1E6E0BDDDC5771734728872D7ABA2D747B5F8DF6EED412084C9BA41FB40859B54436E8D88FBA0F14978AC9E23251272E171BC415A87D2EF552B5F76F86454F10F4135C7BACBACADB69C0CD22A79D06DD2CC25B69C82DC268A7E82D22B995F6E5FAEE10017FABF0FB6E1619112AE47AC750E756DA9AEBEF50F545F98B8A2334CCDF4E2376D0293EC1D58FA1EAADB42137C96D38DA6D885688A1055A69516EB2DBF054B7211A23868668A5D1B901B7E11836F4C50D118331C45F2BA5E506DD8663DD1E118A318461AB783737BD53DEA7B95D22246308C756B12B37BD53E6E3DC3EC1EB31707AABB83637BD53EEE3DD3EC1F031B07BABA8CB4D4FE9B01C04CB09C5D147C5BA1D3409BB062AE160A01266FC18D8BE558CCE4D0F75D80F85FDE9F05C029CCCF7ADD1F79DB983A6F8054F04314C02ADF4E2519D89392006FEF7E8CC3277A53019C430117874E654B72166851866048FCE9CE636C4F410C3D4E0D19931772B619E88618EF0E8CCE96E8F983062982CBC3AF36CB74B4C1D314C1B5E9D39C3ED1393480C138857679EE3F689E92486A9C4AB33677A752656CD31AC94BD3A7396EECCD91E9D8975750C6BE9A2A3568BBEDD361FF876D31C2AE75DAC6F3BCD0507AAA5DA4EECDEFCE21760866BB07FBB4E4C807D1916A675E226B0E52D90DE2A6E13B78B1D5869EEC15D87B0A6FC58DC0BD6BC8F4CF1008D129BA8443C4C0BB1525D26B6D206F128DD28B6638268A7BD62271D11D7D037E23A992AAE9739E2063952DC8469EE16B940DC2A2F10B7CBF5E20EB951DC251F1177CBDDE25E2C8EEF9387C5033E2936F9D2C5C3BE42B1D937566CF5CD118FFAAAC576DF7AD1EEBB5EECF4ED148F83BD2A7E68F69A97287BCD4F94BDCE4D94BD1624CA5EE725CC5E0B1366AF4509B35765C2EC757E17D9AB4A077CF5BF8ABD6A1265AFDA44D9AB2E51F65A9C287BD527CC5E1724CC5E4B1266AFA509B3D7B22EB2D772DD990D3F007BED017B3D07F67A1EECF502D8EB25B0D73EB0D7CB60AF57C15EFBC15E0760F53AD8EB20D8EB0DB0D75B60AF4360AF77C05E87C15EEF81BDDE077B7D08F6FA08EC7504ECF529D8EB39B0D7F360AF17C05E2F81BDF681BD5E067BBD0AF6DA0FF63A00F67A1DEC7510ECF506D8EB2DB0D721B0D73B60AFC360AFF7C05EEF83BD3E047B7D04F63A02F6FA14ECF539D86BC50FCD5E2B1365AFC644D9AB2951F66A4E94BD5A1266AF5509B3D7EA84D9EBC284D96B4D17D9EB221DF017FFABD8EB2789B2D7DA44D9EBA789B2D72589B2D7CF1266AF7509B3D7A509B3D76509B3D7E55D64AF2B7467AEFF01D8EB5B7E5F589C4C524C20BF28A36451875D6C3385B07232C46D94267650BAD84361F055A6F898B2299572C8A49E348A7A5109F5A185D48F96D100DA407974230DA61D944F7B294A4748D03724652AF9650E25CB9114949328241790212FA034B99ED2E5460ACB472853EEA66C798072E461EAE993D4CB974E7D7C85D4CF379606F8E6509EAF9A06FBD653BEEF7A8AFA7652513AD195F1C3CBB522C53A202CE3C3CBB08CEEA20D15FEC7E9AA0A5FC1CCA4C7A9A4C287C89E59E1E7B48D7E116F711F6C945DE14CFFEE5085CF37BD9DAE6BA71B545392E3D0B334BEAD2D8D1F7A96AA434F14E546BA491765990859079DA771512C6AEC4279DAE956F0E8964E598F8D673D369EF558AB1739EBDBE8769DF502B402DB8F620EEF52AE7739EBABB6F123E2998E5099A6F322FD973AAB11B061AB21D1ED746F58DE2382D1767A60F356DA34B560AFE8CED276DA5A31751B6D2AD84E8FB17381B26E8B3B1888DBB91E3D10CE1B2BF660022A8F566C17D73E2636B2C4BE613BB5E91BC23AC7E468016CB6687D7B3C044C040057BE1BF2DEC167D7F4845FD86E9E8CB7510E8CD88DA1CC76D07FF8E256CFC43BF168677BFC42E599C2AF8E69B32C54812BC1A76265B48D7E6F998820BD103719603DB943A3B6D34B2913FB6CA37D7DE666D27F4DEAB78D5EED37D7AEC61F69FFD1D56CA3D7ED92FD890E6A7D86CE3229DA466FDAEAB7E86DAFDBDFB1DDBF1B2F8EF3F6F76CF5FF7AAA3FB4D57FF52EDCC776EE47E8132FFD67F6FD9FD3175ACF87403C364268D222D3DF465FDA36FF4F5F79F9F8D6CEE33B3E633B4A2F7D5A2FFD32E9E82AC8A0AD4E9121AD8EA05FB9670328429BEC661BA479D651A6DBFA0CAF2692995B6CB5CC8AFB578F5102D1BEBDDB640FDB20479A9E05E86567D09BEEF6E8837B6D751FD9F7186D280774D80CD43699BA1041B66993836C8BC19EB5186A17322A0B3C0B79D296780EC33C0D86C79B4916C79B51192447FBB6C9911D0E46793A186D3B38558EF1EA87719BB47EBC9CE05183494A1DE2172AF4439E61F00EB56F60347C53449E3E331ABE27222703EE8FC833000F44E499802D11590A6857B04BC1130A9E56F0AC82BD0A9E57F0A2827D0A5E51B05FC16B0A0E2A7853C12105EF2A784FC1070A3E52F08982CF157CA1E04B055F2BF8CE8208498D491A831A0D8DDD3586356669CCD1581491658CA3359EAA718CC6B11A4FD3384EE3788D1322721AE3E9FA7AB2BE2ED3D753354ED318D3385DE3D91A67683C47E34CED6796BE9EADAFE7E9EBF91ACFD5B840E3791A176A5CA4B152E3F911399DB14A5F57EBEB1A7D5DABB14EE3628DF51A2FD0B844E3528DCBB49FE5FABA415FAFD4D78D1A9B34366B6CD1B84AE36A8D176A5CA3EB7D91BEBE585FFF445FAFD5F8538D9768FC99C6751A2FD57899C6CBB59F2BF4F57ABEDEA207CFD972467CD2F25B632B3B22CFC1BAA12CDA2E67ED92732AB0A66893156AAC86F8B3756D3F520D3691BF957EBF476420DD2E173C851BCAF68A70BB5C58B64BCEAFC0ADE7638A8C3E2A6B9587A0AC938BB587C17A5A0E23AB659BA7EE920D1505DB65739B5CFDA41AE52249AE143C075C2CF8D58F40B3C8F2FCB4F858FF0221FE274D4EB2FE8F52C3AA35FACFB034FE03504B03040A00000000003066954500000000000000000000000010001C004253435F5250543030312F2E73766E2F55540900035C5196541C15955475780B000104000000000400000000504B03040A00000000003066954500000000000000000000000014001C004253435F5250543030312F2E73766E2F746D702F55540900035C5196541C15955475780B000104000000000400000000504B03040A0000000000897294450000000000000000000000001A001C004253435F5250543030312F2E73766E2F746D702F70726F70732F5554090003221595541C15955475780B000104000000000400000000504B03040A0000000000867294450000000000000000000000001E001C004253435F5250543030312F2E73766E2F746D702F70726F702D626173652F55540900031C1595541C15955475780B000104000000000400000000504B03040A0000000000306695450000000000000000000000001E001C004253435F5250543030312F2E73766E2F746D702F746578742D626173652F55540900035C5196541C15955475780B000104000000000400000000504B03040A00000000008972944500000000000000000000000016001C004253435F5250543030312F2E73766E2F70726F70732F5554090003221595541C15955475780B000104000000000400000000504B03040A0000000000897294450000000000000000000000001A001C004253435F5250543030312F2E73766E2F70726F702D626173652F5554090003221595541C15955475780B000104000000000400000000504B03040A000000000089729445FF31268C350000003500000034001C004253435F5250543030312F2E73766E2F70726F702D626173652F4253435F5250543030312E6A61737065722E73766E2D626173655554090003221595545C51965475780B0001040000000004000000004B2031330A73766E3A6D696D652D747970650A562032340A6170706C69636174696F6E2F6F637465742D73747265616D0A454E440A504B03040A0000000000306695450000000000000000000000001A001C004253435F5250543030312F2E73766E2F746578742D626173652F55540900035C5196541C15955475780B000104000000000400000000504B030414000000080030669545D86EF9ED942A0000607F000034001C004253435F5250543030312F2E73766E2F746578742D626173652F4253435F5250543030312E6A61737065722E73766E2D6261736555540900035C5196545C51965475780B000104000000000400000000AC3C6D6C64D755E7DA5E7B6C3F7BBDEBF5EE66379B38DB7CD89BC45E67D364DB4D4B66C6E378B6E3B13B33DE2476C5E479E68DE765DFCC9BBCF7C63B8EAAA8145A21281F421435D0AAA25420203F100A20A28AAF12155A012A040145A5454815A0B6A016A955A516CEB9F7BEAF99E77963152BB1EFE7B9E79E7BBEEEB9E7ED6BDF8013B6050B4DCD59B26B4B2FAA764BB32CAD655A8EBDA435F7F5A6B6749337167823F09F87FE710886733059311B2DDDD056554775E06CEE45F5405DD6CDE5A266E9AAA1BFACEE19DA8D1C9C92C3F26A432BB66B35BDE3C06931D8509BFBCB45C7D29BFB38704A0EB4D2866ADB2FC12B30D4B1E0D17EB8EDA936225848E19F6E04AF64E1AEAD62667B75B35CCC14B2C95CF956A650CC6EE6CBDBD9D52C287BA6E3988D0DD5424059DA8BD16E34D366BBE9640913AA155B6A0551F37A9FD5AB4E7D0766F4FDA669695B2ACE541DDD6CEEC0AC6EAF19A6EAA4F9B835D374348B06DAC576A3A15A8779ED0E0ED77660DE6B7A5677EAD4B6AEA955CD4A36ABEEAC69DD2EE90ED24BCCC9C284A1D51C81680A264D4BD79A0E5F17BB5A1C82BE5F47ACC7A9C2714C610712D5D9B41034A26FD10077ABE38ED972A14DDFA96BCDBC4927583A6C693998D8532BB7F72D2443D581FB7348FB65BBB61CA2FDB2A0FD3291BD59C5733B29A8B3AA5B5A85D072E05ADF890EAEB45C68FB1332CD7603E1289500F5E8F847BC364124DEB60B892AA26B6B8EEDC0C26E0C8AAB622841AF6A35B56D3845E7D0D01C782066221F86D346AB9AA3EA8644674AD48AEE461F8A832206229CD335D36AA8CE9A5A714CEBD0E77024B9DEE0B38A9AE3C04921186D4737508E08EF0409491B4F560E9FC6A90E3146884E930D556FCACDC6A3E55365A4895229218F3639234888330146BBA51A6DA4D963F1C7BAE94F92A7CA593484AB645AEF4491837C6E954BADC42FB5E5CDE12BEDC2A84D67865CF1601C57B8873B660B59948C35EE688D96A13A0463390E86D037253903819D70486AE58E66C3822577F5F6F85D3D1B9AC777D66901BCB926341B30F167F826047E868AE196D62B16ACF4D39BB4D452B708BAB34F03A0DE9D26465C22E65BEAEE6C39A8FE4B8516FDA0F5581A50434B61F05534DB85137BA84390DC0FC4919BEB9A4EAB6DC9A1479B2B31F4A35F7EE5EB1FF9FC87DE3E44F812DD10D18707449400F8588EF53724A375AE7EB9DE2EB60CDD491A867947AB220F70AEA603CD745A9666DB5C655C89D9A83F964C62CB327190A36BF6868A647F3466F2567038CE1FB70923E224076602363775C85976DAEB960C7A359E418BEE14C19B165C1B90AA19436BA06E78068D4BCBA72EEA9D44A5AE1B550BFB5C1CB9FACBE936D7DB5A609E038FC4912F301AF9054F7D963332815C4A5A967A48703B3FF6B7975EFD9CFA8961605918B1F59735C124774624AB3C39E0A6D2E8B2984D5C50AE1BDAD878C5ED8D5368DEC0650FA03088B2F21EEDD081C78F0B0267D1010D2A9F3D5BB83820E79FD36DAE8CB3CD35DDB291E34D83CBD00ECCC91E54961A6AC92A67337B072EC876128E556E52370F34AB868263F34905AD611E6839449046A490676FA7406999B64E1A84B80F3D21DBB134A752A75A164EDC21C7270BAC83FF1F22EDC99141E7817C42C955EA1DA20DB62069CF04BD01949A031DED481C89D17046CCEAE651B200F7210268F0358E00352CE660F8B676282DED48C3AC724B7109C751598ADFC3F1E2B781A3A5659D6CA924341C1902369683F38126F2B70B5A4DC3868A6BE24F054928171DC059DB0ACC928B4729375AE4620EE6BC2E4E8F741D350E99D438974BC86CB7D223A077EFD27ABCF1D05F0E415E8B331B5B3DB370819900E70CEEDA14FD49920623ED36325D48696DA36478067B681A98B0D7C373F8EB1C37CDB49F19FA7599DBCFC14C75F70104AD31C13AEDC0EC5AF6B97221934B96B2B732E5D226FEB725E15F8D85DFB5B708F013795402A542A6945EB75DE780EB54DAF1675E1DFECEC71A1F5A46AD77131443430FB5A8EFA774C7BE899EA9E9D53AAD0FBCBA5BFFD0CA6F7D77E4CD7F4ABCFE958DF722ACC722B1F3D499BD54771AC6D23AFEF2149BEB0B0D41028D58C5D05B9B4D578120258286CE445DA4D2A18F55D0FA6A1D54C5D78FAB47D362265D75B403E417EEDE7AB24E3EB9DF5CD21B83397A99D014C95127EBE875BF8CCBA946D2C06BA6034FC4435A0FCF91A0E6EA9C6288391AA76E111DB72BAAA109BF600057BB48A3B30D74DB25F0A90392CF8A87657FBDC961DC0ACE907E2D9EFE29DFCD9467F557F3D607FEFEE3DFFB26FA873B7082A884C6996CF23BFBB1B1776C4B21B32C8F2EE0732227486D1DE7EEDC2C745B77449908B84242D5D7EDE142D57BC21172359CDF7C16A13D1E0B2DE29423C08DE4326B253B46A5046DBEC7193E8946B230A457F13E55A9B79BB751CB5E8DD3B23E94344D21CF929F1BBFE6E6FD1BE669BFB5A0A986D7C39DAF09F4EDAFC6F9F6DD2B359EFF9B7FD1DFF36BDBAE9B3F8C7B7FE2D87BE7B002AE5B0A461C1E0E1921E691283207269BDA9D7911AF5AE0D7F7C2B00363E9CD7C29932F89061C35FEC8FCE5B653BB7E79916BDFE578ED1B12AFA8534DE7B25BAF1CA52A83A07AA42C8AE5D0321CA9777BC429E84DFA241A0E870EC6A96CB754CFD138E9356C595A4DEF481AE256482DA1BDAD3B4EEB9DCB61865AB2CDB655D1D071DAD7C8695DA6B1011F7AA85EF14CEB834CD8B6BECEADA06EF0C212418E3169D65A88DDE8BE659A0787C7B9D5CA588A4F9A53FDBCE61D18D5D1ABA1D8DB390A111434B1E70D1D19B1B94F68A2E0D574CDA80E12C858A381E4D1D474C3D1AC2E3DBFCBF7D36E0D02C975BEFC53DDA5408D8535048C10AEC4BA5AEEE068172E07275E6A6BD661BC1FF85E1A463AC492C449B59B55C38B7FD9154B6F3986E6F8413444D56B1D04D5A23BF806CDC4BE3549F0F899EE60D7FDA3F5AFEEC2F8816AE914F046208B71406EC9B108E3EE239840BAA5EF1E2C60140120103962809AF5C138CD2A7635F4D4573E97BFF2AD96AB4F1328098F0C28091C822F0727F08652D5C4B9488E1C0A6B8D482E39AEE540C94395B6995D8D0BF0744547C2BA8CB6E122E06345A1026A9DEF8DC40403B5146BE990AF8D7701DF9D1176A2C539D4E1588EDECA16CB8828B574E2879F28654BB9CCA0A34366A867FCE6DE8B5AC5098FDF26F5343836E9D5642988CD693EDA7EC95822F7C676D4462B047FFB98F0B707804FED13A962BA5CD82A5DBDBA827C7D258EAF7DB57419949FB9F2D917EE73797BCE8EB91A0578DB83E2B3CD380FF9AD9916B2550359BCB9BF032775BB78683B5A63158D5E9382806765A0810B748F1F1E2320D34DDCB7562579CEFF3F8B0D6344CBE942666BB3502A73DE79AED40A907FB11F6544F05B3AD762D6A704C43909712B59486E644A68F9CA1BC9AD20E029FFEE88E887269FBD992C6E650A6501A3188556DF030B3E55DA51D84D851608C21DF8093404EF944FBF7C265D42231F8439EB7130E2D214D1EFD0F419397D23F91C82D8CE87300ADC8CB2B8937DCD0ACD3D2DE7A2D424CBC5CDED423A33F87EF80B5091DB8C48848AE942760B954F08A1BE8EECCD4272CF762CB5E278C6354C790939B7994EE64298CEF8FC9033C9130EB3849C57C8883D9653DBF9D53080F33E8042C86388DC5A29BB9129EF6CE643204EFB2048D9ECA0C319C9D56B9B858D24FE49A64B9B85E78310FABA8B1CF05AF00130047DD6E5A25CB258440A2557338520EC399F13B828E74C7A3B0B81B820416C1772E5F52451A81085E53C8744A8E2400AFAA88D7595486545E175C6DD753697E10790BB1546ACEFDBBCD8B46E687428C64117C2DE716436B672A8F88B41B867FCE3489B861121399345815826B71A9A19084C91F10ECD19C57D94C21BE8ABE4A467BFC65DEB48D9BB952D94B693B9ECCE31A0A2FBA75B4E9B1224BAA09E21EFE099FC662183DAF3996C3ED9AD4F7A6325EE7CE07F37C834B5A9B4C59F31DC0BE9D0E5CB7C3E7949BD92D7638F3BE2CFDBE83ACD1F44F092ABE8621B4B2F9B6603AFB86EB513281F76BC780E3F8075D5AEA3863F31F6A53FFDECD917BE380C436B306120EF0A5EA34C843A9AC3BA69543BAD1F795A044AEF24F0375F9470F85F07D8552A7C1FB15F59E2C51F505BE7187E29BF4F046EF781C8C6C371AE3A9FEB0635C24FF1FC31F2E138F72300E0E94FC2AFFEFB1B8F7FDAF53F868E11A7F1C104E23411610A74041CF3B6D62C6A747773E8F5632E100E4DD75552D1E80C2111F84024C2EC6E6F360E0F775CB63592BDF92BF3357471E6F7F6CA073A392FF37869B1B479E4A67709FBCD268624771197A1C1E32FC384D0F5DF79DF2F7DF8D3BFFD16DB7EE06DB36FFD79FE3B48B3C5389A79F7A3BDAFFDF8FB3FF6C81FACBA14A35CA5BE5628403117C88017F3144CA2EDA9B40D1EB04BC194DEAC583CE847DE57AF67978271E45DCD1169333381B983BF28A4FD496EB284D6ED214E7B787801E747D1AF0B6127571C20089B0D4E936B5EF4607956A0DB8DBCF78821219F127D5DBDA93BBA6A44FBBA41C77682D32EB8A3698F9A833F4517DC297227C70F3ADEF92665540CF444D275581181A4D1E2F34534673C2C151FA9EB398AA8B85F9EFC9496A7DC5984723F4F31C91E57716165919B49342499727E7B2385166AA0685988A6519B14A68F6A6F08D99F73C948958B2D3A50764F00E9A1E3223D95DECC6D6FE43DB409E003480CDA4BD4BA7CC005BAA1920BEDAD3B7CBC757BF01E197CFE558EB7E25D06C89327700F1E45A6156F95133F2496A3C7C57282B3848FE3523C8E633F248E896353527280C0D2658051D12AF045667E2A9699FBC4C622A56D3B97A3F227077B34EDCA758B80389E4357BC984E6E65067C0908E7B445404CA0C92A65F10AE51AD80B9EAFE627AF0C1AD428A1D7D015B49BEB9FF231B5679AB71BAA753BA71D6846AAFBE1330527EBB80DCBD09BB74BAAB5AF396848FD166E48E7D0908AD766CA80755F6D7370466D56EAA645EAB9C774443DBBA2CD980DAFCE0D0775BD2BCA90DEE5E191E40BF52C72CE1B40D9953DA1F4D940B71F158F4D4058EF9945515F0F9697A0D183CE059F6EE8EB3B7AAB0FC2916918E8B1F0C4199E51D836780A2BB918FEE1480399700F47D6C75AAA8378BA01A953B2DAB54027C6C875F1594F72D183FD382D07A37BA62513442FE56052D4D281741A99C09D0A0E3B156C0B0E9E121D5B6AB58A3A209C2220550FF9F735B3E9043C075E2FE23D8D14D00BB24EC96278150B00E099DF9412D3F5784FFB95889D8FE8F2D8F5DD397A144AE135481E5142B7B33852AFC8FA946E6F556B99C69E56AD6AEEA0935C8EF4DB5AA98E22B15F97CD0A351F1A5AB524AF049428A4DBDBCD2A6715CDE302ADE6846877D26F09526E929A25DD2419C6084ECAECC4E719E7C4404281E6C87C7AB9E04CA069709F39E74F925EDF28E9232F1503993788EB385D83F62DB555C76BF9000F587C28CFAD4282372BA6044590A9692DCC2022B13E44C6994053885D797B989009CB14E643CE9D72EB921CCB03B8C06628D99A72FB43E84C7B0D416426B0358CCAA95042478073CFF674787CFB34374A0B3239F96D5E6AB29BEB34E1E53AB15D91EB44F56B6EE1097141FCCDD16FDFFEE0FDFFF3AFDF7B7EF20BAFBF6BFDEF085C4B44FA2757B517D55BEDF9A2DAB4E949F6A4EF2F7099FBD237FEF253FFFCD47F7F7D08D89A9B296205A31AF936CA8CF5E1D73E7A69F217BFFAD3FC1699FC134A8FA6956F924791C9973205AAA55BE2F771939A258BFB7A6DB25BDBB89C281B4909F6CFF323E894F16C7670F00DAEE93A14CC56B1D38ACF804D0546DF384280A94923B663668FC80CB7BC9E68A6E56DDE985E5E1AA516D9DFE1C41C344518514FB954F2290AC7F80A4810CD9FCB7C7545F8EC21A82B833EF904E18CF6B756E3B44650C2788397994982E8350C7EBFCDB9535CE92618FC031FDA8B8AE78347750CCED80A8C966FC35218D94BDEAF5AEB186129246B4EF052F0B8088CDD0B5DDC31223B06F72570C58264C081961CDC1B46C825CEB883C0154AE238AF87DCB2F8A027D0E2D6285B9A0E398BD6996B5C121F6E9A432D5DC69355C85AFB4DAE87820C71DA337701B6E86F5503762FA436B89887D0506CB15EB226BEE5A1B629D996A2AC1DD74F521C75AF884AC06355521BB28D2BFDF98E6B0E90DDE4AB6E9656D29DC347D75BFE4FE0C317105663DDBB558E77DF2A87DD27F356F0C7FB7AE651260D8F3650F65EEF873851D7B1FC26C5EA33C5B8B4B3AA66A3F95C723F1B4AFB5F4EFA2C318C2E64C5326D1B491518404B1DE2D1CAAFDE7A7BCE06BEFDEAEA652296CF4086D76778787D868A9D233BDA160CEDA65EFBF6B9EF8E264A5F95B1D7D5FFFCFC0FDEF823EC5E197A3F837BFD37F8F2CAE32BEF58B9F6E4B5C7AEAE5C5F293FF9C4E3EFB87E758C129F9722D9CEFDC8D35E7E86E75AC92C49D31A836106E745029670ACC50091BA300627185C71C3ACA6558EC180C1392F7BA91C7A7B64310251D30D83A75619867F676330E583DBCCAE86C0871E9519DCDDB370E09D88C1859E6EEF4992C19CDF1978E162704FCFA4D0A35C1454EF0D178FABA733FC3CCFE062CF08FF499BF1CF0A7A7AE9659EC1A5DEAEC02B2683FB7BFA239E28A3681678D70E6D3FEA918CC18C3F40BCF231983FE2808A3EF2BD74093FF04691D57BAB8F3C49F73533B47CF4133683719EE727D86969509E14795AC88F62B2CCB261A088BA4831F2BA6552108349399C926CBC5A5AD4DCB12E28D9CB752983336E6A5B39104C663169E30184BDD71C869709175428C48B6CEF750463A80C66C36BCBD6B96E30B27DE67E9B6E43151EEBCF366B266A8C9C69EDA3CEA96A75B56D2F4BED626935F9A8BCEC8D45F4C6CA6584B09166C076F074F0F054DE8DC29E0BEAA50DB703E74C96CB8EDE40374D6DB470DE4D068B819672F9B16BEF283735BC44ADAB2DF4D67C5575EDC91506A34FD193C9BB190C2F2CDE52E00978720246E03A8353F7EFA332570DA3A83B1AFF6C0DD5D6C2E26EF476DA4D5A7119B5A361E3F86577E20D05DE09372660089E62DC593996D21B03446CA5DF8222D5833E26A411C9AADAE2D39E663051516DA76492E964F0E442205A22D4F98DE0A32427E562EF180552909E8024AC2A9080F171DCC79A0213A2B4AEC0A428DD544011A59C0253A29457605A94B61438294A05050D1C2F951438254A48F4D3A2F49C02B3A2B4A3C019517A9F0273A2F4A30A9C15A51714BCDCF2D29E02E745A9AAC05DA25453E08228D515B8284A2F2A70B728190A5C12A526E3CFD7030BFC18B414B807EEA5B99602F3A2E428709F281D287059943A0A5EC579E96505EE17A5F72BF08028BDA2C083A2F401C6E3D1C791E031F8A0020FC1024DFF09051645E9C30A5C11A59F54E06151FA29051E11A58FA010132F17B9646EF82275D7C2E21142A5C0CFC2CF11D3FEBC022BF01841F90506234E5DC769F7E5620C3FA94664DFBA5995117F06D71722FEB9891E6E8B60C061F287183C7A2CA11B8357197F4C36D26D8BBEC6635058C81DE5D7442032106A0AFC0A7C7C127E193EC1C4CB2A8BFA4735B04FB5F6ED709F84C060BAD2B6F1B28DD7876A16D510AA8885AEACD6FED5C55B4420F4F49E3ED6FE8ED8CDAFD36E7E83200ED12F7406875A0DD693688BCD356C1E3AC05F13A43DB9BEA22D76232FD01B615DB96709F85D240B9D4F17671C0FE1D7E1F708E1DFA735D035A5EC8704FC21D54659579E61023E43ED632C2A7128017F4C9D09D69BA196803FA3AE71164A774AC09BD43AC1A2F3B312F017D43DC97A93F912F005EA52D811599A09F86BEA9F6211798D09F822F54DB3EE9CD104BC451D2759641A5B02FE817A6758BF0CB5047C89069D6251498D09F832759E66D1F95109F82A75CF3237BD2B01FF460D67D851D9A409F81A0D98634764F525E03FA8FF2CEB4DCF4CC0D7A9EB1CEB4D5F4BC07F51D7797654C66202BE2519567C6C40A3EFA25F1798972B9D80EF50CB45E6A68A27E07BD47037F3D2BB13F07D6AB9C464C234F20DD5EF6132413BC186A97E2FF312BC136C945AE699BC1D27183254821041BDCED1F8BFDAAE3CB08AEADC9FEFDC9BDC9B9B2124F7262143D809106E12910022C8521A5143E022B2848008311B91259005444B2D2EF878D656C5BA55AD5A352EA82C4AC2E2F6F481B56A796A7968A90B4FA94FADA2555F7D6EFD7D73CEDCCC84016E6CFDE7FCEE7CDF37DFD9BEF33BCBCC24FDC9F5A24090BAB17400757A121FA474960F24F793EE2045589C47CE47CB41CA66E120723FCB0D9289BCF5633B5056567EA9C7E0C26AF8788C6BAF37F8C14D636D6555CDA901427BA4D4374D6FACAF2B65C2C5726A9E417DA95F88FA10AA17503AC8062A599E2186899331B5D0604C45D5F54D3CBF5563925A5E5DD9581D9F8B0CCA57F64389876801D70937641CF5F42840272117EB087A7A2D62C05D2FFB11934127D3F0100DA362F09A3543F057CC8D2DFC468D41232D46A1519CC9104EF23919CA4994132BF7424E8A384186833DE8CB9BAC6822BBC6C22CBDF31C11A01FB3B7619C60D7ECE72FD782740689290971636205A091741617A0947319CE493127233819C98955E9533819CDC9A99C8CE1642C274CFAF5D5585E97923EE747C4DA51347D6935DB8CE3643C27133899C8C98F3899C48955C7124E4EE76432276770722627677162956D0A27659C4CE5641A27314EA67372362733D0FF76DE939B1091FC870D58710E27333999C5C96C4EAC29A89C93B99C5470328F93F99C9CCBC9024ECEE36421278B38A9E4E47C4EAA38B1DCD77052CB099634615E0DE5216E9BF3EA97AF6A5852933782C490E38D9A335577972E5BB13440FFE60A047DC242FF8ECD4BFEFCA3BBCFE3FD406BB7423F0FD106BA9AF86F2B35AD68ACA9ACE6776E498C3F91135B543AD4436AD02FE91AACFAE95AE237CAB872D3ACF5DCF4E57A4185FD7EB53338D5B231A125882EBE47B69E61BB91AEE792FC0AC3B5A3C19B6A9AADD64E6C55390713977AD72D5E206B02B0568527DE1675A556E814BA996EE112FFDA55E23A55E2C11E25F6A4DFCC3ACF02CFF8E71ADDB3857F43777079EFC4B450A73FE9C34A227FE8513919F45BBA3B4457D13DC42F67D6373BF612059E3C788CDD05B5D27D9CE5FD86281227F114F02089FC448F0902F410E2DD2EAA3A64187F74BB0C4DFCDCC1A0476873881EA62DCAB163F2D9867D10148F42D154E3AC70CFFC63D48EC3CD700E1AECA3F8FE2C4FF300ED3C21CF7765BF64503BED4EA55DF438F81955B1FFFAC60917DAC78B9027E92976F9345C36395D8EF9BEE5C62879869E659FFF699DDFE435ADAE5CA13640697955EE3397DE8EFBD17BC36636D436C7DFD941E19EA3DF71043D8FF01D57B5D43ACC31682F1FE3487AD110A3C44856FF2109DBA4294B8A92B619628412BD02066E6A4155F28AF33AD64313F213FFEB509E23379143858E1377FA6F8372697F880ED06BF1F20CCF6B6E508D0722700DC4F8A632685B18F4673AC883F20D123DD4ED23F21A6BEA403A358D2A0079CFEE8E56D401CBAC0677D076EF74974187E8ED10766FFF835EB10BB6BCA1B9BE16BD92AC7E1874182D8DDCFF8229D96D3389777729F1DF06BDAF2C3FC06AAF73B3C727718356A8C6F888445FBB368839AFCFCA488C3A415F79DC64D5B4C7313C1AF4297DC235FE1B0F775DC67A2B2C97776D5FFECF7D34CC65F473BE06FD1F7DC1CDF1771E7ABA4058AE9E782289078A0FE6067D4D5FB19B6FC05BB61B90C3192DCB2D2A9CD9B2820B48A2C48B463DAB52AB6F6D1AD6C909B20C1FEDDA9024B15F3E2025A60E3B4EAA96362CAFE91CE1F1719464A90D992C93103632C093B80E083BF312EB04618C475324B6BAE0237B872B43A6CA10BA5F627B1EE91CA258E31A546105A7EC1E0F8FE179AB2B393CFC0C860C5B212EB1154ECA9F520A9A936199C5126C657BDB1E175B9F7BE93EB74380C469C75FCC4CBEB0AAC6FA48D5BB9BB33CBD1AB2A734B9C4B90E6EE998E4E321B5AC094C736E42ABA9AEEE3F3A02119918B29FECCBE5E9DF69D2EB42761ECED9ED40769BE7A8666DFD72EB0B2F70A5FDD390435407E58311ED60AAACAEB683B5646943534B634D9C357F947FDCC37E65A76FF25C0C82EA8EE9DE9085B280A3ADE87B85B0A3EAC3B8EABC71CC9FA242AE98EB38E2FB9CBA3ABC8E62AFA738A8BF66654BE5D2263E31F0F0310F53823230E45839860B709AA3331657362D2E41335AE702D842066D812127CA096C8D8D71765E5523FF8D3AD7439785C3792FEFB9FF09CAD4A07824283E0B522A1C23AE83F22CCC63E3F2AA3A36F5791382728A5ADA5AAFFD04E554B532C280D6026C6632BDB226714AFE315691C73A79B6EEB30EAD171BF2C7B2048B1039934471D79D04E4EC4E1B1CC5C7C7DA09CAF2909C23E792C83DCE722920E719F22C10AA9C2FCF35E4D9720617F0BC63F4A9E57611DB5682D02A55939CDCE50760B24A56A7A06CE8FCE42AAE0346E9A0134C5FF6B3BDEE9D4401594F2250AB5E5237E412B938242F909804FA7418C61A2CD3D36B6ACFE03FA23CB9B111EB2CB99CD4A7B70E67B186666561536B40AE2011B236004D4D9575C8A0116B2BB952768E7A57C3B78464835C85E1A7023C6C7D426D3FB099C5091FA163E257EF426A49487DFBCD5FE78AFE420ABF40F3887CFC0A8A143E81E7A76C16A66A343476D398A6B1BBC6748D191AC31A231A33356669CCD6D843638E4653634F8DB91A7B69ECADB18FE86B613F8DFD350ED03850639EC6411A076B1CC22FAE02876A8C6A2CD058C8981EE22D2270039F16022FE50752C0145EC65B38CAC2106F04F8540BED788A182D485E08275F59F90AF9E7689B18D32EC64FA56985E189ED62F253626274E163E2CCC2728A153925A545E534D66FFA9DB232D30F619299E4144E3393204C36939DC2E96632840133E014CE30031006CDA05338D30C429862A63885B3CD14084366C8292C374310A69AA94E6185990AA1611A4EE17CD380B09BD9CD295C60768330CD4C730A179A69107637BB3B8595667708D3CD74A7B0CA4C8730C3CC700A6BCC0C08C366D829AC33C31046CC8853586F4620CC34339DC22566268459669653B8CCCC8230DBCC760A1BCC6C087B983DC22B2DE14A1636993D20CC31739CC216330742D3349DC2D5A609614FB3A753B8C6EC0961AE99EB145E6CE642D8CBECE514AE357B41D8DBECED145E62F686B08FD9C7295C67F681B0AFD9377C9925BC8C8557987D21EC67F6730AAF34FB41D8DFECEF146E30FB4338C01CE0145E650E8070A039D029BCDA1C581EDD2EAE1D9B67E6F1F54633AF7C8BE03FC4C8DF571B087EBF3C20AE17BF6299B841DCA80682980182E18F9322180408F89B8AA305853BC4ED7EB19936F368B36EEF673DC8C8D0B7F3AFDF883BAC97CA33C49DE22E585D84DFC99C09D883C46FC5DDDAFD5ADC99C4C7E2706F8D9E7B90C10E719F0FD1EB0FDF5F1C2D8C5F3C501C2D52175BE21947ADB215C6332E140F8A4D56C685E221FC92D6AF87F1CB671521CDCAAE14143705645206CB47C4665514DF60E872E0F82E1405837D4B714178DB0ED1E613765CC58AAC915F545E186E8772A74BC985B7680043BE30BC0BFAC7DD7AB080A28424E89F80FE29B71E84A0D82119FAA7A17FC6AD073728A20840FF2CF47BDC7AD084E28C20F47BA1FF9D5B0FC650F49102FDF3D0BFE0D6833C149384A07F11FA3FB8F5E011452AA9D0EF83FE65B71E94A2F8C580FE15E8FFE8D6835D14D574837E3FF407DC7A108D629D34E85F83FE4F6E3D3847115077E80F42FF865B0FFA515C940EFD9BD0BFEDD68389142D65407F08FA77DC7A909262A830F4EF42FF17B71EFCA4C82A02FD7BD0BFEFD683AA146F6542FF01F47F75EBC15A8AC2B2A0FF08FA236E3D084CB15936F49F40FF37B71E5CA688AD8723FEB339C2E59D76FCE39715FF56ACCFC184ED17B330BDCE1693705529CAC53A311703B2423C2DE689B7C57CC2E68DFA8B0534499C47956221AD138BE84E51494F8BF3E96D51854D77B5EC2F6AE424512B2B459D5C271663CC7C668F19AAC0984905A6A831F379C7B0D01CA7C64C138F992FA0FCBB4B698F99166BCC7C09FD576EBD1E33ABAD31F335F4DFBAF57ACCACB1C6CC77C5051122B7811E3417F3A08990640BBFDB420F9BB53C6C2294C41601B7851E3897F0C08950902D426E0B3D74D661E874EA1A0ADA5D835F0FC5BB6600D66A7EB1125DD388AE6942D734A36B5AC094ABC4B362B5382C2E1444A976338B2368667EC97793D5CC1132B81069AA109AE355435F51C465ECCEEA0C97DA6EEA2BB9A92314668B4CB7856EEC0DDCD811CA628B1E6E0BDDDC5771734728872D7ABA2D747B5F8DF6EED412084C9BA41FB40859B54436E8D88FBA0F14978AC9E23251272E171BC415A87D2EF552B5F76F86454F10F4135C7BACBACADB69C0CD22A79D06DD2CC25B69C82DC268A7E82D22B995F6E5FAEE10017FABF0FB6E1619112AE47AC750E756DA9AEBEF50F545F98B8A2334CCDF4E2376D0293EC1D58FA1EAADB42137C96D38DA6D885688A1055A69516EB2DBF054B7211A23868668A5D1B901B7E11836F4C50D118331C45F2BA5E506DD8663DD1E118A318461AB783737BD53DEA7B95D22246308C756B12B37BD53E6E3DC3EC1EB31707AABB83637BD53EEE3DD3EC1F031B07BABA8CB4D4FE9B01C04CB09C5D147C5BA1D3409BB062AE160A01266FC18D8BE558CCE4D0F75D80F85FDE9F05C029CCCF7ADD1F79DB983A6F8054F04314C02ADF4E2519D89392006FEF7E8CC3277A53019C430117874E654B72166851866048FCE9CE636C4F410C3D4E0D19931772B619E88618EF0E8CCE96E8F983062982CBC3AF36CB74B4C1D314C1B5E9D39C3ED1393480C138857679EE3F689E92486A9C4AB33677A752656CD31AC94BD3A7396EECCD91E9D8975750C6BE9A2A3568BBEDD361FF876D31C2AE75DAC6F3BCD0507AAA5DA4EECDEFCE21760866BB07FBB4E4C807D1916A675E226B0E52D90DE2A6E13B78B1D5869EEC15D87B0A6FC58DC0BD6BC8F4CF1008D129BA8443C4C0BB1525D26B6D206F128DD28B6638268A7BD62271D11D7D037E23A992AAE9739E2063952DC8469EE16B940DC2A2F10B7CBF5E20EB951DC251F1177CBDDE25E2C8EEF9387C5033E2936F9D2C5C3BE42B1D937566CF5CD118FFAAAC576DF7AD1EEBB5EECF4ED148F83BD2A7E68F69A97287BCD4F94BDCE4D94BD1624CA5EE725CC5E0B1366AF4509B35765C2EC757E17D9AB4A077CF5BF8ABD6A1265AFDA44D9AB2E51F65A9C287BD527CC5E1724CC5E4B1266AFA509B3D7B22EB2D772DD990D3F007BED017B3D07F67A1EECF502D8EB25B0D73EB0D7CB60AF57C15EFBC15E0760F53AD8EB20D8EB0DB0D75B60AF4360AF77C05E87C15EEF81BDDE077B7D08F6FA08EC7504ECF529D8EB39B0D7F360AF17C05E2F81BDF681BD5E067BBD0AF6DA0FF63A00F67A1DEC7510ECF506D8EB2DB0D721B0D73B60AFC360AFF7C05EEF83BD3E047B7D04F63A02F6FA14ECF539D86BC50FCD5E2B1365AFC644D9AB2951F66A4E94BD5A1266AF5509B3D7EA84D9EBC284D96B4D17D9EB221DF017FFABD8EB2789B2D7DA44D9EBA789B2D72589B2D7CF1266AF7509B3D7A509B3D76509B3D7E55D64AF2B7467AEFF01D8EB5B7E5F589C4C524C20BF28A36451875D6C3385B07232C46D94267650BAD84361F055A6F898B2299572C8A49E348A7A5109F5A185D48F96D100DA407974230DA61D944F7B294A4748D03724652AF9650E25CB9114949328241790212FA034B99ED2E5460ACB472853EEA66C798072E461EAE993D4CB974E7D7C85D4CF379606F8E6509EAF9A06FBD653BEEF7A8AFA7652513AD195F1C3CBB522C53A202CE3C3CBB08CEEA20D15FEC7E9AA0A5FC1CCA4C7A9A4C287C89E59E1E7B48D7E116F711F6C945DE14CFFEE5085CF37BD9DAE6BA71B545392E3D0B334BEAD2D8D1F7A96AA434F14E546BA491765990859079DA771512C6AEC4279DAE956F0E8964E598F8D673D369EF558AB1739EBDBE8769DF502B402DB8F620EEF52AE7739EBABB6F123E2998E5099A6F322FD973AAB11B061AB21D1ED746F58DE2382D1767A60F356DA34B560AFE8CED276DA5A31751B6D2AD84E8FB17381B26E8B3B1888DBB91E3D10CE1B2BF660022A8F566C17D73E2636B2C4BE613BB5E91BC23AC7E468016CB6687D7B3C044C040057BE1BF2DEC167D7F4845FD86E9E8CB7510E8CD88DA1CC76D07FF8E256CFC43BF168677BFC42E599C2AF8E69B32C54812BC1A76265B48D7E6F998820BD103719603DB943A3B6D34B2913FB6CA37D7DE666D27F4DEAB78D5EED37D7AEC61F69FFD1D56CA3D7ED92FD890E6A7D86CE3229DA466FDAEAB7E86DAFDBDFB1DDBF1B2F8EF3F6F76CF5FF7AAA3FB4D57FF52EDCC776EE47E8132FFD67F6FD9FD3175ACF87403C364268D222D3DF465FDA36FF4F5F79F9F8D6CEE33B3E633B4A2F7D5A2FFD32E9E82AC8A0AD4E9121AD8EA05FB9670328429BEC661BA479D651A6DBFA0CAF2692995B6CB5CC8AFB578F5102D1BEBDDB640FDB20479A9E05E86567D09BEEF6E8837B6D751FD9F7186D280774D80CD43699BA1041B66993836C8BC19EB5186A17322A0B3C0B79D296780EC33C0D86C79B4916C79B51192447FBB6C9911D0E46793A186D3B38558EF1EA87719BB47EBC9CE05183494A1DE2172AF4439E61F00EB56F60347C53449E3E331ABE27222703EE8FC833000F44E499802D11590A6857B04BC1130A9E56F0AC82BD0A9E57F0A2827D0A5E51B05FC16B0A0E2A7853C12105EF2A784FC1070A3E52F08982CF157CA1E04B055F2BF8CE8208498D491A831A0D8DDD3586356669CCD1581491658CA3359EAA718CC6B11A4FD3384EE3788D1322721AE3E9FA7AB2BE2ED3D753354ED318D3385DE3D91A67683C47E34CED6796BE9EADAFE7E9EBF91ACFD5B840E3791A176A5CA4B152E3F911399DB14A5F57EBEB1A7D5DABB14EE3628DF51A2FD0B844E3528DCBB49FE5FABA415FAFD4D78D1A9B34366B6CD1B84AE36A8D176A5CA3EB7D91BEBE585FFF445FAFD5F8538D9768FC99C6751A2FD57899C6CBB59F2BF4F57ABEDEA207CFD972467CD2F25B632B3B22CFC1BAA12CDA2E67ED92732AB0A66893156AAC86F8B3756D3F520D3691BF957EBF476420DD2E173C851BCAF68A70BB5C58B64BCEAFC0ADE7638A8C3E2A6B9587A0AC938BB587C17A5A0E23AB659BA7EE920D1505DB65739B5CFDA41AE52249AE143C075C2CF8D58F40B3C8F2FCB4F858FF0221FE274D4EB2FE8F52C3AA35FACFB034FE03504B0304140000000800306695459FCC654CBA0300000E0B000033001C004253435F5250543030312F2E73766E2F746578742D626173652F4253435F5250543030312E6A72786D6C2E73766E2D6261736555540900035C5196545C51965475780B000104000000000400000000BD56DB6EDB38107D6EBE824BF461775159BE48AE1CD82ED2D84103A449102BE902451150126531A04495A47C49917FDFA1240B76E30429B69B17D943CE9CB91D8D66F8619572B4A05231918D70A7D5C68866A18858361FE16BFFC4F2F087F1C1F08EA89CCA2B9A0BA91198646A8413ADF343DBAEAE6479A55A4A1432A4B19073DACAA8DEBDC595E9E14AB1C67CB95CB696BD1618D8DD76BB63FFF3F96C1626342516CB94265948C14AB143551E9E8990E832D45FF78E5E64B252D18E460B0E30CA484A47F8E3ECF8F6EAD2873031E2249B17640EA77329C4628D510ED21716E964843DA75BC99F289B277A84DD818B91908C66BA8EFF8C64910A490EE985821769B66DCA69AC3F133967A0081D9106644BD622DF9202A1B548B70E8A8245E0D2EDBADD5EA767454EE8580E8DDF5B41DB0DAD41DFEB1387049103698C0FDE0C7329205BBDAE936475DEF742A4182D082F68450CFB19E555A3F9BCDEFA911E91A0A0A9AC152F4E2750104E1410EC8E2C48CBD4B935D3120869A27D338C684C0AAE6F0CCC74954BAA0C77C7C33FBE1E4F8EFCA3AF187FFB361EDA4FA8814BBBF169A4EF0595EB0ABF84DFC028CA69A8D1DF289622454170BB6006002D132A298230476F2F7FC0CF037833A0BB38C398511EBD38A7527B42552859AE77B2297379746D1C96873FBBBA399DDDBE9E37FFD43F9BBE96B3E38B737F7AEEEF717711DC41AB7EB7BBEBD9F4EAF54A698C7E2AA5FACE5B3E4B294CC134FFCDEEAE5F37BBEBFF3F3BCD34A7254C00931525F5E07560EEAA9C33EDAF730804B2A33A4C4A7F604357FAA406307235A4A69CA630A7D16A84BB3D8CD630FD60A82EABF1FCDE1BE006BCE76E866D4CDC413B08032B707A91E57851CF228376DF8A7BFDC0EB769DD8A19D72E06DDC6E9C98FF479CCD3323010DE04965159EA9880015F3382FCB38A177E4A6403392C1A754B17B38EA3A1831F551700842CB02BE2679144F9B2FF86904804CAFAD4F46ED12AED2804611DD686F42B2B762DA8AB22CCEBE21FBF6E447F9F23F949DD9A77AD0C036151EDAA63565DB36DD8269AE09E38FFBE6BAED671A178A341719C4BA1BF253FD7BA27D6EB75F560508AFBF24349B94B15CC0361473B1549B82561D6E3BA11B0DDA1E74388A2D27723B96D71E0C2C37E87B9EDBEB10127B4D3993F030D1B05855EB4E12BE705D31364D6AFF61EDD9C179F9DAB36356ED3DB0A1705A75E098B31C6A074BCC3D1092F092B5B0C7C0AA82CD06A959D81CFA226F385CD7E2188C4CC3F69029A34B548D9D3F8157F59C7F788770A1A1A67F95147B12A5E66F7D5FD36E1F3FB6D8B721DDD0DEDE6BC707FF02504B030414000000080030669545830E6B7E160100002602000017001C004253435F5250543030312F2E73766E2F656E747269657355540900035C5196545D51965475780B0001040000000004000000008D904D4EC3301046F71687E0024E66FC173B4BB80082AEBA41CE7842036E13C5152AB7276D910AA812BC952D7D9EEF8D118448C32C8C524694F75D5BD779A4983763D9D72FA52B54D338B34C23D5774FF7CF8F0F2B00BC9E140B0AD0485452C10A5CABA0455F350E4370EB734737E4BCE48E03CAC434C47C7B3CF361CFF32EE672BAED985391CBF837F10D43C4CA242D135323191165D0CECA1E12F7C8C1616FBFA220783BED3FC48DB84857AF71699C453F645E5C4CF8E18B2B30ADF1AD810A4EAC4544DD80B3CE42240FC40436A2C3CE538CCA68F5FB7168B5AA1AAF74C0F5B9E0B4EC261639CDE354C435B472E07F79CE876DFE4353EB8B26796B3ADD7747431D8C593E255A086403FAE4CD3F35AFA2BC8645EE13504B01021E030A0000000000867294450000000000000000000000000B0018000000000000001000ED41000000004253435F5250543030312F55540500031C15955475780B000104000000000400000000504B01021E03140000000800186C9545E444B36CB70300000E0B00001B0018000000000001000000A481450000004253435F5250543030312F4253435F5250543030312E6A72786D6C5554050003805B965475780B000104000000000400000000504B01021E03140000000800286C95458751AF1D942A0000607F00001C0018000000000000000000A481510400004253435F5250543030312F4253435F5250543030312E6A617370657255540500039B5B965475780B000104000000000400000000504B01021E030A000000000030669545000000000000000000000000100018000000000000001000ED413B2F00004253435F5250543030312F2E73766E2F55540500035C51965475780B000104000000000400000000504B01021E030A000000000030669545000000000000000000000000140018000000000000001000ED41852F00004253435F5250543030312F2E73766E2F746D702F55540500035C51965475780B000104000000000400000000504B01021E030A0000000000897294450000000000000000000000001A0018000000000000001000ED41D32F00004253435F5250543030312F2E73766E2F746D702F70726F70732F55540500032215955475780B000104000000000400000000504B01021E030A0000000000867294450000000000000000000000001E0018000000000000001000ED41273000004253435F5250543030312F2E73766E2F746D702F70726F702D626173652F55540500031C15955475780B000104000000000400000000504B01021E030A0000000000306695450000000000000000000000001E0018000000000000001000ED417F3000004253435F5250543030312F2E73766E2F746D702F746578742D626173652F55540500035C51965475780B000104000000000400000000504B01021E030A000000000089729445000000000000000000000000160018000000000000001000ED41D73000004253435F5250543030312F2E73766E2F70726F70732F55540500032215955475780B000104000000000400000000504B01021E030A0000000000897294450000000000000000000000001A0018000000000000001000ED41273100004253435F5250543030312F2E73766E2F70726F702D626173652F55540500032215955475780B000104000000000400000000504B01021E030A000000000089729445FF31268C350000003500000034001800000000000100010024817B3100004253435F5250543030312F2E73766E2F70726F702D626173652F4253435F5250543030312E6A61737065722E73766E2D6261736555540500032215955475780B000104000000000400000000504B01021E030A0000000000306695450000000000000000000000001A0018000000000000001000ED411E3200004253435F5250543030312F2E73766E2F746578742D626173652F55540500035C51965475780B000104000000000400000000504B01021E0314000000080030669545D86EF9ED942A0000607F0000340018000000000000000000A481723200004253435F5250543030312F2E73766E2F746578742D626173652F4253435F5250543030312E6A61737065722E73766E2D6261736555540500035C51965475780B000104000000000400000000504B01021E03140000000800306695459FCC654CBA0300000E0B0000330018000000000001000000A481745D00004253435F5250543030312F2E73766E2F746578742D626173652F4253435F5250543030312E6A72786D6C2E73766E2D6261736555540500035C51965475780B000104000000000400000000504B01021E0314000000080030669545830E6B7E160100002602000017001800000000000100010024819B6100004253435F5250543030312F2E73766E2F656E747269657355540500035C51965475780B000104000000000400000000504B0506000000000F000F00D2050000026300000000,'vision jasper report!','admin','2014-12-20 14:25:10','admin','2014-12-21 13:39:48'),('9cddb65a-9f3b-4984-847d-c93baa1c357f','BSC_RPT002','BSC_RPT002.jasper','N',0x504B03040A0000000000A55995450000000000000000000000000B001C004253435F5250543030322F5554090003C53A9654A52F965475780B000104000000000400000000504B030414000000080089A996456CAD33C449060000121900001B001C004253435F5250543030322F4253435F5250543030322E6A72786D6C5554090003B11898547218985475780B000104000000000400000000DD59596FDB46107E6E7E05CBF6A9C54A7BF10A24178EADD446E303929C16280A638FA1CD94221592F2D122FFBDB3BA2CCB8E2DB5490AC406042D7776E7F8E6A43A3FDD8C72EF0AAA3A2B8BAECF5AD4F7A030A5CD8A8BAE7F367C4D62FFA79D179D77AA1E43D5877159351E1E29EAAE7FD934E397EDF66CAB9A6ED5ADBA9C5406D2B2BA805601CDFD5D7F76F4E54D9D2D8F5F5F5FB7AE450B0FB439A5ACFDDBD19B81B9849122595137AA3080A7EAEC653D7DF8A634AA998ABA3D776FA32337B5BD47D1C207BE57A81174FD5783BDF3FEE99052EE7BB92A2E26EA029F5E54657975EB7B635CFD9AD9E6B2EBC792CFD607905D5C365D3F4802DF2BAB0C8A662EFF1B55D8DAA831AA67CA7C322A568FE6903647AABAC8901011A9DC252BEBA61CAFAC74D934E568E5C16492D9AE6F5810089D4AA265C4888C2D9024090591423315C45A5A6BFC9D17DF74C65589DA36B77325B3B9DE7F95E5C8F7AE543E01E71832948C2EFF62BFFDC4D19BBB73117F92F2764949E774AA428206AA3961BF777AD21F9E1FEEA3997255A3DBBD5357AAE5ACDF1A3415BAA9D3E19B8E85544DF2E6ADBBAC7733AEA0761EBDD3F9F6F7BDFDDDE1EEEFBEFFC71F3B9DF647C890717BC9F931310667AFE692EC1FF6FFAB28ADF676C2BC9F40753BE33065B0B8A8861C4CE3FDE0A55539F2B43EAFAFCBE67C66D9F351DD78D7975081B734A1D7F5BE3FFD7BB9FC8042385EF7AFEFA419E476AEF6C9B3769F52EF436DAA6CDCDC5373AAE2836DC770FA709DD5A6407F32866F0F078727C7E7C3C3E19BDE97E279D2FFF9FC78F7E88BF1DB3B1BF4FA5FCEA4EED09A6EF5FBBC35CC4680A97C34FEC4ECCEBEAC76679F5FBB266B72985EA3B13C7897F3EA116252AFC779D60C6FC72E19351534E672CA0FCFC04DF37A7E815BCFE2BF97C3088B8D77D3F5051694DBAE2FF192EB598D89A2C45F5ECE97158302B36998580234A1440654100D1C08B789B58996C6A4E9344B2FD82E98B8EFBB797651B815BA017E423513CF59A44412F7713C35638AB93FCD6FEB0902E4D5D95FAE4A48DFCBEA57658E4234D5044BE2D8A6BD651B7268F1C2ACB925078EEC14B7461AAC8505F542A4F68A4C2B524E8DF35822FEFEF5DF8B80FC3005E731EA17CB9BEF8CBCB1CD59FC9CCD2DD52CA2912581B429912116E8386186043CD29CDA341689FC3C360FFF2F9BAF26DEADECDE69BBA89846CC22503AB3DEE900949DD6CAB5C011C1138153BB56CC0C91C3A3288E4AEB32F658BD77C6B9C3942F216511BB8354E077ADCC9F28505975FDEF5E4BF7BF8039480568AE1511A10D89949092C4A6821813C79A8542A626D90266D7B1A3EC2A5FD93ACAACCDE1631EB00FEFD4DB89375045ED1D9545B97403FE39DDE00EF7531C31C6D8AD6457502F319F83BC064427CF0A782EB0EE40588160696E6682D8D99B189662EFAB124D9412D80A0B1173C5B8E59198CBDE692FD96DC7F85E40B305E7348E250360842906449A98110458139A262AE29CC5118BB6E41C33BA89CE1A9288721D902854A8731A19E41C1A62B9B59A052030AFFC3B9D057F46692D421ED3943B55131C30C0B824E694A640394FB5B6C123ACB70D404EE57A0406C18A29E8531168A4A212274C12460287A02408890E6320144044829A9425D1571D818361BF77FCF3F060F05CF86D9D1743FA0016BE292C412842119B885815C618A95691C460251410498879100B095F352CBFF6767F39EE0D06BD4F8E4BC0C4035CD8A6B8680689A22906726C232229932489434C6BD4608293D652A9BE6A5C4E4EDD3C7A767C383CFCF4D08461B00E8DDC38932582A7D4D880D0804BCCB69611150983299F25290077F5E7AB866678D0EFED0E9F05E5A9CAC6A9D8A4A6860ADB337478A24DAA88B458D3922862180A20358D742A396C5B538364A36A2E5940B16623AA81C672452312ABC012C121D5218F02B635E7806DD43B296B4C60424DB061C08924A68C686E0551C094C2EEC939DF969CC3506EC2992AB435360C04E23471EE6C490CE8D8D867CBD8245245C9C3DE696528589B053A161A95E50FA782803E354FD7133D53E099AE68759C8EEFD491AEF6CDF48968222547773181E044629744626B12C280811081B6F1CA6CB7647BFAD137A08B707C48FAE8A4B5F6AA0F03E4A973F3D87B4833DF316551B8FEBD2C9E66B677727CDCDB1BE2843763FAE8B935953F72E3BD97AE1FBC1FBD9517F0E7AC357B4BEFDF57EDC104790FCE1567593847A7BDFADBC6CE8B7F00504B030414000000080090A996454ACC30AB2E3000000C9500001C001C004253435F5250543030322F4253435F5250543030322E6A61737065725554090003BF1898547518985475780B000104000000000400000000B43C0B941BD5756FB4FFAFD77F6383590C017F6077EDB58D290E45D26ABD325A6991B436ECBA11B3D2ECEE6049339E19D9BB2425E400819440CB2909D0C04948E0A41FD25308A4398443CB49524A4ADB400E6D4A9B40D39C969E10D2049F86727A92DEFBE6CDCC1BCD48333A141FD06ADEE7BEFBEEBDEFFEDE1D3DFE53D2A16B6467553246F4C5911B455D95344D5215CDD047A4EA925C95468ED2C62C6D24F4DF25DF8F90B614E92B2A15552E4B13A2211A6453EA46F1B4382A2BA3394993C5B27C93B85096AE4C91B56C585AAC48B9DAE2A2BC629075E6E0B2585D1ACD199A5C5D8281036CA0162F8BBA7E8ADC4C222B1AB9AC196E0BA20E086663F0A71EC1DD4972CE4C2E313B9129E412D96434553896C8E69299746136399124FD0B8A6128956951034049DC4BB956A9C6955AD5482226F89453C522A066F71E974BC6F21C199297AA8A26CD8830533464A53A47D6CBFA6459118D381D37A92886A4E1403D57AB54446D352D9D81E1D21C19B69B8ECBC632B64D496249D2A2D592356B50D6F3B201F432E724496F595A344C4463A44FD164A96AD075A14BA510E4A565C0BA071F288E31E800A21A190D4003FA1A0EB0B6DA6328AA056DF0CCB2544D2BC8C1FCAA2AA548EF82583CB9A401194A06B92805B41FD517475DB41F35693F8A64AF96806F6B4CEA4CC89A5444B40C32DE74A2012B8D666BCE8444B5560138FD458E7AC8FE76BBCD24126D9B27DD254057970CDD203BE703509C308722F492B428D6CA46CE582D4B06F950C0443A0CA67596244394CB0C9D01F329676DF4922028E64080B36E51D12AA23129160D455B75241C482E57E8AC9C6418648D79306A865C8673847877E321A90167D9F041986AA060B8E8D45711E52ADB6C305A0E55DAAB702A19E4CE2A150406718813B46362B90634DB17CCD68C338971958AA80B5726B4364741821C69654BED0D5E6AC69E43579A279D3AF20CA4E2E220A9B098DBA59B679109568F2155D4B268208CD12018A6BEC9B31900ACC3C053CB76B4DE7DB0D8AE0E04EFEAB86B1EDDD98A4AC8B7274DCD4604F34FDB51C2FD8BE4DC2DEACD1AD9DB4C6FE25223F547D09ABD8E10D0BB83288823287C23F59DAA01EA3F9F55C172EC09A99D515538BAB9ABB96EEE5CA61A8DAAC29C5A968D68B9AC9C914A40562A2848A3C48AAA49BA4E4FE1EE00563963D1CAA89A02830C59D2A745D8C965019367F8E130BF47478C90390619E2CC586C954AC1A0DDCD783E16CCF39C35C564B746C643523551962A70DC8E80BE561DEAC251EE2E2ECBE592067D168E54A3A4649DAA42899B67904B83C8C78D066104AEAFA7B2812047A29A26AE22DC954FBC72DE832F880FB7112149DA75F92609E5960C9C69C74F3D4020B94DE5507D14F3D20A67CC05D05406B4584EC1BE90B0100A43DF01767133E90335B8A0684C356D01C56A3E816147EF8211533C638CD21624A6E948C4F8496BF9363A103B7680F0991D3362A9048E8541D6730294AC1AD2928420BB1741AAD38E62A6CF39A0283E5FC89E91C20659CB01A01E084C5FBF0CAAFB26182296A365F05570F70CB12D3E5D4C4E0F06CBE9947B3253F09DB21E53CA25F766628A5296443C6FDDB29E8419721151B8184820EB33A5C54465412A95A4126B5C03071DDCC093527E19A46C699935F76333A8EA529EF11EDAFA647DB60A642D035AAC893A482E06AC715A78F2F76133233EA36517C289292BC14633650EBC12C1C057E61CB20587B8A6F0D632E54CB28809E6E8644D658CEF525DB88283A7894B9AA82E1B645790DAB28622BE2A10BC5A542C5011B369D22D65A697E822E310D7C4D3B19FB6BB09D9AD29A6DD677307AC67468ED1103EA1E2F21CD05175A1336837F0C8F442AB1B95B5A7516117BDE2BFC9D3C190DB1F8CDC317EAAA5A8475A53D48E0EDA16D2026E86D382262F599D94351D2C1F1C2BB4A5736423EB013F440207A44477A2CF91ADAC1D8DE404F55633B0EB4530A03A9D94952ACA6909250F47C4E0B49E8C917E55D165243D5A2108327443938CE2323E2549C7198C2992445881FF57812F182314390E6CE05D6BB097A76560511051C10BF599556F9D7085736149F09E257EC9B693D22A93DBF68A529218837BF03BE3E99E609E4EC368266C7D70B660518A0C02EB0265C935E139C94A8B123414AD03B396271A5B3444E433C3CD628BFBB935B8C8A614D96877517AC49741BBA27F1A14BF98D6BADEDD41A09BE7713DDAB8EA2C0720C7835CDE19CF2C5860889395F09A2FE74C623468AFD540CC5CEECA2C9C05C7FB3DDFF27DC99FC0FFC3E8F6EA969B0A067984EA04E1B1CDB70EFDEE782D423A2649E7A25856974590E0D388588A448AB0CD6D8E0DA712655A7254C31204113D8BDAD2C269731F91F949882A16E91352EE72EAD1905F9F3D7B5655292D87F0E33C7800877B57A0C36D891BEF4B23807506E9CCCC44AF9D4DA8E13CF77A11F201B87E32795D219B4845F3C96389423E03FFCDA826A26381F0EBB8E303BE370D8A2B9F4DE4E3533613A83F883C7BF6C1B65F3E50B97D145CD1A3A41FFC001DBC97A5986CE847215055ECA715F5E1775EBAF38962E6C5C2554F3E73F01F267E86AC462CC12B1D9A906E148FD5867362551F9E56AA0A3A9E6B9CA0847A3BAFFDF4C52FFEF3E1FF7A0B3CC449C665D0CA43CEA8740D1C0DED938F7FE6BCBEFBDEB82B82C14BF46A3340DA1F48061F7FC78F75F1443A9FC802766B9D7599FFF3B7C3DA2DAF3EF4DEDB80DF9C859F2A50D149D17DE2A6C21A11E6803846A4AFDEA1B4FC04D68816A7B91644E898C8515660B0E9CCAEC471C7D0A9054706316EF4950DDC2B6C92A8364B7B1C9A36D5EEF17729689B3DC66BE93BB185F5AF5062860D9D00F598452587A2A48584A34934578C62399388CF25006A7748502E389DCDC3931E5C83F77F68836DB7D04DB21BC287A0296B8AE57B210C9A4B44A017017F80552D48C60C379AA97095E9CC19FB63BFDA425808644D99B2C4B30BC164BDD055BD6147F8E01156CC32010CB56480667543CE53C10D03D754126141DB7EBF03BA17E2A145F41E91C949889D4C7FF8421638B95AEA439B9D184B394D56100A02B1CE0E4638B1686EF9B9A8C4A536E83177A1D1AF9BEB451759DA10DA06585B4C4267D01A68880B395002B6A8A2DA606D5406B6ADA8163DF733CBD29BC4956463F5B229D5FE7773805C5083E08901FCCCC174726222953048FF8CA401A0A2219F0667297CAA0CB9E430AFAD993688919E9295C183736F7F0F1FD5E062EE3C3CA8AD0321313D829C948B9EE0A6A3B9066B5F94CB65CB61C7EFE11DF64918CD3414D5B5D4C45D18AC8BAE746CCD16CAA84B9953B9C3762A05E6540A6EC78EC9CE87AD2F5753A98AFDF1FEAF1EDABDEE15FDDCEB7EB2E7F99F1FFD1C2167DE564D89CBF167F8A4E9BD86922E0F2F7CA4AB1BDCB8C244E6789A2EB5C87621D869E1BE967671D7B6357FA05D70C7E6776FDBFEAF5BB7A73ED16817A7CC5DE057C5B5B08B7C6DBBC32F5C7EE5074F7CE4D0BDCFDD1F79FAF423998F355AD868B4B077C7C3A116FED4B6358391D16FBCFAD5855B7E71D739F1B71A2D7CA67EE161B6F0767BC7C073F2128D42B0FF40A3C800FFFE863F325FBB67F0DFF4FE9FDFFFAD2FC4C8D278F25873EF17A724A8F38ADF92A60FC97B92F87DDAFC732DBF978FDB1F3799BDC7BDBD3E0693EF986BD471C2A7C3C2E52356FB4DCD142F0E281AA407A389F491FC54AE01B91F00A62FBF2F723FF4FA55C69B1FBA357DECE0A1175E5B3EFADA0744EEBBED8F3B7DC86DF6FA909BEF986BD471C2A7C343EE3B4390BBF778227A4D3A91CB251AD1FB7E20B5F0BEE8FD8038F4D7D1E9EF1E7DE23FBFF0BF77FEE3D51B3E207A7FCEFEF8AC0FBDCD5E1F7AF31D738D3A4EF87478E8FDD910F41EC8CCCC64B2F9D974329F6C48F27B81DA0FBE2F927FF970E69C5FFD28FAF6EF8FF6BFDA7151FCE50F88E47F647F3CEA4372B3D787E47CC75CA38E133E1D1E923F1A82E45DF9A96C229ACF353658E46F421BACFB4F3DF0E33FFBE8D9911BEF7CF6B667DF9BDED6C86E3C19CA520A4BA117FE9DDFDAF7B3AF5D72EDC8531B070F65E6935D8D167E3AD4C211127AE127BFF7A75BFF70F768FF575E3A71F2D0E7A784460B7F3DDCC20F845EF89EC39B377CFABE2FDFDFFBEC3D9137FF27735EA385BFC12D6C27092F1454EACB37CDA998392EFED2D5C7D3EA62E92DB5A51C0D2BF8707C61619E742C88D512A66C83F2ABB49E6645AD696C68E3922C73E8677E70F35B9FFECEED07685A0BE94B89B4917E6EC7677AF72AB4103DE66A0B5A5D1D554F8A6C282AD52A734BEBF3D31BB01827A7D4B4A2E4E9EB95EA5BFA657D5687182E2E1697ADDBBB7E85DD4F9877EA21EE8932DC842BE7B19E44132B12448A6192D8F61667AC59101C6C76404C8B6ADD46E649BF261935CD0CAB608D03A1D7C83AF368A05BABE61533ED4437CF8722C2B76C97B697B9B4C4392F7FD5F8BCDCFDE2F6EDE3F3DFBF4FBD2F76EADBFBAE39D8424EC5D9A8C3EFF62489C8A579D2595CAE554FC26EC78276EB4089E3142C82A0994E5AE4C45D30AE735AB39258B67BCC3A0190FAB120A9AF5FA972FDDFFF50BEE64BB3CE01D0C8C196F74E61390488C448BB418BE1B89203356290B5D904DAF0423C934E27E27988672DBBF4773075B086DF5EB6CDF7F760C6406E36C6264D24B366B360902DC37B8677C472F14276263F36B6AFB09721BB03390B74180FA2838F0CCFB73DF4A53BFEF2B9184F8A2B5A3DF53638871A9811BD3C241C5656E60325E2A70C9CDAB315878CFD3619059B8C3D8C86C909B4F9FC936A297EE1ACC04EC55FE098CE254D514EAFB6A2B919F20ECE6B9B6531E6B0EC615AC41ACACD58EA959574AA03A7651DF51B6A26BC3A92A572294C41DA240EC4CBB445B90C84F32820DC4F4D0D03C9BAF773885BA7207707DEF2717AD1E7F630453A4ED5246D35F80AF25A1C86DA4063C489D5AAA5B25DC7A817355935CA92E1144302AA766B185473D660B4023AF44D328207CFB4065B378FB8FEF83CE9392D6A32162E03905D41408EB1B100E3DC0642C0525B57852BFCF301C055000A0474C3C541BAC1DC55E4F0EB2FA477FF42B5D441379C844B439E040AC139071D60B84A92C91726911177E1A8AF94B46A03E0E419A42D939C084A95D695E43988B60156B80D0B01072BAC4FC3D66DDEF23FBEE0160BFC5658B4E45CE599F5E9A6867A8762E96820DAF8CBE019FDC7925473E493F95422ECA4EE4CF648211D9D0E3DA12B3E8B9A2A34561DF189689E87BE8E8ED64F9547F272058E8058515DF0675B843F1B023E0DE31C630822BE3B48C41D0DB583F4DFBDFB9B375C6089F9A6166F4AEACC540F2D399D543490B00A487B75698E96A6ADEA86549990160142294536B172177AB63D4E6FC05919ACC2BEA5121EEDF4FFF3091204A4E5A0E3A5E413D7E5558EFC4D2B14CC7A66BC9701A787CE122E30216E641067A25910C63C18C1C2747486073CE0DCFF03FAAEC99B8E467333896CC18491F343AB29C3F8B74F743FEC065C0BF07043BFD5E282E7E3E57130D7DB121CB76322D7F421367D3A7A1D80984DBB30E20A0458B5A76BEE3ACB578CE6A3855C66361B4F84DF0F2DEA3703315F8472F16C7206B48F0BA1D1E620A30BBAA18945C3B6B36ECA33C8A94C3C9A72613AE4C8434A298A6537469BD8BC6CC2DC6321369B9E7003D8E200C8BA9C07DFADE593D389C25C26ED02B1CE0181CA664EA94ABE523D99C94E47E14F349ECF64AFE72134F51C29E049FE9D0E17F4F59614A5A2B91C50283A91C8F2B0373A92408F724AC1D7215C20B63210B3D954612A8A14CAFA61394C2121AA30100B77C4CA9488A4D2FCF0DA60ED3A994A5006A48EB9116B5AFD606E5A2E4BC894F2E93A846D7624A66752A0F8733CDC0D0E3BE24AB9EC7372FA72266289D4846B26575C8476DC35A713F691776FA0A992634EFE24F5B27DCFDEB164363F1B4D25E75A800A9EA0AC19357CE7AD0EEA8664AE903C92CE6413A03D8F24D3D17A7DE22D19B2E6133B2422F521118490911D3BD4BA6028D02ABB800A3E40DB778C8C9A60DD816B3068E8B4B34E6D34EBD4064E8F6CD269E42645A900AAD6E30AF77D75C5AE9BA21C9E12F56530211D5DAF3DFFCD4D37BCDC462293A4B70C87C314667C7B6D19ECEDB2522EADA8BF79B5992F39D30D9F7451DCE5270D228CE197DB80BC7B47F61FDCBF77CCFE77083B6E074773EFE5FB565AF089692CC3C5B35C7E644F509840E75AA911F7EB5C34D9B727C8DFE1005CFD79F2C89BCFEC7FD47278222D647B1C305CB6C727D9410BAD4F4AD59C8471A381D9C08D5C697F7C59449B803907ACBF82814084F5F3DE373AC12331C8B82EE1611FDE3DBC083ED5F0C242413FA3180513CB424537862172D2A4615B8E873F3CCC3C887B3DA17F1B185EFB3220F66031FEC463774DFFCBF3B3579E7DEAD4D40F8194BB824869876C0BFF7EEBC71EB8F4CF272C42E26BB04DAD2147480B48C85C418CF4810D2CD6CAB4B83D4606E46A51A32513E8057A3DCC18E90111970CF38DCC216E6EF8FADAB833C97A0FCF937F19B4F1B04BADC7C0BF746117BE9224C94F636B6EB361D9D6A8DE9D3DBFC110976F0B3EB75C950D592CFBFBDCBC83DD4B69C7EF68D0A666F87AB8AC3585EDA4F58CE699B7F1E22354B96D1DB37C2E233A73D7E7C0AC862C53F2B0C207627B1AFD2527EF16F1B1075BAAD299618FCBBA73EF2E6AAEC1A0250AE9D9E91858CA50372E2E9AFA6DD23CEB78F4AF3035C0431619F1E18B34BF273CC621DDD62AD203F14C6A763A6DA38D001F0762E05EFCD6A5031EC148195D797BDDF6D6D6F5E0DD117EFE18C5BBDF0E4A30A240705F6944A6AFDBAB74BE4F2CBB5AC5B2978A8483E3D3C13876BF4F1C7B5AA6249300134B4B003ACD56135F10E6C381C2DC245DE77BDA66532984BE3D5C017EDD6BD43E107B521012E4E2D119F39D81A6A6CB7C67C0F5BAB40FC46E3059F92484729681752E1223D4A58BB4905CC117F6EAF2881B9BBFF234B0A02827F1EDB794745A2AC7C8A084CA959200E3B71859B30CDBC0D7FE4EE6456D4932C0903A2DD4906EA46F11E29B0BF8E30AD62D618A6C10ABC5654543F5EC311D6B9C65789BB1DEBD7AF8F7B713AE790D6DEF3936EA518A9B07AFCDF6007CD7DF7321B09EEB6EE1F273CA330B73D7362CFB0D270F3A5B1D52439862C8F577A43CC2BEEF31819343DF35A3EFB7D7CCFA4FFAEAA6CD4F6653BB2D7EB2E72E5534004F2B97B6963DD62D4085F8025604B0C1531CB8D3BE49157ED4F826F5B991CB1EFEA7A75EFAF5E079FFFDF01BBF9768E3AA67FA162162592CAFEA352BE249446F68B97046F889FDF11FDEC219D6EB2D9C7175CC35EA38E1D3515F3803CB36299CE17F678050B5D2F46D0AAA56BCF2EEA35ADAD299E3B6CAEEAD57D96D5CAA5BE5FF51C3F1E3864C5D178AA99F3A37FBD1E1D76FBDEFBB4FA8BF9D7F7DB218C0D45B5A67EAAFEC8FF77C986AF6FA3095EF986BD471C2A7C3C3D4F75A602A8E3F6BF3A2CF8717AEBB0A173FEC2A9BC3F6656B2819F1FEA6859FF94967304792C805DDE197245D5EAA8E58BFC011777E84C8B1356DA0C18B9AA2EB86B8C00DC0A5DE05B79DFD808CB76713F7332A75BD8279492DDCC1B20E4334EB30845F571A7640481A998F3DFECEE6773BBBF36FB058F39A6BBEF3AB679E83EEBD91FB05723E5F08B07FEF15FB0E8C5F7160EC8A7DFB0A971FD83B363ED64504813AD75EB56EFD5E923E7A845E77B3B3A8685DA44D205BCC3B703324370764166E8458BC8B740864B715562A5A210003812B8F29B872BE4240288AC5FDF476BB5C760C8E0B9C2B792F90733D0B71F938816CF574DBA95F816C743AB94CA240B67B26B9929F7E50ED5C39B0C7D3E9BE0611C836CF08E7EA40A02F117B7AF1064420E779BBB86CB1402EF2F4FBA482FD68C6DD1FB8B6EF978C14C89033C0CCA6BA18E44A040A64B801EB72CEB6BC1473A7D8FD086EDF96F8F2D8CA27BB96F7BF4410C87ACF90E48480EF9980335AC8E0F791B0326B5EA50B64C09CCC6E3F0532C880319B25A021A103F0BAD37E8A9B4F6C72DC9ABCCE7CE695AC80EF67612387F006AB10A1C0C5D942C0EF2271B8DB892E816CB241B9A25F383176071F5E0209DD6BB3D68DF56058FBD0453AFDC5169A0649561715502E29455B02F5549296C59A3ECA1411DA5A33EF3F6A8F05F4BA0A0580301D178830078C02D689B41BC430C5ABB069AB03E6F4150A06B81B39BC4186794705B28B6B2914F68D5F51A8421CA14D89AA2A556DADB677ECE04181741EC66CD2550269DBB9EBFF88BBF2F8AAAA6BBDD6BE37B9273787908100873004B84008094364169159C6CB2861520899888604721340C0097100671CAA54AD523556AD03520B567CCAF359CBD3D6BE6A9FF5599E383D9FCF5ACBB3EAA3DABE6F9D7D6E724E7202F7DA5FDB7FD6DE77AD75F6D97B9FB5D6B7F63E3B274B4D1A4363C314A4714C5911E4A153CB6A6B17D73456DA5FB641842B18BCD27F384D7572C76108A4B531E80F8B5F78A64967D28430293A8BED055352F1314467E3319FEA86FA6D9C7C6F48342657946DB02F9BCC142E2F8B352EA91794651A53E0DA92D591FF4CF736AE3D9583DBEB983495A685690A4D37C9A0B4348CE31C93C2BA36CBA4745D9B6392A96BF34CEAA46BF34DCAD0B5852675D6B5C526B0D0AE9D6B5296AE959A407EBBB6DCA41C5D5B6922C5B36BE79994AB6BAB4DEAAA6B652675D3B57293BAEB5AA54996AE559BD443D76A4CCAD3B50B4DEAA96BEB4DEAA56BF52696B3766D23DB5BFF09478110C54CEA43F9726D938995865DDB6C523F5DBBC8A4FEBAB6CDA488AE5D6CD2005DBBD4A481BA76B9498374ED0AB617EDC9F87288AE34A98006CBE5579B54A86BBB4C1AA26BD79A54A46BD79B54AC6B37C29DC5AAF55795E6B53A975530B803F732E966DA23E67B8B49257486B4721B53B0715D0D2EEB3BF734D982C44B18F2BAFA0A675B84696C81CFE71EDBD99D8F29062489622A4ECAFD427417DB3BEEB5539B1AE4031E4C8B0AE676940CF97424A1AE99740F7D2F9DEEA67B596F3FB3DF472D21C3DA36E695392D0047CA9B628DF5EB6BB65656CC424042B02868731AE9D43F072F9509427A3829A9F175309A1FC8681E9616951064906AC37A6E77400AEC2AB0D52690B0C4513B72C910DB765E772FC86D0E0A18F4234C8B3C9F3696915C877F4C07A5C387E41EC867BDC7400C7A56F8A9ECF75ED7A07F126188DB1F2030E888880CF6BC8D36E85F849BC6FEAFCF0D7A59C4616E7FD6C2A07F15513A777088C6A09F8BDC649F632706FD52649DB8ED911E835E174106FB9E3230E8DF45DA994F7580C0A0B7442993FDCE9C18F45B1166B1FFEB6B83DE117136C7DFBE1BF49E3072B8CD6B63833E147E17EEE8109041FF2D0AB9DCC1610C833E1179576E7FAAC6A0DF8BA81BB73F7560D0091175E78E0E9A18F44751B0D8F576D1A0AF1C73D6474845A107DBE7110DFA5A7EE571CBF93783FE229C9EDCBA6F61B0ED34BDD839F466708AFCEECDCE213B830DF9DD875B0EE9199C2E9C7CF62EB80DCE10765FB42DBD41E8B7FBD28F3D2F5C0CCE116E7F6EF346C3E0AEC28FB0F78D81C196B007B07B8BDEE09EC21CC8DE3D7183D149C3D9FE4454CB2D98E5E37FC8A24F1594E3C989ECBD37549595578E0D313A9B56139BDF50533D4B623272AF15260FE081618EF020CC8B96813758F30A4D1A4E23803E5C04B4AAA88909045600C7EA2ACA1A2A5AE0CAE4A15A7F188BE38E40681A3ACCE033646C6836ABDD07F6428C143064BFB99B5F0523F18E2FFE153E93C7F2B8308FE1F108813698C837921A9AE40DA5C913ECE0C367C94D0A840C1652286488902221C542860A41D706FA443AFFB8C6E748D333591FB570C34988674B6BC3858C1052C2FA40DAB0D8C6DA61AD07D2423C5FA4F6148C848ACF9F1A18BC58A4A3848C163246C85821E3848C1762E3EF0421F640270A395BC8242193854C61F96B11A4E0B3D8F9BE0D0C356E3CF36B2B4467AA906942644B9566083947C84C21B384D8E39A2364AE907942A242EC812C10B250C8222176CF97083957888D32A5429609592E64859095425609394FC8F9308678CFA6C760A6F2D53411AC16B246489990B542CA85D8D24A215542AA85AC135223E40221170AA915B25E489D907A211B846C14D220C476E246214D423609D92C648B908B846C4530961C2B02536F8CD4D46DAABFB0327206D3A05339DA39DA3266ADDF501BE2FB3C36E36CF6F0F7B1382A58D9DED47C4E6CD8AB217E20CCFBF841962F26C73634549655C8B12BA6B34ED7489C356BB00FD7E487F8075855F0C32C2FF36570F3EC2C717E9D93A645992A0ADA2D50124A6C9CEEFBDCD6CFC5F851FEA1F4E4317876EB84C72A1BEDD94E2C575D0A70D4C70C5A3A6403879D6B9E7ED995CCA8F050783F3F253D3EE0E971B5EEF1409F1EFB46EC2ED5BE1D5EF8D74DBAEF0C1FE443D2DF678024D5CE1F78200E150C6E7727939FE5C361BE9F9F63391753D3E85AA10CF10D991DAC59F8797E416E79C4A4A1344C50E345A68244B72142FC12EC3DDE55BD897156FB79199CF8BE86C92FF3CFC2FC533EAA1B76E1D52B585D41F02A04B14AF7807B1474303A3137D3ED34589DC9F5B9BEEA21FE15D3EC539B6132AB30937FC96FA4F3EBFC6BC4760C25FE19C0D3A6EFA7B29037F937D2E45B6832E66E72DCB7ED37BCE46DFEADB479CCDE1F8AC436976DD0CBAA8C48B9774FA797EB7A3CBD618BEBAB1A5BDE7DA273C7F95DB1A0F760BE13CA6BEDCD2293DF916D22C51F9A349A4689F8A3142CBE6637E43FBCC2A4919AF5092270AC09438994445A53A88905897FA0DAD77313D9AA68DDFCE7CF4CEECBBF0FF31FF8444B7F46441AEBF5E42110781CB165A96AC4354CFE82FF284EF92553377DF9199186CA6A049DCA066D80B213E0B5568C019959BDD7683BB7B9CAE43FF1C930D6845FE3A9C43B5657DF585385A792AA2B26FF0533CDF72BACFCB3BC3A9365CD98D652379173DB9A58635A6DA7BD05E24DBEC29E0C85055F9FF86860737E7F59C034EA34CFCAE7227BA4DD3A68D154E92A8C112B53DCDDE9638D6D9675C9ADF6FFBA3F21933E06E5BEA6CA549D653AB2C4F59C0E21B33D3D90B4184A00EAA6CA555DA419ACCE7AC49B417098D1546787C2C54D1BA4834C53FDC2A8EF50AA9C4B63C3DA34825B66B76FDA543D94253DC0CAAC53DC4ECA6BEBEB2ADB5A788B1FA5D86253F551BDC56CF205C41D8388DF7CAABD2F31CE672A12CB2EF094DD4D992AA2FACBE3C7DA2BA7AD89223F36B94A1B67418B798C886C2E13F3084A61AA21DAC4B1064A29983D0B614E0D514385834545AF788BEBEC13FFCE338F9B00D399A74E66A66F29AFB4FF4EC9FF31E7FAB66AAA335489F478A42BB6B4827C8B49AD8F21D2AC4A289B4A760FA8D5107113538D5363A53FE3DB805E12B7F3695C9A9D20CD9EE51A66554D9D7DC81FB1325E35D524FD80262322C68DA9ACA2226EAC536BEB634D0D952D517352C1295F26683DE722DF6410A1AEC3E64D355D4D136B9BF1AD4CD835F4993274ACE98205B3B5C9CD9131CEFD367BB9AE56A3D2EA7C57E8AFDCD854561B934D069F36560012B482A916AB45D28125AE87B1AE2CB66E2AA6D1DE4A40578D38C354CB54A9682FC7522352DE209FC7F6BCD4593D4296FDBEEB1F43450CDA6FD04983111F03B06B439501C72644CA5BD7FF9189862AD7A9AD7D30CC50E84470436D53CC50D53A47826B3BA21A0419BF4E308D2EE8209FEC6867DBBECE5E946F33D54AB50AE988AA632A49BE9190DAD066A9A32373476B42D510561B151E54DE2912A7906A32551942ABDAA4369BEA42552B1DBCA883A76B37BB4D74B723B495E929199EF4AB367589BA340D7DBB0CC6522E6388300D380D90C5DF22766EC30AA99D4CA12A7D52D05457A92BC2EA4A753553EF56C568BDAD3AADB26A86FC93A4E90D0DC8B8D42ED67F87E56A2C5ADFA835E24136A4AE650ADB4B8158ACAC1A37B81E5996BA4EDDD066863C137F5358ED5637C3BCB4A967DB7F4F177F21B444886CD12305D05F3A763861FD8780F2A75AD4971405914BA5D260D40C4A931D7E799F6797E94E693A6527A7CC70CACE4E99E994594E99ED94394ED9C529739DB2AB537673CAEE4E6939650FA7CC73CA9E4ED9CB297B3B651FFDA56F79E36697FD9CB2BF53469C7280530E74CA414E5980514B59E894439CB2C8298BA5CC0CCBA211E52ED97244B9435E7CA14C93C4DE2E47DB65589606B2F186F91C436389D52D44814CFBBEA44E14FE98C61FA48973795E51F6A48334E3799A54B8FA4734B3A894A3C56ECEECE2521E1FB4826EDE5C2B08668A95E26646AD143053AD54377381950A66C80AB9998BAC10988665B8994B2C03CC342BCDCD5C6AA58119B6C26EE6322B0C66BA95EE66AEB0D2C1342DD3CD5C65996076B23AB999E75B9DC0CCB032DCCC355606989DADCE6EE65AAB33989956A69B59616582996565B99955561698D956B69BB9CECA0633C7CA71332FB072C0EC627571336BAD2E60E65AB96E669D950B6657ABAB9BB9C1EA0A6637AB9B9BD9607503B3BBD53DBBD166360A7393D51D4CCBB2DCCC2D9605660FAB879BB9D5EA01669E95E7666EB7F2C0EC69F574332FB17A82D9CBEAE5665E66F502B3B7D5DBCDDC61F506B38FD5C7CDDC69F50133DFCACFBECA665E25CC6BAC7C30FB5A7DDDCCDD565F30FB59FDDCCCEBAC7E60F6B7FABB993758FDC18C581137F3262B525AF834DD3A7E8035407EDF6E0D287D12BEC0EA5650131E1154BFA3EF90FDA52FBA93F66AEFA085883E727C3C079E012FF86E49E190A243745F909EE027C405EDCBF3C97EE5E45C2EB57DF47DFBC46716DD4F0F40EB36D453E526082D4C0F52B3D3FCC5B8120B712A46F3B64B3D841B1CA24702548A1F8F961416B5FCF8614961B1FEF164CB8D0BEDBE15B5DCB8881EA3C7ED1B17D113A829BBF6246A01BB0B19F6ED6623FECD416C9A0BCDFDF4941307664366A1E123E80A22C0819221D94F1FA2670214B7AB68B11D0E8A4B8BB27F02E1618F503A6FC706C481A2ECE7207FDE2B4768D0712205F21720FF67AF1C5142878C54C85F84FC25AF1C0143478F10E43F85FC675E3962870E2406E447217FC52B4718D131250DF25721FF85578E88A2C34B18F2D720FF37AF1CC145479A74C87F05F91B5E39E28C0E3A26E4BF86FC4DAF1C2147C79F4E90FF06F2FFF0CA117D7428CA80FC6DC88F79E508443A2A7586FC3F213FEE952326E9009509F9BB90BFEF95233CE9589505F90790FF97578E48A5C35636E41F41FEB1578EA0A523580EE4FF03F9EFBC72C42F1DCCBA40FE29E49F79E508653AAEE542FE07C8FFD72B4754D321AE2BE49F43FE85578E00A7A35D37C8BF84FCFFBC72C43A1DF8BABBFCA3AB78807A21EE1FA8D9FE61FBC20A4883742EB079294DA6522AA36574392DA7FB20798156D2715AC54CE7715F3A9F27D36A2EA3357C3995F17DB4965FA0723E4E158AA952F5A52A3599AA5519AD5397538DBA8F2E805F9D8CFB159F0FBF4A4799A1FDEA4FE8FA37BAEB4E1CD47EB549FCEACF254372983CD2B8636D11C7CA61168D8057C371ADADE25A391C148D54AF86E35CDBC5B97238241A695E0DC7BD2E11F7CAE1B068985E0DC7C12E1307CBE14EA2D1D9ABE1B8D80E71B11CCE6C7D467105C7C776C2C7DA3C23EE147F46A83DD1F28CFA21E30B520CCFA891A6501355D026BA8A36D343B4858ED245F4096D25E6ACF85CD367986B39BFFCA83DD7399C2DBDECA23BE180819EED6B8AA58BB922EEE611C7A77BB79EEEEEA2D1C3ABE14CF7757ABAF344A39757C399EE1BF474F7168D7CAF8633DD3761BADBCC043DD212CD1FB123B79E89AE88DB41BA0299E34E9A4E575235E661175D8DD1F7E57E7AF4A94B30FA7E4429FD64F4C8D94A0F72C19DD4FD200FB993B2F773F15E320FF2F0BD94DAAC76E405EEA550B09982813B292B874B64DC518CB95955E4053DA2913260C9E49AD5ACBC9456511F0C6D54490E8F091EE4330FF1C400C9B8257E37ABFCBC54AFE2D95E450C3F8AA137F3977921AFE224AF228C2E0A836BE69FE7195EC5C9A218685184ED456177CDFC405E669B7B4FF13609238CC2009B795B5E669B9B4FF5B689901F45B86FE68579996DEE3ECDDB26827F1481BF9907E565A67935A77BDB040C440101CDF44D5E66B8557300346794141EA0AD877816D6253C470C85E7082C440109CDF46A5E66BA577FAEE8EF68A30F988802229AE9EEBC4CD3AB3F4FF41BDAE80336A2808C665A9F97D9C9AB1F15FD658EFE025B7F81C0481410D24C67E56566B4EA0F83FEC2129045725195F72639BCC4B67EC19828F0A5595DDACEFC002F51408B9FF90159A240151FF33BD7FB14003151C08B8FF92DF52A026BA2C0191FF32BF52A0274A2001C1FF35BE67DAC409F2890C7CFFC967B9B44FA1D45CAED677E2BBC6D22278F220FF733BF95DE3691A847919CFB99DF2A6F9BC8DEA3C8D8FDCCEF3C3FF343621F4532EF677EE7FB991F72FE28F27C3FF35BED677E580E44B104F033BF357EE687954214AB033FF32B13F35B7B2AF3C38A228A554471BBD43D253F1E7353F2B99C2B64BF21A507572275D779F3499A88E87B13A2EF1EACB16FC5AFDB9141DF816C612F55D15DB483EEA11BE95E44EC7D741029FFEB48F18F03A5BEA187399D1EE541F4188FC2926121EDE7D57480B7D1D3BC8B0EF203F40C3F45CFF21BF41CBF47CFAB143AA272E845358A5E5253E965554947558C5E51FB688F7A9C6E5587E9767594EE506FD25EF501DD1560BA279041F7060A695F600CDD1F584C0F06D6D04381EDF47060373D1A78901E0B1CA02702AFD1FEC0313A1038494F070D3A188CD033C1127A36B8849E0BAEA5E7833BE948700FBD183C402F058FD0CBC1F7E968F004BD92D29B7E0184A9FA07224C75A208B32E5184A94914612E4818612E4C18616A134698F509234C5DC208539F24C26C481261362689300D49224C4C5CBCF1EF89304D8922CCA644116673A208B3256184B9286184D99A30C26C4B1861B6278C3017278930972489309726893097258930978BF9EDF85B22CC9B4098B780306FE3D73120CC3B4098778130EF03613E04C27C0484F91808F30910E65320CC674098134098CF81305F0061BE02C29C04C27C0D84F933EF62E20758F1531CE4373895DF6343A570586169A74671869ACA99AA92B3558CBB0061DE02C2BC0D84390684790708F32E10E67D20CC8740988F80301F03613E01C27C0A84F90C08730208F33910E60B20CC574098934098AF81307F0E1A4CC108AB600907834B3835B8968DE04E0E07F7B0193CC019C1239C197C9FB38327B84B4A6FEE0684B9E21F88303B1345982B134598AB124598AB1346986B1246985D0923CCEE8411E6DA8411E6BA2411E6FA2411E6862411E6C62411E62671F19BFF9E08B3275184B9255184B9355184B92D6184B93D6184F94EC2087347C2087367C208B3374984F96E92087357920873779208738F98DFF7FE8608C3F9D41FB176384768220FA4395C40CBB990AAB88876F050BA9187D3235C8295C9487A9D47D3711E4BDFF0784EE7093C8827F2289EC40B790AAFE669BC8D670061660261660361E60261A24098054098454098254098A54098654098154098556A1FF7538F73441DE681EA2817A837B9507DC04501E6A1810C1E1E28E492C0181E1958CCA3036B786C603B8F0FECE6098107F1D00FF0A4C06B3C25708CA7054EF20C20CC4C20CC6C20CC5C204C1408B30008B30808B30408B31408B30C08B30208B30A08B33A93F9DE96373A17539AFDD6648EBCD1C956853FE17DCB8387F9FEE581218B530EF39CFFAFECDC63A328E2383EF3BB6D7BBD5881DB16DA28052101DD02112B6A8AB1F2F051AFA0D040BB68010951046A056B0514157C21A228627DE0E32FA389187B146ECF9C588D42E383A7898F48426220BE22C688F1158933FBFBCED0C890E83FF7C9DEEFF1FDCDCCCDEE66676F374CA8B9DC147AFA3392AFDA2148281FF61BD3E4BD9D0A13896BF3F2F5BC7C938742F65B096AB097F01AEC4A5003AF04A952BA6516A5B48954BCFA33519732265EFFF9EFF5E4E50E75ACCBFE4BBACE4AD759E9BAF857A0A573328274ABEA85F83F14FA38FBBF540BFDDBCB972C6BAD682D8B0EF4F4BF302155AB7CB4D7E820277BD3F48A480679F97EF736B9ABB1A64F0CD0DFE6E5C761638FDC5593937B7572A16AFDC4261829286EC710352FBAC2DDC2EB6E0EC29CD8BC4374E96F4CC03EB91F016928160735CA270BFB01FB13A8523F00DDF832A57D502FE8C9CF3D61D27C61FBA85239E93467B0DB5BF2AB84F53A6407F1D464873DC19AA5FAB65BB855E0AD7FA5AAC3336AC21C895D44521EB52E23E27B1D54A7E6E537A5F5D53DF2BBEA9672F9FDA4E13DF2C7E12DA619C7E44FA7363392BF98CA8ECB5F611F04C9A22092BF1BF31FF24F57F8DF26FD095B4EBF7092301339CD45C65CEC2C8E9250A7524AB9EC6526FE4C1A00BBBEE0ADE7464A75E9D82A2FA2B4F1F1A9DC956388D1A8A42A97FD6C631F4AD58E269C63CC236824CCBE1A573DB225AA8488461987D1EE369E67EC81B38BC69AF2031A67F3F3DA7249306C6844E71B87F17481B3800946E022B9D331C2BDC67C315D72BA3E9C78D2E752F894A388A4F689E832E351EF6CC56453E4149AEA2CF2AAAC55B8DAE9708DED26CAD86E6487E2605844D34E2698EE4C709D49308366BAC661D656D86753B3A30573D89CD2B7A061E5FB43955DBF01746790DEE2536B53907ECDA7B90A5B7D9AA7F0864FF315B6FBB440A1C07887F11EE303461FE323C61EC67EC6A78CCF185F320E310E33BE661C657CCBF881718CF133E338E337C65F8C13317C29410F2C0153601938104C831560257816580D4EF069A1663D783938099C0C4E01A782578057FA74936623380D9C0ECEF06991E64C6CCF42DC6CB0196C0143700E783D7803D88A3C73C179E07CF046E82DC0F6CD885B04DE022E0697804BC136F056B0DDA7C59AB781CB907739783BF43AB07D07E23AC13BC115E04A701578177837B81A3AF780F722EF7DE01AE8ADC5F603887B107C087C185C073E02AE071F053720CF63E0E3E046F009E83D89ED4D887B0ADC0C3E0D7681CF80CF82CF81CF23CF16F005F045F025E8BDACB7B398D64B68A93D9C7AF1AC1FEC539B3AA3C904796A2FD0B2509DED44D4C17B91947ED809FC2FE4DD8038779B3CB25B0C529F395AF1AE0AC8F489749E56650AD419AAD0D5EAE01D6CA7359C21496BE97E6418851386B4925AD7DD58A0F5614D8E3646B4A997F73FA28836087DEED425F4ED7B251DA2C2F9188AD3BDDBC03E336B5CFCAEA4F6CE9578CED7F27F00504B0304140000000800736C9545C5E751CDDC040000A51300001D001C004253435F5250543030322F4253435F5250543030325F312E6A72786D6C5554090003295C96541913985475780B000104000000000400000000DD58DB6EDB38107D6EBE422BF46901DAA444916261A7485B170D90B6D938D95DA028028A175B852CB9121DDB2DFAEF3B922FEB34DED406BC7E881F04939CE1E1391CCF8CDC79391B65DE9D29ABB4C8BB3E6961DF33B92A749A0FBAFECDF55B14FB2F4F4F3A5F643536E5951917A5F3C025AFBAFED0B9F18B767BB154364B55AB2A26A532B62807A6951B777FD55FB8BE9855E9DA7D3A9DB6A6610B1CDA01C6A4FDF7FB8BBE1A9A9144695E39992B035E55FAA26A262F0A255D73D4FDD1BD9D5C6695BE67D18209DFCBE5C874FD57FDD7B75797D71807B7C4F732990F267200F383B228EEE6BE3786D15FA976C3AECF79BC18BF33E960E8BA3E8D02DF2BCAD4E46EC9E042E6BA52720C0455914D46F9A66B66AC7B2FCB410A86702765BDC9C6D815E38D515238578C362626935403A48E254B628D988A05A2182728118623492D3382498269EC9F9E3CEB8CCB02F8BAF99266BA64FEAD2846BE7727B3094C06AD30E282722608AE3F81DF7EC475B6F613347AD472BEB6C44B3B59828133E5D2F0AA77F9F1EAFAF6FC0DC894C90A02EF8BBC93AD5AFD56DF9510A83587671D6DAC9C64EECF7AB3DE6C5C9AAA8EE9D3CE6F9F5EBF39BB3EFBE4FB9F3F9F76DAFF6106C0ED35723DFA3A31E57CB17FB3FD6A9BCA644639EF77CF96C5C84B92DB6A5AB8DB05995BED326F3A34A5F1D6A7F6BADEF3CBEFEBE10F88010DDC92B9D7EFFDE1C94AC1A96AF0FB781D9B9A4C2F15F8F84BEE8DF51B53A9321DBB7BAC1BCE0F966BC066F267A85DC53E182088B005EA3C776660CA03635D9CBDEA5D1C8BD879BF7FD3EB93E3C205C7850B8F0B478F05F7FAA6DFBB3ADE6FA076EADD07ABBE66ADEB7464A0028EC60786BB392EBB9BFF9F9D364EA659B34F0245D51B2E6B2E542ADFABC659EAAEE7E33AD9B8D238356C109F759C99B9B7CB2DEAF12287F73233821AEDCD9A423A6F9ED34561261C7ECEABBD855895D900C752AA00231B138DA0B65A9448AB11179C2A1A2A1672D994B615E80AA2FE7E96A583BC1E4118C013725EDD8DB954C96C63E97DAA756616E7AEC52AC0BB7E7C6814B6504B6D36AF2670775E957E832902274DAB574506E773E5045A8CB1B6BD756377AE61DBD4CDD1BBDAEC12964689D1DAACAC57A76D6F1C778340A3DAB61AFBFCEDF726C9FE682E6D9BE9C97ADB7FA5FFF54D101EFE7C17D056DCBFE7C5652461420D0D2C5238AC7B9E48A3D8C818199108AA88E6929AAD977108D5C5F1645E9697430B1D066247A121AC6D82A94511D5068436114AA258220EC213CE598C057F3A420787169AC6C1AE111D196D1913288AB8429430E8E22531C8122963C1B06661FC74840E0F2D340BE903A1E9F6880E84D4D21248E12244549A108926C03167903A921863F67484A67B0A9DA5B9D9B9506ED55707098B42781D254A27A0AFD6482A2390C5492435E41382D59257A7BD867B14B8A9C83B40B3208E42164844158F1015B1829760A2902189A50C472CC1784FE830E03B410304B691ACDFBA2386283712C554406932312398050C07E19ED034DE4D701B4534B661827448624429A9A11934264A9080F098D988EC09CDC207696B2B74C40DB3D2322409C790B4788C12CA2812D41862B88448107B42377F8BEC00CD75104B82398A8123A289826F9C0520BDE582290E09745FD68B20AB3BBEE9EA2F1ABE81BD429624508C270C0929012F4938900E14B201E7348804E64A3E40EEB4EB96B5E967576D6CA7BDF977DBE9C93F504B03041400000008008EA99645491ADFB99B2D0000118D00001E001C004253435F5250543030322F4253435F5250543030325F312E6A61737065725554090003BC1898547518985475780B000104000000000400000000AC3C0B901CC5753DF7FFCC49A7BFD0078E9FD0F7EE249D04B264D0EEDE9EB4626FF7D8DD93E08E7899DB9DBB1B6E766798993DED2998921DC031010727D8016C27C6916327862A5231B82029278E89A950954AC5A9A452B820E0244E4819CA76CAD8E5AAA4F25E77CF6F777677B6E00A4E3DDDAF5FBF7EEFF5FB4DCF3DFB2EE9360DB2B72C5BA3E6E2E8BD92A9CB8621EB9A6199A372794929CBA3E76867867612FA73D3BF7490CE24192C68255D51E549C9922CB23579AFB42A8D29DA5856361449552E490BAA7C32493670B0945492B395C545A56A918D0C5895CA4B6359CB50CA4B0038C4018D982A99E67DE401D25135C8A166B42D4826109889C23FB504EE4F90AB66B2F1D9C9743E1BCF2422C9FCF978269B48A7F2B389C904111734CBD24AD392018812B817B5522AC7B44AD94A2025F894D5A50290E68C5E508AD6F21C195696CA9A21CF483053B214AD3C473629E694AA49568CC24D699A251B0868662BA59264ACA5E48B002ECF9111A7EB82622D63DF59592ACA46A45CB467AD53CC9C6201BFD89C041950E5458B111A25839AA1C8658BAE0B433AC5A02C2D03D5FDF840698CC20030D54A1B801AC83710C0DE6ABFA5E936B6751797E5724A4309E6D67439490616A4C2CA92016C285AE48624F07ECC5C1CF3F17E8CF17E0CD95E2E82DCD633EE4C2A865C40B22C72B4E9440B561ACB54DC09F172A50478C482877B28FE2EA78F3189F6CD93BE22906BCA966991BDF32D489C64A088BD282F4A15D5CA5A6BAA6C911B5B4CA46030ADA7285B92A2727286D853D6DEE84DADB03040C0B37151334A923525152CCD5873351C58AE94E8ACAC6C59643D3B18154B51E11C21DD7D78482A20590EBE0EA65AA8183E3E0D9624A5CC37DB9A2C972B5D6538951C734F992A02C738EC51B4F3925A019E1D692DD6B43B894B95AAA88F56AEB48E4441835C6DE54B1D6EBDD48C3387AE344F7A4C941968C59E565A610BB7D76467912B56BF25977455B210C7582B1CCCDEE4F80C40D66DE1A9E53BDAE43F587C57C75AEFEA826F1EDD595527E46FA698652302FBA77380787E8457FC3DFA030639DCCC6EE252A3B547D09EBD9110B0BBEB50114751F9466B07750BCC7F2EA3E30F788FD190169A1F06D7440BF3A47B016C08B0FBC656ECA6B6A6AA570C0EDAD85D31D0CFBDF1C08F1F7DEDA1631D482FB208083D10925044E052D9DBDC91F42C53F34BED765657152BA2AADA45B9083A40B51A051AAFEA866C9AD464EC6FB15117165DA26E68006429B2392D01DB0FB5983CE30587F9FD2652849A6491618FCF8DAE51955DE70C73051D6FADA0597B0AD34D831C0DC9D5B82A97C0369C01E7A2BBDC05BBD3575856D4A20163368DD4FC251593DA6DD933CF22075BB1CF030DFA0252DF441519518E460C435A43BCD54F7C7FF753AF4A5FEA2442827499CA25992A8978B10B7FC3A4F1909BCAC9556B4A91558FBE6C69AE2F430B9AB60246672529AFCA2AB8607915784F2D664E29C951B27E19986BA84A7925075E5AB6A264C8ED81C61CD9029A6619B25558C62022BD2A1B8BA07249B2592A17963503032D578BD020ED0413EB2EC339D9CA033216821AFB090C6FCAE2BE79B63B906B29BBCAD95D84925F47FA360700DD5ECDF03CD9E4193660EBE068C0A01C6D6550CED6CD02EA7639B832F2A20C2A59A8E7E40E571A9AA65A8ADE8460FFE1B745A19851388574305551558B6CF21E4D402A4B78F8075C1DE01EBACFD601FEDCAB4B16105EE68F1BF863CD8A70468FB4A1CEFC04B90ABDA7994243D8B0A019DC95EF8640843D41208CD1383FCFD2456B8CF6E079668177D43B6983B78F02E2C081241E161C98918A4508C4FD8C4A942D79894AAD6F110C6BCA0D64E873160E353E1FE4CF78C82DB2C1838046ECA8E0A074CA250091D4880AB13DEE9E13B63D60881F80E3AD0FC059FF647E027A40FC9A5AC415F6016D8A990000A5C09F871473A6B8182F2DC8C5A26C03ADA7075E599173CB7028979679B788DD10C914516CBC6F503167CB45AA7D32EFA2F9838FDFEBDD1E2FB707B19BF39AB3AE17F144B56AEB9832C9004F221A68F2DC892F38ECE90A1F4C26DD4936EFD070567457FDBDB442FE63484B86A42F5BB0ED168ED206457A756078B9A0D9A83A58D7945FA95812E563E3B0A7CBCB4791F6FB19D967682C2CE67387EC67CE8EB1102993E60BAC318FF391B3CEE9F0123300BD7E5236AC628850A8D7F6AD75039CB889D6C49DF74EB54383B091619DC9D91932E6DA06A70583AC44794A01AB7F61196C27466FD447D21108D36588CF8B7427E61CD9C1FBD1F84ED264CEF69F269D94914BDAAA8C9A8710D44E4789A86BA6421D196C15727093795F7C4A90EE8B987227885085FFD7402E9842173C12D8ECCD3C21425B554044AD980A495AC0ACDA780857B8169684E452F62ED9B922AF71BDED2A6945990BB81FDB5CA6075ACB741AA0B9B20DC2D98245293188AC176CA3A70BCF89E3311D6FE4611A5F34446160C6338B2F1E1448735FBAC519A2FC882D8361976966BDA7367AC6CE5DF3888C76AEB9B8C2840B3375B380B0618F2284376B597712DF6057A5023AE48B7E6741D1DDCCAF60E77DE41B4E8E87FB19C65FD7D1442C5CCE57CB5D6F5A87B836828F9D4ADC99CFC493915CE27C3C9F4BC37F331C7FD3A898E2AFD95B00FA81149CE95C269E8B9D35ED2C9306E7B8E3BF78AAF3174F961E1A83BCE01C11210E32C18F2F4515CB3C47064B9AF354D55FFEDF35EB9553854B238F3C3BF2CBDD690D1945D96091C145C86A17D535B3525EC20460BD9BC95297FFFABB7FFB951F9CFAC98F21019D22DD18A4CA60AB865DA85405DCAFF1F0B39FDB3DF8C45BBF4533C8C838CBAA275A7220C0E90770A127164FE5E219A06E83BB2E0FFDFE6EC4B8FC4F5FFCD57B40DF9C4D9F2E507927F576936EEE965DD33A581B55D9DE9377A21D6E6E1B103B66E45A15805944578DE18E61D0689DA1453DD0271B041DD825D3339EAA73F39DBA3312EC68699F0353EFFF7AB0878F572933C3A6B0407AD4E692CB51D246959A31CD530271432CA4670C50ED0F89CA87A7A7798CDE8F6B78A302DAE158730C1E9C8EF0A580A43DC58E4810072D4023D243201F10551B9A31E381E6B60FB51D91CD38BF4EA2C09A1A3A3FBB934C97BCE24234997AECBAD970207C06052B66B802865A327CA90130E7A8E286C1CB8C4458D44E34ECA21E802C6111632A147202320A16251EE4E984AFA736E01FC70CC3EDB2333150888D4E88EE518BE62ED313ABFBCC063DE63E324493AD1759E4B566E81BE27D511943241BD09216B260041C5545B3C1FBA80E8C54759B9F27B9531948E04A8AB576E8ACEEFC3CD0422FA843A88B8C83DCC17462723219F7D47909C5DED43051ECF5459600F49DA9F485364E8D1BE3B8FAD095201D4A719EF414962BE5158898C65B454C2E96184EC17223F565F4DD8727B1DAE8F666644975466845AEBF62F0959A147C6B572ADDF5F76F2AB7FFE1ACB7F67BBCEDBD535C9E4265947459F41D5997C5D36D20B1D322DDC948349ED4BD3F54896EE4D22C3AB1DB67E1FFE70263376C7ED46E9CA6D3A39FF9FA3B2F25775F4AFDD937FF7DECAF4E9CFEB586110E42C723C3A832BA2F4AC0F634FBE70EAF8DB09C5F1CE842FD688031F40ECC351AB83B60C0A6E56376BFDEEC502140C17F12B06B91A2400B3550C1D60A152CB64A2084DE44363B1BCF1E0E290621115A0C2F3C727FE7B6D5CBBFF7BDDB9E4FE7DE3CFD1F1F9E181E747E5D0E10031B0D10837760AED1C0DD01037562B8FC81C430D8580C47C28AE187A1C5F0F2D53FFD934FDFF3CFB7BCF0EEFBCAECFD91950F4F0C4F38BF1E0F10031B0D10837760AED1C0DD0103756278FC0389416C2C86A361C4F038211D97428BE1F1F4CC6A72E7E59EBF7EFAF59FA67FF7DCBF7D7862B8E2FCFA728018D8688018BC03738D06EE0E18A813C3973F9018861A8B61C22F86F0EF2131F4721D5067B3103F4AFA8BF6BB5C70B44E3B7C010F17F3DFC8805CE458484ACF6078A614EAEA78DDCDD392AE4545A5572BB03685EDF0B5A92980E669074DA068DE7A7DEB04E3A49B40EEA6E1D144DD89101C19873911CFBEF393179EFBDE61F1FB2F661FDBF583736F1072F13DAE5A59AFDABDC06A39A142C63A5904C4747DB9F44C7E327D2145977A3E7017DF08BD8B2BBBDEFECBA9EE15B2F0D0F1FDC94AE2D146BBF873B60B6CBED47861E14CE8851F7C2AF5F0F0C23DD7BFFDFCCFBF76E5FC99FF6EB4F0B7C32DFC56F8854BF77FF6DAAA76C7CF73BF7DF21FB74FEE68B4F077422DDC510DBDF0378B4FBC77F5A517EFBAF0A35FBDFF8C1EA9365AF895500BB36B1FA116FED4C2995F7CFED5AD7FFAF1BDCFF47E72DBEDAF365AF8D5460B0BCEED937EAE6085703B3EF8B38BD1BB7A5F7BE6E0D28B4F7FE5F66F355AF835CFC24EE9B328E834156A5AF062B547EFCD848013D3CBCB8E68E47B960C4D5B5D6BA792C62F4DB9C66D4333E33687EFF9A625BC64B70DEF02656453AB1805795A81E4A2BC84644232B5885707C2DC58A2770CB0E20C5612B2DBBA97E0B89F8A1E0693FD5ADFBDF9358F37B2DC37E7FB5B96C23D2FCC034AEC49D27D5F4536D65ADF30B803C1302F343873A2957251752EBA990543D12D55B6DCDB7240AAD31B86D4AC0D7C1267C2D8146778EB9936B05D9EC7F53F324FFA572543C19BAD80645F2B24E7392CDE2C68A004DCE3DD1AEE66580002CF15318140B6BCA755B6CC76D571EA5F5F4DEDFF996EE7C878E9E560C8935073E1A53B49068B32930BD7C80EFFCDC2402D69B71A0027CF229DE9C464AB08AAE61A94278202AA701B36012E557827087B47EAAF5C796F64E2A5AA2A8B123DAF0BD8056666CFDEA054F667E233E94C2E0FB462E7DBAD677466E37778603D2F05F835072F30AB3884456DA7C66DC21F6913FE689BF013A1E163B3685E43B3B23B3619C9C53DD01B29B4799F3A8A2532D3924ABA0FFF6C9BF86743E0C77E319A8DE53333B9F1F123F9C37032F7B73A99AE61BD8E888FEDFFEE3DD7DAA7734B9BC55C8AC555FC7E7A3B714A33E06094E0909697E6E89D9235D3924B93F222602826C956FE9E9A9AA4BA37AD2D8EF8BA32EC5C2EA2454A7DC8075F10909BEBF8998AA553B9F89D39DD23807DCD38C3EEE962E958AEB21A95700DC3B885639C896422D3F11CF8EEFC7464C68B78C87D3B09E4FB266F3D17C9CEC4337986231B4456538179BFAA3083A81BF22DE0C51BFA6B0D1FBE0D2EFF52F1580EC2142FCE4D8E0E032D659678F8A60FF3E9D3913B01C56CCA4751037365CFDDC8E7C2B989E4B3E9D94C2C1E7E3FF4B27A967ABD4082B2B14C6226978CFB081A6B8E32B2605A8654B09CF0C0CF798E39998E45923E4A875D7D486A0549F553B495CFCBC4D91EF3D1D9D4A41FC1761741C617F3046E2D97988EE7E7D2291F8A8D2E0A3437735A590ED4EAA974663A02FF4462B974E62E2F86A6012F453CE5FD56C1877D93AD45C948360B1C8A4CC6335EDC5B5C4DA04739A9E1357F1F8A1D1CC56C26993F1B410E6582A81CA198905400C46B0552E9AC84AC3282E8DA6CEF3A918C530124CFFB096BFA82966D5A5165148ABA5A43B0238EF8F44C124C7FD68B77B32B8E98A6AA01276730CB088B27277D333D571F30FCF0CDE9817DE4FC1B686AE4786E32459383C0B3773E91C9CD469289B936B04200AB185605BFE5AAC1BA3991CD27CEA4D2993858CF338954A4D69ED4DF6AB0E713A764466A4B6682453AAEBB8ECE7723A8FAF357E797DF66FF5C8F9929BDC1DD09CE57619B19BDA46925C0673F563DEDB5AA73FF828AE1AC642E839DEFEE7DFD3BDFDD7ACF3F74928E2932A08206338DC34FA796C1292E6B6AB1AADF769AA5C217FBE0375D14F777BF4584716C5C021E1C193D7AECE61313371F3F71781C7F8EE0C0AF43B07762E258B58D789BE649AE37EFF0BC853BD02A05A173ED1770FE6F89E8D714075A05251E04A7FF803CF35F2F4F5CB1A392CE36DE29BA68DC8D7405BC52A3D71857E47256C69CD4C24BC45B3C777663CB121A6E0891E6F11E0700021336CDD77F4E086183458E9A329EC891FD238B10F88C2C2CE4CD8B9A956754E68B963A025999218F38CA36F2D111EEE63FD5E15342DE0938378CD02B96230B6B2310B38F48660135B3135CA753528EBEF49F57DE7FE7DEE1B3D38FAC3CA67EFAF420F0795F2B3E3BB9E2C28F7EE3FE270F7E6BD2E6327EA0D9D49F79B86C230959A4889241F062858A4A5F2047C990522E18B484CB3E34A88D11A3A41FF45FB6D8B782C39EB9E16FBFC5DC490D3F0958E7D0E1DC72DC0311A28FBAF095ED84771A5F73A783CBF127B501E9350D407CD12944CD4A59B114490D8E9ABD21F200E59D7747EB1C6E86BF7493B1A7F09DB4FF52FDE27B58BC0B759DAF46580145B59EEC5D59708C21EF42D489220063570A231EDD711342809BD85E962F8ED4059D7B0FEFA30E175C523C9F9A9D8E82AF0B5539F4F1346893CC10A009B8995982276C36E2C3D3B47C297CC9437447BB440FC5D2C9D9E994433622BC02CCC0BD04AD4B019EC26C17837167DDCEF6D6ADA3BB2BFCFC714AB7E8A415981320BAAF3662D3F3CE2ADD1F90CA9E76A91CA02AE1D2F85C6B1A7B3F208D7D6D73926B00A3D256801ED6CBE805653ED552999BD409034FDB6C3289D87785BBE05BF3816F00C6FE2404F5D95864264E8D4253D7C5EE24FB3EE40DC0D8072E2B9780644CD743DE8AAAFF8A36086D2A8DD16B3CDBEA7A50513695A5F2A8FDCD6FCCFDB307BEDAE2A682A199A6252D780070A91F8239E69FACD78F6CF57CB85D332A5499827E9C879AC334D41CC666B5E100841A1DF3D167FF67DB2F7BFA726FF118E2DC9BAFFDDFCBDF86E1C31D5F10C8B5DEBA54FEF0C4E113478E1D3D3171CBC49189FCF163C78F9D38DC4B04819ACD7A5764FF8D0673EC0C7D83C2EF9F69462FE914C876F65A8545620C20BD702F8460BDA45B2007ED804133F22D6910C836E7AD44DE97910B2DC20C7C914C5F99A8AA5B51F3A1F3955604B2AB6E214FB624901D75C34E622E902DEEA027CF13C8D575937CA9691056A79221906BEA06FD452A81ECAC83700B3B02FD36A36E14EB5302D95D3FE4C9E5057243DD7840A21EC4334F75C7B7FDA0545120C32E00CB750532D240405997F87ABEF8CB1C416C752A568192B4737ADFF2C1851C816CAA03494C0A787341568BF934B647C36A267B0B2390213699D7A09D675EB317F0560D1DC79A73CDE884334ADF073884405A520379A4E6F9A83333E6C31BB3A958CF9E3D9BDC6CBFF7CA7BA22BA1C5DF69F0ECD7496F04B2D541E58B79E02C3903DEA002D8EE5F9BF76EA945C3FB876F30D13F1568F09B282F6A02D99FD48C25305D457959AA9863DC48E16D2556AF19736081BCDE7C1E304CC70422CC014F41DC121D060B92F49AB7697B00E60CE6F3965292B358FB8779E704B2CFD393CF1F397A225F965765E3ACA4EB72D9B577C726C605D2730A73885B05D2B977DF79917C849C1C205DE4944036DCB0045E4152D5AC62C9F4E375B07D7B21950CDC4EA58C2B8E8191554D801FB3279E14C9ADE4B601D2414E0BD4C3B765397B4914C4DC6C415645C53F29801091A2A4D369A0340305C9B4721AFA6081DCBCD793A533AF70D29BD95356EEAB8711C914393340E2E4AC48FA487F3FECE39C4806582B299241D64A894464AD19910CB1564624EB582B2792F5AC050C1E66AD3B45B281B5E644880B68EB6E916C62AD8F8964336BDD23922DACB52092ADAC5514C936D65A14C976D65A16C955AC75AF4876B0962A929DAC5516C92ED6D245B29BB50C81D68042DB8B5E6289E46A720DCE5D15C9086B5545722D6B5D12C975AC75BF48AE67AD074472036B5D16C98DACF54991EC61AD074572136B3D2C92BDACF59B22D9C75A8F88643F6B3D2AD040AE9D93DE4B3E239203E4204E7F5C248758EB774432CA5A4F88648CB53E2F9271D67A120E3BEA7C969EE069F7E85DB5775F83C32792A7C91750B9BF289263E43862F97D817459CB0A4CBB3ED932CE401B0C8ABEAC1579B02C905BF606FC79AA3ABD0C50D54E0CC10472A8ADE3D94BBE2AD03A8C1AAB18F845A540327B938D02A9004242912692AF91AF0F923F227F2CB0A28410F447B8FE9FB62B8FAFA2BAFEE7DC3749268F21DB23C01002014208210804544064070DCB43440911116336224B308BB8942A5AB548AD5AB5883F15B4D6F8C1A5200241C5E286D685AA5D6CAD75A1D6AA75FBB96FB5FD9E595E665E26F09EADFF9C3B39E7CCBDE7DE39E77BEFB977DE04B2AAA6FA66BFCCA98129A3BAB5B9A57165C379B535E5002C804971DCE1F8A1FF1CBA5006084BCBC949F5AF8BDEDC2BBDD921352A21587DAAD52BB9D3793DD87560ABB341C282B316B24917E38DB7CDD338EE0048A787302CF27CE23C233983F7D1C362F023D206D6C2FEE33D9D1E177E2A07EDD7EBF4A408D3B8F3C1904E4F8B4867DF29834E07849BCEC1C7223A3D2FE230773E43D3E9F722EAC65D1C8EEAF4A2C80D0E384ED4E9259175E7F8A35A9DFE2A820C0E3C3DD2E9359166F2A10E8674FA9B286571D059A24E6F8A309B838F25747A5BC439EC9EAAE8F4AE3022DCD521AE4E1F88420FEEE2304DA78F449ECB9D4F4575FA54443DB9F3A9914E5F88A817777550A8D3D7A2D09B3D1BCF3A7DEBB8ADFDE69228986CBD06A3B3E5F77D38F60683CE29C2C9E3D83B1F3AEBC2E9CBCE5B0B3A7793BFF3631A6374CE104E3F76DE2AD1395BFEEECFD62B293AF790BF0A62FA653AF712CE801867B4CE7D8433909D372F74CE97BF0771ECCD0D9D0B8453C8A44B4F304158FD18CCBEAD3A9D070BB788E3F6C2742E16FE10F6EF35E93C4CD8C5ECDDDCD1F908610E65FF6E8ACE5849EBCE776C807CB9C5E501318AD5FBA180DB5DE0C8AE4D535D5575EDD8343E12CFAAA1795E53437DB9E036D66FA7187C348F0DF3513C0E0360CBC03BC6E64D3068348DC11CC558ED99350DCD3251D660B65B5553D554139BD40C9E6CEB4F6109EE69D2A712B68FAA7C1F3F49E39968C5DAEB9D5707C7F2F7CBFD428AC1C77379988F937569A635D1C86FDE9B5A654FDBE0391630F15C69C41AD25221C38558833942C84821A3842097280A40C160CCE30AA97A11DB2777DEA9268D4F91DA460B1923E448214709395AC85821E3848C17624DAD13841C2B64A2904942260B992264AA10ACDA55430D96E0E5ECFC741A4EE63EF8792B6A4467BA901942660A394EC8F142CA85CC12325BC81C21D6B04485CC13728290F9424E14B240C849424E16624D2015421609A914627573B19053852CC16374ED99D10CC7928F5388E034214B859C2EA44AC81942AA85582AB542EA84D40B5926A441C89942960B592164A59055421A85AC16729690262156ECB508691572B690350056593115C2255B0A1B569DDDB8BCB61059E2904305C471F6932C5FB97A451AB7F99EB1B3E9C3586C0C2D5EDCD935020EECACCC87EF0CF31D7C17CBD71A9B5737D556D5C8D138D3B187ABC465950F0DE01A7C0FFF0A19046F6339AE91CECDB5567CF356394BAE28534D71A76424A1458A637E40B34121C1F7F20EB1E43E4462C78037D7B658A39DD8BA7321263AFB20296690353958EBC6C3A758C9F40A0F85DB798F587CBFCFE27ADBE2A2008B0391B5477DA0C1F3FFBB410F1CE187F8D762EF3E207EBDF3EE30561EC5433BB564F023FC6898B7F2632C279F0D2D9E7C635820C4759181F07E7E429A7CD2A0515426E8FE145371A25B0E69FC0CFCDD35D5DEB038B6F3B80C4D7C0FC3E003FCDB303FCBCFD9157BE69517902B41F03B089A6BBD1DEE53DC45EFC4DD0C6FD020D792FB7303D5D3F8CF4CB30EED86C9645406BFC87FE9C62FF1CBC07174C5FD0CCB6197E287F29057F855A9F23554D9ECAD72DC77B51B517290FF2675BE61ED051536AFA95A6DA7481985D5FEFD9B7CCFFD787A231634D6B5C43E9D03E3FEC16F8907BD0DF79D50BDC2DA1832F84DD91252FCAE41E368AC88DF4F412235ABA9600B12FDA36DD64740E0E65674A5B0ACB063A933B138F1EF4D06466E221B141D8700FC99C1A3F9D3307FCE5FC4EC1955D8D2680F1E80C01788B1B45377350CFE86BF96A0FC17532FFBF6D1854DB5F5009DDA26DB01E1AD03FCDE8A3E6005D5E877DACCB8BB0CC58AC2A42985A7E21AB6AAB1A5A10E4F25D5BE30540A469AB72A6462D97E9D2992FFA5C7AE0D956E6B228D32E3873D36B11BBCC11A0C85ACA8BFDB1BF85CD0DB9F4C471EE65905DC64F5B45717351A2A5B65498F7324DC1D1B1B2CB75C955CE6FEDFFD3A416CD4A45D43F554B9321CBD24F41C83B0123DFC4412739410D40D95A7FA4835C86BFAB8D5001C66B6AEB2A07041EB6A3190695A108C0676A5CEB9B579445C256832A773D5862A50FDC502E441DD5D3FA95ED1B8AA36DEC363719462890D3558158ADB14C924EE3884DBF8346B8F615CC05024B6BAC053F65665A81235541EFF30CC7FF12E8AB5B0C1AB6DE73C22E61EA30AD754897B6852186A94EDE258F2A714CF2A07CCA9516A8C7090F7E4BB352EB3DECA749EB9EB024CC71C7A3133E39CEA5AEB5DF2E0C79C1B58ABA1C6A9B162F1780FB6744CF231975AD90CA43935A1D554B2FB391D8E88460C35494D147B26C74D7A49341750B9543B55AA9DE6E9665DC32AEB454C60A57B69A899F6033A0E88E83A53554D8DEBACD3563436B736D5C6507372F1210F0E6C3DE7A6C0C520A0AECBEA0D355BCD126F9BF39D5CD8D3F5A8747D1E9CB07896ED72F3A58F277E977D594FAD2749AD277BA0BFF6ACD6AA15CDB2191050C72998126C054355AA4562C0299E87B1ACAA79D9340CA395F223D5D45D86A14E534B447B29528DC2EA26F9F6A0EF0067E92849D303F31F5D95E8F4808EDA78102A865FEB0A7E9C39A1B0BA235F2F9CA8AB067B696BBD33A6ABE5F6CA0801ED3056025A829A663AAAB88B5564577BD3D67D56EE7D8EA1AAD419588428A07559F295A4A996B804C7C6E3AE3241757658B52AE4A57987582EA529CCD8F50054759E3ADF508D6AB518B8B68B676A557B81E85E0840ABB2876464D28769EA2275713A6CFB115CA45AFA50C834F830D3977B4E9819C74A533F664AABB3BFD96CA8CBD56561B55E6D60EAD7A1186DB454A7D7D6CD947FCB30A3A909EB2C7505DB6FC87B2A8B36B6D81A2EB4A6A92B99C25602D0DC5C558F06AEC6DA4A5DA57E163742BE81BF36AC7EAAAE43F8D90E9E63FDD2C13DD43949886CB263E2B73F93E770C2F64F34E4257A1A408A34C2F05029AE74925F0BCBD2CB2EBB39A5E194DD9D32C329339D32CB29B39D32C729234ED9C329739DB2A753F672CADE4E693A651FA7CC73CABE4E99EF94FDA8BF551638E500A71CE894839CB2D029073B6591530E71CA62A71CEA94254E390CA321E570A73CC2294738E54829B15C430A8972BD6C14A2BC480EB550A6CB32DF2AC75965581205D98EC3388FA76388D546A2D03CAB5D0A8D2AD94DC7B6D394393CB734675A3B95EFA369254B77D2ECD20A8E0EF772E60EAFE0F19AA97979F34C0DCC1433C5CB9C6FA680996AA67A990BCC5430D3CC342FF364330D4CDDD4BDCC0A530733DD4CF7322BCD7430C366D8CB5C6C86C1EC6676F3329798DDC0344CC3CB5C6A1A607637BB7B995566773033CC0C2FB3DACC0033D3CCF4326BCD4C30B3CC2C2FB3DECC0233DBCCF6321BCC6C3073CC1C2F73B9990366C48C78992BCD08983DCC1E5E66A3D903CC5C33D7CB3CCBCC05B3A7D9D3CB6C367B82D9CBEC95D36A315B85B9C6EC05666FB3B79779AED91B4CD334BDCCF34D13CC3E661F2F73ADD907CC3C33CFCBBCC0CC03B3AFD9D7CB5C67F60533DFCCF7322F36F3C1EC67F6F3322F31FB81D9DFECEF655E66F607B3C02CF032D79B05600E300778991BCC01600E34077A99579803C11C640ECAB9D2625E29CCABCD4160169A855EE63566219883CDC15EE675E660308BCC222F73A35904E610738897B9C91C5251B28B6E1A5F6C16CBDF9BCDE28AED082556D7831A08282D544A5BE816E1D1ADF40B3BB8683E404D7EE811416021886E2B2B1956BA87B66AB48DB749045BB717907516E5DC2E5777D25DD6B712B2E96EBA075A9B709D2A8D00B1987E45DB9CEAD7E2CE14D97947F556446E47037BE8BE106247CBD95956521AFB635759C970FB8FEDB1864B2CDB4A630D97D26E6AB71A2EA53DB852D6D5FDB80A59266458CDCD05AC460150F3A0F9003DE8C0481164BD51F1AD300500B2B76C58CEAFF7D0A321727D353ADC4293E115A5398F41B8DF2714E32D68018C94E63C01F96FFC72208B0D3329903F05F9337E3940C6469C54C89F85FCB77E39F0C6069F34C89F83FC05BF1CD063E3900EF9EF20FF835F0E14B221291DF23F42FE27BF1C8064A35318F23F43FE17BF1CD864035537C85F86FC15BF1C3065639601F9AB90BFEE9703B16CF8EA0EF941C8DFF0CB015E36926540FE77C8FFE19703C76C50CB84FC2DC8DFF1CB016936BE6541FE4FC8DFF3CB816E36D46543FE3EE41FFAE5003A1BF57220FF7FC83FF6CB817936004620FF04F2CFFC72C09F8D853D20FF1CF22FFD7220A10D8BB9907F05F9377E3940D146C89E90FF0BF27FFBE5C0471B2C7B79FCBFA778B8BAC5F57F5C59FE6FF9FA422C12345A8429BD92A6D02954458B691D9D0ACD25F4309D46AFD352663A9D0750154FA133B88AAA791DD5F02D54CB0F531DBF4EF58A69991A400D6A0A9DA9AA68B95A472BE455063766F84BC44C26CA4D56CC4498CB864538641BEEA09C1D356B103511D6449CEA13BB7173AEC44D84D34423DDAFE144CEF91239110E8B86E1D7706267ADC44E84BB8B46A65FC3899E0B247A229C251A397E0D277ED649FC4438221AB97E0D27822E96088A704FD1E8EDD77062E81289A1089BA291E7D770A2E83289A208F7158D7E7E0D278ED64B1C45B8BF680CF06B3891B4412229C2033B5CC5557042E90A84529CABF0F5AEABE06A4FCC55CA300168D48AD5DED93483D6503D9D43EBE95CA0F879F42C9D4F1FD00F389BD6F268FA219F4C17F01ABA106E30C87503FA106E206F63DFE5B841A1585D641BE5CC41B61B5C6DB9C110110FF5895D37B8C6768312D128F56B386E709DED06C34563845FC371838DB61B8C148D32BF86E3069BE006712383BEBA93C89DD684618F4C4F4C171AC662105D8E91D98091F909FEBA02BD1FCD63ECDEA77C84DE171069CF49EFB1D2AC68E7F19BA8773B1FBB8972EEE5493790D1CE536FA0D436559917DA42695A1B26DB4D941DE1E9D2EF28FADCA68EC9D33A44FD61FF8CB2081FA7B5F3EC3D1C0D91743F8AAEB7A99CBC14BFE23CBF2246218A1168E3B7F352FD8A27F8153118510C441BEFCB4BF32BCE17C5504C11A1114558B4F1C63CDDAF78A2BF46444814D1D1C6ABF2B2E2DA5EE0AF1291124594B4F1F4BCACB8C64FF2D7898889225ADAB84F5E565CEB27FBEB44E44411356DF4615E567A87E660682E2C2BB98FD6EDE14A6452BC589C81174B1C4511436DB42F2F2BECD73F55F4CF8FD347584511526D74555E5637BFFE12D1BF244E1F335614B3551B9D9E9765F8F54F13FDCBE2F4318345317BB5D1C8BCACEE1DFA03A1BF54F42F88D3C78C16C56CD6A61676F22A4C66514C64015E75BA7F7431AB4531A3057855955F11D35B14535B80579DE157C43C17C51C17E055D5FEC785092F8AC92EC0AB6AFC3562E68B62D60BF2AA5A7F954817A2481182BCAACE5F27728828F28620AFAAF7D789C4228A6422C8AB96057915728E28F28C20AF6A08F22AA42351A420415E75669057215389223B09F2AAE5415E8524268AC425C8AB56047915F29B28729AE19DD6F7DA011721B503BC9257C95E87F62437627D6F2FAEDF414EAED1CF8195D723AFBF8126D28D349B6E06666EA116A41457D16D7413DD8E79E70EDA4F5BE92072840FE81EEE865CC2A47BF948BA8FA7D12E5E4AEDBC92EEE7F5F4206FA487780FEDE327E8117E9B1EE3CF69BF32E94935849E52E5F48CAAA003EA5CBA5E5D4A37A86BE9462C806E567B698B7A8A6E556FD26DEA63BA3D94497784FAD2D6D058BA2B3483EE0955D1B65023DD1BDA80646213ED0AEDA4F6D0A3747FE8557A30F41E3DA465D23E2D9F1ED126D163DA5CDAAF35D293DA5A7A4ADB4CCF6877D301ED197A1EF8BFFAFBC6FFB312C5FFA644F1BF3951FC6F4914FF5B13C6FFB313C6FF3509E3FF3909E3FFB949E2FF7949E2FFF949E2FF0F92C4FFB5FF33FCFF61A2F87F41A2F87F61A2F8BF2E51FCBF2861FCBF3861FCFF51C2F87F49C2F87F6992F87F5992F8FFE324F17F7D92F87FF9F781FF2F01FF5F06FEBF02FC7F0DF87F10F8FF06F0FF4DE0FF5BC0FF7780FFEF02FFDF87E443E0FF47C0FF4F80FF9F01FFBF00FE7F05FCFF06F8FF2DAF67E28DAC780F6BFC04A7F2DBACF3E71C56261B6A0867A872CE52159C03FC7F19F8FF0AF0FF35E0FF41E0FF1BC0FF3781FF6F01FFDF01FEBF0BFC7F1FF8FF21F0FF23E0FF27C0FFCF80FF5F6020BE02FE7F03FCFF36F42A53E83D565A266B5A3EA76A9358D7E672586B64435BCB19DA66CED2EEE61CED19CE05FE6FF8BEF1FF2789E2FF1589E2FF4F13C5FF2B13C5FFAB12C6FFAB13C6FF9F258CFFD7248CFFD72689FFD72589FF3F4F12FF372689FFD7FFCFF07F53A2F87F43A2F8FF7F89E2FF8D89E2FF4D09E3FFCD09E3FFE684F17F4BC2F87F4B92F87F6B92F8FF8B24F1FFB624F1FF97DF03FE733F1AC405349207D2442EA4D95C44F55C4C2D5C42577129DDC447D01E1E49FBB98C0E02413FE0A3B81B8F6593C7F3913C81A7F1445ECA9351FF54E0FF74E0FF4CE0FFF1C0FF59C0FF39C0FF28F0FF04E0FF89C0FF9380FF0BD5B95CA02EE581EA5A2E54B77091DACBC5EA292E516F72A9FA988F0865F2C8505F2E0B8DE531A1197C54A88AC7861A797C68034F086DE289A19D3C39F4284F05FE4F07FECF04FE1F0FFC9F05FC9F03FC8F02FF4F00FE9F08FC3F09F8BF10F85F99C57C7BEC88642DA55BC710B3E5882447953CC077546A7B796B6568D88294BDBCB83284905C50A909DDCD77C7863A041D5BAF7481F660B832149AD7CEDBDB79A73DF4EC395A298F6D4E95C78E56CAEDA31598B28B773BA6ACA4B0759C728C98526A1DA8246E4F3B3F8099687B5CD3E3634D8F8F353DDE7AEAD2F483BCD7697A0946C1FA4983CC8249B5FAB0B7BFF666DCE858A3A3ED46B334F9C5A3D3D468E888D690925DFC788EFA25E925EDFC9B6DF7F2D373863D4199C26DE7E72BE7ECE0A787EDE2DF4BE5045B5F88553008B74B3F7AC1FF3757EE276D5B4549E52EBA69276D168E7BC31FF88FCE0D394E8BA925C3A0B3DD91BF187301130E60FD0E0C6DFF494EC8F8AF1AB9D5BC121BA3DE50926A0C5B6D0FBF1E8A691D8C3DC4CE95FD5D23BBCD747929D651CB753E4D9E8E019FCD3BF81D4B8574FE674C65A0F54E0206B59DDF4B9FD46F077FD06F510FFE704AC10EFEB86091DB8D4FF8D3CEDDDCCD5FBA967DC55F3BF26CA7C99492DDFCAD2BFEB7EC2B77BA5D859CEA951633C773BB4A73C57AA0B89B2B36028D53194EEB2A536505C923EEFD3D54AE2397AD5C898D308674B8A9ED56BD5D1D53F509AA23DF6DA39FEA1F241FE8CA07A9C2802E0C71C5C56AA8238EE0B9CA934D8309BB55A9AB303CB88F235CF9C8C0211AED9A3F528D89D56F1FD6A695F4CFDFAD8E72158E5663030D38C66D60023F1AF0841F77C5C7AA895D8DE1940E9DA98E4E0FC7085D7476ABE9AEC68CC05E1CEF1A59AE66051A39777BAC8568A0C209B16152F363C3F89FBAEEED258A308CE3F8EC83A62C04B92F05422048584C145DF42F74B541D69AB5A4A679C84CF27CCC43E6A9424DB3B4EC5C065D749161BBC21276110A41100481100441100681100841D0FBEEF3CD6EB69BFD5CCC33BF6577E69DF785199ED1824D7E4E5C22FF020A520614FE0D3826C7531D8713CFD85E24C5297E41A96E0EBA47C5B8953C6FD3DD6B0A9EFAA15923E5113F3467A4C2F2D248A52566A4CAF2CA48B5E58DB2ACBC55DE29EF950FCA476545F9A47C56BE285F956FCA77E587B2A6FC54D6955FCAEF242610C034CCC0206EC62D68701B66E376CCC15CDC813BD1C73DB80F0F18A9711EC27C3C8C4730820578140B8DD43A8BB0184BF02496525F86A7B01C2BB012ABF034569373066BF02CD66203F58DD884CDD882ADD886EDD861E49CB313CF631776630FF5BD7801FBF022F6E3000EE21039C378092FE3151CA17E14C7F02A8EE3045EC349BCCEFF7203A7701A6FE22DEA67F036DEC1BB780FEFE3037C48CE237C8CB3F8C439C708AE93FA8D99332D39C0ED69DD60172F617F419A12D212B50B9BB8B4EB0523E83A8C50BF5F47BCB7EB456075C9CBB29F31E97A6D77082F7BA105E90927A4336A77EDB3F3B43F2F839A902943324C421E6B8390FDAA91E707133216DD1D93C9B84C2DEAA5C64B9771CF4DB5339E7B722EA3D9DB9AB2B7C3FF1A416F34B1DA9B7C1F465D6B07ADB71AFF00504B03040A00000000003A99964500000000000000000000000010001C004253435F5250543030322F2E73766E2F5554090003FFFB9754C53A965475780B000104000000000400000000504B03040A00000000003A99964500000000000000000000000014001C004253435F5250543030322F2E73766E2F746D702F5554090003FFFB9754C53A965475780B000104000000000400000000504B03040A0000000000A55995450000000000000000000000001A001C004253435F5250543030322F2E73766E2F746D702F70726F70732F5554090003C63A9654C53A965475780B000104000000000400000000504B03040A0000000000A55995450000000000000000000000001E001C004253435F5250543030322F2E73766E2F746D702F70726F702D626173652F5554090003C53A9654C53A965475780B000104000000000400000000504B03040A00000000003A9996450000000000000000000000001E001C004253435F5250543030322F2E73766E2F746D702F746578742D626173652F5554090003FFFB9754C53A965475780B000104000000000400000000504B03040A0000000000A559954500000000000000000000000016001C004253435F5250543030322F2E73766E2F70726F70732F5554090003C63A9654C53A965475780B000104000000000400000000504B03040A0000000000A55995450000000000000000000000001A001C004253435F5250543030322F2E73766E2F70726F702D626173652F5554090003C63A9654C53A965475780B000104000000000400000000504B03040A0000000000A5599545FF31268C350000003500000034001C004253435F5250543030322F2E73766E2F70726F702D626173652F4253435F5250543030322E6A61737065722E73766E2D626173655554090003C53A96548613985475780B0001040000000004000000004B2031330A73766E3A6D696D652D747970650A562032340A6170706C69636174696F6E2F6F637465742D73747265616D0A454E440A504B03040A0000000000A5599545FF31268C350000003500000036001C004253435F5250543030322F2E73766E2F70726F702D626173652F4253435F5250543030325F312E6A61737065722E73766E2D626173655554090003C53A96548613985475780B0001040000000004000000004B2031330A73766E3A6D696D652D747970650A562032340A6170706C69636174696F6E2F6F637465742D73747265616D0A454E440A504B03040A00000000003A9996450000000000000000000000001A001C004253435F5250543030322F2E73766E2F746578742D626173652F5554090003FFFB9754C53A965475780B000104000000000400000000504B03041400000008003A999645DC79091B2B3000000295000034001C004253435F5250543030322F2E73766E2F746578742D626173652F4253435F5250543030322E6A61737065722E73766E2D626173655554090003FFFB9754FFFB975475780B000104000000000400000000B43C0B941BD5756FB4DEFFC7EBBFC1601643C01FD8B5BDB66317872269B55E19ADB4485A1B76DD8859697677B0A419CF8CBCBB2425E400819440CB29894D03272181937E484F2190E6100E2D274929296D0339B4296D024D735A7A4248137C1ACAE921BDF7CD9B99379A916674283EA0D5BCCF7DF7DD7BDFFDBD3B7AEC67A45DD7C8F6AA640CEBF3C33789BA2A699AA42A9AA10F4BD505B92A0D1FA58D59DA48E8BFCB7F10216D29D25B542AAA5C96C6444334C8C6D44DE269714456467292268B65F96671AE2C5D95226BD8B0B4589172B5F97979D9206BCDC165B1BA30923334B9BA0003FBD9402D5E1675FD14B985449635726533DCE6441D10CCC6E04F3D823B93E4BCA95C627A2C53C825B2C968AA702C91CD2533E9C274722C49FAE614C3502A93A2068092B89772AD528D2BB5AA91444CF029A78A4540CDEE3D2E978CC51932282F54154D9A1261A668C84A7586AC93F5F1B2221A713A6E5C510C49C3817AAE56A988DA4A5A5A82E1D20C19B29B8ECBC622B64D486249D2A2D592356B40D6F3B201F432E724494F599A374C4463A457D164A96AD075A14BA510E48545C0BA1B1F288E31E800A21A190D4003FA1A0EB0B6DA6D28AA056D606951AAA615E4607E459552A4674E2C9E5CD0800C25835C9A02DA8FE8F3232EDA8F98B41F41B2574BC0B7D52675C6644D2A225A06196D3AD1809546B2356742A25AAB009CBE22473D64FF2ABBCD24126D9B255D254057970CDD20DB6703501C338722F492342FD6CA46CE58294B06F950C0443A0CA67594244394CB0C9D7EF329676DF4F22028E64080B3765ED12AA2312E160D455B71241C482E57E8AC9C641864B579306A865C8673847877E121A90167D9F001986AA060B8E8D45B11E52ADB6C305A0E555655E15432C81D552A080CE2202768C7C4720D68B63798AD196712E32A155117AE4C686D8E820439D2CA96DA13BCD4943D87AE344B3A74E41948C56541526131B75337CF2213AC6E43AAA865D140182341304C7D9367330058BB81A796ED689DFB60B15DED0FDED571D73CBAB3659590EF8C9B9A8D08E69FB6A384FB17C9B95BD45B34B2A799DEC4A586EB8FA0357B2D21A0770750108751F886EB3B5503D47F3EAB82E5D815523BA3AA7074736773DDDCB148351A558539B52C1BD1725959924A40562A2848A3C4B2AA49BA4E4FE1CE00563963D1CAA89A02830C59D22745D8C9950193A7F8E130BF5B478C90390619E4CC586C854AC180DDCD78BE3B98E7396B8AC96E8D8C86A46AA22C55E0B81D017DAD3AD485A3DC555C94CB250DFA2C1CA94649C93A55851237CF205704918F1B0DC2085C5F476503410E47354D5C41B8CB9F7CF9C2079E171F6A234292ACD2E59B25945BD2BFB40A3FF50081E4369543F551CC4BCB9C3117405319D06239057B43C242280C7D07D865CDA40FD4E09CA231D5B41914ABF904861DBD0B464C71C918A12D484CD39188F193D6F06D7420766C03E1333BA6C452091C0B83ACE304285935A405094176CD8354A71DC54C9F7340517CBE843D23850DB28603403D1098BE6E1154F7CD30442C47CBE0ABE0EE19629B7DBA989C1E0896D309F764A6E03B643DA6944BEECDC414A52C8978DEBA643D0933E422A270199040D6A74AF389CA9C542A4925D6B81A0E3AB88127A5FC2248D9C2226BEEC36650D5A53CE33DB4F5CAFA7415C85A06B4581375905C0C58EDB4F0E4EFC566467C46CB4E84135396838D66CA1C78158281AFCC39640B0E724DE1AD65CA99641113CCD1C99ACA18DFA9BA7005074F131734515D34C88E20B5650D457C552078B5A858A02266D3B85BCA4C2FD145C641AE89A7631F6D7713B24B534CBBCFE6F65BCF8C1C23217C42C5E539A0A3EA4267C06EE091E98156372A6B4EA3C22E7AC57FA3A78321B72F18B963FC544B510FB7A6A81D1DB425A405DC04A7054D5EB23A2E6B3A583E3856684B67C806D6037E88040E4889EE449F21E7B376349263D45BCDC0AEE7C180EA745256AA28A725943C1C1183D37A3246FA54459791F4688520C8D00D4D328A8BF89424ED4B18532489B00CFFAF005F304628721C58CFBBD6602F4FCBC0A220A28217EA33ABDE3AE10A17C092E03D4BFC926D27A51526B7AB2A4A49620CEEC6EF8CA7BB82793A09A399B0F5C2D98245293208AC139425D784E7242BCD4BD050B40ECC1A9E686CD11091CF14378B2DEEE7D6E0221B536483DD45E9115F04ED8AFE6950FC625AEB7A7707816E9AC5F568E38AB31C801C0D7279A73CB36081414E56C26BBE9C3389D16055AD0662E67257A6E12C38DEEF4596EF4BFE04FE1F42B757B7DC5430C8C35427088F6EBA6DF077476B11D23E4E3AE6C5B2BA2882049F46C4522452846D6E716C389528D392A31A962088E89ED716E64E9BFB88CC8E4354314F9F90721FA61E0DF9F5B973E75495D272103F2E840770B877043ADC96B8F1BE3402586B908ECC54F4BAE9841ACE73AF17211F80EBC693D717B28954349F3C9628E433F0DF946A22BA3B107E1D777CC0F7A44171E5B3897C7CC26602F5079167CF3CD0F6ABB3953B46C0153D4AFAC00FD0C17B5988C9867E140255C57E5A561F7AFBC5BB1E2F665E285CFDC4D307FE61ECE7C86AC412BCD2C131E926F1586D282756F5A149A5AAA0E3B9DA094AA8B7F3EACF5EF8D23F1FFEAF37C1431C675C06AD3CE88C4AD7C0D1D03EF5D8672FECBDFFF5BB2318BC44AF3103A47D8164F0F177FC58174FA4F3892C60B7C65997F93F7F3BA4DDFACA83EFBE05F8CD58F8A902159D14DD276E2AAC11610E8863447AEB1D4ACB4F608D68719A6B41848E891C6519069BCEEC721C770C9D5A706410E3465FD5C0BDC226896AB3B4C7A16953ED1E7F9782B6D963BC96BE035B58FF322566D8D009508F595472284A5A48389A4473C528963389F85C0EA0768604E582D3D13C3CE9C63578FF8736D8760BDD24BB217C089AB2A658BE17C2A0B944047A29F00758D582644C71A3990A5799CE9CB23FF6A92D848540D694294B3CBB104CD60B5DD51B76840F1E61C52C13C0504B06685637E43C15DC30704D251116B4EDF73BA07B201E9A47EF11999C84D8C9F4872F618193ABA53EB4D98EB194D36405A120106BED6084138BE6969F8B4A5C6A831E73171A7DBAB95E749EA50DA1AD9FB5C5247406AD818638970325608B2AAA0DD6466560CBB26AD1731FB32C3D495C493656AE9C50ED7FB704C80535089E18C0CF1C4C26C7C6520983F44D491A002A1AF2697096C2A7CA904B0EF3DA9A698318E92E59193C38F7F6F7F0510D2EE6CEC383DADA1F12D323C849B9E8096EDA9B6BB055F372B96C39ECF83DBCC33E0EA39986A2BA969AB84B8275D1558EADD94C197505732AB7D94EA5C09C4AC1EDD831D9F988F5E51A2A55B13FDEF7B5833BD7BEAC5F70FD4F773DF78BA39F2764E92DD594B81C7F864F9ADE6B28E9F2F0C247BABAC08D2B8C658EA7E952F36C17829D16EE6D6917776F59FD07DAC5776E7AE7F6ADFF7AFED6D4271BEDE294B90BFCAAB8167691AF6D67F885CB2FFFF0F18F1EBCEFD93391A74E3F9CF978A3858D460B7B773C146AE14F6F593D1019F9E62B5F9BBBF597779F177FB3D1C24BF50B0FB185B7DA3B069E9317691482FDFB1B4506F8F737FC91F9FABD03FFA6F7FDE2CCB7BF18230BA3C963CDBD5F9C92A0CE2B7E4B9A3E24EF49E2F749F3CF75FC5E3E617FDC6CF61EF7F6FA184CBE63A651C7099F0E0B978F5AED373753BC38A068906E8C26D247F213B906E43E0B4C5F7C5FE47EF0B5AB8D373E745BFAD88183CFBFBA78F4D50F88DCF7D81F77F990DBECF52137DF31D3A8E3844F8787DC77852077CFF144F4DA7422974B34A2F71920B5F0BEE87D561CFCEBE8E4F78E3EFE9F5FFCDFBBFEF19AF51F10BD3F6F7F7CCE87DE66AF0FBDF98E99461D277C3A3CF4FE5C087AF767A6A632D9FC743A994F3624F97D40ED07DE17C9BF723873DE7B3F8EBEF5FB237DAFB45F1A7FE90322F91FD91F8FF890DCECF52139DF31D3A8E3844F8787E48F842079677E229B88E6738D0D16F99BD006EBCCA9B33FF9B38F9D1BBEE9AE676E7FE6DDC92D8DECC613A12CA5B0107AE1DFF9ADBD3FFFFAE5D70D3FB961E0606636D9D968E1A7422D1C21A1177EE2FB7F7AFE1FEE1CE9FBEA8B274E1EFCC284D068E16F845BF86CE885EF3DBC69FD67EEFFCA999E67EE8DBCF13F990B1B2DFC4D6E613B497889A0525FBE694EC5CC71F197AE3E9E56274B6FA92DE56858C187E30B0BB3A47D4EAC9630651B945FA5F534CB6A4D63431B976499433FFBC35BDEFCCC77EFD84FD35A485F4AA40DF4732B3ED3BB57A185E831579BD3EAEAA8BA53647D51A956995B5A9F9F5E8FC53839A5A615254F5F8F54DFD227EBD33AC47071B1B868DDDEF529EC7EC2BC530F714F94E1265C358BF5249A589120520C93C4B6B73865CD82E0609303625254EB36324BFA34C9A8696658056BEC0FBD46D6994703DD5A35AF986927BA793E1411BE6DBBB43DCCA525CE79F9ABC6E7E59E17B66E1D9DFDC1FDEAFDB153DFD97BED8116722ACE461D7EAF4A92885C9A251DC5C55AF524EC7677D06E1D28719C82451034D3498B9CB80BC6B54E6B5612CB768F59270052BF3B48EAEB57AADCF0F73F92AFFDF2B473003472A0E5BD53580E012231B2CAA0C5705CC9811A31C89A6C026D78219E49A713F13CC4B3965DFA3B983A50C36F2FD9E6FBFB30A33F371D6393C69259B35930C8E6A15D43DB62B978213B95DFBD7B6F610F43761B7216E8301A44071F199E6D7BF0CB77FEE5B3319E14875A3DF53638871A9811FD704838ACACCC074AC44F1938B567CB0E19FB6C320A3619BB190D936368F3F927D552FCC239819D8ABFC0311D0B9AA29C5E69457333E41D9CD734CB62CC60D9C3A48835949BB0D42B2BE954074ECA3AEA37D44C7875244BE5529882B4711C889769F3721908E75140B89F9A1A069275EFE710B74E41EE0CBCE5E3F4A2CFED618AB49FAA49DA4AF015E475380CB581C68813AB554B65BB8E512F6AB26A9425C329860454EDD630A8E6ACC1680574E81B67040F9E690DB66E1E71FDD159D27D5AD4642C5C06203B82801C636301C6050D8480A5B6AE0E57F8E70380AB001408E886CB827483B9ABC8E1D79E4FEFFCA56AA9832E380957843C091482730EDAC1709524932F4C2223EEC2515F2969D506C0C933485B263916942AAD2BC973106D03AC701B16020E56589F86AD5BBCE57F7CC12D16F82DB368C9B9CA33EBD34D0DF536C5D2D140B4F157C133FA8E25A9E6C827F3A944D8495D99EC91423A3A197A42677C1A355568ACDAE363D13C0F7D2D1DAD9F2A0FE7E50A1C01B1A2BAE04FB7087F3A047C1AC639C610447C6790883B1A6A1BE9BB67E7B76EBCD812F38D2DDE94D499A96E5A723AAE6820611590F6EAC20C2D4D5BD10DA93226CD0384528A6C64E52EF46C7B9CDE80B33250857D4B253CDAE9FFE713240848CB01C74BC927AECFAB1CF99B562898F5CC782F034E0F9D255C6C42DCC0204E45B3208C79308285C9E8140FB8DFB9FF07F45D93371E8DE6A612D9820923E787565386F16F9FE87ED8F5BB16E0E1867EABC505CFC7CBE360AEB325386EC744AEE9836CFA64F47A00319D7661C41508B06A4FD7DCB596AF18CD470BB9CC74369E08BF1F5AD46F0662BE08E5E2D9E414681F174223CD4146E77443138B866D67DD94679053997834E5C274D09187945214CB6E8C36B279D984B9C7426C3A3DE606B0D9019075390FBE5BCB27271385994CDA0562AD030295CD8C52957CA57A3C939D8CC29F683C9FC9DEC04368EA3952C0E3FC3B1D2EE8EB2C294A457339A050742C91E5616F7024811EE59482AF43B8409CCF404C67538589285228EB87E5108584A8C2402CDC112B1322924AF3C36BBDB5EB642A4119903AE646AC69F583B969B92C2153CAA7EB10B6D991989C4A81E2CFF170D73BEC882BE5B2CFC9E9CD9988255263AE995C7111DA71D79C0ED847DEBD81A64A8E39F9E3D4CBF63D7BC792D9FC7434959C69012A7882B266D4F09DB73AA8EB93B942F2483A934D80F63C924C47EBF589B764C89A4FEC9088D48744104246B66D53EB82A140AB2C5C4C1CA0820FD055DB86474CB0EEC035183474DA59A7369A756A03A74736E9347CB3A2540055EB7199FBBEB26CD74D510E4F88FA229890F6CE579FFBD6C61B5F6A239171D25386C3610A33BEBDB608F67651299796D5DFBCC6CC972C75C1275D1477F9298308BBF1CBED40DE3DC3FB0EECDBB3DBFE77103BEEC011CB2D78C43492E1A2592E3BB22B2848A073ADC488FB652E9AEADB15E4ED7000AEF90279F88DA7F73D62B93B9116723D0E182ED7E393EAA065D627A56A4EC2A8D1C05CE006AEB03FBE28A245C08C03565FC14020C2BA59EFFB9CE08F18645497F0A80FED1C9A078F6A686EAEA02F2946C1C4B250D18D21889B3469C896E2A18F0C31FFE13E4FE0DF0666D7BE0A883D508C3FFEE8DD93FFF2DCF455E79E3C35F12320E58E2052DA01DBDCBFDFF6F1B357FCF99845487C09B6A92DE408690109992988915EB080C55A9996B6C748BF5C2D6AB460027D40AF7F1923DD20E09261BE8F39C8CD0D5F5D1B7726596FE179B22F03361E76A1F56EF02E5DD885AF2349F2D3D89A5B6C58B62DAA77662F6A30C4E5D982C72D57654316CBFE1E37EF5EF750DAF13B1AB0A919BE1A2E6B4D613B693D9FB9F4165E7B842AB6AD6396CF554447EE861C18D590454A1E56F8405C95466FC9C9BA457CACC1E6AAB434E47158B7EFD9418D3598B344213D3D19033B19EABEC54553BF4D9A671D8FFE2153033C6891111FBE44B37BC2A31CD26DAD22DD1FCFA4A627D336DA08F0312006EEC56F5D3AE0618C93D191B7D75DD5DABA1EBCDBC3CFDF4DF1EEB343128C2710DC571B91E91BF62A1DEF13CBCE56B1ECA122E1E0F854308E5DEF13C7EE9629C924C0C4D212800EB3D5C41784F970A0303749D6F99EB6E9540AA16F0D577E5FF712B50FC4EE140404B97874CA7C63A0A9E932DF1870BD2CED03B10B4C563E09819C65609D6BC40875E8222DA456F075BDBA2CE286E62F3CF5CF29CA497CF72D259D96CA313220A172A524C0E82D46562FC236F0A5BF9379515B900C30A44E0B35A41BE83B84F8DE02FEB48275479822EBC56A7151D1503D7B4CC76A6719DE66AC73AF1EFEEDED846B5E43DB7B9E8D7A94E2E6C16B933D00DFF4F75C07ACE3BA5BB8FA9CF0CCC2CCB50DCB7EBFC983CEF90EA9214831E4FA1B521E61DFB798C0C9A16F9AD1B7DB6B66F5277D71D3E627B3A95D163FD973A72A1A80A795495BC31EEB16A0427C312B0158EF290DDC6EDFA30A3F6E7C8FFAECF0950FFDD3932FFE7AE0C2FF7EE8F5DF4BB471B533BDF310AFCC9757F49A15EF24A237B65C3623FCD4FEF80F6FD90CEBF596CDB83A661A759CF0E9A82F9B81659B94CDF0BF3240A85A69FA2E05552B5E79F7512D6DE9CC715B65F7D4ABEC362ED1ADF2FFA8E1F84943A6AE0DC5D44F5F90FDD8D06BB7DDFFBDC7D5DFCEBF365E0C60EAADAD33F53DFBE35D1FA69ABD3E4CE53B661A759CF0E9F030F5DD16988AE3CFD9BCE8F5E185EBA6C2C50FBBC6E6B07DD51A4A46BCBF68E1677ED219CC9024724137F825499717AAC3D6EF6FC49D9F20726C4D1B68F0A2A6E8BA21CE710370A977C06D673F1FE3EDD9C8FD884A5DAF605E510B77B29CC320CD390CE2D7E5861D10924666638FBDBDE99D8EAEFCEB2CD6BC36F1DDF79E7E16BAF744CE08E442BE0C60DF9E437BF71DD8BFFFD0E8A1D1C2E8A1DD073B892050D7DAABD4ADDF4AD2478ED0AB6E761215AD93B40964B379FF6D06E4E680CCDC4D1089779276816CB7824A452B345D5FE00A630AAE6CAF10108662593FBDD72E971D63E302E74ADB0BE402CF425C264E20E77BBAEDA4AF4036389D5C0E51205B3D935C694F3FA876965C2017793ADD172002D9E219E15C1A08F4F5614F2FDE7D00D7BD5D5C9E5820977AFA7D92C07E34E36E0E5CDBF74B430A64D01960E6515D0C72A5000532D4807539675B5E8AB993EB7E04B7EF497C796C65925DCBFB5F1F08649D6748724CC0374CC0112D64F0FB705899352FD105D26F4E66F79E021960C098BD12D088D00178D1693FC5CD2736396E4D5E6B3EF30A56C037B3B0914378BD558250E0626C21E0179138DCED24974036DAA05C912F9C18BB830F2D8184EEB559EB867A30AC7DF0529DFE560B4D8124ABF38A4076A6146D019453495A146BFA0853436867CD8CFF883D16D0EB2C1400C2645C20C20C300A5827D26E10C314AFC026AD0E98D35B2818E06AE4F0EE18E61D15C80EAEA550D83B7AA8508518429B105555AA5A3AEDC0EEBD7B0E09A4E3306692AE1648DBF6FF23EECAE3ABAAAEF55AFBDE24273787908100873004B840121286C8242232CB7819254C0A2113D190406E020838210EE08C4395AA55AAC6AA75406AC18A4F793E6B79DADA57EDB3D6F2C4E9F97CD65A9E551F55DB6F9D7D6E724E7212EEB5BFB6FFACBDEF5AEBECB3F73E6BAD6FED7D764E0A969A3496C685284810648691834E2DABAD5D5CD358697FD306112EBF60A5FF709AEAE48EC311466BA3D01F1EBBF00C93CEA00921527426DB8BA584E2630A9D85C7DCD90DF57B38F9D290684CAE28DB605F369929545E166D5C522F08CB3436DFB51DABE3FE19EE2D5C7B2A0BDAEB983495A685680A4D37C9A0D4548CE36C9342BA36CBA4345D9B6392A96BF34CEAA26BF34D4AD7B5852675D5B5C52670D0AE9D6352A6AE959A407DBBB6DCA46C5D5B6922BDB36BE79A94A36BAB4DEAAE6B6526F5D0B572937AEA5AA54996AE559BD44BD76A4CCAD5B50B4CEAAD6BEB4DEAA36BF52696B2766D23DBDBFE714781148A9AD48FF2E4DA2613AB0CBBB6D9A401BA76A14903756D9B49615DBBC8A441BA7689498375ED329386E8DAE56C2FD813F1E514BAC2A47C2A90CBAF32A950D776993454D7AE31A948D7AE33A958D76E803B8B55EBEF29CD6B752E2BBFA003F732E926DA23E67BB34925749AB4722B53B0715D0D2EEB37B7D35C41A225CC785D7D85B321C2342EDFE7338FEDACCEC71003923E311527E47C297427DB7BEDB5539B1AE4C31D4C8BF2E7769408F97424AEAE9974377D2F8DEEA27B586F3CB3DFC72C21C3AA36EA95392D0045CA9BA28DF5EB6BB65656CC423842A8C86F730AA9F39F054B6582901A4E4A687C1D8CE607329A87A4452504D9A3DAB09EDB1D8C02BB0A6CB509242451D48E5B32C4B69DD7DD0B729B030206FD08D322CFA78D6524D6E11FD341E9F021B9077259EFF10F839E117E32FBBDCF35E85F4498C2ED0F0E18744444067BDE421BF46FC24D65FFD7E606BD24E210B73F6361D0BF8B288D3B383C63D0CF456EB2CF7113837E29B22EDCF6288F41AF89209D7D4F1718F49F22EDCA9D1D1C30E84D51CA60BFB32606FD4E8499ECFFDADAA0B7459CC5B1B7EE06BD2B8C6C6EF3BAD8A00F84DF8D3B3AFC63D0FF88420E777008C3A08F45DE9DDB9FA631E80F22EAC1ED4F1B187442443DB9A3032606FD49142C76BD5734E84BC79CF5D15151E8C5F6394483BE925FB9DC72EECDA0BF08A737B7EE58186C3B4D1F760EBB199C24BFFBB273B8CE60437EF7E396C37906A709278FBD4B6D83D385DD1F6D4B6F10F8EDBE0C60CFAB1683B3853B90DBBCCB30B8BBF0C3EC7D5760B025EC41ECDE9C37B8B73007B37737DC6074D270363E11D572F267F9F81F72E8CE82722C35915DF786AAB2F2CA71298CCEA6D644E737D454CF92988CCC6B85C983787088C33C04F3A265E015685EA149236824B0878B805515355101C00AA0585D455943450B58993C4CEB0F6771DC91084DC3861B7C9A8C0DCD66B6FBB05E0A8FC1DDEC7776F3AB6024DEF1C5BEBE67F2383E3DC463793C42A00D26F26DA48626793769F2043BF8F09972937C2105420A850C155224A458C83021E8DA609F48E71FD7F86C697A26EB23166E3849E1D9D2DA0821238594B03E88363CBAB17678EB41B4149E2F527B0A4641C5E74F0C0C5E2CD2D142C608192B649C90D3858C1762E3EF0421F640270A394BC8242193854C61F92B1124E0B3D8F9AE0D0C35663CF36B2B4467AA9069426433956608395BC84C21B384D8E39A2364AE9079422242EC812C10B250C8222176CF97083947888D32A5429609592E648590954256093957C879308658CFA64761A6F2B53411AC16B246489990B542CA85D8D24A215542AA85AC135223E47C211708A915B25E489D907A211B846C14D220C476E246214D423609D92C648B900B856C4530960C2B0C536F0CD7D46DAABFA0327C1AD390CE1CED6C6D19B3D66FA84DE17B3D36E36CF4F0F7B134CA5FD9DED47CCE6AD86B21BE3FC4FBF801962F25473734549655C8712BA6334FD5488C35ABC0876BF283FC03AC29F82196D7F832B879769638BFCE49D3224C15F9ED96277125364EF77D6EEBE762FC08FF507AF2283CBB75C2A3958DF66CC797AB2E0538EA03062D1DB281C3CE354FBDE84A64547828BC9F9F941E1FF0F4B85AF778B04F8F7D2376B76ADF0E2FFCDB26DD77860FF221E9EFD340926AE70F3B1087F20BDADDC9E467F87088EFE367594EC4D434BAD627437D4366072B167E8E9F975B1E3169180D17D47881293FDE4D88147E11F61EEBAADEC238B3FDBC14C4BFAB61F24BFCB310FF948FEA865D78F532D65610BC0241B4D23DE05EF91D8C4ECCCD743B0DD666727D8EAF7A0AFF8A6976E76698C82ACCE45FF2EB69FC1AFF1AB11D43897DFEEF94E97B6716F206FF469A7C134D46DD4D9EFE6DFB0D2F798B7F276D1EB37787C2D1CD651BF4B22A3D5CEEDDD1E9E3BA1E4F6FF8E2FAAAC696B79EE8DC717E472CE85D98EF84F25A7BABC8E4B7659348F107268DA1D122FE30098BAFD91B1F7BF7B7268DD2AC8F1181A34D184AB824DC9A424DCC8FFFC3D4BE9E1BCF4645EBC63F7F6A727FFE4388FFC8275AFA3332DC58AF270F81C0E3882D4B5523A661F2E7FC2771CA2F987AE8CB4F0B37545623E85436680384B5F6F75A2BC680CCACDE6BB45DDB5C65F29FF964086BC2AFF054621DABAB6FACA9C25349D61593FF8299E6FB1456FE995E9DC9B2664C6DA99BC8B96D4DAC31ADB6D3DE02F1265F6E4F86C282AF5F6C34B039BFBF28601A7D8A67E573913DD21E1DB468AA3415C2889529EEEEF4B1C636CBBAC456FB7FDB9F8E491F83725F5365A8AE321D99E27A4E8790D99E1A485A0C25007553E5A86ED20C5667BD62CD2038CC68AAB343E1E2A60DD241A6A97E61D4772855CEA5D1E16D1AC12DB3DA376DAA5ECA921E6065D6256627E5B5F575956D2DBCC58F926CB1A9FAA9BE62367902E28E41C46E3ED5DE9738DD672AE2CB2EF094DD4D992AAC06CAE3C7DA2BBBAD89223F36B94A1B677E8B798C0C6F2E13F3084A61AAA1DAC4B1064ACA9F3D0B614E0D55C3848345459F588BEBEC93FECE338F9900D3199D2733D3B79457DA7F9FE4FF98737C5B35D569AA447A3CCA155B5A41BEC5A4D647116956C5954D25BA07D46A88B889A94E57E3A43FE3DB805E02B7F3695C9A9D20CD9EE91A66554D9D7DB81FB1325635D524FD80262322C68CA9ACA22266AC536BEBA34D0D952D5173527EA7AF12B49E73916F328850D761F3A69AAEA689B5CDF85626EC1AFA4C193AD674C1FCD9DAE4E6C818E77E9BBD5C57AB116975BE2BF4576E6C2AAB8DCA26834F1B2B00095AC1548BD522E9C012D7C35857165D3715D3686F25A0AB468C61AA65AA54B49763A9112E6F90CF627B5EE9AC1E29CB7EDFF58FA1C206ED37E8A4C1888F01D8B5A1CA806313C2E5ADEBFFF0444395EBD4D63E1266287422B8A1B6296AA86A9D23C1B51D510D828C5F2798C6E477904F76B4B36D5F672FCAB7996AA55A857444D5319524DE488ADAD066A9A32373476B42D510521B151E546E2789538A6A32551942ABDAA4369BEA02552B1DBCB083A76B37BB4D74B723B495E9291991F08B3675B1BA24157DBB14C6522E6308330D3A0590C5DE21766DC34A513B9952AAF41941535DA92E0FA92BD4554C7D5B1523F5B6EAB4CAAA19F2CF91A6373420E352BB58FFFD95ABB1487DA3D68805D914750D53C85E0A44A365D5B8C175C8B2D4B5EAFA3633E499F81B436AB7BA09E6A54D3DCBFE3BBAD8EBA02542648B1E2980FEC2B1C309E93F00943FD1A2FEA428885C2A990A5033285576F8E56D9E5DA639A5E9945D9C32DD29BB3A658653663A659653663B6537A7CC71CAEE4ED9C3297B3AA5E594BD9C32D7297B3B651FA7ECEB94FDF417BEE57D9B5D0E70CA814E1976CA414E39D8298738653E462D65A1530E75CA22A72C963223248B4694BB64CB11E50E79ED853255127BBB1C639721591AC8C61BE6732C8D235637130532ECFB923A51F8631A7F9026CEE5794559930ED28CE76852E1EA1FD1CCA2528E14BB39B38B4B797CD00ABA7973AD20984956929B19B192C04CB692DDCC0556329829568A9BB9C84A01D3B00C3773896580996AA5BA994BAD54304356C8CD5C6685C04CB3D2DCCC15561A98A665BA99AB2C13CC2E561737F33CAB0B98E956BA9BB9C64A07B3ABD5D5CD5C6B750533C3CA70332BAC0C3033AD4C37B3CACA0433CBCA7233D7595960665BD96EE6F9563698DDAC6E6E66ADD50DCC1C2BC7CDACB372C0EC6E7577333758DDC1EC61F570331BAC1E60F6B47A6635DACC46616EB27A826959969BB9C5B2C0EC65F57233B75ABDC0CCB572DDCCED562E98BDADDE6EE6C5566F30FB587DDCCC4BAD3E60F6B5FABA993BACBE60F6B3FAB9993BAD7E60E659795957DACC2B8579B59507667FABBF9BB9DBEA0FE6006B809B79AD3500CC81D64037F37A6B2098612BEC66DE68854B0B9FA25BC60FB206C9EFDBAC41A54FC01758DD026AC22382EAF7F41DB2BFF04577D05EED1DB410D1470E8E67C333E005DF2D291C5A7488EE0DD2E3FCB8B8A07D791ED9AF9C9CCBA5B68FBE6F9FF5CCA4FBE87E68DD8A7AB2DC04A185E9016A769ABF085762214EC568DE76A907718343F470804AF1E39192C2A2961F3F2C292CD63F9E68B971A1DDB7A2961B17D1A3F4987DE3227A1C3565D79E402D607721DDBEDD6CC4BF39884D73A1B99F9E74E2C06CC82C347C045D410438503234EBA943F4748062761529B6C341716951D64F203CEC114AE7EDD880385094F52CE4CF79E5080D3A4E2441FE3CE4FFEA95234AE890910CF90B90BFE8952360E8E89102F94F21FF99578ED8A1038901F951C85FF6CA1146744C4985FC15C87FE19523A2E8F01282FC55C8FFC32B4770D191260DF25F41FEBA578E38A3838E09F9AF217FC32B47C8D1F1A70BE4BF81FCB75E39A28F0E45E990BF05F931AF1C814847A5AE90FF17E4C7BD72C4241DA032207F07F2F7BC7284271DAB32217F1FF2FFF6CA11A974D8CA82FC43C83FF2CA11B47404CB86FC7F21FFBD578EF8A5835937C83F81FC53AF1CA14CC7B51CC8FF08F9FF79E5886A3AC47587FC33C83FF7CA11E074B4EB01F91790FFBF578E58A7035F4F977F74170F50CFC7FC0335DB3F6C5F58016990CE01362FA5C9544A65B48C2EA3E5742F24CFD34A3A4EAB98E95CEE4FE7F1645ACD65B4862FA332BE97D6F2F354CEC7A9423155AAFE54A52653B52AA375EA32AA51F7D2F9F0AB9331BFE2F3E0576928D3B55FFD195DFF5A77DD8983DAAF36895F7D5332349BC9238D39D61671AC6C66D10878351CD7DA2AAE95CD41D148F66A38CEB55D9C2B9B534423D5ABE1B8D7C5E25ED91C120DD3ABE138D8A5E260D9DC4534BA7A351C17DB212E96CD19ADCF28A6E0F8D84EF8589B67C45D62CF08B5C75B9ED100647C418AE21935D2146AA20ADA4457D2667A90B6D051BA903EA6ADC49C199B6BFA14732D27971FB1E73A9BB3A497DD74271C30D0B37D75B1743147C43D3CE2D874EFD6D3DD53347A79359CE9BE564F77AE68F4F16A38D37DBD9EEEBEA291E7D570A6FB464C779B99A0875BA2F9C376E4D633D11D713B48972373DC49D3E90AAAC63CECA2AB30FAFE3C408F3E7909463F802869808C1E395BE941CEBF837A1EE4A17750D67E2EDE4BE6411EB197929BD58EDCC03D94126CA660E00ECACCE612197704636E5615B9418F68940C5832B966352B37A955D40F431B5D92CD638307F98C433C3140326E89DFCD2A2F37D9AB78965711C38F60E8CDFC456E8A5771925711461781C135F3CF730DAFE264510CB428C2F622B0BB66BE3F37A3CDBDA7789B8411466080CDBC2D37A3CDCDA77ADB44C88F20DC37F3C2DC8C36779FE66D13C13F82C0DFCC43723352BD9AD3BD6D020622808066FA3A3723D4AA39089A334A0A0FD0D6433C0BEB129E2386C2730416228084667A253723CDAB3F57F477B4D1074C440011CD74576E86E9D59F27FA0D6DF4011B11404633ADCFCDE8E2D58F88FE32477F81ADBF406024020869A6337333D25BF587437F6109C822B9A8CA7B936C5E625BBF604C04F8D2AC2E69677E809708A0C5CFFC802C11A08A8FF99DE37D0A809808E0C5C7FC967A15813511E08C8FF9957A15013A11008E8FF92DF33E56A04F04C8E3677ECBBD4D22FD8E20E5F633BF15DE369193479087FB99DF4A6F9B48D42348CEFDCC6F95B74D64EF1164EC7EE677AE9FF921B18F2099F733BFF3FCCC0F397F0479BE9FF9ADF6333F2C07225802F899DF1A3FF3C34A2182D5819FF99589F9ADEDCCFCB0A288601551DC2E754FCA8BC5DCA43C2EE70AD96F48EAC59548DD75DE7C922622FADE88E8BB076BEC5BF0EB3664D0B7235BD84B557427EDA0BBE906BA07117B1F1D44CAFF1A52FCE340A9AFE9214EA34778083DCAA3B1645848FB79351DE06DF414EFA2837C3F3DCD4FD233FC3A3DCBEFD2732A898EA86C7A418DA617D5547A4955D25115A597D53EDAA31EA35BD461BA4D1DA5DBD51BB457BD4F770698EE0EA4D33D8142DA17184BF70516D3038135F460603B3D14D84D8F041EA0470307E8F1C0ABB43F708C0E044ED25341830E06C3F474B0849E092EA167836BE9B9E04E3A12DC432F040FD08BC123F452F03D3A1A3C412F27F5A55F0061AAFE8908531D2FC2AC8B17616AE24598F3E346980BE24698DAB811667DDC08531737C2D42788301B1244988D09224C4382081315176FFC47224C53BC08B3295E84D91C2FC26C891B612E8C1B61B6C68D30DBE24698ED7123CC450922CCC50922CC250922CCA50922CC65627E3BFE9E08F30610E64D20CC5BF8750C08F33610E61D20CC7B40980F80301F02613E02C27C0C84F90408F32910E60410E63320CCE740982F81302781305F0161BEE15D4C7C3F2B7E9283FC3A27F3BB6CA8240E292CEDD4684E575339435572968A723720CC9B4098B78030C780306F0361DE01C2BC0784F90008F32110E62320CCC740984F80309F02614E00613E03C27C0E84F91208731208F31510E69BA0C1140CB30A967030B88493836BD908EEE450700F9BC1039C1E3CC219C1F7382B7882BB25F5E51E4098CBFF8908B3335E84B9225E84B9325E84B92A6E84B93A6E84D91537C2EC8E1B61AE891B61AE4D1061AE4B1061AE4F10616E4810616E1417BFE91F89307BE245989BE345985BE245985BE34698DBE24698EFC48D30B7C78D3077C48D307B134498EF2688307726883077258830778BF97DEFEF88309C4703116B47709826F2609AC3F9B49C0BA98A8B68070FA31B78043DCC2558998CA2D7780C1DE771F4358FE7349EC04378228FE649BC90A7F06A9EC6DB780610662610663610662E10260284590084590484590284590A8459068459018459A5F6F100F51887D5611EAC8E72BE7A830BD5FB5C14601E1648E71181422E098CE55181C53C26B086C705B6F3F8C06E9E1078000FFD004F0ABCCA5302C7785AE024CF00C2CC04C2CC06C2CC05C24480300B80308B80304B80304B8130CB80302B8030AB8030AB3398EF6979A37311A5DA6F4DE6C81B9D2C55F813DEB73C7898EF5B1E18BA38E930CFF96B65E71E1B4511C7F199DF6DDBEBC50ADC1668A3148404740B44A8A829C6CA43A55E41A18176D102128308D40AD60A282AF8424450C4FAC0C75F461331F628DC9E39B11A85C63760E22392901888AF883162048DC499FD7D67686448F49FFB64EFF7F8FE66E66677B3B3B71B26D45C6E0C3DFD19C957EC1024940FFB8D6EF4DE4A8589C4B579F95A5EBEC14321FBAC04D5DB4B78F57625A89E578254295D328B525A452A5EFD99A84B191DAFFFFCF77AF272973AD665FF255D6BA56BAD746DFC2BD0D2391941BA45F542FC1F0A7D9CFD5FAA85BEEDE54B963556B48645FB7BFA3F9890AA513EDA6B5490933D697A592483BC7CAF6B87DCD350DD2BFAE96FF3F2A3B0A15BEEA9CEC94F7572A16AFDD8261821286EC760352F3AC3BDC2EB6A0AC29CD8BA4B74EA6F4CC067721F02D2502C0EAA954F16F6FDF62750A97E00BAF1654AFB805ED0935F7AC2A4F9CAF6518572D269CE62B737E53709EB75D00EE2E9C90E7982354BF56DB7701B88B7FD95AA0ECFA809733876114979C4BA0C8FEF75509D9A97DF95D65575CB1FAA9ACBE58F938675CB9F87359B661C95BF9CDECC48FE662A3B267F877D00248B82481E37E613F24F57F8DF26FD495B4E9F7092301339CD45C65CEC2C8E9250A7524AB9EC6526FE6CEA07BBBEE0ADE7464A75E9984A2FA2B4F1F1A9DC9563B0D1A8A04A97FD5C631F42558E269C67CCC36904CCBE1A573DB225AA8488461A8751EE365E60EC81B38BC698F2031A6BF3F3DA724930744844171A877134DE59C0042370B1DCED18E11E63BE842E3D531F4E3CE573197CCA514452FB4474B9F1A873B662B229720A4D75167975D62A4C733A5C63BB8932B61BD9A138181AD1F453096638135C6712CCA459AE7198BD1DF639D4E468C15C36A7F42D6858F9FE4065D76FFEDC1DA4B7F9D4D218A45FF5699EC2769FE62BBCEED302859D3E2D542830DE66BCCB789FD1CBF890F109631FE373C6178CAF1907198718DF328E30BE67FCC438CAF895718CF107E32FC6C918BE94A007968029B00CEC0FA6C1816005780E58054EF0E926CD3AF00A701238199C024E05AF04AFF269916603381D9C01CEF469B1E62C6CCF46DC1CB0096C0643702E783D7803D8823CF3C0F9E002F046E82DC4F6CD885B0CDE022E019782CBC056F056B0CDA7259AB781CB917705783BF4DAB17D07E23AC03BC195E02A703578177837B8063AF780F722EF7DE05AE8ADC3F603887B107C087C185C0F3E026E001F053722CF63E0267033F838F49EC0F616C43D096E059F023BC1A7C167C067C1E790671BF83CF802F822F45ED2DB594CEBA5B4CC1E4EBD78D60FF2A9559DD164823CB5156879A8CE76226AE7BD484A3FEA04FE17F16E409CBF431EDE2B06A8CF1CAD7C4705647A453A4FAB3305EA0855E81A75F00E76D25ACE90A475743F328CC409435A49ADEF6A28D086B03A479B23DAD2C3FB1F51441B853E77EA14FAF6BD9276E13B1E4271A6371AD8A7658D8DDF90D4D6B10ACFF75AF10F504B03041400000008003A999645BF67F984560600002719000033001C004253435F5250543030322F2E73766E2F746578742D626173652F4253435F5250543030322E6A72786D6C2E73766E2D626173655554090003FFFB9754FFFB975475780B000104000000000400000000DD59E96FDB4616FFDCFC155CB69FBA18692E5E81E4C2B1E5DA687C4092D3058AC298E3D1664A910A49F96891FF7DDFE8B22C5FD222C902B10141C37933EFF8BD93EAFC723BCABD6BA8EAAC2CBA3E6B51DF83C294362B2EBBFEF9F080C4FE2F3B6F3A1F553D86AA0FE3B26A3C3C52D45DFFAA69C66FDBEDD95635DDAA5B7539A90CA4657509AD029A87BBFEECE8DBDB3A5B1EBFB9B969DD88161E68734A59FB3FC7EF07E60A468A6445DDA8C2009EAAB3B7F5F4E1FBD2A8662AEAF6DCBD8D8EDCD6F601450B1FF85EA146D0F5DF0DF62EFA67434AB9EFE5AAB89CA84B7C7A5995E5F59DEF8D71F57B669BABAE1F4B3E5B1F427679D574FD20097CAFAC32289AB9FCEF55616BA3C6A89E29F3C9A8583D9A43DA1CABEA32434244A47297ACAC9B72BCB2D265D394A39507934966BBBE614120742A8996112332B640922414440ACD54106B69ADF177DEFCD01957256ADBDCCD95CCE67AFF5D9623DFBB56F9049C63C85032BAFC8BFDF60B476F97E7E88B74778FE85485040D5473C27EEFECB43FBC38DA4723E5AA46A7FBA8AE55CBD9BE35682A7452A7C10F1D0BA99AE4CD077759EF765C41EDFC79A7F3AF3FF6F67787BB7FF8FE9F7FEE74DACF9021E3F692F353620CCEDFCD25D93FEA3F2F8A97D50765755695A371338D9F54E5356C2861ABBD9D8C9F2650DDCD184F192C2EAA2107D3783F7B29CAE1697D51DF94CDC5CCE017A3BAF16EAEA0026F6959AFEBFD74F6CF72F9198570BC1E5EDF4933C8EDDC1AA7AFC231A5DE87DA54D9B879A0E654C547DB8EE1F4E13AAB4DF1FF620C3F1C0D8E4E4F2E8647C3F7BD6FC5F3B4FFEBC5C9EEF137E3B7773EE8F5BF9D49DDA135DDEA4F796B988D00F3FB68FC85D99D7F5BEDCEBFBE764DD6E430BD4663CDF0AEE62525C44C5F8FF3AC19DE8D5D8E6A2A68CCD5941F9E81DBE6607E815BCFE2BF97C3082B9077DBF5055699BBAE2FF1929B59E189A2C45F5ECE97658402B36998580234A1440654100D1C08B789B58996C6A4E934792FD82E98B8EFBB797659B815BA017E423513CF59A44412F7713235638A2521CDEFEA894BA375F6B72B39D225D477658E4234D504EBE4D8A6BD656F7264F1C2ACB923878EEC0CB7461AAC8505F542A4F68A4C2B524E8DF35422FEE9E09F45407E9E82F314F59BE5CDF746DED8E62C7ECDE6966A16D1C89240DA94C810AB769C3043021E694E6D1A8B447E1D9B87FF2F9BAF26DEADECDE69BBA89846CC22503AB386EA10949DD6CAB5C011C10B8153BBFECC0C91C393288E4AEB32F6587D72C6B9C7942F216511BB8754E077ADCC5F28505975FD1F0FA4FB5FC01CA40234D78A88D086444A48496253418C8963CD422153936C01B36BE3517695AF6C1D67D6E6F09C07ECC347F561E20D54517BC765512EDD807F4D37B8C7FD0CE78E31762BD935D44BCCE720AF01D1C9B3025E0BAC7B105620589A9B992076F62686A5D810AB4413A504F6C742C45C316E7924E6B277DA4B76DB317E10D06CC1398D63C90018618A019126660401D684A6898A386771C4A22D39C78C6EA2B38624A25C07240A15EA9C4606398786586EAD660108CC2BFF9BCE82BFA2B416218F69CA9DAA094E1D605C12734A53A09CA75ADBE009D6DB0620A7723D028360C514F4A50834525189632709238193511284448731100A2022414DCA92E8BB8EC0C1B0DF3BF9757838782DFCB6CE8B217D040BDF14962014A1884D44AC0A638C54AB4862B0120A8824C43C888584EF1A96DF7BBBBF9DF40683DE17C72560E2112E6C535C348344D1140339B61191944992C421A6356A30C1496BA954DF352EA7676E1E3D3F391A1E7D7968C2305887466E9CC912C1536A6C4068C025665BCB888A84C194CF921480BBFAF35D43333CECF77687AF82F25265E3546C52534385ED193A3CD12655445AAC694914310C05909A463A951CB6ADA941B25135972CA058B311D54063B9A21189556089E090EA904701DB9A73C036EA9D94352630A126D830E04412534634B78228604A61F7E49C6F4BCE612837E14C15DA1A1B0602719A3877B62406746CECB3656C12A9A2E471EFB43214ACCD021D0B8DCAF2C75341405F9AA7EB899E29F04A57B43A4EC7F7EA4857FB66FA44349192A3BB98407022B14B22B1350961C0408840DB7865B65BB23D7BF6C5E8221C1F933E3969ADBDEAC30079E9DC3CF61ED3CC774C5914AE7F2F8B9799ED9D9E9CF4F68638E1CD983E796E4DE5676E7CF02EF6B3F76F6FE5ADFC056BCD5EDDFB0F557B34413E8073C55916CED169AFFEE0B1F3E6BF504B030414000000080074739545B20E98E79B2D0000118D000036001C004253435F5250543030322F2E73766E2F746578742D626173652F4253435F5250543030325F312E6A61737065722E73766E2D6261736555540900035B6896548613985475780B000104000000000400000000AC3C0B8C24C575D5FBFFF4DEEDFD8FFBC0F23BEEBBBFDB03CE77869B999DBD9B6376669999BD835DE2A177A677B7D99EE9A6BB676FF672466707704CC0C1097600DB8971CEB1138344148305899C382646418AA2384A146141C0499C1019643B32B62C25CA7B55D5BF999E995EC10AF6AAAB5EBD7AF5DEABF7EBEA7DF65DD2691A647F59B686CDC5E1FB2453970D43D635C33287E5F292529687CFD2CE0CED24F4E7A67F6923ED49D25FD04ABAA2CA93922559647BF23E69551A51B491AC6C2892AA5C941654F944926CE26029A924672B8B8B4AD5229B19B02A959746B296A19497007080031A315532CDFBC903A4AD6A9023CD685B904C203013857F6A093C982057CD64E3B393E97C369E494492F973F14C36914EE567139309222E6896A595A625031025702F6AA5548E6995B295404AF029AB4B0520CD193DAF14ADE53932A82C9535439E9160A664295A798E6C51CC295593AC18859BD2344B3610D0CC564A25C9584BC917005C9E23434ED779C55AC6BE33B254948D48B968CFDAA09839C5027EB13909D2A7CA8B1623344AFA354391CB165D1786748A41595A06AA7BF181D218850160AA95360035906F2080BDD55E4BD36D6C1B2E2CCBE5948612CCADE97292F42D4885952503D850B4C80D49E0FD88B938E2E3FD08E3FD08B2BD5C04B96D64DC99540CB9806459E468D38916AC3492A9B813E2E54A09F088050FF750FC1D4E1F6312ED9B273D4520D7942DD322FBE75B9038C940117B515E942AAA95B5D654D92237B69848C1605A5751B62445E5E40CB0A7ACBDD19B5A6161808067F3A26694246B4A2A589AB1E66A38B05C29D15959D9B2C84676302A96A2C23942BA7BF0905440B21C7C034CB550317C7CEA2F494A996FB635592E573ACA702A39E6AE3255048E71D0A368E724B5023C1B6F2DD6B43B894B95AAA88F56AEB48E4441835C6DE54B8DB55E6AC69943579A275D26CA0CB4625F2BADB085DB6DB2B3C815ABD7924BBA2A598863A4150E666F727C0620EBB4F0D4F21D6DF11F2CBEAB63AD7775DE378FEEACAA13F23753CCB21181FDD3DE473C3FC22BFE1EFD01838C35B39BB8D470ED11B4676F2604ECEE0654C46154BEE1DA41DD02F39FCBE8F803DE6338A485E687C135D1C23CE95C001B02ECBEB115BBA9ADA9EA1583833676570CF4736F3CF0E3475F7BE8581BD28B2C02420F85241411B854763777245DCBD4FC52BB9DD555C58AA8AA76412E820E50AD4681C6ABBA219B263519075B6CD4854597A81B1A00598A6C4E4BC0F6232D26CF78C1617EAF8914A1265964D0E373A36B54653738C35C41475B2B68D69EC274D32047437235AECA25B00DA7C1B9E82E77C1EEF4149615B568C0984D23357F49C5A4765BF6CCB3C8E156ECF34083BE80D4B750454694C311C390D6106FF513DFDFFBD4ABD297DA8990201DA67251A64A225EE8C0DF306934E4A67272D59A5264D5A32FDB9AEBCBC082A6AD80D15949CAABB20A2E585E05DE538B99534A72946C5C06E61AAA525EC9819796AD2819707BA03147B681A659866C1596318848AFCAC622A85C926C95CA8565CDC040CBD5223448BBC1C4BACB704EB6F2808C85A0C67E02C39BB2B86F9EED0EE45ACAAE727617A1E4D791BEC30140B757333C4FB678860DD83A381A3028475B19943375B380BA3D0EAE8CBC28834A16EA39B9CB9586A6A996A23721D87FF86D512866144E211D4C5554D5225BBC471390CA121EFE3E5707B887EEB175803F77EB92058497F9E326FE58B3229CD1F175A8333F41AE42EF6BA6D010362C680677E57B2110614F10086334CECFB374C11AA13D789E59E01DF54EDAE4EDA3803870288987050766A4621102713FA312654B5EA252EB5904C39A720319FA9C85438DCF87F9331E728B6CF220A0113B2A38289D7211402435A2426C8FBBE784ED0C18E207E0E6D607E08C7F323F015D207E4D2DE20A078036C54C008052E0CF038A39535C8C9716E46251B68136D203AFACC8B96538944BCBBC5BC46E88648A2836DED7AF98B3E522D53E9977D1FCC1C7EF8D6E8F97DBFDD8CD79CD59D78D78A25AB5754C99648027100D3479EEC4171CF474850F2693EE249B7768382BBAABFE5E5A21FF31A42543D2972DD8760B47698322BD3A30BC5CD06C546DAC6BCAAF542C89F2B171D0D3E5E5A348FBFD8CEC31341616F3B903F63367C748889449F305D698C7F9C8D9E0747889E9835E3F299B56314428D46BFBF6BA014EDC446BE2CE79A7DAA141D8C8B0CEE4EC0E1973ED80D3824156A23CA580D53FBF0CB613A337EA23E90884E932C4E745BA13738EECE2FD687C27693267FB4F934ECAC8256D5546CD43086AA7A344D43553A18E0CB60A39B8C9BC2F3E2548E7054CB91344A8C2FF6B20174CA10B1E096CF5669E10A1AD2A20A2564C85242D60566D3C842B5C0B4B4272297B976C5F91D7B8DE7694B4A2CC05DC8B6D2ED343AD653A0DD05CD9FAE16CC1A2941844D60DB6D1D385E7C4F1988E37F2308D2F1AA23030E399C5170F0AA4B92FDDE60C517EC496C1B0CB34B3DE571B3D63E79E7944463BD75C5C61C28599BA5940D8A04711C29BB5AC3B896FB0A352011DF245BFB3A0E86EE657B0F33EF20D27C7C3FD0CE2AFEB6822162EE7ABE5AE37AD435C9BC1C74E25EECA67E2C9482E712E9ECFA5E1BF198EBF69544CF1D7EC2D007D5F0ACE742E13CFC5CE987696498373DCF15F3CD5FE8B274B0F8D405E709688100799E0C797A28A659E25FD25CD79AAEA2FFFEF9AF5CAC9C2C5A1479E1DFAE5DEB4868CA26CB048FF2264B58BEA9A59292F6102B0D1CD64A9CB7FFDDDBFFDCA0F4EFEE4C790804E914E0C5265B055832E54AA02EED778F8D9CFEDED7FE2ADDFA21964649465D5132D3910E0F403B8D0158BA772F10C50B7C95D97877E7F37645CFEA72FFEEA3DA06FCEA64F17A8BC93FA7A936EEE965DD3DA5F1B55D9DE9377A21D6E6E1B103B66E45A15805944578DE18E61D0689DA1453DD0271A041DD825D3339EAA73F3EDBA3312EC68699F0353EFFFBAB0878F572933C3A6B0407AD4E692CB51B28E2A35639AA704E2865848CF08A03A1812950F4F57F318BD17D7F04605B4C3B1E6183C381DE14B01497B8A1D91200E5A8046A447403E20AA7568C68C079ADB3ED4764436E3FC3A81026B6AE8FCEC4E325DF28A0BD164EAB1EB66C381F01914AC98E10A186AC9F0A506C09CA38A1B062F331261513BD1B08BBA0FB284458CA950C809C82858947898A713BE9EDA807F14330CB7CBCEC44021363B21BA472D9ABB4C4FACEE331BF498FBC8104DB65E6491D79AA16F80F745650C916C404B5AC88211705415CD06EFA33A3054D56D7E9EE04EA52F812B29D6DA9133BAF3F3400BBDA00EA12E320E7207D389C9C964DC53E725147B53C344B1D7175902D0B7A7D2E7D7716ADC18C7D5878E0469538AF3A4ABB05C29AF40C434DA2A6272B1C4700A961BA92FA3EF3E3C89D566B737234BAA33422B72BD1583AFD4A4E05BBB52E9EEBF7F53B9E30F67BDB5DF9BD7BD778ACB53A88C920E8BBE23EBB078BA0D24B65BA4331989C693BAF7872AD18D5C9A452776FB2CFCFF5C60EC86CD8FDA8D53747AF4335F7FE7A5E4DE8BA93FFBE6BF8FFCD5F153BFD630C241E87864105546F74509D89E66FFDCE9B51196F38B039DAF1F0D3086DE81B94603F7040CD8B47CCCEED79B1D2A0428F84F02762D521468A1FA2AD85AA182C5560984D09DC86667E3D9B190621012A1C5F0C22397DA77AC5EFEBDEFDDFE7C3AF7E6A9FFF8F0C4F0A0F3EB728018D8688018BC03738D06EE0918A813C3E50F2486FEC662180F2B861F8616C3CB57FFF44F3E7DEF3FDFFAC2BBEF2BB397222B1F9E189E707E3D1E2006361A2006EFC05CA3817B0206EAC4F0F8071283D8580C47C388E17142DA2E8616C3E3E999D5E4EECB5D7FFDF4EB3F4DFFEED97FFBF0C470C5F9F5E50031B0D100317807E61A0DDC13305027862F7F20310C3416C3845F0CE1DF4362E8E53AA0F666217E94F416ED77B9E0689D76F8021E2EE6BF9101B9C8B190949EC6F04C29D4D5F13A9BA7251D8B8A4AAF56606D0ADBE16B535300CDD30E9A40D1BCF5FAD609C6093781DC4BC3A389BA132138320E73229E7DE7272F3CF7BD31F1FB2F661FDBF383B36F1072E13DAE5A59AFDABDC06A39A142C63A5904C4743DB9F44C7E327D3E45977A3E7017DF08BD8B2B7BDEFECBA9CE15B2F0D0CD079395C4A38D76F1E76C17D87CA9F1C2C2E9D00B3FF854EAE1C1857BAF7FFBF99F7FEDCAB9D3FFDD68E16F875BF8ADF00B972E7DF6DAAA76E7CF73BF7DE21F774EEE6AB4F077422DDC560DBDF0378B4FBC77F5C517EF3EFFA35FBDFF8C1EA9365AF895500BB36B1FA116FED4C2E95F7CFED5ED7FFAF1FDCF747F72C71DAF365AF8D5460B0BCEED935EAE6085703B3EFCB30BD1BBBB5F7BE6F0D28B4F7FE58E6F355AF835CFC24EE9B328E834156A5AF062B547EFCD848013D3CDCB8E68E4BB960C4D5B5D5B4F258D5F9A728DDBA666C66D0EDFF34D4B78C96E07DE05CAC8A656310AF2B402C9457909C984646A11AF0E84B9B144EF1860C519AC2464B7752FC1713F153D0C26FBB5BE7BF36B1E6F64B96FCE0FB62C857B5E980794D893A4F3FE8A6CACB5BE61702782615E6870E6442BE5A2EA5C74330B86A25BAA6CB9B7E58054A7370CA9591BF804CE84B129CEF0D6336D60BB3C8FEB7F649EF4AE4A8682375B01C9815648CE7158BC59D04009B8C7BB2DDCCDB000049E2B6202816C795FAB6C99EDAAEDE4BFBE9A3AF833DDCE91F1D2CBE19027A1E6C24B6792F417652617AE916DFE9B85815AB2DE6A009C3C8BB4A71393AD22A89A6B509E080AA8C26DD804B854E19D20EC1DAABF72E5BD918997AAAA2C4AF4BC2E601798993D7B8352D99B89CFA433B93CD08A9D6FB79ED19E8DDFE981F5BC14E0D71CBCC0ACE21016B59D1AAF137E7C9DF047D7093F111A3E368BE635342B3B6393915CDC03BD99429BF7ABC35822332DA9A4FBF0CFAE13FF6C08FCD82F46B3B17C6626373A3A9E1F839379B0D5C9740DEB75447CECE077EFBDD63E9DDBD659CCA5585CC5EFA5B713A734030E46090E6979698EDE2959332DB934292F028662926CE7EFA9A949AA7BD3DAE2886F28C3CEE5225AA4D4877CF00501B9B9819FA9583A958BDF95D33D0238D08C33EC9E2E968EE52AAB5109D7308CDB38C6994826321DCF81EFCE4F4766BC8807DCB79340BE6FF2F6B391EC4C3C936738B24164351598F7AB0A3388BA01DF025EBCA1BFD6F0E1DBE4F22F158FE5204CF1E2DCE2E830D0526689876FFA209F3E1DB90B50CCA67C14353057F6DCCD7C2E9C9B483E9B9ECDC4E2E1F7432FAB67A9D70B24281BCB246672C9B88FA091E628230BA6654805CB090FFC9CE79893E95824E9A374D0D587A45690543F45DBF9BC4C9CED311F9D4D4DFA11EC7411647C314FE0D67289E9787E2E9DF2A1D8ECA2407333A795E540AD9E4A67A623F04F24964B67EEF662681AF052C453DE6F157CD8B7D85A948C64B3C0A1C8643CE3C5BDCDD5047A94931A5EF3F7A1D8C551CC6692F93311E4502688CA218A09490540BC562095CE48C82A2388AEADF6AE13C9381540F29C9FB0A62F68D9A6155546A1A8AB35043BE2884FCF24C1F467BD78B7BAE28869AA1A7072FAB38CB07872D237D373F501C30FDF9C2ED847CEBF81A6468EE726533439083C7BE71299DC6C2499985B0756086015C3AAE0B75C3558B726B2F9C4E9543A1307EB793A918AD4DA93FA5B0DF67CE294CC486DC94CB048DB75D7D1F96E04557FFEEAFCF2DBEC9FEB3133A537B8DBC1F92A6C33C31735AD04F8ECC7AAA7BD5675EE5F50319C91CC65B0F39DDDAF7FE7BBDBEFFD8776D23645FA54D060A671F8E9D43238C5654D2D56F5DB4FB154F8420FFCA68BE2FE2E594418C5C645E0C1F8F0D163B71C9FB8E5E6E363A3F8338E03BF0EC1DEF18963D575C4DB344F72BD799BE72DDCA15629089D6BBF80F37F4B44BFA638D42A28F12038F507E499FF7A79E28A1D95B4AFE39DA28BC6DD4847C02B357A8D71452E6765CC492DBC44BCCD736737B62CA1E18610691EEF7100203061CB7CFDE784103658E4A829E3891C3A38B40881CFD0C242DEBCA059794665BE68A943909519F290A36C431F1DE26EFE536D3E25E49D8073D310BD6239B4B0360431FB9064165033DBC1753A25E5E84BFF79E5FD77EE1B3C33FDC8CA63EAA74FF5039F0FB4E2B3932B2EFCE8372E3D79F85B933697F103CDA6FECCC3651B49C8224594F483172B5454FA02394A069472C1A0255CF6A1416D8C1825BDA0FFB2C5BE151CF4CC0D7FFB2DE64E6AF849C006870EE796E33E88107DD485AF6C27BCD3F89ABB1D5C8E3FA90D48AF6900E28B4E216A56CA8AA5486A70D4EC0D91FB28EFBC3BDAE07033FCA59B8C3D85EF64FD2FD52FBC87C5BB50D7F96A84155054EBCADE9D05C718F22E449D28023076A430E2D11D372104B8899D65F9C2505DD0B97FEC0075B8E092E2F9D4EC74147C5DA8CAA18FA7419B6486004DC02DCC123C61B3111F9EA6E54BE14B1EA2DBD64BF4402C9D9C9D4E396423C22BC00CDC4BD0BA14E029CC76311877D66D5FDFBA757477849F3F4AE9169DB402730244F7D5466C7ADE59A5F30352D9B55E2AFBA84AB8343ED79AC6EE0F4863CFBA39C9358051692B4017EB65F482329F6CA9CC4DEA8481A76D369944EC7BC25DF0ADF9C03700636F1282FA6C2C3213A746A1A9EB6277927D1FF20660EC0197954B4032A6EB216F45D57F451B843695C6E8359E6D753DA8289BCA5279D8FEE637E6FED9035F6D714BC1D04CD392163C00B8D40FC11CF34FD6EB47B67B3EDCAE1915AA4C413FCE43CD411A6A0E62B3DA7000428DB6F9E8B3FFB3E3975D3DB9B7780C71F6CDD7FEEFE56FC3F058DB170472ADB72E951F9B183B3E36313A36317E6C6C2C3F3E367EECE8B16E2208D46CD6BB22FB6F349823A7E91B147EFF4C33BA49BB4076B2D72A2C126300E985FB2004EB269D02396C070C9A916F49834076386F25F2BE8C5C681166E08B64FACA4455DD8A9A0F9DAFB422903D750B79B22581ECAA1B761273816C73073D799E40AEAE9BE44B4D83B03A950C815C5337E82F520964771D845BD811E8B71975A3589F12C8DEFA214F2E2F901BEAC60312F5209E79AA3BBEED07A58A0219740158AE2B90A10602CABAC4D7F3C55FE60862AB53B10A94A49DD3FB960F2EE408644B1D486252C09B0BB25ACCA7B13D1C5633D95B18810CB0C9BC06ED3CF39ABD80B76AE838D69C6B46279C51FA3EC02104D2921AC8F19AE7A3CECC980F6FCCA662237BF66C72ABFDDE2BEF89AE84167FA7C1B35F27BD11C87607952FE681B3E40C78830A60BB7F6DDEBBAD160DEF1FBCC144FF54A0C16FA2BCA809E460523396C07415E565A9628E702385B79558BD66C48105F2BAF379C0301D138830073C05714B74182C48D26BDEA6ED0198D39FCF5B4A49CE62ED1FE69D15C8014F4F3E3F7EF478BE2CAFCAC61949D7E5B263EF8E8EDD0AB2E93A8939C46D0269DF7FE09C483E424EF4910E7252209B6E5802AF20A96A56B164FAF13AD8BEFD904A066EA752C61547C0C8AA26C08FD8134F88E436727B1F6923A704EAE1D76539BB4914C4DC6C415645C53F29801091A2A4D369A0347D05C9B4721AFA6081DCB2DF93A533AF70C29BD953561EA88711C91439DD47E2E48C487A486F2FECE3AC48FA582B29927ED64A894464AD19910CB05646241B582B27928DAC050C1E64ADBB44B289B5E644880B68EB1E916C61AD8F89642B6BDD2B926DACB52092EDAC5514C90ED65A14C94ED65A16C955AC759F4876B1962A92DDAC5516C91ED6D245B297B50C81D68042DB8B6E6289E46A720DCE5D15C9106B5545722D6B5D14C975AC754924D7B3D60322B981B52E8BE446D6FAA448F6B1D68322B989B51E16C97ED6FA4D911C60AD47447290B51E156820B79E93DE4D3E239243E4304E7F5C244758EB774432CC5A4F886484B53E2F9251D67A120E3BEA7C969EE069F7E85DB5FF4083C32792A7C91750B9BF289263E466C4F2FB02E9B0961598767DB2659C813618147D592BF2605920B7EE0FF8F354757A19A0AAED188209E4C8BA8E6737F9AA40EB306AAC62E0179502C9EC4F360AA4020809459A48BE46BEDE4FFE88FCB1C08A1242D01FE182B1FFA7EDCAE3ABA8AEFF39F74D92C9CB90ED116008810021841004022AFB228B86E5218284888A311B912598455C4A11AD5AA456AD5AC49F7BADF1834BC10512158B1B5A17AA76B1B5D6855AABD6859FFB56DBEF99E531F332C1F76CFDE7DCC93967EE3DF7CE39DF7BCFBDF326D5CD0D2D7E99530353664D5B4B6BD3EAC6B3EB6A2B0058009392B8C3F143FF396C890C109696D392EA5F37BDB95B7A738FD4A88460F5A9D6AEE62EE7F560D783ADCE00090BCE5AC8265D8C37DE364FE3B803209D1EC2B0C8F389F38CE40CDE430F8BC18F481B580BFB8FF7747A5CF8A91CB45FAFD393224CE3AE07433A3D2D229D7DA70C3AED136E3A071F8BE8F4BC88C3DCF50C4DA7DF8B2883BB391CD5E945911B1C709CA8D34B22EBC1F147B53AFD5504991C787AA4D36B22CDE2431D0CE9F43751CAE6A0B3449DDE14610E071F4BE8F4B68873D93D55D1E95D6144B8BB435C9D3E10859EDCCD619A4E1F8A3C8FBB9E8AEAF489887A71D753239D3E17516FEEEEA050A7AF44A10F7B369E75FAC6715BFBCD255130D97A0D4667CBEFFB72EC0D069D538493CFB1773E74D685D38F9DB71674CE90BF0B621A6375CE144E7F76DE2AD13947FE1EC0D62B293AF794BF0A63FAE53AF716CEC018678CCE7D8533889D372F742E90BF0773ECCD0D9D0B8553C4A44B4F304158FD18C2BEAD3A9D8708B798E3F6C2742E11FE50F6EF35E93C5CD825ECDDDCD1F930610E63FF6E8ACE5849EBCE776C807C79251501318AD5FBA180DB5DE0C8AE4D737D754DDDB83446969ADED8B2A0B9B1A142701BEBB7130C3E92C785F9081E8F01B065E04DB479930C1A4363314731567B666D638B4C94B598EDD6D45637D7C6263583A7D9FAD359827B86F4A994EDA32ADFC74FD278365AB1F67A17D4C3B1FCFD72BF9062F0315C11E6A3655D9A654D34F29BF7E636D9D336789E054C3C5F1AB186B44CC80821D6608E14324AC8682158AF1607A06030E671A554BD94ED933BEF5493C627486D63848C1572B89023841C29649C90F1422608B1A6D64942260B992264AA906942A60B394A0856EDAAB1164BF00A767E3A0D27731FFC8255B5A23353C82C21B3851C2DE418211542E608992B649E106B58A24216083956C84221C709592464B190E385581348A590A542AA8458DD5C26E4442127E131BAF6CC6A8163C9C7294470B290E5424E11522DE4542135422C953A21F5421A84AC10D228E434212B85AC12B25AC81A214D42D60A395D48B3102BF65A85B4093943C83A00ABAC988AE092AD458D6BCE685A5957842C71E8A102E268FB4956AC5EBB2A8DDB7DCFD8D9F4612C3686952CEBEA1A01077656E6C3B787F936BE83E56B8D2D6B9BEBAA6BE5689C69F2B755E2B22A8605700DBE8B7F850C82B7B31CD748E7E65B2BBE056B9C255794A9B6A44B3292D022C5313FA0D9A090E0BBF91EB1E45E44E2C1016FA96BB5463BB175E7124C74F64152CC206B72B0D68DDF9E6225D32B3C14EEE04EB1F87E9FC50DB6C5C5011607226BCF86408317FE77831E38C20FF1AFC5DE3D40FC06E7DD61AC3C4A867569C9E047F8D1306FE3C7584E3E1B5B3DF9C6F04088EB2603E1BDFC8434F9A441A3A95CD0FD29A69244B71CD2F819F8BB6BAABD6131B9EBB80C4B7C0FC3E07DFCDB303FCBCFD9157BE69517902B41F03B085AEABC1DEE5BD24DEFC4DD0C6FD020D792FBF302D5D3F8CF4C730EED86C9645406BFC87FC9E097F865E038BAE27E86E55B97E287F29057F855A9F23554D9E2AD72FC77B51B51B29FFF2675BE61ED0515B5ACAB5E6BA748994535FEFD9B02CFFD787A231735D5B7C63E9D03E3FEC16F8907BD0DF79D54B3CADA1832F84DD91252FCAE41E3699C88DF4F41223567E5D23E51838EB4591F02815BDAD095A2F2A2834B9D2925897F6F32307213D9A0387808C09F1A3C863F09F367FC79CC9ED145AD4DF6E001087C81184B3B7557C3E0AFF92B09CA7F31F5B66F1F53D45CD700D0A96BB61D10DE3AD0EFADE80356504D7EA7CD8ABBCB50AC284C9A52782AAE616B9A5A1BEBF15452ED0B43A560A4799B422696E3D7992EF95F7AECDA50E9B626D228337ED86313BBC19BADC150C88A06B8BD81CF05BDFDC974F8B73CAB809BAC9EF6EEA64643E5A86CE971AE84BB6363A3E5966B92CBDCFFBB5F27888D9AB46BA85E2A4F86A3B7849E631056A2DF3E91C41C25047543E5ABBE520DF29ABE6E350087D96D6B2C285CD4B6560C649A1104A3815DA9776E6D191957099ACCED5AB5A10AD500B10079500FD74F6A5635ADA98BF7F0581CA55862430D5145E236C532893B0EE1363EC3DA63181F301489AD2EF094BD5519AA540D93C73F1CF35FBC8B622D6CF05ADB390F8BB9C7E8A275D5E21E9A14861A6DBB3896FC2925732A00736AB41A2B1CE43D056E8D2BACB7329D67EEBA00D3C4432F66669D595367BD4B1EFC98F3026B35D478354E2C9EE0C19683937CCCA556B700694E4C683595EC7ECE41474423869AAAA6883DD3E226BD249A0BA85CAA3D4AAA9DE1E9667DE31AEB454C60A57B69A8D9F6033A1A88E83A53756DADEBAC335635B5B435D7C550735AC9210F0E6C3DE7A6C0C520A0AEDBEA0D3557CD116F9BF79D5CD8D3F5A8747D019CB0648EED720BA58FC77D977D594FAD8BA5D6E33DD05F777A5BF5AA16D90C08A8E3044C09B682A1AAD45231E004CFC35851DDB2620686D14AF9916AEA2EC35027AB93447B39528DA29A66F9F6A0EF0067F96849D303F31F5D95EAF4808EDA78302A865FEB0A7E9C35A9A8E660BE5E3445578DF6D2D67A674C572BED951102DA61AC06B40435CD74444937ABC8EEF6A6ADFBACDCFB4C4355AB53B1085140EBF2E42B4953AD71098E8DC7DD6582EA8CB06A53C84BF30FB15C4A5398B11B00A8EA6C758EA19AD45A31707D37CFD4AA7683E89E0B40ABB6876454D28769EA3C757E3A6CFB115CA446FA50C434E45BA62FF79C302B8E95A67ECC94566F7FB3D95017AB8BC26A93DACCD4FFA062B4C9529D59573F5BFE2DC3ACE666ACB3D4256CBF21EFA92CDAD46A6BB8D09AA62E650A5B09404B4B75031AB81C6B2B7599FA59DC08F906FECAB0FAA9BA0AE1673B78AEF54B07F75067B110D964C7C46F7F26CFE184ED9F68C84BF43490146984E1A1325CE924BF1696A5975D6638A5E1943D9C32D329B39C32DB29739C32D729234ED9D329F39CB29753F676CA3E4E693A655FA7CC77CA7E4E59E094FD698055163AE540A71CE494839DB2C829873865B1530E75CA12A71CE694A54E391CA321E508A73CCC29473AE52829B15C430A8972936C14A23C4F0EB550A6CB32DF2AC75B65581205D98EC3384FA089C46A0B516881D52E854697EEA2C91D347D1ECF2FCB9DD141157B6846E9F2FB686E592547477839F34754F204CDD4BCBC05A606668A99E2652E3453C04C3553BDCC45662A9869669A9779BC9906A66EEA5E66A5A983996EA67B9955663A986133EC652E33C3606698195EE64966069886697899CB4D03CC1E660F2FB3DAEC0166A699E965D69899606699595E669D990566B699ED653698D960E698395E66A3990366AE99EB65AE3473C18C98112F73B51901B3A7D9D3CB6C327B829967E67999A79B7960F6327B79992D662F307B9BBD73DB2C669B30D799BDC1EC63F6F132CF32FB80699AA697798E6982D9D7ECEB65AE37FB82996FE67B991BCC7C30FB99FDBCCC8D663F300BCC022FF37CB300CCFE667F2FF302B33F9803CC015EE645E600300BCD422F73935908E64073A097B9D91C08E62073909779893908CCC1E6E0DC4B2DE6A5C2BCDC1C0C669159E4655E61168139C41CE2655E650E01B3D82CF632B798C5600E35877A995BCDA195A53BE9BA09256689FC7D835952B903A1C4EA6A500301A585CAE846BA49787433FDC20E2E5A0850931F7A44105808A25BCA4B879775D2368DB6F3768960EBF642B2CEA29CDBE5EA76BAC3FA56420EDD4977416B2BAE53A5112016D3AF68BB53FD7ADC99223BEFA8DE8AC81D68A093EE0D2176B4DCFBCA4BCB627FEC2C2F1D61FFB123D670A9655B59ACE132DA451D56C365D4892B655DDD8FAB906542A6D5DC7CC06A1400B5009A0FD0830E8C1443D60715DF0C530020BBCB87E7FEBA931E0D91EBABD111169A8CA82CCB7D0CC2BD3EA1186F410B60A42CF709C87FE39703596C984981FC29C89FF1CB013236E2A442FE2CE4BFF5CB813736F8A441FE1CE42FF8E5801E1B8774C87F07F91FFC72A0900D49E990FF11F23FF9E500241B9DC290FF19F2BFF8E5C0261BA832207F19F257FC72C0948D5906E4AF42FEBA5F0EC4B2E1AB07E4FB217FC32F0778D9489609F9DF21FF875F0E1CB3412D0BF2B7207FC72F07A4D9F8960DF93F217FCF2F07BAD9509703F9FB901FF0CB017436EAE542FEFF907FE49703F36C008C40FE31E49FFAE5803F1B0B7B42FE19E45FF8E540421B16F320FF12F2AFFD7280A28D90BD20FF17E4FFF6CB818F3658F6F6F87F2FF1707593EBFFB8B2FCDFF2F525582468B414537A154DA713A89A96D1463A119A27D1C37432BD4ECB99E9141E48D53C9D4EE56AAAE18D54CB37511D3F4CF5FC3A3528A6156A2035AAE9749AAAA6956A23AD925719DC98E12F10335928B75A3113612E1F1EE1906DB8837276D4AC43D444581371AA4FECC6CD591237114E138D74BF861339E748E444382C1A865FC3899DF5123B11EE211A597E0D277A3648F444385B3472FD1A4EFC6C94F889704434F2FC1A4E049D2F1114E15EA2D1C7AFE1C4D005124311364523DFAFE144D145124511EE271AFDFD1A4E1C6D92388AF000D118E8D7702269B3445284071D741557C109A54B104A71AEC257BBAE82ABCE98AB946302D0A80DABBD336816ADA3063A9336D15940F1B3E9593A873EA01F700EADE731F4433E9E36F03A3A176E30D875033A003790B7B1EF70DCA048AC2EB68D72E620DB0D2EB7DC60A88887F9C4AE1B5C61BB41A96894F9351C37B8CA768311A231D2AFE1B8C116DB0D468946B95FC37183AD7083B891415FDD49E4766BC2B047A617A60B0D6331982EC6C86CC6C8FC047F5D82DE8FE1B176EF533E44EF0B89B4E7A4F758695676F084ADD4A783276FA5DCBB79EA356474F051D7506ABBAACA0FDD48695A3B26DBAD9413E199D2EF28FADCAE26E66B07450360FFACF2081FAD75F0DC4E8E8648BA1F45D7DB556E7E8A5F71815F11A310C508B4F3DBF9A97EC563FD8A188C2806A29DF7E4A7F915178A6228A688D088222CDA794BBEEE573CCE5F2322248AE868E735F9D9716D2FF257894889224ADA79667E765CE38BFD752262A2889676EE9B9F1DD7FAF1FE3A113951444D3B1DC8CF4E3FA839049A4BCA4BEFA58D9D5C854C8A978933F03289A32862A89DF6E46787FDFA278AFE3971FA08AB2842AA9D2ECBCFCEF0EB9F24FA17C4E963C68A62B66AA753F2B30DBFFEC9A27F519C3E66B02866AF761A959FDDE3A0FE20E82F17FD0D71FA98D1A298CDDAD5922E5E85C92C8A892CC0AB4EF18F2E66B52866B400AFAAF62B627A8B626A0BF0AA53FD8A98E7A298E302BCAAC6FFB830E14531D9057855ADBF46CC7C51CC7A415E55E7AF12E9421429429057D5FBEB440E1145DE10E4550DFE3A915844914C0479D58A20AF42CE11459E11E4558D415E8574248A1424C8AB4E0BF22A642A516427415EB532C8AB90C44491B80479D5AA20AF427E13454E33A2CBFA5EDBE722A4B68F57F31AD9EBD09EE426ACEFEDC5F53BC8C935FA39B0F26AE4F5D7D014BA96E6D2F5C0CC1BA91529C565740B5D47B762DEB98DF6D236DA8F1CE103BA8B33904B9874371F4EF7F20CDAC9CBA98357D3FDBC891EE42DF41077D21E7E821EE1B7E931FE8CF62A939E5443E9295541CFA84ADAA7CEA2ABD585748DBA92AEC502E87AB59B6E544FD1CDEA4DBA457D44B786B2E8B6503FDA161A47778466D15DA16ADA1E6AA2BB439B914C6CA59DA1FBA823F428DD1F7A951E0CBD470F6959B4472BA047B4A9F498369FF66A4DF4A4B69E9ED26EA067B43B699FF60C3D0FFC5FFB7DE3FFE989E27F73A2F8DF9228FEB7268AFF6D09E3FF1909E3FFBA84F1FFCC84F1FFAC24F1FFEC24F1FF9C24F1FF0749E2FFFAFF19FEFF3051FCDF9028FE9F9B28FE6F4C14FFCF4B18FFCF4F18FF7F9430FE5F9030FE5F9824FE5F9424FEFF3849FCDF9424FE5FFC7DE0FF4BC0FF9781FFAF00FF5F03FEEF07FEBF01FC7F13F8FF16F0FF1DE0FFBBC0FFF7213900FCFF10F8FF31F0FF53E0FFE7C0FF2F81FF5F03FFBFE14D4CBC851577B2C64F702ABFCD3A7FC66165B2A18672A6AAE06C55C9B9C0FF9781FFAF00FF5F03FEEF07FEBF01FC7F13F8FF16F0FF1DE0FFBBC0FFF781FF0780FF1F02FF3F06FE7F0AFCFF1C03F125F0FF6BE0FF37A1579942EFB1D2B258D30A38559BCABA369FC35A131BDA7ACED46EE06CED4ECED59EE13CE0FFE6EF1BFF7F9228FE5F9228FEFF3451FCBF3451FCBF2C61FCBF3C61FCFF59C2F87F45C2F87F6592F87F5592F8FFF324F17F4B92F87FF5FF0CFFB7268AFFD7248AFFFF9728FE5F9B28FE5F9730FE5F9F30FEDF9030FEDF9830FEDF9424FEDF9C24FEFF2249FCBF2549FCFFE5F780FFDC9F0673218DE24134858B682E17530397502B97D2655C46D7F161D4C9A3682F97D37E20E8077C0467F0383679021FCE9378064FE1E53C0DF51F05FC9F09FC9F0DFC3F06F83F07F83F0FF81F05FE1F0BFC3F0EF8BF18F8BF449DC585EA421EA4AEE422751317ABDD5CA29EE252F52697A98FF8B050168F0AF5E3F2D0381E1B9AC54784AA795CA889278436F3A4D0569E12BA8FA7851EE5A380FF3381FFB381FFC700FFE700FFE701FFA3C0FF6381FFC701FF1703FF9700FFABB2996F8D1D91ACA774EB1862AE1C91E4AAD207F8B62A6D376FAB0A0D5F94B29B97558510928BAA34A1BBF8CED85087A063EB952DD21E0C5785420B3A784707DF670F3D7B8E562A629B5315B1A3950AFB6805A6ECE45D8E29AB296C1DA74C1453CAAC0395C4EDE9E0073013ED886B7A42ACE909B1A627584F5D9A7E90773B4D9F8451B07ED220B36052AD3EECEDAFBD193726D6E818BBD16C4D7EF1E83435063AA235B474273F9EAB7E497A6907FF66FBDDFCF4BCE14F5096703BF8F9AA79F7F0D3C377F2EFA57282AD2FC42A188CDBA51FBDE1FF3754ED256D7B6569D54EBAEE3EBA4138EE0D7FE03F3A37E43A2DA6960E87CE0E47FE62CC054C3880F53B30B4FD273921E3BF6AE456F34A6C8CFA4049AA316CB54E7E3D14D3DA1F7B885D2BFBBB46769BE9F252ACA396E77C9A3C1D033E97EFE1772C15D2F99F319541D63B0918D40E7E2F7D6AFF7BF883FE4B7BF281E985F7F047854BDD6E7CCC9F74EDE62EFEC2B5EC4BFECA91E7384DA694EEE26F5CF1BF655FB9CBED2AE454AFB498399EDB559A2BD603C519AED808344E653AADAB2C951D248FB8F7F754798E5CB6722536C218D211A6B64BF571754CD537A88E02B78DFE6A40907C902B1FAC8A02BA30D41597A8618E3882E72A4F360D26EC5265AEC288E03E8E74E5A30287688C6BFE28353656BF7D589B563AA060973AC25538528D0B3460A2DBC0247E34E0093FEE8A27AB29DD8DE1F4833A47393A3D1D2374D1D9A566BA1AB3027B718C6B64859A1368E4FC1DB116A2810AC7C686492DFC4F5D77F7124514C6717CF6C1541682DC438220081115234617FD0B5D6D90B9662D6969BE642699EFAF59566A859965A999A59541175D64D8AEB0445D44421008411004411084811004411078CE3EDFB69BED663F17F3CC6FD99939730EECF04CEA306A41A65F1097C8BF80D2B401657F030ECAA174E7E1F013B6974B459A5F50A99B83EE5131FE4A5EB4E9EE35058FFDD0BC91EA881F5A305263796EA4D6123352677961A4DEF25A5956DE2AEF9415E5BDF241F9A87C523E2B5F94AFCA37E5BBB2A6FC507E2ABF94DFCA9F242610C00CCCC2206EC44D683017F3301F0B700B6EC5EDE86311EEC23D461A9CFBB018F7630946B0140F60999146673956E0113C8A95D457E131ACC61AACC53A3C8EF5E49CC0063C898DD84C7D0BB6621BB66307766217761B39E5ECC15EECC333D84FFD593C8703781E2FE0451CC4217286F1125EC62B3842FD551CC56B3886D7F1068EE34D8ECB2D9CC0499CC2DBD44FE31D9CC1BB780F67710EEF93F3001FE23C3E722E30829BE4746AE6CC480E707B5937DBC54BD85F92D684B447EDC2262E5D7AC308BA0E23D4EFD611EFED7816587DE3E5D8CF98F4BDB23B8497BDD092F48713D213B5BB0ED879DA5F94414DC896211926611B6B8390FDAA91A77B13321A2D8CC9785C265EEAADC6DB20639E9B6AA73DF7E45C569BB7396D6F87FF35824E35B1DA997C1F46534737ADB75AD601504B030414000000080074739545C5E751CDDC040000A513000035001C004253435F5250543030322F2E73766E2F746578742D626173652F4253435F5250543030325F312E6A72786D6C2E73766E2D6261736555540900035B689654F5FB975475780B000104000000000400000000DD58DB6EDB38107D6EBE422BF46901DAA444916261A7485B170D90B6D938D95DA028028A175B852CB9121DDB2DFAEF3B922FEB34DED406BC7E881F04939CE1E1391CCF8CDC79391B65DE9D29ABB4C8BB3E6961DF33B92A749A0FBAFECDF55B14FB2F4F4F3A5F643536E5951917A5F3C025AFBAFED0B9F18B767BB154364B55AB2A26A532B62807A6951B777FD55FB8BE9855E9DA7D3A9DB6A6610B1CDA01C6A4FDF7FB8BBE1A9A9144695E39992B035E55FAA26A262F0A255D73D4FDD1BD9D5C6695BE67D18209DFCBE5C874FD57FDD7B75797D71807B7C4F732990F267200F383B228EEE6BE3786D15FA976C3AECF79BC18BF33E960E8BA3E8D02DF2BCAD4E46EC9E042E6BA52720C0455914D46F9A66B66AC7B2FCB410A86702765BDC9C6D815E38D515238578C362626935403A48E254B628D988A05A2182728118623492D3382498269EC9F9E3CEB8CCB02F8BAF99266BA64FEAD2846BE7727B3094C06AD30E282722608AE3F81DF7EC475B6F613347AD472BEB6C44B3B59828133E5D2F0AA77F9F1EAFAF6FC0DC894C90A02EF8BBC93AD5AFD56DF9510A83587671D6DAC9C64EECF7AB3DE6C5C9AAA8EE9D3CE6F9F5EBF39BB3EFBE4FB9F3F9F76DAFF6106C0ED35723DFA3A31E57CB17FB3FD6A9BCA644639EF77CF96C5C84B92DB6A5AB8DB05995BED326F3A34A5F1D6A7F6BADEF3CBEFEBE10F88010DDC92B9D7EFFDE1C94AC1A96AF0FB781D9B9A4C2F15F8F84BEE8DF51B53A9321DBB7BAC1BCE0F966BC066F267A85DC53E182088B005EA3C776660CA03635D9CBDEA5D1C8BD879BF7FD3EB93E3C205C7850B8F0B478F05F7FAA6DFBB3ADE6FA076EADD07ABBE66ADEB7464A0028EC60786BB392EBB9BFF9F9D364EA659B34F0245D51B2E6B2E542ADFABC659EAAEE7E33AD9B8D238356C109F759C99B9B7CB2DEAF12287F73233821AEDCD9A423A6F9ED34561261C7ECEABBD855895D900C752AA00231B138DA0B65A9448AB11179C2A1A2A1672D994B615E80AA2FE7E96A583BC1E4118C013725EDD8DB954C96C63E97DAA756616E7AEC52AC0BB7E7C6814B6504B6D36AF2670775E957E832902274DAB574506E773E5045A8CB1B6BD756377AE61DBD4CDD1BBDAEC12964689D1DAACAC57A76D6F1C778340A3DAB61AFBFCEDF726C9FE682E6D9BE9C97ADB7FA5FFF54D101EFE7C17D056DCBFE7C5652461420D0D2C5238AC7B9E48A3D8C818199108AA88E6929AAD977108D5C5F1645E9697430B1D066247A121AC6D82A94511D5068436114AA258220EC213CE598C057F3A420787169AC6C1AE111D196D1913288AB8429430E8E22531C8122963C1B06661FC74840E0F2D340BE903A1E9F6880E84D4D21248E12244549A108926C03167903A921863F67484A67B0A9DA5B9D9B9506ED55707098B42781D254A27A0AFD6482A2390C5492435E41382D59257A7BD867B14B8A9C83B40B3208E42164844158F1015B1829760A2902189A50C472CC1784FE830E03B410304B691ACDFBA2386283712C554406932312398050C07E19ED034DE4D701B4534B661827448624429A9A11934264A9080F098D988EC09CDC207696B2B74C40DB3D2322409C790B4788C12CA2812D41862B88448107B42377F8BEC00CD75104B82398A8123A289826F9C0520BDE582290E09745FD68B20AB3BBEE9EA2F1ABE81BD429624508C270C0929012F4938900E14B201E7348804E64A3E40EEB4EB96B5E967576D6CA7BDF977DBE9C93F504B03041400000008003A999645230ED589720100006F03000017001C004253435F5250543030322F2E73766E2F656E74726965735554090003FFFB97548613985475780B0001040000000004000000008D92DD6E1B211085EF511EA22F80778619FEF6B27981A8F1956F2260A171BBF15A8B55256F1F6C5749EDAC541F7101D2CC9C8F0308420CDB59B06212F5CFAEEFBA714A617C9EEAA1FB59634D5D9AE62C872975DF1FEF9F7E3CAC01D472A56852802C5149856BA01EDB82151847CC9BB347DC8E63AB3B0EA8FB9CB661FC76DCE7D7439E7761ACA7D32EE7A1CA36FEB7F8479C52563C901C72B23223A2F464B42C30E482D91B2CFA6F2988FCB23FBC893BF109BDFA159AE32CCA76CC8D45BB0B5EB546ECC1F7AAF19EB411DE591542C4828E79606B989D674F81585BD352F8D2AC71C5A0BC82CDD9E074D9E750E57E9EF6552C891CB2B9E29C5F5FC6FF60A2FDC074453946CFA1B4E204C69798069F62217409C9DC88B928C3E42FE09EF03A467DFDECBA27DD337DF019E734E49664130488CA7B4BD114061D07ADCB45B3E995EDC9AF9455D6E1E66C704B8C0691BE905E04B90C4A9F41166B219BC8FA18A075BA508CEDC7196EFC0EF856D045E99677C37B07504B01021E030A0000000000A55995450000000000000000000000000B0018000000000000001000ED41000000004253435F5250543030322F5554050003C53A965475780B000104000000000400000000504B01021E0314000000080089A996456CAD33C449060000121900001B0018000000000001000000A481450000004253435F5250543030322F4253435F5250543030322E6A72786D6C5554050003B118985475780B000104000000000400000000504B01021E0314000000080090A996454ACC30AB2E3000000C9500001C0018000000000000000000A481E30600004253435F5250543030322F4253435F5250543030322E6A61737065725554050003BF18985475780B000104000000000400000000504B01021E03140000000800736C9545C5E751CDDC040000A51300001D0018000000000001000000A481673700004253435F5250543030322F4253435F5250543030325F312E6A72786D6C5554050003295C965475780B000104000000000400000000504B01021E031400000008008EA99645491ADFB99B2D0000118D00001E0018000000000000000000A4819A3C00004253435F5250543030322F4253435F5250543030325F312E6A61737065725554050003BC18985475780B000104000000000400000000504B01021E030A00000000003A999645000000000000000000000000100018000000000000001000ED418D6A00004253435F5250543030322F2E73766E2F5554050003FFFB975475780B000104000000000400000000504B01021E030A00000000003A999645000000000000000000000000140018000000000000001000ED41D76A00004253435F5250543030322F2E73766E2F746D702F5554050003FFFB975475780B000104000000000400000000504B01021E030A0000000000A55995450000000000000000000000001A0018000000000000001000ED41256B00004253435F5250543030322F2E73766E2F746D702F70726F70732F5554050003C63A965475780B000104000000000400000000504B01021E030A0000000000A55995450000000000000000000000001E0018000000000000001000ED41796B00004253435F5250543030322F2E73766E2F746D702F70726F702D626173652F5554050003C53A965475780B000104000000000400000000504B01021E030A00000000003A9996450000000000000000000000001E0018000000000000001000ED41D16B00004253435F5250543030322F2E73766E2F746D702F746578742D626173652F5554050003FFFB975475780B000104000000000400000000504B01021E030A0000000000A5599545000000000000000000000000160018000000000000001000ED41296C00004253435F5250543030322F2E73766E2F70726F70732F5554050003C63A965475780B000104000000000400000000504B01021E030A0000000000A55995450000000000000000000000001A0018000000000000001000ED41796C00004253435F5250543030322F2E73766E2F70726F702D626173652F5554050003C63A965475780B000104000000000400000000504B01021E030A0000000000A5599545FF31268C35000000350000003400180000000000010001002481CD6C00004253435F5250543030322F2E73766E2F70726F702D626173652F4253435F5250543030322E6A61737065722E73766E2D626173655554050003C53A965475780B000104000000000400000000504B01021E030A0000000000A5599545FF31268C35000000350000003600180000000000010001002481706D00004253435F5250543030322F2E73766E2F70726F702D626173652F4253435F5250543030325F312E6A61737065722E73766E2D626173655554050003C53A965475780B000104000000000400000000504B01021E030A00000000003A9996450000000000000000000000001A0018000000000000001000ED41156E00004253435F5250543030322F2E73766E2F746578742D626173652F5554050003FFFB975475780B000104000000000400000000504B01021E031400000008003A999645DC79091B2B30000002950000340018000000000000000000A481696E00004253435F5250543030322F2E73766E2F746578742D626173652F4253435F5250543030322E6A61737065722E73766E2D626173655554050003FFFB975475780B000104000000000400000000504B01021E031400000008003A999645BF67F9845606000027190000330018000000000001000000A481029F00004253435F5250543030322F2E73766E2F746578742D626173652F4253435F5250543030322E6A72786D6C2E73766E2D626173655554050003FFFB975475780B000104000000000400000000504B01021E0314000000080074739545B20E98E79B2D0000118D0000360018000000000000000000A481C5A500004253435F5250543030322F2E73766E2F746578742D626173652F4253435F5250543030325F312E6A61737065722E73766E2D6261736555540500035B68965475780B000104000000000400000000504B01021E0314000000080074739545C5E751CDDC040000A5130000350018000000000001000000A481D0D300004253435F5250543030322F2E73766E2F746578742D626173652F4253435F5250543030325F312E6A72786D6C2E73766E2D6261736555540500035B68965475780B000104000000000400000000504B01021E031400000008003A999645230ED589720100006F03000017001800000000000100010024811BD900004253435F5250543030322F2E73766E2F656E74726965735554050003FFFB975475780B000104000000000400000000504B050600000000140014000C080000DEDA00000000,'SWOT jasper report!','admin','2014-12-21 11:59:02','admin','2014-12-22 21:13:16');
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
INSERT INTO `tb_sys_prog` VALUES ('00294662-68df-4097-aac6-5406ff2ee78f','CORE_PROG001D0009Q','09 - Web Context bean','core.systemContextBeanManagmentAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-11-02 12:43:06',NULL,NULL),('004ba165-39b2-43ee-97b9-54d34eb3821a','CORE_PROG001D0010E','10 - Twitter pane (Edit)','core.systemTwitterEditAction.action','Y','N','N',0,0,'CORE','ITEM','TWITTER','admin','2014-12-18 22:08:34','admin','2014-12-18 22:10:11'),('01f4523c-0383-41fa-a6f8-a4b58d4bcaa9','CORE_PROG001D0008A','08 - Report management (Create)','core.systemJreportCreateAction.action','N','N','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2014-10-30 20:06:08',NULL,NULL),('03513766-a2a1-48ae-be36-409749a8a3d4','CORE_PROG001D0006A','06 - Message notice (Create)','core.systemMessageNoticeCreateAction.action','N','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2014-10-20 18:45:13',NULL,NULL),('04660c5c-674b-4b1d-af35-c95a93db4300','QCHARTS_PROG001D0001A','01 - Data source config (Create)','qcharts.dataSourceConfCreateAction.action','N','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-01-11 12:56:39',NULL,NULL),('04dc11fd-9f85-4c6f-b150-e2d6e2e7b311','CORE_PROG001D0007E','07 - Template settings (Edit)','core.systemTemplateEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2014-10-21 16:08:44',NULL,NULL),('04febc0b-34ee-4b2e-9da5-80f065103e21','CORE_PROG003D0002E','02 - Expression (Edit)','core.systemExpressionEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2014-10-23 14:02:20',NULL,NULL),('067fd09e-9fc5-4482-8f8c-97bcf57c7436','CORE_PROG001D0013A','13 - Form (Create)','core.systemFormCreateAction.action','N','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-25 16:54:52',NULL,NULL),('076b4b1e-0ba3-451d-9530-11367341ee7f','CORE_PROG001D0013E','13 - Form (Edit)','core.systemFormEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-26 12:52:27',NULL,NULL),('093038ce-6635-4139-bdda-36983d92bca4','BSC_PROG001D0003E','03 - Formula (Edit)','bsc.formulaEditAction.action','Y','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2014-11-19 16:41:39',NULL,NULL),('0a16584c-ed8b-4ada-bbd5-3a9e957dd604','CORE_PROG003D0002Q','02 - Expression','core.systemExpressionManagementAction.action','N','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2014-10-23 10:01:11','admin','2014-10-23 10:10:48'),('0e1be4e0-e4a0-40c9-9e86-2293360ea342','BSC_PROG002D0002E','02 - Perspective (Edit)','bsc.perspectiveEditAction.action','Y','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-15 12:40:53','admin','2014-11-16 09:33:42'),('0f9ebd8d-c935-4f24-ba04-315647c40bc7','BSC_PROG001D0004Q','04 - Score color','bsc.scoreColorManagementAction.action','N','N','N',0,0,'BSC','ITEM','GIMP','admin','2014-11-29 11:26:13','admin','2014-12-02 10:12:50'),('13639357-b390-4841-887f-81c997eb5e99','BSC_PROG002D0003E','03 - Strategy objective (Edit)','bsc.objectiveEditAction.action','Y','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-17 11:21:12','admin','2014-11-24 20:58:30'),('1597bf11-fb9a-4de9-8e7e-e2de6a6a32a8','BSC_PROG001D0001A','01 - Employee (Create)','bsc.employeeCreateAction.action','N','N','N',0,0,'BSC','ITEM','PERSON','admin','2014-11-11 21:59:29',NULL,NULL),('16f5a921-e73a-4abd-b060-45322f0f34eb','CORE_PROG001D0005Q','05 - Message config','core.systemMessageNoticeConfigManagementAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-19 14:05:19','admin','2014-10-20 15:42:51'),('1b11c7eb-6133-48fb-87f0-dfbd098ce914','CORE_PROG001D0001E','01 - Application site (Edit)','core.applicationSystemEditAction.action','Y','N','N',0,0,'CORE','ITEM','COMPUTER','admin','2014-10-02 00:00:00',NULL,NULL),('1fed314f-7e29-4be1-a05e-a03f68a84d70','QCHARTS_PROG001D0001Q','01 - Data source config','qcharts.dataSourceConfManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-01-10 10:23:28','admin','2015-01-11 10:00:49'),('20f31670-bd3c-4749-8ad1-a10e3b6e4958','CORE_PROG001D0012Q','12 - Form Template','core.systemFormTemplateManagementAction.action','N','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2015-04-24 09:31:18',NULL,NULL),('2412398b-5a40-46e5-944d-232331c29941','BSC_PROG002D0006Q','06 - Weight settings','bsc.weightManagementAction.action','N','N','N',0,0,'BSC','ITEM','VIEW_LIST','admin','2014-11-27 22:42:13','admin','2014-11-28 08:56:55'),('2546b34f-735f-4ae5-b923-bf56b38dd817','QCHARTS_PROG001D0002E','02 - Data query mapper (Edit)','qcharts.dataQueryMapperEditAction.action','Y','N','N',0,0,'QCHARTS','ITEM','PROPERTIES','admin','2015-01-14 22:40:54',NULL,NULL),('27b61c4c-b3ef-485b-a03d-694ea6725750','BSC_PROG003D0002Q','02 - Personal Report','bsc.personalReportAction.action','N','N','N',0,0,'BSC','ITEM','APPLICATION_PDF','admin','2014-12-11 10:21:53',NULL,NULL),('292b02f6-24dc-41b9-b254-4b4820c1c7ee','BSC_PROG004D0002A_C01','02 - Workspace settings (Create Content)','bsc.workspaceSettingsCreateContentAction.action','N','N','N',0,0,'BSC','ITEM','SYSTEM','admin','2014-12-29 20:48:53',NULL,NULL),('2a041c0c-5183-47c7-b919-51b2a4d2b3e8','BSC_PROG001D','Basic data','','N','N','N',0,0,'BSC','FOLDER','PROPERTIES','admin','2014-11-03 15:23:44','admin','2014-11-03 21:24:01'),('2c750670-c4af-4de2-a5c4-40b23eecea4f','CORE_PROG001D0011Q','11 - Settings','core.settingsManagementAction.action','N','N','N',0,0,'CORE','ITEM','PROPERTIES','admin','2014-12-25 22:24:52',NULL,NULL),('2fdecdc5-bcf2-41c7-9e1f-949fabe9553c','QCHARTS_PROG001D0003E','03 - Analytics config (Edit)','qcharts.analyticsConfigEditAction.action','Y','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-03-03 14:38:33','admin','2015-03-03 14:44:59'),('3630ee1b-6169-452f-821f-5c015dfb84d5','CORE_PROG001D','Config',' ','N','N','N',0,0,'CORE','FOLDER','PROPERTIES','admin','2014-10-02 00:00:00','admin','2014-10-08 09:04:26'),('3c472616-2e0d-4acb-9775-24cbbdd40e47','QCHARTS_PROG001D0002A','02 - Data query mapper (Create)','qcharts.dataQueryMapperCreateAction.action','N','N','N',0,0,'QCHARTS','ITEM','PROPERTIES','admin','2015-01-14 15:02:36',NULL,NULL),('3c79b99d-a630-4449-9e87-a85614acabb8','BSC_PROG004D0002Q_S00','Workspace layout view','bsc.workspaceSettingsViewAction.action','Y','Y','N',480,480,'BSC','ITEM','SYSTEM','admin','2014-12-31 10:05:57','admin','2014-12-31 20:02:15'),('40878892-4aad-45a2-8318-09b86372e377','CORE_PROG004D0002Q','02 - Login log','core.commomLoadForm.action?form_id=FORM_CORE_4D_002_01&form_method=init','N','N','N',0,0,'CORE','ITEM','PROPERTIES','admin','2015-04-28 10:59:59',NULL,NULL),('4102f56c-94c1-4901-bcee-9b51ada60a69','CORE_PROG001D0007E_S00','07 - Template parameter (Edit)','core.systemTemplateParamEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2014-10-21 19:08:11',NULL,NULL),('417f62db-a856-4ccd-a48f-57c540aac190','CORE_PROG001D0009A','09 - Web Context bean (Create)','core.systemContextBeanCreateAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-11-02 16:11:26','admin','2014-11-02 16:13:54'),('46563229-5b03-48dd-8c43-0b89faf8c2bd','QCHARTS_PROG001D0003A','03 - Analytics config (Create)','qcharts.analyticsConfigCreateAction.action','N','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-03-03 12:21:09',NULL,NULL),('48df9cb1-3615-4b96-adbd-e2524d81b937','BSC_PROG001D0003A','03 - Formula (Create)','bsc.formulaCreateAction.action','N','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2014-11-17 22:47:32','admin','2014-11-19 10:00:16'),('4a3524e9-9fce-4b2f-ad93-77ce9d650267','BSC_PROG001D0001E','01 - Employee (Edit)','bsc.employeeEditAction.action','Y','N','N',0,0,'BSC','ITEM','PERSON','admin','2014-11-12 21:30:07',NULL,NULL),('4a5370bd-c09f-496a-89b5-90b6fab96acd','CORE_PROG001D0003Q','03 - Menu settings','core.systemMenuManagementAction.action','N','N','N',0,0,'CORE','ITEM','FOLDER','admin','2014-10-08 09:31:51',NULL,NULL),('4e68492b-eb52-4fca-9fcf-cf4bcaf9ffaf','BSC_PROG002D0001A','01 - Vision (Create)','bsc.visionCreateAction.action','N','N','N',0,0,'BSC','ITEM','GWENVIEW','admin','2014-11-13 13:28:54',NULL,NULL),('4ec04618-edfc-4279-a506-b89b27fc41a8','CORE_PROG001D0007Q','07 - Template settings','core.systemTemplateManagementAction.action','N','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2014-10-21 13:52:53','admin','2014-10-21 14:05:40'),('50129a01-8cf8-44ed-a2d3-063bd9a0e162','BSC_PROG002D0002Q','02 - Perspective','bsc.perspectiveManagementAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-14 16:31:16','admin','2014-11-16 09:33:34'),('50a3a071-5036-446f-93c8-cef17581a746','CORE_PROG001D0007A','07 - Template settings (Create)','core.systemTemplateCreateAction.action','N','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2014-10-21 15:01:11',NULL,NULL),('530f6713-4059-42b0-87ae-04a99b270e04','BSC_PROG001D0008E','08 - Aggregation Method (Edit)','bsc.aggregationMethodEditAction.action','Y','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2015-03-12 11:50:14',NULL,NULL),('5372677a-92f3-4aa0-86a8-aa5a8987b7d0','QCHARTS_PROG001D0004Q','04 - Analytics Catalog','qcharts.analyticsCatalogManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','TEXT_SOURCE','admin','2015-03-03 16:16:25',NULL,NULL),('552343eb-10cb-4e67-8585-54443dfcd8b5','BSC_PROG002D0001E','01 - Vision (Edit)','bsc.visionEditAction.action','Y','N','N',0,0,'BSC','ITEM','GWENVIEW','admin','2014-11-13 20:42:28','admin','2014-11-13 20:47:20'),('566abf01-247c-48f3-b466-d398d2cbb5bb','CORE_PROG001D0010Q','10 - Twitter pane','core.systemTwitterManagementAction.action','N','N','N',0,0,'CORE','ITEM','TWITTER','admin','2014-12-18 19:41:23','admin','2014-12-18 21:10:39'),('56db2eea-746d-47e6-9e9f-3ec034877d22','BSC_PROG004D0002A_C00','02 - Workspace settings (Create Base)','bsc.workspaceSettingsCreateBaseAction.action','N','N','N',0,0,'BSC','ITEM','SYSTEM','admin','2014-12-29 16:31:10',NULL,NULL),('5be72761-ccb9-4250-ad81-8c6a594e9a37','CORE_PROG003D0001E','01 - WebService registration (Edit)','core.systemWsConfigEditAction.action','Y','N','N',0,0,'CORE','ITEM','WWW','admin','2014-10-22 15:53:46',NULL,NULL),('5d5ea262-703b-4d01-839a-29a60a85699b','BSC_PROG004D','Settings','','N','N','N',0,0,'BSC','FOLDER','PROPERTIES','admin','2014-12-07 10:07:10','admin','2014-12-07 10:10:42'),('62712bf3-22a3-47d6-8a70-cfd3177cfbde','BSC_PROG001D0007Q','07 - Introduction','/groovy/introduction.gsp','N','N','N',0,0,'BSC','ITEM','HELP_ABOUT','admin','2015-02-03 19:11:29',NULL,NULL),('67a3d99a-771f-464c-854a-1766c9fa4962','CORE_PROGCOMM0001Q','About!','./pages/about.htm','N','Y','N',300,180,'CORE','ITEM','HELP_ABOUT','admin','2014-10-29 11:41:22','admin','2014-10-29 16:39:29'),('689523ce-1641-43c7-b473-0155c701f0b4','CORE_PROG001D0013Q','13 - Form','core.systemFormManagementAction.action','N','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-25 15:15:59',NULL,NULL),('6937c0ca-c44b-47bf-913e-0b0cae521d0f','CORE_PROG003D0003E','03 - Script support settings (Edit)','core.systemBeanHelpEditAction.action','Y','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-27 14:52:36',NULL,NULL),('6a7eec4a-6cc7-432c-9626-d9e588a9be48','CORE_PROG003D0002A','02 - Expression (Create)','core.systemExpressionCreateAction.action','N','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2014-10-23 11:36:46',NULL,NULL),('6f4ba0b0-e0bd-4743-9a6a-d16e7093ccf3','CORE_PROG002D0002Q','02 - User\'s role','core.userRoleManagementAction.action','N','N','N',0,0,'CORE','ITEM','PEOPLE','admin','2014-10-13 20:11:14','admin','2014-10-13 22:10:47'),('6f5f2b43-a84c-4ff6-b837-452d9824ac29','CORE_PROG003D0003Q','03 - Script support settings','core.systemBeanHelpManagementAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-26 10:19:32','admin','2014-10-26 10:20:50'),('72ca21b1-9121-49a6-86ab-978c37a98acb','CORE_PROG001D0006Q','06 - Message notice','core.systemMessageNoticeManagementAction.action','N','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2014-10-20 12:33:03','admin','2014-10-20 12:48:14'),('74b8bdd5-93c2-408e-85dd-b112086608bd','QCHARTS_PROG001D','Config','','N','N','N',0,0,'QCHARTS','FOLDER','PROPERTIES','admin','2015-01-10 10:22:08',NULL,NULL),('75863ede-bf79-4b8e-a33b-df01bb24acda','CORE_PROG001D0008Q','08 - Report management','core.systemJreportManagementAction.action','N','N','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2014-10-30 16:14:22','admin','2014-10-30 16:22:02'),('77336175-f8e1-43ce-9ba8-169d1f7aeb68','QCHARTS_PROG001D0004A','04 - Analytics Catalog (Create)','qcharts.analyticsCatalogCreateAction.action','N','N','N',0,0,'QCHARTS','ITEM','TEXT_SOURCE','admin','2015-03-03 21:50:27',NULL,NULL),('790a73c4-b22f-4130-9dde-2a501765d4da','CORE_PROG001D0008E_S00','08 - Report paramter (Edit)','core.systemJreportEditParamAction.action','Y','N','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2014-10-31 21:13:32',NULL,NULL),('7b3ab428-b170-49fd-b784-01d88fe02d15','CORE_PROG001D0015Q','15 - Basic message','core.commomLoadForm.action?form_id=FORM001&form_method=init','N','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2015-04-27 14:17:46','admin','2015-04-27 14:18:21'),('7ba4e131-3438-4956-a6f9-c3166128206e','QCHARTS_PROG001D0003Q','03 - Analytics config','qcharts.analyticsConfigManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-03-03 10:40:25',NULL,NULL),('7d0e66e5-b46c-4523-8956-d6e97a907416','BSC_PROG004D0002A','02 - Workspace settings (Create)','bsc.workspaceSettingsCreateAction.action','N','N','N',0,0,'BSC','ITEM','SYSTEM','admin','2014-12-29 11:30:22',NULL,NULL),('80c61b4d-4385-42b1-b139-4a4ad9435d41','BSC_PROG001D0002Q','02 - Organization','bsc.organizationManagementAction.action','N','N','N',0,0,'BSC','ITEM','INTER_ARCHIVE','admin','2014-11-06 08:54:15','admin','2014-11-06 09:10:10'),('828e7bca-4bc4-44b2-8327-de4550e7e22c','QCHARTS_PROG001D0001E','01 - Data source config (Edit)','qcharts.dataSourceConfEditAction.action','Y','N','N',0,0,'QCHARTS','ITEM','SYSTEM','admin','2015-01-12 13:37:24',NULL,NULL),('82908e56-8433-4dd0-add2-f232de32422b','CORE_PROG001D0012A','12 - Form Template (Create)','core.systemFormTemplateCreateAction.action','N','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2015-04-24 14:41:57',NULL,NULL),('8409a87b-d81a-4243-9d92-99df82aa355d','BSC_PROG001D0003Q','03 - Formula','bsc.formulaManagementAction.action','N','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2014-11-17 15:10:28',NULL,NULL),('84dcdbfb-f7fb-4f7b-822b-a9a2d09b0f5f','CORE_PROG001D0009E','09 - Web Context bean (Edit)','core.systemContextBeanEditAction.action','Y','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-11-02 16:56:35','admin','2014-11-02 16:58:24'),('870ff407-876d-4352-90c2-a970ca0615fc','QCHARTS_PROG002D','Basic','','N','N','N',0,0,'QCHARTS','FOLDER','STAR','admin','2015-01-12 20:04:01',NULL,NULL),('87d21f6b-34d2-4d4a-b134-2b41fabb8577','CORE_PROGCOMM0002Q','Upload file','core.commonUploadAction.action','N','Y','N',550,120,'CORE','ITEM','IMPORT','admin','2014-10-29 22:47:01',NULL,NULL),('8d3cafee-94b9-439a-ac89-ac0e04ba0482','CORE_PROGCOMM0004Q','Code editor','core.commonCodeEditorAction.action','Y','N','Y',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-03-12 15:44:31',NULL,NULL),('8f25dd05-72b5-48af-8a60-84c1cb7b7447','CORE_PROG004D0001Q','01 - Event log','core.commomLoadForm.action?form_id=FORM_CORE_4D_001_01&form_method=init','N','N','N',0,0,'CORE','ITEM','PROPERTIES','admin','2015-04-28 10:06:41',NULL,NULL),('8f72667b-d5ae-4009-9bbd-c7ca5a35c8d1','CORE_PROG001D0014E','14 - Form method (Edit)','core.systemFormMethodEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-26 19:49:07',NULL,NULL),('8f8a7e96-6163-4c0a-8a72-a22028661ff8','BSC_PROG003D0001Q','01 - KPI Report','bsc.kpiReportAction.action','N','N','N',0,0,'BSC','ITEM','APPLICATION_PDF','admin','2014-12-01 16:03:32','admin','2014-12-01 16:04:03'),('90fdac4f-264b-4271-a4b8-5163d0c7734e','BSC_PROG002D0005Q','05 - Measure data','bsc.measureDataManagementAction.action','N','N','N',0,0,'BSC','ITEM','CALENDAR','admin','2014-11-24 09:19:15','admin','2014-11-24 09:21:00'),('9190b070-8fa4-4c5e-9a07-3d89b91aee3b','BSC_PROG002D','Scorecard','','N','N','N',0,0,'BSC','FOLDER','STAR','admin','2014-11-13 11:36:58',NULL,NULL),('9220f559-1572-4a52-8692-a6272a4b050e','CORE_PROG001D0005A','05 - Message config (Create)','core.systemMessageNoticeConfigCreateAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-19 22:49:59','admin','2014-10-20 15:42:30'),('92bb4944-b459-47fa-9dc3-750b5d72d931','CORE_PROG002D','Role authority management',' ','N','N','N',0,0,'CORE','FOLDER','PEOPLE','admin','2014-10-02 00:00:00',NULL,NULL),('9331da1e-7edd-436f-a0da-49c540e31678','BSC_PROG002D0008Q','08 - SWOT','bsc.swotManagementAction.action','N','N','N',0,0,'BSC','ITEM','EXCEL','admin','2014-12-19 11:24:00',NULL,NULL),('943bbbe1-f9e4-4c60-9296-46c6335c78f4','CORE_PROG003D0003E_S01','03 - Script support settings (Edit expression mapper)','core.systemBeanHelpEditExpressionMapperAction.action','Y','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-28 15:55:24',NULL,NULL),('94578851-a20c-4421-95f5-6e4020fc1af1','QCHARTS_PROG002D0001Q','01 - Basic query','qcharts.queryManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','CHART_BAR','admin','2015-01-12 20:06:21','admin','2015-01-16 14:13:42'),('947d5caa-1f9b-402f-bc76-802be35d69d2','CORE_PROG001D0014A','14 - Form method (Create)','core.systemFormMethodCreateAction.action','Y','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-26 16:58:48',NULL,NULL),('94ad2c63-89b5-46ac-95d3-aa183cd0cade','BSC_PROG001D0005Q','05 - Workspace','bsc.loadWorkspaceAction.action','N','N','N',0,0,'BSC','ITEM','VIEW_LIST','admin','2015-01-03 12:07:51',NULL,NULL),('95f9a7c8-72c5-4457-8728-ef49f6ef70df','CORE_PROG001D0014Q','14 - Form method','core.systemFormMethodManagementAction.action','Y','N','N',0,0,'CORE','ITEM','TEXT_SOURCE','admin','2015-04-26 16:31:28',NULL,NULL),('9a4003c6-cebe-4f3b-8d77-f18d6a50e80c','CORE_PROG002D0003Q','03 - Role for program (menu)','core.systemProgramMenuRoleManagementAction.action','N','N','N',0,0,'CORE','ITEM','FOLDER','admin','2014-10-13 22:09:19','admin','2014-10-14 12:15:29'),('9eb67bb0-ad8c-47af-a7f4-98f08835aa0c','CORE_PROG001D0006E','06 - Message notice (Edit)','core.systemMessageNoticeEditAction.action','Y','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2014-10-21 11:34:53',NULL,NULL),('9f301759-781d-4f46-9b7f-547b0665c283','CORE_PROG001D0004A','04 - My calendar note (Create)','core.systemCalendarNoteCreateAction.action','N','N','N',0,0,'CORE','ITEM','CALENDAR','admin','2014-10-15 22:14:58','admin','2014-10-16 08:45:36'),('a8309d51-5920-4d6b-af95-3e15d6805b5b','QCHARTS_PROG001D0002Q','02 - Data query mapper','qcharts.dataQueryMapperManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','PROPERTIES','admin','2015-01-14 13:40:29','admin','2015-01-14 14:43:30'),('a94f96b0-ed4b-4111-97b0-2829141c45df','CORE_PROG001D0005E','05 - Message config (Edit)','core.systemMessageNoticeConfigEditAction.action','Y','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-20 00:05:28','admin','2014-10-20 15:42:41'),('a9d1dd82-e65f-4d07-9561-b489e729455d','CORE_PROG001D0002A','02 - Program registration (Create)','core.systemProgramCreateAction.action','N','N','N',0,0,'CORE','ITEM','G_APP_INSTALL','admin','2014-10-07 11:09:08',NULL,NULL),('abade404-fc33-4ae6-891a-094c3dae024a','BSC_PROG004D0002Q','02 - Workspace settings','bsc.workspaceSettingsManagementAction.action','N','N','N',0,0,'BSC','ITEM','SYSTEM','admin','2014-12-28 08:56:31',NULL,NULL),('ac5bcfd0-4abd-11e4-916c-0800200c9a66','CORE_PROG001D0001A','01 - Application site (Create)','core.applicationSystemCreateAction.action','N','N','N',0,0,'CORE','ITEM','COMPUTER','admin','2014-10-03 13:26:36','admin','2014-10-08 15:57:31'),('ad16c4bb-d742-4ffd-83b2-95931b4eff4b','CORE_PROG003D0003A','03 - Script support settings (Create)','core.systemBeanHelpCreateAction.action','N','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-27 10:43:11',NULL,NULL),('b37213ca-3ff4-47e5-9183-4e930485811d','BSC_PROG002D0003Q','03 - Strategy objective','bsc.objectiveManagementAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-16 13:11:01','admin','2014-11-24 20:58:41'),('b4006581-eff2-4f42-9316-028a084fa82e','QCHARTS_PROG001D0004E','04 - Analytics Catalog (Edit)','qcharts.analyticsCatalogEditAction.action','Y','N','N',0,0,'QCHARTS','ITEM','TEXT_SOURCE','admin','2015-03-04 22:05:09','admin','2015-03-04 22:05:31'),('b4220f50-70c4-4c79-82e4-5934a2692b5f','BSC_PROG004D0003Q','03 - Report limit role','bsc.reportRoleViewManagementAction.action','N','N','N',0,0,'BSC','ITEM','SHARED','admin','2015-03-23 20:43:55','admin','2015-03-23 21:21:24'),('b453b796-9ef2-40b1-899c-ac1a8edf2cd1','CORE_PROG002D0001A','01 - Role (Create)','core.roleCreateAction.action','N','N','N',0,0,'CORE','ITEM','SHARED','admin','2014-10-13 08:42:30','admin','2014-10-13 08:45:09'),('b6b89559-6864-46ab-9ca9-0992dcf238f1','CORE_PROG001D0001Q','01 - Application site','core.applicationSystemManagementAction.action','N','N','N',0,0,'CORE','ITEM','COMPUTER','admin','2014-10-02 00:00:00',NULL,NULL),('b79dfcab-7163-4b21-b763-dfdef1bb9ffa','BSC_PROG001D0001Q_S00','Employee','bsc.employeeSelectAction.action','Y','Y','N',750,600,'BSC','ITEM','PERSON','admin','2014-11-22 12:25:25','admin','2014-11-22 12:33:17'),('b7a73743-112f-4612-afb1-4c83fe447e18','CORE_PROG001D0010A','10 - Twitter pane (Create)','core.systemTwitterCreateAction.action','N','N','N',0,0,'CORE','ITEM','TWITTER','admin','2014-12-18 21:11:15','admin','2014-12-18 22:08:17'),('bc077f57-b217-4d78-8989-78db5f3070a5','CORE_PROG001D0004Q','04 - My calendar note','core.systemCalendarNoteManagementAction.action','N','N','N',0,0,'CORE','ITEM','CALENDAR','admin','2014-10-15 11:29:44','admin','2014-10-16 08:45:46'),('bf965d90-06d4-4683-a8d8-ef6c934c9a27','CORE_PROG002D0001E','01 - Role (Edit)','core.roleEditAction.action','Y','N','N',0,0,'CORE','ITEM','SHARED','admin','2014-10-13 08:42:51','admin','2014-10-13 08:45:21'),('c074a3ee-13bf-4a1b-908c-18e435e0f557','CORE_PROG001D0008Q_S00','Report preview settings','core.systemJreportPreviewParamAction.action','Y','Y','N',650,410,'CORE','ITEM','APPLICATION_PDF','admin','2015-04-20 11:38:00','admin','2015-04-21 09:07:14'),('c0a8696d-cd34-4d4c-9be3-24d20711a3a4','CORE_PROG004D','Log','','N','N','N',0,0,'CORE','FOLDER','VIEW_LIST','admin','2015-04-28 09:56:32',NULL,NULL),('c1b2abc6-7d7c-4337-ad17-e206e65fb5dc','BSC_PROG001D0008A','08 - Aggregation Method (Create)','bsc.aggregationMethodCreateAction.action','N','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2015-03-12 09:49:10',NULL,NULL),('c20ad36f-4fb7-4b98-8fde-594c4a9de7c4','BSC_PROG001D0006Q','06 - Region view','bsc.regionMapViewAction.action','N','N','N',0,0,'BSC','ITEM','STOCK_HOME','admin','2015-01-20 20:08:48','admin','2015-01-22 19:06:47'),('c33f14f6-06d7-4b37-8d11-97450f33971c','CORE_PROG001D0002Q','02 - Program registration','core.systemProgramManagementAction.action','N','N','N',0,0,'CORE','ITEM','G_APP_INSTALL','admin','2014-10-06 15:17:20',NULL,NULL),('c9c8972b-2203-4cf0-89fe-a6b00bb803ac','BSC_PROG004D0001Q','01 - Report property','bsc.reportPropertyManagementAction.action','N','N','N',0,0,'BSC','ITEM','PROPERTIES','admin','2014-12-07 10:08:03','admin','2014-12-07 10:09:03'),('ca013410-0164-4b41-a9d0-d3147111c408','CORE_PROG003D0003E_S00','03 - Script support settings (Edit expression)','core.systemBeanHelpEditExpressionAction.action','Y','N','N',0,0,'CORE','ITEM','SYSTEM','admin','2014-10-28 09:24:06','admin','2014-10-28 09:28:35'),('cbc92f50-0a61-497c-bccb-69cab4875402','BSC_PROG003D','Report','','N','N','N',0,0,'BSC','FOLDER','TEMPLATE','admin','2014-12-01 16:01:42','admin','2014-12-02 14:29:18'),('cdb68c68-1c5a-4833-8601-ce42ce2a6da9','QCHARTS_PROG002D0002Q','02 - Analytics','qcharts.analyticsManagementAction.action','N','N','N',0,0,'QCHARTS','ITEM','VIEW_LIST','admin','2015-03-05 10:02:59',NULL,NULL),('ce0c120b-6ca2-455e-a6d1-650a5fcfc3c4','BSC_PROG001D0008Q','08 - Aggregation Method','bsc.aggregationMethodManagementAction.action','N','N','N',0,0,'BSC','ITEM','TEXT_SOURCE','admin','2015-03-11 20:35:16',NULL,NULL),('ce49a49c-edcf-45f2-b237-95e6e6e1ef33','CORE_PROG001D0008E','08 - Report management (Edit)','core.systemJreportEditAction.action','Y','N','N',0,0,'CORE','ITEM','APPLICATION_PDF','admin','2014-10-31 14:39:31',NULL,NULL),('cf2de76b-4a3d-4d19-a638-d28c705748b9','CORE_PROG003D0001A','01 - WebService registration (Create)','core.systemWsConfigCreateAction.action','N','N','N',0,0,'CORE','ITEM','WWW','admin','2014-10-22 14:38:19','admin','2014-10-22 14:40:27'),('d454755c-1a37-4d21-8ff9-284a42482d11','BSC_PROG002D0004E','04 - KPI (Edit)','bsc.kpiEditAction.action','Y','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-23 17:16:00',NULL,NULL),('d5dfeb66-9c47-4cc7-9fd8-8395dba1333e','CORE_PROG001D0004E','04 - My calendar note (Edit)','core.systemCalendarNoteEditAction.action','Y','N','N',0,0,'CORE','ITEM','CALENDAR','admin','2014-10-16 11:54:10',NULL,NULL),('d6339624-da34-4854-958f-a4c3c871863a','BSC_PROG003D0003Q','03 - Department Report','bsc.departmentReportAction.action','N','N','N',0,0,'BSC','ITEM','APPLICATION_PDF','admin','2014-12-14 09:55:08',NULL,NULL),('d6592509-197c-4ae6-8a33-72c9b70e39ab','CORE_PROG001D0012E','12 - Form Template (Edit)','core.systemFormTemplateEditAction.action','Y','N','N',0,0,'CORE','ITEM','TEMPLATE','admin','2015-04-24 20:23:55',NULL,NULL),('d7054c4d-75b3-4531-b339-2f029075eaf9','BSC_PROG002D0002A','02 - Perspective (Create)','bsc.perspectiveCreateAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-15 09:54:52','admin','2014-11-16 09:33:47'),('d80cb826-3598-4cf8-bdfe-acf645791d04','CORE_PROGCOMM0003Q','Signature','core.commonSignatureAction.action','N','Y','N',480,320,'CORE','ITEM','SIGNATURE','admin','2015-02-13 20:06:06','admin','2015-02-16 19:19:25'),('d999d561-0521-46c8-b5d4-e63b4b6d486c','BSC_PROG001D0002Q_S00','Department','bsc.organizationSelectAction.action','Y','Y','N',640,550,'BSC','ITEM','INTER_ARCHIVE','admin','2014-11-12 09:14:31','admin','2014-11-12 11:07:53'),('df3f38e0-cf25-4712-aae8-78d748219d63','CORE_PROG003D','BO Service config','','N','N','N',0,0,'CORE','FOLDER','DIAGRAM','admin','2014-10-22 11:51:48','admin','2014-10-22 12:16:31'),('df8a9ea3-7ca0-4bf2-82fb-bf6c7f3eda7a','BSC_PROG002D0004A','04 - KPI (Create)','bsc.kpiCreateAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-22 10:25:04',NULL,NULL),('e2426bc7-e9f3-40b6-9b24-7cdc26121a97','BSC_PROG002D0003A','03 - Strategy objective (Create)','bsc.objectiveCreateAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-17 09:28:00','admin','2014-11-24 20:58:19'),('e252148d-eceb-42b6-b55f-1637b9f80ec4','BSC_PROG003D0005Q','05 - Objectives Dashboard','bsc.objectivesDashboardAction.action','N','N','N',0,0,'BSC','ITEM','CHART_PIE','admin','2015-04-01 19:37:10',NULL,NULL),('e4e0e29d-b638-461e-8779-48d0e031646f','BSC_PROG001D0001Q','01 - Employee','bsc.employeeManagementAction.action','N','N','N',0,0,'BSC','ITEM','PERSON','admin','2014-11-03 15:25:03','admin','2014-11-03 21:48:28'),('e6a6a097-5dc7-41b8-999c-96f5121acc1f','BSC_PROG002D0007Q','07 - Strategy Map','bsc.strategyMapManagementAction.action','N','N','Y',0,0,'BSC','ITEM','DIAGRAM','admin','2014-12-15 15:27:43','admin','2014-12-15 15:28:28'),('eb05899b-4bdb-448a-b24d-2431efa8a990','CORE_PROG002D0001Q','01 - Role','core.roleManagementAction.action','N','N','N',0,0,'CORE','ITEM','SHARED','admin','2014-10-02 00:00:00','admin','2014-10-13 08:45:30'),('eb59b40c-010d-4318-968a-918f5c179cd4','BSC_PROG001D0001E_S00','01 - Employee (password)','bsc.employeePasswordEditAction.action','Y','Y','N',800,260,'BSC','ITEM','PERSON','admin','2014-11-12 23:08:49','admin','2014-11-12 23:14:17'),('ecb5be95-15be-4f9a-aa3a-a3a1c7315a2a','CORE_PROG002D0001E_S00','Role\'s permitted settings','core.rolePermittedEditAction.action','Y','N','N',0,0,'CORE','ITEM','SHARED','admin','2014-10-13 09:17:42','admin','2014-10-22 08:47:52'),('ed286ac7-9fb1-4053-814c-ce4f1b9a85ac','CORE_PROG003D0001Q','01 - WebService registration','core.systemWsConfigManagementAction.action','N','N','N',0,0,'CORE','ITEM','WWW','admin','2014-10-22 11:53:21','admin','2014-10-22 14:40:42'),('ed838fc5-f83d-4386-95dd-03846f439f58','BSC_PROG002D0004Q','04 - KPI','bsc.kpiManagementAction.action','N','N','N',0,0,'BSC','ITEM','STAR','admin','2014-11-20 15:44:50','admin','2014-11-20 15:45:16'),('efaf9edd-19cd-4fc7-81d5-9784378997b5','CORE_PROG001D0002E','02 - Program registration (Edit)','core.systemProgramEditAction.action','Y','N','N',0,0,'CORE','ITEM','G_APP_INSTALL','admin','2014-10-08 08:50:29',NULL,NULL),('f0c4f5ac-4da3-4218-a14d-7ee6f3ebdce9','CORE_PROG001D0015E','15 - Basic message (Edit)','core.commomLoadForm.action?form_id=FORM002&form_method=edit','Y','N','N',0,0,'CORE','ITEM','HELP_ABOUT','admin','2015-04-27 20:28:03','admin','2015-04-27 20:50:35'),('f336c30b-cc22-439a-b951-950584a8b7e9','BSC_PROG002D0001Q','01 - Vision','bsc.visionManagementAction.action','N','N','N',0,0,'BSC','ITEM','GWENVIEW','admin','2014-11-13 11:38:02','admin','2014-11-13 11:40:18'),('f4c757b5-f70c-420a-bda9-a6831ce5f50c','BSC_PROG003D0004Q','04 - Perspectives Dashboard','bsc.perspectivesDashboardAction.action','N','N','N',0,0,'BSC','ITEM','CHART_PIE','admin','2015-03-31 10:40:54',NULL,NULL),('fa12b194-7a86-465a-a448-1a577eb05f46','BSC_PROG003D0006Q','06 - KPIs Dashboard','bsc.kpisDashboardAction.action','N','N','N',0,0,'BSC','ITEM','CHART_PIE','admin','2015-04-04 15:55:51',NULL,NULL),('fe08215e-bb9f-4b36-b90c-bead9afaafa7','CORE_PROG002D0001A_S00','01 - Role (Copy as new)','core.roleCopyAsNewAction.action','Y','Y','N',750,500,'CORE','ITEM','SHARED','admin','2015-03-22 12:07:49','admin','2015-03-22 12:28:03');
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
INSERT INTO `tb_sys_upload_tran_segm` VALUES ('0ec39f8f-eb83-44c7-92d4-a7d5086d16e6','TRAN004','OBJ_ID',1,1,'admin','2015-05-26 10:29:13',NULL,NULL),('0f62a211-ad92-4ea9-97d4-cfae69261a55','TRAN004','TARGET',4,4,'admin','2015-05-26 10:30:54',NULL,NULL),('107a63ab-ef5a-4520-86a3-c9dfcba5f938','TRAN004','MANAGEMENT',8,8,'admin','2015-05-26 10:33:31',NULL,NULL),('1eb75881-8a4d-41be-84da-48b5f6af0cc7','TRAN003','NAME',2,2,'admin','2015-05-26 08:56:16',NULL,NULL),('1f13fe26-bac5-4018-a5f8-f3d10479c389','TRAN001','VIS_ID',0,0,'admin','2015-05-25 13:26:52',NULL,NULL),('2070c0ab-5d2d-4224-9e5f-14b0af5c26c4','TRAN003','TARGET',4,4,'admin','2015-05-26 08:57:31',NULL,NULL),('22f47521-ce31-4abf-8b59-9bba62c6b70f','TRAN004','COMPARE_TYPE',9,9,'admin','2015-05-26 10:34:03',NULL,NULL),('23097189-9295-4cc5-b2b8-96c7ff40e4b0','TRAN005','FREQUENCY',4,4,'admin','2015-05-26 11:59:15',NULL,NULL),('23547ccb-54df-4500-b043-e5043cac2232','TRAN004','DESCRIPTION',15,15,'admin','2015-05-26 10:37:56',NULL,NULL),('241d818e-030c-4b68-b2db-cefcdb08d3c0','TRAN002','WEIGHT',3,3,'admin','2015-05-25 15:06:50',NULL,NULL),('2f502cb8-0952-4bb9-b13c-af7442c2b0e4','TRAN004','USER_MEASURE_SEPARATE',13,13,'admin','2015-05-26 10:36:44',NULL,NULL),('40ae97d2-5feb-4d65-ae26-af2adea2e728','TRAN004','MIN',5,5,'admin','2015-05-26 10:31:34',NULL,NULL),('42893686-1809-44e3-a369-0003dfdd75bb','TRAN003','WEIGHT',3,3,'admin','2015-05-26 08:56:56',NULL,NULL),('5245baf9-72f8-419f-8864-492b80ad4081','TRAN003','PER_ID',1,1,'admin','2015-05-26 08:55:39',NULL,NULL),('568759ae-bf25-4c9d-ad31-44c0fba7da27','TRAN003','MIN',5,5,'admin','2015-05-26 08:58:03',NULL,NULL),('573f3a7e-be78-4adc-b895-031d2e70adb9','TRAN002','VIS_ID',1,1,'admin','2015-05-25 15:05:54',NULL,NULL),('59df7630-ff32-4146-87f1-00db9ec6978f','TRAN005','KPI_ID',0,0,'admin','2015-05-26 11:57:24',NULL,NULL),('6046f795-afcb-485b-904e-3f80c7d13484','TRAN005','DATE',1,1,'admin','2015-05-26 11:57:50',NULL,NULL),('6483cc80-0b7d-460c-9708-c791d478b422','TRAN004','QUASI_RANGE',14,14,'admin','2015-05-26 10:37:16',NULL,NULL),('6510741d-6876-4ce6-b41f-9b64f8c61c0b','TRAN005','ACTUAL',3,3,'admin','2015-05-26 11:58:46',NULL,NULL),('6a047983-02e7-400b-a54e-fe2b7e812458','TRAN003','DESCRIPTION',6,6,'admin','2015-05-26 08:58:42',NULL,NULL),('6d0eea62-f8d4-4941-97e4-3504231f3508','TRAN005','EMP_ID',6,6,'admin','2015-05-26 12:00:05',NULL,NULL),('742d699f-3ddc-44af-b00d-f5826c7ca38f','TRAN004','NAME',2,2,'admin','2015-05-26 10:29:45',NULL,NULL),('77c32725-91c0-4407-a9b9-25b36d64743d','TRAN004','CAL',10,10,'admin','2015-05-26 10:34:35',NULL,NULL),('8b638d00-74b0-4bf8-92f1-7c795ac24de0','TRAN004','ORGA_MEASURE_SEPARATE',12,12,'admin','2015-05-26 10:36:09',NULL,NULL),('94e35504-24bd-4a9e-851b-789a396e02d2','TRAN005','TARGET',2,2,'admin','2015-05-26 11:58:14',NULL,NULL),('9fbab5cc-a24d-4db1-babd-206bf6fe09bb','TRAN004','FOR_ID',7,7,'admin','2015-05-26 10:32:56',NULL,NULL),('a62a7541-cb57-43b0-a257-22525912a868','TRAN003','OBJ_ID',0,0,'admin','2015-05-26 08:55:04',NULL,NULL),('a7b0a3e8-dffd-4657-9270-d9f2c8781091','TRAN002','PER_ID',0,0,'admin','2015-05-25 15:05:24',NULL,NULL),('b0e1cd0d-f9a0-4344-a127-71ab7622cf6b','TRAN004','WEIGHT',3,3,'admin','2015-05-26 10:30:14',NULL,NULL),('b1f1d1c5-3c15-4612-a2e9-c4c230c5c6bd','TRAN004','ID',0,0,'admin','2015-05-26 10:28:45',NULL,NULL),('bc38b23f-b5e2-410e-9dcc-a5cc1e17c4ed','TRAN002','DESCRIPTION',6,6,'admin','2015-05-25 15:14:55',NULL,NULL),('cd88c4ce-b849-4d1e-bcaf-556e75f918a5','TRAN001','CONTENT',2,2,'admin','2015-05-25 13:27:47',NULL,NULL),('dd25a684-4775-4fdb-9d72-675d97180244','TRAN002','MIN',5,5,'admin','2015-05-25 15:14:26',NULL,NULL),('e41e29a6-f749-4628-a024-b988ad422a39','TRAN005','ORG_ID',5,5,'admin','2015-05-26 11:59:40',NULL,NULL),('e6240b19-2f1a-45c1-9f92-d2544ec73e9e','TRAN004','DATA_TYPE',11,11,'admin','2015-05-26 10:35:33',NULL,NULL),('ed1f0035-c9fa-413d-a198-b171098a499f','TRAN004','UNIT',6,6,'admin','2015-05-26 10:32:09',NULL,NULL),('f1ed8d54-8042-4ab9-9d6d-a0bac03273da','TRAN002','TARGET',4,4,'admin','2015-05-25 15:14:00',NULL,NULL),('f336c34f-b939-46de-99df-8755ef794a7e','TRAN001','TITLE',1,1,'admin','2015-05-25 13:27:21',NULL,NULL),('f625df4a-19b0-4a38-975a-ee33953e5721','TRAN002','NAME',2,2,'admin','2015-05-25 15:06:20',NULL,NULL);
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

-- Dump completed on 2015-05-26 13:50:29
