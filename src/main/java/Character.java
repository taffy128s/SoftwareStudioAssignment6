package main.java;

import java.util.ArrayList;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/

public class Character {
	// Declare the variables, new the small balls.
	public static int DIAMETER = 35;
	public float x, y;
	private float originX, originY, diameter = DIAMETER;
	private MainApplet parent;
	private String name;
	private int color;
	private boolean inCircle;
	// Array for targets and their weights.
	private ArrayList<Character> targets = new ArrayList<Character>();
	private ArrayList<Integer> weights = new ArrayList<Integer>();

	// Initialize the values.
	public Character(MainApplet parent, String name, String color, float x, float y) {
		this.parent = parent;
		this.name = name;
		this.color = (int) Long.parseLong(color.replace("#", ""), 16);
		this.originX = x;
		this.originY = y;
		this.x = x;
		this.y = y;
	}

	public void display() {
		// Draw the small balls.
		parent.fill(color, 200);
		parent.noStroke();
		parent.ellipse(x, y, diameter, diameter);
		// If this ball is in the big circle, draw the line between the balls in the circle.
		if (inCircle) {
			for (Character c : targets) {
				parent.noFill();
				parent.stroke(0);
				parent.strokeWeight(weights.get(targets.indexOf(c)) / (float) 5);
				// Create two slopes to produce the bezier.
				float a = (550 + (x + c.x) / 2) / 2;
				float b = (340 + (y + c.y) / 2) / 2;
				if (c.isInCircle()) parent.bezier(x, y, a, b, a, b, c.x, c.y); // 550 340
			}
		}
	}
	
	// Just add the connected targets and the weights.
	public void addTarget(Character ch, Integer weight) {
		targets.add(ch);
		weights.add(weight);
	}
	
	public void setInCircle(boolean input) {
		inCircle = input;
	}
	
	public boolean isInCircle() {
		return inCircle;
	}
	
	public String getName() {
		return name;
	}
	
	public float getOriginX() {
		return originX;
	}
	
	public float getOriginY() {
		return originY;
	}
}
