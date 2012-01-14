/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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

import javax.media.opengl.*;
import java.awt.*;

import igeo.IG;

/**
   Class of Graphics like java.awt.Graphics to wrap all possible graphic mode
   (Currently Java AWT and OpenGL).
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IGraphics{
    
    public GL gl;
    public Graphics2D g;
    public IView view;
    
    //IGraphicMode mode=defaultMode;
    
    public void IGraphics(){}
    
    public void setGL(GL gl){ this.gl=gl; }
    public void setGraphics(Graphics2D g){ this.g=g; }
    
    public void setView(IView v){ view = v; }
    
    //public boolean isTypeGL(){ return type==Type.GL_WIREFRAME || type==Type.GL_FILL; }
    //public boolean isTypeJava(){ return type==Type.JAVA_WIREFRAME || type==Type.JAVA_FILL; }
    //public void setType(Type t){ type=t; }
    //public Type getType(){ return type; }
    
    //public IGraphicMode mode(){ return mode; }
    
    
    public GL getGL(){ return gl; }
    public Graphics2D getGraphics(){ return g; }
    
    public IView view(){ return view; }
        
}
