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

import java.util.ArrayList;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import java.awt.*;

import igeo.*;
import igeo.io.IIO;

/**
   A root GUI object of iGeo managing all IPane instance.
   An instance IG is keyed by IPanel object when it's in Graphic mode.
   
   @author Satoru Sugihara
*/
public class IPanel extends IComponent implements IPanelI /*IServerI*/ , MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, FocusListener, ComponentListener, WindowListener{
    
    public ArrayList<IPane> panes;
    
    public IG ig;
    
    public IPane currentMousePane=null;
    
    /** to control parent GUIs */
    //public IPanelAdapter adapter;
    
    /** AWT parent component */
    public Container parentContainer; 
    
    //public IPane fullScreenPane=null;
    //public int fullPaneOrigX, fullPaneOrigY, fullPaneOrigWidth, fullPaneOrigHeight;
    
    public IBounds bounds;
    public int serverStateCount=-1;
    
    public boolean startDynamicServer=true;
    
    public boolean firstDraw=true;

    public boolean skipAutoFocus=false;
    
    public boolean sizeChanged=false;
    
    public IPanel(int x, int y, int width, int height){
	super(x,y,width,height);
	panes = new ArrayList<IPane>();
	//this.ig = ig;
    }
    
    public void setIG(IG ig){
	this.ig = ig;
	//for(int i=0; i<panes.size(); i++) panes.get(i).setIG(ig);
    }
    public IG getIG(){ return ig; }
    
    public IServer server(){ return ig.server(); }

    public void setParent(Container container){
	parentContainer = container;
    }
    //public void setAdapter(IPanelAdapter adp){ adapter = adp; }
        
    public void addPane(IPane p){
	panes.add(p);
	p.setPanel(this);
	//if(ig!=null) p.setIG(ig);
    }
    
    public IPane pane(int i){
	if(panes==null || i<0 || i>=panes.size()) return null;
	return panes.get(i);
    }
    
    public int paneNum(){
	if(panes==null) return 0;
	return panes.size();
    }
    
    public void removePane(int i){ panes.remove(i); }
    public void clearPane(){ panes.clear(); }

    public void setVisible(boolean v){ for(int i=0; i<panes.size(); i++) panes.get(i).setVisible(v); }
    //public void show(){ for(int i=0; i<panes.size(); i++) panes.get(i).show(); }
    //public void hide(){ for(int i=0; i<panes.size(); i++) panes.get(i).hide(); }
    
    /** focus on all pane */
    public void focus(){
	for(int i=0; i<panes.size(); i++) panes.get(i).focus(); 
    }
    
    public void setSize(int w, int h){
	int origW = width;
	int origH = height;
	
	for(int i=0; i<panes.size(); i++){
	    int nx = (int)(panes.get(i).getX()*w/origW);
	    int ny = (int)(panes.get(i).getY()*h/origH);
	    int nw = (int)(panes.get(i).getWidth()*w/origW);
	    int nh = (int)(panes.get(i).getHeight()*h/origH);
	    panes.get(i).setBounds(nx,ny,nw,nh);
	}
	width=w;
	height=h;
	
	sizeChanged=true;
    }
    
    
    public void startDynamicServer(){
	if(ig!=null &&ig.dynamicServer()!=null &&
	   (ig.dynamicServer().num()>0 || ig.dynamicServer().addingNum()>0)){
	    if(IConfig.syncDrawAndDynamics){ ig.dynamicServer().startWithoutThread(); }
	    else{ ig.dynamicServer().start(); }
	    startDynamicServer=false;
	}
    }
    
    public void skipAutoFocus(){ skipAutoFocus=true; }
    
    public void predraw(IGraphics g){
	if(firstDraw){
	    if(ig!=null && IConfig.autoFocusAtStart && !skipAutoFocus){ ig.focusView(); skipAutoFocus=true; }
	    firstDraw=false;
	}
	if(startDynamicServer){
	    // here is a point to start dynamicServer
	    startDynamicServer();
	}
	if(sizeChanged){
	    sizeChanged=false;
	    g.firstDraw(true); //redraw bg if IConfig.clearBG=false
	}
    }
    
    public void draw(IGraphics g){
	// some initialization process
	predraw(g);
	
	for(int i=0; i<panes.size(); i++){ 
	    synchronized(IG.lock){ // shouldnt this be "ig"?
		if(panes.get(i).isVisible()){ panes.get(i).draw(g); }
	    }
	}
	postdraw(g);
    }
    
    public void postdraw(IGraphics g){
	
	if(IConfig.deleteGraphicObjectsAfterDraw &&
	   ig!=null && ig.server().graphicServer()!=null){
	    ig.server().graphicServer().clearObjects();
	}
	
	
	if(IConfig.syncDrawAndDynamics && !startDynamicServer &&
	   ig!=null && ig.dynamicServer()!=null){
	    ig.dynamicServer().step();
	}
	
	if(g.firstDraw()){ g.firstDraw(false); }
    }
    
    
    
    public IPane getPaneAt(int x, int y){
	//for(IPane p: panes) if(p.isVisible()&&p.contains(x,y)) return p;
	// to match with drawing order in case they overlap and some panes come to the front
	for(int i=panes.size()-1; i>=0; i--)
	    if(panes.get(i).isVisible()&&panes.get(i).contains(x,y)) return panes.get(i);
	return null;
    }
    
    public IPane getPaneAt(IMouseEvent e){ return getPaneAt(e.getX(),e.getY()); }
    
    
    /** returns current pane; if null, it returns first pane. */
    public IPane currentPane(){
	if(currentMousePane!=null) return currentMousePane;
	return panes.get(0);
    }
    
    
    public void mousePressed(MouseEvent e){
	//IG.err();
	
	IMouseEvent me = new IMouseEvent(e);
	IPane p = getPaneAt(me);
	if(p!=null){
	    currentMousePane = p;
	    p.mousePressed(me);
	}
	else{
	    IOut.err("no pane"); //
	}
    }
    public void mouseReleased(MouseEvent e){
	IMouseEvent me = new IMouseEvent(e);
	IPane p=null;
	if(currentMousePane!=null){
	    //p = currentMousePane;
	    currentMousePane.mouseReleased(me);
	    //currentMousePane = getPaneAt(e); // update
	}
	else{
	    p = getPaneAt(me);
	    if(p!=null){
		//currentMousePane = null;
		p.mouseReleased(me);
		currentMousePane = p;
	    }
	}
    }
    public void mouseClicked(MouseEvent e){
	IMouseEvent me = new IMouseEvent(e);
	IPane p = getPaneAt(me);
	if(p!=null){ p.mouseClicked(me); }
	
	//if(fullScreenPane==null){ if(p!=null) enableFullScreen(p); }
	//else disableFullScreen();
	
	currentMousePane = p; // update
    }
    public void mouseEntered(MouseEvent e){
	//IG.err();
	
	//IPane p = getPaneAt(e);
	//if(p!=null){ currentMousePane = p; }
	
	//IPane p = getPaneAt(e);
	//if(p!=null){ p.mouseEntered(e); }
    }
    public void mouseExited(MouseEvent e){
	//IPane p = getPaneAt(e);
	//if(p!=null){ p.mouseExited(e); }
    }
    public void mouseMoved(MouseEvent e){
	//IG.err();
	
	IMouseEvent me = new IMouseEvent(e);
	IPane p = getPaneAt(me);
	if(p!=null){
	    p.mouseMoved(me);
	}
    }
    public void mouseDragged(MouseEvent e){
	//IG.err();
	
	IMouseEvent me = new IMouseEvent(e);
	IPane p=null;
	if(currentMousePane!=null){ p = currentMousePane; }
	else{ p = getPaneAt(me); }
	if(p!=null){
	    p.mouseDragged(me);
	}
    }
    
    
    public void mouseWheelMoved(MouseWheelEvent e){
	//IG.err();
	
	IMouseWheelEvent me = new IMouseWheelEvent(e);
	if(currentMousePane!=null){ currentMousePane.mouseWheelMoved(me); }
	/*
	IPane p = getPaneAt(e);
	if(p!=null){
	    currentMousePane=p;
	    currentMousePane.mouseWheelMoved(e);
	}
	*/
    }
    
    
    public void keyPressed(KeyEvent e){
	//IG.err();
	
	int key = e.getKeyCode();
	boolean shift = e.isShiftDown();
	boolean control = e.isControlDown();

	// for mac
	if(e.isMetaDown()){ control = true; }
	
	
	if(key==KeyEvent.VK_F && /*!shift &&*/!control){
	    currentMousePane.focus();
	}
	/*
	else if(key==KeyEvent.VK_F && shift &&!control){
	    setBounds();
	    currentMousePane.focus();
	}
	*/
	else if(key==KeyEvent.VK_S&& !shift &&!control){
	    // fill & wireframe
	    currentMousePane.getView().mode().setDrawMode(true,true,false);
	}
	else if(key==KeyEvent.VK_S&& shift &&!control){
	    // toggle fill shading
	    //currentMousePane.getView().mode().toggleFill();
	    // fill 
	    currentMousePane.getView().mode().setDrawMode(false,true,false);
	}
	else if(key==KeyEvent.VK_W&& !shift &&!control){
	    // wireframe
	    currentMousePane.getView().mode().setDrawMode(true,false,false);
	}
	/*
	else if(key==KeyEvent.VK_W&& shift &&!control){
	    // toggle wireframe
	    currentMousePane.getView().mode().toggleWireframe();
	}
	*/
	else if(key==KeyEvent.VK_T&& !shift &&!control){
	    // transparent fill & wireframe
	    currentMousePane.getView().mode().setDrawMode(true,true,true);
	}
	else if(key==KeyEvent.VK_T&& shift &&!control){
	    // toggle transparency
	    //currentMousePane.getView().mode().toggleTransparent();
	    // transparent fill
	    currentMousePane.getView().mode().setDrawMode(false,true,true);
	}
	//else if(key==KeyEvent.VK_Q && control&& !shift){
	else if( (key==KeyEvent.VK_W || key==KeyEvent.VK_Q)
		 && control&& !shift){ // to match with Processing closing behavior
	    if(!ig.isOnline()){
		System.exit(0); // temporary.
	    }
	    // if applet, this doesn't quit by key
	}
	else if(key==KeyEvent.VK_S && control&& !shift){
	    // save
	    saveDialog();
	}
	else if(key==KeyEvent.VK_ENTER && !control&& !shift){

	    // toggle running
	    if(ig.isDynamicsRunning()){
		ig.pauseDynamics();
	    }
	    else{
		ig.resumeDynamics();
	    }
	    //if(ig.isDynamicsRunning()){ ig.pauseDynamics(); }
	    //else{ ig.resumeDynamics(); }
	}
	
	//if(currentMousePane!=null){ currentMousePane.keyPressed(e); }
	if(currentMousePane!=null){ currentMousePane.keyPressed(new IKeyEvent(e)); }
    }
    public void keyReleased(KeyEvent e){
	//IG.err();
	if(currentMousePane!=null){ currentMousePane.keyReleased(new IKeyEvent(e)); }
    }
    public void keyTyped(KeyEvent e){
	//IG.err();
	if(currentMousePane!=null){ currentMousePane.keyTyped(new IKeyEvent(e)); }
    }
    
    public void focusLost(FocusEvent e){
	//IG.err();
    }
    public void focusGained(FocusEvent e){
	//IG.err();
    }
    
    
    public void componentHidden(ComponentEvent e){
	//IG.err();
    }
    public void componentMoved(ComponentEvent e){
	//IG.err();
    }
    public void componentResized(ComponentEvent e){
	
	//IG.err("componentResized: e = "+e); //
	
	int w = e.getComponent().getBounds().width;
	int h = e.getComponent().getBounds().height;
	
	//IG.err("componentResized: "+w+"x"+h); //
	
	setSize(w,h);

	
	//IG.err("componentResized: end"); //
    }
    public void componentShown(ComponentEvent e){
	//IG.err();
    }

    
    public void windowActivated(WindowEvent e){
    }
    public void windowClosed(WindowEvent e){
	if(ig.online){
	    // nothing, yet.
	    
	    //if(adapter!=null){ adapter.close(); }
	    
	    /*
	    if(parentCotainer!=null && parentContainer instanceof Applet){
		((Applet)parentContainer).stop(); // to prevent stopping whole java vm when other applets exist.
	    }
	    */
	}
	else{
	    System.exit(0);
	}
    }
    public void windowClosing(WindowEvent e){
    }
    public void windowDeactivated(WindowEvent e){
    }
    public void windowDeiconified(WindowEvent e){
    }
    public void windowIconified(WindowEvent e){
    }
    public void windowOpened(WindowEvent e){
    }
    
    
    
    
	    
    
    public IBounds getBounds(){ return bounds; } 
    
    public void setBounds(){
	if(bounds==null) bounds = new IBounds();
	if(ig.server().stateCount()!=serverStateCount){
	    if(ig.server.objectNum()>0){
		bounds.setObjects(ig.server());
		if(bounds.min!=null && bounds.max!=null){
		    for(int i=0; i<panes.size(); i++){ 
			panes.get(i).navigator().setRatioByBounds(bounds); // added 20130519
			panes.get(i).getView().setParametersByBounds(bounds); // added 20130519
		    }
		}
	    }
	    serverStateCount = ig.server().stateCount();
	}
    }

    
    public void saveDialog(){
	try{
	    // create folder of the base path if not existing; what if it's applet or mobile environment
	    if(ig.basePath!=null){
		File baseDir = new File(ig.basePath);
		if(!baseDir.isDirectory()){
		    IOut.debug(20, "creating directory"+baseDir.toString());
		    if(!baseDir.mkdir()){
			IOut.err("failed to create directory: "+baseDir.toString());
		    }
		}
	    }
	}catch(Exception ex){ ex.printStackTrace(); }
	
	File file = chooseFile(new String[][]{ new String[]{ "3dm", "3DM" },
					       new String[]{ "obj", "Obj", "OBJ" },
					       new String[]{ "ai", "Ai", "AI" } },
			       new String[]{ "Rhinoceros 3D file v4 (.3dm)",
					     "Wavefront OBJ file (.obj)",
					     "Adobe Illustrator file (.ai) (line&polyline only)" },
			       "Save",
			       true,
			       ig.basePath,
			       null);
	
	//if(file!=null) ig.saveFile(file.getAbsolutePath());
	if(file!=null){
	    if(IIO.getFileType(file.getName()) == IIO.FileType.AI){
		new AIScaleDialog(file);
		return;
	    }
	    IIO.save(file, ig);
	}
	
	/*
	boolean canceled = false;
	do{
	    JFileChooser jfc = new JFileChooser(ig.basePath);
	    int retval = jfc.showSaveDialog(null);
	    if(retval==JFileChooser.APPROVE_OPTION){
		File file = jfc.getSelectedFile();
		ig.saveFile(file.getAbsolutePath());
	    }
	}while(canceled);
	*/
    }
    
    
    public class AIScaleDialog extends JDialog implements ActionListener{
	
	public JTextField scalefield;
	//double scale=1.0;
	public File file;
	
	public AIScaleDialog(File f){
	    super();
	    file = f;
	    
	    getContentPane().setLayout(new GridLayout(2,3));
	    boolean axon = currentPane().getView().isAxonometric();
	    if(axon){
		String unitStr = server().unit().toString().toLowerCase();
		getContentPane().add(new JLabel(" scale: 1 "+unitStr+" = " ));
	    }
	    else{
		getContentPane().add(new JLabel(" scale: 1 pixel = " ));
	    }
	    
	    String defaultScaleText = String.valueOf(IConfig.defaultAIExportScale); //"0.1"; //"0.01"; //"1";
	    scalefield = new JTextField(defaultScaleText, 12);
	    getContentPane().add(scalefield);
	    
	    if(axon){
		getContentPane().add(new JLabel("inch"));
	    }
	    else{
		getContentPane().add(new JLabel("pt"));
	    }
	    
	    JButton btn1 = new JButton("OK");
	    btn1.addActionListener(this);
	    getContentPane().add(btn1);
	    
	    JButton btn2 = new JButton("Cancel");
	    btn2.addActionListener(this);
	    getContentPane().add(btn2);
	    
	    setTitle("Set Scale");
	    
	    setSize(400, 90);
	    
	    setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
	    if(e.getActionCommand().equals("OK")){
		
		String scalestr = scalefield.getText();
		
		try{
		    
		    double scale = Double.parseDouble(scalestr);
		    IIO.saveAI(file, ig, scale);
		    
		}catch(Exception ex){
		    ex.printStackTrace();
		}
	    }
	    setVisible(false); //hide();
	}
	
	//public double scale(){ return scale; }
	
    }
    
    
    // file chooser dialog
    public File chooseFile(String acceptableExtension,
			   String extensionDescription,
                           String approveButtonText,
			   boolean writing,
			   String defaultPath,
			   File defaultFile){
        return chooseFile(IFileFilter.createCaseVariation(acceptableExtension),
                          extensionDescription, approveButtonText,writing,
			  defaultPath, defaultFile);
    }
    
    public File chooseFile(String[] acceptableExtensions, String extensionDescription,
                           String approveButtonText, boolean writing,
			   String defaultPath, File defaultFile){
	String[][] extensions = new String[1][];
	extensions[0] = acceptableExtensions;
	String[] description = new String[1];
	description[0] = extensionDescription;
	
	return chooseFile(extensions, description, approveButtonText, writing, defaultPath, defaultFile);
    }
    
    public File chooseFile(String[][] acceptableExtensions,
			   String[] extensionDescriptions,
                           String approveButtonText, boolean writing,
			   String defaultPath, File defaultFile){
        File file=null;
        boolean canceled=false;
	
	//if(defaultPath==null) defaultPath=null; //".";
	
        file = defaultFile;
	
	IFileFilter[] filters = new IFileFilter[acceptableExtensions.length];
        for(int i=0; i<acceptableExtensions.length; i++){
	    for(int j=0; j<acceptableExtensions[i].length; j++){
		if(!acceptableExtensions[i][j].startsWith(".")){
		    acceptableExtensions[i][j] = "." + acceptableExtensions[i][j];
		}
	    }
	    String description = "";
	    if(extensionDescriptions!=null && extensionDescriptions.length > i &&
	       extensionDescriptions[i] != null){
		description = extensionDescriptions[i];
	    }
	    filters[i] = new IFileFilter(acceptableExtensions[i], description);
        }
	
	do{
            canceled=false; // in the case once canceled
            JFileChooser chooser;
	    if(defaultPath==null){ chooser = new JFileChooser(); }
	    else{ chooser = new JFileChooser(defaultPath); }
	    
	    for(int i=filters.length-1; i>=0; i--){ // opposite order
		chooser.addChoosableFileFilter(filters[i]);
	    }
	    chooser.setFileFilter(filters[0]); // default format (filter)
	    
            if(file!=null){
                chooser.setCurrentDirectory(new File(file.getParent()));
                chooser.setSelectedFile(file);
            }
            
            int result = chooser.showDialog(null, approveButtonText);
            
            if(result==JFileChooser.APPROVE_OPTION){
                file = chooser.getSelectedFile();
                String filename = file.toString();
                boolean endWithExtension=false;
		
		javax.swing.filechooser.FileFilter currentFilter = chooser.getFileFilter();
		int currentFilterIndex = -1;
		for(int i=0; i<filters.length && currentFilterIndex<0; i++){
		    if(currentFilter==filters[i]) currentFilterIndex=i;
		}
		
		for(int i=0; currentFilterIndex>=0 &&
			(i<acceptableExtensions[currentFilterIndex].length) &&
			!endWithExtension; i++){
                    if(filename.endsWith(acceptableExtensions[currentFilterIndex][i])) endWithExtension=true;
                }
		
		if(currentFilterIndex>=0 && ! endWithExtension ){
		    // should it be changed?
                    //IOut.err("extension of file is invalid: "+filename);
                    filename = filename.concat(acceptableExtensions[currentFilterIndex][0]);
                    file = new File(filename);
                    //IOut.err("renamed to "+ file.toString());
                }
		
                if(writing){
                    if(file.exists()){
                        //IOut.err("overwiting file?: "+file.toString());
                        String message =
                            "file is existing\ndo you want to overwrite it?";
                        // Modal dialog with yes/no button
                        int answer = JOptionPane.showConfirmDialog(null, message);
                        if (answer == JOptionPane.YES_OPTION);
                        else if (answer == JOptionPane.NO_OPTION) return null;
                        else if (answer == JOptionPane.CANCEL_OPTION) canceled=true;
                        else return null;
                    }
                }
                else{
                    if(!file.exists()){
                        IOut.err("file doesn't exist "+file.toString());
                        String message = "file doesn't exist: "+file.toString();
                        // Modal dialog with yes/no button
                        JOptionPane.showMessageDialog(null, message);
                        canceled=true;
                    }
                }
            }
            else{ file=null; }
        }while(canceled);
        return file;
    }
}
