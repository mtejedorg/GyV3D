/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tutorials;

import Utils.FPCameraControllerA;
import Utils.Matrix4f;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 *
 * @author Agustin
 */
public class Tutorial17 {
   
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cpCallback;
    
    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;
    public static final int FLOAT_SIZE = 4;
    
    // The window handle
    private long window;
    private int shaderProgram;
    private int uniModel;
    private int uniView;
    
    private FPCameraControllerA camera = new FPCameraControllerA(-5, -5, -15);
    private float deltaTime       = 0.0f; //length of frame
    private double lastFrame; // when the last frame was
    
    private float movementSpeed = 5.0f; //move 10 units per second
    private double lastX = 400, lastY = 300;
    
    public void run() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!. Tutorial 17");

        try {
            initGL();
            loop();

            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
        }
    }
    float[] vertices = new float[]
        {
              1.0f,  1.0f,  1.0f ,         // Top Right Of The Quad (Front)
             -1.0f,  1.0f,  1.0f ,         // Top Left Of The Quad (Front)
             -1.0f, -1.0f,  1.0f ,         // Bottom Left Of The Quad (Front)
              1.0f, -1.0f,  1.0f ,         // Bottom Right Of The Quad (Front)
              
              1.0f, 1.0f, -1.0f ,          // Top Right Of The Quad (Top)
             -1.0f, 1.0f, -1.0f ,          // Top Left Of The Quad (Top)
             -1.0f, 1.0f,  1.0f ,          // Bottom Left Of The Quad (Top)
              1.0f, 1.0f,  1.0f ,          // Bottom Right Of The Quad (Top)
        
              1.0f, -1.0f,  1.0f ,         // Top Right Of The Quad (Bottom)
             -1.0f, -1.0f,  1.0f ,         // Top Left Of The Quad (Bottom)
             -1.0f, -1.0f, -1.0f ,         // Bottom Left Of The Quad (Bottom)
              1.0f, -1.0f, -1.0f ,         // Bottom Right Of The Quad (Bottom)
        
              1.0f, -1.0f, -1.0f ,         // Bottom Left Of The Quad (Back)
             -1.0f, -1.0f, -1.0f ,         // Bottom Right Of The Quad (Back)
             -1.0f,  1.0f, -1.0f ,         // Top Right Of The Quad (Back)
              1.0f,  1.0f, -1.0f ,         // Top Left Of The Quad (Back)
        
             -1.0f,  1.0f,  1.0f ,         // Top Right Of The Quad (Left)
             -1.0f,  1.0f, -1.0f ,         // Top Left Of The Quad (Left)
             -1.0f, -1.0f, -1.0f ,         // Bottom Left Of The Quad (Left)
             -1.0f, -1.0f,  1.0f ,         // Bottom Right Of The Quad (Left)

              1.0f,  1.0f, -1.0f ,         // Top Right Of The Quad (Right)
              1.0f,  1.0f,  1.0f ,         // Top Left Of The Quad (Right)
              1.0f, -1.0f,  1.0f ,         // Bottom Left Of The Quad (Right)
              1.0f, -1.0f, -1.0f           // Bottom Right Of The Quad (Right)
        };
     
    float[] colors = new float[]
        {
              1.0f,  0.0f,  0.0f ,         // Top Right Of The Quad (Front)
              0.0f,  1.0f,  0.0f ,         // Top Left Of The Quad (Front)
              0.0f,  0.0f,  1.0f ,         // Bottom Left Of The Quad (Front)
              0.0f,  1.0f,  1.0f ,         // Bottom Right Of The Quad (Front)
              
              0.0f, 1.0f,  0.0f ,          // Top Right Of The Quad (Top)
              1.0f, 0.0f,  1.0f ,          // Top Left Of The Quad (Top)
              0.0f, 1.0f,  1.0f ,          // Bottom Left Of The Quad (Top)
              0.0f, 0.0f,  0.0f ,          // Bottom Right Of The Quad (Top)
              
              1.0f,  1.0f,  1.0f ,         // Top Right Of The Quad (Bottom)
              0.0f,  0.0f,  1.0f ,         // Top Left Of The Quad (Bottom)
              1.0f,  0.0f,  1.0f ,         // Bottom Left Of The Quad (Bottom)
              1.0f,  1.0f,  0.0f ,         // Bottom Right Of The Quad (Bottom)
        
              1.0f,  0.0f,  0.0f ,         // Bottom Left Of The Quad (Back)
              1.0f,  0.0f,  1.0f ,         // Bottom Right Of The Quad (Back)
              0.0f,  1.0f,  1.0f ,         // Top Right Of The Quad (Back)
              0.0f,  1.0f,  0.0f ,         // Top Left Of The Quad (Back)
        
              1.0f,  1.0f,  1.0f ,         // Top Right Of The Quad (Left)
              1.0f,  1.0f,  0.0f ,         // Top Left Of The Quad (Left)
              1.0f,  0.0f,  0.0f ,         // Bottom Left Of The Quad (Left)
              1.0f,  0.0f,  1.0f ,         // Bottom Right Of The Quad (Left)

              1.0f,  1.0f,  0.0f ,         // Top Right Of The Quad (Right)
              0.0f,  1.0f,  1.0f ,         // Top Left Of The Quad (Right)
              1.0f,  0.0f,  1.0f ,         // Bottom Left Of The Quad (Right)
              0.0f,  0.0f,  1.0f           // Bottom Right Of The Quad (Right)
        };
     
    static final String VertexShaderSrc = 
                        "#version 110\n" +
                        "\n" +
                        "   in vec3 aVertexPosition;\n" +
                        "   in vec3 aVertexColor;\n" +
                        "\n" +
                        "   varying vec3 vColor;\n" +
                        "\n" +
                        "   uniform mat4 model;\n" +
                        "   uniform mat4 view;\n" +
                        "   uniform mat4 projection;\n" +
                        "\n" +
                        "   void main() {\n" +
                        "       vColor = aVertexColor;\n" +
                        "       mat4 mvp = projection * view * model;\n" +
                        "       gl_Position = mvp * vec4(aVertexPosition, 1.0);\n" +
                        "   }";
    
    static final String FragmentShaderSrc = 
                        "#version 110\n" +
                        "\n" +
                        "   varying vec3 vColor;\n" +
                        "\n" +
                        "   void main() {\n" +
                        "        gl_FragColor = vec4(vColor, 1.0);\n" +
                        "   }";
    
    
    private void initGL() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GL_TRUE) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Tutorial 17", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
                    return;
                }
               
                //when passing in the distance to move
                //we times the movementSpeed with dt this is a time scale
                //so if its a slow frame u move more then a fast frame
                //so on a slow computer you move just as fast as on a fast computer
                if (key == GLFW_KEY_W)//move forward
                {
                    camera.walkForward(movementSpeed*deltaTime);
                }
                if (key == GLFW_KEY_S)//move backwards
                {
                    camera.walkBackwards(movementSpeed*deltaTime);
                }
                if (key == GLFW_KEY_A)//strafe left
                {
                    camera.strafeLeft(movementSpeed*deltaTime);
                }
                if (key == GLFW_KEY_D)//strafe right
                {
                    camera.strafeRight(movementSpeed*deltaTime);
                }
            }
        });

        glfwSetCursorPosCallback(window, cpCallback = new GLFWCursorPosCallback() {
                        private boolean firstMouse = true;
                        
			@Override
			public void invoke(long window, double xpos, double ypos) {
				if(firstMouse)
                                {
                                    lastX = xpos;
                                    lastY = ypos;
                                    firstMouse = false;
                                }

                                float xoffset = (float)(xpos - lastX);
                                float yoffset = (float)(ypos - lastY); 

                                lastX = xpos;
                                lastY = ypos;

                                camera.ProcessMouseMovement(xoffset, yoffset);
			}
		});
        
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
        
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();

        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        
        glEnable( GL_DEPTH_TEST);
        
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, VertexShaderSrc);
        glCompileShader(vertexShader);
        int status = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(vertexShader));
        }
        
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, FragmentShaderSrc);
        glCompileShader(fragmentShader);
        status = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(vertexShader));
        }

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        glUseProgram(shaderProgram);
    
    }
   
        
    private void loop() {
        /// Create a FloatBuffer of vertices
        /// Do not forget to do vertices.flip()! This is important, because passing the buffer without 
        // flipping will crash your JVM because of a EXCEPTION_ACCESS_VIOLATION.
        
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        int vbo_v = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
 
        int posAttrib = glGetAttribLocation(shaderProgram, "aVertexPosition");
        glEnableVertexAttribArray(posAttrib);
        glBindBuffer(GL_ARRAY_BUFFER, vbo_v);
        
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);      
        
        FloatBuffer colorsBuffer = BufferUtils.createFloatBuffer(colors.length);
        colorsBuffer.put(colors).flip();
 
        int vbo_c = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_c);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);

        int vertexColorAttribute = glGetAttribLocation(shaderProgram, "aVertexColor");
        glEnableVertexAttribArray(vertexColorAttribute);
        glBindBuffer(GL_ARRAY_BUFFER, vbo_c);
        glVertexAttribPointer(vertexColorAttribute, 3, GL_FLOAT, false, 0, 0);
        
        uniModel = glGetUniformLocation(shaderProgram, "model");
        
        int uniProjection = glGetUniformLocation(shaderProgram, "projection");
        float ratio = (float)WIDTH / (float)HEIGHT;
        Matrix4f projection = Matrix4f.perspective(60, ratio, 0.1f, 100.0f );
        glUniformMatrix4(uniProjection, false, projection.getBuffer());
        
        uniView = glGetUniformLocation(shaderProgram, "view");
        
        
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (glfwWindowShouldClose(window) == GL_FALSE) {
            
            double currentFrame = glfwGetTime();
            deltaTime = (float)(currentFrame - lastFrame);
            lastFrame = currentFrame;
            
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            /* update camera position */
            Matrix4f view = camera.lookThrough();
            glUniformMatrix4(uniView, false, view.getBuffer());
        
            /* Render some model */
            drawSomeModel();
            
            /* Swap buffers and poll Events */
            glfwSwapBuffers(window); // swap the color buffers  
        }
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

