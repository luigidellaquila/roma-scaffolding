/*
 *
 * SearchFieldUtilities: a collection of scripts to manage (client-side) text fields.
 * Author: Luigi Quarta.
 *
 */





/* ****** SCRIPT VARS - DO NOT MODIFY!!! ****** */
var originalBorderColor = null;
var originalTextColor = null;


/* ****** CUSTOMIZATION VARS - MODIFY TO CHANGE THE APPEARANCE  ****** */
var editedTextColor = '#000';
var borderColorOnEdit = '#7BB1DD';





			
/*
 *
 * Checks if the text field is changed and writes this on an attribute.
 *
 */
function checkForChanges(field){
	if(trim(field.value)!="")
		field.setAttribute('isdefaultvalue','false');
}

		
/*
 *
 * Clears the text field if it contains the default value, and changes the field's style.
 *
 */
function clearField(field){
	//Saves the original border color. In this way the color isn't hardcoded in the js.
	if(originalBorderColor == null)
		originalBorderColor = field.parentNode.style.borderColor;
		
	if(originalTextColor == null)
		originalTextColor = field.style.color;
		
	field.style.color=editedTextColor;
	field.parentNode.style.borderColor=borderColorOnEdit;
				
	if(field.getAttribute('isdefaultvalue')=='true'){
		field.value="";
	}
}

	
/*
 *
 * Set a text field to the default value when someone ends up writing in it. If the text field is empty, its values are set to the originals.
 *
 */
function restoreDefaultValues(field){
	field.style.border='none';
	field.parentNode.style.borderColor=originalBorderColor;
				
	if(trim(field.value)=="")
		restoreOriginalValues(field);
}

	
/*
 *
 * Restores a text field to it's original values.
 *
 */
function restoreOriginalValues(field){
	field.style.color=originalTextColor;
	field.setAttribute('isdefaultvalue','true');
	field.value = field.getAttribute('defaultValue');
}

			
/*
 *
 * Deletes the blank spaces on the edge of a string
 *
 */
function trim(string){
	return string.replace(/^\s+|\s+$/g,"");
}