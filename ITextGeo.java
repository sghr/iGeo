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
    Class of a text object in 3D space
    @author Satoru Sugihara
*/

public class ITextGeo extends IParameterObject implements ITransformable{
    public enum HorizontalAlignment{ Left, Center, Right };
    public enum VerticalAlignment{ Top, Middle, Bottom };
    
    public String text; 
    public IVecI pos;
    /** direction of text with length of font size */
    public IVecI uvec;
    /** text height direction with length of font size */
    public IVecI vvec; 
    
    public HorizontalAlignment halign = HorizontalAlignment.Left;
    public VerticalAlignment valign = VerticalAlignment.Bottom;
    
    public ITextGeo(String str, double fontSize, IVecI pos, IVecI textDir, IVecI textUpDir){
	text = str;
	this.pos = pos;
	uvec = textDir.cp().len(fontSize);
	// re-orthogonalize
	vvec = textDir.cross(textUpDir).cross(textDir).len(fontSize);
    }
    
    public ITextGeo(String str, double fontSize, IVecI pos, IVecI textDir){
	text = str;
	this.pos = pos;
	uvec = textDir.cp().len(fontSize);
	IVec textUpDir = new IVec(0,1,0);
	if(textUpDir.isParallel(textDir)){
	    if(textDir.dot(textUpDir)>0){ textUpDir.set(-1,0,0); }
	    else{ textUpDir.set(1,0,0); }
	}
	// re-orthogonalize
	vvec = textDir.cross(textUpDir).cross(textDir).len(fontSize);
    }
    
    public ITextGeo(String str, double fontSize, IVecI pos){
	text = str;
	this.pos = pos;
	uvec = new IVec(fontSize,0,0);
	vvec = new IVec(0,fontSize,0);
    }
    
    public ITextGeo(String str, IVecI pos, IVecI uvec, IVecI vvec){
	text = str;
	this.pos = pos;
	this.uvec = uvec;
	this.vvec = vvec;
    }
    
    public ITextGeo(ITextGeo text){
	this.text = new String(text.text()); //
	pos = text.pos().dup();
	uvec = text.uvec().dup();
	vvec = text.vvec().dup();
    }
    
    
    public IVecI pos(){ return pos; }
    public IVecI uvec(){ return uvec; }
    public IVecI vvec(){ return vvec; }
    
    public ITextGeo pos(IVecI v){ pos=v; return this; }
    public ITextGeo uvec(IVecI v){ uvec=v; return this; }
    public ITextGeo vvec(IVecI v){ vvec=v; return this; }
    
    public String text(){ return text; }
    public ITextGeo text(String txt){ text=txt; return this; }
    
    public ITextGeo alignLeft(){ halign = HorizontalAlignment.Left; return this; }
    public ITextGeo alignCenter(){ halign = HorizontalAlignment.Center; return this; }
    public ITextGeo alignRight(){ halign = HorizontalAlignment.Right; return this; }
    public ITextGeo alignTop(){ valign = VerticalAlignment.Top; return this; }
    public ITextGeo alignMiddle(){ valign = VerticalAlignment.Middle; return this; }
    public ITextGeo alignBottom(){ valign = VerticalAlignment.Bottom; return this; }
    
    public boolean isAlignLeft(){ return halign == HorizontalAlignment.Left; }
    public boolean isAlignCenter(){ return halign == HorizontalAlignment.Center; }
    public boolean isAlignRight(){ return halign == HorizontalAlignment.Right; }
    public boolean isAlignTop(){ return valign == VerticalAlignment.Top; }
    public boolean isAlignMiddle(){ return valign == VerticalAlignment.Middle; }
    public boolean isAlignBottom(){ return valign == VerticalAlignment.Bottom; }
    
    
    /******************************************************************************
     * methods for ITransformable
     ******************************************************************************/
    
    public ITextGeo add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public ITextGeo add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }
    public ITextGeo add(IVecI v){ pos.add(v); return this; }
    public ITextGeo sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public ITextGeo sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public ITextGeo sub(IVecI v){ pos.sub(v); return this; }
    public ITextGeo mul(IDoubleI v){ pos.mul(v); uvec.mul(v); vvec.mul(v); return this; }
    public ITextGeo mul(double v){ pos.mul(v); uvec.mul(v); vvec.mul(v); return this; }
    public ITextGeo div(IDoubleI v){ pos.div(v); uvec.div(v); vvec.div(v); return this; }
    public ITextGeo div(double v){ pos.div(v); uvec.div(v); vvec.div(v); return this; }
    
    public ITextGeo neg(){ pos.neg(); uvec.neg(); vvec.neg(); return this; }
    public ITextGeo rev(){ return neg(); }
    public ITextGeo flip(){ return neg(); }
    
    /** scale add */
    public ITextGeo add(IVecI v, double f){ pos.add(v,f); return this; }
    /** scale add */
    public ITextGeo add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    /** scale add alias */
    public ITextGeo add(double f, IVecI v){ pos.add(v,f); return this; }
    /** scale add alias */
    public ITextGeo add(IDoubleI f, IVecI v){ pos.add(v,f); return this; }
    
    /** rotation around z-axis and origin */
    public ITextGeo rot(IDoubleI angle){ pos.rot(angle); uvec.rot(angle); vvec.rot(angle); return this; }
    public ITextGeo rot(double angle){ pos.rot(angle); uvec.rot(angle); vvec.rot(angle); return this; }
    
    /** rotation around axis vector */
    public ITextGeo rot(IVecI axis, IDoubleI angle){
	pos.rot(axis, angle); uvec.rot(axis, angle); vvec.rot(axis, angle); return this;
    }
    public ITextGeo rot(IVecI axis, double angle){
	pos.rot(axis, angle); uvec.rot(axis, angle); vvec.rot(axis, angle); return this;
    }
    
    /** rotation around axis vector and center */
    public ITextGeo rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis, angle); uvec.rot(axis, angle); vvec.rot(axis, angle); return this;
    }
    public ITextGeo rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis, angle); uvec.rot(axis, angle); vvec.rot(axis, angle); return this;
    }
    
    /** rotate to destination direction vector */
    public ITextGeo rot(IVecI axis, IVecI destDir){
	pos.rot(axis, destDir); uvec.rot(axis, destDir); vvec.rot(axis, destDir); return this;
    }
    /** rotate to destination point location */    
    public ITextGeo rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center, axis, destPt);
	uvec.rot(axis, destPt.dif(center)); // is this transformation correct?
	vvec.rot(axis, destPt.dif(center)); // is this transformation correct?
	return this;
    }
    
    /** rotation on xy-plane around origin; same with rot(IDoubleI) */
    public ITextGeo rot2(IDoubleI angle){ pos.rot2(angle); uvec.rot2(angle); vvec.rot2(angle); return this; }
    /** rotation on xy-plane around origin; same with rot(double) */
    public ITextGeo rot2(double angle){ pos.rot2(angle); uvec.rot2(angle); vvec.rot2(angle); return this; }
    
    /** rotation on xy-plane around center */
    public ITextGeo rot2(IVecI center, IDoubleI angle){ pos.rot2(center, angle); uvec.rot2(angle); vvec.rot2(angle); return this; }
    public ITextGeo rot2(IVecI center, double angle){ pos.rot2(center, angle); uvec.rot2(angle); vvec.rot2(angle); return this; }
    
    /** rotation on xy-plane to destination direction vector */
    public ITextGeo rot2(IVecI destDir){ pos.rot2(destDir); uvec.rot2(destDir); vvec.rot2(destDir); return this; }
    /** rotation on xy-plane to destination point location */
    public ITextGeo rot2(IVecI center, IVecI destPt){
	pos.rot2(center,destPt);
	uvec.rot2(destPt.dif(center)); // is this transformation correct?
	vvec.rot2(destPt.dif(center)); // is this transformation correct?
	return this;
    }
    
    
    /** alias of mul */
    public ITextGeo scale(IDoubleI f){ return mul(f); }
    public ITextGeo scale(double f){ return mul(f); }
    public ITextGeo scale(IVecI center, IDoubleI f){ pos.scale(center,f); uvec.scale(f); vvec.scale(f); return this; }
    public ITextGeo scale(IVecI center, double f){ pos.scale(center,f); uvec.scale(f); vvec.scale(f); return this; }

    
    /** scale only in 1 direction */
    public ITextGeo scale1d(IVecI axis, double f){ pos.scale1d(axis,f); uvec.scale1d(axis,f); vvec.scale1d(axis,f); return this; }
    public ITextGeo scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); uvec.scale1d(axis,f); vvec.scale1d(axis,f); return this; }
    public ITextGeo scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); uvec.scale1d(axis,f); vvec.scale1d(axis,f); return this;
    }
    public ITextGeo scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); uvec.scale1d(axis,f); vvec.scale1d(axis,f); return this;
    }
    
    
    /** reflect(mirror) 3 dimensionally to the other side of the plane */
    public ITextGeo ref(IVecI planeDir){
	pos.ref(planeDir); uvec.ref(planeDir); vvec.ref(planeDir); return this;
    }
    public ITextGeo ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); uvec.ref(planeDir); vvec.ref(planeDir); return this;
    }
    /** mirror is alias of ref */
    public ITextGeo mirror(IVecI planeDir){ return ref(planeDir); }
    public ITextGeo mirror(IVecI center, IVecI planeDir){ return ref(planeDir); }
    
    
    /** shear operation */
    public ITextGeo shear(double sxy, double syx, double syz,
			  double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz);
	uvec.shear(sxy,syx,syz,szy,szx,sxz);
	vvec.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public ITextGeo shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			  IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz);
	uvec.shear(sxy,syx,syz,szy,szx,sxz);
	vvec.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public ITextGeo shear(IVecI center, double sxy, double syx, double syz,
			  double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz);
	uvec.shear(sxy,syx,syz,szy,szx,sxz);
	vvec.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    public ITextGeo shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			  IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz);
	uvec.shear(sxy,syx,syz,szy,szx,sxz);
	vvec.shear(sxy,syx,syz,szy,szx,sxz);
	return this;
    }
    
    public ITextGeo shearXY(double sxy, double syx){
	pos.shearXY(sxy,syx); uvec.shearXY(sxy,syx); vvec.shearXY(sxy,syx); return this;
    }
    public ITextGeo shearXY(IDoubleI sxy, IDoubleI syx){
	pos.shearXY(sxy,syx); uvec.shearXY(sxy,syx); vvec.shearXY(sxy,syx); return this;
    }
    public ITextGeo shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); uvec.shearXY(sxy,syx); vvec.shearXY(sxy,syx); return this;
    }
    public ITextGeo shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); uvec.shearXY(sxy,syx); vvec.shearXY(sxy,syx); return this;
    }
    
    public ITextGeo shearYZ(double syz, double szy){
	pos.shearYZ(syz,szy); uvec.shearYZ(syz,szy); vvec.shearYZ(syz,szy); return this;
    }
    public ITextGeo shearYZ(IDoubleI syz, IDoubleI szy){
	pos.shearYZ(syz,szy); uvec.shearYZ(syz,szy); vvec.shearYZ(syz,szy); return this;
    }
    public ITextGeo shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); uvec.shearYZ(syz,szy); vvec.shearYZ(syz,szy); return this;
    }
    public ITextGeo shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); uvec.shearYZ(syz,szy); vvec.shearYZ(syz,szy); return this;
    }
    
    public ITextGeo shearZX(double szx, double sxz){
	pos.shearZX(szx,sxz); uvec.shearZX(szx,sxz); vvec.shearZX(szx,sxz); return this;
    }
    public ITextGeo shearZX(IDoubleI szx, IDoubleI sxz){
	pos.shearZX(szx,sxz); uvec.shearZX(szx,sxz); vvec.shearZX(szx,sxz); return this;
    }
    public ITextGeo shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); uvec.shearZX(szx,sxz); vvec.shearZX(szx,sxz); return this;
    }
    public ITextGeo shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); uvec.shearZX(szx,sxz); vvec.shearZX(szx,sxz); return this;
    }
    
    /** mv() is alias of add() */
    public ITextGeo mv(double x, double y, double z){ return add(x,y,z); }
    public ITextGeo mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ITextGeo mv(IVecI v){ return add(v); }
    
    
    /** duplicate the instance */
    public ITextGeo dup(){ return new ITextGeo(this); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public ITextGeo cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public ITextGeo cp(double x, double y, double z){ return cp().add(x,y,z); }
    public ITextGeo cp(IDoubleI x, IDoubleI y, IDoubleI z){ return cp().add(x,y,z); }
    public ITextGeo cp(IVecI v){ return cp().add(v); }
    
    
    /** translate() is alias of add() */
    public ITextGeo translate(double x, double y, double z){ return add(x,y,z); }
    public ITextGeo translate(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public ITextGeo translate(IVecI v){ return add(v); }
    
    
    public ITextGeo transform(IMatrix3I mat){
	pos.transform(mat); uvec.transform(mat); vvec.transform(mat); return this;
    }
    public ITextGeo transform(IMatrix4I mat){
	IMatrix3I mat3 = mat.matrix3(); // without translate
	pos.transform(mat); uvec.transform(mat3); vvec.transform(mat3); return this;
    }
    
    public ITextGeo transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); uvec.transform(xvec,yvec,zvec); vvec.transform(xvec,yvec,zvec); return this;
    }
    public ITextGeo transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); uvec.transform(xvec,yvec,zvec); vvec.transform(xvec,yvec,zvec); return this;
    }
    
    
}