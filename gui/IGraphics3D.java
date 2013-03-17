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

/**
   Class of Graphics to draw 3D geometry (OpenGL or P3D)
   @author Satoru Sugihara
*/
public interface IGraphics3D extends IGraphics{
    
    //public Color ambientColor = IConfig.ambientColor;
    //public Color specularColor = IConfig.specularColor;
    //public Color emissiveColor = IConfig.emissiveColor;
    //public double shininess = IConfig.shininess;
    
    // line stipple? , line style?
    
    /** diffuse color is same with color(Color c) */
    public abstract void diffuse(float r, float g, float b, float a); //{ diffuse(new Color(r,g,b,a)); }
    public abstract void diffuse(float r, float g, float b); //{ diffuse(r,g,b,0); }
    public abstract void diffuse(float[] rgba);
    
    public abstract void diffuse(IColor c); //{ super.clr(c); }
    
    
    public abstract void ambient(float r, float g, float b, float a); //{ ambient(new Color(r,g,b,a)); }
    public abstract void ambient(float r, float g, float b); //{ ambient(r,g,b,0); }
    public abstract void ambient(float[] rgba);
    public abstract void ambient(IColor c); //{ ambientColor = c; }
    public abstract void specular(float r, float g, float b, float a); //{ specular(new Color(r,g,b,a)); }
    public abstract void specular(float r, float g, float b); //{ specular(r,g,b,0); }
    public abstract void specular(float[] rgba);
    public abstract void specular(IColor c); //{ specularColor = c; }
    public abstract void emissive(float r, float g, float b, float a); //{ emissive(new Color(r,g,b,a)); }
    public abstract void emissive(float r, float g, float b); //{ emissive(r,g,b,0); }
    public abstract void emissive(float[] rgba);
    public abstract void emissive(IColor c); //{ emissiveColor = c; }
    
    public abstract void shininess(float s); //{ shininess = s; }
    
    public abstract void enableLight();
    public abstract void disableLight();
    
    public abstract void pointSize(float size);
    
    public abstract void drawPoint(IVec p);
    public abstract void drawPoints(IVec[] p);
    public abstract void drawLines(IVec[] p);
    public abstract void drawLineStrip(IVec[] p);
    public abstract void drawLineLoop(IVec[] p);
    public abstract void drawPolygon(IVec[] pts);
    public abstract void drawPolygon(IVec[] pts, IVec[] nml);
    public abstract void drawQuads(IVec[] pts);
    public abstract void drawQuads(IVec[] pts, IVec[] nml);
    public abstract void drawQuadStrip(IVec[] pts);
    public abstract void drawQuadStrip(IVec[] pts, IVec[] nml);
    public abstract void drawQuadMatrix(IVec[][] pts);
    public abstract void drawQuadMatrix(IVec[][] pts, IVec[][] nml);
    public abstract void drawTriangles(IVec[] pts);
    public abstract void drawTriangles(IVec[] pts, IVec[] nml);
    public abstract void drawTriangleStrip(IVec[] pts);
    public abstract void drawTriangleStrip(IVec[] pts, IVec[] nml);
    public abstract void drawTriangleFan(IVec[] pts);
    public abstract void drawTriangleFan(IVec[] pts, IVec[] nml);
    
}
