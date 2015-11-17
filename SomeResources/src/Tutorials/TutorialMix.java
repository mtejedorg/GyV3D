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
public class TutorialMix implements Drawable {

    private ShaderProgram shaderProgram;
    private final OpenGLHelper openGLHelper = new OpenGLHelper("Tutorial Mix", new FPCameraControllerA(-5, -5, -15));
    private int uniModel;
    private int uNMatrixAttribute;

    private final CubeModel cubeModel = new CubeModel();
    private final SphereModel sphereModel = new SphereModel();
    private final PlaneModel planeModel = new PlaneModel(5, 5);
    private final PlaneModel roadModel = new PlaneModel(5, 1);

    private int cubeVao, axiesVao, sphereVao, planeVao, roadVao;
    private int uKdAttribute;
    private int uKaAttribute;
    private int useTextures;
    private int uniTex1, uniTex2, uniTex3;

    private Texture fieldstoneTexture, stoneTexture, nicegrasstexture, roadTexture;

    public void run() {
        openGLHelper.initGL("VS_ADS_Texture.vs", "FS_ADS_Texture4.fs");
        prepareModels();
        initLights();
        openGLHelper.run(this);
    }

    private void prepareModels() {
        // --------------------- AXIES MODEL  -------------------------------//
        shaderProgram = openGLHelper.getShaderProgram();
        int posAttrib = shaderProgram.getAttributeLocation("aVertexPosition");
        int vertexNormalAttribute = shaderProgram.getAttributeLocation("aVertexNormal");
        int texCoordsAttribute = shaderProgram.getAttributeLocation("aVertexTexCoord");

        axiesVao = glGenVertexArrays();
        glBindVertexArray(axiesVao);

        int vbo_v = cubeModel.createVerticesBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        int vbo_n = cubeModel.createNormalsBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
        glEnableVertexAttribArray(vertexNormalAttribute);
        glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

        int ebo = cubeModel.createIndicesBuffer();
        glBindVertexArray(0);

        uniModel = shaderProgram.getUniformLocation("model");
        uNMatrixAttribute = shaderProgram.getUniformLocation("uNMatrix");
        useTextures = shaderProgram.getUniformLocation("useTextures");

        // ----------------------- CUBE MODEL -----------------------------//
        cubeVao = glGenVertexArrays();
        glBindVertexArray(cubeVao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
        glEnableVertexAttribArray(vertexNormalAttribute);
        glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

        int vbo_t = cubeModel.createTextCoordsBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_t);
        glEnableVertexAttribArray(texCoordsAttribute);
        glVertexAttribPointer(texCoordsAttribute, 2, GL_FLOAT, false, 0, 0);

        ebo = cubeModel.createIndicesBuffer();

        glActiveTexture(GL_TEXTURE0);
        nicegrasstexture = Texture.loadTexture("nicegrass.jpg");
        uniTex1 = shaderProgram.getUniformLocation("Texture1");
        shaderProgram.setUniform(uniTex1, 0);

        glActiveTexture(GL_TEXTURE1);
        Texture texture = Texture.loadTexture("baserock.jpg");
        uniTex2 = shaderProgram.getUniformLocation("Texture2");
        shaderProgram.setUniform(uniTex2, 1);

        glActiveTexture(GL_TEXTURE2);
        texture = Texture.loadTexture("darkrockalpha.png");
        uniTex3 = shaderProgram.getUniformLocation("Texture3");
        shaderProgram.setUniform(uniTex3, 2);

        glBindVertexArray(0);
        
        glActiveTexture(GL_TEXTURE3);
        fieldstoneTexture = Texture.loadTexture("fieldstone.jpg");
        
        glActiveTexture(GL_TEXTURE4);
        stoneTexture = Texture.loadTexture("stone-128px.jpg");
        
        glActiveTexture(GL_TEXTURE5);
        roadTexture = Texture.loadTexture("stone-256px.jpg");
        
        // ----------------------- SPHERE MODEL -----------------------------//
        sphereVao = glGenVertexArrays();
        glBindVertexArray(sphereVao);

        vbo_v = sphereModel.createVerticesBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        vbo_n = sphereModel.createNormalsBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
        glEnableVertexAttribArray(vertexNormalAttribute);
        glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

        ebo = sphereModel.createIndicesBuffer();
        glBindVertexArray(0);
        
        // ----------------------- PLANE MODEL -----------------------------//
        planeVao = glGenVertexArrays();
        glBindVertexArray(planeVao);

        vbo_v = planeModel.createVerticesBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        vbo_n = planeModel.createNormalsBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
        glEnableVertexAttribArray(vertexNormalAttribute);
        glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

        vbo_t = planeModel.createTextCoordsBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_t);
        glEnableVertexAttribArray(texCoordsAttribute);
        glVertexAttribPointer(texCoordsAttribute, 2, GL_FLOAT, false, 0, 0);

        ebo = planeModel.createIndicesBuffer();
        glBindVertexArray(0);
        
        // ----------------------- ROAD MODEL -----------------------------//
        roadVao = glGenVertexArrays();
        glBindVertexArray(roadVao);

        vbo_v = roadModel.createVerticesBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        vbo_n = roadModel.createNormalsBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_n);
        glEnableVertexAttribArray(vertexNormalAttribute);
        glVertexAttribPointer(vertexNormalAttribute, 3, GL_FLOAT, false, 0, 0);

        vbo_t = roadModel.createTextCoordsBuffer();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_t);
        glEnableVertexAttribArray(texCoordsAttribute);
        glVertexAttribPointer(texCoordsAttribute, 2, GL_FLOAT, false, 0, 0);

        ebo = roadModel.createIndicesBuffer();
        glBindVertexArray(0);
        
    }

    private void initLights() {
        int uLightPositionAttribute = shaderProgram.getUniformLocation("LightPosition");
        int uLightIntensityAttribute = shaderProgram.getUniformLocation("LightIntensity");

        uKaAttribute = shaderProgram.getUniformLocation("Ka");
        uKdAttribute = shaderProgram.getUniformLocation("Kd");
        int uKsAttribute = shaderProgram.getUniformLocation("Ks");

        int uShininessAttribute = shaderProgram.getUniformLocation("Shininess");

        glUniform4f(uLightPositionAttribute, 2.0f, 4.0f, 2.0f, 1.0f);
        glUniform3f(uLightIntensityAttribute, 0.7f, 0.7f, 0.7f);

        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 0.8f, 0.8f, 0.8f);
        glUniform3f(uKsAttribute, 0.2f, 0.2f, 0.2f);

        glUniform1f(uShininessAttribute, 18.0f);
    }

    @Override
    public void draw() {
        angle += angularVelocity * openGLHelper.getDeltaTime();
        
        drawAxies();
        drawFloor();
        drawRoad();
        drawModel1();
        drawModel2();
        drawModel3();
    }

    private float angle;
    private static final float angularVelocity = 10.f;

    private void drawModel1() {
        shaderProgram.setUniform(uniTex1, fieldstoneTexture.getId() -1);
        glBindVertexArray(cubeVao);
        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 0.8f, 0.8f, 0.8f);
        glUniform1i(useTextures, 1);

        Matrix4f model = Matrix4f.rotate(angle, 0.5f, 1f, 0);
        model = Matrix4f.scale(2.0f, 2.0f, 2.0f).multiply(model);
        model = Matrix4f.translate(-1, 4, -3).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }

    private void drawModel2() {
        shaderProgram.setUniform(uniTex1, stoneTexture.getId() -1);

        glBindVertexArray(cubeVao);
        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 0.8f, 0.8f, 0.8f);
        glUniform1i(useTextures, 1);

        Matrix4f model = Matrix4f.rotate(angle, 0.5f, 1f, 0);
        model = Matrix4f.scale(2.0f, 2.0f, 2.0f).multiply(model);
        model = Matrix4f.translate(6, 3, 0).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }

    private void drawModel3() {
        glBindVertexArray(sphereVao);
        glUniform3f(uKaAttribute, 0.8f, 0.2f, 0.5f);
        glUniform3f(uKdAttribute, 0.8f, 0.2f, 0.5f);
        glUniform1i(useTextures, 0);

        Matrix4f model = Matrix4f.scale(6.0f, 1.0f, 1.0f);
        model = Matrix4f.translate(5.0f*(1.2f + (float)Math.cos(angle/10)), 4, 3).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());

        Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
        normalMatrix.transpose();
        glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());

        glDrawElements(GL_TRIANGLES, sphereModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
    }

    private void drawAxies() {
        glBindVertexArray(axiesVao);
        glUniform1i(useTextures, 0);

        for (int i = 0; i < 20; i++) {
            Matrix4f model = Matrix4f.scale(0.35f, 0.15f, 0.15f);
            model = Matrix4f.translate(i, 0, 0).multiply(model);
            glUniformMatrix4(uniModel, false, model.getBuffer());
            Matrix3f normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
            normalMatrix.transpose();
            glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());
            glUniform3f(uKaAttribute, 0.9f, 0.0f, 0.0f);
            glUniform3f(uKdAttribute, 0.9f, 0.0f, 0.0f);
            glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);

            model = Matrix4f.scale(0.15f, 0.35f, 0.15f);
            model = Matrix4f.translate(0, i, 0).multiply(model);
            glUniformMatrix4(uniModel, false, model.getBuffer());
            normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
            normalMatrix.transpose();
            glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());
            glUniform3f(uKaAttribute, 0.0f, 0.9f, 0.0f);
            glUniform3f(uKdAttribute, 0.0f, 0.9f, 0.0f);
            glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);

            model = Matrix4f.scale(0.15f, 0.15f, 0.35f);
            model = Matrix4f.translate(0, 0, 0 + i).multiply(model);
            glUniformMatrix4(uniModel, false, model.getBuffer());
            normalMatrix = model.multiply(openGLHelper.getViewMatrix()).toMatrix3f().invert();
            normalMatrix.transpose();
            glUniformMatrix3(uNMatrixAttribute, false, normalMatrix.getBuffer());
            glUniform3f(uKaAttribute, 0.0f, 0.0f, 0.9f);
            glUniform3f(uKdAttribute, 0.0f, 0.0f, 0.9f);
            glDrawElements(GL_TRIANGLES, cubeModel.getIndicesLength(), GL_UNSIGNED_INT, 0);
        }
    }
     private void drawFloor() {
        
        shaderProgram.setUniform(uniTex1, nicegrasstexture.getId() -1);
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
     
    private void drawRoad() {
        
        shaderProgram.setUniform(uniTex1, roadTexture.getId() -1);
        glBindVertexArray(roadVao);
        glUniform3f(uKaAttribute, 1.0f, 1.0f, 1.0f);
        glUniform3f(uKdAttribute, 1.0f, 1.0f, 1.0f);
        glUniform1i(useTextures, 1);

        Matrix4f model = Matrix4f.scale(10.0f, 1.0f, 2.0f);
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
