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

package igeo.gui;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.text.*;
import java.lang.reflect.*;

import igeo.*;

/**
   Viewer window to show parameters and relationship of parameters.
   Implementation is not completed at all yet.
   
   @author Satoru Sugihara
*/
public class IParameterViewer extends Component{

    /** top down flow or bottom up flow */
    public static boolean bottomUp=true; // false; //
    
    public static Color bgcolor = Color.white;
    
    public static int paramXSpacing=160;
    public static int paramYSpacing=40;
    
    public ArrayList<IParameter> parameters;
    public ArrayList<IParameterGraphic> pgraphics;
    
        
    public IDoubleBuffer db;
    
    public IParameterViewer(int x, int y, int width, int height){
	super();
	setBounds(x,y,width,height);
	parameters = new ArrayList<IParameter>();
	pgraphics = new ArrayList<IParameterGraphic>();
	db = new IDoubleBuffer(this);
    }
    
    public IParameterGraphic addParameter(IParameter param, int x, int y){
	if(!parameters.contains(param)){
	    parameters.add(param);
	    IParameterGraphic pg = new IParameterGraphic(param);
	    pgraphics.add(pg);
	    pg.setSize(getFontMetrics(IParameterGraphic.font));
	    pg.setLocation(x,y);
	    // add child elements recursively
	    //IParameter[] params = IParameterGraphic.getOperand(param);
	    IParameter[] params = IParameterGraphic.getOperand2(param);
	    for(int i=0; params!=null && i<params.length; i++){
		int px = x+paramXSpacing*i;
		int py = y+paramYSpacing;
		if(!bottomUp) py = y-paramYSpacing;
		pg.addLink(addParameter(params[i],px,py));
	    }
	    return pg;
	}
	else{
	    return findParameterGraphic(param, pgraphics);
	}
	
    }
    
    public void paint(Graphics g){
	Graphics og= db.getGraphics();
	
	og.setColor(bgcolor);
	og.fillRect(0,0,getWidth(),getHeight());
	
	for(int i=0; i<pgraphics.size(); i++){
	    pgraphics.get(i).paint((Graphics2D)og);
	}
	og.drawRect(0,0,getWidth()-1,getHeight()-1);
	
	db.paint(g);
    }
    public void update(Graphics g){ paint(g); }
    
    public void setSize(Dimension d){
	this.setSize(d.width, d.height);
    }
    public void setSize(int w, int h){
	super.setSize(w,h);
	db.setSize(w,h);
    }
    
    
    public static IParameterGraphic
	findParameterGraphic(IParameter param,
			     ArrayList<IParameterGraphic> parameters){
	for(int i=0; i<parameters.size(); i++)
	    if(parameters.get(i).parameter == param) return parameters.get(i);
	return null;
    }
    
    public static class IParameterGraphic{
	
	public static int topBlank=0;
	public static int bottomBlank=6;
	public static int leftBlank=4;
	public static int rightBlank=6; //4;
	public static int handleHeight=4;
	public static int handleWidth=8;
	public static int handleGap=6;
	public static int cornerSizeX=6;
	public static int cornerSizeY=8;
	
	public static Color bgcolor=new Color(140,140,140,100);
	//public static Color fgcolor=Color.black;
	public static Color typeColor=new Color(80,80,80);
	public static Color valueColor=Color.black;
	public static Color handleColor=new Color(60,60,60);
	public static Color linkColor=new Color(0,0,0,192);
	public static BasicStroke linkStroke =
	    new BasicStroke(0.5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
	
	public static int fontSize=12;
	public static Font font = new Font("Sanserif", Font.PLAIN, fontSize);
	
	public static boolean showValue=true; //false;
	
	
	public String type, value;
	//public String label;
	public Rectangle bounds;
	public GeneralPath bgShape;
	public int fontHeight, fontWidth1, fontWidth2;
	
	public int inputNum=0;
	
	public IParameter parameter;
	public ArrayList<IParameterGraphic> linkedParameters=null;
	
	
	public IParameterGraphic(IParameter p){
	    //setParameter(p);
	    setParameter2(p);
	}
		
	public void addLink(IParameter linked, ArrayList<IParameterGraphic> parameters){
	    IParameterGraphic pg = findParameterGraphic(linked, parameters);
	    if(pg!=null) addLink(pg);
	    else{
		IOut.p("parameter cannot be found"); //
	    }
	}
	
	public void addLink(IParameterGraphic linked){
	    if(linkedParameters==null)
		linkedParameters = new ArrayList<IParameterGraphic>();
	    linkedParameters.add(linked);
	    inputNum = linkedParameters.size();
	}
	
	public Point getInputLocation(int i){
	    return new Point(bounds.x+i*(handleGap+handleWidth)+handleWidth/2,
			     bounds.y+(bottomUp?bounds.height:0));
	}
	
	public Point getOutputLocation(){
	    return new Point(bounds.x+handleWidth/2, bounds.y+(bottomUp?0:bounds.height));
	}
	
	public void paint(Graphics2D g){
	    
	    drawBGShape(g);
	    
	    //if(label!=null){
	    if(type!=null){
		
		g.setFont(font);
		//g.setColor(fgcolor);
		//g.drawString(label, bounds.x+leftBlank, bounds.y+topBlank+fontHeight);
		
		g.setColor(typeColor);
		g.drawString(type, bounds.x+leftBlank, bounds.y+topBlank+fontHeight);
		g.setColor(valueColor);
		g.drawString(value, bounds.x+leftBlank+fontWidth1, bounds.y+topBlank+fontHeight);
		
	    }
	    
	    // input handle
	    for(int i=0; i<inputNum; i++){
		g.setColor(handleColor);
		g.fillRect(bounds.x + i*(handleGap+handleWidth),
			   bounds.y + (bottomUp?(bounds.height-handleHeight):0),
			   handleWidth, handleHeight);
		
		g.setColor(linkColor);
		g.setStroke(linkStroke);
		Point linkPt1 = linkedParameters.get(i).getOutputLocation();
		Point linkPt2 = this.getInputLocation(i);
		g.drawLine(linkPt1.x, linkPt1.y, linkPt2.x, linkPt2.y);
		//g.draw(new Line2D.Float(linkPt1.x, linkPt1.y, linkPt2.x, linkPt2.y));
	    }
	    
	    // output handle
	    g.setColor(handleColor);
	    g.fillRect(bounds.x, bounds.y+(bottomUp?0:bounds.height-handleHeight),
		       handleWidth, handleHeight);
	}
	
	//public void setLabel(String label){ this.label=label; }
	//public void setLabel(String type, String value){ label = type + ":" + value; }
	public void setLabel(String type, String value){
	    this.type = type + ": ";
	    this.value = value;
	}
	
	public void setLocation(int x, int y){
	    if(bounds==null) bounds = new Rectangle();
	    bounds.x=x; bounds.y=y;
	}
	public void setSize(int w, int h){
	    if(bounds==null) bounds = new Rectangle();
	    bounds.width=w; bounds.height=h;
	}
	public void setSize(FontMetrics metrics){
	    fontHeight = metrics.getAscent()+metrics.getDescent();
	    
	    //IOut.p("parameter="+parameter);
	    //IOut.p("type="+type);
	    //IOut.p("value="+value);
	    fontWidth1 = metrics.stringWidth(type);
	    fontWidth2 = metrics.stringWidth(value);
	    if(bounds==null) bounds = new Rectangle();
	    bounds.width = fontWidth1 + fontWidth2 + leftBlank + rightBlank;
	    bounds.height = fontHeight + topBlank + bottomBlank;
	}
	
	public void drawBGShape(Graphics2D g){
	    if(bgShape==null){
		bgShape = new GeneralPath(GeneralPath.WIND_EVEN_ODD,6);
		bgShape.moveTo(bounds.x,bounds.y);
		bgShape.lineTo(bounds.x,bounds.y+bounds.height);
		if(bottomUp){
		    bgShape.lineTo(bounds.x+bounds.width,bounds.y+bounds.height);
		    bgShape.lineTo(bounds.x+bounds.width,bounds.y+cornerSizeY);
		    bgShape.lineTo(bounds.x+bounds.width-cornerSizeX,bounds.y);
		}
		else{
		    bgShape.lineTo(bounds.x+bounds.width-cornerSizeX,bounds.y+bounds.height);
		    bgShape.lineTo(bounds.x+bounds.width,bounds.y+bounds.height-cornerSizeY);
		    bgShape.lineTo(bounds.x+bounds.width,bounds.y);
		}
		bgShape.closePath();
	    }
	    g.setColor(bgcolor);
	    g.fill(bgShape);
	}
	
	public void setParameter2(IParameter p){
	    String type=null;
	    String value=null;
	    
	    parameter = p;
	    
	    if(p instanceof IReferenceParameter) p = ((IReferenceParameter)p).operator();
	    
	    if(p instanceof IBoolOp){
		type="bool";
		if(p instanceof IBool) value = toString((IBool)p);
	    }
	    else if(p instanceof IIntegerOp){
		type="int";
		if(p instanceof IInteger) value = toString((IInteger)p);
	    }
	    else if(p instanceof IDoubleOp){
		type="double";
		if(p instanceof IDouble) value = toString((IDouble)p);
	    }
	    else if(p instanceof IVec2Op){
		type="vector2D";
		if(p instanceof IVec2) value = toString((IVec2)p);
	    }
	    else if(p instanceof IVecOp){
		type="vector3D";
		if(p instanceof IVec) value = toString((IVec)p);
	    }
	    else if(p instanceof IVec4Op){
		type="vector4D";
		if(p instanceof IVec4) value = toString((IVec4)p);
	    }
	    
	    if(value==null){
		if(!(p instanceof IEntityParameter)){
		    value = p.getClass().toString();
		    int idx=-1;
		    if((idx=value.lastIndexOf('$'))>0) value=value.substring(++idx);
		    else if((idx=value.lastIndexOf('.'))>0) value=value.substring(++idx);
		    else if((idx=value.lastIndexOf(' '))>0) value=value.substring(++idx);
		    value = value.toLowerCase();
		}
		else value = "unknown"; 
	    }
	    
	    setLabel(type,value);
	}
	
	public void setParameter(IParameter p){
	    String type=null;
	    String value=null;
	    parameter = p;
	    if(p instanceof IBoolOp){
		type = "bool";
		
		IBoolOp op = (IBoolOp)p;
		if(op instanceof IBoolR) op = ((IBoolR)op).operator();
		
		if(op instanceof IBool) value = toString((IBool)op);
		else{
		    if(op instanceof IBoolR.And) value="and";
		    else if(op instanceof IBoolR.Or) value="or";
		    else if(op instanceof IBoolR.Not) value="not";
		    else{ IOut.p("no such operator class "+op); value="?"; }
		    
		    if(showValue) value+=" <"+toString(op.get())+">";
		}
	    }
	    else if(p instanceof IIntegerOp){
		type = "int";
		
		IIntegerOp op = (IIntegerOp)p;
		if(op instanceof IIntegerR) op = ((IIntegerR)op).operator();
		
		if(op instanceof IInteger) value = toString((IInteger)op);
		else{
		    if(op instanceof IIntegerR.Add) value="add";
		    else if(op instanceof IIntegerR.Sub) value="sub";
		    else if(op instanceof IIntegerR.Mul) value="mul";
		    else if(op instanceof IIntegerR.Div) value="div";
		    else if(op instanceof IIntegerR.Neg) value="neg";
		    else if(op instanceof IIntegerR.Mod) value="mod";
		    else if(op instanceof IIntegerR.FromDouble) value="fromDouble";
		    else{ IOut.p("no such operator "+op); value="?"; }
		    
		    if(showValue) value+=" <"+toString(op.get())+">";
		}
	    }
	    else if(p instanceof IDoubleOp){
		type = "double";
		
		IDoubleOp op = (IDoubleOp)p;
		if(op instanceof IDoubleR) op = ((IDoubleR)op).operator();
		
		if(op instanceof IDouble) value = toString((IDouble)op);
		else{
		    if(op instanceof IDoubleR.Add) value="add";
		    else if(op instanceof IDoubleR.Sub) value="sub";
		    else if(op instanceof IDoubleR.Mul) value="mul";
		    else if(op instanceof IDoubleR.Div) value="div";
		    else if(op instanceof IDoubleR.Neg) value="neg";
		    else if(op instanceof IDoubleR.Inv) value="inv";
		    else if(op instanceof IDoubleR.Abs) value="abs";
		    else if(op instanceof IDoubleR.FromInt) value="fromInt";
		    else if(op instanceof IDoubleR.Pow) value="pow";
		    else if(op instanceof IDoubleR.Sq) value="sq";
		    else if(op instanceof IDoubleR.Sqrt) value="sqrt";
		    else if(op instanceof IDoubleR.Exp) value="exp";
		    else if(op instanceof IDoubleR.Log) value="log";
		    else if(op instanceof IDoubleR.Sin) value="sin";
		    else if(op instanceof IDoubleR.Cos) value="cos";
		    else if(op instanceof IDoubleR.Tan) value="tan";
		    else if(op instanceof IDoubleR.ASin) value="asin";
		    else if(op instanceof IDoubleR.ACos) value="acos";
		    else if(op instanceof IDoubleR.ATan) value="atan";
		    else if(op instanceof IDoubleR.ATan2) value="atan2";
		    else if(op instanceof IDoubleR.Deg) value="deg";
		    else if(op instanceof IDoubleR.Rad) value="rad";
		    else if(op instanceof IVec2R.Dot) value="dot";
		    else if(op instanceof IVec2R.Angle) value="angle";
		    else if(op instanceof IVec2R.Len) value="len";
		    else if(op instanceof IVec2R.Len2) value="len2";
		    else if(op instanceof IVec2R.X) value="x";
		    else if(op instanceof IVec2R.Y) value="y";
		    else if(op instanceof IVecR.Dot) value="dot";
		    else if(op instanceof IVecR.Angle) value="angle";
		    //else if(op instanceof IVecR.AngleWithAxis) value="angleWithAxis";
		    else if(op instanceof IVecR.Len) value="len";
		    else if(op instanceof IVecR.Len2) value="len2";
		    else if(op instanceof IVecR.X) value="x";
		    else if(op instanceof IVecR.Y) value="y";
		    else if(op instanceof IVecR.Z) value="z";
		    else if(op instanceof IVec4R.X) value="x";
		    else if(op instanceof IVec4R.Y) value="y";
		    else if(op instanceof IVec4R.Z) value="z";
		    else if(op instanceof IVec4R.W) value="w";
		    else{ IOut.p("no such operator "+op); value="?"; }
		
		    if(showValue) value+=" <"+toString(op.get())+">";
		}
	    }
	    else if(p instanceof IVec2Op){
		type = "vector2D";
		
		IVec2Op op = (IVec2Op)p;
		if(op instanceof IVec2R) op = ((IVec2R)op).operator();
		
		if(op instanceof IVec2) value = toString((IVec2)op);
		else{
		    if(op instanceof IVec2R.Add) value="add";
		    else if(op instanceof IVec2R.Sub) value="sub";
		    else if(op instanceof IVec2R.Mul) value="mul";
		    else if(op instanceof IVec2R.Div) value="div";
		    else if(op instanceof IVec2R.Neg) value="neg";
		    else if(op instanceof IVec2R.Rot) value="rot";
		    else if(op instanceof IVec2R.FromXY) value="fromXY";
		    else if(op instanceof IVec2R.Unit) value="unit";
		    else if(op instanceof IVec2R.Ortho) value="ortho";
		    else if(op instanceof IVec2R.SetLen) value="setLen";
		    else{ IOut.p("no such operator "+op); value="?"; }
		    
		    if(showValue) value+=" <"+toString(op.get())+">";
		}
	    }
	    else if(p instanceof IVecOp){
		type = "vector3D";
		
		IVecOp op = (IVecOp)p;
		if(op instanceof IVecR) op = ((IVecR)op).operator();
		
		if(op instanceof IVec) value = toString((IVec)op);
		else{
		    if(op instanceof IVecR.Add) value="add";
		    else if(op instanceof IVecR.Sub) value="sub";
		    else if(op instanceof IVecR.Mul) value="mul";
		    else if(op instanceof IVecR.Div) value="div";
		    else if(op instanceof IVecR.Neg) value="neg";
		    else if(op instanceof IVecR.FromXYZ) value="fromXYZ";
		    else if(op instanceof IVecR.Cross) value="cross";
		    else if(op instanceof IVec2R.Cross) value="cross";
		    else if(op instanceof IVecR.Rot) value="rot";
		    else if(op instanceof IVecR.Unit) value="unit";
		    else if(op instanceof IVecR.SetLen) value="setLen";
		    else{ IOut.p("no such operator "+op); value="?"; }
		    
		    if(showValue) value+=" <"+toString(op.get())+">";
		}
	    }
	    else if(p instanceof IVec4Op){
		type = "vector4D";
		
		IVec4Op op = (IVec4Op)p;
		if(op instanceof IVec4R) op = ((IVec4R)op).operator();
		
		if(op instanceof IVec4) value = toString((IVec4)op);
		else{
		    if(op instanceof IVec4R.FromVec) value="fromVec";
		    else if(op instanceof IVec4R.FromVecAndW) value="fromVecW";
		    else if(op instanceof IVec4R.FromXYZ) value="fromXYZ";
		    else if(op instanceof IVec4R.FromXYZW) value="fromXYZW";
		    else if(op instanceof IVec4R.Add) value="add";
		    else if(op instanceof IVec4R.Sub) value="sub";
		    else if(op instanceof IVec4R.Mul) value="mul";
		    else if(op instanceof IVec4R.Div) value="div";
		    else if(op instanceof IVec4R.Neg) value="neg";
		    else if(op instanceof IVec4R.Cross) value="cross";
		    else if(op instanceof IVec4R.Rot) value="rot";
		    else if(op instanceof IVec4R.Unit) value="unit";
		    else if(op instanceof IVec4R.SetLen) value="setLen";
		    else{ IOut.p("no such operator "+op); value="?"; }
		    
		    if(showValue) value+=" <"+toString(op.get())+">";
		}
	    }
	    else IOut.p("no such class "+p); 
	    
	    setLabel(type, value);
	}
	
	public static IParameter[] getOperand2(IParameter p){
	    if(p instanceof IReferenceParameter) p = ((IReferenceParameter)p).operator();
	    if(p instanceof IEntityParameter) return null;
	    
	    Field[] f = p.getClass().getFields();
	    ArrayList<IParameter> ops=null;
	    IOut.p(p.toString());
	    for(int i=0; i<f.length; i++){
		IOut.p("field"+i+" "+f[i].toString()); //
		
		if(!Modifier.isStatic(f[i].getModifiers())){
		    Class type = f[i].getType();
		    Class[] scls = type.getInterfaces();
		    boolean flag=false;
		    for(int j=0; !flag&&j<scls.length; j++)
			if(scls[j].equals(IParameter.class)) flag=true;
		    if(flag){
			if(ops==null) ops=new ArrayList<IParameter>();
			IParameter op = null;
			try{ op = (IParameter)f[i].get(p); }
			catch(Exception e){ e.printStackTrace(); }
			if(op!=null) ops.add(op); 
		    }
		}
	    }
	    if(ops==null || ops.size()==0) return null;
	    IParameter[] operands = new IParameter[ops.size()];
	    for(int i=0; i<ops.size(); i++) operands[i] = ops.get(i);
	    return operands;
	}
	
	public static IParameter[] getOperand(IParameter p){
	    
	    if(p instanceof IBoolOp){
		IBoolOp op = (IBoolOp)p;
		if(op instanceof IBoolR) op = ((IBoolR)op).operator();
		
		if(op instanceof IBool) return null;
		if(op instanceof IBoolR.And)
		    return new IParameter[]{ ((IBoolR.And)op).v1, ((IBoolR.And)op).v2 };
		if(op instanceof IBoolR.Or)
		    return new IParameter[]{ ((IBoolR.Or)op).v1, ((IBoolR.Or)op).v2 };
		if(op instanceof IBoolR.Not)
		    return new IParameter[]{ ((IBoolR.Not)op).v };
		IOut.p("ERROR: no matching operation class in IBoolR");
		IOut.p(op); //
		return null;
	    }
	    if(p instanceof IIntegerOp){
		IIntegerOp op = (IIntegerOp)p;
		if(op instanceof IIntegerR) op = ((IIntegerR)op).operator();
		
		if(op instanceof IInteger) return null;
		if(op instanceof IIntegerR.Add)
		    return new IParameter[]{ ((IIntegerR.Add)op).v1,
					      ((IIntegerR.Add)op).v2 };
		if(op instanceof IIntegerR.Sub)
		    return new IParameter[]{ ((IIntegerR.Sub)op).v1,
					      ((IIntegerR.Sub)op).v2 };
		if(op instanceof IIntegerR.Mul)
		    return new IParameter[]{ ((IIntegerR.Mul)op).v1,
					      ((IIntegerR.Mul)op).v2 };
		if(op instanceof IIntegerR.Div)
		    return new IParameter[]{ ((IIntegerR.Div)op).v1,
					      ((IIntegerR.Div)op).v2 };
		if(op instanceof IIntegerR.Neg)
		    return new IParameter[]{ ((IIntegerR.Neg)op).v };
		if(op instanceof IIntegerR.Mod)
		    return new IParameter[]{ ((IIntegerR.Mod)op).v1,
					      ((IIntegerR.Mod)op).v2 };
		if(op instanceof IIntegerR.FromDouble)
		    return new IParameter[]{ ((IIntegerR.FromDouble)op).v };
	    
		IOut.p("ERROR: no matching operation class in IIntegerR");
		IOut.p(op); //
		return null;
	    }
	    if(p instanceof IDoubleOp){
		IDoubleOp op = (IDoubleOp)p;
		if(op instanceof IDoubleR) op = ((IDoubleR)op).operator();
		
		if(op instanceof IDouble) return null;
		if(op instanceof IDoubleR.Add)
		    return new IParameter[]{ ((IDoubleR.Add)op).v1,
					      ((IDoubleR.Add)op).v2 };
		if(op instanceof IDoubleR.Sub)
		    return new IParameter[]{ ((IDoubleR.Sub)op).v1,
					      ((IDoubleR.Sub)op).v2 };
		if(op instanceof IDoubleR.Mul)
		    return new IParameter[]{ ((IDoubleR.Mul)op).v1,
					      ((IDoubleR.Mul)op).v2 };
		if(op instanceof IDoubleR.Div)
		    return new IParameter[]{ ((IDoubleR.Div)op).v1,
					      ((IDoubleR.Div)op).v2 };
		if(op instanceof IDoubleR.Neg)
		    return new IParameter[]{ ((IDoubleR.Neg)op).v };
		if(op instanceof IDoubleR.Inv)
		    return new IParameter[]{ ((IDoubleR.Neg)op).v };
		if(op instanceof IDoubleR.Abs)
		    return new IParameter[]{ ((IDoubleR.Abs)op).v };
		if(op instanceof IDoubleR.FromInt)
		    return new IParameter[]{ ((IDoubleR.FromInt)op).v };
		if(op instanceof IDoubleR.Pow)
		    return new IParameter[]{ ((IDoubleR.Pow)op).v1,
					      ((IDoubleR.Pow)op).v2 };
		if(op instanceof IDoubleR.Sq)
		    return new IParameter[]{ ((IDoubleR.Sq)op).v };
		if(op instanceof IDoubleR.Sqrt)
		    return new IParameter[]{ ((IDoubleR.Sqrt)op).v };
		if(op instanceof IDoubleR.Exp)
		    return new IParameter[]{ ((IDoubleR.Exp)op).v };
		if(op instanceof IDoubleR.Log)
		    return new IParameter[]{ ((IDoubleR.Log)op).v };
		if(op instanceof IDoubleR.Sin)
		    return new IParameter[]{ ((IDoubleR.Sin)op).v };
		if(op instanceof IDoubleR.Cos)
		    return new IParameter[]{ ((IDoubleR.Cos)op).v };
		if(op instanceof IDoubleR.Tan)
		    return new IParameter[]{ ((IDoubleR.Tan)op).v };
		if(op instanceof IDoubleR.ASin)
		    return new IParameter[]{ ((IDoubleR.ASin)op).v };
		if(op instanceof IDoubleR.ACos)
		    return new IParameter[]{ ((IDoubleR.ACos)op).v };
		if(op instanceof IDoubleR.ATan)
		    return new IParameter[]{ ((IDoubleR.ATan)op).v };
		if(op instanceof IDoubleR.ATan2)
		    return new IParameter[]{ ((IDoubleR.ATan2)op).y,
					      ((IDoubleR.ATan2)op).x };
		if(op instanceof IDoubleR.Deg)
		    return new IParameter[]{ ((IDoubleR.Deg)op).v };
		if(op instanceof IDoubleR.Rad)
		    return new IParameter[]{ ((IDoubleR.Rad)op).v };
		if(op instanceof IVec2R.Dot)
		    return new IParameter[]{ ((IVec2R.Dot)op).v1,
					      ((IVec2R.Dot)op).v2 };
		if(op instanceof IVec2R.Angle)
		    return new IParameter[]{ ((IVec2R.Angle)op).v1,
					      ((IVec2R.Angle)op).v2 };
		if(op instanceof IVec2R.Len)
		    return new IParameter[]{ ((IVec2R.Len)op).v };
		if(op instanceof IVec2R.Len2)
		    return new IParameter[]{ ((IVec2R.Len2)op).v };
		if(op instanceof IVec2R.X)
		    return new IParameter[]{ ((IVec2R.X)op).v };
		if(op instanceof IVec2R.Y)
		    return new IParameter[]{ ((IVec2R.Y)op).v };
		if(op instanceof IVecR.Dot)
		    return new IParameter[]{ ((IVecR.Dot)op).v1,
					      ((IVecR.Dot)op).v2 };
		if(op instanceof IVecR.Angle)
		    return new IParameter[]{ ((IVecR.Angle)op).v1,
					      ((IVecR.Angle)op).v2 };
		//if(op instanceof IVecR.AngleWithAxis)
		//    return new IParameter[]{ ((IVecR.AngleWithAxis)op).v1,
		//			     ((IVecR.AngleWithAxis)op).v2,
		//			     ((IVecR.AngleWithAxis)op).axis };
		if(op instanceof IVecR.Len)
		    return new IParameter[]{ ((IVecR.Len)op).v };
		if(op instanceof IVecR.Len2)
		    return new IParameter[]{ ((IVecR.Len2)op).v };
		if(op instanceof IVecR.X)
		    return new IParameter[]{ ((IVecR.X)op).v };
		if(op instanceof IVecR.Y)
		    return new IParameter[]{ ((IVecR.Y)op).v };
		if(op instanceof IVecR.Z)
		    return new IParameter[]{ ((IVecR.Z)op).v };
		if(op instanceof IVec4R.X)
		    return new IParameter[]{ ((IVec4R.X)op).v };
		if(op instanceof IVec4R.Y)
		    return new IParameter[]{ ((IVec4R.Y)op).v };
		if(op instanceof IVec4R.Z)
		    return new IParameter[]{ ((IVec4R.Z)op).v };
		if(op instanceof IVec4R.W)
		    return new IParameter[]{ ((IVec4R.W)op).v };
		
		IOut.p("ERROR: no matching operation class in IDouble");
		IOut.p(op); //
		return null;
	    }
	    if(p instanceof IVec2Op){
		IVec2Op op = (IVec2Op)p;
		if(op instanceof IVec2R) op = ((IVec2R)op).operator();
		
		if(op instanceof IVec2) return null;
		if(op instanceof IVec2R.Add)
		    return new IParameter[]{ ((IVec2R.Add)op).v1,
					      ((IVec2R.Add)op).v2 };
		if(op instanceof IVec2R.Sub)
		    return new IParameter[]{ ((IVec2R.Sub)op).v1,
					      ((IVec2R.Sub)op).v2 };
		if(op instanceof IVec2R.Mul)
		    return new IParameter[]{ ((IVec2R.Mul)op).v,
					      ((IVec2R.Mul)op).d };
		if(op instanceof IVec2R.Div)
		    return new IParameter[]{ ((IVec2R.Div)op).v,
					      ((IVec2R.Div)op).d };
		if(op instanceof IVec2R.Neg)
		    return new IParameter[]{ ((IVec2R.Neg)op).v };
		if(op instanceof IVec2R.Rot)
		    return new IParameter[]{ ((IVec2R.Rot)op).v,
					      ((IVec2R.Rot)op).angle };
		if(op instanceof IVec2R.FromXY)
		    return new IParameter[]{ ((IVec2R.FromXY)op).x,
					      ((IVec2R.FromXY)op).y };
		if(op instanceof IVec2R.Unit)
		    return new IParameter[]{ ((IVec2R.Unit)op).v };
		if(op instanceof IVec2R.Ortho)
		    return new IParameter[]{ ((IVec2R.Ortho)op).v };
		if(op instanceof IVec2R.SetLen)
		    return new IParameter[]{ ((IVec2R.SetLen)op).v,
					      ((IVec2R.SetLen)op).l };
		IOut.p("ERROR: no matching operation class in IVec2R");
		IOut.p(op); //
		return null;
	    }
	    if(p instanceof IVecOp){
		IVecOp op = (IVecOp)p;
		if(op instanceof IVecR) op = ((IVecR)op).operator();
		
		if(op instanceof IVec) return null;
		if(op instanceof IVecR.Add)
		    return new IParameter[]{ ((IVecR.Add)op).v1,
					      ((IVecR.Add)op).v2 };
		if(op instanceof IVecR.Sub)
		    return new IParameter[]{ ((IVecR.Sub)op).v1,
					      ((IVecR.Sub)op).v2 };
		if(op instanceof IVecR.Mul)
		    return new IParameter[]{ ((IVecR.Mul)op).v,
					      ((IVecR.Mul)op).d };
		if(op instanceof IVecR.Div)
		    return new IParameter[]{ ((IVecR.Div)op).v,
					      ((IVecR.Div)op).d };
		if(op instanceof IVecR.Neg)
		    return new IParameter[]{ ((IVecR.Neg)op).v };
		if(op instanceof IVecR.FromXYZ)
		    return new IParameter[]{ ((IVecR.FromXYZ)op).x,
					      ((IVecR.FromXYZ)op).y,
					      ((IVecR.FromXYZ)op).z };
		if(op instanceof IVecR.Cross)
		    return new IParameter[]{ ((IVecR.Cross)op).v1,
					      ((IVecR.Cross)op).v2 };
		if(op instanceof IVec2R.Cross)
		    return new IParameter[]{ ((IVec2R.Cross)op).v1,
					      ((IVec2R.Cross)op).v2 };
		if(op instanceof IVecR.Rot)
		    return new IParameter[]{ ((IVecR.Rot)op).v,
					      ((IVecR.Rot)op).axis,
					      ((IVecR.Rot)op).angle };
		if(op instanceof IVecR.Unit)
		    return new IParameter[]{ ((IVecR.Unit)op).v };
		if(op instanceof IVecR.SetLen)
		    return new IParameter[]{ ((IVecR.SetLen)op).v,
					      ((IVecR.SetLen)op).l };
		IOut.p("ERROR: no matching operation class in IVecR");
		IOut.p(op); //
		return null;
	    }
	    if(p instanceof IVec4Op){
		IVec4Op op = (IVec4Op)p;
		if(op instanceof IVec4R) op = ((IVec4R)op).operator();
		
		if(op instanceof IVec4) return null;
		if(op instanceof IVec4R.FromVec)
		    return new IParameter[]{ ((IVec4R.FromVec)op).v };
		if(op instanceof IVec4R.FromVecAndW)
		    return new IParameter[]{ ((IVec4R.FromVecAndW)op).v,
					      ((IVec4R.FromVecAndW)op).w };
		if(op instanceof IVec4R.FromXYZ)
		    return new IParameter[]{ ((IVec4R.FromXYZ)op).x,
					      ((IVec4R.FromXYZ)op).y,
					      ((IVec4R.FromXYZ)op).z };
		if(op instanceof IVec4R.FromXYZW)
		    return new IParameter[]{ ((IVec4R.FromXYZW)op).x,
					      ((IVec4R.FromXYZW)op).y,
					      ((IVec4R.FromXYZW)op).z,
					      ((IVec4R.FromXYZW)op).w };
		if(op instanceof IVec4R.Add)
		    return new IParameter[]{ ((IVec4R.Add)op).v1,
					      ((IVec4R.Add)op).v2 };
		if(op instanceof IVec4R.Sub)
		    return new IParameter[]{ ((IVec4R.Sub)op).v1,
					      ((IVec4R.Sub)op).v2 };
		if(op instanceof IVec4R.Mul)
		    return new IParameter[]{ ((IVec4R.Mul)op).v,
					      ((IVec4R.Mul)op).d };
		if(op instanceof IVec4R.Div)
		    return new IParameter[]{ ((IVec4R.Div)op).v,
					      ((IVec4R.Div)op).d };
		if(op instanceof IVec4R.Neg)
		    return new IParameter[]{ ((IVec4R.Neg)op).v };
		if(op instanceof IVec4R.Cross)
		    return new IParameter[]{ ((IVec4R.Cross)op).v1,
					      ((IVec4R.Cross)op).v2 };
		if(op instanceof IVec4R.Rot)
		    return new IParameter[]{ ((IVec4R.Rot)op).v,
					      ((IVec4R.Rot)op).axis,
					      ((IVec4R.Rot)op).angle };
		if(op instanceof IVec4R.Unit)
		    return new IParameter[]{ ((IVec4R.Unit)op).v };
		if(op instanceof IVec4R.SetLen)
		    return new IParameter[]{ ((IVec4R.SetLen)op).v,
					      ((IVec4R.SetLen)op).l };
		
		IOut.p("ERROR: no matching operation class in IVec4R");
		IOut.p(op); //
		return null;
	    }
	    
	    IOut.p("ERROR: no matching class"); //
	    IOut.p(p); //
	    
	    return null;
	}
	
	public static void setupLink(ArrayList<IParameterGraphic> parameters){
	    
	    for(int i=0; i<parameters.size(); i++){
		IParameterGraphic pg = parameters.get(i);
		IParameter p = pg.parameter;
		
		IParameter[] operands = getOperand(p);
		if(operands!=null)
		    for(int j=0; j<operands.length; j++)
			pg.addLink(operands[j], parameters);
		
		
		/*
		if(p instanceof IBoolR){
		    IBoolOp op = ((IBoolR)p).operator();
		    if(op instanceof IBoolR.And){
			pg.addLink(((IBoolR.And)op).v1,parameters);
			pg.addLink(((IBoolR.And)op).v2,parameters);
		    }
		    else if(op instanceof IBoolR.Or){
			pg.addLink(((IBoolR.Or)op).v1,parameters);
			pg.addLink(((IBoolR.Or)op).v2,parameters);
		    }
		    else if(op instanceof IBoolR.Not){
			pg.addLink(((IBoolR.Not)op).v,parameters);
		    }
		}
		else if(p instanceof IIntegerR){
		    IIntegerOp op = ((IIntegerR)p).operator();
		    if(op instanceof IIntegerR.Add){
			pg.addLink(((IIntegerR.Add)op).v1,parameters);
			pg.addLink(((IIntegerR.Add)op).v2,parameters);
		    }
		    else if(op instanceof IIntegerR.Sub){
			pg.addLink(((IIntegerR.Sub)op).v1,parameters);
			pg.addLink(((IIntegerR.Sub)op).v2,parameters);
		    }
		    else if(op instanceof IIntegerR.Mul){
			pg.addLink(((IIntegerR.Mul)op).v1,parameters);
			pg.addLink(((IIntegerR.Mul)op).v2,parameters);
		    }
		    else if(op instanceof IIntegerR.Div){
			pg.addLink(((IIntegerR.Div)op).v1,parameters);
			pg.addLink(((IIntegerR.Div)op).v2,parameters);
		    }
		    else if(op instanceof IIntegerR.Neg){
			pg.addLink(((IIntegerR.Neg)op).v,parameters);
		    }
		    else if(op instanceof IIntegerR.Mod){
			pg.addLink(((IIntegerR.Mod)op).v1,parameters);
			pg.addLink(((IIntegerR.Mod)op).v2,parameters);
		    }
		    else if(op instanceof IIntegerR.FromDouble){
			pg.addLink(((IIntegerR.FromDouble)op).v,parameters);
		    }
		}
		else if(p instanceof IDoubleR){
		    IDoubleOp op = ((IDoubleR)p).operator();
		    if(op instanceof IDoubleR.Add){
			pg.addLink(((IDoubleR.Add)op).v1,parameters);
			pg.addLink(((IDoubleR.Add)op).v2,parameters);
		    }
		    else if(op instanceof IDoubleR.Sub){
			pg.addLink(((IDoubleR.Sub)op).v1,parameters);
			pg.addLink(((IDoubleR.Sub)op).v2,parameters);
		    }
		    else if(op instanceof IDoubleR.Mul){
			pg.addLink(((IDoubleR.Mul)op).v1,parameters);
			pg.addLink(((IDoubleR.Mul)op).v2,parameters);
		    }
		    else if(op instanceof IDoubleR.Div){
			pg.addLink(((IDoubleR.Div)op).v1,parameters);
			pg.addLink(((IDoubleR.Div)op).v2,parameters);
		    }
		    else if(op instanceof IDoubleR.Neg){
			pg.addLink(((IDoubleR.Neg)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.Abs){
			pg.addLink(((IDoubleR.Abs)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.FromInt){
			pg.addLink(((IDoubleR.FromInt)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.Pow){
			pg.addLink(((IDoubleR.Pow)op).v1,parameters);
			pg.addLink(((IDoubleR.Pow)op).v2,parameters);
		    }
		    else if(op instanceof IDoubleR.Sq){
			pg.addLink(((IDoubleR.Sq)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.Sqrt){
			pg.addLink(((IDoubleR.Sqrt)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.Exp){
			pg.addLink(((IDoubleR.Exp)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.Log){
			pg.addLink(((IDoubleR.Log)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.Sin){
			pg.addLink(((IDoubleR.Sin)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.Cos){
			pg.addLink(((IDoubleR.Cos)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.Tan){
			pg.addLink(((IDoubleR.Tan)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.ASin){
			pg.addLink(((IDoubleR.ASin)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.ACos){
			pg.addLink(((IDoubleR.ACos)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.ATan){
			pg.addLink(((IDoubleR.ATan)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.ATan2){
			pg.addLink(((IDoubleR.ATan2)op).y,parameters);
			pg.addLink(((IDoubleR.ATan2)op).x,parameters);
		    }
		    else if(op instanceof IDoubleR.Deg){
			pg.addLink(((IDoubleR.Deg)op).v,parameters);
		    }
		    else if(op instanceof IDoubleR.Rad){
			pg.addLink(((IDoubleR.Rad)op).v,parameters);
		    }
		    else if(op instanceof IVec2R.Dot){
			pg.addLink(((IVec2R.Dot)op).v1,parameters);
			pg.addLink(((IVec2R.Dot)op).v2,parameters);
		    }
		    else if(op instanceof IVec2R.Angle){
			pg.addLink(((IVec2R.Angle)op).v1,parameters);
			pg.addLink(((IVec2R.Angle)op).v2,parameters);
		    }
		    else if(op instanceof IVec2R.Len){
			pg.addLink(((IVec2R.Len)op).v,parameters);
		    }
		    else if(op instanceof IVec2R.Len2){
			pg.addLink(((IVec2R.Len2)op).v,parameters);
		    }
		    else if(op instanceof IVec2R.X){
			pg.addLink(((IVec2R.X)op).v,parameters);
		    }
		    else if(op instanceof IVec2R.Y){
			pg.addLink(((IVec2R.Y)op).v,parameters);
		    }
		    else if(op instanceof IVecR.Dot){
			pg.addLink(((IVecR.Dot)op).v1,parameters);
			pg.addLink(((IVecR.Dot)op).v2,parameters);
		    }
		    else if(op instanceof IVecR.Angle){
			pg.addLink(((IVecR.Angle)op).v1,parameters);
			pg.addLink(((IVecR.Angle)op).v2,parameters);
		    }
		    else if(op instanceof IVecR.AngleWithAxis){
			pg.addLink(((IVecR.AngleWithAxis)op).v1,parameters);
			pg.addLink(((IVecR.AngleWithAxis)op).v2,parameters);
			pg.addLink(((IVecR.AngleWithAxis)op).axis,parameters);
		    }
		    else if(op instanceof IVecR.Len){
			pg.addLink(((IVecR.Len)op).v,parameters);
		    }
		    else if(op instanceof IVecR.Len2){
			pg.addLink(((IVecR.Len2)op).v,parameters);
		    }
		    else if(op instanceof IVecR.X){
			pg.addLink(((IVecR.X)op).v,parameters);
		    }
		    else if(op instanceof IVecR.Y){
			pg.addLink(((IVecR.Y)op).v,parameters);
		    }
		    else if(op instanceof IVecR.Z){
			pg.addLink(((IVecR.Z)op).v,parameters);
		    }
		    else if(op instanceof IVec4R.X){
			pg.addLink(((IVec4R.X)op).v,parameters);
		    }
		    else if(op instanceof IVec4R.Y){
			pg.addLink(((IVec4R.Y)op).v,parameters);
		    }
		    else if(op instanceof IVec4R.Z){
			pg.addLink(((IVec4R.Z)op).v,parameters);
		    }
		    else if(op instanceof IVec4R.W){
			pg.addLink(((IVec4R.W)op).v,parameters);
		    }
		}
		else if(p instanceof IVec2R){
		    IVec2Op op = ((IVec2R)p).operator();
		    if(op instanceof IVec2R.Add){
			pg.addLink(((IVec2R.Add)op).v1,parameters);
			pg.addLink(((IVec2R.Add)op).v2,parameters);
		    }
		    else if(op instanceof IVec2R.Sub){
			pg.addLink(((IVec2R.Sub)op).v1,parameters);
			pg.addLink(((IVec2R.Sub)op).v2,parameters);
		    }
		    else if(op instanceof IVec2R.Mul){
			pg.addLink(((IVec2R.Mul)op).v,parameters);
			pg.addLink(((IVec2R.Mul)op).d,parameters);
		    }
		    else if(op instanceof IVec2R.Div){
			pg.addLink(((IVec2R.Div)op).v,parameters);
			pg.addLink(((IVec2R.Div)op).d,parameters);
		    }
		    else if(op instanceof IVec2R.Neg){
			pg.addLink(((IVec2R.Neg)op).v,parameters);
		    }
		    else if(op instanceof IVec2R.Rot){
			pg.addLink(((IVec2R.Rot)op).v,parameters);
			pg.addLink(((IVec2R.Rot)op).angle,parameters);
		    }
		    else if(op instanceof IVec2R.FromXY){
			pg.addLink(((IVec2R.FromXY)op).x,parameters);
			pg.addLink(((IVec2R.FromXY)op).y,parameters);
		    }
		    else if(op instanceof IVec2R.Unit){
			pg.addLink(((IVec2R.Unit)op).v,parameters);
		    }
		    else if(op instanceof IVec2R.Ortho){
			pg.addLink(((IVec2R.Ortho)op).v,parameters);
		    }
		    else if(op instanceof IVec2R.SetLen){
			pg.addLink(((IVec2R.SetLen)op).v,parameters);
			pg.addLink(((IVec2R.SetLen)op).l,parameters);
		    }
		}
		else if(p instanceof IVecR){
		    IVecOp op = ((IVecR)p).operator();
		    if(op instanceof IVecR.Add){
			pg.addLink(((IVecR.Add)op).v1,parameters);
			pg.addLink(((IVecR.Add)op).v2,parameters);
		    }
		    else if(op instanceof IVecR.Sub){
			pg.addLink(((IVecR.Sub)op).v1,parameters);
			pg.addLink(((IVecR.Sub)op).v2,parameters);
		    }
		    else if(op instanceof IVecR.Mul){
			pg.addLink(((IVecR.Mul)op).v,parameters);
			pg.addLink(((IVecR.Mul)op).d,parameters);
		    }
		    else if(op instanceof IVecR.Div){
			pg.addLink(((IVecR.Div)op).v,parameters);
			pg.addLink(((IVecR.Div)op).d,parameters);
		    }
		    else if(op instanceof IVecR.Neg){
			pg.addLink(((IVecR.Neg)op).v,parameters);
		    }
		    else if(op instanceof IVecR.FromXYZ){
			pg.addLink(((IVecR.FromXYZ)op).x,parameters);
			pg.addLink(((IVecR.FromXYZ)op).y,parameters);
			pg.addLink(((IVecR.FromXYZ)op).z,parameters);
		    }
		    else if(op instanceof IVecR.Cross){
			pg.addLink(((IVecR.Cross)op).v1,parameters);
			pg.addLink(((IVecR.Cross)op).v2,parameters);
		    }
		    else if(op instanceof IVec2R.Cross){
			pg.addLink(((IVec2R.Cross)op).v1,parameters);
			pg.addLink(((IVec2R.Cross)op).v2,parameters);
		    }
		    else if(op instanceof IVecR.Rot){
			pg.addLink(((IVecR.Rot)op).v,parameters);
			pg.addLink(((IVecR.Rot)op).axis,parameters);
			pg.addLink(((IVecR.Rot)op).angle,parameters);
		    }
		    else if(op instanceof IVecR.Unit){
			pg.addLink(((IVecR.Unit)op).v,parameters);
		    }
		    else if(op instanceof IVecR.SetLen){
			pg.addLink(((IVecR.SetLen)op).v,parameters);
			pg.addLink(((IVecR.SetLen)op).l,parameters);
		    }
		}
		else if(p instanceof IVec4R){
		    IVec4Op op = ((IVec4R)p).operator();
		    if(op instanceof IVec4R.FromVec){
			pg.addLink(((IVec4R.FromVec)op).v,parameters);
		    }
		    else if(op instanceof IVec4R.FromVecAndW){
			pg.addLink(((IVec4R.FromVecAndW)op).v,parameters);
			pg.addLink(((IVec4R.FromVecAndW)op).w,parameters);
		    }
		    else if(op instanceof IVec4R.FromXYZ){
			pg.addLink(((IVec4R.FromXYZ)op).x,parameters);
			pg.addLink(((IVec4R.FromXYZ)op).y,parameters);
			pg.addLink(((IVec4R.FromXYZ)op).z,parameters);
		    }
		    else if(op instanceof IVec4R.FromXYZW){
			pg.addLink(((IVec4R.FromXYZW)op).x,parameters);
			pg.addLink(((IVec4R.FromXYZW)op).y,parameters);
			pg.addLink(((IVec4R.FromXYZW)op).z,parameters);
			pg.addLink(((IVec4R.FromXYZW)op).w,parameters);
		    }
		    else if(op instanceof IVec4R.Add){
			pg.addLink(((IVec4R.Add)op).v1,parameters);
			pg.addLink(((IVec4R.Add)op).v2,parameters);
		    }
		    else if(op instanceof IVec4R.Sub){
			pg.addLink(((IVec4R.Sub)op).v1,parameters);
			pg.addLink(((IVec4R.Sub)op).v2,parameters);
		    }
		    else if(op instanceof IVec4R.Mul){
			pg.addLink(((IVec4R.Mul)op).v,parameters);
			pg.addLink(((IVec4R.Mul)op).d,parameters);
		    }
		    else if(op instanceof IVec4R.Div){
			pg.addLink(((IVec4R.Div)op).v,parameters);
			pg.addLink(((IVec4R.Div)op).d,parameters);
		    }
		    else if(op instanceof IVec4R.Neg){
			pg.addLink(((IVec4R.Neg)op).v,parameters);
		    }
		    else if(op instanceof IVec4R.Cross){
			pg.addLink(((IVec4R.Cross)op).v1,parameters);
			pg.addLink(((IVec4R.Cross)op).v2,parameters);
		    }
		    else if(op instanceof IVec4R.Rot){
			pg.addLink(((IVec4R.Rot)op).v,parameters);
			pg.addLink(((IVec4R.Rot)op).axis,parameters);
			pg.addLink(((IVec4R.Rot)op).angle,parameters);
		    }
		    else if(op instanceof IVec4R.Unit){
			pg.addLink(((IVec4R.Unit)op).v,parameters);
		    }
		    else if(op instanceof IVec4R.SetLen){
			pg.addLink(((IVec4R.SetLen)op).v,parameters);
			pg.addLink(((IVec4R.SetLen)op).l,parameters);
		    }
		}
		*/
	    }
	}
	
	public static String toString(IBool b){
	    if(b.x) return "true";
	    return "false";
	}
	public static String toString(IInteger i){
	    return String.valueOf(i.x);
	}
	public static String toString(double x){
	    NumberFormat f = NumberFormat.getInstance();
	    if(f instanceof DecimalFormat){
		if(Double.isNaN(x)){
		    return "NaN";
		}
		else if(Math.abs(x)==0){
		    return "0";
		}
		else if(Math.abs(x)<0.1){
		    ((DecimalFormat)f).applyPattern("0.###E0");
		}
		else if(Math.abs(x)<1){
		    ((DecimalFormat)f).setMaximumFractionDigits(3);
		}
		else if(Math.abs(x)<10){
		    ((DecimalFormat)f).setMaximumFractionDigits(3);
		}
		else if(Math.abs(x)<100){
		    ((DecimalFormat)f).setMaximumFractionDigits(2);
		}
		else if(Math.abs(x)<1000){
		    ((DecimalFormat)f).setMaximumFractionDigits(1);
		}
		else if(Math.abs(x)<10000){
		    ((DecimalFormat)f).applyPattern("0.###E0");
		}
		return f.format(x);
	    }
	    return String.valueOf(x);
	}
	
	public static String toString(IDouble v){
	    return toString(v.x);
	}
	public static String toString(IVec2 v){
	    return "("+toString(v.x)+","+toString(v.y)+")";
	}
	public static String toString(IVec v){
	    return "("+toString(v.x)+","+toString(v.y)+","+toString(v.z)+")";
	}
	public static String toString(IVec4 v){
	    return "("+toString(v.x)+","+toString(v.y)+","+toString(v.z)+","+toString(v.w)+")";
	}
	
    }
    
}

