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
   Class of an edge of polygon mesh.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IEdge{
    public IVertex[] vertices;
    public ArrayList<IFace> faces;
    
    public IEdge(IVertex v1, IVertex v2){
	vertices = new IVertex[2];
	vertices[0]=v1;
	vertices[1]=v2;
	faces = new ArrayList<IFace>();
	
	v1.addEdge(this);
	v2.addEdge(this);
    }
    
    public IVertex getVertex(int i){ return vertex(i); }
    public IVertex vertex(int i){ return vertices[i]; }
    public int vertexNum(){ return vertices.length; }  // should be always 2
    
    public void addFace(IFace f){ faces.add(f); }
    public IFace getFace(int i){ return face(i); }
    public IFace face(int i){ return faces.get(i); }
    
    public void del(){
	for(int i=0; i<faces.size(); i++) faces.get(i).del(); 
	vertices[0].edges.remove(this);
	vertices[1].edges.remove(this);
    }
    
    public IVec nrml(double param){ // 0 - 1.0
	return vertices[0].nrml().get().mul(1.-param).add(vertices[1].nrml().mul(param)).unit();
    }
    public IVec normal(double param){ return nrml(param); }
    
    public IVec nrml(){ return nrml(0.5); }
    public IVec normal(){ return nrml(); }
    
    
    public IVec getPointOnEdge(double ratio){
	// ratio 0-1 point of vertices[0] to vertices[1], 0.5 means mid point
	return vertices[0].pos.get().sum(vertices[1].pos,ratio);
    }
    
    public boolean contains(IVertex v){
	if(vertices[0]==v || vertices[1]==v) return true;
	return false;
    }
    
    public void replaceVertex(IVertex oldVertex, IVertex newVertex){
	if(vertices[0]==oldVertex){ vertices[0] = newVertex; }
	else if(vertices[1] ==oldVertex){ vertices[1] = newVertex; }
	else{ IOut.err(" no such vertex on this edge"); }
    }
    
    public IVertex getOtherVertex(IVertex v){
	if(vertices[0]==v) return vertices[1];
	if(vertices[1]==v) return vertices[0]; 
	IOut.err("no such vertex on this edge");
	return null;
    }
    
    public IVertex getOtherVertex(IEdge edge){
	if(edge.contains(vertices[0])) return vertices[1];
	if(edge.contains(vertices[1])) return vertices[0];
	return null;
    }
    
    public IVertex getSharingVertex(IEdge edge){
	if(edge.contains(vertices[0])) return vertices[0];
	if(edge.contains(vertices[1])) return vertices[1];
	return null;
    }
    
    public boolean isSharingVertex(IEdge edge){ return getSharingVertex(edge)!=null; }
    
    
    
    public IFace getOtherFace(IFace f){
	for(int i=0; i<faces.size(); i++) if(faces.get(i)!=f) return faces.get(i);
	return null;
    }
    
    public ArrayList getOtherFaces(IFace f){
	ArrayList<IFace> others = new ArrayList<IFace>();
	for(int i=0; i<faces.size(); i++) if(faces.get(i)!=f) others.add(faces.get(i));
	return others;
    }
    
    public boolean isOnEdge(IVertex v){
	IVec diff = vertices[1].pos.get().diff(vertices[0].pos);
	IVec d = v.pos.get().diff(vertices[0].pos);
	if(!diff.isParallel(d)) return false;
	double ip = diff.dot(d);
	if(ip<0 || ip>diff.len2()) return false;
	return true;
    }
    
    
    public ArrayList<ArrayList<IEdge>> traceEdge(int maxVertexNum){
	ArrayList<ArrayList<IEdge>> loops = new ArrayList<ArrayList<IEdge>>();
	ArrayList<IEdge> trace = new ArrayList<IEdge>();
	traceEdge(trace, loops, maxVertexNum);
	return loops;
    }
    
    
    public void traceEdge(ArrayList<IEdge> trace, ArrayList<ArrayList<IEdge>> loops, int depth){
	
	IVertex vertex=null;
	if(trace.size()>0){
	    vertex = getOtherVertex(trace.get(trace.size()-1));
	    if(vertex==null){
		// nothing... ?
	    }
	}
	else{
	    vertex = vertices[1]; // vertices[0] is origin
	}
	
	if(trace.size()>1 && trace.get(0).vertices[0] == vertex){ // found a loop
	    ArrayList<IEdge> loop = new ArrayList<IEdge>();
	    for(int i=0; i<trace.size(); i++) loop.add(trace.get(i));
	    loop.add(this);
	    loops.add(loop);
	    
	    return;
	}
	
	if(depth<=1){
	    //IOut.p("traceEdge: maximumDepth; return"); //
	    return; // max recursion
	}
	
	trace.add(this);
	if(vertex!=null){ // is this necessary?
	    for(int i=0; i<vertex.edges.size(); i++){
		if(vertex.edges.get(i) != this) vertex.edges.get(i).traceEdge(trace, loops, depth-1);
	    }
	}
	
	trace.remove(trace.size()-1);//
    }
    
    public String toString(){
	//return "IEdge: "+String.valueOf(vertices[0].getIndex())+"-"+String.valueOf(vertices[1].getIndex());
	String s1 = "null";
	if(vertices[0]!=null) s1=vertices[0].toString();
	String s2 = "null";
	if(vertices[1]!=null) s2=vertices[1].toString();
	return "IEdge: "+s1+"-"+s2;
    }
    
    public boolean eq(IEdge e){
	// direction does not matter?
	if(vertices.length!=e.vertices.length) return false;
	if(vertices[0].eq(e.vertices[0]) && vertices[1].eq(e.vertices[1])) return true;
	if(vertices[0].eq(e.vertices[1]) && vertices[1].eq(e.vertices[0])) return true;
	return false;
    }
    
    public IFace[] createFace(IMeshCreator creator){
	
	ArrayList<ArrayList<IEdge>> loops = traceEdge(creator.getMaxVertexNum());
	if(loops.size()==0) return null;
	
	IFace fcs[] = new IFace[loops.size()];
	for(int i=0; i<loops.size(); i++){
	    IEdge[] edgs = loops.get(i).toArray(new IEdge[loops.get(i).size()]);
	    fcs[i] = creator.createFace(edgs);
	}
	return fcs;
    }
    
    public IVertex[] subdivide(int divnum, ArrayList<IVertex> newVertexArray, ArrayList<IEdge> newEdgeArray){
	IEdge[] newedges = new IEdge[divnum];
	IVertex[] newvertices = new IVertex[divnum+1];
	
	// original vertices are kept: don't delete
	for(int i=0; i<=divnum; i++){
	    if(i==0){ newvertices[i] = vertices[0]; }
	    else if(i==divnum){ newvertices[i] = vertices[1]; }
	    else{
		newvertices[i] = new IVertex(vertices[0].pos.sum(vertices[1].pos,
								 (double)i/divnum));
	    }
	    
	    if(i>0){ newedges[i-1] = new IEdge(newvertices[i-1], newvertices[i]); }
	}
	
	this.del();
	
	for(int i=0; i<newvertices.length; i++) newVertexArray.add(newvertices[i]);
	for(int i=0; i<newedges.length; i++) newEdgeArray.add(newedges[i]);
	return newvertices;
    }
    
}
    
    
