package practica3d_v2;

import Utils.Drawable;
import Utils.OpenGLHelper;
import Utils.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL13.GL_TEXTURE4;
import static org.lwjgl.opengl.GL13.GL_TEXTURE5;
import static org.lwjgl.opengl.GL13.GL_TEXTURE6;
import static org.lwjgl.opengl.GL13.GL_TEXTURE7;
import static org.lwjgl.opengl.GL13.GL_TEXTURE8;
import static org.lwjgl.opengl.GL13.GL_TEXTURE9;
import static org.lwjgl.opengl.GL13.GL_TEXTURE10;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Lienzo implements Drawable{
    
    public ShaderProgram shaderProgram;
    private final OpenGLHelper openGLHelper = new OpenGLHelper("Practica3D");
    private int uniModel;
    public int floatSize = 4;
    private int uKdAttribute;
    private int uKaAttribute;
    public Aeropuerto aero;
    
    private int uniTex1, uniTex2, uniTex3;

    private Texture fieldstoneTexture, stoneTexture, nicegrasstexture, roadTexture, aster1Texture;
    private Texture batwingTexture, aster2Texture, spaceTexture, spacedustTexture, hall2Texture;

    public void init(){
        openGLHelper.initGL("VS_ADS_Texture.vs", "FS_ADS_Texture4.fs");
        shaderProgram = openGLHelper.getShaderProgram();
        initLights();
        initTextures();
    }
    
    public void run(Aeropuerto aero) {
        this.aero = aero;
        openGLHelper.run(this);
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
    
    private void initTextures() {
        glActiveTexture(GL_TEXTURE0);
        nicegrasstexture = Texture.loadTexture("nicegrass.jpg");
        uniTex1 = shaderProgram.getUniformLocation("Texture1");
        shaderProgram.setUniform(uniTex1, 0);

        glActiveTexture(GL_TEXTURE1);
        aster1Texture = Texture.loadTexture("aster1.jpg");
        uniTex2 = shaderProgram.getUniformLocation("Texture2");
        shaderProgram.setUniform(uniTex2, 1);

        glActiveTexture(GL_TEXTURE2);
        Texture texture = Texture.loadTexture("darkrockalpha.png");
        uniTex3 = shaderProgram.getUniformLocation("Texture3");
        shaderProgram.setUniform(uniTex3, 2);

        
        
        glActiveTexture(GL_TEXTURE3);
        fieldstoneTexture = Texture.loadTexture("fieldstone.jpg");
        
        glActiveTexture(GL_TEXTURE4);
        stoneTexture = Texture.loadTexture("stone-128px.jpg");
        
        glActiveTexture(GL_TEXTURE5);
        roadTexture = Texture.loadTexture("stone-256px.jpg");
        
        glActiveTexture(GL_TEXTURE6);
        batwingTexture = Texture.loadTexture("Batwing_D.png");
        
        glActiveTexture(GL_TEXTURE7);
        spacedustTexture = Texture.loadTexture("spacedust.jpg");
        
        glActiveTexture(GL_TEXTURE8);
        aster2Texture = Texture.loadTexture("aster2.jpg");
        
        glActiveTexture(GL_TEXTURE9);
        spaceTexture = Texture.loadTexture("spacetexture.png");
        
        glActiveTexture(GL_TEXTURE10);
        hall2Texture = Texture.loadTexture("hall2.jpeg");
    }
    
    @Override
    public void draw() {
        float DeltaTime = openGLHelper.getDeltaTime();
        aero.drawall(openGLHelper);
    }
}
