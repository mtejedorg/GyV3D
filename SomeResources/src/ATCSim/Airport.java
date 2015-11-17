/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ATCSim;

import ATCDisplay.ATCDFlight;
import Utils.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
 
/**
 *
 * @author Paco
 */
public class Airport implements Drawable
{
    private ShaderProgram shaderProgram;
    private final OpenGLHelper openGLHelper = new OpenGLHelper("Airport", new FPCameraControllerA(-5, -5, -15));

    
    
    private Land land;
    private LandStrip strip;
    private Tower tower;
    private SimpleFlight sflight;
    private Flight cflight;
 
   
    private static boolean Test = true;
    
	private static Ice.Communicator ic = null;
	private static ATCDisplay.AirportInterfacePrx airportI = null;

    
    public void run() {
    	
		String[] args1 = {new String("")};
		ic = Ice.Util.initialize(args1);
		Ice.ObjectPrx base = null;		
		
		
		base = ic.stringToProxy("AirportInterface:default -p 10000");
		airportI = ATCDisplay.AirportInterfacePrxHelper.checkedCast(base);
    	
        openGLHelper.initGL("VS_Texture.vs", "FS_Texture.fs");
        initObjects();
        prepareBuffers();
        openGLHelper.run(this);
    }

    private void initObjects() {
    	  shaderProgram = openGLHelper.getShaderProgram();
          
    	  
    	  
          land = new Land();
          land.setTexture("nicegrass.jpg");
          strip = new LandStrip();
          strip.setTexture("stone-256px.jpg");
          tower = new Tower();
          tower.setTexture("stone-128px.jpg");
         
          sflight = new SimpleFlight();
          cflight = new Flight();
         
	}

	private void prepareBuffers() {
        shaderProgram = openGLHelper.getShaderProgram();              
        
    }

    @Override
    public void draw()
    {
        /* Render a model */
        drawSomeModel();
    }
    
    float angle;
    private static final float angularVelocity = 30.f;
 
    private void drawSomeModel() {
        


    	//Get flights positions
    	List<ATCDFlight> flights = airportI.getFlights();
		Iterator<ATCDFlight> itf = flights.iterator();
		while(itf.hasNext()){
			
			ATCDFlight flightdata = itf.next();
			sflight.setPosition(flightdata.pos.x/1000.0f, flightdata.pos.z/1000.0f, flightdata.pos.y/1000.0f);
			sflight.setRotation(0.0f, 0.0f, 1f, 0);
			sflight.setScale(0.2f, 0.2f, 0.2f);
			
			System.out.println("("+(flightdata.pos.x/1000.0f)+", "+flightdata.pos.z/1000.0f+", "+(flightdata.pos.y/1000.0f)+")");
			//sflight.setRotation(flightdata.bearing, flightdata.inclination, 0.0f);
			
			
		}
  	
    	land.setRotation(0.0f, 0.0f, 1f, 0);
    	land.setPosition(10,0,0);
    	land.setScale(20.0f, 1.0f, 20.0f);
    	
    	strip.setRotation(0.0f, 0.0f, 1f, 0);
    	strip.setPosition(10, 0.1f,0.0f);
    	strip.setScale(10.0f, 1.0f, 2.0f);
    	
    	tower.setRotation(0.0f, 0.0f, 1f, 0);
    	tower.setPosition(10, 0.0f, 5.0f);
    	tower.setScale(1.0f, 5.0f, 1.0f);
  	
    	cflight.setRotation(0.0f, 0.0f, 1f, 0);
    	cflight.setPosition(10, 4.0f, 8.0f);
    	cflight.setScale(0.0001f, 0.0001f, 0.0001f);
  	
    	
    	land.draw(openGLHelper); 
        strip.draw(openGLHelper); 
        tower.draw(openGLHelper); 
        sflight.draw(openGLHelper); 
        cflight.draw(openGLHelper); 
             
       
    }

    public static void main(String[] args) {
        new Airport().run();
    }

}
