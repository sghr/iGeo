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
   Class of Graphics like java.awt.Graphics to wrap all possible graphic mode
   (Currently Java AWT and OpenGL).
   
   @author Satoru Sugihara
*/
public class IGraphicsGL implements IGraphics3D{
    
    // constants
    public static float[] defaultGLLightPosition = {0f, 0f, 1f, 0f};
    public static float[] defaultGLAmbientLight = {.4f,.4f,.4f,1f};
    public static float[] defaultGLDiffuseLight = {.7f,.7f,.7f,1f};
    public static float[] defaultGLSpecularLight = {.0f,.0f,.0f,1f};
    
    public static boolean defaultGLTwoSidedLighting = false; //true; // when this is true, it seems to cause weird behavior looking like some of normals are flipped or messed up
    
    
    public GL gl;
    public GL2 gl2;
    public Graphics2D g;
    public IView view;
        
    public double[][][] bgColor = new double[2][2][3];
    
    public boolean firstDraw=true;
    
    
    public IGraphicsGL(){}
    
    public IView view(){ return view; }
    
    
    public void setGL(GL gl){ this.gl=gl; gl2 = gl.getGL2(); }
    public void setGraphics(Graphics2D g){ this.g=g; }
    
    public GL getGL(){ return gl; }
    public GL2 getGL2(){ return gl2; }
    public Graphics2D getGraphics(){ return g; }
    
    public IGraphicMode.GraphicType type(){ return IGraphicMode.GraphicType.GL; }
    
    public void drawBG(GL gl, IView v){
	
	GL2 gl2 = gl.getGL2(); // jogl 2.0
	
	if(view.bgColor!=null){
	    for(int i=0; i<bgColor.length; i++){
		for(int j=0; j<bgColor[i].length; j++){
		    bgColor[i][j][0] = (double)(view.bgColor[i][j].getRed())/255.0;
		    bgColor[i][j][1] = (double)(view.bgColor[i][j].getGreen())/255.0;
		    bgColor[i][j][2] = (double)(view.bgColor[i][j].getBlue())/255.0;
		}
	    }
	    
	    //  jogl2?
	    gl2.glMatrixMode(GL2.GL_MODELVIEW); // not in processing 2.0
	    gl2.glPushMatrix(); // not in processing 2.0
	    gl2.glLoadIdentity(); // not in processing 2.0
	    gl2.glMatrixMode(GL2.GL_PROJECTION); // not in processing 2.0
	    gl2.glPushMatrix(); // not in processing 2.0
	    gl2.glLoadIdentity(); // not in processing 2.0
	    
	    //if(IConfig.depthSort) gl2.glDisable(GL2.GL_DEPTH_TEST);
	    //if(mode.isLight()) gl2.glDisable(GL2.GL_LIGHTING);
	    gl2.glBegin(GL2.GL_QUADS);
	    gl2.glColor3dv(bgColor[0][1],0);
	    gl2.glVertex3d(-1.,-1.,0);
	    gl2.glColor3dv(bgColor[1][1],0);
	    gl2.glVertex3d(1.,-1.,0);
	    gl2.glColor3dv(bgColor[1][0],0);
	    gl2.glVertex3d(1.,1.,0);
	    gl2.glColor3dv(bgColor[0][0],0);
	    gl2.glVertex3d(-1.,1.,0);
	    gl2.glEnd();
	    //if(IConfig.depthSort) gl2.glEnable(GL2.GL_DEPTH_TEST);
	    gl2.glMatrixMode(GL2.GL_MODELVIEW);
	    gl2.glPopMatrix();
	    gl2.glMatrixMode(GL2.GL_PROJECTION);
	    gl2.glPopMatrix();
	}
    }
    
    public void drawView(IView view){
	
	gl2.glViewport(view.screenX, view.screenY, view.screenWidth, view.screenHeight);
	
	//gl2.glClear(GL2.GL_DEPTH_BUFFER_BIT); //
	
	// background
	
	if(IConfig.clearBG || firstDraw){
	    gl2.glDisable(GL2.GL_DEPTH_TEST); // no depth for background
	    drawBG(gl, view);
	}
	
	if(IConfig.depthSort) gl2.glEnable(GL2.GL_DEPTH_TEST);
	// should DEPTH_TEST be changed to set to hint[ENABLE_DEPTH_TEST] later?
	
	
	//gl2.glDisable(GL2.GL_DEPTH_TEST); // !! for transparency
	//gl2.glEnable(GL2.GL_DEPTH_TEST);
	
	// default light
	if(view.mode.isLight()){
	    //
	    gl2.glMatrixMode(GL2.GL_MODELVIEW);
	    gl2.glPushMatrix();
	    gl2.glLoadIdentity();
	    
	    gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, defaultGLLightPosition, 0);
	    gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, defaultGLAmbientLight, 0);
	    gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, defaultGLDiffuseLight, 0);
	    gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR,defaultGLSpecularLight, 0);
	    
	    //gl2.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, 1); //
	    if(defaultGLTwoSidedLighting)
		gl2.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, 1); // this seems to cause weird behavior looking like some of normals are flipped or messed up
	    
	    //gl2.glEnable(GL2.GL_NORMALIZE); //
	    //gl2.glEnable(GL2.GL_AUTO_NORMAL); //
	    
	    gl2.glEnable(GL2.GL_LIGHT1);
	    gl2.glEnable(GL2.GL_LIGHTING);
	    	    	    
	    gl2.glPopMatrix();
	}
	
	gl2.glMatrixMode(GL2.GL_PROJECTION);
	gl2.glLoadIdentity();
	//gl2.glClear(GL2.GL_DEPTH_BUFFER_BIT);
	
	if(view.axonometric){
	    double glWidth = view.screenWidth*view.axonRatio;
	    double glHeight = view.screenHeight*view.axonRatio;
	    gl2.glOrtho(-glWidth/2,glWidth/2,-glHeight/2,glHeight/2,view.near,view.far);
	}
	else{
	    double glHeight = view.near*view.persRatio*2;
	    double glWidth = glHeight*view.screenWidth/view.screenHeight;
	    gl2.glFrustum(-glWidth/2,glWidth/2,-glHeight/2,glHeight/2,view.near,view.far);
	}
	
	gl2.glMatrixMode(GL2.GL_MODELVIEW);
	//gl2.glLoadIdentity();
	//gl2.glTranslated(-x,-y,-z);
	
	gl2.glLoadMatrixd(view.transformArray,0);
	
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
	    gl2.glDisable(GL2.GL_LIGHTING);
	    gl2.glDisable(GL2.GL_LIGHT1);
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
	//gl2.glColor4f(c.red(),c.green(),c.blue(),c.alpha());
	gl2.glColor4fv(c.rgba(),0);
    }
    public void clr(float[] rgba){ gl2.glColor4fv(rgba,0); }
    public void clr(float red, float green, float blue, float alpha){
	gl2.glColor4f(red/255, green/255, blue/255, alpha/255);
    }
    public void clr(float red, float green, float blue){ clr(red,green,blue,255); }
    
    /** in GL, stroke color and fill color use both same glColor */
    public void stroke(IColor c){ clr(c); }
    public void stroke(float[] rgba){ clr(rgba); }
    public void stroke(float red, float green, float blue, float alpha){
	clr(red,green,blue,alpha);
    }
    public void stroke(float red, float green, float blue){ clr(red,green,blue); }
    
    public void weight(double w){ gl2.glLineWidth((float)w); }
    public void weight(float w){ gl2.glLineWidth(w); }
    
    
    public void diffuse(float[] rgba){
	gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, rgba, 0);
    }
    public void diffuse(float r, float g, float b, float a){
	diffuse(new float[]{r/255,g/255,b/255,a/255});
    }
    public void diffuse(float r, float g, float b){ diffuse(r,g,b,255); }
    public void diffuse(IColor c){ diffuse(c.rgba()); }
    
    public void ambient(float[] rgba){
	gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, rgba, 0);
    }
    public void ambient(float r, float g , float b, float a){
	ambient(new float[]{r/255,g/255,b/255,a/255});
    }
    public void ambient(float r, float g , float b){ ambient(r,g,b,255); }
    public void ambient(IColor c){ ambient(c.rgba()); }
    
    public void specular(float[] rgba){
	gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, rgba, 0);
    }
    public void specular(float r, float g , float b, float a){
	specular(new float[]{r/255,g/255,b/255,a/255});
    }
    public void specular(float r, float g , float b){ specular(r,g,b,255); }
    public void specular(IColor c){ specular(c.rgba()); }
    
    public void emissive(float[] rgba){
	gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, rgba, 0);
    }
    public void emissive(float r, float g , float b, float a){
	emissive(new float[]{r/255,g/255,b/255,a/255});
    }
    public void emissive(float r, float g , float b){ emissive(r,g,b,255); }
    public void emissive(IColor c){ emissive(c.rgba()); }
    
    public void shininess(float s){
	gl2.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS,s);
    }
    
    public void enableLight(){
	gl2.glEnable(GL2.GL_LIGHT1);
	gl2.glEnable(GL2.GL_LIGHTING);
    }
    public void disableLight(){
	gl2.glDisable(GL2.GL_LIGHTING);
	gl2.glDisable(GL2.GL_LIGHT1);
    }
    
    public void pointSize(float sz){ gl2.glPointSize(sz); }
    
    public void drawPoint(IVec p){
	gl2.glBegin(GL2.GL_POINTS);
	gl2.glVertex3d(p.x,p.y,p.z);
        gl2.glEnd();
    }
    public void drawPoints(IVec[] p){
	gl2.glBegin(GL2.GL_POINTS);
	for(int i=0; i<p.length; i++){ gl2.glVertex3d(p[i].x,p[i].y,p[i].z); }
        gl2.glEnd();
    }
    public void drawLines(IVec[] p){
	gl2.glBegin(GL2.GL_LINES);
	for(int i=0; i<p.length; i++){ gl2.glVertex3d(p[i].x,p[i].y,p[i].z); }
        gl2.glEnd();
    }
    public void drawLineStrip(IVec[] p){
	gl2.glBegin(GL2.GL_LINE_STRIP);
	for(int i=0; i<p.length; i++){ gl2.glVertex3d(p[i].x,p[i].y,p[i].z); }
        gl2.glEnd();
    }
    public void drawLineLoop(IVec[] p){
	gl2.glBegin(GL2.GL_LINE_LOOP);
	for(int i=0; i<p.length; i++){ gl2.glVertex3d(p[i].x,p[i].y,p[i].z); }
        gl2.glEnd();
    }
    public void drawPolygon(IVec[] pts, IVec[] nml){
	gl2.glBegin(GL2.GL_POLYGON);
	for(int i=0; i<pts.length; i++){
	    gl2.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl2.glEnd();
    }
    public void drawPolygon(IVec[] pts){
	gl2.glBegin(GL2.GL_POLYGON);
	for(int i=0; i<pts.length; i++){ gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl2.glEnd();
    }
    public void drawQuads(IVec[] pts){
	gl2.glBegin(GL2.GL_QUADS);
	for(int i=0; i<pts.length; i++){ gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl2.glEnd();
    }
    public void drawQuads(IVec[] pts, IVec[] nml){
	gl2.glBegin(GL2.GL_QUADS);
	for(int i=0; i<pts.length; i++){
	    gl2.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl2.glEnd();
    }
    public void drawQuadStrip(IVec[] pts){
	gl2.glBegin(GL2.GL_QUAD_STRIP);
	for(int i=0; i<pts.length; i++){ gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl2.glEnd();
    }
    public void drawQuadStrip(IVec[] pts, IVec[] nml){
	gl2.glBegin(GL2.GL_QUAD_STRIP);
	for(int i=0; i<pts.length; i++){
	    gl2.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl2.glEnd();
    }
    public void drawQuadMatrix(IVec[][] pts){
	for(int i=0; i<pts.length-1; i++){
	    gl2.glBegin(GL2.GL_QUAD_STRIP);
	    for(int j=0; j<pts[i].length; j++){
		gl2.glVertex3d(pts[i][j].x,pts[i][j].y,pts[i][j].z);
		gl2.glVertex3d(pts[i+1][j].x,pts[i+1][j].y,pts[i+1][j].z);
	    }
	    gl2.glEnd();
	}
    }
    public void drawQuadMatrix(IVec[][] pts, IVec[][] nml){
	for(int i=0; i<pts.length-1; i++){
	    gl2.glBegin(GL2.GL_QUAD_STRIP);
	    for(int j=0; j<pts[i].length; j++){
		gl2.glNormal3d(nml[i][j].x,nml[i][j].y,nml[i][j].z);
		gl2.glVertex3d(pts[i][j].x,pts[i][j].y,pts[i][j].z);
		gl2.glNormal3d(nml[i+1][j].x,nml[i+1][j].y,nml[i+1][j].z);
		gl2.glVertex3d(pts[i+1][j].x,pts[i+1][j].y,pts[i+1][j].z);
	    }
	    gl2.glEnd();
	}
    }
    public void drawTriangles(IVec[] pts, IVec[] nml){
	gl2.glBegin(GL2.GL_TRIANGLES);
	for(int i=0; i<pts.length; i++){
	    gl2.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl2.glEnd();
    }
    public void drawTriangles(IVec[] pts){
	gl2.glBegin(GL2.GL_TRIANGLES);
	for(int i=0; i<pts.length; i++){ gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl2.glEnd();
    }
    public void drawTriangleStrip(IVec[] pts, IVec[] nml){
	gl2.glBegin(GL2.GL_TRIANGLE_STRIP);
	for(int i=0; i<pts.length; i++){
	    gl2.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl2.glEnd();
    }
    public void drawTriangleStrip(IVec[] pts){
	gl2.glBegin(GL2.GL_TRIANGLE_STRIP);
	for(int i=0; i<pts.length; i++){ gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl2.glEnd();
    }
    public void drawTriangleFan(IVec[] pts, IVec[] nml){
	gl2.glBegin(GL2.GL_TRIANGLE_FAN);
	for(int i=0; i<pts.length; i++){
	    gl2.glNormal3d(nml[i].x,nml[i].y,nml[i].z);
	    gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z);
	}
        gl2.glEnd();
    }
    public void drawTriangleFan(IVec[] pts){
	gl2.glBegin(GL2.GL_TRIANGLE_FAN);
	for(int i=0; i<pts.length; i++){ gl2.glVertex3d(pts[i].x,pts[i].y,pts[i].z); }
        gl2.glEnd();
    }
    
}
