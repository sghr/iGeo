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

import java.util.ArrayList;

/**
   create polygon mesh as sampled interface of equal intensity of fields
   
   @author Satoru Sugihara
*/

public class IFieldMesh{
    
    /** sample field at the given intensity and create polygon mesh */
    public static IMesh create(double intensity, IVecI minPos, IVecI maxPos,
			       int xnum, int ynum, int znum){
	return create(IG.cur(), intensity, minPos, maxPos, xnum, ynum, znum);
    }
    /** sample field at the given intensity and create polygon mesh */
    public static IMesh create(IG ig, double intensity, IVecI minPos, IVecI maxPos,
			       int xnum, int ynum, int znum){
	
	ArrayList<IFieldI> fields = new ArrayList<IFieldI>();
	
	for(int i=0; i<ig.dynamicServer().dynamics.size(); i++){
	    if(ig.dynamicServer().dynamics.get(i) instanceof IFieldI){
		fields.add((IFieldI)ig.dynamicServer().dynamics.get(i));
	    }
	}
	return create(fields, intensity, minPos, maxPos, xnum, ynum, znum);
    }
    /** sample field at the given intensity and create polygon mesh */
    public static IMesh create(ArrayList<IFieldI> fields,
			       double intensity, IVecI minPos, IVecI maxPos,
			       int xnum, int ynum, int znum){
	
	double[][][] intensities = new double[xnum+1][ynum+1][znum+1];
	//IVertex[][][] xpt = new IVertex[xnum][ynum+1][znum+1];
	//IVertex[][][] ypt = new IVertex[xnum+1][ynum][znum+1];
	//IVertex[][][] zpt = new IVertex[xnum+1][ynum+1][znum];
	
	VertexGrid grid = new VertexGrid(xnum,ynum,znum);
	
	IMeshGeo meshGeo = new IMeshGeo();
	
	IVec dif = maxPos.get().dif(minPos);
	dif.x /= xnum;
	dif.y /= ynum;
	dif.z /= znum;
	
	grid.setGridPosition(minPos.get(), dif.x, dif.y, dif.z);
	
	
	for(int i=0; i<=xnum; i++){
	    double x = dif.x*i + minPos.x();
	    for(int j=0; j<=ynum; j++){
		double y = dif.y*j + minPos.y();
		for(int k=0; k<=znum; k++){
		    double z = dif.z*k + minPos.z();
		    
		    IVec pos = new IVec(x,y,z);
		    
		    for(int l=0; l<fields.size(); l++){
			
			// remove the case when pos is exactly at the center of attractor
			if(fields.get(l) instanceof IAttractor &&
			   ((IAttractorGeo)((IAttractor)fields.get(l)).field).pos.eq(pos)
			   && ((IAttractorGeo)((IAttractor)fields.get(l)).field).constantIntensity // sure?
			   ){
			    intensities[i][j][k] +=fields.get(l).intensity();
			}
			else{
			    
			    IVal val = fields.get(l).get(pos);
			    
			    if(val instanceof IVecOp){
				IVec v = ((IVecOp)val).get();
				intensities[i][j][k] += v.len();
			    }
			    else if(val instanceof IDoubleOp){
				double v = ((IDoubleOp)val).x();
				intensities[i][j][k] += v;
			    }
			    else if(val instanceof IIntegerOp){
				int v = ((IIntegerOp)val).x();
				intensities[i][j][k] += v;
			    }
			    
			}
		    }
		    
		    
		    //new IPoint(pos).clr(0,0,1.).size(2); //
		    
		    if((i*ynum*znum+j*znum+k)%200==0){ // not all
			IOut.debug(10, "intensity("+i+","+j+","+k+") = "+intensities[i][j][k]); //
		    }
		    
		    grid.index(i,j,k);
		    
		    double idif = intensities[i][j][k] - intensity;
		    if(idif==0){ // exact value at the corner
			grid.corner(pos.cp());
		    }
		    //grid.corner(pos.cp());
		    
		    if(i>0){
			double xdif = intensities[i-1][j][k] - intensity;
			if( xdif*idif < 0 ){ // crossing
			    IVec pt = pos.cp();
			    pt.x -= Math.abs(idif)/(Math.abs(xdif)+Math.abs(idif))*dif.x;
			    //xpt[i-1][j][k] = new IVertex(pt);
			    grid.x(pt);
			    
			    // debug
			    //new IPoint(pt).clr(1.0,0,0);
			}
		    }
		    if(j>0){
			double ydif = intensities[i][j-1][k] - intensity;
			if( ydif*idif < 0 ){ // crossing
			    IVec pt = pos.cp();
			    pt.y -= Math.abs(idif)/(Math.abs(ydif)+Math.abs(idif))*dif.y;
			    //ypt[i][j-1][k] = new IVertex(pt);
			    grid.y(pt);
			    
			    // debug
			    //new IPoint(pt).clr(1.0,0,0);
			}
		    }
		    if(k>0){
			double zdif = intensities[i][j][k-1] - intensity;
			if( zdif*idif < 0 ){ // crossing
			    IVec pt = pos.cp();
			    pt.z -= Math.abs(idif)/(Math.abs(zdif)+Math.abs(idif))*dif.z;
			    //zpt[i][j][k-1] = new IVertex(pt);
			    grid.z(pt);
			    
			    // debug
			    //new IPoint(pt).clr(1.0,0,0);
			}
		    }

		    if(i>0 && j>0){
			if( (i+j+k)%2==0 ){
			    double xydif = intensities[i-1][j-1][k] - intensity;
			    if( xydif*idif < 0 ){ // crossing
				IVec pt = pos.cp();
				pt.x -= Math.abs(idif)/(Math.abs(xydif)+Math.abs(idif))*dif.x;
				pt.y -= Math.abs(idif)/(Math.abs(xydif)+Math.abs(idif))*dif.y;
				grid.xy(pt);
				
				// debug
				//new IPoint(pt).clr(1.0,0,0);
				//new ICurve(pos.cp(), pos.cp().sub(dif.x, dif.y,0)).clr(1.,0,1.);
			    }
			}
			else{
			    double xydif1 = intensities[i][j-1][k] - intensity;
			    double xydif2 = intensities[i-1][j][k] - intensity;
			    if( xydif1*xydif2 < 0 ){ // crossing
				IVec pt1 = pos.cp();
				IVec pt2 = pos.cp();
				pt1.y -= dif.y;
				pt2.x -= dif.x;
				
				IVec pt = pt1.sum(pt2, Math.abs(xydif1)/(Math.abs(xydif1)+Math.abs(xydif2)));
				grid.xy(pt);
				
				// debug
				//new IPoint(pt).clr(1.0,0,0);
				//new ICurve(pt1, pt2).clr(1.,0,1.);
			    }
			}
		    }
		    
		    
		    if(j>0 && k>0){
			if( (i+j+k)%2==0 ){
			    double yzdif = intensities[i][j-1][k-1] - intensity;
			    if( yzdif*idif < 0 ){ // crossing
				IVec pt = pos.cp();
				pt.y -= Math.abs(idif)/(Math.abs(yzdif)+Math.abs(idif))*dif.y;
				pt.z -= Math.abs(idif)/(Math.abs(yzdif)+Math.abs(idif))*dif.z;
				grid.yz(pt);
				
				// debug
				//new IPoint(pt).clr(1.0,0,0);
				//new ICurve(pos.cp(), pos.cp().sub(0,dif.y, dif.z)).clr(1.,0,1.);
			    }
			}
			else{
			    double yzdif1 = intensities[i][j][k-1] - intensity;
			    double yzdif2 = intensities[i][j-1][k] - intensity;
			    if( yzdif1*yzdif2 < 0 ){ // crossing
				IVec pt1 = pos.cp();
				IVec pt2 = pos.cp();
				pt1.z -= dif.z;
				pt2.y -= dif.y;
				
				IVec pt = pt1.sum(pt2, Math.abs(yzdif1)/(Math.abs(yzdif1)+Math.abs(yzdif2)));
				grid.yz(pt);
				
				// debug
				//new IPoint(pt).clr(1.0,0,0);
				//new ICurve(pt1, pt2).clr(1.,0,1.);
			    }
			}
		    }
		    
		    
		    
		    if(k>0 && i>0){
			if( (i+j+k)%2==0 ){
			    double zxdif = intensities[i-1][j][k-1] - intensity;
			    if( zxdif*idif < 0 ){ // crossing
				IVec pt = pos.cp();
				pt.z -= Math.abs(idif)/(Math.abs(zxdif)+Math.abs(idif))*dif.z;
				pt.x -= Math.abs(idif)/(Math.abs(zxdif)+Math.abs(idif))*dif.x;
				grid.zx(pt);
				
				// debug
				//new IPoint(pt).clr(1.0,0,0);
				//new ICurve(pos.cp(), pos.cp().sub(dif.x,0, dif.z)).clr(1.,0,1.);
			    }
			}
			else{
			    double zxdif1 = intensities[i-1][j][k] - intensity;
			    double zxdif2 = intensities[i][j][k-1] - intensity;
			    if( zxdif1*zxdif2 < 0 ){ // crossing
				IVec pt1 = pos.cp();
				IVec pt2 = pos.cp();
				pt1.x -= dif.x;
				pt2.z -= dif.z;
				
				IVec pt = pt1.sum(pt2, Math.abs(zxdif1)/(Math.abs(zxdif1)+Math.abs(zxdif2)));
				grid.zx(pt);
				
				// debug
				//new IPoint(pt).clr(1.0,0,0);
				//new ICurve(pt1, pt2).clr(1.,0,1.);
			    }
			}
		    }
		    
		    
		    if(i>0 && j>0 && k>0){
			int[][][] edgeIdx;
			
			if((i+j+k)%2==0){
			    // corner 1 
			    edgeIdx = findEdge(intensity,
					       intensities[i-1][j-1][k-1],
					       intensities[i][j-1][k-1],
					       intensities[i-1][j][k-1],
					       intensities[i-1][j-1][k]);
			    if(edgeIdx!=null){
				IFace[] faces = grid.faces(new int[]{ 0, 1, 3, 4 }, edgeIdx,
							   new double[]{
							       intensities[i-1][j-1][k-1]-intensity,
							       intensities[i][j-1][k-1]-intensity,
							       intensities[i-1][j][k-1]-intensity,
							       intensities[i-1][j-1][k]-intensity
							   });
				for(int l=0; l<faces.length; l++){
				    meshGeo.addFace(faces[l]);
				}
			    }
			    
			    // corner 2
			    edgeIdx = findEdge(intensity,
					       intensities[i][j][k-1],
					       intensities[i-1][j][k-1],
					       intensities[i][j-1][k-1],
					       intensities[i][j][k]);
			    if(edgeIdx!=null){
				IFace[] faces = grid.faces(new int[]{ 2, 3, 1, 6 }, edgeIdx,
							   new double[]{
							       intensities[i][j][k-1]-intensity,
							       intensities[i-1][j][k-1]-intensity,
							       intensities[i][j-1][k-1]-intensity,
							       intensities[i][j][k]-intensity
							   });
				for(int l=0; l<faces.length; l++){
				    meshGeo.addFace(faces[l]);
				}
			    }
			    
			    // corner 3
			    edgeIdx = findEdge(intensity,
					       intensities[i][j][k],
					       intensities[i][j-1][k],
					       intensities[i-1][j-1][k],
					       intensities[i][j-1][k-1]);
			    if(edgeIdx!=null){
				IFace[] faces = grid.faces(new int[]{ 6, 5, 4, 1 }, edgeIdx,
							   new double[]{
							       intensities[i][j][k]-intensity,
							       intensities[i][j-1][k]-intensity,
							       intensities[i-1][j-1][k]-intensity,
							       intensities[i][j-1][k-1]-intensity
							   });
				for(int l=0; l<faces.length; l++){
				    meshGeo.addFace(faces[l]);
				}
			    }
			    
			    // corner 4
			    edgeIdx = findEdge(intensity,
					       intensities[i-1][j-1][k],
					       intensities[i-1][j][k],
					       intensities[i][j][k],
					       intensities[i-1][j][k-1]);
			    if(edgeIdx!=null){
				IFace[] faces = grid.faces(new int[]{ 4, 7, 6, 3 }, edgeIdx,
							   new double[]{
							       intensities[i-1][j-1][k]-intensity,
							       intensities[i-1][j][k]-intensity,
							       intensities[i][j][k]-intensity,
							       intensities[i-1][j][k-1]-intensity
							   });
				for(int l=0; l<faces.length; l++){
				    meshGeo.addFace(faces[l]);
				}
			    }
			    
			    // inside
			    edgeIdx = findEdge(intensity,
					       intensities[i][j-1][k-1],
					       intensities[i-1][j][k-1],
					       intensities[i][j][k],
					       intensities[i-1][j-1][k]);
			    if(edgeIdx!=null){
				IFace[] faces = grid.faces(new int[]{ 1, 3, 6, 4 }, edgeIdx,
							   new double[]{
							       intensities[i][j-1][k-1]-intensity,
							       intensities[i-1][j][k-1]-intensity,
							       intensities[i][j][k]-intensity,
							       intensities[i-1][j-1][k]-intensity
							   });
				for(int l=0; l<faces.length; l++){
				    meshGeo.addFace(faces[l]);
				}
			    }
			    
			}
			else{
			    // corner 1
			    edgeIdx = findEdge(intensity,
					       intensities[i-1][j-1][k-1],
					       intensities[i][j-1][k-1],
					       intensities[i][j][k-1],
					       intensities[i][j-1][k]);
			    if(edgeIdx!=null){
				IFace[] faces = grid.faces(new int[]{ 0, 1, 2, 5 }, edgeIdx,
							   new double[]{
							       intensities[i-1][j-1][k-1]-intensity,
							       intensities[i][j-1][k-1]-intensity,
							       intensities[i][j][k-1]-intensity,
							       intensities[i][j-1][k]-intensity
							   });
				for(int l=0; l<faces.length; l++) meshGeo.addFace(faces[l]);
			    }
			    
			    // corner 2
			    edgeIdx = findEdge(intensity,
					       intensities[i][j][k-1],
					       intensities[i-1][j][k-1],
					       intensities[i-1][j-1][k-1],
					       intensities[i-1][j][k]);
			    if(edgeIdx!=null){
				IFace[] faces = grid.faces(new int[]{ 2, 3, 0, 7 }, edgeIdx,
							   new double[]{
							       intensities[i][j][k-1]-intensity,
							       intensities[i-1][j][k-1]-intensity,
							       intensities[i-1][j-1][k-1]-intensity,
							       intensities[i-1][j][k]-intensity
							   });
				for(int l=0; l<faces.length; l++) meshGeo.addFace(faces[l]);
			    }
			    
			    // corner 3
			    edgeIdx = findEdge(intensity,
					       intensities[i-1][j-1][k],
					       intensities[i][j-1][k],
					       intensities[i-1][j][k],
					       intensities[i-1][j-1][k-1]);
			    if(edgeIdx!=null){
				IFace[] faces = grid.faces(new int[]{ 4, 5, 7, 0 }, edgeIdx,
							   new double[]{
							       intensities[i-1][j-1][k]-intensity,
							       intensities[i][j-1][k]-intensity,
							       intensities[i-1][j][k]-intensity,
							       intensities[i-1][j-1][k-1]-intensity
							   });
				for(int l=0; l<faces.length; l++) meshGeo.addFace(faces[l]);
			    }
			    
			    // corner 4
			    edgeIdx = findEdge(intensity,
					       intensities[i][j][k],
					       intensities[i-1][j][k],
					       intensities[i][j-1][k],
					       intensities[i][j][k-1]);
			    if(edgeIdx!=null){
				IFace[] faces = grid.faces(new int[]{ 6, 7, 5, 2 }, edgeIdx,
							   new double[]{
							       intensities[i][j][k]-intensity,
							       intensities[i-1][j][k]-intensity,
							       intensities[i][j-1][k]-intensity,
							       intensities[i][j][k-1]-intensity
							   });
				for(int l=0; l<faces.length; l++) meshGeo.addFace(faces[l]);
			    }
			    
			    // inside 
			    edgeIdx = findEdge(intensity,
					       intensities[i-1][j-1][k-1],
					       intensities[i][j][k-1],
					       intensities[i][j-1][k],
					       intensities[i-1][j][k]);
			    if(edgeIdx!=null){
				IFace[] faces = grid.faces(new int[]{ 0, 2, 5, 7 }, edgeIdx,
							   new double[]{
							       intensities[i-1][j-1][k-1]-intensity,
							       intensities[i][j][k-1]-intensity,
							       intensities[i][j-1][k]-intensity,
							       intensities[i-1][j][k]-intensity
							   });
				for(int l=0; l<faces.length; l++) meshGeo.addFace(faces[l]);
			    }
			    
			}
			
		    }
		    
		    
		    
		    /*
		      
		    
		    
		    for(int l=0; l<8; l++){
			IFace f =grid.triangle(l);
			if(f!=null) meshGeo.addFace(f);
		    }
		    for(int l=0; l<3; l++){
			IFace f =grid.quad(l);
			if(f!=null) meshGeo.addFace(f);
		    }
		    //if(grid.v(2)!=null && grid.v(6)!=null && grid.v(10)!=null ){
		    //meshGeo.addFace(new IFace(grid.v(2),grid.v(6),grid.v(10)));
		    //}
		    //if(grid.x(i-1,j,k)!=null && grid.y(i,j-1,k)!=null && grid.z(i,j,k-1)!=null ){
		    //meshGeo.addFace(new IFace(grid.x(i-1,j,k),grid.y(i,j-1,k),grid.z(i,j,k-1)));
		    //}
		    
		    // ... other faces
		    */
		}
	    }
	}
	
	if(meshGeo.faceNum()>0){
	    //for(int i=0; i<meshGeo.vertexNum(); i++){ meshGeo.vertex(i).calcNormal(); }
	    //meshGeo.removeDuplicatedEdge(); // no effect
	    return new IMesh(meshGeo);
	}
	
	IOut.err("no face is sampled"); //
	return null;
    }

    public static class VertexGrid{
	public IVertex[][][] xpt;
	public IVertex[][][] ypt;
	public IVertex[][][] zpt;
	public IVertex[][][] corner; // when exact corner matches
	public IVertex[][][] xypt;
	public IVertex[][][] yzpt;
	public IVertex[][][] zxpt;
	
	public int xnum,ynum,znum;
	public int xindex,yindex,zindex;
	
	// when the grid need to find out vertex position by itself.
	public IVec pos;
	public double xinc, yinc, zinc;
	
	
	public VertexGrid(int xn, int yn, int zn){
	    xnum=xn;
	    ynum=yn;
	    znum=zn;
	    xpt = new IVertex[xnum][ynum+1][znum+1];
	    ypt = new IVertex[xnum+1][ynum][znum+1];
	    zpt = new IVertex[xnum+1][ynum+1][znum];
	    corner = new IVertex[xnum+1][ynum+1][znum+1];
	    xypt = new IVertex[xnum][ynum][znum+1];
	    yzpt = new IVertex[xnum+1][ynum][znum];
	    zxpt = new IVertex[xnum][ynum+1][znum];
	    
	}

	public void setGridPosition(IVec pos, double xinc, double yinc, double zinc){
	    this.pos = pos;
	    this.xinc=xinc;
	    this.yinc=yinc;
	    this.zinc=zinc;
	}
	
	public void index(int i, int j, int k){
	    xindex=i; yindex=j; zindex=k;
	}
	
	public void x(int i, int j, int k, IVecI v){ xpt[i][j][k] = new IVertex(v); }
	public void y(int i, int j, int k, IVecI v){ ypt[i][j][k] = new IVertex(v); }
	public void z(int i, int j, int k, IVecI v){ zpt[i][j][k] = new IVertex(v); }
	
	public void x(IVecI v){ xpt[xindex-1][yindex][zindex] = new IVertex(v); }
	public void y(IVecI v){ ypt[xindex][yindex-1][zindex] = new IVertex(v); }
	public void z(IVecI v){ zpt[xindex][yindex][zindex-1] = new IVertex(v); }
	
	public void xy(IVecI v){ xypt[xindex-1][yindex-1][zindex] = new IVertex(v); }
	public void yz(IVecI v){ yzpt[xindex][yindex-1][zindex-1] = new IVertex(v); }
	public void zx(IVecI v){ zxpt[xindex-1][yindex][zindex-1] = new IVertex(v); }
	
	public void corner(IVecI v){ corner[xindex][yindex][zindex] = new IVertex(v); }
	
	public IVertex x(int i, int j, int k){
	    //IOut.err(i+","+j+","+k); //
	    if(i<0 || j<0 || k<0 || i>=xnum || j>ynum || k>znum){
		IOut.err(i+","+j+","+k); //
		return null;
	    }
	    return xpt[i][j][k];
	}
	
	public IVertex y(int i, int j, int k){
	    //IOut.err(i+","+j+","+k); //
	    if(i<0 || j<0 || k<0 || i>xnum || j>=ynum || k>znum){
		IOut.err(i+","+j+","+k); //
		return null;
	    }
	    return ypt[i][j][k];
	}
	
	public IVertex z(int i, int j, int k){
	    //IOut.err(i+","+j+","+k); //
	    if(i<0 || j<0 || k<0 || i>xnum || j>ynum || k>=znum){
		IOut.err(i+","+j+","+k); //
		return null;
	    }
	    return zpt[i][j][k];
	}
	
	public IVertex corner(int i, int j, int k){
	    //IOut.err(i+","+j+","+k); //
	    if(i<0 || j<0 || k<0 || i>xnum || j>ynum || k>znum){
		IOut.err(i+","+j+","+k); //
		return null;
	    }
	    return corner[i][j][k];
	}
	
	public IVertex xy(int i, int j, int k){
	    //IOut.err(i+","+j+","+k); //
	    if(i<0 || j<0 || k<0 || i>=xnum || j>=ynum || k>znum){
		IOut.err(i+","+j+","+k); //
		return null;
	    }
	    return xypt[i][j][k];
	}
	
	public IVertex yz(int i, int j, int k){
	    //IOut.err(i+","+j+","+k); //
	    if(i<0 || j<0 || k<0 || i>xnum || j>=ynum || k>=znum){
		IOut.err(i+","+j+","+k); //
		return null;
	    }
	    return yzpt[i][j][k];
	}
	
	public IVertex zx(int i, int j, int k){
	    //IOut.err(i+","+j+","+k); //
	    if(i<0 || j<0 || k<0 || i>=xnum || j>ynum || k>=znum){
		IOut.err(i+","+j+","+k); //
		return null;
	    }
	    return zxpt[i][j][k];
	}
	
	/** @param vertexIdx array of 4 index num from 0 to 7 */
	public IFace[] faces(int[] vertexIdx, int[][][] edgeIdx, double[] intensityDif){
	    // vertexIdx: 0 = [xindex-1][yindex-1][zindex-1]
	    // vertexIdx: 1 = [xindex][yindex-1][zindex-1]
	    // vertexIdx: 2 = [xindex][yindex][zindex-1]
	    // vertexIdx: 3 = [xindex-1][yindex][zindex-1]
	    // vertexIdx: 4 = [xindex-1][yindex-1][zindex]
	    // vertexIdx: 5 = [xindex][yindex-1][zindex]
	    // vertexIdx: 6 = [xindex][yindex][zindex]
	    // vertexIdx: 7 = [xindex-1][yindex][zindex]
	    
	    IFace[] fcs = new IFace[edgeIdx.length];
	    
	    /*
	    IVertex[] cornerVtx = new IVertex[vertexIdx.length];
	    for(int i=0; i<vertexIdx.length; i++){
		cornerVtx[i] = cornerByIndex(vertexIdx[i]);
		IG.p("vertexIdx["+i+"] = "+vertexIdx[i]); //
		IG.p("cornerVtx["+i+"] = "+cornerVtx[i]); //
	    }
	    */
	    
	    for(int i=0; i<edgeIdx.length; i++){
		IVertex[] vtx = new IVertex[edgeIdx[i].length];
		int normalCheckIndex=-1;
		for(int j=0; j<edgeIdx[i].length; j++){
		    if(edgeIdx[i][j].length==1){
			vtx[j] = cornerByIndex(vertexIdx[edgeIdx[i][j][0]]);
			//vtx[j] = cornerVtx[edgeIdx[i][j][0]];
		    }
		    else{
			vtx[j] = pointByIndex(vertexIdx[edgeIdx[i][j][0]],
					      vertexIdx[edgeIdx[i][j][1]]);
			// first appearing edge index
			if(normalCheckIndex<0){ normalCheckIndex = edgeIdx[i][j][0]; }
		    }
		    
		    if(vtx[j]==null){
			IOut.err("null at "+j); //
		    }
		}
		
		// check face direction depending on intensity
		if(normalCheckIndex<0){ // all corners
		    int j=0;
		    for(; j<vertexIdx.length && normalCheckIndex<0; j++){
			int k=0;
			for(; k<edgeIdx[i].length && edgeIdx[i][k][0]!=j; k++);
			if(k==edgeIdx[i].length) normalCheckIndex=j; // j didn't match with any
		    }
		    if(normalCheckIndex<0){
			IOut.err("normalCheckIndex couldn't be foound"); // something wrong
		    }
		}
		
		if(vtx.length<3){
		    IOut.err("too less number of edgeIdx["+i+"].length ("+edgeIdx[i].length+"). it shoudl be >= 3");
		}
		else if(normalCheckIndex>=0){
		    IVec normal = vtx[1].get().dif(vtx[0].pos()).cross(vtx[2].pos().dif(vtx[0].pos()));
		    IVec corner=null;
		    IVertex cornerVtx = cornerByIndex(vertexIdx[normalCheckIndex]);
		    if(cornerVtx!=null) corner = cornerVtx.get();
		    if(corner==null){
			corner = posByIndex(vertexIdx[normalCheckIndex]);
		    }
		    if(corner!=null){
			
			if(corner.dif(vtx[0].pos()).dot(normal) < 0){
			    if(intensityDif[normalCheckIndex] < 0){
				vtx = flipVertexArray(vtx); // flip
				//normal.show(vtx[0].pos()).clr(0,0,1.).size(0.1); // debug
			    }
			    //else normal.show(vtx[0].pos()).clr(1.,0,0).size(0.1); // debug
			}
			else{
			    if(intensityDif[normalCheckIndex] > 0){
				vtx = flipVertexArray(vtx); // flip
				//normal.show(vtx[0].pos()).clr(0,0,1.).size(0.1); // debug
			    }
			    //else normal.show(vtx[0].pos()).clr(1.,0,0).size(0.1); // debug
			}
		    }
		    else{
			IOut.err("corner couldn't be calculated"); //
		    }
		}
		
		fcs[i] = new IFace(vtx);
		//fcs[i].calcNormal(); // ?
	    }
	    return fcs;
	}
	
	public IVertex[] flipVertexArray(IVertex[] vtx){
	    IVertex[] v = new IVertex[vtx.length];
	    for(int i=0; i<vtx.length; i++){ v[i] = vtx[vtx.length-1-i]; }
	    return v;
	}

	public IVec posByIndex(int xindex, int yindex, int zindex){
	    if(pos==null) return null;
	    return pos.cp().add(xindex*xinc, yindex*yinc, zindex*zinc);
	}
	
	public IVec posByIndex(int vtxIdx){
	    // vertexIdx: 0 = [xindex-1][yindex-1][zindex-1]
	    // vertexIdx: 1 = [xindex][yindex-1][zindex-1]
	    // vertexIdx: 2 = [xindex][yindex][zindex-1]
	    // vertexIdx: 3 = [xindex-1][yindex][zindex-1]
	    // vertexIdx: 4 = [xindex-1][yindex-1][zindex]
	    // vertexIdx: 5 = [xindex][yindex-1][zindex]
	    // vertexIdx: 6 = [xindex][yindex][zindex]
	    // vertexIdx: 7 = [xindex-1][yindex][zindex]
		
	    switch(vtxIdx){
	    case 0: return posByIndex(xindex-1,yindex-1,zindex-1);
	    case 1: return posByIndex(xindex,yindex-1,zindex-1);
	    case 2: return posByIndex(xindex,yindex,zindex-1);
	    case 3: return posByIndex(xindex-1,yindex,zindex-1);
	    case 4: return posByIndex(xindex-1,yindex-1,zindex);
	    case 5: return posByIndex(xindex,yindex-1,zindex);
	    case 6: return posByIndex(xindex,yindex,zindex);
	    case 7: return posByIndex(xindex-1,yindex,zindex);
	    }
	    IOut.err("invalid index "+vtxIdx); //
	    return null;
	}
	
	public IVertex pointByIndex(int vtxIdx1, int vtxIdx2){
	    // vertexIdx: 0 = [xindex-1][yindex-1][zindex-1]
	    // vertexIdx: 1 = [xindex][yindex-1][zindex-1]
	    // vertexIdx: 2 = [xindex][yindex][zindex-1]
	    // vertexIdx: 3 = [xindex-1][yindex][zindex-1]
	    // vertexIdx: 4 = [xindex-1][yindex-1][zindex]
	    // vertexIdx: 5 = [xindex][yindex-1][zindex]
	    // vertexIdx: 6 = [xindex][yindex][zindex]
	    // vertexIdx: 7 = [xindex-1][yindex][zindex]
	    
	    //IOut.err("vtx1 = "+vtxIdx1+", vtx2 = "+vtxIdx2); //
	    
	    
	    // x
	    if(vtxIdx1==0 && vtxIdx2==1 || vtxIdx1==1 && vtxIdx2==0){
		return x(xindex-1, yindex-1, zindex-1);
	    }
	    if(vtxIdx1==3 && vtxIdx2==2 || vtxIdx1==2 && vtxIdx2==3){
		return x(xindex-1, yindex, zindex-1);
	    }
	    if(vtxIdx1==4 && vtxIdx2==5 || vtxIdx1==5 && vtxIdx2==4){
		return x(xindex-1, yindex-1, zindex);
	    }
	    if(vtxIdx1==7 && vtxIdx2==6 || vtxIdx1==6 && vtxIdx2==7){
		return x(xindex-1, yindex, zindex);
	    }
	    
	    // y
	    if(vtxIdx1==0 && vtxIdx2==3 || vtxIdx1==3 && vtxIdx2==0){
		return y(xindex-1, yindex-1, zindex-1);
	    }
	    if(vtxIdx1==2 && vtxIdx2==1 || vtxIdx1==1 && vtxIdx2==2){
		return y(xindex, yindex-1, zindex-1);
	    }
	    if(vtxIdx1==4 && vtxIdx2==7 || vtxIdx1==7 && vtxIdx2==4){
		return y(xindex-1, yindex-1, zindex);
	    }
	    if(vtxIdx1==5 && vtxIdx2==6 || vtxIdx1==6 && vtxIdx2==5){
		return y(xindex, yindex-1, zindex);
	    }
	    
	    // z
	    if(vtxIdx1==0 && vtxIdx2==4 || vtxIdx1==4 && vtxIdx2==0){
		return z(xindex-1, yindex-1, zindex-1);
	    }
	    if(vtxIdx1==1 && vtxIdx2==5 || vtxIdx1==5 && vtxIdx2==1){
		return z(xindex, yindex-1, zindex-1);
	    }
	    if(vtxIdx1==2 && vtxIdx2==6 || vtxIdx1==6 && vtxIdx2==2){
		return z(xindex, yindex, zindex-1);
	    }
	    if(vtxIdx1==3 && vtxIdx2==7 || vtxIdx1==7 && vtxIdx2==3){
		return z(xindex-1, yindex, zindex-1);
	    }
	    
	    // vertexIdx: 0 = [xindex-1][yindex-1][zindex-1]
	    // vertexIdx: 1 = [xindex][yindex-1][zindex-1]
	    // vertexIdx: 2 = [xindex][yindex][zindex-1]
	    // vertexIdx: 3 = [xindex-1][yindex][zindex-1]
	    // vertexIdx: 4 = [xindex-1][yindex-1][zindex]
	    // vertexIdx: 5 = [xindex][yindex-1][zindex]
	    // vertexIdx: 6 = [xindex][yindex][zindex]
	    // vertexIdx: 7 = [xindex-1][yindex][zindex]
	    
	    // xy
	    if(vtxIdx1==0 && vtxIdx2==2 || vtxIdx1==2 && vtxIdx2==0 ||
	       vtxIdx1==1 && vtxIdx2==3 || vtxIdx1==3 && vtxIdx2==1 ){
		return xy(xindex-1,yindex-1,zindex-1);
	    }
	    if(vtxIdx1==4 && vtxIdx2==6 || vtxIdx1==6 && vtxIdx2==4 ||
	       vtxIdx1==5 && vtxIdx2==7 || vtxIdx1==7 && vtxIdx2==5 ){
		return xy(xindex-1,yindex-1,zindex);
	    }
	    
	    // yz
	    if(vtxIdx1==0 && vtxIdx2==7 || vtxIdx1==7 && vtxIdx2==0 ||
	       vtxIdx1==3 && vtxIdx2==4 || vtxIdx1==4 && vtxIdx2==3 ){
		return yz(xindex-1,yindex-1,zindex-1);
	    }
	    if(vtxIdx1==1 && vtxIdx2==6 || vtxIdx1==6 && vtxIdx2==1 ||
	       vtxIdx1==2 && vtxIdx2==5 || vtxIdx1==5 && vtxIdx2==2 ){
		return yz(xindex,yindex-1,zindex-1);
	    }
	    
	    // zx
	    if(vtxIdx1==0 && vtxIdx2==5 || vtxIdx1==5 && vtxIdx2==0 ||
	       vtxIdx1==1 && vtxIdx2==4 || vtxIdx1==4 && vtxIdx2==1 ){
		return zx(xindex-1,yindex-1,zindex-1);
	    }
	    if(vtxIdx1==2 && vtxIdx2==7 || vtxIdx1==7 && vtxIdx2==2 ||
	       vtxIdx1==3 && vtxIdx2==6 || vtxIdx1==6 && vtxIdx2==3 ){
		return zx(xindex-1,yindex,zindex-1);
	    }
	    
	    IOut.err("invalid combination "+vtxIdx1+", "+vtxIdx2); //
	    
	    return null;
	}
	
	public IVertex cornerByIndex(int vertexIdx){
	    // vertexIdx: 0 = [xindex-1][yindex-1][zindex-1]
	    // vertexIdx: 1 = [xindex][yindex-1][zindex-1]
	    // vertexIdx: 2 = [xindex][yindex][zindex-1]
	    // vertexIdx: 3 = [xindex-1][yindex][zindex-1]
	    // vertexIdx: 4 = [xindex-1][yindex-1][zindex]
	    // vertexIdx: 5 = [xindex][yindex-1][zindex]
	    // vertexIdx: 6 = [xindex][yindex][zindex]
	    // vertexIdx: 7 = [xindex-1][yindex][zindex]
	    
	    switch(vertexIdx){
	    case 0: return corner[xindex-1][yindex-1][zindex-1];
	    case 1: return corner[xindex][yindex-1][zindex-1];
	    case 2: return corner[xindex][yindex][zindex-1];
	    case 3: return corner[xindex-1][yindex][zindex-1];		
	    case 4: return corner[xindex-1][yindex-1][zindex];
	    case 5: return corner[xindex][yindex-1][zindex];
	    case 6: return corner[xindex][yindex][zindex];
	    case 7: return corner[xindex-1][yindex][zindex];
	    }
	    
	    IOut.err("invalid index "+vertexIdx); //
	    
	    return null;
	}
	
	/** check all possible faces */
	/*
	public IFace[] faces(){
	    ArrayList<EdgeVertex> ev=null;
	    for(int i=0; i<2; i++){
		for(int j=0; j<2; j++){
		    if(x(xindex-1, yindex-1+i, zindex-1+j)!=null){
			if(ev==null){ ev = new ArrayList<EdgeVertex>(); }
			//ev.add(new 
		    }
		}
	    }
	    int edgeNum=0;
	    for(int i=0; i<12; i++){ if(v(i)!=null){ edgeNum++; } }
	    int cornerNum=0;
	    for(int i=0; i<8; i++){ if(corner(i)!=null){ cornerNum++; } }
	    if(cornerNum==0){
		if(edgeNum<3){
		    return null; // no face
		}
		if(edgeNum==3){
		}
	    }
	    else{
	    }
	}
	*/
	
	
	/** @param idx index of corner from 0 to 7. */
	public IFace triangle(int idx){
	    int i=-1,j=-1,k=-1;
	    
	    switch(idx){
	    case 0: i=0; j=8; k=4; break;
	    case 1: i=7; j=9; k=0; break;
	    case 2: i=1; j=10; k=7; break;
	    case 3: i=4; j=11; k=1; break;
	    case 4: i=5; j=8; k=3; break;
	    case 5: i=3; j=9; k=6; break;
	    case 6: i=6; j=10; k=2; break;
	    case 7: i=2; j=11; k=5; break;
	    }
	    
	    if(i<0 || j<0 || k<0) return null;
	    
	    IVertex v1 = v(i);
	    IVertex v2 = v(j);
	    IVertex v3 = v(k);
	    
	    if(v1==null || v2==null || v3==null) return null;
	    
	    return new IFace(v1,v2,v3);
	}
	/** @param idx index of corner from 0 to 2. */
	public IFace quad(int idx){
	    int i=-1,j=-1,k=-1,l=-1;
	    
	    switch(idx){
	    case 0: i=0; j=1; k=2; l=3; break;
	    case 1: i=4; j=5; k=6; l=7; break;
	    case 2: i=8; j=9; k=10; l=11; break;
	    }
	    
	    if(i<0 || j<0 || k<0 || l<0) return null;
	    
	    IVertex v1 = v(i);
	    IVertex v2 = v(j);
	    IVertex v3 = v(k);
	    IVertex v4 = v(l);
	    
	    if(v1==null || v2==null || v3==null || v4==null) return null;
	    
	    return new IFace(v1,v2,v3,v4);
	}
	
	/** @param idx index number from 0 to 7. */
	public IVertex corner(int idx){
	    switch(idx){
	    case 0: return corner(xindex-1, yindex-1, zindex-1);
	    case 1: return corner(xindex, yindex-1, zindex-1);
	    case 2: return corner(xindex, yindex, zindex-1);
	    case 3: return corner(xindex-1, yindex, zindex-1);
	    case 4: return corner(xindex-1, yindex-1, zindex);
	    case 5: return corner(xindex, yindex-1, zindex);
	    case 6: return corner(xindex, yindex, zindex);
	    case 7: return corner(xindex-1, yindex, zindex);
	    }
	    return null;
	}
	
	/** @param idx index number from 0 to 11. 0 - 3 : xpt, 4 - 7 : ypt, 8 - 11: zpt */
	public IVertex v(int idx){
	    switch(idx){
	    case 0: return x(xindex-1, yindex-1, zindex-1);
	    case 1: return x(xindex-1, yindex, zindex-1);
	    case 2: return x(xindex-1, yindex, zindex);
	    case 3: return x(xindex-1, yindex-1, zindex);
	    case 4: return y(xindex-1, yindex-1, zindex-1);
	    case 5: return y(xindex-1, yindex-1, zindex);
	    case 6: return y(xindex, yindex-1, zindex);
	    case 7: return y(xindex, yindex-1, zindex-1);
	    case 8: return z(xindex-1, yindex-1, zindex-1);
	    case 9: return z(xindex, yindex-1, zindex-1);
	    case 10: return z(xindex, yindex, zindex-1);
	    case 11: return z(xindex-1, yindex-1, zindex-1);
	    }
	    return null;
	}
	
    }
    
    
    public static class EdgeVertex{
	IVertex vertex;
	int dir; // 0-2; x or y or z
	int i,j; // [0,0], [1,0], [0,1], [1,1]
	public EdgeVertex(IVertex v, int d, int i, int j){
	    vertex=v;
	    dir=d;
	    this.i=i;
	    this.j=j;
	}
    }
    
    /*
    static IFace[] makeFace(int[][][] edgeIndex, IVecI ... pts){
	IFace[] faces = new IFace[edgeIndex.length];
	for(int i=0; i<edgeIndex.length; i++){
	    
	    //for(int j=0; j<
	    
	}
    }
    */
    
    /* check value state of tetrahedron if its larger/small/equal to the target value
       and find edges to separate different state
    */
    static int[][][] findEdge(double target, double ... values){
	int num = values.length; // this has to be 4
	int[] states = new int[num];
	int sum=0;
	int zeroCount=0;
	for(int i=0; i<num; i++){
	    if(values[i]==target){ states[i] = 0; zeroCount++; }
	    else if(values[i]>target){ states[i] = 1; }
	    else{ states[i] = -1; } // values[i]<target
	    
	    sum+=states[i];
	}
	
	if(sum==num || sum==-num){ return null; } // all positive or all negative
	
	if(zeroCount==4){ // all faces should be filled
	    int[][][] retval = new int[4][3][1];
	    retval[0][0][0] = 0;
	    retval[0][1][0] = 2;
	    retval[0][2][0] = 1;
	    retval[1][0][0] = 0;
	    retval[1][1][0] = 1;
	    retval[1][2][0] = 3;
	    retval[2][0][0] = 1;
	    retval[2][1][0] = 2;
	    retval[2][2][0] = 3;
	    retval[3][0][0] = 0;
	    retval[3][1][0] = 3;
	    retval[3][2][0] = 2;
	    return retval;
	}
	
	if(zeroCount==3){ // one face
	    int[][][] retval = new int[1][3][1];
	    for(int i=0, idx=0; i<num; i++){
		if(states[i]==0){
		    retval[0][idx][0] = i;
		    idx++;
		}
	    }
	    return retval;
	}
	
	if(zeroCount==2){ // one face or none
	    
	    if(sum!=0){ // other two states are both + or -; no intersection
		return null;
	    }
	    
	    //int edgeIdx1=-1, edgeIdx2=-1, zeroIdx1=-1, zeroIdx2=-1;
	    int[][][] retval = new int[1][3][];
	    retval[0][0] = new int[1];
	    retval[0][1] = new int[1];
	    retval[0][2] = new int[2];
	    
	    int zeroIdx=0;
	    int edgeIdx=0;
	    for(int i=0; i<num; i++){
		if(states[i]==0){ retval[0][zeroIdx][0] = i; zeroIdx++; }
		else{ retval[0][2][edgeIdx] = i; edgeIdx++; }
		/*
		if(states[i]==0){ if(zeroIdx1<0){ zeroIdx1=i; } else if(zeroIdx2<0){ zeroIdx2=i; } }
		for(int j=i+1; j<num; j++){
		    if(states[i]!=0 && states[j]!=0){
			if(states[i]==states[j]){ return null; }// no intersection 
			else{ edgeIdx1=i; edgeIdx2=j; }
		    }
		}
		*/
	    }
	    return retval;
	}
	
	if(zeroCount==1){ // one face or none
	    if(sum >= (num-zeroCount) || sum <= -(num-zeroCount) ){ // no intersection
		return null;
	    }
	    
	    int[][][] retval = new int[1][3][];
	    retval[0][0] = new int[1];
	    retval[0][1] = new int[2];
	    retval[0][2] = new int[2];
	    int edgeIdx=1;
	    for(int i=0; i<num; i++){
		if(states[i]==0){
		    retval[0][0][0] = i;
		}
		else{
		    for(int j=i+1; j<num; j++){
			if(states[i]!=states[j]){
			    retval[0][edgeIdx][0] = i;
			    retval[0][edgeIdx][0] = j;
			    edgeIdx++;
			}
		    }
		}
	    }
	    
	    if(edgeIdx==2){ return retval; }
	    
	    IOut.err("edge index count doesn't match"); //
	    return null;
	}
	
	
	// non zero
	
	if(states[0]==states[1]){
	    if(states[0]==states[2]){
		if(states[0]==states[3]){} // never happen
		else{ // only states[3] different
		    int[][][] retval = new int[1][3][2];
		    retval[0][0][0] = 0;
		    retval[0][0][1] = 3;
		    retval[0][1][0] = 1;
		    retval[0][1][1] = 3;
		    retval[0][2][0] = 2;
		    retval[0][2][1] = 3;
		    return retval;
		}
	    }
	    else{
		if(states[0]==states[3]){ // only states[2] different
		    int[][][] retval = new int[1][3][2];
		    retval[0][0][0] = 0;
		    retval[0][0][1] = 2;
		    retval[0][1][0] = 1;
		    retval[0][1][1] = 2;
		    retval[0][2][0] = 3;
		    retval[0][2][1] = 2;
		    return retval;
		}
		else{ // states[0]==states[1], states[2]==states[3]
		    int[][][] retval = new int[1][4][2];
		    retval[0][0][0] = 0;
		    retval[0][0][1] = 2;
		    retval[0][1][0] = 0;
		    retval[0][1][1] = 3;
		    retval[0][2][0] = 1;
		    retval[0][2][1] = 3;
		    retval[0][3][0] = 1;
		    retval[0][3][1] = 2;
		    return retval;
		}
	    }
	}
	else{ // states[0]!=states[1]
	    if(states[0]==states[2]){
		if(states[0]==states[3]){ // only states[1] is different
		    int[][][] retval = new int[1][3][2];
		    retval[0][0][0] = 0;
		    retval[0][0][1] = 1;
		    retval[0][1][0] = 2;
		    retval[0][1][1] = 1;
		    retval[0][2][0] = 3;
		    retval[0][2][1] = 1;
		    return retval;
		}		    
		else{ // states[0]==states[2], states[1]==states[3]
		    int[][][] retval = new int[1][4][2];
		    retval[0][0][0] = 0;
		    retval[0][0][1] = 3;
		    retval[0][1][0] = 0;
		    retval[0][1][1] = 1;
		    retval[0][2][0] = 2;
		    retval[0][2][1] = 1;
		    retval[0][3][0] = 2;
		    retval[0][3][1] = 3;
		    return retval;
		}
	    }
	    else{ // states[0]!=states[2]
		if(states[0]==states[3]){ // stataes[0]==states[3], states[1][==states[2]
		    int[][][] retval = new int[1][4][2];
		    retval[0][0][0] = 0;
		    retval[0][0][1] = 1;
		    retval[0][1][0] = 0;
		    retval[0][1][1] = 2;
		    retval[0][2][0] = 3;
		    retval[0][2][1] = 2;
		    retval[0][3][0] = 3;
		    retval[0][3][1] = 1;
		    return retval;
		}
		else{ // only states[0] is different
		    int[][][] retval = new int[1][3][2];
		    retval[0][0][0] = 0;
		    retval[0][0][1] = 1;
		    retval[0][1][0] = 0;
		    retval[0][1][1] = 2;
		    retval[0][2][0] = 0;
		    retval[0][2][1] = 3;
		    return retval;
		}
	    }
	}
	
	IOut.err("invalid states"); //
	return null;
	/*
	ArrayList<int[]> pairs = new ArrayList<int[]>();
	
	for(int i=0; i<num; i++){
	    for(int j=i+1; j<num; j++){
		if(states[i]!=states[j]){
		    pairs.add(new int[]{ i, j });
		}
	    }
	}
	
	if(pairs.size()!=3 && pairs.size()!=4){
	    IOut.err("pair count is invalid ("+pairs.size()+")");
	}
	
	int[][][] retval = new int[1][pairs.size()][2];
	for(int i=0; i<pairs.size(); i++){
	    retval[0][i][0] = pairs.get(i)[0];
	    retval[0][i][1] = pairs.get(i)[1];
	}
	
	return retval;
	*/
    }
    
    
    /*
    public static class VertexTetrahedron{
	double[] val;
		
	public VertexTetrahedron(double v1, double v2, double v3, double v4){
	    val = new double[4];
	    val[0] = v1;
	    val[1] = v2;
	    val[2] = v3;
	    val[3] = v4;
	}
	
    }
    */
}
