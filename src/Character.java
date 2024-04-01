import java.awt.*;
public class Character {
    public int xpos;
    public int ypos;
    public boolean isAlive;
    public int height = 90;
    public int width = 90;
    public String name = "Yellow";
    public int dx = 2;
    public int dy = 2;
    public Rectangle rec;
    public boolean up;
    public boolean down;
    public boolean right;
    public boolean left;
    public int points = 0;

    public Character (String paramName, int paramXpos, int paramYpos){
        name = paramName;
        xpos = paramXpos;
        ypos = paramYpos;
        rec = new Rectangle (xpos, ypos, width, height);
    }
    // move method
    public void move() {

        /**These are controlling movement based on key listener booleans*/
        if (up == true) {
            ypos = ypos - dy;
        } else if (down == true){
            ypos = ypos + dy;
        }
        if (right == true){
            xpos = xpos + dx;
        } else if (left == true){
            xpos = xpos -dx;
        }

        /**These are keeping the object on the screen by bouncing off the walls */
        if (ypos < 0) {
            dy=-dy;
        }
        //bottom
        if (ypos > 700-height) {
            dy=-dy;
        }
        //right side
        if (xpos < 0) {
           dx=-dx;
        }
        //left side
        if (xpos > 1000-width){
            dx=-dx;
        }


        rec = new Rectangle(xpos,ypos,width,height);
    } // end of the method
}

