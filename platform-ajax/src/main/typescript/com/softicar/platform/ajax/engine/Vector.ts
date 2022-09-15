/**
 * An immutable 2-dimensional vector.
 */
class Vector2d {
	public readonly x: number;
	public readonly y: number;
	
	public constructor(x: number = 0, y: number = 0) {
		this.x = x;
		this.y = y;
	}
	
	public plus(other: Vector2d) {
		return new Vector2d(this.x + other.x, this.y + other.y);
	}

	public minus(other: Vector2d) {
		return new Vector2d(this.x - other.x, this.y - other.y);
	}
}

/**
 * An immutable 3-dimensional vector.
 */
class Vector3d {
	public readonly x: number;
	public readonly y: number;
	public readonly z: number;
	
	public constructor(x: number = 0, y: number = 0, z: number = 0) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public plus(other: Vector3d) {
		return new Vector3d(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	public minus(other: Vector3d) {
		return new Vector3d(this.x - other.x, this.y - other.y, this.z - other.z);
	}
}
