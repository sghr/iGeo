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

/**
   3D vector filed defined by normal vector of mesh vertices
   
   @author Satoru Sugihara
*/

public class ILinkedNodeMeshField extends I3DField{
    
    public ILinkedNodeMeshField(IMeshI m){ super(new ILinkedNodeMeshFieldGeo(m)); }
    public static class ILinkedNodeMeshFieldGeo extends IMeshFieldGeo{
	public ILinkedDataAgent<IVecI>[] linkedAgents;
	public IVectorObject[] vectorObjects; // to visualize
	public ILinkedNodeMeshFieldGeo(IMeshI m){ super(m); initField(); }
	public void initField(){
	    @SuppressWarnings("unchecked")
	    ILinkedDataAgent<IVecI>[] agents =  ILinkedDataAgent.<IVecI>create(mesh);
	    linkedAgents = agents;
	    for(int i=0; i<linkedAgents.length; i++){
		linkedAgents[i].setData(new IVec());
	    }
	}
	
	public ILinkedNodeMeshFieldGeo setForce(int vertexIndex, IVecI vec){
	    if(linkedAgents==null || vertexIndex<0 || vertexIndex>=linkedAgents.length) return this;
	    linkedAgents[vertexIndex].setData(vec);
	    return this;
	}
	
	public IVecI getForce(int vertexIndex){
	    if(linkedAgents==null || vertexIndex<0 || vertexIndex>=linkedAgents.length) return new IVec();
	    return linkedAgents[vertexIndex].getData();
	}
	
	public ILinkedNodeMeshFieldGeo addForce(int vertexIndex, IVecI vec){
	    if(linkedAgents==null || vertexIndex<0 || vertexIndex>=linkedAgents.length) return this;
	    linkedAgents[vertexIndex].addData(vec);
	    return this;
	}
	/*
	public ILinkedVecAgent<IVecI> getNode(int vertexIndex){
	    if(linkedAgents==null || vertexIndex<0 || vertexIndex>=linkedAgents.length) return null;
	    return linkedAgents[vertexIndex];
        }
	*/
	
	public IVecI get(int vertexIdx){
	    return getForce(vertexIdx);
	}
	public ILinkedNodeMeshFieldGeo showVector(){
	    if(linkedAgents==null||mesh==null) return this;
	    vectorObjects = new IVectorObject[mesh.vertexNum()];
	    for(int i=0; i<mesh.vertexNum(); i++){
		vectorObjects[i] = new IVectorObject(linkedAgents[i].getData(), mesh.vertex(i));
	    }
	    return this;
	}
	
        public ILinkedNodeMeshFieldGeo linkFriction(double fric){
	    if(linkedAgents==null) return this;
	    for(int i=0; i<linkedAgents.length; i++){
		linkedAgents[i].fric(fric);
	    }
	    return this;
	}
        public ILinkedNodeMeshFieldGeo linkFriction(int vertexIndex, double fric){
	    if(linkedAgents==null || vertexIndex<0 || vertexIndex>=linkedAgents.length) return null;
	    linkedAgents[vertexIndex].fric(fric);
	    return this;
	}
    }

    
    
    public IVecI getForce(int vertexIndex){
	return ((ILinkedNodeMeshFieldGeo)field).getForce(vertexIndex);
    }

    public ILinkedNodeMeshField setForce(int vertexIndex,IVecI force){
	((ILinkedNodeMeshFieldGeo)field).setForce(vertexIndex,force);
	return this;
    }

    public ILinkedNodeMeshField addForce(int vertexIndex,IVecI force){
	((ILinkedNodeMeshFieldGeo)field).addForce(vertexIndex,force);
	return this;
    }

    public ILinkedNodeMeshField showVector(){
	((ILinkedNodeMeshFieldGeo)field).showVector();
	return this;
    }

    public ILinkedNodeMeshField linkFriction(double fric){
	((ILinkedNodeMeshFieldGeo)field).linkFriction(fric);
	return this;
    }

    public ILinkedNodeMeshField linkFriction(int vertexIndex, double fric){
	((ILinkedNodeMeshFieldGeo)field).linkFriction(vertexIndex, fric);
	return this;
    }

    
    public ILinkedNodeMeshField noDecay(){ super.noDecay(); return this; }
    public ILinkedNodeMeshField linearDecay(double threshold){ super.linearDecay(threshold); return this; }
    public ILinkedNodeMeshField linear(double threshold){ super.linear(threshold); return this; }
    public ILinkedNodeMeshField gaussianDecay(double threshold){ super.gaussianDecay(threshold); return this; }
    public ILinkedNodeMeshField gaussian(double threshold){ super.gaussian(threshold); return this; }
    public ILinkedNodeMeshField gauss(double threshold){ super.gauss(threshold); return this; }
    public ILinkedNodeMeshField decay(IDecay decay, double threshold){ super.decay(decay,threshold); return this; }
    public ILinkedNodeMeshField constantIntensity(boolean b){ super.constantIntensity(b); return this; }
    /** if bidirectional is on, field force vector is flipped when velocity of particle is going opposite */
    public ILinkedNodeMeshField bidirectional(boolean b){ super.bidirectional(b); return this; }
    
    public ILinkedNodeMeshField threshold(double t){ super.threshold(t); return this; }
    public ILinkedNodeMeshField intensity(double i){ super.intensity(i); return this; }
}
