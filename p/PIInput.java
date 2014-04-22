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

package igeo.p;

import processing.core.*;
import java.io.*;
import igeo.io.IInputWrapper;

import igeo.*;

/**
   Input wrapper class. Mostly for wrapping Processing's input stream method.
   
   @author Satoru Sugihara
*/
public class PIInput extends IInputWrapper{
    public PApplet papplet;
    public PIInput(PApplet p){ papplet = p; }
    public InputStream getStream(String filename){
	return papplet.createInput(filename);
    }
    public InputStream getStream(File file){
	return papplet.createInput(file.getName());
    }
}
