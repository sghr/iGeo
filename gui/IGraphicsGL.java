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


import igeo.*;

//import javax.media.opengl.*; // Processing 1 & 2
import com.jogamp.opengl.*; // Processing 3

import java.awt.image.*;

/**
   Interface of Graphics to draw 3D geometry with OpenGL
   @author Satoru Sugihara
*/
public interface IGraphicsGL extends IGraphics3D{
    public GL getGL();
    public void setGL(GL g);
    public void setBGImage(String imageFilename);

    public ITextureGraphicGL getTextureGraphic(String filename);
    public ITextureGraphicGL getTextureGraphic(BufferedImage image);
}
