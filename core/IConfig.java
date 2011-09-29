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

package igeo.core;

/**
   An interface to contain static constants used in the whole iGeo system.
   
   @author Satoru Sugihara
   @version 0.7.0.0
*/
public /*interface*/ class IConfig{
    
    /**
       if IObject should keep multiple IGraphicObject when it swithes to
       different graphic mode
    */
    //static final boolean keepMultipleGraphicsInObject=false;
    
    
    // now knots are always normalized. this option is discarded.
    //static final boolean normalizeKnots = true;
    
    /*****************************
     * resolution settings
     *****************************/
        
    /**
       A parameter to check identical location. Length below this is ignored and seen as zero. Length is measured in whatever unit of the file
    */
    public static double lengthResolution=0.001; //0.1; //0.01; //0.00001; //0.001; // in any unit
    /**
       A parameter to check identical location in U-V parametric space in NURBS geometry. Length below this is ignored and seen as zero. Range of U-V parameter space should be always 0.0-1.0.
    */
    public static double parameterResolution=0.001;
    
    /**
       A parameter to check identical direction in angle. Angle difference below this is ignored and seen as zero. The unit is radian.
    */
    public static double angleResolution = Math.PI/1000; //Math.PI/30; //Math.PI/10000; //Math.PI/1000; // in radian
    
    
    /*****************************
     * graphic properties
     *****************************/
    
    /**
       Point resolution per edit points to draw curves in graphic classes.
    */
    public static int curveGraphicResolution=10; //1; //4; //3*2*2; //4; //8; //30;
    
    /**
       Number of isoperms to draw surfaces in graphic classes.
    */
    public static int surfaceIsoparmResolution=4; //2;//10; //9; //2 ; //3; //4; //2; //10; // isoparm num
    
    /**
       Point resolution per the isoparm segment to draw wireframe curves of surfaces.
    */
    public static int surfaceWireframeResolution=8; //2;
    /**
       Point resolution per the isoparm segment to draw trim curves of surfaces.
    */
    public static int surfaceTrimEdgeResolution=4; //2;
    
    /*****************************
     * dynamics properties
     *****************************/
    
    /** update speed of dynamics thread in millisecond */
    public static int dynamicsUpdateSpeed = 30; 
    
    /*****************************
     * mouse properties in INavigator
     *****************************/
    
    /**
       Speed of rotation in mouse 3D navigation in INavigator class.
       The unit is degree of the angle per pixel of the mouse move.
    */
    public static double mouseRotationSpeed=0.3;
    
    /**
       Speed of panning in mouse 3D navigation in perspective view in INavigator class.
       The unit is unit of length in 3D space per pixel of the mouse move.
    */
    public static double mousePerspectivePanSpeed = 0.2; //1; //0.25;
    
    /**
       Speed of panning in mouse 3D navigation in axonometric view in INavigator class.
       The unit is ratio of corresponding length per pixel in axonometric view.
    */
    public static double mouseAxonometricPanSpeed = 1; //0.25;
    
    /**
       Speed of zooming in mouse 3D navigation in perspective view in INavigator class.
       The unit is distance of front/back view move per pixel of the mouse move.
    */
    public static double mousePerspectiveZoomSpeed = 1.; //1.25;
        
    /**
       Speed of zooming in mouse 3D navigation in axonometric view in INavigator class.
       The unit is increment/decrement of percentage per pixel of the mouse move.
    */
    public static double mouseAxonometricZoomSpeed = 0.75; //0.25; //0.125;
    
    /**
       Speed of zooming in mouse 3D navigation in both axonometric and perspective view in INavigator class.
       The unit is equivalent pixel move of mouse per a increment/decrement of the wheel.
    */
    public static double mouseWheelZoomSpeed = 40;
    
    
    /*****************************
     * keyboard properties in INavigator
     *****************************/
    
    /**
       Speed of rotation in keyboard (arrow keys in default) 3D navigation in INavigator class.
       The unit is degree of the angle per one key press.
    */
    public static double keyRotationSpeed=3.;
    
    /**
       Speed of panning in keyboard (arrow keys in default) 3D navigation in perspective view in INavigator class.
       The unit is unit of length in 3D space per one key press.
    */
    public static double keyPerspectivePanSpeed = 0.5; //0.2; //1; //0.25;
    
    /**
       Speed of panning in keyboard (arrow keys in default) 3D navigation in axonometric view in INavigator class.
       The unit is ratio of corresponding length per one key press in axonometric view.
    */
    public static double keyAxonometricPanSpeed = 10; //1; //0.25;
    
    /**
       Speed of zooming in keyboard (arrow keys in default) 3D navigation in perspective view in INavigator class.
       The unit is distance of front/back view move per one key press of the mouse move.
    */
    public static double keyPerspectiveZoomSpeed = 1.; //1.25;
        
    /**
       Speed of zooming in keyboard (arrow keys in default) 3D navigation in axonometric view in INavigator class.
       The unit is equivalent pixel move of mouse per one key press.
    */
    public static double keyZoomSpeed = 5;
    
    /**
       Minimum size to create bounding box and to zoom into.
    */
    public static double minimumBoundingBox = 0.002;

    
    /*************************************************************************************
     * NURBS geometry construction
     ************************************************************************************/
    
    /** when NURBS geometry is created at a constructor, if control points on edges share a same instance, another instance is created with dup().  This can be overridden by checkDuplicatedControlPoint. Duplicate is avoided to apply tranfromation to all the control points equally. */
    public static boolean checkDuplicatedControlPointOnEdge=true;

    /** when NURBS geometry is created at a constructor, if any control points share a same instance, another instance is created with dup(). Duplicate is avoided to apply tranfromation to all the control points equally. */
    public static boolean checkDuplicatedControlPoint=true;
    
}
    
