/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slickexample;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author rguajardo0103
 */
public class Orb {
    private int x, y , width, height;
    private int dmg,  hitboxX, hitboxY;
    private boolean isVisible;
    Image orbpic;
    Shape hitbox;
    
    public Orb (int a, int b) throws SlickException{
        this.x = a;
        this.y = b;
        this.isVisible = false;
        this.orbpic = new Image ("res/orbs/Ninja_12.png");
        this.hitbox = new Rectangle(a, b, 32, 32);
         
    }

    /*
    *Getters and setters are a common concept in Java
    *A desing guidline in Java, and object oriented
    *programing in general is to encapsulate/isolate
    *values as mach as possible.
    *Getters - are methods used to query the values of 
    *istamce variable
    *thisgetLocationX();
    *
    */
    
    
    
    
    
    
    
}
