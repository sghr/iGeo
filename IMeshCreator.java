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

package igeo;

import java.util.ArrayList;
import java.util.Arrays;

/**
   Miscellious static methods to instantiate IMesh object.
   @see IMesh
   
   @author Satoru Sugihara
*/
public class IMeshCreator{
    /** state variable of a server to store surfaces created in the methods in this class */
    public static IServerI server = null;
    
    /** state variable of a mesh type to specify what vertex, edge face class to use to create a mesh,
	default is IMeshType */
    public static IMeshType type = new IMeshType();
    
    /** set a server to store surfaces created in the methods in this class */
    public static void server(IServerI s){ server=s; }
    public static IServerI server(){ return server; }
    
    /** set variable of a mesh type to specify what vertex, edge face class to use to create a mesh */
    public static void meshType(IMeshType typ){ type=typ; }
    public static IMeshType meshType(){ return type; }
    
    public static IMesh mesh(){ return new IMesh(server); }
    public static IMesh mesh(IMeshGeo m){ return new IMesh(server,m); }
    public static IMesh mesh(IMesh m){ return new IMesh(server,m); }
    //public static IMesh mesh(ArrayList<ICurveI> lines){ return new IMesh(server,lines,type); }
    public static IMesh mesh(ArrayList<ICurveI> lines){ return new IMesh(server,lines.toArray(new ICurve[lines.size()])); }
    public static IMesh mesh(ICurveI[] lines){ return new IMesh(server,lines); }
    
    public static IMesh mesh(IVec[][] matrix){ return new IMesh(server,matrix); }
    public static IMesh mesh(IVec[][] matrix, boolean triangulateDir){
	return new IMesh(server,matrix,triangulateDir,type);
    }
    public static IMesh mesh(IVec[][] matrix, int unum, int vnum, boolean triangulateDir){
	return new IMesh(server, matrix,unum,vnum,triangulateDir,type);
    }
    public static IMesh mesh(ArrayList<IVertex> v, ArrayList<IEdge> e, ArrayList<IFace> f){
	return new IMesh(server,v,e,f);
    }
    public static IMesh mesh(IVertex[] vtx, IEdge[] edg,IFace[] fcs){
	return new IMesh(server,vtx,edg,fcs);
    }

    public static IMesh mesh(IVec[] vert){ return new IMesh(server,vert); }
    public static IMesh mesh(IVertex[] vert){ return new IMesh(server,vert); }
    public static IMesh mesh(IVertex v1, IVertex v2, IVertex v3){ return new IMesh(server,v1,v2,v3); }
    public static IMesh mesh(IVertex v1, IVertex v2, IVertex v3, IVertex v4){
	return new IMesh(server,v1,v2,v3,v4);
    }
    public static IMesh mesh(IVecI v1, IVecI v2, IVecI v3){ return new IMesh(server,v1,v2,v3); }
    public static IMesh mesh(IVecI v1, IVecI v2, IVecI v3, IVecI v4){ return new IMesh(server,v1,v2,v3,v4); }
    public static IMesh mesh(double x1, double y1, double z1, double x2, double y2, double z2,
		      double x3, double y3, double z3){
	return new IMesh(server,x1,y1,z1,x2,y2,z2,x3,y3,z3);
    }
    public static IMesh mesh(double x1, double y1, double z1, double x2, double y2, double z2,
		      double x3, double y3, double z3, double x4, double y4, double z4){
        return new IMesh(server,x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4);
    }
    public static IMesh mesh(IFace[] fcs){ return new IMesh(server,fcs); }
    
    
    public static IMesh box(double x, double y, double z, double size){
	return box(new IVec(x,y,z),size,size,size);
    }
    public static IMesh box(double x, double y, double z, double width, double height, double depth){
        return box(new IVec(x,y,z),width,height,depth);
    }
    public static IMesh box(IVecI origin, double size){ return box(origin,size,size,size); }
    public static IMesh box(IVecI origin, double width, double height, double depth){
        return box(origin, new IVec(width,0,0), new IVec(0,height,0), new IVec(0,0,depth));
    }
    public static IMesh box(IVecI origin, IVecI xvec, IVecI yvec, IVecI zvec){
        IVecI[][][] pts = new IVecI[2][2][2];
	IVec o = origin.get();
	for(int i=0; i<2; i++){
	    for(int j=0; j<2; j++){
		for(int k=0; k<2; k++){
		    pts[i][j][k] = o.dup();
		    if(i>0) pts[i][j][k].add(xvec);
		    if(j>0) pts[i][j][k].add(yvec);
		    if(k>0) pts[i][j][k].add(zvec);
		}
	    }
	}
	return box(pts);
    }
    public static IMesh box(IVecI pt1, IVecI pt2, IVecI pt3, IVecI pt4,
			    IVecI pt5, IVecI pt6, IVecI pt7, IVecI pt8 ){
        IVecI[][][] pts = new IVecI[2][2][2];
	pts[0][0][0] = pt1;
	pts[1][0][0] = pt2;
	pts[1][1][0] = pt3;
	pts[0][1][0] = pt4;
	pts[0][0][1] = pt5;
	pts[1][0][1] = pt6;
	pts[1][1][1] = pt7;
	pts[0][1][1] = pt8;
	return box(pts);
    }
    public static IMesh box(IVecI[][][] corners){
	if(corners.length<2 ||
	   corners[0].length<2 || corners[1].length<2 ||
	   corners[0][0].length<2 || corners[0][1].length<2 ||
	   corners[1][0].length<2 || corners[1][1].length<2 ){ return null; }
	IVertex[][][] vtx = new IVertex[2][2][2];
	for(int i=0; i<2; i++)
	    for(int j=0; j<2; j++)
		for(int k=0; k<2; k++) vtx[i][j][k] = type.createVertex(corners[i][j][k]);
	return box(vtx);
    }
    public static IMesh box(IVertex[][][] corners){
	if(corners.length<2 ||
	   corners[0].length<2 || corners[1].length<2 ||
	   corners[0][0].length<2 || corners[0][1].length<2 ||
	   corners[1][0].length<2 || corners[1][1].length<2 ){ return null; }

	/*
	IEdge[][] edges = new IEdge[6][4];
	IFace[] faces = new IFace[6];
	
	// top
	edges[0][0] = type.createEdge(corners[0][0][0], corners[0][1][0]);
	edges[0][1] = type.createEdge(corners[0][1][0], corners[1][1][0]);
	edges[0][2] = type.createEdge(corners[1][1][0], corners[1][0][0]);
	edges[0][3] = type.createEdge(corners[1][0][0], corners[0][0][0]);
	faces[0] = type.createFace(edges[0]);
	
	// bottom
	edges[1][0] = type.createEdge(corners[0][0][1], corners[1][0][1]);
	edges[1][1] = type.createEdge(corners[1][0][1], corners[1][1][1]);
	edges[1][2] = type.createEdge(corners[1][1][1], corners[0][1][1]);
	edges[1][3] = type.createEdge(corners[0][1][1], corners[0][0][1]);
	faces[1] = type.createFace(edges[1]);
	
	// left
	edges[2][0] = type.createEdge(corners[0][0][0], corners[0][0][1]);
	edges[2][1] = type.createEdge(corners[0][0][1], corners[0][1][1]);
	edges[2][2] = type.createEdge(corners[0][1][1], corners[0][1][0]);
	edges[2][3] = type.createEdge(corners[0][1][0], corners[0][0][0]);
	faces[2] = type.createFace(edges[2]);
	
	// right
	edges[3][0] = type.createEdge(corners[1][0][0], corners[1][1][0]);
	edges[3][1] = type.createEdge(corners[1][1][0], corners[1][1][1]);
	edges[3][2] = type.createEdge(corners[1][1][1], corners[1][0][1]);
	edges[3][3] = type.createEdge(corners[1][0][1], corners[1][0][0]);
	faces[3] = type.createFace(edges[3]);
	
	//front
	edges[4][0] = type.createEdge(corners[0][0][0], corners[1][0][0]);
	edges[4][1] = type.createEdge(corners[1][0][0], corners[1][0][1]);
	edges[4][2] = type.createEdge(corners[1][0][1], corners[0][0][1]);
	edges[4][3] = type.createEdge(corners[0][0][1], corners[0][0][0]);
	faces[4] = type.createFace(edges[4]);
	
	// back
	edges[5][0] = type.createEdge(corners[0][1][0], corners[0][1][1]);
	edges[5][1] = type.createEdge(corners[0][1][1], corners[1][1][1]);
	edges[5][2] = type.createEdge(corners[1][1][1], corners[1][1][0]);
	edges[5][3] = type.createEdge(corners[1][1][0], corners[0][1][0]);
	faces[5] = type.createFace(edges[5]);

	ArrayList<IVertex> vertexArray = new ArrayList<IVertex>();
	ArrayList<IEdge> edgeArray = new ArrayList<IEdge>();
	ArrayList<IFace> faceArray = new ArrayList<IFace>();
	
	for(int i=0; i<2; i++)
	    for(int j=0; j<2; j++)
		for(int k=0; k<2; k++) vertexArray.add(corners[i][j][k]);
	
	for(int i=0; i<6; i++)
	    for(int j=0; j<4; j++) edgeArray.add(edges[i][j]);
	
	for(int i=0; i<6; i++) faceArray.add(faces[i]); 
	*/
	
	
	IEdge[] edges = new IEdge[12];
	IFace[] faces = new IFace[6];
	
	// direction check not to have inverted normals on mesh faces
	IVec uvec = corners[1][0][0].get().dif(corners[0][0][0]);
	IVec vvec = corners[0][1][0].get().dif(corners[0][0][0]);
	IVec wvec = corners[0][0][1].get().dif(corners[0][0][0]);
	if(uvec.cross(vvec).dot(wvec) >= 0){
	    edges[0] = type.createEdge(corners[0][0][0], corners[0][1][0]);
	    edges[1] = type.createEdge(corners[0][1][0], corners[1][1][0]);
	    edges[2] = type.createEdge(corners[1][1][0], corners[1][0][0]);
	    edges[3] = type.createEdge(corners[1][0][0], corners[0][0][0]);
	    
	    edges[4] = type.createEdge(corners[0][0][1], corners[0][1][1]);
	    edges[5] = type.createEdge(corners[0][1][1], corners[1][1][1]);
	    edges[6] = type.createEdge(corners[1][1][1], corners[1][0][1]);
	    edges[7] = type.createEdge(corners[1][0][1], corners[0][0][1]);
	}
	else{ // swap w dir
	    edges[0] = type.createEdge(corners[0][0][1], corners[0][1][1]);
	    edges[1] = type.createEdge(corners[0][1][1], corners[1][1][1]);
	    edges[2] = type.createEdge(corners[1][1][1], corners[1][0][1]);
	    edges[3] = type.createEdge(corners[1][0][1], corners[0][0][1]);
	    
	    edges[4] = type.createEdge(corners[0][0][0], corners[0][1][0]);
	    edges[5] = type.createEdge(corners[0][1][0], corners[1][1][0]);
	    edges[6] = type.createEdge(corners[1][1][0], corners[1][0][0]);
	    edges[7] = type.createEdge(corners[1][0][0], corners[0][0][0]);
	}
	edges[8] = type.createEdge(corners[0][0][0], corners[0][0][1]);
	edges[9] = type.createEdge(corners[0][1][0], corners[0][1][1]);
	edges[10] = type.createEdge(corners[1][1][0], corners[1][1][1]);
	edges[11] = type.createEdge(corners[1][0][0], corners[1][0][1]);
	
	// top
	faces[0] = type.createFace(edges[0],edges[1],edges[2],edges[3]);
	// bottom
	faces[1] = type.createFace(edges[4],edges[7],edges[6],edges[5]);
	// left
	faces[2] = type.createFace(edges[8],edges[4],edges[9],edges[0]);
	// right
	faces[3] = type.createFace(edges[11],edges[2],edges[10],edges[6]);
	//front
	faces[4] = type.createFace(edges[11],edges[7],edges[8],edges[3]);
	// back
	faces[5] = type.createFace(edges[9],edges[5],edges[10],edges[1]);
	
	
	ArrayList<IVertex> vertexArray = new ArrayList<IVertex>();
	ArrayList<IEdge> edgeArray = new ArrayList<IEdge>();
	ArrayList<IFace> faceArray = new ArrayList<IFace>();
	
	for(int i=0; i<2; i++)
	    for(int j=0; j<2; j++)
		for(int k=0; k<2; k++) vertexArray.add(corners[i][j][k]);
	for(int i=0; i<12; i++) edgeArray.add(edges[i]);
	for(int i=0; i<6; i++) faceArray.add(faces[i]); 
	IMesh mesh = new IMesh(vertexArray,edgeArray,faceArray);
	mesh.close();
	return mesh;
    }
    
    public static IMesh rectStick(IVecI pt1, IVecI pt2, IVecI udir, IVecI vdir){
	IVec[][][] pts = new IVec[2][2][2];
	IVec v1 = pt1.get();
	IVec v2 = pt2.get();
	pts[0][0][0] = v1.dup().add(-0.5, udir).add(-0.5, vdir);
	pts[0][0][1] = v1.dup().add( 0.5, udir).add(-0.5, vdir);
	pts[0][1][0] = v1.dup().add(-0.5, udir).add( 0.5, vdir);
	pts[0][1][1] = v1.dup().add( 0.5, udir).add( 0.5, vdir);
	pts[1][0][0] = v2.dup().add(-0.5, udir).add(-0.5, vdir);
	pts[1][0][1] = v2.dup().add( 0.5, udir).add(-0.5, vdir);
	pts[1][1][0] = v2.dup().add(-0.5, udir).add( 0.5, vdir);
	pts[1][1][1] = v2.dup().add( 0.5, udir).add( 0.5, vdir);
	return box(pts);
    }
    
    
    /**
       @param heightDir it provides reference to the direction of height but actual direction is re-calculated to be perpendicular to pt1-pt2 direction.
    */
    public static IMesh rectStick(IVecI pt1, IVecI pt2, double width, double height, IVecI heightDir){
	IVec dir = pt2.get().dif(pt1);
	IVec udir = dir.cross(heightDir);
	// < IConfig.tolerance or < IConfig.tolerance*IConfig.tolerance
	if(udir.len2()<IConfig.tolerance){
	    udir = dir.cross(IVec.zaxis); // if heightDir is parallel to dir, use z axis
	    if(udir.len2()<IConfig.tolerance) udir = dir.cross(IVec.xaxis); // if still parallel, use x axis
	}
	IVec vdir = udir.cross(dir);
	udir.len(width);
	vdir.len(height);
	return rectStick(pt1,pt2,udir,vdir);
    }
    
    /** height direction is z axis */
    public static IMesh rectStick(IVecI pt1, IVecI pt2, double width, double height){
	IVec dir = pt2.get().dif(pt1);
	IVec udir = dir.cross(IVec.zaxis);
	if(udir.len2()<IConfig.tolerance) udir = dir.cross(IVec.xaxis); // if parallel, use x axis
	IVec vdir = udir.cross(dir);
	udir.len(width);
	vdir.len(height);
	return rectStick(pt1,pt2,udir,vdir);
    }
    
    /**
       @param heightDir it provides reference to the direction of height but actual direction is re-calculated to be perpendicular to pt1-pt2 direction.
    */
    public static IMesh squareStick(IVecI pt1, IVecI pt2, double width, IVecI heightDir){
	return rectStick(pt1,pt2,width,width,heightDir);
    }
    
    /** height direction is z axis */
    public static IMesh squareStick(IVecI pt1, IVecI pt2, double width){
	return rectStick(pt1,pt2,width,width);
    }
    
    /**
       creates closed mesh stick with polygon profile
       @param pt1 center of one side of a stick
       @param pt2 center of another side of a stick
       @param polygonVertexNum number of vertex of profile polygon
    */
    public static IMesh polygonStick(IVecI pt1, IVecI pt2, double radius, int polygonVertexNum){
	return polygonStick(pt1,pt2,radius,radius,polygonVertexNum,IG.zaxis);
    }
    
    /**
       creates closed mesh stick with polygon profile
       @param pt1 center of one side of a stick
       @param pt2 center of another side of a stick
       @param polygonVertexNum number of vertex of profile polygon
       @param heightDir reference vector to locate one of vertex of polygon aligned with this direction
    */
    public static IMesh polygonStick(IVecI pt1, IVecI pt2, double radius, int polygonVertexNum,
				     IVecI heightDir){
	return polygonStick(pt1,pt2,radius,radius,polygonVertexNum,heightDir);
    }
    
    /**
       creates closed mesh stick with polygon profile
       @param pt1 center of one side of a stick
       @param pt2 center of another side of a stick
       @param polygonVertexNum number of vertex of profile polygon
    */
    public static IMesh polygonStick(IVecI pt1, IVecI pt2, double radius1, double radius2,
				     int polygonVertexNum){
	return polygonStick(pt1,pt2,radius1,radius2,polygonVertexNum,IG.zaxis);
    }
    
    /**
       creates closed mesh stick with polygon profile
       @param pt1 center of one side of a stick
       @param pt2 center of another side of a stick
       @param polygonVertexNum number of vertex of profile polygon
       @param heightDir reference vector to locate one of vertex of polygon aligned with this direction
    */
    public static IMesh polygonStick(IVecI pt1, IVecI pt2, double radius1, double radius2,
				     int polygonVertexNum, IVecI heightDir){
	
	if(polygonVertexNum<=2){
	    IOut.err("too less vertex number of a polygon. needs to be >= 3");
	    return null;
	}
	
	int num=polygonVertexNum;
	IVertex[] polygon1 = new IVertex[num];
	IVertex[] polygon2 = new IVertex[num];
	
	IVec p1 = pt1.get();
	IVec p2 = pt2.get();
	
	IVertex vertex1 = type.createVertex(p1);
	IVertex vertex2 = type.createVertex(p2);
	
	IVec dir = p2.dif(p1);
	
	IVec udir = heightDir.get().cross(dir);
	if(udir.len2()<IConfig.tolerance){
	    udir = IVec.zaxis.cross(dir); // if heightDir is parallel to dir, use z axis
	    if(udir.len2()<IConfig.tolerance) udir = IVec.xaxis.cross(dir); // if still parallel, use x axis
	}
	
	IVec vdir1 = dir.cross(udir).len(radius1);
	
	
	double angle = 2*Math.PI/num;
	if(radius1!=radius2){
	    IVec vdir2 = vdir1.dup().len(radius2);
	    for(int i=0; i<num; i++){
		polygon1[i] = type.createVertex(p1.cp(vdir1));
		polygon2[i] = type.createVertex(p2.cp(vdir2));
		vdir1.rot(dir, angle);
		vdir2.rot(dir, angle);
	    }
	}
	else{
	    for(int i=0; i<num; i++){
		polygon1[i] = type.createVertex(p1.cp(vdir1));
		polygon2[i] = type.createVertex(p2.cp(vdir1));
		vdir1.rot(dir, angle);
	    }
	}
	
	IEdge[] edges1 = new IEdge[num];
	IEdge[] edges2 = new IEdge[num];
	IEdge[] sideEdges = new IEdge[num];
	
	for(int i=0; i<num; i++){
	    edges1[i] = type.createEdge(polygon1[i], polygon1[(i+1)%num]);
	    edges2[i] = type.createEdge(polygon2[i], polygon2[(i+1)%num]);
	    sideEdges[i] = type.createEdge(polygon1[i], polygon2[i]);
	}
	
	IEdge[] radialEdges1 = null;
	IEdge[] radialEdges2 = null;
	
	if(num>4){
	    radialEdges1 = new IEdge[num];
	    radialEdges2 = new IEdge[num];
	    for(int i=0; i<num; i++){
		radialEdges1[i] = type.createEdge(vertex1, polygon1[i]);
		radialEdges2[i] = type.createEdge(vertex2, polygon2[i]);
	    }
	}
	
	IFace[] faces1 = null; // top faces at pt1
	IFace[] faces2 = null; // bottom faces at pt2
	
	if(num==3){
	    faces1 = new IFace[1];
	    faces2 = new IFace[1];
	    faces1[0] = type.createFace(edges1[0], edges1[2], edges1[1]);
	    faces2[0] = type.createFace(edges2[0], edges2[1], edges2[2]);
	}
	else if(num==4){
	    faces1 = new IFace[1];
	    faces2 = new IFace[1];
	    faces1[0] = type.createFace(edges1[0], edges1[3], edges1[2], edges1[1]);
	    faces2[0] = type.createFace(edges2[0], edges2[1], edges2[2], edges2[3]);
	}
	else{
	    faces1 = new IFace[num];
	    faces2 = new IFace[num];
	    for(int i=0; i<num; i++){
		faces1[i] = type.createFace(radialEdges1[(i+1)%num], edges1[i], radialEdges1[i]);
		faces2[i] = type.createFace(radialEdges2[i], edges2[i], radialEdges2[(i+1)%num]);
	    }
	}
	
	IFace[] sideFaces = new IFace[num];
	
	for(int i=0; i<num; i++){
	    sideFaces[i] = type.createFace(edges1[i], sideEdges[(i+1)%num], edges2[i], sideEdges[i]);
	}
	
	
	ArrayList<IVertex> vertexArray = new ArrayList<IVertex>();
	ArrayList<IEdge> edgeArray = new ArrayList<IEdge>();
	ArrayList<IFace> faceArray = new ArrayList<IFace>();
	
	if(num>4){ vertexArray.add(vertex1); }
	if(num>4){ vertexArray.add(vertex2); }
	for(int i=0; i<num; i++) vertexArray.add(polygon1[i]);
	for(int i=0; i<num; i++) vertexArray.add(polygon2[i]);
	
	for(int i=0; i<num; i++) edgeArray.add(edges1[i]);
	for(int i=0; i<num; i++) edgeArray.add(edges2[i]);
	if(num>4){ for(int i=0; i<num; i++) edgeArray.add(radialEdges1[i]); }
	if(num>4){ for(int i=0; i<num; i++) edgeArray.add(radialEdges2[i]); }
	for(int i=0; i<num; i++) edgeArray.add(sideEdges[i]);
	
	for(int i=0; i<faces1.length; i++) faceArray.add(faces1[i]);
	for(int i=0; i<faces2.length; i++) faceArray.add(faces2[i]);
	for(int i=0; i<num; i++) faceArray.add(sideFaces[i]);
	
	IMesh mesh = new IMesh(vertexArray,edgeArray,faceArray);
	mesh.close();
	return mesh;
    }
    
    
    public static IMesh stick(IVecI[][] pts){ return stick(pts, false); }
    
    /**
       creates closed mesh stick with matrix of points
       @param pts control point matrix whose u is stick direction and v is wrapped by connecting the first and the last in v.
       @param closeU if true, connect the end and the beginning in U direction
    */
    public static IMesh stick(IVecI[][] pts, boolean closeU){
	if(pts==null){
	    IOut.err("pts is null");
	    return null;
	}
	if(pts.length<=1){
	    IOut.err("pts unum needs to be more than 2");
	    return null;
	}
	if(pts[0].length<=2){
	    IOut.err("pts unum needs to be more than 3");
	    return null;
	}
	
	int unum = pts.length;
	int vnum = pts[0].length;
	
	IVertex[][] vtx = new IVertex[unum][vnum];
	for(int i=0; i<unum; i++)
	    for(int j=0; j<vnum; j++) vtx[i][j] = type.createVertex(pts[i][j]);
	
	IVertex vertex1=null, vertex2=null;
	IEdge[] radialEdges1 = null;
	IEdge[] radialEdges2 = null;
	
	if(!closeU && vnum>4){
	
	    IVec center1 = new IVec();
	    IVec center2 = new IVec();
	    
	    for(int i=0; i<vnum; i++){
		center1.add(pts[0][i]);
		center2.add(pts[unum-1][i]);
	    }
	    center1.div(vnum);
	    center2.div(vnum);
	
	    vertex1 = type.createVertex(center1);
	    vertex2 = type.createVertex(center2);

	    radialEdges1 = new IEdge[vnum];
	    radialEdges2 = new IEdge[vnum];
	    for(int i=0; i<vnum; i++){
		radialEdges1[i] = type.createEdge(vertex1, vtx[0][i]);
		radialEdges2[i] = type.createEdge(vertex2, vtx[unum-1][i]);
	    }
	}
	
	IEdge[][] uedges = null;
	IEdge[][] vedges = new IEdge[unum][vnum];
	
	if(closeU){ uedges = new IEdge[unum][vnum]; }
	else{ uedges = new IEdge[unum-1][vnum]; }
	
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum; j++){
		if(closeU || i<unum-1) uedges[i][j] = type.createEdge(vtx[i][j],vtx[(i+1)%unum][j]);
		vedges[i][j] =  type.createEdge(vtx[i][j],vtx[i][(j+1)%vnum]);
	    }
	}
	
	IFace[] capFaces1 = null; // top faces at pt1
	IFace[] capFaces2 = null; // bottom faces at pt2
	
	if(!closeU){
	    if(vnum==3){
		capFaces1 = new IFace[1];
		capFaces2 = new IFace[1];
		capFaces1[0] = type.createFace(vedges[0][0],vedges[0][1],vedges[0][2]);
		capFaces2[0] = type.createFace(vedges[unum-1][0],vedges[unum-1][1],vedges[unum-1][2]);
	    }
	    else if(vnum==4){
		capFaces1 = new IFace[1];
		capFaces2 = new IFace[1];
		capFaces1[0] = type.createFace(vedges[0][0],vedges[0][1],vedges[0][2],vedges[0][3]);
		capFaces2[0] = type.createFace(vedges[unum-1][0],vedges[unum-1][1],vedges[unum-1][2],vedges[unum-1][3]);
	    }
	    else{
		capFaces1 = new IFace[vnum];
		capFaces2 = new IFace[vnum];
		for(int i=0; i<vnum; i++){
		    capFaces1[i] = type.createFace(radialEdges1[(i+1)%vnum], vedges[0][i], radialEdges1[i]);
		    capFaces2[i] = type.createFace(radialEdges2[i], vedges[unum-1][i], radialEdges2[(i+1)%vnum]);
		}
	    }
	}
	
	IFace[][] sideFaces = null;
	if(closeU) sideFaces = new IFace[unum][vnum];
	else sideFaces = new IFace[unum-1][vnum];
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum; j++){
		if(closeU || i<unum-1){
		    sideFaces[i][j] = type.createFace(uedges[i][j], vedges[i][j],
						      uedges[i][(j+1)%vnum],
						      vedges[(i+1)%unum][j]);
		}
	    }
	}
	
	ArrayList<IVertex> vertexArray = new ArrayList<IVertex>();
	ArrayList<IEdge> edgeArray = new ArrayList<IEdge>();
	ArrayList<IFace> faceArray = new ArrayList<IFace>();
	
	if(vertex1!=null){ vertexArray.add(vertex1); }
	if(vertex2!=null){ vertexArray.add(vertex2); }
	for(int i=0; i<unum; i++)
	    for(int j=0; j<vnum; j++) vertexArray.add(vtx[i][j]);
	
	for(int i=0; i<uedges.length; i++)
	    for(int j=0; j<uedges[i].length; j++) edgeArray.add(uedges[i][j]);
	for(int i=0; i<vedges.length; i++)
	    for(int j=0; j<vedges[i].length; j++) edgeArray.add(vedges[i][j]);
	if(radialEdges1!=null){ for(int i=0; i<vnum; i++) edgeArray.add(radialEdges1[i]); }
	if(radialEdges2!=null){ for(int i=0; i<vnum; i++) edgeArray.add(radialEdges2[i]); }
	
	if(capFaces1!=null){ for(int i=0; i<capFaces1.length; i++) faceArray.add(capFaces1[i]); }
	if(capFaces2!=null){ for(int i=0; i<capFaces2.length; i++) faceArray.add(capFaces2[i]); }
	for(int i=0; i<sideFaces.length; i++)
	    for(int j=0; j<sideFaces[i].length; j++) faceArray.add(sideFaces[i][j]);
	
	IMesh mesh = new IMesh(vertexArray,edgeArray,faceArray);
	mesh.close();
	return mesh;
    }
    
    
    
    /** round stick */
    public static IMesh stick(IVecI pt1, IVecI pt2, double radius){
	return polygonStick(pt1,pt2,radius,IConfig.meshCircleResolution);
    }
    
    /** round stick */
    public static IMesh stick(IVecI pt1, IVecI pt2, double radius1, double radius2){
	return polygonStick(pt1,pt2,radius1,radius2,IConfig.meshCircleResolution);
    }
    
    /** round stick (alias of stick()) */
    public static IMesh roundStick(IVecI pt1, IVecI pt2, double radius){ return stick(pt1,pt2,radius); }
    /** round stick (alias of stick()) */
    public static IMesh roundStick(IVecI pt1, IVecI pt2, double radius1, double radius2){
	return stick(pt1,pt2,radius1,radius2);
    }
    
    
    // sphere
    
    /** mesh sphere
     @param topDir length of vector is radius in vertical direction
     @param sideDir length of vector is radius in holizontal direction. sideDir is re-orthogonalized to topDir
    */
    public static IMesh sphere(IVecI center, IVecI topDir, IVecI sideDir, int resolution){
	double topRadius = topDir.len();
	double sideRadius = sideDir.len();
	IVec top = topDir.get();
	IVec side2 = topDir.get().cross(sideDir); // other side dir
	IVec side1 = side2.cross(topDir).len(sideRadius); // re-orthogonalized side dir
	
	int unum = resolution;
	int vnum = (resolution+1)/2;
	IVertex[][] vtx = new IVertex[unum][vnum+1];
	for(int j=0; j<=vnum; j++){
	    if(j==0){
		IVertex v = new IVertex(top.cp(center));
		for(int i=0; i<unum; i++){ vtx[i][j] = v; }
	    }
	    else if(j==vnum){
		IVertex v = new IVertex(top.cp().neg().add(center));
		for(int i=0; i<unum; i++){ vtx[i][j] = v; }
	    }
	    else{
		double phi = Math.PI/vnum*j;
		for(int i=0; i<unum; i++){
		    double theta = 2*Math.PI/unum*i;
		    vtx[i][j] = new IVertex(top.cp().rot(side2,phi).scale1d(side1,sideRadius/topRadius).rot(top,theta).add(center));
		}
	    }
	}
	
	IMesh mesh = new IMesh(server);
	for(int j=0; j<vnum; j++){
	    for(int i=0; i<unum; i++){
		if(j==0){ // triangle
		    mesh.addFace(new IFace(vtx[0][j], vtx[i][j+1], vtx[(i+1)%unum][j+1]));
		}
		else if(j==vnum-1){ // triangle
		    mesh.addFace(new IFace(vtx[i][j], vtx[0][j+1], vtx[(i+1)%unum][j] ));
		}
		else{ // quad
		    mesh.addFace(new IFace(vtx[i][j], vtx[i][j+1], vtx[(i+1)%unum][j+1], vtx[(i+1)%unum][j]));
		}
	    }
	}
	return mesh;
    }
    
    /** mesh sphere
     @param topDir length of vector is radius in vertical direction
     @param sideDir length of vector is radius in holizontal direction. sideDir is re-orthogonalized to topDir
    */
    public static IMesh sphere(IVecI center, IVecI topDir, IVecI sideDir){
	return sphere(center,topDir,sideDir,IConfig.meshCircleResolution);
    }
    
    /** mesh sphere
     @param topDir length of vector is radius in vertical direction
    */
    public static IMesh sphere(IVecI center, IVecI topDir, int resolution){
	IVec sideDir = new IVec(topDir.len(),0,0);
	if(sideDir.isParallel(topDir)){ sideDir = new IVec(0,topDir.len(),0); }
	return sphere(center,topDir,sideDir,resolution);
    }
    
    
    /** mesh sphere
     @param topDir length of vector is radius in vertical direction
    */
    public static IMesh sphere(IVecI center, IVecI topDir){
	return sphere(center,topDir,IConfig.meshCircleResolution);
    }
    
    /** mesh sphere
     @param topDir length of vector is radius in vertical direction
    */
    public static IMesh sphere(IVecI center, IVecI topDir, double radius, int resolution){
	return sphere(center,topDir,radius,radius,resolution);
    }
    public static IMesh sphere(IVecI center, IVecI topDir, double topRadius, double sideRadius, int resolution){
	IVec topD = topDir.get().cp().len(topRadius);
	IVec sideDir = new IVec(sideRadius,0,0);
	if(sideDir.isParallel(topDir)){ sideDir = new IVec(0,sideRadius,0); }
	return sphere(center,topD,sideDir,resolution);
    }
    
    public static IMesh sphere(IVecI center, IVecI topDir, double radius){
	return sphere(center,topDir,radius,IConfig.meshCircleResolution);
    }
    public static IMesh sphere(IVecI center, IVecI topDir, double topRadius, double sideRadius){
	return sphere(center,topDir,topRadius,sideRadius,IConfig.meshCircleResolution);
    }
    
    public static IMesh sphere(IVecI center, double radius, int resolution){
	return sphere(center,new IVec(0,0,radius),new IVec(radius,0,0),resolution);
    }
    public static IMesh sphere(IVecI center, double radius){
	return sphere(center,radius,IConfig.meshCircleResolution);
    }
    
    public static IMesh sphere(IVecI center, double zradius, double xyradius, int resolution){
	return sphere(center,new IVec(0,0,zradius),new IVec(xyradius,0,0),resolution);
    }
    public static IMesh sphere(IVecI center, double zradius, double xyradius){
	return sphere(center,zradius,xyradius,IConfig.meshCircleResolution);
    }
    
    
    
    // pipe, cylinder
    // rectPipe, squarePipe, polygonPipe
    // stick, roundStick(==stick), rectStick, squareStick, polygonStick
    
    
    
    

    public static IVec[] polygonProfile(IVecI center, IVecI normal, IVecI rollDir,
					double radius, int vertexNum){
	IVec rdir = rollDir.get().dup().len(radius);
	IVec[] pts = new IVec[vertexNum];
	double angle = Math.PI*2/vertexNum;
	for(int i=0; i<vertexNum; i++){
	    pts[i] = rdir.cp(center);
	    rdir.rot(normal, angle);
	}
	return pts;
    }
    
    public static IMesh polygonStick(ICurveI crv, double radius, int polygonVertexNum, int railSegmentNum){
	IVecI[] cps = crv.cps();
	IVec n = IVec.averageNormal(cps);
	IVec dir = cps[1].get().dif(cps[0]);
	IVec center = cps[0].get();
	if(n.isParallel(dir)){
	    n = IG.zaxis;
	    if(n.isParallel(dir)){ n = IG.yaxis; }
	}
	IVec roll = dir.cross(n);
	IVec[] profilePts = polygonProfile(center, dir, roll, radius, polygonVertexNum);
	
	boolean closed=crv.isClosed();
	IVecI[] railPts=null;
	if(crv.deg()==1){
	    if(closed){ railPts = new IVec[crv.cpNum()-1]; }
	    else{ railPts = new IVec[crv.cpNum()]; }
	    for(int i=0; i<railPts.length; i++) railPts[i] = crv.cp(i);
	}
	else{
	    if(closed){ railPts = new IVec[railSegmentNum]; }
	    else{ railPts = new IVec[railSegmentNum+1]; }
	    for(int i=0; i<railPts.length; i++) railPts[i] = crv.pt((double)i/railSegmentNum);
	}
	
	IVecI[][] pts = ISurfaceCreator.sweepPoints(profilePts,1,center,roll,railPts,1);
	
	return stick(pts, closed);
    }
    
    public static IMesh polygonStick(ICurveI crv, double radius, int polygonVertexNum){
	int railSegmentNum = crv.epNum()*IConfig.segmentResolution;
	return polygonStick(crv,radius,polygonVertexNum,railSegmentNum);
    }
    
    public static IMesh roundStick(ICurveI crv, double radius, int railSegmentNum){
	return polygonStick(crv,radius,IConfig.meshCircleResolution,railSegmentNum);
    }
    
    public static IMesh roundStick(ICurveI crv, double radius){
	int railSegmentNum = crv.epNum()*IConfig.segmentResolution;
	return polygonStick(crv,radius,IConfig.meshCircleResolution,railSegmentNum);
    }
    
    public static IMesh stick(ICurveI crv, double radius, int railSegmentNum){
	return roundStick(crv,radius,railSegmentNum);
    }
    
    public static IMesh stick(ICurveI crv, double radius){
	return roundStick(crv,radius);
    }

    public static IMesh squareStick(ICurveI crv, double radius, int railSegmentNum){
	return polygonStick(crv,radius,4,railSegmentNum);
    }
    
    public static IMesh squareStick(ICurveI crv, double radius){
	int railSegmentNum = crv.epNum()*IConfig.segmentResolution;
	return polygonStick(crv,radius,4,railSegmentNum);
    }
    
    
    /** create thickned mesh towards each normal direction. if modifyOriginal is true and the mesh instance is IMesh class instance, it modify the input mesh */
    public static IMesh thicken(IMeshI mesh, double thickness, boolean modifyOriginal, IMeshType creator){
	if(modifyOriginal && mesh instanceof IMesh){ return thicken((IMesh)mesh,thickness,null); }
	IMesh m = null;
	if(mesh instanceof IMesh){ m = (IMesh)mesh.dup(); }
	else{ m = new IMesh(mesh.get()); }
	return thicken(m,thickness,null,creator);
    }
    
    /** create thickned mesh towards the specified direction. if modifyOriginal is true and the mesh instance is IMesh class instance, it modify the input mesh */
    public static IMesh thicken(IMeshI mesh, double thickness, IVecI dir, boolean modifyOriginal,IMeshType creator){
	if(modifyOriginal && mesh instanceof IMesh){ return thicken((IMesh)mesh,thickness,dir,creator); }
	IMesh m = null;
	if(mesh instanceof IMesh){ m = (IMesh)mesh.dup(); }
	else{ m = new IMesh(mesh.get()); }
	return thicken(m,thickness,dir,creator);
    }
    
    /** add thickness to mesh towards each normal direction. this modify the input mesh */
    public static IMesh thicken(IMesh mesh, double thickness, IMeshType creator){
	return thicken(mesh,thickness,null,creator);
    }
    
    /** add thickness to mesh towards the specified direction. this modify the input mesh. if the direction is null, it thicken into each normal direction */
    public static IMesh thicken(IMesh mesh, double thickness, IVecI dir, IMeshType creator){
	
	// offset vertices
	ArrayList<IVertex> offVertices = new ArrayList<IVertex>();
	// offset edges
	ArrayList<IEdge> offEdges = new ArrayList<IEdge>();
	// offset faces
	ArrayList<IFace> offFaces = new ArrayList<IFace>();
	
	offset(mesh, thickness, dir, offVertices, offEdges, offFaces);
	
	/*
	IVec offdir = null;
	if(dir!=null){ offdir = dir.get().dup().len(thickness); }
	for(int i=0; i<mesh.vertexNum(); i++){
	    IVec n = mesh.vertex(i).normal().get();
	    if(dir==null) offdir = n.dup().len(thickness);
	    IVertex v = new IVertex(mesh.vertex(i).get().dup());
	    v.add(offdir);
	    v.nml(n);
	    offVertices.add(v);
	}
	for(int i=0; i<mesh.edgeNum(); i++){
	    IEdge e = mesh.edge(i);
	    int idx1 = mesh.vertices().indexOf(e.vertices[0]);
	    int idx2 = mesh.vertices().indexOf(e.vertices[1]);
	    offEdges.add(new IEdge(offVertices.get(idx1), offVertices.get(idx2)));
	}
	for(int i=0; i<mesh.faceNum(); i++){
	    IFace f = mesh.face(i);
	    IEdge[] e = new IEdge[f.edgeNum()];
	    for(int j=0; j<f.edgeNum(); j++){
		int idx = mesh.edges().indexOf(f.edge(j));
		e[j] = offEdges.get(idx);
	    }
	    offFaces.add(new IFace(e));
	}
	*/
	
	/** this boolean switch make it assume duplicated edges at the same location exist and
	    they are supposed to be connected */
	final boolean checkDuplicatedEdge = true;
	/*
	boolean[] nakedEdges = new boolean[mesh.edgeNum()];
	for(int i=0; i<mesh.edgeNum(); i++){
	    nakedEdges[i]=false;
	    IEdge e1 = mesh.edge(i);
	    if(e1.faceNum()==1){
		boolean uniqueEdge=true;
		for(int j=0; j<i && uniqueEdge; j++){
		    if(e1.eq(mesh.edge(j))){
			uniqueEdge=false;
			nakedEdges[j]=false;
		    }
		}
		if(uniqueEdge){ nakedEdges[i] = true; }
	    }
	}
	*/
	ArrayList<IEdge> nakedEdges = mesh.mesh.nakedEdges(checkDuplicatedEdge);
	
	
	// side edges and faces
	ArrayList<IEdge> sideEdges = new ArrayList<IEdge>();
	ArrayList<IFace> sideFaces = new ArrayList<IFace>();
	int[] sideEdgeIndex = new int[mesh.vertexNum()];
	Arrays.fill(sideEdgeIndex, -1);

	for(int i=0; i<nakedEdges.size(); i++){
	    IEdge e = nakedEdges.get(i);
	    int vidx1 = mesh.vertices().indexOf(e.vertex(0));
	    IEdge sideEdge1 = null;
	    if(sideEdgeIndex[vidx1]<0){
		sideEdge1 = new IEdge(e.vertex(0), offVertices.get(vidx1));
		sideEdges.add(sideEdge1);
		sideEdgeIndex[vidx1] = sideEdges.size()-1;
	    }
	    else{ sideEdge1 = sideEdges.get(sideEdgeIndex[vidx1]); }
	    
	    int vidx2 = mesh.vertices().indexOf(e.vertex(1));
	    IEdge sideEdge2 = null;
	    if(sideEdgeIndex[vidx2]<0){
		sideEdge2 = new IEdge(e.vertex(1), offVertices.get(vidx2));
		sideEdges.add(sideEdge2);
		sideEdgeIndex[vidx2] = sideEdges.size()-1;
	    }
	    else{ sideEdge2 = sideEdges.get(sideEdgeIndex[vidx2]); }
	    
	    // quad mesh. should it have triangulated mesh option?
	    sideFaces.add(new IFace(e,sideEdge2,offEdges.get(mesh.edges().indexOf(e)),sideEdge1));
	}
	
	/*
	for(int i=0; i<mesh.edgeNum(); i++){
	    if(nakedEdges[i]){
		IEdge e = mesh.edge(i);
		int vidx1 = mesh.vertices().indexOf(e.vertex(0));
		IEdge sideEdge1 = null;
		if(sideEdgeIndex[vidx1]<0){
		    sideEdge1 = new IEdge(e.vertex(0), offVertices.get(vidx1));
		    sideEdges.add(sideEdge1);
		    sideEdgeIndex[vidx1] = sideEdges.size()-1;
		}
		else{ sideEdge1 = sideEdges.get(sideEdgeIndex[vidx1]); }
		
		int vidx2 = mesh.vertices().indexOf(e.vertex(1));
		IEdge sideEdge2 = null;
		if(sideEdgeIndex[vidx2]<0){
		    sideEdge2 = new IEdge(e.vertex(1), offVertices.get(vidx2));
		    sideEdges.add(sideEdge2);
		    sideEdgeIndex[vidx2] = sideEdges.size()-1;
		}
		else{ sideEdge2 = sideEdges.get(sideEdgeIndex[vidx2]); }
		
		// quad mesh. should it have triangulated mesh option?
		sideFaces.add(new IFace(mesh.edge(i),sideEdge2,offEdges.get(i),sideEdge1));
	    }
	}
	*/
	
	// flip normals of the original vertex
	if(thickness>0){ // if thickness < 0, flip offset vertices' normal
	    for(int i=0; i<mesh.vertexNum(); i++){
		if(mesh.vertex(i).normal!=null){
		    mesh.vertex(i).normal.neg();
		}
	    }
	    for(int i=0; i<mesh.faceNum(); i++){
		mesh.face(i).flipNormal();
	    }
	}
	else if(thickness<0){
	    for(int i=0; i<offVertices.size(); i++){
		if(offVertices.get(i).normal!=null){
		    offVertices.get(i).normal.neg();
		}
	    }
	    for(int i=0; i<offFaces.size(); i++){
		offFaces.get(i).flipNormal();
	    }
	    for(int i=0; i<sideFaces.size(); i++){
		sideFaces.get(i).flipNormal();
	    }
	}
	
	mesh.vertices().addAll(offVertices);
	mesh.edges().addAll(offEdges);
	mesh.edges().addAll(sideEdges);
	mesh.faces().addAll(offFaces);
	mesh.faces().addAll(sideFaces);
	mesh.close(); //
	return mesh;
	
	/*
	ArrayList<IVertex> newVertices = new ArrayList<IVertex>();
	ArrayList<IEdge> newEdges = new ArrayList<IEdge>();
	ArrayList<IFace> newFaces = new ArrayList<IFace>();
	
	newVertices.addAll(mesh.vertices());
	newVertices.addAll(offVertices);
	newEdges.addAll(mesh.edges());
	newEdges.addAll(offEdges);
	newEdges.addAll(sideEdges);
	newFaces.addAll(mesh.faces());
	newFaces.addAll(offFaces);
	newFaces.addAll(sideFaces);
	IG.p("new mesh");
	return new IMesh(//newVertices.toArray(new IVertex[newVertices.size()]),
			 //newEdges.toArray(new IEdge[newEdges.size()]),
			 newFaces.toArray(new IFace[newFaces.size()]));
	*/
    }
    
    
    /** create offset mesh towards each normal direction. */
    public static IMesh offset(IMeshI mesh, double thickness, IMeshType creator){
	// offset vertices
	ArrayList<IVertex> offVertices = new ArrayList<IVertex>();
	// offset edges
	ArrayList<IEdge> offEdges = new ArrayList<IEdge>();
	// offset faces
	ArrayList<IFace> offFaces = new ArrayList<IFace>();
	
	offset(mesh, thickness, null, offVertices, offEdges, offFaces);
	//IMesh m = new IMesh(offVertices, offEdges, offFaces);
	IMesh m = new IMesh(creator.createMesh(offVertices, offEdges, offFaces));
	if(mesh instanceof IObject){ m.attr((IObject)mesh); }
	return m;
    }
    
    /** create ArrayList of vertices, edges, faces, offset from the mesh */
    public static void offset(IMeshI mesh, double thickness, IVecI offsetDir,
			      ArrayList<IVertex> resultVertices,
			      ArrayList<IEdge> resultEdges,
			      ArrayList<IFace> resultFaces){
	
	IVec dir = null;
	if(offsetDir!=null){ dir = offsetDir.get().dup().len(thickness); }
	
	for(int i=0; i<mesh.vertexNum(); i++){
	    IVec n = mesh.vertex(i).normal().get();
	    if(offsetDir==null) dir = n.dup().len(thickness);
	    IVertex v = new IVertex(mesh.vertex(i).get().dup());
	    v.add(dir);
	    //if(thickness<0){ n=n.dup().neg(); }
	    v.nml(n);
	    resultVertices.add(v);
	}
	
	for(int i=0; i<mesh.edgeNum(); i++){
	    IEdge e = mesh.edge(i);
	    int idx1 = mesh.vertices().indexOf(e.vertices[0]);
	    int idx2 = mesh.vertices().indexOf(e.vertices[1]);
	    resultEdges.add(new IEdge(resultVertices.get(idx1), resultVertices.get(idx2)));
	}
	
	for(int i=0; i<mesh.faceNum(); i++){
	    IFace f = mesh.face(i);
	    IEdge[] e = new IEdge[f.edgeNum()];
	    for(int j=0; j<f.edgeNum(); j++){
		int idx = mesh.edges().indexOf(f.edge(j));
		e[j] = resultEdges.get(idx);
	    }
	    //if(thickness<0){ f.flipNormal(); }
	    resultFaces.add(new IFace(e));
	}
    }
    
    
    public static IMesh diamondTessellation(IMeshI mesh, boolean splitDiamond, boolean splitDir, IMeshType creator){
	
	IVertex[] midVtx = new IVertex[mesh.edgeNum()];
	IEdge[][] divEdge = new IEdge[mesh.edgeNum()][2];
	
	for(int i=0; i<mesh.edgeNum(); i++){
	    IEdge e = mesh.edge(i);
	    midVtx[i] = creator.createVertex(e.vertex(0).mid(e.vertex(1)));
	    divEdge[i][0] = creator.createEdge(e.vertex(0), midVtx[i]);
	    divEdge[i][1] = creator.createEdge(midVtx[i], e.vertex(1));
	}
	
	IEdge[][] diaEdge = new IEdge[mesh.faceNum()][];
	IFace[][] diaFace = new IFace[mesh.faceNum()][];
	IFace[] face = null;
	
	IEdge[] splitEdge = null;
	IFace[][] splitFace = null;

	if(splitDiamond){
	    splitEdge = new IEdge[mesh.faceNum()];
	    splitFace = new IFace[mesh.faceNum()][2];
	}
	else{
	    face = new IFace[mesh.faceNum()];
	}
	
	for(int i=0; i<mesh.faceNum(); i++){
	    IFace f = mesh.face(i);
	    int en = f.edgeNum();
	    diaEdge[i] = new IEdge[en];
	    diaFace[i] = new IFace[en];
	    int[] idx = new int[en];
	    for(int j=0; j<en; j++){ idx[j] = mesh.edges().indexOf(f.edge(j)); }
	    
	    for(int j=0; j<en; j++){
		diaEdge[i][j] = creator.createEdge(midVtx[idx[j]], midVtx[idx[(j+1)%en]]);
		diaFace[i][j] = creator.createFace(divEdge[idx[j]][1], divEdge[idx[(j+1)%en]][0], diaEdge[i][j]);
	    }
	    if(splitDiamond){
		if(en==4){
		    if(splitDir){
			splitEdge[i] = creator.createEdge(midVtx[idx[0]], midVtx[idx[2]]);
			splitFace[i][0] = creator.createFace(diaEdge[i][0],diaEdge[i][1],splitEdge[i]);
			splitFace[i][1] = creator.createFace(diaEdge[i][2],diaEdge[i][3],splitEdge[i]);
		    }
		    else{
			splitEdge[i] = creator.createEdge(midVtx[idx[1]], midVtx[idx[3]]);
			splitFace[i][0] = creator.createFace(diaEdge[i][1],diaEdge[i][2],splitEdge[i]);
			splitFace[i][1] = creator.createFace(diaEdge[i][3],diaEdge[i][0],splitEdge[i]);
		    }
		}
		else{
		    splitEdge[i] = null;
		    splitFace[i][0] = creator.createFace(diaEdge[i]);
		    splitFace[i][1] = null;
		}
	    }
	    else{
		face[i] = creator.createFace(diaEdge[i]);
	    }
	}
	
	ArrayList<IVertex> allVtx= new ArrayList<IVertex>();
	ArrayList<IEdge> allEdge = new ArrayList<IEdge>();
	ArrayList<IFace> allFace = new ArrayList<IFace>();
	
	for(int i=0; i<mesh.vertexNum(); i++){
	    allVtx.add(mesh.vertex(i)); // dup? or creator.createVertex? or same instance?
	}
	for(int i=0; i<midVtx.length; i++){
	    allVtx.add(midVtx[i]);
	}
	for(int i=0; i<divEdge.length; i++){
	    allEdge.add(divEdge[i][0]);
	    allEdge.add(divEdge[i][1]);
	}
	for(int i=0; i<diaEdge.length; i++){
	    for(int j=0; j<diaEdge[i].length; j++){
		allEdge.add(diaEdge[i][j]);
	    }
	}
	if(splitDiamond){
	    for(int i=0; i<splitEdge.length; i++){
		if(splitEdge[i]!=null){ allEdge.add(splitEdge[i]); }
	    }
	    for(int i=0; i<splitFace.length; i++){
		for(int j=0; j<splitFace[i].length; j++){
		    if(splitFace[i][j]!=null){ allFace.add(splitFace[i][j]); }
		}
	    }
	}
	else{
	    for(int i=0; i<face.length; i++){
		allFace.add(face[i]);
	    }
	}
	for(int i=0; i<diaFace.length; i++){
	    for(int j=0; j<diaFace[i].length; j++){
		allFace.add(diaFace[i][j]);
	    }
	}
	
	IMesh m = new IMesh(creator.createMesh(allVtx,allEdge,allFace));
	if(mesh instanceof IObject){ m.attr((IObject)mesh); }
	return m;
    }
    
    
}
