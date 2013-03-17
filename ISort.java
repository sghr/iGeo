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


//import java.util.Vector;
//import java.util.AbstractList;
//import java.util.ArrayList;
import java.util.List;

/**
   A class to sort objects in the order defined by a comparator implementing IComparator interface.
   
   @see IComparator
   
   @author Satoru Sugihara
*/
public class ISort/*<T>*/{
    //IComparator<T> comp;
    //public ISort(){}
    //public ISort(IComparator<T> c){ comp = c; }
    
    public static<T> void sort(List<T> target, IComparator<T> comparator, int from, int to) {
	//Object o1 = target.get((from + to)/ 2);
	T o1 = target.get((from + to)/ 2);
	int i = from;
	int j = to;
	
	while (true) {
	    while(comparator.compare(target.get(i),o1) < 0) i++;
	    while(comparator.compare(o1,target.get(j)) < 0) j--;
	    if(i >= j) break;
	    
	    T temp = target.get(i);
	    target.set(i,target.get(j));
	    target.set(j,temp);
	    i++;
	    j--;
	}
	
	if(from < i-1) sort(target, comparator, from, i-1);
	if(j+1 < to) sort(target, comparator, j+1, to);
    }
    
    public static<T> List<T> sort(List<T> target,IComparator<T> comparator){
	if(target.size()>2) sort(target, comparator, 0, target.size()-1);
	else if(target.size()>1){
	    if(comparator.compare(target.get(1),target.get(0))<0){
		T temp = target.get(1);
		target.set(1, target.get(0));
		target.set(0, temp);
	    }
	}
	return target;
    }
    
    /*
    public List<T> sort(List<T> target){
	return sort(target,comp);
    }
    public List<T> sort(List<T> target,IComparator<T> comparator){
	if(target.size()>2) sort(target, comparator, 0, target.size()-1);
	else if(target.size()>1){
	    if(comparator.compare(target.get(1),target.get(0))<0){
		T temp = target.get(1);
		target.set(1, target.get(0));
		target.set(0, temp);
	    }
	}
	return target;
    }
    */
    
    public static<T> List<T> bubbleSort(List<T> target, IComparator<T> comparator){
	for(int i=0; i<target.size(); i++){
	    for(int j=0; (j+i)<target.size()-1; j++){
		T o1 = target.get(j);
		T o2 = target.get(j+1);
		if(comparator.compare(o1,o2) > 0){
		    target.remove(j);
		    target.add(j+1,o1);
		}
	    }
	}
	return target;
    }
    
}
