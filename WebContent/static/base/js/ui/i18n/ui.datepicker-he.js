﻿/* Hebrew initialisation for the UI Datepicker extension. */
/* Written by Amir Hardon (ahardon at gmail dot com). */
jQuery(document).ready(function(){
	jQuery.datepicker.regional['he'] = {clearText: 'נקה', clearStatus: '',
		closeText: 'סגור', closeStatus: '',
		prevText: '&#x3c;הקוד�?', prevStatus: '',
		nextText: 'הב�?&#x3e;', nextStatus: '',
		currentText: 'היו�?', currentStatus: '',
		monthNames: ['ינו�?ר','פברו�?ר','מרץ','�?פריל','מ�?י','יוני',
		'יולי','�?וגוסט','ספטמבר','�?וקטובר','נובמבר','דצמבר'],
		monthNamesShort: ['1','2','3','4','5','6',
		'7','8','9','10','11','12'],
		monthStatus: '', yearStatus: '',
		weekHeader: 'Sm', weekStatus: '',
		dayNames: ['ר�?שון','שני','שלישי','רביעי','חמישי','שישי','שבת'],
		dayNamesShort: ['�?\'','ב\'','ג\'','ד\'','ה\'','ו\'','שבת'],
		dayNamesMin: ['�?\'','ב\'','ג\'','ד\'','ה\'','ו\'','שבת'],
		dayStatus: 'DD', dateStatus: 'DD, M d',
		dateFormat: 'dd/mm/yy', firstDay: 0, 
		initStatus: '', isRTL: true};
	jQuery.datepicker.setDefaults($.datepicker.regional['he']);
});
