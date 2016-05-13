# baidu-apistore
A general framework for working with web services in the form of API from http://apistore.baidu.com/

### How To Use?
```java
Map<String, String> headers = new HashMap<>();
headers.put("apikey", "put your api key here");
WebService service = new WebServiceProvider("api url", headers);
String outcome = service.get();
