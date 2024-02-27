//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BasicGame implements Runnable,KeyListener {
    //Variable Declaration Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    public Character Yellow; // main character for the game
    public Image BackgroundImage;
    public Image SquareImage;
    public Image TriangleImage;
    public Image YellowImage;
    public Obstacle Square;
    public Obstacle Triangle;

    // main method definition
    // this is the method that runs first and automatically
    public static void main(String[] args) {
        BasicGame GeometryDash; // creates a new instance of the game
        GeometryDash = new BasicGame();
        new Thread(String.valueOf(GeometryDash)).start(); // creates a thread and starts uo he code in the run method
    }

    public BasicGame() {

        setUpGraphics();
        // declare variable and objects
        Yellow = new Character("Yellow", 200, 200);
        YellowImage = Toolkit.getDefaultToolkit().getImage("YellowImage.png");
        BackgroundImage = Toolkit.getDefaultToolkit().getImage("BackgroundImage.png");

        Triangle = new Obstacle("Triangle", 0, 400);
        Square = new Obstacle("Square", 0, 500);

        canvas.addKeyListener(this);
    }

    public void run() {

    }

    public void moveThings() {

    }

    public void collisions() {

    }

    private void render() {

    }

    public void pause() {

    }

    private void setUpGraphics() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // Step 3 for keyboard: actually make something happen when you press a key
    @Override
    public void keyPressed(KeyEvent e) {

        char key = e.getKeyChar();
        int keyCode = e.getKeyCode();
        System.out.println("Key pressed: " + key + ", Keycode is: " + keyCode);

        if (keyCode == 38) {
            Yellow.up = true;
        } //up
        if (keyCode == 40) {
           Yellow.down = true;
        }//down
        if (keyCode == 37) {
            Yellow.left = true;

        } //left
        if (keyCode == 39) {
           Yellow.right = true;
        } //right
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode = e.getKeyCode();
        System.out.println("Key pressed: " + key + ", Keycode is: " + keyCode);

        if (keyCode == 38) {
            //orange.dy=0;
            Yellow.up = false;
        } //up
        if (keyCode == 40) {
            // orange.dy=0;
            Yellow.down = false;
        }//down
        if (keyCode == 37) {
            // orange.dx = 0;
            Yellow.left = false;
        } //left
        if (keyCode == 39) {
            // orange.dx=0;
            Yellow.right = false;
        } //right


    }
}
