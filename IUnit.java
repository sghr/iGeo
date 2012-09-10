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

/**
   Unit of geometries in a server
   
   @author Satoru Sugihara
*/

public class IUnit{
    public enum Type{ NoUnits,
	    Angstroms,
	    Nanometers,
	    Microns,
	    Millimeters,
	    Centimeters,
	    Decimeters,
	    Meters,
	    Dekameters,
	    Hectometers,
	    Kilometers,
	    Megameters,
	    Gigameters,
	    Microinches,
	    Mils,
	    Inches,
	    Feet,
	    Yards,
	    Miles,
	    Points,
	    Picas,
	    NauticalMiles,
	    AstronomicalUnits,
	    Lightyears,
	    Parsecs,
	    CustomUnits };
    
    public static double scale(Type unit){
	switch(unit){
	case NoUnits: return 1.0; // ?
	case Angstroms: return 1.0E-10;
	case Nanometers: return 1.0E-9;
	case Microns: return 1.0E-6;
	case Millimeters: return 1.0E-3;
	case Centimeters: return 1.0E-2;
	case Decimeters: return 1.0E-1;
	case Meters: return 1.0;
	case Dekameters: return 1.0E1;
	case Hectometers: return 1.0E2;
	case Kilometers: return 1.0E3;
	case Megameters: return 1.0E6;
	case Gigameters: return 1.0E9;
	case Microinches: return 2.54E-8;
	case Mils: return 2.54E-5;
	case Inches: return 2.54E-2;
	case Feet: return 0.3408;
	case Yards: return 0.9144;
	case Miles: return 1609.344;
	case Points: return 0.0254/72;
	case Picas: return 0.0254/6;
	case NauticalMiles: return 1852;
	case AstronomicalUnits: return 1.4959787e+11;
	case Lightyears: return 9.4607304725808e+15;
	case Parsecs: return 3.08567758e+16;
	case CustomUnits: return 1.0; //?
	}
	IOut.err("unknown unit: "+unit);
	return 1.0; //
    }
    
    public static String name(Type unit){
	switch(unit){
	case NoUnits: return "No Unit";
	case Angstroms: return "Angstroms";
	case Nanometers: return "Nanometers";
	case Microns: return "Microns";
	case Millimeters: return "Millimeters";
	case Centimeters: return "Centimeters";
	case Decimeters: return "Decimeters";
	case Meters: return "Meters";
	case Dekameters: return "Dekameters"; 
	case Hectometers: return "Hectometers";
	case Kilometers: return "Kilometers";
	case Megameters: return "Megameters";
	case Gigameters: return "Gigameters"; 
	case Microinches: return "Microinches";
	case Mils: return "Mils";
	case Inches: return "Inches";
	case Feet: return "Feet";
	case Yards: return "Yards";
	case Miles: return "Miles";
	case Points: return "Points";
	case Picas: return "Picas";
	case NauticalMiles: return "Nautical miles";
	case AstronomicalUnits: return "Astronomical units";
	case Lightyears: return "Lightyears";
	case Parsecs: return "Parsecs";
	case CustomUnits: return "Custom units";
	}
	IOut.err("unknown unit: "+unit);
	return "Unknown Units";
    }
    
    /** convert name to type */
    public static Type type(String name){
	
	name = name.toLowerCase();
	if(name.endsWith("s")){ name = name.substring(0,name.length()-1); }
	
	if(name.equals("angstrom")) return Type.Angstroms; 
	if(name.equals("nanometer")) return Type.Nanometers;
	if(name.equals("micron")) return Type.Microns; 
	if(name.equals("millimeter")) return Type.Millimeters;
	if(name.equals("centimeter")) return Type.Centimeters;
	if(name.equals("decimeter")) return Type.Decimeters;
	if(name.equals("meter")) return Type.Meters;
	if(name.equals("dekameter")) return Type.Dekameters;
	if(name.equals("hectometer")) return Type.Hectometers;
	if(name.equals("kilometer")) return Type.Kilometers;
	if(name.equals("megameter")) return Type.Megameters;
	if(name.equals("gigameter")) return Type.Gigameters;
	if(name.equals("microinch")||name.equals("microinche") ) return Type.Microinches;
	if(name.equals("mil")) return Type.Mils;
	if(name.equals("inch")||name.equals("inche") ) return Type.Inches;
	if(name.equals("feet")||name.equals("foot") ) return Type.Feet;
	if(name.equals("yard")) return Type.Yards;
	if(name.equals("mile")) return Type.Miles;
	if(name.equals("point")) return Type.Points;
	if(name.equals("pica")) return Type.Picas;
	if(name.equals("nautical")||name.equals("nautical mile")) return Type.NauticalMiles;
	if(name.equals("astronomical")||name.equals("astronomical unit")) return Type.AstronomicalUnits;
	if(name.equals("lightyear")) return Type.Lightyears;
	if(name.equals("parsec")) return Type.Parsecs;
	if(name.equals("custom") || name.equals("custom unit")) return Type.CustomUnits;
	
	IOut.err("unknown unit: "+name);
	return Type.NoUnits;
    }
    
    
    public Type type;
    
    public IUnit(){ type = Type.Millimeters; }
    
    public IUnit(Type type){ this.type = type; }

    public IUnit(String name){ type = type(name); }
        
    public String toString(){ return name(type); }
    
}
