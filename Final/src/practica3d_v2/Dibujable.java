/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3d_v2;

import Utils.*;

public class Dibujable {
    
    protected int vbo_v, vbo_n, vbo_t, ebo;
    protected ShaderProgram shaderProgram;
    protected final OpenGLHelper openGLHelper = new OpenGLHelper("Tutorial Mix", new FPCameraController(-5, -5, -15));
    protected int uniModel;
    protected int uNMatrixAttribute;

    protected int uKdAttribute;
    protected int uKaAttribute;
    protected int useTextures;
    protected int uniTex1, uniTex2, uniTex3;
    protected float x,y,z,xr,yr,zr,xs,ys,zs;
    protected int angle;
    protected float bearing = 0f;
    protected float inclination = 0f;
    
    public String ID;
    
    public Dibujable (String ID, ShaderProgram shaderProgram){
        this.ID = ID;
        x = y = z = xr = yr = zr = angle = 0;
        xs = ys = zs = 1.0f;
    }
    
    public void setPos(float newx, float newy, float newz){
        this.x = newx;
        this.y = newy;
        this.z = newz;
    }
    
    public void setRotation(float newx, float newy, float newz, int newangle){
        this.xr = newx;
        this.yr = newy;
        this.zr = newz;
        this.angle = newangle;
    }
    
    public void setRotation(float bearing, float inclination, float ejez){
        this.bearing = (float) Math.toDegrees(bearing);
        this.inclination = (float) Math.toDegrees(inclination);
    }
    
    public void setScale(float newx, float newy, float newz){
        this.xs = newx;
        this.ys = newy;
        this.zs = newz;
    }
    
    public void draw(OpenGLHelper openGLHelper){
        System.out.println("Cagada brutal, he entrado por dibujable");
    }
    
}
