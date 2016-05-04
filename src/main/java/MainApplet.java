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
	// JSON objects and arrays for 7 episodes.
	private JSONObject[] datas = new JSONObject[8];
	private JSONArray[] nodes = new JSONArray[8];
	private JSONArray[] links = new JSONArray[8];
	// ArrayList for small balls in current episode.
	private ArrayList<Character> characters = new ArrayList<Character>();
	// Show the version number.
	private int version = 1;
	// True when the mouse is pointing to a ball.
	private boolean overNode;
	// lockNode gives the pointer of the chosen character.
	// overNodeWhileNotPressed gives the pointer of the pointed character(not pressing).
	private Character lockNode, overNodeWhileNotPressed;
	@SuppressWarnings("unused")
	private Ani ani;
	private ControlP5 cp5;
	
	private final static int width = 1150, height = 650;
	
	public void setup() {
		size(width, height);
		smooth();
		// Load the resources.
		loadData();
		loadNodeAndLink();
		Ani.init(this);
		cp5 = new ControlP5(this);
		// Create two buttons.
		cp5.addButton("button1").setLabel("ADD ALL")
								.setPosition(875, 50)
								.setSize(200, 50);
		cp5.addButton("button2").setLabel("CLEAR")
								.setPosition(875, 150)
								.setSize(200, 50);
	}
	
	public void button1() {
		// Add all the characters to the big circle.
		for (Character c : characters) {
			c.setInCircle(true);
		}
		// Move the characters into the big circles.
		moveInCircle();
	}
	
	public void button2() {
		// Remove all the characters out.
		for (Character c : characters) {
			c.setInCircle(false);
			// Move all the characters back.
			ani = Ani.to(c, (float) 1, "x", c.getOriginX());
			ani = Ani.to(c, (float) 1, "y", c.getOriginY());
		}
	}

	public void draw() {
		// Draw the background.
		background(255);
		// Draw the big circle, the strokeWeight will be bigger when dragging in.
		fill(255);
		stroke(38, 58, 109);
		if (lockNode != null && dist(lockNode.x, lockNode.y, 550, 340) < 520 / 2) {
			strokeWeight(10);
		} else strokeWeight(5);
		ellipse(550, 340, 520, 520);
		// Draw the words.
		textSize(24);
		fill(100, 50, 25);
		text("Star Wars " + String.valueOf(version), 485, 50);
		// Display the nodes first, then test if the mouse is pointing to any of them.
		// If the mouse is not pressing and is pointing to one of them, 
		// then assign the pointer to "overNodeWhileNotPressed".
		// Else, resize the node.
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
		// If overNodeWhileNotPressed is not null, make it bigger and draw the label.
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
		// If the lockNode is not null and released in the big circle, set inCircle true.
		// And then move the characters to the proper positions in the big circle.
		// Else, set inCircle false, move the lockNode back, 
		// move the rest of the nodes to the proper positions in the big circle.
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
		// Reset the lockNode.
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
		// Get the links and link weights between the nodes.
		for (int i = 0; i < links[version].size(); i++) {
			JSONObject obj = links[version].getJSONObject(i);
			int source = obj.getInt("source");
			int target = obj.getInt("target");
			int weight = obj.getInt("value");
			characters.get(source).addTarget(characters.get(target), weight);
		}
	}
	// Put the character into the right places.
	private void moveInCircle() {
		int counter = 0;
		float angle = 0;
		// Check the number of the characters in the big circle.
		for (Character c : characters)
			if (c.isInCircle()) counter++;
		// Use trigonometric functions to determine the positions.
		for (Character c : characters) {
			if (c.isInCircle()) {
				c.x = 550 + 260 * cos(angle);
				c.y = 340 - 260 * sin(angle);
				angle += (TWO_PI / (float) counter);
			}
		}
	}
}
