/**
 * This class encodes the {@link AjaxRequestMessage} data.
 */
class AjaxRequestMessageEncoder {
	private static HEX_DIGITS = "0123456789ABCDEF";
	private data: Map<string, string>;
	
	public constructor(data: Map<string, string>) {
		this.data = data;
	}

	/**
	 * Encodes the {@link AjaxRequestMessage} data into a string.
	 */
	public encode(): string {
		let data = [];

		for(let [key, value] of this.data) {
			data.push(key.length+"\n"+key);
			data.push(value.length+"\n"+value);
		}

		return data.join("");
	}

	/**
	 * Encodes the {@link AjaxRequestMessage} data into a hexadecimal string.
	 */
	public encodeToHex(): string {
		return this.encodeTextCharsToHex(this.encode());
	}

	private encodeTextCharsToHex(text: string): string {
		let hex: string[] = [];
		for(let i = 0; i < text.length; i++) {
			let codePoint = text.charCodeAt(i);
			let utf8 = this.encodeCodePointToUtf8(codePoint);
			hex.push(this.hexBytes(utf8));
		}
		return hex.join("");
	}

	private encodeCodePointToUtf8(codePoint: number) {
		let bytes: number[] = [];
		if(codePoint <= 0x7F) {
			bytes.push(codePoint);
		} else if(codePoint <= 0x7FF) {
			bytes.push(128 + 64 + (codePoint >> 6));
			bytes.push(128 + (codePoint & 63));
		} else if(codePoint <= 0xFFFF) {
			bytes.push(128 + 64 + 32 + (codePoint >> 12));
			bytes.push(128 + ((codePoint >> 6) & 63));
			bytes.push(128 + (codePoint & 63));
		} else {
			bytes.push(128 + 64 + 32 + 16 + (codePoint >> 18));
			bytes.push(128 + ((codePoint >> 12) & 63));
			bytes.push(128 + ((codePoint >> 6) & 63));
			bytes.push(128 + (codePoint & 63));
		}
		return bytes;
	}

	private hexBytes(bytes: number[]): string {
		let hex: string[] = [];
		for(let i in bytes) {
			hex.push(this.hexByte(bytes[i]));
		}
		return hex.join("");
	}

	private hexByte(byte: number): string {
		return this.hexNibble(byte >> 4) + this.hexNibble(byte & 15);
	}

	private hexNibble(nibble: number): string {
		return AjaxRequestMessageEncoder.HEX_DIGITS.charAt(nibble);
	}
}
