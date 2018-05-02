package com.darren.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

	public List<Rectangle> obstacles;
	public int currentTime;
	public final int timeBetweenObstacles = 80;
	public final int speedOfScreen = 4;
	
	private Random random;
	
	public World() {
		obstacles = new ArrayList<Rectangle>();
		random = new Random();
	}
	
	public void update() {
		currentTime++;
		
		if(currentTime == timeBetweenObstacles) {
			currentTime = 0;
			
			int width = 54;
			
			int height1 = random.nextInt(Game.HEIGHT / 2);
			
			int y2 = height1 + findRandomGapBetweenObstacles();
			int height2 = Game.HEIGHT - y2;
			
			obstacles.add(new Rectangle(Game.WIDTH, 0, width, height1));
			obstacles.add(new Rectangle(Game.WIDTH, y2, width, height2));
		}
		
		for(int i = 0; i < obstacles.size(); i++) {
			Rectangle rect = obstacles.get(i);
			rect.x -= speedOfScreen;
			
			if(rect.x + rect.width < 0) {
				obstacles.remove(i--);
				continue;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.green);
		
		for(Rectangle rect : obstacles) {
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
	}
	
	public int findRandomGapBetweenObstacles() {
		return 170;
	}
}
