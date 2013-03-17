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

public class ILineAgent extends IAgent{
    
    public ILineAgentGeo line;
    
    /** current position, i.e. end point of line */
    public IVec pos(){ return line.pos; }
    /** alias of pos() */
    public IVec end(){ return pos(); }
    /** alias of pos() */
    public IVec pt2(){ return pos(); }
    
    /** previous position, i.e. start/root point of line */
    public IVec root(){ return line.root; }
    /** alias of root() */
    public IVec start(){ return root(); }
    /** alias of root() */
    public IVec prevPos(){ return root(); }
    /** alias of root() */
    public IVec pt1(){ return root(); }
    
    /** direction from root to pos */
    public IVec dir(){ return line.dir(); }
    /** alias of dir()*/
    public IVec dif(){ return dir(); }
    
    
    public ILineAgent(IVec rootPos, IVec lineDir){
	super();
	line = new ILineAgentGeo(rootPos, lineDir);
	addDynamics(line);
    }
    
    public ILineAgent(IVec rootPos){
	this(rootPos, new IVec(0,1,0)); // default dir
    }
    
    public ILineAgent(){
	this(new IVec(0,0,0), new IVec(0,1,0)); // default pos, default dir
    }
    
    public ICurve line(){ return line.line(); }
    
    public ILineAgent hide(){ line.hide(); return this; }
    public ILineAgent show(){ line.show(); return this; }
    
    
}

