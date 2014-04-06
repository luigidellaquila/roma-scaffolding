/*
 *
 * Validator: dato un input field e una regex controlla che il field sia valido (lato client) per quella regex.
 *
 * @author Luigi Quarta 
 *
 */

function validate(field, regex){
	var clazz = " csvalid";
	var originalCass = field.getAttribute("class");
	
	/* Manage the null class case */
	if(originalCass == null)
		originalCass = "";
	
	/* Delete previous validation result */
	originalCass = originalCass.replace(" csinvalid","");
	originalCass = originalCass.replace(" csvalid","");
	
	/* Validate */
	if(regex || regex!=""){
		if(!(regex.test(field.value)))
		 clazz = " csinvalid";
	}
	
	/* Apply the "validation style" */
	field.setAttribute("class", originalCass + clazz);
}