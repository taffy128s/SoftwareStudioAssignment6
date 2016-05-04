package main.java;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	private String fileFront = "starwars-episode-";
	private String fileBack = "-interactions.json";
	private JSONObject[] datas = new JSONObject[8];
	private JSONArray[] nodes = new JSONArray[8];
	private JSONArray[] links = new JSONArray[8];
	
	private final static int width = 1200, height = 650;
	
	public void setup() {

		size(width, height);
		smooth();
		loadData();
		
	}

	public void draw() {
	}

	private void loadData(){
		for (int i = 1; i <= 7; i++) {
			datas[i] = loadJSONObject(path + fileFront + Integer.toString(i) + fileBack);
			nodes[i] = datas[i].getJSONArray("nodes");
			links[i] = datas[i].getJSONArray("links");
		}
	}

}
