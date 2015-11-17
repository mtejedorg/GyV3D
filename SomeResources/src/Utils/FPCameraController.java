/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Agustin
 */
public abstract class FPCameraController {


    public FPCameraController(float x, float y, float z) {
    	
    }

    public abstract void yaw(float amount);
    public abstract void pitch(float amount);

    public abstract void walkForward(float distance);
    public abstract void walkBackwards(float distance);

    
    public abstract void strafeLeft(float distance);
    public abstract void strafeRight(float distance);
    
    public abstract Matrix4f lookThrough();
    public abstract void ProcessMouseMovement(float dx, float dy);

    
    public abstract float getMouseSensitivity();
    public abstract void setMouseSensitivity(float mouseSensitivity);
}
