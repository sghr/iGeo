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
   A panel class to contain multiple IPane in grid layout with toggle function
   to switch showing only one pane and showing all panes.
   
   @author Satoru Sugihara
*/
public class IGridPanel extends IScreenTogglePanel{ //IPanel{
    
    public IPane[][] gridPanes;
    public int xnum, ynum;
    
    public double[] widthRatio;
    public double[] heightRatio;
    
    //int fullViewIndex=-1; 
    
    public IGridPanel(int x, int y, int width, int height, int xnum, int ynum){
	super(x,y,width,height);
	setupGrid(xnum,ynum,null);
	currentMousePane = gridPanes[1][0]; //
    }
    public IGridPanel(int x, int y, int width, int height, int xnum, int ynum, IPane[][] panes){
	super(x,y,width,height);
	setupGrid(xnum,ynum,panes);
	currentMousePane = gridPanes[1][0]; //
    }
    
    public void setupGrid(int xnum, int ynum, IPane[][] panes){
        if(xnum<=0 || ynum<=0){
            IOut.err("xnum and ynum need to be larger than 1");
            return;
        }
        
        double[] widthRatio = new double[xnum];
        double[] heightRatio = new double[ynum];
        
        for(int i=0; i<xnum; i++) widthRatio[i]=1.0/xnum;
        for(int i=0; i<ynum; i++) heightRatio[i]=1.0/ynum;
        
        setupGrid(widthRatio,heightRatio,panes);
    }
    
    /**
       @param widthRatio Array of relative width ratio. Actual width is calculatd by each ratio divided by sum of ratios
       @param heightRatio Array of relative height ratio. Actual height is calculatd by each ratio divided by sum of ratios
    */
    public void setupGrid(double[] widthRatio, double[] heightRatio, IPane[][] panes){
	this.widthRatio=widthRatio;
	this.heightRatio=heightRatio;
	
	if(widthRatio==null || heightRatio==null){
            IOut.err("widthRatio or heightRatio is null"); return;
        }
        if(widthRatio.length==0 || heightRatio.length==0){
            IOut.err("ength of widthRatio or heightRatio is zero"); return;
        }
	
        xnum = widthRatio.length;
        ynum = heightRatio.length;
	
	/*
        double[] culmutiveWidth, culmutiveHeight;
        culmutiveWidth=new double[xnum];
        culmutiveHeight=new double[ynum];
        
        culmutiveWidth[0] = widthRatio[0];
        for(int i=1; i<xnum; i++)
            culmutiveWidth[i]=culmutiveWidth[i-1]+widthRatio[i];
        
        culmutiveHeight[0] = heightRatio[0];
        for(int i=1; i<ynum; i++)
            culmutiveHeight[i]=culmutiveHeight[i-1]+heightRatio[i];
        
        int[] xpos = new int[xnum+1];
        int[] ypos = new int[ynum+1];
        
        xpos[0] = 0;
        ypos[0] = 0;
        for(int i=0; i<xnum; i++)
            xpos[i+1]=(int)(culmutiveWidth[i]/culmutiveWidth[xnum-1]*width);
        for(int i=0; i<ynum; i++)
            ypos[i+1]=(int)(culmutiveHeight[i]/culmutiveHeight[ynum-1]*height);
	*/
	
	int[] xpos = getPositionArrayFromRatio(widthRatio, width);
	int[] ypos = getPositionArrayFromRatio(heightRatio, height);
	
	if(gridPanes==null || gridPanes.length!=xnum ||
           gridPanes[0].length!=ynum || panes!=null){
	    
            gridPanes = new IPane[xnum][ynum];
            for(int i=0; i<xnum; i++){
                for(int j=0; j<ynum; j++){
		    IView v=null;
		    int paneX = x+xpos[i];
		    int paneY = y+ypos[j];
		    int paneW = xpos[i+1]-xpos[i];
		    int paneH = ypos[j+1]-ypos[j];
		    
		    boolean orthogonal = false;
		    
		    // only if it's 2x2 view and the first execution
		    if(xnum==2&&ynum==2){
			if(i==0&&j==0){ // top
			    v = IView.getTopView(paneX,paneY,paneW,paneH);
			    orthogonal = true;
			}
			else if(i==1&&j==0){ // pers
			    v = IView.getDefaultPerspectiveView(paneX,paneY,paneW,paneH);
			}
			else if(i==0&&j==1){ // front
			    v = IView.getFrontView(paneX,paneY,paneW,paneH);
			    orthogonal = true;
			}
			else if(i==1&&j==1){ // right
			    v = IView.getRightView(paneX,paneY,paneW,paneH);
			    orthogonal = true;
			}
		    }
		    else{ // default; axon
			v = IView.getDefaultAxonometricView(paneX,paneY,paneW,paneH);
		    }
		    //v.setMode(mode);
		    
		    v.enableGL(); // here?
		    
		    v.enableRotationAroundTarget(); // here?
		    v.setTarget(0,0,0); //

		    if(panes!=null && i<panes.length && j<panes[i].length && panes[i][j]!=null){
			gridPanes[i][j] = panes[i][j];
			gridPanes[i][j].setBounds(paneX,paneY,paneW,paneH);
			gridPanes[i][j].setPanel(this);
			gridPanes[i][j].setView(v);
		    }
		    else{
			gridPanes[i][j] = new IPaneLight(paneX,paneY,paneW,paneH,v,this);
		    }
		    
		    if(orthogonal){
			if(gridPanes[i][j].navigator()!=null){
			    gridPanes[i][j].navigator().setRotateLock(true);
			}
		    }
                }
            }
	    
	    if(super.paneNum()>0) super.clearPane();
            for(int i=0; i<xnum; i++)
                for(int j=0; j<ynum; j++)
		    super.addPane(gridPanes[i][j]);
	    
	}
	else{ // change size
            for(int i=0; i<xnum; i++){
                for(int j=0; j<ynum; j++){
                    gridPanes[i][j].setBounds
                        (x+xpos[i], y+ypos[j],xpos[i+1]-xpos[i], ypos[j+1]-ypos[j]);
                }
            }
        }
    }
    
    static public int[] getPositionArrayFromRatio(double[] ratio, int totalLen){
	int n = ratio.length;
        double[] culmutiveLen;
        culmutiveLen=new double[n];
        culmutiveLen[0] = ratio[0];
        for(int i=1; i<n; i++)
            culmutiveLen[i]=culmutiveLen[i-1]+ratio[i];
        int[] pos = new int[n+1];
        pos[0] = 0;
        for(int i=0; i<n; i++)
	    pos[i+1]=(int)(culmutiveLen[i]/culmutiveLen[n-1]*totalLen);
	return pos;
    }
    /*
    static public int[] getXArrayFromWidthRatio(double[] wratio, int width){
	int xn = wratio.length;
        double[] culmutiveWidth;
        culmutiveWidth=new double[xn];
        culmutiveWidth[0] = wratio[0];
        for(int i=1; i<xn; i++)
            culmutiveWidth[i]=culmutiveWidth[i-1]+wratio[i];
        int[] xpos = new int[xn+1];
        xpos[0] = 0;
        for(int i=0; i<xn; i++)
	    xpos[i+1]=(int)(culmutiveWidth[i]/culmutiveWidth[xn-1]*width);
	return xpos;
    }
    static public int[] getYArrayFromHeightRatio(double[] hratio, int height){
        int yn = hratio.length;
        double[] culmutiveHeight;
        culmutiveHeight=new double[yn];
        culmutiveHeight[0] = hratio[0];
        for(int i=1; i<yn; i++)
            culmutiveHeight[i]=culmutiveHeight[i-1]+hratio[i];
        int[] ypos = new int[yn+1];
        ypos[0] = 0;
        for(int i=0; i<yn; i++)
            ypos[i+1]=(int)(culmutiveHeight[i]/culmutiveHeight[yn-1]*height);
	return ypos;
    }
    */
    
    public void setSize(int w, int h){
	width=w;
	height=h;
	
	int[] xpos = getPositionArrayFromRatio(widthRatio, w);
	int[] ypos = getPositionArrayFromRatio(heightRatio, h);
	
	for(int i=0; i<xnum; i++){
	    for(int j=0; j<ynum; j++){
		if(fullScreenPane!=gridPanes[i][j]){
		    gridPanes[i][j].setBounds
			(x+xpos[i], y+ypos[j],xpos[i+1]-xpos[i], ypos[j+1]-ypos[j]);
		}
		else{
		    gridPanes[i][j].setBounds(x,y,width,height);
		    fullPaneOrigX = x+xpos[i];
		    fullPaneOrigY = y+ypos[j];
		    fullPaneOrigWidth = xpos[i+1]-xpos[i];
		    fullPaneOrigHeight = ypos[j+1]-ypos[j];
		}
	    }
	}
	
	sizeChanged=true;
    }
    
    
    //public void setFullView(int idx){ fullViewIndex=idx; }
    //public void setFullView(int xidx, int yidx){ setFullView(gridPanes[xidx][yidx]); }
    //public void setFullView(IPane p){ fullViewIndex = panes.indexOf(p); }
    
    /*
    public void show(){
	if(fullViewIndex<0) super.show();
	else for(int i=0; i<panes.size(); i++)
	    if(i==fullViewIndex) panes.get(i).show();
	    else panes.get(i).hide();
    }
    */
    
    /*
    public void draw(GL gl){
	if(fullViewIndex<0) super.draw(gl);
	else panes.get(fullViewIndex).draw(gl);
    }
    public void draw(Graphics2D g){
	if(fullViewIndex<0) super.draw(g);
	else panes.get(fullViewIndex).draw(g);
    }
    */
    /*
    public void draw(IGraphics g){
	if(fullViewIndex<0) super.draw(g);
	else panes.get(fullViewIndex).draw(g);
    }
    */
    
}
