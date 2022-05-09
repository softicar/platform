/**
 * Inserts the specified text at the caret position of the given input element.
 */
function insertTextAtCaret(input: HTMLInputElement | HTMLTextAreaElement, text: string) {
	let selectionStart = input.selectionStart;
	if(selectionStart !== null) {
		let value = input.value;
		let front = value.substring(0, selectionStart);
		let back = value.substring(selectionStart, value.length);
		let scrollTop = input.scrollTop;
		input.value = front + text + back;
		input.selectionStart = selectionStart;
		input.selectionEnd = selectionStart;
		input.scrollTop = scrollTop;
		input.focus();
	}
}

/**
 * Moves the caret of the given input element to the specified position.
 */
function moveCaretToPosition(input: HTMLInputElement | HTMLTextAreaElement, position: number) {
	input.selectionStart = input.selectionEnd = position;
}
