$(function() {
	let input = document.querySelector('#inputBox');
	let button = document.querySelector("#inputBotton");
	button.disabled = true;
	input.addEventListener("change", stateHandle);
	function stateHandle() {
		if (document.querySelector(".input").value === "") {
			button.disabled = true;
		} else {
			button.disabled = false;
		}
		return true;
	}
});