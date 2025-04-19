import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

class aabbCollision extends JFrame implements KeyListener{
    public static char[][] pixels = new char[18][24];
    boolean rightKey;
    boolean leftKey;
    boolean upKey;
    boolean downKey;
    public static boolean colliding;
    public static int playerx = 0;
    public static int playery = -2;
    public static ArrayList<Integer> entities = new ArrayList<Integer>();

    public aabbCollision(){
        this.setTitle("aabb collision");
        this.setSize(100,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setVisible(true);
        gameLoop();
    }

    private void gameLoop(){
        //add entities
        addEntity(4, -6, 3, 3);
        addEntity(19, -7, 3, 5);
        addEntity(9, -11, 6, 4);
        
        while (1==1){
            System.out.print("\033[H\033[2J");

            //reset buffer
            for (int i = 0; i < 18; i++){
                for (int j = 0; j < 24; j++){
                    pixels[i][j] = '.';
                }
            }

            //handle player
            if (leftKey){
                playerx--;
            }
            if (rightKey){
                playerx++;
            }
            if (downKey){
                playery--;
            }
            if (upKey){
                playery++;
            }
            drawRect(playerx, playery, 3, 3, '#');

            //handle entities
            for (int l = 0; l < entities.size(); l += 4){
                drawRect(entities.get(l), entities.get(l+1), entities.get(l+2), entities.get(l+3), '$');
            }

            colliding = false;
            //entity collision
            for (int h = 0; h < entities.size(); h += 4){
                checkCollision(playerx, playerx + 2, playery, playery + 2, entities.get(h), entities.get(h) + (entities.get(h+2)-1), entities.get(h+1), entities.get(h+1) + (entities.get(h+3)-1));
            }

            //render
            for (int e = 0; e < 18; e++){
                for (int f = 0; f < 24; f++){
                    System.out.print(pixels[e][f]);
                }
                System.out.println();
            }
            System.out.print(colliding);

            //delay
            try {
		Thread.sleep(100);
            } catch (InterruptedException e) {
		e.printStackTrace();
	        }
        }
    }

    //draw functions
    public static void setPixel(int x, int y, char sym){
        if (x >= 0 && x <= 23 && y >= -17 && y <= 0){
            pixels[Math.abs(y)][x] = sym;
            //now any pixel can be outside of the screen view!
        }
    }

    public static void drawRect(int x, int y, int width, int height, char sym){
        for (int a = x; a < x+width; a++){
            for (int b = y; b < y+height; b++){
                setPixel(a,b,sym);
            }
        }
    }

    public static void addEntity(int x, int y, int width, int height){
        entities.add(x);
        entities.add(y);
        entities.add(width);
        entities.add(height);
    }

    public static void checkCollision(int minX1, int maxX1, int minY1, int maxY1, int minX2, int maxX2, int minY2, int maxY2){
        if (minX1 <= maxX2 && maxX1 >= minX2 && minY1 <= maxY2 && maxY1 >= minY2){
            colliding = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e){
        //not used :O
    }

    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case 37: leftKey = true;
            break;
            case 38: upKey = true;
            break;
            case 39: rightKey = true;
            break;
            case 40: downKey = true;
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()){
            case 37: leftKey = false;
            break;
            case 38: upKey = false;
            break;
            case 39: rightKey = false;
            break;
            case 40: downKey = false;
            break;
        }
    }

    public static void main(String[] args){
        new aabbCollision();
    }
}