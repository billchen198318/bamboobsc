<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/14-WebContextBean.md">⇦ 
Previous section 14 - Web Context bean</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/16-API.md">⇨ 
Next section 16 - API</a>

# 15 - Formula
# Introduction
Management bambooBSC formula for REPORT/KPI score calculate.


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. GROOVY http://www.groovy-lang.org/documentation.html<br/>
3. BeanShell http://www.beanshell.org/intro.html

# Management formula or KPI aggregation method
management aggregation method use `A. Balanced scorecard -> Basic data -> 08 - Aggregation Method`<br/>
management formula use `A. Balanced scorecard -> Basic data -> 03 - Formula`

# Formula & Aggregation method computing architecture
![Image of FORMULA-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/15-001.jpg)
<br/>
<br/>
# BscFormulaUtils
| Name | return |description |
| --- | --- | --- |
| getFormulaById(String forId) | FormulaVO | get formula data by formula-id |
| getTrendsFlagMap(boolean select) | `Map<String, String>` | get formula trends flag options for `gs:select`  |
| getParameter(BscMeasureData data) | `Map<String, Object>` | get formula need variable (target and actual) |
| parse(String type, String returnMode, String returnVar, String expression, `Map<String, Object>` parameter) | `Map<String, Object>` | parse expression |
| parse(FormulaVO formula, BscMeasureData data) | Object | parse expression |
| parseKPIPeroidScoreChangeValue(FormulaVO formula, float currentPeroidScore, float previousPeroidScore) | Object | parse expression for peroid score change |

# AggregationMethodUtils
| Name | return |description |
| --- | --- | --- |
| processDefaultMode(KpiVO kpi) | float | process expression |
| processDateRangeMode(KpiVO kpi, String frequency) | float | process expression for date-range |
| processDefaultModeByOid(String aggregationMethodOid, KpiVO kpi) | Object | process expression by primary key |
| processDateRangeModeByOid(String aggregationMethodOid, KpiVO kpi, String frequency) | Object | process expression by primary key |
| processDefaultModeByUK(String id, KpiVO kpi) | Object | process expression by unique key |
| processDateRangeModeByUK(String id, KpiVO kpi, String frequency) | Object | process expression by unique key |
| findSimpleByOid(String oid) | AggregationMethodVO | find resource by primary key |
| findSimpleById(String id) | AggregationMethodVO | find resource by unique key |
| getNameByOid(String oid) | String | find resource name by primary key |
| getNameByAggrId(String id) | String | find resource name by unique key |
| findMap(boolean pleaseSelect) | `Map<String, String>` | get resource options for `gs:select` |

