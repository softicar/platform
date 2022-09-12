/**
 * An immutable 2-dimensional point.
 */
class Point {
	public readonly x: number;
	public readonly y: number;
	
	public constructor(x: number = 0, y: number = 0) {
		this.x = x;
		this.y = y;
	}
	
	public plus(point: Point) {
		return new Point(this.x + point.x, this.y + point.y);
	}

	public minus(point: Point) {
		return new Point(this.x - point.x, this.y - point.y);
	}
}
