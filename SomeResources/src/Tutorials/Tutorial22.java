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
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
 
/**
 *
 * @author Agustin
 */
public class Tutorial22 implements Drawable
{
    private ShaderProgram shaderProgram;
    private final OpenGLHelper openGLHelper = new OpenGLHelper("Tutorial 22");
    private int uniModel;
    
    float[] vertices = new float[]{
         1.0f,  1.0f, 1.0f,  // 0 - Top Right Of The Quad (Front)
        -1.0f,  1.0f, 1.0f,  // 1 - Top Left Of The Quad (Front)
        -1.0f, -1.0f, 1.0f,  // 2 - Bottom Left Of The Quad (Front)
         1.0f, -1.0f, 1.0f,  // 3 - Bottom Right Of The Quad (Front)

         1.0f,  1.0f, -1.0f, // 4 - Top Right Of The Quad (Top)
        -1.0f,  1.0f, -1.0f, // 5 - Top Left Of The Quad (Top)
        -1.0f,  1.0f,  1.0f, // 6 - Bottom Left Of The Quad (Top)
         1.0f,  1.0f,  1.0f, // 7 - Bottom Right Of The Quad (Top)

         -1.0f, 1.0f,  1.0f, // 8 - Top Right Of The Quad (Left)
        -1.0f,  1.0f, -1.0f, // 9 - Top Left Of The Quad (Left)
        -1.0f, -1.0f, -1.0f, // 10 - Bottom Left Of The Quad (Left)
        -1.0f, -1.0f,  1.0f, // 11 - Bottom Right Of The Quad (Left)
        
         1.0f, -1.0f, -1.0f, // 12 - Bottom Left Of The Quad (Back)
        -1.0f, -1.0f, -1.0f, // 13 - Bottom Right Of The Quad (Back)
        -1.0f,  1.0f, -1.0f, // 14 - Top Right Of The Quad (Back)
         1.0f,  1.0f, -1.0f, // 15 - Top Left Of The Quad (Back)
        
         1.0f, -1.0f,  1.0f, // 16 - Top Right Of The Quad (Bottom)
        -1.0f, -1.0f,  1.0f, // 17 - Top Left Of The Quad (Bottom)
        -1.0f, -1.0f, -1.0f, // 18 - Bottom Left Of The Quad (Bottom)
         1.0f, -1.0f, -1.0f, // 19 - Bottom Right Of The Quad (Bottom)

         1.0f,  1.0f, -1.0f, // 20 - Top Right Of The Quad (Right)
         1.0f,  1.0f,  1.0f, // 21 - Top Left Of The Quad (Right)
         1.0f, -1.0f,  1.0f, // 22 - Bottom Left Of The Quad (Right)
         1.0f, -1.0f, -1.0f  // 23 - Bottom Right Of The Quad (Right)
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

        3.0f, 3.0f, // Top Right Of The Quad (Top)
        0.0f, 3.0f, // Top Left Of The Quad (Top)
        0.0f, 0.0f, // Bottom Left Of The Quad (Top)
        3.0f, 0.0f, // Bottom Right Of The Quad (Top)

        2.0f, 2.0f, // Top Right Of The Quad (Left)
        0.0f, 2.0f, // Top Left Of The Quad (Left)
        0.0f, 0.0f, // Bottom Left Of The Quad (Left)
        2.0f, 0.0f, // Bottom Right Of The Quad (Left)

        6.0f, 6.0f, // Bottom Left Of The Quad (Back)
        0.0f, 6.0f, // Bottom Right Of The Quad (Back)
        0.0f, 0.0f, // Top Right Of The Quad (Back)
        6.0f, 0.0f, // Top Left Of The Quad (Back)

        4.0f, 4.0f, // Top Right Of The Quad (Bottom)
        0.0f, 4.0f, // Top Left Of The Quad (Bottom)
        0.0f, 0.0f, // Bottom Left Of The Quad (Bottom)
        4.0f, 0.0f, // Bottom Right Of The Quad (Bottom)

        0.5f, 0.5f, // Top Right Of The Quad (Right)
        0.0f, 0.5f, // Top Left Of The Quad (Right)
        0.0f, 0.0f, // Bottom Left Of The Quad (Right)
        0.5f, 0.0f, // Bottom Right Of The Quad (Right)
    };
    
    int[] elements  = new int[]{
        0, 1, 2, // Top Right, Top Left, Bottom Left Of The Quad (Front)
        0, 2, 3, // Top Right, Bottom Left, Bottom Right Of The Quad (Front)
        
        4, 5, 6, // Top Right, Top Left, Bottom Left Of The Quad (Top)
        4, 6, 7, // Top Right, Bottom Left, Bottom Right Of The Quad(Top)

        8,  9, 10, // Top Right, Top Left, Bottom Left Of The Quad (Left)
        8, 10, 11, // Top Right, Bottom Left, Bottom Right Of The Quad (Left)

        12, 13, 14, // Top Right, Top Left, Bottom Left Of The Quad (Back)
        12, 14, 15, // Top Right, Bottom Left, Bottom Right Of The Quad (Back)
       
        16, 17, 18, // Top Right, Top Left, Bottom Left Of The Quad (Bottom)
        16, 18, 19, // Top Right, Bottom Left, Bottom Right Of The Quad (Bottom)

        20, 21, 22, // Top Right, Top Left, Bottom Left Of The Quad (Right)
        20, 22, 23, // Top Right, Bottom Left, Bottom Right Of The Quad (Right)
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
        
        // ----------------------- ELEMENTS INDICES --------------------------//
        IntBuffer elementsBuffer = BufferUtils.createIntBuffer(elements.length);
        elementsBuffer.put(elements).flip();
        
        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementsBuffer, GL_STATIC_DRAW);
        
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

        // glDrawElements(mode, count, type, offset). 
        // The mode will be GL_TRIANGLES, 
        // count specifies how many vertices are to draw and
        // type indicates the type of the index values in the EBO, 
        // offset should be clear.
        glDrawElements(GL_TRIANGLES, 2*3*6, GL_UNSIGNED_INT, 0);       
    }

}
