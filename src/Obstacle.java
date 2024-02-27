import java.awt.*;

import java.awt.*;
public class Obstacle {
        public int xpos;
        public int ypos;
        public boolean isAlive;
        public int height = 90;
        public int width = 90;
        public String name;
        public int dx = 2;
        public int dy = 2;
        public Rectangle rec;
        public boolean up;
        public boolean down;
        public boolean right;
        public boolean left;

        public Obstacle (String paramName, int paramXpos, int paramYpos){
            name = paramName;
            xpos = paramXpos;
            ypos = paramYpos;
            rec = new Rectangle (xpos, ypos, width, height);
        }
    }

