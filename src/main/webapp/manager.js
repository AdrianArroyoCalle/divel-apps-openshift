/**
 * manager.js Used by Divel Apps Manager. Licensed under GPL-2 
 */
var statetext;
var progress;
var Buttons = {};

function DoNothing()
{
	statetext.textContent="Nothing";
	progress.value="0";
}
function Publish()
{
	statetext.textContent="Publishing content in Divel Feed";
	progress.value="10";
	
	var sendurl="/feed?NEW=0&TITLE="+document.getElementById("title").value+
	"&AUTHOR="+document.getElementById("author").value+
	"&BODY="+document.getElementById("htmltext").value;
	
	if(document.getElementById("sendmail").checked==true)
		sendurl+="&SENDMAIL";
	
	progress.value="20";
	
	var request=new XMLHttpRequest();
	request.onload=DoNothing;
	request.open("GET",sendurl,false);
	request.send();
	
	progress.value="30";
	
}
function SetMasterKey()
{
	statetext.textContent="Setting Divel Master Key";
	progress.value="10";
	
	var sendurl="/masterkey?KEY="+document.getElementById("newmasterkey").value;
	
	var request=new XMLHttpRequest();
	request.onload=DoNothing;
	request.open("GET",sendurl,false);
	request.send();
	
	progress.value="20";
}
function CreateRedirect()
{
	statetext.textContent="Creating redirect in Divel Redirecter";
	progress.value="10";
	
	var sendurl="/redirecter?ID="+document.getElementById("idredirecter").value+
	"&URL="+document.getElementById("urlredirecter").value+
	"&MASTERKEY="+document.getElementById("masterkey").value;
	
	var request=new XMLHttpRequest();
	request.onload=DoNothing;
	request.open("GET",sendurl,false);
	request.send();
	
	progress.value="20";
}
function UseUUID()
{
	statetext.textContent="Using UUID in Divel Licencias";
	progress.value="10";
	
	var sendurl="/licencias?GUID="+document.getElementById("uuid").value;
	
	var request=new XMLHttpRequest();
	request.onload=DoNothing;
	request.open("GET",sendurl,false);
	request.send();
	
	progress.value="20";
}
function Loader()
{
	statetext=document.getElementById("currentoperation");
	progress=document.getElementById("status");
	
	statetext.textContent="Starting Divel Apps Manager WebApp";
	progress.value="10";
	
	Buttons.publish=document.getElementById("publishnew");
	Buttons.publish.addEventListener("click",Publish);
	
	Buttons.useuuid=document.getElementById("useuuid");
	Buttons.useuuid.addEventListener("click",UseUUID);
	
	Buttons.setmasterkey=document.getElementById("setmasterkey");
	Buttons.setmasterkey.addEventListener("click",SetMasterKey);
	
	Buttons.createredirect=document.getElementById("setredirecter");
	Buttons.createredirect.addEventListener("click",CreateRedirect);
	
	DoNothing();
	
}
