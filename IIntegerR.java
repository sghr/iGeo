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
   Reference class of integer to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public class IIntegerR extends IParameterObject implements IIntegerI, IReferenceParameter{
    protected IIntegerOp op;
    
    public IIntegerR(IIntegerOp v){ op=v; }
    public IIntegerR(int v){ op=new IInteger(v); }
    
    public IIntegerR(IServerI s, IIntegerOp v){ super(s); op=v; }
    public IIntegerR(IServerI s, int v){ super(s); op=new IInteger(v); }
    
    public int x(){ return op.x(); }
    public IInteger get(){ return op.get(); }
    public IIntegerR getX(){ return this; }
    
    public IIntegerOp operator(){ return op; } // for viewer
    
    public IIntegerR dup(){ return new IIntegerR(op); }
    
    /** alias of dup() */
    public IIntegerR cp(){ return dup(); }
    
    /** duplicate and add */
    public IIntegerR cp(int v){ return dup().add(v); }
    /** duplicate and add */
    public IIntegerR cp(IIntegerI v){ return dup().add(v); }
    
    
    public IIntegerR set(IIntegerI u){ op=u; return this; }
    public IIntegerR set(int u){ op=new IInteger(u); return this; }
    public IIntegerR set(IDoubleI u){ op=new FromDouble(u); return this; }
    public IIntegerR set(double u){ op=new IInteger((int)u); return this; }
    
    public IIntegerR add(IIntegerI u){ op=new Add(op,u); return this; }
    public IIntegerR add(int u){ op=new Add(op,new IInteger(u)); return this; }
    public IIntegerR sub(IIntegerI u){ op=new Sub(op,u); return this; }
    public IIntegerR sub(int u){ op=new Sub(op,new IInteger(u)); return this; }
    public IIntegerR mul(IIntegerI u){ op=new Mul(op,u); return this; }
    public IIntegerR mul(int u){ op=new Mul(op,new IInteger(u)); return this; }
    public IIntegerR div(IIntegerI u){ op=new Div(op,u); return this; }
    public IIntegerR div(int u){ op=new Div(op,new IInteger(u)); return this; }
    public IIntegerR neg(){ op=new Neg(op); return this; }
    
    public IIntegerR mod(IIntegerI u){ op=new Mod(op,u); return this; }
    public IIntegerR mod(int u){ op=new Mod(op,new IInteger(u)); return this; }

    public boolean eq(int v){ return op.get().eq(v); }
    public boolean eq(IIntegerI v){ return op.get().eq(v.get()); }
    //public IBoolR eqR(IIntegerI v){ return new IBoolR(new Eq(op,v)); }
    public boolean eq(ISwitchE e, IIntegerI v){ return eq(v); }
    public IBoolR eq(ISwitchR r, IIntegerI v){ return new IBoolR(new Eq(op,v)); }
    
    
    public static class Add extends IParameterObject implements IIntegerOp{
	public IIntegerOp v1,v2;
	public Add(IIntegerOp v1, IIntegerOp v2){ this.v1=v1; this.v2=v2; }
	public int x(){ return v1.x()+v2.x(); }
	public IInteger get(){ return v1.get().add(v2.get()); }
    }
    public static class Sub extends IParameterObject implements IIntegerOp{
	public IIntegerOp v1,v2;
	public Sub(IIntegerOp v1, IIntegerOp v2){ this.v1=v1; this.v2=v2; }
	public int x(){ return v1.x()-v2.x(); }
	public IInteger get(){ return v1.get().sub(v2.get()); }
    }
    public static class Mul extends IParameterObject implements IIntegerOp{
	public IIntegerOp v1,v2;
	public Mul(IIntegerOp v1, IIntegerOp v2){ this.v1=v1; this.v2=v2; }
	public int x(){ return v1.x()*v2.x(); }
	public IInteger get(){ return v1.get().mul(v2.get()); }
    }
    public static class Div extends IParameterObject implements IIntegerOp{
	public IIntegerOp v1,v2;
	public Div(IIntegerOp v1, IIntegerOp v2){ this.v1=v1; this.v2=v2; }
	public int x(){ return v1.x()/v2.x(); }
	public IInteger get(){ return v1.get().div(v2.get()); }
    }
    public static class Neg extends IParameterObject implements IIntegerOp{
	public IIntegerOp v;
	public Neg(IIntegerOp v){ this.v=v; }
	public int x(){ return -v.x(); }
	public IInteger get(){ return v.get().neg(); }
    }
    public static class Mod extends IParameterObject implements IIntegerOp{
	public IIntegerOp v1,v2;
	public Mod(IIntegerOp v1, IIntegerOp v2){ this.v1=v1; this.v2=v2; }
	public int x(){ return v1.x()%v2.x(); }
	public IInteger get(){ return v1.get().mod(v2.get()); }
    }
    public static class FromDouble extends IParameterObject implements IIntegerOp{
	public IDoubleOp v;
	public FromDouble(IDoubleOp v){ this.v=v; }
	public int x(){ return (int)v.x(); }
	public IInteger get(){ return new IInteger(v.x()); }
    }
    public static class Eq extends IParameterObject implements IBoolOp{
	public IIntegerOp v1,v2;
	public Eq(IIntegerOp v1, IIntegerOp v2){ this.v1=v1; this.v2=v2; }
	public boolean x(){ return v1.get().eq(v2.get()); }
	public IBool get(){ return v1.get().eq((Ir)null,v2.get()); }
    }
    
}
