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

import javax.media.opengl.*;
import java.awt.*;
import java.util.ArrayList;

import igeo.*;

/**
   Class of Graphics like java.awt.Graphics to wrap all possible graphic mode,
   with OpenGL in older version used in Processing 1.5
   
   @author Satoru Sugihara
*/
public class IGraphicsGL1 implements IGraphicsGL{
    
    // constants
    public static float[] defaultGLLightPosition = {0f, 0f, 1f, 0f};
    public static float[] defaultGLAmbientLight = {.4f,.4f,.4f,1f};
    public static float[] defaultGLDiffuseLight = {.7f,.7f,.7f,1f};
    public static float[] defaultGLSpecularLight = {.0f,.0f,.0f,1f};
    
    public static boolean defaultGLTwoSidedLighting = false; //true; // when this is true, it seems to cause weird behavior looking like some of normals are flipped or messed up
    
    
    public GL gl;
    public Graphics2D g;
    public IView view;
        
    public double[][][] bgColor = new double[2][2][3];
    
    public boolean firstDraw=true;
    
    
    public IGraphicsGL1(){}
    
    public IView view(){ return view; }
    
    
    public void setGL(GL gl){ this.gl=gl; }
    public void setGraphics(Graphics2D g){ this.g=g; }
    
    public GL getGL(){ return gl; }
    public Graphics2D getGraphics(){ return g; }
    
    public IGraphicMode.GraphicType type(){ return IGraphicMode.GraphicType.GL; }
    
    public void drawBG(GL gl, IView v){
	
	if(view.bgColor!=null){
	    for(int i=0; i<bgColor.length; i++){
		for(int j=0; j<bgColor[i].length; j++){
		    bgColor[i][j][0] = (double)(view.bgColor[i][j].getRed())/255.0;
		    bgColor[i][j][1] = (double)(view.bgColor[i][j].getGreen())/255.0;
		    bgColor[i][j][2] = (double)(view.bgColor[i][j].getBlue())/255.0;
		}
	    }
	    
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glPushMatrix();
	    gl.glLoadIdentity();
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glPushMatrix();
	    gl.glLoadIdentity();
	    
	    //if(IConfig.depthSort) gl.glDisable(GL.GL_DEPTH_TEST);
	    //if(mode.isLight()) gl.glDisable(GL.GL_LIGHTING);
	    gl.glBegin(GL.GL_QUADS);
	    gl.glColor3dv(bgColor[0][1],0);
	    gl.glVertex3d(-1.,-1.,0);
	    gl.glColor3dv(bgColor[1][1],0);
	    gl.glVertex3d(1.,-1.,0);
	    gl.glColor3dv(bgColor[1][0],0);
	    gl.glVertex3d(1.,1.,0);
	    gl.glColor3dv(bgColor[0][0],0);
	    gl.glVertex3d(-1.,1.,0);
	    gl.glEnd();
	    //if(IConfig.depthSort) gl.glEnable(GL.GL_DEPTH_TEST);
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glPopMatrix();
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glPopMatrix();
	}
    }
    
    public void setBGImage(String imageFilename){} // do nothing for the moment
    
    public void drawView(IView view){
	
	gl.glViewport(view.screenX, view.screenY, view.screenWidth, view.screenHeight);
	
	//gl.glClear(GL.GL_DEPTH_BUFFER_BIT); //
	
	// background
	
	if(IConfig.clearBG || firstDraw){
	    gl.glDisable(GL.GL_DEPTH_TEST); // no depth for background
	    drawBG(gl, view);
	}
	
	if(IConfig.depthSort) gl.glEnable(GL.GL_DEPTH_TEST);
	// should DEPTH_TEST be changed to set to hint[ENABLE_DEPTH_TEST] later?
	
	
	//gl.glDisable(GL.GL_DEPTH_TEST); // !! for transparency
	//gl.glEnable(GL.GL_DEPTH_TEST);
	
	// default light
	if(view.mode.isLight()){
	    //
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glPushMatrix();
	    gl.glLoadIdentity();
	    
	    gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, defaultGLLightPosition, 0);
	    gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, defaultGLAmbientLight, 0);
	    gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, defaultGLDiffuseLight, 0);
	    gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR,defaultGLSpecularLight, 0);
	    
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
	
	if(view.axonometric){
	    double glWidth = view.screenWidth*view.axonRatio;
	    double glHeight = view.screenHeight*view.axonRatio;
	    gl.glOrtho(-glWidth/2,glWidth/2,-glHeight/2,glHeight/2,view.near,view.far);
	}
	else{
	    double glHeight = view.near*view.persRatio*2;
	    double glWidth = glHeight*view.screenWidth/view.screenHeight;
	    gl.glFrustum(-glWidth/2,glWidth/2,-glHeight/2,glHeight/2,view.near,view.far);
	}
	
	gl.glMatrixMode(GL.GL_MODELVIEW);
	//gl.glLoadIdentity();
	//gl.glTranslated(-x,-y,-z);
	
	gl.glLoadMatrixd(view.transformArray,0);
	
    }
    
    
    public void draw(ArrayList<IGraphicI> objects, IView v){
	
	view = v; // current view
	
	//if(view==null || view.hide) return; // view==null is already checked in IPane
	if(view.hide) return;
	
	drawView(view);
	
	if(objects!=null){
	    if(IConfig.drawOrderForward){
		for(int i=0; i<objects.size(); i++)
		    if(objects.get(i).isVisible()) objects.get(i).draw(this);
	    }
	    else{
		for(int i=objects.size()-1; i>=0; i--)
		    if(objects.get(i).isVisible()) objects.get(i).draw(this);
	    }
	}
	
	if(view.mode().isLight()){
	    gl.glDisable(GL.GL_LIGHTING);
	    gl.glDisable(GL.GL_LIGHT1);
	}
	
	if(g!=null){ // overlay
	    
	    // draw here?
	    // border
	    if(view.pane!=null && 
	       view.pane.getBorderWidth()>0&&
	       (view.pane.getPanel().getWidth()!=view.pane.getWidth() ||view.pane.getPanel().getHeight()!=view.pane.getHeight())){
		g.setColor(new Color(view.pane.getBorderColor(),true));
		//g.setStroke(view.pane.getBorderStroke());
		g.setStroke(new BasicStroke(view.pane.getBorderWidth()));
		//g.drawRect(view.x,view.y,view.width-1,view.height-1);
		g.drawRect((int)view.pane.getX(),(int)view.pane.getY(),view.pane.getWidth(),view.pane.getHeight());
	    }
	}
    }
    
    public boolean firstDraw(){ return firstDraw; }
    public void firstDraw(boolean f){ firstDraw=f; }
    
    public void clr(IColor c){
	//clr((float)c.getRed()/255,(float)c.getGreen()/255,(float)c.getBlue()/255,(float)c.getAlpha()/255);
	//clr(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
	//gl.glColor4f(c.red(),c.green(),c.blue(),c.alpha());
	gl.glColor4fv(c.rgba(),0);
    }
    public void clr(float[] rgba){ gl.glColor4fv(rgba,0); }
    public void clr(float red, float green, float blue, float alpha){
	gl.glColor4f(red/255, green/255, blue/255, alpha/255);
    }
    public void clr(float red, float green, float blue){ clr(red,green,blue,255); }
    
    /** in GL, stroke color and fill color use both same glColor */
    public void stroke(IColor c){ clr(c); }
    public void stroke(float[] rgba){ clr(rgba); }
    public void stroke(float red, float green, float blue, float alpha){
	clr(red,green,blue,alpha);
    }
    public void stroke(float red, float green, float blue){ clr(red,green,blue); }
    
    public void weight(double w){ gl.glLineWidth((float)w); }
    public void weight(float w){ gl.glLineWidth(w); }
    
    
    public void diffuse(float[] rgba){
	gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, rgba, 0);
    }
    public void diffuse(float r, float g, float b, float a){
	diffuse(new float[]{r/255,g/255,b/255,a/255});
    }
    public void diffuse(float r, float g, float b){ diffuse(r,g,b,255); }
    public void diffuse(IColor c){ diffuse(c.rgba()); }
    
    public void ambient(float[] rgba){
	gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, rgba, 0);
    }
    public void ambient(float r, float g , float b, float a){
	ambient(new float[]{r/255,g/255,b/255,a/255});
    }
    public void ambient(float r, float g , float b){ ambient(r,g,b,255); }
    public void ambient(IColor c){ ambient(c.rgba()); }
    
    public void specular(float[] rgba){
	gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, rgba, 0);
    }
    public void specular(float r, float g , float b, float a){
	specular(new float[]{r/255,g/255,b/255,a/255});
    }
    public void specular(float r, float g , float b){ specular(r,g,b,255); }
    public void specular(IColor c){ specular(c.rgba()); }
    
    public void emissive(float[] rgba){
	gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, rgba, 0);
    }
    public void emissive(float r, float g , float b, float a){
	emissive(new float[]{r/255,g/255,b/255,a/255});
    }
    public void emissive(float r, float g , float b){ emissive(r,g,b,255); }
    public void emissive(IColor c){ emissive(c.rgba()); }
    
    public void shininess(float s){
	gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS,s);
    }
    
    public void enableLight(){
	gl.glEnable(GL.GL_LIGHT1);
	gl.glEnable(GL.GL_LIGHTING);
    }
    public void disableLight(){
	gl.glDisable(GL.GL_LIGHTING);
	gl.glDisable(GL.GL_LIGHT1);
    }
    
    public void pointSize(float sz){ gl.glPointSize(sz); }
    
    public void drawPoint(IVec p){
	gl.glBegin(GL.GL_POINTS);
	gl.glVertex3d(p.x,p.y,p.z);
        gl.glEnd();
    }
    public void drawPoints(IVec[] p){
	gl.glBegin(GL.GL_POINTS);
	for(int i=0; i<p.length; i++){ gl.glVertex3d(p[i].x,p[i].y,p[i].z); }
        gl.glEnd();
    }
    public void drawLines(IVec[] p){
	gl.glBegin(GL.GL_LINES);
	for(int i=0; i<p.length; i++){ gl.glVertex3d(p[i].x,p[i].y,p[i].z); }
        gl.glEnd();
    }
    public void drawLineStrip(IVec[] p){
	gl.glBegin(GL.GL_LINE_STRIP);
	for(int i=0; i<p.length; i++){ gl.glVertex3d(p[i].x,p[i].y,p[i].z); }
        gl.glEnd();
    }
    public void drawLineLoop(IVec[] p){
	gl.glBegin(GL.GL_LINE_LOOP);
	for(int i=0; i<p.length; i++){ gl.glVertex3d(p[i].x,p[i].y,p[i].z); }
        gl.glEnd();
    }
    public void drawPolygon(IVec[] pts, IVec[] nml){
	gl.glBegin(GL.GL_POLYGON);
	for(int i=0; i<pts.length; i++){
	    gl.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl.glEnd();
    }
    public void drawPolygon(IVec[] pts){
	gl.glBegin(GL.GL_POLYGON);
	for(int i=0; i<pts.length; i++){ gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl.glEnd();
    }
    public void drawQuads(IVec[] pts){
	gl.glBegin(GL.GL_QUADS);
	for(int i=0; i<pts.length; i++){ gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl.glEnd();
    }
    public void drawQuads(IVec[] pts, IVec[] nml){
	gl.glBegin(GL.GL_QUADS);
	for(int i=0; i<pts.length; i++){
	    gl.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl.glEnd();
    }
    public void drawQuadStrip(IVec[] pts){
	gl.glBegin(GL.GL_QUAD_STRIP);
	for(int i=0; i<pts.length; i++){ gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl.glEnd();
    }
    public void drawQuadStrip(IVec[] pts, IVec[] nml){
	gl.glBegin(GL.GL_QUAD_STRIP);
	for(int i=0; i<pts.length; i++){
	    gl.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl.glEnd();
    }
    public void drawQuadMatrix(IVec[][] pts){
	for(int i=0; i<pts.length-1; i++){
	    gl.glBegin(GL.GL_QUAD_STRIP);
	    for(int j=0; j<pts[i].length; j++){
		gl.glVertex3d(pts[i][j].x,pts[i][j].y,pts[i][j].z);
		gl.glVertex3d(pts[i+1][j].x,pts[i+1][j].y,pts[i+1][j].z);
	    }
	    gl.glEnd();
	}
    }
    public void drawQuadMatrix(IVec[][] pts, IVec[][] nml){
	for(int i=0; i<pts.length-1; i++){
	    gl.glBegin(GL.GL_QUAD_STRIP);
	    for(int j=0; j<pts[i].length; j++){
		gl.glNormal3d(nml[i][j].x,nml[i][j].y,nml[i][j].z);
		gl.glVertex3d(pts[i][j].x,pts[i][j].y,pts[i][j].z);
		gl.glNormal3d(nml[i+1][j].x,nml[i+1][j].y,nml[i+1][j].z);
		gl.glVertex3d(pts[i+1][j].x,pts[i+1][j].y,pts[i+1][j].z);
	    }
	    gl.glEnd();
	}
    }
    public void drawTriangles(IVec[] pts, IVec[] nml){
	gl.glBegin(GL.GL_TRIANGLES);
	for(int i=0; i<pts.length; i++){
	    gl.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl.glEnd();
    }
    public void drawTriangles(IVec[] pts){
	gl.glBegin(GL.GL_TRIANGLES);
	for(int i=0; i<pts.length; i++){ gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl.glEnd();
    }
    public void drawTriangleStrip(IVec[] pts, IVec[] nml){
	gl.glBegin(GL.GL_TRIANGLE_STRIP);
	for(int i=0; i<pts.length; i++){
	    gl.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl.glEnd();
    }
    public void drawTriangleStrip(IVec[] pts){
	gl.glBegin(GL.GL_TRIANGLE_STRIP);
	for(int i=0; i<pts.length; i++){ gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl.glEnd();
    }
    public void drawTriangleFan(IVec[] pts, IVec[] nml){
	gl.glBegin(GL.GL_TRIANGLE_FAN);
	for(int i=0; i<pts.length; i++){
	    gl.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl.glEnd();
    }
    public void drawTriangleFan(IVec[] pts){
	gl.glBegin(GL.GL_TRIANGLE_FAN);
	for(int i=0; i<pts.length; i++){ gl.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl.glEnd();
    }
    
}
