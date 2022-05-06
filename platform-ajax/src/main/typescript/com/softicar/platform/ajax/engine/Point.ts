/**
 * An immutable 2-dimensional point.
 */
class Point {
	private _x: number;
	private _y: number;
	
	public constructor(x: number = 0, y: number = 0) {
		this._x = x;
		this._y = y;
	}
	
	public get x() {
		return this._x;
	}

	public get y() {
		return this._y;
	}
	
	public plus(point: Point) {
		return new Point(this.x + point.x, this.y + point.y);
	}

	public minus(point: Point) {
		return new Point(this.x - point.x, this.y - point.y);
	}
}
