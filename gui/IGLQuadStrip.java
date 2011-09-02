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

package igeo.gui;
//package igeo.gl;

import javax.media.opengl.*;

/**
   Objectified OpenGL quad strip drawing process.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IGLQuadStrip extends IGLElement{
    public IGLQuadStrip(){}
    public IGLQuadStrip(int size){ super(size); }
    
    public void draw(GL gl){
	gl.glBegin(GL.GL_QUAD_STRIP);
	drawPoints(gl);
	gl.glEnd();
    }
}
