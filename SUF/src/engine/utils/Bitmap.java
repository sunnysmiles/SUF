package engine.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bitmap {
	protected int w;
	protected int h;
	public int[] pixels;

	public Bitmap() {
	}

	public Bitmap(String path) throws IOException {
		BufferedImage image = ImageIO.read(Bitmap.class
				.getResourceAsStream(path));
		if (image == null) {
			System.out.println("Couldn't read file");
			return;
		}
		w = image.getWidth();
		h = image.getHeight();
		pixels = image.getRGB(0, 0, w, h, null, 0, w);
	}

	public Bitmap(int w, int h, int[] pixels) {
		this.w = w;
		this.h = h;
		this.pixels = pixels;
	}

	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		pixels = new int[w * h];
	}
	
	public int[] getPixels(){
		return pixels;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

	public void blit(Bitmap bmp, int x, int y) {
		int endX = (w > bmp.w - x) ? bmp.w - x : w;
		int endY = (h > bmp.h - y) ? bmp.h - y : h;
		for (int yi = 0; yi < endY; yi++) {
			for (int xi = 0; xi < endX; xi++) {
				int index = (xi + x) + (yi + y) * bmp.w;
				if (index < 0 || x + xi < 0)
					continue;
				int col = pixels[xi + yi * w];

				int alpha = (col >> 24) & 0xff;
				if (alpha == 255) {
					bmp.pixels[index] = col;
				} else {
					bmp.pixels[index] = blendPixels(bmp.pixels[index], col);
				}
			}
		}
	}

	public void blitShade(Bitmap bmp, int x, int y, int col) {
		int endX = (w > bmp.w - x) ? bmp.w - x : w;
		int endY = (h > bmp.h - y) ? bmp.h - y : h;
		for (int yi = 0; yi < endY; yi++)
			for (int xi = 0; xi < endX; xi++) {
				int index = (xi + x) + (yi + y) * bmp.w;
				if (index < 0 || x + xi < 0)
					continue;
				int colo = pixels[xi + yi * w];
				int alpha = (colo >> 24) & 0xff;
				if (alpha > 0)
					bmp.pixels[index] = blendPixels(colo, col);
			}
	}

	public void blitShade(Bitmap bmp, int x, int y, boolean xf, boolean yf,
			int col) {
		int endX = (w > bmp.w - x) ? bmp.w - x : w;
		int endY = (h > bmp.h - y) ? bmp.h - y : h;
		for (int yi = 0; yi < endY; yi++) {
			int ySheet = yi;
			if (yf)
				ySheet = endY - yi - 1;
			for (int xi = 0; xi < endX; xi++) {
				int xSheet = xi;
				if (xf)
					xSheet = endX - xi - 1;
				int index = (xi + x) + (yi + y) * bmp.w;
				if (index < 0 || x + xi < 0)
					continue;
				int colo = pixels[xSheet + ySheet * w];
				int alpha = (colo >> 24) & 0xff;
				if (alpha > 0)
					bmp.pixels[index] = blendPixels(colo, col);
			}
		}
	}

	public void blitf(Bitmap bmp, float x, float y, boolean xf, boolean yf) {
		blit(bmp, Math.round(x), Math.round(y), xf, yf);
	}

	public void blit(Bitmap bmp, int x, int y, boolean xf, boolean yf) {
		int endX = (w > bmp.w - x) ? bmp.w - x : w;
		int endY = (h > bmp.h - y) ? bmp.h - y : h;
		for (int yi = 0; yi < endY; yi++) {
			int ySheet = yi;
			if (yf)
				ySheet = endY - yi - 1;
			for (int xi = 0; xi < endX; xi++) {
				int xSheet = xi;
				if (xf)
					xSheet = endX - xi - 1;
				int index = (xi + x) + (yi + y) * bmp.w;
				if (index < 0 || x + xi < 0)
					continue;
				int col = pixels[xSheet + ySheet * w];

				int alpha = (col >> 24) & 0xff;
				if (alpha == 255) {
					bmp.pixels[index] = col;
				} else {
					bmp.pixels[index] = blendPixels(bmp.pixels[index], col);
				}
			}
		}
	}

	public void blit(Bitmap bmp, int sX, int sY, int seX, int seY, int dX,
			int dY) {
		int w = seX - sX;
		int h = seY - sY;
		int endX = (w > bmp.w - dX) ? bmp.w - dX : w;
		int endY = (h > bmp.h - dY) ? bmp.h - dY : h;
		for (int xi = 0; xi < endX; xi++)
			for (int yi = 0; yi < endY; yi++) {
				int col = pixels[(sX + xi) + (sY + yi) * this.w];
				int index = (dX + xi) + (dY + yi) * bmp.w;
				int alpha = (col >> 24) & 0xff;
				if (alpha == 255)
					bmp.pixels[index] = col;
				else
					bmp.pixels[index] = blendPixels(bmp.pixels[index], col);
			}
	}

	public static int blendPixels(int bg, int toBlend) {
		int alpha_blend = (toBlend >> 24) & 0xff;
		int alpha_bg = 256 - alpha_blend;

		int rr = bg & 0xff0000;
		int gg = bg & 0xff00;
		int bb = bg & 0xff;

		int r = toBlend & 0xff0000;
		int g = toBlend & 0xff00;
		int b = toBlend & 0xff;

		r = ((rr * alpha_bg + r * alpha_blend) >> 8) & 0xff0000;
		g = ((gg * alpha_bg + g * alpha_blend) >> 8) & 0xff00;
		b = ((bb * alpha_bg + b * alpha_blend) >> 8) & 0xff;

		return 0xff000000 | r | g | b;
	}

	public Object clone() {
		return new Bitmap(w, h, pixels);
	}

	public void cropToData() {
		int fX, fY, lX, lY;
		fX = w;
		fY = h;
		lX = lY = 0;
		for (int xi = 0; xi < w; xi++)
			for (int yi = 0; yi < h; yi++) {
				int alpha = (pixels[xi + yi * w] >> 24) & 0xff;
				if (alpha != 0) {
					if (xi < fX)
						fX = xi;
					if (yi < fY)
						fY = yi;
					if (xi > lX)
						lX = xi;
					if (yi > lY)
						lY = yi;
				}
			}
		int nw = (lX + 1) - fX;
		int nh = (lY + 1) - fY;
		int[] nPixels = new int[nw * nh];
		for (int xi = 0; xi < nw; xi++)
			for (int yi = 0; yi < nh; yi++) {
				nPixels[xi + yi * nw] = pixels[(xi + fX) + (yi + fY) * w];
			}
		w = nw;
		h = nh;
		pixels = nPixels;
	}

	public void blitf(Screen screen, float f, float g) {
		blit(screen, Math.round(f), Math.round(g));
	}

}
