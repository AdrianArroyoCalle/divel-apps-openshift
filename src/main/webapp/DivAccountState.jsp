<%@ page import="com.google.appengine.api.datastore.DatastoreService"
import="com.google.appengine.api.datastore.DatastoreServiceFactory"
import="com.google.appengine.api.datastore.Entity"
import="com.google.appengine.api.datastore.EntityNotFoundException"
import="com.google.appengine.api.datastore.Key"
import="com.google.appengine.api.datastore.KeyFactory"
import="com.divel.online.apps.*"
 %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DivAccount - State</title>
<script src="state.js"></script>
</head>
<body>
<div id="arriba" style="text-align: center;">
<img src="https://sites.google.com/site/divelmedia/home/nuevologo/divel_128.png" alt="Divel logo"/>
<h1>DivAccount - State</h1>
</div>
<%
//GetCurrentUser
Long azpazeta=new Long(0);
Long mct=0l;
Long bloco=0l;
Long dcm=0l;
Long divso=0l;
Long developer=0l;

Long divcoins=0l;
Long karma=0l;
Long buys=0l;
Long uses=0l;

String divelNN="";
String googleplus="";
String dkey1="";
String email="";

if(request.getParameter("EMAIL")!=null){
	email=request.getParameter("EMAIL");
try{
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key keydiv=KeyFactory.createKey("DIVEL-ACCOUNTS",email);
	Entity div=datastore.get(keydiv);
	azpazeta=(Long)div.getProperty("Azpazeta");
	mct=(Long)div.getProperty("DivCity");
	bloco=(Long)div.getProperty("Bloco");
	dcm=(Long)div.getProperty("LicenseDCM");
	divso=(Long)div.getProperty("DivSO");
	developer=(Long)div.getProperty("Developer");
	
	divcoins=(Long)div.getProperty("DivCoins");
	karma=(Long)div.getProperty("Karma");
	buys=(Long)div.getProperty("NumeroBuys");
	uses=(Long)div.getProperty("VecesUsado");
	
	divelNN=div.getProperty("DivelNetworkName").toString();
	
	googleplus=(String)div.getProperty("Google+").toString();
	dkey1=(String)div.getProperty("DivelKey1").toString();
	
	
}catch(EntityNotFoundException e){
	e.printStackTrace();
}
}
%>

<%!
public String TraduceData (Long data) {
	String frase = null;
	if(data==0){
		frase="Not registered";
	}
	if(data==1){
		frase="Registered";
	}
	return frase;
}
%>

<div id="services">
Azpazeta: <%= TraduceData(azpazeta) %><br>
DivCity: <%= TraduceData(mct) %><br>
Bloco: <%= TraduceData(bloco) %><br>
DivCity Manager: <%= TraduceData(dcm) %><br>
Divel SO: <%= TraduceData(divso) %><br>
Developer: <%= TraduceData(developer) %><br>
</div>
<div id="moneyData">
DivCoins: <%= divcoins %><br>
Karma: <%= karma %>(With 1000 karma you can get 100 DivCoins)<br>
Number of products bought: <%= buys %><br>
Number of uses: <%= uses %><br>
</div>
<div id="userData">
Divel Network Name: <%= divelNN %><br>
Google+ Profile: <%= googleplus %><br>
Divel Key - v1: <%= dkey1 %><br>
Divel Key - v2: <button id="requestDivelKey2" onclick="sendEmailconfirm();">Get Divel Key - v2</button>
</div>
<button id="requestDelete" onclick="insertDivKey2confirm();">Delete DivAccount</button>

<div id="console">

</div>

<div id="culo">
<p>Java version: <%= System.getProperty("java.version") %><br>
Java vendor: <%= System.getProperty("java.vendor") %><br>
Operatyng system: <%= System.getProperty("os.name") %><br>
OS Version: <%= System.getProperty("os.version") %><br>
OS Arch: <%= System.getProperty("os.arch") %><br>
Date: <%= new java.util.Date() %><br>
</p>
</div>
</body>
</html>