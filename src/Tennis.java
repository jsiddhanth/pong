

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;

public class Tennis extends Applet implements Runnable,KeyListener {
	
	final int width=700;
	final int height=500;
	Thread thread;
	HumanPaddle p1;
	AIPaddle p2;
	Ball b1;
	boolean gameStarted;
	Graphics gfx;
	Image img;

	public void init() {
		
		this.resize(width, height);
		gameStarted =false;
		this.addKeyListener(this);
		p1 = new HumanPaddle(1);
		b1 = new Ball();
		p2 = new AIPaddle(2,b1);
		img= createImage(width,height);
		gfx=img.getGraphics();
		thread = new Thread(this);
		thread.start();
	}

	public void paint(Graphics g) {
		gfx.setColor(Color.black);
		gfx.fillRect(0, 0, width, height);
		
		if(b1.getX() < -10 || b1.getX() > 710){
			gfx.setColor(Color.red);
			gfx.drawString("GAME OVER", 350, 250);
		}
		
		else{
			p1.draw(gfx); 
			b1.draw(gfx);
			p2.draw(gfx);
		}
		
		if (!gameStarted) {
			gfx.setColor(Color.WHITE);
			gfx.drawString("Tennis", 340, 100);
			gfx.drawString("Press Enter to begin..", 310, 130);
		}
		g.drawImage(img, 0, 0, this);

	}

	public void update(Graphics g) {
		
		paint(g);
	}

	public void run() { 
		
		for(;;)
		{	
			if (gameStarted) {
			p1.move();
			p2.move();
			b1.move();
			b1.checkPaddleCollision(p1, p2);
			}
			repaint();
			
			try {
				Thread.sleep(10); 
				} 
			catch (InterruptedException e) 
				{
				e.printStackTrace();
				}
		}	
	} 


	public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				p1.setUpAccel(true);
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				p1.setDownAccel(true);
			}
			else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				gameStarted=true;
			}
			
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			p1.setUpAccel(false);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			p1.setDownAccel(false);
		}
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
 