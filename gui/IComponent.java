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

import igeo.IG;

/**
   Base class of custom GUI components.
   
   @author Satoru Sugihara
*/
public class IComponent{
    public int x, y, width, height;
    public boolean visible=false;
    
    public IComponent(int x, int y, int width, int height){
	this.x=x; this.y=y; this.width=width; this.height=height;
    }
    
    public void setBounds(int x, int y, int width, int height){
	this.x=x; this.y=y; this.width=width; this.height=height;
    }
    public void setBounds(IComponent c){ x=c.x; y=c.y; width=c.width; height=c.height; }
    public void setLocation(int x, int y){ this.x=x; this.y=y; }
    public void setSize(int width, int height){ this.width=width; this.height=height; }
    
    public float getX(){ return x; }
    public float getY(){ return y; }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    
    
    public boolean isVisible(){ return visible; }
    public void setVisible(boolean v){ visible=v; }
    //public void hide(){ visible=false; }
    //public void show(){ visible=true; }
    
    public boolean contains(int x, int y){
	if(x < this.x) return false;
	if(y < this.y) return false;
	if(x >= (this.x+width)) return false;
	if(y >= (this.y+height)) return false;
	return true;
    }
    
}
