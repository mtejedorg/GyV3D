package practica3d_v2;

import Utils.*;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Avion extends Dibujable{
    
    private final ModelLoader acr170 = new ModelLoader("src/Batwing.obj");
    private int acr170Vao;
    
    public Avion (String ID, ShaderProgram shaderProgram){
        super(ID, shaderProgram);
            this.shaderProgram = shaderProgram;
            this.useTextures = shaderProgram.getUniformLocation("useTextures");
            acr170.loadElements();
            vbo_v = acr170.createVerticesBuffer();
            vbo_n = acr170.createNormalsBuffer();
            vbo_t = acr170.createTextCoordsBuffer();
            acr170Vao = defVao(acr170Vao);
            x = y = z = 0.0f;
    }
    
    private int defVao(int Vao){
        int posAttrib = shaderProgram.getAttributeLocation("aVertexPosition");
        int vertexNormalAttribute = shaderProgram.getAttributeLocation("aVertexNormal");
        int texCoordsAttribute = shaderProgram.getAttributeLocation("aVertexTexCoord");
        Vao = glGenVertexArrays();
        
        glBindVertexArray(Vao);
            uniTex1 = shaderProgram.getUniformLocation("Texture1");
            
            glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
            glEnableVertexAttribArray(posAttrib);
            glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
            glEnableVertexAttribArray(vertexNormalAttribute);
            glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);
            
            glBindBuffer(GL_ARRAY_BUFFER, vbo_t);
            glEnableVertexAttribArray(texCoordsAttribute);
            glVertexAttribPointer(texCoordsAttribute, 2, GL_FLOAT, false, 0, 0);

            uniModel = shaderProgram.getUniformLocation("model");

        glBindVertexArray(0);
        
        return Vao;
    }
    
    @Override
    public void draw(OpenGLHelper openGLHelper){  
        angle += angularVelocity * openGLHelper.getDeltaTime();
        drawarc170(openGLHelper);
    }
    
    private float angle;
    private static final float angularVelocity = 30.f;
        
    private void drawarc170(OpenGLHelper openGLHelper) {
    	shaderProgram.setUniform(uniTex1, 6);
        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 1.0f, 1.0f, 1.0f);
        glUniform1i(useTextures, 1);
        glBindVertexArray(acr170Vao);
		  
        Matrix4f model = Matrix4f.scale(0.01f, 0.01f, 0.01f);
        model = Matrix4f.rotate(270, 1f, 0, 0).multiply(model);
        model = Matrix4f.rotate(90, 0, 1f, 0).multiply(model);
        model = Matrix4f.rotate(inclination, 0, 0, 1f).multiply(model);
        model = Matrix4f.rotate(bearing, 0, 1f, 0).multiply(model);
        model = Matrix4f.translate(x,y,z).multiply(model);
        //model = Matrix4f.translate(5.0f*(1.2f + (float)Math.cos(angle/10)), 4, 3).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());
        
        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());
	       
        glDrawArrays(GL11.GL_TRIANGLES, 0, acr170.getNumVertices()*3);
    }
}
