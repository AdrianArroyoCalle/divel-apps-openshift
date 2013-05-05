/**
 * Social Share JavaScript Code
 */


function Google(){
	var url=document.getElementById("url").value;
	if(url==null || url==""){alert("You must enter a URL");}else{
		var plusone="http://plus.google.com/share?url="+url;
		var open=window.open(plusone,"Google+ Share","_blank");
		
		
		
	}
	
	
	
}
function Tuenti(){
	var url=document.getElementById("url").value;
	if(url==null || url==""){alert("You must enter a URL");}else{
		var plusone="http://tuenti.com/share?url="+url;
		var open=window.open(plusone,"Tuenti Share","_blank");
		
		
		
	}
	
	
	
}
function Facebook(){
	var url=document.getElementById("url").value;
	if(url==null || url==""){
		alert("You must enter a URL");
	}else{
		var facebook="http://www.facebook.com/sharer.php?u="+url;
		var open=window.open(facebook,"Facebook Share","_blank");
	}
}
function Twitter(){
	var url=document.getElementById("url").value;
	if(url==null || url==""){
		alert("You must enter a URL");
	}else{
		var twitter="http://www.twitter.com/share?url="+url;
		var open=window.open(twitter,"Twitter Share","_blank");
	}
}

function Init(){
	var Unity = external.getUnityObject(1.0); 

	Unity.init({name: "Social Share",
	            iconUrl: "http://divelapps.appspot.com/socialshare128.png",
	            onInit: unityReady});

}
function unityReady(){
	Unity.MessagingIndicator.addAction("Share a URL", SocialShare);
	
}
function SocialShare(){
	window.open("http://divelapps.appspot.com/socialshare.html","Social Share","_blank");
	
	
}


