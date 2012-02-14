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

package igeo.p;

/**
   Configuration setting class for the processing interface piGeon. 
   
   @author Satoru Sugihara
   @version 0.7.1.0;
*/
public class PIConfig{
    /**
       This boolean flag specifies the order of drawing of iGeo and Processing.
       If it's true iGeo is drawn before Processing's draw() method.
       Otherwise, drawn after Processing's draw() method.
       Default is true.
    */
    public static boolean drawBeforeProcessing=true;
    
    /**
       A boolean flag to switch for PIGraphicsGL to clear OpenGL's GL_DEPTH_BUFFER_BIT
       before drawing iGeo's geometries. Default is true.
    */
    public static boolean resetGLDepthBefore=true;
    
    /**
       A boolean flag to switch for PIGraphicsGL to clear OpenGL's GL_DEPTH_BUFFER_BIT
       after drawing iGeo's geometries. Default is true.
    */
    public static boolean resetGLDepthAfter=true;
    
}
