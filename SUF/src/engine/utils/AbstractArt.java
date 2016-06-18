package engine.utils;

import java.io.IOException;


public abstract class AbstractArt {
	
	public abstract void load() throws IOException;
	
	public static Bitmap[] getAnimClean(Bitmap bmp, int x, int y, int w,
			int size) {
		Bitmap[] res = getAnim(bmp,x,y,w,size);
		for(int i = 0; i < res.length; i++){
			res[i].cropToData();
		}
		return res;
	}

	public static void drawSquare(Bitmap screen, int x, int y, int w, int h,
			int col) {
		int alpha = (col >> 24) & 0xff;
		if (w < 0) {
			x += w;
			w = -w;
		}
		if (h < 0) {
			y += h;
			h = -h;
		}
		int xMin = Math.max(0, x);
		int yMin = Math.max(0, y);
		int xMax = Math.min(screen.w, x + w);
		int yMax = Math.min(screen.h, y + h);
		for (int xi = xMin; xi < xMax; xi++) {
			for (int yi = yMin; yi < yMax; yi++) {
				int index = xi + yi * screen.w;
				if(index < 0) continue;
				if (alpha == 255)
					screen.pixels[index] = col;
				else
					screen.pixels[index] = Bitmap.blendPixels(
							screen.pixels[index], col);
			}
		}
	}

	public static void drawSquare(Bitmap screen, Rectangle rect, int col) {
		drawSquare(screen, Math.round(rect.x), Math.round(rect.y),
				Math.round(rect.w), Math.round(rect.h), col);
	}
	
	public static void drawRect(Bitmap screen, Rectangle rect, int col){
		int index = 0;
		int xMin = (int) Math.max(0, rect.x);
		int yMin = (int) Math.max(0, rect.y);
		int xMax = (int) Math.min(screen.w, rect.x + rect.w);
		int yMax = (int) Math.min(screen.h, rect.y + rect.h);
		for(int xi = xMin; xi < xMax; xi++){
			screen.pixels[yMin*screen.w+xi] = col;
		}
		for(int xi = xMin; xi < xMax; xi++){
			index = yMax*screen.w+xi;
			if(index < screen.pixels.length)
			screen.pixels[yMax*screen.w+xi] = col;
		}
		for(int yi = yMin; yi < yMax; yi++){
			screen.pixels[yi*screen.w+xMin] = col;
			if(yi*screen.w+xMax < screen.pixels.length)
				screen.pixels[yi*screen.w+xMax] = col;
		}
	}

	public static Bitmap getCrop(Bitmap bmp, int x, int y, int endX, int endY) {
		int w = endX - x;
		int h = endY - y;
		int[] pixels = new int[w * h];
		for (int xi = 0; xi < w; xi++)
			for (int yi = 0; yi < h; yi++) {
				pixels[xi + yi * w] = bmp.pixels[(x + xi) + (y + yi) * bmp.w];
			}
		return new Bitmap(w, h, pixels);
	}

	public static void changeColor(Bitmap bmp, int col, int toCol) {
		for (int i = 0; i < bmp.pixels.length; i++) {
			if (bmp.pixels[i] == col)
				bmp.pixels[i] = toCol;
		}
	}

	public static void setTrans(Bitmap bmp, int col) {
		changeColor(bmp, col, 0x00ffffff);
	}

	public static Bitmap[] getAnim(Bitmap bmp, int x, int y, int w, int size) {
		Bitmap[] result = new Bitmap[w];
		for (int i = 0; i < w; i++) {
			Bitmap b = getCrop(bmp, (i + x) * size, y * size, (i + x + 1) * size,
					(y + 1) * size);
			result[i] = b;
		}
		return result;
	}

}
