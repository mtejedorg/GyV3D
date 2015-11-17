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
public class FPCameraControllerB extends FPCameraController {

	
	private float mouseSensitivity = 0.05f;
	 
	private Vector3f pos;
	private Vector3f target;
	private Vector3f up;

	private float yaw = 0.0f;
	private float pitch = 0.0f;
	
	 static public float normalizePi(float value) {
		 final double twoPi = Math.PI + Math.PI;          
		     while (value <= -Math.PI) value += twoPi;
		     while (value >   Math.PI) value -= twoPi;
		    
		return value;
	}
	
    public FPCameraControllerB(float x, float y, float z) {
    	super(x, y, z);
    	
    	pos = new Vector3f();
		target = new Vector3f();
		up = new Vector3f();

		pos.x = x;
		pos.y = y;
		pos.z = z;

		yaw =(float) Math.PI;
		pitch = (float) -Math.PI/2.0f;
		
		target.x = (float) (pos.x + (Math.cos(yaw) * Math.sin (pitch)));
		target.y = (float) (pos.y + (Math.sin(yaw) * Math.sin (pitch)));
		target.z = (float) (pos.z + Math.cos(pitch));
		
		up.x = 0.0f;
		up.y = 0.0f;
		up.z = 1.0f;    
		
    }
    //increment the camera's current yaw rotation
    public void yaw(float amount) {
        //increment the yaw by the amount param
    	rotateYaw(yaw + amount);
    	
    }

    //increment the camera's current yaw rotation
    public void pitch(float amount) {
        //increment the pitch by the amount param
    	rotatePitch(pitch + amount); 
    }

    //moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance) {
    	translate(distance, 0.0f);
    	
    }

    //moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance) {
    	translate(-distance, 0.0f);
    }

    //strafes the camera left relitive to its current rotation (yaw)
    public void strafeLeft(float distance) {
    	translate(0.0f, distance);
    }

    //strafes the camera right relitive to its current rotation (yaw)
    public void strafeRight(float distance) {
    	translate(0.0f, -distance);
    }

    //translates and rotate the matrix so that it looks through the camera
    //this dose basic what gluLookAt() does
    public Matrix4f lookThrough() {
		Vector3f forward = new Vector3f();
		Vector3f UP = new Vector3f();
		Vector3f s = new Vector3f();
		Vector3f u = new Vector3f();
		
		
		forward.x = target.x - pos.x;
		forward.y = target.y - pos.y;
		forward.z = target.z - pos.z;
			
		UP.x = up.x;
		UP.y = up.y;
		UP.z = up.z;
		
		forward.normalize();
		UP.normalize();
		
		s = forward.cross(UP);
		s.normalize();
		u = s.cross(forward);
		
		Vector4f c0 = new Vector4f(s.x, u.x, -forward.x, 0.0f);
		Vector4f c1 = new Vector4f(s.y, u.y, -forward.y, 0.0f);
		Vector4f c2 = new Vector4f(s.z, u.z, -forward.z, 0.0f);
		Vector4f c3 = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
		
		/*
		Vector4f c0 = new Vector4f(s.x, s.y, s.z, 0.0f);
		Vector4f c1 = new Vector4f(u.x, u.y, u.z, 0.0f);
		Vector4f c2 = new Vector4f(-forward.x, -forward.y, -forward.z, 0.0f);
		Vector4f c3 = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);*/
	
		Matrix4f m = new Matrix4f(c0, c1, c2, c3);
		
		/*m.m00 = s.x;
		m.m01 = s.y;
		m.m02 = s.z;
		m.m10 = u.x;
		m.m11 = u.y;
		m.m12 = u.z;
		m.m20 = -forward.x;
		m.m21 = -forward.y;
		m.m22 = -forward.z;
		 */
		//m = m.multiply(Matrix4f.translate(-pos.x, -pos.y, -pos.z));
		
		return m;    
		}

    public void ProcessMouseMovement(float dx, float dy) {
        //controll camera yaw from x movement fromt the mouse
    	rotateYaw(dx * getMouseSensitivity());
    	rotatePitch(dy * getMouseSensitivity());
        
    }

    /**
     * @return the mouseSensitivity
     */
    public float getMouseSensitivity() {
        return mouseSensitivity;
    }

    /**
     * @param mouseSensitivity the mouseSensitivity to set
     */
    public void setMouseSensitivity(float mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }
    
	public void rotateYaw(float dyaw) {

		yaw = normalizePi(yaw + dyaw);
		
		updateTarget();
	}

	private void updateTarget() {

		target.x = (float) (pos.x + (Math.cos(yaw) * Math.sin (pitch)));
		target.y = (float) (pos.y + (Math.sin(yaw) * Math.sin (pitch)));
		target.z = (float) (pos.z + Math.cos(pitch));
	}

	public void rotatePitch(float dpitch) {
		
		pitch = normalizePi(pitch + dpitch);
		
		updateTarget();
	}

	public void translate(float dlin, float dlat) {
		
		pos.x = (float) (pos.x + (dlin * Math.cos(yaw) * Math.sin (pitch)));
		pos.y = (float) (pos.y + (dlin * Math.sin(yaw) * Math.sin (pitch)));
		pos.z = (float) (pos.z + dlin * Math.cos(pitch));

		pos.x = (float) (pos.x + (dlat * Math.cos(yaw+(Math.PI/2.0f)) * Math.sin (pitch)));
		pos.y = (float) (pos.y + (dlat * Math.sin(yaw+(Math.PI/2.0f)) * Math.sin (pitch)));
		
		updateTarget();
	}
	
	public void setCameraPos(float x, float y, float z) {
		
		pos.x = x;
		pos.y = y;
		pos.z = z;
		
		yaw =(float) Math.PI;
		pitch = (float) -Math.PI/2.0f;
		
		updateTarget();
	}
}
