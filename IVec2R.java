/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

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
   Reference class of 2 dimensional vector to be used as IParameterObject.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IVec2R extends IParameterObject implements IVec2I, IReferenceParameter{
    protected IVec2Op op;
    
    public IVec2R(IVec2Op v){ op=v; }
    public IVec2R(double x, double y){ op=new IVec2(x,y); }
    public IVec2R(IDoubleI x, IDoubleI y){ op = new FromXY(x,y); }
    
    public IVec2R(IServerI s, IVec2Op v){ super(s); op=v; }
    public IVec2R(IServerI s, double x, double y){ super(s); op=new IVec2(x,y); }
    public IVec2R(IServerI s, IDoubleI x, IDoubleI y){ super(s); op = new FromXY(x,y); }
    
    public double x(){ return op.get().x; }
    public double y(){ return op.get().y; }
    public IVec2 get(){ return op.get(); }
    
    public IDoubleR getX(){ return new IDoubleR(new X(op)); }
    public IDoubleR getY(){ return new IDoubleR(new Y(op)); }
    
    public IVec2Op operator(){ return op; } // for viewer
    
    public IVec2R dup(){ return new IVec2R(op); }
    
    public IVecR to3d(){ return new IVecR(new ToVec3(this)); }
    public IVecR to3d(double z){ return new IVecR(new ToVec3(this,new IDouble(z))); }
    public IVecR to3d(IDoubleI z){ return new IVecR(new ToVec3(this,z)); }
    public IVec4R to4d(){ return new IVec4R(new ToVec4(this)); }
    public IVec4R to4d(double z, double w){ return new IVec4R(new ToVec4(this,new IDouble(z),new IDouble(w))); }
    public IVec4R to4d(IDoubleI z, IDoubleI w){ return new IVec4R(new ToVec4(this,z,w)); }
    
    public IVec2R set(IVec2I u){ op=u; return this; }
    public IVec2R set(double x, double y){ op=new IVec2(x,y); return this; }
    public IVec2R set(IDoubleI x, IDoubleI y){ op = new FromXY(x,y); return this; }
    
    public IVec2R add(double x, double y){ op = new Add(op,new IVec2(x,y)); return this; }
    public IVec2R add(IDoubleI x, IDoubleI y){ op = new Add(op,new IVec2R(x,y)); return this; }
    public IVec2R add(IVec2I u){ op = new Add(op,u); return this; }
    public IVec2R sub(double x, double y){ op = new Sub(op,new IVec2(x,y)); return this; }
    public IVec2R sub(IDoubleI x, IDoubleI y){ op = new Sub(op,new IVec2R(x,y)); return this; }
    public IVec2R sub(IVec2I u){ op = new Sub(op,u); return this; }
    public IVec2R mul(IDoubleI u){ op = new Mul(op,u); return this; }
    public IVec2R mul(double u){ op = new Mul(op, new IDouble(u)); return this; }
    public IVec2R div(IDoubleI u){ op = new Div(op,u); return this; }
    public IVec2R div(double u){ op = new Div(op, new IDouble(u)); return this; }
    public IVec2R neg(){ op = new Neg(op); return this; }
    public IVec2R rev(){ return neg(); }

    public IVec2R add(IVec2I v, double f){ return add(v.dup().mul(f)); }
    public IVec2R add(IVec2I v, IDoubleI f){ return add(v.dup().mul(f)); }
    
    public double dot(IVec2I u){ return get().dot(u); }
    //public IDoubleR dotR(IVec2I u){ return new IDoubleR(new Dot(op,u)); }
    public double dot(ISwitchE e, IVec2I u){ return dot(u); }
    public IDoubleR dot(ISwitchR r, IVec2I u){ return new IDoubleR(new Dot(op,u)); }
    
    public IVecR cross(IVec2I u){ return new IVecR(new Cross(op,u)); }
    
    public double len(){ return get().len(); }
    //public IDoubleR lenR(){ return new IDoubleR(new Len(op)); }
    public double len(ISwitchE e){ return len(); }
    public IDoubleR len(ISwitchR r){ return new IDoubleR(new Len(op)); }
    
    public double len2(){ return get().len2(); }
    //public IDoubleR len2R(){ return new IDoubleR(new Len2(op)); }
    public double len2(ISwitchE e){ return len2(); }
    public IDoubleR len2(ISwitchR r){ return new IDoubleR(new Len2(op)); }
    
    public IVec2R len(IDoubleI l){ op = new SetLen(op,l); return this; }
    public IVec2R len(double l){ op = new SetLen(op,new IDouble(l)); return this; }
    
    public IVec2R unit(){ op = new Unit(op); return this; }
    public IVec2R ortho(){ op = new Ortho(op); return this; }
        
    
    public double dist(IVec2I v){ return get().dist(v); }
    //public IDoubleR distR(IVec2I v){ return new IDoubleR(new Dist(op, v)); }
    public double dist(ISwitchE e, IVec2I v){ return dist(v); }
    public IDoubleR dist(ISwitchR r, IVec2I v){ return new IDoubleR(new Dist(op, v)); }
    
    /**
       squared distance 
    */
    public double dist2(IVec2I v){ return get().dist2(v); }
    //public IDoubleR dist2R(IVec2I v){ return new IDoubleR(new Dist2(op, v)); }
    public double dist2(ISwitchE e, IVec2I v){ return dist2(v); }
    public IDoubleR dist2(ISwitchR r, IVec2I v){ return new IDoubleR(new Dist2(op, v)); }
    
    /**
       whether location is same or not
    */
    public boolean eq(IVec2I v){ return get().eq(v); }
    //public IBoolR eqR(IVec2I v){ return new IBoolR(new Eq(op, v)); }
    public boolean eq(ISwitchE e, IVec2I v){ return eq(v); }
    public IBoolR eq(ISwitchR r, IVec2I v){ return new IBoolR(new Eq(op, v)); }
    
    public boolean eq(IVec2I v, double resolution){ return get().eq(v,resolution); }
    //public IBoolR eqR(IVec2I v, IDoubleI resolution){ return new IBoolR(new Eq(op,v,resolution)); }
    public boolean eq(ISwitchE e, IVec2I v, double resolution){ return eq(v,resolution); }
    public IBoolR eq(ISwitchR r, IVec2I v, IDoubleI resolution){ return new IBoolR(new Eq(op,v,resolution)); }
    
    public boolean eqX(IVec2I v){ return get().eqX(v); }
    public boolean eqY(IVec2I v){ return get().eqY(v); }
    //public IBoolR eqXR(IVec2I v){ return new IBoolR(new EqX(op,v)); }
    //public IBoolR eqYR(IVec2I v){ return new IBoolR(new EqY(op,v)); }
    public boolean eqX(ISwitchE e, IVec2I v){ return eqX(v); }
    public boolean eqY(ISwitchE e, IVec2I v){ return eqY(v); }
    public IBoolR eqX(ISwitchR r, IVec2I v){ return new IBoolR(new EqX(op,v)); }
    public IBoolR eqY(ISwitchR r, IVec2I v){ return new IBoolR(new EqY(op,v)); }
    
    public boolean eqX(IVec2I v, double resolution){ return get().eqX(v,resolution); }
    public boolean eqY(IVec2I v, double resolution){ return get().eqY(v,resolution); }
    //public IBoolR eqXR(IVec2I v, IDoubleI resolution){ return new IBoolR(new EqX(op,v,resolution)); }
    //public IBoolR eqYR(IVec2I v, IDoubleI resolution){ return new IBoolR(new EqY(op,v,resolution)); }
    public boolean eqX(ISwitchE e, IVec2I v, double resolution){ return eqX(v,resolution); }
    public boolean eqY(ISwitchE e, IVec2I v, double resolution){ return eqY(v,resolution); }
    public IBoolR eqX(ISwitchR r, IVec2I v, IDoubleI resolution){ return new IBoolR(new EqX(op,v,resolution)); }
    public IBoolR eqY(ISwitchR r, IVec2I v, IDoubleI resolution){ return new IBoolR(new EqY(op,v,resolution)); }
    
    
    public double angle(IVec2I u){ return get().angle(u); }
    //public IDoubleR angleR(IVec2I u){ return new IDoubleR(new Angle(op,u)); }
    public double angle(ISwitchE e, IVec2I u){ return angle(u); }
    public IDoubleR angle(ISwitchR r, IVec2I u){ return new IDoubleR(new Angle(op,u)); }
    
    public IVec2R rot(double angle){ op = new Rot(op,new IDouble(angle)); return this; }
    public IVec2R rot(IDoubleI angle){ op = new Rot(op,angle); return this; }
    
    public IVec2R rot(IVec2I center, double angle){
	if(center==this) return this;
	return sub(center).rot(angle).add(center);
    }
    public IVec2R rot(IVec2I center, IDoubleI angle){
	if(center==this) return this;
	return sub(center).rot(angle).add(center);
    }
    
    // to be tested. 
    public IVec2R rot(IVec2I destDir){ return rot(angle(destDir)); }
    public IVec2R rot(IVec2I center, IVec2I destPt){
	if(center==this) return this;
	return sub(center).rot(destPt.diff(center)).add(center);
    }
    
    public IVec2R scale(double f){ return mul(f); }
    public IVec2R scale(IDoubleI f){ return mul(f); }
    public IVec2R scale(IVec2I center, double f){
	if(center==this) return this;
	return sub(center).mul(f).add(center);
    }
    public IVec2R scale(IVec2I center, IDoubleI f){
	if(center==this) return this;
	return sub(center).mul(f).add(center);
    }
    
    /**
       reflect (mirror) 2 dimensionally to the other side of the line
    */
    public IVec2R ref(IVec2I lineDir){ op = new Ref(op, lineDir); return this; }
    public IVec2R ref(IVec2I linePt, IVec2I lineDir){
	if(linePt==this) return this;
	return sub(linePt).ref(lineDir).add(linePt);
    }
    public IVec2R mirror(IVec2I lineDir){ return ref(lineDir); }
    public IVec2R mirror(IVec2I linePt, IVec2I lineDir){ return ref(linePt,lineDir); }
    
    
    //public IVec2R transform(IMatrix2I mat);
    //public IVec2R transform(IMatrix3I mat);
    public IVec2R transform(IVec2I xvec, IVec2I yvec){
	op = new TransformVec2(op,xvec,yvec); return this;
    }
    public IVec2R transform(IVec2I xvec, IVec2I yvec, IVec2I translate){
	op = new TransformVec3(op,xvec,yvec,translate); return this; 
    }
    
    
    public IVec2R diff(IVec2I v){ return dup().sub(v); }
    public IVec2R mid(IVec2I v){ return dup().add(v).div(2); }
    public IVec2R sum(IVec2I v){ return dup().add(v); }
    public IVec2R sum(IVec2I... v){
	IVec2R ret = dup();
	for(IVec2I vi : v) ret.add(vi);
	return ret;
    }
    public IVec2R bisect(IVec2I v){ return dup().unit().add(v.dup().unit()); }
    
    /**
       weighted sum
    */
    public IVec2R sum(IVec2I v2, double w1, double w2){
	return sum(v2,new IDouble(w1),new IDouble(w2));
    }
    public IVec2R sum(IVec2I v2, double w2){
	return sum(v2,new IDouble(w2));
    }
    
    public IVec2R sum(IVec2I v2, IDoubleI w1, IDoubleI w2){
	return dup().mul(w1).add(v2,w2);
    }
    public IVec2R sum(IVec2I v2, IDoubleI w2){
	return dup().mul((new IDouble(1.0)).sub(w2)).add(v2,w2);
    }
    
    static public class ToVec3 extends IParameterObject implements IVecOp{
	public IVec2Op v;
	public IDoubleOp z;
	public ToVec3(IVec2Op v){ this.v=v; z=new IDouble(0.); }
	public ToVec3(IVec2Op v, IDoubleOp z){ this.v=v; this.z=z; }
	public IVec get(){ IVec2 v2=v.get(); return new IVec(v2.x,v2.y,z.x()); }
    }
    
    static public class ToVec4 extends IParameterObject implements IVec4Op{
	public IVec2Op v;
	public IDoubleOp z, w;
	public ToVec4(IVec2Op v){ this.v=v; z=new IDouble(0.); w=new IDouble(1.); }
	public ToVec4(IVec2Op v, IDoubleOp z, IDoubleOp w){ this.v=v; this.z=z; this.w=w; }
	public IVec4 get(){ IVec2 v2=v.get(); return new IVec4(v2.x,v2.y,z.x(),w.x()); }
    }
    
    static public class ToVec2 extends IParameterObject implements IVec2Op{
        public IVecOp v;
        public ToVec2(IVecOp v){ this.v=v; }
        public IVec2 get(){ return new IVec2(v.get()); }
    }
    
    static public class Add extends IParameterObject implements IVec2Op{
	public IVec2Op v1, v2;
	public Add(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public IVec2 get(){ return v1.get().add(v2.get()); }
    }
    static public class Sub extends IParameterObject implements IVec2Op{
	public IVec2Op v1,v2;
	public Sub(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public IVec2 get(){ return v1.get().sub(v2.get()); }
    }
    static public class Mul extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IDoubleOp d;
	public Mul(IVec2Op v, IDoubleOp d){ this.v=v; this.d=d; }
	public IVec2 get(){ return v.get().mul(d.x()); }
    }
    static public class Div extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IDoubleOp d;
	public Div(IVec2Op v, IDoubleOp d){ this.v=v; this.d=d; }
	public IVec2 get(){ return v.get().div(d.x()); }
    }
    static public class Neg extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public Neg(IVec2Op v){ this.v=v; }
	public IVec2 get(){ return v.get().neg(); }
    }
    static public class Dot extends IParameterObject implements IDoubleOp{
	public IVec2Op v1,v2;
	public Dot(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().dot(v2.get()); }
	public IDouble get(){ return v1.get().dot((Ir)null,v2.get()); }
    }
    static public class Cross extends IParameterObject implements IVecOp{
	public IVec2Op v1,v2;
	public Cross(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public IVec get(){ return v1.get().cross(v2.get()); }
    }
    static public class Angle extends IParameterObject implements IDoubleOp{
	public IVec2Op v1,v2;
	public Angle(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().angle(v2.get()); }
	public IDouble get(){ return v1.get().angle((Ir)null,v2.get()); }
    }
    static public class Rot extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IDoubleOp angle;
	public Rot(IVec2Op v, IDoubleOp angle){ this.v=v; this.angle=angle; }
	public IVec2 get(){ return v.get().rot(angle.x()); }
    }
    static public class FromXY extends IParameterObject implements IVec2Op{
	public IDoubleOp x, y;
	public FromXY(IDoubleOp x, IDoubleOp y){ this.x=x; this.y=y; }
	public IVec2 get(){ return new IVec2(x.x(), y.x()); }
    }
    static public class Len extends IParameterObject implements IDoubleOp{
	public IVec2Op v;
	public Len(IVec2Op v){ this.v=v; }
	public double x(){ return v.get().len(); }
	public IDouble get(){ return v.get().len((Ir)null); }
    }
    static public class Len2 extends IParameterObject implements IDoubleOp{
	public IVec2Op v;
	public Len2(IVec2Op v){ this.v=v; }
	public double x(){ return v.get().len2(); }
	public IDouble get(){ return v.get().len2((Ir)null); }
    }
    static public class X extends IParameterObject implements IDoubleOp{
	public IVec2Op v;
	public X(IVec2Op v){ this.v=v; }
	public double x(){ return v.get().x; }
	public IDouble get(){ return v.get().getX(); }
    }
    static public class Y extends IParameterObject implements IDoubleOp{
	public IVec2Op v;
	public Y(IVec2Op v){ this.v=v; }
	public double x(){ return v.get().y; }
	public IDouble get(){ return v.get().getY(); }
    }
    static public class Unit extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public Unit(IVec2Op v){ this.v=v; }
	public IVec2 get(){ return v.get().unit(); }
    }
    static public class Ortho extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public Ortho(IVec2Op v){ this.v=v; }
	public IVec2 get(){ return v.get().ortho(); }
    }
    
    static public class SetLen extends IParameterObject implements IVec2Op{
	public IVec2Op v;
	public IDoubleOp l;
	public SetLen(IVec2Op v,  IDoubleOp l){ this.v=v; this.l=l; }
	public IVec2 get(){ return v.get().len(l.x()); }
    }
    
    static public class Dist extends IParameterObject implements IDoubleOp{
	public IVec2Op v1,v2;
	public Dist(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().dist(v2.get()); }
	public IDouble get(){ return v1.get().dist((Ir)null,v2.get()); }
    }
    
    static public class Dist2 extends IParameterObject implements IDoubleOp{
	public IVec2Op v1,v2;
	public Dist2(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public double x(){ return v1.get().dist2(v2.get()); }
	public IDouble get(){ return v1.get().dist2((Ir)null,v2.get()); }
    }
    
    static public class Eq extends IParameterObject implements IBoolOp{
	public IVec2Op v1,v2;
	public IDoubleOp resolution=null;
	public Eq(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public Eq(IVec2Op v1, IVec2Op v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; resolution=reso; }
	public boolean x(){ 
	    if(resolution==null) return v1.get().eq(v2.get());
	    return v1.get().eq(v2.get(),resolution.x());
	}
	public IBool get(){
	    if(resolution==null) return v1.get().eq((Ir)null,v2.get());
	    return v1.get().eq((Ir)null,v2.get(),resolution.get());
	}
    }
    
    static public class EqX extends IParameterObject implements IBoolOp{
	public IVec2Op v1,v2;
	public IDoubleOp resolution=null;
	public EqX(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public EqX(IVec2Op v1, IVec2Op v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; resolution=reso; }
	public boolean x(){
	    if(resolution==null) return v1.get().eqX(v2.get());
	    return v1.get().eqX(v2.get(),resolution.x());
	}
	public IBool get(){
	    if(resolution==null) return v1.get().eqX((Ir)null,v2.get());
	    return v1.get().eqX((Ir)null,v2.get(),resolution.get());
	}
    }
    static public class EqY extends IParameterObject implements IBoolOp{
	public IVec2Op v1,v2;
	public IDoubleOp resolution=null;
	public EqY(IVec2Op v1, IVec2Op v2){ this.v1=v1; this.v2=v2; }
	public EqY(IVec2Op v1, IVec2Op v2, IDoubleOp reso){ this.v1=v1; this.v2=v2; resolution=reso; }
	public boolean x(){
	    if(resolution==null) return v1.get().eqY(v2.get());
	    return v1.get().eqY(v2.get(),resolution.x());
	}
	public IBool get(){
	    if(resolution==null) return v1.get().eqY((Ir)null,v2.get());
	    return v1.get().eqY((Ir)null,v2.get(),resolution.get());
	}
    }
    
    static public class Ref extends IParameterObject implements IVec2Op{
	public IVec2Op v, lineDir;
	public Ref(IVec2Op v,  IVec2Op lineDir){ this.v=v; this.lineDir=lineDir; }
	public IVec2 get(){ return v.get().ref(lineDir.get()); }
    }
    
    static public class TransformVec2 extends IParameterObject implements IVec2Op{
	public IVec2Op v, xvec, yvec;
	public TransformVec2(IVec2Op v,  IVec2Op xvec, IVec2Op yvec){ this.v=v; this.xvec=xvec; this.yvec=yvec; }
	public IVec2 get(){ return v.get().transform(xvec.get(),yvec.get()); }
    }
    
    static public class TransformVec3 extends IParameterObject implements IVec2Op{
	public IVec2Op v, xvec, yvec, translate;
	public TransformVec3(IVec2Op v,  IVec2Op xvec, IVec2Op yvec, IVec2Op translate){
	    this.v=v; this.xvec=xvec; this.yvec=yvec; this.translate=translate;
	}
	public IVec2 get(){ return v.get().transform(xvec.get(),yvec.get(),translate.get()); }
    }
    
    
    // reflection is not used because of the performance cost of try-catch    
    /*
    static public class Operator implements IVec2Op{
	String method;
	Operator(String method, IVec2Op v){ super(v); this.method=method; }
	public IVec2 get(){
	    try{
		Method m = IVec2.class.getMethod(method, (Class[])null);
		return (IVec2) m.invoke(v.get(), (Object[])null);
	    }catch(Exception e){ e.printStackTrace(); }
	    return null;
	}
    }
    
    static public class OperatorV2 implements IVec2Op{
	IVec2Op v2;
	String method;
	OperatorV2(String method, IVec2Op v, IVec2Op v2){
	    super(v); this.method=method; this.v2=v2;
	}
	public IVec2 get(){
	    try{
		Method m = IVec2.class.getMethod(method, new Class[]{IVec2.class} );
		return (IVec2) m.invoke(v.get(), new Object[]{v2.get()} );
	    }catch(Exception e){ e.printStackTrace(); }
	    return null;
	}
    }
    */
    
}
