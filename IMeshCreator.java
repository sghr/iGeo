/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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

/**
   Miscellious static methods to instantiate IMesh object.
   @see IMesh
   
   @author Satoru Sugihara
   @version 0.7.0.0;
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
    public static IMesh mesh(ArrayList<ICurveI> lines){
	return new IMesh(server,lines,type);
    }
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
	if(udir.len2()>0) udir = dir.cross(IVec.xaxis); // if parallel, use x axis
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
    // pipe, cylinder
    // rectPipe, squarePipe, polygonPipe
    // stick, roundStick(==stick), rectStick, squareStick, polygonStick
    
    
}
