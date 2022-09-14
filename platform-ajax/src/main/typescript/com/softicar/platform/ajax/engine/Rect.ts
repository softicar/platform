/**
 * An immutable 2-dimensional rectangle.
 */
class Rect {
	public readonly x: number;
	public readonly y: number;
	public readonly width: number;
	public readonly height: number;
	
	public constructor(x: number = 0, y: number = 0, width: number = 0, height: number = 0) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public static fromDomRect(rect: DOMRect) {
		return new Rect(rect.x, rect.y, rect.width, rect.height);
	}	
}
