/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tutorials;

import Utils.SphereModel;
import Utils.Drawable;
import Utils.OpenGLHelper;
import Utils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 *
 * @author Agustin
 */
public class Tutorial27 implements Drawable {

    private ShaderProgram shaderProgram;
    private final OpenGLHelper openGLHelper = new OpenGLHelper("Tutorial 27", new FPCameraControllerA(0, 0, -6));
    private int uniModel;
    private int uNMatrixAttribute;

    private final SphereModel sphereModel = new SphereModel(20);
    private final CubeModel cubeModel = new CubeModel();

    private int sphereVao;
    private int cubeVao;
    private int uKdAttribute;
    private int uKaAttribute;
    
    public void run() {
        openGLHelper.initGL("VS_ADS_PhongLight.vs", "FS_ADS_PhongLight.fs");
        prepareBuffers();
        initLights();
        openGLHelper.run(this);
    }

    private void prepareBuffers() {
        shaderProgram = openGLHelper.getShaderProgram();
        int posAttrib = shaderProgram.getAttributeLocation("aVertexPosition");

        int vertexNormalAttribute = shaderProgram.getAttributeLocation("aVertexNormal");

        // --------------------- SPHERE MODEL  -------------------------------//
        sphereVao = glGenVertexArrays();
        glBindVertexArray(sphereVao);

        int vbo_v = sphereModel.createVerticesBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        int vbo_n = sphereModel.createNormalsBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
        glEnableVertexAttribArray(vertexNormalAttribute);
        glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

        int ebo = sphereModel.createIndicesBuffer();
        glBindVertexArray(0);

        // --------------------- CUBE MODEL  -------------------------------//
        cubeVao = glGenVertexArrays();
        glBindVertexArray(cubeVao);

        vbo_v = cubeModel.createVerticesBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        vbo_n = cubeModel.createVerticesBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
        glEnableVertexAttribArray(vertexNormalAttribute);
        glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

        ebo = cubeModel.createIndicesBuffer();
        glBindVertexArray(0);

        uniModel = shaderProgram.getUniformLocation("model");

        uNMatrixAttribute = shaderProgram.getUniformLocation("uNMatrix");
    }

    private void initLights() {
        int uLightPositionAttribute = shaderProgram.getUniformLocation("LightPosition");
        int uLightIntensityAttribute = shaderProgram.getUniformLocation("LightIntensity");

        uKaAttribute = shaderProgram.getUniformLocation("Ka");
        uKdAttribute = shaderProgram.getUniformLocation("Kd");
        int uKsAttribute = shaderProgram.getUniformLocation("Ks");

        int uShininessAttribute = shaderProgram.getUniformLocation("Shininess");

        glUniform3f(uLightPositionAttribute, 2.0f, 4.0f, 2.0f);
        glUniform3f(uLightIntensityAttribute, 0.4f, 0.4f, 0.4f);

        glUniform3f(uKaAttribute, 0.2f, 0.8f, 0.0f);
        glUniform3f(uKdAttribute, 0.2f, 0.8f, 0.0f);
        glUniform3f(uKsAttribute, 0.9f, 0.9f, 0.9f);

        glUniform1f(uShininessAttribute, 18.0f);
    }

    @Override
    public void draw() {
        /* Render a model */
        drawSomeModel1();
        drawSomeModel2();
        drawSomeModel3();
        drawSomeModel4();
        drawSomeModel5();
    }

    private void drawSomeModel1() {
        /* Render X, Y, Z */
        glBindVertexArray(sphereVao);

        Matrix4f model = Matrix4f.translate(0, 0, -2.2f);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glUniform3f(uKaAttribute, 0.8f, 0.8f, 0.0f);
        glUniform3f(uKdAttribute, 0.8f, 0.8f, 0.0f);
        glDrawElements(GL_TRIANGLES, sphereModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }

    private void drawSomeModel2() {
        /* Render X, Y, Z */
        glBindVertexArray(sphereVao);

        Matrix4f model = Matrix4f.translate(-4, 2, -2.2f);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glUniform3f(uKaAttribute, 0.2f, 0.8f, 0.5f);
        glUniform3f(uKdAttribute, 0.2f, 0.8f, 0.5f);
        glDrawElements(GL_TRIANGLES, sphereModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }

    private void drawSomeModel3() {
        /* Render X, Y, Z */
        glBindVertexArray(cubeVao);

        Matrix4f model = Matrix4f.translate(-2, 2, -6.2f);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());
        
        glUniform3f(uKaAttribute, 0.8f, 0.1f, 0.2f);
        glUniform3f(uKdAttribute, 0.8f, 0.1f, 0.2f);
        glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }

    private void drawSomeModel4() {
        /* Render X, Y, Z */
        glBindVertexArray(cubeVao);

        Matrix4f model = Matrix4f.translate(2, -4, -4.2f);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glUniform3f(uKaAttribute, 0.9f, 0.2f, 0.8f);
        glUniform3f(uKdAttribute, 0.9f, 0.2f, 0.8f);
        glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }

    private void drawSomeModel5() {
        /* Render X, Y, Z */
        glBindVertexArray(cubeVao);

        Matrix4f model = Matrix4f.translate(-2, -3, -4.2f);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glUniform3f(uKaAttribute, 0.2f, 0.8f, 0.0f);
        glUniform3f(uKdAttribute, 0.2f, 0.8f, 0.0f);
        glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }
}
