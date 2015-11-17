package practica3d_v2;

import Utils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Pista extends Dibujable{
    
    private final PlaneModel roadModel = new PlaneModel(5, 1);
    private int roadVao;
    
    public Pista (String ID, ShaderProgram shaderProgram){
        super(ID, shaderProgram);
            this.shaderProgram = shaderProgram;
            vbo_v = roadModel.createVerticesBuffer();
            vbo_n = roadModel.createNormalsBuffer();
            vbo_t = roadModel.createTextCoordsBuffer();
            ebo = roadModel.createIndicesBuffer();
            roadVao = defVao(roadVao);
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

            ebo = roadModel.createIndicesBuffer();
        glBindVertexArray(0);
        
        return Vao;
    }
    
    @Override
    public void draw(OpenGLHelper openGLHelper){ 
        
        angle += angularVelocity * openGLHelper.getDeltaTime();
        
        drawRoad(openGLHelper);
    }
    
    private float angle;
    private static final float angularVelocity = 30.f;
        
    private void drawRoad(OpenGLHelper openGLHelper) {
        shaderProgram.setUniform(uniTex1, 8);
        
        uKaAttribute = shaderProgram.getUniformLocation("Ka");
        uKdAttribute = shaderProgram.getUniformLocation("Kd");
        useTextures = shaderProgram.getUniformLocation("useTextures");
        uNMatrixAttribute = shaderProgram.getUniformLocation("uNMatrix");
        
        glBindVertexArray(roadVao);
                
        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 1.0f, 1.0f, 1.0f);
        glUniform1i(useTextures, 1);

        Matrix4f model = Matrix4f.scale(20.0f, 1.0f, 2.0f);
        model = Matrix4f.translate(10, 0, 0).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());
        // Enable polygon offset to resolve depth-fighting isuses
        glEnable(GL_POLYGON_OFFSET_FILL);
        glPolygonOffset(-2.0f, 4.0f);
        glDrawElements(GL_TRIANGLES, roadModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
        glDisable(GL_POLYGON_OFFSET_FILL);
    }
}
