/*********************************************** * DD Tab Menu script- � Dynamic Drive DHTML code library (www.dynamicdrive.com) * This notice MUST stay intact for legal use * Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code ***********************************************/ 
//Set tab to intially be selected when page loads: 
//[which tab (1=first tab), ID of tab content to display]:
var menuTimeout = 2000;
var initialtab = [2, "sc1"]
//Turn menu into single level image tabs (completely hides 2nd level)?
var turntosingle = 0 //0 for no (default), 1 for yes 
//Disable hyperlinks in 1st level tab images?
var disabletablinks = 0 //0 for no (default), 1 for yes 

////////Stop editting////////////////  

var previoustab = ""

 if (turntosingle == 1) 
 document.write('') 
 
 function expandcontent(cid, aobject) { 
 if (disabletablinks == 1) 
 aobject.onclick = new Function("return false") 
 if (document.getElementById) { 
 highlighttab(aobject) 
 if (turntosingle == 0) { 
 if (previoustab != "") 
 document.getElementById(previoustab).style.display = "none" 
 document.getElementById(cid).style.display = "block" 
 previoustab = cid 
 } 
 } 
 } 
 
 function highlighttab(aobject) { 
 if (typeof tabobjlinks == "undefined") collecttablinks() 
 for (i = 0; i < tabobjlinks.length; i++) tabobjlinks[i].className = "" 
 aobject.className = "current" } 
 function collecttablinks() { var tabobj = document.getElementById("tablist") 
 tabobjlinks = tabobj.getElementsByTagName("A") } 

function do_onload() { 
collecttablinks() 
expandcontent(initialtab[1], tabobjlinks[initialtab[0] - 1]) } var thread1; 
function menuOnOut() { thread1 = setTimeout('do_onload()', menuTimeout); } 
function menuOnIn(a,b) { 
clearTimeout(thread1); } 

if (window.addEventListener) 
window.addEventListener("load", do_onload, false) 
else if (window.attachEvent) 
window.attachEvent("onload", do_onload) 
else if (document.getElementById) 
window.onload = do_onload 