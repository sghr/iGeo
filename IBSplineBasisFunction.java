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
   Implementation of Bernstein polynomial for NURBS geometry.
      
   @author Satoru Sugihara
*/
public class IBSplineBasisFunction{ // not implementing IFunction. return type of eval() is different
    public int degree;
    public IBSplineBasisSubFunction[] functions;
    public double[] knots;
    
    public IBSplineBasisFunction(int degree, double[] knots){
	this.degree=degree;
	this.knots = knots;
	init();
    }
    
    public IBSplineBasisFunction(IBSplineBasisFunction bf){
	this.degree=bf.degree;
	//this.knots = bf.knots.clone();
	this.knots = new double[bf.knots.length];
	for(int i=0; i<bf.knots.length; i++){ this.knots[i]=bf.knots[i]; }
	init(bf);
    }
    
    public IBSplineBasisFunction dup(){ return new IBSplineBasisFunction(this); }
    
    public void init(IBSplineBasisFunction bf){
	functions = new IBSplineBasisSubFunction[knots.length-degree-1];
	for(int i=0; i<functions.length; i++){
	    functions[i] = new IBSplineBasisSubFunction(bf.functions[i],knots);
	}
    }
    
    public void init(){
	functions = new IBSplineBasisSubFunction[knots.length-degree-1];
	for(int i=0; i<functions.length; i++)
	    functions[i] = new IBSplineBasisSubFunction(degree,i,knots);
    }
    
    public int index(double x){
	//if(x<domains[0]) return -1; // index -1 means from -infinity to domains[0] 
	//if(x>=domains[domains.length-1]) return domains.length-1;
	
	int min = degree; //0;
	int max = knots.length-1-degree;
	
	//int min = 0;  // what's this for? // is this for the closed curve?
	//int max = knots.length-1;  // what's this for? // is this for the closed curve?
	
	if(x<=knots[min]) return min;
	if(x>=knots[max]) return max-1;
	
	int mid = (min+max)/2;
	
	for(; min<max && ( x<knots[mid] || x>=knots[mid+1] ); mid = (min+max)/2 ){
	    if(x<knots[mid]) max = mid; else min = mid;
	}
	return mid;
    }
    
    
    public double[] eval(double x){
	int index = index(x);
	
	double[] val=new double[degree+1];
	for(int i=0; i<=degree; i++){
	    if( i+index-degree >= 0 && i+index-degree < functions.length )
		val[i] = functions[i+index-degree].functions[degree-i+1].eval(x);
	    else val[i]=0;
	}
	return val;
    }
    
    public double[] eval(int index, double x){
	double[] val=new double[degree+1];
	for(int i=0; i<=degree; i++){
	    if( i+index-degree >= 0 && i+index-degree < functions.length ){
		val[i] = functions[i+index-degree].functions[degree-i+1].eval(x);
		/*
		// debug ...
		IBSplineBasisSubFunction sf = functions[i+index-degree];
		IFunction pf=null;
		try{
		    pf = sf.functions[ degree - i + 1];
		    //IFunction pf = sf.functions[ degree - i ];
		}
		catch(Exception e){
		    IGConsole.p("eval("+index+", "+x+")"); //
		    IGConsole.p(sf.toString());
		    e.printStackTrace();
		}
		val[i] = pf.eval(x);
		*/
	    }
	    else val[i]=0;
	}
	return val;
    }
    
    public void differentiate(){
	for(int i=0; i<functions.length; i++) functions[i].differentiate();
    }
    
    
    public IMatrix[] getMatrices(){
	IMatrix[] matrices = new IMatrix[knots.length - degree*2 -1];
	for(int i=0; i<matrices.length; i++){
	    matrices[i] = new IMatrix(degree+1, degree+1);
	    matrices[i].setZero();
	    
	    double domain1 = knots[i+degree];
	    double domain2 = knots[i+degree+1];
	    double[] coeff = new double[2];
	    coeff[0] = domain1;
	    coeff[1] = domain2-domain1;
	    // normalize function
	    IPolynomialFunction uf = new IPolynomialFunction(coeff);
	    
	    for(int j=0; j<=degree; j++){
		//functions[j+i].functions[degree-j+1];
		IPolynomialFunction pf =
		    (IPolynomialFunction)functions[j+i].functions[degree-j+1];
		// normalize
		pf = pf.substitute(uf);
		
		for(int k=0; k<=degree; k++){
		    matrices[i].set(k, j, pf.coeff[degree-k]);
		}
	    }
	}
	return matrices;
    }
    
    
    public IMatrix getMatrix(int i){
	// i = number of span
	
	IMatrix matrix = new IMatrix(degree+1, degree+1);
	matrix.setZero();
	
	double domain1 = knots[i+degree];
	double domain2 = knots[i+degree+1];
	double[] coeff = new double[2];
	coeff[0] = domain1;
	coeff[1] = domain2-domain1;
	// normalize function
	IPolynomialFunction uf = new IPolynomialFunction(coeff);
	
	for(int j=0; j<=degree; j++){
	    //functions[j+i].functions[degree-j+1];
	    IPolynomialFunction pf =
		(IPolynomialFunction)functions[j+i].functions[degree-j+1];
	    // normalize
	    pf = pf.substitute(uf);
	    for(int k=0; k<=degree; k++) matrix.set(k, j, pf.coeff[degree-k]);
	}
	
	return matrix;
    }
    
    
    public IMatrix getBezierMatrix(int i){
	IMatrix mat = getMatrix(i);
	IMatrix4 bezierMat = new IMatrix4(-1, 3, -3, 1,
					  3, -6,  3, 0,
					  -3, 3,  0, 0,
					  1,  0,  0, 0);
	bezierMat.invert();
	
	if(mat.rowNum()==3){ // in case of degree==2
	    IMatrix m = new IMatrix(4,3);
	    for(int j=0; j<3; j++) m.set(0,j,0);
	    for(int j=0; j<3; j++) m.set(1,j,mat.get(0,j));
	    for(int j=0; j<3; j++) m.set(2,j,mat.get(1,j));
	    for(int j=0; j<3; j++) m.set(3,j,mat.get(2,j));
	    
	    return bezierMat.mul(m); // 4x3 matrix
	}
	if(mat.rowNum()==4){ // in case of degree==3
	    return bezierMat.mul(mat);
	}
	
	// in case of degree>3
	// not implemented...
	
	return null;
    }
    
    
    /**
       Implementation of sub-indexed Bernstein polynominal for NURBS geometry.
       
       @author Satoru Sugihara
    */
    public class IBSplineBasisSubFunction extends IPiecewisePolynomialFunction{
	public int degree;
	public int index;
	public double[] knots;
	
	public IBSplineBasisSubFunction(int deg, int idx, double[] kv){
	    degree=deg;
	    index = idx;
	    knots = kv;
	    init();
	}
	
	public IBSplineBasisSubFunction(IBSplineBasisSubFunction bf, double[] knots){
	    super(bf);
	    degree = bf.degree;
	    index = bf.index;
	    //knots = bf.knots;
	    this.knots = knots; // share knots from parent basis function
	}
	
	public IBSplineBasisSubFunction dup(){
	    return new IBSplineBasisSubFunction(this,knots);
	}
	
	public void init(){
	    if(degree==0){
		double[] dom = new double[2];
		for(int i=0; i<dom.length; i++) dom[i] = knots[index+i];
		super.init(dom);
		double[] coeff = {1};
		IPolynomialFunction f = new IPolynomialFunction(coeff);
		this.setFunction(0, f);
		return;
	    }
	    
	    IBSplineBasisSubFunction bs1=new IBSplineBasisSubFunction(degree-1,index,knots);
	    IBSplineBasisSubFunction bs2=new IBSplineBasisSubFunction(degree-1,index+1,knots);
	    
	    double[] coeff1 = new double[2];
	    coeff1[0] = -knots[index]/(knots[index+degree] - knots[index]);
	    coeff1[1] = 1.0/(knots[index+degree] - knots[index]);
	    IPolynomialFunction p1 = new IPolynomialFunction(coeff1);
	    
	    double[] coeff2 = new double[2];
	    coeff2[0] = knots[index+degree+1]/(knots[index+degree+1]-knots[index+1]);
	    coeff2[1] = -1.0/(knots[index+degree+1] - knots[index+1]);
	    IPolynomialFunction p2 = new IPolynomialFunction(coeff2);
	    
	    bs1.mul(p1);
	    bs2.mul(p2);
	    
	    bs1.add(bs2);
	    
	    super.init(bs1);
	}
	
	
	public void add(IBSplineBasisSubFunction bs){
	    IBSplineBasisSubFunction bs1 = this;
	    IBSplineBasisSubFunction bs2 = bs;
	    
	    if(this.index>bs.index){
		bs1 = bs;
		bs2 = this;
	    }
	    
	    double[] newdomains = new double[degree+2+bs2.index-bs1.index];
	    IFunction[] newfunctions = new IFunction[degree+3+bs2.index-bs1.index];
	    
	    int i=0;
	    for(; i<bs1.domains.length; i++) newdomains[i] = bs1.domains[i];
	    for(; i<(bs2.domains.length + bs2.index - bs1.index); i++)
		newdomains[i] = bs2.domains[i - (bs2.index-bs1.index) ];
	    
	    i=0;
	    for(; i<(bs2.index - bs1.index); i++) newfunctions[i] = bs1.functions[i];
	    for(; i<bs1.functions.length; i++){
		
		if(bs1.functions[i]!=null&&bs2.functions[i-bs2.index+bs1.index]!=null ){
		    newfunctions[i] =
			new IPolynomialFunction((IPolynomialFunction)bs1.functions[i]);
		    ((IPolynomialFunction)newfunctions[i]).add
			((IPolynomialFunction)bs2.functions[i-bs2.index+bs1.index]);
		}
		else if(bs1.functions[i]==null&&bs2.functions[i-bs2.index+bs1.index]!=null)
		    newfunctions[i] = bs2.functions[i-bs2.index+bs1.index];
		else if(bs1.functions[i]!=null&&bs2.functions[i-bs2.index+bs1.index]==null)
		    newfunctions[i] = bs1.functions[i];
		else newfunctions[i] = null;
		
	    }
	    for(; i<(bs2.functions.length + bs2.index - bs1.index); i++)
		newfunctions[i] = bs2.functions[i - (bs2.index-bs1.index) ];
	    
	    domains = newdomains;
	    functions = newfunctions;
	}
	
	public String toString(){
	    return "IBSplineBasisSubFunction: index="+String.valueOf(index)+
		" degree="+String.valueOf(degree) + "\n" +
		super.toString();
	}
	
    }
        
}
