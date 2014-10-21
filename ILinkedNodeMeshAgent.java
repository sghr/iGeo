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

import java.awt.Color;

/**
   Mesh with vertices linked with scalar data and visualized as vertex color
   
   @author Satoru Sugihara
*/

public class ILinkedNodeMeshAgent extends IAgent{
    
    public IMesh mesh;
    public ILinkedScalarAgent[] nodeAgents;
    
    public ILinkedNodeMeshAgent(IMesh m){
	mesh = m;
	initNodes();
    }
    
    public ILinkedScalarAgent node(int i){
	if(i<0 || i>=nodeAgents.length){ return null; }
	return nodeAgents[i];
    }

    public double data(int i){
	return getData(i);
    }
    
    public double getData(int i){
	if(i<0 || i>nodeAgents.length){ return 0; }
	return nodeAgents[i].getData().x();
    }
    
    public ILinkedNodeMeshAgent data(int i, double value){
	return setData(i,value);
    }
	
    public ILinkedNodeMeshAgent setData(int i, double value){
	if(i<0 || i>nodeAgents.length){ return this; }
	nodeAgents[i].setData(new IDouble(value));
	return this;
    }
    
    public ILinkedNodeMeshAgent fric(int i, double fric){
	return setFriction(i,fric);
    }
    
    public ILinkedNodeMeshAgent setFriction(int i, double fric){
	if(i<0 || i>nodeAgents.length){ return this; }
	nodeAgents[i].fric(fric);
	return this;
    }

    public double fric(int i){
	return getFriction(i);
    }
    
    public double getFriction(int i){
	if(i<0 || i>nodeAgents.length){ return 0; }
	return nodeAgents[i].fric();
    }
    
    public void initNodes(){
	nodeAgents = ILinkedScalarAgent.create(mesh);
	for(int i=0; i<mesh.vertexNum(); i++){
	    mesh.vertex(i).clr(new IColor(0,0,0));
	}
    }
    
    public IMesh mesh(){ return mesh; }

    public int vertexNum(){ return mesh.vertexNum(); }

    public int edgeNum(){ return mesh.edgeNum(); }
    
    public int faceNum(){ return mesh.faceNum(); }
    
    
    /*
    public void update(){
	
	for(int i=0; i<mesh.vertexNum() && i<nodeAgents.length; i++){
	    
	    if(i%100==0){
		nodeAgents[i].fric(1);
		nodeAgents[i].setData(new IDouble(1));
	    }
	    
	    //mesh.vertex(i).clr.r((float)nodeAgents[i].getData().x());
	    float val = (float)nodeAgents[i].getData().x();
	    mesh.vertex(i).clr(val, 0, 0);
	    
	    //mesh.vertex(i).clr(IRand.clr());
	    
	    //if(val>0) IG.err("node data = "+val);
	    
	    
	}
    }
    */
    
    /** set vertex color */
    public IColor vertexColor(int i){
	return mesh.vertexColor(i);
    }
    
    public ILinkedNodeMeshAgent vertexColor(int i, IColor c){
	mesh.vertexColor(i,c);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, IColor c, int alpha){
	mesh.vertexColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, IColor c, float alpha){
	mesh.vertexColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, IColor c, double alpha){
	mesh.vertexColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, Color c){
	mesh.vertexColor(i,c);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, Color c, int alpha){
	mesh.vertexColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, Color c, float alpha){
	mesh.vertexColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, Color c, double alpha){
	mesh.vertexColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, int gray){
	mesh.vertexColor(i,gray);
	return this;
    }
    
    public ILinkedNodeMeshAgent vertexColor(int i, double dgray){
	mesh.vertexColor(i,dgray);
	return this;
    }
    
    public ILinkedNodeMeshAgent vertexColor(int i, float fgray){
	mesh.vertexColor(i,fgray);
	return this;
    }
    
    public ILinkedNodeMeshAgent vertexColor(int i, int gray, int alpha){
	mesh.vertexColor(i,gray,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, double dgray, double dalpha){
	mesh.vertexColor(i,dgray,dalpha);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, float fgray, float falpha){
	mesh.vertexColor(i,fgray,falpha);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, int r, int g, int b){
	mesh.vertexColor(i,r,g,b);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, double dr, double dg, double db){
	mesh.vertexColor(i,dr,dg,db);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, float fr, float fg, float fb){
	mesh.vertexColor(i,fr,fg,fb);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, int r, int g, int b, int a){
	mesh.vertexColor(i,r,g,b,a);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, double dr, double dg, double db, double da){
	mesh.vertexColor(i,dr,dg,db,da);
	return this;
    }
    public ILinkedNodeMeshAgent vertexColor(int i, float fr, float fg, float fb, float fa){
	mesh.vertexColor(i,fr,fg,fb,fa);
	return this;
    }
    public ILinkedNodeMeshAgent vertexHSB(int i, double dh, double ds, double db, double da){
	return vertexColorHSB(i,dh,ds,db,da);
    }
    public ILinkedNodeMeshAgent vertexColorHSB(int i, double dh, double ds, double db, double da){
	mesh.vertexColorHSB(i,dh,ds,db,da);
	return this;
    }
    public ILinkedNodeMeshAgent vertexHSB(int i, float h, float s, float b, float a){
	return vertexColorHSB(i,h,s,b,a);
    }
    public ILinkedNodeMeshAgent vertexColorHSB(int i, float h, float s, float b, float a){
	mesh.vertexColorHSB(i,h,s,b,a);
	return this;
    }
    public ILinkedNodeMeshAgent vertexHSB(int i, double dh, double ds, double db){
	return vertexColorHSB(i,dh,ds,db);
    }
    public ILinkedNodeMeshAgent vertexColorHSB(int i, double dh, double ds, double db){
	mesh.vertexColorHSB(i,dh,ds,db);
	return this;
    }
    public ILinkedNodeMeshAgent vertexHSB(int i, float h, float s, float b){
	return vertexColorHSB(i,h,s,b);
    }
    public ILinkedNodeMeshAgent vertexColorHSB(int i, float h, float s, float b){
	mesh.vertexColorHSB(i,h,s,b);
	return this;
    }
    
    
    /** set face color */
    public IColor faceColor(int i){
	return mesh.faceColor(i);
    }
    
    public ILinkedNodeMeshAgent faceColor(int i, IColor c){
	mesh.faceColor(i,c);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, IColor c, int alpha){
	mesh.faceColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, IColor c, float alpha){
	mesh.faceColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, IColor c, double alpha){
	mesh.faceColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, Color c){
	mesh.faceColor(i,c);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, Color c, int alpha){
	mesh.faceColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, Color c, float alpha){
	mesh.faceColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, Color c, double alpha){
	mesh.faceColor(i,c,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, int gray){
	mesh.faceColor(i,gray);
	return this;
    }
    
    public ILinkedNodeMeshAgent faceColor(int i, double dgray){
	mesh.faceColor(i,dgray);
	return this;
    }
    
    public ILinkedNodeMeshAgent faceColor(int i, float fgray){
	mesh.faceColor(i,fgray);
	return this;
    }
    
    public ILinkedNodeMeshAgent faceColor(int i, int gray, int alpha){
	mesh.faceColor(i,gray,alpha);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, double dgray, double dalpha){
	mesh.faceColor(i,dgray,dalpha);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, float fgray, float falpha){
	mesh.faceColor(i,fgray,falpha);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, int r, int g, int b){
	mesh.faceColor(i,r,g,b);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, double dr, double dg, double db){
	mesh.faceColor(i,dr,dg,db);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, float fr, float fg, float fb){
	mesh.faceColor(i,fr,fg,fb);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, int r, int g, int b, int a){
	mesh.faceColor(i,r,g,b,a);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, double dr, double dg, double db, double da){
	mesh.faceColor(i,dr,dg,db,da);
	return this;
    }
    public ILinkedNodeMeshAgent faceColor(int i, float fr, float fg, float fb, float fa){
	mesh.faceColor(i,fr,fg,fb,fa);
	return this;
    }
    public ILinkedNodeMeshAgent faceHSB(int i, double dh, double ds, double db, double da){
	return faceColorHSB(i,dh,ds,db,da);
    }
    public ILinkedNodeMeshAgent faceColorHSB(int i, double dh, double ds, double db, double da){
	mesh.faceColorHSB(i,dh,ds,db,da);
	return this;
    }
    public ILinkedNodeMeshAgent faceHSB(int i, float h, float s, float b, float a){
	return faceColorHSB(i,h,s,b,a);
    }
    public ILinkedNodeMeshAgent faceColorHSB(int i, float h, float s, float b, float a){
	mesh.faceColorHSB(i,h,s,b,a);
	return this;
    }
    public ILinkedNodeMeshAgent faceHSB(int i, double dh, double ds, double db){
	return faceColorHSB(i,dh,ds,db);
    }
    public ILinkedNodeMeshAgent faceColorHSB(int i, double dh, double ds, double db){
	mesh.faceColorHSB(i,dh,ds,db);
	return this;
    }
    public ILinkedNodeMeshAgent faceHSB(int i, float h, float s, float b){
	return faceColorHSB(i,h,s,b);
    }
    public ILinkedNodeMeshAgent faceColorHSB(int i, float h, float s, float b){
	mesh.faceColorHSB(i,h,s,b);
	return this;
    }
    
    
}
