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
   A server to take care of all IDynamicObject. It runs as separate thread.
   
   @author Satoru Sugihara
*/
public class IDynamicServer implements Runnable{
    
    public Thread thread;
    //public int speed = IConfig.dynamicsUpdateSpeed; // in millisecond
    //public int updateRate = (int)(IConfig.updateRate*1000); // in millisecond
    
    public IServer server;
    
    public boolean runningDynamics=false;
    public boolean startedOnce=false;
    
    public ArrayList<IDynamics> dynamics;
    
    public ArrayList<IDynamics> addingDynamics;
    public ArrayList<IDynamics> removingDynamics;
    
    public int duration = -1;
    public int time;
    
    public IDynamicServer(IServerI s){
	server = s.server();
	dynamics = new ArrayList<IDynamics>();
	
	addingDynamics = new ArrayList<IDynamics>();
	removingDynamics = new ArrayList<IDynamics>();
    }
    
    public synchronized void add(IObject e){
	for(IDynamics d:e.dynamics) add(d);
    }
    
    public synchronized void add(IDynamics e){
	
	// when not running, simply adding
	if(!runningDynamics){
	    if(!dynamics.contains(e)){ dynamics.add(e); }
	}
	else{
	    // added object is once buffered in addingDynamics and actually added in the update cycle
	    if(!addingDynamics.contains(e) &&
	       !dynamics.contains(e) ){ // isn't this heavy?
		
		addingDynamics.add(e);
		if(removingDynamics.contains(e)){ removingDynamics.remove(e); }
	    }
	}
	
	/*
	if(!dynamics.contains(e)){
	    dynamics.add(e);
	    //if(IConfig.autoStart && !startedOnce) start(); // here is the point to start the thread. // not any more. it starts the first draw of IPanel
	}
	*/
    }
    
    
    /**************
       returns number of objects in dynamicServer but it
       includes objects to be added and excludes
       objects to be removed in next update cycle. 
    */
    /*
    public int num(){
	return dynamics.size()+addingDynamics.size()-removingDynamics.size();
	//return dynamics.size();
    }
    */
    /*************
       returns number of objects in dynamicServer ignoring 
       objects to be added and to be removed in next update cycle. 
    */
    //public int currentNum(){ return dynamics.size(); }
    /** get number of dynamic objects to be added in the next update cycle */
    public int addingNum(){ return addingDynamics.size(); }
    /** get number of dynamic objects to be removed in the next update cycle */
    public int removingNum(){ return removingDynamics.size(); }
    
    /** get number of current dynamic objects in the server */
    public int num(){ return dynamics.size(); }
    /** get i-th dynamic object in the server */
    public IDynamics get(int i){ return dynamics.get(i); }
    
    //public IDynamicServer updateRate(int rate){ updateRate = rate; return this; }
    //public int updateRate(){ return updateRate; }
    
    public synchronized void remove(int i){
	// removed object is once buffered in removingDynamics and actually removed in the update cycle
	IDynamics d = dynamics.get(i);
	if(!removingDynamics.contains(d)){
	    removingDynamics.add(d);
	    if(addingDynamics.contains(d)){ addingDynamics.remove(d); }
	}
	
	//dynamics.remove(i);
    }
    public synchronized void remove(IDynamics d){
	// removed object is once buffered in removingDynamics and actually removed in the update cycle
	
	if(!removingDynamics.contains(d)){
	    removingDynamics.add(d);
	    if(addingDynamics.contains(d)){ addingDynamics.remove(d); }
	}
	//dynamics.remove(d);
    }
    
    
    public synchronized void clear(){
	addingDynamics.clear();
	removingDynamics.clear();
	dynamics.clear();
    }
    
    public IDynamicServer duration(int dur){ duration = dur; return this; }
    public int duration(){ return duration; }
    
    public IDynamicServer time(int tm){ time = tm; return this; }
    public int time(){ return time; }
    
    public void pause(){ runningDynamics=false; }
    public void resume(){ runningDynamics=true; }
    public boolean isRunning(){ return runningDynamics; }
    
    
    public void start(){
	if(!startedOnce && !runningDynamics && thread==null){
	    thread = new Thread(this);
	    runningDynamics=true;
	    startedOnce=true;
	    time=0;
	    thread.start();
	    IOut.debug(0,"dynamic server started");
	}
    }

    public void startWithoutThread(){
	runningDynamics=true;
	startedOnce=true;
	time=0;
	IOut.debug(0,"dynamic server started");
    }
    
    public void stop(){
	runningDynamics=false;
	thread=null;
	IOut.debug(0,"dynamic server stopped");
    }
    
    /** in case dynamicServer need to start again after stopped. */
    public void reset(){ startedOnce=false; }
    
    
    public void step(){
	
	if(runningDynamics){
	    if(duration>=0 && time>=duration){ stop(); }
	    else{
		synchronized(this){
		    // adding objects
		    if(addingDynamics.size()>0){
			dynamics.addAll(addingDynamics);//any possible exception?
			addingDynamics.clear();
		    }
		    
		    // removing objects
		    if(removingDynamics.size()>0){
			dynamics.removeAll(removingDynamics);//any possible exception?
			removingDynamics.clear();
		    }
		    
		    // preinteract
		    if(IConfig.loopPreinteract&&IConfig.enablePreinteract){
			for(int i=0; i<dynamics.size(); i++){
			    dynamics.get(i).preinteract(dynamics);
			    
			    if(dynamics.get(i).localDynamics()!=null){ // added 20120826
				ArrayList<IDynamics> localDynamics = dynamics.get(i).localDynamics();
				for(int j=0; j<localDynamics.size(); j++){
				    localDynamics.get(j).preinteract(dynamics);
				}
			    }
			}
		    }
		    
		    for(int i=0; i<dynamics.size(); i++){
			// preinteract
			if(!IConfig.loopPreinteract&&IConfig.enablePreinteract){
			    dynamics.get(i).preinteract(dynamics);
			    
			    if(dynamics.get(i).localDynamics()!=null){ // added 20120826
				ArrayList<IDynamics> localDynamics = dynamics.get(i).localDynamics();
				for(int j=0; j<localDynamics.size(); j++){
				    localDynamics.get(j).preinteract(dynamics);
				}
			    }
			}
			
			// interact
			dynamics.get(i).interact(dynamics);
			if(dynamics.get(i).localDynamics()!=null){ // added 20120826
			    ArrayList<IDynamics> localDynamics = dynamics.get(i).localDynamics();
			    for(int j=0; j<localDynamics.size(); j++){
				localDynamics.get(j).interact(dynamics);
			    }
			}
			
			
			// postinteract
			if(!IConfig.loopPostinteract&&IConfig.enablePostinteract){
			    dynamics.get(i).postinteract(dynamics);
			    if(dynamics.get(i).localDynamics()!=null){ // added 20120826
				ArrayList<IDynamics> localDynamics = dynamics.get(i).localDynamics();
				for(int j=0; j<localDynamics.size(); j++){
				    localDynamics.get(j).postinteract(dynamics);
				}
			    }
			}
		    }
		    
		    // preupdate is executed before post interact to update force first and velocity second. // updated 20120826
		    // preupdate
		    if(IConfig.loopPreupdate&&IConfig.enablePreupdate){
			for(int i=0; i<dynamics.size(); i++){
			    dynamics.get(i).preupdate();
			    if(dynamics.get(i).localDynamics()!=null){ // added 20120826
				ArrayList<IDynamics> localDynamics = dynamics.get(i).localDynamics();
				for(int j=0; j<localDynamics.size(); j++){
				    localDynamics.get(j).preupdate();
				}
			    }
			}
		    }
		    
		    // postinteract
		    if(IConfig.loopPostinteract&&IConfig.enablePostinteract){
			for(int i=0; i<dynamics.size(); i++){
			    dynamics.get(i).postinteract(dynamics);
			    if(dynamics.get(i).localDynamics()!=null){ // added 20120826
				ArrayList<IDynamics> localDynamics = dynamics.get(i).localDynamics();
				for(int j=0; j<localDynamics.size(); j++){
				    localDynamics.get(j).postinteract(dynamics);
				}
			    }
			}
		    }
		    
		    // if anything is removed in interact (or preinteract/postinteract) process. // can this be any possible problem?
		    if(removingDynamics.size()>0){
			dynamics.removeAll(removingDynamics);//any possible exception?
			removingDynamics.clear();
		    }
		    
		    
		    //for(IDynamics d:dynamics){ d.update(); }
		    for(int i=0; i<dynamics.size(); i++){
			// preupdate
			if(!IConfig.loopPreupdate&&IConfig.enablePreupdate){
			    dynamics.get(i).preupdate();
			    if(dynamics.get(i).localDynamics()!=null){ // added 20120826
				ArrayList<IDynamics> localDynamics = dynamics.get(i).localDynamics();
				for(int j=0; j<localDynamics.size(); j++){
				    localDynamics.get(j).preupdate();
				}
			    }
			}
			
			// update
			dynamics.get(i).update();
			if(dynamics.get(i).localDynamics()!=null){ // added 20120826
			    ArrayList<IDynamics> localDynamics = dynamics.get(i).localDynamics();
			    for(int j=0; j<localDynamics.size(); j++){
				localDynamics.get(j).update();
			    }
			}
			
			// postupdate
			if(!IConfig.loopPostupdate&&IConfig.enablePostupdate){
			    dynamics.get(i).postupdate();
			    if(dynamics.get(i).localDynamics()!=null){ // added 20120826
				ArrayList<IDynamics> localDynamics = dynamics.get(i).localDynamics();
				for(int j=0; j<localDynamics.size(); j++){
				    localDynamics.get(j).postupdate();
				}
			    }
			}
		    }
		    
		    // postupdate
		    if(IConfig.loopPostupdate&&IConfig.enablePostupdate){
			for(int i=0; i<dynamics.size(); i++){
			    dynamics.get(i).postupdate();
			    if(dynamics.get(i).localDynamics()!=null){ // added 20120826
				ArrayList<IDynamics> localDynamics = dynamics.get(i).localDynamics();
				for(int j=0; j<localDynamics.size(); j++){
				    localDynamics.get(j).postupdate();
				}
			    }
			}
		    }
		}
		time++;
		IOut.debug(20,"time="+time); //
	    }
	}
    }
    
    
    public void run(){
	Thread thisThread = Thread.currentThread();
	while(thread==thisThread){
	    
	    step();
	    
	    try{
		//Thread.sleep(updateRate);
		Thread.sleep((int)(IConfig.updateRate*1000));
	    }catch(InterruptedException e){}
	}
    }
    
}
