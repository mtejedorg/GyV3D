/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3d_v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ATCDisplay.ATCDFlight;
import Utils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Aeropuerto extends Dibujable{    
    
    private final PlaneModel planeModel = new PlaneModel(5, 5);
    private final CubeModel cubeModel = new CubeModel();
    private int planeVao, cubeVao;
    ArrayList<Dibujable> controlled = new ArrayList<Dibujable>();
    
    private int vbo_v_f, vbo_n_f, vbo_t_f, ebo_f, vbo_v_w, vbo_n_w, vbo_t_w, ebo_w;
	private static Ice.Communicator ic = null;
	private static ATCDisplay.AirportInterfacePrx airportI = null;
	
    private final ModelLoader acr170 = new ModelLoader("src/Batwing.obj");
    
    public Aeropuerto (String ID, ShaderProgram shaderProgram){
        super(ID, shaderProgram);
            this.shaderProgram = shaderProgram;
            vbo_v_f = planeModel.createVerticesBuffer();
            vbo_n_f = planeModel.createNormalsBuffer();
            vbo_t_f = planeModel.createTextCoordsBuffer();
            ebo_f = planeModel.createIndicesBuffer();
            planeVao = defFloorVao(planeVao);
            
            vbo_v_w = cubeModel.createVerticesBuffer();
            vbo_n_w = cubeModel.createNormalsBuffer();
            vbo_t_w = cubeModel.createTextCoordsBuffer();
            ebo_w = cubeModel.createIndicesBuffer();
            cubeVao = defcubeVao(cubeVao);
            
            //-------INIT Simulation-----------
    		String[] args1 = {new String("")};
    		ic = Ice.Util.initialize(args1);
    		Ice.ObjectPrx base = null;		
    		
    		base = ic.stringToProxy("AirportInterface:default -p 10000");
    		airportI = ATCDisplay.AirportInterfacePrxHelper.checkedCast(base);
    }
    
    protected int defFloorVao(int Vao){
        int posAttrib = shaderProgram.getAttributeLocation("aVertexPosition");
        int vertexNormalAttribute = shaderProgram.getAttributeLocation("aVertexNormal");
        int texCoordsAttribute = shaderProgram.getAttributeLocation("aVertexTexCoord");
        
        Vao = glGenVertexArrays();
        glBindVertexArray(Vao);
            uniTex1 = shaderProgram.getUniformLocation("Texture1");

            glBindBuffer(GL_ARRAY_BUFFER, vbo_v_f);
            glEnableVertexAttribArray(posAttrib);
            glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, vbo_n_f);
            glEnableVertexAttribArray(vertexNormalAttribute);
            glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, vbo_t_f);
            glEnableVertexAttribArray(texCoordsAttribute);
            glVertexAttribPointer(texCoordsAttribute, 2, GL_FLOAT, false, 0, 0);

            uniModel = shaderProgram.getUniformLocation("model");
            
            ebo_f = planeModel.createIndicesBuffer();
        glBindVertexArray(0);
        
        return Vao;
    }
    
    private int defcubeVao(int Vao){
        int posAttrib = shaderProgram.getAttributeLocation("aVertexPosition");
        int vertexNormalAttribute = shaderProgram.getAttributeLocation("aVertexNormal");
        int texCoordsAttribute = shaderProgram.getAttributeLocation("aVertexTexCoord");
        
        Vao = glGenVertexArrays();
        glBindVertexArray(Vao);
            uniTex1 = shaderProgram.getUniformLocation("Texture1");

            glBindBuffer(GL_ARRAY_BUFFER, vbo_v_w);
            glEnableVertexAttribArray(posAttrib);
            glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, vbo_n_w);
            glEnableVertexAttribArray(vertexNormalAttribute);
            glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, vbo_t_w);
            glEnableVertexAttribArray(texCoordsAttribute);
            glVertexAttribPointer(texCoordsAttribute, 2, GL_FLOAT, false, 0, 0);

            uniModel = shaderProgram.getUniformLocation("model");

            ebo_w = cubeModel.createIndicesBuffer();
        glBindVertexArray(0);
        
        return Vao;
    }
    
    private Map<String, Dibujable> elements = new HashMap<String, Dibujable>();
    
    public void add(Dibujable element){
        elements.put(element.ID, element);
    }
    
    public void del(String ID){
        elements.remove(ID);
    }
    
    public Dibujable get(String ID){
        return elements.get(ID);
    }
   
    public void drawall(OpenGLHelper openGLHelper){
    	//Get controlled objects positions
    	List<ATCDFlight> flights = airportI.getFlights();
		Iterator<ATCDFlight> itf = flights.iterator();
		int i = 0;
		while(itf.hasNext()){
			if (i == controlled.size()){
				break;
			}
			ATCDFlight flightdata = itf.next();
			controlled.get(i).setPos(flightdata.pos.x/1000.0f, flightdata.pos.z/1000.0f, flightdata.pos.y/1000.0f);
			controlled.get(i).setRotation(0.0f, 0.0f, 1f, 0);
			controlled.get(i).setScale(0.2f, 0.2f, 0.2f);
			
			System.out.println("("+(flightdata.pos.x/1000.0f)+", "+flightdata.pos.z/1000.0f+", "+(flightdata.pos.y/1000.0f)+")");
			System.out.println(controlled.get(i).ID);
			System.out.println("bearing: " + flightdata.bearing);
			System.out.println("inclination: " + flightdata.bearing);
			controlled.get(i).setRotation(-flightdata.bearing, flightdata.inclination, 0.0f);
			i = i+1;
		}
		
        for (Dibujable obj : elements.values()){
            draw(openGLHelper);
            obj.draw(openGLHelper);
        }
    }
    
    @Override
    public void draw(OpenGLHelper openGLHelper){
        angle += angularVelocity * openGLHelper.getDeltaTime();

    	drawFloor(openGLHelper);
    	drawWorld(openGLHelper);
    	drawCube1(openGLHelper);
    	drawCube2(openGLHelper);
    }
    
    public void drawFloor(OpenGLHelper openGLHelper) {
        uKaAttribute = shaderProgram.getUniformLocation("Ka");
        uKdAttribute = shaderProgram.getUniformLocation("Kd");
        useTextures = shaderProgram.getUniformLocation("useTextures");
        uNMatrixAttribute = shaderProgram.getUniformLocation("uNMatrix");
        
        shaderProgram.setUniform(uniTex1, 7);

        glBindVertexArray(planeVao);
        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 1.0f, 1.0f, 1.0f);
        glUniform1i(useTextures, 3);

        Matrix4f model = Matrix4f.scale(20.0f, 1.0f, 20.0f);
        model = Matrix4f.translate(10, 0, 0).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glDrawElements(GL_TRIANGLES, planeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }
    
    public void drawWorld(OpenGLHelper openGLHelper) {
        uKaAttribute = shaderProgram.getUniformLocation("Ka");
        uKdAttribute = shaderProgram.getUniformLocation("Kd");
        useTextures = shaderProgram.getUniformLocation("useTextures");
        uNMatrixAttribute = shaderProgram.getUniformLocation("uNMatrix");
        shaderProgram.setUniform(uniTex1, 7);

        glBindVertexArray(cubeVao);
        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 0.8f, 0.8f, 0.8f);
        glUniform1i(useTextures, 1);

        Matrix4f model = Matrix4f.scale(200.0f, 200.0f, 200.0f);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }
    
    private float angle;
    private static final float angularVelocity = 30.f;
        
    private void drawCube1(OpenGLHelper openGLHelper) {
        uKaAttribute = shaderProgram.getUniformLocation("Ka");
        uKdAttribute = shaderProgram.getUniformLocation("Kd");
        useTextures = shaderProgram.getUniformLocation("useTextures");
        uNMatrixAttribute = shaderProgram.getUniformLocation("uNMatrix");
        
        shaderProgram.setUniform(uniTex1, 1);
        glBindVertexArray(cubeVao);
        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 0.8f, 0.8f, 0.8f);
        glUniform1i(useTextures, 1);

        Matrix4f model = Matrix4f.rotate(angle, 0.5f, 1f, 0);
        model = Matrix4f.scale(2.0f, 2.0f, 2.0f).multiply(model);
        model = Matrix4f.translate(-1*angle/1000 -1, 15*(float)Math.cos(angle/1000) + 15, -3*angle/1000 - 3).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }

    private void drawCube2(OpenGLHelper openGLHelper) {
        uKaAttribute = shaderProgram.getUniformLocation("Ka");
        uKdAttribute = shaderProgram.getUniformLocation("Kd");
        useTextures = shaderProgram.getUniformLocation("useTextures");
        uNMatrixAttribute = shaderProgram.getUniformLocation("uNMatrix");
        shaderProgram.setUniform(uniTex1, 8);

        glBindVertexArray(cubeVao);
        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 0.8f, 0.8f, 0.8f);
        glUniform1i(useTextures, 1);

        Matrix4f model = Matrix4f.rotate(angle/2, 0.5f, 1f, 0);
        model = Matrix4f.scale(2.0f, 2.0f, 2.0f).multiply(model);
        model = Matrix4f.translate(6*angle/2000 + 6, 10*angle/1000 + 10, angle/500).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }
}
