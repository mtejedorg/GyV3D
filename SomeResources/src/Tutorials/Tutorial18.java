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
public class Tutorial18 implements Drawable
{
    private ShaderProgram shaderProgram;
    private final OpenGLHelper openGLHelper = new OpenGLHelper("Tutorial 18");
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
        1.0f, 0.0f, 0.0f, // Top Right Of The Quad (Front)
        0.0f, 1.0f, 0.0f, // Top Left Of The Quad (Front)
        0.0f, 0.0f, 1.0f, // Bottom Left Of The Quad (Front)
        0.0f, 1.0f, 1.0f, // Bottom Right Of The Quad (Front)

        0.0f, 1.0f, 0.0f, // Top Right Of The Quad (Top)
        1.0f, 0.0f, 1.0f, // Top Left Of The Quad (Top)
        0.0f, 1.0f, 1.0f, // Bottom Left Of The Quad (Top)
        0.0f, 0.0f, 0.0f, // Bottom Right Of The Quad (Top)

        1.0f, 1.0f, 1.0f, // Top Right Of The Quad (Bottom)
        0.0f, 0.0f, 1.0f, // Top Left Of The Quad (Bottom)
        1.0f, 0.0f, 1.0f, // Bottom Left Of The Quad (Bottom)
        1.0f, 1.0f, 0.0f, // Bottom Right Of The Quad (Bottom)

        1.0f, 0.0f, 0.0f, // Bottom Left Of The Quad (Back)
        1.0f, 0.0f, 1.0f, // Bottom Right Of The Quad (Back)
        0.0f, 1.0f, 1.0f, // Top Right Of The Quad (Back)
        0.0f, 1.0f, 0.0f, // Top Left Of The Quad (Back)

        1.0f, 1.0f, 1.0f, // Top Right Of The Quad (Left)
        1.0f, 1.0f, 0.0f, // Top Left Of The Quad (Left)
        1.0f, 0.0f, 0.0f, // Bottom Left Of The Quad (Left)
        1.0f, 0.0f, 1.0f, // Bottom Right Of The Quad (Left)

        1.0f, 1.0f, 0.0f, // Top Right Of The Quad (Right)
        0.0f, 1.0f, 1.0f, // Top Left Of The Quad (Right)
        1.0f, 0.0f, 1.0f, // Bottom Left Of The Quad (Right)
        0.0f, 0.0f, 1.0f // Bottom Right Of The Quad (Right)
    };

    public void run() {
        openGLHelper.initGL("VertexShaderSrc.vs", "FragmentShaderSrc.fs");
        prepareBuffers();
        openGLHelper.run(this);
    }

    private void prepareBuffers() {
        shaderProgram = openGLHelper.getShaderProgram();
        
        // Create a FloatBuffer of vertices
        // Do not forget to do vertices.flip()! This is important, because passing the buffer without 
        // flipping will crash your JVM because of a EXCEPTION_ACCESS_VIOLATION.
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        int vbo_v = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        int posAttrib = shaderProgram.getAttributeLocation("aVertexPosition");
        glEnableVertexAttribArray(posAttrib);
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);

        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

        FloatBuffer colorsBuffer = BufferUtils.createFloatBuffer(colors.length);
        colorsBuffer.put(colors).flip();

        int vbo_c = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_c);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);

        int vertexColorAttribute = shaderProgram.getAttributeLocation("aVertexColor");
        glEnableVertexAttribArray(vertexColorAttribute);
        glBindBuffer(GL_ARRAY_BUFFER, vbo_c);
        glVertexAttribPointer(vertexColorAttribute, 3, GL_FLOAT, false, 0, 0);

        uniModel = shaderProgram.getUniformLocation("model");
    }

    @Override
    public void draw()
    {
        /* Render a model */
        drawSomeModel();
    }
    
    private void drawSomeModel() {
            /* Render X, Y, Z */
            for (int i = 0; i < 15; i++)
            {
                Matrix4f model = Matrix4f.scale(0.35f, 0.15f, 0.15f);
                model = Matrix4f.translate(i, 0, 0).multiply(model);
                glUniformMatrix4(uniModel, false, model.getBuffer());
                glDrawArrays(GL_QUADS, 0, 4*6);
                
                model = Matrix4f.scale(0.15f, 0.35f, 0.15f);
                model = Matrix4f.translate(0, i, 0).multiply(model);
                glUniformMatrix4(uniModel, false, model.getBuffer());
                glDrawArrays(GL_QUADS, 0, 4*6);
                
                model = Matrix4f.scale(0.15f, 0.15f, 0.35f);
                model = Matrix4f.translate(0, 0, 0+i).multiply(model);
                glUniformMatrix4(uniModel, false, model.getBuffer());
                glDrawArrays(GL_QUADS, 0, 4*6);
            }
    }

}
