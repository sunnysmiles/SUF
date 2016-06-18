package engine.utils;

import java.io.Serializable;

public class Vector implements Serializable {

	private static final long serialVersionUID = 1L;
	private float x, y;

	public void setX(float x) {
		this.x = x;
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other instanceof Vector) {
			Vector otherVector = (Vector) other;
			if (otherVector.getX() == this.getX()
					&& otherVector.getY() == this.getY())
				return true;
			else
				return false;
		} else
			return false;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public int getIntX() {
		return (int) x;
	}

	public int getIntY() {
		return (int) y;
	}

	public static Vector substract(Vector f, Vector s) {
		return new Vector(f.x - s.x, f.y - s.y);
	}

	public void substractThis(Vector f) {
		this.x -= f.x;
		this.y -= f.y;
	}

	public float getLength() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public void normalize() {
		float length = getLength();
		if (length != 0) {
			x = x / length;
			y = y / length;
		} else {
			x = 0;
			y = 0;
		}
	}

	public Vector getNormalized() {
		Vector result = copy();
		result.normalize();
		return result;
	}

	public Vector copy() {
		return new Vector(x, y);
	}

	public void addThis(Vector v) {
		x += v.x;
		y += v.y;
	}

	public void multi(float i) {
		x *= i;
		y *= i;
	}

	public static Vector toIso(Vector p) {
		return new Vector(p.x - p.y, (p.x + p.y) / 2);
	}

	public static Vector toCartex(Vector p) {
		return new Vector((2 * p.y + p.x) / 2, (2 * p.y - p.x) / 2);
	}

	public void toIso() {
		float nx = x - y;
		float ny = (y + x) / 2;
		this.x = nx;
		this.y = ny;
	}

	public void toCartex() {
		float nx = (2 * y + x) / 2;
		float ny = (2 * y - x) / 2;
		this.x = nx;
		this.y = ny;
	}

	public Object clone() {
		return new Vector(x, y);
	}

	public static float nDistance(Vector v1, Vector v2) {
		float l = substract(v1, v2).getLength();
		if (l < 0)
			l = -l;
		return l;
	}

	public void reverse() {
		this.x = -this.x;
		this.y = -this.y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean equalsTo(Vector position) {
		return (position.getX() == x && position.getY() == y);
	}
}
