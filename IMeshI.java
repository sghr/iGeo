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
   Abstract interface of polygon mesh.
   
   @author Satoru Sugihara
*/
public interface IMeshI extends ITransformable{

    public IMeshGeo get();

    public IMeshI dup();

    public boolean isValid();
    
    public int vertexNum();
    public int edgeNum();
    public int faceNum();
    
    public int vertexNum(ISwitchE e);
    public int edgeNum(ISwitchE e);
    public int faceNum(ISwitchE e);
    
    public IIntegerI vertexNum(ISwitchR r);
    public IIntegerI edgeNum(ISwitchR r);
    public IIntegerI faceNum(ISwitchR r);
    
    public IVertex vertex(int i);
    public IEdge edge(int i);
    public IFace face(int i);
    
    public IVertex vertex(IIntegerI i);
    public IEdge edge(IIntegerI i);
    public IFace face(IIntegerI i);
    
    public IMeshI deleteVertex(int i);
    public IMeshI deleteEdge(int i);
    public IMeshI deleteFace(int i);
    
    public IMeshI deleteVertex(IIntegerI i);
    public IMeshI deleteEdge(IIntegerI i);
    public IMeshI deleteFace(IIntegerI i);
    
    public IMeshI deleteVertex(IVertex v);
    public IMeshI deleteEdge(IEdge e);
    public IMeshI deleteFace(IFace f);
    
    
    /** center point of mesh */
    public IVecI center();
    
    
    /** return all vertices */
    public ArrayList<IVertex> vertices();
    /** return all edges */
    public ArrayList<IEdge> edges();
    /** return all faces */
    public ArrayList<IFace> faces();
    
    public IMeshI close();
    public boolean isClosed();
    
    
    /** cp() is alias of dup() */
    public IMeshI cp();

    /** cp() is alias of dup().add() */
    public IMeshI cp(double x, double y, double z);
    public IMeshI cp(IDoubleI x, IDoubleI y, IDoubleI z);
    public IMeshI cp(IVecI v);
    
}
