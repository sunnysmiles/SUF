package game.client;

import java.io.IOException;

import engine.utils.AbstractArt;
import engine.utils.Bitmap;

public class Art extends AbstractArt {

	public Bitmap danmark;
	public void load() throws IOException {
		danmark = new Bitmap("/danmark2.png");
	}

}
