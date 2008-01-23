package org.cybergarage.x3d.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.OpenGLContext;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;

import org.cybergarage.x3d.*;
import org.cybergarage.x3d.node.*;

/**
 * Example of how to use OpenGL|ES in a custom view
 *
 */

public class X3DView extends View
{
    ////////////////////////////////////////
    // Member
    ////////////////////////////////////////
	
	private	SceneGraph mSceneGraph = null;

    ////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////
	
    /**
     * The View constructor is a good place to allocate our OpenGL context
     */
    public X3DView(Context context)
    {
        super(context);
        
        /* 
         * Create an OpenGL|ES context. This must be done only once, an
         * OpenGL contex is a somewhat heavy object.
         */
        mSceneGraph = new SceneGraph();
        
        mGLContext = new OpenGLContext(0);
        mAnimate = true;
    }
    
    /*
     * Start the animation only once we're attached to a window
     * @see android.view.View#onAttachedToWindow()
     */
    @Override
    protected void onAttachedToWindow() {
        mAnimate = true;
        Message msg = mHandler.obtainMessage(INVALIDATE);
        mNextTime = SystemClock.uptimeMillis();
        mHandler.sendMessageAtTime(msg, mNextTime);
        super.onAttachedToWindow();
    }
    
    /*
     * Make sure to stop the animation when we're no longer on screen,
     * failing to do so will cause most of the view hierarchy to be
     * leaked until the current process dies.
     * @see android.view.View#onDetachedFromWindow()
     */
    @Override
    protected void onDetachedFromWindow() {
        mAnimate = false;
        super.onDetachedFromWindow();
    }

    /**
     * Draw the view content
     * 
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
    	if (true) {
        /*
         * First, we need to get to the appropriate GL interface.
         * This is simply done by casting the GL context to either
         * GL10 or GL11.
         */
        GL10 gl = (GL10)(mGLContext.getGL());
        
        /*
         * Before we can issue GL commands, we need to make sure all
         * native drawing commands are completed. Simply call
         * waitNative() to accomplish this. Once this is done, no native
         * calls should be issued.
         */
        mGLContext.waitNative(canvas, this);
        
    	drawSceneGraph(gl, mSceneGraph, OGL_RENDERING_TEXTURE);
    	
            int w = getWidth();
            int h = getHeight();
/*
            gl.glViewport(0, 0, w, h);
        
            float ratio = (float)w / h;
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glFrustumf(-ratio, ratio, -1, 1, 2, 12);

             gl.glDisable(GL10.GL_DITHER);

            gl.glClearColor(1,1,1,1);
            gl.glEnable(GL10.GL_SCISSOR_TEST);
            gl.glScissor(0, 0, w, h);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0, 0, -3.0f);
            gl.glScalef(0.5f, 0.5f, 0.5f);
            gl.glRotatef(mAngle,        0, 1, 0);
            gl.glRotatef(mAngle*0.25f,  1, 0, 0);

            gl.glColor4f(0.7f, 0.7f, 0.7f, 1.0f);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glEnable(GL10.GL_CULL_FACE);

            //drawSceneGraph();
            
            mAngle += 1.2f;
*/
        /*
         * Once we're done with GL, we need to flush all GL commands and
         * make sure they complete before we can issue more native
         * drawing commands. This is done by calling waitGL().
         */
        mGLContext.waitGL();
    	}
    }
    

    // ------------------------------------------------------------------------

    private static final int INVALIDATE = 1;

    private final Handler mHandler = new Handler() {
        @Override
		public void handleMessage(Message msg) {
            if (mAnimate && msg.what == INVALIDATE) {
                invalidate();
                msg = obtainMessage(INVALIDATE);
                long current = SystemClock.uptimeMillis();
                if (mNextTime < current) {
                    mNextTime = current + 20;
                }
                sendMessageAtTime(msg, mNextTime);
                mNextTime += 20;
            }
        }
    };

    private OpenGLContext   mGLContext;
    private float           mAngle;
    private long            mNextTime;
    private boolean         mAnimate;
  
    ////////////////////////////////////////
    // CyberX3D
    ////////////////////////////////////////
    
	public SceneGraph getSceneGraph() 
	{
		return mSceneGraph;
	}
    
	//////////////////////////////////////////////////////////
	//  Constants
	////////////////////////////////////////////////////////// 
    
    static final int OGL_RENDERING_WIRE = 1;
    static final int OGL_RENDERING_SHADE = 2;
    static final int OGL_RENDERING_TEXTURE = 3;
    
	//////////////////////////////////////////////////////////
	//  MoveViewpoint
	////////////////////////////////////////////////////////// 

    public void updateViewport(GL10 gl, SceneGraph sg, int width, int height) 
	{
		float aspect = (float)width/(float)height;
	
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
	
		ViewpointNode view = sg.getViewpointNode();
		if (view == null)
			view = sg.getDefaultViewpointNode();
	
		float fov = (view.getFieldOfView() / 3.14f) * 180.0f;
	
		GLU.gluPerspective(gl, fov, aspect, 0.1f, 10000.0f);
	
		gl.glViewport( 0, 0, width, height );
	}
    
	////////////////////////////////////////////////////////// 
	//  MoveViewpoint
	////////////////////////////////////////////////////////// 

    public void moveViewpoint(GL10 gl, SceneGraph sg, int width, int height, int mosx, int mosy)
	{
		ViewpointNode view = sg.getViewpointNode();
		if (view == null)
			view = sg.getDefaultViewpointNode();

		NavigationInfoNode navInfo = sg.getNavigationInfoNode();
		if (navInfo == null)
			navInfo = sg.getDefaultNavigationInfoNode();

		float	trans[] = {0.0f, 0.0f, 0.0f};
		float	rot[] = {0.0f, 0.0f, 1.0f, 0.0f};

		float transScale = 1.0f /10.0f;
		float rotScale = (3.1415f/120.0f);

		trans[2] = (float)(mosy - height/2) / (float)(height/2) * navInfo.getSpeed() * transScale;
		rot[0] = 0.0f;
		rot[1] = 1.0f;
		rot[2] = 0.0f;
		rot[3] = -(float)(mosx - width/2) / (float)(width/2) * rotScale;

		view.translate(trans);
		view.rotate(rot);
	}

	////////////////////////////////////////////////////////// 
	//  DrawSceneGraph
	////////////////////////////////////////////////////////// 

	static int gnLights;
	static PointLightNode headLight;

	public void pushLightNode(GL10 gl, LightNode lightNode)
	{
		if (!lightNode.isOn()) 
			return;

		int nMaxLightMax[] = new int[1];
		gl.glGetIntegerv(GL10.GL_MAX_LIGHTS, nMaxLightMax, 0);

		if (nMaxLightMax[0] < gnLights) {
			gnLights++;
			return;
		}

		float	color[] = new float[4];
		float	pos[] = new float[4];
		float	attenuation[] = new float[3];
		float	direction[] = new float[3];
		float	intensity;

		if (lightNode.isPointLightNode()) {
			
			PointLightNode pLight = (PointLightNode)lightNode;

			gl.glEnable(GL10.GL_LIGHT0+gnLights);
			
			pLight.getAmbientColor(color);
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_AMBIENT, color, 0);

			pLight.getColor(color);
			intensity = pLight.getIntensity();
			color[0] *= intensity; 
			color[1] *= intensity; 
			color[2] *= intensity; 
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_DIFFUSE, color, 0);
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_SPECULAR, color, 0);

			pLight.getLocation(pos); pos[3] = 1.0f;
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_POSITION, pos, 0);

			direction[0] = 0.0f; direction[0] = 0.0f; direction[0] = 0.0f;
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_SPOT_DIRECTION, direction, 0);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_SPOT_EXPONENT, 0.0f);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_SPOT_CUTOFF, 180.0f);

			pLight.getAttenuation(attenuation);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_CONSTANT_ATTENUATION, attenuation[0]);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_LINEAR_ATTENUATION, attenuation[1]);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_QUADRATIC_ATTENUATION, attenuation[2]);
			
			gnLights++;
		}
		else if (lightNode.isDirectionalLightNode()) {

			DirectionalLightNode dLight = (DirectionalLightNode)lightNode;
			
			gl.glEnable(GL10.GL_LIGHT0+gnLights);
			
			dLight.getAmbientColor(color);
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_AMBIENT, color, 0);

			dLight.getColor(color);
			intensity = dLight.getIntensity();
			color[0] *= intensity; 
			color[1] *= intensity; 
			color[2] *= intensity; 
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_DIFFUSE, color, 0);
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_SPECULAR, color, 0);

			dLight.getDirection(pos); pos[3] = 0.0f;
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_POSITION, pos, 0);

			direction[0] = 0.0f; direction[0] = 0.0f; direction[0] = 0.0f;
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_SPOT_DIRECTION, direction, 0);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_SPOT_EXPONENT, 0.0f);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_SPOT_CUTOFF, 180.0f);

			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_CONSTANT_ATTENUATION, 1.0f);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_LINEAR_ATTENUATION, 0.0f);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_QUADRATIC_ATTENUATION, 0.0f);

			gnLights++;
		}
		else if (lightNode.isSpotLightNode()) {

			SpotLightNode sLight = (SpotLightNode)lightNode;

			gl.glEnable(GL10.GL_LIGHT0+gnLights);
			
			sLight.getAmbientColor(color);
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_AMBIENT, color, 0);

			sLight.getColor(color);
			intensity = sLight.getIntensity();
			color[0] *= intensity; 
			color[1] *= intensity; 
			color[2] *= intensity; 
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_DIFFUSE, color, 0);
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_SPECULAR, color, 0);

			sLight.getLocation(pos); pos[3] = 1.0f;
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_POSITION, pos, 0);

			sLight.getDirection(direction);
			gl.glLightfv(GL10.GL_LIGHT0+gnLights, GL10.GL_SPOT_DIRECTION, direction, 0);

			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_SPOT_EXPONENT, 0.0f);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_SPOT_CUTOFF, sLight.getCutOffAngle());

			sLight.getAttenuation(attenuation);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_CONSTANT_ATTENUATION, attenuation[0]);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_LINEAR_ATTENUATION, attenuation[1]);
			gl.glLightf(GL10.GL_LIGHT0+gnLights, GL10.GL_QUADRATIC_ATTENUATION, attenuation[2]);

			gnLights++;
		}
	}

	public void popLightNode(GL10 gl,LightNode lightNode)
	{
		if (!lightNode.isOn()) 
			return;

		int nMaxLightMax[] = new int[1];
		gl.glGetIntegerv(GL10.GL_MAX_LIGHTS, nMaxLightMax, 0);

		gnLights--;
		
		if (gnLights < nMaxLightMax[0])
			gl.glDisable(GL10.GL_LIGHT0+gnLights);
	}

	public void drawShapeNode(GL10 gl, SceneGraph sg, ShapeNode shape, int drawMode)
	{
		gl.glPushMatrix ();

		/////////////////////////////////
		//	Appearance(Material)
		/////////////////////////////////

		float color[] = new float[4];
		color[3] = 1.0f;

		AppearanceNode			appearance = shape.getAppearanceNodes();
		MaterialNode			material = null;
		ImageTextureNode		imgTexture = null;
		TextureTransformNode	texTransform = null;

		boolean				bEnableTexture = false;

		if (appearance != null) {

			// Texture Transform
			texTransform = appearance.getTextureTransformNodes();
			if (texTransform != null) {
				float texCenter[] = new float[2];
				float texScale[] = new float[2];
				float texTranslation[] = new float[2];
				float texRotation;

				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();

				texTransform.getTranslation(texTranslation);
				gl.glTranslatef(texTranslation[0], texTranslation[1], 0.0f);

				texTransform.getCenter(texCenter);
				gl.glTranslatef(texCenter[0], texCenter[1], 0.0f);

				texRotation = texTransform.getRotation();
				gl.glRotatef(0.0f, 0.0f, 1.0f, texRotation);

				texTransform.getScale(texScale);
				gl.glScalef(texScale[0], texScale[1], 1.0f);

				gl.glTranslatef(-texCenter[0], -texCenter[1], 0.0f);
			}
			else {
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.0f, 0.0f, 1.0f);
			}

			gl.glMatrixMode(GL10.GL_MODELVIEW);

			// Texture
			imgTexture = appearance.getImageTextureNodes();
			if (imgTexture != null && drawMode == OGL_RENDERING_TEXTURE) {

				/*
				int width = imgTexture.getWidth();
				int height = imgTexture.getHeight();
				RGBAColor32 *color = imgTexture.getImage();

				if (0 < width && 0 < height && color != null) 
					bEnableTexture = true;

				if (bEnableTexture == true) {
					if (imgTexture.hasTransparencyColor() == true) {
						gl.glEnable(GL10.GL_BLEND);
						gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
					}
					else
						gl.glDisable(GL10.GL_BLEND);

					gl.glPixelStorei(GL10.GL_UNPACK_ALIGNMENT, 1);
					gl.glBindTexture(GL10.GL_TEXTURE_2D, imgTexture.getTextureName());

					gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

					gl.glEnable(GL10.GL_TEXTURE_2D);

					gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					gl.glColorMaterial(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE);
					gl.glEnable(GL10.GL_COLOR_MATERIAL);
				}
				else {
					gl.glDisable(GL10.GL_TEXTURE_2D);
					gl.glDisable(GL10.GL_COLOR_MATERIAL);
				}
				*/
			}
			else {
				gl.glDisable(GL10.GL_TEXTURE_2D);
				gl.glDisable(GL10.GL_COLOR_MATERIAL);
			}

			// Material
			material = appearance.getMaterialNodes();
			if (material != null) {
				float	ambientIntesity = material.getAmbientIntensity();

				material.getDiffuseColor(color);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, color, 0);

				material.getSpecularColor(color);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, color, 0);

				material.getEmissiveColor(color);
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, color, 0);

				material.getDiffuseColor(color);
				color[0] *= ambientIntesity; 
				color[1] *= ambientIntesity; 
				color[2] *= ambientIntesity; 
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, color, 0);

				gl.glMaterialx(GL10.GL_FRONT, GL10.GL_SHININESS, (int)(material.getShininess()*128.0));
			}

		}
		
		if (appearance == null || material == null) {
			color[0] = 0.8f; color[1] = 0.8f; color[2] = 0.8f;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, color, 0);
			color[0] = 0.0f; color[1] = 0.0f; color[2] = 0.0f;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, color, 0);
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, color, 0);
			color[0] = 0.8f*0.2f; color[1] = 0.8f*0.2f; color[2] = 0.8f*0.2f;
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, color, 0);
			gl.glMaterialx(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, (int)(0.2*128.0));
		}

		if (appearance == null || imgTexture == null || drawMode != OGL_RENDERING_TEXTURE) {
			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisable(GL10.GL_BLEND);
		}

		/////////////////////////////////
		//	Transform 
		/////////////////////////////////

		float m4[] = new float[16];
		shape.getTransformMatrix(m4);
		gl.glMultMatrixf(m4, 0);

		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		/////////////////////////////////
		//	Geometry3D
		/////////////////////////////////

		Geometry3DNode gnode = shape.getGeometry3D();
		if (gnode != null) {
			GeometryNodeObject.draw(gl, gnode);
		}

		/*
		ShapeNode selectedShapeNode = sg.getSelectedShapeNode();
		if (gnode != null  && selectedShapeNode == shape) {
			float	bboxCenter[] = new float[3];
			float	bboxSize[] = new float[3];
			gnode.getBoundingBoxCenter(bboxCenter);
			gnode.getBoundingBoxSize(bboxSize);

			gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			gl.glDisable(GL10.GL_LIGHTING);
	//		gl.glDisable(GL10.GL_COLOR_MATERIAL);
			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisable(GL10.GL_BLEND);

			gl.glBegin(GL10.GL_LINES);
			
			int x, y, z;
			for (x=0; x<2; x++) {
				for (y=0; y<2; y++) {
					float point[] = new float[3];
					point[0] = (x==0) ? bboxCenter[0] - bboxSize[0] : bboxCenter[0] + bboxSize[0];
					point[1] = (y==0) ? bboxCenter[1] - bboxSize[1] : bboxCenter[1] + bboxSize[1];
					point[2] = bboxCenter[2] - bboxSize[2];
					gl.glVertex3fv(point);
					point[2] = bboxCenter[2] + bboxSize[2];
					gl.glVertex3fv(point);
				}
			}
			for (x=0; x<2; x++) {
				for (z=0; z<2; z++) {
					float point[] = new float[3];
					point[0] = (x==0) ? bboxCenter[0] - bboxSize[0] : bboxCenter[0] + bboxSize[0];
					point[1] = bboxCenter[1] - bboxSize[1];
					point[2] = (z==0) ? bboxCenter[2] - bboxSize[2] : bboxCenter[2] + bboxSize[2];
					gl.glVertex3fv(point);
					point[1] = bboxCenter[1] + bboxSize[1];
					gl.glVertex3fv(point);
				}
			}
			for (y=0; y<2; y++) {
				for (z=0; z<2; z++) {
					float point[] = new float[3];
					point[0] = bboxCenter[0] - bboxSize[0];
					point[1] = (y==0) ? bboxCenter[1] - bboxSize[1] : bboxCenter[1] + bboxSize[1];
					point[2] = (z==0) ? bboxCenter[2] - bboxSize[2] : bboxCenter[2] + bboxSize[2];
					gl.glVertex3fv(point);
					point[0] = bboxCenter[0] + bboxSize[0];
					gl.glVertex3fv(point);
				}
			}
			gl.glEnd();

			gl.glEnable(GL10.GL_LIGHTING);
	//		gl.glEnable(GL10.GL_COLOR_MATERIAL);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnable(GL10.GL_BLEND);
		}
		*/
		
		gl.glPopMatrix();
	}


	public void drawNode(GL10 gl, SceneGraph sceneGraph, Node firstNode, int drawMode) 
	{
		if (firstNode == null)
			return;

		Node node;

		for (node = firstNode; node != null ; node=node.next()) {
			if (node.isLightNode()) 
				pushLightNode(gl, (LightNode)node);
		}

		for (node = firstNode; node != null; node=node.next()) {
			if (node.isShapeNode()) 
				drawShapeNode(gl, sceneGraph, (ShapeNode)node, drawMode);
			else
				drawNode(gl, sceneGraph, node.getChildNodes(), drawMode);
		}

		for (node = firstNode; node != null; node=node.next()) {
			if (node.isLightNode()) 
				popLightNode(gl, (LightNode)node);
		}
	}

	public void drawSceneGraph(GL10 gl, SceneGraph sg, int drawMode)
	{
		/////////////////////////////////
		//	Headlight 
		/////////////////////////////////

		NavigationInfoNode navInfo = sg.getNavigationInfoNode();
		if (navInfo == null)
			navInfo = sg.getDefaultNavigationInfoNode();

		if (navInfo.getHeadlight()) {
			float	location[] = new float[3];
			ViewpointNode view = sg.getViewpointNode();
			if (view == null)
				view = sg.getDefaultViewpointNode();
			view.getPosition(location);
			headLight.setLocation(location);
			headLight.setAmbientIntensity(0.3f);
			headLight.setIntensity(0.7f);
			sg.addNode(headLight);
		}

		/////////////////////////////////
		//	Viewpoint 
		/////////////////////////////////

		ViewpointNode view = sg.getViewpointNode();
		if (view == null)
			view = sg.getDefaultViewpointNode();

		if (view != null) {
			int	viewport[] = new int[4];
			gl.glGetIntegerv(GL10.GL_VIEWPORT, viewport, 0);
			updateViewport(gl, sg, viewport[2], viewport[3]);
		}

		/////////////////////////////////
		//	Rendering 
		/////////////////////////////////

		gl.glEnable(GL10.GL_DEPTH_TEST);
		switch (drawMode) {
		case OGL_RENDERING_WIRE:
			//gl.glPolygonMode(GL10.GL_FRONT_AND_BACK, GL10.GL_LINE);
			break;
		default:
			//gl.glPolygonMode(GL10.GL_FRONT_AND_BACK, GL10.GL_FILL);
		}
		gl.glCullFace(GL10.GL_BACK);
		gl.glEnable(GL10.GL_CULL_FACE);

		gl.glEnable(GL10.GL_LIGHTING);
	//	gl.glShadeModel (GL10.GL_FLAT);
		gl.glShadeModel (GL10.GL_SMOOTH);

		/////////////////////////////////
		//	Background 
		/////////////////////////////////

		float clearColor[] = {0.0f, 0.0f, 0.0f};
		
		BackgroundNode bg = sg.getBackgroundNode();
		if (bg != null) {
			if (0 < bg.getNSkyColors())
				bg.getSkyColor(0, clearColor);
		}

		gl.glClearColor(clearColor[0], clearColor[1], clearColor[2], 1.0f);
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );

		if (view == null)
			return;

		/////////////////////////////////
		//	Viewpoint Matrix
		/////////////////////////////////

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		float m4[]= new float[16];
		view.getMatrix(m4);
		gl.glMultMatrixf(m4, 0);

		/////////////////////////////////
		//	Light
		/////////////////////////////////

		int nMaxLightMax[] = new int[1];
		gl.glGetIntegerv(GL10.GL_MAX_LIGHTS, nMaxLightMax, 0);
		for (int n = 0; n < nMaxLightMax[0]; n++)
			gl.glDisable(GL10.GL_LIGHT0+n);
		gnLights = 0;

		/////////////////////////////////
		//	General Node
		/////////////////////////////////

		drawNode(gl, sg, sg.getNodes(), drawMode);

		/////////////////////////////////
		//	Headlight 
		/////////////////////////////////

		headLight.remove();

		/////////////////////////////////
		//	glFlush 
		/////////////////////////////////

		gl.glFlush();
	}
}
