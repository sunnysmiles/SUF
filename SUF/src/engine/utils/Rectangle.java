package engine.utils;

import java.io.Serializable;

public class Rectangle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public float x, y, w, h;

	public Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Rectangle() {
		x = y = w = h = 0;
	}

	public void normalize() {
		if (w < 0) {
			x += w;
			w = -w;
		}
		if (h < 0) {
			y += h;
			h = -h;
		}
	}

	public static boolean isCollision(Rectangle f, Rectangle s) {
		if (f.x < s.x + s.w && s.x < f.x + f.w)
			if (f.y < s.y + s.h && s.y < f.y + f.h)
				return true;
		return false;
	}

	public static boolean contains(Rectangle r, Vector p) {
		if (p.getX() > r.x && p.getX() < r.x + r.w && p.getY() > r.y && p.getY() < r.y + r.h)
			return true;
		else
			return false;
	}
}
