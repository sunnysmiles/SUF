package game.shared;

import java.io.Serializable;

public class Stats implements Serializable{
	public int impact = 0;
	public int impactRød = 0;
	public int impactGrøn = 0;
	public int impactLilla = 0;
	public int impactSort = 0;
	
	public float gejst = 20; //0-100
	public float relation = 0; //-100-100
	public float kendthed = 20; //0-100
	public float ry = 0; //-100-100;
	public float profil; //A100-B100
	
	public int bundlinje = 2000000;
	public int sidsteDUF = 2000000;
}
