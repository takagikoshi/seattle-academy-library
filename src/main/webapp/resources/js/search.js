$(function() {
	let input = document.querySelector('#input1');
	let button = document.querySelector("#input2");
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