package main.java;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.looksgood.ani.Ani;
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
	private ArrayList<Character> characters = new ArrayList<Character>();
	private int version = 1;
	private Character lockWhich, overWhich;
	@SuppressWarnings("unused")
	private Ani ani;
	
	private final static int width = 1200, height = 650;
	
	public void setup() {
		size(width, height);
		smooth();
		loadData();
		loadNodeAndLink();
		Ani.init(this);
	}

	public void draw() {
		// Draw the background.
		background(255);
		for (Character c : characters) {
			// Display the nodes first, then test if the mouse is pointing to any of them.
			// If the mouse is pointing to one of them, assign the pointer to "overWhich",
			// else move back the node.
			c.display();
			if (dist(c.x, c.y, mouseX, mouseY) < Character.DIAMETER / 2 && !mousePressed) {
				overWhich = c;
			} else {
				ani = Ani.to(c, (float) 0.5, "diameter", Character.DIAMETER);
			}
		}
		// If overWhich is not null, make it bigger and draw the label.
		if (overWhich != null) {
			if (dist(overWhich.x, overWhich.y, mouseX, mouseY) < Character.DIAMETER / 2) {
				ani = Ani.to(overWhich, (float) 0.5, "diameter", 50);
				fill(0, 162, 123);
				rect(mouseX, mouseY -  15, overWhich.getName().length() * 15, 30, 15);
				fill(255);
				textSize(16);
				text(overWhich.getName(), mouseX + 10, mouseY + 5);
			}
		}
	}
	
	public void mousePressed() {
		// When the mouse is pressed, lock the character which the mouse is pointing to.
		if (overWhich != null) {
			lockWhich = overWhich;
		}
	}

	public void mouseDragged() {
		// The locked character will follow the mouse.
		if (lockWhich != null) {
			lockWhich.x = mouseX;
			lockWhich.y = mouseY;
		}
	}
	
	public void mouseReleased() {
		// TODO: move to the big circle.
		if (lockWhich != null) {
			ani = Ani.to(lockWhich, (float) 0.5, "x", lockWhich.getOriginX());
			ani = Ani.to(lockWhich, (float) 0.5, "y", lockWhich.getOriginY());
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
		// Then load the nodes again.
		loadNodeAndLink();
	}
	
	private void loadData(){
		// Load all the data.
		for (int i = 1; i <= 7; i++) {
			datas[i] = loadJSONObject(path + fileFront + Integer.toString(i) + fileBack);
			nodes[i] = datas[i].getJSONArray("nodes");
			links[i] = datas[i].getJSONArray("links");
		}
	}
	
	private void loadNodeAndLink() {
		// Clear the list first.
		characters.clear();
		// Initialize the positions.
		int x = 0, y = 0;
		for (int i = 0; i < nodes[version].size(); i++) {
			// When the node reaches the end, it will go back to the top.
			if (i % 11 == 0) {
				x = 35 + i * 5;
				y = 48;
			}
			// Get the JSON object, add the information of the node, put it into the array.
			JSONObject obj = nodes[version].getJSONObject(i);
			Character c = new Character(this, obj.getString("name"), obj.getString("colour"), (float) x, (float) y);
			characters.add(c);
			// The gap between the nodes.
			y += 55;
		}
	}
}
