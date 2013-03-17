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
    Class of an agent with a line with start and end points.
    @author Satoru Sugihara
*/

public class ILineAgentGeo extends IDynamicsBase{
    
    /** end point of line */
    public IVec pos;
    /** start point of line */
    public IVec root;
    
    public ICurve line;
    
    /** current position, i.e. end point of line */
    public IVec pos(){ return pos; }
    /** alias of pos() */
    public IVec end(){ return pos(); }
    /** alias of pos() */
    public IVec pt2(){ return pos(); }
    
    /** previous position, i.e. start/root point of line */
    public IVec root(){ return root; }
    /** alias of root() */
    public IVec start(){ return root(); }
    /** alias of root() */
    public IVec prevPos(){ return root(); }
    /** alias of root() */
    public IVec pt1(){ return root(); }
    
    /** direction from root to pos */
    public IVec dir(){ return pos.dif(root); }
    /** alias of dir()*/
    public IVec dif(){ return dir(); }
    
    
    public ILineAgentGeo(IVec rootPos, IVec lineDir){
	root = rootPos;
	pos = rootPos.cp(lineDir);
    }
    
    public ILineAgentGeo(IVec rootPos){
	this(rootPos, new IVec(0,1,0)); // default dir
    }
    
    public ILineAgentGeo(){
	this(new IVec(0,0,0), new IVec(0,1,0)); // default pos, default dir
    }
    
    public ICurve drawLine(){
	ICurve l = new ICurve(root, pos);
	if(parent()!=null && parent().attr()!=null) l.attr(parent().attr());
	return l;
    }
    
    public ICurve line(){ return line; }

    public void hide(){ line.hide(); }
    public void show(){ line.show(); }
    
    public void update(){
	if(line==null){ // should be in the first time
	    line = drawLine();
	}
    }
    
}

