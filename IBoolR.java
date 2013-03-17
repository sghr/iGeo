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
   Reference class of boolean to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public class IBoolR extends IParameterObject implements IBoolI, IReferenceParameter{
    
    protected IBoolOp op;
    
    public IBoolR(IBoolOp v){ op=v; }
    public IBoolR(boolean v){ op = new IBool(v); }
    
    public IBoolR(IServerI s, IBoolOp v){ super(s); op=v; }
    public IBoolR(IServerI s, boolean v){ super(s); op = new IBool(v); }
    
    
    public boolean x(){ return op.get().x; }
    public IBool get(){ return op.get(); }
    public IBoolR getX(){ return this; }
    public IBoolR dup(){ return new IBoolR(op); }
    public IBoolR cp(){ return dup(); }
    
    public IBoolOp operator(){ return op; } // for viewer 
    
    public IBoolR set(IBoolI v){ op=v; return this; }
    public IBoolR set(boolean v){ op=new IBool(v); return this; }
    
    public IBoolR and(IBoolI v){ op = new And(op,v); return this; }
    public IBoolR and(boolean v){ op = new And(op,new IBool(v)); return this; }
    public IBoolR or(IBoolI v){ op = new Or(op,v); return this; }
    public IBoolR or(boolean v){ op = new Or(op,new IBool(v)); return this; }
    public IBoolR not(){ op = new Not(op); return this; }
    
    public boolean eq(IBoolI v){ return x()==v.x(); }
    //public IBoolR eqR(IBoolI v){ return new IBoolR(new Eq(op,v)); }
    public boolean eq(ISwitchE e, IBoolI v){ return eq(v); }
    public IBoolR eq(ISwitchR r, IBoolI v){ return new IBoolR(new Eq(op,v)); }
    
    
    static public class And extends IParameterObject implements IBoolOp{
	public IBoolOp v1, v2;
	And(IBoolOp v1, IBoolOp v2){ this.v1=v1; this.v2=v2; }
	public boolean x(){ return v1.x()&v2.x(); }
	public IBool get(){ return v1.get().and(v2.get()); }
    }
    
    static public class Or extends IParameterObject implements IBoolOp{
	public IBoolOp v1, v2;
	Or(IBoolOp v1, IBoolOp v2){ this.v1=v1; this.v2=v2; }
	public boolean x(){ return v1.x()|v2.x(); }
	public IBool get(){ return v1.get().or(v2.get()); }
    }
    
    static public class Not extends IParameterObject implements IBoolOp{
	public IBoolOp v;
	Not(IBoolOp v){ this.v=v; }
	public boolean x(){ return !v.x(); }
	public IBool get(){ return v.get().not(); }
    }
    
    static public class Eq extends IParameterObject implements IBoolOp{
	public IBoolOp v1, v2;
	Eq(IBoolOp v1, IBoolOp v2){ this.v1=v1; this.v2=v2; }
	public boolean x(){ return v1.x()==v2.x(); }
	public IBool get(){ return v1.get().eq((Ir)null,v2.get()); }
    }
    
}
