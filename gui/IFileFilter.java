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

import java.io.File;

public class IFileFilter extends javax.swing.filechooser.FileFilter implements java.io.FilenameFilter{
    public String[] extensions;
    public String msg;
    
    // static method
    static public String[] createCaseVariation(String ext){
	String[] exts = new String[3];
	int startPos=0;
	if(ext.startsWith(".")) startPos=1;
	exts[0] = ext.toLowerCase();
	exts[1] = ext.toLowerCase();
	exts[1] = Character.toUpperCase(ext.charAt(startPos)) + ext.substring(startPos+1);
	exts[2] = ext.toUpperCase();
	return exts;
    }
    
    // instance method
    public IFileFilter(String extension , String msg) {
	this.extensions = createCaseVariation(extension);
	this.msg = msg;
    }
    
    public IFileFilter(String extension1 , String extension2, String msg) {
	this.extensions = new String[2];
	this.extensions[0] = extension1;
	this.extensions[1] = extension2;
	this.msg = msg;
	for(int i=0; i<this.extensions.length; i++){
	    if(!this.extensions[i].startsWith(".")){
		this.extensions[i] = "." + this.extensions[i];
	    }
	}
    }
        
    public IFileFilter(String extension1 , String extension2,
		       String extension3, String msg) {
	this.extensions = new String[3];
	this.extensions[0] = extension1;
	this.extensions[1] = extension2;
	this.extensions[2] = extension3;
	this.msg = msg;
	for(int i=0; i<this.extensions.length; i++){
	    if(!this.extensions[i].startsWith(".")){
		this.extensions[i] = "." + this.extensions[i];
	    }
	}
    }
    
    public IFileFilter(String[] extensions , String msg) {
	this.extensions = extensions;
	this.msg = msg;
	for(int i=0; i<this.extensions.length; i++){
	    if(!this.extensions[i].startsWith(".")){
		this.extensions[i] = "." + this.extensions[i];
	    }
	}
    }
    
    public boolean accept(java.io.File f) {
	//return f.getName().endsWith(extension) || f.isDirectory();
	if(f.isDirectory()) return true;
	for(int i=0; i<extensions.length; i++)
	    if(f.getName().endsWith(extensions[i])) return true;
	return false;
    }
    
    public String getDescription() { return msg; }

    
    
    // for FilenameFilter, for FileDialog
    public boolean accept(File dir, String name){
	for(int i=0; i<extensions.length; i++) if(name.endsWith(extensions[i])) return true;
	return false;
    }
    
    
}
        
