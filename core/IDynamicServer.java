/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

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

package igeo.core;

import java.util.ArrayList;

/**
   A server to take care of all IDynamicObject. It runs as separate thread.
   
   @author Satoru Sugihara
   @version 0.7.0.0
*/
public class IDynamicServer implements Runnable{
    
    public Thread thread;
    //public int speed = IConfig.dynamicsUpdateSpeed; // in millisecond
    public int speed = (int)(IConfig.dynamicsSpeed*1000); // in millisecond
    
    public IServer server;
    
    public boolean runningDynamics=false;
    public boolean startedOnce=false;
    
    public ArrayList<IDynamicObject> dynamics;
    
    public IDynamicServer(IServerI s){
	server = s.server();
	dynamics = new ArrayList<IDynamicObject>();
    }
    
    public synchronized void add(IObject e){
	for(IDynamicObject d:e.dynamics) add(d);
    }
    
    public synchronized void add(IDynamicObject e){
	if(!dynamics.contains(e)){
	    dynamics.add(e);
	    if(!startedOnce) start(); // here is the point to start the thread.
	}
    }
    
    public int num(){ return dynamics.size(); }
    public IDynamicObject get(int i){ return dynamics.get(i); }
    
    
    public synchronized void remove(int i){ dynamics.remove(i); }
    public synchronized void remove(IDynamicObject d){ dynamics.remove(d); }
    
    public synchronized void clear(){ dynamics.clear(); }
    
    public void pause(){ runningDynamics=false; }
    public void resume(){ runningDynamics=true; }
    
    public void start(){
	thread = new Thread(this);
	runningDynamics=true;
	startedOnce=true;
	thread.start();
    }
    
    public void stop(){
	runningDynamics=false;
	thread=null;
    }
    
    public void run(){
	Thread thisThread = Thread.currentThread();
	while(thread==thisThread){
	    
	    if(runningDynamics){
		synchronized(this){
		    for(int i=0; i<dynamics.size(); i++){
			
			dynamics.get(i).interact(dynamics); //
			
			//for(int j=i+1; j<dynamics.size(); j++) dynamics.get(i).interact(dynamics.get(j));
		    }
		    //for(IDynamicObject d:dynamics){ d.update(); }
		    for(int i=0; i<dynamics.size(); i++){
			dynamics.get(i).update();
		    }
		}
	    }
	    
	    try{
		Thread.sleep(speed);
	    }catch(InterruptedException e){}
	}
    }
}
