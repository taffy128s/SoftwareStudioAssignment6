package main.java;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	public float x, y, radius = 35;
	private MainApplet parent;
	private String name;
	private int color;
	boolean mouseOver, locked, inCircle;
	private Ani ani;

	public Character(MainApplet parent, String name, String color, float x, float y, 
			boolean mouseOver, boolean locked, boolean inCircle) {

		this.parent = parent;
		this.name = name;
		this.color = (int) Long.parseLong(color.replace("#", ""), 16);
		this.x = x;
		this.y = y;
		this.mouseOver = mouseOver;
		this.locked = locked;
		this.inCircle = inCircle;
		Ani.init(this.parent);
		
	}

	public void display() {
		parent.fill(color);
		parent.noStroke();
		parent.ellipse(x, y, radius, radius);
		if (inCircle) {
			
		}
		
	}
	
	public void showLabelAndBigger() {
		if (PApplet.dist(x, y, parent.mouseX, parent.mouseY) < 35 / 2) {
			ani = Ani.to(this, (float) 0.5, "radius", 50);
			parent.fill(0, 162, 123);
			parent.rect(parent.mouseX, parent.mouseY -  15, name.length() * 15, 30, 15);
			parent.fill(255);
			parent.textSize(16);
			parent.text(this.name, parent.mouseX + 10, parent.mouseY + 5);
		} else ani = Ani.to(this, (float) 0.5, "radius", 35);
	}
	
}
