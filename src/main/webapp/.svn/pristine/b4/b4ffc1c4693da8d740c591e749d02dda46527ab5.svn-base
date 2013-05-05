/**
 * state.js:Used for quick operation in DivAccount State Page
 */

function sendEmailconfirm(){
	//Enviar email cuando quiere obtener Divel Key v2

	
	
	var parrafo=document.createElement("p");
	parrafo.value="Sending email...";
	document.getElementById("console").appendChild(parrafo);
	var email=getParameter("EMAIL");
	window.open("/divaccounts?EMAIL="+email+"&SENDMAIL","Sending email","_blank");
	
	
}
function getParameter(parameter){
	// Obtiene la cadena completa de URL
	var url = location.href;
	/* Obtiene la posicion donde se encuentra el signo ?,
	ahi es donde empiezan los parametros */
	var index = url.indexOf("?");
	/* Obtiene la posicion donde termina el nombre del parametro
	e inicia el signo = */
	index = url.indexOf(parameter,index) + parameter.length;
	/* Verifica que efectivamente el valor en la posicion actual
	es el signo = */
	if (url.charAt(index) == "="){
	// Obtiene el valor del parametro
	var result = url.indexOf("&",index);
	if (result == -1){result=url.length;};
	// Despliega el valor del parametro
	return(url.substring(index + 1,result));
	}
}

function insertDivKey2confirm(){
	//Confirmar borrado con Divel Key v2
	var dkey2=prompt("Insert your Divel Key v2");
	if(dkey2!=null)
		{
		var parrafo=document.createElement("p");
		parrafo.value="Deleting account";
		document.getElementById("console").appendChild(parrafo);
		var email=getParameter("EMAIL");
		window.open("/divaccounts?EMAIL="+email+"&DELETE&DKEY2="+dkey2,"Sending email","_blank");
		
		
		}
	
}