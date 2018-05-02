package com.darren.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Entity extends Rectangle {

	private static final long serialVersionUID = 1L;
	
	public boolean isPressed = false;
	private int speed = 5;
	private List<Rectangle> obstacles;

	public Entity(int x, int y) {
		setBounds(x, y, 32, 32);
		obstacles = new ArrayList<Rectangle>();
	}
	
	public void update() {
		if(isPressed) {
			y -= speed;
			if(y <= 0) {
				y = 0;
			}
		} else {
			y += speed;
			if(y >= Game.HEIGHT) {
				y = Game.HEIGHT;
			}
		}
		
		for(int i = 0; i < obstacles.size(); i++) {
			if(this.intersects(obstacles.get(i))) {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, width, height);
	}
	
	public void loadObstacles(List<Rectangle> obstacles) {
		this.obstacles = obstacles;
	}
}
