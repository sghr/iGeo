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

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import igeo.*;

import javax.media.opengl.*;
import com.sun.opengl.util.j2d.TextRenderer;

/**
   Base class of OpenGL graphic vertex data collection
   
   @author Satoru Sugihara
*/
public class ITextGraphicGL extends IGraphicObject{
    public static final int defaultFontResolution = 80; //200;
    public static final Font defaultFont = new Font("SansSerif", Font.PLAIN, defaultFontResolution);
    
    public TextRenderer renderer;
    public IText text;
    
    public String[] lines;
    public float textWidth;
    public float textHeight;
    
    public Font font;
    
    
    public ITextGraphicGL(IText txt){
	super(txt);
	text = txt;
	font = text.font;
	if(font==null){ font=defaultFont; }
	//initText(); // initialized when first drawn
    }
    
    public boolean isDrawable(IGraphicMode m){
        return m.isGL(); // currently GL only
        //return m.isGraphic3D();
    }
    
    public void initText(){
	lines = lines();
	textWidth = (float)textWidth(lines);
	textHeight = (float)textHeight(lines);
    }
    
    public String[] lines(){ return text.text().split("\n"); }
    
    public double textWidth(String[] lines){
	if(renderer==null) renderer = new TextRenderer(font, true, true);
	double maxWidth=0;
	for(int i=0; i<lines.length; i++){
	    Rectangle2D bounds = renderer.getBounds(lines[i]);
	    if(bounds.getWidth()>maxWidth){ maxWidth=bounds.getWidth(); }
	}
	return maxWidth/renderer.getFont().getSize();
    }
    
    public double textHeight(String[] lines){ return lines.length; }
    
    synchronized public void draw(IGraphics g){
	if(text.text()==null) return;
	
	if(g.type()==IGraphicMode.GraphicType.GL){
	    IGraphicsGL ggl = (IGraphicsGL)g;
	    if(ggl.firstDraw() || renderer==null){ update(); } // when resized, it's firstDraw too.
	    
	    float halign=0, valign=0;
	    if(text.isAlignLeft()){ halign=0; }
	    else if(text.isAlignCenter()){ halign = textWidth/2; }
	    else if(text.isAlignRight()){ halign = textWidth; }
	    if(text.isAlignBottom()){ valign = -textHeight+1; }
	    else if(text.isAlignMiddle()){ valign = -textHeight/2+1; }
	    else if(text.isAlignTop()){ valign = 1f; }
	    
	    IMatrix4 mat = IMatrix4.getTransform(text.uvec(),
						 text.vvec(),
						 text.uvec().cross(text.vvec()),
						 text.pos());
	    ggl.getGL().glPushMatrix();
	    ggl.getGL().glMultMatrixd(mat.toArray(),0);
	    renderer.begin3DRendering();
	    renderer.setColor((float)text.red(),(float)text.green(),(float)text.blue(),(float)text.alpha());
	    for(int i=0; i<lines.length; i++){
		renderer.draw3D(lines[i], -halign, -valign-i, 0, 1f/renderer.getFont().getSize());
		//renderer.flush(); // necessary?
	    }
	    renderer.end3DRendering();
	    ggl.getGL().glPopMatrix();
	}
    }
    
    public IVec corner(int i, int j){
	//if(renderer==null) initText(); ?
	
	IVec corner = text.pos().cp();
	if(i==0){
	    if(text.isAlignCenter()){ corner.add(text.uvec(),-textWidth/2); }
	    else if(text.isAlignRight()){ corner.add(text.uvec(),-textWidth); }
	}
	else{
	    if(text.isAlignLeft()){ corner.add(text.uvec(),textWidth); }
	    else if(text.isAlignCenter()){ corner.add(text.uvec(),textWidth/2); }
	}
	
	if(j==0){
	    if(text.isAlignMiddle()){ corner.add(text.vvec(),-textHeight/2); }
	    else if(text.isAlignBottom()){ corner.add(text.vvec(),-textHeight); }
	}
	else{
	    if(text.isAlignTop()){ corner.add(text.vvec(),textHeight); }
	    else if(text.isAlignMiddle()){ corner.add(text.vvec(),textHeight/2); }
	}
	return corner;
    }
    
    public void update(){
	if(text.font==null && font!=defaultFont){ font = defaultFont; }
	else if(text.font!=null && font!=text.font){ font = text.font; }
	renderer = new TextRenderer(font, true, true);
	initText();
	super.update();
    }
    
}
