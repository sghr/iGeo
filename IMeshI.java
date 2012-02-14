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

/**
   Abstract interface of polygon mesh.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public interface IMeshI{

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
    
    
    public IMeshI close();
    public boolean isClosed();

}
