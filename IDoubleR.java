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
   Reference class of double (1 dimensional vector) to be used as IParameterObject.
   
   @author Satoru Sugihara
*/
public class IDoubleR extends IParameterObject implements IDoubleI, IReferenceParameter{
    
    protected IDoubleOp op;
    
    public IDoubleR(double v){ op=new IDouble(v); }
    public IDoubleR(IDoubleOp v){ op=v; }
    public IDoubleR(IIntegerI v){ op=new FromInt(v); }
    
    public IDoubleR(IServerI s, double v){ super(s); op=new IDouble(v); }
    public IDoubleR(IServerI s, IDoubleOp v){ super(s); op=v; }
    public IDoubleR(IServerI s, IIntegerI v){ super(s); op=new FromInt(v); }
    
    public double x(){ return op.x(); }
    public IDouble get(){ return op.get(); }
    public IDoubleR getX(){ return this; }
    
    public IDoubleOp operator(){ return op; } // for viewer
    
    public IDoubleR dup(){ return new IDoubleR(op); }
    
    /** alias of dup() */
    public IDoubleR cp(){ return dup(); }
    
    /** duplicate and add */
    public IDoubleR cp(double v){ return dup().add(v); }
    /** duplicate and add */
    public IDoubleR cp(IDoubleI v){ return dup().add(v); }
    
    
    public IDoubleR set(double u){ op=new IDouble(u); return this; }
    public IDoubleR set(IDoubleI u){ op=u; return this; }
    public IDoubleR set(IIntegerI u){ op=new FromInt(u); return this; }
    
    public IDoubleR add(IDoubleI u){ op = new Add(op,u); return this; }
    public IDoubleR add(double u){ op = new Add(op, new IDouble(u)); return this; }
    public IDoubleR sub(IDoubleI u){ op = new Sub(op,u); return this; }
    public IDoubleR sub(double u){ op = new Sub(op, new IDouble(u)); return this; }
    public IDoubleR mul(IDoubleI u){ op = new Mul(op,u); return this; }
    public IDoubleR mul(double u){ op = new Mul(op, new IDouble(u)); return this; }
    public IDoubleR div(IDoubleI u){ op = new Div(op,u); return this; }
    public IDoubleR div(double u){ op = new Div(op, new IDouble(u)); return this; }
    public IDoubleR neg(){ op = new Neg(op); return this; }
    
    public IDoubleR inv(){ op = new Inv(op); return this; }
    
    public IDoubleI abs(){ op = new Abs(op); return this; }
    
    public IDoubleI pow(double n){ op = new Pow(op,new IDouble(n)); return this; }
    public IDoubleI pow(IDoubleI n){ op = new Pow(op,n); return this; }
    
    public IDoubleI sq(){ op = new Sq(op); return this; }
    public IDoubleI sqrt(){ op = new Sqrt(op); return this; }
    
    public IDoubleI exp(){ op = new Exp(op); return this; }
    public IDoubleI log(){ op = new Log(op); return this; }
    
    public IDoubleI sin(){ op = new Sin(op); return this; }
    public IDoubleI cos(){ op = new Cos(op); return this; }
    public IDoubleI tan(){ op = new Tan(op); return this; }
    
    public IDoubleI asin(){ op = new ASin(op); return this; }
    public IDoubleI acos(){ op = new ACos(op); return this; }
    public IDoubleI atan(){ op = new ATan(op); return this; }
    public IDoubleI atan2(double x2){ op = new ATan2(op,new IDouble(x2)); return this; }
    public IDoubleI atan2(IDoubleI x2){ op = new ATan2(op,x2); return this; }
    
    public IDoubleI deg(){ op = new Deg(op); return this; }
    public IDoubleI rad(){ op = new Rad(op); return this; }
    
    public boolean eq(double v){ return get().eq(v); }
    public boolean eq(IDoubleI v){ return get().eq(v); }
    //public IBoolR eqR(IDoubleI v){ return new IBoolR(new Eq(op,v)); }
    public boolean eq(ISwitchE e, IDoubleI v){ return eq(v); }
    public IBoolR eq(ISwitchR r, IDoubleI v){ return new IBoolR(new Eq(op,v)); }
    
    public boolean eq(double v, double resolution){ return get().eq(v,resolution); }
    public boolean eq(IDoubleI v, double resolution){ return get().eq(v,resolution); }
    //public IBoolR eqR(IDoubleI v, IDoubleI resolution){ return new IBoolR(new Eq(op,v,resolution)); }
    public boolean eq(ISwitchE e, IDoubleI v, double resolution){ return eq(v,resolution); }
    public IBoolR eq(ISwitchR r, IDoubleI v, IDoubleI resolution){ return new IBoolR(new Eq(op,v,resolution)); }
    
    
    
    public static class Add extends IParameterObject implements IDoubleOp{
	public IDoubleOp v1,v2;
	public Add(IDoubleOp v1, IDoubleOp v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.x()+v2.x(); }
	public IDouble get(){
	    return v1.get().add(v2.get());
	}
    }
    public static class Sub extends IParameterObject implements IDoubleOp{
	public IDoubleOp v1,v2;
	public Sub(IDoubleOp v1, IDoubleOp v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.x()-v2.x(); }
	public IDouble get(){ return v1.get().sub(v2.get()); }
    }
    public static class Mul extends IParameterObject implements IDoubleOp{
	public IDoubleOp v1,v2;
	public Mul(IDoubleOp v1, IDoubleOp v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.x()*v2.x(); }
	public IDouble get(){ return v1.get().mul(v2.get()); }
    }
    public static class Div extends IParameterObject implements IDoubleOp{
	public IDoubleOp v1,v2;
	public Div(IDoubleOp v1, IDoubleOp v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.x()/v2.x(); }
	public IDouble get(){ return v1.get().div(v2.get()); }
    }
    public static class Neg extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Neg(IDoubleOp v){ this.v=v; }
	public double x(){ return -v.x(); }
	public IDouble get(){ return v.get().neg(); }
    }
    public static class Inv extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Inv(IDoubleOp v){ this.v=v; }
	public double x(){ return 1./v.x(); }
	public IDouble get(){ return v.get().inv(); }
    }
    public static class Abs extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Abs(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.abs(v.x()); }
	public IDouble get(){ return v.get().abs(); }
    }
    public static class FromInt extends IParameterObject implements IDoubleOp{
	public IIntegerOp v;
	public FromInt(IIntegerOp v){ this.v=v; }
	public double x(){ return v.x(); }
	public IDouble get(){ return new IDouble(v.x()); }
    }
    public static class Pow extends IParameterObject implements IDoubleOp{
	public IDoubleOp v1, v2;
	public Pow(IDoubleOp v1, IDoubleOp v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return Math.pow(v1.x(),v2.x()); }
	public IDouble get(){ return v1.get().pow(v2.get()); }
    }
    public static class Sq extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Sq(IDoubleOp v){ this.v=v; }
	public double x(){ double x=v.x(); return x*x; }
	public IDouble get(){ return v.get().sq(); }
    }
    public static class Sqrt extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Sqrt(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.sqrt(v.x()); }
	public IDouble get(){ return v.get().sqrt(); }
    }
    public static class Exp extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Exp(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.exp(v.x()); }
	public IDouble get(){ return v.get().exp(); }
    }
    public static class Log extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Log(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.log(v.x()); }
	public IDouble get(){ return v.get().log(); }
    }
    
    public static class Sin extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Sin(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.sin(v.x()); }
	public IDouble get(){ return v.get().sin(); }
    }
    public static class Cos extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Cos(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.cos(v.x()); }
	public IDouble get(){ return v.get().cos(); }
    }
    public static class Tan extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Tan(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.tan(v.x()); }
	public IDouble get(){ return v.get().tan(); }
    }
    public static class ASin extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public ASin(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.asin(v.x()); }
	public IDouble get(){ return v.get().asin(); }
    }
    public static class ACos extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public ACos(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.acos(v.x()); }
	public IDouble get(){ return v.get().acos(); }
    }
    public static class ATan extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public ATan(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.atan(v.x()); }
	public IDouble get(){ return v.get().atan(); }
    }
    public static class ATan2 extends IParameterObject implements IDoubleOp{
	public IDoubleOp y,x;
	public ATan2(IDoubleOp y, IDoubleOp x){ this.y=y; this.x=x; }
	public double x(){ return Math.atan2(y.x(),x.x()); }
	public IDouble get(){ return y.get().atan2(x.get()); }
    }
    
    public static class Deg extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Deg(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.toDegrees(v.x()); }
	public IDouble get(){ return v.get().deg(); }
    }
    public static class Rad extends IParameterObject implements IDoubleOp{
	public IDoubleOp v;
	public Rad(IDoubleOp v){ this.v=v; }
	public double x(){ return Math.toRadians(v.x()); }
	public IDouble get(){ return v.get().rad(); }
    }
    
    public static class Eq extends IParameterObject implements IBoolOp{
	public IDoubleOp v1, v2, resolution=null;
	public Eq(IDoubleOp v1, IDoubleOp v2){ this.v1=v1; this.v2=v2; }
	public Eq(IDoubleOp v1, IDoubleOp v2, IDoubleOp reso){
	    this.v1=v1; this.v2=v2; resolution=reso;
	}
	public boolean x(){ 
	    if(resolution==null) return v1.get().eq(v2.get());
	    return v1.get().eq(v2.get(),resolution.x());
	}
	public IBool get(){
	    if(resolution==null) return v1.get().eq((Ir)null,v2.get());
	    return v1.get().eq((Ir)null,v2.get(),resolution.get());
	}
    }
    
}
