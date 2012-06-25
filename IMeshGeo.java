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
   Geometry of polygon mesh containing lists of vertices, edges and faces.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IMeshGeo extends IParameterObject implements IMeshI{
    public ArrayList<IVertex> vertices;
    public ArrayList<IEdge> edges;
    public ArrayList<IFace> faces;
    
    public boolean closed=false;
    
    public IMeshGeo(ArrayList<ICurveI> lines){
        init(lines, new IMeshType());
    }
    
    public IMeshGeo(ArrayList<ICurveI> lines, IMeshType creator){
        init(lines, creator);
    }
    
    //public IMeshGeo(ArrayList<IEdge> edges, IMeshType creator){
    //    initWithEdges(edges, creator);
    //}
    
    public IMeshGeo(IVec[][] matrix){ this(matrix,true,new IMeshType()); }
    public IMeshGeo(IVec[][] matrix, boolean triangulateDir){
	this(matrix,triangulateDir,new IMeshType());
    }
    public IMeshGeo(IVec[][] matrix, boolean triangulateDir, IMeshType creator){
        vertices = new ArrayList<IVertex>();
        faces = new ArrayList<IFace>();
        edges = new ArrayList<IEdge>();
	initWithPointMatrix(matrix,matrix.length,matrix[0].length,triangulateDir,creator);
    }
    
    public IMeshGeo(IVec[][] matrix, int unum, int vnum, boolean triangulateDir){
	this(matrix,unum,vnum,triangulateDir,new IMeshType());
    }
    public IMeshGeo(IVec[][] matrix, int unum, int vnum, boolean triangulateDir,
		    IMeshType creator){
        vertices = new ArrayList<IVertex>();
        faces = new ArrayList<IFace>();
        edges = new ArrayList<IEdge>();
	initWithPointMatrix(matrix,unum,vnum,triangulateDir,creator);
    }
    
    public IMeshGeo(){
	super();
        vertices = new ArrayList<IVertex>();
        faces = new ArrayList<IFace>();
        edges = new ArrayList<IEdge>();
    }
    
    public IMeshGeo(ArrayList<IVertex> v, ArrayList<IEdge> e, ArrayList<IFace> f){
	super();
        vertices = v;
        edges = e;
        faces = f;
    }
    
    public IMeshGeo(IVertex[] vtx, IEdge[] edg,IFace[] fcs){
        vertices = new ArrayList<IVertex>();
        faces = new ArrayList<IFace>();
        edges = new ArrayList<IEdge>();
	for(int i=0; i<vtx.length; i++) vertices.add(vtx[i]);
	for(int i=0; i<edg.length; i++) edges.add(edg[i]);
	for(int i=0; i<fcs.length; i++) faces.add(fcs[i]);
    }
    
    
    public IMeshGeo(IVec[] vert){ // single face mesh
	vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
        faces = new ArrayList<IFace>();
	for(int i=0; i<vert.length; i++) vertices.add(new IVertex(vert[i]));
	for(int i=0; i<vert.length; i++)
	    edges.add(new IEdge(vertices.get(i), vertices.get((i+1)%vertices.size())));
	IEdge[] e = new IEdge[edges.size()];
	for(int i=0; i<edges.size(); i++) e[i] = edges.get(i);
	faces.add(new IFace(e));
    }
    
    public IMeshGeo(IVertex[] vert){ // single face mesh
	vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
        faces = new ArrayList<IFace>();
	for(int i=0; i<vert.length; i++) vertices.add(vert[i]);
	for(int i=0; i<vert.length; i++)
	    edges.add(new IEdge(vertices.get(i), vertices.get((i+1)%vertices.size())));
	IEdge[] e = new IEdge[edges.size()];
	for(int i=0; i<edges.size(); i++) e[i] = edges.get(i);
	faces.add(new IFace(e));
    }
    
    public IMeshGeo(IVertex v1, IVertex v2, IVertex v3){
	this(new IVertex[]{ v1, v2, v3 });
    }
    
    public IMeshGeo(IVertex v1, IVertex v2, IVertex v3, IVertex v4){
	this(new IVertex[]{ v1, v2, v3, v4 });
    }
    
    public IMeshGeo(IVecI v1, IVecI v2, IVecI v3){
	this(new IVertex[]{ new IVertex(v1), new IVertex(v2), new IVertex(v3) });
    }
    
    public IMeshGeo(IVecI v1, IVecI v2, IVecI v3, IVecI v4){
	this(new IVertex[]{ new IVertex(v1), new IVertex(v2), new IVertex(v3), new IVertex(v4) });
    }
    
    public IMeshGeo(double x1, double y1, double z1, double x2, double y2, double z2,
		    double x3, double y3, double z3){
	this(new IVertex[]{ new IVertex(x1,y1,z1), new IVertex(x2,y2,z2), new IVertex(x3,y3,z3) });
    }
    
    public IMeshGeo(double x1, double y1, double z1, double x2, double y2, double z2,
		    double x3, double y3, double z3, double x4, double y4, double z4){
	this(new IVertex[]{ new IVertex(x1,y1,z1), new IVertex(x2,y2,z2),
			    new IVertex(x3,y3,z3), new IVertex(x4,y4,z4) });
    }
    
    
    
    public IMeshGeo(IFace[] fcs){
	vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
        faces = new ArrayList<IFace>();
	for(IFace f : fcs){
	    faces.add(f);
	    for(IVertex v : f.vertices) if(!vertices.contains(v)) vertices.add(v);
	    for(IEdge e : f.edges) if(!edges.contains(e)) edges.add(e);
	}
    }
    
    public IMeshGeo(IMeshGeo m){
	// deep copy
	vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
        faces = new ArrayList<IFace>();
	
	for(int i=0; i<m.vertices.size(); i++) vertices.add(m.vertices.get(i).dup());
	for(int i=0; i<m.edges.size(); i++) edges.add(m.edges.get(i).dup());
	for(int i=0; i<m.faces.size(); i++) faces.add(m.faces.get(i).dup());
	
	// re-connect everything
	for(int i=0; i<m.faces.size(); i++) replaceFace(m.faces.get(i), faces.get(i));
	for(int i=0; i<m.edges.size(); i++) replaceEdge(m.edges.get(i), edges.get(i));
	for(int i=0; i<m.vertices.size(); i++) replaceVertex(m.vertices.get(i), vertices.get(i));
	
    }
    
    
    public void init(ArrayList<ICurveI> lines, IMeshType creator){
	
        //boolean fixAllPoints=true; //false;
        
        // pick all points
        vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
	
        for(int i=0; i<lines.size(); i++){
	    
            synchronized(IG.lock){
                ICurveI link = lines.get(i);
		
                IVertex p1 = creator.createVertex(link.start().get());
                IVertex p2 = creator.createVertex(link.end().get());
                		
                IEdge e = creator.createEdge(p1,p2);
		
                edges.add(e);
		
                vertices.add(p1);
                vertices.add(p2);
            }
        }
	
	// sort 
	ISort.sort(vertices, new IVertex.ZYXComparator());
	
        for(int i=0; i<vertices.size(); i++){
            IVertex pt1 = vertices.get(i);
	    
	    // removing duplicated vertices
	    boolean samePos=true;
	    
	    for(int j=i+1; j<vertices.size() && samePos; j++){
		IVertex pt2 = vertices.get(j);
		if(pt1.eq(pt2)){
		    synchronized(IG.lock){
			pt2.replaceVertex(pt1);
			vertices.remove(j);
			j--;
		    }
		}
		else{ samePos=false; }
            }
        }
	
	
	// deleting vertices
	for(int i=vertices.size()-1; i>=0; i--){
	    if(vertices.get(i).edges.size()==0) vertices.remove(i);
	}
	
	// putting index of arrya to local index of v (unexpected use)
        //for(int i=0; i<vertices.size(); i++) vertices.get(i).setIndex(i);
	
        // creating faces
        
        faces = new ArrayList<IFace>();
        for(int i=0; i<edges.size(); i++){
            IEdge e = edges.get(i);
            IFace[] fcs = e.createFace(creator);
            if(fcs!=null){
                for(int j=0; j<fcs.length; j++){
		    
                    synchronized(IG.lock){
                        boolean newface=true;
                        for(int k=0; (k<faces.size())&&newface; k++){
                            IFace f = faces.get(k);
			    //if(f.equals(fcs[j])) newface=false;
			    //if(f.eq(fcs[j])) newface=false;
			    if(f==fcs[j]) newface=false; // correct?
                        }
                        if(newface){ faces.add(fcs[j]); }
                        else fcs[j].del();
                    }
                }
            }
        }
    }
    
    public IMeshGeo get(){ return this; }
    
    public IMeshGeo dup(){ return new IMeshGeo(this); }

    public boolean isValid(){
	if(vertices==null) return false;
	for(int i=0; i<vertices.size(); i++){
	    if(!vertices.get(i).isValid()){
		IOut.err("vertices at "+i+" is invalid");
		return false;
	    }
	}
	return true;
    }
    
    /** For use in copy constructor */
    protected void replaceVertex(IVertex origVertex, IVertex newVertex){
	// vertices
	for(IVertex v:vertices)
	    for(int i=0; i<v.linkedVertices.size(); i++)
		if(v.linkedVertices.get(i) == origVertex)
		    v.linkedVertices.set(i,newVertex);
	// edges
	for(IEdge e:edges)
	    for(int i=0; i<e.vertices.length; i++)
		if(e.vertices[i] == origVertex) e.vertices[i] = newVertex;
	// faces
	for(IFace f:faces)
	    for(int i=0; i<f.vertices.length; i++)
		if(f.vertices[i] == origVertex) f.vertices[i] = newVertex;
    }
    
    /** For use in copy constructor */
    protected void replaceEdge(IEdge origEdge, IEdge newEdge){
	// vertices
	for(IVertex v:vertices)
	    for(int i=0; i<v.edges.size(); i++)
		if(v.edges.get(i) == origEdge) v.edges.set(i,newEdge);
	// faces
	for(IFace f:faces)
	    for(int i=0; i<f.edges.length; i++)
		if(f.edges[i] == origEdge) f.edges[i] = newEdge;
    }
    
    /** For use in copy constructor */
    protected void replaceFace(IFace origFace, IFace newFace){
	// vertices
	for(IVertex v:vertices)
	    for(int i=0; i<v.faces.size(); i++)
		if(v.faces.get(i) == origFace) v.faces.set(i,newFace);
	// edges
	for(IEdge e:edges)
	    for(int i=0; i<e.faces.size(); i++)
		if(e.faces.get(i) == origFace) e.faces.set(i,newFace);
    }
    
    
    static IMeshGeo createMeshWithEdges(ArrayList<IEdge> edges, IMeshType creator){
	IMeshGeo mesh = new IMeshGeo();
	mesh.initWithEdges(edges,creator);
	return mesh;
    }
    
    public void initWithEdges(ArrayList<IEdge> edges, IMeshType creator){
	
	for(int i=0; i<edges.size(); i++){
	    if(!vertices.contains(edges.get(i).vertices[0]))
		vertices.add(edges.get(i).vertices[0]);
	    if(!vertices.contains(edges.get(i).vertices[1]))
		vertices.add(edges.get(i).vertices[1]);
	}
        
        // pick all points
        ArrayList<IVertex> vertices = new ArrayList<IVertex>();
	
	for(int i=0; i<edges.size(); i++){
	    if(!vertices.contains(edges.get(i).vertices[0]))
		vertices.add(edges.get(i).vertices[0]);
	    if(!vertices.contains(edges.get(i).vertices[1]))
		vertices.add(edges.get(i).vertices[1]);
	}
		
	// putting index of arrya to local index (unexpected use)
        //for(int i=0; i<vertices.size(); i++) vertices.get(i).setIndex(i);
	
	
	faces = new ArrayList<IFace>();
        for(int i=0; i<edges.size(); i++){
            IEdge e = edges.get(i);
	    
            IFace[] fcs = e.createFace(creator);
            if(fcs!=null){
                for(int j=0; j<fcs.length; j++){
		    
                    synchronized(IG.lock){
                        boolean newface=true;
                        for(int k=0; (k<faces.size())&&newface; k++){
                            IFace f = faces.get(k);
			    //if(f.equals(fcs[j])) newface=false;
			    //if(f.eq(fcs[j])) newface=false;
			    if(f ==fcs[j]) newface=false; // correct?
                        }
                        if(newface){ faces.add(fcs[j]); }
                        else fcs[j].del();
                    }
                }
            }
        }
	
    }
    
    
    public void initWithPointMatrix(IVec[][] matrix,
				    int unum, int vnum,
				    boolean triangulateDir,
				    IMeshType creator){
	
	IVertex[][] vmatrix = new IVertex[unum][vnum];
	
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum; j++){
		vmatrix[i][j] = creator.createVertex(matrix[i][j]);
		vertices.add(vmatrix[i][j]);
	    }
	}
	
	IEdge[][] ematrix1 =  new IEdge[unum-1][vnum];
	IEdge[][] ematrix2 =  new IEdge[unum][vnum-1];
	
	for(int i=0; i<unum-1; i++){
	    for(int j=0; j<vnum; j++){
		ematrix1[i][j] = creator.createEdge(vmatrix[i][j],vmatrix[i+1][j]);
		edges.add(ematrix1[i][j]);
	    }
	}
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum-1; j++){
		ematrix2[i][j] = creator.createEdge(vmatrix[i][j],vmatrix[i][j+1]);
		edges.add(ematrix2[i][j]);
	    }
	}
	
	IEdge[] triEdges = new IEdge[3];
	for(int i=0; i<unum-1; i++){
	    for(int j=0; j<vnum-1; j++){
		if(triangulateDir){
		    IEdge diagonal = creator.createEdge(vmatrix[i][j], vmatrix[i+1][j+1]);
		    edges.add(diagonal);
		    
		    triEdges[0] = ematrix1[i][j];
		    triEdges[1] = diagonal;
		    triEdges[2] = ematrix2[i+1][j];
		    faces.add(creator.createFace(triEdges));
		    
		    triEdges[0] = ematrix2[i][j];
		    triEdges[1] = ematrix1[i][j+1];
		    triEdges[2] = diagonal;
		    faces.add(creator.createFace(triEdges));
		}
		else{
		    IEdge diagonal = creator.createEdge(vmatrix[i+1][j], vmatrix[i][j+1]);
		    edges.add(diagonal);
		    
		    triEdges[0] = ematrix1[i][j];
		    triEdges[1] = ematrix2[i][j];
		    triEdges[2] = diagonal;
		    faces.add(creator.createFace(triEdges));
		    
		    triEdges[0] = ematrix1[i][j+1];
		    triEdges[1] = ematrix2[i+1][j];
		    triEdges[2] = diagonal;
		    faces.add(creator.createFace(triEdges));
		}
	    }
	}
    }
    
    
    
    
    public int vertexNum(){ return vertices.size(); }
    public int edgeNum(){ return edges.size(); }
    public int faceNum(){ return faces.size(); }    
    
    public int vertexNum(ISwitchE e){ return vertexNum(); }
    public int edgeNum(ISwitchE e){ return edgeNum(); }
    public int faceNum(ISwitchE e){ return faceNum(); }
    
    public IInteger vertexNum(ISwitchR r){ return new IInteger(vertexNum()); }
    public IInteger edgeNum(ISwitchR r){ return new IInteger(edgeNum()); }
    public IInteger faceNum(ISwitchR r){ return new IInteger(faceNum()); }
    
    
    public IVertex vertex(int i){ return vertices.get(i); }
    public IEdge edge(int i){ return edges.get(i); }
    public IFace face(int i){ return faces.get(i); }
    
    public IVertex vertex(IIntegerI i){ return vertices.get(i.x()); }
    public IEdge edge(IIntegerI i){ return edges.get(i.x()); }
    public IFace face(IIntegerI i){ return faces.get(i.x()); }
    
    
    public void deleteVertex(int i){ vertices.get(i).del(); vertices.remove(i); }
    public void deleteEdge(int i){ edges.get(i).del(); edges.remove(i); }
    public void deleteFace(int i){ faces.get(i).del(); faces.remove(i); }
    
    public int getIndex(IVertex v){
        for(int i=0; i<vertices.size(); i++) if(vertices.get(i) == v) return i; 
        return -1;
    }
    
    public int getIndex(IEdge e){
        for(int i=0; i<edges.size(); i++) if(edges.get(i) ==e) return i; 
        return -1;
    }
    
    public int getIndex(IFace f){
        for(int i=0; i<faces.size(); i++) if(faces.get(i)==f) return i; 
        return -1;
    }
    
    
    public void addFace(IFace f){
        if(!faces.contains(f)){ faces.add(f); }
        
        for(int i=0; i<f.edges.length; i++){
            if(!edges.contains(f.edges[i])){ edges.add(f.edges[i]); }
        }
        
        for(int i=0; i<f.vertices.length; i++){
            if(!vertices.contains(f.vertices[i])){ vertices.add(f.vertices[i]); }
        }
    }
    
    public void addFace(IFace f, boolean checkVertexExisting, boolean checkEdgeExisting, boolean checkFaceExisting){
	for(int i=0; i<f.vertices.length; i++){
	    if(!checkVertexExisting || !vertices.contains(f.vertices[i])) vertices.add(f.vertices[i]);
	}
	for(int i=0; i<f.edges.length; i++){
	    if(!checkEdgeExisting || !edges.contains(f.edges[i])) edges.add(f.edges[i]);
	}
	if(!checkFaceExisting || !faces.contains(f)) faces.add(f);
    }
    
    
    // OpenGL way of adding mesh faces
    public void addTriangles(IVertex[] v){
	for(int i=0; i<v.length-2; i+=3){
	    IFace f = new IFace(v[i],v[i+1],v[i+2]);
	    addFace(f, true, false, false);
	}
    }
    public void addTriangles(IVec[] v){
	for(int i=0; i<v.length-2; i+=3){
	    IFace f = new IFace(new IVertex(v[i]),new IVertex(v[i+1]), new IVertex(v[i+2]));
	    addFace(f, false, false, false);
	}
    }
    public void addQuads(IVertex[] v){
	for(int i=0; i<v.length-3; i+=4){
	    IFace f = new IFace(v[i],v[i+1],v[i+2],v[i+3]);
	    addFace(f, true, false, false);
	}
    }
    public void addQuads(IVec[] v){
	for(int i=0; i<v.length-3; i+=4){
	    IFace f = new IFace(new IVertex(v[i]), new IVertex(v[i+1]), new IVertex(v[i+2]), new IVertex(v[i+3]));
	    addFace(f, true, false, false);
	}
    }
    public void addPolygon(IVertex[] v){
	if(v.length<3) return;
	IFace f = new IFace(v);
	addFace(f, true, false, false);
    }
    public void addPolygon(IVec[] v){
	if(v.length<3) return;
	IVertex[] vtx = new IVertex[v.length];
	for(int i=0; i<v.length; i++) vtx[i] = new IVertex(v[i]);
	IFace f = new IFace(vtx);
	addFace(f, false, false, false);
    }
    public void addTriangleStrip(IVertex[] v){
	if(v.length<3) return;
	
	for(int i=0; i<v.length; i++) if(!vertices.contains(v[i])) vertices.add(v[i]);
	
	IEdge[] edges1 = new IEdge[v.length-1];
	IEdge[] edges2 = new IEdge[v.length-2];
	
	for(int i=0; i<v.length-1; i++){ edges1[i] = new IEdge(v[i],v[i+1]); edges.add(edges1[i]); }
	for(int i=0; i<v.length-2; i++){ edges2[i] = new IEdge(v[i],v[i+2]); edges.add(edges2[i]); }
	
	for(int i=0; i<v.length-2; i++){
	    IFace f;
	    if(i%2==0) f = new IFace(edges1[i], edges1[i+1], edges2[i]);
	    else f = new IFace(edges1[i], edges2[i], edges1[i+1]);
	    faces.add(f);
	}
    }
    
    public void addTriangleStrip(IVec[] v){
	if(v.length<3) return;
	IVertex[] vtx = new IVertex[v.length];
	for(int i=0; i<v.length; i++){ vtx[i] = new IVertex(v[i]); vertices.add(vtx[i]); }
	
	IEdge[] edges1 = new IEdge[v.length-1];
	IEdge[] edges2 = new IEdge[v.length-2];
	
	for(int i=0; i<v.length-1; i++){ edges1[i] = new IEdge(vtx[i],vtx[i+1]); edges.add(edges1[i]); }
	for(int i=0; i<v.length-2; i++){ edges2[i] = new IEdge(vtx[i],vtx[i+2]); edges.add(edges2[i]); }
	
	for(int i=0; i<v.length-2; i++){
	    IFace f;
	    if(i%2==0) f = new IFace(edges1[i], edges1[i+1], edges2[i]);
	    else f = new IFace(edges1[i], edges2[i], edges1[i+1]);
	    faces.add(f);
	}
    }
    public void addQuadStrip(IVertex[] v){
	if(v.length<4) return;
	int num = v.length/2;
	for(int i=0; i<num*2; i++) if(!vertices.contains(v[i])) vertices.add(v[i]);
	
	IEdge[] edges1 = new IEdge[num];
	IEdge[] edges2 = new IEdge[num-1];
	IEdge[] edges3 = new IEdge[num-1];
	
	for(int i=0; i<num; i++){ edges1[i] = new IEdge(v[i*2],v[i*2+1]); edges.add(edges1[i]); }
	for(int i=0; i<num-1; i++){ edges2[i] = new IEdge(v[i*2],v[i*2+2]); edges.add(edges2[i]); }
	for(int i=0; i<num-1; i++){ edges3[i] = new IEdge(v[i*2+1],v[i*2+3]); edges.add(edges3[i]); }
	
	for(int i=0; i<num-1; i++){
	    IFace f = new IFace(edges1[i], edges3[i], edges1[i+1], edges2[i]);
	    faces.add(f);
	}
    }
    public void addQuadStrip(IVec[] v){
	if(v.length<4) return;
	int num = v.length/2;
	IVertex[] vtx = new IVertex[num*2];
	for(int i=0; i<num*2; i++){ vtx[i] = new IVertex(v[i]); vertices.add(vtx[i]); }
	
	IEdge[] edges1 = new IEdge[num];
	IEdge[] edges2 = new IEdge[num-1];
	IEdge[] edges3 = new IEdge[num-1];
	
	for(int i=0; i<num; i++){ edges1[i] = new IEdge(vtx[i*2],vtx[i*2+1]); edges.add(edges1[i]); }
	for(int i=0; i<num-1; i++){ edges2[i] = new IEdge(vtx[i*2],vtx[i*2+2]); edges.add(edges2[i]); }
	for(int i=0; i<num-1; i++){ edges3[i] = new IEdge(vtx[i*2+1],vtx[i*2+3]); edges.add(edges3[i]); }
	
	for(int i=0; i<num-1; i++){
	    IFace f = new IFace(edges1[i], edges3[i], edges1[i+1], edges2[i]);
	    faces.add(f);
	}
    }
    public void addTriangleFan(IVertex[] v){
	if(v.length<3) return;
	
	for(int i=0; i<v.length; i++) if(!vertices.contains(v[i])) vertices.add(v[i]);
	
	IEdge[] edges1 = new IEdge[v.length-1];
	IEdge[] edges2 = new IEdge[v.length-2];
	for(int i=1; i<v.length; i++){ edges1[i-1] = new IEdge(v[0], v[i]); edges.add(edges1[i-1]); }
	for(int i=1; i<v.length-1; i++){ edges2[i-1] = new IEdge(v[i], v[i+1]); edges.add(edges2[i-1]); }
	
	for(int i=0; i<v.length-2; i++){
	    IFace f = new IFace(edges1[i], edges2[i], edges1[i+1]);
	    faces.add(f);
	}
    }
    public void addTriangleFan(IVec[] v){
	if(v.length<3) return;
	IVertex[] vtx = new IVertex[v.length];
	for(int i=0; i<v.length; i++){ vtx[i] = new IVertex(v[i]); vertices.add(vtx[i]); }
	
	IEdge[] edges1 = new IEdge[v.length-1];
	IEdge[] edges2 = new IEdge[v.length-2];
	for(int i=1; i<v.length; i++){ edges1[i-1] = new IEdge(vtx[0], vtx[i]); edges.add(edges1[i-1]); }
	for(int i=1; i<v.length-1; i++){ edges2[i-1] = new IEdge(vtx[i], vtx[i+1]); edges.add(edges2[i-1]); }
	
	for(int i=0; i<v.length-2; i++){
	    IFace f = new IFace(edges1[i], edges2[i], edges1[i+1]);
	    faces.add(f);
	}
    }
    
    
    // returns actual inserted vertex
    public IVertex insertVertex(IFace f, IVertex v, IMeshType creator){
	
	// check vertex
	for(int i=0; i<f.vertices.length; i++){
	    if(f.vertices[i].pos.eq(v.pos)) return f.vertices[i];
	}
	
	// check edges
	IEdge onEdge=null;
	int onEdgeIdx=-1;
	
	for(int i=0; i<f.edges.length && onEdge==null; i++)
	    if(f.edges[i].isOnEdge(v)){ onEdge=f.edges[i]; onEdgeIdx=i; }
	
	ArrayList<IEdge> onEdgeEdges=new ArrayList<IEdge>();
	
	int num = f.edges.length;
	IEdge[] newEdges = new IEdge[num];
	for(int i=0; i<num; i++){
	    IVertex v1 = f.edges[i].getSharingVertex(f.edges[(i+1)%num]);
	    newEdges[i] = creator.createEdge(v1,v);
	    if(onEdgeIdx>=0){
		if(i==onEdgeIdx || i+1==onEdgeIdx) onEdgeEdges.add(newEdges[i]);
	    }
	}
	
	IFace[] newFaces = new IFace[num];
	for(int i=0; i<num; i++){
	    if(i!=onEdgeIdx){
		IEdge[] e = new IEdge[3];
		e[0] = f.edges[i];
		e[1] = newEdges[i];
		e[2] = newEdges[(i-1+num)%num];
		newFaces[i] = creator.createFace(e);
	    }
	}
	
	if(!vertices.contains(v)) vertices.add(v);
	
	for(int i=0; i<num; i++){
	    edges.add(newEdges[i]);
	    if(newFaces[i]!=null) faces.add(newFaces[i]);
	}
	
	f.del();
	faces.remove(f);
	
	if(onEdge!=null){
	    //if(onEdge.faces.size()==0){ onEdge.del(); edges.remove(onEdge); }
	    if(onEdgeEdges.size()!=2){
		IOut.err("new edges for on-edge insertion point cannot be found");
	    }
	    for(int i=0; i<onEdge.faces.size(); i++){
		replaceEdge(onEdge.faces.get(i), onEdge,
			    onEdgeEdges.get(0), onEdgeEdges.get(1), v, creator);
	    }
	    onEdge.del();
	    edges.remove(onEdge); 
	}
	return v;
    }
    
    
    public void replaceEdge(IFace f, IEdge oldEdge, IEdge newEdge1, IEdge newEdge2,
			    IVertex vertexOnEdge, IMeshType creator){
	int edgeIdx = f.indexOf(oldEdge);
	
	if(edgeIdx<0){
	    IOut.err("specified edge is not included in the face");
	    return;
	}
	
	IVertex v1 = newEdge1.getOtherVertex(vertexOnEdge);
	IVertex v2 = newEdge2.getOtherVertex(vertexOnEdge);
	
	int num = f.edges.length;
	IEdge[] newEdges = new IEdge[num];
	for(int i=0; i<num; i++){
	    IVertex v = f.edges[i].getSharingVertex(f.edges[(i+1)%num]);
	    if(v==v1) newEdges[i] = newEdge1;
	    else if(v==v2) newEdges[i] = newEdge2;
	    else newEdges[i] = creator.createEdge(v,vertexOnEdge);
	}
	
	IFace[] newFaces = new IFace[num];
	for(int i=0; i<num; i++){
	    if(i!=edgeIdx){
		IEdge[] e = new IEdge[3];
		e[0] = f.edges[i];
		e[1] = newEdges[i];
		e[2] = newEdges[(i-1+num)%num];
		newFaces[i] = creator.createFace(e);
	    }
	}
	
	// usually already added in insertVertex
	if(!vertices.contains(vertexOnEdge)) vertices.add(vertexOnEdge);
	
	for(int i=0; i<num; i++){
	    if(newEdges[i]!=newEdge1 && newEdges[i]!=newEdge2) edges.add(newEdges[i]);
	    if(newFaces[i]!=null) faces.add(newFaces[i]);
	}
	
	f.del();
	faces.remove(f);
    }
    
    
    
    // ratio:0-1: 0 -> e.vertices[0], 1->e.vertices[1]
    public void divideEdge(IEdge e, double ratio, IMeshType creator){
	
	IVertex v1 = e.vertices[0];
	IVertex v2 = e.vertices[1];
	IVertex v = creator.createVertex(v2.pos.dup().get().sum(v1.pos,1.-ratio));
	
	IEdge ne1 = creator.createEdge(v1, v);
	IEdge ne2 = creator.createEdge(v, v2);
	
	vertices.add(v);
	edges.add(ne1);
	edges.add(ne2);
	
	for(int i=0; i<e.faces.size(); i++){
	    IVertex v3 = e.faces.get(i).getOtherVertex(v1,v2);
	    if(v3!=null){
		IEdge me = creator.createEdge(v3, v);
		edges.add(me);
		
		IEdge e1 = e.faces.get(i).getEdge(v3,v1);
		IEdge e2 = e.faces.get(i).getEdge(v3,v2);
		
		IEdge[] es = new IEdge[3];
		es[0] = e1;
		es[1] = ne1;
		es[2] = me;
		IFace f1 = creator.createFace(es);
		
		es[0] = e2;
		es[1] = ne2;
		es[2] = me;
		IFace f2 = creator.createFace(es);
		
		faces.add(f1);
		faces.add(f2);
	    }
	    else{
		IOut.err("no opposite vertex!"); //
	    }
	}

	for(int i=0; i<e.faces.size(); i++){
	    faces.remove(e.faces.get(i));
	    e.faces.get(i).del();
	}
	
	edges.remove(e);
	e.del();
	// edge also delete connected faces
    }
    
    
    // ratio:0-1: 0 -> e.vertices[0], 1->e.vertices[1]
    public void divideFace(IFace f, IEdge e1, IVertex nv1, IEdge e2, IVertex nv2,
			   IMeshType creator){
	
	if( !f.contains(e1) || !f.contains(e2) ){
	    IOut.err("edges are not included in the face");
	    return;
	}
	
	IEdge ne = creator.createEdge(nv1,nv2);
	
	IEdge ne11 = creator.createEdge(e1.vertices[0], nv1);
	IEdge ne12 = creator.createEdge(nv1, e1.vertices[1]);

	
	IEdge ne21 = creator.createEdge(e2.vertices[0], nv2);
	IEdge ne22 = creator.createEdge(nv2, e2.vertices[1]);
	
	
	int e1idx = f.indexOf(e1);
	int e2idx = f.indexOf(e2);
	if(e1idx<0 || e2idx<0){
	    IOut.err("edges are not included in the face"); //
	    return;
	}	
	
	ArrayList<IEdge> edges1=new ArrayList<IEdge>();
	ArrayList<IEdge> edges2=new ArrayList<IEdge>();
	
	int num = f.edges.length;
	int i;
	
	edges1.add(ne);
	if(f.edges[ (e1idx+1)%num ].isSharingVertex(ne11) ) edges1.add(ne11);
	else if(f.edges[ (e1idx+1)%num ].isSharingVertex(ne12) ) edges1.add(ne12);
	
	//for(int i=e1idx+1; i<e2idx; i++) edges1.add(f.edges.get(i));
	i=e1idx+1;
	while(i%num != e2idx){ edges1.add(f.edges[i%num]); i++; }
	
	if(f.edges[ (e2idx-1+num)%num ].isSharingVertex(ne11) ) edges1.add(ne11);
	else if(f.edges[ (e2idx-1+num)%num ].isSharingVertex(ne12) ) edges1.add(ne12);
	
	edges2.add(ne);
	if(f.edges[ (e2idx+1)%num ].isSharingVertex(ne21) ) edges2.add(ne21);
	else if(f.edges[ (e2idx+1)%num ].isSharingVertex(ne22) ) edges2.add(ne22);
	
	i=e2idx+1;
	while(i%num != e1idx){ edges2.add(f.edges[i%num]); i++; }
	
	if(f.edges[ (e1idx-1+num)%num ].isSharingVertex(ne21) ) edges2.add(ne21);
	else if(f.edges[ (e1idx-1+num)%num ].isSharingVertex(ne22) ) edges2.add(ne22);
	
	
	IEdge[] edgeArray1 = new IEdge[edges1.size()];
	for(i=0; i<edges1.size(); i++) edgeArray1[i] = edges1.get(i);
	
	IEdge[] edgeArray2 = new IEdge[edges2.size()];
	for(i=0; i<edges2.size(); i++) edgeArray2[i] = edges2.get(i);
	
	
	IFace nf1 = creator.createFace(edgeArray1);
	IFace nf2 = creator.createFace(edgeArray2);
	
	faces.add(nf1);
	faces.add(nf2);
	
	faces.remove(f);
	f.del();
	
	// keep original edge for adjacent face
	if(e1.faces.size()==0){ edges.remove(e1); e1.del(); }
	if(e2.faces.size()==0){ edges.remove(e2); e2.del(); }
	
    }
    
    
    public void triangulate(IFace f, boolean triangulateDirection, IMeshType creator){
	
	ArrayList<IFace> newFaces = new ArrayList<IFace>();
	ArrayList<IEdge> newEdges = new ArrayList<IEdge>();
	
	ArrayList<Object> retval = f.triangulate(triangulateDirection, creator);
	    
	for(int i=0; i<retval.size(); i++){
	    if(retval.get(i) instanceof IEdge) newEdges.add((IEdge)retval.get(i));
	    else if(retval.get(i) instanceof IFace) newFaces.add((IFace)retval.get(i));
	}
	
	f.del();
	faces.remove(f);
	
	for(int i=0; i<newEdges.size(); i++) edges.add(newEdges.get(i));
	for(int i=0; i<newFaces.size(); i++) faces.add(newFaces.get(i));
    }
    
    public void triangulateAll(boolean triangulateDirection, IMeshType creator){
	
	ArrayList<IFace> newFaces = new ArrayList<IFace>();
	ArrayList<IEdge> newEdges = new ArrayList<IEdge>();
	
	for(int i=0; i<faces.size(); i++){
	    ArrayList<Object> retval = faces.get(i).triangulate(triangulateDirection, creator);
	    for(int j=0; retval!=null&&j<retval.size(); j++){
		if(retval.get(j) instanceof IEdge)
		    newEdges.add((IEdge)retval.get(j));
		else if(retval.get(j) instanceof IFace)
		    newFaces.add((IFace)retval.get(j));
	    }
	    
	    if(retval!=null){
		faces.get(i).del();
		faces.remove(i);
		i--;
	    }
	}
	
	for(int i=0; i<newEdges.size(); i++) edges.add(newEdges.get(i));
	for(int i=0; i<newFaces.size(); i++) faces.add(newFaces.get(i));
    }
    
    
    public void triangulateAtCenter(IMeshType creator){
	
	ArrayList<IFace> newFaces = new ArrayList<IFace>();
	ArrayList<IEdge> newEdges = new ArrayList<IEdge>();
	ArrayList<IVertex> newVertices = new ArrayList<IVertex>();
	
	for(int i=0; i<faces.size(); i++){
	    ArrayList<Object> retval = faces.get(i).triangulateAtCenter(creator);
	    for(int j=0; retval!=null&&j<retval.size(); j++){
		if(retval.get(j) instanceof IVertex)
		    newVertices.add((IVertex)retval.get(j));
		if(retval.get(j) instanceof IEdge)
		    newEdges.add((IEdge)retval.get(j));
		else if(retval.get(j) instanceof IFace)
		    newFaces.add((IFace)retval.get(j));
	    }
	    
	    if(retval!=null){
		faces.get(i).del();
		faces.remove(i);
		i--;
	    }
	}

	for(int i=0; i<newVertices.size();i++)vertices.add(newVertices.get(i));
	for(int i=0; i<newEdges.size(); i++) edges.add(newEdges.get(i));
	for(int i=0; i<newFaces.size(); i++) faces.add(newFaces.get(i));
	
    }
    
    // return: min, max
    public IVec[] getBoundingBox(){
	IVec min=new IVec(vertices.get(0));
	IVec max=new IVec(vertices.get(0));
	
	for(int i=1; i<vertices.size(); i++){
	    IVec v = vertices.get(i).pos.get();
	    if(v.x<min.x) min.x=v.x; if(v.x>max.x) max.x=v.x;
	    if(v.y<min.y) min.y=v.y; if(v.y>max.y) max.y=v.y;
	    if(v.z<min.z) min.z=v.z; if(v.z>max.z) max.z=v.z;
	}
	
	IVec[] retval = new IVec[2];
	retval[0]=min; retval[1]=max;
	return retval;
    }
    
    
    public static IMeshGeo joinMesh(IMeshGeo[] meshes){
	ArrayList<IFace> faces = new ArrayList<IFace>();
	for(int i=0; i<meshes.length; i++){
	    for(int j=0; j<meshes[i].faceNum(); j++){ faces.add(meshes[i].face(j)); }
	}
	IFace[] fcs = new IFace[faces.size()];
	for(int i=0; i<faces.size(); i++) fcs[i] = faces.get(i);
	return new IMeshGeo(fcs);
    }
    

    /** only setting value to closed. checking no connection of mesh */
    public IMeshGeo close(){ closed=true; return this; } 
    public boolean isClosed(){ return closed; }
    
    
    
    /*************************************************
     * ITransformable methods
     ************************************************/


    public IMeshGeo add(double x, double y, double z){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).add(x,y,z);
	return this;
    }
    public IMeshGeo add(IDoubleI x, IDoubleI y, IDoubleI z){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).add(x,y,z);
	return this;
    }
    public IMeshGeo add(IVecI v){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).add(v);
	return this;
    }
    public IMeshGeo sub(double x, double y, double z){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).sub(x,y,z);
	return this;
    }
    public IMeshGeo sub(IDoubleI x, IDoubleI y, IDoubleI z){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).sub(x,y,z);
	return this;
    }
    public IMeshGeo sub(IVecI v){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).sub(v);
	return this;
    }
    public IMeshGeo mul(IDoubleI v){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).mul(v);
	return this;
    }
    public IMeshGeo mul(double v){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).mul(v);
	return this;
    }
    public IMeshGeo div(IDoubleI v){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).div(v);
	return this;
    }
    public IMeshGeo div(double v){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).div(v);
	return this;
    }
    
    public IMeshGeo neg(){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).neg();
	return this;
    }
    /** alias of neg */
    public IMeshGeo rev(){ return neg(); }
    /** alias of neg */
    public IMeshGeo flip(){ return neg(); }
    
    
    
    /** scale add */
    public IMeshGeo add(IVecI v, double f){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).add(v,f);
	return this;
    }
    /** scale add */
    public IMeshGeo add(IVecI v, IDoubleI f){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).add(v,f);
	return this;
    }
    /** scale add alias */
    public IMeshGeo add(double f, IVecI v){ return add(v,f); }
    /** scale add alias */
    public IMeshGeo add(IDoubleI f, IVecI v){ return add(v,f); }
    
    /** rotation around z-axis and origin */
    public IMeshGeo rot(IDoubleI angle){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot(angle);
	return this;
    }
    public IMeshGeo rot(double angle){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot(angle);
	return this;
    }
    
    /** rotation around axis vector */
    public IMeshGeo rot(IVecI axis, IDoubleI angle){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot(axis,angle);
	return this;
    }
    public IMeshGeo rot(IVecI axis, double angle){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot(axis,angle);
	return this;
    }
    
    /** rotation around axis vector and center */
    public IMeshGeo rot(IVecI center, IVecI axis, IDoubleI angle){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot(center,axis,angle);
	return this;
    }
    public IMeshGeo rot(IVecI center, IVecI axis, double angle){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot(center,axis,angle);
	return this;
    }
    
    /** rotate to destination direction vector */
    public IMeshGeo rot(IVecI axis, IVecI destDir){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot(axis,destDir);
	return this;
    }
    /** rotate to destination point location */    
    public IMeshGeo rot(IVecI center, IVecI axis, IVecI destPt){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot(center,axis,destPt);
	return this;
    }
    
    
    /** rotation on xy-plane around origin; same with rot(IDoubleI) */
    public IMeshGeo rot2(IDoubleI angle){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot2(angle);
	return this;
    }
    /** rotation on xy-plane around origin; same with rot(double) */
    public IMeshGeo rot2(double angle){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot2(angle);
	return this;
    }
    
    /** rotation on xy-plane around center */
    public IMeshGeo rot2(IVecI center, IDoubleI angle){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot2(center,angle);
	return this;
    }
    public IMeshGeo rot2(IVecI center, double angle){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot2(center,angle);
	return this;
    }
    
    /** rotation on xy-plane to destination direction vector */
    public IMeshGeo rot2(IVecI destDir){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot2(destDir);
	return this;
    }
    /** rotation on xy-plane to destination point location */    
    public IMeshGeo rot2(IVecI center, IVecI destPt){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).rot2(center,destPt);
	return this;
    }
    
    
    
    /** alias of mul */
    public IMeshGeo scale(IDoubleI f){ return mul(f); }
    public IMeshGeo scale(double f){ return mul(f); }
    public IMeshGeo scale(IVecI center, IDoubleI f){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).scale(center,f);
	return this;
    }
    public IMeshGeo scale(IVecI center, double f){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).scale(center,f);
	return this;
    }
    
    
    /** scale only in 1 direction */
    public IMeshGeo scale1d(IVecI axis, double f){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).scale1d(axis,f);
	return this;
    }
    public IMeshGeo scale1d(IVecI axis, IDoubleI f){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).scale1d(axis,f);
	return this;
    }
    public IMeshGeo scale1d(IVecI center, IVecI axis, double f){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).scale1d(center,axis,f);
	return this;
    }
    public IMeshGeo scale1d(IVecI center, IVecI axis, IDoubleI f){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).scale1d(center,axis,f);
	return this;
    }
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public IMeshGeo ref(IVecI planeDir){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).ref(planeDir);
	return this;
    }
    public IMeshGeo ref(IVecI center, IVecI planeDir){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).ref(center,planeDir);
	return this;
    }
    /** mirror is alias of ref */
    public IMeshGeo mirror(IVecI planeDir){ return ref(planeDir); }
    public IMeshGeo mirror(IVecI center, IVecI planeDir){ return ref(center,planeDir); }
    
    
    /** shear operation */
    public IMeshGeo shear(double sxy, double syx, double syz,
			  double szy, double szx, double sxz){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IMeshGeo shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			  IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IMeshGeo shear(IVecI center, double sxy, double syx, double syz,
			  double szy, double szx, double sxz){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public IMeshGeo shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			  IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shear(center,sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    public IMeshGeo shearXY(double sxy, double syx){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearXY(sxy,syx);
	return this;
    }
    public IMeshGeo shearXY(IDoubleI sxy, IDoubleI syx){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearXY(sxy,syx);
	return this;
    }
    public IMeshGeo shearXY(IVecI center, double sxy, double syx){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearXY(center,sxy,syx);
	return this;
    }
    public IMeshGeo shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearXY(center,sxy,syx);
	return this;
    }
    
    public IMeshGeo shearYZ(double syz, double szy){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearYZ(syz,szy);
	return this;
    }
    public IMeshGeo shearYZ(IDoubleI syz, IDoubleI szy){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearYZ(syz,szy);
	return this;
    }
    public IMeshGeo shearYZ(IVecI center, double syz, double szy){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearYZ(center,syz,szy);
	return this;
    }
    public IMeshGeo shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearYZ(center,syz,szy);
	return this;
    }
    
    public IMeshGeo shearZX(double szx, double sxz){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearZX(szx,sxz);
	return this;
    }
    public IMeshGeo shearZX(IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearZX(szx,sxz);
	return this;
    }
    public IMeshGeo shearZX(IVecI center, double szx, double sxz){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearZX(center,szx,sxz);
	return this;
    }
    public IMeshGeo shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).shearZX(center,szx,sxz);
	return this;
    }
    
    /** mv() is alias of add() */
    public IMeshGeo mv(double x, double y, double z){ return add(x,y,z); }
    public IMeshGeo mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IMeshGeo mv(IVecI v){ return add(v); }
    
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IMeshGeo cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IMeshGeo cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IMeshGeo cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IMeshGeo cp(IVecI v){ return dup().add(v); }
    
    
    /** translate() is alias of add() */
    public IMeshGeo translate(double x, double y, double z){
	// debug
	/*
	for(int i=0; i<vertices.size(); i++){
	    for(int j=0; j<vertices.size(); j++){
		if(i!=j){
		    if(vertices.get(i) == vertices.get(j)){ IOut.err("over wrap: "+i+" - "+j); }
		}
	    }
	}
	*/
	return add(x,y,z);
	
    }
    public IMeshGeo translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IMeshGeo translate(IVecI v){ return add(v); }
    
    
    public IMeshGeo transform(IMatrix3I mat){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).transform(mat);
	return this;
    }
    public IMeshGeo transform(IMatrix4I mat){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).transform(mat);
	return this;
    }
    public IMeshGeo transform(IVecI xvec, IVecI yvec, IVecI zvec){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).transform(xvec,yvec,zvec);
	return this;
    }
    public IMeshGeo transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	for(int i=0; i<vertices.size(); i++) vertices.get(i).transform(xvec,yvec,zvec,translate);
	return this;
    }

}
