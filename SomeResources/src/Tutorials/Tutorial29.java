/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tutorials;

import Utils.Drawable;
import Utils.OpenGLHelper;
import Utils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 *
 * @author Agustin
 */
public class Tutorial29 implements Drawable {

    private ShaderProgram shaderProgram;
    private final OpenGLHelper openGLHelper = new OpenGLHelper("Tutorial 29", new FPCameraControllerA(0, 0, -6));
    private int uniModel;
    private int uNMatrixAttribute;

    private final CubeModel cubeModel = new CubeModel();

    private int cubeVao;
    private int uKdAttribute;
    private int uKaAttribute;

    public void run() {
        openGLHelper.initGL("VS_ADS_Texture.vs", "FS_ADS_Texture2.fs");
        prepareBuffers();
        initLights();
        openGLHelper.run(this);
    }

    private void prepareBuffers() {
        shaderProgram = openGLHelper.getShaderProgram();
        int posAttrib = shaderProgram.getAttributeLocation("aVertexPosition");
        int vertexNormalAttribute = shaderProgram.getAttributeLocation("aVertexNormal");
        int textCoordsAttribute = shaderProgram.getAttributeLocation("aVertexTexCoord");

        // --------------------- CUBE MODEL  -------------------------------//
        cubeVao = glGenVertexArrays();
        glBindVertexArray(cubeVao);

        int vbo_v = cubeModel.createVerticesBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        int vbo_n = cubeModel.createNormalsBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
        glEnableVertexAttribArray(vertexNormalAttribute);
        glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

        int vbo_t = cubeModel.createTextCoordsBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_t);
        glEnableVertexAttribArray(textCoordsAttribute);
        glVertexAttribPointer(textCoordsAttribute, 2, GL_FLOAT, false, 0, 0);

        int ebo = cubeModel.createIndicesBuffer();
        glBindVertexArray(0);

        uniModel = shaderProgram.getUniformLocation("model");

        // ----------------------- TEXTURE IMAGE -----------------------------//
        glActiveTexture(GL_TEXTURE0);
        Texture texture = Texture.loadTexture("fieldstone.jpg");
        int uniTex1 = shaderProgram.getUniformLocation("Texture1");
        shaderProgram.setUniform(uniTex1, 0);
        
        glActiveTexture(GL_TEXTURE1);
        Texture texture2 = Texture.loadTexture("webgl_logo-512px.png");
        int uniTex2 = shaderProgram.getUniformLocation("Texture2");
        shaderProgram.setUniform(uniTex2, 1);

        uNMatrixAttribute = shaderProgram.getUniformLocation("uNMatrix");
    }

    private void initLights() {
        int uLightPositionAttribute = shaderProgram.getUniformLocation("LightPosition");
        int uLightIntensityAttribute = shaderProgram.getUniformLocation("LightIntensity");

        uKaAttribute = shaderProgram.getUniformLocation("Ka");
        uKdAttribute = shaderProgram.getUniformLocation("Kd");
        int uKsAttribute = shaderProgram.getUniformLocation("Ks");

        int uShininessAttribute = shaderProgram.getUniformLocation("Shininess");

        glUniform4f(uLightPositionAttribute, 2.0f, 4.0f, 2.0f, 1.0f);
        glUniform3f(uLightIntensityAttribute, 0.4f, 0.4f, 0.4f);

        glUniform3f(uKaAttribute, 0.8f, 0.8f, 0.8f);
        glUniform3f(uKdAttribute, 0.8f, 0.8f, 0.8f);
        glUniform3f(uKsAttribute, 0.9f, 0.9f, 0.9f);

        glUniform1f(uShininessAttribute, 18.0f);
    }

    @Override
    public void draw() {
        /* Render a model */
        drawSomeModel();
    }

    private float angle;
    private static final float angularVelocity = 10.f;

    private void drawSomeModel() {
        glBindVertexArray(cubeVao);

        angle += angularVelocity * openGLHelper.getDeltaTime();

        Matrix4f model = Matrix4f.rotate(angle, 0.5f, 1f, 0);
        //model = Matrix4f.scale(2.0f, 2.0f, 2.0f).multiply(model);
        //model = Matrix4f.translate(-1, -1, -3).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }
}
