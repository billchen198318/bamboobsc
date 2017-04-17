
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/15-Formula.md">⇦ 
Previous section 15 - Formula</a>


# 16 - API (REST/SOAP) for get Scorecard
# Introduction
bambooBSC API rest/soap for get Scorecard.

### only 0.7.0-RELEASE or later version has support.

<br>
<br>

# 1. REST:
### REST url:

127.0.0.1:8080/gsbsc-web/services/jaxrs/scorecard1/

### Example:

```
curl -i -X GET "http://127.0.0.1:8080/gsbsc-web/services/jaxrs/scorecard1?visionOid=1089abb5-3faf-445d-88ff-cd7690ac6743&startDate=&endDate=&startYearDate=2015&endYearDate=2016&frequency=6&dataFor=all&measureDataOrganizationOid=&measureDataEmployeeOid=&contentFlag="
```
<br>

# 2. SOAP:
### WSDL url:
http://127.0.0.1:8080/gsbsc-web/services/api?wsdl
### Method:
getScorecard1

<br>
### Example:

```XML
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://webservice.bsc.greenstep.netsteadfast.com/">
	<soapenv:Header/>
	<soapenv:Body>
		   
		<web:getScorecard1>
			<visionOid>1089abb5-3faf-445d-88ff-cd7690ac6743</visionOid>
			<startDate></startDate>
			<endDate></endDate>
			<startYearDate>2015</startYearDate>
			<endYearDate>2016</endYearDate>
			<frequency>6</frequency>
			<dataFor>all</dataFor>
			<measureDataOrganizationOid></measureDataOrganizationOid>
			<measureDataEmployeeOid></measureDataEmployeeOid>
			<contentFlag></contentFlag>
		</web:getScorecard1>
		      
	</soapenv:Body>
</soapenv:Envelope>
```


# Query parameter description

| Name |description |
| --- | --- |
| visionOid | Vision PK, tb_vision.OID |
| startDate | measure-data start date, if frequency is 1,2,3 is required |
| endDate | measure-data end date, if frequency is 1,2,3 is required |
| startYearDate | measure-data start year, if frequency is 4,5,6 is required |
| endYearDate | measure-data end year, if frequency is 4,5,6 is required |
| dataFor | `all` / `employee` / `organization` |
| measureDataOrganizationOid | if dataFor is `organization` this field is required |
| measureDataEmployeeOid | if dataFor is `employee` this field is required |
| contentFlag | if value `Y` , will put xml and json data result out |


### Frequency value description

| Frequency |description | 
| --- | --- |
| 1 | Day |
| 2 | Week |
| 3 | Month |
| 4 | Quarter |
| 5 | Half of year |
| 6 | Year |

<br>
<br>

# REST Result:

### Example:

```JSON
{
   "success":"Y",
   "message":"",
   "outJsonData":"{}",
   "outXmlData":"",
   "htmlBodyUrl":"192.168.1.101:8080/gsbsc-web/bsc.printContentAction.action?oid=67b28d05-008f-40ec-8c2f-28b548888f78",
   "perspectivesMeterChartUrl":[
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=df042678-401c-48ea-ac87-b14564b38377",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=8f6c9d81-4680-42df-9b5b-704efb25a627",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=30d85422-0d85-47bb-ae57-ef4aee28bbec",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=169ab07b-5418-47e4-9cb3-e3dbc876c83f"
   ],
   "objectivesMeterChartUrl":[
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=2b527381-073d-4a6d-8782-da7d25df50ae",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=244f6394-c3df-497c-a99a-1f6e318d9635",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=f45c7f6b-484b-4dfe-b85c-eb791b7cf2ef",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=c471648c-c5b4-435b-832c-8599c135fb10",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=0a427383-5599-4665-b243-9a384caab7d8"
   ],
   "kpisMeterChartUrl":[
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=26f99629-a201-48c8-b9c1-0cf9821ee0aa",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=1d01f2fc-6323-418c-9831-d5e552094aea",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=fc33691c-7198-46e8-b52d-27f0bad69f5f",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=ec9ceaa5-b877-4f5b-912b-539375e5826c",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=8c354778-8932-46ff-8a55-84d19d663e71",
      "192.168.1.101:8080/gsbsc-web/bsc.commonMeterChartAction.action?oid=f88423e8-bc02-4285-8f53-649f21ff7abe"
   ],
   "pieChartUrl":"192.168.1.101:8080/gsbsc-web/bsc.commonPieChartAction.action?oid=6181f976-a293-46d7-a77d-80c8a21ffeab",
   "barChartUrl":"192.168.1.101:8080/gsbsc-web/bsc.commonBarChartAction.action?oid=ec271a18-e9fb-4dcd-9a6f-91fd43d3a86f"
}
```

<br>

# Result variable description

| Name |description |
| --- | --- |
| htmlBodyUrl | This url will print BSC report html content |
| pieChartUrl | This url will paint BSC-perspectives PIE chart |
| barChartUrl | This url will paint BSC-perspectives BAR chart |
| perspectivesMeterChartUrl | This url items will paint perspective Meter Chart |
| objectivesMeterChartUrl | This url items will paint objective Meter Chart |
| kpisMeterChartUrl | This url items will paint KPI Meter Chart |
| outJsonData | This variable value is scorecard data for JSON, but query parameter contentFlag value need set Y to effective |
| outXmlData | This variable value is scorecard data for XML, but query parameter contentFlag value need set Y to effective|

