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

import java.util.ArrayList;
//import java.awt.Color;

//import javax.media.opengl.*;

import igeo.*;

/**
   Graphic subobject class to draw filled faces of a surface object by OpenGL.
   
   @author Satoru Sugihara
*/
public class ISurfaceGraphicFillGL extends IGraphicObject{
    
    public static final boolean insertPointOnDegree1TwistedSurface=true;
    
    public int isoparmRatioU=IConfig.tessellationResolution;
    public int isoparmRatioV=IConfig.tessellationResolution;
    
    //public final static int fragmentResolution = 10;
    
    public ISurfaceI surface=null; // parent
    
    //public IGLQuadMatrix quadMatrix=null;
    //public IGLTriangles triangles=null;
    
    public IVec[][] quads;
    public IVec[][] quadsNormal;
    public IVec[][] triangles;
    public IVec[][] trianglesNormal;
    
    
    // cache to update surface 
    public double[] uvalCache, vvalCache;
    public IVec2[][] triangles2DCache;
    
    public boolean initialized=false;

    int origUEPNum, origVEPNum; // added 20121111
    
    public ISurfaceGraphicFillGL(ISurface srf){
	super(srf);
	surface = srf.surface;
	//initSurface();
    }
    
    public ISurfaceGraphicFillGL(ISurfaceR srf){
	super(srf);
	surface = srf.surface;
	//initSurface();
    }
    
    public ISurfaceGraphicFillGL(IObject obj, ISurfaceI srf){
	super(obj);
	surface = srf;
	//initSurface();
    }
    
    public ISurfaceGraphicFillGL(ISurface srf, int isoparmRatioU, int isoparmRatioV ){
	super(srf);
	this.isoparmRatioU = isoparmRatioU;
	this.isoparmRatioV = isoparmRatioV;
	//initSurface();
    }
    
    public ISurfaceGraphicFillGL(ISurfaceR srf, int isoparmRatioU, int isoparmRatioV ){
	super(srf);
	this.isoparmRatioU = isoparmRatioU;
	this.isoparmRatioV = isoparmRatioV;
	//initSurface();
    }
    
    public void initSurface(){
	//if(parent instanceof ISurface){ surface = ((ISurface)parent).surface; }
	//else if(parent instanceof ISurfaceR){ surface = ((ISurfaceR)parent).surface; }
	
	origUEPNum = surface.uepNum();
	origVEPNum = surface.vepNum();
	
	if(!surface.hasTrim()||!surface.hasInnerTrim()&&surface.hasDefaultTrim())
	    initWithoutTrim(); // initialize IGLQuadMatrix
	else initWithTrim(); // initialize IGLTriangles
	
	initialized=true;
    }
    
    synchronized public void initWithoutTrim(){
	synchronized(parent){
	
	double[] uval;
	double[] vval;
	if(surface.udeg()==1){
	    int num = surface.unum();
	    uval = new double[num];
	    for(int i=0; i<num; i++) uval[i] = surface.u(i,0);
	}
	else{
	    int epnum = surface.uepNum();
	    int num = (epnum-1)*isoparmRatioU+1;
	    uval = new double[num];
	    for(int i=0; i<epnum; i++)
		for(int j=0; j<isoparmRatioU; j++)
		    if(i<epnum-1||j==0)
			uval[i*isoparmRatioU + j] = surface.u(i, (double)j/isoparmRatioU);
	}
	
	if(surface.vdeg()==1){
	    int num = surface.vnum();
	    vval = new double[num];
	    for(int i=0; i<num; i++) vval[i] = surface.v(i,0);
	}
	else{
	    int epnum = surface.vepNum();
	    int num = (epnum-1)*isoparmRatioV+1;
	    vval = new double[num];
	    for(int i=0; i<epnum; i++)
		for(int j=0; j<isoparmRatioV; j++)
		    if(i<epnum-1||j==0)
			vval[i*isoparmRatioV + j] = surface.v(i, (double)j/isoparmRatioV);
	}
	
	// insert points for deg 1 twisted surface
	if(insertPointOnDegree1TwistedSurface&&
	   surface.udeg()==1 && surface.vdeg()==1){
	    
	    boolean uinsert[] = new boolean[uval.length-1];
	    boolean vinsert[] = new boolean[vval.length-1];
	    boolean anyInsert=false;
	    for(int i=0; i<uval.length-1; i++) uinsert[i] = false;
	    for(int i=0; i<vval.length-1; i++) vinsert[i] = false;
	    
	    for(int i=0; i<uval.length-1; i++){
		for(int j=0; j<vval.length-1; j++){
		    if(!IVec.isFlat(surface.pt(uval[i],vval[j]),
				    surface.pt(uval[i+1],vval[j]),
				    surface.pt(uval[i+1],vval[j+1]),
				    surface.pt(uval[i],vval[j+1]))){
			uinsert[i] = true;
			vinsert[j] = true;
			anyInsert = true;
		    }
		}
	    }
	    
	    if(anyInsert){
		ArrayList<Double> uval2 = new ArrayList<Double>();
		for(int i=0; i<uval.length-1; i++){
		    uval2.add(uval[i]);
		    if(uinsert[i]){
			for(int j=1; j<isoparmRatioU; j++){
			    uval2.add(((uval[i+1]-uval[i])*j)/isoparmRatioU+uval[i]);
			}
		    }
		}
		uval2.add(uval[uval.length-1]);
		
		ArrayList<Double> vval2 = new ArrayList<Double>();
		for(int i=0; i<vval.length-1; i++){
		    vval2.add(vval[i]);
		    if(vinsert[i]){
			for(int j=1; j<isoparmRatioV; j++){
			    vval2.add(((vval[i+1]-vval[i])*j)/isoparmRatioV+vval[i]);
			}
		    }
		}
		vval2.add(vval[vval.length-1]);
		
		uval = new double[uval2.size()];
		for(int i=0; i<uval2.size(); i++) uval[i] = uval2.get(i);
		
		vval = new double[vval2.size()];
		for(int i=0; i<vval2.size(); i++) vval[i] = vval2.get(i);
	    }
	}
	
	IVec[][] pts = new IVec[uval.length][vval.length];
	IVec[][] nrm = new IVec[uval.length][vval.length];
	for(int i=0; i<uval.length; i++){
	    for(int j=0; j<vval.length; j++){
		pts[i][j] = surface.pt(uval[i], vval[j]).get();
		nrm[i][j] = surface.normal(uval[i], vval[j]).get().unit();
	    }
	}
	
	//quadMatrix = new IGLQuadMatrix(pts,nrm);
	quads = pts;
	quadsNormal = nrm;
	
	uvalCache=uval;
	vvalCache=vval;

	}
    }
    
    synchronized public void initWithTrim(){
	synchronized(parent){
	    
	ITrimLoopGraphic[] outtrims = null;
        ITrimLoopGraphic[] intrims = null;
        
        if(surface.hasOuterTrim()){
            outtrims = new ITrimLoopGraphic[surface.outerTrimLoopNum()];
            for(int i=0; i<surface.outerTrimLoopNum(); i++)
                outtrims[i] = new ITrimLoopGraphic(surface.outerTrimLoop(i),
						   true,IConfig.trimSegmentResolution);
        }
        else{
	    // default outer trim loop?
            //outtrims = new ITrimLoopGraphic[1];
            //outtrims[0] = new ITrimLoopGraphic(surface);
        }
        
        if(surface.hasInnerTrim()){
            intrims = new ITrimLoopGraphic[surface.innerTrimLoopNum()];
            for(int i=0; i<surface.innerTrimLoopNum(); i++)
                intrims[i] = new ITrimLoopGraphic(surface.innerTrimLoop(i),
						  false,IConfig.trimSegmentResolution);
        }
	
        int unum = isoparmRatioU*(surface.uepNum()-1)+1;
        int vnum = isoparmRatioV*(surface.vepNum()-1)+1;
	
	double[] uval = null;
	double[] vval = null;
	
	if(surface.udeg()==1){
	    unum = surface.unum();
	    uval = new double[unum];
	    for(int i=0; i<unum; i++) uval[i] = (double)i/(unum-1);
	}
	else{
	    uval = new double[unum];
	    for(int i=0; i<surface.uepNum(); i++)
		for(int j=0; j<isoparmRatioU&&i<surface.uepNum()-1||j==0; j++)
		    uval[i*isoparmRatioU+j] =
			surface.u(i,(double)j/isoparmRatioU);
	}
	
	if(surface.vdeg()==1){
	    vnum = surface.vnum();
	    vval = new double[vnum];
	    for(int i=0; i<vnum; i++) vval[i] = (double)i/(vnum-1);
	}
	else{
	    vval = new double[vnum];
	    for(int k=0; k<surface.vepNum(); k++)
		for(int l=0; l<isoparmRatioV&&k<surface.vepNum()-1||l==0; l++){
		    vval[k*isoparmRatioV+l] =
			surface.v(k,(double)l/isoparmRatioV);
		    
		    //IOut.debug(20, "vval["+k+"*"+isoparmRatioV+"+"+l+"] = "+vval[k*isoparmRatioV+l]); //
		}
	}
	
	// insert points for deg 1 twisted surface
	if(insertPointOnDegree1TwistedSurface&&
	   surface.udeg()==1 && surface.vdeg()==1){
	    
	    boolean uinsert[] = new boolean[uval.length-1];
	    boolean vinsert[] = new boolean[vval.length-1];
	    boolean anyInsert=false;
	    for(int i=0; i<uval.length-1; i++) uinsert[i] = false;
	    for(int i=0; i<vval.length-1; i++) vinsert[i] = false;
	    
	    for(int i=0; i<uval.length-1; i++){
		for(int j=0; j<vval.length-1; j++){
		    if(!IVec.isFlat(surface.pt(uval[i],vval[j]),
				    surface.pt(uval[i+1],vval[j]),
				    surface.pt(uval[i+1],vval[j+1]),
				    surface.pt(uval[i],vval[j+1]))){
			uinsert[i] = true;
			vinsert[j] = true;
			anyInsert = true;
		    }
		}
	    }
	    
	    if(anyInsert){
		ArrayList<Double> uval2 = new ArrayList<Double>();
		for(int i=0; i<uval.length-1; i++){
		    uval2.add(uval[i]);
		    if(uinsert[i]){
			for(int j=1; j<isoparmRatioU; j++){
			    uval2.add(((uval[i+1]-uval[i])*j)/isoparmRatioU+uval[i]);
			}
		    }
		}
		uval2.add(uval[uval.length-1]);
		
		ArrayList<Double> vval2 = new ArrayList<Double>();
		for(int i=0; i<vval.length-1; i++){
		    vval2.add(vval[i]);
		    if(vinsert[i]){
			for(int j=1; j<isoparmRatioV; j++){
			    vval2.add(((vval[i+1]-vval[i])*j)/isoparmRatioV+vval[i]);
			}
		    }
		}
		vval2.add(vval[vval.length-1]);
		
		uval = new double[uval2.size()];
		for(int i=0; i<uval2.size(); i++) uval[i] = uval2.get(i);
		unum = uval.length;
		
		vval = new double[vval2.size()];
		for(int i=0; i<vval2.size(); i++) vval[i] = vval2.get(i);
		vnum = vval.length;
	    }
	    
	}
	
	
	IVec2[][] surfPts = new IVec2[unum][vnum];
	
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum; j++){
		surfPts[i][j] = new IVec2(uval[i],vval[j]);
	    }
	}
	
	/*
	for(int i=0; i<surface.uepNum(); i++)
	    for(int j=0; j<isoparmRatioU && i<surface.uepNum()-1 || j==0; j++)
		for(int k=0; k<surface.vepNum(); k++)
		    for(int l=0; l<isoparmRatioV && k<surface.uepNum()-1 || l==0; l++)
			surfPts[i*isoparmRatioU+j][k*isoparmRatioV+l] =
			    new IVec2(surface.u(i,(double)j/isoparmRatioU),
				       surface.v(k,(double)l/isoparmRatioV));
	*/
	
	IVec2[][] outerPts = null;
	if(outtrims!=null){
	    outerPts = new IVec2[outtrims.length][];
	    for(int i=0; i<outtrims.length; i++)
		outerPts[i] = outtrims[i].getPolyline2D().get();
	}
	
	IVec2[][] innerPts = null;
	if(intrims!=null){
	    innerPts = new IVec2[intrims.length][];
	    for(int i=0; i<intrims.length; i++){
		innerPts[i] = intrims[i].getPolyline2D().get();
	    }
	}
	
	IVec2[][] triangles2D = ISurfaceMesh.getTriangles(surfPts,outerPts,innerPts);
		
	IVec[][] triangles3D = new IVec[triangles2D.length][3];
	IVec[][] trianglesNml = new IVec[triangles2D.length][3];
	for(int i=0; i<triangles2D.length; i++){
	    for(int j=0; j<triangles2D[i].length; j++){
		triangles3D[i][j] = surface.pt(triangles2D[i][j]).get();
		trianglesNml[i][j] = surface.normal(triangles2D[i][j]).get().unit();
	    }
	}
	
	//triangles = new IGLTriangles(triangles3D,trianglesNormal);
	triangles = triangles3D;
	trianglesNormal = trianglesNml;
	
	triangles2DCache = triangles2D;

	}
    }
    
    synchronized public void updateWithoutTrim(){
	synchronized(parent){
	
	
	if(uvalCache==null || vvalCache==null){
	    IOut.err("cache is null. not updated.");
	    return;
	}
	
	if(quads==null || quads.length!=uvalCache.length || quads[0].length!=vvalCache.length){
	    quads = new IVec[uvalCache.length][vvalCache.length];
	    quadsNormal = new IVec[uvalCache.length][vvalCache.length];
	}
	for(int i=0; i<uvalCache.length; i++){
	    for(int j=0; j<vvalCache.length; j++){
		quads[i][j] = surface.pt(uvalCache[i], vvalCache[j]).get();
		quadsNormal[i][j] = surface.normal(uvalCache[i], vvalCache[j]).get().unit();
	    }
	}
	/*
	if(quadMatrix!=null &&
	   quadMatrix.width() == uvalCache.length &&
	   quadMatrix.height() == vvalCache.length){
	    for(int i=0; i<uvalCache.length; i++){
		for(int j=0; j<vvalCache.length; j++){
		    IVec pt = surface.pt(uvalCache[i], vvalCache[j]).get();
		    IVec n = surface.normal(uvalCache[i], vvalCache[j]).get().unit();
		    quadMatrix.setPoint(i,j,pt,n);
		}
	    }
	}
	else{
	    IVec[][] pts = new IVec[uvalCache.length][vvalCache.length];
	    IVec[][] nrm = new IVec[uvalCache.length][vvalCache.length];
	    for(int i=0; i<uvalCache.length; i++){
		for(int j=0; j<vvalCache.length; j++){
		    pts[i][j] = surface.pt(uvalCache[i], vvalCache[j]).get();
		    nrm[i][j] = surface.normal(uvalCache[i], vvalCache[j]).get().unit();
		}
	    }
	    quadMatrix = new IGLQuadMatrix(pts,nrm);
	}
	*/

	}
    }
    
    synchronized public void updateWithTrim(){
	synchronized(parent){
	    
	if(triangles2DCache==null){
	    IOut.err("cache is null. not updated.");
	    return;
	}
	
	if(triangles==null || triangles.length!=triangles2DCache.length){
	    triangles = new IVec[triangles2DCache.length][3];
	    trianglesNormal = new IVec[triangles2DCache.length][3];
	}
	for(int i=0; i<triangles2DCache.length; i++){
	    for(int j=0; j<triangles2DCache[i].length; j++){
		triangles[i][j] = surface.pt(triangles2DCache[i][j]).get();
		trianglesNormal[i][j] = surface.normal(triangles2DCache[i][j]).get().unit();
	    }
	}
	
	/*
	if(triangles==null || triangles.length!=triangles2DCache.length){
	    triangles = new IVec[triangles2DCache.length][3];
	    trianglesNormal = new IVec[triangles2DCache.length][3];
	    for(int i=0; i<triangles2DCache.length; i++){
		for(int j=0; j<triangles2DCache[i].length; j++){
		    triangles[i][j] = surface.pt(triangles2DCache[i][j]).get();
		    trianglesNormal[i][j] = surface.normal(triangles2DCache[i][j]).get().unit();
		}
	    }
	}
	*/
	
	
	/*
	if(triangles2DCache.length == triangles.triangleNum()){
	    for(int i=0; i<triangles2DCache.length; i++){
		for(int j=0; j<triangles2DCache[i].length; j++){
		    IVec pt = surface.pt(triangles2DCache[i][j]).get();
		    IVec n = surface.normal(triangles2DCache[i][j]).get().unit();
		    triangles.setPoint(i,j,pt,n);
		}
	    }
	}
	else{
	    IVec[][] triangles3D = new IVec[triangles2DCache.length][3];
	    IVec[][] trianglesNormal = new IVec[triangles2DCache.length][3];
	    for(int i=0; i<triangles2DCache.length; i++){
		for(int j=0; j<triangles2DCache[i].length; j++){
		    triangles3D[i][j] = surface.pt(triangles2DCache[i][j]).get();
		    trianglesNormal[i][j] = surface.normal(triangles2DCache[i][j]).get().unit();
		}
	    }
	    triangles = new IGLTriangles(triangles3D,trianglesNormal);
	}
	*/

	}
    }
    
    public void updateSurface(){

	if(origUEPNum!=surface.uepNum() || origVEPNum!=surface.vepNum()){ initSurface(); return; }
	
	if(quads!=null) updateWithoutTrim();
	if(triangles!=null) updateWithTrim();
	//if(quadMatrix!=null) updateWithoutTrim();
	//if(triangles!=null) updateWithTrim();
    }
    
    
    public boolean isDrawable(IGraphicMode m){
	//return m.isGL()&&m.isFill();
	return m.isGraphic3D()&&m.isFill();
    }
    
    synchronized public void draw(IGraphics g){

	
	
	//if(surface==null) initSurface(); // not initizlized at the constructor // shouldn't it?
	if(!initialized) initSurface();
	else if(update){ updateSurface(); update=false; }
	
	if(g.type() == IGraphicMode.GraphicType.GL||
	   g.type() == IGraphicMode.GraphicType.P3D){
	   
	    
	    IGraphics3D g3d = (IGraphics3D)g;
	    
	    //float red,green,blue,alpha;
	    float[] rgba=null;
	    if(color!=null){
		rgba = color.rgba();
		//red = color.getRed();
		//green = color.getGreen();
		//blue = color.getBlue();
		//alpha = color.getAlpha();
	    }
	    else{
		rgba = IConfig.objectColor.rgba();
		//red = IConfig.objectColor.getRed();
		//green = IConfig.objectColor.getGreen();
		//blue = IConfig.objectColor.getBlue();
		//alpha = IConfig.objectColor.getAlpha();
	    }
	    
	    if(g3d.view().mode().isTransparent()){
		rgba = new float[]{ rgba[0], rgba[1], rgba[2], IConfig.transparentModeAlpha/255f };
	    }
	    
	    if(g3d.view().mode().isLight()){
		g3d.ambient(rgba);
		g3d.diffuse(rgba);
		//g3d.specular(rgba);
		g3d.shininess(IConfig.shininess);
		g3d.clr(rgba[0]*255,rgba[1]*255,rgba[2]*255,0f);
	    }
	    //else{ g3d.clr(red,green,blue,alpha); }
	    
	    g3d.clr(rgba);
	    
	    if(quads!=null){
		if(quadsNormal==null){ g3d.drawQuadMatrix(quads); }
		else{ g3d.drawQuadMatrix(quads,quadsNormal); }
            }
	    if(triangles!=null){
		if(trianglesNormal==null){
		    for(int i=0; i<triangles.length; i++) g3d.drawTriangles(triangles[i]);
		}
		else{
		    for(int i=0; i<triangles.length; i++)
			g3d.drawTriangles(triangles[i], trianglesNormal[i]);
		}
	    }
	    
	    /*
	    if(quadMatrix!=null){
		IVec[][] p = new IVec[quadMatrix.width][quadMatrix.height];
		for(int i=0; i<quadMatrix.height; i++){
		    for(int j=0; j<quadMatrix.width; j++){
			p[j][i] = quadMatrix.pts[i*quadMatrix.width+j];
		    }
		}
		
		g3d.drawQuadMatrix(p);
		
		if(quadMatrix.normal==null){ g3d.drawQuadMatrix(p); }
		else{
		    IVec[][] n = new IVec[quadMatrix.width][quadMatrix.height];
		    for(int i=0; i<quadMatrix.height; i++){
			for(int j=0; j<quadMatrix.width; j++){
			    n[j][i] = quadMatrix.normal[i*quadMatrix.width+j];
			}
		    }
		    g3d.drawQuadMatrix(p,n);
		}
            }
	    if(triangles!=null){
		if(triangles.normal==null){ g3d.drawTriangles(triangles.pts); }
		else{ g3d.drawTriangles(triangles.pts, triangles.normal); }
	    }
	    */
	    
	    /*
	    GL gl = ((IGraphicsGL)g).getGL();
	    //GL gl = g.getGL();
	    
	    if(gl!=null){
		//gl.glLineWidth(0.01f);
		//gl.glLineWidth(1f);
		//gl.glLineStipple(0,(short)0xFFFF);
		
		float red,green,blue,alpha;
		//float red = defaultRed;
		//float green = defaultGreen;
		//float blue = defaultBlue;
		//float alpha = defaultAlpha;
		if(color!=null){
		    red = (float)color.getRed()/255;
		    green = (float)color.getGreen()/255;
		    blue = (float)color.getBlue()/255;
		    alpha = (float)color.getAlpha()/255;
		}
		else{
		    red = (float)IConfig.objectColor.getRed()/255;
		    green = (float)IConfig.objectColor.getGreen()/255;
		    blue = (float)IConfig.objectColor.getBlue()/255;
		    alpha = (float)IConfig.objectColor.getAlpha()/255;
		}
		
		if(g.view().mode().isTransparent()){ alpha = (float)IConfig.transparentModeAlpha/255f; }
		
		if(g.view().mode().isLight()){
		    float[] colorf = new float[]{ red, green, blue, alpha };
		    gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, colorf, 0);
		    gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, colorf, 0);
		    //gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, colorf, 0);
		    gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS,
				   ISurfaceGraphicGL.defaultShininess);
		    gl.glColor4f(red, green, blue, 0f); // ? without this, the color is tinted with the previous object's color
		}
		else{ gl.glColor4f(red, green, blue, alpha); }
		
		if(quadMatrix!=null) quadMatrix.draw(gl);
		if(triangles!=null) triangles.draw(gl);
	    }
	    */
	    
	}
    }
    
    
}
