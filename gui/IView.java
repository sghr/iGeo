/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

    This file is part of iGeo.

    iGeo is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, version 3.

    iGeo is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with iGeo.  If not, see <http://www.gnu.org/licenses/>.

---*/

package igeo.gui;

import java.awt.*;

import javax.media.opengl.*;

import igeo.geo.*;
import igeo.core.*;

/**
   Class to define view by location, direction, axonometric/perspective mode and perspective angle.
   An instance of IView is associated with an instance of IPane.
   
   @author Satoru Sugihara
   @version 0.7.1.0;
*/
public class IView{
    
    public static double defaultNear = 0.001;
    public static double defaultFar = 10000; //1000; //10000;
    
    public static double defaultAxonRatio = 1.0;
    public static double defaultPersRatio = 0.5;
    
    public static double defaultViewDistance=100; //1000;
    
    public static IGraphicMode defaultMode =
	new IGraphicMode(IGraphicMode.GraphicType.GL, true, true, true); //
	//new IGraphicMode(IGraphicMode.GraphicType.GL, true, true, false); //
	//new IGraphicMode(IGraphicMode.GraphicType.GL, IGraphicMode.DisplayType.FILL);
	//IGraphicMode.DisplayType.WIREFRAME);
    
    public static float[] defaultGLLightPosition = {0f, 0f, 1f, 0f};
    public static float[] defaultGLAmbientLight = {.4f,.4f,.4f,1f};
    public static float[] defaultGLDiffuseLight = {.7f,.7f,.7f,1f};
    public static float[] defaultGLSpecularLight = {.0f,.0f,.0f,1f};
    
    public static boolean defaultGLTwoSidedLighting = false; //true; // when this is true, it seems to cause weird behavior looking like some of normals are flipped or messed up

    public static double[][][] defaultGLBGColor =
	new double[][][]{ { {1.0,1.0,1.0},{0.3,0.5,0.7} },
			  { {0.9,0.9,0.9},{0.3,0.5,0.7} } };
    
    // eye location
    public IVec pos;
    
    /**
       yaw-pitch-roll ZXY conversion
       yaw=0 pitch=0 equals x-axis
       yaw is rotation around z, pitch is around y, roll is around x, in right hand rule
    */
    public double yaw, pitch, roll;
    
    // view direction
    //IVec dir;
    
    // upward direction of view
    //IVec up;
    
    // axonometric or perspective
    public boolean axonometric=true; //false;
    
    // screen width&height
    public int screenX, screenY;
    public int screenWidth, screenHeight;
    
    // perspective ratio = tan(angle of view)
    public double persRatio=defaultPersRatio;
    
    // scale to window size
    public double axonRatio=defaultAxonRatio;
    
    public double near=defaultNear, far=defaultFar;
    
    // target location
    //double tx, ty, tz;
    public IVec target;
    public boolean rotateAroundTarget=false;
    public double distToTarget;
    
    //public IPoint targetPt; // for debug
    
    public double glWidth, glHeight;
    //public IMatrix3 glRotationMatrix;
    public IMatrix4 glTransformMatrix;
    public double[] glTransformArray;
    public boolean useGL=false;
    
    public boolean hide=false;
    //public IViewSlot slot=null;
    
    public IPane pane;
    
    public IGraphicMode mode=new IGraphicMode(defaultMode);
    
    public double[][][] glBGColor = null; //defaultGLBGColor;
    
    public Color javaBGColor1, javaBGColor2; 
    
    public IView(double x, double y, double z,
		 double yaw, double pitch, double roll,
		 int screenX, int screenY, int screenWidth, int screenHeight,
		 boolean axonometric){
	//this.x = x; this.y = y; this.z = z;
	pos = new IVec(x,y,z);
	this.yaw = yaw; this.pitch = pitch; this.roll = roll;
	this.screenX = screenX; this.screenY = screenY;
	this.screenWidth = screenWidth; this.screenHeight = screenHeight;
	this.axonometric = axonometric;
	target = new IVec();
    }
    public IView(double x, double y, double z,
		 double yaw, double pitch, double roll,
		 int screenX, int screenY, int screenWidth, int screenHeight){
	this(x,y,z,yaw,pitch,roll,screenX,screenY,screenWidth,screenHeight,true);
    }
    public IView(double x, double y, double z,
		 double yaw, double pitch, double roll){
	//this.x = x; this.y = y; this.z = z;
	pos = new IVec(x,y,z);
	this.yaw = yaw; this.pitch = pitch; this.roll = roll;
	target = new IVec();
    }
    public IView(double x, double y, double z,
		  double yaw, double pitch){
	//this.x = x; this.y = y; this.z = z;
	pos = new IVec(x,y,z);
	this.yaw = yaw; this.pitch = pitch; this.roll = 0;
	target = new IVec();
    }
    
    public void enableGL(){
	useGL=true;
	glTransformMatrix = new IMatrix4();
	glTransformArray = new double[16];
	update();
    }
    
    public void disableGL(){ useGL=false; }
    
    public void setMode(IGraphicMode m){ mode=m; }
    public IGraphicMode mode(){ return mode; }
    
    public boolean isHidden(){ return hide; }
    public void hide(){ hide=true; }
    public void show(){ hide=false; }


    public void setBGColor(Color c){
	if(useGL){
	    for(int i=0; i<2; i++){
		for(int j=0; j<2; j++){
		    glBGColor[i][j][0] = (double)c.getRed()/255;
		    glBGColor[i][j][1] = (double)c.getGreen()/255;
		    glBGColor[i][j][2] = (double)c.getBlue()/255;
		}
	    }    
	}
	else{ javaBGColor1 = c; javaBGColor2 = c; }
    }
    public void setBGColor(Color c1, Color c2){
	if(useGL){
	    for(int i=0; i<2; i++){
		glBGColor[i][0][0] = (double)c1.getRed()/255;
		glBGColor[i][0][1] = (double)c1.getGreen()/255;
		glBGColor[i][0][2] = (double)c1.getBlue()/255;
		glBGColor[i][1][0] = (double)c2.getRed()/255;
		glBGColor[i][1][1] = (double)c2.getGreen()/255;
		glBGColor[i][1][2] = (double)c2.getBlue()/255;
	    }
	}
	else{ javaBGColor1 = c1; javaBGColor2 = c2; }
    }
    
    public void setBGColor(Color[][] c){
	if(useGL){
	    for(int i=0; i<2; i++){
		for(int j=0; j<2; j++){
		    glBGColor[i][j][0] = (double)c[i][j].getRed()/255;
		    glBGColor[i][j][1] = (double)c[i][j].getGreen()/255;
		    glBGColor[i][j][2] = (double)c[i][j].getBlue()/255;
		}
	    }
	}
	else{ javaBGColor1 = c[0][0]; javaBGColor2 = c[0][1]; }
    }
    
    
    
    public void setPane(IPane p){
	pane=p;
	
	//IOut.p("pane.x="+pane.x+", pane.y="+pane.y+", pane.width="+pane.width+", pane.height="+pane.height); //
	int origScH = screenHeight;
	
	screenX=pane.x; screenWidth=pane.width; screenHeight=pane.height;
	
	if(mode.isGL()){ // flip y
	    screenY=pane.getPanel().height - (pane.y+pane.height); 
	}
	else{
	    screenY=pane.y; 
	}
	
	// to keep extend, not axonometric ratio
	if(axonometric&&screenHeight>0&&origScH>0){
	    //IOut.p("screenHeight="+screenHeight+", origScH="+origScH); //
	    //IOut.p("axonRatio="+axonRatio); //
	    axonRatio /= (double)screenHeight/origScH;
	    //IOut.p("axonRatio="+axonRatio); //
	}
	
	//IOut.p("x="+screenX+", y="+screenY+", w="+screenWidth+", h="+screenHeight); //
	
	update(); // necessary?
    }
    
    public void setScreen(int x, int y, int w, int h){
	//IOut.p("x="+x+", y="+y+", w="+w+", h="+h); //
	screenX=x; screenY=y; screenWidth=w; screenHeight=h;
	update(); //?
    }
    public void setScreenSize(int w, int h){ screenWidth=w; screenHeight=h; update(); }
    public void setScreenPosition(int x, int y){ screenX=x; screenY=y; }
    
    public double getAxonometricRatio(){ return axonRatio; }
    public double getPerspectiveRatio(){ return persRatio; }
    
    public void setAxonometricRatio(double r){
	axonRatio = r; 
	axonometric = true;
	glWidth = screenWidth*axonRatio;
	glHeight = screenHeight*axonRatio;
	update(); //?
    }
    public void setPerspectiveRatio(double r){
	persRatio = r;
	axonometric = false;
	glWidth = near*persRatio*2;
	glHeight = glWidth*screenHeight/screenWidth;
	update(); //?
    }
    public void setPerspectiveAngle(double angle){
	setPerspectiveRatio(Math.tan(angle/2));
    }
    
    public void perspective(){ axonometric=false; }
    public void axonometric(){ axonometric=true; }
    
    public boolean isAxonometric(){ return axonometric; }
    
    public void setTarget(double x, double y, double z){
	//this.tx = x; this.ty=y; this.tz = z;
	target.set(x,y,z);
    }
    public void setTarget(IVecI t){ target.set(t); }
    
    public void enableRotationAroundTarget(){ rotateAroundTarget=true; }
    public void disableRotationAroundTarget(){ rotateAroundTarget=true; }
    
    public double distanceToTarget(){
	//return(Math.sqrt((tx-x)*(tx-x)+(ty-y)*(ty-y)+(tz-z)*(tz-z)));
	return target.diff(pos).len();
    }
    
    
    
    public void setLocation(double x, double y, double z){
	//this.x=x; this.y=y; this.z=z;
	pos.set(x,y,z);
    }
    public void setLocation(IVecI pt){ pos.set(pt); }
    
    public void setAngle(double yaw, double pitch, double roll){
	this.roll=roll; setAngle(yaw,pitch); 
	/*
	this.yaw=yaw; this.pitch=pitch; this.roll=roll;
	if(rotateAroundTarget){
	    IVec dir = frontDirection();
	    dir.len(-distanceToTarget());
	    dir.add(target);
	    pos.set(dir);
	    //x = dir.x + tx;
	    //y = dir.y + ty;
	    //z = dir.z + tz;
	}
	*/
    }
    
    public void setAngle(double yaw, double pitch){
	this.yaw=yaw; this.pitch=pitch;
	if(rotateAroundTarget){
	    IVec dir = frontDirection();
	    IVec tdir = target.diff(pos);
	    double dist = -tdir.len();
	    if(tdir.dot(dir)<0) dist = -dist; 
	    dir.len(dist);
	    dir.add(target);
	    pos.set(dir);
	    //x = dir.x + tx;
	    //y = dir.y + ty;
	    //z = dir.z + tz;
	    //IOut.p("x="+x+",y="+y+",z="+z+",tx="+tx+",ty="+ty+",tz="+tz);//
	}
    }
    
    public void moveForward(double dist){
	IVec dir = frontDirection();
	dir.len(dist);
	pos.add(dir);
	target.add(dir);
	//x += dir.x;
	//y += dir.y;
	//z += dir.z;
	//tx += dir.x;
	//ty += dir.y;
	//tz += dir.z;
	update();
    }


    public IVec location(){ return pos.dup(); }
    public IVec target(){ return target.dup(); }

    public double getYaw(){ return yaw; }
    public double getPitch(){ return pitch; }
    public double getRoll(){ return roll; }
    
    public IVec getAngles(){ return new IVec(yaw, pitch, roll); }
    
    
    // front direction
    //public IVec getViewDirection(){
    public IVec frontDirection(){
	//IOut.p("yaw="+(yaw/Math.PI*180)+", pitch="+(pitch/Math.PI*180));
	
	IVec v = IVec.xaxis.dup();
	//IOut.p("(1) "+v.toString());
	v.rot(IVec.yaxis, pitch);
	//IOut.p("(2) "+v.toString());
	v.rot(IVec.zaxis, yaw);
	//IOut.p("(3) "+v.toString());
	//IOut.p(v.toString());
	return v;
    }
    
    
    public IVec rightDirection(){
	IVec v = new IVec(0, -1, 0);
	v.rot(IVec.xaxis, roll);
	v.rot(IVec.yaxis, pitch);
	v.rot(IVec.zaxis, yaw);
	return v;
    }
    
    public IVec upDirection(){
	IVec v = IVec.zaxis.dup();
	v.rot(IVec.xaxis, roll);
	v.rot(IVec.yaxis, pitch);
	v.rot(IVec.zaxis, yaw);
	return v;
    }
    
    public void focus(IBoundingBox bb){
	if(bb==null || bb.min==null || bb.max==null) return;
	IVec center = bb.min.mid(bb.max);
	IVec dir = frontDirection();
	IVec[] pts = new IVec[8];
	pts[0] = new IVec(bb.min.x,bb.min.y,bb.min.z);
	pts[1] = new IVec(bb.max.x,bb.min.y,bb.min.z);
	pts[2] = new IVec(bb.min.x,bb.max.y,bb.min.z);
	pts[3] = new IVec(bb.max.x,bb.max.y,bb.min.z);
	pts[4] = new IVec(bb.min.x,bb.min.y,bb.max.z);
	pts[5] = new IVec(bb.max.x,bb.min.y,bb.max.z);
	pts[6] = new IVec(bb.min.x,bb.max.y,bb.max.z);
	pts[7] = new IVec(bb.max.x,bb.max.y,bb.max.z);
	
	IVec cnt = glTransformMatrix.transform(center);
	for(int i=0; i<pts.length; i++) pts[i]=glTransformMatrix.transform(pts[i]);
	if(axonometric){
	    double xmin=0, ymin=0, xmax=0, ymax=0;
	    for(int i=0; i<pts.length; i++){
		if(i==0){ xmin=xmax=pts[i].x; ymin=ymax=pts[i].y; }
		else{
		    if(pts[i].x<xmin) xmin=pts[i].x;
		    else if(pts[i].x>xmax) xmax=pts[i].x;
		    if(pts[i].y<ymin) ymin=pts[i].y;
		    else if(pts[i].y>ymax) ymax=pts[i].y;
		}
	    }
	    double wid = cnt.x-xmin;
	    if(xmax-cnt.x > wid) wid=xmax-cnt.x;
	    double hei = cnt.y-ymin;
	    if(ymax-cnt.y > hei) hei=ymax-cnt.y;
	    
	    double r = wid/(screenWidth/2);
	    if(hei/(screenHeight/2) > r) r = hei/(screenHeight/2);
	    
	    dir.len(-defaultViewDistance);
	    axonRatio = r;
	}
	else{
	    double maxz=0;
	    for(int i=0; i<pts.length; i++){
		double z = 0;
		// check x
		if(pts[i].x < cnt.x) z = pts[i].z+(cnt.x-pts[i].x)/persRatio;
		else z = pts[i].z+(pts[i].x-cnt.x)/persRatio;
		if(i==0) maxz=z;
		else if(z>maxz) maxz=z;
		// check y
		if(pts[i].y < cnt.y) z = pts[i].z+(cnt.y-pts[i].y)/persRatio;
		else z = pts[i].z+(pts[i].y-cnt.y)/persRatio;
		if(z>maxz) maxz=z;
	    }
	    maxz -= cnt.z;
	    dir.len(-maxz);
	}
	setLocation(dir.add(center));
	setTarget(center);
	update();
    }
    
    /**
       converts 3d coordinates to 2d coordinates of screen whose unit is
       pixels and whose origin is top left corner and positive Y is downward
       @return returns if result is inside screen
    */
    public boolean convert(IVecI orig, IVec2 dest){
	synchronized(this){
	    
	    IVec trans = glTransformMatrix.transform(orig);
	    
	    if(axonometric){
		trans.div(axonRatio);
		dest.x=screenWidth/2 + trans.x;
		dest.y=screenHeight/2 - trans.y;
	    }
	    else{
		trans.z = -trans.z;
		
		if(trans.z < near) return false;
		
		double r = screenHeight*near/trans.z/glHeight;
		dest.x = screenWidth/2 + trans.x*r;
		dest.y = screenHeight/2 - trans.y*r;
	    }
	    if(dest.x < 0 || dest.x > screenWidth ||
	       dest.y < 0 || dest.y > screenHeight) return false;
	    return true;
	}
    }
    
    
    /**
       converts 3d coordinates to 2d. Putting depth in z component of the second argument
       @return returns if result is inside screen
    */
    public boolean convert(IVecI orig, IVec dest){
	synchronized(this){
	    
	    IVec trans = glTransformMatrix.transform(orig);
	    
	    trans.z = -trans.z;
	    if(axonometric){
		trans.div(axonRatio);
		dest.x=screenWidth/2 + trans.x;
		dest.y=screenHeight/2 - trans.y;
		dest.z=trans.z;
	    }
	    else{
		double r = screenHeight*near/trans.z/glHeight;
		dest.x = screenWidth/2 + trans.x*r;
		dest.y = screenHeight/2 - trans.y*r;
		dest.z = trans.z;
		
		if(trans.z < near) return false;
	    }
	    if(dest.x < 0 || dest.x > screenWidth ||
	       dest.y < 0 || dest.y > screenHeight) return false;
	    return true;
	}
    }
    
    
    //public void drawBG(IGraphics g){}
    
    public void drawBG(GL gl){
	if(glBGColor!=null){
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glPushMatrix();
	    gl.glLoadIdentity();
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glPushMatrix();
	    gl.glLoadIdentity();
	    gl.glDisable(GL.GL_DEPTH_TEST);
	    //if(mode.isLight()) gl.glDisable(GL.GL_LIGHTING);
	    gl.glBegin(GL.GL_QUADS);
	    //gl.glColor3d(0.3,0.5,0.7);
	    gl.glColor3dv(glBGColor[0][1],0);
	    gl.glVertex3d(-1.,-1.,0);
	    gl.glColor3dv(glBGColor[1][1],0);
	    gl.glVertex3d(1.,-1.,0);
	    //gl.glColor3d(1.,1.,1.);
	    gl.glColor3dv(glBGColor[1][0],0);
	    gl.glVertex3d(1.,1.,0);
	    //gl.glColor3d(0.9,0.9,0.9);
	    gl.glColor3dv(glBGColor[0][0],0);
	    gl.glVertex3d(-1.,1.,0);
	    gl.glEnd();
	    gl.glEnable(GL.GL_DEPTH_TEST);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glPopMatrix();
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glPopMatrix();
	}
    }
    
    
    //public void beginGLView(GL gl, IGraphics g){
    public void draw(GL gl){
	
	if(hide) return;
	
	gl.glViewport(screenX, screenY, screenWidth, screenHeight);
	
	//gl.glClear(GL.GL_DEPTH_BUFFER_BIT); //
	
	// background
	
	drawBG(gl);
	/*
	gl.glMatrixMode(GL.GL_MODELVIEW);
	gl.glPushMatrix();
	gl.glLoadIdentity();
	gl.glMatrixMode(GL.GL_PROJECTION);
	gl.glPushMatrix();
	gl.glLoadIdentity();
	gl.glDisable(GL.GL_DEPTH_TEST);
	//if(mode.isLight()) gl.glDisable(GL.GL_LIGHTING);
	gl.glBegin(GL.GL_QUADS);
	//gl.glColor3d(0.3,0.5,0.7);
	gl.glColor3dv(glBGColor[0][1],0);
	gl.glVertex3d(-1.,-1.,0);
	gl.glColor3dv(glBGColor[1][1],0);
	gl.glVertex3d(1.,-1.,0);
	//gl.glColor3d(1.,1.,1.);
	gl.glColor3dv(glBGColor[1][0],0);
	gl.glVertex3d(1.,1.,0);
	//gl.glColor3d(0.9,0.9,0.9);
	gl.glColor3dv(glBGColor[0][0],0);
	gl.glVertex3d(-1.,1.,0);
	gl.glEnd();
	gl.glEnable(GL.GL_DEPTH_TEST);
	gl.glMatrixMode(GL.GL_MODELVIEW);
	gl.glPopMatrix();
	gl.glMatrixMode(GL.GL_PROJECTION);
	gl.glPopMatrix();
	*/
	
	
	//gl.glClearColor(1f,1f,1f,1f);
	//if(IGRandom.percent(1)) gl.glClearColor(IGRandom.getf(),IGRandom.getf(),IGRandom.getf(),1f); //
	
	//gl.glDisable(GL.GL_DEPTH_TEST); // !! for transparency
	//gl.glEnable(GL.GL_DEPTH_TEST);
	
	
	
	// default light
	if(mode.isLight()){
	    //
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glPushMatrix();
	    gl.glLoadIdentity();
	    
	    
	    //gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, new float[]{0f,0f,1f,0f}, 0);
	    //gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, new float[]{.1f,.1f,.1f,1.0f}, 0);
	    ////gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, new float[]{.3f,.3f,.3f}, 0);
	    ////gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, new float[]{1f,1f,1f}, 0);
	    ////gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, new float[]{1f,1f,1f}, 0);
	    //gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, new float[]{.01f,.01f,.01f,1f}, 0);
	    //gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, new float[]{.0f,.0f,.0f,1f}, 0);
	    
	    gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, defaultGLLightPosition, 0);
	    gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, defaultGLAmbientLight, 0);
	    gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, defaultGLDiffuseLight, 0);
	    gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, defaultGLSpecularLight, 0);
	    
	    //gl.glLightModeli(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, 1); //
	    if(defaultGLTwoSidedLighting)
		gl.glLightModeli(GL.GL_LIGHT_MODEL_TWO_SIDE, 1); // this seems to cause weird behavior looking like some of normals are flipped or messed up
	    
	    //gl.glEnable(GL.GL_NORMALIZE); //
	    //gl.glEnable(GL.GL_AUTO_NORMAL); //
	    
	    gl.glEnable(GL.GL_LIGHT1);
	    gl.glEnable(GL.GL_LIGHTING);
	    
	    	    
	    gl.glPopMatrix();
	    
	}
	
	
	gl.glMatrixMode(GL.GL_PROJECTION);
	gl.glLoadIdentity();
	//gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
	
	
	if(axonometric)
	    gl.glOrtho(-glWidth/2,glWidth/2,-glHeight/2,glHeight/2,near,far);
	else gl.glFrustum(-glWidth/2,glWidth/2,-glHeight/2,glHeight/2,near,far);
	
	gl.glMatrixMode(GL.GL_MODELVIEW);
	//gl.glLoadIdentity();
	//gl.glTranslated(-x,-y,-z);
	
	gl.glLoadMatrixd(glTransformArray,0);
	
	
    }
    
    
    public void update(){
	
	synchronized(this){
	    //if(hide) return;
	    
	    if(useGL){
		IMatrix3 rot = IMatrix3.getZRotation(yaw);
		rot.mul(IMatrix3.getYRotation(pitch));
		if(roll!=0) rot.mul(IMatrix3.getXRotation(roll));
		
		//IOut.p("x="+x+", y="+y+", z="+z+", yaw="+yaw+", pitch="+pitch); //
		
		rot.invert();
		
		// coordinates conversion to openGL coordinates
		IMatrix3 conv = new IMatrix3(0,-1,0,
					     0,0,1,
					     -1,0,0);
		conv = conv.mul(rot);
		
		glTransformMatrix.setId();
		glTransformMatrix.setRange(conv, 0, 3, 0, 3);
		glTransformMatrix.mul(IMatrix4.getTranslate(-pos.x,-pos.y,-pos.z));
		
		for(int i=0; i<4; i++)
		    for(int j=0; j<4; j++)
			glTransformArray[j*4+i] = glTransformMatrix.get(i,j);
		
		//IOut.p("screenX="+screenX+", screenY="+screenY+", screenWidth="+screenWidth+", screenHeight="+screenHeight); //
		
		if(axonometric){
		    glWidth = screenWidth*axonRatio;
		    glHeight = screenHeight*axonRatio;
		    //IOut.p("axonRatio = "+axonRatio+", glWidth = "+glWidth+", glHeight = "+glHeight); //
		}
		else{
		    // constant width 
		    //glWidth = near*persRatio*2;
		    //glHeight = glWidth*screenHeight/screenWidth;
		    
		    // constant height
		    glWidth = glHeight*screenWidth/screenHeight;
		    glHeight = near*persRatio*2;
		    
		    //IOut.p("persRatio = "+persRatio+", glWidth = "+glWidth+", glHeight = "+glHeight); //
		}
		
		//if(targetPt==null){ 
		//targetPt = new IPoint(target.dup());
		//targetPt.setColor(Color.red);
	    }
	    
	}
    }
    
    
    
    public static IView getTopView(int screenX, int screenY,
				    int screenWidth, int screenHeight){
	return new IView(0,0,defaultViewDistance,
			  Math.PI/2, Math.PI/2, 0,
			  screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getBottomView(int screenX, int screenY,
				       int screenWidth, int screenHeight){
	return new IView(0,0,-defaultViewDistance,
			  Math.PI/2, -Math.PI/2, 0,
			  screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getLeftView(int screenX, int screenY,
				     int screenWidth, int screenHeight){
	return new IView(-defaultViewDistance,0,0,
			  0,0,0,
			  screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getRightView(int screenX, int screenY,
				      int screenWidth, int screenHeight){
	return new IView(defaultViewDistance,0,0,
			  Math.PI,0,0,
			  screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getFrontView(int screenX, int screenY,
				      int screenWidth, int screenHeight){
	return new IView(0,-defaultViewDistance,0,
			  Math.PI/2,0,0,
			  screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getBackView(int screenX, int screenY,
				    int screenWidth, int screenHeight){
	return new IView(0,defaultViewDistance,0,
			 -Math.PI/2,0,0,
			 screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getDefaultAxonometricView(int screenX, int screenY,
						  int screenWidth, int screenHeight){
	return new IView(defaultViewDistance/Math.sqrt(3),
			 defaultViewDistance/Math.sqrt(3),
			 defaultViewDistance/Math.sqrt(3),
			 -Math.PI*3/4,Math.atan(Math.sqrt(2)/2),0,
			 screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getDefaultPerspectiveView(int screenX, int screenY,
						  int screenWidth, int screenHeight){
	return new IView(-defaultViewDistance/Math.sqrt(3),
			 -defaultViewDistance/Math.sqrt(3),
			 defaultViewDistance/Math.sqrt(3),
			 Math.PI/4,Math.atan(Math.sqrt(2)/2),0,
			 screenX, screenY, screenWidth, screenHeight, false);
    }
    
    
}
