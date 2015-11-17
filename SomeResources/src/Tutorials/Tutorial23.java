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

/**
 *
 * @author Agustin
 */
public class Tutorial23 implements Drawable {

    private ShaderProgram shaderProgram;
    private final OpenGLHelper openGLHelper = new OpenGLHelper("Tutorial 23", new FPCameraControllerA(0, 0, 0));
    private int uniModel;
    private final SphereModel sphereModel = new SphereModel(10);

    public void run() {
        openGLHelper.initGL("VS_NormalDebug.vs", "FS_GoraudLight.fs");
        prepareBuffers();
        openGLHelper.run(this);
    }

    private void prepareBuffers() {
        shaderProgram = openGLHelper.getShaderProgram();

        // --------------------- VERTICES POSITIONS --------------------------//
        int vbo_v = sphereModel.createVerticesBuffer();

        int posAttrib = shaderProgram.getAttributeLocation("aVertexPosition");
        glEnableVertexAttribArray(posAttrib);
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        // ----------------------- NORMALS DATA -------------------------------//
        int vbo_n = sphereModel.createVerticesBuffer();

        int vertexNormalAttribute = shaderProgram.getAttributeLocation("aVertexNormal");
        glEnableVertexAttribArray(vertexNormalAttribute);
        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
        glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

        // ----------------------- ELEMENTS INDICES --------------------------//
        int ebo = sphereModel.createIndicesBuffer();

        uniModel = shaderProgram.getUniformLocation("model");

         }

    @Override
    public void draw() {
        /* Render a model */
        drawSomeModel();
    }

    private float angle;
    private static final float angularVelocity = 20.f;

    private void drawSomeModel() {
        /* Render X, Y, Z */
        angle += angularVelocity * openGLHelper.getDeltaTime();

        Matrix4f model = Matrix4f.rotate(angle, 0, 1f, 0);
        model = Matrix4f.translate(0, 0, -2.5f).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        glDrawElements(GL_TRIANGLES, sphereModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }

}
