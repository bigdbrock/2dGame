package com.darren.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800, HEIGHT = 600;
	private boolean running = false;
	private Thread thread;

	public World world;
	public Entity entity;
	
	public Game() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		
		world = new World();
		entity = new Entity(70, HEIGHT/2);
		entity.loadObstacles(world.obstacles);
	}
	
	public synchronized void start() {
		if(running) {
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running) {
			return;
		}
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		world.update();
		entity.update();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		entity.render(g);
		
		g.dispose();
		
		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nanosecond = 1000000000 / 60;
		double delta = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nanosecond;
			lastTime = now;
			
			while(delta >= 1) {
				update();
				render();
				delta--;
			}
		}
		
		stop();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("2DGame");
		Game game = new Game();
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			entity.isPressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			entity.isPressed = false;
		}
	}

	public void keyTyped(KeyEvent e) { }
}
