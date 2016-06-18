package engine.utils;

public abstract class AnimationFrame {
	protected Bitmap bmp;
	public AnimationFrame(Bitmap bmp){
		this.bmp = bmp;
	}
	public abstract void render(Screen screen, int x, int y, boolean flipped);
}
