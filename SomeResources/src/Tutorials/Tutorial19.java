/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tutorials;

import Utils.Drawable;
import Utils.OpenGLHelper;
import Utils.*;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
 
/**
 *
 * @author Agustin
 */
public class Tutorial19 implements Drawable
{
    private ShaderProgram shaderProgram;
    private final OpenGLHelper openGLHelper = new OpenGLHelper("Tutorial 19");
    private int uniModel;
    
    float[] vertices = new float[]{
        1.0f, 1.0f, 1.0f, // Top Right Of The Quad (Front)
        -1.0f, 1.0f, 1.0f, // Top Left Of The Quad (Front)
        -1.0f, -1.0f, 1.0f, // Bottom Left Of The Quad (Front)
        1.0f, -1.0f, 1.0f, // Bottom Right Of The Quad (Front)

        1.0f, 1.0f, -1.0f, // Top Right Of The Quad (Top)
        -1.0f, 1.0f, -1.0f, // Top Left Of The Quad (Top)
        -1.0f, 1.0f, 1.0f, // Bottom Left Of The Quad (Top)
        1.0f, 1.0f, 1.0f, // Bottom Right Of The Quad (Top)

        -1.0f, 1.0f, 1.0f, // Top Right Of The Quad (Left)
        -1.0f, 1.0f, -1.0f, // Top Left Of The Quad (Left)
        -1.0f, -1.0f, -1.0f, // Bottom Left Of The Quad (Left)
        -1.0f, -1.0f, 1.0f, // Bottom Right Of The Quad (Left)
        
        1.0f, -1.0f, -1.0f, // Bottom Left Of The Quad (Back)
        -1.0f, -1.0f, -1.0f, // Bottom Right Of The Quad (Back)
        -1.0f, 1.0f, -1.0f, // Top Right Of The Quad (Back)
        1.0f, 1.0f, -1.0f, // Top Left Of The Quad (Back)
        
        1.0f, -1.0f, 1.0f, // Top Right Of The Quad (Bottom)
        -1.0f, -1.0f, 1.0f, // Top Left Of The Quad (Bottom)
        -1.0f, -1.0f, -1.0f, // Bottom Left Of The Quad (Bottom)
        1.0f, -1.0f, -1.0f, // Bottom Right Of The Quad (Bottom)

        1.0f, 1.0f, -1.0f, // Top Right Of The Quad (Right)
        1.0f, 1.0f, 1.0f, // Top Left Of The Quad (Right)
        1.0f, -1.0f, 1.0f, // Bottom Left Of The Quad (Right)
        1.0f, -1.0f, -1.0f // Bottom Right Of The Quad (Right)
    };

    float[] colors = new float[]{
        0.9f, 0.9f, 0.9f, // Top Right Of The Quad (Front)
        0.9f, 0.9f, 0.9f, // Top Left Of The Quad (Front)
        0.9f, 0.9f, 0.9f, // Bottom Left Of The Quad (Front)
        0.9f, 0.9f, 0.9f, // Bottom Right Of The Quad (Front)

        1.0f, 1.0f, 1.0f, // Top Right Of The Quad (Top)
        1.0f, 1.0f, 1.0f, // Top Left Of The Quad (Top)
        1.0f, 1.0f, 1.0f, // Bottom Left Of The Quad (Top)
        1.0f, 1.0f, 1.0f, // Bottom Right Of The Quad (Top)

        0.7f, 0.7f, 0.7f, // Top Right Of The Quad (Left)
        0.7f, 0.7f, 0.7f, // Top Left Of The Quad (Left)
        0.7f, 0.7f, 0.7f, // Bottom Left Of The Quad (Left)
        0.7f, 0.7f, 0.7f, // Bottom Right Of The Quad (Left)

        0.7f, 0.7f, 0.7f, // Bottom Left Of The Quad (Back)
        0.7f, 0.7f, 0.7f, // Bottom Right Of The Quad (Back)
        0.7f, 0.7f, 0.7f, // Top Right Of The Quad (Back)
        0.7f, 0.7f, 0.7f, // Top Left Of The Quad (Back)

        0.5f, 0.5f, 0.5f, // Top Right Of The Quad (Bottom)
        0.5f, 0.5f, 0.5f, // Top Left Of The Quad (Bottom)
        0.5f, 0.5f, 0.5f, // Bottom Left Of The Quad (Bottom)
        0.5f, 0.5f, 0.5f, // Bottom Right Of The Quad (Bottom)

        0.9f, 0.9f, 0.9f, // Top Right Of The Quad (Right)
        0.9f, 0.9f, 0.9f, // Top Left Of The Quad (Right)
        0.9f, 0.9f, 0.9f, // Bottom Left Of The Quad (Right)
        0.9f, 0.9f, 0.9f  // Bottom Right Of The Quad (Right)
    };

    float[] textCoords = new float[]{
        1.0f, 1.0f, // Top Right Of The Quad (Front)
        0.0f, 1.0f, // Top Left Of The Quad (Front)
        0.0f, 0.0f, // Bottom Left Of The Quad (Front)
        1.0f, 0.0f, // Bottom Right Of The Quad (Front)

        1.0f, 1.0f, // Top Right Of The Quad (Top)
        0.0f, 1.0f, // Top Left Of The Quad (Top)
        0.0f, 0.0f, // Bottom Left Of The Quad (Top)
        1.0f, 0.0f, // Bottom Right Of The Quad (Top)

        1.0f, 1.0f, // Top Right Of The Quad (Left)
        0.0f, 1.0f, // Top Left Of The Quad (Left)
        0.0f, 0.0f, // Bottom Left Of The Quad (Left)
        1.0f, 0.0f, // Bottom Right Of The Quad (Left)

        1.0f, 1.0f, // Bottom Left Of The Quad (Back)
        0.0f, 1.0f, // Bottom Right Of The Quad (Back)
        0.0f, 0.0f, // Top Right Of The Quad (Back)
        1.0f, 0.0f, // Top Left Of The Quad (Back)

        1.0f, 1.0f, // Top Right Of The Quad (Bottom)
        0.0f, 1.0f, // Top Left Of The Quad (Bottom)
        0.0f, 0.0f, // Bottom Left Of The Quad (Bottom)
        1.0f, 0.0f, // Bottom Right Of The Quad (Bottom)

        1.0f, 1.0f, // Top Right Of The Quad (Right)
        0.0f, 1.0f, // Top Left Of The Quad (Right)
        0.0f, 0.0f, // Bottom Left Of The Quad (Right)
        1.0f, 0.0f, // Bottom Right Of The Quad (Right)
    };
    
    public void run() {
        openGLHelper.initGL("VS_Texture.vs", "FS_Texture.fs");
        prepareBuffers();
        openGLHelper.run(this);
    }

    private void prepareBuffers() {
        shaderProgram = openGLHelper.getShaderProgram();
        
        // --------------------- VERTICES POSITIONS --------------------------//
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        int vbo_v = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        int posAttrib = shaderProgram.getAttributeLocation("aVertexPosition");
        glEnableVertexAttribArray(posAttrib);
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);

        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        // ----------------------- COLORS DATA -------------------------------//
        
        FloatBuffer colorsBuffer = BufferUtils.createFloatBuffer(colors.length);
        colorsBuffer.put(colors).flip();

        int vbo_c = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_c);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
        
        int vertexColorAttribute = shaderProgram.getAttributeLocation("aVertexColor");
        glEnableVertexAttribArray(vertexColorAttribute);
        glBindBuffer(GL_ARRAY_BUFFER, vbo_c);
        glVertexAttribPointer(vertexColorAttribute, 3, GL_FLOAT, false, 0, 0);
        
        // ----------------------- TEXTURE COORDS ----------------------------//
        FloatBuffer textCoordsBuffer = BufferUtils.createFloatBuffer(textCoords.length);
        textCoordsBuffer.put(textCoords).flip();
        
        int vbo_t = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_t);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
    
        int texcoordAttribute = shaderProgram.getAttributeLocation("texcoord");
        glEnableVertexAttribArray(texcoordAttribute);
        glBindBuffer(GL_ARRAY_BUFFER, vbo_t);
        glVertexAttribPointer(texcoordAttribute, 2, GL_FLOAT, false, 0, 0);
       
        // ----------------------- TEXTURE IMAGE -----------------------------//
        Texture texture =  Texture.loadTexture("grass.png");
        int uniTex = shaderProgram.getUniformLocation("texImage");
        shaderProgram.setUniform(uniTex, 0);
        
        uniModel = shaderProgram.getUniformLocation("model");
    }

    @Override
    public void draw()
    {
        /* Render a model */
        drawSomeModel();
    }
    
    private float angle;
    private static final float angularVelocity = 30.f;
    
    private void drawSomeModel() {
        /* Render X, Y, Z */
        angle += angularVelocity*openGLHelper.getDeltaTime();
       
        Matrix4f model = Matrix4f.rotate(angle, 0.5f, 1f, 0); 
        model = Matrix4f.scale(3.0f, 3.0f, 3.0f).multiply(model);
        model = Matrix4f.translate(4, 5, 0).multiply(model);
        glUniformMatrix4(uniModel, false, model.getBuffer());
        glDrawArrays(GL_QUADS, 0, 4*6);
               
    }

}
