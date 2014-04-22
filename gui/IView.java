/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2013 Satoru Sugihara

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
import java.nio.ByteBuffer;
//import javax.media.opengl.*;

import igeo.*;

/**
   Class to define view by location, direction, axonometric/perspective mode and perspective angle.
   An instance of IView is associated with an instance of IPane.
   
   @author Satoru Sugihara
*/
public class IView{
    
    //public static double defaultNear = IConfig.nearView; //0.001;
    //public static double defaultFar = IConfig.farView; //10000; //1000; //10000;
    
    //public static double defaultAxonRatio = IConfig.axonometricRatio; //1.0;
    //public static double defaultPersRatio = IConfig.perspectiveRatio; //0.5;
    //public static double defaultViewDistance = IConfig.viewDistance; //100; //1000;
    
    public static float[] defaultGLLightPosition = {0f, 0f, 1f, 0f};
    public static float[] defaultGLAmbientLight = {.4f,.4f,.4f,1f};
    public static float[] defaultGLDiffuseLight = {.7f,.7f,.7f,1f};
    public static float[] defaultGLSpecularLight = {.0f,.0f,.0f,1f};
    
    public static boolean defaultGLTwoSidedLighting = false; //true; // when this is true, it seems to cause weird behavior looking like some of normals are flipped or messed up

    /** eye location */
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
    
    /** axonometric or perspective */
    public boolean axonometric=true; //false;
    
    /**  screen location */
    public int screenX, screenY;
    /**  screen width & height */
    public int screenWidth, screenHeight;
    
    /** perspective ratio = tan(angle of view) */
    public double persRatio=IConfig.perspectiveRatio; //defaultPersRatio;
    
    /** scale to window size */
    public double axonRatio=IConfig.axonometricRatio; //defaultAxonRatio;
    
    public double near=100.0*IConfig.nearViewRatio; //IConfig.nearView;
    public double far=100.0*IConfig.farViewRatio; //IConfig.farView;
    
    public double viewDistance = IConfig.viewDistance;
    
    //double tx, ty, tz;
    /** view target location */
    public IVec target;
    public boolean rotateAroundTarget=false;
    //public double distToTarget;
    
    //public IPoint targetPt; // for debug
    
    public IMatrix4 transformMatrix;
    public double[] transformArray;
    
    //public double glWidth, glHeight;
    public boolean useGL=false;
    
    public boolean hide=false;
    
    public IPane pane;
    
    public IGraphicMode mode=new IGraphicMode(IGraphicServer.defaultMode);
    
    //public double[][][] glBGColor = defaultGLBGColor; // null
    //public Color javaBGColor1, javaBGColor2;
    
    /** background color: one color(1x1matrix), two color(1x2matrix) or four colors(2x2 matrix) */
    public IColor[][] bgColor = new IColor[][]{
	new IColor[]{ IConfig.bgColor1, IConfig.bgColor4 },
	new IColor[]{ IConfig.bgColor2, IConfig.bgColor3 }
    };
    
    public ByteBuffer bgImageBuf;
    public int bgImageWidth;
    public int bgImageHeight;
    
    public boolean useBGTexture=false;
    public boolean bgTextureGenerated=false;
    public int bgTextureID;
    
    
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
	transformMatrix = new IMatrix4();
	transformArray = new double[16];
	update();
    }
    
    public void disableGL(){ useGL=false; }
    
    public void setMode(IGraphicMode m){ mode=m; }
    public IGraphicMode mode(){ return mode; }
    
    public boolean isHidden(){ return hide; }
    public void hide(){ hide=true; }
    public void show(){ hide=false; }
    
    public void bgColor(IColor c){
	bgColor[0][0]=bgColor[0][1]=bgColor[1][0]=bgColor[1][1]=c;
    }
    public void bgColor(IColor c1, IColor c2){
	bgColor[0][0]=bgColor[1][0]=c1;
	bgColor[0][1]=bgColor[1][1]=c2;
    }
    
    public void bgColor(IColor c1, IColor c2, IColor c3, IColor c4){
	bgColor[0][0] = c1;
	bgColor[1][0] = c2;
	bgColor[1][1] = c3;
	bgColor[0][1] = c4;
	//bgColor(new Color[][]{ new Color[]{ c1, c4 }, new Color[]{ c2, c3 } }); // necessary?
    }
    
    public void bgColor(IColor[][] c){
	// test
	for(int i=0; i<2 && i<c.length; i++)
	    for(int j=0; j<2 && j<c[i].length; j++) bgColor[i][j]=c[i][j];
    }
    
    public IColor[][] bgColor(){ return bgColor; }
    
    
    public void bgImage(ByteBuffer imgBuf, int imgWidth, int imgHeight){
	bgImageBuf = imgBuf;
	bgImageWidth = imgWidth;
	bgImageHeight = imgHeight;
    }
    
    
    public void bgTexture(int texID){
	bgTextureID = texID;
	useBGTexture=true;
    }
    
    
    public void setPane(IPane p){
	pane=p;
	//IOut.p("pane.x="+pane.x+", pane.y="+pane.y+", pane.width="+pane.width+", pane.height="+pane.height); //
	
	int origScH = screenHeight;
	
	screenX=(int)pane.getX(); screenWidth=pane.getWidth(); screenHeight=pane.getHeight();
	
	if(mode.isGL()){ // flip y
	    screenY=pane.getPanel().getHeight() - ((int)pane.getY()+pane.getHeight()); 
	}
	else{ screenY=(int)pane.getY(); }
	
	// to keep extend, not axonometric ratio
	if(axonometric&&screenHeight>0&&origScH>0){
	    //IOut.p("screenHeight="+screenHeight+", origScH="+origScH); //
	    //IOut.p("axonRatio="+axonRatio); //
	    axonRatio /= (double)screenHeight/origScH;
	    //IOut.p("axonRatio="+axonRatio); //
	}
	
	//IOut.p("x="+screenX+", y="+screenY+", w="+screenWidth+", h="+screenHeight); //
	//update(); // necessary? // ok not to have?
    }
    
    public void setScreen(int x, int y, int w, int h){
	//IOut.p("x="+x+", y="+y+", w="+w+", h="+h); //
	screenX=x; screenY=y; screenWidth=w; screenHeight=h;
	update(); //?
    }
    
    public void setScreenSize(int w, int h){ screenWidth=w; screenHeight=h; update(); }
    public void setScreenPosition(int x, int y){ screenX=x; screenY=y; }

    public double getScreenWidth(){ return screenWidth; }
    public double getScreenHeight(){ return screenHeight; }
    public double getScreenX(){ return screenX; }
    public double getScreenY(){ return screenY; }
    
    public double getAxonometricRatio(){ return axonRatio; }
    public double getPerspectiveRatio(){ return persRatio; }

    public void setAxonometricRatio(double r){
	axonRatio = r; 
	axonometric = true;
	//glWidth = screenWidth*axonRatio;
	//glHeight = screenHeight*axonRatio;
	update(); //?
    }
    public void setPerspectiveRatio(double r){
	persRatio = r;
	axonometric = false;
	
	//glWidth = near*persRatio*2;
	//glHeight = glWidth*screenHeight/screenWidth;
	
	//glHeight = near*persRatio*2;
	//glWidth = glHeight*screenWidth/screenHeight;
	
	update(); //?
    }
    public void setPerspectiveAngle(double angle){
	setPerspectiveRatio(Math.tan(angle/2));
    }
    
    public void perspective(){ axonometric=false; }
    public void axonometric(){ axonometric=true; }
    
    public boolean isAxonometric(){ return axonometric; }
    
    
    /** update near, far, viewDistance by bounding box */
    public void setParametersByBounds(IBounds bounds){

	if(bounds==null || bounds.min==null || bounds.max==null){ return; } // do nothing
	
	double avgSz = (bounds.width()+bounds.height()+bounds.depth())/3;

	if(avgSz == 0){ // just a point
	    return; // do nothing
	}
	
	if(avgSz < IConfig.tolerance){ avgSz = IConfig.tolerance; }
	
	near = avgSz * IConfig.nearViewRatio;
	far = avgSz * IConfig.farViewRatio;
	viewDistance =  avgSz * IConfig.viewDistanceRatio;
    }
    
    public void setTarget(double x, double y, double z){
	//this.tx = x; this.ty=y; this.tz = z;
	target.set(x,y,z);
    }
    public void setTarget(IVecI t){ target.set(t); }
    
    public void enableRotationAroundTarget(){ rotateAroundTarget=true; }
    public void disableRotationAroundTarget(){ rotateAroundTarget=false; }
    
    //public double distanceToTarget(){
    public double targetDistance(){
	//return(Math.sqrt((tx-x)*(tx-x)+(ty-y)*(ty-y)+(tz-z)*(tz-z)));
	//return target.diff(pos).len();
	
	//if(!rotateAroundTarget) return 0;
	if(target==null) return 0;
	return target.dist(pos);
    }
    
    public void set(double x, double y, double z){ setLocation(x,y,z); }
    public void set(IVec location){ setLocation(location); }
    public void set(double x, double y, double z,
		    double yaw, double pitch){ set(x,y,z,yaw,pitch,0); }
    public void set(IVec location, double yaw, double pitch){ set(location,yaw,pitch,0); }
    public void set(double x, double y, double z,
		    double yaw, double pitch, double roll){
	disableRotationAroundTarget();
	setLocation(x,y,z);
	setAngle(yaw,pitch,roll);
 	update();
    }
    public void set(IVec location, double yaw, double pitch, double roll){
	disableRotationAroundTarget();
	setLocation(location);
	setAngle(yaw,pitch,roll);
	update();
    }
    
    public IVec getLocation(){ return pos; }
    
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
    
    public void setAngle(double yaw, double pitch, double roll, boolean updateTarget){
	this.roll=roll; setAngle(yaw,pitch,updateTarget); 
    }
    
    public void setAngle(double yaw, double pitch){ setAngle(yaw,pitch,false); }
    
    public void setAngle(double yaw, double pitch, boolean updateTarget){
	this.yaw=yaw; this.pitch=pitch;
	
	// update(); //// no need to update?
	
	if(updateTarget){ // update to the angle with the default view distance
	    IVec dir = frontDirection();
	    dir.len(viewDistance);
	    target.set(dir.add(pos));
	}
	else if(rotateAroundTarget){
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
    
    public void focus(IBounds bb){
	if(bb==null || bb.min==null || bb.max==null) return;
	IVec center = bb.min.mid(bb.max);
	IVec dir = frontDirection();
	
	if(bb.min.x==bb.max.x&&bb.min.y==bb.max.y&&bb.min.z==bb.max.z){ // just point
	    if(axonometric){
		dir.len(-viewDistance);
	    }
	    else{
		dir.len(-targetDistance());
	    }
	}
	else{
	    IVec[] pts = new IVec[8];
	    pts[0] = new IVec(bb.min.x,bb.min.y,bb.min.z);
	    pts[1] = new IVec(bb.max.x,bb.min.y,bb.min.z);
	    pts[2] = new IVec(bb.min.x,bb.max.y,bb.min.z);
	    pts[3] = new IVec(bb.max.x,bb.max.y,bb.min.z);
	    pts[4] = new IVec(bb.min.x,bb.min.y,bb.max.z);
	    pts[5] = new IVec(bb.max.x,bb.min.y,bb.max.z);
	    pts[6] = new IVec(bb.min.x,bb.max.y,bb.max.z);
	    pts[7] = new IVec(bb.max.x,bb.max.y,bb.max.z);
	    
	    IVec cnt = transformMatrix.transform(center);
	    for(int i=0; i<pts.length; i++) pts[i]=transformMatrix.transform(pts[i]);
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
	    
		dir.len(-viewDistance);
		axonRatio = r;
	    }
	    else{
		double maxz=0;
		for(int i=0; i<pts.length; i++){
		    double z = 0;
		    // check x
		    //if(pts[i].x < cnt.x) z = pts[i].z+(cnt.x-pts[i].x)/persRatio;
		    //else z = pts[i].z+(pts[i].x-cnt.x)/persRatio;
		    
		    //z = pts[i].z+Math.abs(cnt.x-pts[i].x)/persRatio;
		    z = pts[i].z+Math.abs(cnt.x-pts[i].x)/(persRatio * screenWidth/screenHeight);
		    
		    if(i==0) maxz=z;
		    else if(z>maxz) maxz=z;
		    // check y
		    //if(pts[i].y < cnt.y) z = pts[i].z+(cnt.y-pts[i].y)/persRatio;
		    //else z = pts[i].z+(pts[i].y-cnt.y)/persRatio;
		    
		    z = pts[i].z+Math.abs(cnt.y-pts[i].y)/persRatio;
		    
		if(z>maxz) maxz=z;
		}
		maxz -= cnt.z;
		dir.len(-maxz);
	    }
	}
	setLocation(dir.add(center));
	setTarget(center);
	enableRotationAroundTarget(); // !! added 2011/12/29
	update();
    }
    
    /**
       converts 3d coordinates to 2d coordinates of screen whose unit is
       pixels and whose origin is top left corner and positive Y is downward
       @return returns if result is inside screen
    */
    public boolean convert(IVecI orig, IVec2 dest){
	synchronized(this){
	    IVec trans = transformMatrix.transform(orig);
	    if(axonometric){
		trans.div(axonRatio);
		dest.x=screenWidth/2 + trans.x;
		dest.y=screenHeight/2 - trans.y;
	    }
	    else{
		trans.z = -trans.z;
		if(trans.z < near) return false;
		//double r = screenHeight*near/trans.z/glHeight;
		double r = screenHeight/(trans.z*persRatio*2);
		
		dest.x = screenWidth/2 + trans.x*r;
		dest.y = screenHeight/2 - trans.y*r;
	    }
	    if(dest.x < 0 || dest.x > screenWidth ||
	       dest.y < 0 || dest.y > screenHeight) return false;
	    return true;
	}
    }
    
    
    /**
       converts 3d coordinates to 2d coordinates of screen whose unit is
       pixels and whose origin is top left corner and positive Y is downward
       @return returns if result is inside screen
    */
    public boolean convert(IVecI orig, IVec2f dest){
	synchronized(this){
	    IVec trans = transformMatrix.transform(orig);
	    if(axonometric){
		trans.div(axonRatio);
		dest.x = (float)(screenWidth/2 + trans.x);
		dest.y = (float)(screenHeight/2 - trans.y);
	    }
	    else{
		trans.z = -trans.z;
		if(trans.z < near) return false;
		//float r = (float)(screenHeight*near/trans.z/glHeight);
		float r = (float)(screenHeight/(trans.z*persRatio*2));
		
		dest.x = (float)screenWidth/2 + ((float)trans.x)*r;
		dest.y = (float)screenHeight/2 - ((float)trans.y)*r;
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
	    IVec trans = transformMatrix.transform(orig);
	    trans.z = -trans.z;
	    if(axonometric){
		trans.div(axonRatio);
		dest.x=screenWidth/2 + trans.x;
		dest.y=screenHeight/2 - trans.y;
		dest.z=trans.z;
	    }
	    else{
		//double r = screenHeight*near/trans.z/glHeight;
		double r = screenHeight/(trans.z*persRatio*2);
		
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
    
    
    /**
       converts 3d coordinates to 2d. Putting depth in z component of the return vector. no check if it's inside screen
       @return 3d vector whose x and y are 2d coordinates in screen and z is depth
    */
    public IVec convert(IVecI orig){
	synchronized(this){
	    IVec trans = transformMatrix.transform(orig);
	    trans.z = -trans.z;
	    if(axonometric){
		trans.div(axonRatio);
		trans.x += screenWidth/2;
		trans.y = screenHeight/2 - trans.y;
		return trans;
	    }
	    // perspective
	    //double r = screenHeight*near/trans.z/glHeight;
	    double r = screenHeight/(trans.z*persRatio*2);
	    
	    trans.x = screenWidth/2 + trans.x*r;
	    trans.y = screenHeight/2 - trans.y*r;
	    return trans;
	}
    }
    
    public void update(){
	
	synchronized(this){
	    //if(hide) return;
	    
	    if(useGL){
		
		
		IMatrix3 rot = IMatrix3.getZRotation(yaw);
		rot.mul(IMatrix3.getYRotation(pitch));
		if(roll!=0) rot.mul(IMatrix3.getXRotation(roll));
		
		//IOut.p("x="+pos.x+", y="+pos.y+", z="+pos.z+", yaw="+yaw+", pitch="+pitch); //
		
		rot.invert();
		
		// coordinates conversion to openGL coordinates
		IMatrix3 conv = new IMatrix3( 0,-1, 0,
					      0, 0, 1,
					     -1, 0, 0);
		conv = conv.mul(rot);
		
		transformMatrix.setId();
		transformMatrix.setRange(conv, 0, 3, 0, 3);
		transformMatrix.mul(IMatrix4.getTranslate(-pos.x,-pos.y,-pos.z));
		
		for(int i=0; i<4; i++)
		    for(int j=0; j<4; j++)
			transformArray[j*4+i] = transformMatrix.get(i,j);
		
		//IOut.p("screenX="+screenX+", screenY="+screenY+", screenWidth="+screenWidth+", screenHeight="+screenHeight); //
		
		/*
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
		    glHeight = near*persRatio*2;
		    glWidth = glHeight*screenWidth/screenHeight;
		}
		*/
	    }
	}
    }
    
    public void setTop(){ setTop(0,0); }
    public void setTop(double x, double y){ setTop(x,y,viewDistance); }
    public void setTop(double x, double y, double z){ setTop(x,y,z,axonRatio); }
    public void setTop(double x, double y, double z, double axonometricRatio){
	setLocation(x,y,z);
	setAngle(Math.PI/2, Math.PI/2, true);
	setAxonometricRatio(axonometricRatio);

	if( pane.getPanel() != null ){ pane.getPanel().skipAutoFocus(); }
	
    }
    public void setBottom(){ setBottom(0,0); }
    public void setBottom(double x, double y){ setBottom(x,y,-viewDistance); }
    public void setBottom(double x, double y, double z){ setBottom(x,y,z,axonRatio); }
    public void setBottom(double x, double y, double z, double axonometricRatio){
	setLocation(x,y,z);
	setAngle(Math.PI/2, -Math.PI/2, true);
	setAxonometricRatio(axonometricRatio);

	if( pane.getPanel() != null ){ pane.getPanel().skipAutoFocus(); }
    }
    public void setLeft(){ setLeft(0,0); }
    public void setLeft(double y, double z){ setLeft(-viewDistance,y,z); }
    public void setLeft(double x, double y, double z){ setLeft(x,y,z,axonRatio); }
    public void setLeft(double x, double y, double z, double axonometricRatio){
	setLocation(x,y,z);
	setAngle(0,0,true);
	setAxonometricRatio(axonometricRatio);

	if( pane.getPanel() != null ){ pane.getPanel().skipAutoFocus(); }
    }
    public void setRight(){ setRight(0,0); }
    public void setRight(double y, double z){ setRight(viewDistance,y,z); }
    public void setRight(double x, double y, double z){ setRight(x,y,z,axonRatio); }
    public void setRight(double x, double y, double z, double axonometricRatio){
	setLocation(x,y,z);
	setAngle(Math.PI,0,true);
	setAxonometricRatio(axonometricRatio);

	if( pane.getPanel() != null ){ pane.getPanel().skipAutoFocus(); }
    }
    public void setFront(){ setFront(0,0); }
    public void setFront(double x, double z){ setFront(x,-viewDistance,z); }
    public void setFront(double x, double y, double z){ setFront(x,y,z,axonRatio); }
    public void setFront(double x, double y, double z, double axonometricRatio){
	setLocation(x,y,z);
	setAngle(Math.PI/2,0,true);
	setAxonometricRatio(axonometricRatio);

	if( pane.getPanel() != null ){ pane.getPanel().skipAutoFocus(); }
    }
    public void setBack(){ setBack(0,0); }
    public void setBack(double x, double z){ setBack(x,viewDistance,z); }
    public void setBack(double x, double y, double z){ setBack(x,y,z,axonRatio); }
    public void setBack(double x, double y, double z, double axonometricRatio){
	setLocation(x,y,z);
	setAngle(-Math.PI/2,0,true);
	setAxonometricRatio(axonometricRatio);

	if( pane.getPanel() != null ){ pane.getPanel().skipAutoFocus(); }
    }
    
    public void setPerspective(){
	setPerspective(-viewDistance/Math.sqrt(3),
		       -viewDistance/Math.sqrt(3),
		       viewDistance/Math.sqrt(3));
    }
    public void setPerspective(double x, double y, double z){
	setPerspective(x,y,z,Math.PI/4,Math.atan(Math.sqrt(2)/2));
    }
    public void setPerspective(double x, double y, double z,
			       double yaw, double pitch){
	setLocation(x,y,z);
	setAngle(yaw,pitch,true);
	perspective();
	//setPerspectiveRatio(defaultPersRatio); // keep the original perspective ratio
	update();

	if( pane.getPanel() != null ){ pane.getPanel().skipAutoFocus(); }
    }
    
    public void setPerspective(double perspectiveAngle){
	setPerspective(-viewDistance/Math.sqrt(3),
		       -viewDistance/Math.sqrt(3),
		       viewDistance/Math.sqrt(3), perspectiveAngle);
	setAngle(Math.PI/4,Math.atan(Math.sqrt(2)/2),true);
	perspective();
	setPerspectiveAngle(perspectiveAngle);
    }
    public void setPerspective(double x, double y, double z, double perspectiveAngle){
	setPerspective(x,y,z,Math.PI/4,Math.atan(Math.sqrt(2)/2));
	setPerspectiveAngle(perspectiveAngle); // added 20140315
    }
    public void setPerspective(double x, double y, double z,
			       double yaw, double pitch,
			       double perspectiveAngle){
	setLocation(x,y,z);
	setAngle(yaw,pitch,true);
	perspective();
	setPerspectiveAngle(perspectiveAngle);

	if( pane.getPanel() != null ){ pane.getPanel().skipAutoFocus(); }
    }
    
    public void setAxonometric(){
	setAxonometric(-viewDistance/Math.sqrt(3),
		       -viewDistance/Math.sqrt(3),
		       viewDistance/Math.sqrt(3));
    }
    public void setAxonometric(double x, double y, double z){
	setAxonometric(x,y,z,Math.PI/4,Math.atan(Math.sqrt(2)/2));
    }
    public void setAxonometric(double x, double y, double z,
			       double axonometricRatio){
	setAxonometric(x,y,z,Math.PI/4,Math.atan(Math.sqrt(2)/2),axonometricRatio);
    }
    public void setAxonometric(double x, double y, double z, double yaw, double pitch){
	setAxonometric(x,y,z,yaw,pitch,axonRatio);
    }
    public void setAxonometric(double x, double y, double z, double yaw, double pitch, double axonometricRatio){
	setLocation(x,y,z);
	setAngle(yaw,pitch,true);
	setAxonometricRatio(axonometricRatio);
	
	if( pane.getPanel() != null ){ pane.getPanel().skipAutoFocus(); }
    }
    
    
    public static IView getTopView(int screenX, int screenY,
				   int screenWidth, int screenHeight){
	return new IView(0,0,IConfig.viewDistance,
			 Math.PI/2, Math.PI/2, 0,
			 screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getBottomView(int screenX, int screenY,
				      int screenWidth, int screenHeight){
	return new IView(0,0,-IConfig.viewDistance,
			 Math.PI/2, -Math.PI/2, 0,
			 screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getLeftView(int screenX, int screenY,
				    int screenWidth, int screenHeight){
	return new IView(-IConfig.viewDistance,0,0,
			 0,0,0,
			 screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getRightView(int screenX, int screenY,
				     int screenWidth, int screenHeight){
	return new IView(IConfig.viewDistance,0,0,
			 Math.PI,0,0,
			 screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getFrontView(int screenX, int screenY,
				     int screenWidth, int screenHeight){
	return new IView(0,-IConfig.viewDistance,0,
			 Math.PI/2,0,0,
			 screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getBackView(int screenX, int screenY,
				    int screenWidth, int screenHeight){
	return new IView(0,IConfig.viewDistance,0,
			 -Math.PI/2,0,0,
			 screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getDefaultAxonometricView(int screenX, int screenY,
						  int screenWidth, int screenHeight){
	return new IView(-IConfig.viewDistance/Math.sqrt(3),
			 -IConfig.viewDistance/Math.sqrt(3),
			 IConfig.viewDistance/Math.sqrt(3),
			 Math.PI/4,Math.atan(Math.sqrt(2)/2),0,
			 screenX, screenY, screenWidth, screenHeight, true);
    }
    public static IView getDefaultPerspectiveView(int screenX, int screenY,
						  int screenWidth, int screenHeight){
	return new IView(-IConfig.viewDistance/Math.sqrt(3),
			 -IConfig.viewDistance/Math.sqrt(3),
			 IConfig.viewDistance/Math.sqrt(3),
			 Math.PI/4,Math.atan(Math.sqrt(2)/2),0,
			 screenX, screenY, screenWidth, screenHeight, false);
    }
    
    
}
