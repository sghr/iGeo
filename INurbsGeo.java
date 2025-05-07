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
   Base class of NURBS geometry providing miscellaneous methods to be used in child classes.
   @see ICurveGeo
   @see ISurfaceGeo

   @author Satoru Sugihara
*/
public class INurbsGeo extends IParameterObject{


    // default constractor creates parent IGElement
    //INurbsGeo(){ super(null); }

    /** Default constractor doesn't create parent IGElement
     */
    INurbsGeo(){ super(); }

    /**
       @param h if h is null, this doesn't create parent IGElement
    */
    INurbsGeo(IServerI h){
	super();
	// when do you need to register Geo class in the server?
	if(h!=null) createObject(h);
    }


    public static void normalizeKnots(double[] knots, double ustart, double uend){
	for(int i=0; i<knots.length; i++){
            knots[i] -= ustart;
            knots[i] /= (uend-ustart);
        }
    }
    public static double[] createKnots(int degree, int num, boolean closed){
	if(closed) return createClosedKnots(degree,num);
	return createKnots(degree,num);
    }

    /**
       Creating generic knots. Knot values are already normalized.
    */
    public static double[] createKnots(int degree, int num){
        double[] knots = new double[degree+num+1];
        int k,m;
	double inc = 1.0/(num-degree);
        for(k=0; k<=degree; k++) knots[k]=0;
        //for(m=1; k<num; k++,m++) knots[k]=(double)m/(num-degree);
	for(m=1; k<num; k++,m++) knots[k]=m*inc;
        for(; k<=(degree+num); k++) knots[k]=1.;
	return knots;
    }
    public static double[] createClosedKnots(int degree, int num){
	double[] knots = new double[degree+num+1];
	double inc = 1.0/(num-degree);
	//for(int k=0,m=-degree; k<=(degree+num); k++,m++) knots[k]=(double)m/(num-degree);
	for(int k=0,m=-degree; k<=(degree+num); k++,m++) knots[k]=m*inc;
	return knots;
    }

    public static IVecI[] createClosedCP(IVecI[] cpts, int degree){
	int headNum = (degree-1)/2;
	int tailNum = degree/2 + 1;
	int len = cpts.length;

	if(cpts[0]==cpts[cpts.length-1] || cpts[0].eq(cpts[cpts.length-1])){
	    if(cpts[0]==cpts[cpts.length-1]) cpts[cpts.length-1] = cpts[0].dup();
	    if(degree==1) return cpts;
	    len--;
	}

	IVecI[] cpts2 = new IVecI[len+degree];
	for(int i=0; i<cpts2.length; i++){
	    if(i<headNum) cpts2[i] = cpts[(i-headNum+len)%len].dup();
	    else if(i<cpts2.length-tailNum) cpts2[i] = cpts[i-headNum];
	    else cpts2[i] = cpts[(i-(cpts2.length-tailNum))%len].dup(); // tail
	}
	return cpts2;

	/*
	if(cpts[0]==cpts[cpts.length-1] || cpts[0].eq(cpts[cpts.length-1])){
	    if(degree==1) return cpts;
	    IVecI[] cpts2 = new IVecI[cpts.length+degree-1];
	    int headNum = (degree-1)/2;
	    int tailNum = degree/2;
	    for(int i=0; i<cpts2.length; i++){
		if(i<headNum) cpts2[i] = cpts[(i-headNum+cpts.length-1)%(cpts.length-1)];
		else if(i<cpts2.length-tailNum) cpts2[i] = cpts[i-headNum];
		else cpts2[i] = cpts[(i+1-(cpts2.length-tailNum))%(cpts.length-1)];
	    }
	    return cpts2;
	}
	IVecI[] cpts2 = new IVecI[cpts.length+degree];
	int headNum = (degree-1)/2;
	int tailNum = degree/2 +1;
	for(int i=0; i<cpts2.length; i++){
	    if(i<headNum) cpts2[i] = cpts[(i-headNum + cpts.length)%cpts.length];
	    else if(i<cpts2.length-tailNum) cpts2[i] = cpts[i-headNum];
	    else cpts2[i] = cpts[(i-(cpts2.length-tailNum))%cpts.length];
	}
	return cpts2;
	*/
    }


    // close in U direction for surface
    public static IVecI[][] createClosedCPInU(IVecI[][] cpts, int udeg){
      int headNum = (udeg-1)/2;
      int tailNum = udeg/2 + 1;
      int ulen = cpts.length;
      int vlen = cpts[0].length;
      boolean isEdgeClosed=true;
      for(int i=0; i<vlen && isEdgeClosed; i++){
        if(cpts[0][i]!=cpts[ulen-1][i] && !cpts[0][i].eq(cpts[ulen-1][i])){
          isEdgeClosed=false;
        }
      }
      if(isEdgeClosed){
        if(udeg==1){
          for(int i=0; i<vlen ; i++)
          if(cpts[0][i] == cpts[ulen-1][i]) cpts[ulen-1][i] = cpts[0][i].dup();
          return cpts;
        }
        ulen--;
      }
      IVecI[][] cpts2 = new IVecI[ulen+udeg][vlen];
      for(int i=0; i<cpts2.length; i++){
        for(int j=0; j<vlen; j++){
          if(i<headNum) cpts2[i][j] = cpts[(i-headNum+ulen)%ulen][j].dup();
          else if(i<cpts2.length-tailNum) cpts2[i][j] = cpts[i-headNum][j];
          else cpts2[i][j] = cpts[(i-(cpts2.length-tailNum))%ulen][j].dup(); // tail
        }
      }
      return cpts2;
    }

    // close in V direction for surface
    public static IVecI[][] createClosedCPInV(IVecI[][] cpts, int vdeg){
      int headNum = (vdeg-1)/2;
      int tailNum = vdeg/2 + 1;
      int ulen = cpts.length;
      int vlen = cpts[0].length;
      boolean isEdgeClosed=true;
      for(int i=0; i<ulen && isEdgeClosed; i++){
        if(cpts[i][0]!=cpts[i][vlen-1] && !cpts[i][0].eq(cpts[i][vlen-1])){
          isEdgeClosed=false;
        }
      }
      if(isEdgeClosed){
        if(vdeg==1){
          for(int i=0; i<ulen ; i++)
          if(cpts[i][0] == cpts[i][vlen-1]) cpts[i][vlen-1] = cpts[i][0].dup();
          return cpts;
        }
        vlen--;
      }
      IVecI[][] cpts2 = new IVecI[ulen][vlen+vdeg];
      for(int i=0; i<ulen; i++){
        for(int j=0; j<cpts2[i].length; j++){
          if(j<headNum) cpts2[i][j] = cpts[i][(j-headNum+vlen)%vlen].dup();
          else if(j<cpts2[i].length-tailNum) cpts2[i][j] = cpts[i][j-headNum];
          else cpts2[i][j] = cpts[i][(j-(cpts2[i].length-tailNum))%vlen].dup(); // tail
        }
      }
      return cpts2;
    }

    public static boolean isUClosed(IVecI[][][] cpts){
      int ulen = cpts.length;
      int vlen = cpts[0].length;
      int wlen = cpts[0][0].length;
      for(int i=0; i<vlen; i++){
        for(int j=0; j<wlen; j++){
          if(cpts[0][i][j]!=cpts[ulen-1][i][j] && !cpts[0][i][j].eq(cpts[ulen-1][i][j])){
            return false;
          }
        }
      }
      return true;
    }
    public static boolean isVClosed(IVecI[][][] cpts){
      int ulen = cpts.length;
      int vlen = cpts[0].length;
      int wlen = cpts[0][0].length;
      for(int i=0; i<ulen; i++){
        for(int j=0; j<wlen; j++){
          if(cpts[i][0][j]!=cpts[i][vlen-1][j] && !cpts[i][0][j].eq(cpts[i][vlen-1][j])){
            return false;
          }
        }
      }
      return true;
    }
    public static boolean isWClosed(IVecI[][][] cpts){
      int ulen = cpts.length;
      int vlen = cpts[0].length;
      int wlen = cpts[0][0].length;
      for(int i=0; i<ulen; i++){
        for(int j=0; j<vlen; j++){
          if(cpts[i][j][0]!=cpts[i][j][wlen-1] && !cpts[i][j][0].eq(cpts[i][j][wlen-1])){
            return false;
          }
        }
      }
      return true;
    }

    // close in U direction for volume
    public static IVecI[][][] createClosedCPInU(IVecI[][][] cpts, int udeg){
      int headNum = (udeg-1)/2;
      int tailNum = udeg/2 + 1;
      int ulen = cpts.length;
      int vlen = cpts[0].length;
      int wlen = cpts[0][0].length;
      if(isUClosed(cpts)){
        if(udeg==1){
          for(int i=0; i<vlen ; i++){
            for(int j=0; j<wlen; j++){
              if(cpts[0][i][j] == cpts[ulen-1][i][j]) cpts[ulen-1][i][j] = cpts[0][i][j].dup();
            }
          }
          return cpts;
        }
        ulen--;
      }
      int ulen2 = ulen+udeg;
      IVecI[][][] cpts2 = new IVecI[ulen2][vlen][wlen];
      for(int i=0; i<ulen2; i++){
        for(int j=0; j<vlen; j++){
          for(int k=0; k<wlen; k++){
            if(i<headNum) cpts2[i][j][k] = cpts[(i-headNum+ulen)%ulen][j][k].dup();
            else if(i<ulen2-tailNum) cpts2[i][j][k] = cpts[i-headNum][j][k];
            else cpts2[i][j][k] = cpts[(i-(ulen2-tailNum))%ulen][j][k].dup(); // tail
          }
        }
      }
      return cpts2;
    }

    // close in V direction for volume
    public static IVecI[][][] createClosedCPInV(IVecI[][][] cpts, int vdeg){
      int headNum = (vdeg-1)/2;
      int tailNum = vdeg/2 + 1;
      int ulen = cpts.length;
      int vlen = cpts[0].length;
      int wlen = cpts[0][0].length;
      if(isVClosed(cpts)){
        if(vdeg==1){
          for(int i=0; i<ulen ; i++){
            for(int j=0; j<wlen; j++){
              if(cpts[i][0][j] == cpts[i][vlen-1][j]) cpts[i][vlen-1][j] = cpts[i][0][j].dup();
            }
          }
          return cpts;
        }
        vlen--;
      }
      int vlen2 = vlen+vdeg;
      IVecI[][][] cpts2 = new IVecI[ulen][vlen2][wlen];
      for(int j=0; j<vlen2; j++){
        for(int i=0; i<ulen; i++){
          for(int k=0; k<wlen; k++){
            if(j<headNum) cpts2[i][j][k] = cpts[i][(j-headNum+vlen)%vlen][k].dup();
            else if(j<vlen2-tailNum) cpts2[i][j][k] = cpts[i][j-headNum][k];
            else cpts2[i][j][k] = cpts[i][(j-(vlen2-tailNum))%vlen][k].dup(); // tail
          }
        }
      }
      return cpts2;
    }

    // close in W direction for volume
    public static IVecI[][][] createClosedCPInW(IVecI[][][] cpts, int wdeg){
      int headNum = (wdeg-1)/2;
      int tailNum = wdeg/2 + 1;
      int ulen = cpts.length;
      int vlen = cpts[0].length;
      int wlen = cpts[0][0].length;
      if(isWClosed(cpts)){
        if(wdeg==1){
          for(int i=0; i<ulen ; i++){
            for(int j=0; j<vlen; j++){
              if(cpts[i][j][0] == cpts[i][j][wlen-1]) cpts[i][j][wlen-1] = cpts[i][j][0].dup();
            }
          }
          return cpts;
        }
        vlen--;
      }
      int wlen2 = wlen+wdeg;
      IVecI[][][] cpts2 = new IVecI[ulen][vlen][wlen2];
      for(int k=0; k<wlen2; k++){
        for(int j=0; j<vlen; j++){
          for(int i=0; i<ulen; i++){
            if(k<headNum) cpts2[i][j][k] = cpts[i][j][(k-headNum+wlen)%wlen].dup();
            else if(k<wlen2-tailNum) cpts2[i][j][k] = cpts[i][j][k-headNum];
            else cpts2[i][j][k] = cpts[i][j][(k-(wlen2-tailNum))%wlen].dup(); // tail
          }
        }
      }
      return cpts2;
    }



    public static boolean isValidKnots(double[] knots){
      for(int i=0; i<knots.length; i++){
        if(!IDouble.isValid(knots[i])) return false;
      }
      return true;
    }

    public static double[] invertKnots(double[] knots){
	double[] knots2 = new double[knots.length];
	for(int i=0; i<knots.length; i++){ knots2[i] = 1.0 - knots[knots.length-1-i]; }
	return knots2;
    }

}
