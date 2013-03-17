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

package igeo.io;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.text.*;

import igeo.*;
import igeo.gui.*;

/**
   Wavefront OBJ file exporter class.
   
   @author Satoru Sugihara
*/
public class IObjFileExporter{
    
    public static final int maxColumn=20; // max number of data per line
    
    protected int vertexNumber, parametricVertexNumber, textureVertexNumber, normalVertexNumber, objectNumber, curve2DNumber;
    
    protected NumberFormat f;
    
    protected HashMap<IVertex, int[]> vertexMap;
    
    public IObjFileExporter(){}
    
    public void init(){
        vertexNumber=1;
        parametricVertexNumber=1;
        textureVertexNumber=1;
        normalVertexNumber=1;
        objectNumber=1;
        curve2DNumber=1;
	
	f = NumberFormat.getInstance();
	if(f instanceof DecimalFormat){
	    // default setting
	    ((DecimalFormat)f).setDecimalSeparatorAlwaysShown(false);
	    ((DecimalFormat)f).setMaximumFractionDigits(100);
	    ((DecimalFormat)f).setMinimumFractionDigits(0);
	    ((DecimalFormat)f).setGroupingUsed(false);
	}
	else{
	    IOut.p("ERROR: fail to get DecimalFormat"); 
	}
    }
    
    /**
       Writing the content of server out to OBJ file.
       The main entry of the exporter class.
       @param filename An exporting file name.
       @param server A server interface containing exporting data.
       @return Boolean true if writing is successful. Otherwise false.
    */
    static public boolean write(String filename, IServerI server){
	PrintStream ps = null;
	try{ ps = new PrintStream(new FileOutputStream(filename)); }
	catch(Exception e){ e.printStackTrace(); }
	
	if(ps==null){
	    IOut.p("ERROR: file coundn't be opened: "+filename); return false;
	}
	
	IObjFileExporter export = new IObjFileExporter();
	export.write(ps, server);
	
	ps.close();
	return true;
    }
    
    /**
       Writing the content of server out to OBJ file.
       The main entry of the exporter class.
       @param file An exporting file object.
       @param server A server interface containing exporting data.
       @return Boolean true if writing is successful. Otherwise false.
    */
    static public boolean write(File file, IServerI server){
	PrintStream ps = null;
	try{ ps = new PrintStream(new FileOutputStream(file)); }
	catch(Exception e){ e.printStackTrace(); }
	
	if(ps==null){
	    IOut.p("ERROR: file coundn't be opened: "+file); return false;
	}
	
	IObjFileExporter export = new IObjFileExporter();
	export.write(ps, server);
	
	ps.close();
	return true;
    }
    
    
    public void write(PrintStream ps, IServerI server){
	IServer serv = server.server();
	synchronized(IG.lock){
	    init();
	    for(int i=0; i<serv.objectNum(); i++){
		if(i%100==0 && i>0){
		    IOut.debug(1, (i)+"/"+serv.objectNum()); //
		}
		
		IObject e = serv.getObject(i);
		//if(e instanceof IPoint){
		if(e instanceof IVecI){
		    //IOut.p("saving: "+ e + "-"+e.parameter); //
		    ps.println("g group_"+objectNumber); //
		    ps.println("o object_"+objectNumber); //
		    writePoint(ps, (IVecI)e);
		    objectNumber++;
		}
		else if(e instanceof ICurveI){
		    //IOut.p("saving: "+ e + "-"+e.parameter); //
		    ps.println("g group_"+objectNumber); //
		    ps.println("o object_"+objectNumber); //
		    writeCurve(ps, (ICurveI)e);
		    objectNumber++;
		}
		else if(e instanceof ISurfaceI){
		    //IOut.p("saving: "+ e + "-"+e.parameter); //
		    ps.println("g group_"+objectNumber); //
		    ps.println("o object_"+objectNumber); //
		    writeSurface(ps, (ISurfaceI)e);
		    objectNumber++;
		}
		else if(e instanceof IMeshI){
		    ps.println("g group_"+objectNumber); //
		    ps.println("o object_"+objectNumber); //
		    writeMesh(ps, (IMeshI)e);
		    objectNumber++;
		}
		else{
		    IOut.p("Warning: "+ e + "-"+e.parameter+
			   " is not saved"); //
		}
	    }
	}
    }
    
    
    /** Exports a point object.
	Because OBJ file doesn't have point object, a point is exported as a line with zero length.
	@param ps PrintStream to write out.
	@param point Point object.
    */
    public void writePoint(PrintStream ps, IVecI point){
	int deg = 1;
	double[] knots = new double[]{ 0., 1. };
	double ustart = 0;
	double uend = 1;
	IVec pt = point.get();
	IVec[] cpts = new IVec[]{ pt, pt };
	for(int i=0; i<cpts.length; i++){
	    ps.println("v "+f.format(cpts[i].x)+" "+f.format(cpts[i].y)+
		       " " +f.format(cpts[i].z));
	}
	ps.println("cstype bspline");
	ps.println("deg "+deg);
	ps.print("curv "+ustart+" "+uend);
	for(int i=1; i<=cpts.length; i++, vertexNumber++) ps.print(" "+vertexNumber);
	ps.println();
	ps.print("parm u");
	for(int i=0; i<knots.length; i++) ps.print(" " + f.format(knots[i]));
	ps.println();
	ps.println("end"); //
    }
    
    /** Exports a curve object.
	@param ps PrintStream to write out.
	@param curve Curve object.
    */
    public void writeCurve(PrintStream ps, ICurveI curve){
	int deg = curve.deg();
	double[] knots = new double[curve.knotNum()];
	double ustart = curve.ustart();
	double uend = curve.uend();
	for(int i=0; i<knots.length; i++){
	    // recover original knots // should it ?
	    if(ustart!=0.0||uend!=1.0)
		knots[i] = curve.knot(i)*(uend-ustart) + ustart;
	    else knots[i] = curve.knot(i);
	}
	boolean rational=false;
	IVec[] cpts = new IVec[curve.cpNum()];
	for(int i=0; i<cpts.length; i++){
	    cpts[i] = curve.cp(i).get();
	    if(cpts[i] instanceof IVec4 && ((IVec4)cpts[i]).w!=1.0)
		rational = true;
	}
	
	for(int i=0; i<cpts.length; i++){
	    if(rational)
		if(cpts[i] instanceof IVec4)
		    ps.println("v "+f.format(cpts[i].x)+" "+f.format(cpts[i].y)+
			       " " +f.format(cpts[i].z)+" "+f.format(((IVec4)cpts[i]).w));
		else ps.println("v "+f.format(cpts[i].x)+" "+f.format(cpts[i].y)+
				" " +f.format(cpts[i].z)+" "+"1.0");
	    else
		ps.println("v "+f.format(cpts[i].x)+" "+f.format(cpts[i].y)+
			   " " +f.format(cpts[i].z));
	}
	if(rational) ps.println("cstype rat bspline");
	else ps.println("cstype bspline");
	ps.println("deg "+deg);
	ps.print("curv "+ustart+" "+uend);
	for(int i=1; i<=cpts.length; i++, vertexNumber++){
	    ps.print(" "+vertexNumber);
	    if(i%maxColumn==0 && i!=cpts.length) ps.println(" \\"); // too long line can't be read
	}
	ps.println();
	ps.print("parm u");
	for(int i=0; i<knots.length; i++){
            ps.print(" " + f.format(knots[i]));
            if((i%maxColumn==0)&&(i!=0)&&(i!=(knots.length-1))) ps.println(" \\");// too long line can'
	}
	ps.println();
	ps.println("end"); //
    }



    static public void checkTrimLoopDirection(ArrayList<ArrayList<ITrimCurveI>> trimLoops,
					      boolean isOuter){
	for(int i=0; i<trimLoops.size(); i++){
	    ITrimCurveI[] loop =
		trimLoops.get(i).toArray(new ITrimCurveI[trimLoops.get(i).size()]);
	    ITrimLoopGraphic loopGr = new ITrimLoopGraphic(loop, isOuter, 1);
	    if(loopGr.reversed()){
		ArrayList<ITrimCurveI> revLoop = new ArrayList<ITrimCurveI>();
		for(int j=0; j<loop.length; j++) revLoop.add(loop[loop.length-1-j].rev());
		trimLoops.set( i, revLoop);
	    }
	}
    }
    
    
    /** Exports a surface object.
	@param ps PrintStream to write out.
	@param surface Surface object.
    */
    public void writeSurface(PrintStream ps, ISurfaceI surface){
	int unum = surface.ucpNum();
	int vnum = surface.vcpNum();
	
	
	ArrayList<ArrayList<ITrimCurveI>> outloops = new ArrayList<ArrayList<ITrimCurveI>>();
	ArrayList<ArrayList<ITrimCurveI>> inloops = new ArrayList<ArrayList<ITrimCurveI>>();
	
	
	if(!surface.hasOuterTrim() && surface.hasInnerTrim()){
	    ArrayList<ITrimCurveI> defaultOutLoop = new ArrayList<ITrimCurveI>();
	    defaultOutLoop.add(new ITrimCurve(new IVec(0.,0.,0.),new IVec(1.,0.,0.)).surface(surface));
	    defaultOutLoop.add(new ITrimCurve(new IVec(1.,0.,0.),new IVec(1.,1.,0.)).surface(surface));
	    defaultOutLoop.add(new ITrimCurve(new IVec(1.,1.,0.),new IVec(0.,1.,0.)).surface(surface));
	    defaultOutLoop.add(new ITrimCurve(new IVec(0.,1.,0.),new IVec(0.,0.,0.)).surface(surface));
	    outloops.add(defaultOutLoop);
	}
	
	for(int i=0; i<surface.outerTrimLoopNum(); i++){
	    ArrayList<ITrimCurveI> loop = new ArrayList<ITrimCurveI>();
	    for(int j=0; j<surface.outerTrimNum(i); j++) loop.add(surface.outerTrim(i,j));
	    outloops.add(loop);
	}
	
	for(int i=0; i<surface.innerTrimLoopNum(); i++){
	    ArrayList<ITrimCurveI> loop = new ArrayList<ITrimCurveI>();
	    for(int j=0; j<surface.innerTrimNum(i); j++) loop.add(surface.innerTrim(i,j));
	    inloops.add(loop);
	}
	
	checkTrimLoopDirection(outloops, true);
	checkTrimLoopDirection(inloops, false);
	
	int[][] outerTrimIdx=null;
	int[][] innerTrimIdx=null;
	
	if(outloops.size()>0){
	    outerTrimIdx = new int[outloops.size()][];
	    for(int i=0; i<outloops.size(); i++){
		outerTrimIdx[i] = new int[outloops.get(i).size()];
		for(int j=0; j<outloops.get(i).size(); j++){
		    outerTrimIdx[i][j] = curve2DNumber;
		    writeTrimCurve(ps, outloops.get(i).get(j));
		}
	    }
	}
	if(inloops.size()>0){
	    innerTrimIdx = new int[inloops.size()][];
	    for(int i=0; i<inloops.size(); i++){
		innerTrimIdx[i] = new int[inloops.get(i).size()];
		for(int j=0; j<inloops.get(i).size(); j++){
		    innerTrimIdx[i][j] = curve2DNumber;
		    writeTrimCurve(ps, inloops.get(i).get(j));
		}
	    }
	}
	
	
	IVec[][] cpts = new IVec[unum][vnum];
	boolean rational = false;
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum; j++){
		cpts[i][j] = surface.cp(i,j).get();
		if(cpts[i][j] instanceof IVec4 && ((IVec4)cpts[i][j]).w!=1.0)
		    rational=true;
	    }
	}
	int udeg = surface.udeg();
        int vdeg = surface.vdeg();
	
	double ustart = surface.ustart();
	double uend = surface.uend();
	double vstart = surface.vstart();
	double vend = surface.vend();
	
	double[] uknots = new double[surface.uknotNum()];
        double[] vknots = new double[surface.vknotNum()];
	
	for(int i=0; i<uknots.length; i++){
	    uknots[i] = surface.uknot(i);
	    if(ustart!=0.0||uend!=1.0) uknots[i] = (uend-ustart)*uknots[i]+ustart;
	}
	for(int i=0; i<vknots.length; i++){
	    vknots[i] = surface.vknot(i);
	    if(vstart!=0.0||vend!=1.0) vknots[i] = (vend-vstart)*vknots[i]+vstart;
	}
	
	for(int j=0; j<vnum; j++){
	    for(int i=0; i<unum; i++){
		if(rational)
                    ps.println("v "+f.format(cpts[i][j].x)+" "+f.format(cpts[i][j].y)+
			       " " +f.format(cpts[i][j].z)+" "+f.format(((IVec4)cpts[i][j]).w));
                else ps.println("v "+f.format(cpts[i][j].x)+" "+f.format(cpts[i][j].y)+
				" " +f.format(cpts[i][j].z));
	    }
	}
	
        if(rational) ps.println("cstype rat bspline");
        else ps.println("cstype bspline");
        
        ps.println("deg " + udeg + " " + vdeg);
        
        ps.print("surf ");
        ps.print(f.format(ustart) + " " + f.format(uend) + " ");
        ps.print(f.format(vstart) + " " + f.format(vend) );
	
	for(int i=1; i<=(unum*vnum); i++, vertexNumber++){
	    ps.print(" "+vertexNumber);
	    if(i%maxColumn==0 && i!=(unum*vnum)) ps.println(" \\"); // too long line can't be read
	}
	ps.println();
	
        ps.print("parm u");
        for(int i=0; i<uknots.length; i++){
            ps.print(" " + f.format(uknots[i]));
            //ps.print(" " + uknots[i]);
            if((i%maxColumn==0)&&(i!=0)&&(i!=(uknots.length-1))) ps.println(" \\");// too long line can't be read
        }
        ps.println();
        
        ps.print("parm v");
        for(int i=0; i<vknots.length; i++){
            ps.print(" " + f.format(vknots[i]));
            //ps.print(" " + vknots[i]);
            if((i%maxColumn==0)&&(i!=0)&&(i!=(vknots.length-1))) ps.println(" \\");//too long line can't be read
        }
        ps.println();
	
	
	if(outloops.size()>0){
	    for(int i=0; i<outloops.size(); i++){
		ps.print("trim");
		for(int j=0; j<outloops.get(i).size(); j++){
		    ITrimCurveI tr = outloops.get(i).get(j);
		    ps.print(" "+f.format(tr.ustart()));
		    ps.print(" "+f.format(tr.uend()));
		    ps.print(" "+outerTrimIdx[i][j]);
		    if(j>0 && j%(maxColumn/2)==0 && j<outloops.get(i).size()-1)
			ps.println(" \\"); 
		}
		ps.println();
	    }
	}
	if(inloops.size()>0){
	    for(int i=0; i<inloops.size(); i++){
		ps.print("hole");
		for(int j=0; j<inloops.get(i).size(); j++){
		    ITrimCurveI tr = inloops.get(i).get(j);
		    ps.print(" "+f.format(tr.ustart()));
		    ps.print(" "+f.format(tr.uend()));
		    ps.print(" "+innerTrimIdx[i][j]);
		    if(j>0 && j%(maxColumn/2)==0 && j<inloops.get(i).size()-1)
			ps.println(" \\"); 
		}
		ps.println();
	    }
	}
	
	ps.println("end");
    }
    
    
    public void writeTrimCurve(PrintStream ps, ITrimCurveI trimCurve){
	int deg = trimCurve.deg();
	double[] knots = new double[trimCurve.knotNum()];
	double ustart = trimCurve.ustart();
	double uend = trimCurve.uend();
	for(int i=0; i<knots.length; i++){
	    // recover original knots // should it ?
	    if(ustart!=0.0||uend!=1.0)
		knots[i] = trimCurve.knot(i)*(uend-ustart) + ustart;
	    else knots[i] = trimCurve.knot(i);
	}
	
	double surfUStart = trimCurve.surface().ustart();
	double surfUEnd = trimCurve.surface().uend();
	double surfVStart = trimCurve.surface().vstart();
	double surfVEnd = trimCurve.surface().vend();
	
	boolean rational=false;
	IVec[] cpts = new IVec[trimCurve.cpNum()];
	for(int i=0; i<cpts.length; i++){
	    cpts[i] = trimCurve.cp(i).get();
	    if(cpts[i] instanceof IVec4 && ((IVec4)cpts[i]).w!=1.0) rational = true;
	    
	    // recover original u-v coordinate // sholud it !?
	    if(surfUStart!=0.0 || surfUEnd!=1.0)
		cpts[i].x = (surfUEnd-surfUStart)*cpts[i].x + surfUStart;
	    if(surfVStart!=0.0 || surfVEnd!=1.0)
		cpts[i].y = (surfVEnd-surfVStart)*cpts[i].y + surfVStart;
	}
	
	for(int i=0; i<cpts.length; i++){
	    if(rational)
		if(cpts[i] instanceof IVec4)
		    ps.println("vp "+f.format(cpts[i].x)+" "+f.format(cpts[i].y)+
			       " "+f.format(((IVec4)cpts[i]).w));
		else ps.println("vp "+f.format(cpts[i].x)+" "+f.format(cpts[i].y)+
				" "+"1.0");
	    else
		ps.println("vp "+f.format(cpts[i].x)+" "+f.format(cpts[i].y));
	}
	if(rational) ps.println("cstype rat bspline");
	else ps.println("cstype bspline");
	ps.println("deg "+deg);
	ps.print("curv2");
	for(int i=1; i<=cpts.length; i++, parametricVertexNumber++){
	    ps.print(" "+parametricVertexNumber);
	    if(i%maxColumn==0 && i!=cpts.length) ps.println(" \\"); // too long line can't be read
	}
	ps.println();
	ps.print("parm u");
	for(int i=0; i<knots.length; i++){
            ps.print(" " + f.format(knots[i]));
            if((i%maxColumn==0)&&(i!=0)&&(i!=(knots.length-1))) ps.println(" \\");// too long line can'
	}
	ps.println();
	ps.println("end"); //
	
	curve2DNumber++;
    }
    
    
    /** Exports a polygon mesh object.
	@param ps PrintStream to write out.
	@param mesh Polygon mesh object.
    */
    public void writeMesh(PrintStream ps, IMeshI mesh){
        for(int i=0; i<mesh.vertexNum(); i++) writeVertex(ps, mesh.vertex(i));
        for(int i=0; i<mesh.faceNum(); i++) writeFace(ps, mesh.face(i)); 
    }
    
    public void writeVertex(PrintStream ps, IVertex vertex){
	if(vertexMap==null) vertexMap = new HashMap<IVertex,int[]>();
	
	int[] referenceNum = new int[3];
	IVec v = vertex.get();
	ps.println("v "+f.format(v.x)+" "+f.format(v.y)+" "+f.format(v.z));
	referenceNum[0] = vertexNumber;
	vertexNumber++;
	
        if(vertex.texture()!=null){
	    IVec2 t = vertex.texture().get();
            ps.println("vt "+f.format(t.x)+" "+f.format(t.y));
	    referenceNum[1] = textureVertexNumber;
            textureVertexNumber++;
        }
	else{ referenceNum[1] = -1; }
	
	if(vertex.normal()!=null){
	    IVec n = vertex.normal().get();
            ps.println("vn "+f.format(n.x)+" "+f.format(n.y)+" "+f.format(n.z));
	    referenceNum[2] = normalVertexNumber;
            normalVertexNumber++;
        }
	else{ referenceNum[2] = -1; }
	
	vertexMap.put(vertex,referenceNum);
    }
    
    public void writeFace(PrintStream ps, IFace face){
        
        //for(int i=0; i<vertices.length; i++) writer.print(" "+vertices[i].referenceNum);
        
        int[][] vertexRef = new int[face.vertexNum()][];
	boolean missingVertex=false;
	if(vertexMap==null) missingVertex=true; 
	for(int i=0; i<face.vertexNum()&&!missingVertex; i++){
	    vertexRef[i] = vertexMap.get(face.vertex(i));
	    if(vertexRef[i]==null) missingVertex=true;
	}
	
	if(missingVertex){
	    IOut.err("vertex number in a face is not found. the face is not exported.");
	    return;
	}
	
	boolean writeNormal=true;
        boolean writeTexture=true;
	// only when all the vertices have normal / texture, it's saved
	for(int i=0; i<vertexRef.length&&writeTexture; i++) if(vertexRef[i][1] < 0) writeTexture=false;
        for(int i=0; i<vertexRef.length&&writeNormal; i++) if(vertexRef[i][2] < 0) writeNormal=false;
        
	ps.print("f");
        for(int i=0; i<vertexRef.length; i++){
	    
	    // fold line when it's too long?
	    
            ps.print(" "+vertexRef[i][0]);
            if(writeNormal||writeTexture){
                ps.print("/");
                if(writeTexture) ps.print(vertexRef[i][1]);
                ps.print("/");
                if(writeNormal) ps.print(vertexRef[i][2]);
            }
        }
        ps.println();
    }
    
    
}
