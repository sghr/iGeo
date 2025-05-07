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

//import javax.media.opengl.*; // Processing 1 & 2
import com.jogamp.opengl.GL; // Processing 3
import java.awt.image.*;
import igeo.*;

/**
    Texture graphic interface
 */
public interface ITextureGraphicGL{
    public void init(String filename, GL gl);
    public void init(BufferedImage image, GL gl);
    public int id();
    public int width();
    public int height();
    public void destroy(GL gl);
    public void destroy();
}
