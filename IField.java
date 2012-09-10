/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

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

import java.lang.reflect.*;
import java.util.ArrayList;

/**
   Field agent base class.
   @author Satoru Sugihara
*/

abstract public class IField extends IAgent implements IFieldI{
    public ArrayList<Class<? extends IParticleI>> targetClasses;
    
    //public IField(){}
    
    /** make the field applicable only to the specified target class */
    public IField targetClass(Class<? extends IParticleI> targetClass){
	targetClasses = new ArrayList<Class<? extends IParticleI>>();
	targetClasses.add(targetClass);
	return this;
    }
    /** alias */
    public IField target(Class<? extends IParticleI> targetClass){ return targetClass(targetClass); }
    
    
    /** make the field applicable only to the specified target classes */
    public IField targetClass(Class<? extends IParticleI>... targets){
	targetClasses = new ArrayList<Class<? extends IParticleI>>();
	for(Class<? extends IParticleI> tgt : targets){ targetClasses.add(tgt); }
	return this;
    }
    /** alias */
    public IField target(Class<? extends IParticleI>... targets){ return targetClass(targets); }
    
    public boolean isTargetClass(Object obj){
	for(int i=0; i<targetClasses.size(); i++){
	    if(targetClasses.get(i).isInstance(obj)) return true;
	}
	return false;
    }
    
    /** alias */
    public boolean isTarget(Object obj){ return isTargetClass(obj); }
    
    
    public void interact(ArrayList<IDynamics> agents){
	super.interact(agents); // in case of interact(IDynamics) in child class

	for(int i=0; i<agents.size(); i++){
	    if(targetClasses==null && agents.get(i) instanceof IParticleI ||
	       targetClasses!=null && isTargetClass(agents.get(i))){
		applyField((IParticleI)agents.get(i));
	    }
	}
    }
    
    abstract public void applyField(IParticleI particle);
    
}
