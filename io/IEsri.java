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

package igeo.io;

import igeo.*;
import java.awt.Color;

public class IEsri{
    
    public static Shape[] getShape(IObject[] objects){
	if(objects==null) return new Shape[0];
	Shape[] shapes = new Shape[objects.length];
	for(int i=0; i<objects.length; i++){
	    shapes[i] = getShape(objects[i]);
	}
	return shapes;
    }
    
    public static Shape getShape(IObject object){
	return new Shape(object);
    }
    
    public static class Shape{
	public IObject object;
	public Attributes attr;
	
	public Shape(IObject obj){
	    object = obj;
	    for(int i=0; i<object.userDataNum() && attr==null; i++){
		if(object.userData(i) instanceof Attributes){
		    attr = (Attributes)object.userData(i);
		}
	    }
	}

	public ICurve curve(){
	    if(object==null){ return null; }
	    if(object instanceof ICurve){
		return (ICurve)object;
	    }
	    IG.err("object is not curve "+object);
	    return null;
	}
	
	public IPoint point(){
	    if(object==null){ return null; }
	    if(object instanceof IPoint){
		return (IPoint)object;
	    }
	    IG.err("object is not point "+object);
	    return null;
	}
	
	public IVec center(){
	    if(object instanceof IGeometry){
		return ((IGeometry)object).center().get();
	    }
	    IG.err("object is not IGeometry "+object);
	    return new IVec();
	}
		
	
	public int fieldNum(){
	    if(attr==null){
		IG.err("no attributes found");
		return 0;
	    }
	    if(attr.fields==null){
		IG.err("no fields found");
		return 0;
	    }
	    return attr.fields.length;
	}
	
	public String fieldName(int index){
	    if(attr==null){
		IG.err("no attributes found");
		return "";
	    }
	    if(attr.fields==null){
		IG.err("no fields found");
		return "";
	    }
	    if(index<0 || index>=attr.fields.length){
		IG.err("invalid index number "+index);
		return "";
	    }
	    return attr.fields[index].name;
	}
	
	public char fieldType(int index){
	    if(attr==null){
		IG.err("no attributes found");
		return 0;
	    }
	    if(attr.fields==null){
		IG.err("no fields found");
		return 0;
	    }
	    if(index<0 || index>=attr.fields.length){
		IG.err("invalid index number "+index);
		return 0;
	    }
	    return attr.fields[index].type;
	}
	
	public AttributeField field(int index){
	    if(attr==null){
		IG.err("no attributes found");
		return null;
	    }
	    if(attr.fields==null){
		IG.err("no fields found");
		return null;
	    }
	    if(index<0 || index>=attr.fields.length){
		IG.err("invalid index number "+index);
		return null;
	    }
	    return attr.fields[index];
	}
	
	/**
	   currently fieldName is case insensitive.
	*/
	public int fieldIndex(String fieldName){
	    if(attr.fields==null){
		IG.err("no fields found");
		return -1;
	    }
	    
	    String lowerName = fieldName.toLowerCase();
	    for(int i=0; i<attr.fields.length; i++){
		
		//IG.p("\""+attr.fields[i].name+"\"");
		//IG.p("field[i]: \""+attr.fields[i].name.toLowerCase()+"\" "+attr.fields[i].name.toLowerCase().length());
		//for(int j=0; j<attr.fields[i].name.length(); j++){
		//    IG.p(j+":\""+ attr.fields[i].name.charAt(j)+"\"");
		//}
		//IG.p("fieldName: \""+lowerName+"\" "+fieldName.length());
		
		if(lowerName.equals(attr.fields[i].name.toLowerCase())){
		    return i;
		}
	    }
	    IG.err("no field named "+fieldName+" is found");
	    return -1;
	}
	
	public String text(int index){ return textAttr(index); }
	public String textAttr(int index){
	    if(attr==null){
		IG.err("no attributes found");
		return "";
	    }
	    if(index<0 || index>=attr.text.length){
		IG.err("invalid index number "+index);
		return "";
	    }
	    if(attr.text[index]!=null) return attr.text[index];
	    return "";
	}
	
	
	/**
	   currently fieldName is case insensitive.
	*/
	public String text(String fieldName){ return textAttr(fieldName); }
	public String textAttr(String fieldName){
	    int idx = fieldIndex(fieldName);
	    if(idx<0) return "";
	    return textAttr(idx);
	}
	
	public double num(int index){ return numAttr(index); }
	public double numAttr(int index){
	    if(attr==null){
		IG.err("no attributes found");
		return 0;
	    }
	    if(index<0 || index>=attr.number.length){
		IG.err("invalid index number "+index);
		return 0;
	    }
	    return attr.number[index];
	}
	
	/**
	   currently fieldName is case insensitive.
	*/
	public double num(String fieldName){ return numAttr(fieldName); }
	public double numAttr(String fieldName){
	    int idx = fieldIndex(fieldName);
	    if(idx<0) return 0;
	    return numAttr(idx);
	}
	
	
	
	// colors and weights
	
	/** @return returns whatever Color of any graphics member. (first found) */
	public IColor clr(){ return object.clr(); }
	
	
	/** @return returns weight in attribute if any attribute exists. if not default weight in IConfig */
	public float weight(){ return object.weight(); }
	
    
	public int redInt(){ return clr().getRed(); }
	public int greenInt(){ return clr().getGreen(); }
	public int blueInt(){ return clr().getBlue(); }
	public int alphaInt(){ return clr().getAlpha(); }
	public int grayInt(){ return clr().getGrey(); }
	public int greyInt(){ return grayInt(); }
	public double red(){ return ((double)redInt()/255.); }
	public double green(){ return ((double)greenInt()/255.); }
	public double blue(){ return ((double)blueInt()/255.); }
	public double alpha(){ return ((double)alphaInt()/255.); }
	public double gray(){ return ((double)grayInt()/255.); }
	
	
	public Shape clr(IColor c){ object.clr(c); return this; }
	public Shape clr(IColor c, int alpha){ object.clr(c,alpha); return this; }
	
	public Shape clr(IColor c, float alpha){ object.clr(c,alpha); return this; }
	public Shape clr(IColor c, double alpha){ return clr(c,(float)alpha); }
	
	public Color awtColor(){ return object.awtColor(); }
	public Color getAWTColor(){ return awtColor(); }
	
	public Shape clr(Color c){ return clr(new IColor(c)); }
	public Shape clr(Color c, int alpha){ return clr(new IColor(c),alpha); }
	public Shape clr(Color c, float alpha){ return clr(new IColor(c),alpha); }
	public Shape clr(Color c, double alpha){ return clr(new IColor(c),alpha); }
	
	public IColor getColor(){ return clr(); }
	
	public Shape clr(int gray){ object.clr(gray); return this; }
	public Shape clr(double dgray){ object.clr(dgray); return this; }
	public Shape clr(float fgray){ object.clr(fgray); return this; }
	public Shape clr(int gray, int alpha){ object.clr(gray,alpha); return this; }
	public Shape clr(double dgray, double dalpha){ object.clr(dgray,dalpha); return this; }
	public Shape clr(float fgray, float falpha){ object.clr(fgray,falpha); return this; }
	public Shape clr(int r, int g, int b){ object.clr(r,g,b); return this; }
	public Shape clr(double dr, double dg, double db){ object.clr(dr,dg,db); return this; }
	public Shape clr(float fr, float fg, float fb){ object.clr(fr,fg,fb); return this; }
	public Shape clr(int r, int g, int b, int a){ object.clr(r,g,b,a); return this; }
	public Shape clr(double dr, double dg, double db, double da){ object.clr(dr,dg,db,da); return this; }
	public Shape clr(float fr, float fg, float fb, float fa){ object.clr(fr,fg,fb,fa); return this; }
	
	public Shape hsb(double dh, double ds, double db, double da){ object.hsb(dh,ds,db,da); return this; }
	public Shape hsb(float h, float s, float b, float a){ object.hsb(h,s,b,a); return this; }
	public Shape hsb(double dh, double ds, double db){ object.hsb(dh,ds,db); return this; }
	public Shape hsb(float h, float s, float b){ object.hsb(h,s,b); return this; }
	
	public Shape weight(double w){ return weight((float)w); }
	public Shape weight(float w){ object.weight(w); return this; }
	
    }
    
    public static class AttributeField{
	public String name;
	public char type;
	public int length;
	public int fractionLength;
	public int workspaceID;
    }
    
    public static class Attributes{
	public long length;
	public String[] text;
	public double[] number;
	
	public AttributeField[] fields; // shared per database
	
	public Attributes(int len){
	    length = len;
	    text = new String[len];
	    number = new double[len];
	}
    }
    
    public static class AttributeHeader{
	public long recordNum;
	public long firstDataPos;
	public long recordLen;
	public long fieldNum;
	public AttributeField[] fields;
    }
}