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
//import javax.media.opengl.*;

import igeo.*;

/**
   Graphic subobject class to draw wireframes of a surface object by OpenGL.
   
   @author Satoru Sugihara
*/
public class ISurfaceGraphicWireframeGL extends IGraphicObject{
    public static float weight = IConfig.strokeWeight; //1f;
    
    public int isoparmNumRatio=IConfig.isoparmResolution;
    public int isoparmNumU;
    public int isoparmNumV;
    
    public ISurfaceI surface=null;
    
    //public IGLLineStrip[] uline, vline;
    //public IGLLineLoop[] inTrim=null, outTrim=null;
    
    public IVec[][] ulinePts, vlinePts;
    public IVec[][] inTrimPts, outTrimPts;
    
    public IPolyline2D[] uline2, vline2;
    public IPolyline2D[] inTrim2=null, outTrim2=null;
    
    /** true when unum==2 && vnum==2 and flat */
    public boolean simpleFlat=false; 
    
    public boolean initialized=false;
    
    
    public int uepnum, vepnum; // added 20121111
    
    
    public ISurfaceGraphicWireframeGL(ISurface srf){
	super(srf); surface = srf.surface;
    }
    public ISurfaceGraphicWireframeGL(ISurfaceR srf){
	super(srf); surface = srf.surface;
    }
    public ISurfaceGraphicWireframeGL(IObject obj, ISurfaceI srf){
	super(obj);
	surface = srf;
    }
    
    public ISurfaceGraphicWireframeGL(ISurface srf, int isoparmNumRatio){
	super(srf);
	surface = srf.surface;
	this.isoparmNumRatio = isoparmNumRatio;
    }
    public ISurfaceGraphicWireframeGL(ISurfaceR srf, int isoparmNumRatio){
	super(srf);
	surface = srf.surface;
	this.isoparmNumRatio = isoparmNumRatio;
    }
    public ISurfaceGraphicWireframeGL(IObject obj, ISurfaceI srf, int isoparmNumRatio){
	super(obj);
	surface = srf;
	this.isoparmNumRatio = isoparmNumRatio;
    }
        
    public void setIsoparmNumberRatio(int p){ isoparmNumRatio = p; }
    
    
    public void setWeight(float w){ weight=w; }
    public float getWeight(){ return weight; }
    
    /*
    public ArrayList<IVec[]> //ArrayList<IGLLineStrip>
	getLineInsideTrim(IVec2[] uvpts, IVec2[][] outTrimUV, IVec2[][] inTrimUV){
	
	//ArrayList<IGLLineStrip> lines = new ArrayList<IGLLineStrip>();
	ArrayList<IVec[]> linePts = new ArrayList<IVec[]>();
	//IGLLineStrip curLine = null;
	IVec[] curLinePts = null;
	
	if(inTrimUV==null&&outTrimUV==null){
	    //curLine = new IGLLineStrip(uvpts.length);
	    curLinePts = new IVec[uvpts.length];
	    for(int i=0; i<uvpts.length; i++)
		//curLine.setPoint(i,surface.pt(uvpts[i].x, uvpts[i].y).get());
		curLinePts[i] = surface.pt(uvpts[i].x, uvpts[i].y).get();
	    //lines.add(curLine);
	    linePts.add(curLinePts);
	    return lines;
	}
	
	if(outTrimUV!=null && outTrimUV.length>0){
	    //IOut.print("outTrim[0]");
	    for(int i=0; i<outTrimUV[0].length; i++){
		//IOut.print(outTrimUV[0][i]+", ");
		//IOut.p("outTrimUV[0]["+i+"]="+outTrimUV[0][i]);//
	    }
	    //IOut.print("\n");
	}
	
	
	boolean prevInside=false;
	IVec2[] prevTrimPts=null;
	for(int i=0; i<uvpts.length; i++){
	    boolean inside=true;
	    IVec2[] trimPts=null;
	    
	    for(int j=0; outTrimUV!=null&&j<outTrimUV.length && inside; j++)
		if(!uvpts[i].isInside(outTrimUV[j])){
		    //IOut.p("uvpts["+i+"] out of outTrim:"+uvpts[i]);
		    inside=false;
		    trimPts = outTrimUV[j];
		}
	    for(int j=0; inTrimUV!=null&&j<inTrimUV.length && inside; j++)
		if(uvpts[i].isInside(inTrimUV[j])){
		    //IOut.p("uvpts["+i+"] in of inTrim:"+uvpts[i]);
		    inside=false;
		    trimPts = inTrimUV[j];
		}
		else{
		    //IOut.p("uvpts["+i+"] out of inTrim:"+uvpts[i]);
		}
	    
	    if(inside){
		if(!prevInside){
		    curLine = new IGLLineStrip();
		    if(i>0){
			IVec2 isct=IVec2.intersectPolygon(uvpts[i-1],uvpts[i],prevTrimPts);
			if(isct!=null){
			    IOut.p("intersect point!");
			    curLine.addPoint(surface.pt(isct.x, isct.y).get());
			}
			else{
			    IOut.p("intersect not found!");
			}
		    }
		}
		curLine.addPoint(surface.pt(uvpts[i].x, uvpts[i].y).get());
	    }
	    else if(prevInside){
		if(curLine.getNum()>1){
		    IVec2 isct=IVec2.intersectPolygon(uvpts[i-1],uvpts[i],trimPts);
		    if(isct!=null){
			IOut.p("intersect point!");
			curLine.addPoint(surface.pt(isct.x, isct.y).get());
		    }
		    else{
			IOut.p("intersect not found!");
		    }
		    curLine.finalizePoint();
		    lines.add(curLine);
		}
		curLine=null;
	    }
	    prevInside=inside;
	    prevTrimPts = trimPts;
	}
	if(prevInside && curLine!=null){
	    curLine.finalizePoint();
	    lines.add(curLine);
	}
	
	return lines;
    }
    
    */
    
    /**
     //@return u lines for graphics; number depends on how it intersects with trim lines.
    **/
    /*
    public ArrayList<IGLLineStrip> getULine(double v, IVec2[][] outTrimUV, IVec2[][] inTrimUV){
	int uepnum = surface.uepNum();
	int udeg = surface.udeg();
	int unum = uepnum;
	
	int reso = IConfig.isoparmResolution * IConfig.segmentResolution;
	
	if(udeg>1) unum = (uepnum-1)*reso+1;
	
	IVec2[] upts = new IVec2[unum];
	
	if(udeg==1)
	    for(int uidx=0; uidx<uepnum; uidx++)
		upts[uidx] = new IVec2(surface.u(uidx,0),v);
	else
	    for(int uidx=0; uidx<uepnum; uidx++)
		for(int u=0; (u<reso)&&(uidx<uepnum-1)||(u<=0);u++){
		    double ufrc = (double)u/reso;
		    upts[uidx*reso+u] = new IVec2(surface.u(uidx,ufrc),v);
		}
	
	return getLineInsideTrim(upts, outTrimUV, inTrimUV);
    }
    */
    /**
       //@return u lines for graphics; number depends on how it intersects with trim lines.
    **/
    /*
    public ArrayList<IGLLineStrip> getVLine(double u, IVec2[][] outTrimUV, IVec2[][] inTrimUV){
	int vepnum = surface.vepNum();
	int vdeg = surface.vdeg();
	int vnum = vepnum;
	
	int reso = IConfig.isoparmResolution * IConfig.segmentResolution;
	
	
	if(vdeg>1) vnum = (vepnum-1)*reso+1;
	
	IVec2[] vpts = new IVec2[vnum];
	
	if(vdeg==1)
	    for(int vidx=0; vidx<vepnum; vidx++)
		vpts[vidx] = new IVec2(u,surface.v(vidx,0));
	else
	    for(int vidx=0; vidx<vepnum; vidx++)
		for(int v=0; (v<reso)&&(vidx<vepnum-1)||(v<=0);v++){
		    double vfrc = (double)v/reso;
		    vpts[vidx*reso+v] = new IVec2(u,surface.v(vidx,vfrc));
		}
	
	return getLineInsideTrim(vpts, outTrimUV, inTrimUV);
    }
    */
    
    public void initSurface(){
	
	synchronized(parent){
	    
	    //if(parent instanceof ISurface){ surface = ((ISurface)parent).surface; }
	    //else if(parent instanceof ISurfaceR){ surface = ((ISurfaceR)parent).surface; }
	    
	    //int uepnum = surface.uepNum();
	    //int vepnum = surface.vepNum();
	    
	    uepnum = surface.uepNum();
	    vepnum = surface.vepNum();
	    
	    isoparmNumU = isoparmNumRatio*(vepnum-1)+1;
	    isoparmNumV = isoparmNumRatio*(uepnum-1)+1;
	    
	    ITrimLoopGraphic[] outtrims=null;
	    ITrimLoopGraphic[] intrims=null;
	
	if(surface.hasOuterTrim()){
	    outtrims = new ITrimLoopGraphic[surface.outerTrimLoopNum()];
	    //outTrim = new IGLLineLoop[surface.outerTrimLoopNum()];
	    outTrimPts = new IVec[surface.outerTrimLoopNum()][];
	    outTrim2 = new IPolyline2D[surface.outerTrimLoopNum()];
	    
	    for(int i=0; i<surface.outerTrimLoopNum(); i++){
		outtrims[i] = new ITrimLoopGraphic(surface.outerTrimLoop(i),
						   true,IConfig.segmentResolution);
		//outTrim[i] = new IGLLineLoop(outtrims[i].getPolyline());
		outTrimPts[i] = outtrims[i].getPolyline().get();
		outTrim2[i] = outtrims[i].getPolyline2D();
	    }
	}
	else{
	    outtrims = new ITrimLoopGraphic[1];
	    //outTrim = new IGLLineLoop[1];
	    outTrimPts = new IVec[1][];
	    outTrim2 = new IPolyline2D[1];
	    outtrims[0] = new ITrimLoopGraphic(surface);
	    //outTrim[0] = new IGLLineLoop(outtrims[0].getPolyline());
	    outTrimPts[0] = outtrims[0].getPolyline().get();
	    outTrim2[0] = outtrims[0].getPolyline2D();
	}
	
	if(surface.hasInnerTrim()){
	    intrims = new ITrimLoopGraphic[surface.innerTrimLoopNum()];
	    //inTrim = new IGLLineLoop[surface.innerTrimLoopNum()];
	    inTrimPts = new IVec[surface.innerTrimLoopNum()][];
	    inTrim2 = new IPolyline2D[surface.innerTrimLoopNum()];
	    for(int i=0; i<surface.innerTrimLoopNum(); i++){
		intrims[i] = new ITrimLoopGraphic(surface.innerTrimLoop(i),
						  false,IConfig.segmentResolution);
		//inTrim[i] = new IGLLineLoop(intrims[i].getPolyline());
		inTrimPts[i] = intrims[i].getPolyline().get();
		inTrim2[i] = intrims[i].getPolyline2D();
	    }
	}
	
	
	// u isoparms
	if(isoparmNumU>2){
	    //ArrayList<IGLLineStrip> ul = new ArrayList<IGLLineStrip>();
	    ArrayList<IVec[]> ul = new ArrayList<IVec[]>();
	    ArrayList<IPolyline2D> ul2 = new ArrayList<IPolyline2D>();
	    for(int vidx=0; vidx<vepnum-1; vidx++){
		for(int v=0; v<isoparmNumRatio; v++){
		    double vfrc = (double)v/isoparmNumRatio;
		    if(vidx>0||v>0){
			IIsoparmGraphic isoparm =
			    new IIsoparmGraphic(surface, surface.v(vidx,vfrc),
						true, outtrims, intrims);
			for(int i=0; i<isoparm.num(); i++){
			    //ul.add(new IGLLineStrip(isoparm.getLine(i)));
			    ul.add(isoparm.getLine(i).get());
			    ul2.add(isoparm.getLine2D(i));
			}
		    }
		}
	    }
	    //uline = ul.toArray(new IGLLineStrip[ul.size()]);
	    ulinePts = ul.toArray(new IVec[ul.size()][]);
	    uline2 = ul2.toArray(new IPolyline2D[ul2.size()]);
	}
	
	
	// v isoparms
	if(isoparmNumV>2){
	    //ArrayList<IGLLineStrip> vl = new ArrayList<IGLLineStrip>();
	    ArrayList<IVec[]> vl = new ArrayList<IVec[]>();
	    ArrayList<IPolyline2D> vl2 = new ArrayList<IPolyline2D>();	    
	    for(int uidx=0; uidx<uepnum-1; uidx++){
		for(int u=0; u<isoparmNumRatio; u++){
		    double ufrc = (double)u/isoparmNumRatio;
		    if(uidx>0||u>0){
			IIsoparmGraphic isoparm =
			    new IIsoparmGraphic(surface, surface.u(uidx,ufrc),
						false, outtrims, intrims);
			for(int i=0; i<isoparm.num(); i++){
			    //vl.add(new IGLLineStrip(isoparm.getLine(i)));
			    vl.add(isoparm.getLine(i).get());
			    vl2.add(isoparm.getLine2D(i));
			}
		    }
		}
	    }
	    //vline = vl.toArray(new IGLLineStrip[vl.size()]);
	    vlinePts = vl.toArray(new IVec[vl.size()][]);
	    vline2 = vl2.toArray(new IPolyline2D[vl2.size()]);
	}
	
	initialized=true;
	
	if(surface!=null && surface.unum()==2 && surface.vnum()==2 &&
	   // need to be parallelogram
	   Math.abs(surface.cp(0,0).dist(surface.cp(1,0))-surface.cp(0,1).dist(surface.cp(1,1)))<=IConfig.tolerance &&
	   Math.abs(surface.cp(0,0).dist(surface.cp(0,1))-surface.cp(1,0).dist(surface.cp(1,1)))<=IConfig.tolerance &&
	   surface.isFlat()){ simpleFlat=true; }
	else{ simpleFlat=false; }

	}
    }
    
    
    public void updateSurface(){
	
	synchronized(parent){
	    
	    //if(true){initSurface();return;}
	    
	    if(uepnum != surface.uepNum() || vepnum != surface.vepNum()){
		initSurface();
		return;
	    }
	    
	
	if(simpleFlat && !surface.isFlat()){
	    // if not flat anymore, rebuild whole points
	    simpleFlat=false;
	    initSurface();
	    return;
	}
	
	/*
	if( uline2==null && vline2==null && inTrim2==null && outTrim2==null){
	    initSurface();
	    return;
	}
	*/
	
	// uline
	if(ulinePts!=null){
	    if(ulinePts.length!=uline2.length){ ulinePts = new IVec[uline2.length][]; }
	    for(int i=0; i<uline2.length; i++){
		if(ulinePts[i]==null || ulinePts[i].length!=uline2[i].num()){
		    ulinePts[i] = new IVec[uline2[i].num()];
		}
		for(int j=0; j<uline2[i].num(); j++){
		    IVec2 pt2 = uline2[i].get(j);
		    ulinePts[i][j] = surface.pt(pt2).get();
		}
	    }
	}
	// vline
	if(vlinePts!=null){
	    if(vlinePts.length!=vline2.length){ vlinePts = new IVec[vline2.length][]; }
	    for(int i=0; i<vline2.length; i++){
		if(vlinePts[i]==null || vlinePts[i].length!=vline2[i].num()){
		    vlinePts[i] = new IVec[vline2[i].num()];
		}
		for(int j=0; j<vline2[i].num(); j++){
		    IVec2 pt2 = vline2[i].get(j);
		    vlinePts[i][j] = surface.pt(pt2).get();
		}
	    }
	}
	// intrim
	if(inTrimPts!=null){
	    if(inTrimPts.length!=inTrim2.length){ inTrimPts = new IVec[inTrim2.length][]; }
	    for(int i=0; i<inTrim2.length; i++){
		if(inTrimPts[i]==null || inTrimPts[i].length!=inTrim2[i].num()){
		    inTrimPts[i] = new IVec[inTrim2[i].num()];
		}
		for(int j=0; j<inTrim2[i].num(); j++){
		    IVec2 pt2 = inTrim2[i].get(j);
		    inTrimPts[i][j] = surface.pt(pt2).get();
		}
	    }
	}
	// outtrim
	if(outTrimPts!=null){
	    if(outTrimPts.length!=outTrim2.length){ outTrimPts = new IVec[outTrim2.length][]; }
	    for(int i=0; i<outTrim2.length; i++){
		if(outTrimPts[i]==null || outTrimPts[i].length!=outTrim2[i].num()){
		    outTrimPts[i] = new IVec[outTrim2[i].num()];
		}
		for(int j=0; j<outTrim2[i].num(); j++){
		    IVec2 pt2 = outTrim2[i].get(j);
		    outTrimPts[i][j] = surface.pt(pt2).get();
		}
	    }
	}
	
	
	/*
	// uline
	if(uline!=null){
	    if(uline.length!=uline2.length){ uline = new IGLLineStrip[uline2.length]; }
	    for(int i=0; i<uline2.length; i++){
		if(uline[i]==null || uline[i].num()!=uline2[i].num()){
		    uline[i] = new IGLLineStrip(uline2[i].num());
		}
		for(int j=0; j<uline2[i].num(); j++){
		    IVec2 pt2 = uline2[i].get(j);
		    uline[i].setPoint(j,surface.pt(pt2).get());
		}
	    }
	}
	
	// vline
	if(vline!=null){
	    if(vline.length!=vline2.length){ vline = new IGLLineStrip[vline2.length]; }
	    for(int i=0; i<vline2.length; i++){
		if(vline[i]==null || vline[i].num()!=vline2[i].num()){
		    vline[i] = new IGLLineStrip(vline2[i].num());
		}
		for(int j=0; j<vline2[i].num(); j++){
		    IVec2 pt2 = vline2[i].get(j);
		    vline[i].setPoint(j,surface.pt(pt2).get());
		}
	    }
	}
	
	// intrim
	if(inTrim!=null){
	    if(inTrim.length!=inTrim2.length){ inTrim = new IGLLineLoop[inTrim2.length]; }
	    for(int i=0; i<inTrim2.length; i++){
		if(inTrim[i]==null || inTrim[i].num()!=inTrim2[i].num()){
		    inTrim[i] = new IGLLineLoop(inTrim2[i].num());
		}
		for(int j=0; j<inTrim2[i].num(); j++){
		    IVec2 pt2 = inTrim2[i].get(j);
		    inTrim[i].setPoint(j,surface.pt(pt2).get());
		}
	    }
	}
	
	// outtrim
	if(outTrim!=null){
	    if(outTrim.length!=outTrim2.length){ outTrim = new IGLLineLoop[outTrim2.length]; }
	    for(int i=0; i<outTrim2.length; i++){
		if(outTrim[i]==null || outTrim[i].num()!=outTrim2[i].num()){
		    outTrim[i] = new IGLLineLoop(outTrim2[i].num());
		}
		for(int j=0; j<outTrim2[i].num(); j++){
		    IVec2 pt2 = outTrim2[i].get(j);
		    outTrim[i].setPoint(j,surface.pt(pt2).get());
		}
	    }
	}
	*/
	
	/*
	public IGLLineStrip[] uline, vline;
	public IGLLineLoop[] inTrim=null, outTrim=null;
	
	public IPolyline2D[] uline2, vline2;
	public IPolyline2D[] inTrim2=null, outTrim2=null;
	*/

	}
    }
    
    public boolean isDrawable(IGraphicMode m){
	//return m.isGL()&&m.isWireframe();
	return m.isGraphic3D()&&m.isWireframe();
    }
    
    
    synchronized public void draw(IGraphics g){
	
	//if(surface==null) initSurface();
	if(!initialized) initSurface();
	else if(update){ updateSurface(); update=false; }
	
	if(g.type() == IGraphicMode.GraphicType.GL ||
	   g.type() == IGraphicMode.GraphicType.P3D){
	    
	    IGraphics3D g3d = (IGraphics3D)g;
	    
	    //float red,green,blue,alpha;
	    float[] rgba = null;
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
	    
	    if(!g3d.view().mode().isTransparentWireframe()){
		if(rgba[3]!=1f) rgba = new float[]{ rgba[0], rgba[1], rgba[2], 1f };
		//alpha = 255;
	    }
	    else if(g3d.view().mode().isTransparent()){
		rgba = new float[]{ rgba[0], rgba[1], rgba[2], IConfig.transparentModeAlpha/255f };
		//alpha = IConfig.transparentModeAlpha;
	    }
	    
	    
	    if(g3d.view().mode().isLight() && g3d.view().mode().isLightWireframe()){
		g3d.ambient(rgba);
		g3d.diffuse(rgba);
		//g3d.specular(rgba);
		g3d.shininess(IConfig.shininess);
		g3d.stroke(rgba[0]*255, rgba[1]*255, rgba[2]*255, 0f); // ? without this, the color is tinted with the previous object's color
	    }
	    else{ g3d.stroke(rgba); }
	    
	    g3d.weight(weight);
	    
	    if(g3d.view().mode().isLight()&&!g3d.view().mode().isLightWireframe()) g3d.disableLight();
	    
	    if(inTrimPts!=null){
		for(int i=0; i<inTrimPts.length; i++){ g3d.drawLineLoop(inTrimPts[i]); }
	    }
	    if(outTrimPts!=null){
		for(int i=0; i<outTrimPts.length; i++){ g3d.drawLineLoop(outTrimPts[i]); }
	    }
	    if(ulinePts!=null){
		for(int i=0; i<ulinePts.length; i++){ g3d.drawLineStrip(ulinePts[i]); }
	    }
	    if(vlinePts!=null){
		for(int i=0; i<vlinePts.length; i++){ g3d.drawLineStrip(vlinePts[i]); }
	    }
	    
	    if(g3d.view().mode().isLight()&&!g3d.view().mode().isLightWireframe()) g3d.enableLight();
	    
	    ////
	    
	    /*
	    GL gl = ((IGraphicsGL)g).getGL();
	    //GL gl = g.getGL();
	    
	    if(gl==null) return;
	    
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
	    
	    if(!g.view().mode().isTransparentWireframe()){ alpha = 1f; }
	    else if(g.view().mode().isTransparent()){ alpha = (float)IConfig.transparentModeAlpha/255f; }
	    
	    if(g.view().mode().isLight() && g.view().mode().isLightWireframe()){
		float[] colorf = new float[]{ red, green, blue, alpha };
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, colorf, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, colorf, 0);
		//gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, colorf, 0);
		gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS,
			       ISurfaceGraphicGL.defaultShininess);
		gl.glColor4f(red, green, blue, 0f); // ? without this, the color is tinted with the previous object's color
	    }
	    else{ gl.glColor4f(red,green,blue,alpha); }
	    
	    gl.glLineWidth(weight);
	    
	    if(g.view().mode().isLight()&&!g.view().mode().isLightWireframe())
		gl.glDisable(GL.GL_LIGHTING);
	    
	    if(inTrim!=null){ for(int i=0; i<inTrim.length; i++) inTrim[i].draw(gl); }
	    if(outTrim!=null){ for(int i=0; i<outTrim.length; i++) outTrim[i].draw(gl); }
	    if(uline!=null){ for(int i=0; i<uline.length; i++) uline[i].draw(gl); }
	    if(vline!=null){ for(int i=0; i<vline.length; i++) vline[i].draw(gl); }
	    
	    if(g.view().mode().isLight()&&!g.view().mode().isLightWireframe())
		gl.glEnable(GL.GL_LIGHTING);
	    */
	}
    }
}
