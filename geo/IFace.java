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

package igeo.geo;

import java.util.ArrayList;
import igeo.core.*;

/**
   Class of a face of polygon mesh.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IFace{
    public IVertex[] vertices;
    public IEdge[] edges;
    public IVec normal;
    
    
    public IFace(IEdge[] e){ init(e); }
    public IFace(IEdge e1, IEdge e2, IEdge e3){ this(new IEdge[]{e1, e2, e3}); }
    public IFace(IEdge e1, IEdge e2, IEdge e3, IEdge e4){ this(new IEdge[]{e1, e2, e3, e4}); }
    
    public IFace(IVertex[] v){
	int num = v.length;
	vertices = v;
	edges = new IEdge[num];
	for(int i=0; i<num; i++){
	    edges[i] = new IEdge(v[i],v[(i+1)%num]);
	    vertices[i].addFace(this);
	    edges[i].addFace(this);
	}
    }
    
    public IFace(IVertex v1, IVertex v2, IVertex v3){ this(new IVertex[]{v1,v2,v3}); }
    public IFace(IVertex v1, IVertex v2, IVertex v3, IVertex v4){ this(new IVertex[]{v1,v2,v3,v4}); }
    
    public void init(IEdge[] e){
	int num = e.length;
	
	vertices = new IVertex[num];
	edges = new IEdge[num];
	
	for(int i=0; i<num; i++) edges[i] = e[i];
	
	if(edges[1].contains(edges[0].vertices[0])) vertices[0]=edges[0].vertices[1];
	else if(edges[1].contains(edges[0].vertices[1])) vertices[0]=edges[0].vertices[0];
	else IOut.err("first&second edges don't match");
	
	
	for(int i=1; i<num; i++){
	    if(edges[i-1].contains(vertices[i-1]))
		vertices[i]=edges[i-1].getOtherVertex(vertices[i-1]);
	    else IOut.err((i-1)+"&"+i+" edges don't match");
	}
	
	if(edges[num-1].getOtherVertex(vertices[num-1]) != vertices[0])
	    IOut.err("first&last vertex doesn't match");
	
	for(int i=0; i<num; i++){
	    vertices[i].addFace(this);
	    edges[i].addFace(this);
	}
	
    }
    
    public IFace dup(){ // duplicate vertices, edges  
	int num = vertices.length;
	IVertex v[] = new IVertex[num];
	for(int i=0; i<num; i++) v[i] = vertices[i].dup();
	
	IEdge e[] = new IEdge[num];
	for(int i=0; i<num; i++) e[i] = new IEdge(v[i],v[(i+1)%num]);
	
	IFace f = new IFace(e);
	f.normal = normal;
	return f;
    }
    
    
    public IVertex getVertex(int i){ return vertex(i); }
    public IVertex vertex(int i){ return vertices[i]; }
    public IEdge getEdge(int i){ return edge(i); }
    public IEdge edge(int i){ return edges[i]; }
    
    public int vertexNum(){ return vertices.length; }
    public int edgeNum(){ return edges.length; }
    
    public IEdge getEdge(IVertex v1, IVertex v2){
	for(int i=0; i<edges.length; i++){
	    if( edges[i].vertices[0]==v1 && edges[i].vertices[1]==v2 ||
		edges[i].vertices[0]==v2 && edges[i].vertices[1]==v1 ){
		return edges[i];
	    }
	}
	return null;
    }
    
    public IVertex getOtherVertex(IVertex v1, IVertex v2){
	for(int i=0; i<vertices.length; i++){
	    if( (vertices[i] != v1) && (vertices[i] != v2) ) return vertices[i];
	}
	return null;
    }
    
    
    public IVec getCenter(){
	IVec center = new IVec();
	for(int i=0; i<vertices.length; i++) center.add(vertices[i].pos);
	center.div(vertices.length);
	return center;
    }
    
    
    public void del(){
	for(int i=0; i<edges.length; i++){
	    vertices[i].faces.remove(this);
	    edges[i].faces.remove(this);
	}
    }
    
    
    public boolean contains(IVertex v){
	for(int i=0; i<vertices.length; i++){ if(vertices[i]==v) return true; }
	return false;
    }
    
    public boolean contains(IEdge e){
	for(int i=0; i<edges.length; i++){ if(edges[i]==e) return true; }
	return false;
    }
    
    public int indexOf(IEdge e){
	for(int i=0; i<edges.length; i++){ if(edges[i]==e) return i; }
	return -1;
    }
    
    public void replaceVertex(IVertex oldVertex, IVertex newVertex){
	for(int i=0; i<edges.length; i++){
	    if(vertices[i] == oldVertex){ vertices[i] = newVertex; return; }
	}
	IOut.err("no such vertex on this face");
    }
    
    /*
    public boolean eq(IFace f){
	if(edges.length!=f.edges.length) return false; 
	ArrayList<IEdge> e = new ArrayList<IEdge>();
	for(int i=0; i<f.edges.length; i++) e.add(f.edges[i]);
	// ?
	for(int i=0; i<edges.length; i++){
	    if(!e.contains(edges[i])) return false;
	    else e.remove(edges[i]);
	}
	if(e.size()>0) return false;
	return true; 
    }
    */
    
    
    public double area(){
	if(vertices.length==3){
	    IVec v1 = vertices[1].pos.get().diff(vertices[0].pos);
	    IVec v2 = vertices[2].pos.get().diff(vertices[0].pos);
	    return v1.cross(v2).len()/2;
	}
	
	ArrayList<Object> edgesAndFaces = triangulate(true, new IMeshCreator());
	double area=0;
	for(int i=0; i<edgesAndFaces.size(); i++){
	    if(edgesAndFaces.get(i) instanceof IFace){
		area += ((IFace)edgesAndFaces.get(i)).area();
	    }
	}
	
	for(int i=0; i<edgesAndFaces.size(); i++){
	    if(edgesAndFaces.get(i) instanceof IEdge){
		((IEdge)edgesAndFaces.get(i)).del();
	    }
	    else if(edgesAndFaces.get(i) instanceof IFace){
		((IFace)edgesAndFaces.get(i)).del();
	    }
	}
	return area;
    }
    
    // average normal

    public IVec getAverageNormal(){
	int n = vertices.length;
	IVec nrm = new IVec();
	for(int i=0; i<n; i++){
	    IVec diff1 = vertices[(i+1)%n].pos.get().diff(vertices[i].pos);
	    IVec diff2 = vertices[(i+2)%n].pos.get().diff(vertices[(i+1)%n].pos);
	    nrm.add(diff1.cross(diff2));
	}
	nrm.unit();
	return nrm;
    }
    
    public void calcNormal(){
	setNormal(getAverageNormal());
    }
    
    public void setNormal(IVec n){
	normal = n;
	// set normal of vertices too.
	//for(IVertex v: vertices) v.normal(normal); ?
    }
    
    public void flipNormal(){
	if(normal!=null) normal.neg();
	
	IVertex[] tmpvtx = new IVertex[vertices.length];
	IEdge[] tmpedg = new IEdge[edges.length];
	
	for(int i=0; i<vertices.length; i++) tmpvtx[vertices.length-i-1] = vertices[i];
	for(int i=0; i<edges.length; i++) tmpedg[edges.length-i-1] = edges[i];
	
	vertices = tmpvtx;
	edges = tmpedg;
    }
    
    
    public ArrayList<Object> triangulate(boolean triangulateDirection, IMeshCreator creator){
	if(vertices.length==3) return null;
	
	int idx1=0;
	int idx2=vertices.length-1;
	
	int num = vertices.length - 2;
	
	ArrayList<IEdge> newEdges = new ArrayList<IEdge>();
	ArrayList<IFace> newFaces = new ArrayList<IFace>();
	
	for(; (idx1+1)<=(idx2-1); idx1++, idx2--){
	    
	    IEdge edge1=null, edge2=null, edgeExisting1=null, edgeExisting2=null,
		edgeDiagonal=null;
	    
	    IEdge[] triEdges1 = new IEdge[3];
	    IEdge[] triEdges2 = new IEdge[3];
	    
	    edgeExisting1 = edges[idx1];
	    edgeExisting2 = edges[idx2-1];
	    
	    if(idx1==0) edge1 = edges[idx2];
	    else edge1 = newEdges.get(newEdges.size()-1);
	    
	    if( (idx1+1)==(idx2-1) ) edge2 = null;
	    else if( (idx1+1)+1 ==(idx2-1) ) edge2 = edges[idx1+1];
	    else edge2 = creator.createEdge(vertices[idx1+1],vertices[idx2-1]);
	    
	    if(edge2!=null){
		if(triangulateDirection){
		    edgeDiagonal = creator.createEdge(vertices[idx1],vertices[idx2-1]);
		    triEdges1[0] = edge1;
		    triEdges1[1] = edgeDiagonal;
		    triEdges1[2] = edgeExisting2;
		    triEdges2[0] = edgeDiagonal;
		    triEdges2[1] = edgeExisting1;
		    triEdges2[2] = edge2;
		}
		else{
		    edgeDiagonal = creator.createEdge(vertices[idx1+1],vertices[idx2]);
		    triEdges1[0] = edge1;
		    triEdges1[1] = edgeExisting1;
		    triEdges1[2] = edgeDiagonal;
		    triEdges2[0] = edgeDiagonal;
		    triEdges2[1] = edge2;
		    triEdges2[2] = edgeExisting2;
		}
	    }
	    else{
		triEdges1[0] = edge1;
		triEdges1[1] = edgeExisting1;
		triEdges1[2] = edgeExisting2;
	    }
	    
	    IFace face1 = creator.createFace(triEdges1);
	    IFace face2 = null;
	    if(edge2!=null) face2 = creator.createFace(triEdges2);
	    
	    
	    if(edgeDiagonal!=null) newEdges.add(edgeDiagonal);
	    if(edge2!=null &&  (idx1+1)+1!=(idx2-1)) newEdges.add(edge2);
	    
	    newFaces.add(face1);
	    if(face2!=null) newFaces.add(face2);
	}
	
	ArrayList<Object> retval = new ArrayList<Object>(); // mix of edges and faces
	for(int i=0; i<newEdges.size(); i++) retval.add(newEdges.get(i));
	for(int i=0; i<newFaces.size(); i++) retval.add(newFaces.get(i));
	return retval;
    }
    
    
    
    public ArrayList<Object> triangulateAtCenter(IMeshCreator creator){
	
	if(vertices.length==3) return null;
	
	IVec center = getCenter();
	IVertex cv = creator.createVertex(center);
	
        int num = edges.length;
        IEdge[] newEdges = new IEdge[num];
        for(int i=0; i<num; i++){
            IVertex v1 = edges[i].getSharingVertex(edges[(i+1)%num]);
            newEdges[i] = creator.createEdge(v1,cv);
        }
        
        IFace[] newFaces = new IFace[num];
        for(int i=0; i<num; i++){
	    IEdge[] e = new IEdge[3];
	    e[0] = edges[i];
	    e[1] = newEdges[i];
	    e[2] = newEdges[(i-1+num)%num];
	    newFaces[i] = creator.createFace(e);
        }
	
	ArrayList<Object> retval = new ArrayList<Object>(); // mix of edges and faces
	retval.add(cv);
	for(int i=0; i<newEdges.length; i++) retval.add(newEdges[i]);
	for(int i=0; i<newFaces.length; i++) retval.add(newFaces[i]);
	return retval;
    }
    
    
    static public boolean eq(double v1, double v2){
	if( Math.abs(v1-v2) < IConfig.lengthResolution) return true;
	return false;
    }
    
    public boolean eq(IFace face){
	if(face.edges.length != this.edges.length) return false;
	
	if(edges.length==3){
	    if( face.edges[0].eq(edges[0]) ){
		if( face.edges[1].eq(edges[1]) ){
		    if( face.edges[2].eq(edges[2]) ) return true;
		    return false;
		}
		if( face.edges[1].eq(edges[2]) ){
		    if( face.edges[2].eq(edges[1]) ) return true;
		    return false;
		}
		return false;
	    }
	    if( face.edges[0].eq(edges[1]) ){
		if( face.edges[1].eq(edges[0]) ){
		    if( face.edges[2].eq(edges[2]) ) return true;
		    return false;
		}
		if( face.edges[1].eq(edges[2]) ){
		    if( face.edges[2].eq(edges[0]) ) return true;
		    return false;
		}
		return false;
	    }
	    if( face.edges[0].eq(edges[2]) ){
		if( face.edges[1].eq(edges[0]) ){
		    if( face.edges[2].eq(edges[1]) ) return true;
		    return false;
		}
		if( face.edges[1].eq(edges[1]) ){
		    if( face.edges[2].eq(edges[0]) ) return true;
		    return false;
		}
		return false;
	    }
	    return false;
	}
	else if(this.edges.length==4){
	    
	    IVertex vertices1[] = this.vertices; // does this work?
	    IVertex vertices2[] = face.vertices; // does this work?
	    
	    double len1[] = new double[4+2];
	    double len2[] = new double[4+2];
            
	    for(int i=0; i<4; i++){
		len1[i] = vertices1[i].pos.dist(vertices1[(i+1)%4].pos);
		len2[i] = vertices2[i].pos.dist(vertices2[(i+1)%4].pos);
	    }
	    
	    // opposite corner
	    len1[4] = vertices1[0].pos.dist(vertices1[2].pos);
	    len2[4] = vertices2[0].pos.dist(vertices2[2].pos);
	    len1[5] = vertices1[1].pos.dist(vertices1[3].pos);
	    len2[5] = vertices2[1].pos.dist(vertices2[3].pos);
	    
	    if(eq(len1[0],len2[0])){
		if(eq(len1[1],len2[1]) &&
		   eq(len1[2],len2[2]) &&
		   eq(len1[3],len2[3]) &&
		   ( eq(len1[4],len2[4])&&
		     eq(len1[5],len2[5])||
		     eq(len1[4],len2[5])&&
		     eq(len1[5],len2[4]) ) ){
		    return true;
		}
		
		if(eq(len1[1],len2[3]) &&
		   eq(len1[2],len2[2]) &&
		   eq(len1[3],len2[1]) &&
		   ( eq(len1[4],len2[4])&&
		     eq(len1[5],len2[5])||
		     eq(len1[4],len2[5])&&
		     eq(len1[5],len2[4]) )){
		    return true;
		}
		
	    }
	    if(eq(len1[0],len2[1])){
		
		if(eq(len1[1],len2[2]) &&
		   eq(len1[2],len2[3]) &&
		   eq(len1[3],len2[0]) &&
		   ( eq(len1[4],len2[4])&&
		     eq(len1[5],len2[5])||
		     eq(len1[4],len2[5])&&
		     eq(len1[5],len2[4]) )){
		    return true;
		}
		if(eq(len1[1],len2[0]) &&
		   eq(len1[2],len2[3]) &&
		   eq(len1[3],len2[2]) &&
		   ( eq(len1[4],len2[4])&&
		     eq(len1[5],len2[5])||
		     eq(len1[4],len2[5])&&
		     eq(len1[5],len2[4]) ) ){
		    return true;
		}
                
	    }
	    if(eq(len1[0],len2[2])){
		
		if(eq(len1[1],len2[3]) &&
		   eq(len1[2],len2[0]) &&
		   eq(len1[3],len2[1]) &&
		   ( eq(len1[4],len2[4])&&
		     eq(len1[5],len2[5])||
		     eq(len1[4],len2[5])&&
		     eq(len1[5],len2[4]) ) ){
		    return true;
		}
		if(eq(len1[1],len2[1]) &&
		   eq(len1[2],len2[0]) &&
		   eq(len1[3],len2[3]) &&
		   ( eq(len1[4],len2[4])&&
		     eq(len1[5],len2[5])||
		     eq(len1[4],len2[5])&&
		     eq(len1[5],len2[4]) ) ){
		    return true;
		}
                
	    }
	    if(eq(len1[0],len2[3])){
		
		if(eq(len1[1],len2[0]) &&
		   eq(len1[2],len2[1]) &&
		   eq(len1[3],len2[2]) &&
		   ( eq(len1[4],len2[4]) &&
		     eq(len1[5],len2[5])||
		     eq(len1[4],len2[5])&&
		     eq(len1[5],len2[4]) ) ){
		    return true;
		}
		
		if(eq(len1[1],len2[2]) &&
		   eq(len1[2],len2[1]) &&
		   eq(len1[3],len2[0]) &&
		   ( eq(len1[4],len2[4])&&
		     eq(len1[5],len2[5])||
		     eq(len1[4],len2[5])&&
		     eq(len1[5],len2[4]) ) ){
		    return true;
		}
                
	    }
	    return false;
	}
	return false; // unknown for other number of edges
    }
    
    
    // retval[0]: min, retval[1]: max
    public IVec[] getBoundary(){
	IVec min = new IVec(vertices[0]);
	IVec max = new IVec(vertices[0]);
	for(int i=1; i<vertices.length; i++){
	    IVec p = vertices[i].pos.get();
	    if(p.x < min.x) min.x = p.x;
	    else if(p.x > max.x) max.x = p.x;
	    if(p.y < min.y) min.y = p.y;
	    else if(p.y > max.y) max.y = p.y;
	    if(p.z < min.z) min.z = p.z;
	    else if(p.z > max.z) max.z = p.z;
	}
	IVec[] retval = new IVec[2];
	retval[0] = min;
	retval[1] = max;
	return retval;
    }
    
    
}
