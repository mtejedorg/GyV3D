package practica3d_v2;

import Utils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class TorreControl extends Dibujable{
    
    private final CubeModel cubeModel = new CubeModel();
    private int cubeVao;
    
    public TorreControl (String ID, ShaderProgram shaderProgram){
        super(ID, shaderProgram);
            this.shaderProgram = shaderProgram;
            vbo_v = cubeModel.createVerticesBuffer();
            vbo_n = cubeModel.createNormalsBuffer();
            vbo_t = cubeModel.createTextCoordsBuffer();
            ebo = cubeModel.createIndicesBuffer();
            cubeVao = defVao(cubeVao);
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

            ebo = cubeModel.createIndicesBuffer();
        glBindVertexArray(0);
        
        return Vao;
    }
    
    @Override
    public void draw(OpenGLHelper openGLHelper){        
        shaderProgram = openGLHelper.getShaderProgram();        
        drawMonolith(openGLHelper);
    }
    
    private void drawMonolith(OpenGLHelper openGLHelper) {
        uKaAttribute = shaderProgram.getUniformLocation("Ka");
        uKdAttribute = shaderProgram.getUniformLocation("Kd");
        useTextures = shaderProgram.getUniformLocation("useTextures");
        uNMatrixAttribute = shaderProgram.getUniformLocation("uNMatrix");
        shaderProgram.setUniform(uniTex1, 10);

        glBindVertexArray(cubeVao);
        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 0.8f, 0.8f, 0.8f);
        glUniform1i(useTextures, 1);

        Matrix4f model = Matrix4f.rotate(30, 0, 1.0f, 0);
        model = Matrix4f.scale(2.0f, 5.0f, 2.0f).multiply(model);
        model = Matrix4f.translate(-5.0f, 5.0f, -5.0f).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }
}
