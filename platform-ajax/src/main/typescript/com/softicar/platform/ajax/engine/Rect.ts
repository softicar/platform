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
	
	public right() {
		return this.x + this.width;
	}

	public bottom() {
		return this.y + this.height;
	}

	public static fromDomRect(rect: DOMRect) {
		return new Rect(rect.x, rect.y, rect.width, rect.height);
	}
	
	public clamp(point: Vector2d) {
		return new Vector2d(
			clamp(point.x, this.x, this.right() - 1),
			clamp(point.y, this.y, this.bottom() - 1));
	}
	
	public getMergedWith(other: Rect) {
		let x = Math.min(this.x, other.x);
		let y = Math.min(this.y, other.y);
		let right = Math.max(this.right(), other.right());
		let bottom = Math.max(this.bottom(), other.bottom());
		return new Rect(x, y, right - x, bottom - y);
	}
	
	public contains(point: Vector2d) {
		return point.x >= this.x && point.x < this.right() &&
			point.y >= this.y && point.y < this.bottom();
	}
}
