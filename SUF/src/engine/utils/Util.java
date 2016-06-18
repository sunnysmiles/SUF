package engine.utils;

import java.util.Random;

public class Util {
	public static int getRandom(int l, int h){
		Random r = new Random();
		if(h < l) return -1;
		if(h == l) return l;
		return r.nextInt(h-l) + l;
	}
}
