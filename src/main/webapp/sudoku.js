/*
 * Esto es un borrador de JavaScript.
 *
 * Escribe algo de JavaScript, después haz clic derecho o elige del menú Acción:
 * 1. Ejecutar para evaluar el texto seleccionado,
 * 2. Inspeccionar para mostrar el inspector de objetos con el resultado, o,
 * 3. Mostrar para insertar el resultado en un comentario después de la selección.
 */
 function Check() {
var mal=false;
var clase=document.getElementsByClassName("number");
 for(var i = 0, length = clase.length; i < length; i++) {
       if(clase[i].value==""){
          alert("You must enter a digit");
          alert(clase[i].value);
          clase[i].focus();
          mal=true;
       }else{
    	   alert(clase[i].value);
       
       } 
    }
    if(mal==false){document.fsudoku.submit();}
}
