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

public class ITextGeo extends IParameterObject{
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
    
}