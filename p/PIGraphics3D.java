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

package igeo.p;

import processing.core.*;
import processing.opengl.*;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
//import com.sun.opengl.util.j2d.Overlay;

import igeo.*;
import igeo.gui.*;

/**
   A child class of Processing's PGraphic to draw on Processing using P3D.
   This class also manages the IServer to manage all the objects in iGeo.
   
   @author Satoru Sugihara
*/
public class PIGraphics3D extends PGraphics3D
    implements IGraphics3D, IPane/*, IPanelAdapter*/
	       /*, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, FocusListener, ComponentListener*/
{
    
    /** preserve Procesing's color mode or not */
    static boolean keepColorMode=false;
    
    public IPanelI panel;
    
    public IView view; // for IGrahpics3D
    /** cache of view.frontDirection() */
    public IVec viewDirection;
    /** cache of view.location() */
    public IVec viewLocation;
    
    public int origColorMode;
    public float origColorModeX, origColorModeY, origColorModeZ, origColorModeA;
    
    /**
       To show iGeo correctly in Processing's basic mode, this needs to be true.
    */
    public boolean overridePAppletFinish=true;
    public boolean finished=false;
    
    /**
       To show iGeo correctly in Processing's basic mode, this needs to be true.
    */
    public boolean overridePAppletLoop=true;
    public boolean looping=true;
    
    
    public boolean enableDepthSort;
    
    /** when number of objects exceeds IConfig.maxObjectNumberForDepthSort, depth sort is overwritten to be disabled */
    public boolean overrideDepthSort=false;
    
    public boolean firstDraw=true;
    
    // for IPane
    public int screenX=0, screenY=0;
    public float borderWidth=1f;
    public BasicStroke borderStroke = new BasicStroke(borderWidth);
    public Color borderColor = Color.gray;
    public INavigator navigator;
    public boolean visible=true;
    
    public PImage bgImage=null;
    public IColor[][] bgColorCache=new IColor[2][2];
    public boolean creatingBGImage=false;
    public boolean resetTexture=false;
    
    
    public PIGraphics3D[][] subGraphics=null;

    public PIGraphics3D alias=null;

    //public boolean firstPanelDraw = true;
    
    
    public PIGraphics3D(){ super(); }
    
    public PIGraphics3D(PIGraphics3D g){
	super();
	alias=g; // alias mode (use alias graphics in fullscreen but use own in multi view )
    }
    
    public void initPrimary(){
	super.initPrimary();

	if(alias!=null) return;
	
	// only in processing 2.0?
	Object canvasObj = pgl.getCanvas();
	if(canvasObj instanceof Canvas){
	    Canvas canvas = (Canvas)canvasObj;
	    canvas.addMouseListener(panel);
	    canvas.addMouseMotionListener(panel);
	    canvas.addMouseWheelListener(panel);
	    canvas.addKeyListener(panel);
	    canvas.addFocusListener(panel);
	    canvas.addComponentListener(panel);
	}
	else{
	    IG.err("not canvas: "+canvasObj);
	}
    }
    
    
    /**
       setParent is called by Processing in the initialization process of Processing.
       Here the initialization proces of iGeo is also done.
       @param parent parent PApplet of Processing.
    */
    public void setParent(PApplet parent){
	super.setParent(parent);
	
	if(alias!=null) return;
	
	subGraphics = new PIGraphics3D[2][2];
	subGraphics[0][0] = new PIGraphics3D(this); //this; 
	subGraphics[0][1] = new PIGraphics3D(this);
	subGraphics[1][0] = new PIGraphics3D(this);
	subGraphics[1][1] = new PIGraphics3D(this);
	subGraphics[0][0].setParent(parent); // ?
	subGraphics[0][1].setParent(parent); // ?
	subGraphics[1][1].setParent(parent); // ?
	subGraphics[1][0].setParent(parent); // ?

	int w = parent.getWidth();
	int h = parent.getHeight();
	
	//subGraphics[0][0].setSize(w/2,h/2);
	//subGraphics[0][1].setSize(w/2,h/2);
	//subGraphics[1][1].setSize(w/2,h/2);
	//subGraphics[1][0].setSize(w/2,h/2);
	
	subGraphics[0][0].setBounds(0,0,w/2,h/2);
	subGraphics[0][1].setBounds(w/2,0,w/2,h/2);
	subGraphics[1][0].setBounds(0,h/2,w/2,h/2);
	subGraphics[1][1].setBounds(w/2,h/2,w/2,h/2);
	
	//subGraphics[i][j].setPrimary(true); // true?
	
	
	// panel = new IGridPanel(0,0,parent.getWidth(),parent.getHeight(),2,2, subGraphics);
	//panel = new IGridPanel(0,0,parent.getWidth(),parent.getHeight(),1,1, new IPane[][]{ new IPane[]{ this } });
	
	panel = new IGridPanel(0,0,parent.getWidth(),parent.getHeight(),2,2, subGraphics);
	
	panel.setVisible(true); // ?
	panel.setParent(parent);
	//panel.setAdapter(this);
	
	// initialize iGeo 
	IG ig = IG.init(panel);
	ig.server().graphicServer().enableGL(); //
	setColorMode();
	
	//ig.setBasePath(parent.sketchPath("")); // not sketchPath
	
	ig.setOnline(parent.online);
	
	if(!parent.online){ // only when running local
	    ig.setBasePath(parent.dataPath("")); // for default path to read/write files
	}
	
	ig.setInputWrapper(new PIInput(parent));
	
	if(parent.frame!=null){
	    parent.frame.addWindowListener(panel);
	}
	//noSmooth();
	
	if(PIConfig.drawBeforeProcessing) parent.registerPre(this);
	else parent.registerDraw(this);
	parent.registerPost(this);
	
	if(PIConfig.resizable){ parent.frame.setResizable(true); }
	
	enableDepthSort=IConfig.depthSort;
	if(enableDepthSort){ super.hint(ENABLE_DEPTH_SORT); }
	else{ super.hint(DISABLE_DEPTH_SORT); }
    }
    
    public void pre(){ drawIG(); }
    public void draw(){ drawIG(); }
    
    
    // overriding PGraphics.vertex to avoid error of disappearing lines when their end and start points are at the same location and DEPTH_SORT is enabled. 
    public void vertex(float x, float y, float z) {
	
	// part of this is excluded in PGraphics.vertex(float,float,float)
	if(hints[ENABLE_DEPTH_SORT] &&
	   (shape == POLYGON || shape==LINE_STRIP || shape==LINE)&&
	   vertexCount > 0 &&
	   // vertexCount == shapeFirst &&  // first vertex
	   (Math.abs(vertices[vertexCount-1][X] - x) < EPSILON) &&
	   (Math.abs(vertices[vertexCount-1][Y] - y) < EPSILON) &&
	   (Math.abs(vertices[vertexCount-1][Z] - z) < EPSILON)) {
	    // in case depth sort is enabled and end of previous line is identical with the start of the current line
	    
	    // add a vertex in the regular way as PGraphics.vertex does.
	    vertexCheck();
	    float[] vertex = vertices[vertexCount];
	    curveVertexCount = 0;
	    vertex[X] = x;
	    vertex[Y] = y;
	    vertex[Z] = z;
	    vertex[EDGE] = edge ? 1 : 0;
	    if (fill || textureImage != null) {
		if (textureImage == null) {
		    vertex[R] = fillR;
		    vertex[G] = fillG;
		    vertex[B] = fillB;
		    vertex[A] = fillA;
		}
		else {
		    if (tint) {
			vertex[R] = tintR;
			vertex[G] = tintG;
			vertex[B] = tintB;
			vertex[A] = tintA;
		    }
		    else {
			vertex[R] = 1;
			vertex[G] = 1;
			vertex[B] = 1;
			vertex[A] = 1;
		    }
		}
		vertex[AR] = ambientR;
		vertex[AG] = ambientG;
		vertex[AB] = ambientB;
		vertex[SPR] = specularR;
		vertex[SPG] = specularG;
		vertex[SPB] = specularB;
		//vertex[SPA] = specularA;
		vertex[SHINE] = shininess;
		vertex[ER] = emissiveR;
		vertex[EG] = emissiveG;
		vertex[EB] = emissiveB;
	    }
	    if (stroke) {
		vertex[SR] = strokeR;
		vertex[SG] = strokeG;
		vertex[SB] = strokeB;
		vertex[SA] = strokeA;
		vertex[SW] = strokeWeight;
	    }
	    if (textureImage != null) {
		vertex[U] = textureU;
		vertex[V] = textureV;
	    }
	    vertex[NX] = normalX;
	    vertex[NY] = normalY;
	    vertex[NZ] = normalZ;
	    vertex[BEEN_LIT] = 0;
	    vertexCount++;
	}
	else{ super.vertex(x,y,z); }
    }
    
    
    /*
    // for debug
    protected void render(){
	super.render();
    }
    // for debug
    protected void sort(){
	super.sort();
    }
    // debug
    protected void endShapeStroke(int mode) {
	switch (shape) {
	case POLYGON:
	    {
		IOut.err("shapeFirst="+shapeFirst+", shapeLast="+shapeLast); //
		// store index of first vertex
		int stop = shapeLast - 1;
		addLineBreak();
		for (int i = shapeFirst; i < stop; i++) { addLine(i, i+1); }
		if (mode == CLOSE) {
		    // draw the last line connecting back to the first point in poly
		    addLine(stop, shapeFirst); //lines[first][VERTEX1]);
		}
	    }
	    break;
	}
    }
    // debug
    protected void addLine(int a, int b) { addLineWithClip2(a, b); }
    protected final void addLineWithClip2(int a, int b) {
	float az = vertices[a][VZ];
	float bz = vertices[b][VZ];
	if (az > cameraNear) {
	    if (bz > cameraNear) {
		IOut.err("bz > cameraNear!!!!");
		return;
	    }
	    IOut.err("interpolate");
	    int cb = interpolateClipVertex(a, b);
	    addLineWithoutClip(cb, b);
	    return;
	}
	else {
	    if (bz <= cameraNear) {
		//IOut.err("normal"); //
		addLineWithoutClip(a, b);
		return;
	    }
	    IOut.err("interpolate");
	    int cb = interpolateClipVertex(a, b);
	    addLineWithoutClip(a, cb);
	    return;
	}
    }
    */
    
    /*
    public void translateImpl(float x, float y, float z){
	IG.err(x+","+y+","+z+ " @"+this);
	IErr.printStack();
	super.translateImpl(x,y,z);
    }
    */
    
    /*
    protected void begin2D(){
	IG.err();
	super.begin2D();
    }
    */
    
    /**
       Drawing all the iGeo objects through IPanel.
       Overlay is also used to draw 2D graphics on top of OpenGL 3D graphics.
    */
    public void drawIG(){
	if(alias==null){ // main graphic
	    if(((IScreenTogglePanel)panel).fullScreenPane!=null){
		panel.predraw(this);
		
		IView vw = ((PIGraphics3D)(((IScreenTogglePanel)panel).fullScreenPane)).view;
		ArrayList<IGraphicI> objects = panel.getIG().server().graphicServer().getObjects(vw);
		this.draw(objects, vw); // not g.draw() but this.draw()
		panel.postdraw(this);
	    }
	    else{
		
		//if(firstPanelDraw){ // debug
		if(panel!=null) panel.draw(this);
		//    firstPanelDraw=false;
		//}
		

		
		beginDraw();
		
		pushMatrix();
		resetMatrix();
		
		
		ortho(0,super.width,0,super.height);
		translate(-super.width/2, -super.height/2);
		
		
		//IG.err("super.w="+super.width+", super.h"+super.height); //
		
		//scale((float)texture.glWidth/super.width, (float)texture.glHeight/super.height); //
		
		for(int i=0; i<subGraphics.length; i++){
		    for(int j=0; j<subGraphics[i].length; j++){
			//IG.err("sub["+i+"]["+j+"].w=" +subGraphics[i][j].width +", "+"sub["+i+"]["+j+"].h=" +subGraphics[i][j].height); //
			
			//super.image(subGraphics[i][j],subGraphics[i][j].screenX,subGraphics[i][j].screenY,subGraphics[i][j].width, subGraphics[i][j].height);
			super.image(subGraphics[i][j],
				    subGraphics[i][j].screenX,
				    subGraphics[i][j].screenY,
				    subGraphics[i][j].width,
				    subGraphics[i][j].height); //
		    }
		}
		
		popMatrix();
		
		endDraw();
		
		
		
		
	    }
	}
    }
    
    
    public void post(){
	if(overridePAppletFinish) parent.finished=finished;
	if(overridePAppletLoop) if(looping) parent.loop(); else parent.noLoop();
    }
    
    public void loop(){ if(!looping) looping=true; }
    public void noLoop(){ if(looping) looping=false; }
    
    public void start(){ if(finished) finished=false; }
    public void stop(){ if(!finished) finished=true; }
    
    

    /*******************************************************************************************
     * implementation of IGraphics3D
     *******************************************************************************************/
    
    public IGraphicMode.GraphicType type(){ return IGraphicMode.GraphicType.P3D; }
    
    public IView view(){ return view; }
    
    
    public void updateBGImage(int w, int h, IView vw){
	
	if( vw!=null && vw.bgColor!=null ){
	    
	    boolean createImg=false;
	    
	    for(int i=0; i<2; i++){
		for(int j=0; j<2; j++){
		    if(bgColorCache[i][j] != vw.bgColor[i][j]){
			bgColorCache[i][j] = vw.bgColor[i][j];
			createImg=true;
		    }
		}
	    }
	    
	    if(bgImage==null||createImg
	       || bgImage.width!=w||bgImage.height!=h){ 
		
		PImage bg = createBGImage(w,h,vw);
		creatingBGImage = true;
		bgImage = bg;
		creatingBGImage = false;
	    }
	    
	    if(resetTexture &&
	       texture!=null &&
	       (texture.glWidth!=super.width || texture.glHeight!=super.height)){
		//IG.err("primarySurface = "+primarySurface); //
		/*
		if(primarySurface){
		    //unbindFrontTexture();
		    texture = null;
		    loadTextureImpl(POINT, false);
		    //bindFrontTexture();
		}
		else{
		*/
		//unbindFrontTexture();
		//createPTexture();
		//bindFrontTexture();
		
		texture = null;
		if(primarySurface) loadTextureImpl(POINT, false);
		super.restartPGL();
		
		//}
		resetTexture=false;		    
	    }
	    
	}
    }
    
    public PImage createBGImage(int w, int h, IView v){
	
	//IG.err("w = "+w+", h = "+h+ " @"+this); //
	
	PImage bgimg = new PImage(w,h);
	float[][][] cl = new float[2][2][3];
	for(int i=0; i<2; i++){
	    for(int j=0; j<2; j++){
		cl[i][j][0] = v.bgColor[i][j].getRed();
		cl[i][j][1] = v.bgColor[i][j].getGreen();
		cl[i][j][2] = v.bgColor[i][j].getBlue();
	    }
	}
	for(int j=0; j<h; j++){
	    float jr = (float)(h-j)/h;
	    for(int i=0; i<w; i++){
		float ir = (float)(w-i)/w;
		int red = (int)(jr*(ir*cl[0][0][0] + (1-ir)*cl[1][0][0]) +
				(1-jr)*(ir*cl[0][1][0] + (1-ir)*cl[1][1][0]));
		int green = (int)(jr*(ir*cl[0][0][1] + (1-ir)*cl[1][0][1]) +
				  (1-jr)*(ir*cl[0][1][1] + (1-ir)*cl[1][1][1]));
		int blue = (int)(jr*(ir*cl[0][0][2] + (1-ir)*cl[1][0][2]) +
				 (1-jr)*(ir*cl[0][1][2] + (1-ir)*cl[1][1][2]));
		bgimg.set(i,j, 0xFF<<24|(red&0xFF)<<16|(green&0xFF)<<8|(blue&0xFF));
	    }
	}
	
	return bgimg;
	
	/*
	PGraphics g = new PGraphics2D();
	g.setSize(w,h);
	g.beginDraw();
	
	// image drawn by QUAD
	g.noStroke();
	g.beginShape(QUADS);
	Color c = v.bgColor[0][0];
	g.fill(c.getRed(),c.getGreen(),c.getBlue());
	g.vertex(0,0);
	c = v.bgColor[1][0];
	g.fill(c.getRed(),c.getGreen(),c.getBlue());
	g.vertex(w,0);
	c = v.bgColor[1][1];
	g.fill(c.getRed(),c.getGreen(),c.getBlue());
	g.vertex(w,h);
	c = v.bgColor[0][1];
	g.fill(c.getRed(),c.getGreen(),c.getBlue());
	g.vertex(0,h);
	g.endShape();
	
	g.endDraw();
	
	return g.get();
	*/
    }
    
    public void drawBG(IView vw){
	
	//IG.p("firstDraw = "+firstDraw);
	//IG.p("clearBG = "+IConfig.clearBG);
	//IG.p("x = "+screenX+", y = "+screenY+", w = "+super.width+", h = "+super.height); //
	
	if(vw.bgColor!=null){
	    
	    /*
	    boolean createImg=false;
	    for(int i=0; i<2; i++){
		for(int j=0; j<2; j++){
		    if(bgColorCache[i][j] != vw.bgColor[i][j]){
			bgColorCache[i][j] = vw.bgColor[i][j];
			createImg=true;
		    }
		}
	    }
	    if(bgImage==null||createImg
	       || bgImage.width<super.width||bgImage.height<super.height){ // added 20120915
		PImage bg = createBGImage(super.width,super.height,vw);
		creatingBGImage = true;
		bgImage = bg;
		creatingBGImage = false;
	    }
	    */
	    
	    if(!creatingBGImage){

		/*
		if(resetTexture &&
		   texture!=null&&
		   (texture.glWidth!=super.width || texture.glHeight!=super.height)){
		   //IG.err("primarySurface = "+primarySurface); //
		    if(primarySurface){
			//unbindFrontTexture();
			texture = null;
			loadTextureImpl(POINT, false);
			//bindFrontTexture();

		    }
		    else{
			//unbindFrontTexture();
			//createPTexture();
			//bindFrontTexture();
		    }
		    resetTexture=false;		    
		}
		*/
		
		background(bgImage);
		//backgroundImpl(bgImage);
		//backgroundImpl();
		//super.setImpl(bgImage,0,0,super.width,super.height,0,0);
		
		//set(0,0,bgImage);
		//image(bgImage, 0, 0); //
	    }
	    
	}
	
    }
    
    public void drawView(IView view){
	
	if(IConfig.clearBG || firstDraw){
	    drawBG(view);
	}
	
	viewLocation = view.location();
	viewDirection = view.frontDirection();
	
       // default light
        if(view.mode.isLight()){
	    
	    super.lights();
	    
	    /*
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
	    */
        }
	
	/*
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        //gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
	*/
	
	
	if(!overrideDepthSort){
	    if(IConfig.depthSort!=enableDepthSort){
		if(IConfig.depthSort){
		    enableDepthSort=true;
		    super.hint(ENABLE_DEPTH_SORT);
		}
		else{
		    enableDepthSort=false;
		    super.hint(DISABLE_DEPTH_SORT);
		}
	    }
	    /*
	    if(IConfig.depthSort){
		super.hint(ENABLE_DEPTH_SORT);
		//super.hint(ENABLE_DEPTH_TEST); // default is already on, isn't it?
	    }
	    else{
		super.hint(DISABLE_DEPTH_SORT);
	    }
	    */
	}
	
	if(IConfig.smoothGraphicP3D){
	    super.smooth(); // this makes perspective close up very very heavy.
	}
	
	// if(true)return;
	
	// super.resetMatrix();
	
	// not sure why this is necessary. but it works.
	// super.translate(view.screenWidth/2, view.screenHeight/2, 0);
	
	// IG.p("view w = "+view.screenWidth + ", h = "+view.screenHeight); //
	// IG.p("pane w = "+this.getWidth() + ", h = "+this.getHeight()); //
	
	
	super.scale(1,-1,1);
	
        if(view.axonometric){
            double glWidth = view.screenWidth*view.axonRatio; 
            double glHeight = view.screenHeight*view.axonRatio;
	    
	    super.ortho((float)-glWidth/2+(float)view.screenWidth/2,
			(float)glWidth/2+(float)view.screenWidth/2,
			(float)-glHeight/2+(float)view.screenHeight/2,
			(float)glHeight/2+(float)view.screenHeight/2,
			(float)view.near,(float)view.far);
	    
	    // not sure why +scrrenWidth/2, +screenHeight/2
        }
        else{
	    super.scale(view.screenHeight);
	    double glHeight = view.near*view.persRatio*2;
            double glWidth = glHeight*view.screenWidth/view.screenHeight;
            super.frustum((float)-glWidth/2,(float)glWidth/2,
			  (float)-glHeight/2,(float)glHeight/2,
			  (float)view.near,
			  (float)view.far*view.screenHeight);
	}
	
	
	super.applyMatrix((float)view.transformArray[0], (float)view.transformArray[4],
			  (float)view.transformArray[8], (float)view.transformArray[12],
			  (float)view.transformArray[1], (float)view.transformArray[5],
			  (float)view.transformArray[9], (float)view.transformArray[13],
			  (float)view.transformArray[2], (float)view.transformArray[6],
			  (float)view.transformArray[10], (float)view.transformArray[14],
			  (float)view.transformArray[3], (float)view.transformArray[7],
			  (float)view.transformArray[11], (float)view.transformArray[15]);
	
	
	/*
        gl.glMatrixMode(GL.GL_MODELVIEW);
        //gl.glLoadIdentity();
        //gl.glTranslated(-x,-y,-z);
        gl.glLoadMatrixd(view.transformArray,0);
	*/
		
	/*
	super.smooth();
	super.stroke(0);
	clr(0,128,0);
	super.beginShape(QUADS);
	super.vertex(20,20,-20); //vertex(-0.5f,-1);
	super.vertex(width-20,20,20); //vertex(-0.5f,-1);
	super.vertex(width-20,height-20,20);
	super.vertex(20,height-20,20);
	super.endShape();
	*/
	
	//this.clr(255,0,0);
	//this.drawQuads(IG.v(0,0,0, 100,0,0, 100,100,0, 0,100,0)); //
	
    }
    
    
    // draw each view 
    public synchronized void draw(ArrayList<IGraphicI> objects, IView v){
	
	view = v;
	if(view.hide) return;
	
	// check object num to determin depth sort
	if(enableDepthSort){
	    if(objects!=null &&
	       IConfig.maxObjectNumberForDepthSort>0 &&
	       objects.size() > IConfig.maxObjectNumberForDepthSort){
		if(!overrideDepthSort){
		    overrideDepthSort=true;
		    enableDepthSort=false;
		    super.hint(DISABLE_DEPTH_SORT);
		    IOut.err("number of graphic objects ("+objects.size()+") exceeds IConfig.maxObjectNumberForDepthSort ("+IConfig.maxObjectNumberForDepthSort+")");
		    IOut.err("depth sort is disalbed"); 
		}
	    }
	    else if(IConfig.maxObjectNumberForDepthSort>0){
		if(overrideDepthSort){ overrideDepthSort=false; }
	    }
	}
	
	// bg image preparation
	updateBGImage(super.width,super.height,view);
	
	//super.pushMatrix();
	super.resetMatrix();
	
	super.beginDraw();
	
	drawView(view);
	
	//IG.err("drawing "+objects.size() +" objects");
	
	if(objects!=null)
	    //for(int i=0; i<objects.size(); i++)
	    for(int i=objects.size()-1; i>=0; i--)
		if(objects.get(i).isVisible()) objects.get(i).draw(this);
	
	if(view.mode().isLight()){ disableLight(); }
	
	/*
	if(g!=null){ // overlay
	    // draw here?
	    // border
	    if(view.pane!=null && 
	       view.pane.borderWidth>0&&
	       (view.pane.parent.width!=view.pane.width ||view.pane.parent.height!=view.pane.height)){
		g.setColor(view.pane.borderColor);
		g.setStroke(view.pane.borderStroke);
		//g.drawRect(view.x,view.y,view.width-1,view.height-1);
		g.drawRect(view.pane.x,view.pane.y,view.pane.width,view.pane.height);
	    }
	}
	*/
	
	super.endDraw();
	
	//super.popMatrix();
    }
    
    public boolean firstDraw(){ return firstDraw; }
    public void firstDraw(boolean f){
	firstDraw=f;
	if(subGraphics!=null){
	    for(int i=0; i<subGraphics.length; i++){
		for(int j=0; j<subGraphics[i].length; j++){
		    if(subGraphics[i][j]!=this){
			subGraphics[i][j].firstDraw(f);
		    }
		}
	    }
	}
    }
    
    
    /** default color mode for IG */
    public boolean isDefaultColorMode(){
	return super.colorMode == RGB &&
	    super.colorModeX == 255 &&
	    super.colorModeY == 255 &&
	    super.colorModeZ == 255 &&
	    super.colorModeA == 255 ;
	/*
	return super.colorMode == RGB &&
	    super.colorModeX == 1f &&
	    super.colorModeY == 1f &&
	    super.colorModeZ == 1f &&
	    super.colorModeA == 1f ;
	*/
    }
    
    public void pushColorMode(){
	origColorMode = super.colorMode;
	origColorModeX = super.colorModeX;
	origColorModeY = super.colorModeY;
	origColorModeZ = super.colorModeZ;
	origColorModeA = super.colorModeA;
    }
    public void popColorMode(){
	super.colorMode(origColorMode,origColorModeX,origColorModeY,origColorModeZ,origColorModeA);
    }
    public void setColorMode(){
	colorMode(RGB, 255);
    }
    
    public void clr(IColor c){ this.clr(c.red(),c.green(),c.blue(),c.alpha()); }
    /* float 0 - 1 */
    public void clr(float r, float g, float b){ this.clr(r,g,b,1f); }
    /* float 0 - 1 */
    public void clr(float r, float g, float b, float a){
	//IG.err(r+","+g+","+b+","+a); //
	
	if(keepColorMode && !isDefaultColorMode()){
	    pushColorMode();
	    setColorMode();
	    super.fill(r*255,g*255,b*255,a*255);
	    popColorMode();
	}
	else{ super.fill(r*255,g*255,b*255,a*255); }
    }
    /* float 0 - 1 */
    public void clr(float[] rgba){ this.clr(rgba[0],rgba[1],rgba[2],rgba[3]); }
    /** alpha : 0-1 */
    public void clr(IColor c, float alpha){
	this.clr(c.red(),c.green(),c.blue(),alpha);
    }
    
    public void stroke(IColor c){ this.stroke(c.red(),c.green(),c.blue(),c.alpha()); }
    public void stroke(float r, float g, float b, float a){
	if(keepColorMode && !isDefaultColorMode()){
	    pushColorMode();
	    setColorMode();
	    super.stroke(r*255,g*255,b*255,a*255);
	    popColorMode();
	}
	else{ super.stroke(r*255,g*255,b*255,a*255); }
    }
    public void stroke(float r, float g, float b){ this.stroke(r,g,b,1f); }
    public void stroke(float[] rgba){ this.stroke(rgba[0],rgba[1],rgba[2],rgba[3]); }
    public void stroke(IColor c, float alpha){ this.stroke(c.red(),c.green(),c.blue(),alpha); }
    
    public void weight(float w){
	/*this.weight=w;*/
	
	if(view.axonometric){
	    super.strokeWeight(w);
	}
	else{
	    super.strokeWeight(w/view.screenHeight); // somehow in perspective, line weight is scaled by screenHeight
	}
    }
    
    public void diffuse(float r, float g, float b, float a){
	clr(r,g,b,a);
    }
    
    public void diffuse(float r, float g, float b){ this.diffuse(r,g,b,1f); }
    public void diffuse(IColor c){
	this.diffuse(c.red(), c.green(), c.blue(), c.alpha());
    }
    public void diffuse(IColor c,float alpha){
	this.diffuse(c.red(), c.green(), c.blue(), alpha);
    }
    public void diffuse(float[] rgba){
	this.diffuse(rgba[0], rgba[1], rgba[2], rgba[3]);
    }
    
    
    public void ambient(float r, float g, float b, float a){
	if(keepColorMode && !isDefaultColorMode()){
	    pushColorMode();
	    setColorMode();
	    super.ambient(r*255,g*255,b*255);
	    popColorMode();
	}
	else{ super.ambient(r*255,g*255,b*255); }
    }
    public void ambient(float r, float g, float b){ this.ambient(r,g,b,1f); }
    public void ambient(float[] rgba){ this.ambient(rgba[0],rgba[1],rgba[2],rgba[3]); }
    public void ambient(IColor c){ this.ambient(c.red(),c.green(),c.blue(),c.alpha()); }
    public void ambient(IColor c, float alpha){ this.ambient(c.red(),c.green(),alpha); }
    
    public void specular(float r, float g, float b, float a){
	if(keepColorMode && !isDefaultColorMode()){
	    pushColorMode();
	    setColorMode();
	    super.specular(r*255,g*255,b*255);
	    popColorMode();
	}
	else{ super.specular(r*255,g*255,b*255); }
    }
    public void specular(float r, float g, float b){ this.specular(r,g,b,1f); }
    public void specular(float[] rgba){ this.specular(rgba[0],rgba[1],rgba[2],rgba[3]); }
    public void specular(IColor c){ specular(c.red(),c.green(),c.blue(),c.alpha()); }
    public void specular(IColor c, float alpha){ specular(c.red(),c.green(),c.blue(),alpha); }
    
    public void emissive(float r, float g, float b, float a){
	if(keepColorMode && !isDefaultColorMode()){
	    pushColorMode();
	    setColorMode();
	    super.emissive(r*255,g*255,b*255);
	    popColorMode();
	}
	else{ super.emissive(r*255,g*255,b*255); }
    }
    public void emissive(float r, float g, float b){ this.emissive(r,g,b,1f); }
    public void emissive(float[] rgba){ this.emissive(rgba[0],rgba[1],rgba[2],rgba[3]); }
    public void emissive(IColor c){
	emissive(c.red(),c.green(),c.blue(),c.alpha());
    }
    public void emissive(IColor c, float alpha){
	emissive(c.red(),c.green(),c.blue(),alpha);
    }
    
    public void shininess(float s){ super.shininess(s); }
    
    public void enableLight(){ super.lights(); }
    public void disableLight(){ super.noLights(); }
    
    
    //public void pointSize(float sz){ pointSize=sz; }
    public void pointSize(float sz){ // point is drawn as line
	super.strokeWeight(sz);
    } 
    
    
    public boolean isInFront(IVec p){
	return true;  // debug
	//return p.dif(viewLocation).dot(viewDirection)>0;
    }
    
    
    public void vertex(IVec p){
	vertex((float)p.x, (float)p.y, (float)p.z);
    }
    
    public void vertex(IVec[] p){
	for(int i=0; i<p.length; i++){
	    vertex((float)p[i].x, (float)p[i].y, (float)p[i].z);
	}
    }
    
    public void vertex(IVec[] p, IVec[] n){
	for(int i=0; i<p.length; i++){
	    super.normal((float)n[i].x, (float)n[i].y, (float)n[i].z);
	    vertex((float)p[i].x, (float)p[i].y, (float)p[i].z);
	}
    }
    
    public void normal(IVec n){
	super.normal((float)n.x, (float)n.y, (float)n.z);
    }
    
    public void drawPoint(IVec p){
	super.stroke=true;
	super.fill=false;
	
	super.beginShape(POINTS); // use POINTS for processing 2.0
	//super.beginShape(LINE); // use lines. POINTS in P3D has no control on size.
	//super.beginShape(LINE_STRIP); // use lines. POINTS in P3D has no control on size.
	
	if(IConfig.cullVertexBehindViewInP3D){
	    if(isInFront(p)){ vertex(p); /*vertex(p);*/ }
	}
	else{ vertex(p); /*vertex(p);*/ }
	super.endShape();
	
	/*
	if(IConfig.cullVertexBehindViewInP3D){
	    if(isInFront(p)){
		super.beginShape(POINTS);
		vertex(p);
		super.endShape();
		//super.strokeCap(ROUND); //ROUND doesn't work in P3D
		//drawLines(new IVec[]{p, p});
	    }
	}
	else{
	    super.beginShape(POINTS);
	    vertex(p);
	    super.endShape();
	}
	*/
    }
    
    public void drawPoints(IVec[] p){
	super.stroke=true;
	super.fill=false;
	super.beginShape(POINTS); // use POINTS for processing 2.0
	//super.beginShape(LINES); // use lines. POINTS in P3D has no control on size.
	//super.beginShape(LINE_STRIP); // use lines. POINTS in P3D has no control on size.
	for(int i=0; i<p.length; i++){
	    if(IConfig.cullVertexBehindViewInP3D){
		if(isInFront(p[i])){ vertex(p[i]); /*vertex(p[i]);*/ }
	    }
	    else{ vertex(p[i]); /*vertex(p[i]);*/ }
	}
	super.endShape();
	
	/*
	super.beginShape(POINTS);
	for(int i=0; i<p.length; i++){
	    if(IConfig.cullVertexBehindViewInP3D){
		if(isInFront(p[i])){ vertex(p); }
	    }
	    else{ vertex(p); }
	}
	//vertex(p);
	super.endShape();
	*/
    }
    public void drawLines(IVec[] p){
	super.stroke=true;
	super.fill=false;
	super.beginShape(LINES);
	//vertex(p);
	for(int i=0; i<p.length-1; i+=2){
	    if(IConfig.cullVertexBehindViewInP3D){
		if(isInFront(p[i]) && isInFront(p[i+1])){
		    vertex(p[i]);
		    vertex(p[i+1]);
		}
	    }
	    else{
		vertex(p[i]);
		vertex(p[i+1]);
	    }
	}
	super.endShape();
    }
    public void drawLineStrip(IVec[] p){
	super.stroke=true;
	super.fill=false;
	
	if(IConfig.cullVertexBehindViewInP3D){
	    boolean previousFront=false;
	    boolean isDrawing=false;
	    for(int i=0; i<p.length; i++){
		boolean currentFront = isInFront(p[i]);
		if(currentFront){
		    if(isDrawing){ vertex(p[i]); }
		    else{
			//super.beginShape(POLYGON);
			super.beginShape(LINE_STRIP);
			vertex(p[i]);
			isDrawing=true;
		    }
		}
		else{
		    if(isDrawing){
			//if(previousFront){ vertex(p[i]); }
			//else{ super.endShape(); isDrawing=false; }
			super.endShape(); isDrawing=false;
		    }
		}
		previousFront = currentFront;
	    }
	    if(isDrawing){ super.endShape(); }
	}
	else{
	    //super.beginShape(POLYGON);
	    super.beginShape(LINE_STRIP);
	    for(int i=0; i<p.length; i++){ vertex(p[i]); }
	    //vertex(p);
	    super.endShape();
	}
    }
    
    public void drawLineLoop(IVec[] p){
	super.stroke=true;
	super.fill=false;
	
	if(IConfig.cullVertexBehindViewInP3D){
	    boolean[] isFront = new boolean[p.length];
	    boolean isAnyBehind = false;
	    for(int i=0; i<p.length; i++){
		isFront[i] = isInFront(p[i]);
		if(!isFront[i]){ isAnyBehind=true; }
	    }
	    
	    if(isAnyBehind){ // line strip
		
		boolean previousFront=false;
		boolean isDrawing=false;
		for(int i=0; i<=p.length; i++){
		    boolean currentFront = isFront[i%p.length];
		    if(currentFront){
			if(isDrawing){ vertex(p[i%p.length]); }
			else{
			    super.beginShape(POLYGON);
			    vertex(p[i%p.length]);
			    isDrawing=true;
			}
		    }
		    else{
			if(isDrawing){
			    //if(previousFront){ vertex(p[i%p.length]); } 
			    //else{ super.endShape(); isDrawing=false; }
			    // simply end is fine.
			    super.endShape(); isDrawing=false; 
			}
		    }
		    previousFront = currentFront;
		}
		if(isDrawing){ super.endShape(); }
		
	    }
	    else{
		super.beginShape(POLYGON);
		for(int i=0; i<p.length; i++){ vertex(p[i]); }
		super.endShape(CLOSE);
	    }
	}
	else{
	    super.beginShape(POLYGON);
	    for(int i=0; i<p.length; i++){ vertex(p[i]); }
	    super.endShape(CLOSE);
	}
    }
    public void drawPolygon(IVec[] p){
	super.stroke= false;
	super.fill = true;
	super.beginShape(POLYGON);
	for(int i=0; i<p.length; i++){
	    if(IConfig.cullVertexBehindViewInP3D){
		if(isInFront(p[i])){ vertex(p[i]); }
	    }
	    else{ vertex(p[i]); }
	}
	//vertex(p);
	super.endShape(CLOSE);
    }
    public void drawPolygon(IVec[] p, IVec[] n){
	super.stroke= false;
	super.fill = true;
	super.beginShape(POLYGON);
	for(int i=0; i<p.length; i++){
	    if(IConfig.cullVertexBehindViewInP3D){
		if(isInFront(p[i])){ normal(n[i]); vertex(p[i]); }
	    }
	    else{ normal(n[i]); vertex(p[i]); }
	}
	//vertex(p,n);
	super.endShape(CLOSE);
    }
    public void drawPolygon(IVec[] p, IColor clr[], float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	super.beginShape(POLYGON);
	if(light){
	    if(alpha<0){
		for(int i=0; i<p.length; i++){
		    ambient(clr[i]);
		    diffuse(clr[i]);
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i])){ vertex(p[i]); }
		    }
		    else{ vertex(p[i]); }
		}
	    }
	    else{
		for(int i=0; i<p.length; i++){
		    ambient(clr[i], alpha);
		    diffuse(clr[i], alpha);
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i])){ vertex(p[i]); }
		    }
		    else{ vertex(p[i]); }
		}
	    }
	}
	else{
	    if(alpha<0){
		for(int i=0; i<p.length; i++){
		    clr(clr[i]);
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i])){ vertex(p[i]); }
		    }
		    else{ vertex(p[i]); }
		}
	    }
	    else{
		for(int i=0; i<p.length; i++){
		    clr(clr[i], alpha);
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i])){ vertex(p[i]); }
		    }
		    else{ vertex(p[i]); }
		}
	    }
	}
	super.endShape(CLOSE);
    }
    public void drawPolygon(IVec[] p, IVec[] n, IColor[] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	super.beginShape(POLYGON);
	if(light){
	    if(alpha<0){
		for(int i=0; i<p.length; i++){
		    ambient(clr[i]);
		    diffuse(clr[i]);
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i])){ normal(n[i]); vertex(p[i]); }
		    }
		    else{ normal(n[i]); vertex(p[i]); }
		}
	    }
	    else{
		for(int i=0; i<p.length; i++){
		    ambient(clr[i], alpha);
		    diffuse(clr[i], alpha);
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i])){ normal(n[i]); vertex(p[i]); }
		    }
		    else{ normal(n[i]); vertex(p[i]); }
		}
	    }
	}
	else{
	    if(alpha<0){
		for(int i=0; i<p.length; i++){
		    clr(clr[i]);
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i])){ normal(n[i]); vertex(p[i]); }
		    }
		    else{ normal(n[i]); vertex(p[i]); }
		}
	    }
	    else{
		for(int i=0; i<p.length; i++){
		    clr(clr[i], alpha);
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i])){ normal(n[i]); vertex(p[i]); }
		    }
		    else{ normal(n[i]); vertex(p[i]); }
		}
	    }
	}
	super.endShape(CLOSE);
    }
    public void drawQuads(IVec[] p){
	super.stroke= false;
	super.fill = true;
	super.beginShape(QUADS);
	for(int i=0; i<p.length-3; i+=4){
	    if(IConfig.cullVertexBehindViewInP3D){
		if(isInFront(p[i]) && isInFront(p[i+1]) &&
		   isInFront(p[i+2]) && isInFront(p[i+3])){
		    vertex(p[i]);
		    vertex(p[i+1]);
		    vertex(p[i+2]);
		    vertex(p[i+3]);
		}
	    }
	    else{
		vertex(p[i]);
		vertex(p[i+1]);
		vertex(p[i+2]);
		vertex(p[i+3]);
	    }
	}
	super.endShape();
    }
    public void drawQuads(IVec[] p, IVec[] n){
	super.stroke= false;
	super.fill = true;
	super.beginShape(QUADS);
	for(int i=0; i<p.length-3; i+=4){
	    if(IConfig.cullVertexBehindViewInP3D){
		if(isInFront(p[i]) && isInFront(p[i+1]) &&
		   isInFront(p[i+2]) && isInFront(p[i+3])){
		    normal(n[i]); vertex(p[i]);
		    normal(n[i+1]); vertex(p[i+1]);
		    normal(n[i+2]); vertex(p[i+2]);
		    normal(n[i+3]); vertex(p[i+3]);
		}
	    }
	    else{
		normal(n[i]); vertex(p[i]);
		normal(n[i+1]); vertex(p[i+1]);
		normal(n[i+2]); vertex(p[i+2]);
		normal(n[i+3]); vertex(p[i+3]);
	    }
	}
	//vertex(p,n);
	super.endShape();
    }
    public void drawQuads(IVec[] p, IColor[] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	super.beginShape(QUADS);
	if(light){
	    if(alpha<0){
		for(int i=0; i<p.length-3; i+=4){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) &&
			   isInFront(p[i+2]) && isInFront(p[i+3])){
			    ambient(clr[i]);
			    diffuse(clr[i]);
			    vertex(p[i]);
			    ambient(clr[i+1]);
			    diffuse(clr[i+1]);
			    vertex(p[i+1]);
			    ambient(clr[i+2]);
			    diffuse(clr[i+2]);
			    vertex(p[i+2]);
			    ambient(clr[i+3]);
			    diffuse(clr[i+3]);
			    vertex(p[i+3]);
			}
		    }
		    else{
			ambient(clr[i]);
			diffuse(clr[i]);
			vertex(p[i]);
			ambient(clr[i+1]);
			diffuse(clr[i+1]);
			vertex(p[i+1]);
			ambient(clr[i+2]);
			diffuse(clr[i+2]);
			vertex(p[i+2]);
			ambient(clr[i+3]);
			diffuse(clr[i+3]);
			vertex(p[i+3]);
		    }
		}
	    }
	    else{
		for(int i=0; i<p.length-3; i+=4){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) &&
			   isInFront(p[i+2]) && isInFront(p[i+3])){
			    ambient(clr[i], alpha);
			    diffuse(clr[i], alpha);
			    vertex(p[i]);
			    ambient(clr[i+1], alpha);
			    diffuse(clr[i+1], alpha);
			    vertex(p[i+1]);
			    ambient(clr[i+2], alpha);
			    diffuse(clr[i+2], alpha);
			    vertex(p[i+2]);
			    ambient(clr[i+3], alpha);
			    diffuse(clr[i+3], alpha);
			    vertex(p[i+3]);
			}
		    }
		    else{
			ambient(clr[i], alpha);
			diffuse(clr[i], alpha);
			vertex(p[i]);
			ambient(clr[i+1], alpha);
			diffuse(clr[i+1], alpha);
			vertex(p[i+1]);
			ambient(clr[i+2], alpha);
			diffuse(clr[i+2], alpha);
			vertex(p[i+2]);
			ambient(clr[i+3], alpha);
			diffuse(clr[i+3], alpha);
			vertex(p[i+3]);
		    }
		}
	    }
	}
	else{
	    if(alpha<0){
		for(int i=0; i<p.length-3; i+=4){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) &&
			   isInFront(p[i+2]) && isInFront(p[i+3])){
			    clr(clr[i]);
			    vertex(p[i]);
			    clr(clr[i+1]);
			    vertex(p[i+1]);
			    clr(clr[i+2]);
			    vertex(p[i+2]);
			    clr(clr[i+3]);
			    vertex(p[i+3]);
			}
		    }
		    else{
			clr(clr[i]);
			vertex(p[i]);
			clr(clr[i+1]);
			vertex(p[i+1]);
			clr(clr[i+2]);
			vertex(p[i+2]);
			clr(clr[i+3]);
			vertex(p[i+3]);
		    }
		}
	    }
	    else{
		for(int i=0; i<p.length-3; i+=4){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) &&
			   isInFront(p[i+2]) && isInFront(p[i+3])){
			    clr(clr[i], alpha);
			    vertex(p[i]);
			    clr(clr[i+1], alpha);
			    vertex(p[i+1]);
			    clr(clr[i+2], alpha);
			    vertex(p[i+2]);
			    clr(clr[i+3], alpha);
			    vertex(p[i+3]);
			}
		    }
		    else{
			clr(clr[i], alpha);
			vertex(p[i]);
			clr(clr[i+1], alpha);
			vertex(p[i+1]);
			clr(clr[i+2], alpha);
			vertex(p[i+2]);
			clr(clr[i+3], alpha);
			vertex(p[i+3]);
		    }
		}
	    }
	}
	super.endShape();
    }
    public void drawQuads(IVec[] p, IVec[] n, IColor[] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	super.beginShape(QUADS);
	if(light){
	    if(alpha<0){
		for(int i=0; i<p.length-3; i+=4){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) &&
			   isInFront(p[i+2]) && isInFront(p[i+3])){
			    ambient(clr[i]); diffuse(clr[i]); normal(n[i]); vertex(p[i]);
			    ambient(clr[i+1]); diffuse(clr[i+1]); normal(n[i+1]); vertex(p[i+1]);
			    ambient(clr[i+2]); diffuse(clr[i+2]); normal(n[i+2]); vertex(p[i+2]);
			    ambient(clr[i+3]); diffuse(clr[i+3]); normal(n[i+3]); vertex(p[i+3]);
			}
		    }
		    else{
			ambient(clr[i]); diffuse(clr[i]); normal(n[i]); vertex(p[i]);
			ambient(clr[i+1]); diffuse(clr[i+1]); normal(n[i+1]); vertex(p[i+1]);
			ambient(clr[i+2]); diffuse(clr[i+2]); normal(n[i+2]); vertex(p[i+2]);
			ambient(clr[i+3]); diffuse(clr[i+3]); normal(n[i+3]); vertex(p[i+3]);
		    }
		}
	    }
	    else{
		for(int i=0; i<p.length-3; i+=4){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) &&
			   isInFront(p[i+2]) && isInFront(p[i+3])){
			    ambient(clr[i],alpha); diffuse(clr[i],alpha); normal(n[i]); vertex(p[i]);
			    ambient(clr[i+1],alpha); diffuse(clr[i+1],alpha); normal(n[i+1]); vertex(p[i+1]);
			    ambient(clr[i+2],alpha); diffuse(clr[i+2],alpha); normal(n[i+2]); vertex(p[i+2]);
			    ambient(clr[i+3],alpha); diffuse(clr[i+3],alpha); normal(n[i+3]); vertex(p[i+3]);
			}
		    }
		    else{
			ambient(clr[i],alpha); diffuse(clr[i],alpha); normal(n[i]); vertex(p[i]);
			ambient(clr[i+1],alpha); diffuse(clr[i+1],alpha); normal(n[i+1]); vertex(p[i+1]);
			ambient(clr[i+2],alpha); diffuse(clr[i+2],alpha); normal(n[i+2]); vertex(p[i+2]);
			ambient(clr[i+3],alpha); diffuse(clr[i+3],alpha); normal(n[i+3]); vertex(p[i+3]);
		    }
		}
	    }
	}
	else{
	    if(alpha<0){
		for(int i=0; i<p.length-3; i+=4){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) &&
			   isInFront(p[i+2]) && isInFront(p[i+3])){
			    clr(clr[i]); normal(n[i]); vertex(p[i]);
			    clr(clr[i+1]); normal(n[i+1]); vertex(p[i+1]);
			    clr(clr[i+2]); normal(n[i+2]); vertex(p[i+2]);
			    clr(clr[i+3]); normal(n[i+3]); vertex(p[i+3]);
			}
		    }
		    else{
			clr(clr[i]); normal(n[i]); vertex(p[i]);
			clr(clr[i+1]); normal(n[i+1]); vertex(p[i+1]);
			clr(clr[i+2]); normal(n[i+2]); vertex(p[i+2]);
			clr(clr[i+3]); normal(n[i+3]); vertex(p[i+3]);
		    }
		}
	    }
	    else{
		for(int i=0; i<p.length-3; i+=4){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) &&
			   isInFront(p[i+2]) && isInFront(p[i+3])){
			    clr(clr[i],alpha); normal(n[i]); vertex(p[i]);
			    clr(clr[i+1],alpha); normal(n[i+1]); vertex(p[i+1]);
			    clr(clr[i+2],alpha); normal(n[i+2]); vertex(p[i+2]);
			    clr(clr[i+3],alpha); normal(n[i+3]); vertex(p[i+3]);
			}
		    }
		    else{
			clr(clr[i],alpha); normal(n[i]); vertex(p[i]);
			clr(clr[i+1],alpha); normal(n[i+1]); vertex(p[i+1]);
			clr(clr[i+2],alpha); normal(n[i+2]); vertex(p[i+2]);
			clr(clr[i+3],alpha); normal(n[i+3]); vertex(p[i+3]);
		    }
		}
	    }
	}
	super.endShape();
    }
    public void drawQuadStrip(IVec[] p){
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    boolean previousFront=false;
	    boolean isDrawing=false;
	    for(int i=0; i<p.length-1; i+=2){
		boolean currentFront = isInFront(p[i]) && isInFront(p[i+1]);
		if(currentFront){
		    if(isDrawing){
			vertex(p[i]);
			vertex(p[i+1]);
		    }
		    else{
			super.beginShape(QUAD_STRIP);
			vertex(p[i]);
			vertex(p[i+1]);
			isDrawing=true;
		    }
		}
		else if(isDrawing){ super.endShape(); isDrawing=false; }
		previousFront = currentFront;
	    }
	    if(isDrawing){ super.endShape(); }
	}
	else{
	    super.beginShape(QUAD_STRIP);
	    for(int i=0; i<p.length; i++){ vertex(p[i]); }
	    super.endShape();
	}
    }
    public void drawQuadStrip(IVec[] p, IVec[] n){
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    boolean previousFront=false;
	    boolean isDrawing=false;
	    for(int i=0; i<p.length-1; i+=2){
		boolean currentFront = isInFront(p[i]) && isInFront(p[i+1]);
		if(currentFront){
		    if(isDrawing){
			normal(n[i]);
			vertex(p[i]);
			normal(n[i+1]);
			vertex(p[i+1]);
		    }
		    else{
			super.beginShape(QUAD_STRIP);
			normal(n[i]);
			vertex(p[i]);
			normal(n[i+1]);
			vertex(p[i+1]);
			isDrawing=true;
		    }
		}
		else if(isDrawing){ super.endShape(); isDrawing=false; }
		previousFront = currentFront;
	    }
	    if(isDrawing){ super.endShape(); }
	}
	else{
	    super.beginShape(QUAD_STRIP);
	    for(int i=0; i<p.length; i++){ normal(n[i]); vertex(p[i]); }
	    super.endShape();
	}
    }
    public void drawQuadStrip(IVec[] p, IColor[] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    boolean previousFront=false;
	    boolean isDrawing=false;
	    if(light){
		if(alpha<0){
		    for(int i=0; i<p.length-1; i+=2){
			boolean currentFront = isInFront(p[i]) && isInFront(p[i+1]);
			if(currentFront){
			    if(isDrawing){
				ambient(clr[i]);
				diffuse(clr[i]);
				vertex(p[i]);
				ambient(clr[i+1]);
				diffuse(clr[i+1]);
				vertex(p[i+1]);
			    }
			    else{
				super.beginShape(QUAD_STRIP);
				ambient(clr[i]);
				diffuse(clr[i]);
				vertex(p[i]);
				ambient(clr[i+1]);
				diffuse(clr[i+1]);
				vertex(p[i+1]);
				isDrawing=true;
			    }
			}
			else if(isDrawing){ super.endShape(); isDrawing=false; }
			previousFront = currentFront;
		    }
		}
		else{
		    for(int i=0; i<p.length-1; i+=2){
			boolean currentFront = isInFront(p[i]) && isInFront(p[i+1]);
			if(currentFront){
			    if(isDrawing){
				ambient(clr[i], alpha);
				diffuse(clr[i], alpha);
				vertex(p[i]);
				ambient(clr[i+1], alpha);
				diffuse(clr[i+1], alpha);
				vertex(p[i+1]);
			    }
			    else{
				super.beginShape(QUAD_STRIP);
				ambient(clr[i], alpha);
				diffuse(clr[i], alpha);
				vertex(p[i]);
				ambient(clr[i+1], alpha);
				diffuse(clr[i+1], alpha);
				vertex(p[i+1]);
				isDrawing=true;
			    }
			}
			else if(isDrawing){ super.endShape(); isDrawing=false; }
			previousFront = currentFront;
		    }
		}
	    }
	    else{
		if(alpha<0){
		    for(int i=0; i<p.length-1; i+=2){
			boolean currentFront = isInFront(p[i]) && isInFront(p[i+1]);
			if(currentFront){
			    if(isDrawing){
				clr(clr[i]);
				vertex(p[i]);
				clr(clr[i+1]);
				vertex(p[i+1]);
			    }
			    else{
				super.beginShape(QUAD_STRIP);
				clr(clr[i]);
				vertex(p[i]);
				clr(clr[i+1]);
				vertex(p[i+1]);
				isDrawing=true;
			    }
			}
			else if(isDrawing){ super.endShape(); isDrawing=false; }
			previousFront = currentFront;
		    }
		}
		else{
		    for(int i=0; i<p.length-1; i+=2){
			boolean currentFront = isInFront(p[i]) && isInFront(p[i+1]);
			if(currentFront){
			    if(isDrawing){
				clr(clr[i], alpha);
				vertex(p[i]);
				clr(clr[i+1], alpha);
				vertex(p[i+1]);
			    }
			    else{
				super.beginShape(QUAD_STRIP);
				clr(clr[i], alpha);
				vertex(p[i]);
				clr(clr[i+1], alpha);
				vertex(p[i+1]);
				isDrawing=true;
			    }
			}
			else if(isDrawing){ super.endShape(); isDrawing=false; }
			previousFront = currentFront;
		    }
		}
	    }
	    if(isDrawing){ super.endShape(); }
	}
	else{
	    super.beginShape(QUAD_STRIP);
	    if(light){
		if(alpha<0){
		    for(int i=0; i<p.length; i++){
			ambient(clr[i]); diffuse(clr[i]); vertex(p[i]);
		    }
		}
		else{
		    for(int i=0; i<p.length; i++){
			ambient(clr[i], alpha); diffuse(clr[i], alpha); vertex(p[i]);
		    }
		}
	    }
	    else{
		if(alpha<0){
		    for(int i=0; i<p.length; i++){ clr(clr[i]); vertex(p[i]); }
		}
		else{
		    for(int i=0; i<p.length; i++){ clr(clr[i], alpha); vertex(p[i]); }
		}
	    }
	    super.endShape();
	}
    }
    public void drawQuadStrip(IVec[] p, IVec[] n, IColor[] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    boolean previousFront=false;
	    boolean isDrawing=false;
	    if(light){
		if(alpha<0){
		    for(int i=0; i<p.length-1; i+=2){
			boolean currentFront = isInFront(p[i]) && isInFront(p[i+1]);
			if(currentFront){
			    if(isDrawing){
				ambient(clr[i]);
				diffuse(clr[i]);
				normal(n[i]);
				vertex(p[i]);
				ambient(clr[i+1]);
				diffuse(clr[i+1]);
				normal(n[i+1]);
				vertex(p[i+1]);
			    }
			    else{
				super.beginShape(QUAD_STRIP);
				ambient(clr[i]);
				diffuse(clr[i]);
				normal(n[i]);
				vertex(p[i]);
				ambient(clr[i+1]);
				diffuse(clr[i+1]);
				normal(n[i+1]);
				vertex(p[i+1]);
				isDrawing=true;
			    }
			}
			else if(isDrawing){ super.endShape(); isDrawing=false; }
			previousFront = currentFront;
		}
		}
		else{
		    for(int i=0; i<p.length-1; i+=2){
			boolean currentFront = isInFront(p[i]) && isInFront(p[i+1]);
			if(currentFront){
			    if(isDrawing){
				ambient(clr[i], alpha);
				diffuse(clr[i], alpha);
				normal(n[i]);
				vertex(p[i]);
				ambient(clr[i+1], alpha);
				diffuse(clr[i+1], alpha);
				normal(n[i+1]);
				vertex(p[i+1]);
			    }
			    else{
				super.beginShape(QUAD_STRIP);
				ambient(clr[i], alpha);
				diffuse(clr[i], alpha);
				normal(n[i]);
				vertex(p[i]);
				ambient(clr[i+1], alpha);
				diffuse(clr[i+1], alpha);
				normal(n[i+1]);
				vertex(p[i+1]);
				isDrawing=true;
			    }
			}
			else if(isDrawing){ super.endShape(); isDrawing=false; }
			previousFront = currentFront;
		    }
		}
	    }
	    else{
		if(alpha<0){
		    for(int i=0; i<p.length-1; i+=2){
			boolean currentFront = isInFront(p[i]) && isInFront(p[i+1]);
			if(currentFront){
			    if(isDrawing){
				clr(clr[i]);
				normal(n[i]);
				vertex(p[i]);
				clr(clr[i+1]);
				normal(n[i+1]);
				vertex(p[i+1]);
			    }
			    else{
				super.beginShape(QUAD_STRIP);
				clr(clr[i]);
				normal(n[i]);
				vertex(p[i]);
				clr(clr[i+1]);
				normal(n[i+1]);
				vertex(p[i+1]);
				isDrawing=true;
			    }
			}
			else if(isDrawing){ super.endShape(); isDrawing=false; }
			previousFront = currentFront;
		    }
		}
		else{
		    for(int i=0; i<p.length-1; i+=2){
			boolean currentFront = isInFront(p[i]) && isInFront(p[i+1]);
			if(currentFront){
			    if(isDrawing){
				clr(clr[i], alpha);
				normal(n[i]);
				vertex(p[i]);
				clr(clr[i+1], alpha);
				normal(n[i+1]);
				vertex(p[i+1]);
			    }
			    else{
				super.beginShape(QUAD_STRIP);
				clr(clr[i], alpha);
				normal(n[i]);
				vertex(p[i]);
				clr(clr[i+1], alpha);
				normal(n[i+1]);
				vertex(p[i+1]);
				isDrawing=true;
			    }
			}
			else if(isDrawing){ super.endShape(); isDrawing=false; }
			previousFront = currentFront;
		    }
		}
	    }
	    if(isDrawing){ super.endShape(); }
	}
	else{
	    super.beginShape(QUAD_STRIP);
	    if(alpha<0){
		for(int i=0; i<p.length; i++){ clr(clr[i]); normal(n[i]); vertex(p[i]); }
	    }
	    else{
		for(int i=0; i<p.length; i++){ clr(clr[i],alpha); normal(n[i]); vertex(p[i]); }
	    }
	    super.endShape();
	}
    }
    public void drawQuadMatrix(IVec[][] pts){
	super.stroke= false;
	super.fill = true;
        for(int i=0; i<pts.length-1; i++){
	    
	    if(IConfig.cullVertexBehindViewInP3D){
		boolean previousFront=false;
		boolean isDrawing=false;
		for(int j=0; j<pts[i].length; j++){
		    boolean currentFront = isInFront(pts[i][j]) && isInFront(pts[i+1][j]);
		    if(currentFront){
			if(isDrawing){
			    vertex(pts[i][j]);
			    vertex(pts[i+1][j]);
			}
			else{
			    super.beginShape(QUAD_STRIP);
			    vertex(pts[i][j]);
			    vertex(pts[i+1][j]);
			    isDrawing=true;
			}
		    }
		    else if(isDrawing){ super.endShape(); isDrawing=false; }
		    previousFront = currentFront;
		}
		if(isDrawing){ super.endShape(); }
	    }
	    else{
		super.beginShape(QUAD_STRIP);
		for(int j=0; j<pts[i].length; j++){
		    vertex(pts[i][j]);
		    vertex(pts[i+1][j]);
		}
		super.endShape();
	    }
        }
    }
    public void drawQuadMatrix(IVec[][] pts, IVec[][] nml){
	
	super.stroke= false;
	super.fill = true;
	
        for(int i=0; i<pts.length-1; i++){
	    
	    if(IConfig.cullVertexBehindViewInP3D){
		boolean previousFront=false;
		boolean isDrawing=false;
		for(int j=0; j<pts[i].length; j++){
		    boolean currentFront = isInFront(pts[i][j]) && isInFront(pts[i+1][j]);
		    if(currentFront){
			if(isDrawing){
			    normal(nml[i][j]);
			    vertex(pts[i][j]);
			    normal(nml[i+1][j]);
			    vertex(pts[i+1][j]);
			}
			else{
			    super.beginShape(QUAD_STRIP);
			    normal(nml[i][j]);
			    vertex(pts[i][j]);
			    normal(nml[i+1][j]);
			    vertex(pts[i+1][j]);
			    isDrawing=true;
			}
		    }
		    else if(isDrawing){ super.endShape(); isDrawing=false; }
		    previousFront = currentFront;
		}
		if(isDrawing){ super.endShape(); }
	    }
	    else{
		super.beginShape(QUAD_STRIP);
		for(int j=0; j<pts[i].length; j++){
		    normal(nml[i][j]);
		    vertex(pts[i][j]);
		    normal(nml[i+1][j]);
		    vertex(pts[i+1][j]);
		}
		super.endShape();
	    }
        }
    }
    public void drawQuadMatrix(IVec[][] pts, IColor[][] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	if(light){
	    if(alpha<0){
		for(int i=0; i<pts.length-1; i++){
		    if(IConfig.cullVertexBehindViewInP3D){
			boolean previousFront=false;
			boolean isDrawing=false;
			for(int j=0; j<pts[i].length; j++){
			    boolean currentFront = isInFront(pts[i][j]) && isInFront(pts[i+1][j]);
			    if(currentFront){
				if(isDrawing){
				    ambient(clr[i][j]);
				    diffuse(clr[i][j]);
				    vertex(pts[i][j]);
				    ambient(clr[i+1][j]);
				    diffuse(clr[i+1][j]);
				    vertex(pts[i+1][j]);
				}
				else{
				    super.beginShape(QUAD_STRIP);
				    ambient(clr[i][j]);
				    diffuse(clr[i][j]);
				    vertex(pts[i][j]);
				    ambient(clr[i+1][j]);
				    diffuse(clr[i+1][j]);
				    vertex(pts[i+1][j]);
				    isDrawing=true;
				}
			    }
			    else if(isDrawing){ super.endShape(); isDrawing=false; }
			    previousFront = currentFront;
			}
			if(isDrawing){ super.endShape(); }
		    }
		    else{
			super.beginShape(QUAD_STRIP);
			for(int j=0; j<pts[i].length; j++){
			    ambient(clr[i][j]);
			    diffuse(clr[i][j]);
			    vertex(pts[i][j]);
			    ambient(clr[i+1][j]);
			    diffuse(clr[i+1][j]);
			    vertex(pts[i+1][j]);
			}
			super.endShape();
		    }
		}
	    }
	    else{
		for(int i=0; i<pts.length-1; i++){
		    if(IConfig.cullVertexBehindViewInP3D){
			boolean previousFront=false;
			boolean isDrawing=false;
			for(int j=0; j<pts[i].length; j++){
			    boolean currentFront = isInFront(pts[i][j]) && isInFront(pts[i+1][j]);
			    if(currentFront){
				if(isDrawing){
				    ambient(clr[i][j], alpha);
				    diffuse(clr[i][j], alpha);
				    vertex(pts[i][j]);
				    ambient(clr[i+1][j], alpha);
				    diffuse(clr[i+1][j], alpha);
				    vertex(pts[i+1][j]);
				}
				else{
				    super.beginShape(QUAD_STRIP);
				    ambient(clr[i][j], alpha);
				    diffuse(clr[i][j], alpha);
				    vertex(pts[i][j]);
				    ambient(clr[i+1][j], alpha);
				    diffuse(clr[i+1][j], alpha);
				    vertex(pts[i+1][j]);
				    isDrawing=true;
				}
			    }
			    else if(isDrawing){ super.endShape(); isDrawing=false; }
			    previousFront = currentFront;
			}
			if(isDrawing){ super.endShape(); }
		    }
		    else{
			super.beginShape(QUAD_STRIP);
			for(int j=0; j<pts[i].length; j++){
			    ambient(clr[i][j], alpha);
			    diffuse(clr[i][j], alpha);
			    vertex(pts[i][j]);
			    ambient(clr[i+1][j], alpha);
			    diffuse(clr[i+1][j], alpha);
			    vertex(pts[i+1][j]);
			}
			super.endShape();
		    }
		}
	    }
	}
	else{
	    if(alpha<0){
		for(int i=0; i<pts.length-1; i++){
		    if(IConfig.cullVertexBehindViewInP3D){
			boolean previousFront=false;
			boolean isDrawing=false;
			for(int j=0; j<pts[i].length; j++){
			    boolean currentFront = isInFront(pts[i][j]) && isInFront(pts[i+1][j]);
			    if(currentFront){
				if(isDrawing){
				    clr(clr[i][j]);
				    vertex(pts[i][j]);
				    clr(clr[i+1][j]);
				    vertex(pts[i+1][j]);
				}
				else{
				    super.beginShape(QUAD_STRIP);
				    clr(clr[i][j]);
				    vertex(pts[i][j]);
				    clr(clr[i+1][j]);
				    vertex(pts[i+1][j]);
				    isDrawing=true;
				}
			    }
			    else if(isDrawing){ super.endShape(); isDrawing=false; }
			    previousFront = currentFront;
			}
			if(isDrawing){ super.endShape(); }
		    }
		    else{
			super.beginShape(QUAD_STRIP);
			for(int j=0; j<pts[i].length; j++){
			    clr(clr[i][j]);
			    vertex(pts[i][j]);
			    clr(clr[i+1][j]);
			    vertex(pts[i+1][j]);
			}
			super.endShape();
		    }
		}
	    }
	    else{
		for(int i=0; i<pts.length-1; i++){
		    if(IConfig.cullVertexBehindViewInP3D){
			boolean previousFront=false;
			boolean isDrawing=false;
			for(int j=0; j<pts[i].length; j++){
			    boolean currentFront = isInFront(pts[i][j]) && isInFront(pts[i+1][j]);
			    if(currentFront){
				if(isDrawing){
				    clr(clr[i][j], alpha);
				    vertex(pts[i][j]);
				    clr(clr[i+1][j], alpha);
				    vertex(pts[i+1][j]);
				}
				else{
				    super.beginShape(QUAD_STRIP);
				    clr(clr[i][j], alpha);
				    vertex(pts[i][j]);
				    clr(clr[i+1][j], alpha);
				    vertex(pts[i+1][j]);
				    isDrawing=true;
				}
			    }
			    else if(isDrawing){ super.endShape(); isDrawing=false; }
			    previousFront = currentFront;
			}
			if(isDrawing){ super.endShape(); }
		    }
		    else{
			super.beginShape(QUAD_STRIP);
			for(int j=0; j<pts[i].length; j++){
			    clr(clr[i][j], alpha);
			    vertex(pts[i][j]);
			    clr(clr[i+1][j], alpha);
			    vertex(pts[i+1][j]);
			}
			super.endShape();
		    }
		}
	    }
	}
    }
    public void drawQuadMatrix(IVec[][] pts, IVec[][] nml, IColor[][] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	if(light){
	    if(alpha<0){
		for(int i=0; i<pts.length-1; i++){
		    if(IConfig.cullVertexBehindViewInP3D){
			boolean previousFront=false;
			boolean isDrawing=false;
			for(int j=0; j<pts[i].length; j++){
			    boolean currentFront = isInFront(pts[i][j]) && isInFront(pts[i+1][j]);
			    if(currentFront){
				if(isDrawing){
				    ambient(clr[i][j]);
				    diffuse(clr[i][j]);
				    normal(nml[i][j]);
				    vertex(pts[i][j]);
				    ambient(clr[i+1][j]);
				    diffuse(clr[i+1][j]);
				    normal(nml[i+1][j]);
				    vertex(pts[i+1][j]);
				}
				else{
				    super.beginShape(QUAD_STRIP);
				    ambient(clr[i][j]);
				    diffuse(clr[i][j]);
				    normal(nml[i][j]);
				    vertex(pts[i][j]);
				    ambient(clr[i+1][j]);
				    diffuse(clr[i+1][j]);
				    normal(nml[i+1][j]);
				    vertex(pts[i+1][j]);
				    isDrawing=true;
				}
			    }
			    else if(isDrawing){ super.endShape(); isDrawing=false; }
			    previousFront = currentFront;
			}
			if(isDrawing){ super.endShape(); }
		    }
		    else{
			super.beginShape(QUAD_STRIP);
			for(int j=0; j<pts[i].length; j++){
			    ambient(clr[i][j]);
			    diffuse(clr[i][j]);
			    normal(nml[i][j]);
			    vertex(pts[i][j]);
			    ambient(clr[i+1][j]);
			    diffuse(clr[i+1][j]);
			    normal(nml[i+1][j]);
			    vertex(pts[i+1][j]);
			}
			super.endShape();
		    }
		}
	    }
	    else{
		for(int i=0; i<pts.length-1; i++){
		    if(IConfig.cullVertexBehindViewInP3D){
			boolean previousFront=false;
			boolean isDrawing=false;
			for(int j=0; j<pts[i].length; j++){
			    boolean currentFront = isInFront(pts[i][j]) && isInFront(pts[i+1][j]);
			    if(currentFront){
				if(isDrawing){
				    ambient(clr[i][j], alpha);
				    diffuse(clr[i][j], alpha);
				    normal(nml[i][j]);
				    vertex(pts[i][j]);
				    ambient(clr[i+1][j], alpha);
				    diffuse(clr[i+1][j], alpha);
				    normal(nml[i+1][j]);
				    vertex(pts[i+1][j]);
				}
				else{
				    super.beginShape(QUAD_STRIP);
				    ambient(clr[i][j], alpha);
				    diffuse(clr[i][j], alpha);
				    normal(nml[i][j]);
				    vertex(pts[i][j]);
				    ambient(clr[i+1][j], alpha);
				    diffuse(clr[i+1][j], alpha);
				    normal(nml[i+1][j]);
				    vertex(pts[i+1][j]);
				    isDrawing=true;
				}
			    }
			    else if(isDrawing){ super.endShape(); isDrawing=false; }
			    previousFront = currentFront;
			}
			if(isDrawing){ super.endShape(); }
		    }
		    else{
			super.beginShape(QUAD_STRIP);
			for(int j=0; j<pts[i].length; j++){
			    ambient(clr[i][j], alpha);
			    diffuse(clr[i][j], alpha);
			    normal(nml[i][j]);
			    vertex(pts[i][j]);
			    ambient(clr[i+1][j], alpha);
			    diffuse(clr[i+1][j], alpha);
			    normal(nml[i+1][j]);
			    vertex(pts[i+1][j]);
			}
			super.endShape();
		    }
		}
	    }
	}
	else{
	    if(alpha<0){
		for(int i=0; i<pts.length-1; i++){
		    if(IConfig.cullVertexBehindViewInP3D){
			boolean previousFront=false;
			boolean isDrawing=false;
			for(int j=0; j<pts[i].length; j++){
			    boolean currentFront = isInFront(pts[i][j]) && isInFront(pts[i+1][j]);
			    if(currentFront){
				if(isDrawing){
				    clr(clr[i][j]);
				    normal(nml[i][j]);
				    vertex(pts[i][j]);
				    clr(clr[i+1][j]);
				    normal(nml[i+1][j]);
				    vertex(pts[i+1][j]);
				}
				else{
				    super.beginShape(QUAD_STRIP);
				    clr(clr[i][j]);
				    normal(nml[i][j]);
				    vertex(pts[i][j]);
				    clr(clr[i+1][j]);
				    normal(nml[i+1][j]);
				    vertex(pts[i+1][j]);
				    isDrawing=true;
				}
			    }
			    else if(isDrawing){ super.endShape(); isDrawing=false; }
			    previousFront = currentFront;
			}
			if(isDrawing){ super.endShape(); }
		    }
		    else{
			super.beginShape(QUAD_STRIP);
			for(int j=0; j<pts[i].length; j++){
			    clr(clr[i][j]);
			    normal(nml[i][j]);
			    vertex(pts[i][j]);
			    clr(clr[i+1][j]);
			    normal(nml[i+1][j]);
			    vertex(pts[i+1][j]);
			}
			super.endShape();
		    }
		}
	    }
	    else{
		for(int i=0; i<pts.length-1; i++){
		    if(IConfig.cullVertexBehindViewInP3D){
			boolean previousFront=false;
			boolean isDrawing=false;
			for(int j=0; j<pts[i].length; j++){
			    boolean currentFront = isInFront(pts[i][j]) && isInFront(pts[i+1][j]);
			    if(currentFront){
				if(isDrawing){
				    clr(clr[i][j], alpha);
				    normal(nml[i][j]);
				    vertex(pts[i][j]);
				    clr(clr[i+1][j], alpha);
				    normal(nml[i+1][j]);
				    vertex(pts[i+1][j]);
				}
				else{
				    super.beginShape(QUAD_STRIP);
				    clr(clr[i][j], alpha);
				    normal(nml[i][j]);
				    vertex(pts[i][j]);
				    clr(clr[i+1][j], alpha);
				    normal(nml[i+1][j]);
				    vertex(pts[i+1][j]);
				    isDrawing=true;
				}
			    }
			    else if(isDrawing){ super.endShape(); isDrawing=false; }
			    previousFront = currentFront;
			}
			if(isDrawing){ super.endShape(); }
		    }
		    else{
			super.beginShape(QUAD_STRIP);
			for(int j=0; j<pts[i].length; j++){
			    clr(clr[i][j], alpha);
			    normal(nml[i][j]);
			    vertex(pts[i][j]);
			    clr(clr[i+1][j], alpha);
			    normal(nml[i+1][j]);
			    vertex(pts[i+1][j]);
			}
			super.endShape();
		    }
		}
	    }
	}
    }
    public void drawTriangles(IVec[] p){
	super.stroke= false;
	super.fill = true;
	super.beginShape(TRIANGLES);
	for(int i=0; i<p.length-2; i+=3){
	    if(IConfig.cullVertexBehindViewInP3D){
		if(isInFront(p[i]) && isInFront(p[i+1]) && isInFront(p[i+2])){
		    vertex(p[i]);
		    vertex(p[i+1]);
		    vertex(p[i+2]);
		}
	    }
	    else{
		vertex(p[i]);
		vertex(p[i+1]);
		vertex(p[i+2]);
	    }
	}
	//vertex(p);
	super.endShape();
    }
    public void drawTriangles(IVec[] p, IVec[] n){
	super.stroke= false;
	super.fill = true;
	super.beginShape(TRIANGLES);
	for(int i=0; i<p.length-2; i+=3){
	    if(IConfig.cullVertexBehindViewInP3D){
		if(isInFront(p[i]) && isInFront(p[i+1]) && isInFront(p[i+2])){
		    normal(n[i]); vertex(p[i]);
		    normal(n[i+1]); vertex(p[i+1]);
		    normal(n[i+2]); vertex(p[i+2]);
		}
	    }
	    else{
		normal(n[i]); vertex(p[i]);
		normal(n[i+1]); vertex(p[i+1]);
		normal(n[i+2]); vertex(p[i+2]);
	    }
	}
	//vertex(p,n);
	super.endShape();
    }
    public void drawTriangles(IVec[] p, IColor[] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	super.beginShape(TRIANGLES);
	if(light){
	    if(alpha<0){
		for(int i=0; i<p.length-2; i+=3){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) && isInFront(p[i+2])){
			    ambient(clr[i]);
			    diffuse(clr[i]);
			    vertex(p[i]);
			    ambient(clr[i+1]);
			    diffuse(clr[i+1]);
			    vertex(p[i+1]);
			    ambient(clr[i+2]);
			    diffuse(clr[i+2]);
			    vertex(p[i+2]);
			}
		    }
		    else{
			ambient(clr[i]);
			diffuse(clr[i]);
			vertex(p[i]);
			ambient(clr[i+1]);
			diffuse(clr[i+1]);
			vertex(p[i+1]);
			ambient(clr[i+2]);
			diffuse(clr[i+2]);
			vertex(p[i+2]);
		    }
		}
	    }
	    else{
		for(int i=0; i<p.length-2; i+=3){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) && isInFront(p[i+2])){
			    ambient(clr[i], alpha);
			    diffuse(clr[i], alpha);
			    vertex(p[i]);
			    ambient(clr[i+1], alpha);
			    diffuse(clr[i+1], alpha);
			    vertex(p[i+1]);
			    ambient(clr[i+2], alpha);
			    diffuse(clr[i+2], alpha);
			    vertex(p[i+2]);
			}
		    }
		    else{
			ambient(clr[i], alpha);
			diffuse(clr[i], alpha);
			vertex(p[i]);
			ambient(clr[i+1], alpha);
			diffuse(clr[i+1], alpha);
			vertex(p[i+1]);
			ambient(clr[i+2], alpha);
			diffuse(clr[i+2], alpha);
			vertex(p[i+2]);
		    }
		}
	    }
	}
	else{
	    if(alpha<0){
		for(int i=0; i<p.length-2; i+=3){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) && isInFront(p[i+2])){
			    clr(clr[i]);
			    vertex(p[i]);
			    clr(clr[i+1]);
			    vertex(p[i+1]);
			    clr(clr[i+2]);
			    vertex(p[i+2]);
			}
		    }
		    else{
			clr(clr[i]);
			vertex(p[i]);
			clr(clr[i+1]);
			vertex(p[i+1]);
			clr(clr[i+2]);
			vertex(p[i+2]);
		    }
		}
	    }
	    else{
		for(int i=0; i<p.length-2; i+=3){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) && isInFront(p[i+2])){
			    clr(clr[i], alpha);
			    vertex(p[i]);
			    clr(clr[i+1], alpha);
			    vertex(p[i+1]);
			    clr(clr[i+2], alpha);
			    vertex(p[i+2]);
			}
		    }
		    else{
			clr(clr[i], alpha);
			vertex(p[i]);
			clr(clr[i+1], alpha);
			vertex(p[i+1]);
			clr(clr[i+2], alpha);
			vertex(p[i+2]);
		    }
		}
	    }
	}
	super.endShape();
    }
    public void drawTriangles(IVec[] p, IVec[] n, IColor[] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	super.beginShape(TRIANGLES);
	if(light){
	    if(alpha<0){
		for(int i=0; i<p.length-2; i+=3){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) && isInFront(p[i+2])){
			    ambient(clr[i]); diffuse(clr[i]); normal(n[i]); vertex(p[i]);
			    ambient(clr[i+1]); diffuse(clr[i+1]); normal(n[i+1]); vertex(p[i+1]);
			    ambient(clr[i+2]); diffuse(clr[i+2]); normal(n[i+2]); vertex(p[i+2]);
			}
		    }
		    else{
			ambient(clr[i]); diffuse(clr[i]); normal(n[i]); vertex(p[i]);
			ambient(clr[i+1]); diffuse(clr[i+1]); normal(n[i+1]); vertex(p[i+1]);
			ambient(clr[i+2]); diffuse(clr[i+2]); normal(n[i+2]); vertex(p[i+2]);
		    }
		}
	    }
	    else{
		for(int i=0; i<p.length-2; i+=3){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) && isInFront(p[i+2])){
			    ambient(clr[i],alpha); diffuse(clr[i],alpha); normal(n[i]); vertex(p[i]);
			    ambient(clr[i+1],alpha); diffuse(clr[i+1],alpha); normal(n[i+1]); vertex(p[i+1]);
			    ambient(clr[i+2],alpha); diffuse(clr[i+2],alpha); normal(n[i+2]); vertex(p[i+2]);
			}
		    }
		    else{
			ambient(clr[i],alpha); diffuse(clr[i],alpha); normal(n[i]); vertex(p[i]);
			ambient(clr[i+1],alpha); diffuse(clr[i+1],alpha); normal(n[i+1]); vertex(p[i+1]);
			ambient(clr[i+2],alpha); diffuse(clr[i+2],alpha); normal(n[i+2]); vertex(p[i+2]);
		    }
		}
	    }
	}
	else{
	    if(alpha<0){
		for(int i=0; i<p.length-2; i+=3){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) && isInFront(p[i+2])){
			    clr(clr[i]); normal(n[i]); vertex(p[i]);
			    clr(clr[i+1]); normal(n[i+1]); vertex(p[i+1]);
			    clr(clr[i+2]); normal(n[i+2]); vertex(p[i+2]);
			}
		    }
		    else{
			clr(clr[i]); normal(n[i]); vertex(p[i]);
			clr(clr[i+1]); normal(n[i+1]); vertex(p[i+1]);
			clr(clr[i+2]); normal(n[i+2]); vertex(p[i+2]);
		    }
		}
	    }
	    else{
		for(int i=0; i<p.length-2; i+=3){
		    if(IConfig.cullVertexBehindViewInP3D){
			if(isInFront(p[i]) && isInFront(p[i+1]) && isInFront(p[i+2])){
			    clr(clr[i],alpha); normal(n[i]); vertex(p[i]);
			    clr(clr[i+1],alpha); normal(n[i+1]); vertex(p[i+1]);
			    clr(clr[i+2],alpha); normal(n[i+2]); vertex(p[i+2]);
			}
		    }
		    else{
			clr(clr[i],alpha); normal(n[i]); vertex(p[i]);
			clr(clr[i+1],alpha); normal(n[i+1]); vertex(p[i+1]);
			clr(clr[i+2],alpha); normal(n[i+2]); vertex(p[i+2]);
		    }
		}
	    }
	}
	//vertex(p,n);
	super.endShape();
    }
    public void drawTriangleStrip(IVec[] p){
	if(p.length<3) return;
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    boolean isDrawing = false;
	    //boolean previous1Front = isInFront(p[1]);
	    //boolean previous2Front = isInFront(p[0]);
	    //for(int i=2; i<p.length; i++){
	    boolean previous1Front = false;
	    boolean previous2Front = false;
	    for(int i=0; i<p.length; i++){
		boolean isFront = isInFront(p[i]);
		if(isFront){
		    if(isDrawing){
			vertex(p[i]);
		    }
		    else{
			super.beginShape(TRIANGLE_STRIP);
			vertex(p[i]);
			isDrawing=true;
		    }
		    
		    /*
		    if(!isDrawing){
			super.beginShape(TRIANGLE_STRIP);
			vertex(p[i-2]);
			vertex(p[i-1]);
			isDrawing=true;
		    }
		    vertex(p[i]);
		    */
		}
		else{
		    if(isDrawing){
			super.endShape();
			isDrawing=false;
		    }
		    /*
		    if(previous1Front || previous2Front){ vertex(p[i]); }
		    else if(isDrawing){
			super.endShape();
			isDrawing=false;
		    }
		    */
		}
		previous2Front = previous1Front;
		previous1Front = isFront;
	    }
	    if(isDrawing){ super.endShape(); }
	}
	else{
	    super.beginShape(TRIANGLE_STRIP);
	    vertex(p);
	    super.endShape();
	}
    }
    public void drawTriangleStrip(IVec[] p, IVec[] n){
	if(p.length<3) return;
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    boolean isDrawing = false;
	    //boolean previous1Front = isInFront(p[1]);
	    //boolean previous2Front = isInFront(p[0]);
	    //for(int i=2; i<p.length; i++){
	    boolean previous1Front = false; 
	    boolean previous2Front = false;
	    for(int i=0; i<p.length; i++){
		boolean isFront = isInFront(p[i]);
		if(isFront){
		    if(isDrawing){
			normal(n[i]);
			vertex(p[i]);
		    }
		    else{
			super.beginShape(TRIANGLE_STRIP);
			normal(n[i]);
			vertex(p[i]);
			/*
			super.beginShape(TRIANGLE_STRIP);
			normal(n[i-2]);
			vertex(p[i-2]);
			normal(n[i-1]);
			vertex(p[i-1]);
			isDrawing=true;
			*/
		    }
		}
		else{
		    if(isDrawing){
			super.endShape();
			isDrawing=false;
		    }
		    /*
		    if(previous1Front || previous2Front){
			normal(n[i]);
			vertex(p[i]);
		    }
		    else if(isDrawing){
			super.endShape();
			isDrawing=false;
		    }
		    */
		}
		previous2Front = previous1Front;
		previous1Front = isFront;
	    }
	    if(isDrawing){ super.endShape(); }
	}
	else{
	    super.beginShape(TRIANGLE_STRIP);
	    vertex(p,n);
	    super.endShape();
	}
    }
    public void drawTriangleStrip(IVec[] p, IColor[] clr, float alpha, boolean light){
	if(p.length<3) return;
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    boolean isDrawing = false;
	    boolean previous1Front = false;
	    boolean previous2Front = false;
	    if(light){
		if(alpha<0){
		    for(int i=0; i<p.length; i++){
			boolean isFront = isInFront(p[i]);
			if(isFront){
			    if(isDrawing){
				ambient(clr[i]);
				diffuse(clr[i]);
				vertex(p[i]);
			    }
			    else{
				super.beginShape(TRIANGLE_STRIP);
				ambient(clr[i]);
				diffuse(clr[i]);
				vertex(p[i]);
				isDrawing=true;
			}
			}
			else{
			    if(isDrawing){
				super.endShape();
				isDrawing=false;
			    }
			}
			previous2Front = previous1Front;
			previous1Front = isFront;
		    }
		}
		else{
		    for(int i=0; i<p.length; i++){
			boolean isFront = isInFront(p[i]);
			if(isFront){
			    if(isDrawing){
				ambient(clr[i],alpha);
				diffuse(clr[i],alpha);
				vertex(p[i]);
			    }
			    else{
				super.beginShape(TRIANGLE_STRIP);
				ambient(clr[i], alpha);
				diffuse(clr[i], alpha);
				vertex(p[i]);
				isDrawing=true;
			    }
			}
			else{
			    if(isDrawing){
				super.endShape();
				isDrawing=false;
			    }
			}
			previous2Front = previous1Front;
			previous1Front = isFront;
		    }
		}
	    }
	    else{
		if(alpha<0){
		    for(int i=0; i<p.length; i++){
			boolean isFront = isInFront(p[i]);
			if(isFront){
			    if(isDrawing){
				clr(clr[i]);
				vertex(p[i]);
			    }
			    else{
				super.beginShape(TRIANGLE_STRIP);
				clr(clr[i]);
				vertex(p[i]);
				isDrawing=true;
			    }
			}
			else{
			    if(isDrawing){
				super.endShape();
				isDrawing=false;
			    }
			}
			previous2Front = previous1Front;
			previous1Front = isFront;
		    }
		}
		else{
		    for(int i=0; i<p.length; i++){
			boolean isFront = isInFront(p[i]);
			if(isFront){
			    if(isDrawing){
				clr(clr[i],alpha);
				vertex(p[i]);
			    }
			    else{
				super.beginShape(TRIANGLE_STRIP);
				clr(clr[i], alpha);
				vertex(p[i]);
				isDrawing=true;
			    }
			}
			else{
			    if(isDrawing){
				super.endShape();
				isDrawing=false;
			    }
			}
			previous2Front = previous1Front;
			previous1Front = isFront;
		    }
		}
	    }
	    if(isDrawing){ super.endShape(); }
	}
	else{
	    super.beginShape(TRIANGLE_STRIP);
	    if(light){
		if(alpha<0){
		    ambient(clr[0]); // ?
		    diffuse(clr[0]); // ?
		}
		else{
		    ambient(clr[0], alpha); // ?
		    diffuse(clr[0], alpha); // ?
		}
	    }
	    else{
		if(alpha<0){
		    clr(clr[0]); // ?
		}
		else{
		    clr(clr[0], alpha); // ?
		}
		
	    }
	    vertex(p);
	    super.endShape();
	}
    }
    
    public void drawTriangleStrip(IVec[] p, IVec[] n, IColor[] clr, float alpha, boolean light){
	if(p.length<3) return;
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    boolean isDrawing = false;
	    boolean previous1Front = false; 
	    boolean previous2Front = false;
	    if(light){
		if(alpha<0){
		    for(int i=0; i<p.length; i++){
			boolean isFront = isInFront(p[i]);
			if(isFront){
			    if(isDrawing){
				ambient(clr[i]);
				diffuse(clr[i]);
				normal(n[i]);
				vertex(p[i]);
			    }
			    else{
				super.beginShape(TRIANGLE_STRIP);
				ambient(clr[i]);
				diffuse(clr[i]);
				normal(n[i]);
				vertex(p[i]);
			    }
			}
			else{
			    if(isDrawing){
				super.endShape();
				isDrawing=false;
			    }
			}
			previous2Front = previous1Front;
			previous1Front = isFront;
		    }
		}
		else{
		    for(int i=0; i<p.length; i++){
			boolean isFront = isInFront(p[i]);
			if(isFront){
			    if(isDrawing){
				ambient(clr[i],alpha);
				diffuse(clr[i],alpha);
				normal(n[i]);
				vertex(p[i]);
			    }
			    else{
				super.beginShape(TRIANGLE_STRIP);
				ambient(clr[i],alpha);
				diffuse(clr[i],alpha);
				normal(n[i]);
				vertex(p[i]);
			    }
			}
			else{
			    if(isDrawing){
				super.endShape();
				isDrawing=false;
			    }
			}
			previous2Front = previous1Front;
			previous1Front = isFront;
		    }
		}
	    }
	    else{
		if(alpha<0){
		    for(int i=0; i<p.length; i++){
			boolean isFront = isInFront(p[i]);
			if(isFront){
			    if(isDrawing){
				clr(clr[i]);
				normal(n[i]);
				vertex(p[i]);
			    }
			    else{
				super.beginShape(TRIANGLE_STRIP);
				clr(clr[i]);
				normal(n[i]);
				vertex(p[i]);
			    }
			}
			else{
			    if(isDrawing){
				super.endShape();
				isDrawing=false;
			    }
			}
			previous2Front = previous1Front;
			previous1Front = isFront;
		    }
		}
		else{
		    for(int i=0; i<p.length; i++){
			boolean isFront = isInFront(p[i]);
			if(isFront){
			    if(isDrawing){
				clr(clr[i],alpha);
				normal(n[i]);
				vertex(p[i]);
			    }
			    else{
				super.beginShape(TRIANGLE_STRIP);
				clr(clr[i],alpha);
				normal(n[i]);
				vertex(p[i]);
			    }
			}
			else{
			    if(isDrawing){
				super.endShape();
				isDrawing=false;
			    }
			}
			previous2Front = previous1Front;
			previous1Front = isFront;
		    }
		}
	    }
	    if(isDrawing){ super.endShape(); }
	}
	else{
	    super.beginShape(TRIANGLE_STRIP);
	    if(light){
		if(alpha<0){
		    ambient(clr[0]); // ?
		    diffuse(clr[0]); // ?
		}
		else{
		    ambient(clr[0],alpha); // ?
		    diffuse(clr[0],alpha); // ?
		}
	    }
	    else{
		if(alpha<0){
		    clr(clr[0]); // ?
		}
		else{
		    clr(clr[0],alpha); // ?
		}
	    }
	    vertex(p,n);
	    super.endShape();
	}
    }
    public void drawTriangleFan(IVec[] p){
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    super.beginShape(TRIANGLE_FAN);
	    vertex(p[0]);
	    boolean firstFront=isInFront(p[0]);
	    boolean prevFront= firstFront;
	    for(int i=1; i<p.length; i++){
		boolean currentFront = isInFront(p[i]);
		if(firstFront||prevFront||currentFront){ vertex(p[i]); }
		prevFront=currentFront;
	    }
	    super.endShape();
	}
	else{
	    super.beginShape(TRIANGLE_FAN);
	    vertex(p);
	    super.endShape();
	}
    }
    public void drawTriangleFan(IVec[] p, IVec[] n){
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    super.beginShape(TRIANGLE_FAN);
	    normal(n[0]);
	    vertex(p[0]);
	    boolean firstFront=isInFront(p[0]);
	    boolean prevFront= firstFront;
	    for(int i=1; i<p.length; i++){
		boolean currentFront = isInFront(p[i]);
		if(firstFront||prevFront||currentFront){
		    normal(n[i]); vertex(p[i]);
		}
		prevFront=currentFront;
	    }
	    super.endShape();
	}
	else{
	    super.beginShape(TRIANGLE_FAN);
	    vertex(p,n);
	    super.endShape();
	}
    }
    
    public void drawTriangleFan(IVec[] p, IColor[] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    super.beginShape(TRIANGLE_FAN);
	    if(light){
		if(alpha<0){
		    ambient(clr[0]);
		    diffuse(clr[0]);
		    vertex(p[0]);
		    boolean firstFront=isInFront(p[0]);
		    boolean prevFront= firstFront;
		    for(int i=1; i<p.length; i++){
			boolean currentFront = isInFront(p[i]);
			if(firstFront||prevFront||currentFront){
			    ambient(clr[i]); diffuse(clr[i]); vertex(p[i]);
			}
			prevFront=currentFront;
		    }
		}
		else{
		    ambient(clr[0],alpha);
		    diffuse(clr[0],alpha);
		    vertex(p[0]);
		    boolean firstFront=isInFront(p[0]);
		    boolean prevFront= firstFront;
		    for(int i=1; i<p.length; i++){
			boolean currentFront = isInFront(p[i]);
			if(firstFront||prevFront||currentFront){
			    ambient(clr[i],alpha); diffuse(clr[i],alpha); vertex(p[i]);
			}
			prevFront=currentFront;
		    }
		}
	    }
	    else{
		if(alpha<0){
		    clr(clr[0]);
		    vertex(p[0]);
		    boolean firstFront=isInFront(p[0]);
		    boolean prevFront= firstFront;
		    for(int i=1; i<p.length; i++){
			boolean currentFront = isInFront(p[i]);
			if(firstFront||prevFront||currentFront){ clr(clr[i]); vertex(p[i]); }
			prevFront=currentFront;
		    }
		}
		else{
		    clr(clr[0],alpha);
		    vertex(p[0]);
		    boolean firstFront=isInFront(p[0]);
		    boolean prevFront= firstFront;
		    for(int i=1; i<p.length; i++){
			boolean currentFront = isInFront(p[i]);
			if(firstFront||prevFront||currentFront){ clr(clr[i],alpha); vertex(p[i]); }
			prevFront=currentFront;
		    }
		}
	    }
	    super.endShape();
	}
	else{
	    super.beginShape(TRIANGLE_FAN);
	    if(alpha<0){
		clr(clr[0]); // ?
	    }
	    else{
		clr(clr[0],alpha); // ?
	    }
	    vertex(p);
	    super.endShape();
	}
    }
    public void drawTriangleFan(IVec[] p, IVec[] n, IColor[] clr, float alpha, boolean light){
	super.stroke= false;
	super.fill = true;
	if(IConfig.cullVertexBehindViewInP3D){
	    super.beginShape(TRIANGLE_FAN);
	    if(light){
		if(alpha<0){
		    ambient(clr[0]);
		    diffuse(clr[0]);
		    normal(n[0]);
		    vertex(p[0]);
		    boolean firstFront=isInFront(p[0]);
		    boolean prevFront= firstFront;
		    for(int i=1; i<p.length; i++){
			boolean currentFront = isInFront(p[i]);
			if(firstFront||prevFront||currentFront){
			    ambient(clr[i]); diffuse(clr[i]); normal(n[i]); vertex(p[i]);
			}
		    prevFront=currentFront;
		    }
		}
		else{
		    ambient(clr[0],alpha);
		    diffuse(clr[0],alpha);
		    normal(n[0]);
		    vertex(p[0]);
		    boolean firstFront=isInFront(p[0]);
		    boolean prevFront= firstFront;
		    for(int i=1; i<p.length; i++){
			boolean currentFront = isInFront(p[i]);
			if(firstFront||prevFront||currentFront){
			    ambient(clr[i],alpha); diffuse(clr[i],alpha); normal(n[i]); vertex(p[i]);
			}
			prevFront=currentFront;
		    }
		}
	    }
	    else{
		if(alpha<0){
		    clr(clr[0]);
		    normal(n[0]);
		    vertex(p[0]);
		    boolean firstFront=isInFront(p[0]);
		    boolean prevFront= firstFront;
		    for(int i=1; i<p.length; i++){
			boolean currentFront = isInFront(p[i]);
			if(firstFront||prevFront||currentFront){
			    clr(clr[i]); normal(n[i]); vertex(p[i]);
			}
		    prevFront=currentFront;
		    }
		}
		else{
		    clr(clr[0],alpha);
		    normal(n[0]);
		    vertex(p[0]);
		    boolean firstFront=isInFront(p[0]);
		    boolean prevFront= firstFront;
		    for(int i=1; i<p.length; i++){
			boolean currentFront = isInFront(p[i]);
			if(firstFront||prevFront||currentFront){
			    clr(clr[i],alpha); normal(n[i]); vertex(p[i]);
			}
			prevFront=currentFront;
		    }
		}
	    }
	    super.endShape();
	}
	else{
	    super.beginShape(TRIANGLE_FAN);
	    if(light){
		if(alpha<0){
		    ambient(clr[0]); //?
		    diffuse(clr[0]); //?
		}
		else{
		    ambient(clr[0],alpha); //?
		    diffuse(clr[0],alpha); //?
		}
	    }
	    else{
		if(alpha<0){
		    clr(clr[0]); //?
		}
		else{
		    clr(clr[0],alpha); //?
		}
	    }
	    vertex(p,n);
	    super.endShape();
	}
    }
    
    
    /*****************************************************************************
     * implementation of IPane
     *****************************************************************************/
    
    public synchronized void setLocation(int x, int y){
	screenX=x; screenY=y;
	if(view!=null) view.setPane(this);
    }
    
    public synchronized void setSize(int w, int h){

	/*
	creatingBGImage = true;
	if(view!=null){
	    PImage bg = createBGImage(w,h,view);
	    bgImage = bg;
	} // create bg before setSize()
	*/

	//if(!primarySurface){
	resetTexture=true;
	updateBGImage(w,h,view); 
	//}
	
	super.setSize(w, h); // set size before setPane
	
	//super.texture = null; // to update OpenGL texture and pixels
	
	
	//IErr.printStack(); //
	
	if(view!=null){ view.setPane(this); }
	
	creatingBGImage = false;
	
	/*
	if(alias==null){ // main view
	    subGraphics[0][0].setBounds(screenX,screenY,w/2,h/2);
	    subGraphics[0][1].setBounds(screenX+w/2,screenY,w/2,h/2);
	    subGraphics[1][0].setBounds(screenX,screenY+h/2,w/2,h/2);
	    subGraphics[1][1].setBounds(screenX+w/2,screenY+h/2,w/2,h/2);
	}
	*/
    }
    /*
    public void loadTextureImpl(int t, boolean b){
	IG.err();
	super.loadTextureImpl(t,b);
    }
    
    public void drawTexture(int x, int y, int w, int h){
	IG.err("x="+x+", y="+y+",w="+w+",h="+h+" @"+this); //
	IG.err("width = "+width+", height = "+height);
	IG.err("texture.glWidth="+texture.glWidth+", texture.glHeight="+texture.glHeight);
	//IErr.printStack();
	super.drawTexture(x,y,w,h);
    }
    */

    /*
    public void drawPixels(int x, int y, int w, int h){
	IG.err("x="+x+", y="+y+",w="+w+",h="+h+" @"+this); //
	IG.err("texture.glWidth="+texture.glWidth+", texture.glHeight="+texture.glHeight);
	super.drawPixels(x,y,w,h);
    }
    */
    
    /*
    public void updatePixels(int x, int y, int w, int h){
	IG.err("x="+x+", y="+y+",w="+w+",h="+h+" @"+this); //
	super.updatePixels(x,y,w,h);
    }
    */

    
    /*
    public void setImpl(PImage i, int x, int y, int w, int h, int tx, int ty){
	IG.err("img.w="+i.width+", img.h="+i.height+", x="+x+", y="+y+", w="+w+", h="+h+", tx="+tx+", ty="+ty); //
	super.setImpl(i,x,y,w,h,tx,ty);
    }
    */

    /*
    public void initOffscreen(){
	IG.err(); //
	super.initOffscreen();
    }
    */
    
    
    public synchronized void setBounds(int x, int y, int w, int h){
	//IG.err("x="+x+", y="+y+",w="+w+",h="+h+" @"+this); //
	screenX=x; screenY=y;
	this.setSize(w,h);
    }
    
    public float getX(){ return screenX; }
    public float getY(){ return screenY; }
    public int getWidth(){ return super.width; }
    public int getHeight(){ return super.height; }
    
    public boolean isVisible(){ return visible; }
    public void setVisible(boolean b){ visible=b; }
    
    public boolean contains(int x, int y){
        if(x < screenX) return false;
        if(y < screenY) return false;
        if(x >= (screenX+super.width)) return false;
        if(y >= (screenY+super.height)) return false;
        return true;
    }
    
    public void setPanel(IPanelI p){ panel = p; }
    public IPanelI getPanel(){ return panel; }
    
    public void setBorderWidth(float b){ borderWidth=b; }
    public float getBorderWidth(){ return borderWidth; }
    public Stroke getBorderStroke(){ return null; }
    public void setBorderColor(int r, int b, int g, int a){ borderColor = new Color(r,g,b,a); }
    public void setBorderColor(Color c){ borderColor = c; }
    //public Color getBorderColor(){ return borderColor; }
    public int getBorderColor(){ return borderColor.getRGB(); }
    public INavigator navigator(){ return navigator; }
    public void setView(IView v){
	view=v;
	view.setPane(this);
	IGraphicMode m = new IGraphicMode(view.mode());
	m.setGraphicType(IGraphicMode.GraphicType.P3D); //
	view.setMode(m);
	if(navigator==null){ navigator = new INavigator(view, this); }
	else{ navigator.setView(view); }
    }
    
    public IView getView(){ return view; }
    
    public void draw(IGraphics g){
	
	if(view!=null){
	    ArrayList<IGraphicI> objects = panel.getIG().server().graphicServer().getObjects(view);
	    this.draw(objects, view); // not g.draw()
	    
	    // to be continued 20150101
	    
	    //((PIGraphics3D)g).image(this,screenX,screenY);
	    //((PIGraphics3D)g).set(screenX,screenY,this); // necessary?
	}
	else{ IOut.err("view is null"); } //
    }
    
    /** Focus view on objects */
    public void focus(){
        //if(parent.getBounds()==null) parent.setBounds();
        // parent is checking if bounding box is needed to be updated or not.
        panel.setBounds();
        view.focus(panel.getBounds());
    }

    /** Focus view on objects */
    public void focus(ArrayList<IObject> e){
        IBounds bb = new IBounds();
        bb.setObjects(e);
        view.focus(bb);
    }
    
    
    public void mousePressed(MouseEvent e){ mousePressed(new IMouseEvent(e)); }
    public void mouseReleased(MouseEvent e){ mouseReleased(new IMouseEvent(e)); }
    public void mouseClicked(MouseEvent e){ mouseClicked(new IMouseEvent(e)); }
    public void mouseEntered(MouseEvent e){ mouseEntered(new IMouseEvent(e)); }
    public void mouseExited(MouseEvent e){ mouseExited(new IMouseEvent(e)); }
    public void mouseMoved(MouseEvent e){ mouseMoved(new IMouseEvent(e)); }
    public void mouseDragged(MouseEvent e){ mouseDragged(new IMouseEvent(e)); }
    public void mouseWheelMoved(MouseWheelEvent e){ mouseWheelMoved(new IMouseWheelEvent(e)); }
    public void keyPressed(KeyEvent e){ keyPressed(new IKeyEvent(e)); }
    public void keyReleased(KeyEvent e){ keyReleased(new IKeyEvent(e)); }
    public void keyTyped(KeyEvent e){ keyTyped(new IKeyEvent(e)); }
    
    
    public void mousePressed(IMouseEvent e){
        navigator.mousePressed(e);
    }
    public void mouseReleased(IMouseEvent e){
        navigator.mouseReleased(e);
    }
    public void mouseClicked(IMouseEvent e){
        navigator.mouseClicked(e);
    }
    public void mouseEntered(IMouseEvent e){
        navigator.mouseEntered(e);
    }
    public void mouseExited(IMouseEvent e){
        navigator.mouseExited(e);
    }
    public void mouseMoved(IMouseEvent e){
        navigator.mouseMoved(e);
    }
    public void mouseDragged(IMouseEvent e){
        navigator.mouseDragged(e);
    }
    
    
    public void mouseWheelMoved(IMouseWheelEvent e){
        navigator.mouseWheelMoved(e);
    }

    public void keyPressed(IKeyEvent e){ navigator.keyPressed(e); }
    public void keyReleased(IKeyEvent e){ navigator.keyReleased(e); }
    public void keyTyped(IKeyEvent e){ navigator.keyTyped(e); }
    
    public void focusLost(FocusEvent e){}
    public void focusGained(FocusEvent e){}
    
    public void close(){}
    
    /*
    public void componentHidden(ComponentEvent e){
    }
    public void componentMoved(ComponentEvent e){
    }
    public void componentResized(ComponentEvent e){
    }
    public void componentShown(ComponentEvent e){
    }
    */
    
    
    
    ////////////////////////////////////////////////////////////////////////
    // below, not used any more
    ////////////////////////////////////////////////////////////////////////
    /*
    public static class SubApplet extends PApplet implements IPane{
	public IPanelI panel;
	public float borderWidth;
	public Color borderColor;
	public INavigator navigator;
	public IView view;
	public SubApplet(){ super(); }
	public void init(){ IG.p(); super.init(); IG.p("end"); }
	public void setup(){ size(getWidth(),getHeight(),"igeo.p.PISubGraphicsP3D"); }
	public void setLocation(int x, int y){
	    super.setLocation(x,y); if(view!=null) view.setPane(this);
	}
	public void setSize(int width, int height){
	    super.setSize(width,height); if(view!=null) view.setPane(this);
	}
	public void setBounds(int x, int y, int w, int h){
	    super.setBounds(x,y,width,height); if(view!=null) view.setPane(this);
	}
	public int getX(){ return super.getX(); }
	public int getY(){ return super.getY(); }
	public int getWidth(){ return super.getWidth(); }
	public int getHeight(){ return super.getHeight(); }
	public boolean isVisible(){ return false; }
	public void setVisible(boolean b){}
	public boolean contains(int x, int y){ return false; }
	public void setPanel(IPanelI p){ panel = p; }
	public IPanelI getPanel(){ return panel; }
	public void setBorderWidth(float b){ borderWidth=b; }
	public float getBorderWidth(){ return borderWidth; }
	public Stroke getBorderStroke(){ return null; }
	public void setBorderColor(Color c){ borderColor = c; }
	public Color getBorderColor(){ return borderColor; }
	public INavigator navigator(){ return navigator; }
	public void setView(IView v){ view=v; }
	public IView getView(){ return view; }
	public void draw(IGraphics g){}
	public void focus(){}
	public void focus(ArrayList<IObject> e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
	public void mouseWheelMoved(MouseWheelEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void focusLost(FocusEvent e){}
	public void focusGained(FocusEvent e){}
    }
    */
    
    /*
    public static class SubGraphics extends PIGraphicsP3D implements IPane{
	PIGraphicsP3D parentGraphics;
	public SubGraphics(){ super(); }
	//public SubGraphics(PIGraphicsP3D parent){ super(); parentGraphics = parent; }
	public void setParent(PApplet parent){
	    super.setParentPGraphics3D(parent);
	    // initialize root GUI
	    // panel = new IGridPanel(0,0,parent.getWidth(),parent.getHeight(),2,2);
	    // panel.setVisible(true); //?
	    // initialize iGeo 
	    // IG ig = IG.init(panel);
	    // ig.server().graphicServer().enableGL(); //
	    // //ig.setBasePath(parent.sketchPath("")); // not sketchPath
	    // ig.setBasePath(parent.dataPath("")); // for default path to read/write files
	    // parent.addMouseListener(panel);
	    // parent.addMouseMotionListener(panel);
	    // parent.addMouseWheelListener(panel);
	    // parent.addKeyListener(panel);
	    // parent.addFocusListener(panel);
	    // parent.addComponentListener(panel);
	    // //noSmooth();
	    if(PIConfig.drawBeforeProcessing) parent.registerPre(this);
	    else parent.registerDraw(this);
	    parent.registerPost(this);
	}
	public void pre(){ drawIG(); }
	public void draw(){ drawIG(); }
	public void post(){ super.post(); }
	public void loop(){ super.loop(); }
	public void noLoop(){ super.noLoop(); }
	public void start(){ super.start(); }
	public void stop(){ super.stop(); }
	public void drawIG(){}
    }
    */
    
}

