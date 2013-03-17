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
   Visitor class to define a type of mesh to be instantiated.
   Used in IMeshGeo. To define custom mesh, define child class of this to override methods.
   
   @see IMeshGeo
   
   @author Satoru Sugihara
*/
public class IMeshType{
    //static int maxVertexNum=3;
    public static int maxVertexNum=4;
    
    public IVertex createVertex(double x, double y, double z){
	//synchronized(IG.lock){ // locking necessary?
	return new IVertex(x,y,z);
	//}
    }
    public IVertex createVertex(IVec p){
	//synchronized(IG.lock){
	return new IVertex(p);
	//}
    }
    public IVertex createVertex(IVec4 p){
	//synchronized(IG.lock){
	return new IVertex(p);
	//}
    }
    public IVertex createVertex(IVecI p){
	//synchronized(IG.lock){
	return new IVertex(p);
	//}
    }
    
    public IEdge createEdge(IVertex v1, IVertex v2){
	//synchronized(IG.lock){
	return new IEdge(v1,v2);
	//}
    }
    
    public IFace createFace(IEdge[] e){
	synchronized(IG.lock){ // locking necessary?
	    return new IFace(e);
	}
    }
    
    public IFace createFace(IEdge e1, IEdge e2, IEdge e3){
	return createFace(new IEdge[]{ e1, e2, e3 });
    }
    
    public IFace createFace(IEdge e1, IEdge e2, IEdge e3, IEdge e4){
	return createFace(new IEdge[]{ e1, e2, e3, e4 });
    }
    
    public IMeshGeo createMesh(ArrayList<IVertex> v, ArrayList<IEdge> e, ArrayList<IFace> f){
	synchronized(IG.lock){
	    return new IMeshGeo(v,e,f);
	}
    }
    
    public IMeshGeo createMesh(IVec[][] matrix, boolean triangulateDir){
	synchronized(IG.lock){
	    return new IMeshGeo(matrix, triangulateDir, this);
	}
    }
    
    public int getMaxVertexNum(){ return maxVertexNum; } // max # of vertex to create a face
    
    /*
    IFace createFace(IEdge e1, IEdge e2, IEdge e3){
	synchronized(IG.lock){
	    return new IFace(e1,e2,e3);
	}
    }
    
    IFace createFace(IVertex v1, IVertex v2, IVertex v3){
	synchronized(IG.lock){
	    return new IFace(v1,v2,v3);
	}
    }
    */
    
}
