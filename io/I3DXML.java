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

import igeo.*;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.*;

/*
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com._3ds.xsd._3dxml.*;
import com._3ds.xsd.osm.*;
*/

import org.w3c.dom.*;
import javax.xml.parsers.*;

//import org.apache.xerces.parsers.*;

/**
   3DXML file
      
   @author Satoru Sugihara
*/
public class I3DXML{
    
    static final String materialReferenceFile = "CATMaterialRef.3dxml";
    static final String representationImageFile = "CATRepImage.3dxml";
    
    
    /** 3DXML file (which is actually a zip archive) */
    //public File archiveFile;
    
    public String filename;
    
    /** filenames iside a zip archive */
    public ArrayList<String> fileList;
    
    public ArrayList<Reference3D> references;
    public ArrayList<Instance3D> instances;
    public ArrayList<ReferenceRep> referenceReps;
    public ArrayList<InstanceRep> instanceReps;
    
    /** representation files inside a zip archive */
    public ArrayList<RepresentationDocument> representationDocuments; 
    
    
    
    //public ArrayList<IVec> positions;
    //public ArrayList<IVec> normals;
    //public ArrayList<IVec2> texture;
    //public ArrayList<IVertex> vertices;
    //public ArrayList<Face> faces;
    
    
    //public I3DXML(File archiveFile){
    public I3DXML(String filename){
	//this.archiveFile = archiveFile;
	this.filename=filename;
	fileList = new ArrayList<String>();
	//positions = new ArrayList<IVec>();
	//normals = new ArrayList<IVec>();
	//texture = new ArrayList<IVec2>();
	//faces = new ArrayList<Face>();
	
	references = new ArrayList<Reference3D>();
	instances = new ArrayList<Instance3D>();
	referenceReps = new ArrayList<ReferenceRep>(); 
	instanceReps = new ArrayList<InstanceRep>();
	representationDocuments = new ArrayList<RepresentationDocument>();
    }


    /*
    public void createMesh(){
	vertices = new ArrayList<IVertex>();
	for(int i=0; i<positions.size(); i++){
	    IVertex vtx = new IVertex(positions.get(i));
	    vertices.add(vtx);
	}
	if(vertices.size()==normals.size()){
	    for(int i=0; i<vertices.size(); i++){
		vertices.get(i).setNml(normals.get(i));
	    }
	}
	if(vertices.size()==texture.size()){
	    for(int i=0; i<vertices.size(); i++){
		vertices.get(i).texture(texture.get(i));
	    }
	}
	for(int i=0; i<faces.size(); i++){
	    Face f = faces.get(i);
	    ArrayList<IFace> ifcs = new ArrayList<IFace>();
	    for(int j=0; j<f.triangles.size(); j++){
		Triangle tr = f.triangles.get(j);
		IFace ifc = new IFace(vertices.get(tr.i),vertices.get(tr.j),vertices.get(tr.k));
		ifcs.add(ifc);
	    }
	    for(int j=0; j<f.strips.size(); j++){
		Strip st = f.strips.get(j);
		for(int k=0; k<st.index.size()-2; k++){
		    IFace ifc=null;
		    if(k%2==0){
			ifc = new IFace(vertices.get(st.index.get(k)),vertices.get(st.index.get(k+1)),vertices.get(st.index.get(k+2)));
		    }
		    else{
			ifc = new IFace(vertices.get(st.index.get(k)),vertices.get(st.index.get(k+2)),vertices.get(st.index.get(k+1)));
		    }
		    ifcs.add(ifc);
		}
	    }
	    for(int j=0; j<f.fans.size(); j++){
		Fan fn = f.fans.get(j);
		for(int k=1; k<fn.index.size()-1; k++){
		    IFace ifc = new IFace(vertices.get(fn.index.get(0)),vertices.get(fn.index.get(k)),vertices.get(fn.index.get(k+1)));
		    ifcs.add(ifc);
		}
	    }
	    IMesh mesh = new IMesh();
	    for(int j=0; j<ifcs.size(); j++){ mesh.addFace(ifcs.get(j)); }
	}
    }
    
    static public class Face{
	//public enum Type { TRIANGLE, STRIP, FAN };
	//public ArrayList<Integer> indices;
	//public Type type;
	
	public ArrayList<Triangle> triangles;
	public ArrayList<Strip> strips;
	public ArrayList<Fan> fans;
	
	//public Face(){ indices = new ArrayList<Integer>(); }
	//public Face(Type t){ this(); type = t; }
	
	public Face(){
	    triangles = new ArrayList<Triangle>();
	    strips = new ArrayList<Strip>();
	    fans = new ArrayList<Fan>();
	}
	
	//public void add(int i){ indices.add(i); }
	
	public void addFan(String value){
	    String[] v = value.split("\\s+");
	    if(v!=null){
		Fan fan = new Fan();
		for(int i=0; i<v.length; i++){
		    fan.add(Integer.parseInt(v[i]));
		}
		fans.add(fan);
	    }
	    IG.p(fans.size() + " fans"); //
	}
	public void addTriangle(String value){
	    String[] v = value.split("\\s+");
	    if(v!=null){
		for(int i=0; i<v.length; i+=3){
		    int i1 = Integer.parseInt(v[i]);
		    int i2 = Integer.parseInt(v[i+1]);
		    int i3 = Integer.parseInt(v[i+2]);
		    Triangle tr = new Triangle(i1,i2,i3);
		    triangles.add(tr);
		}
	    }
	    
	    IG.p(triangles.size() + " triangles"); //
	}
	public void addStrip(String value){
	    String[] v = value.split("\\s+");
	    if(v!=null){
		Strip str = new Strip();
		for(int i=0; i<v.length; i++){
		    str.add(Integer.parseInt(v[i]));
		}
		strips.add(str);
	    }
	    
	    IG.p(strips.size() + " strips"); //
	}
	
    }
    
    static public class Triangle{
	public int i,j,k;
	public Triangle(int i, int j, int k){ this.i=i; this.j=j; this.k=k; }
    }
    
    static public class Strip{
	public ArrayList<Integer> index;
	public Strip(){ index = new ArrayList<Integer>(); }
	public void add(int i){ index.add(i); }
    }
    
    static public class Fan{
	public ArrayList<Integer> index;
	public Fan(){ index = new ArrayList<Integer>(); }
	public void add(int i){ index.add(i); }
    }
    
    public void trace(Node top){ recurse(top); }
    
    public void recurse(Node node){
	
	int type = node.getNodeType();
	String name = node.getNodeName();
	
	boolean skip=false;
	if(type == Node.TEXT_NODE){
	}
	else if(type == Node.ELEMENT_NODE){
	    if(name.equals("PolygonalLOD")){ skip = true; }
	    else if(name.equals("Faces")){ readFaces(node); skip = true; }
	    else if(name.equals("VertexBuffer")){ readVertexBuffer(node); skip = true; }
	}
	if(skip) return;
	if(node.hasAttributes()){
	    NamedNodeMap attr = node.getAttributes();
	    for(int i=0; i<attr.getLength(); i++){
		Node a = attr.item(i);
		int atype = a.getNodeType();
		String aname = a.getNodeName();
	    }
	}
	String value = node.getNodeValue();
	if(value!=null){
	}
	NodeList children = node.getChildNodes();
	for(int i=0; i<children.getLength(); i++){
	    Node child = children.item(i);
	    recurse(child);
	}
    }
    
    public void readFaces(Node node){
	NodeList children = node.getChildNodes();
	for(int i=0; i<children.getLength(); i++){
	    Node child = children.item(i);
	    if(child.getNodeName().equals("Face")){ readFace(child); }
	}
    }
    
    public void readFace(Node node){
	Face face = new Face();
	NamedNodeMap attr = node.getAttributes();
	for(int i=0; i<attr.getLength(); i++){
	    Node a = attr.item(i);
	    int atype = a.getNodeType();
	    String aname = a.getNodeName();
	    if(aname.equals("fans")){
		String value = a.getNodeValue();
		String[] seg = value.split("\\s*,\\s*");
		for(int j=0; j<seg.length; j++){ face.addFan(seg[j]); }
	    }
	    else if(aname.equals("strips")){
		String value = a.getNodeValue();
		String[] seg = value.split("\\s*,\\s*");
		for(int j=0; j<seg.length; j++){ face.addStrip(seg[j]); }
	    }
	    else if(aname.equals("triangles")){
		String value = a.getNodeValue();
		String[] seg = value.split("\\s*,\\s*");
		for(int j=0; j<seg.length; j++){ face.addTriangle(seg[j]); }
	    }
	    
	}
	faces.add(face);
    }
    
    public void readVertexBuffer(Node node){
	NodeList children = node.getChildNodes();
	for(int i=0; i<children.getLength(); i++){
	    Node child = children.item(i);
	    if(child.getNodeName().equals("Positions")){ readPositions(child); }
	    else if(child.getNodeName().equals("Normals")){ readNormals(child); }
	    else if(child.getNodeName().equals("TextureCoordinates")){ readTextureCoordinates(child); }
	}
    }
    
    public void readPositions(Node node){
	Node child = node.getFirstChild();
	if(child!=null && child.getNodeType()==Node.TEXT_NODE){
	    String value = child.getNodeValue();
	    String[] coord = value.split("\\s*,\\s*");
	    for(int i=0; coord!=null && i<coord.length; i++){
		String[] xyz = coord[i].split("\\s+");
		if(xyz.length>=3){
		    double x = Double.parseDouble(xyz[0]);
		    double y = Double.parseDouble(xyz[1]);
		    double z = Double.parseDouble(xyz[2]);
		    IVec v = new IVec(x,y,z);
		    positions.add(v);
		}
	    }
	}
    }
    
    public void readNormals(Node node){
	Node child = node.getFirstChild();
	if(child!=null && child.getNodeType()==Node.TEXT_NODE){
	    String value = child.getNodeValue();
	    String[] coord = value.split("\\s*,\\s*");
	    for(int i=0; coord!=null && i<coord.length; i++){
		String[] xyz = coord[i].split("\\s+");
		if(xyz.length>=3){
		    double x = Double.parseDouble(xyz[0]);
		    double y = Double.parseDouble(xyz[1]);
		    double z = Double.parseDouble(xyz[2]);
		    IVec v = new IVec(x,y,z);
		    normals.add(v);
		}
	    }
	}
    }
    
    public void readTextureCoordinates(Node node){
	Node child = node.getFirstChild();
	if(child!=null && child.getNodeType()==Node.TEXT_NODE){
	    String value = child.getNodeValue();
	    String[] coord = value.split("\\s*,\\s*");
	    for(int i=0; coord!=null && i<coord.length; i++){
		String[] xy = coord[i].split("\\s+");
		double x=0,y=0;
		if(xy!=null){
		    if(xy.length>0) x = Double.parseDouble(xy[0]);
		    if(xy.length>1) y = Double.parseDouble(xy[1]);
		    texture.add(new IVec2(x,y));
		}
	    }
	}
    }
    */
    
    
    public void linkReferenceReps(){
	for(int i=0; i<referenceReps.size(); i++){
	    for(int j=0; j<representationDocuments.size(); j++){
		if(referenceReps.get(i).associatedFile.equals(representationDocuments.get(j).filename)){
		    referenceReps.get(i).representationDocument = representationDocuments.get(j);
		}
	    }
	    if(referenceReps.get(i).representationDocument == null){
		IOut.err("representation file \""+referenceReps.get(i).associatedFile+"\" not found");
	    }
	}
    }
    
    public void linkInstances(){
	for(int i=0; i<instances.size(); i++){ instances.get(i).link(this); }
    }
    
    public void linkInstanceReps(){
	for(int i=0; i<instanceReps.size(); i++){ instanceReps.get(i).link(this); }
    }
    

    public void linkNodes(){
	linkInstances();
	linkInstanceReps();
	linkReferenceReps();
    }
    
    
    public ArrayList<IGeometry> instantiate(){
	if(references!=null){
	    if(references.size()>0) return references.get(0).instantiate(null);
	}
	return null;
    }
    
    
    
    
    public I3DXMLNode getNode(int id){
	for(int i=0; i<references.size(); i++){
	    if(references.get(i).id == id) return references.get(i);
	}
	for(int i=0; i<instances.size(); i++){
	    if(instances.get(i).id == id) return instances.get(i);
	}
	for(int i=0; i<referenceReps.size(); i++){
	    if(referenceReps.get(i).id == id) return referenceReps.get(i);
	}
	for(int i=0; i<instanceReps.size(); i++){
	    if(instanceReps.get(i).id == id) return instanceReps.get(i);
	}
	IOut.err("no such node with id="+id); //
	return null;
    }
    
    
    public static class I3DXMLNode{
	
	static String nameDelimiter = "/";
	
	public int id;
	//public String type;
	public String name;
	
	public I3DXMLNode(){}
	public I3DXMLNode(int id){ this.id = id; }
	public I3DXMLNode(int id, String name){
	    this.id = id;
	    this.name = name;
	}
	
	public ArrayList<IGeometry> instantiate(IMatrix4 transform){ return null; }
	
    }
    
    public static class Reference3D extends I3DXMLNode{
	
	public ArrayList<I3DXMLNode> components;
	public ArrayList<I3DXMLNode> instances;
	
	public Reference3D(){}
	public Reference3D(int id){ super(id); }
	public Reference3D(int id, String name){ super(id,name); }
	
	
	public void addInstance(Instance3D instance){
	    if(instances==null) instances = new  ArrayList<I3DXMLNode>();
	    instances.add(instance);
	}

	public void addComponent(I3DXMLNode n){
	    if(components==null) components = new ArrayList<I3DXMLNode>();
	    components.add(n);
	}
	
	public ArrayList<IGeometry> instantiate(IMatrix4 transform){
	    if(components!=null){
		ArrayList<IGeometry> geo = new ArrayList<IGeometry>();
		for(int i=0; i<components.size(); i++){
		    ArrayList<IGeometry> g = components.get(i).instantiate(transform);
		    if(g!=null) geo.addAll(g);
		}
		return geo;
	    }
	    return null;
	}
    }
    
    public static class Instance3D extends I3DXMLNode{
	public int aggregatedBy;
	public int instanceOf;
	public Matrix4x3 relativeMatrix;
	
	public I3DXMLNode aggregatedNode;
	public Reference3D reference;
	
	public Instance3D(){}
	public Instance3D(int id){ super(id); }
	public Instance3D(int id, String name){ super(id,name); }
	
	public void link(I3DXML xml){
	    aggregatedNode = xml.getNode(aggregatedBy);
	    reference = (Reference3D)xml.getNode(instanceOf);
	    
	    if(aggregatedNode==null){
		IOut.err("no aggregatedNode found");
	    }

	    if(reference==null){
		IOut.err("no reference found");
	    }
	    
	    if(aggregatedNode instanceof Reference3D) ((Reference3D)aggregatedNode).addComponent(this);
	    reference.addInstance(this);
	}
	
	public ArrayList<IGeometry> instantiate(IMatrix4 transform){
	    if(reference!=null){
		
		IMatrix4 mat = null;
		if(transform!=null){
		    
		    if(relativeMatrix==null) mat = transform.cp();
		    else{
			
			IMatrix3 tm = transform.getMatrix3();
			IVec tv = transform.getTranslateVector();
			
			IMatrix4 rm4 = relativeMatrix.getIMatrix4();
			
			IMatrix3 rm = rm4.getMatrix3();
			IVec rv = rm4.getTranslateVector();
			
			tv.add( tm.mul(rv) ); // this comes before tm.mul(rm)
			tm.mul(rm);
			mat = new IMatrix4(tm,tv);
			
			//rm.mul(tm);
			//rv.add(rm.mul(tv));
			//mat = new IMatrix4(rm,rv);
			
			//mat = transform.cp().mul(relativeMatrix.getIMatrix4());
		    }
		    
		    //mat = transform.cp().mul(relativeMatrix.getIMatrix4());
		    
		    /*
		    mat = new Matrix4x3(transform.getIMatrix3().mul(relativeMatrix.getIMatrix3()),
					transform.getTranslateVector().add(relativeMatrix.getTranslateVector()));
		    */
		    /*
		    mat = new Matrix4x3(relativeMatrix.getIMatrix3().mul(transform.getIMatrix3()),
					relativeMatrix.getTranslateVector().add(transform.getTranslateVector()));
		    */
		}
		else{
		    mat = relativeMatrix.getIMatrix4();
		}
		
		ArrayList<IGeometry> geo = reference.instantiate(mat);


		// add name
		if(name!=null){
		    for(int i=0; i<geo.size(); i++){
			String n = geo.get(i).name();
			if(n!=null){ n = name + nameDelimiter + n; }
			else{ n = name; }
			geo.get(i).name(n);
		    }
		}
		
		//ArrayList<IGeometry> geo = reference.instantiate(relativeMatrix);
		/*
		if(geo!=null && relativeMatrix!=null){
		    //IOut.err(geo.size() + " meshes"); //
		    for(int i=0; i<geo.size(); i++){
			//geo.get(i).clr((float)(i/2), (float)(i%2), 0); //
			//IOut.err("vertexnum="+((IMesh)geo.get(i)).vertexNum());
			//IOut.err(geo.get(i));
			double[][] m = relativeMatrix.val;
			geo.get(i).transform(new IMatrix3(m[0][0], m[0][1], m[0][2],
							  m[1][0], m[1][1], m[1][2],
							  m[2][0], m[2][1], m[2][2]));
			geo.get(i).translate(m[3][0], m[3][1], m[3][2]);
			//geo.get(i).translate(0,0,200);
		    }
		}
		*/
		return geo;
	    }
	    return null;
	}
	
    }
    
    public static class ReferenceRep extends I3DXMLNode{
	
	public enum FormatType{ UVR, TECHREP, TESSELLATED, BEHAVIORAL, OTHER }
	
	public ArrayList<I3DXMLNode> instanceReps;
	
	public FormatType format;
	public String associatedFile;
	public String associatedURN;
	
	public RepresentationDocument representationDocument=null;
	
	public ReferenceRep(){}
	public ReferenceRep(int id){ super(id); }
	public ReferenceRep(int id, String name){ super(id,name); }
	
	public void addInstance(InstanceRep instanceR){
	    if(instanceReps==null) instanceReps = new  ArrayList<I3DXMLNode>();
	    instanceReps.add(instanceR);
	}
	
	public ArrayList<IMesh> instantiatePolygon(IMatrix4 transform){
	    if(representationDocument!=null){
		return representationDocument.instantiatePolygon(transform);
	    }
	    return null;
	}
	public ArrayList<ICurve> instantiatePolyline(IMatrix4 transform){
	    if(representationDocument!=null){
		return representationDocument.instantiatePolyline(transform);
	    }
	    return null;
	}
	
	public ArrayList<IGeometry> instantiate(IMatrix4 transform){
	    
	    ArrayList<IMesh> mesh = instantiatePolygon(transform);
	    ArrayList<ICurve> curve = instantiatePolyline(transform);
	    
	    ArrayList<IGeometry> geo = new ArrayList<IGeometry>();
	    if(mesh!=null) for(int i=0; i<mesh.size(); i++) geo.add(mesh.get(i));
	    if(curve!=null) for(int i=0; i<curve.size(); i++) geo.add(curve.get(i));

	    return geo;
	}
	
    }
    
    public static class InstanceRep extends I3DXMLNode{
	public int aggregatedBy;
	public int instanceOf;
	
	public I3DXMLNode aggregatedNode;
	public ReferenceRep referenceRep;
	
	public InstanceRep(){}
	public InstanceRep(int id){ super(id); }
	public InstanceRep(int id, String name){ super(id,name); }
	
	public void link(I3DXML xml){
	    aggregatedNode = xml.getNode(aggregatedBy);
	    referenceRep = (ReferenceRep)xml.getNode(instanceOf);
	    
	    if(aggregatedNode==null){
		IOut.err("no aggregatedNode found");
	    }
	    
	    if(referenceRep==null){
		IOut.err("no reference found");
	    }
	    
	    if(aggregatedNode instanceof Reference3D) ((Reference3D)aggregatedNode).addComponent(this);
	    referenceRep.addInstance(this);
	}
	
	public ArrayList<IGeometry> instantiate(IMatrix4 transform){
	    if(referenceRep!=null){ return referenceRep.instantiate(transform); }
	    return null;
	}
	
    }
    
    public static class Matrix4x3{
	public double[][] val;
	public Matrix4x3(){ val = new double[4][3]; }
	
	public Matrix4x3(double v1, double v2, double v3, double v4,
			 double v5, double v6, double v7, double v8,
			 double v9, double v10, double v11, double v12){
	    this();
	    val[0][0] = v1;
	    val[0][1] = v2;
	    val[0][2] = v3;
	    val[1][0] = v4;
	    val[1][1] = v5;
	    val[1][2] = v6;
	    val[2][0] = v7;
	    val[2][1] = v8;
	    val[2][2] = v9;
	    val[3][0] = v10;
	    val[3][1] = v11;
	    val[3][2] = v12;
	}
	
	public Matrix4x3(double[] v){
	    this();
	    if(v!=null && v.length>=12){
		val[0][0] = v[0];
		val[0][1] = v[1];
		val[0][2] = v[2];
		val[1][0] = v[3];
		val[1][1] = v[4];
		val[1][2] = v[5];
		val[2][0] = v[6];
		val[2][1] = v[7];
		val[2][2] = v[8];
		val[3][0] = v[9];
		val[3][1] = v[10];
		val[3][2] = v[11];
	    }
	}
	
	public Matrix4x3(IMatrix3 m, IVec translate){
	    this( m.val[0][0], m.val[0][1], m.val[0][2],
		  m.val[1][0], m.val[1][1], m.val[1][2],
		  m.val[2][0], m.val[2][1], m.val[2][2],
		  translate.x, translate.y, translate.z);
	}
	
	public String toString(){
	    return
		String.valueOf(val[0][0]) + " "+
		String.valueOf(val[0][1]) + " "+
		String.valueOf(val[0][2]) + " \n"+
		String.valueOf(val[1][0]) + " "+
		String.valueOf(val[1][1]) + " "+
		String.valueOf(val[1][2]) + " \n"+
		String.valueOf(val[2][0]) + " "+
		String.valueOf(val[2][1]) + " "+
		String.valueOf(val[2][2]) + " \n"+
		String.valueOf(val[3][0]) + " "+
		String.valueOf(val[3][1]) + " "+
		String.valueOf(val[3][2]) + " \n";
	}
	
	public IMatrix4 getIMatrix4(){
	    
	    return new IMatrix4(val[0][0], val[1][0], val[2][0], val[3][0],
				val[0][1], val[1][1], val[2][1], val[3][1], 
				val[0][2], val[1][2], val[2][2], val[3][2], 
				0, 0, 0, 0);
	    /*
	    return new IMatrix4(val[0][0], val[0][1], val[0][2], val[3][0],
				val[1][0], val[1][1], val[1][2], val[3][1], 
				val[2][0], val[2][1], val[2][2], val[3][2], 
				0, 0, 0, 0);
	    */
	}
	/*
	public IMatrix3 getIMatrix3(){
	    return new IMatrix3(val[0][0], val[0][1], val[0][2],
				val[1][0], val[1][1], val[1][2],
				val[2][0], val[2][1], val[2][2]);
	}
	
	public IVec getTranslateVector(){
	    return new IVec(val[3][0], val[3][1], val[3][2]);
	}
	*/		       
	
    }
    
    // 3DRep classes ; Tesselated format
    public static class VisualizationRepType{
	public int id;
	public VisualizationRepType(int id){ this.id=id; }
	public VisualizationRepType(){ id=-1; }
	
	public ArrayList<IMesh> instantiatePolygon(IMatrix4 transform){ return null; }
	public ArrayList<ICurve> instantiatePolyline(IMatrix4 transform){ return null; }
    }
    
    public static class BagRepType extends VisualizationRepType{
	public ArrayList<VisualizationRepType> rep;
	public BagRepType(){
	    rep =  new ArrayList<VisualizationRepType>();
	}
	public BagRepType(int id){
	    super(id);
	    rep =  new ArrayList<VisualizationRepType>();
	}
	
	public ArrayList<IMesh> instantiatePolygon(IMatrix4 transform){
	    if(rep!=null && rep.size()>0){
		ArrayList<IMesh> meshes = new ArrayList<IMesh>();
		for(int i=0; i<rep.size(); i++){
		    ArrayList<IMesh> m = rep.get(i).instantiatePolygon(transform);
		    if(m!=null) meshes.addAll(m);
		}
		return meshes;
	    }
	    return null;
	}
	public ArrayList<ICurve> instantiatePolyline(IMatrix4 transform){
	    if(rep!=null && rep.size()>0){
		ArrayList<ICurve> curves = new ArrayList<ICurve>();
		for(int i=0; i<rep.size(); i++){
		    ArrayList<ICurve> c = rep.get(i).instantiatePolyline(transform);
		    if(c!=null) curves.addAll(c);
		}
		return curves;
	    }
	    return null;
	}
    }
    
    public static class PolygonalRepType extends VisualizationRepType{
	
	public SurfaceAttributesType surfaceAttributes;
	public LineAttributesType lineAttributes;
	
	public ArrayList<FaceSetType> faces;
	public ArrayList<PolylineSetType> edges;
	public VertexBufferType vertexBuffer;
	
	/** flag to renew vertexBuffer vertices to avoid redundunt transformation (if instantiated more than once with same vertices and transformed, it affects all of instances */
	//public boolean vertexBufferUsed = false;
	
	public ArrayList<PolygonalLODType> polygonalLOD;
	
	public double accuracy;
	public boolean solid;
	
	
	public PolygonalRepType(int id){
	    super(id);
	    
	    faces = new ArrayList<FaceSetType>();
	    edges = new ArrayList<PolylineSetType>();
	    polygonalLOD = new ArrayList<PolygonalLODType>();
	}
	
	public PolygonalRepType(){
	    faces = new ArrayList<FaceSetType>();
	    edges = new ArrayList<PolylineSetType>();
	    polygonalLOD = new ArrayList<PolygonalLODType>();
	}
	
	public ArrayList<IMesh> instantiatePolygon(IMatrix4 transform){
	    if(faces!=null && faces.size()>0){
		//if(vertexBufferUsed){ vertexBuffer.renewVertices(); vertexBufferUsed=false; }
		
		// all transformation for IMesh doesn't affect the original vertex set.
		VertexBufferType vertices = new VertexBufferType(vertexBuffer);
		
		vertices.transform(transform);
		
		ArrayList<IMesh> meshes = new ArrayList<IMesh>();
		for(int i=0; i<faces.size(); i++){
		    //ArrayList<IMesh> m = faces.get(i).instantiatePolygon(vertexBuffer);
		    ArrayList<IMesh> m = faces.get(i).instantiatePolygon(vertices);
		    if(m!=null) meshes.addAll(m);
		}
		//vertexBufferUsed = true;
		return meshes;
	    }
	    return null;
	}
	
	// to be implemented
	public ArrayList<ICurve> instantiatePolyline(IMatrix4 transform){ return null; }
	
    }
    
    public static class SurfaceAttributesType{
	public ColorType color;
	public ArrayList<MaterialApplicationType> materialApplication;
    }
    
    public static class LineAttributesType{
	public enum LineTypeType{ SOLID, DOTTED, DASHED, DOT_DASHED, PHANTOM, SMALL_DOTTED, JIS_AXIS }
	
	public ColorType color;
	public LineTypeType lineType = LineTypeType.SOLID;
	public double thickness;
    }
    
    public static class PointAttributesType{
	public enum PointSymbolType{ CROSS, PLUS, CONCENTRIC, COINCIDENT, FULLCIRCLE, FULLSQUARE, STAR, DOT, SMALLDOT }
	public ColorType color;
	public PointSymbolType symbolType = PointSymbolType.DOT;
    }
    
    public static class MaterialApplicationType{
	public enum MappingSideType{ FRONT, BACK, FRONT_AND_BACK }
	public enum TextureBlendFunctionType{ REPLACE, ADD, ALPHA_TRANSPARENCY, LIGHTMAP, BURN, INVERT }
	
	public String id;
	public int mappingChannel;
	public MappingSideType mappingSide;
	public TextureBlendFunctionType blendType;
    }
    
    public static class PolygonalLODType{
    }
    
    public static class ColorType{
	public double red, blue, green, alpha;
    }
    
    public static class FaceSetType{
	public SurfaceAttributesType surfaceAttributes;
	public ArrayList<FaceGPType> faces;
	public FaceSetType(){ faces = new ArrayList<FaceGPType>(); }
	public ArrayList<IMesh> instantiatePolygon(VertexBufferType vtx){
	    ArrayList<IMesh> meshes = new ArrayList<IMesh>();
	    for(int i=0; i<faces.size(); i++){
		IMesh m = faces.get(i).instantiatePolygon(vtx);
		if(m!=null) meshes.add(m);
	    }
	    return meshes;
	}
    }
    public static class PolylineSetType{
	public LineAttributesType lineAttributes;
	public ArrayList<PolylineGPType> polyline;
	public PolylineSetType(){ polyline = new ArrayList<PolylineGPType>(); }
    }
    public static class PolylineGPType{
	public LineAttributesType lineAttributes;
	public String vertices;
    }
    public static class VertexBufferType{
	//public String positions;
	//public String normals;
	public ArrayList<TextureCoordinatesBufferType> textureCoordinates;
	public ColorBufferType diffuseColors;
	public ColorBufferType specularColors;
	
	//public double[][] positionXYZ;
	//public double[][] normalXYZ;
	
	//public IVec[] positions;n
	public IVec[] normals;
	
	public IVertex[] vertices;
	
	public VertexBufferType(){ textureCoordinates = new ArrayList<TextureCoordinatesBufferType>(); }
	
	public VertexBufferType(VertexBufferType vb){
	    
	    textureCoordinates = new ArrayList<TextureCoordinatesBufferType>();
	    if(vb.textureCoordinates!=null){
		for(int i=0; i<vb.textureCoordinates.size(); i++){
		    textureCoordinates.add(new TextureCoordinatesBufferType(vb.textureCoordinates.get(i)));
		}
	    }
	    
	    if(vb.diffuseColors!=null) diffuseColors = new ColorBufferType(vb.diffuseColors);
	    if(vb.specularColors!=null) specularColors = new ColorBufferType(vb.specularColors);
	    
	    if(vb.normals != null){
		normals = new IVec[vb.normals.length];
		for(int i=0; i<vb.normals.length; i++){ normals[i] = new IVec(vb.normals[i]); }
	    }
	    
	    if(vb.vertices!=null){
		vertices = new IVertex[vb.vertices.length];
		for(int i=0; i<vb.vertices.length; i++){ vertices[i] = new IVertex(vb.vertices[i]); }
	    }
	}
	
	public void transform(IMatrix4 m){
	    if(vertices == null || m == null) return;
	    for(int i=0; i<vertices.length; i++) vertices[i].transform(m);
	}
	
	
	/*
	public void renewVertices(){
	    if(vertices==null) return;
	    //IVertex[] vtx = new IVertex[vertices.length];
	    for(int i=0; i<vertices.length; i++){
		//vtx[i] = new IVertex(vertices[i].get().cp());
		IVertex v = new IVertex(vertices[i].get().cp());
		if(vertices[i].normal!=null) v.setNormal(vertices[i].normal.get().cp());
		if(vertices[i].texture!=null) v.texture(vertices[i].texture.dup());
		vertices[i] = v;
	    }
	}
	*/
	
	
	static IVertex[] parseVertex(String str, int dim){
	    String[] xyzStr = str.split("\\s*,\\s*");
	    int len = xyzStr.length;
	    //IVec[] vecs = new IVec[len];
	    IVertex[] vtx = new IVertex[len];
	    for(int i=0; i<len; i++){
		String[] vals= xyzStr[i].split("\\s+");
		if(vals.length>=dim){
		    try{
			double[] xyz = new double[dim];
			for(int j=0; j<dim; j++) xyz[j] = Double.parseDouble(vals[j]);
			vtx[i] = new IVertex(xyz[0], dim>=2?xyz[1]:0, dim>=3?xyz[2]:0);
		    }catch(Exception e){
			IOut.err("wrong format: "+xyzStr[i]);
			e.printStackTrace();
		    }
		}
		else{ IOut.err("wrong format: "+xyzStr[i]); }
	    }
	    return vtx;
	}
	
	
	static IVec[] parseVector(String str, int dim){
	    String[] xyzStr = str.split("\\s*,\\s*");
	    int len = xyzStr.length;
	    IVec[] vecs = new IVec[len];
	    for(int i=0; i<len; i++){
		String[] vals= xyzStr[i].split("\\s+");
		if(vals.length>=dim){
		    try{
			double[] xyz = new double[dim];
			for(int j=0; j<dim; j++) xyz[j] = Double.parseDouble(vals[j]);
			vecs[i] = new IVec(xyz[0], dim>=2?xyz[1]:0, dim>=3?xyz[2]:0);
		    }catch(Exception e){
			IOut.err("wrong format: "+xyzStr[i]);
			e.printStackTrace();
		    }
		}
		else{ IOut.err("wrong format: "+xyzStr[i]); }
	    }
	    return vecs;
	}
	
	
	static double[][] parseXYZ(String str, int dim){
	    String[] xyzStr = str.split("\\s*,\\s*");
	    int len = xyzStr.length;
	    double[][] xyz = new double[len][];
	    for(int i=0; i<len; i++){
		String[] vals= xyzStr[i].split("\\s+");
		if(vals.length>=dim){
		    try{
			xyz[i] = new double[dim];
			for(int j=0; j<dim; j++) xyz[i][j] = Double.parseDouble(vals[j]);
		    }catch(Exception e){
			IOut.err("wrong format: "+xyzStr[i]);
			e.printStackTrace();
		    }
		}
		else{ IOut.err("wrong format: "+xyzStr[i]); }
	    }
	    return xyz;
	}
	
	
	
	public void setPositions(String pos){
	    //positionXYZ = parseXYZ(pos, 3);
	    //positions = parseVertex(pos, 3);
	    vertices = parseVertex(pos, 3);
	    
	    if(normals!=null){
		for(int i=0; i<vertices.length && i<normals.length; i++){
		    vertices[i].setNormal(normals[i]);
		}
	    }
	}
	public void setNormals(String nml){
	    //normalXYZ = parseXYZ(nml, 3);
	    normals = parseVector(nml, 3);

	    if(vertices!=null){
		for(int i=0; i<vertices.length && i<normals.length; i++){
		    vertices[i].setNormal(normals[i]);
		}
	    }
	}
	

	public int getVertexNum(){
	    if(vertices==null) return 0;
	    return vertices.length;
	}
	/*
	public int getPositionNum(){
	    if(positions==null) return 0;
	    return positions.length;
	    //if(positionXYZ==null) return 0;
	    //return positionXYZ.length;
	}
	*/
	public int getNormalNum(){
	    if(normals==null) return 0;
	    return normals.length;
	    //if(normalXYZ==null) return 0;
	    //return normalXYZ.length;
	}
	
	public IVertex getVertex(int i){
	    if(i<0||i>=getVertexNum()) return null;
	    return vertices[i];
	}
	/*
	public IVec getPosition(int i){
	    if(i<0||i>=getPositionNum()) return null;
	    return positions[i];
	}
	*/
	public IVec getNormal(int i){
	    if(i<0||i>=getNormalNum()) return null;
	    return normals[i];
	}
	
    }
    
    public static class ColorBufferType{
	public enum ColorFormatType{ RGB, RGBA }
	public ColorBufferType format;
	public String value;
	
	public ColorBufferType(){}
	public ColorBufferType(ColorBufferType cbt){
	    format = cbt.format;
	    if(cbt.value!=null) value = new String(cbt.value);
	}
    }
    
    public static class TextureCoordinatesBufferType{
	public enum TextureDimensionType { _1D, _2D, _3D }
	public String value;
	public int channel;
	public TextureDimensionType dimension;
	
	public double[][] textureXYZ;
	
	public TextureCoordinatesBufferType(){}
	public TextureCoordinatesBufferType(TextureCoordinatesBufferType t){
	    if(t.value!=null) value = new String(t.value);
	    channel = t.channel;
	    dimension = t.dimension;
	    
	    if(t.textureXYZ!=null){
		textureXYZ = new double[t.textureXYZ.length][];
		for(int i=0; i<t.textureXYZ.length; i++){
		    textureXYZ[i] = new double[t.textureXYZ[i].length];
		    for(int j=0; j<t.textureXYZ[i].length; j++){ textureXYZ[i][j] = t.textureXYZ[i][j]; }
		}
	    }
	}
	
	
	public void setTextureCoordinates(String coordinatesStr){
	    int dim = 0;
	    if(dimension==TextureDimensionType._1D){ dim = 1; }
	    else if(dimension==TextureDimensionType._2D){ dim = 2; }
	    else if(dimension==TextureDimensionType._3D){ dim = 3; }
	    
	    textureXYZ = VertexBufferType.parseXYZ(coordinatesStr, dim);
	}
    }
    
    public static class FaceGPType{
	public SurfaceAttributesType surfaceAttributes;
	public String triangles;
	public String strips;
	public String fans;
	
	public int[] triangleIndices;
	public int[][] stripIndices;
	public int[][] fanIndices;
	
	
	public IMesh mesh;
	
	
	public void setTriangles(String triangles){
	    try{
		String[] divided = triangles.split("\\s+");
		//if(divided!=null)
		triangleIndices = new int[divided.length];
		for(int i=0; i<divided.length; i++){
		    triangleIndices[i] = Integer.parseInt(divided[i]);
		}
		
		/*
		if(divided.length % 3 == 0){
		    int len = divided.length/3;
		    triangleIndices = new int[len][];
		    for(int i=0; i<len; i++){
			triangleIndices[i] = new int[3];
			triangleIndices[i][0] = Integer.parseInt(divided[i*3]);
			triangleIndices[i][1] = Integer.parseInt(divided[i*3+1]);
			triangleIndices[i][2] = Integer.parseInt(divided[i*3+2]); 
		    }
		}
		*/
	    }catch(Exception e){ e.printStackTrace(); }
	    
	    IOut.p(triangleIndices.length + " triangles"); //
	    
	}
	public void setStrips(String strips){
	    try{
		String[] divided = strips.split("\\s*,\\s*");
		int len = divided.length;
		stripIndices = new int[len][];
		for(int i=0; i<len; i++){
		    
		    String[] divided2 = divided[i].split("\\s+");
		    int len2 = divided2.length;
		    stripIndices[i] = new int[len2];
		    
		    for(int j=0; j<len2; j++){
			stripIndices[i][j] = Integer.parseInt(divided2[j]);
		    }
		    
		}
	    }catch(Exception e){ e.printStackTrace(); }
	    
	    IOut.p(stripIndices.length + " strips"); //
	    
	}
	public void setFans(String fans){
	    try{
		String[] divided = fans.split("\\s*,\\s*");
		int len = divided.length;
		fanIndices = new int[len][];
		for(int i=0; i<len; i++){
		    
		    String[] divided2 = divided[i].split("\\s+");
		    int len2 = divided2.length;
		    fanIndices[i] = new int[len2];
		    
		    for(int j=0; j<len2; j++){
			fanIndices[i][j] = Integer.parseInt(divided2[j]);
		    }
		    
		}
	    }catch(Exception e){ e.printStackTrace(); }
	    
	    IOut.p(fanIndices.length + " fans"); //
	    
	}
	
	
	public IMesh instantiatePolygon(VertexBufferType vtx){
	    
	    if(triangleIndices==null && stripIndices==null &&  fanIndices==null) return null;
	    
	    mesh = new IMesh();
	    if(triangleIndices!=null){
		IVertex[] v = new IVertex[triangleIndices.length];
		for(int i=0; i<triangleIndices.length; i++){
		    v[i] = vtx.getVertex(triangleIndices[i]);
		    if(v[i]==null) IOut.err("vertex["+triangleIndices[i]+"] doesn't exist for triangle indices");
		    /*
		    IVec pos = vtx.getPosition(triangleIndices[i]);
		    if(pos!=null){
			v[i] = new IVertex(pos);
			IVec n = vtx.getNormal(triangleIndices[i]);
			if(n!=null){ v[i].setNml(n); }
		    }
		    else{
			IOut.err("vertex["+triangleIndices[i]+"] doesn't exist for triangle indices");
		    }
		    */
		}
		mesh.addTriangles(v);
	    }
	    
	    if(stripIndices!=null){
		for(int i=0; i<stripIndices.length; i++){
		    IVertex[] v = new IVertex[stripIndices[i].length];
		    for(int j=0; j<stripIndices[i].length; j++){
			v[j] = vtx.getVertex(stripIndices[i][j]);
			if(v[j]==null) IOut.err("vertex["+stripIndices[i][j]+"] doesn't exist for strip indices");
			/*
			IVec pos = vtx.getPosition(stripIndices[i][j]);
			if(pos!=null){
			    v[j] = new IVertex(pos);
			    IVec n = vtx.getNormal(stripIndices[i][j]);
			    if(n!=null){ v[j].setNml(n); }
			}
			else{
			    IOut.err("vertex["+stripIndices[i][j]+"] doesn't exist for strip indices");
			}
			*/
		    }
		    mesh.addTriangleStrip(v);
		}
	    }
		    
	    if(fanIndices!=null){
		for(int i=0; i<fanIndices.length; i++){
		    IVertex[] v = new IVertex[fanIndices[i].length];
		    for(int j=0; j<fanIndices[i].length; j++){
			v[j] = vtx.getVertex(fanIndices[i][j]);
			if(v[j]==null) IOut.err("vertex["+fanIndices[i][j]+"] doesn't exist for fan indices");
			/*
			IVec pos = vtx.getPosition(fanIndices[i][j]);
			if(pos!=null){
			    v[j] = new IVertex(pos);
			    IVec n = vtx.getNormal(fanIndices[i][j]);
			    if(n!=null){ v[j].setNml(n); }
			}
			else{
			    IOut.err("vertex["+fanIndices[i][j]+"] doesn't exist for fan indices");
			}
			*/
		    }
		    mesh.addTriangleFan(v);
		}
	    }
	    
	    return mesh;
	}
	
    }

    
    
    public static class RepresentationDocument{
	public String filename;
	public ArrayList<IMesh> instantiatePolygon(IMatrix4 transform){ return null; }
	public ArrayList<ICurve> instantiatePolyline(IMatrix4 transform){ return null; }
    }
    
    // 3DRep document; tessellated XML representation file
    public static class XMLRepresentationDocument extends RepresentationDocument{
	
	public VisualizationRepType root;
	
	public ArrayList<VisualizationRepType> representations;
	
	XMLRepresentationDocument(){
	    representations = new ArrayList<VisualizationRepType>();
	}
	
	public ArrayList<IMesh> instantiatePolygon(IMatrix4 transform){
	    //if(!filename.startsWith("Chassis"))return null; // debug
	    
	    if(root!=null) return root.instantiatePolygon(transform);
	    return null;
	    /*
	    ArrayList<IMesh> meshes = new ArrayList<IMesh>();
	    for(int i=0; i<representations.size(); i++){
		ArrayList<IMesh> m = representations.get(i).instantiatePolygon();
		if(m!=null) meshes.addAll(m);
	    }
	    return meshes;
	    */
	}
	public ArrayList<ICurve> instantiatePolyline(IMatrix4 transform){ return null; }
	
    }
}
