import java.awt.*;

import java.awt.*;
public class Obstacle {
    public Image pic;
    public int xpos;
    public int ypos;
    public boolean isAlive = true;
    public int height = 90;
    public int width = 90;
    public String name;
    public int dx = -3;
    public int dy = 0;
    public Rectangle rec;
    public boolean up;
    public boolean down;
    public boolean right;
    public boolean left;
    public boolean hasBeenTouched = false;

    public Obstacle (String paramName, int paramXpos, int paramYpos){
        name = paramName;
        xpos = paramXpos;
        ypos = paramYpos;
        rec = new Rectangle (xpos, ypos, width, height);
    }
    public void wrap() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (ypos < 0) {
            ypos=700;
        }
        if (ypos > 700){
            ypos=0;
        }
        if (xpos < 0){
            xpos=5600;
        }
        //if (xpos > 1000-width){
        //    xpos=0;
        //   }
        rec = new Rectangle(xpos,ypos,width,height);
    }
}

