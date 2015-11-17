/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3d_v2;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import ATCDisplay.ATCDFlight;
import Utils.*;

public class Practica3D_v2 {

    public static void build(Aeropuerto aero, ShaderProgram shaderProgram){
        Avion avion1 = new Avion("Avion1", shaderProgram);
        //Avion avion2 = new Avion("Avion2", shaderProgram);
        Pista pista1 = new Pista("Pista Sur", shaderProgram);
        TorreControl torre1 = new TorreControl("Torre1", shaderProgram);
        
        ArrayList<Dibujable> controlled = new ArrayList<Dibujable>();
        controlled.add(avion1);
        
        //Set static posiitons
        //avion2.setPos(5.0f,5.0f,5.0f);
        
        //Adds the object to the airport
        aero.add(avion1);
        //aero.add(avion2);
        aero.add(pista1);
        aero.add(torre1);
        aero.controlled = controlled;
    }
    public static void main(String[] args) {
        Lienzo lienzo = new Lienzo();
        lienzo.init();
        ShaderProgram shaderProgram = lienzo.shaderProgram;
        Aeropuerto aero = new Aeropuerto("Barajas", shaderProgram);
        build(aero, shaderProgram);
        lienzo.run(aero);
    }
    
}
