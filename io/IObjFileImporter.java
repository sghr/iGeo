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

package igeo.io;

import java.io.*;
import java.awt.*;
import java.util.*;

import igeo.*;
import igeo.gui.*;

/**
   Wavefront OBJ file importer class.
   
   @author Satoru Sugihara
*/
public class IObjFileImporter {
    
    //static final int meshTypeGeneral=0;
    //static final int meshTypeTriMesh=1;
    //static final int meshTypeQuadMesh=2;
    //static final int meshTypeMeshField=3;
    //static int meshType=meshTypeGeneral; //meshTypeMeshField; //meshTypeTriMesh;
    
    
    public static final boolean ignoreLayers=false; //true; //false; //true; //false; 
    
    public static class Entity{ // pool to keep data
	public ArrayList<EntityData> data;
	public ArrayList<Entity> entities; // all other entities
	public ArrayList<IObject> objects;
	
	public IServerI server;
	
	public Entity(){ data = new ArrayList<EntityData>(); }
	
	public String getLabel(){ return null; }
	
	public EntityData getData(int i){
	    // input reference number starts from 1
	    // sometimes negative for relative number
	    // but index number for ArrayList is from 0
	    
	    //if(i<0){ i = data.size() + i + 1; }
	    if(i>0){ i--; } else if(i<0){ i = data.size() + i; }
	    
	    return data.get(i);
	}
	
	public void read(String line){ add(parse(line)); }
	public EntityData parse(String line){ return parse(line.split(" +")); }
	public EntityData parse(String[] segments){ return null; }
	public void add(EntityData d){ data.add(d); }
	
	public EntityData getLatestData(){ return data.get(data.size()-1); }
	
	public void setAllEntities(ArrayList<Entity> e){ entities=e; }
	public void setObjectContainer(ArrayList<IObject> objs){ objects=objs; }
	
	public void setServer(IServerI s){ server=s; }
    }
    
    
    public static class EntityData{}
    
    public static class ParametricGeometryData extends EntityData{
	public GroupName.Data group=null;
	public void setDegree(Degree.Data d){}
	public void setKnots(Parameter.Data d){}
	public void instantiate(){}
	public void setGroup(GroupName.Data g){ group=g; }
	public void addToGroup(IObject elem){ if(group!=null) group.add(elem); }
    }
    
    
    public static class Comment extends Entity{ public String getLabel(){ return("#"); } }
    
    
    // //////////////////////
    // vertex data
    // //////////////////////
    public static class GeometricVertex extends Entity{
	public String getLabel(){ return("v"); }
	public EntityData parse(String[] args){
	    if( args.length != (3+1) && args.length != (4+1) ){
		String errMsg = "";
		for(int i=0; i<args.length; i++) errMsg+=args[i]+" ";
		IOut.err("wrong number of arguments ("+args.length+" )"+errMsg); 
		return null;
	    }
	    Data d = new Data();
	    try{
		d.x = Double.parseDouble(args[1]);
		d.y = Double.parseDouble(args[2]);
		d.z = Double.parseDouble(args[3]);
		if(args.length==5) d.w = Double.parseDouble(args[4]);
		else d.w = 1.0;
	    }catch(Exception e){ e.printStackTrace(); }
	    return d;
	}
	public class Data extends EntityData{
	    public double x, y, z, w=1;
	    //IGSpatialElement vertex=null;
	    //IObject vertex=null;
	    public IVertex vertex=null;
	    //public IObject getVertex(){
	    public IVertex getVertex(){
		if(vertex==null){
		    vertex = new IVertex(x,y,z); 
		    
		    //if(meshType==meshTypeTriMesh){ vertex = new IGTriMesh.Vertex(x,y,z); }
		    //else if(meshType==meshTypeQuadMesh){ vertex = new IGQuadMesh.Vertex(x,y,z); }
		    //else if(meshType==meshTypeMeshField){ vertex = new MeshField.Vertex(x,y,z); }
		    //else{ vertex = new IGVertex(x,y,z); }
		}
		return vertex;
	    }
	}
    }
    
    public static class TextureVertex extends Entity{
	public String getLabel(){ return("vt"); }
	public EntityData parse(String[] args){
	    if( args.length != (2+1) && args.length != (3+1) ){
		String errMsg="";
		for(int i=0; i<args.length; i++) errMsg+=args[i]+" ";
		IOut.err("wrong number of arguments; args.length="+args.length+": "+errMsg);
		return null;
	    }
	    Data d = new Data();
	    try{
		d.u = Double.parseDouble(args[1]);
		d.v = Double.parseDouble(args[2]);
		if(args.length==4) d.w = Double.parseDouble(args[3]);
		else d.w = 1.0;
	    }catch(Exception e){ e.printStackTrace(); }
	    return d;
	}
	public class Data extends EntityData{
	    public double u, v, w=1;
	    public IVec getVertex(){ return new IVec(u,v,w); }
	}
    }
    
    public static class VertexNormal extends Entity{
	public String getLabel(){ return("vn"); }
	public EntityData parse(String[] args){
	    if( args.length != (3+1) ){
		String errMsg = "";
		for(int i=0; i<args.length; i++) errMsg += args[i]+" ";
		IOut.err("wrong number of arguments; args.length="+args.length+": "+errMsg);
		return null;
	    }
	    Data d = new Data();
	    try{
		d.x = Double.parseDouble(args[1]);
		d.y = Double.parseDouble(args[2]);
		d.z = Double.parseDouble(args[3]);
	    }catch(Exception e){ e.printStackTrace(); }
	    return d;
	}
	public class Data extends EntityData{
	    public double x, y, z;
	    public IVec getVertex(){ return new IVec(x,y,z); }
	}
    }
    
    public static class ParameterSpaceVertex extends Entity{
	public String getLabel(){ return("vp"); }
	public EntityData parse(String[] args){
	    if( args.length != (2+1) && args.length != (3+1) ){
		String errMsg="";
		for(int i=0; i<args.length; i++) errMsg += args[i]+" ";
		IOut.err("wrong number of arguments; args.length="+args.length+": "+errMsg);
		return null;
	    }
	    Data d = new Data();
	    try{
		d.u = Double.parseDouble(args[1]);
		d.v = Double.parseDouble(args[2]);
		if(args.length==4) d.w = Double.parseDouble(args[3]);
		else d.w = 1.0;
	    }catch(Exception e){ e.printStackTrace(); }
	    return d;
	}
	public class Data extends EntityData{
	    public double u, v, w=1;
	}
    }
    
    public static class CurveType extends Entity{ // used also for curve2D and surface
	public String getLabel(){ return("cstype"); }
	public EntityData parse(String[] args){
	    if( args.length != (1+1) && args.length != (2+1) ){
		String errMsg = "";
		for(int i=0; i<args.length; i++) errMsg += args[i]+" ";
		IOut.err("wrong number of arguments; args.length="+args.length+": "+errMsg);
		return null;
	    }
	    Data d = new Data();
	    if(args.length==3){
		if(args[1].equals("rat")){ d.rational=true; }
		d.type = args[2];
	    }
	    else d.type = args[1];
	    return d;
	}
	public class Data extends ParametricGeometryData{
	    public String type; // bmatrix, bezier, bspline, cardinal or taylor
	    public boolean rational=false;
	    
	    public void setDegree(Degree.Data d){}
	    public void setKnots(Parameter.Data d){}
	    public void instantiate(){}
	    
	}
    }
    
    public static class Degree extends Entity{
	public String getLabel(){ return("deg"); }
	public EntityData parse(String[] args){
	    if( args.length != (1+1) && args.length != (2+1) ){
		String errMsg = "";
		for(int i=0; i<args.length; i++) errMsg += args[i]+" ";
		IOut.err("wrong number of arguments; args.length="+args.length+": "+errMsg);
		return null;
	    }
	    Data d = new Data();
	    try{
		d.udegree = Integer.parseInt(args[1]);
		if(args.length==3) d.vdegree = Integer.parseInt(args[2]);
		else{ d.vdegree=-1; }
	    }catch(Exception e){ e.printStackTrace(); }
	    return d;
	}
	public class Data extends EntityData{
	    public int udegree, vdegree=-1;
	}
    }
    
    public static class BasisMatrix extends Entity{
	public String getLabel(){ return("bmat"); }
	public class Data extends EntityData{
	}
    }
    
    public static class StepSize extends Entity{
	public String getLabel(){ return("step"); }
	public class Data extends EntityData{
	}
    }
    
    // ////////////////////
    // elements
    // ////////////////////
    public static class Point extends Entity{
	public GeometricVertex geometryVertex=null;
	public String getLabel(){ return("p"); }
	public EntityData parse(String[] args){
	    if(geometryVertex==null)
		for(int i=0; i<entities.size() && geometryVertex==null; i++)
		    if(entities.get(i) instanceof GeometricVertex)
			geometryVertex = (GeometricVertex)entities.get(i);
	    
	    Data d = new Data();
	    for(int i=1; i<args.length; i++){
		try{
		    int vertexNumber = Integer.parseInt(args[i]);
		    GeometricVertex.Data v =
			(GeometricVertex.Data)geometryVertex.getData(vertexNumber);
		    d.vertices.add(v);
		}catch(Exception e){ e.printStackTrace(); }
	    }
	    d.instantiate();
	    return d;
	}
	public class Data extends ParametricGeometryData{
	    public ArrayList<GeometricVertex.Data> vertices;
	    public Data(){ vertices=new ArrayList<GeometricVertex.Data>(); }
	    public void instantiate(){
		for(int i=0; i<vertices.size(); i++){
		    GeometricVertex.Data d = vertices.get(i);
		    //IPoint pt = new IPoint(d.x,d.y,d.z); // ?
		    IPoint pt = new IPoint(server, d.x,d.y,d.z); // ?
		    objects.add(pt);
		    addToGroup(pt); //
		}
	    }
	}
    }
    
    public static class Line extends Entity{
	public GeometricVertex geometryVertex=null;
	public TextureVertex textureVertex=null;
	
	public String getLabel(){ return("l"); }
	public EntityData parse(String[] args){
	    if(geometryVertex==null)
		for(int i=0; i<entities.size() && geometryVertex==null; i++)
		    if(entities.get(i) instanceof GeometricVertex)
			geometryVertex = (GeometricVertex)entities.get(i);
	    if(textureVertex==null)
		for(int i=0; i<entities.size() && textureVertex==null; i++)
		    if(entities.get(i) instanceof TextureVertex)
			textureVertex = (TextureVertex)entities.get(i);
	    
	    Data d = new Data();
	    for(int i=1; i<args.length; i++){
		String[] index = args[i].split("/");
		try{
		    int vnum = Integer.parseInt(index[0]);
		    GeometricVertex.Data v =
			(GeometricVertex.Data)geometryVertex.getData(vnum);
		    d.vertices.add(v);
		    if(index.length>1){
			int vtnum = Integer.parseInt(index[1]);
			TextureVertex.Data vt =
			    (TextureVertex.Data)textureVertex.getData(vtnum);
			d.textureVertices.add(vt);
		    }
		}catch(Exception e){ e.printStackTrace(); }
	    }
	    d.instantiate();
	    return d;
	}
	public class Data extends EntityData{
	    public ArrayList<GeometricVertex.Data> vertices;
	    public ArrayList<TextureVertex.Data> textureVertices;
	    public Data(){
		vertices=new ArrayList<GeometricVertex.Data>();
		textureVertices=new ArrayList<TextureVertex.Data>();
	    }
	    public void instantiate(){}
	}
    }
    
    public static class Face extends Entity{
	public GeometricVertex geometryVertex=null;
	public TextureVertex textureVertex=null;
	public VertexNormal vertexNormal=null;
	
	public boolean newface=true;
	//public IObject mesh;
	public IMesh mesh;
	
	public GroupName.Data group=null;
	public void setGroup(GroupName.Data g){ group=g; }
	public void addToGroup(IObject elem){ if(group!=null) group.add(elem); }
	
	
	public String getLabel(){ return("f"); }
	public EntityData parse(String[] args){
	    if(geometryVertex==null)
		for(int i=0; i<entities.size() && geometryVertex==null; i++)
		    if(entities.get(i) instanceof GeometricVertex)
			geometryVertex = (GeometricVertex)entities.get(i);
	    if(textureVertex==null)
		for(int i=0; i<entities.size() && textureVertex==null; i++)
		    if(entities.get(i) instanceof TextureVertex)
			textureVertex = (TextureVertex)entities.get(i);
	    if(vertexNormal==null)
		for(int i=0; i<entities.size() && vertexNormal==null; i++)
		    if(entities.get(i) instanceof VertexNormal)
			vertexNormal = (VertexNormal)entities.get(i);
	    
	    
	    //if(newface){ mesh = new IGTriMesh(); newface=false; }
	    if(newface){
		/*
		if(meshType==meshTypeTriMesh) mesh = new IGTriMesh(); 
		else if(meshType==meshTypeQuadMesh) mesh = new IGTriMesh();
		else if(meshType==meshTypeMeshField) mesh = new MeshField();
		else mesh = new IMesh();
		*/
		mesh = new IMesh(server);
		newface=false;
	    }
	    
	    
	    Data d = new Data();
	    for(int i=1; i<args.length; i++){
		String[] index = args[i].split("/");
		try{
		    int vnum = Integer.parseInt(index[0]);
		    GeometricVertex.Data v =
			(GeometricVertex.Data)geometryVertex.getData(vnum);
		    d.vertices.add(v);
		    
		    if( (index.length>1) && (index[1].length()>0) ){
			int vtnum = Integer.parseInt(index[1]);
			TextureVertex.Data vt =
			    (TextureVertex.Data)textureVertex.getData(vtnum);
			d.textureVertices.add(vt);
		    }
		    
		    if( (index.length>2) && (index[2].length()>0) ){
			int vnnum = Integer.parseInt(index[2]);
			VertexNormal.Data vn =
			    (VertexNormal.Data)vertexNormal.getData(vnnum);
			d.vertexNormals.add(vn);
		    }
		    
		}catch(Exception e){ e.printStackTrace(); }
	    }
	    /*
	    if(meshType==meshTypeTriMesh){
		//IGTriMesh.Face f = (IGTriMesh.Face)d.instantiate();
		//((IGTriMesh)mesh).addFace(f);
	    }
	    else if(meshType==meshTypeQuadMesh){
		//IGQuadMesh.Face f = (IGQuadMesh.Face)d.instantiate();
		//((IGQuadMesh)mesh).addFace(f);
	    }
	    else if(meshType==meshTypeMeshField){
		//MeshField.Face f = (MeshField.Face)d.instantiate();
		//((MeshField)mesh).addFace(f);
	    }
	    else{
		//IFace f = (IFace)d.instantiate();
		//((IMesh)mesh).addFace(f);
	    }
	    */
	    IFace f = d.instantiate();
	    mesh.addFace(f);
	    
	    return d;
	}
	
	public void resetMesh(){
	    newface=true; 
	    if(mesh!=null) addToGroup(mesh);
	    mesh=null;
	}
	
	public boolean creatingMesh(){ return !newface; }
	
	public class Data extends EntityData{
	    public ArrayList<GeometricVertex.Data> vertices;
	    public ArrayList<TextureVertex.Data> textureVertices;
	    public ArrayList<VertexNormal.Data> vertexNormals;
	    
	    public Data(){
		vertices=new ArrayList<GeometricVertex.Data>();
		textureVertices=new ArrayList<TextureVertex.Data>();
		vertexNormals=new ArrayList<VertexNormal.Data>();
	    }
	    //public IGTriMesh.Face instantiate(){
	    public /*IObject*/ IFace instantiate(){

		/*
		if( meshType==meshTypeTriMesh && vertices.size()!=3 ){
		    IOut.err("number of vertex have to be 3. not "+vertices.size());
		    return null;
		}
		*/
		
		int num = vertices.size();
		
		GeometricVertex.Data[] v = new GeometricVertex.Data[num];
		TextureVertex.Data[] vt = new TextureVertex.Data[num];
		VertexNormal.Data[] vn = new VertexNormal.Data[num];
		
		for(int i=0; i<vertices.size(); i++){
		    v[i] = vertices.get(i);
		    vt[i] =null;
		    vn[i] =null;
		    
		    if(textureVertices.size()==vertices.size())
			vt[i] = textureVertices.get(i);
		    if(vertexNormals.size()==vertices.size())
			vn[i] = vertexNormals.get(i);
		}
		
		IFace f=null;
		IVertex[] vtx = new IVertex[num];
		for(int i=0; i<num; i++){
		    vtx[i] = (IVertex)v[i].getVertex();
		    if(vt[i]!=null) vtx[i].texture(vt[i].getVertex().to2d());
		    if(vn[i]!=null) vtx[i].setNormal(vn[i].getVertex());
		}
		
		IEdge[] edg = new IEdge[num];
		for(int i=0; i<num; i++) edg[i] = (IEdge)vtx[i].createEdgeTo(vtx[(i+1)%num]);
		
		f = new IFace(edg);
		//objects.add(f);
		return f;
	    }
	}
    }
    
    public static class Curve extends Entity{
	public GeometricVertex geometryVertex=null;
	public Degree degree=null;
	
	public String getLabel(){ return("curv"); }
	
	public EntityData parse(String[] args){
	    if(geometryVertex==null)
		for(int i=0; i<entities.size() && geometryVertex==null; i++)
		    if(entities.get(i) instanceof GeometricVertex)
			geometryVertex = (GeometricVertex)entities.get(i);
	    if(degree==null)
		for(int i=0; i<entities.size() && degree==null; i++)
		    if(entities.get(i) instanceof Degree)
			degree = (Degree)entities.get(i);
	    
	    Data d = new Data();
	    
	    try{
		d.u0 = Double.parseDouble(args[1]);
		d.u1 = Double.parseDouble(args[2]);
		
		for(int i=3; i<args.length; i++){
		    int vnum = Integer.parseInt(args[i]);
		    GeometricVertex.Data v =
			(GeometricVertex.Data)geometryVertex.getData(vnum);
		    d.vertices.add(v);
		}
		
		d.degree = (Degree.Data)degree.getLatestData();
		
	    }catch(Exception e){ e.printStackTrace(); }
	    return d;
	}
	
	public class Data extends ParametricGeometryData{
	    public double u0, u1;
	    public ArrayList<GeometricVertex.Data> vertices;
	    
	    public Degree.Data degree;
	    public Parameter.Data knots;
	    
	    public Data(){ vertices=new ArrayList<GeometricVertex.Data>(); }
	    
	    public void setDegree(Degree.Data d){
		degree = d;
	    }
	    public void setKnots(Parameter.Data d){
		knots = d;
	    }
	    public void instantiate(){
		IVec[] cpts = new IVec[vertices.size()];
		for(int i=0; i<vertices.size(); i++){
		    GeometricVertex.Data v = vertices.get(i);
		    if(v.w!=1.0) cpts[i] = new IVec4(v.x,v.y,v.z,v.w);
		    else cpts[i] = new IVec(v.x,v.y,v.z);
		}
		//ICurve curve=new ICurve(cpts,degree.udegree,knots.getArray(),u0,u1);
		ICurve curve=new ICurve(server,cpts,degree.udegree,knots.getArray(),u0,u1);
		//objects.add(curve.parent());
		//addToGroup(curve.parent());
		objects.add(curve);
		addToGroup(curve);
	    }
	}
    }
    
    public static class Curve2D extends Entity{
	public ParameterSpaceVertex parameterSpaceVertex=null;
	public Degree degree=null;
	
	public String getLabel(){ return("curv2"); }
	
	public EntityData parse(String[] args){
	    if(parameterSpaceVertex==null)
		for(int i=0; i<entities.size() && parameterSpaceVertex==null; i++)
		    if(entities.get(i) instanceof ParameterSpaceVertex)
			parameterSpaceVertex = (ParameterSpaceVertex)entities.get(i);
	    if(degree==null)
		for(int i=0; i<entities.size() && degree==null; i++)
		    if(entities.get(i) instanceof Degree)
			degree = (Degree)entities.get(i);
	    Data d = new Data();
	    try{
		for(int i=1; i<args.length; i++){
		    int vnum = Integer.parseInt(args[i]);
		    ParameterSpaceVertex.Data vp =
			(ParameterSpaceVertex.Data)parameterSpaceVertex.getData(vnum);
		    d.parameterSpaceVertices.add(vp);
		}
		d.degree = (Degree.Data)degree.getLatestData();
	    }catch(Exception e){ e.printStackTrace(); }
	    return d;
	}
	
	public class Data extends ParametricGeometryData{
	    public ArrayList<ParameterSpaceVertex.Data> parameterSpaceVertices;
	    
	    public Degree.Data degree;
	    public Parameter.Data knots;
	    
	    public double u0=0, u1=0;
	    public ISurface surface=null;
	    public ITrimCurve curve=null;
	    
	    public Data(){ parameterSpaceVertices=new ArrayList<ParameterSpaceVertex.Data>(); }
	    
	    public void setDegree(Degree.Data d){ degree = d; }
	    public void setKnots(Parameter.Data d){ knots = d; }
	    
	    public void setParameterRange(double u0, double u1){ this.u0=u0; this.u1=u1; }
	    
	    public void setSurface(ISurface surf){ surface=surf; }
	    public ITrimCurve getCurve(){ return curve; }
	    
	    public void instantiate(){
		IVec[] cpts = new IVec[parameterSpaceVertices.size()];
		for(int i=0; i<parameterSpaceVertices.size(); i++){
		    ParameterSpaceVertex.Data v = parameterSpaceVertices.get(i);
		    if(v.w!=1.0) cpts[i] = new IVec4(v.u,v.v,0,v.w);
		    else cpts[i] = new IVec(v.u,v.v,0);
		}
		
		//curve = new ITrimCurve(surface,cpts,degree.udegree,knots.getArray(),u0,u1);
		curve = new ITrimCurve(cpts,degree.udegree,knots.getArray(),u0,u1);
		curve.normalizeControlPoints(surface);
	    }
	}
    }
    
    public static class Surface extends Entity{
	public GeometricVertex geometryVertex=null;
	public TextureVertex textureVertex=null;
	public VertexNormal vertexNormal=null;
	public Degree degree=null;
	
	public String getLabel(){ return("surf"); }
	
	public EntityData parse(String[] args){
	    if(geometryVertex==null)
		for(int i=0; i<entities.size() && geometryVertex==null; i++)
		    if(entities.get(i) instanceof GeometricVertex)
			geometryVertex = (GeometricVertex)entities.get(i);
	    if(textureVertex==null)
		for(int i=0; i<entities.size() && textureVertex==null; i++)
		    if(entities.get(i) instanceof TextureVertex)
			textureVertex = (TextureVertex)entities.get(i);
	    if(vertexNormal==null)
		for(int i=0; i<entities.size() && vertexNormal==null; i++)
		    if(entities.get(i) instanceof VertexNormal)
			vertexNormal = (VertexNormal)entities.get(i);
	    if(degree==null)
		for(int i=0; i<entities.size() && degree==null; i++)
		    if(entities.get(i) instanceof Degree)
			degree = (Degree)entities.get(i);
	    
	    Data d = new Data();
	    
	    try{
		d.u0 = Double.parseDouble(args[1]);
		d.u1 = Double.parseDouble(args[2]);
		d.v0 = Double.parseDouble(args[3]);
		d.v1 = Double.parseDouble(args[4]);
		
		for(int i=5; i<args.length; i++){
		    String[] index = args[i].split("/");
		    int vnum = Integer.parseInt(index[0]);
		    GeometricVertex.Data v =
			(GeometricVertex.Data)geometryVertex.getData(vnum);
		    d.vertices.add(v);
		    
		    if( (index.length>1) && (index[1].length()>0) ){
			int vtnum = Integer.parseInt(index[1]);
			TextureVertex.Data vt =
			    (TextureVertex.Data)textureVertex.getData(vtnum);
			d.textureVertices.add(vt);
		    }
		    
		    if( (index.length>2) && (index[2].length()>0) ){
			int vnnum = Integer.parseInt(index[2]); //Integer.parseInt(index[1]); // bug
			VertexNormal.Data vn =
			    (VertexNormal.Data)vertexNormal.getData(vnnum);
			d.vertexNormals.add(vn);
		    }
		}
		
		d.degree = (Degree.Data)degree.getLatestData();
		
	    }catch(Exception e){ e.printStackTrace(); }
	    return d;
	}
	
	public class Data extends ParametricGeometryData{
	    public double u0, u1, v0, v1;
	    public ArrayList<GeometricVertex.Data> vertices;
	    public ArrayList<TextureVertex.Data> textureVertices;
	    public ArrayList<VertexNormal.Data> vertexNormals;
	    
	    public Degree.Data degree;
	    public Parameter.Data uknots, vknots;
	    
	    public ArrayList<OuterTrimmingLoop.Data> outerTrimmingLoops;
	    public ArrayList<InnerTrimmingLoop.Data> innerTrimmingLoops;
	    
	    public Data(){
		vertices=new ArrayList<GeometricVertex.Data>();
		textureVertices=new ArrayList<TextureVertex.Data>();
		vertexNormals=new ArrayList<VertexNormal.Data>();
		outerTrimmingLoops=new ArrayList<OuterTrimmingLoop.Data>();
		innerTrimmingLoops=new ArrayList<InnerTrimmingLoop.Data>();
	    }
	    
	    public void setDegree(Degree.Data d){ degree = d; }
	    public void setKnots(Parameter.Data d){
		if(d.isU()) uknots = d;
		else if(d.isV()) vknots = d;
		else IOut.err("invalid parameter data"); 
	    }
	    public void setOuterTrimmingLoop(OuterTrimmingLoop.Data d){
		outerTrimmingLoops.add(d);
	    }
	    public void setInnerTrimmingLoop(InnerTrimmingLoop.Data d){
		innerTrimmingLoops.add(d);
	    }
	    
	    public void instantiate(){
		int unum = uknots.size() - degree.udegree - 1;
		int vnum = vknots.size() - degree.vdegree - 1;
		IVec[][] cpts = new IVec[unum][vnum];
		
		for(int i=0; i<vertices.size(); i++){
		    GeometricVertex.Data v = vertices.get(i);
		    if(v.w!=1.0) cpts[i%unum][i/unum] = new IVec4(v.x,v.y,v.z,v.w);
		    else cpts[i%unum][i/unum] = new IVec(v.x,v.y,v.z);
		}
		ISurface surf =
		    new ISurface(server, //
				 cpts, degree.udegree, degree.vdegree,
				 uknots.getArray(), vknots.getArray(),
				 u0, u1, v0, v1);
		for(int i=0; i<outerTrimmingLoops.size(); i++){
		    OuterTrimmingLoop.Data loop = outerTrimmingLoops.get(i);
		    loop.setSurface(surf);
		    loop.instantiate();
		}
		
		for(int i=0; i<innerTrimmingLoops.size(); i++){
		    InnerTrimmingLoop.Data loop = innerTrimmingLoops.get(i);
		    loop.setSurface(surf);
		    loop.instantiate();
		}
		
		//objects.add(surf.parent());
		//addToGroup(surf.parent());
		objects.add(surf);
		addToGroup(surf);
	    }
	}
    }
    
    
    // ///////////////////////////////////////////////
    // free-form curve/surface body statements
    // ///////////////////////////////////////////////
    public static class Parameter extends Entity{
	public String getLabel(){ return("parm"); }
	public EntityData parse(String[] args){
	    Data d = new Data();
	    if(args[1].equals("u")) d.u_v=true; // true means u
	    else if(args[1].equals("v")) d.u_v=false; // false means v
	    
	    for(int i=2; i<args.length; i++){
		try{ d.parameters.add(Double.valueOf(args[i])); }
		catch(Exception e){ e.printStackTrace(); }
	    }
	    return d;
	}
	
	public class Data extends EntityData{
	    public boolean u_v=true; // true means u: otherwise v
	    public ArrayList<Double> parameters;
	    public Data(){ parameters=new ArrayList<Double>(); }
	    public boolean isU(){ return u_v; }
	    public boolean isV(){ return !u_v; }
	    public int size(){ return parameters.size(); }
	    public double[] getArray(){
		double[] params = new double[parameters.size()];
		for(int i=0; i<parameters.size(); i++)
		    params[i] = parameters.get(i).doubleValue();
		return params;
	    }
	}
    }
    
    public static class OuterTrimmingLoop extends Entity{
	public Curve2D curve2D=null;
	public String getLabel(){ return("trim"); }
	
	public EntityData parse(String[] args){
	    if(curve2D==null)
		for(int i=0; i<entities.size() && curve2D==null; i++)
		    if(entities.get(i) instanceof Curve2D)
			curve2D = (Curve2D)entities.get(i);
	    
	    Data d = new Data();
	    
	    for(int i=1; i<args.length; i+=3){
		try{
		    d.startParameters.add(Double.valueOf(args[i]));
		    d.endParameters.add(Double.valueOf(args[i+1]));
		    
		    int cvnum = Integer.parseInt(args[i+2]);
		    Curve2D.Data curv2d = (Curve2D.Data)curve2D.getData(cvnum);
		    d.curve2d.add(curv2d);
		}catch(Exception e){ e.printStackTrace(); }
	    }
	    return d;
	}
	
	public class Data extends EntityData{
	    public ArrayList<Double> startParameters;
	    public ArrayList<Double> endParameters;
	    public ArrayList<Curve2D.Data> curve2d;
	    
	    public ISurface surface=null;
	    
	    public Data(){
		startParameters=new ArrayList<Double>();
		endParameters=new ArrayList<Double>();
		curve2d=new ArrayList<Curve2D.Data>();
	    }
	    
	    public void setSurface(ISurface surf){ surface=surf; }
	    
	    public void instantiate(){
		//surface.initOuterTrimLoop();
		for(int i=0; i<curve2d.size(); i++){
		    Curve2D.Data crv = curve2d.get(i);
		    crv.setSurface(surface);
		    crv.setParameterRange(startParameters.get(i).doubleValue(),
					  endParameters.get(i).doubleValue());
		    crv.instantiate();
		    ((ISurfaceGeo)surface.surface).addOuterTrim(crv.getCurve()); // is this cast OK?
		}
		((ISurfaceGeo)surface.surface).closeOuterTrim(); // is this OK
		//surface.checkTrimLoop();
		//if(surface.graphics()!=null) surface.graphics().update();
		
	    }
	}
    }
    
    public static class InnerTrimmingLoop extends Entity{
	public Curve2D curve2D=null;
	
	public String getLabel(){ return("hole"); }
	public EntityData parse(String[] args){
	    if(curve2D==null)
		for(int i=0; i<entities.size() && curve2D==null; i++)
		    if(entities.get(i) instanceof Curve2D)
			curve2D = (Curve2D)entities.get(i);
	    
	    Data d = new Data();
	    
	    for(int i=1; i<args.length; i+=3){
		try{
		    d.startParameters.add(Double.valueOf(args[i]));
		    d.endParameters.add(Double.valueOf(args[i+1]));
		    
		    int cvnum = Integer.parseInt(args[i+2]);
		    Curve2D.Data curv2d = (Curve2D.Data)curve2D.getData(cvnum);
		    d.curve2d.add(curv2d);
		}catch(Exception e){ e.printStackTrace(); }
	    }
	    
	    return d;
	}
	public class Data extends EntityData{
	    public ArrayList<Double> startParameters;
	    public ArrayList<Double> endParameters;
	    public ArrayList<Curve2D.Data> curve2d;
	    
	    public ISurface surface=null;
	    
	    public Data(){
		startParameters=new ArrayList<Double>();
		endParameters=new ArrayList<Double>();
		curve2d=new ArrayList<Curve2D.Data>();
	    }
	    
	    public void setSurface(ISurface surf){ surface=surf; }
	    
	    public void instantiate(){
		//surface.initInnerTrimLoop();
		for(int i=0; i<curve2d.size(); i++){
		    Curve2D.Data crv = curve2d.get(i);
		    crv.setSurface(surface);
		    crv.setParameterRange(startParameters.get(i).doubleValue(),
					  endParameters.get(i).doubleValue());
		    crv.instantiate();
		    ((ISurfaceGeo)surface.surface).addInnerTrim(crv.getCurve()); // is this OK?
		}
		
		((ISurfaceGeo)surface.surface).closeInnerTrim(); // is this ok?
		
		//if(surface.graphics()!=null) surface.graphics().update();
		
	    }
	    
	}
    }
    
    public static class SpecialCurve extends Entity{
	public Curve2D curve2D=null;
	public String getLabel(){ return("scrv"); }
	public EntityData parse(String[] args){
	    if(curve2D==null)
		for(int i=0; i<entities.size() && curve2D==null; i++)
		    if(entities.get(i) instanceof Curve2D)
			curve2D = (Curve2D)entities.get(i);
	    
	    Data d = new Data();
	    
	    for(int i=1; i<args.length; i+=3){
		try{
		    d.startParameters.add(Double.valueOf(args[i]));
		    d.endParameters.add(Double.valueOf(args[i+1]));
		    
		    int cvnum = Integer.parseInt(args[i+2]);
		    Curve2D.Data curv2d = (Curve2D.Data)curve2D.getData(cvnum);
		    d.curve2d.add(curv2d);
		}catch(Exception e){ e.printStackTrace(); }
	    }
	    
	    return d;
	}
	public class Data extends EntityData{
	    public ArrayList<Double> startParameters;
	    public ArrayList<Double> endParameters;
	    public ArrayList<Curve2D.Data> curve2d;
	    public Data(){
		startParameters=new ArrayList<Double>();
		endParameters=new ArrayList<Double>();
		curve2d=new ArrayList<Curve2D.Data>();
	    }
	}
    }
    
    public static class SpecialPoint extends Entity{
	
	public String getLabel(){ return("sp"); }
	public class Data extends EntityData{
	    public ArrayList<ParameterSpaceVertex.Data> parameterSpaceVertices;
	    public Data(){
		parameterSpaceVertices=new ArrayList<ParameterSpaceVertex.Data>();
	    }
	}
    }
    
    public static class EndStatement extends Entity{
	public String getLabel(){ return("end"); }
	public class Data extends EntityData{
	}
    }
    
    // ///////////////////////////////////////////////
    // connectivity between free-form surfaces
    // ///////////////////////////////////////////////
    public static class Connect extends Entity{
	public Curve2D curve2D=null;
	public Surface surface=null;
	
	public String getLabel(){ return("con"); }
	
	public EntityData parse(String[] args){
	    if(curve2D==null)
		for(int i=0; i<entities.size() && curve2D==null; i++)
		    if(entities.get(i) instanceof Curve2D)
			curve2D = (Curve2D)entities.get(i);
	    if(surface==null)
		for(int i=0; i<entities.size() && surface==null; i++)
		    if(entities.get(i) instanceof Surface)
			surface = (Surface)entities.get(i);
	    
	    Data d = new Data();
	    
	    try{
		int surfnum1 = Integer.parseInt(args[1]);
		d.surf1 = (Surface.Data)surface.getData(surfnum1);
		
		d.startParameter1 = Double.parseDouble(args[2]);
		d.endParameter1 = Double.parseDouble(args[3]);
		
		int crvnum1 = Integer.parseInt(args[4]);
		d.curv1 = (Curve2D.Data)curve2D.getData(crvnum1);
		
		
		int surfnum2 = Integer.parseInt(args[5]);
		d.surf2 = (Surface.Data)surface.getData(surfnum2);
		
		d.startParameter2 = Double.parseDouble(args[6]);
		d.endParameter2 = Double.parseDouble(args[7]);
		
		int crvnum2 = Integer.parseInt(args[8]);
		d.curv2 = (Curve2D.Data)curve2D.getData(crvnum2);
		
	    }catch(Exception e){ e.printStackTrace(); }
	    
	    return d;
	}
	
	public class Data extends EntityData{
	    public Surface.Data surf1,surf2;
	    public Curve2D.Data curv1,curv2;
	    public double startParameter1, startParameter2, endParameter1, endParameter2;
	}
    }
    
    // ///////////////////////////////////////////////
    // Grouping
    // ///////////////////////////////////////////////
    public static class GroupName extends Entity{
	public String getLabel(){ return("g"); }
	
	public EntityData parse(String[] args){
	    Data d = new Data();
	    d.setName(args[1]);
	    return d;
	}
	
	public class Data extends EntityData{
	    //IGLayer layer; 
	    public void setName(String name){
		if(!ignoreLayers){
		    //layer = IGLayer.getLayer(name);
		}
	    }
	    public void add(IObject elem){
		if(!ignoreLayers){
		    //layer.add(elem);
		}
	    }
	}
    }
    
    public static class SmoothingGroup extends Entity{
	public String getLabel(){ return("s"); }
	public class Data extends EntityData{
	}
    }
    
    public static class MergingGroup extends Entity{
	public String getLabel(){ return("mg"); }
	public class Data extends EntityData{
	}
    }
    
    public static class ObjectName extends Entity{
	public String getLabel(){ return("o"); }
	public EntityData parse(String[] args){
	    Data d = new Data();
	    d.name = args[1];
	    return d;
	}
	public class Data extends EntityData{
	    public String name;
	}
    }
    
    // ///////////////////////////////////////////////
    // Display/render attributes
    // ///////////////////////////////////////////////
    public static class BevelInterpolation extends Entity{
	public String getLabel(){ return("bevel"); }
	public class Data extends EntityData{
	}
    }
    
    public static class ColorInterpolation extends Entity{
	public String getLabel(){ return("c_interp"); }
	public class Data extends EntityData{
	}
    }
    
    public static class DissolveInterpolation extends Entity{
	public String getLabel(){ return("d_interp"); }
	public class Data extends EntityData{
	}
    }
    
    public static class LevelOfDetail extends Entity{
	public String getLabel(){ return("lod"); }
	public class Data extends EntityData{
	}
    }
    
    public static class MaterialName extends Entity{
	public String getLabel(){ return("usemtl"); }
	public class Data extends EntityData{
	}
    }
    
    public static class MaterialLibrary extends Entity{
	public String getLabel(){ return("mtllib"); }
	public class Data extends EntityData{
	}
    }
    
    public static class ShadowCasting extends Entity{
	public String getLabel(){ return("shadow_obj"); }
	public class Data extends EntityData{
	}
    }
    
    public static class RayTracing extends Entity{
	public String getLabel(){ return("trace_obj"); }
	public class Data extends EntityData{
	}
    }
    
    public static class CurveApproximationTechnique extends Entity{
	public String getLabel(){ return("ctech"); }
	public class Data extends EntityData{
	}
    }
    
    public static class SurfaceApproximationTechnique extends Entity{
	public String getLabel(){ return("stech"); }
	public class Data extends EntityData{
	}
    }
    
    
    
    // ///////////////////////////////////////////////////////////////////////
    
    public static String readwoBS(BufferedReader reader) throws IOException{
	String line, line2;
	if( (line = reader.readLine())!=null){
	    while( line.endsWith("\\") &&
		   ((line2 = reader.readLine()) != null) ){
		line = line.substring(0,line.length()-1);
		line += line2;
	    }
	}
	return(line);
    }
    
    
    public static String skipUntil(BufferedReader reader, String line, String start)
	throws IOException {
	if( (line!=null)&& line.startsWith(start) ){ return(line); }
	while( ((line = readwoBS(reader)) != null)&& !line.startsWith(start) );
	return(line);
    }
    

    public static String skipUntilOr(BufferedReader reader, String line, String start1, String start2)
	throws IOException {
	if( (line!=null)&& (line.startsWith(start1)||line.startsWith(start2)) ){ return(line); }
	while( ((line = readwoBS(reader)) != null)&&
	       !(line.startsWith(start1)||line.startsWith(start2)) );
	return(line);
    }
    
    
    public static ArrayList<Entity> init(IServerI server, ArrayList<IObject> objectContainer){
	
	ArrayList<Entity> entityPools = new ArrayList<Entity>();
	
	entityPools.add(new Comment());
	entityPools.add(new GeometricVertex());
	entityPools.add(new TextureVertex());
	entityPools.add(new VertexNormal());
	entityPools.add(new ParameterSpaceVertex());
	entityPools.add(new CurveType());
	entityPools.add(new Degree());
	entityPools.add(new BasisMatrix());
	entityPools.add(new StepSize());
	entityPools.add(new Point());
	entityPools.add(new Line());
	entityPools.add(new Face());
	entityPools.add(new Curve());
	entityPools.add(new Curve2D());
	entityPools.add(new Surface());
	entityPools.add(new Parameter());
	entityPools.add(new OuterTrimmingLoop());
	entityPools.add(new InnerTrimmingLoop());
	entityPools.add(new SpecialCurve());
	entityPools.add(new SpecialPoint());
	entityPools.add(new EndStatement());
	entityPools.add(new Connect());
	entityPools.add(new GroupName());
	entityPools.add(new SmoothingGroup());
	entityPools.add(new MergingGroup());
	entityPools.add(new ObjectName());
	entityPools.add(new BevelInterpolation());
	entityPools.add(new ColorInterpolation());
	entityPools.add(new DissolveInterpolation());
	entityPools.add(new LevelOfDetail());
	entityPools.add(new MaterialName());
	entityPools.add(new MaterialLibrary());
	entityPools.add(new ShadowCasting());
	entityPools.add(new RayTracing());
	entityPools.add(new CurveApproximationTechnique());
	entityPools.add(new SurfaceApproximationTechnique());
	
	
	for(int i=0; i<entityPools.size(); i++){
	    entityPools.get(i).setAllEntities(entityPools);
	    entityPools.get(i).setObjectContainer(objectContainer);
	    entityPools.get(i).setServer(server);
	    
	}
	
	return entityPools;
    }
    

    /**
       Reading an OBJ file and creating objects in a server.
       The main entry of the importer class.
       @param file An importing file object.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public ArrayList<IObject> read(File file, IServerI server){
        try{ return IObjFileImporter.read(new FileReader(file), server); }
        catch(IOException e){ e.printStackTrace(); }
	return null; 
    }
    
    /**
       Reading an OBJ file and creating objects in a server.
       The main entry of the importer class.
       @param stream An input stream.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public ArrayList<IObject> read(InputStream stream, IServerI server){
        return IObjFileImporter.read(new InputStreamReader(stream), server);
    }
    
    /**
       Reading an OBJ file and creating objects in a server.
       @param filereader Reader object of an importing file.
       @param server A server interface to put imported objects in. 
       @return ArrayList of created IObject.
    */
    static public ArrayList<IObject> read(Reader filereader, IServerI server){
	
	BufferedReader reader = null;
	
	ArrayList<IObject> createdObjects = new ArrayList<IObject>();
	ArrayList<Entity> entityProcessors = init(server, createdObjects);
	
	// assume sequential creation of face means they are in one mesh
	Face faceProcessor=null;
	for(int i=0; i<entityProcessors.size() && faceProcessor==null; i++)
	    if(entityProcessors.get(i) instanceof Face)
		faceProcessor = (Face)entityProcessors.get(i);
	
	try {
	    reader = new BufferedReader(filereader);
	    String line, line2;
	    
	    GroupName.Data currentGroup=null;
	    ParametricGeometryData currentGeometry=null;
	    
	    synchronized(IG.lock){
		
		while ((line = readwoBS(reader)) != null) {
		    
		    //IOut.print("."); //
		    
		    Entity currentEntity=null;
		    
		    for(int i=0; i<entityProcessors.size()&&currentEntity==null; i++){
			Entity e = entityProcessors.get(i);
			if((line.split(" +"))[0].equals(e.getLabel())) currentEntity = e; // better split beforehand?
			//if(line.startsWith(e.getLabel())) currentEntity = e;
		    }
		    
		    if(currentEntity!=null){
			
			currentEntity.read(line);
			
			// post process
			if(currentEntity instanceof GroupName){
			    currentGroup = (GroupName.Data)currentEntity.getLatestData();
			}
			else if(currentEntity instanceof Curve){
			    currentGeometry =
				(ParametricGeometryData)currentEntity.getLatestData();
			}
			
			else if(currentEntity instanceof Curve2D){
			    currentGeometry =
				(ParametricGeometryData)currentEntity.getLatestData();
			}
			else if(currentEntity instanceof Surface){
			    currentGeometry =
				(ParametricGeometryData)currentEntity.getLatestData();
			}
			else if(currentEntity instanceof CurveType){
			    currentGeometry=null; // reset for next parametric geometry
			}
			else if(currentEntity instanceof EndStatement){
			    if(currentGeometry!=null){
				// curve2d is instantiated later in surface
				if(!(currentGeometry instanceof Curve2D.Data)){
				    if(currentGroup!=null) currentGeometry.setGroup(currentGroup);
				    currentGeometry.instantiate();
				    currentGeometry = null;
				}
			    }
			    else{
				IOut.err("no correspondent statement to end statement"); //
			    }
			}
			else if(currentEntity instanceof Degree){
			    if(currentGeometry!=null){
				currentGeometry.setDegree((Degree.Data)currentEntity.getLatestData());
			    }
			}
			else if(currentEntity instanceof Parameter){
			    currentGeometry.setKnots((Parameter.Data)currentEntity.getLatestData());
			}
			else if(currentEntity instanceof InnerTrimmingLoop &&
				currentGeometry instanceof Surface.Data ){
			    ((Surface.Data)currentGeometry).setInnerTrimmingLoop((InnerTrimmingLoop.Data)currentEntity.getLatestData());
			}
			else if(currentEntity instanceof OuterTrimmingLoop &&
				currentGeometry instanceof Surface.Data ){
			    ((Surface.Data)currentGeometry).setOuterTrimmingLoop((OuterTrimmingLoop.Data)currentEntity.getLatestData());
			}
			
			if(currentEntity != faceProcessor){
			    if(faceProcessor.creatingMesh()){
				if(currentGroup!=null) faceProcessor.setGroup(currentGroup);
				faceProcessor.resetMesh();
			    }
			}
			
		    }
		    
		}
		
	    }
	    
	}catch(FileNotFoundException e) {
	    e.printStackTrace();
	    //doRecovery(e);
	}catch(IOException e) {
	    //doRecovery(e);
	    e.printStackTrace();
	}finally{
	    try{ if(reader!=null) reader.close(); }catch(Exception e){ e.printStackTrace(); }
	}
	
	IOut.debug(0,"reading complete"); //
	return(createdObjects);
    }
    
    /*
    public static void main(String args[]){
	IGCore.init(null);
	ArrayList objects = new ArrayList();
	BufferedReader reader = null;
	try {
	    reader = new BufferedReader(new FileReader(args[0]));
	    IObjFileImporter objimport = new IObjFileImporter();
	    ArrayList objs = objimport.read(reader);
	    IOut.p(objs.size()+" objects created");
	    for(int i=0; i<objs.size(); i++) IOut.p(objs.get(i)); //
	}catch(Exception e){ e.printStackTrace(); }
    }
    */
    
}
