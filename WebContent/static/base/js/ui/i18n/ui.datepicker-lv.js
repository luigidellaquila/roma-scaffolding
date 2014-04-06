/**
 * Latvian (UTF-8) initialisation for the jQuery UI date picker plugin.
 * @author Arturas Paleicikas <arturas.paleicikas@metasite.net>
 */
jQuery(function($){
	$.datepicker.regional['lv'] = {
		clearText: 'Notīrīt', clearStatus: '',
		closeText: 'Aizvērt', closeStatus: '',
		prevText: 'Iepr',  prevStatus: '',
		nextText: 'N�?ka', nextStatus: '',
		currentText: 'Šodien', currentStatus: '',
		monthNames: ['Janv�?ris','Febru�?ris','Marts','Aprīlis','Maijs','Jūnijs',
		'Jūlijs','Augusts','Septembris','Oktobris','Novembris','Decembris'],
		monthNamesShort: ['Jan','Feb','Mar','Apr','Mai','Jūn',
		'Jūl','Aug','Sep','Okt','Nov','Dec'],
		monthStatus: '', yearStatus: '',
		weekHeader: 'Nav', weekStatus: '',
		dayNames: ['svētdiena','pirmdiena','otrdiena','trešdiena','ceturtdiena','piektdiena','sestdiena'],
		dayNamesShort: ['svt','prm','otr','tre','ctr','pkt','sst'],
		dayNamesMin: ['Sv','Pr','Ot','Tr','Ct','Pk','Ss'],
		dayStatus: 'DD', dateStatus: 'D, M d',
		dateFormat: 'dd-mm-yy', firstDay: 1, 
		initStatus: '', isRTL: false};
	$.datepicker.setDefaults($.datepicker.regional['lv']);
});