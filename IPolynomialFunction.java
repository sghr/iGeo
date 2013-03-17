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
   Implementation of IFunction with polynomial.
   
   @author Satoru Sugihara
*/
public class IPolynomialFunction implements IFunction{
    public int degree;
    public double[] coeff;
    
    public IPolynomialFunction(int deg){ degree=deg; coeff=new double[degree+1]; }
    
    // coef[i] = coefficient of x^i
    public IPolynomialFunction(int deg, double[] coef){
	degree = deg;
	coeff = coef;
	if(coeff.length != degree+1)
	    IOut.p("ERROR: length of coefficients is wrong"); 
    }
    
    public IPolynomialFunction(IPolynomialFunction pf){
	degree = pf.degree;
	coeff = new double[degree+1];
	for(int i=0; i<=degree; i++){ coeff[i] = pf.coeff[i]; }
    }
    
    public IPolynomialFunction(){}
    
    public IPolynomialFunction(double[] coef){
	coeff = coef;
	degree = coeff.length-1;
    }
    
    public IPolynomialFunction dup(){ return new IPolynomialFunction(this); }
    
    public void set(int index, double v){ coeff[index] = v; }
    
    public double eval(double x){
	double retval=coeff[0];
	for(int i=1; i<=degree; i++) retval += coeff[i]*Math.pow(x,i);
	return retval;	
    }
    
    public void setDegree(int d){ degree=d; }
    
    public void mul(double a){ for(int i=0; i<=degree; i++) coeff[i] *= a; }
    
    public void mul(IPolynomialFunction p){
	//for(int i=0; i<=degree; i++){ coeff[i] *= a; }
	
	int newdeg = this.degree + p.degree;
	double[] newcoeff = new double[newdeg+1];
	
	for(int i=0; i<=newdeg; i++) newcoeff[i]=0;
	
	for(int i=0; i<=degree; i++)
	    for(int j=0; j<=p.degree; j++) newcoeff[i+j] += coeff[i]*p.coeff[j];
	
	degree = newdeg;
	coeff = newcoeff;
    }
    
    public void add(IPolynomialFunction p){
	if(p.degree > degree){
	    degree = p.degree;
	    double[] oldCoeff = coeff;
	    coeff = new double[p.degree+1];
	    for(int i=0; i<=p.degree; i++){
		if(i<oldCoeff.length) coeff[i] = oldCoeff[i];
		else coeff[i]=0;
	    }
	}
	for(int i=0; i<=p.degree; i++) coeff[i] += p.coeff[i];
    }
    
    public void differentiate(){
	if(degree==0){
	    double[] newcoeff = new double[1];
	    newcoeff[0] = 0;
	    return;
	    //return new IPolynomialFunction(newcoeff);
	}
	double[] newcoeff = new double[degree];
	for(int i=0; i<degree; i++){ newcoeff[i] = coeff[i+1]*(i+1); }
	
	degree--;
	coeff = newcoeff;
	//return new IPolynomialFunction(newcoeff);
    }
    
    public IPolynomialFunction power(int p){
	IPolynomialFunction f;
	if(p<0) return null;
	if(p==0){
	    f = new IPolynomialFunction(0);
	    f.set(0,1);
	    return f;
	}
	f = new IPolynomialFunction(this);
	for(int i=0; i<(p-1); i++) f.mul(this);
	
	return f;
    }
    
    public IPolynomialFunction substitute(IPolynomialFunction f){
	IPolynomialFunction ff = new IPolynomialFunction(degree*f.degree);
	for(int i=0; i<=degree; i++){
	    IPolynomialFunction tf = f.power(degree-i);
	    tf.mul(coeff[degree-i]);
	    ff.add(tf);
	}
	return ff;
    }
    
    public String toString(){
	if(coeff==null) return super.toString();
	String str = "";
	for(int i=degree; i>=0; i--){
	    if(coeff[i]!=0 && i<degree ) str += " ";
	    if( (coeff[i]>0) && (i<degree) ) str += "+";
	    if(coeff[i]!=0){
		str += String.valueOf(coeff[i]);
		if( i>0 ) str += "x";
		if( i>1 ) str += "^" + String.valueOf(i);
	    }
	}
	return str;
    }
    
    
}
