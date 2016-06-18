package engine.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen extends Bitmap {
	public BufferedImage image;

	public Screen(int w, int h) {
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		this.w = w;
		this.h = h;
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}
}
