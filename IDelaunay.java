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

import java.util.*;

import igeo.gui.*;

/**
   Class to calculate Delaunay triangulation out of 3D points.
   
   @author Satoru Sugihara
*/
public class IDelaunay {
    
    public static double maxDistToCheck = -1;
    
    /**
       Getting delaunay tetrahedron cells out of array of 3D points
       @return array of tetrahedron, which consist of array of 4 points of IVec
    */
    public static IVec[][] getTetrahedron(IVec[] pts){
	if(pts.length<=4){
	    return new IVec[][]{ pts };
	}
	
	ArrayList<IVec[]> tetra = new ArrayList<IVec[]>();
	
	for(int i=0; i<pts.length-3; i++){
	    IOut.debug(40, (i+1)+"/"+(pts.length-3)); //
	    for(int j=i+1; j<pts.length-2; j++){
		
		if(maxDistToCheck < 0 ||
		   maxDistToCheck >= 0 && pts[i].dist(pts[j]) <= maxDistToCheck){
		    
		    for(int k=j+1; k<pts.length-1; k++){
			
			if(maxDistToCheck < 0 ||
			   maxDistToCheck >= 0 && pts[i].dist(pts[k]) <= maxDistToCheck){
			    
			    for(int l=k+1; l<pts.length; l++){
				
				if(maxDistToCheck < 0 ||
				   maxDistToCheck >= 0 && pts[i].dist(pts[l]) <= maxDistToCheck){
				    
				    boolean anyInside=false;
				    for(int m=0; m<pts.length && !anyInside; m++){
					
					//IOut.p("checking "+i+","+j+","+k+","+l);
					if(m!=i && m!=j && m!=k && m!=l){
					    // maxDist check?
					    
					    if(maxDistToCheck < 0 ||
					       maxDistToCheck >= 0 && pts[i].dist(pts[m]) <= maxDistToCheck){
						
						if(isInsideCircumsphere(pts[m],pts[i],pts[j],pts[k],pts[l])){
						    anyInside=true;
						    //IOut.p(m+" is inside");
						}
					    }
					}
				    }
				    if(!anyInside){ // nothing is inside
					//IOut.p(i+","+j+","+k+","+l+" has no inside pt");
					
					tetra.add(new IVec[]{ pts[i], pts[j], pts[k], pts[l] });
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return tetra.toArray(new IVec[tetra.size()][]);
    }

    /**
       Getting delaunay tetrahedron cells out of array of 3D points
       @return array of tetrahedron, which consist of array of 4 points of IVec
    */
    public static IVecI[][] getTetrahedron(IVecI[] pts){
	if(pts.length<=4){
	    return new IVecI[][]{ pts };
	}
	
	ArrayList<IVecI[]> tetra = new ArrayList<IVecI[]>();
	
	for(int i=0; i<pts.length-3; i++){
	    IOut.debug(40, (i+1)+"/"+(pts.length-3)); //
	    for(int j=i+1; j<pts.length-2; j++){
		
		if(maxDistToCheck < 0 ||
		   maxDistToCheck >= 0 && pts[i].get().dist(pts[j]) <= maxDistToCheck){
		    
		    for(int k=j+1; k<pts.length-1; k++){
			
			if(maxDistToCheck < 0 ||
			   maxDistToCheck >= 0 && pts[i].get().dist(pts[k]) <= maxDistToCheck){
			    
			    for(int l=k+1; l<pts.length; l++){
				
				if(maxDistToCheck < 0 ||
				   maxDistToCheck >= 0 && pts[i].get().dist(pts[l]) <= maxDistToCheck){
				    
				    boolean anyInside=false;
				    for(int m=0; m<pts.length && !anyInside; m++){
					
					//IOut.p("checking "+i+","+j+","+k+","+l);
					if(m!=i && m!=j && m!=k && m!=l){
					    // maxDist check?
					    
					    if(maxDistToCheck < 0 ||
					       maxDistToCheck >= 0 && pts[i].get().dist(pts[m]) <= maxDistToCheck){
						
						if(isInsideCircumsphere(pts[m].get(),pts[i].get(),pts[j].get(),pts[k].get(),pts[l].get())){
						    anyInside=true;
						    //IOut.p(m+" is inside");
						}
					    }
					}
				    }
				    if(!anyInside){ // nothing is inside
					//IOut.p(i+","+j+","+k+","+l+" has no inside pt");
					
					tetra.add(new IVecI[]{ pts[i], pts[j], pts[k], pts[l] });
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return tetra.toArray(new IVecI[tetra.size()][]);
    }

    public static IVec circumsphereCenter(IVec p1, IVec p2, IVec p3, IVec p4){
	
	double a = IMatrix4.determinant(p1.x, p1.y, p1.z, 1,
					p2.x, p2.y, p2.z, 1,
					p3.x, p3.y, p3.z, 1,
					p4.x, p4.y, p4.z, 1);

	if(a==0){
	    IOut.err("4 points are on a plane"); 
	    return null;
	}
	
	double d1 = p1.len2();
	double d2 = p2.len2();
	double d3 = p3.len2();
	double d4 = p4.len2();
	
	double Dx = IMatrix4.determinant(d1, p1.y, p1.z, 1,
					 d2, p2.y, p2.z, 1,
					 d3, p3.y, p3.z, 1,
					 d4, p4.y, p4.z, 1);
	double Dy = -IMatrix4.determinant(d1, p1.x, p1.z, 1,
					  d2, p2.x, p2.z, 1,
					  d3, p3.x, p3.z, 1,
					  d4, p4.x, p4.z, 1);
	double Dz = IMatrix4.determinant(d1, p1.x, p1.y, 1,
					 d2, p2.x, p2.y, 1,
					 d3, p3.x, p3.y, 1,
					 d4, p4.x, p4.y, 1);
	
	a*=2;
	return new IVec(Dx/a, Dy/a, Dz/a);
	
	/*
	IVec dir1 = p2.dif(p1);
	IVec dir2 = p3.dif(p1);
	IVec dir3 = p4.dif(p1);
	IVec mid1 = p2.mid(p1);
	IVec mid2 = p3.mid(p1);
	IVec mid3 = p4.mid(p1);
	
	IVec[] itxnLine = IVec.intersectPlane(dir1,mid1,dir2,mid2);
	if(itxnLine==null){
	    IOut.err("plane intersection failed: most likely two edges are parallel");
	    return null; // dir1/dir2 parallel, no circumsphere
	}
	//if(Math.abs(dir3.dot(itxnLine[0]))/(dir3.len()*itxnLine[0].len()) < IConfig.tolerance){
	if(dir3.dot(itxnLine[0])==0){
	    IOut.err("plane line intersection failed: most likely fourth point is on a plane of 3 poitns"); 
	    return null;  // plane and line are parallel
	}
	return IVec.intersectPlaneAndLine(dir3, mid3, itxnLine[0], itxnLine[1]);
	*/
    }
    
    public static boolean isInsideCircumsphere(IVec pt, IVec tetraPt1, IVec tetraPt2, IVec tetraPt3, IVec tetraPt4){
	
	IVec center = circumsphereCenter(tetraPt1, tetraPt2, tetraPt3, tetraPt4);
	if(center==null){
	    //IOut.p("center is null");
	    return true; // suppose circumsphere is infinetely large
	}
	return center.dist(pt) < center.dist(tetraPt1);
	
	// todo figure out the case when several circumspheres are same
	//return center.dist(pt) < center.dist(tetraPt1) - IConfig.tolerance;
    }
    
    /**
       pick 3 point triangle out of 4 point tetrahedron with right-screw normal order
     */
    public static IVec[] getTriangleFace(IVec[] tetra, int index){
	IVec[] tri = new IVec[3];
	for(int i=0, j=0; i<tetra.length; i++){
	    if(i!=index){ tri[j++] = tetra[i]; }
	}
	if( IVec.nml(tri[0],tri[1],tri[2]).dot(tetra[index].dif(tri[0])) <= 0){
	    return tri;
	}
	IVec tmp = tri[1];
	tri[1] = tri[2];
	tri[2] = tmp;
	return tri;
    }
    
    
    /**
       Getting array of triangle points on the surface (hull) of delaunay tetrahedron cells out of array of 3D points
       @return array of triangle points, which consist of array of 3 points of IVec
    */
    public static IVec[][] getSurfaceTriangles(IVec[] pts){
	IVec[][] tetra = getTetrahedron(pts);
	if(tetra.length == 1){
	    if(tetra[0].length<3) return null; // too less pts
	    if(tetra[0].length==3) return tetra; // just one triangle
	}
	
	boolean[] hasPair = new boolean[tetra.length*4];
	ArrayList<IVec[]> triangles = new ArrayList<IVec[]>();
	for(int i=0; i<tetra.length; i++){
	    for(int j=0; j<tetra[i].length; j++){
		if(!hasPair[i*4+j]){
		    //IVec[] tri1 = new IVec[3];
		    //for(int k=0, l=0; k<tetra[i].length; k++){ if(j!=k){ tri1[l++] = tetra[i][k]; } }
		    IVec[] tri1 = getTriangleFace(tetra[i],j);
		    
		    for(int k=i+1; k<tetra.length && !hasPair[i*4+j]; k++){
			for(int l=0; l<tetra[k].length && !hasPair[i*4+j]; l++){
			    //IVec[] tri2 = new IVec[3];
			    //for(int m=0, n=0; m<tetra[k].length; m++){ if(l!=m){ tri2[n++] = tetra[k][m]; } }
			    IVec[] tri2 = getTriangleFace(tetra[k],l);
			    if(isTriangleSame(tri1,tri2)){
				hasPair[i*4+j] = hasPair[k*4+l] = true;
			    }
			}
		    }
		    if(!hasPair[i*4+j]){
			triangles.add(tri1);
		    }
		}
	    }
	}
	return triangles.toArray(new IVec[triangles.size()][]);
    }
    
    /**
       Getting array of triangle points on the surface (hull) of delaunay tetrahedron cells out of array of 3D points
       @return array of triangle points, which consist of array of 3 points of IVec
    */
    public static IVecI[][] getSurfaceTriangles(IVecI[] pts){
	IVecI[][] tetra = getTetrahedron(pts);
	if(tetra.length == 1){
	    if(tetra[0].length<3) return null; // too less pts
	    if(tetra[0].length==3) return tetra; // just one triangle
	}
	
	boolean[] hasPair = new boolean[tetra.length*4];
	ArrayList<IVecI[]> triangles = new ArrayList<IVecI[]>();
	for(int i=0; i<tetra.length; i++){
	    for(int j=0; j<tetra[i].length; j++){
		if(!hasPair[i*4+j]){
		    IVecI[] tri1 = new IVecI[3];
		    for(int k=0, l=0; k<tetra[i].length; k++){
			if(j!=k){ tri1[l++] = tetra[i][k]; }
		    }
		    for(int k=i+1; k<tetra.length && !hasPair[i*4+j]; k++){
			for(int l=0; l<tetra[k].length && !hasPair[i*4+j]; l++){
			    IVecI[] tri2 = new IVecI[3];
			    for(int m=0, n=0; m<tetra[k].length; m++){
				if(l!=m){ tri2[n++] = tetra[k][m]; }
			    }
			    if(isTriangleSame(tri1,tri2)){
				hasPair[i*4+j] = hasPair[k*4+l] = true;
			    }
			}
		    }
		    if(!hasPair[i*4+j]){
			triangles.add(tri1);
		    }
		}
	    }
	}
	return triangles.toArray(new IVecI[triangles.size()][]);
    }
    
    /**
       Getting array of edge points on the surface of delaunay tetrahedron cells out of array of 3D points
       @return array of edge points, which consist of array of 2 points of IVec
    */
    public static IVec[][] getSurfaceEdges(IVec[] pts){
	IVec[][] tri = getSurfaceTriangles(pts);
	if(tri.length == 1){
	    if(tri[0].length<2) return null; // too less pts
	    if(tri[0].length==2) return tri; // just one triangle
	}
	
	ArrayList<IVec[]> edges = new ArrayList<IVec[]>();
	for(int i=0; i<tri.length; i++){
	    for(int j=0; j<tri[i].length; j++){
		boolean exists=false;
		for(int k=0; k<edges.size() && !exists; k++){
		    if(isEdgeSame(edges.get(k), tri[i][j], tri[i][(j+1)%tri[i].length])){
			exists=true;
		    }
		}
		if(!exists){
		    edges.add(new IVec[]{ tri[i][j], tri[i][(j+1)%tri[i].length] });
		}
	    }
	}
	return edges.toArray(new IVec[edges.size()][]);
    }
    
    /**
       Getting array of edge points on the surface of delaunay tetrahedron cells out of array of 3D points
       @return array of edge points, which consist of array of 2 points of IVec
    */
    public static IVecI[][] getSurfaceEdges(IVecI[] pts){
	IVecI[][] tri = getSurfaceTriangles(pts);
	if(tri.length == 1){
	    if(tri[0].length<2) return null; // too less pts
	    if(tri[0].length==2) return tri; // just one triangle
	}
	ArrayList<IVecI[]> edges = new ArrayList<IVecI[]>();
	for(int i=0; i<tri.length; i++){
	    for(int j=0; j<tri[i].length; j++){
		boolean exists=false;
		for(int k=0; k<edges.size() && !exists; k++){
		    if(isEdgeSame(edges.get(k), tri[i][j], tri[i][(j+1)%tri[i].length])){
			exists=true;
		    }
		}
		if(!exists){
		    edges.add(new IVecI[]{ tri[i][j], tri[i][(j+1)%tri[i].length] });
		}
	    }
	}
	return edges.toArray(new IVecI[edges.size()][]);
    }
    
    /**
       Getting a polygon mesh of the outside surface of delaunay tetrahedron cells out of array of 3D points
       @return a polygon mesh
    */
    public static IMesh getSurfaceMesh(IVec[] pts){
	IVec[][] tri = getSurfaceTriangles(pts);
	if(tri==null) return null; // too less pts
	
	IVertex[] vtx = new IVertex[pts.length];
	List<IVec> plist = Arrays.asList(pts);
	IFace[] fcs = new IFace[tri.length];
	for(int i=0; i<tri.length; i++){
	    int idx1 = plist.indexOf(tri[i][0]);
	    int idx2 = plist.indexOf(tri[i][1]);
	    int idx3 = plist.indexOf(tri[i][2]);
	    if(vtx[idx1]==null) vtx[idx1] = new IVertex(pts[idx1]);
	    if(vtx[idx2]==null) vtx[idx2] = new IVertex(pts[idx2]);
	    if(vtx[idx3]==null) vtx[idx3] = new IVertex(pts[idx3]);
	    fcs[i] = new IFace(vtx[idx1],vtx[idx2],vtx[idx3]);
	}
	return new IMesh(fcs);
    }
    
    /**
       Getting a polygaon mesh of the outside surface of delaunay tetrahedron cells out of array of 3D points
       @return a polygon mesh
    */
    public static IMesh getSurfaceMesh(IVecI[] pts){
	IVecI[][] tri = getSurfaceTriangles(pts);
	if(tri==null) return null; // too less pts
	
	IVertex[] vtx = new IVertex[pts.length];
	List<IVecI> plist = Arrays.asList(pts);
	IFace[] fcs = new IFace[tri.length];
	for(int i=0; i<tri.length; i++){
	    int idx1 = plist.indexOf(tri[i][0]);
	    int idx2 = plist.indexOf(tri[i][1]);
	    int idx3 = plist.indexOf(tri[i][2]);
	    if(vtx[idx1]==null) vtx[idx1] = new IVertex(pts[idx1]);
	    if(vtx[idx2]==null) vtx[idx2] = new IVertex(pts[idx2]);
	    if(vtx[idx3]==null) vtx[idx3] = new IVertex(pts[idx3]);
	    fcs[i] = new IFace(vtx[idx1],vtx[idx2],vtx[idx3]);
	}
	return new IMesh(fcs);
    }
    
    /**
       Getting edges of delaunay tetrahedron out of array of points.
       @return array of pairs of points, which represent edges.
    */
    public static IVec[][] getEdges(IVec[] pts){
        IVec[][] tet = getTetrahedron(pts);
        ArrayList<IVec[]> pairs = new ArrayList<IVec[]>();
        for(int i=0; i<tet.length; i++){
            for(int j=0; j<tet[i].length; j++){
                boolean exists=false;
                for(int k=0; k<pairs.size()&&!exists; k++){
                    if(pairs.get(k)[0] == tet[i][j] &&
                       pairs.get(k)[1] == tet[i][(j+1)%tet[i].length] ||
                       pairs.get(k)[1] == tet[i][j] &&
                       pairs.get(k)[0] == tet[i][(j+1)%tet[i].length]){
                        exists=true;
                    }
                }
                if(!exists){
                    pairs.add(new IVec[]{ tet[i][j], tet[i][(j+1)%tet[i].length] });
                }
            }
        }
        return pairs.toArray(new IVec[pairs.size()][]);
    }


    /**
       Getting edges of delaunay tetrahedron out of array of points.
       @return array of pairs of points, which represent edges.
    */
    public static IVecI[][] getEdges(IVecI[] pts){
        IVecI[][] tet = getTetrahedron(pts);
        ArrayList<IVecI[]> pairs = new ArrayList<IVecI[]>();
        for(int i=0; i<tet.length; i++){
            for(int j=0; j<tet[i].length; j++){
                boolean exists=false;
                for(int k=0; k<pairs.size()&&!exists; k++){
                    if(pairs.get(k)[0] == tet[i][j] &&
                       pairs.get(k)[1] == tet[i][(j+1)%tet[i].length] ||
                       pairs.get(k)[1] == tet[i][j] &&
                       pairs.get(k)[0] == tet[i][(j+1)%tet[i].length]){
                        exists=true;
                    }
                }
                if(!exists){
                    pairs.add(new IVecI[]{ tet[i][j], tet[i][(j+1)%tet[i].length] });
                }
            }
        }
        return pairs.toArray(new IVecI[pairs.size()][]);
    }

    
    /** check if two triangle points are same in terms of same instance
	@param tri1 array of 3 points
	@param tri2 array of 3 points
     */
    public static boolean isTriangleSame(IVecI[] tri1, IVecI[] tri2){
	if(tri1[0]==tri2[0]){
	    if(tri1[1]==tri2[1]) return tri1[2]==tri2[2];
	    if(tri1[1]==tri2[2]) return tri1[2]==tri2[1];
	    return false;
	}
	if(tri1[0]==tri2[1]){
	    if(tri1[1]==tri2[2]) return tri1[2]==tri2[0];
	    if(tri1[1]==tri2[0]) return tri1[2]==tri2[2];
	    return false;
	}
	if(tri1[0]==tri2[2]){
	    if(tri1[1]==tri2[0]) return tri1[2]==tri2[1];
	    if(tri1[1]==tri2[1]) return tri1[2]==tri2[0];
	    return false;
	}
	return false;
    }

    
    /** check if two edge points are same in terms of same instance
	@param edge1 array of 2 points
	@param edge2 array of 2 points
     */
    public static boolean isEdgeSame(IVecI[] edge1, IVecI[] edge2){
	if(edge1[0]==edge2[0]) return edge1[1]==edge2[1];
	if(edge1[0]==edge2[1]) return edge1[1]==edge2[0];
	return false;
    }
    
    /** check if two edge points are same in terms of same instance
	@param edge1 array of 2 points
	@param edge2Pt1 point of second edge
	@param edge2Pt2 point of second edge
     */
    public static boolean isEdgeSame(IVecI[] edge1, IVecI edge2Pt1, IVecI edge2Pt2){
	if(edge1[0]==edge2Pt1) return edge1[1]==edge2Pt2;
	if(edge1[0]==edge2Pt2) return edge1[1]==edge2Pt1;
	return false;
    }
    
    /** check if two edge points are same in terms of same instance
	@param edge1Pt1 point of first edge
	@param edge1Pt2 point of first edge
	@param edge2Pt1 point of second edge
	@param edge2Pt2 point of second edge
     */
    public static boolean isEdgeSame(IVecI edge1Pt1, IVecI edge1Pt2, IVecI edge2Pt1, IVecI edge2Pt2){
	if(edge1Pt1==edge2Pt1) return edge1Pt2==edge2Pt2;
	if(edge1Pt1==edge2Pt2) return edge1Pt2==edge2Pt1;
	return false;
    }
    
}
