package main.java;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	public static int DIAMETER = 35;
	public float x, y;
	private float originX, originY, diameter = DIAMETER;
	private MainApplet parent;
	private String name;
	private int color;
	private boolean inCircle;

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
		parent.fill(color);
		parent.noStroke();
		parent.ellipse(x, y, diameter, diameter);
		if (inCircle) {
			
		} else {
			
		}
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
