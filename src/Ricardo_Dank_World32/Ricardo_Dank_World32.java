package Ricardo_Dank_World32;

import org.newdawn.slick.state.*;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Iterator;

import java.util.logging.Level;

import java.util.logging.Logger;

import org.newdawn.slick.Animation;

import org.newdawn.slick.Color;

import org.newdawn.slick.AppGameContainer;

import org.newdawn.slick.BasicGame;

import org.newdawn.slick.Font;

import org.newdawn.slick.GameContainer;

import org.newdawn.slick.Graphics;

import org.newdawn.slick.Image;

import org.newdawn.slick.Input;

import org.newdawn.slick.SlickException;

import org.newdawn.slick.SpriteSheet;

import org.newdawn.slick.TrueTypeFont;

import org.newdawn.slick.geom.Rectangle;

import org.newdawn.slick.geom.Shape;

import org.newdawn.slick.state.BasicGameState;

import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.css.Rect;

public class Ricardo_Dank_World32 extends BasicGameState {
    
    public Player player;
    
    public Item healthpotion, healthpotion1;
    
    public Item1 speedpotion, speedpotion1;
    
    public ItemWin antidote;
    
    public Enemy Messi;

    public ArrayList<Enemy> enemiez = new ArrayList();

    public ArrayList<Item> stuff = new ArrayList();

    public ArrayList<Item1> stuff1 = new ArrayList();

    public ArrayList<ItemWin> stuffwin = new ArrayList();

    private boolean[][] hostiles;

    private static TiledMap grassMap;

    private static AppGameContainer app;

    public ArrayList<Ninja> ninjaz = new ArrayList();

    private static Camera camera;

    public static int counter = 0;



    /**
     *
     * The collision map indicating which tiles block movement - generated based
     *
     * on tile properties
     */
	// changed to match size of sprites & map
    private static final int SIZE = 64;

	// screen width and height won't change
    private static final int SCREEN_WIDTH = 1000;

    private static final int SCREEN_HEIGHT = 750;

    public Ricardo_Dank_World32(int xSize, int ySize) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {

        gc.setTargetFrameRate(60);

        gc.setShowFPS(false);

		// *******************
		// Scenerey Stuff
		// ****************
        grassMap = new TiledMap("res/Official_Game.tmx");

		// Ongoing checks are useful
		//System.out.println("Tile map is this wide: " + grassMap.getWidth());
        camera = new Camera(gc, grassMap);
        player = new Player();
		// *********************************************************************************
		// Player stuff --- these things should probably be chunked into methods
        // and classes
		// *********************************************************************************
      

		// *****************************************************************
		// Obstacles etc.
		// build a collision map based on tile properties in the TileD map
        Blocked.blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];

		

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {

            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {

				
                // for a reason
                int tileID = grassMap.getTileId(xAxis, yAxis, 0);

                String value = grassMap.getTileProperty(tileID,
                        "blocked", "false");

                if ("true".equals(value)) {

					
                    Blocked.blocked[xAxis][yAxis] = true;

                }

            }

        }

        for (int layer = 0; layer < 3; layer++) {
            for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {

                for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {

				
                    int tileID = grassMap.getTileId(xAxis, yAxis, layer);

                    String value = grassMap.getTileProperty(tileID,
                            "blocked", "false");

                    if ("true".equals(value)) {

                      
                        Blocked.blocked[xAxis][yAxis] = true;

                    }

                }

            }

        }

        
        hostiles = new boolean[grassMap.getWidth()][grassMap.getHeight()];

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (yBlock % 7 == 0 && xBlock % 15 == 0) {
                        Item i = new Item(xAxis * SIZE, yAxis * SIZE);
                        stuff.add(i);
                        //stuff1.add(h);
                        hostiles[xAxis][yAxis] = true;
                    }
                }
            }
        }

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (xBlock % 9 == 0 && yBlock % 25 == 0) {
                        Item1 h = new Item1(xAxis * SIZE, yAxis * SIZE);
                        //	stuff.add(i);100100100100100100
                        stuff1.add(h);
                        hostiles[xAxis][yAxis] = true;
                    }

                }
            }
        }

        healthpotion = new Item(100, 150);
        healthpotion1 = new Item(500, 800);
        stuff.add(healthpotion);
        stuff.add(healthpotion1);

                
        Messi = new Enemy(89, 64);
        

                
         enemiez.add(Messi);
         

        speedpotion = new Item1(170, 90);
        speedpotion1 = new Item1(450, 100);
        stuff1.add(speedpotion);
        stuff1.add(speedpotion1);

        antidote = new ItemWin(5774, 2310);
        stuffwin.add(antidote);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {

        camera.centerOn((int) Player.x, (int) Player.y);

        camera.drawMap();

        camera.translateGraphics();

		// it helps to add status reports to see what's going on
		// but it gets old quickly
		
        player.sprite.draw((int) Player.x, (int) Player.y);

       

        g.drawString("Health: " + Player.health / 1000, camera.cameraX + 10,
                camera.cameraY + 10);

        g.drawString("speed: " + (int) (Player.speed * 10), camera.cameraX + 10,
                camera.cameraY + 25);

		//g.draw(player.rect);
        g.drawString("time passed: " + counter / 1000, camera.cameraX + 600, camera.cameraY);
        // moveenemies();

        for (Item i : stuff) {
            if (i.isvisible) {
                i.currentImage.draw(i.x, i.y);
				

            }
        }

                
        Messi.currentanime.draw(Messi.Bx, Messi.By);
         

        for (Item1 h : stuff1) {
            if (h.isvisible) {
                h.currentImage.draw(h.x, h.y);
				// draw the hitbox
                //g.draw(h.hitbox);

            }
        }

        for (ItemWin w : stuffwin) {
            if (w.isvisible) {
                w.currentImage.draw(w.x, w.y);
				// draw the hitbox
                //g.draw(w.hitbox);

            }
        }

        for (Ninja y : ninjaz) {
            if (y.isvisible) {
                y.currentImage.draw(y.x, y.y);
				// draw the hitbox
                //g.draw(w.hitbox);

            }
        }

        for (Enemy e : enemiez) {
            if (e.isvisible) {
                e.currentanime.draw(e.Bx, e.By);
				// draw the hitbox
                //g.draw(w.hitbox);

            }
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {

        counter += delta;

        Input input = gc.getInput();

        float fdelta = delta * Player.speed;

        Player.setpdelta(fdelta);

        double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);

		// System.out.println("Right limit: " + rightlimit);
        float projectedright = Player.x + fdelta + SIZE;

        boolean cangoright = projectedright < rightlimit;

        for (Enemy e : enemiez) {

                     //  e.setdirection();
            e.move();

        }

		// there are two types of fixes. A kludge and a hack. This is a kludge.
        if (input.isKeyDown(Input.KEY_UP)) {

            player.sprite = player.up;

            float fdsc = (float) (fdelta - (SIZE * .15));

            if (Player.y > 5 & !(isBlocked(Player.x, Player.y - fdelta) || isBlocked((float) (Player.x + SIZE + 1.5), Player.y - fdelta))) {

                player.sprite.update(delta);

				// The lower the delta the slower the sprite will animate.
                Player.y -= fdelta;

            }

        } else if (input.isKeyDown(Input.KEY_DOWN)) {

            player.sprite = player.down;

            if (!isBlocked(Player.x, Player.y + SIZE + fdelta)
                    || !isBlocked(Player.x + SIZE - 1, Player.y + SIZE + fdelta)) {

                player.sprite.update(delta);

                Player.y += fdelta;

            }

        } else if (input.isKeyDown(Input.KEY_LEFT)) {

            player.sprite = player.left;

            if (!(isBlocked(Player.x - fdelta, Player.y) || isBlocked(Player.x
                    - fdelta, Player.y + SIZE - 1))) {

                player.sprite.update(delta);

                Player.x -= fdelta;

            }

        } else if (input.isKeyDown(Input.KEY_RIGHT)) {

            player.sprite = player.right;

			// the boolean-kludge-implementation
            if (cangoright
                    && (!(isBlocked(Player.x + SIZE + fdelta,
                            Player.y) || isBlocked(Player.x + SIZE + fdelta, Player.y
                            + SIZE - 1)))) {

                player.sprite.update(delta);

                Player.x += fdelta;

            }

        }

        Player.rect.setLocation(Player.getplayershitboxX(),
                Player.getplayershitboxY());

        for (Item i : stuff) {

            if (Player.rect.intersects(i.hitbox)) {
                //System.out.println("yay");
                if (i.isvisible) {

                    Player.health += 10000;
                    i.isvisible = false;
                }

            }
        }

        for (Ninja n : ninjaz) {

            if (Player.rect.intersects(n.hitbox)) {
                //System.out.println("yay");
                if (n.isvisible) {

                    Player.health += 100000;
                    n.isvisible = false;
                }

            }
        }

        for (Enemy e : enemiez) {

            if (Player.rect.intersects(e.rect)) {
                //System.out.println("yay");
                if (e.isvisible) {

                    Player.health -= 1000;

                }

            }
        }

        for (Item1 h : stuff1) {

            if (Player.rect.intersects(h.hitbox)) {
                //System.out.println("yay");
                if (h.isvisible) {

                    Player.speed += .1f;
                    h.isvisible = false;
                }

            }
        }

        for (ItemWin w : stuffwin) {

            if (Player.rect.intersects(w.hitbox)) {
                //System.out.println("yay");
                if (w.isvisible) {
                    w.isvisible = false;
                    makevisible();
                    sbg.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

                }

            }
        }

        Player.health -= counter / 1000000;
        if (Player.health <= 0) {
            makevisible();
            sbg.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }

    }

    public int getID() {

        return 1;

    }

    public void makevisible() {
        for (Item1 h : stuff1) {

            h.isvisible = true;
        }

        for (Item i : stuff) {

            i.isvisible = true;
        }
    }

    private boolean isBlocked(float tx, float ty) {

        int xBlock = (int) tx / SIZE;

        int yBlock = (int) ty / SIZE;

        return Blocked.blocked[xBlock][yBlock];

		// this could make a better kludge
    }

}
