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

/**
   A class to define order of IVec in a specified direction to be used in sorting with ISort.
   
   @see ISort
   
   @author Satoru Sugihara
*/
public class IDirectionalComparator implements IComparator<IVec>{
    public IVec dir;
    public IDirectionalComparator(IVec dir){ this.dir = dir; }
    
    public int compare(IVec v1, IVec v2){ // return >0, <0, ==0
	double d1 = v1.dot(dir);
	double d2 = v2.dot(dir);
	if(d1 < d2) return -1;
	if(d1 > d2) return 1;
	return 0;
    }
    
}

