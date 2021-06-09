import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/******************************************************************************
* 
*FruitNinja.java
* Testing for MouseMotionListener
* 
*****************************************************************************/
public class FruitNinja extends JFrame
                       implements MouseListener, MouseMotionListener, ActionListener   // need ActionListener for time
{
	private Fruit b;
	private static final int DELAY_IN_MILLISEC = 25; 
	
	private static final int WINDOW_WIDTH = 1100;
	private static final int WINDOW_HEIGHT = 800;
	
	private static Color backgroundColor;
	private Random gen = new Random();
	private ArrayList <Point> slashPoints;
	private ArrayList <Fruit> theFruit;
	private double timeInSeconds;

	/**
	 * Create the window and register this as a MouseListener and a MouseMotionListener
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		FruitNinja fn = new FruitNinja();
		fn.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		fn.setVisible(true);
		
		// Register listeners
		fn.addMouseListener(fn);
		fn.addMouseMotionListener(fn);
		
		fn.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		Timer clock = new Timer(DELAY_IN_MILLISEC, fn);			
		clock.start();		
	}

	/**
	 * MouseDemo constructor.  Just create the ball.
	 */
	public FruitNinja()
	{
		timeInSeconds = 0.0;
		
		backgroundColor = Color.BLACK;
		double deltaX = 5 * gen.nextDouble();
		double deltaY = - 15 - gen.nextInt (5);
		
		// equal chance of right or left movement
		
		slashPoints = new ArrayList <Point>();
		theFruit = new ArrayList <Fruit>();
		
		b = new Fruit(100, WINDOW_HEIGHT, deltaX, deltaY, 20, Color.YELLOW);	
		
		theFruit.add (b);
		theFruit.add (new Fruit (200, WINDOW_HEIGHT, 4, -12, 15, Color.ORANGE));
		
		

	}
	
	public void actionPerformed(ActionEvent e)	
	{
		// Move the ball.
		for (int j = 0; j < theFruit.size(); j++)
			theFruit.get(j).move();
		//b.bounce(0, WINDOW_WIDTH, 22, WINDOW_HEIGHT);
		timeInSeconds += (double ) DELAY_IN_MILLISEC / 1000;
		System.out.println (timeInSeconds);
		
		// Update the window.
		repaint();
	}
	
	/**
	 * Draw the info rectangle and the ball WITHOUT first clearing the screen
	 * 
	 * @param g		The usual Graphics object
	 */
	public void paint(Graphics g)
	{
		// Draw the info rectangle.
		g.setColor(backgroundColor);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT );
		
		// Draw the ball object.
		for (int j = 0; j < theFruit.size(); j++)
			theFruit.get(j).draw(g);
		
		g.setColor (Color.MAGENTA);
		for (int i = 0; i < slashPoints.size() - 1; i++)
			g.drawLine(slashPoints.get(i).getX(), slashPoints.get(i).getY(), slashPoints.get(i + 1).getX(), slashPoints.get(i + 1).getY());
			
		backgroundColor = Color.BLACK;	
	}
	
	/********************************************
	   MouseListener event handlers
	********************************************/	

	/**
	 * Called when the mouse is clicked (= pressed and released without moving
	 *    while the mouse is in our window)
	 * Required for any MouseListener

	 * 
	 * @param e		Contains info about the mouse click
	 */
	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		
		if (b.processClick (x, y))
			backgroundColor = Color.yellow; // hit
		else
			backgroundColor = Color.DARK_GRAY; // miss
	}
	
	/**
	 * Called when the mouse is pressed (in our window)
	 * Required for any MouseListener
	 * 
	 * @param e		Contains info about the mouse click
	 */
	public void mousePressed(MouseEvent e)
	{
		// Called when the mouse is pressed in the window.
		//System.out.println("In mousePressed");

	}
	
	/**
	 * Called when the mouse is released (in our window)
	 * Required for any MouseListener
	 * 
	 * @param e		Contains info about the mouse click
	 */
	public void mouseReleased(MouseEvent e)
	{
		// Called when the mouse is released after having been pressed in the window.
		// System.out.println("In mouseReleased");
		
		slashPoints.clear(); // remove all points at end of each slash
	}
	
	/**
	 * Called when the mouse enters our window.
	 * Required for any MouseListener
	 * 
	 * @param e		Contains info about the mouse click
	 */
	public void mouseEntered(MouseEvent e)
	{
		//System.out.println("In mouseEntered");
	}
	
	/**
	 * Called when the mouse exits our window.
	 * Required for any MouseListener
	 * 
	 * @param e		Contains info about the mouse click
	 */
	public void mouseExited(MouseEvent e)
	{
		//System.out.println("In mouseExited");
	}
	
	/********************************************
	   MouseMotionListener event handlers
	********************************************/	

	/**
	 * Called when the mouse is dragged (in our window).
	 * Required for any MouseMotionListener.
	 * Move the ball to the new dragged position.
	 * 
	 * @param e		Contains info about the mouse location
	 */
	public void mouseDragged(MouseEvent e)
	{
		//System.out.println("                In mouseDragged");
		System.out.println (e.getX() + " , " + e.getY());
		Point p = new Point (e.getX(), e.getY());
		slashPoints.add(p);
		
	}
	
	/**
	 * Called when the mouse is moved (in our window).
	 * Required for any MouseMotionListener.
	 * 
	 * @param e		Contains info about the mouse location
	 */
	public void mouseMoved(MouseEvent e)
	{
		//System.out.println("                In mouseMoved");
	}
}

/***********************************************************************
   Fruit Class
***********************************************************************/
class Fruit
{
	
	private double x, y;		// Center of the fruit
	private double radius;		// Radius of the fruit
	private double dx, dy; // Rate of movement of the fruit
	private Color color;	// Color of the fruit
	
	public Fruit(double xIn, double yIn, double dxIn, double dyIn, int radiusIn, Color colorIn)
	{
		x = xIn;
		y = yIn;
		dx = dxIn;
		dy = dyIn;
		radius = radiusIn;
		color = colorIn;

	}
	
	// Accessor Methods
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public double getRadius()
	{
		return radius;
	}
	public Color getColor()
	{
		return color;
	}
	

	// Mutator Methods
	public void setColor(Color colorIn)
	{
		color = colorIn;
	}
	public void setCenter(int xIn, int yIn)
	{
		x = xIn;
		y = yIn;
	}
	
	public double getVelocity ()
	{
		return Math.sqrt (dx * dx + dy * dy);
	}
	

	
	public void move()
	 {
		  x = x + dx;
		  y = y + dy;
		  dy += 0.2;
		 
	 }
	
	 public void bounce(int xLow, int xHigh, int yLow, int yHigh)
	 {
	  // Check for an x bounce.  Note that we bounce if the x is too
	  //  low or too high AND IS HEADING IN THE WRONG DIRECTION.
	  if ((x - radius <= xLow && dx < 0) || (x + radius >= xHigh && dx > 0))
	  {
		  dx = -dx;
	   
	  }
	   
	  // Now check for a y bounce.
	  if ((y - radius <= yLow && dy < 0) || (y + radius >= yHigh && dy > 0))
	  {
		  dy = -dy;
	  }
	 }
	 
	 public boolean processClick (int mouseX, int mouseY)
	 // evaluates if given x and y are within the Ball
	 // returns true for hit, false for miss
	 {
		 double xDistance = x - mouseX;
		 double yDistance = y - mouseY;
		 
		 double distance = Math.sqrt (xDistance * xDistance + yDistance * yDistance);
		 // from Pythagorean 
		 if (distance < radius)
			{
				System.out.println ("HIT!");
			
				return true; // indicates hit
			}
			else
			{
				System.out.println ("MISS!");
			
				return false;
			}
	 }
	 
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval((int)(x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
		
	}
}
		
	