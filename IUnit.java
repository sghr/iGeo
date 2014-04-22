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
   Unit of geometries in a server
   
   @author Satoru Sugihara
*/

public class IUnit{
    static public final Type NoUnits = Type.NoUnits;
    static public final Type Angstroms = Type.Angstroms;
    static public final Type Nanometers = Type.Nanometers;
    static public final Type Microns = Type.Microns;
    static public final Type Millimeters = Type.Millimeters;
    static public final Type Centimeters = Type.Centimeters;
    static public final Type Decimeters = Type.Decimeters;
    static public final Type Meters = Type.Meters;
    static public final Type Dekameters = Type.Dekameters;
    static public final Type Hectometers = Type.Hectometers;
    static public final Type Kilometers = Type.Kilometers;
    static public final Type Megameters = Type.Megameters;
    static public final Type Gigameters = Type.Gigameters;
    static public final Type Microinches = Type.Microinches;
    static public final Type Mils = Type.Mils;
    static public final Type Inches = Type.Inches;
    static public final Type Feet = Type.Feet;
    static public final Type Yards = Type.Yards;
    static public final Type Miles = Type.Miles;
    static public final Type Points = Type.Points;
    static public final Type Picas = Type.Picas;
    static public final Type NauticalMiles = Type.NauticalMiles;
    static public final Type AstronomicalUnits = Type.AstronomicalUnits;
    static public final Type Lightyears = Type.Lightyears;
    static public final Type Parsecs = Type.Parsecs;
    static public final Type CustomUnits = Type.CustomUnits;
    

    static public final Type NoUnit = Type.NoUnits;
    static public final Type Angstrom = Type.Angstroms;
    static public final Type Nanometer = Type.Nanometers;
    static public final Type Micron = Type.Microns;
    static public final Type Millimeter = Type.Millimeters;
    static public final Type Centimeter = Type.Centimeters;
    static public final Type Decimeter = Type.Decimeters;
    static public final Type Meter = Type.Meters;
    static public final Type Dekameter = Type.Dekameters;
    static public final Type Hectometer = Type.Hectometers;
    static public final Type Kilometer = Type.Kilometers;
    static public final Type Megameter = Type.Megameters;
    static public final Type Gigameter = Type.Gigameters;
    static public final Type Microinche = Type.Microinches;
    static public final Type Mil = Type.Mils;
    static public final Type Inche = Type.Inches;
    static public final Type Foot = Type.Feet;
    static public final Type Yard = Type.Yards;
    static public final Type Mile = Type.Miles;
    static public final Type Point = Type.Points;
    static public final Type Pica = Type.Picas;
    static public final Type NauticalMile = Type.NauticalMiles;
    static public final Type AstronomicalUnit = Type.AstronomicalUnits;
    static public final Type Lightyear = Type.Lightyears;
    static public final Type Parsec = Type.Parsecs;
    static public final Type CustomUnit = Type.CustomUnits;

    static public final Type nounits = Type.NoUnits;
    static public final Type angstroms = Type.Angstroms;
    static public final Type nanometers = Type.Nanometers;
    static public final Type microns = Type.Microns;
    static public final Type millimeters = Type.Millimeters;
    static public final Type centimeters = Type.Centimeters;
    static public final Type decimeters = Type.Decimeters;
    static public final Type meters = Type.Meters;
    static public final Type dekameters = Type.Dekameters;
    static public final Type hectometers = Type.Hectometers;
    static public final Type kilometers = Type.Kilometers;
    static public final Type megameters = Type.Megameters;
    static public final Type gigameters = Type.Gigameters;
    static public final Type microinches = Type.Microinches;
    static public final Type mils = Type.Mils;
    static public final Type inches = Type.Inches;
    static public final Type feet = Type.Feet;
    static public final Type yards = Type.Yards;
    static public final Type miles = Type.Miles;
    static public final Type points = Type.Points;
    static public final Type picas = Type.Picas;
    static public final Type nauticalmiles = Type.NauticalMiles;
    static public final Type astronomicalunits = Type.AstronomicalUnits;
    static public final Type lightyears = Type.Lightyears;
    static public final Type parsecs = Type.Parsecs;
    static public final Type customunits = Type.CustomUnits;
    
    static public final Type nounit = Type.NoUnits;
    static public final Type angstrom = Type.Angstroms;
    static public final Type nanometer = Type.Nanometers;
    static public final Type micron = Type.Microns;
    static public final Type millimeter = Type.Millimeters;
    static public final Type centimeter = Type.Centimeters;
    static public final Type decimeter = Type.Decimeters;
    static public final Type meter = Type.Meters;
    static public final Type dekameter = Type.Dekameters;
    static public final Type hectometer = Type.Hectometers;
    static public final Type kilometer = Type.Kilometers;
    static public final Type megameter = Type.Megameters;
    static public final Type gigameter = Type.Gigameters;
    static public final Type microinche = Type.Microinches;
    static public final Type mil = Type.Mils;
    static public final Type inch = Type.Inches;
    static public final Type foot = Type.Feet;
    static public final Type yard = Type.Yards;
    static public final Type mile = Type.Miles;
    static public final Type point = Type.Points;
    static public final Type pica = Type.Picas;
    static public final Type nauticalmile = Type.NauticalMiles;
    static public final Type astronomicalunit = Type.AstronomicalUnits;
    static public final Type lightyear = Type.Lightyears;
    static public final Type parsec = Type.Parsecs;
    static public final Type customunit = Type.CustomUnits;
        
    
    
    
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
