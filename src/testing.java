

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class testing implements Runnable, KeyListener {
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

    // booleans for game restart
    public boolean gamePlaying = false;
    public boolean gameOver = false;
    public boolean isPaused = false;

    public Character Yellow; // main character for the game
    public Image BackgroundImage;
    public Image SquareImage;
    public Image TriangleImage;
    public Image YellowImage2;
    // public Obstacle Square;
    //  public Obstacle Triangle;
    /**
     * step 1 for squares array
     */

    public Obstacle[] squares;
    public Obstacle[] triangles;
    public SoundFile arcadeActionSound;

    // main method definition
    // this is the method that runs first and automatically
    public static void main(String[] args) {
        // BasicGame GeometryDash; // creates a new instance of the game
        testing geometryDash = new testing();

        new Thread(geometryDash).start(); // creates a thread and starts uo he code in the run method
    }

    public testing() {

        setUpGraphics();

        // declare variable and objects
        Yellow = new Character("Yellow", 200, 200);

        YellowImage2 = Toolkit.getDefaultToolkit().getImage("YellowImage2.png");

        BackgroundImage = Toolkit.getDefaultToolkit().getImage("BackgroundImage.jpeg");

        //  Triangle = new Obstacle("Triangle", 0, 600);
        // TriangleImage = Toolkit.getDefaultToolkit().getImage("TriangleImage.png");
        //  Square = new Obstacle("Square", 300, 600);
        //  SquareImage = Toolkit.getDefaultToolkit().getImage("SquareImage.png");

/**
 * Step 2 for the squares array: construct
 */

        squares = new Obstacle [8];
        triangles = new Obstacle [8];
/**
 * Step 3 for the squares array: fill using a for loop
 */

        placeObstacles();

        arcadeActionSound = new SoundFile("Arcade Action .wav");

        canvas.addKeyListener(this);
    }

    public void placeObstacles() {
        for(int x=0;x<squares.length;x++){
            squares[x] = new Obstacle ("squares",x*600+(int)(Math.random()*800),600);
            squares[x].pic = Toolkit.getDefaultToolkit().getImage("SquareImage.png");
        }
        for(int x=0;x<triangles.length;x++){
            triangles[x] = new Obstacle ("triangles",100+x*600,600);
            triangles[x].pic = Toolkit.getDefaultToolkit().getImage("TriangleImage.png");
            for (int y=0; y<squares.length; y++) {
                if (triangles[x].rec.intersects(squares[y].rec)) {
                    triangles[x].isAlive = false;
                    // squares[y].isAlive = true;
                }
            }
        }
    }
    public void run() {
        while (true) {

            moveThings(); //move all the game objects

            collisions();
            render(); // paint the graphics
            pause(10); // sleep for 10 ms
        } // the "while" loop will run forever
    }

    public void moveThings() {
        Yellow.move();
        // Square.wrap();
        // Triangle.wrap();
        for (int x=0; x<=7;x++){
            if (squares[x].isAlive == true){

                squares[x].wrap();
            }
        }
        for (int x=0; x<=7;x++){
            if (triangles[x].isAlive == true){

                triangles[x].wrap();
            }
        }
    }


    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (gamePlaying == false){
            // game instructions
            //"press space bar to begin"
            g.setColor(Color.yellow);
            g.fillRect(0,0,WIDTH,HEIGHT);
            g.setColor(Color.red);
            g.setFont(new Font("Times Roman",Font.BOLD,60));
            g.drawString("Press Spacebar to Begin",120,350);
        } // start screen

        else if(gamePlaying == true && gameOver  == false){
//draw the images
            g.drawImage(BackgroundImage, 0,0,WIDTH,HEIGHT,null);
            g.drawImage(YellowImage2,Yellow.xpos,Yellow.ypos,Yellow.width,Yellow.height,null);

            g.setColor(Color.blue);
            g.drawRect(Yellow.rec.x,Yellow.rec.y,Yellow.rec.width,Yellow.rec.height);

            for (int x=0; x<squares.length;x++){
                if (squares[x].isAlive == true){
                    g.drawImage(squares[x].pic,squares[x].xpos, squares[x].ypos,squares[x].width,squares[x].height,null);
                }
            }
            for (int x=0; x<squares.length;x++){
                if (squares[x].isAlive == true){
                    g.setColor(Color.green);
                    g.drawRect(squares[x].rec.x,squares[x].rec.y,squares[x].rec.width,squares[x].rec.height);
                 //   g.drawString(""+x, squares[x].xpos, squares[x].ypos);

                }
            }
            for (int x=0; x<triangles.length;x++) {
                if (triangles[x].isAlive == true) {
                    g.drawImage(triangles[x].pic, triangles[x].xpos, triangles[x].ypos, triangles[x].width, triangles[x].height, null);
                }
            }
            for (int x=0; x<triangles.length;x++){
                if (triangles[x].isAlive == true){
                    g.setColor(Color.green);
                    g.drawRect(triangles[x].rec.x,triangles[x].rec.y,triangles[x].rec.width,triangles[x].rec.height);
                   // g.drawString(""+x, triangles[x].xpos, triangles[x].ypos);
                }
            }

            g.setColor(Color.black);
            g.setFont(new Font("Times New Roman", Font.BOLD, 25));
            g.drawString("Square Points: " + Yellow.points, 650,100);
            // tally point keeper on screen

        } // game play - what I currently have in my render

        else if (Yellow.points>=5) {
            // you win screen
            g.setColor(Color.green);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.yellow);
            g.setFont(new Font("Times Roman", Font.BOLD, 40));
            g.drawString("You WIN Game Over", 100, 350);

        } // you won screen
        // background screen that says "you lose"
        // "press space to play again"
        else {
            // you lost screen
            g.drawString("You LOSE Game Over", 100, 350);

        } // game is over


        g.dispose();
        bufferStrategy.show();
    }

    public void collisions() {
        for (int x = 0; x < triangles.length; x++) {
            if (Yellow.rec.intersects(triangles[x].rec) && triangles[x].isAlive == true) {
                Yellow.xpos = 0;
                Yellow.points = 0;

                arcadeActionSound.play();
//                g.setColor(Color.red);
//                g.fillRect(0,0,WIDTH,HEIGHT);
//                g.setColor(Color.yellow);
//                g.setFont(new Font("Times Roman",Font.BOLD,40));
//                g.drawString("You LOSE Game Over",100,350);

            } // how do I make this work for when the character lands on top of the triangle and how do i make this intersection only happen once
        }
        for (int x = 0; x < squares.length; x++){
            if (Yellow.rec.intersects(squares[x].rec)){
                Yellow.ypos=squares[x].ypos -90;
                Yellow.rec.y = squares[x].ypos-90;
                if (squares[x].hasBeenTouched == false) {
                    Yellow.points++;
                    System.out.println("Point!");
                    squares[x].hasBeenTouched = true;
                }
                if (Yellow.points >= 5){
                    gameOver = true;
                } // win detection
                System.out.println("yellow points: " + Yellow.points);
            } // how do I make my character jump / have gravity?
        }
        for (int x = 0; x < squares.length; x++) {
            if(squares[x].rec.intersects(triangles[x].rec)){
                triangles[x].isAlive = false;
            }
        } // how do I get this to work . . . for the triangles to not show up if a square is already there
    }

    public void pause(int time ) { // ** never have to edit **
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private void setUpGraphics() {
        frame = new JFrame("BasicGame");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
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

//        if (keyCode == 38) {
//            Yellow.up = true;
//        } //up
//        if (keyCode == 40) {
//            Yellow.down = true;
//        }//down
        if (keyCode == 37) {
            Yellow.left = true;

        } //left
        if (keyCode == 39) {
            Yellow.right = true;
        } //right

        // spacebar to start the game
        if(gamePlaying == false && keyCode == 32){
            gamePlaying = true;
        }
        if (keyCode == 32 && Yellow.jumps <3){ // space bar
            Yellow.dy= -20;
            Yellow.jumps++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode = e.getKeyCode();
        System.out.println("Key pressed: " + key + ", Keycode is: " + keyCode);

//        if (keyCode == 38) {
//            //orange.dy=0;
//            Yellow.up = false;
//        } //up
//        if (keyCode == 40) {
//            // orange.dy=0;
//            Yellow.down = false;
//        }//down
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