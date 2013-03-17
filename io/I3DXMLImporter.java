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
import java.util.*;
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
   3DXML Importer
      
   @author Satoru Sugihara
*/
public class I3DXMLImporter{

    public static class ZipInputStreamWrapper extends ZipInputStream{
	ZipInputStreamWrapper(InputStream is){ super(is); }
	public void close(){
	    // not close.

	    //IOut.err("close called"); //
	}
	
	public void doClose()throws IOException{ super.close(); } // actually close
	
    }
    
    /*
    public static InputStream zipContentInputStream(File zipFile, String entryName) throws IOException{
	// unzip 
	FileInputStream fis = new FileInputStream(zipFile);
	ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
	ZipEntry entry;
	while( (entry = zis.getNextEntry()) != null){
	    //ignore upper/lower case in file names
	    if(!entry.isDirectory() && entry.getName().toLowerCase().equals(entryName.toLowerCase())){
		return zis;
	    }
	}
	zis.close();
	IOut.err("no such file inside zip file "+zipFile.getName());
	return null;
    }
    */
    
    public static I3DXML read(String filename) throws IOException{
	return read(new File(filename));
    }
    
    public static I3DXML read(File file) throws IOException{
	
	String documentName = file.getName();
	
	//I3DXML document = new I3DXML(file);
	I3DXML document = new I3DXML(documentName);
	
	// unzip with ZipInputStream
	//FileInputStream fis = new FileInputStream(file);
	//ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
	ZipEntry entry;
	
	// unzip with ZipFile
	ZipFile zipfile = new ZipFile(file);
	Enumeration e = zipfile.entries();
	
	//while( (entry = zis.getNextEntry()) != null){
	
	while(e.hasMoreElements()){
	    
	    entry = (ZipEntry)e.nextElement();
	    
	    if(!entry.isDirectory()){ // exclude directory
		
		String entryName = entry.getName();
		document.fileList.add(entryName);
		
		if(entryName.toLowerCase().equals(documentName)){ // main 3dxml file
		    BufferedInputStream is = new BufferedInputStream(zipfile.getInputStream(entry));
		    read3DXML(document, entryName, is);
		    is.close();
		}
		else if(entryName.toLowerCase().equals("CATMaterialRef.3dxml")){ // material ref file
		    //read3DXML(document, entryName, is); 
		}
		else if(entryName.toLowerCase().equals("CATRepImage.3dxml")){ // image file
		    //read3DXML(document, entryName, is); 
		}
		else if(entryName.toLowerCase().endsWith("3dxml")){ // read 3dxml file
		    //read3DXML(document, entryName, is); 
		}
		else if(entryName.toLowerCase().endsWith("3drep")){ // read 3drep file
		    BufferedInputStream is = new BufferedInputStream(zipfile.getInputStream(entry));
		    read3DRep(document, entryName,  is);
		}
		
		/*
		IOut.p("file name : "+entry.getName());
		final int bufSize = 2048;
		byte[] buf = new byte[bufSize];
		if(entry.getName().toUpperCase().endsWith(".3DXML")){
		    int count=0;
		    while( (count=zis.read(buf, 0, bufSize) ) != -1 ){
			System.out.write(buf);
			
		    }   
		}
		*/
	    }
	    else{
		//IOut.p("directory name : "+entry.getName());
	    }
	    
	}
	
	//zis.close();
	
	// read root file
	//read3DXML(document, file, documentName);
	
	IOut.p("number of files in archive : " + document.fileList.size()); //
	
	IOut.p("number of Reference3D : " + document.references.size()); //
	IOut.p("number of Instance3D : " + document.instances.size()); //
	IOut.p("number of ReferenceRep : " + document.referenceReps.size()); //
	IOut.p("number of InstanceRep : " + document.instanceReps.size()); //
	
	IOut.p("number of RepresentationDocument : "+document.representationDocuments.size()); //
	
	
	for(int i=0; i<document.references.size(); i++){
	    IOut.p("reference "+i+": "+document.references.get(i).name);
	}
	for(int i=0; i<document.instances.size(); i++){
	    IOut.p("instance "+i+": "+document.instances.get(i).name);
	}
	for(int i=0; i<document.referenceReps.size(); i++){
	    IOut.p("referenceRep "+i+": "+document.referenceReps.get(i).name);
	}
	for(int i=0; i<document.instanceReps.size(); i++){
	    IOut.p("instanceRep "+i+": "+document.instanceReps.get(i).name);
	}
	
	return document;
    }
    
    
    
    
    
    public static I3DXML read(InputStream is, String filename) throws IOException{
	
	String documentName = filename;
	I3DXML document = new I3DXML(filename);
	
	// unzip with ZipInputStream
	//ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
	ZipInputStreamWrapper zis = new ZipInputStreamWrapper(new BufferedInputStream(is));
	ZipEntry entry;
	
	while( (entry = zis.getNextEntry()) != null){
	    
	    if(!entry.isDirectory()){ // exclude directory
		
		String entryName = entry.getName();
		document.fileList.add(entryName);
		
		if(entryName.toLowerCase().equals(documentName)){ // main 3dxml file
		    IG.err("reading main 3dxml file");
		    
		    read3DXML(document, entryName, zis);
		    
		    IG.err("end reading main 3dxml file");
		    //is.close();
		}
		else if(entryName.toLowerCase().equals("CATMaterialRef.3dxml")){ // material ref file
		    //read3DXML(document, entryName, is); 
		}
		else if(entryName.toLowerCase().equals("CATRepImage.3dxml")){ // image file
		    //read3DXML(document, entryName, is); 
		}
		else if(entryName.toLowerCase().endsWith("3dxml")){ // read 3dxml file
		    //read3DXML(document, entryName, is); 
		}
		else if(entryName.toLowerCase().endsWith("3drep")){ // read 3drep file
		    IOut.debug(10,"reading 3drep file");
		    
		    read3DRep(document, entryName,  zis);
		    
		    IOut.debug(10,"end reading 3drep file");
		}
	    }
	    else{
		//IOut.p("directory name : "+entry.getName());
	    }
	    
	}
	//zis.close();
	// read root file
	//read3DXML(document, file, documentName);
	
	
	
	

	
	IOut.p("number of files in archive : " + document.fileList.size()); //
	
	IOut.p("number of Reference3D : " + document.references.size()); //
	IOut.p("number of Instance3D : " + document.instances.size()); //
	IOut.p("number of ReferenceRep : " + document.referenceReps.size()); //
	IOut.p("number of InstanceRep : " + document.instanceReps.size()); //
	
	IOut.p("number of RepresentationDocument : "+document.representationDocuments.size()); //

	/*
	//debug
	for(int i=0; i<document.representationDocuments.size(); i++){
	    document.representationDocuments.get(i).instantiatePolygon(null);
	}
	*/
	
	/*
	for(int i=0; i<document.representationDocuments.size(); i++){
	    IOut.p("representationDocumenent "+i+": "+document.representationDocuments.get(i).filename);
	    
	    document.representationDocuments.get(i).instantiatePolygon();
	    document.representationDocuments.get(i).instantiatePolyline();
	}
	*/
	
	/*
	for(int i=0; i<document.referenceReps.size(); i++){
	    for(int j=0; j<document.representationDocuments.size(); j++){
		if(document.referenceReps.get(i).associatedFile.equals(document.representationDocuments.get(j).filename)){
		    document.referenceReps.get(i).representationDocument = document.representationDocuments.get(j);
		}
	    }
	    if(document.referenceReps.get(i).representationDocument == null){
		IOut.err("representation file \""+document.referenceReps.get(i).associatedFile+"\" not found");
	    }
	    else{
		IOut.err("representation file \""+document.referenceReps.get(i).associatedFile+"\" FOUND!");
	    }
	}
	for(int i=0; i<document.instances.size(); i++){
	    document.instances.get(i).link(document);
	}
	for(int i=0; i<document.instanceReps.size(); i++){
	    document.instanceReps.get(i).link(document);
	}
	*/
	
	document.linkNodes(); // reference, instance, referenceRep
	
	document.instantiate();
	
	for(int i=0; i<document.references.size(); i++){
	    IOut.p("reference "+i+": "+document.references.get(i).name);
	    
	    for(int j=0; document.references.get(i).components!=null && j<document.references.get(i).components.size(); j++){
		String type;
		
		IOut.p("reference "+i+": component "+j+": "+
		       document.references.get(i).components.get(j).name+
		       " : ("+document.references.get(i).components.get(j).getClass().getName() + ")");
	    }

	    for(int j=0; document.references.get(i).instances!=null && j<document.references.get(i).instances.size(); j++){
		IOut.p("reference "+i+": instance "+j+": "+
		       document.references.get(i).instances.get(j).name);
	    }
	}
	for(int i=0; i<document.instances.size(); i++){
	    IOut.p("instance "+i+": "+document.instances.get(i).name);
	}
	for(int i=0; i<document.referenceReps.size(); i++){
	    IOut.p("referenceRep "+i+": "+document.referenceReps.get(i).name);
	    for(int j=0; document.referenceReps.get(i).instanceReps!=null && j<document.referenceReps.get(i).instanceReps.size(); j++){
		IOut.p("referenceRep "+i+": instanceRep "+j+": "+
		       document.referenceReps.get(i).instanceReps.get(j).name);
	    }
	    
	}
	for(int i=0; i<document.instanceReps.size(); i++){
	    IOut.p("instanceRep "+i+": "+document.instanceReps.get(i).name);
	}
	
	
	
	return document;
    }
    
    
    
    
    /** build a XML node tree and returns its root node */
    public static Node getXMLNode(InputStream is){
	try{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Node root = builder.parse(is);
	    return root;
	}
	catch(Exception e){ e.printStackTrace(); }
	IOut.err("XML document parsing failed");
	return null;
    }
    
    /*
    public static void read3DXML(I3DXML document, File zipfile, String contentFileName) throws IOException{
	InputStream is = zipContentInputStream(zipfile, contentFileName);
	read3DXML(document, is);
	//recursivePrint(root, 0);
	is.close();
    }
    */
    
    public static void read3DXML(I3DXML document, String filename, InputStream is) throws IOException{
	
	IOut.p("reading document: "+filename);
	
	Node root = getXMLNode(is);
	// root document node
	NodeList nodeList = root.getChildNodes();
	if(root.getNodeType()==Node.DOCUMENT_NODE && nodeList.getLength()==1){
	    Node modelNode = nodeList.item(0);
	    readModel3DXML(document, modelNode);
	}
	else{
	    IOut.err("no document node found"); 
	}
    }
    
    
    
    public static void readModel3DXML(I3DXML document, Node node){

	final String elementName = "Model_3dxml";
	
	if(node.getNodeName().equals(elementName)){
	    NodeList nodeList = node.getChildNodes();
	    for(int i=0; i<nodeList.getLength(); i++){
		
		Node child = nodeList.item(i);
		
		if(child.getNodeName().equals("Header")){
		    readHeader(document, child);
		}
		else if(child.getNodeName().equals("ProductStructure")){
		    readProductStructure(document, child);
		}
		else if(child.getNodeName().equals("DefaultView")){
		    readDefaultView(document, child);
		}
		else if(child.getNodeName().equals("CATMaterialRef")){
		    readCATMaterialRef(document, child);
		}
		else if(child.getNodeName().equals("CATRepImage")){
		    readCATRepImage(document, child);
		}
		else if(child.getNodeName().equals("CATMaterial")){
		    readCATMaterial(document, child);
		}
	    }
	}
	else{
	    IOut.err("invalid node for "+elementName +": "+node.getNodeName());
	}
    }
    
    public static void readHeader(I3DXML document, Node node){
	
    }
    
    public static void readProductStructure(I3DXML document, Node node){

	NodeList nodeList = node.getChildNodes();
	for(int i=0; i<nodeList.getLength(); i++){
	    
	    //IOut.p(nodeList.item(i).getNodeName()); //
	    //IOut.p(i);
	    //printNode(nodeList.item(i), 0);
	    
	    Node child = nodeList.item(i);
	    
	    
	    if(child.getNodeName().equals("Reference3D")){
		readReference3D(document, child);
	    }
	    else if(child.getNodeName().equals("Instance3D")){
		readInstance3D(document, child);
	    }
	    else if(child.getNodeName().equals("ReferenceRep")){
		readReferenceRep(document, child);
	    }
	    else if(child.getNodeName().equals("InstanceRep")){
		readInstanceRep(document, child);
	    }
	}
    }
    
    public static void readReference3D(I3DXML document, Node node){
	IOut.p(); //
	//printNode(node, 2); //
	
	try{
	    int id = getIntegerAttribute(node, "id");
	    String name = getAttribute(node, "name");
	    
	    IOut.p("id = "+id);
	    IOut.p("name = "+name);
	    
	    I3DXML.Reference3D ref = new I3DXML.Reference3D(id, name);
	    
	    document.references.add(ref);
	    
	}catch(Exception e){ e.printStackTrace(); }
	
    }
    
    public static void readInstance3D(I3DXML document, Node node){
	IOut.p(); //
	//printNode(node, 2); //
	
	try{
	    int id = getIntegerAttribute(node, "id");
	    String name = getAttribute(node, "name");
	    
	    IOut.p("id = "+id);
	    IOut.p("name = "+name);
	    
	    I3DXML.Instance3D instance = new I3DXML.Instance3D(id, name);
	    
	    //recursivePrint(node, 5); //
	    
	    NodeList nodeList = node.getChildNodes();
	    for(int i=0; i<nodeList.getLength(); i++){
		Node child = nodeList.item(i);
		
		try{
		    if(child.getNodeName().equals("IsAggregatedBy")){
			instance.aggregatedBy = getElementIntegerValue(child);
			
			IOut.p("aggregatedBy = "+instance.aggregatedBy); //
		    }
		    else if(child.getNodeName().equals("IsInstanceOf")){
			instance.instanceOf = getElementIntegerValue(child);
			
			IOut.p("instanceOf = "+instance.instanceOf); //
		    }
		    else if(child.getNodeName().equals("RelativeMatrix")){
			instance.relativeMatrix = getMatrix4x3(child);
			
			//IOut.p("relativeMatrix = "+instance.relativeMatrix); //
			IOut.p(name + ": relativeMatrix = \n"+instance.relativeMatrix); //
		    }
		    
		}catch(Exception e){ e.printStackTrace(); }
	    }
	    
	    document.instances.add(instance);
	}catch(Exception e){ e.printStackTrace(); }
	
    }
    
    public static void readReferenceRep(I3DXML document, Node node){
	IOut.p(); //
	//printNode(node, 2); //
	
	try{
	    int id = getIntegerAttribute(node, "id");
	    String name = getAttribute(node, "name");
	    
	    IOut.p("id = "+id);
	    IOut.p("name = "+name);
	    
	    I3DXML.ReferenceRep refRep = new I3DXML.ReferenceRep(id, name);
	    
	    // format
	    String format = getAttribute(node, "format");
	    if(format.equals("UVR")){
		refRep.format = I3DXML.ReferenceRep.FormatType.UVR;
		
		IOut.p("format = UVR");
	    }
	    else if(format.equals("TECHREP")){
		refRep.format = I3DXML.ReferenceRep.FormatType.TECHREP;
		
		IOut.p("format = TECHREP");
	    }
	    else if(format.equals("TESSELLATED")){
		refRep.format = I3DXML.ReferenceRep.FormatType.TESSELLATED;
		
		IOut.p("format = TESSELLATED");
	    }
	    else if(format.equals("BEHAVIORAL")){
		refRep.format = I3DXML.ReferenceRep.FormatType.BEHAVIORAL;
		
		IOut.p("format = BEHAVIORAL");
	    }
	    else if(format.equals("*")){
		refRep.format = I3DXML.ReferenceRep.FormatType.OTHER;

		IOut.p("format = *");
	    }
	    else{
		refRep.format = I3DXML.ReferenceRep.FormatType.OTHER; // default
		
		IOut.p("format = *");
	    }
	    
	    // associatedFile
	    refRep.associatedURN = getAttribute(node, "associatedFile");
	    
	    String[] split = refRep.associatedURN.split(":");
	    
	    refRep.associatedFile = split[split.length-1];
	    
	    IOut.p("associatedFile = "+refRep.associatedFile);
	    
	    //
	    //readAssociatedFile(document, refRep);
	    
	    document.referenceReps.add(refRep);
	    
	}catch(Exception e){ e.printStackTrace(); }
    }
    
    
    public static void readAssociatedFile(I3DXML document, I3DXML.ReferenceRep referenceRep){
	if(referenceRep!=null && referenceRep.associatedFile!=null){
	    String[] split = referenceRep.associatedFile.split(":");
	    String filename = split[split.length-1];
	    if(filename.toLowerCase().endsWith("3drep")){
		/*
		try{
		    read3DRep(document, referenceRep, document.archiveFile, filename);
		}catch(IOException e){ e.printStackTrace(); }
		*/
	    }
	}
    }
    
    
    public static void readInstanceRep(I3DXML document, Node node){
	IOut.p(); //
	//printNode(node, 2); //
	
	try{
	    int id = getIntegerAttribute(node, "id");
	    String name = getAttribute(node, "name");
	    
	    IOut.p("id = "+id);
	    IOut.p("name = "+name);
	    
	    I3DXML.InstanceRep instanceRep = new I3DXML.InstanceRep(id, name);
	    
	    NodeList nodeList = node.getChildNodes();
	    for(int i=0; i<nodeList.getLength(); i++){
		Node child = nodeList.item(i);
		try{
		    if(child.getNodeName().equals("IsAggregatedBy")){
			instanceRep.aggregatedBy = getElementIntegerValue(child);
			
			IOut.p("aggregatedBy = "+instanceRep.aggregatedBy); //
		    }
		    else if(child.getNodeName().equals("IsInstanceOf")){
			instanceRep.instanceOf = getElementIntegerValue(child);
			
			IOut.p("instanceOf = "+instanceRep.instanceOf); //
		    }
		}catch(Exception e){ e.printStackTrace(); }
	    }
	    
	    
	    document.instanceReps.add(instanceRep);
	    
	}catch(Exception e){ e.printStackTrace(); }
	
    }
    
    public static void readDefaultView(I3DXML document, Node node){
	
    }
    
    public static void readCATMaterialRef(I3DXML document, Node node){
	
    }
    
    public static void readCATMaterial(I3DXML document, Node node){
	
    }
    
    public static void readCATRepImage(I3DXML document, Node node){
	
    }
    
    /*
    public static void read3DRep(I3DXML document, I3DXML.ReferenceRep refRep,
				 File zipfile, String contentFileName) throws IOException{
	InputStream is = zipContentInputStream(zipfile, contentFileName);
	read3DRep(document, zipfile.getName(), is);
	is.close();
    }
    */
    
    
    public static void read3DRep(I3DXML document, String filename, InputStream is) throws IOException{
	
	IOut.debug(10,"reading document: "+filename);
	
	Node root = getXMLNode(is);
	if(root==null){
	    IOut.err("reading "+filename+" failed");
	    return;
	}
	
	//System.out.println(); //
	//IOut.p(); //
	
	// root document node
	NodeList nodeList = root.getChildNodes();
	if(root.getNodeType()==Node.DOCUMENT_NODE && nodeList.getLength()==1){
	    if(nodeList.item(0).getNodeName().equals("XMLRepresentation")){
		
		I3DXML.XMLRepresentationDocument rep = new I3DXML.XMLRepresentationDocument();
		rep.filename = filename;
		document.representationDocuments.add(rep);
		
		readXMLRepresentation(rep, nodeList.item(0));
	    }
	}
	else{
	    IOut.err("no document node found"); 
	}
	
    }
    
    
    public static void readXMLRepresentation(I3DXML.XMLRepresentationDocument rep, Node node){
	
	//IOut.p(node.getNodeName());
	
	NodeList nodeList = node.getChildNodes();
	for(int i=0; i<nodeList.getLength(); i++){
	    if(nodeList.item(i).getNodeName().equals("Root")){
		rep.root = readVisualizationRepType(rep, nodeList.item(i));
	    }
	}
	//recursivePrint(node, 0);
    }
    
    public static I3DXML.VisualizationRepType readVisualizationRepType(I3DXML.XMLRepresentationDocument document, Node node){
	
	//String node.getType
	
	String typeName = getAttribute(node, "xsi:type");
	if(typeName!=null){
	    
	    if(typeName.equals("VisualizationRepType")){
		int id=-1;
		try{
		    id = getIntegerAttribute(node, "id");
		}catch(Exception e){} // possible to have no id
		I3DXML.VisualizationRepType rep = new I3DXML.VisualizationRepType(id);
		document.representations.add(rep);
		return rep;
	    }
	    else if(typeName.equals("BagRepType")){
		int id=-1;
		try{
		    id = getIntegerAttribute(node, "id");
		}catch(Exception e){} // possible to have no id
		
		I3DXML.BagRepType rep = new I3DXML.BagRepType(id);
		document.representations.add(rep);
		
		IOut.p("BagType : id : "+rep.id); //
		
		NodeList nodeList = node.getChildNodes();
		for(int i=0; i<nodeList.getLength(); i++){
		    
		    if(nodeList.item(i).getNodeName().equals("Rep")){
			I3DXML.VisualizationRepType childRep =
			    readVisualizationRepType(document, nodeList.item(i));
			if(childRep!=null){
			    rep.rep.add(childRep);
			}
			else{
			    IOut.err("invalid child representation for BagRepType"); //
			}
		    }
		}
		return rep;
	    }
	    else if(typeName.equals("PolygonalRepType")){
		return readPolygonalRepType(document, node);
	    }
	    else{
		IOut.err("unknown type : "+typeName); //
	    }
	}
	else{ IOut.err("no type for VisualizationRepType in "+node.getNodeName()); }
	
	return null;
    }
    
    
    public static I3DXML.PolygonalRepType readPolygonalRepType(I3DXML.XMLRepresentationDocument document, Node node){
	int id=-1;
	try{
	    id = getIntegerAttribute(node, "id");
	}catch(Exception e){ e.printStackTrace(); }
	
	I3DXML.PolygonalRepType rep = new I3DXML.PolygonalRepType(id);
	document.representations.add(rep);
	
	try{
	    rep.solid = getBooleanAttribute(node, "solid");
	}catch(Exception e){}
	
	try{
	    rep.accuracy = getDoubleAttribute(node, "accuracy");
	}catch(Exception e){}
	
	NodeList nodeList = node.getChildNodes();
	for(int i=0; i<nodeList.getLength(); i++){
	    
	    String nodeName = nodeList.item(i).getNodeName();
	    if(nodeName!=null){
		if(nodeName.equals("SurfaceAttributes")){
		}
		else if(nodeName.equals("LineAttributes")){
		}
		else if(nodeName.equals("PolygonalLOD")){
		}
		else if(nodeName.equals("Faces")){
		    I3DXML.FaceSetType faces = readFaceSet(nodeList.item(i));
		    if(faces!=null){ rep.faces.add(faces); }
		}
		else if(nodeName.equals("Edges")){
		    I3DXML.PolylineSetType edges = readPolylineSet(nodeList.item(i));
		    if(edges!=null){ rep.edges.add(edges); }
		}
		else if(nodeName.equals("VertexBuffer")){
		    I3DXML.VertexBufferType vertices = readVertexBuffer(nodeList.item(i));
		    if(vertices!=null){ rep.vertexBuffer = vertices; }
		}
	    }
	}
	IOut.p("PolygonalRepType : id : "+rep.id); //

	/*
	// ///////////////////////////////////////////////////////////
	// instantiating mesh (test)
	// ///////////////////////////////////////////////////////////
	if(rep.faces!=null && rep.vertexBuffer!=null){
	    for(int i=0; i<rep.faces.size(); i++){
		IOut.p("instantiate mesh "+i);
		//rep.faces.get(i).instantiateMesh(rep.vertexBuffer);
		rep.instantiatePolygon();
	    }
	}
	*/
	
	return rep;
    }
    
    public static I3DXML.FaceSetType readFaceSet(Node node){
	
	I3DXML.FaceSetType faces = new I3DXML.FaceSetType();
	
	NodeList nodeList = node.getChildNodes();
	for(int i=0; i<nodeList.getLength(); i++){
	    
	    String nodeName = nodeList.item(i).getNodeName();
	    if(nodeName!=null){
		if(nodeName.equals("SurfaceAttributes")){
		    
		}
		else if(nodeName.equals("Face")){
		    I3DXML.FaceGPType face = readFaceGP(nodeList.item(i));
		    if(face!=null){
			faces.faces.add(face);
		    }
		}
	    }
	}
	//IOut.p();
	return faces;
    }
    
    public static I3DXML.FaceGPType readFaceGP(Node node){
	
	I3DXML.FaceGPType face = new I3DXML.FaceGPType();
	
	String triangleStr = getAttribute(node, "triangles");
	String stripStr = getAttribute(node, "strips");
	String fanStr = getAttribute(node, "fans");
	
	if(triangleStr!=null) face.setTriangles(triangleStr);
	if(stripStr!=null) face.setStrips(stripStr);
	if(fanStr!=null) face.setFans(fanStr);
	
	//face.triangles = triangleStr;
	//face.strips = stripStr;
	//face.fans = fanStr;
	
	//IOut.p("triangles = "+triangleStr);
	//IOut.p("strips = "+stripStr);
	//IOut.p("fans = "+fanStr);
	
	NodeList nodeList = node.getChildNodes();
	for(int i=0; i<nodeList.getLength(); i++){
	    
	    String nodeName = nodeList.item(i).getNodeName();
	    if(nodeName!=null){
		if(nodeName.equals("SurfaceAttributes")){
		    
		}
	    }
	}
	return face;
    }
    
    public static I3DXML.PolylineSetType readPolylineSet(Node node){
	
	I3DXML.PolylineSetType polylines = new I3DXML.PolylineSetType();
	NodeList nodeList = node.getChildNodes();
	for(int i=0; i<nodeList.getLength(); i++){
	    
	    String nodeName = nodeList.item(i).getNodeName();
	    if(nodeName!=null){
		if(nodeName.equals("LineAttributes")){
		    
		}
		else if(nodeName.equals("Polyline")){
		    I3DXML.PolylineGPType pl = readPolylineGP(nodeList.item(i));
		    if(pl!=null){
			polylines.polyline.add(pl);
		    }
		}
	    }
	}
	//IOut.p();
	return polylines;
    }
    
    public static I3DXML.PolylineGPType readPolylineGP(Node node){
	
	I3DXML.PolylineGPType polyline = new I3DXML.PolylineGPType();
	
	String verticesStr = getAttribute(node, "vertices");
	
	polyline.vertices = verticesStr;
	
	NodeList nodeList = node.getChildNodes();
	for(int i=0; i<nodeList.getLength(); i++){
	    String nodeName = nodeList.item(i).getNodeName();
	    if(nodeName!=null){
		if(nodeName.equals("LineAttributes")){
		    
		}
	    }
	}
	return polyline;
    }
    
    
    public static I3DXML.VertexBufferType readVertexBuffer(Node node){
	
	I3DXML.VertexBufferType vertices = new I3DXML.VertexBufferType();
	
	NodeList nodeList = node.getChildNodes();
	for(int i=0; i<nodeList.getLength(); i++){
	    String nodeName = nodeList.item(i).getNodeName();
	    if(nodeName!=null){
		if(nodeName.equals("Positions")){
		    Node subnode = nodeList.item(i).getFirstChild();
		    if(subnode!=null){
			//vertices.positions = subnode.getNodeValue();
			String posStr = subnode.getNodeValue();
			vertices.setPositions(posStr);
 			//IOut.p("positions : "+vertices.positions);
			IOut.p("vertices num = "+vertices.getVertexNum());
		    }
		}
		else if(nodeName.equals("Normals")){
		    Node subnode = nodeList.item(i).getFirstChild();
		    if(subnode!=null){
			String nmlStr = subnode.getNodeValue();
			vertices.setNormals(nmlStr);
			//IOut.p("normals : "+vertices.normals);
		    }
		}
		else if(nodeName.equals("TextureCoordinates")){
		    I3DXML.TextureCoordinatesBufferType texture =
			readTextureCoordinatesBuffer(nodeList.item(i));
		    vertices.textureCoordinates.add(texture);
		}
		else if(nodeName.equals("DiffuseColors")){
		    // ...
		}
		else if(nodeName.equals("SpecularColors")){
		    // ...
		}
	    }
	}
	
	//IOut.p();
	return vertices;
    }

    public static I3DXML.TextureCoordinatesBufferType readTextureCoordinatesBuffer(Node node){
	I3DXML.TextureCoordinatesBufferType texture = new I3DXML.TextureCoordinatesBufferType();
	
	try{
	    int channel = getIntegerAttribute(node, "channel");
	    texture.channel = channel;
	}catch(Exception e){}
	
	String dimension = getAttribute(node, "dimension");
	if(dimension!=null){
	    if(dimension.equals("1D")){
		texture.dimension = I3DXML.TextureCoordinatesBufferType.TextureDimensionType._1D;
	    }
	    else if(dimension.equals("2D")){
		texture.dimension = I3DXML.TextureCoordinatesBufferType.TextureDimensionType._2D;
	    }
	    else if(dimension.equals("3D")){
		texture.dimension = I3DXML.TextureCoordinatesBufferType.TextureDimensionType._3D;
	    }
	    
	}
	
	//texture.value = node.getFirstChild().getNodeValue();

	Node child = node.getFirstChild();
	if(child!=null){
	    String coordinatesStr = child.getNodeValue();
	    
	    texture.setTextureCoordinates(coordinatesStr);
	    //IOut.p("texture = "+texture.value);

	    IOut.p("texture coordinates num = "+texture.textureXYZ.length); //
	}
	
	return texture;
    }
    
    
    public static String getAttribute(Node node, String attributeName){
	NamedNodeMap attrs = node.getAttributes();
	if(attrs!=null){
	    Node attr = attrs.getNamedItem(attributeName);
	    if(attr!=null) return attr.getNodeValue();
	}
	return null;
    }
    
    public static int getIntegerAttribute(Node node, String attributeName) throws NumberFormatException{
	String value = getAttribute(node,attributeName);
	if(value==null){
	    //IOut.err("no such attributes : "+attributeName); //
	    //IOut.err("node : "+node); //
	    throw new NumberFormatException();
	}
	return Integer.parseInt(value);
    }
    
    public static double getDoubleAttribute(Node node, String attributeName) throws NumberFormatException{
	String value = getAttribute(node,attributeName);
	if(value==null){
	    //IOut.err("no such attributes : "+attributeName); //
	    //IOut.err("node : "+node); //
	    throw new NumberFormatException();
	}
	return Double.parseDouble(value);
    }
    
    public static boolean getBooleanAttribute(Node node, String attributeName) throws NumberFormatException{
	String value = getAttribute(node,attributeName);
	if(value==null){
	    //IOut.err("no such attributes : "+attributeName); //
	    //IOut.err("node : "+node); //
	    throw new NumberFormatException();
	}
	if(value.equals("0") || value.toLowerCase().equals("false") ) return false; 
	if(value.equals("1") || value.toLowerCase().equals("true") ) return true; 
	
	throw new NumberFormatException();
    }
    
    public static String getElementValue(Node node){
	Node valueNode = node.getFirstChild();
	if(valueNode!=null){ return valueNode.getNodeValue(); }
	return null;
    }
    
    public static int getElementIntegerValue(Node node) throws NumberFormatException{
	String value = getElementValue(node);
	if(value==null){ throw new NumberFormatException(); }
	return Integer.parseInt(value);
    }
    
    public static I3DXML.Matrix4x3 getMatrix4x3(Node node) throws NumberFormatException{
	String values = getElementValue(node);
	String[] array = values.split("\\s+");
	if(array.length<12){ throw new NumberFormatException(); }
	double[] vals = new double[12];
	for(int i=0; i<12; i++){ vals[i] = Double.parseDouble(array[i]); }
	return new I3DXML.Matrix4x3(vals);
    }
    
    
    public static void printNode(Node node, int indentDepth){
	String indent = "  ";
	for(int i=0; i<indentDepth; i++) System.out.print(indent); //
	
	String typeName = null;
	short type = node.getNodeType();
	switch(type){
	case Node.ELEMENT_NODE: typeName = "ELEMENT_NODE"; break;
	case Node.ATTRIBUTE_NODE: typeName = "ATTRIBUTE_NODE"; break;
	case Node.TEXT_NODE: typeName = "TEXT_NODE"; break;
	case Node.CDATA_SECTION_NODE: typeName = "CDATA_SECTION_NODE"; break;
	case Node.ENTITY_REFERENCE_NODE: typeName = "ENTITY_REFERENCE_NODE"; break;
	case Node.ENTITY_NODE: typeName = "ENTITY_NODE"; break;
	case Node.PROCESSING_INSTRUCTION_NODE: typeName = "PROCESSING_INSTRUCTION_NODE"; break;
	case Node.COMMENT_NODE: typeName = "COMMENT_NODE"; break;
	case Node.DOCUMENT_NODE: typeName = "DOCUMENT_NODE"; break;
	case Node.DOCUMENT_TYPE_NODE: typeName = "DOCUMENT_TYPE_NODE"; break;
	case Node.DOCUMENT_FRAGMENT_NODE: typeName = "DOCUMENT_FRAGMENT_NODE"; break;
	case Node.NOTATION_NODE: typeName = "NOTATION_NODE"; break;
	default: typeName = "UNKNOWN("+String.valueOf(type)+")";
	}
	
	//System.out.println("node type: "+node.getNodeType());
	System.out.println("node type: "+typeName);
	
	if(type != Node.TEXT_NODE && type != Node.DOCUMENT_NODE ){
	    for(int i=0; i<indentDepth; i++) System.out.print(indent); //
	    System.out.println("node name: "+node.getNodeName());
	}
	
	if(node.hasAttributes()){
	    for(int i=0; i<indentDepth; i++) System.out.print(indent); //
	    System.out.println("node attr:"); 
	    
	    NamedNodeMap attr = node.getAttributes();
	    for(int i=0; i<attr.getLength(); i++){
		
		//recursivePrint(attr.item(i), indentDepth+1);
		printNode(attr.item(i), indentDepth+1);
		
	    }
	}
	
	String value = node.getNodeValue();
	if(value!=null){
	    if(value.length()>100){ value = value.substring(0,100); }
	    for(int i=0; i<indentDepth; i++) System.out.print(indent); //
	    System.out.println("node valu: "+value);
	}
	//System.out.println("node cont: "+node.getTextContent());
	
    }
    
    public static void recursivePrint(Node node, int depth){
	//if(node.getNodeType()==Node.TEXT_NODE) return;

	printNode(node, depth);
	
	NodeList children = node.getChildNodes();
	//IG.p(children.getLength()+" children"); //
	for(int i=0; i<children.getLength(); i++){
	    Node child = children.item(i);
	    recursivePrint(child, depth+1);
	}
	
    }
    
    
    public static void main(String[] args){
	
	String filename = null;
	
	if(args.length>0){ filename = args[0]; }
	
	if(filename==null){ return; }
	
	
	//IG.debugLevel(100);
	IG.init();
	
	
	try{
	    
	    read(filename);
	    
	    
	    /*
	    readZip(filename);
	    
	    if(false){
		
	    DocumentBuilderFactory factory =
		DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Node root = builder.parse(new File(filename));
	    
	    I3DXMLImporter importer = new I3DXMLImporter();
	    importer.trace(root);
	    
	    IG.p("createMesh");
	    importer.createMesh();
	    IG.p("createMesh done");
	    
	    //IG.save("c:/users/s.sugihara.Morphosis/Documents/java/data/FacadeTest_Surface.3dm"); //
	    IG.save("c:/users/s.sugihara.Morphosis/Documents/java/data/FacadeTest_Surface.obj"); //
	    */
	    
	    /*
	    if (root.getNodeType() == Node.DOCUMENT_NODE) {
		System.out.println("Root is Document!");
	    }
	    recursivePrint(root, 0);
	    */
	    
	    
	    /*
	    Node child = root.getFirstChild();
 	    System.out.println("child node name: "+child.getNodeName());
	    System.out.println("child node valu: "+child.getNodeValue());
	    
	    NamedNodeMap attributes = child.getAttributes();
	    
	    
	    NodeList gchildren = child.getChildNodes();
	    for (int i = 0; i < gchildren.getLength(); i++) {
		Node gchild = gchildren.item(i);
		System.out.println("Name: " + gchild.getNodeName());
	    }
	    */

	    //}
	}catch(Exception e){
	    e.printStackTrace();
	}
	
	
	
	/*
	String packageName = "com._3ds.xsd._3dxml";

	
	Model3Dxml model = null;
	try{
	    JAXBContext context =
		JAXBContext.newInstance(packageName);
	    
	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    
	    model = (Model3Dxml)unmarshaller.unmarshal(new File(filename));
	    
	}catch(JAXBException e){
	    e.printStackTrace();
	}
	*/
    }
    
}
