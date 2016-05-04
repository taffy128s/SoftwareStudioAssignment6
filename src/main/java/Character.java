package main.java;

import java.util.ArrayList;

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
	//MY ADD
	private ArrayList<Character> targets;

	public Character(MainApplet parent, String name, String color, float x, float y) {
		this.parent = parent;
		this.name = name;
		this.color = (int) Long.parseLong(color.replace("#", ""), 16);
		this.originX = x;
		this.originY = y;
		this.x = x;
		this.y = y;
		
		this.targets = new ArrayList<>();
	}

	public void display() {
		parent.fill(color);
		parent.noStroke();
		parent.ellipse(x, y, diameter, diameter);
		if (inCircle) {
			/*for(int i = 0; i < targets.size(); ++i){
				this.parent.line(this.x+4, this.y+4, targets.get(i).x+4, targets.get(i).y+4);
			}*/
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
	
	//My add
	public ArrayList<Character> getTargets(){
		return this.targets;
	}

	public void addTarget(Character target) {
		this.targets.add(target);
	}
}
