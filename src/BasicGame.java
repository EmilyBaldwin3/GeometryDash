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
    public Image YellowImage2;
   //public Obstacle Square;
  //  public Obstacle Triangle;
    /***
     * step 1 for squares array
     * */
    public Obstacle[] squares;
    public Obstacle[] triangles;
    public SoundFile arcadeActionSound;

    // main method definition
    // this is the method that runs first and automatically
    public static void main(String[] args) {
        // BasicGame GeometryDash; // creates a new instance of the game
        BasicGame geometryDash = new BasicGame();

        new Thread(geometryDash).start(); // creates a thread and starts uo he code in the run method
    }

    public BasicGame() {

        setUpGraphics();

        // declare variable and objects
        Yellow = new Character("Yellow", 200, 200);

        YellowImage2 = Toolkit.getDefaultToolkit().getImage("YellowImage2.png");

        BackgroundImage = Toolkit.getDefaultToolkit().getImage("BackgroundImage.jpeg");

      //  Triangle = new Obstacle("Triangle", 0, 600);
       // TriangleImage = Toolkit.getDefaultToolkit().getImage("TriangleImage.png");
      //  Square = new Obstacle("Square", 300, 600);
      //  SquareImage = Toolkit.getDefaultToolkit().getImage("SquareImage.png");

        /***
         * Step 2 for the squares array: construct
         * */
        squares = new Obstacle [8];
        triangles = new Obstacle [8];
        /***
         * Step 3 for the squares array: fill using a for loop
         * */
        for(int x=0;x<squares.length;x++){
            squares[x] = new Obstacle ("squares",x*400,600);
            squares[x].pic = Toolkit.getDefaultToolkit().getImage("SquareImage.png");
        }
       for(int x=0;x<triangles.length;x++){
            triangles[x] = new Obstacle ("triangles",x*1000,600);
            triangles[x].pic = Toolkit.getDefaultToolkit().getImage("TriangleImage.png");
        }
       arcadeActionSound = new SoundFile("Arcade Action .wav");

        canvas.addKeyListener(this);
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

        //draw the images
        g.drawImage(BackgroundImage, 0,0,WIDTH,HEIGHT,null);
        g.drawImage(YellowImage2,Yellow.xpos,Yellow.ypos,Yellow.width,Yellow.height,null);
      //  g.drawImage(TriangleImage,Triangle.xpos,Triangle.ypos,Triangle.width,Triangle.height,null);
      //  g.drawImage(SquareImage,Square.xpos,Square.ypos,Square.width,Square.height,null);

        // draw hit boxes
        // g.setColor(Color.RED);
        //  g.drawRect(orange.xpos, orange.ypos,orange.width,orange.height);
        g.setColor(Color.blue);
        g.drawRect(Yellow.rec.x,Yellow.rec.y,Yellow.rec.width,Yellow.rec.height);
      //  g.setColor(Color.green);
      //  g.drawRect(Triangle.rec.x,Triangle.rec.y,Triangle.rec.width,Triangle.rec.height);
      //  g.setColor(Color.green);
      //  g.drawRect(Square.rec.x,Square.rec.y,Square.rec.width,Square.rec.height);

        for (int x=0; x<=7;x++){
            if (squares[x].isAlive == true){
                g.drawImage(squares[x].pic,squares[x].xpos, squares[x].ypos,squares[x].width,squares[x].height,null);
            }
        }
        for (int x=0; x<=7;x++){
            if (squares[x].isAlive == true){
               g.setColor(Color.green);
                g.drawRect(squares[x].rec.x,squares[x].rec.y,squares[x].rec.width,squares[x].rec.height);
            }
        }
        for (int x=0; x<=7;x++) {
            if (triangles[x].isAlive == true) {
                g.drawImage(triangles[x].pic, triangles[x].xpos, triangles[x].ypos, triangles[x].width, triangles[x].height, null);
            }
        }
        for (int x=0; x<=7;x++){
            if (triangles[x].isAlive == true){
                g.setColor(Color.green);
                g.drawRect(triangles[x].rec.x,triangles[x].rec.y,triangles[x].rec.width,triangles[x].rec.height);
            }
        }





        g.dispose();
        bufferStrategy.show();
    }

    public void collisions() {
        for (int x = 0; x < triangles.length; x++) {
            if (Yellow.rec.intersects(triangles[x].rec)) {
                Yellow.xpos = 0;
                arcadeActionSound.play();
            } // how do I make this work for when the character lands on top of the triangle and how do i make this intersection only happen once
        }
        for (int x = 0; x < squares.length; x++){
            if (Yellow.rec.intersects(squares[x].rec)){
                Yellow.ypos=squares[x].ypos -90;
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
