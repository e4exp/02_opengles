package com.example.adam.opengles;


import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements GLSurfaceView.Renderer {
    public GLRenderer(Context applicationContext){

    }
    Triangle triangle;


    //called once on created
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        triangle = new Triangle();
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }
    @Override
    public void onDrawFrame(GL10 unused) {
        //背景色(R,G,B,ALPHA)
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        triangle.draw();
    }


    public class Triangle {
        //シンプルなシェーダー
        public final String vertexShaderCode =
                "attribute  vec4 vPosition;" +
                        "void main() {" +
                        "  gl_Position = vPosition;" +
                        "}";
        //シンプル色は自分で指定(R,G,B ALPHA)指定
        public final String fragmentShaderCode =
                "precision mediump float;" +
                        "void main() {" +
                        "  gl_FragColor =vec4(1.0, 0.0, 0.0, 1.0);" +
                        "}";
        private int loadShader(int type, String shaderCode){
            int shader = GLES20.glCreateShader(type);
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);
            return shader;
        }

        private  int shaderProgram;

        public Triangle(){
            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
            shaderProgram = GLES20.glCreateProgram();
            GLES20.glAttachShader(shaderProgram, vertexShader);
            GLES20.glAttachShader(shaderProgram, fragmentShader);
            GLES20.glLinkProgram(shaderProgram);
        }

        public void draw(){
            GLES20.glUseProgram(shaderProgram);
            int positionAttrib = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
            GLES20.glEnableVertexAttribArray(positionAttrib);

            float vertices[] = {
                    0.0f, 0.5f, 0.0f,//三角形の点A(x,y,z)
                    -0.5f, -0.5f, 0.0f,//三角形の点B(x,y,z)
                    0.5f, -0.5f, 0.0f//三角形の点C(x,y,z)
            };
            ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
            bb.order(ByteOrder.nativeOrder());
            FloatBuffer vertexBuffer = bb.asFloatBuffer();
            vertexBuffer.put(vertices);
            vertexBuffer.position(0);
            //GLES20.glVertexAttribPointer(positionAttrib,vertices.length, GLES20.GL_FLOAT, false, 0, vertexBuffer);
            GLES20.glVertexAttribPointer(positionAttrib, 3, GLES20.GL_FLOAT, false, 3 *4, vertexBuffer);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length/3);

            GLES20.glDisableVertexAttribArray(positionAttrib);
        }
    }
}


