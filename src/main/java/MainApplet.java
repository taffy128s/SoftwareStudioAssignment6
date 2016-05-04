package main.java;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import controlP5.ControlP5;
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
	private boolean overNode;
	private Character lockNode, overNodeWhileNotPressed;
	@SuppressWarnings("unused")
	private Ani ani;
	private ControlP5 cp5;
	
	private final static int width = 1150, height = 650;
	
	public void setup() {
		size(width, height);
		smooth();
		loadData();
		loadNodeAndLink();
		Ani.init(this);
		cp5 = new ControlP5(this);
		cp5.addButton("button1").setLabel("ADD ALL")
								.setPosition(875, 50)
								.setSize(200, 50);
		cp5.addButton("button2").setLabel("CLEAR")
								.setPosition(875, 150)
								.setSize(200, 50);
	}
	
	public void button1() {
		
	}
	
	public void button2() {
		
	}

	public void draw() {
		// Draw the background.
		background(255);
		// Draw the big circle, the strokeWeight will be bigger when dragging in.
		fill(255);
		stroke(38, 58, 109);
		if (lockNode != null && dist(lockNode.x, lockNode.y, 550, 340) < 520 / 2) {
			strokeWeight(5);
		} else strokeWeight(1);
		ellipse(550, 340, 520, 520);
		// Draw the words.
		textSize(24);
		fill(100, 50, 25);
		text("Star Wars " + String.valueOf(version), 485, 50);
		// Display the nodes first, then test if the mouse is pointing to any of them.
		// If the mouse is not pressing and is pointing to one of them, 
		// then assign the pointer to "overNodeWhileNotPressed".
		// Else, move back the node.
		for (Character c : characters) {
			c.display();
			if (dist(c.x, c.y, mouseX, mouseY) < Character.DIAMETER / 2 && !mousePressed) {
				overNodeWhileNotPressed = c;
			} else {
				ani = Ani.to(c, (float) 0.5, "diameter", Character.DIAMETER);
			}
		}
		// "Only" check whether the mouse is pointing to a node.
		for (Character c : characters) {
			if (dist(c.x, c.y, mouseX, mouseY) < Character.DIAMETER / 2) {
				overNode = true;
				break;
			} else {
				overNode = false;
			}
		}
		// If overWhich is not null, make it bigger and draw the label.
		if (overNodeWhileNotPressed != null) {
			if (dist(overNodeWhileNotPressed.x, overNodeWhileNotPressed.y, mouseX, mouseY) < Character.DIAMETER / 2) {
				ani = Ani.to(overNodeWhileNotPressed, (float) 0.5, "diameter", 50);
				fill(0, 162, 123);
				rect(mouseX, mouseY -  15, overNodeWhileNotPressed.getName().length() * 15, 30, 15);
				fill(255);
				textSize(16);
				text(overNodeWhileNotPressed.getName(), mouseX + 10, mouseY + 5);
			}
		}
	}
	
	public void mousePressed() {
		// When the mouse is pressed, lock the character which the mouse is pointing to.
		if (overNode) {
			lockNode = overNodeWhileNotPressed;
		}
	}

	public void mouseDragged() {
		// The locked character will follow the mouse.
		if (lockNode != null) {
			lockNode.x = mouseX;
			lockNode.y = mouseY;
		}
	}
	
	public void mouseReleased() {
		if (lockNode != null) {
			if (dist(lockNode.x, lockNode.y, 550, 340) < 520 / 2) {
				lockNode.setInCircle(true);
				moveInCircle();
			} else {
				lockNode.setInCircle(false);
				ani = Ani.to(lockNode, (float) 0.5, "x", lockNode.getOriginX());
				ani = Ani.to(lockNode, (float) 0.5, "y", lockNode.getOriginY());
				moveInCircle();
			}
		}
		lockNode = null;
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
	
	private void moveInCircle() {
		int counter = 0;
		float angle = 0;
		for (Character c : characters)
			if (c.isInCircle()) counter++;
		for (Character c : characters) {
			if (c.isInCircle()) {
				c.x = 550 + 260 * cos(angle);
				c.y = 340 - 260 * sin(angle);
				angle += (TWO_PI / (float) counter);
			}
		}
	}
}
