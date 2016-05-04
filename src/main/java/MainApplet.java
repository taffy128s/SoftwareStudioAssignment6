package main.java;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
	private int version = 1;
	private ArrayList<Character> characters = new ArrayList<Character>();
	
	private final static int width = 1200, height = 650;
	
	public void setup() {

		size(width, height);
		smooth();
		loadData();
		loadNodeAndLink();
		
	}

	public void draw() {
		// Draw the background and show the nodes.
		background(255);
		for (Character c : characters) {
			c.display();
		}
		for (Character c : characters) {
			c.showLabelAndBigger();
		}
	}

	public void keyPressed() {
		// Change the versions.
		if (keyCode == KeyEvent.VK_1) version = 1;
		else if (keyCode == KeyEvent.VK_2) version = 2;
		else if (keyCode == KeyEvent.VK_3) version = 3;
		else if (keyCode == KeyEvent.VK_4) version = 4;
		else if (keyCode == KeyEvent.VK_5) version = 5;
		else if (keyCode == KeyEvent.VK_6) version = 6;
		else if (keyCode == KeyEvent.VK_7) version = 7;
		loadNodeAndLink();
	}
	
	private void loadData(){
		// Load in all data.
		for (int i = 1; i <= 7; i++) {
			datas[i] = loadJSONObject(path + fileFront + Integer.toString(i) + fileBack);
			nodes[i] = datas[i].getJSONArray("nodes");
			links[i] = datas[i].getJSONArray("links");
		}
	}
	
	private void loadNodeAndLink() {
		characters.clear();
		int x = 0, y = 0;
		for (int i = 0; i < nodes[version].size(); i++) {
			// When the node reaches the end, it will go back to the top.
			if (i % 11 == 0) {
				x = 35 + i * 5;
				y = 48;
			}
			JSONObject obj = nodes[version].getJSONObject(i);
			Character c = new Character(this, obj.getString("name"), obj.getString("colour"), x, y, false, false, false);
			characters.add(c);
			// The gap between the nodes.
			y += 55;
		}
	}

}
