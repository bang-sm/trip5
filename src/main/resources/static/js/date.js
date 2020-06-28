$('<div class="ui-datepicker-icon" onclick="DateD(this,1)">date_range</div><div class="ui-datepicker-holder"><div class="ui-datepicker-result"><div class="ui-datepicker-result-year"></div><div class="ui-datepicker-result-date"></div></div><button onclick="DateD(this,0)">OK</button><button onclick="DateD(this,2)">CANCEL</button></div>').insertAfter('.ui-datepicker-input');
$('.ui-datepicker-input').datepicker({
	dayNamesMin: ["S", "M", "T", "W", "T", "F", "S"],
	prevText: "keyboard_arrow_left",
	nextText: "keyboard_arrow_right",
	onSelect: function(dateText) {
		dateF(new Date(dateText))
	}
});
$('.ui-datepicker-input').focus().off();

var da = document.getElementById("ui-datepicker-div");
da.style = "";
$(da).insertBefore('.ui-datepicker-holder > button:first-of-type');

function dateF(d) {
	var w = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
	var m = ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"];
	document.getElementsByClassName('ui-datepicker-result-date')[0].innerHTML = w[d.getDay()] + ', ' + m[d.getMonth()] + ' ' + d.getDate();
	document.getElementsByClassName('ui-datepicker-result-year')[0].innerHTML = d.getFullYear()
}

dateF(new Date())

function DateD(t, a) {
	if (a === 0) {
		t.parentNode.classList.toggle('ui-datepicker-holder-open');
		document.getElementsByClassName('ui-datepicker-input')[0].value = (document.getElementsByClassName('ui-datepicker-result-date')[0].innerHTML + ' ' + document.getElementsByClassName('ui-datepicker-result-year')[0].innerHTML)
	} else if (a === 1) {
		t.nextSibling.classList.toggle('ui-datepicker-holder-open')
	} else if (a === 2) {
		t.parentNode.classList.toggle('ui-datepicker-holder-open');
		document.getElementsByClassName('ui-datepicker-input')[0].value = ""
	}
}