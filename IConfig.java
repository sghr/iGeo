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

package igeo;


//import java.awt.Color;

/**
   An interface to contain static constants used in the whole iGeo system.
   
   @author Satoru Sugihara
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
    public static double tolerance=0.001; //0.1; //0.01; //0.00001; //0.001; // in any unit
    //public static double lengthResolution=0.001; //0.1; //0.01; //0.00001; //0.001; // in any unit
    /**
       A parameter to check identical location in U-V parametric space in NURBS geometry. Length below this is ignored and seen as zero. Range of U-V parameter space should be always 0.0-1.0.
    */
    public static double parameterTolerance=0.001;
    //public static double parameterResolution=0.001;
    
    /**
       A parameter to check identical direction in angle. Angle difference below this is ignored and seen as zero. The unit is radian.
    */
    public static double angleTolerance = Math.PI/1000; //Math.PI/30; //Math.PI/10000; //Math.PI/1000; // in radian
    //public static double angleResolution = Math.PI/1000; //Math.PI/30; //Math.PI/10000; //Math.PI/1000; // in radian
    
    
    /*****************************
     * graphic properties
     *****************************/
    
    /** Point resolution per edit points to draw curves in graphic classes. */
    public static int segmentResolution = 10;
    //public static int curveGraphicResolution=10; //1; //4; //3*2*2; //4; //8; //30; // variable name chbanged to segmentResolution
    
    /** Number of isoperms to draw surfaces in graphic classes. if it's zero, no isoparm */
    public static int isoparmResolution=1; // default is changed to zero. 4;
    //public static int surfaceIsoparmResolution=4; //2;//10; //9; //2 ; //3; //4; //2; //10; // variable name changed to isoparmResolution
    
    /** Number of division per edit point to tesselate a surface into mesh. */
    public static int tessellationResolution = 5; //4;
    //public static int surfaceTessellationResolution=4; //2;//10; //9; //2 ; //3; //4; //2; //10; // variable name changed to tessellationResolution
    
    /** Point resolution per the isoparm segment to draw wireframe curves of surfaces. */
    //public static int surfaceWireframeResolution=8; //2; // now both wireframe and curve resolution are defined by segmentResolution
    // public static int wireSegmentResolution = 8;
    
    /** Point resolution per the isoparm segment to draw trim curves of surfaces. */
    public static int trimSegmentResolution=4;
    //public static int surfaceTrimEdgeResolution=4; //2; // variable name changed to trimSegmentResolution
    
    /**
       This is only for saving brep or trimmed surface to Rhino file where
       the 2D trim curve is also required to have 3D representation;
       3D curve is approximated with sampled points in this resolution. 
    */
    public static int trimApproximationResolution = 20;
    //public static int trim3dCurveInterpolationResolution = 20; // variable name changed to trimApproximationResolution
    
    
    /** default object color to draw */
    //public static Color objectColor = new Color(102,102,102);
    public static IColor objectColor = new IColor(102,102,102);
    
    /** default stroke color to draw */
    //public static Color strokeColor = new Color(102,102,102);
    public static IColor strokeColor = new IColor(102,102,102);
    
    /** default stroke weight to draw */
    public static float strokeWeight = 1.0f;
    
    /** default ambient color */
    public static IColor ambientColor = null; //new Color(0,0,0);
    
    /** default emissive color */
    public static IColor emissiveColor = null;
    
    /** default specular color */
    public static IColor specularColor = new IColor(255,255,255);
    
    /** default shininess */
    public static float shininess = 0.5f;
    
    
    /** transparency in transparent graphic mode in integer 0 - 255 (it used to be float 0.0-1.0. it changed) */
    public static int transparentModeAlpha = 102; //0.4f;
    
    
    public static IColor bgColor1 = new IColor(255,255,255);
    public static IColor bgColor2 = new IColor(230,230,230);
    public static IColor bgColor3 = new IColor(77,128,179);
    public static IColor bgColor4 = new IColor(77,128,179);
    
    
    /** default point graphic dot size */
    public static float pointSize=5f;
    
    /** default vector graphic arrow size */
    public static float arrowSize=2f;

    /** default vector graphic arrow width ratio */
    public static float arrowWidthRatio=0.5f;

    
    /** turn on lights on display */
    public static boolean useLight=true;

    /** change wireframe colors by lights when using lights */
    public static boolean lightWireframe=false;

    /** make wireframe transparent in transparent display mode */
    public static boolean transparentWireframe=false;

    
    /** check and remove vertices behind view location in P3D graphics */
    public static boolean cullVertexBehindViewInP3D=true;
    /** check and remove vertices behind view location in OpenGL graphics */
    public static boolean cullVertexBehindViewInGL=false; 
    
    
    /** enable depth test in GL graphics / depth sort in P3D graphics. if there are many faces, this makes it very slow to deisplay */
    public static boolean depthSort=true;
    
    
    /** if number of objects exceeds this, it automatically turn off depth sort in P3D. if number is negative, number is not checked. */
    public static int maxObjectNumberForDepthSort = 3000; //
    
    /** turn on smooth option in P3D. This makes it slow in perspective when many vertices are out of display */
    public static boolean smoothGraphicP3D=true; //false; //true;

    
    /** set order of drawing in forward (from old to new) or backwards. default is false(backwards) */
    public static boolean drawOrderForward=false;
    
    
    /** all graphic objects in IGraphicServer are deleted once drawn in a panel
	(objects in IServer and dynamics in IDynamicServer are preserved)
	usually used with clearBackground=false */
    public static boolean deleteGraphicObjectsAfterDraw=false;
    
    /** in every drawing cycle, background is cleared with specified background color or gradient */
    public static boolean clearBG=true;
    
    
    /*****************************
     * polygon mesh properties
     *****************************/
    
    /** number to facet circle to approximate it in polygon mesh. */
    public static int meshCircleResolution = 24; 
    
    
    /** update normal vector of mesh when its graphics is updated by agents. */
    public static boolean updateMeshNormal=false;

    /** remove duplicated vertices and edges under the tolerance = IConfig.tolerance automatically when a mesh is created in new IMesh(IFace[] faces) */
    public static boolean removeDuplicatesAtMeshCreation=true; 
    
    
    /*****************************
     * curve / surface cache properties 
     *****************************/
    /** resolution per EP for cache points of curve for search of closest point on curve */
    public static int curveCacheResolution=10; //4; //1;
    /** resolution per EP for cache points of surface for search of closest point on surface */
    public static int surfaceCacheResolution=4; //1;
    /** max depth of recursive search of closest point */
    public static int cacheRecursionMaxDepth = 15; //12; //10;
    
    /*****************************
     * field properties
     *****************************/
    //public static double defaultFieldThreshold = 100;
    public static double defaultFieldIntensity = 10;
    public static boolean defaultConstantFieldIntensity = true;
    
    
    /*****************************
     * dynamics properties
     *****************************/
    
    /** update speed of dynamics thread in millisecond */
    //public static int dynamicsUpdateSpeed = 30; 
    
    /** update speed of dynamics thread in seccond */
    //public static double dynamicsSpeed = 30.0/1000;
    public static double updateRate = 30.0/1000;

    /******** If true, IDynamcServer thread automatically starts when the first IDynamicObject is
	added to the server. Codes to create instances of IDynamicObject should be enclosed
	by synchronized(IG.updateThread()) not to have the thread started before finishing
	execution of all construcors.
    */
    //public static boolean autoStart = false;
    
    
    /** enable preinteract method in IDynamicServer. Default is true. If false, some functionalities of IDynamics inheriting class like IAgent are disabled. */
    public static boolean enablePreinteract=true;
    /** enable postinteract method in IDynamicServer. Default is true. If false, some functionalities of IDynamics inheriting class like IAgent are disabled. */
    public static boolean enablePostinteract=true;
    /** enable preupdate method in IDynamicServer. Default is true. If false, some functionalities of IDynamics inheriting class like IAgent are disabled. */
    public static boolean enablePreupdate=true;
    /** enable postupdate method in IDynamicServer. Default is true. If false, some functionalities of IDynamics inheriting class like IAgent are disabled. */
    public static boolean enablePostupdate=true;
    
    /** put preinteract method in another independent for-loop in IDynamicServer. Execution order will change but the execution speed might be slower. Default is true. */
    public static boolean loopPreinteract=true; //false;
    /** put postinteract method in another independent for-loop in IDynamicServer. Execution order will change but the execution speed might be slower. To have accurate physical simulation, this need to be true. Default is true. */
    public static boolean loopPostinteract=true;
    /** put preupdate method in another independent for-loop in IDynamicServer. Execution order will change but the execution speed might be slower. To have accurate physical simulation, this need to be true.  Default is true. */
    public static boolean loopPreupdate=true;
    /** put postupdate method in another independent for-loop in IDynamicServer. Execution order will change but the execution speed might be slower. Default is false. */
    public static boolean loopPostupdate=false;
    
    /** synchronize draw loop and dynamics updating loop instead of using another thread. default is false to use multi-thread. */
    public static boolean syncDrawAndDynamics=false;
    
    /*****************************
     * properties of IParticleGeo
     *****************************/
    /** frc.zero called in IParticleGeo if this is true and also IConfig.enablePostupdate is true */
    public static boolean clearParticleForceInPostupdate=false; 
    
    
    /*****************************
     * properties of IWall
     *****************************/
    /** IWall put a particle to bouncing location forcefully once. usually if it's fast the particle can be already away from the wall without having a moment being exactly on the wall. If the following boolean option is true, IWall force particle to once stay at the intersecting bouncing point but this is less acurate in terms of physical simulation in longer duration. */
    /** foceStayOnWallOnce option is removed. Insteady IWall inserts intersection point into trajectory curve.
	see IConfig.insertBouncePointInTrajectory */
    //public static boolean forceStayOnWallOnce = false; //true;
    
    /** Boolean option to turn on/off inserting an interesection point on IWall when bouncing into
	a trajectory curve of ITrajectoryI. Default is true */
    public static boolean insertBouncePointInTrajectory = true;
    
    /** When this option is true, IWall checks all other exsiting walls to see if the particle is also colliging into other walls. If so, only the closest one collides. True on this option makes the process heavy. Default is true.*/
    public static boolean checkAdjacentWalls = true;
    
    
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
       -> now zoom speed is controlled by mousePerspectivePanResolution.
    */
    //public static double mousePerspectivePanSpeed = 0.2; //1; //0.25;
    
    /**
       Amount of panning in perspective view by one pixel move of mouse is equal to bounding box size of existing geometry devided by this resolution. 
       Bounding box is updated when view is focused to existing geometry. Size of boundary box is average of width, height and depth.
    */
    public static double mousePerspectivePanResolution = 200;
    
    /**
       Speed of panning in mouse 3D navigation in axonometric view in INavigator class.
       The unit is ratio of corresponding length per pixel in axonometric view.
    */
    public static double mouseAxonometricPanSpeed = 1; //0.25;
    
    /**
       Speed of zooming in mouse 3D navigation in perspective view in INavigator class.
       The unit is distance of front/back view move per pixel of the mouse move.
       -> now zoom speed is controlled by mousePerspectiveZoomResolution.
    */
    //public static double mousePerspectiveZoomSpeed = 1.; //1.25;
    
    
    /**
       Amount of zoom in perspective view by one pixel move of mouse is equal to bounding box size of existing geometry devided by this resolution. 
       Bounding box is updated when view is focused to existing geometry. Size of boundary box is average of width, height and depth.
    */
    public static double mousePerspectiveZoomResolution = 200;
    
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
    
    
    /**
       enable focus after geometries created in setup method
    */
    public static boolean autoFocusAtStart = true;
    
    
    
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
    //public static double minimumBounds = 0.001; //0.002; -> use IConfig.tolerance
    
    
    /**
       default parameters for IView class
    */
    /** default near clipping distance -> replaced by nearViewRatio */
    //public static double nearView = 0.1; //0.001;
    /** default far clipping distance replaced by farViewRatio */
    //public static double farView = 100000;
    
    /** default near clipping distance. 
	ratio to existing geometry bounding box */
    public static double nearViewRatio = 0.001;
    
    /** default far clipping distance.
	ratio to existing geometry bounding box */
    public static double farViewRatio = 1000;
    
    /** view distance ration. ratio to bounding box size */
    public static double viewDistanceRatio = 10;
    
    
    /** default view distance */
    public static double viewDistance = 500; //10000; // 100;
    
    /** default axonometric ratio */
    public static double axonometricRatio = 1.0;
    /** default perspective ratio */
    public static double perspectiveRatio = 0.5;
    
    
    
    /*************************************************************************************
     * NURBS geometry construction
     ************************************************************************************/
    
    /** when NURBS geometry is created at a constructor, if control points on edges share a same instance, another instance is created with dup().  This can be overridden by checkDuplicatedControlPoint. Duplicate is avoided to apply tranfromation to all the control points equally. */
    public static boolean checkDuplicatedControlPointOnEdge=true;

    /** when NURBS geometry is created at a constructor, if any control points share a same instance, another instance is created with dup(). Duplicate is avoided to apply tranfromation to all the control points equally. */
    public static boolean checkDuplicatedControlPoint=true;
    
    /** when NURBS geometry is created at a constructor, checking validity (not infinite nor NaN) of numbers if this is true. */
    public static boolean checkValidControlPoint=true;


    /*************************************************************************************
     * AI Export
     ************************************************************************************/
    public static double defaultAIExportScale = 0.01;

    
    /*************************************************************************************
     * Text Object Property
     ************************************************************************************/
    //public static int defaultFontResolution=200;
}

