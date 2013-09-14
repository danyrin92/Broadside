package Broadside;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Image;
import java.net.URL;

public class StartingClass extends Applet implements Runnable{

	//add KeyListener to implements above
	
	private BoatPlayer main_boat;
	private Image background;
	private Image image;
	private Image character;
	private Graphics second;
	private URL base;
	private static Background bg1;
	private static Background bg2;
	
	@Override
	public void init() {
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		//addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Broadside");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
		}
		background = getImage(base, "data/background.png");
		character = getImage(base, "data/boat.png");
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		main_boat = new BoatPlayer();
		
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void run() {
		while (true) {
			main_boat.update();
			bg1.update();
			bg2.update();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		g.drawImage(character, main_boat.getCenterX()-100, main_boat.getCenterY()-250, this);
	}
	
/*	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            System.out.println("Move up");
            break;

        case KeyEvent.VK_DOWN:
            System.out.println("Move down");
            break;

        case KeyEvent.VK_LEFT:
            System.out.println("Move left");
            main_boat.moveLeft();
            break;

        case KeyEvent.VK_RIGHT:
            System.out.println("Move right");
            main_boat.moveRight();
            break;

        case KeyEvent.VK_SPACE:
            System.out.println("Jump");
            main_boat.jump();
            break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            System.out.println("Stop moving up");
            break;

        case KeyEvent.VK_DOWN:
            System.out.println("Stop moving down");
            break;

        case KeyEvent.VK_LEFT:
            System.out.println("Stop moving left");
            main_boat.stop();
            break;

        case KeyEvent.VK_RIGHT:
            System.out.println("Stop moving right");
            main_boat.stop();
            break;

        case KeyEvent.VK_SPACE:
            System.out.println("Stop jumping");
            break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
*/	
    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }

}
