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

/**
   Geometry of polygon mesh containing lists of vertices, edges and faces.
   
   @author Satoru Sugihara
*/
public class IMeshGeo extends IParameterObject implements IMeshI{
    public ArrayList<IVertex> vertices;
    public ArrayList<IEdge> edges;
    public ArrayList<IFace> faces;

    /** just setting boolean value to closed. checking no connection of mesh. used to set closed flag in saving */
    public boolean closed=false;
    
    //public IMeshGeo(ArrayList<ICurveI> lines){ initWithLines(lines, new IMeshType()); }
    //public IMeshGeo(ArrayList<ICurveI> lines, IMeshType creator){ initWithLines(lines, creator); }
    
    public IMeshGeo(ICurveI[] lines){ initWithLines(lines, new IMeshType()); }
    public IMeshGeo(ICurveI[] lines, IMeshType creator){ initWithLines(lines, creator); }
    
    
    /** takes surfaces as face with 3 or 4 points face. trim is ignored*/
    public IMeshGeo(ISurfaceI[] faces){ initWithSurfaces(faces, new IMeshType()); }
    /** takes surfaces as face with 3 or 4 points face. trim is ignored*/
    public IMeshGeo(ISurfaceI[] faces, IMeshType creator){ initWithSurfaces(faces, creator); }
    
    
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
	super();
        vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
        faces = new ArrayList<IFace>();
	for(int i=0; i<vtx.length; i++) addVertex(vtx[i]); //vertices.add(vtx[i]);
	for(int i=0; i<edg.length; i++) edges.add(edg[i]);
	for(int i=0; i<fcs.length; i++) faces.add(fcs[i]);
    }
    
    
    public IMeshGeo(IVec[] vert){ // single face mesh
	vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
        faces = new ArrayList<IFace>();
	for(int i=0; i<vert.length; i++) addVertex(new IVertex(vert[i])); //vertices.add(new IVertex(vert[i]));
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
	for(int i=0; i<vert.length; i++) addVertex(vert[i]); //vertices.add(vert[i]);
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
	    for(IVertex v : f.vertices) if(!vertices.contains(v)) addVertex(v); //vertices.add(v);
	    for(IEdge e : f.edges) if(!edges.contains(e)) edges.add(e);
	}
	
	if(IConfig.removeDuplicatesAtMeshCreation){
	    this.removeDuplicates();
	}
    }
    
    public IMeshGeo(IMeshGeo m){
	// deep copy
	vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
        faces = new ArrayList<IFace>();
	
	for(int i=0; i<m.vertices.size(); i++)  addVertex(m.vertices.get(i).dup()); //vertices.add(m.vertices.get(i).dup());
	for(int i=0; i<m.edges.size(); i++) edges.add(m.edges.get(i).dup());
	for(int i=0; i<m.faces.size(); i++) faces.add(m.faces.get(i).dup());
	
	// re-connect everything
	for(int i=0; i<m.faces.size(); i++) replaceFace(m.faces.get(i), faces.get(i));
	for(int i=0; i<m.edges.size(); i++) replaceEdge(m.edges.get(i), edges.get(i));
	for(int i=0; i<m.vertices.size(); i++) replaceVertex(m.vertices.get(i), vertices.get(i));
	
    }
    
    
    /*
    public void initWithLines(ArrayList<ICurveI> lines, IMeshType creator){
	initWithLines(lines.toArray(new ICurveI[lines.size()]),creator);
    }
    */
    
    public void initWithLines(ICurveI[] lines, IMeshType creator){

	// check if lines are closed
	int closedNum=0;
	for(int i=0; i<lines.length; i++) if(lines[i].isClosed()) closedNum++;
	
	// half?
	if(closedNum>lines.length/2){
	    initWithClosedLines(lines,creator);
	    return;
	}

	// should the closed lines exploaded?
	
	
	//boolean fixAllPoints=true; //false;
	
        // pick all points
        vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
        for(int i=0; i<lines.length; i++){
            //synchronized(IG.lock){
	    ICurveI link = lines[i];
	    IVertex p1 = creator.createVertex(link.start().get());
	    IVertex p2 = creator.createVertex(link.end().get());
	    IEdge e = creator.createEdge(p1,p2);
	    edges.add(e);
	    vertices.add(p1); // addVertex(p1); //would it mess up because some of them replaced later?
	    vertices.add(p2); // addVertex(p2); //would it mess up because some of them replaced later?
	    //}
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
    
    
    public void initWithClosedLines(ICurveI[] lines, IMeshType creator){
	// should open lines removed?
	
	ArrayList<IVecI[]> cpts = new ArrayList<IVecI[]>();
	for(int i=0; i<lines.length; i++){
	    if(lines[i].deg()==1 && lines[i].isClosed()){
		IVecI[] pts = new IVecI[lines[i].cpNum()-1];
		for(int j=0; j<lines[i].cpNum()-1; j++){ pts[j] = lines[i].cp(j); }
		cpts.add(pts);
	    }
	}
	initWithClosedLines(cpts.toArray(new IVecI[cpts.size()][]),creator);
	
	/*
        // pick all points
        vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
	faces = new ArrayList<IFace>();
        for(int i=0; i<lines.length; i++){
	    if(lines[i].deg()==1 && lines[i].isClosed()){
		ICurveI link = lines[i];
		int num = link.cpNum()-1;
		IVertex[] vtx = new IVertex[num];
		for(int j=0; j<num; j++){
		    IVecI cp = link.cp(j);
		    boolean unique=true;
		    for(int k=0; k<vertices.size()&&unique; k++){
			if(cp.eq(vertices.get(k))){
			    unique=false;
			    vtx[j] = vertices.get(k);
			}
		    }
		    if(unique){
			vtx[j] = creator.createVertex(cp);
			vertices.add(vtx[j]);
		    }
		}
		IEdge[] e = new IEdge[num];
		for(int j=0; j<num; j++){
		    boolean unique=true;
		    for(int k=0; k<edges.size()&&unique; k++){
			if(edges.get(i).contains(vtx[j],vtx[(j+1)%num])){
			    unique=false;
			    e[j] = edges.get(i);
			}
		    }
		    if(unique){
			e[j] = creator.createEdge(vtx[j],vtx[(j+1)%num]);
			edges.add(e[j]);
		    }
		}
		IFace f = creator.createFace(e);
		faces.add(f);
	    }
        }
	*/
    }
    
    
    public void initWithClosedLines(IVecI[][] linePts, IMeshType creator){
	// should open lines removed?
	
        // pick all points
        vertices = new ArrayList<IVertex>();
        edges = new ArrayList<IEdge>();
	faces = new ArrayList<IFace>();
        for(int i=0; i<linePts.length; i++){
	    int num = linePts[i].length;
	    IVertex[] vtx = new IVertex[num];
	    for(int j=0; j<num; j++){
		IVecI cp = linePts[i][j];
		boolean unique=true;
		for(int k=0; k<vertices.size()&&unique; k++){
		    if(cp.eq(vertices.get(k))){
			unique=false;
			vtx[j] = vertices.get(k);
		    }
		}
		if(unique){
		    vtx[j] = creator.createVertex(cp);
		    addVertex(vtx[j]); // vertices.add(vtx[j]); 
		}
	    }
	    IEdge[] e = new IEdge[num];
	    for(int j=0; j<num; j++){
		boolean unique=true;
		for(int k=0; k<edges.size()&&unique; k++){
		    if(edges.get(i).contains(vtx[j],vtx[(j+1)%num])){
			unique=false;
			e[j] = edges.get(i);
		    }
		}
		if(unique){
		    e[j] = creator.createEdge(vtx[j],vtx[(j+1)%num]);
		    edges.add(e[j]);
		}
	    }
	    IFace f = creator.createFace(e);
	    faces.add(f);
        }
    }
    
    
    public void initWithSurfaces(ISurfaceI[] faces, IMeshType creator){
	ArrayList<IVecI[]> cpts = new ArrayList<IVecI[]>();
	for(int i=0; i<faces.length; i++){
	    ArrayList<IVecI> pts = new ArrayList<IVecI>();
	    pts.add(faces[i].cornerCP(0,0));
	    if(!pts.get(pts.size()-1).eq(faces[i].cornerCP(1,0))){ pts.add(faces[i].cornerCP(1,0)); }
	    if(!pts.get(pts.size()-1).eq(faces[i].cornerCP(1,1))){ pts.add(faces[i].cornerCP(1,1)); }
	    if(!pts.get(pts.size()-1).eq(faces[i].cornerCP(0,1))){ pts.add(faces[i].cornerCP(0,1)); }
	    
	    if(pts.size()==3 || pts.size()==4){
		cpts.add(pts.toArray(new IVecI[pts.size()]));
	    }
	}
	initWithClosedLines(cpts.toArray(new IVecI[cpts.size()][]),creator);
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

    /** add a vertex to a mesh and set index number to the vertex */
    // this makes a vartex impossble to be shared between different mesh
    public void addVertex(IVertex v){
	//if(vertices==null){ vertices = new ArrayList<IVertex>(); }
	//v.index = vertices.size(); // this will be done at saving
	vertices.add(v);
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
    
    
    public static IMeshGeo createMeshWithEdges(ArrayList<IEdge> edges, IMeshType creator){
	IMeshGeo mesh = new IMeshGeo();
	mesh.initWithEdges(edges,creator);
	return mesh;
    }

    /** create polyhedron mesh by delaunay triangulation around a center */
    public static IMeshGeo createPolyhedron(IVertex[] vtx){
	int n = vtx.length;
	IVec c = new IVec();
	IVec[] p = new IVec[n];
	for(int i=0; i<n; i++){ p[i] = vtx[i].get().dup(); c.add(p[i]); }
	c.div(n);
	for(int i=0; i<n; i++){ p[i].sub(c).unit(); } // unitize around center
	
	ArrayList<IFace> fcs = new ArrayList<IFace>();
	for(int i=0; i<n; i++){
	    if(i%10==0){
		IOut.debug(20, "checking vertex "+i+"/"+n); //
	    }
	    for(int j=i+1; j<n; j++){
		for(int k=j+1; k<n; k++){
		    boolean in=false;
		    IVec cc = IVec.circumcenter(p[i],p[j],p[k]);
		    if(cc==null) in=true;
		    else{
			double r = cc.dist(p[i]);
			for(int l=0; l<n&&!in; l++){
			    if(i!=l&&j!=l&&k!=l){
				IVec ix = IVec.intersectPlaneAndLine(p[i],p[j],p[k],p[l],IG.origin);
				if(ix!=null && ix.dot(p[l])>0 && r>cc.dist(ix)){
				    in=true;
				}
			    }
			}
		    }
		    if(!in){
			if(p[i].nml(p[j],p[k]).dot(p[i])>0){
			    fcs.add(new IFace(vtx[i],vtx[j],vtx[k]));
			}
			else{
			    fcs.add(new IFace(vtx[i],vtx[k],vtx[j]));
			}
		    }
		}
	    }
	}
	
	return new IMeshGeo(fcs.toArray(new IFace[fcs.size()]));
    }
    
    
    /** create polyhedron mesh by delaunay triangulation around a center with maximum length of triangle edge by threshold */
    public static IMeshGeo createPolyhedron(IVertex[] vtx, double threshold){
	int n = vtx.length;
	IVec c = new IVec();
	IVec[] p = new IVec[n];
	for(int i=0; i<n; i++){ p[i] = vtx[i].get().dup(); c.add(p[i]); }
	c.div(n);
	for(int i=0; i<n; i++){ p[i].sub(c).unit(); } // unitize around center
	
	ArrayList<IFace> fcs = new ArrayList<IFace>();
	for(int i=0; i<n; i++){
	    if(i%10==0){
		IOut.debug(20, "checking vertex "+i+"/"+n); //
	    }
	    for(int j=i+1; j<n; j++){
		if(vtx[j].pos.dist(vtx[i].pos)<threshold){
		    for(int k=j+1; k<n; k++){
			if(vtx[k].pos.dist(vtx[i].pos)<threshold && vtx[k].pos.dist(vtx[j].pos)<threshold){
			    boolean in=false;
			    IVec cc = IVec.circumcenter(p[i],p[j],p[k]);
			    if(cc==null) in=true;
			    else{
				double r = cc.dist(p[i]);
				for(int l=0; l<n&&!in; l++){
				    if(vtx[l].dist(vtx[i])<threshold &&
				       vtx[l].dist(vtx[j])<threshold &&
				       vtx[l].dist(vtx[k])<threshold ){
					if(i!=l&&j!=l&&k!=l){
					    IVec ix = IVec.intersectPlaneAndLine(p[i],p[j],p[k],p[l],IG.origin);
					    if(ix!=null && ix.dot(p[l])>0 && r>cc.dist(ix)){
						in=true;
					    }
					}
				    }
				}
			    }
			    if(!in){
				if(p[i].nml(p[j],p[k]).dot(p[i])>0){
				    fcs.add(new IFace(vtx[i],vtx[j],vtx[k]));
				}
				else{
				    fcs.add(new IFace(vtx[i],vtx[k],vtx[j]));
				}
			    }
			}
		    }
		}
	    }
	}
	
	return new IMeshGeo(fcs.toArray(new IFace[fcs.size()]));
    }
    


    /** alias of createPolyhedron */
    public static IMeshGeo polyhedron(IVertex[] vtx){
	return createPolyhedron(vtx);
    }
    
    /** alias of createPolyhedron with maximum threshold for edge length */
    public static IMeshGeo polyhedron(IVertex[] vtx, double threshold){
	return createPolyhedron(vtx,threshold);
    }
    
    /** connect closest vertex */
    public static IMeshGeo connectVertex(IMeshGeo mesh1, IMeshGeo mesh2){
	int n1 = mesh1.vertexNum();
	int n2 = mesh2.vertexNum();
	double mindist=-1;
	int minIdx1=0, minIdx2=0;
	for(int i=0; i<n1; i++){
	    for(int j=0; j<n2; j++){
		double dist = mesh1.vertex(i).dist(mesh2.vertex(j));
		if(mindist<0 || dist<mindist){ mindist = dist; minIdx1=i; minIdx2=j; }
	    }
	}
	return connectVertex(mesh1, mesh1.vertex(minIdx1), mesh2, mesh2.vertex(minIdx2));
    }
    
    
    public static IMeshGeo connectVertex(IMeshGeo mesh1, IVertex v1, IMeshGeo mesh2, IVertex v2){
	
	// remove duplicated vertices (multiple edge to a same vertex)
	ArrayList<IVertex> vtx1 = new ArrayList<IVertex>();
	ArrayList<IVertex> vtx2 = new ArrayList<IVertex>();
	
	for(int i=0; i<v1.linkedVertices.size(); i++){
	    if(!vtx1.contains(v1.linkedVertices.get(i))){
		vtx1.add(v1.linkedVertices.get(i));
	    }
	}
	
	for(int i=0; i<v2.linkedVertices.size(); i++){
	    if(!vtx2.contains(v2.linkedVertices.get(i))){
		vtx2.add(v2.linkedVertices.get(i));
	    }
	}
		
	ArrayList<IVertex> allVtx = new ArrayList<IVertex>();
	for(int i=0; i<vtx1.size(); i++){ allVtx.add(vtx1.get(i)); }
	for(int i=0; i<vtx2.size(); i++){ allVtx.add(vtx2.get(i)); }
	IMeshGeo mesh = IMeshGeo.polyhedron(allVtx.toArray(new IVertex[allVtx.size()])); //
	
	ArrayList<IFace> fcs = new ArrayList<IFace>();
	
	for(int i=0; i<mesh.faceNum(); i++){
	    boolean mixed=false;
	    if(vtx1.contains(mesh.face(i).vertex(0))){
		for(int j=1; j<mesh.face(i).vertexNum() && !mixed; j++){
		    if(!vtx1.contains(mesh.face(i).vertex(j))){ mixed=true; }
		}
	    }
	    else{ // vertex is in vtx2
		for(int j=1; j<mesh.face(i).vertexNum() && !mixed; j++){
		    if(vtx1.contains(mesh.face(i).vertex(j))){ mixed=true; }
		}
	    }
	    
	    if(mixed){ // connecting vtx1 and vtx2
		fcs.add(mesh.face(i));
	    }
	}
	
	for(int i=0; i<mesh1.faceNum(); i++){
	    boolean contain=false;
	    for(int j=0; j<mesh1.face(i).vertexNum() && !contain; j++){
		if(mesh1.face(i).vertex(j)==v1) contain=true;
	    }
	    if(!contain){ fcs.add(mesh1.face(i)); }
	}
	
	for(int i=0; i<mesh2.faceNum(); i++){
	    boolean contain=false;
	    for(int j=0; j<mesh2.face(i).vertexNum() && !contain; j++){
		if(mesh2.face(i).vertex(j)==v2) contain=true;
	    }
	    if(!contain){ fcs.add(mesh2.face(i)); }
	}
	
	return new IMeshGeo(fcs.toArray(new IFace[fcs.size()]));
	
	/*    
	int ln1 = vtx1.size();
	int ln2 = vtx2.size();
	
	double mindist=-1;
	int minIdx1=0, minIdx2=0;
	for(int i=0; i<ln1; i++){
	    if(vtx1.get(i).dist(v1)>IConfig.tolerance ){
		for(int j=0; j<ln2; j++){
		    if(vtx2.get(j).dist(v2)>IConfig.tolerance ){
			double dist = vtx1.get(i).dist(vtx2.get(j));
			if(mindist<0 || dist<mindist){ mindist = dist; minIdx1=i; minIdx2=j; }
		    }
		}
	    }
	}
	
	vtx1 = IVertex.sortByEdge(vtx1, minIdx1);
	vtx2 = IVertex.sortByEdge(vtx2, minIdx2);
	
	if(vtx1==null || vtx2==null){ // how about partial error where sorted.size()<vertices.size()
	    IOut.err("connection error");
	    return null;
	}
	ln1 = vtx1.size();
	ln2 = vtx2.size();
	
	// check direction of ring
	IVec cnt1 = IVec.center(vtx1.toArray(new IVertex[vtx1.size()]));
	IVec cnt2 = IVec.center(vtx2.toArray(new IVertex[vtx2.size()]));
	IVec dir = cnt2.dif(cnt1);
	
	IVec nml1 = IVec.averageNormal(vtx1.toArray(new IVertex[vtx1.size()]));
	IVec nml2 = IVec.averageNormal(vtx2.toArray(new IVertex[vtx2.size()]));

	if(dir.dot(nml1) * dir.dot(nml2) < 0){
	//if(nml1.dot(nml2)<0){
	    // flip order
	    ArrayList<IVertex> tmp = new ArrayList<IVertex>();
	    for(int i=ln2-1; i>=0; i--){ tmp.add(vtx2.get(i)); }
	    vtx2.clear();
	    vtx2 = tmp;
	    nml2.flip();
	}
	ArrayList<IFace> fcs = new ArrayList<IFace>();
	for(int i=0,j=0; i<ln1&&j<ln2; ){
	    IVertex u1 = vtx1.get(i%ln1);
	    IVertex u2 = vtx2.get(j%ln2);
	    IVertex u3 = null;
	    if(i==ln1){
		u3 = vtx2.get((j+1)%ln2);
		j++;
	    }
	    else if(j==ln2){
		u3 = vtx1.get((i+1)%ln1);
		i++;
	    }
	    else{
		if( u1.dist(vtx2.get((j+1)%ln2)) < u2.dist(vtx1.get((i+1)%ln1)) ){
		    u3 = vtx2.get((j+1)%ln2);
		    j++;
		}
		else{
		    u3 = vtx1.get((i+1)%ln1);
		    i++;
		}
	    }
	    fcs.add(new IFace(u1,u2,u3));
	    if(i>=ln1-1 && j>=ln2-1){
		i++; j++; // end
	    }
	    else{
		if(ln1<ln2){ if(i==ln1){ i--; } }
		else{ if(j==ln2){ j--; } }
	    }
	}
	return new IMeshGeo(fcs.toArray(new IFace[fcs.size()])); //
	*/
    }
    
    
    public void initWithEdges(ArrayList<IEdge> edges, IMeshType creator){
	
	for(int i=0; i<edges.size(); i++){
	    if(!vertices.contains(edges.get(i).vertices[0]))
		addVertex(edges.get(i).vertices[0]);
		//vertices.add(edges.get(i).vertices[0]);
		
	    if(!vertices.contains(edges.get(i).vertices[1]))
		addVertex(edges.get(i).vertices[1]);
		//vertices.add(edges.get(i).vertices[1]);
	}
        
        // pick all points
        //ArrayList<IVertex> vertices = new ArrayList<IVertex>();
	//
	//for(int i=0; i<edges.size(); i++){
	//    if(!vertices.contains(edges.get(i).vertices[0]))
	//	vertices.add(edges.get(i).vertices[0]);
	//    if(!vertices.contains(edges.get(i).vertices[1]))
	//	vertices.add(edges.get(i).vertices[1]);
	//}
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
    
    
    public void initWithPointMatrix(IVec[][] matrix, int unum, int vnum,
				    boolean triangulateDir, IMeshType creator){
	
	IVertex[][] vmatrix = new IVertex[unum][vnum];
	
	for(int i=0; i<unum; i++){
	    for(int j=0; j<vnum; j++){
		vmatrix[i][j] = creator.createVertex(matrix[i][j]);
		//vertices.add(vmatrix[i][j]);
		addVertex(vmatrix[i][j]);
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
    
    /** return all vertices */
    public ArrayList<IVertex> vertices(){ return vertices; }
    /** return all edges */
    public ArrayList<IEdge> edges(){ return edges; }
    /** return all faces */
    public ArrayList<IFace> faces(){ return faces; }


    /** center of mesh, calculated by average of all vertices */
    public IVec center(){
	IVec cnt = new IVec();
	for(int i=0; i<vertices.size(); i++) cnt.add(vertices.get(i).pos());
	return cnt.div(vertices.size());
    }
    
    

    public void deleteUnlinked(){
	for(int i=0; i<faces.size(); i++){
	    if(faces.get(i).deleted){
		faces.remove(i);
		i--;
	    }
	}
	for(int i=0; i<edges.size(); i++){
	    if(edges.get(i).faces.size()==0){
		edges.get(i).del();
		edges.remove(i);
		i--;
	    }
	}
	for(int i=0; i<vertices.size(); i++){
	    if(vertices.get(i).faces.size()==0 ||
	       vertices.get(i).edges.size()<=1 ||
	       vertices.get(i).linkedVertices.size()<=1){
		vertices.get(i).del();
		vertices.remove(i);
		i--;
	    }
	}
    }
    
    public IMeshGeo deleteVertex(IVertex v){
	//vertices.get(i).del(); vertices.remove(i);
	/*
	IVertex v = vertices.get(i);
	for(int i=0; i<v.faceNum(); i++){
	    deleteFace(v.face(i));
	}
	for(int i=0; i<v.edgeNum(); i++){
	    deleteEdge(v.edge(i));
	}
	for(int i=0; i<v.linkedVertices.size(); i++){
	    v.linkedVertices.remove(v);
	}
	*/
	v.del();
	vertices.remove(v);
	deleteUnlinked();
	return this;
    }
    
    public IMeshGeo deleteEdge(IEdge e){
	/*
	//edges.get(i).del(); edges.remove(i);
	IEdge e = edges.get(i);
	edges.remove(i);
	for(int i=0; i<e.faceNum(); i++){
	    e.face(i).edges.remove(e);
	    deleteFace(e.face(i));
	}
	e.vertices[0].del(e);
	e.vertices[1].del(e);
	*/
	e.del();
	edges.remove(e);
	deleteUnlinked();
	return this;
    }
    public IMeshGeo deleteFace(IFace f){
	/*
	//faces.get(i).del(); faces.remove(i);
	IFace f = faces.get(i);
	for(int i=0; i<f.vertexNum(); i++){ f.vertex(i).faces.remove(f); }
	for(int i=0; i<f.edgeNum(); i++){ f.edge(i).faces.remove(f); }
	faces.remove(i);
	
	for(int i=0; i<f.vertexNum(); i++){
	    if(f.vertex(i).faceNum()==0){ deleteVertex(f.vertex(i)); }
	}
	for(int i=0; i<f.edgeNum(); i++){
	    if(f.edge(i).faceNum()==0){ deleteEdge(f.edge(i)); }
	}
	*/
	f.del();
	faces.remove(f);
	deleteUnlinked();
	return this;
    }
    
    public IMeshGeo deleteVertex(int i){ return deleteVertex(vertex(i)); }
    public IMeshGeo deleteEdge(int i){ return deleteEdge(edge(i)); }
    public IMeshGeo deleteFace(int i){ return deleteFace(face(i)); }
    
    public IMeshGeo deleteVertex(IIntegerI i){ return deleteVertex(i.x()); }
    public IMeshGeo deleteEdge(IIntegerI i){ return deleteEdge(i.x()); }
    public IMeshGeo deleteFace(IIntegerI i){ return deleteFace(i.x()); }
    
    
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
            if(!vertices.contains(f.vertices[i])){
		//vertices.add(f.vertices[i]);
		addVertex(f.vertices[i]);
	    }
        }
    }
    
    public void addFace(IFace f, boolean checkVertexExisting, boolean checkEdgeExisting, boolean checkFaceExisting){
	for(int i=0; i<f.vertices.length; i++){
	    if(!checkVertexExisting || !vertices.contains(f.vertices[i])){
		//vertices.add(f.vertices[i]);
		addVertex(f.vertices[i]);
	    }
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
	
	for(int i=0; i<v.length; i++){
	    if(!vertices.contains(v[i])){
		//vertices.add(v[i]);
		addVertex(v[i]);
	    }
	}
	
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
	for(int i=0; i<v.length; i++){
	    vtx[i] = new IVertex(v[i]);
	    //vertices.add(vtx[i]);
	    addVertex(vtx[i]);
	}
	
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
	for(int i=0; i<num*2; i++){
	    if(!vertices.contains(v[i])){
		//vertices.add(v[i]);
		addVertex(v[i]);
	    }
	}
	
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
	for(int i=0; i<num*2; i++){
	    vtx[i] = new IVertex(v[i]);
	    //vertices.add(vtx[i]);
	    addVertex(vtx[i]);
	}
	
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
	
	for(int i=0; i<v.length; i++){
	    if(!vertices.contains(v[i])){
		//vertices.add(v[i]);
		addVertex(v[i]);
	    }
	}
	
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
	for(int i=0; i<v.length; i++){
	    vtx[i] = new IVertex(v[i]);
	    //vertices.add(vtx[i]);
	    addVertex(vtx[i]);
	}
	
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
	
	// check existing vertex
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
	    IVertex v1 = f.edges[i].getSharedVertex(f.edges[(i+1)%num]);
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
	
	if(!vertices.contains(v)){
	    //vertices.add(v);
	    addVertex(v);
	}
	
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
	    IVertex v = f.edges[i].getSharedVertex(f.edges[(i+1)%num]);
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
	if(!vertices.contains(vertexOnEdge)){
	    //vertices.add(vertexOnEdge);
	    addVertex(vertexOnEdge);
	}
	
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
	
	//vertices.add(v);
	addVertex(v);
	edges.add(ne1);
	edges.add(ne2);
	
	for(int i=0; i<e.faces.size(); i++){
	    
	    if(e.faces.get(i).vertexNum()==3){
		IVertex v3 = e.faces.get(i).getOtherVertex(v1,v2);
		
		if(v3!=null){
		    IEdge me = creator.createEdge(v3, v);
		    edges.add(me);
		    
		    IEdge e1 = e.faces.get(i).getEdge(v3,v1);
		    IEdge e2 = e.faces.get(i).getEdge(v3,v2);
		    
		    IEdge[] es1 = new IEdge[3];
		    es1[0] = e1;
		    es1[1] = ne1;
		    es1[2] = me;
		    IFace f1 = creator.createFace(es1);

		    IEdge[] es2 = new IEdge[3];
		    es2[0] = e2;
		    es2[1] = ne2;
		    es2[2] = me;
		    IFace f2 = creator.createFace(es2);
		    
		    faces.add(f1);
		    faces.add(f2);
		}
		else{
		    IOut.err("no opposite vertex!"); //
		}
	    }
	    else{ // quad mesh
		
		IVertex[] v3 = e.faces.get(i).getAdjacentVertices(v1,v2); // in case of quad mesh
		
		if(v3!=null){
		    IEdge me1 = creator.createEdge(v3[0], v);
		    edges.add(me1);
		    IEdge me2 = creator.createEdge(v3[1], v);
		    edges.add(me2);
		    
		    IEdge e1 = e.faces.get(i).getEdge(v3[0],v1);
		    IEdge e2 = e.faces.get(i).getEdge(v3[1],v2);
		    
		    IEdge[] es1 = new IEdge[3];
		    es1[0] = e1;
		    es1[1] = ne1;
		    es1[2] = me1;
		    IFace f1 = creator.createFace(es1);
		    
		    IEdge[] es2 = new IEdge[3];
		    es2[0] = e2;
		    es2[1] = ne2;
		    es2[2] = me2;
		    IFace f2 = creator.createFace(es2);
		    
		    IFace f3 = null;
		    if(e.faces.get(i).vertexNum()==4){
			IEdge e3 = e.faces.get(i).getEdge(v3[0],v3[1]);
			IEdge[] es3 = new IEdge[3];
			es3[0] = me1;
			es3[1] = me2;
			es3[2] = e3;
			f3 = creator.createFace(es3);
		    }
		    else{
			//ArrayList<IEdge> otherEdges = e.faces.get(i).getOtherEdges(v1,v2,v3[0],v3[1]);
			IEdge[] otherEdges = e.faces.get(i).getOtherEdges(v1,v2,v3[0],v3[1]);
			//IEdge[] es3 = new IEdge[2+otherEdges.size()];
			IEdge[] es3 = new IEdge[2+otherEdges.length];
			es3[0] = me1;
			es3[1] = me2;
			//for(int j=0; j<otherEdges.size(); i++){ es3[2+j] = otherEdges.get(j); }
			for(int j=0; j<otherEdges.length; i++){ es3[2+j] = otherEdges[j]; }
			f3 = creator.createFace(es3);
		    }
		    
		    faces.add(f1);
		    faces.add(f2);
		    faces.add(f3);
		}
		else{
		    IOut.err("no opposite vertex!"); //
		}
		
	    }
	}
	
	for(int i=0; i<e.faces.size(); i++){
	    faces.remove(e.faces.get(i));
	    e.faces.get(i).del();
	    // i--; // no need?
	}
	
	edges.remove(e);
	e.del();
	// edge also delete connected faces
    }
    
    

    /** divide by multiple ratios. ratios need to be orderd from smaller to larger 
	@param ratios  [0-1]: 0 -> e.vertices[0], 1->e.vertices[1]
	@return divided edges.
    */
    public IEdge[] divideEdge(IEdge e, double[] ratios, IMeshType creator){
	
	IVertex v1 = e.vertices[0];
	IVertex v2 = e.vertices[1];
	IVecI n1 = v1.nml();
	IVecI n2 = v2.nml();
	IVertex[] vtx = new IVertex[ratios.length];
	for(int i=0; i<ratios.length; i++){
	    vtx[i] = creator.createVertex(v2.pos.dup().get().sum(v1.pos,1.-ratios[i]));
	    vtx[i].setNormal(n1.sum(n2,ratios[i]));
	    //vertices.add(vtx[i]);
	    addVertex(vtx[i]);
	}
	
	IEdge[] ne = new IEdge[vtx.length+1];
	for(int i=0; i<=vtx.length; i++){
	    if(i==0){ ne[i] = creator.createEdge(v1, vtx[i]); }
	    else if(i==vtx.length){ ne[i] = creator.createEdge(vtx[i-1],v2); }
	    else{ ne[i] = creator.createEdge(vtx[i-1],vtx[i]); }
	    edges.add(ne[i]);
	}
	
	for(int i=0; i<e.faces.size(); i++){
	    
	    if(e.faces.get(i).vertexNum()==3){
		IVertex v3 = e.faces.get(i).getOtherVertex(v1,v2);
		
		if(v3!=null){
		    IEdge[] me = new IEdge[vtx.length];
		    for(int j=0; j<vtx.length; j++){
			me[j] = creator.createEdge(v3,vtx[j]);
			edges.add(me[j]);
		    }
		    
		    IEdge e1 = e.faces.get(i).getEdge(v3,v1);
		    IEdge e2 = e.faces.get(i).getEdge(v3,v2);
		    
		    for(int j=0; j<=vtx.length; j++){
			if(j==0){
			    IEdge[] es = new IEdge[3];
			    es[0] = e1;
			    es[1] = ne[j];
			    es[2] = me[j];
			    faces.add(creator.createFace(es));
			}
			else if(j==vtx.length){
			    IEdge[] es = new IEdge[3];
			    es[0] = me[j-1];
			    es[1] = ne[j];
			    es[2] = e2;
			    faces.add(creator.createFace(es));
			}
			else{
			    IEdge[] es = new IEdge[3];
			    es[0] = me[j-1];
			    es[1] = ne[j];
			    es[2] = me[j];
			    faces.add(creator.createFace(es));
			}
		    }
		}
		else{
		    IOut.err("no opposite vertex!"); //
		}
	    }
	    else{ // quad mesh
		
		IVertex[] v3 = e.faces.get(i).getAdjacentVertices(v1,v2); // in case of quad mesh
		
		if(v3!=null){
		    int halfIndex=0;
		    for(int j=0; j<ratios.length; j++){ if(ratios[j]<0.5) halfIndex++; }
		    if(halfIndex>=ratios.length) halfIndex=ratios.length-1;
		    
		    IEdge[] me1 = new IEdge[halfIndex+1];
		    IEdge[] me2 = new IEdge[vtx.length-halfIndex];
		    
		    for(int j=0; j<=halfIndex; j++){
			me1[j] = creator.createEdge(v3[0],vtx[j]);
			edges.add(me1[j]);
		    }
		    for(int j=halfIndex; j<vtx.length; j++){
			me2[j-halfIndex] = creator.createEdge(v3[1],vtx[j]);
			edges.add(me2[j-halfIndex]);
		    }
		    
		    IEdge e1 = e.faces.get(i).getEdge(v3[0],v1);
		    IEdge e2 = e.faces.get(i).getEdge(v3[1],v2);
		    
		    for(int j=0; j<=halfIndex; j++){
			if(j==0){
			    IEdge[] es = new IEdge[3];
			    es[0] = e1;
			    es[1] = ne[j];
			    es[2] = me1[j];
			    faces.add(creator.createFace(es));
			}
			else{
			    IEdge[] es = new IEdge[3];
			    es[0] = me1[j-1];
			    es[1] = ne[j];
			    es[2] = me1[j];
			    faces.add(creator.createFace(es));
			}
		    }
		    for(int j=halfIndex; j<vtx.length; j++){
			if(j==vtx.length-1){
			    IEdge[] es = new IEdge[3];
			    es[0] = me2[j-halfIndex];
			    es[1] = ne[j+1];
			    es[2] = e2;
			    faces.add(creator.createFace(es));
			}
			else{
			    IEdge[] es = new IEdge[3];
			    es[0] = me2[j-halfIndex];
			    es[1] = ne[j+1];
			    es[2] = me2[j+1-halfIndex];
			    faces.add(creator.createFace(es));
			}
		    }
		    
		    if(e.faces.get(i).vertexNum()==4){
			IEdge e3 = e.faces.get(i).getEdge(v3[0],v3[1]);
			IEdge[] es3 = new IEdge[3];
			es3[0] = me1[me1.length-1];
			es3[1] = me2[0];
			es3[2] = e3;
			IFace f3 = creator.createFace(es3);
			faces.add(f3);
		    }
		    else{
			//ArrayList<IEdge> otherEdges = e.faces.get(i).getOtherEdges(v1,v2,v3[0],v3[1]);
			IEdge[] otherEdges = e.faces.get(i).getOtherEdges(v1,v2,v3[0],v3[1]);
			//IEdge[] es3 = new IEdge[2+otherEdges.size()];
			IEdge[] es3 = new IEdge[2+otherEdges.length];
			es3[0] = me1[me1.length-1];
			es3[1] = me2[0];
			//for(int j=0; j<otherEdges.size(); i++){ es3[2+j] = otherEdges.get(j); }
			for(int j=0; j<otherEdges.length; i++){ es3[2+j] = otherEdges[j]; }
			IFace f3 = creator.createFace(es3);
			faces.add(f3);
		    }
		}
		else{ IOut.err("no opposite vertex!"); } //
	    }
	}
	
	for(int i=0; i<e.faces.size(); i++){
	    faces.remove(e.faces.get(i));
	    e.faces.get(i).del();
	    // i--; // no need?
	}
	
	edges.remove(e);
	e.del();
	// edge also delete connected faces
	
	return ne;
    }
    
    
    


    // ratio:0-1: 0 -> e.vertices[0], 1->e.vertices[1]
    public IMeshGeo divideFace(IFace f, IEdge e1, IVertex nv1, IEdge e2, IVertex nv2,
			       IMeshType creator){
	
	if( !f.contains(e1) || !f.contains(e2) ){
	    IOut.err("edges are not included in the face");
	    return this;
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
	    return this;
	}	
	
	ArrayList<IEdge> edges1=new ArrayList<IEdge>();
	ArrayList<IEdge> edges2=new ArrayList<IEdge>();
	
	int num = f.edges.length;
	int i;
	
	edges1.add(ne);
	if(f.edges[ (e1idx+1)%num ].hasSharedVertex(ne11) ) edges1.add(ne11);
	else if(f.edges[ (e1idx+1)%num ].hasSharedVertex(ne12) ) edges1.add(ne12);
	
	//for(int i=e1idx+1; i<e2idx; i++) edges1.add(f.edges.get(i));
	i=e1idx+1;
	while(i%num != e2idx){ edges1.add(f.edges[i%num]); i++; }
	
	if(f.edges[ (e2idx-1+num)%num ].hasSharedVertex(ne11) ) edges1.add(ne11);
	else if(f.edges[ (e2idx-1+num)%num ].hasSharedVertex(ne12) ) edges1.add(ne12);
	
	edges2.add(ne);
	if(f.edges[ (e2idx+1)%num ].hasSharedVertex(ne21) ) edges2.add(ne21);
	else if(f.edges[ (e2idx+1)%num ].hasSharedVertex(ne22) ) edges2.add(ne22);
	
	i=e2idx+1;
	while(i%num != e1idx){ edges2.add(f.edges[i%num]); i++; }
	
	if(f.edges[ (e1idx-1+num)%num ].hasSharedVertex(ne21) ) edges2.add(ne21);
	else if(f.edges[ (e1idx-1+num)%num ].hasSharedVertex(ne22) ) edges2.add(ne22);
	
	
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

	return this;
    }
    
    
    public IMeshGeo triangulate(IFace f, boolean triangulateDirection, IMeshType creator){
	
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

	return this;
    }
    
    
    public IMeshGeo triangulate(){
	return triangulate(true, new IMeshType()); // default mesh type, default direction
    }
    
    public IMeshGeo triangulate(boolean triangulateDirection){
	return triangulate(triangulateDirection, new IMeshType()); // default mesh type
    }
    
    /** alias of triangulate; fow downward compatibility */
    public IMeshGeo triangulateAll(boolean triangulateDirection, IMeshType creator){
	return triangulate(triangulateDirection,creator);
    }
    
    public IMeshGeo triangulate(boolean triangulateDirection, IMeshType creator){
	
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
	
	return this;
    }
    
    public IMeshGeo triangulateAtCenter(){ return triangulateAtCenter(new IMeshType()); } // deault mesh type
    
    public IMeshGeo triangulateAtCenter(IMeshType creator){
	
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

	for(int i=0; i<newVertices.size();i++){
	    //vertices.add(newVertices.get(i));
	    addVertex(newVertices.get(i));
	}
	for(int i=0; i<newEdges.size(); i++) edges.add(newEdges.get(i));
	for(int i=0; i<newFaces.size(); i++) faces.add(newFaces.get(i));
	
	return this;
    }
    
    
    /** remove duplicated edge */
    public IMeshGeo removeDuplicatedEdge(){
	
	for(int i=0; i<edges.size(); i++){
	    for(int j=i+1; j<edges.size(); j++){
		if(edges.get(i).vertex(0) == edges.get(j).vertex(0) &&
		   edges.get(i).vertex(1) == edges.get(j).vertex(1) ||
		   edges.get(i).vertex(0) == edges.get(j).vertex(1) &&
		   edges.get(i).vertex(1) == edges.get(j).vertex(0) ){
		    for(int k=0; k<edges.get(j).faces.size(); k++){
			edges.get(j).faces.get(k).replaceEdge(edges.get(j),edges.get(i));
		    }
		    edges.remove(j);
		    j--;
		}
	    }
	}
	
	// duplicated linked vertices
	
	for(int i=0; i<vertices.size(); i++){
	    IVertex v = vertices.get(i);
	    for(int j=0; j<v.linkedVertices.size(); j++){
		IVertex v1 = v.linkedVertices.get(j);
		for(int k=j+1; k<v.linkedVertices.size(); k++){
		    if(v.linkedVertices.get(k) == v1){
			v.linkedVertices.remove(k);
			k--;
		    }
		}
	    }
	}
	
	return this;
    }
    
    /** returns naked edges excluding duplicated edges on the same place.
	Note nakedEdges returns array and nakedEdges returns ArrayList. This is because
	nakedEdge(boolean) is inteded for internal uses and nakedEdge() is for external.
	@return array of naked edge
    */
    public IEdge[] nakedEdges(){
	ArrayList<IEdge> nakedEdges = nakedEdges(true);
	//ArrayList<IEdge> nakedEdges = nakedEdges(false);
	return nakedEdges.toArray(new IEdge[nakedEdges.size()]);
    }
    
    /**
       @param checkDuplicatedEdge This boolean switch make it assume duplicated edges at the same location exist and they are supposed to be connected */
    public ArrayList<IEdge> nakedEdges(boolean checkDuplicatedEdge){
	boolean[] naked = new boolean[edges.size()];
	for(int i=0; i<edges.size(); i++){
	    naked[i]=false;
	    IEdge e = edges.get(i);
	    if(e.faceNum()==1){
		if(checkDuplicatedEdge){
		    boolean uniqueEdge=true;
		    for(int j=0; j<i && uniqueEdge; j++){
			if(e.eq(edges.get(j))){
			    uniqueEdge=false;
			    naked[j]=false;
			}
		    }
		    if(uniqueEdge){ naked[i] = true; }
		}
		else{ naked[i] = true; }
	    }
	}
	ArrayList<IEdge> nakedEdges = new ArrayList<IEdge>();
	for(int i=0; i<edges.size(); i++){
	    if(naked[i]){ nakedEdges.add(edges.get(i)); }
	}
	return nakedEdges;
    }
    
    /** subdivide all triangle faces into 4 triangles each divided at each mid point of edges. */
    public IMeshGeo subdivide(){
	
	IVertex[] midVtx = new IVertex[edges.size()];
	
	for(int i=0; i<edges.size(); i++){
	    midVtx[i] = new IVertex(edges.get(i).vertex(0).get().mid(edges.get(i).vertex(1)));
	}
	
	ArrayList<IFace> fcs = new ArrayList<IFace>();
	
	for(int i=0; i<faces.size(); i++){
	    IFace f = faces.get(i);
	    if(f.vertexNum()==3){
		IVertex v1 = f.vertex(0);
		IVertex v2 = f.vertex(1);
		IVertex v3 = f.vertex(2);
		
		IEdge e1 = f.getEdge(v1, v2);
		IEdge e2 = f.getEdge(v2, v3);
		IEdge e3 = f.getEdge(v3, v1);
		
		int ei1 = edges.indexOf(e1);
		int ei2 = edges.indexOf(e2);
		int ei3 = edges.indexOf(e3);
		
		if(ei1>=0 && ei2>=0 && ei3>=0){
		    IVertex m1 = midVtx[ei1];
		    IVertex m2 = midVtx[ei2];
		    IVertex m3 = midVtx[ei3];
		    
		    IFace f1 = new IFace(v1, m1, m3);
		    IFace f2 = new IFace(v2, m2, m1);
		    IFace f3 = new IFace(v3, m3, m2);
		    IFace f4 = new IFace(m1, m2, m3);
		    
		    fcs.add(f1);
		    fcs.add(f2);
		    fcs.add(f3);
		    fcs.add(f4);
		}
	    }
	    else{
		
	    }
	}
	
	return new IMeshGeo(fcs.toArray(new IFace[fcs.size()]));
    }
    
    
    public void subdivideNakedEdges(int recursionNum, IMeshType creator){
	final boolean checkDuplicatedEdge=true;
	//final int recursionNum = 0; //3;
	
	ArrayList<IEdge> nakedEdges = nakedEdges(checkDuplicatedEdge);
	
	//for(int i=0; i<nakedEdges.size(); i++) divideEdge(nakedEdges.get(i), new double[]{ 1./3, 2./3, }, creator);
	
	ArrayList<ArrayList<IEdge>> loops = findLoop(nakedEdges);
	for(int i=0; i<loops.size(); i++){
	    subdivideNakedEdges(loops.get(i),recursionNum,creator);
	}
    }
    
    public void subdivideNakedEdges(ArrayList<IEdge> loop, int recursionNum, IMeshType creator){
	if(recursionNum<=0) return;
	
	int num = loop.size();
	IEdge[][] divEdge = new IEdge[num][];
	for(int i=0; i<num; i++){
	    divEdge[i] = divideEdge(loop.get(i), new double[]{ 1./3, 2./3, }, creator);
	}
	
	ArrayList<IEdge> newLoop = new ArrayList<IEdge>();
	
	for(int i=0; i<num; i++){
	    IEdge e1=null,e2=null;
	    if(divEdge[i][0].isConnected(divEdge[(i+1)%num][0])){
		e1 = divEdge[i][0]; e2 = divEdge[(i+1)%num][0];
	    }
	    else if(divEdge[i][0].isConnected(divEdge[(i+1)%num][2])){
		e1 = divEdge[i][0]; e2 = divEdge[(i+1)%num][2];
	    }
	    else if(divEdge[i][2].isConnected(divEdge[(i+1)%num][0])){
		e1 = divEdge[i][2]; e2 = divEdge[(i+1)%num][0];
	    }
	    else if(divEdge[i][2].isConnected(divEdge[(i+1)%num][2])){
		e1 = divEdge[i][2]; e2 = divEdge[(i+1)%num][2];
	    }
	    else{
		IOut.err("divided edges are not connected.");
	    }

	    
	    if(e1!=null&&e2!=null){
		IVertex v0 = e1.getSharedVertex(e2);
		IVertex v1 = e1.getOtherVertex(v0);
		IVertex v2 = e2.getOtherVertex(v0);
		
		if(!v0.get().isStraight(v1,v2)){
		    IEdge filletEdge = creator.createEdge(v1,v2);
		    newLoop.add(divEdge[i][1]);
		    newLoop.add(filletEdge);
		    edges.add(filletEdge);
		    faces.add(creator.createFace(new IEdge[]{filletEdge,e1,e2}));
		}
		else{
		    newLoop.add(divEdge[i][1]);
		    newLoop.add(e1);
		    newLoop.add(e2);
		}
	    }
	    
	}
	
	if(newLoop.size()>2){ subdivideNakedEdges(newLoop, recursionNum-1, creator); }
    }
    
    
    public ArrayList<ArrayList<IEdge>> findLoop(ArrayList<IEdge> edges){
	ArrayList<ArrayList<IEdge>> loops = new ArrayList<ArrayList<IEdge>>();
	
	while(edges.size()>0){
	    ArrayList<IEdge> loop = new ArrayList<IEdge>();
	    IEdge e = edges.get(0);
	    loop.add(e);
	    edges.remove(e);
	    
	    IEdge nextEdge = null;
	    for(int i=0; i<edges.size()&&nextEdge==null; i++){
		if(edges.get(i).isConnected(e)){ nextEdge = edges.get(i); }
	    }
	    if(nextEdge==null){
		IOut.err("loop is not closed. skipped");
		//loops.add(loop); // add non-loop or not?
	    }
	    else{
		loop.add(nextEdge);
		edges.remove(nextEdge);
		e = nextEdge;
		while(nextEdge!=null){
		    nextEdge=null;
		    for(int i=0; i<edges.size()&&nextEdge==null; i++){
			if(edges.get(i).isConnected(e)){ nextEdge = edges.get(i); }
		    }
		    if(nextEdge!=null){
			e = nextEdge;
			loop.add(e);
			edges.remove(e);
			if(e.isConnected(loop.get(0))){
			    // loop closed
			    loops.add(loop);
			    nextEdge=null; // exit loop
			}
		    }
		    else{
			IOut.err("loop is not closed. skipped");
			//loops.add(loop); // add non-loop or not?
		    }
		}
	    }
	}
	return loops;
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
    
    
    /** just setting boolean value to closed. checking no connection of mesh. used to set closed flag in saving */
    public IMeshGeo close(){ closed=true; return this; }
    /** just setting boolean value to closed. checking no connection of mesh. used to set closed flag in saving */
    public boolean isClosed(){ return closed; }
    
    
    
    public static IMeshGeo joinMesh(IMeshGeo[] meshes){
	ArrayList<IFace> faces = new ArrayList<IFace>();
	for(int i=0; i<meshes.length; i++){
	    for(int j=0; j<meshes[i].faceNum(); j++){ faces.add(meshes[i].face(j)); }
	}
	IMeshGeo mesh = new IMeshGeo(faces.toArray(new IFace[faces.size()]));
	mesh.removeDuplicates();
	return mesh;
    }
    
    /** join other meshes into the current one and remove duplicated edges and vertices */
    public IMeshGeo join(IMeshGeo[] meshes){ return join(meshes, IConfig.tolerance); }
    
    /** join other meshes into the current one and remove duplicated edges and vertices */
    public IMeshGeo join(IMeshGeo[] meshes, double tolerance){
	synchronized(IG.lock){ // IG.lock or IG.dynamicServer() ?
	    for(int i=0; i<meshes.length; i++){
		for(int j=0; j<meshes[i].vertexNum(); j++){
		    if(!vertices.contains(meshes[i].vertex(j))){
			//vertices.add(meshes[i].vertex(j));
			addVertex(meshes[i].vertex(j));
		    }
		}
		for(int j=0; j<meshes[i].edgeNum(); j++){
		    if(!edges.contains(meshes[i].edge(j))) edges.add(meshes[i].edge(j));
		}
		for(int j=0; j<meshes[i].faceNum(); j++){
		    if(!faces.contains(meshes[i].face(j))) faces.add(meshes[i].face(j));
		}
	    }
	    removeDuplicates(tolerance);
	}
	return this;
    }
    
    
    
    /** remove duplicated vertices and edges */
    public IMeshGeo removeDuplicates(){ return removeDuplicates(IConfig.tolerance); }
    
    /** remove duplicated vertices and edges */
    public IMeshGeo removeDuplicates(double tolerance){
	
	ArrayList<IVertex> vertices2 = new ArrayList<IVertex>(this.vertices);
	ArrayList<IVertex> removedVertex = new ArrayList<IVertex>();
	ArrayList<IVertex> replacingVertex = new ArrayList<IVertex>();
	
	for(int i=0; i<vertices2.size()-1; i++){
	    IVertex v1 = vertices2.get(i);
	    for(int j=i+1; j<vertices2.size(); j++){
		IVertex v2 = vertices2.get(j);
		if(v1.eq(v2, tolerance)){
		    //v2.replaceVertex(v1); // this is buggy
		    vertices2.remove(j);
		    j--;
		    removedVertex.add(v2);
		    replacingVertex.add(v1);
		}
	    }
	}
	
	for(int i=0; i<vertices2.size(); i++){
	    vertices2.get(i).edges.clear();
	    vertices2.get(i).faces.clear();
	    vertices2.get(i).linkedVertices.clear();
	}
	
	ArrayList<IEdge> edges2 = new ArrayList<IEdge>(this.edges);
	ArrayList<IEdge> removedEdge = new ArrayList<IEdge>();
	ArrayList<IEdge> replacingEdge = new ArrayList<IEdge>();
	for(int i=0; i<edges2.size()-1; i++){
	    IEdge e1 = edges2.get(i);
	    for(int j=i+1; j<edges2.size(); j++){
		IEdge e2 = edges2.get(j);
		
		if(e1.eq(e2, tolerance)){
		    edges2.remove(j);
		    j--;
		    removedEdge.add(e2);
		    replacingEdge.add(e1);
		}
	    }
	}
	
	if(removedVertex.size()==0 && removedEdge.size()==0){
	    return this; // no duplicates
	}
	
	for(int i=0; i<edges2.size(); i++){
	    if( removedVertex.contains( edges2.get(i).vertices[0] ) ){
		edges2.get(i).vertices[0] = replacingVertex.get(removedVertex.indexOf( edges2.get(i).vertices[0] ));
	    }
	    if( removedVertex.contains( edges2.get(i).vertices[1] ) ){
		edges2.get(i).vertices[1] = replacingVertex.get(removedVertex.indexOf( edges2.get(i).vertices[1] ));
	    }
	    edges2.get(i).faces.clear();
	    
	    edges2.get(i).vertices[0].addEdge(edges2.get(i));
	    edges2.get(i).vertices[1].addEdge(edges2.get(i));
	}
	
	ArrayList<IFace> faces2 = new ArrayList<IFace>();
	for(int i=0; i<faces.size(); i++){
	    IEdge[] faceEdges = new IEdge[faces.get(i).edgeNum()];
	    for(int j=0; j<faces.get(i).edgeNum(); j++){
		if(removedEdge.contains(faces.get(i).edges[j])){
		    faceEdges[j] = replacingEdge.get(removedEdge.indexOf(faces.get(i).edges[j]));
		}
		else{ faceEdges[j] =faces.get(i).edges[j]; }
		//IG.err("face "+i+", "+j+": "+faceEdges[j].vertices[0].pos+" - "+faceEdges[j].vertices[1].pos);
	    }
	    faces2.add(new IFace(faceEdges));
	}
	
	synchronized(IG.lock){ // IG.lock or IG.dynamicServer() ?
	    vertices.clear();
	    edges.clear();
	    faces = faces2;
	    for(int i=0; i<faces2.size(); i++){
		IFace f = faces2.get(i);
		for(int j=0; j<f.vertices.length; j++){
		    if(!vertices.contains(f.vertices[j])){
			//vertices.add(f.vertices[j]);
			addVertex(f.vertices[j]);
		    }
		}
		for(int j=0; j<f.edges.length; j++){
		    if(!edges.contains(f.edges[j])) edges.add(f.edges[j]);
		}
	    }
	}
	return this;
    }
    
    
    /** remove duplicated vertices and edges */
    //public static IMeshGeo unify(IMeshGeo mesh){ return unify(mesh, IConfig.tolerance); }
    
    /** remove duplicated vertices and edges */
    /*
    public static IMeshGeo unify(IMeshGeo mesh, double tolerance){
	ArrayList<IVertex> vertices = new ArrayList<IVertex>(mesh.vertices);
	ArrayList<IEdge> edges = new ArrayList<IEdge>(mesh.edges);
	ArrayList<IFace> faces = new ArrayList<IFace>(mesh.faces);

	ArrayList<IVertex> removedVertex = new ArrayList<IVertex>();
	ArrayList<IVertex> replacingVertex = new ArrayList<IVertex>();

	for(int i=0; i<vertices.size()-1; i++){
	    IVertex v1 = vertices.get(i);
	    for(int j=i+1; j<vertices.size(); j++){
		IVertex v2 = vertices.get(j);
		if(v1.eq(v2, tolerance)){
		    //v2.replaceVertex(v1); // this is buggy
		    vertices.remove(j);
		    j--;
		    removedVertex.add(v2);
		    replacingVertex.add(v1);
		    //IG.err("replacing v2="+v2.pos+", v1="+v1.pos); //
		}
	    }
	}
	
	for(int i=0; i<vertices.size(); i++){
	    vertices.get(i).edges.clear();
	    vertices.get(i).faces.clear();
	    vertices.get(i).linkedVertices.clear();
	}
	
	ArrayList<IEdge> removedEdge = new ArrayList<IEdge>();
	ArrayList<IEdge> replacingEdge = new ArrayList<IEdge>();
	for(int i=0; i<edges.size()-1; i++){
	    IEdge e1 = edges.get(i);
	    for(int j=i+1; j<edges.size(); j++){
		IEdge e2 = edges.get(j);
		if(e1.eq(e2, tolerance)){
		    edges.remove(j);
		    j--;
		    removedEdge.add(e2);
		    replacingEdge.add(e1);
		}
	    }
	}
	for(int i=0; i<edges.size(); i++){
	    if( removedVertex.contains( edges.get(i).vertices[0] ) ){
		edges.get(i).vertices[0] = replacingVertex.get(removedVertex.indexOf( edges.get(i).vertices[0] ));
	    }
	    if( removedVertex.contains( edges.get(i).vertices[1] ) ){
		edges.get(i).vertices[1] = replacingVertex.get(removedVertex.indexOf( edges.get(i).vertices[1] ));
	    }
	    edges.get(i).faces.clear();
	    edges.get(i).vertices[0].addEdge(edges.get(i));
	    edges.get(i).vertices[1].addEdge(edges.get(i));
	}
	
	IFace[] newFaces = new IFace[faces.size()];
	for(int i=0; i<faces.size(); i++){
	    IEdge[] faceEdges = new IEdge[faces.get(i).edgeNum()];
	    for(int j=0; j<faces.get(i).edgeNum(); j++){
		if(removedEdge.contains(faces.get(i).edges[j])){
		    faceEdges[j] = replacingEdge.get(removedEdge.indexOf(faces.get(i).edges[j]));
		}
		else{ faceEdges[j] =faces.get(i).edges[j]; }
		//IG.err("face "+i+", "+j+": "+faceEdges[j].vertices[0].pos+" - "+faceEdges[j].vertices[1].pos);
	    }
	    newFaces[i] = new IFace(faceEdges);
	}
	return new IMeshGeo(newFaces);
    }
    */
    
    
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
