package engine.utils;

public class Animation {
	private AnimationFrame[] frames;
	protected int tickCount;
	protected int frameCount;
	private int speed;
	
	public Animation(AnimationFrame[] frames, int speed){
		this.frames = frames;
		this.speed = speed;
		frameCount = 0;
		tickCount = 0;
	}
	
	public void reset(){
		frameCount = 0;
		tickCount = 0;
	}
	
	public void update(){
		tickCount++;
		if(tickCount >= speed){
			frameCount++;
			if(frameCount >= frames.length){
				frameCount = 0;
			}
			tickCount = 0;
		}
	}
	
	public AnimationFrame[] getAnimationFrames(){
		return frames;
	}
	
	public AnimationFrame getCurrentFrame(){
		return frames[frameCount];
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public int getSpeed(){
		return speed;
	}
}
