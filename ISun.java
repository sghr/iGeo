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

/*******************************************

     This algorithm is based on a translation of sunpos.pro in IDL library.
     For more information about the IDL library see
     http://idlastro.gsfc.nasa.gov/

******************************************/


package igeo;

import static java.lang.Math.*;
import java.util.ArrayList;

/**
   A solar analysis package providing direction of the sun in
   the spcecified location at the specified time. 
   
   @author Satoru Sugihara
*/
public class ISun {
    
    /** internal search buffer default sample num per a day */
    public static int bufferSampleNum =24*60; // check every min
    
    
    /** input location info */
    public double latitude=0, longitude=0;
    /** height(altitude) of the location */
    public double elevation=0;
    /** time zone as difference of hours from GMT; -12 - +12 */
    public double timeZone = 0;
    
    /** input time info */
    public int year=2000, month=1, day=1;
    /** hour includes fraction of minutes and seconds */
    public double hour = 0;
    /** switch to interpret hour as hour in daylight saving day */
    public boolean daylightSaving = false;
    
    /* output direction of the sun in a vector */
    public IVec dir;
    /* output direction of the sun in degrees */
    public double azimuth, altitude;
    public boolean updateAngle = true;
    
    public IVec northDir;
    
    //public SearchBuffer buffer=null; // internal cache for search
    //public SearchBuffer[] buffers=null; // internal cache for search, through a year
    public ArrayList<SearchBuffer> buffers; // internal caches for search
    
    
    public static boolean precessionCorrection = true;
    public static boolean nutationCorrection = true;
    public static boolean aberrationCorrection = true;
    public static boolean refractionCorrection = false; //true; // default false
    
    public static boolean measureAzimuthFromSouth = false;
    
    
    public enum City{
	
	// capitals
	AbuDhabi, // UAE
	    Abuja, // Nigeria
	    Accra, // Ghana
	    Doha, // Qatar
	    Adamstown, // Pitcairn Islands (UK)
	    AddisAbaba, // Ethiopia
	    Khartoum, // Sudan
	    Kuwait, // Kuwait
	    Manama, // Bahrain
	    Cairo, // Egypt
	    Algiers, // Algeria
	    Alofi, // Niue (NZ)
	    Amman, // Jordan
	    Amsterdam, // Netherlands
	    AndorraLaVella, // Andorra
	    Ankara, // Turkey
	    Antananarivo, //Madagascar
	    Apia, // Samoa
	    Riyadh, // Saudi Arabia
	    Ashgabat, // Turkmenistan
	    Asmara, // Eritrea
	    Astana, // Kazakhstan
	    Asuncion, // Paraguay
	    Athens, // Greece
	    Avarua, // Cook Islands (NZ)
	    Baghdad, // Iraq
	    Bairiki, // Tawara, Kiribati
	    Baku, // Azerbaijan
	    Bamako, // Mali
	    BandarSeriBegawan, //Brunei
	    Bangui, // Central African Republic
	    Banjul, // Gambia
	    BasseTerre, // Guandeloupe (FR)
	    Basseterre, // St. Kitts and Nevis
	    Beijing, // China
	    Belmopan, // Belize
	    Belgrade, // Serbia
	    Berlin, // Germany
	    Bern, // Switzerland
	    Beirut, // Lebanon
	    Bishkek, // Kyrgyzstan
	    Bissau, // Guinea-Bissau
	    Bogota, // Colombia
	    Brasilia, // Brazil
	    Bratislava, // Slovakia
	    Brazzaville, // Congo
	    Bridgetown, // Barbados
	    Brussels, // Belgium
	    Bucharest, // Romania
	    Budapest, // Hungary
	    BuenosAires, //Argentina
	    Bujumbura, // Burundi
	    Canberra, // Australia
	    Caracas, // Venezuela
	    Castries, // St. Lucia
	    Cayenne, // French Guiana (FR)
	    CharlotteAmalie, // United States Virgin Islands
	    Chisinau, //Moldova
	    MexicoCity, // Mexico
	    CockburnTown, // Turks- and Caicos Island (UK)
	    Colombo, // Sri Lanka
	    Conakry, // Guinea
	    Dakar, // Senegal
	    DalapUligaDarrit, // Marshall Island
	    Dhaka, // Bangladesh
	    Dili, // Timor-Leste/East Timor
	    Damascus, // Syria
	    Djibouti, // Djibouti
	    Dodoma, // Tanzania
	    Douglas, //Isle of man (UK)
	    Dublin, // Ireland
	    Dushanbe, // Tajikistan
	    FortDeFrance, // Martinique (FR)
	    Freetown, // Sierra Leone
	    Gaborone, // Botswana
	    Garapan, // Northern Mariana Island (on Saipan)
	    GeorgeTown, // Cayman Island (UK)
	    Georgetown, // Guyana
	    Gibraltar, // Gibraltar(UK)
	    Guatemala, // Guatemala (UK)
	    Hanoi, // Vietnam
	    Hagatna, // Guam
	    Hamilton, // Bermunda (UK)
	    Harare, // Zimbabwe
	    Helsinki, // Finland
	    Honiara, // Solomon Islands
	    Islamabad, // Pakistan
	    Jakarta, // Indonesia
	    Jamestown, // St Helena (UK)
	    Kabul, // Afghanistan
	    Kampala, // Uganda
	    Kathmandu, // Nepal
	    Kigali, // Rwanda
	    Kingston, // Jamaica
	    KingstonNorfolk, // Norfolk Island (AU)
	    Kingstown, //St. Vincent and the Grenadines
	    Kinshasa, // Congo
	    Copenhagen, //Denmark
	    Bangkok, // Thailand
	    KualaLumpur, // Malaysia
	    Kiev, // Ukraine
	    Havana, // Cuba
	    LaPaz, // Bolivia
	    ElAaiun, // Western Sahara
	    Lhasa, // Tibet, China
	    Libreville, // Gabon
	    Lilongwe, // Malawi
	    Lima, // Peru
	    Lisbon, // Portugal
	    Ljubljana, // Slovenia
	    Lome, // Togo
	    London, // United Kingdom
	    Longyearbyen, // Svalbard (NO)
	    Luanda, // Angola
	    Lusaka, // Zambia
	    Luxembourg, // Luxembourg
	    Male, // Maldives
	    Madrid, // Spain
	    Malabo, // Equatorial Guinea
	    Mamoudzou, // Mayotte (FR)
	    Managua, // Nicaragua
	    Manila, // Phillippines
	    Maputo, // Mozambique
	    Maseru, // Lesotho
	    Muscat, // Oman
	    MataUtu, // Wallis and Futuna (FR)
	    Mbabane, // Swaziland
	    Melekeok, //Palau
	    Minsk, // Belarus
	    Monaco, // Monaco
	    Monrovia, // Liberia
	    Montevideo, // Uruguay
	    Moroni, // Comoros
	    Moscow, // Russia
	    Mogadishu, // Somalia
	    Ndjamena, // Chad
	    Nairobi, // Kenya
	    Nassau, //Bahamas
	    NewDelhi, // India
	    Niamey, // Niger
	    Nicosia, // Cyprus
	    Nouakchott, //Mauritania
	    Noumea, // New Caledonia
	    Nukualofa, // Tonga
	    Nuuk, // Greenland (DK)
	    Oranjestad, //Aruba (NL)
	    Oslo, // Norway
	    Ottawa, // Canada
	    Ouagadougou, // Burkina Faso
	    Pyongyang, // North Korea
	    PagoPago, // American Samoa
	    Palikir, // Micronesia (Federated States)
	    Panama, // Panama
	    Papeete, // French Polynesia (FR)
	    Paramaribo, // Suriname
	    Paris, // France
	    PhnomPenh, // Cambodia
	    Plymouth, // Montserrat (UK)
	    Podgorica, // Montenegro
	    PortLouis, // Mauritius
	    PortMoresby, // Papua New Guinea
	    PortOfSpain, //Trinidad and Tobago
	    PortVila, // Vanuatu
	    PortAuPrince, // Haiti
	    PortoNovo, // Benin
	    Prague, //Czech Repablic
	    Praia, // Cape Verde
	    Pretoria, // South Africa
	    Pristina, // Kosovo (RS)
	    WestIsland, // Cocos Island (AU)
	    Pyinmana, // Myanmar/Burma
	    Quito, // Ecuador
	    Rabat, // Morocco
	    Reykjavik, // Iceland
	    Riga, //Latvia
	    RoadTown, // British Virgin Island (UK)
	    Rome, // Italy
	    Roseau, // Dominica
	    SaintDenis, // Reunion (FR)
	    SaintGeorges, // Grenada
	    SaintHelier, // Jersey (UK)
	    SaintJohns, // Antigua and Barbuda
	    SaintPeterPort, // Guernsey (UK)
	    SaintPierre, // St Pierre and Miquelon
	    SanJose, // Costa Rica
	    SanJuan, // Puerto Rico (US)
	    SanMarino, // San Marino
	    SanSalvador, // El Salvador
	    Sanaa, // Yemen
	    Santiago, // Chile
	    SantoDomingo, // Dominican Republic
	    SaoTome, // Sao Tome and Principe
	    Sarajevo, // Bosnia and Herzegovina
	    Seoul, // South Korea
	    Shanghai, // China
	    Shenzhen, // China
	    Singapore, // Singapore
	    Skopje, // Macedonia
	    Sofia, // Bulgaria
	    Stanley, // Falkland Island (UK)
	    Stockholm, // Sweden
	    Suva, // Fiji
	    Taipei, // Taiwan
	    Tbilisi, // Georgia
	    Tallinn, // Estonia
	    Tripoli, // Libya
	    Tegucigalpa, // Honduras
	    Tehran, // Iran
	    TheSettlement, // Christmas Island (AU)
	    TheValley, //Anguilla (UK)
	    Thimphu, // Bhutan
	    Tirana, // Albania
	    Tokyo, // Japan
	    Torshavn, //Faroe Island (DK)
	    Tashkent, // Uzbekistan
	    Tunis, // Tunisia
	    UlanBator, //Mongolia
	    Vaduz, // Liechtenstein
	    Vaiaku, // Tuvalu
	    Valletta, // Malta
	    Vientiane, // Laos
	    Victoria, // Seychelles
	    Vienna, // Austria
	    Vilnius, // Lithuania
	    Warsaw, // Poland
	    Washington, // United States
	    Wellington, // New Zealand
	    Willemstad, //Netherlands Antilles (NL)
	    Windhoek, // Nambibia
	    Yamoussoukro, // Cote d'Ivoire
	    Yaounde, // Cameroon
	    Yaren, //Nauru
	    Yerevan, // Armenia
	    Jerusalem, // Israel
	    Zagreb, //Croatia

	    // additional cities
	    NewYork,
	    LosAngeles,
	    Chicago,
	    Osaka,
	    Milan,
	    
	    }
    
    
    public static class Location{
	public double longitude, latitude, elevation, timeZone;
	
	public Location(double lat, double lng, double elev, double tmZone){
	    latitude = lat;
	    longitude = lng;
	    elevation = elev;
	    timeZone = tmZone;
	}
	
	public static Location cityLocation(City city){
	    switch(city){
	    case AbuDhabi: // UAE
		return new Location(24.4764, 54.3705, 13, +4);
	    case Abuja: // Nigeria
		return new Location(9.0580, 7.4891, 777, +1);
	    case Accra: // Ghana
		return new Location(5.5401, -0.2074, 98, 0);
	    case Doha: // Qatar
		return new Location(25.2948, 51.5082, 13, +3);
	    case Adamstown: // Pitcairn Islands (UK)
		return new Location(-25.0662, -130.1027, 0, -8);
	    case AddisAbaba: // Ethiopia
		return new Location(9.0084, 38.7575, 2362, +3);
	    case Khartoum: // Sudan
		return new Location(15.6331, 32.5330, 377, +3);
	    case Kuwait: // Kuwait
		return new Location(29.3721, 47.9824, 5, +3);
	    case Manama: // Bahrain
		return new Location(26.1921, 50.5354, 6, +3);
	    case Cairo: // Egypt
		return new Location(30.0571, 31.2272, 22, +2);
	    case Algiers: // Algeria
		return new Location(36.7755, 3.0597, 0, +1);
	    case Alofi: // Niue (NZ)
		return new Location(-19.0565, -169.9237, 6, -11);
	    case Amman: // Jordan
		return new Location(31.9394, 35.9349, 759, +2);
	    case Amsterdam: // Netherlands
		return new Location(52.3738, 4.8910, -1, +1);
	    case AndorraLaVella: // Andorra
		return new Location(42.5075, 1.5218, 1409, +1);
	    case Ankara: // Turkey
		return new Location(39.9439, 32.8560, 938, +2);
	    case Antananarivo: //Madagascar
		return new Location(-18.9201, 47.5237, 1288, +3);
	    case Apia: // Samoa
		return new Location(-13.8314, -171.7518, 0, +13);
	    case Riyadh: // Saudi Arabia
		return new Location(24.6748, 46.6977, 624, +3);
	    case Ashgabat: // Turkmenistan
		return new Location(37.9509, 58.3794, 215, +5);
	    case Asmara: // Eritrea
		return new Location(15.3315, 38.9183, 2363, +3);
	    case Astana: // Kazakhstan
		return new Location(51.1796, 71.4475, 338, +6);
	    case Asuncion: // Paraguay
		return new Location(-25.3005, -57.6362, 54, -4);
	    case Athens: // Greece
		return new Location(37.9792, 23.7166, 153, +2);
	    case Avarua: // Cook Islands (NZ)
		return new Location(-21.2039, -159.7658, 208, -10);
	    case Baghdad: // Iraq
		return new Location(33.3157, 44.3922, 40, +3);
	    case Bairiki: // Tawara, Kiribati
		return new Location(1.3282, 172.9784, 0, +12);
	    case Baku: // Azerbaijan
		return new Location(40.3834, 49.8932, 1, +4);
	    case Bamako: // Mali
		return new Location(12.6530, -7.9864, 349, 0);
	    case BandarSeriBegawan: //Brunei
		return new Location(4.9431, 114.9425, 0, +8);
	    case Bangui: // Central African Republic
		return new Location(4.3621, 18.5873, 369, +1);
	    case Banjul: // Gambia
		return new Location(13.4399, -16.6775, 0, 0);
	    case BasseTerre: // Guandeloupe (FR)
		return new Location(15.9985, -61.7220, 0, -4);
	    case Basseterre: // St. Kitts and Nevis
		return new Location(17.2968, -62.7138, 0, -4);
	    case Beijing: // China
		return new Location(39.9056, 116.3958, 63, +8);
	    case Belmopan: // Belize
		return new Location(17.2534, -88.7713, 59, -6);
	    case Belgrade: // Serbia
		return new Location(44.8048, 20.4781, 116, +1);
	    case Berlin: // Germany
		return new Location(52.5235, 13.4115, 34, +1);
	    case Bern: // Switzerland
		return new Location(46.9480, 7.4481, 513, +1);
	    case Beirut: // Lebanon
		return new Location(33.8872, 35.5134, 55, +2);
	    case Bishkek: // Kyrgyzstan
		return new Location(42.8679, 74.5984, 771, +6);
	    case Bissau: // Guinea-Bissau
		return new Location(11.8598, -15.5875, 0, 0);
	    case Bogota: // Colombia
		return new Location(4.6473, -74.0962, 2619, -5);
	    case Brasilia: // Brazil
		return new Location(-15.7801, -47.9292, 1079, -3);
	    case Bratislava: // Slovakia
		return new Location(48.2116, 17.1547, 131, +1);
	    case Brazzaville: // Congo
		return new Location(-4.2767, 15.2662, 155, +1);
	    case Bridgetown: // Barbados
		return new Location(13.0935, -59.6105, 6, -4);
	    case Brussels: // Belgium
		return new Location(50.8371, 4.3676, 76, +1);
	    case Bucharest: // Romania
		return new Location(44.4479, 26.0979, 70, +2);
	    case Budapest: // Hungary
		return new Location(47.4984, 19.0408, 102, +1);
	    case BuenosAires: //Argentina
		return new Location(-34.6118, -58.4173, 10, -3);
	    case Bujumbura: // Burundi
		return new Location(-3.3818, 29.3622, 794, +2);
	    case Canberra: // Australia
		return new Location(-35.2820, 149.1286, 605, +10);
	    case Caracas: // Venezuela
		return new Location(10.4961, -66.8983, 909, -4.5);
	    case Castries: // St. Lucia
		return new Location(13.9972, -60.0018, 204, -4);
	    case Cayenne: // French Guiana (FR)
		return new Location(4.9346, -52.3303, 32, -3);
	    case CharlotteAmalie: // United States Virgin Islands
		return new Location(18.3405, -64.9326, 0, -4);
	    case Chisinau: //Moldova
		return new Location(47.0167, 28.8497, 80, +2);
	    case MexicoCity: // Mexico
		return new Location(19.4271, -99.1276, 2216, -6);
	    case CockburnTown: // Turks- and Caicos Island (UK)
		return new Location(21.4608, -71.1363, 0, -5);
	    case Colombo: // Sri Lanka
		return new Location(6.9155, 79.8572, 4, +5.5);
	    case Conakry: // Guinea
		return new Location(9.5370, -13.6785, 0, 0);
	    case Dakar: // Senegal
		return new Location(14.6953, -17.4439, 37, 0);
	    case DalapUligaDarrit: // Marshall Island
		return new Location(7.1167, 171.3667, 0, +12);
	    case Dhaka: // Bangladesh
		return new Location(23.7106, 90.3978, 3, +6);
	    case Dili: // Timor-Leste/East Timor
		return new Location(-8.5662, 125.5880, 11, +9);
	    case Damascus: // Syria
		return new Location(33.5158, 36.2939, 691, +2);
	    case Djibouti: // Djibouti
		return new Location(11.5806, 43.1425, 0, +3);
	    case Dodoma: // Tanzania
		return new Location(-6.1670, 35.7497, 1148, +3);
	    case Douglas: //Isle of man (UK)
		return new Location(54.1670, -4.4821, 34, 0);
	    case Dublin: // Ireland
		return new Location(53.3441, -6.2675, 8, 0);
	    case Dushanbe: // Tajikistan
		return new Location(38.5737, 68.7738, 789, +5);
	    case FortDeFrance: // Martinique (FR)
		return new Location(14.5997, -61.0760, 0, -4);
	    case Freetown: // Sierra Leone
		return new Location(8.4697, -13.2659, 76, 0);
	    case Gaborone: // Botswana
		return new Location(-24.6570, 25.9089, 1014, +2);
	    case Garapan: // Northern Mariana Island (on Saipan)
		return new Location(15.2069, 145.7197, 132, +10);
	    case GeorgeTown: // Cayman Island (UK)
		return new Location(19.3022, -81.3857, 3, -5);
	    case Georgetown: // Guyana
		return new Location(6.8046, -58.1548, 0, -4);
	    case Gibraltar: // Gibraltar(UK)
		return new Location(36.1377, -5.3453, 447, +1);
	    case Guatemala: // Guatemala (UK)
		return new Location(14.6248, -90.5328, 1529, -6);
	    case Hanoi: // Vietnam
		return new Location(21.0341, 105.8372, 25, +7);
	    case Hagatna: // Guam
		return new Location(13.4667, 144.7470, 9, +10);
	    case Hamilton: // Bermunda (UK)
		return new Location(32.2930, -64.7820, 0, -4);
	    case Harare: // Zimbabwe
		return new Location(-17.8227, 31.0496, 1480, +2);
	    case Helsinki: // Finland
		return new Location(60.1699, 24.9384, 25, +2);
	    case Honiara: // Solomon Islands
		return new Location(-9.4333, 159.9500, 29, +11);
	    case Islamabad: // Pakistan
		return new Location(33.6751, 73.0946, 507, +5);
	    case Jakarta: // Indonesia
		return new Location(-6.1862, 106.8063, 3, +7);
	    case Jamestown: // St Helena (UK)
		return new Location(-15.9244, -5.7181, 292, 0);
	    case Kabul: // Afghanistan
		return new Location(34.5155, 69.1952, 1807, +4.5);
	    case Kampala: // Uganda
		return new Location(0.3133, 32.5714, 1202, +3);
	    case Kathmandu: // Nepal
		return new Location(27.7058, 85.3157, 1298, +5.75);
	    case Kigali: // Rwanda
		return new Location(-1.9441, 30.0619, 1567, +2);
	    case Kingston: // Jamaica
		return new Location(17.9927, -76.7920, 53, -5);
	    case KingstonNorfolk: // Norfolk Island (AU)
		return new Location(-29.0545, 167.9666, 0, +11.5);
	    case Kingstown: //St. Vincent and the Grenadines
		return new Location(13.2035, -61.2653, 0, -4);
	    case Kinshasa: // Congo
		return new Location(-4.3369, 15.3271, 240, +1);
	    case Copenhagen: //Denmark
		return new Location(55.6763, 12.5681, 0, +1);
	    case Bangkok: // Thailand
		return new Location(13.7573, 100.5020, 1, +7);
	    case KualaLumpur: // Malaysia
		return new Location(3.1502, 101.7077, 60, +8);
	    case Kiev: // Ukraine
		return new Location(50.4422, 30.5367, 168, +2);
	    case Havana: // Cuba
		return new Location(23.1333, -82.3667, 4, -5);
	    case LaPaz: // Bolivia
		return new Location(-19.0421, -65.2559, 3660, -4);
	    case ElAaiun: // Western Sahara
		return new Location(27.1536, -13.2033, 72, 0);
	    case Lhasa: // Tibet, China
		return new Location(29.3900, 91.0700, 3490, +8);
	    case Libreville: // Gabon
		return new Location(0.3858, 9.4496, 0, +1);
	    case Lilongwe: // Malawi
		return new Location(-13.9899, 33.7703, 1024, +2);
	    case Lima: // Peru
		return new Location(-12.0931, -77.0465, 107, -5);
	    case Lisbon: // Portugal
		return new Location(38.7072, -9.1355, 15, 0);
	    case Ljubljana: // Slovenia
		return new Location(46.0514, 14.5060, 281, +1);
	    case Lome: // Togo
		return new Location(6.1228, 1.2255, 63, 0);
	    case London: // United Kingdom
		return new Location(51.5002, -0.1262, 14, 0);
	    case Longyearbyen: // Svalbard (NO)
		return new Location(78.2186, 15.6488, 199, +1);
	    case Luanda: // Angola
		return new Location(-8.8159, 13.2306, 6, +1);
	    case Lusaka: // Zambia
		return new Location(-15.4145, 28.2809, 1270, +2);
	    case Luxembourg: // Luxembourg
		return new Location(49.6100, 6.1296, 273, +1);
	    case Male: // Maldives
		return new Location(4.1742, 73.5109, 0, +5);
	    case Madrid: // Spain
		return new Location(40.4167, -3.7033, 588, +1);
	    case Malabo: // Equatorial Guinea
		return new Location(3.7523, 8.7741, 107, +1);
	    case Mamoudzou: // Mayotte (FR)
		return new Location(-12.7806, 45.2278, 0, +3);
	    case Managua: // Nicaragua
		return new Location(12.1475, -86.2734, 75, -6);
	    case Manila: // Phillippines
		return new Location(14.5790, 120.9726, 7, +8);
	    case Maputo: // Mozambique
		return new Location(-25.9686, 32.5804, 63, +2);
	    case Maseru: // Lesotho
		return new Location(-29.2976, 27.4854, 1673, +2);
	    case Muscat: // Oman
		return new Location(23.6086, 58.5922, 68, +4);
	    case MataUtu: // Wallis and Futuna (FR)
		return new Location(-13.2784, -176.1430, 0, +12);
	    case Mbabane: // Swaziland
		return new Location(-26.3186, 31.1410, 1243, +2);
	    case Melekeok: //Palau
		return new Location(7.5007, 134.6241, 0, +9);
	    case Minsk: // Belarus
		return new Location(53.9678, 27.5766, 198, +3);
	    case Monaco: // Monaco
		return new Location(43.7325, 7.4189, 0, +1);
	    case Monrovia: // Liberia
		return new Location(6.3106, -10.8047, 0, 0);
	    case Montevideo: // Uruguay
		return new Location(-34.8941, -56.0675, 43, -3);
	    case Moroni: // Comoros
		return new Location(-11.7004, 43.2412, 110, +3);
	    case Moscow: // Russia
		return new Location(55.7558, 37.6176, 124, +4);
	    case Mogadishu: // Somalia
		return new Location(2.0411, 45.3426, 28, +3);
	    case Ndjamena: // Chad
		return new Location(12.1121, 15.0355, 298, +1);
	    case Nairobi: // Kenya
		return new Location(-1.2762, 36.7965, 1728, +3);
	    case Nassau: //Bahamas
		return new Location(25.0661, -77.3390, 2, -5);
	    case NewDelhi: // India
		return new Location(28.6353, 77.2250, 210, +5.5);
	    case Niamey: // Niger
		return new Location(13.5164, 2.1157, 207, +1);
	    case Nicosia: // Cyprus
		return new Location(35.1676, 33.3736, 134, +2);
	    case Nouakchott: //Mauritania
		return new Location(18.0669, -15.9900, 6, 0);
	    case Noumea: // New Caledonia
		return new Location(-22.2758, 166.4581, 0, +11);
	    case Nukualofa: // Tonga
		return new Location(-21.1360, -175.2164, 0, +13);
	    case Nuuk: // Greenland (DK)
		return new Location(64.1836, -51.7214, 0, -3);
	    case Oranjestad: //Aruba (NL)
		return new Location(12.5246, -70.0265, 13, -4);
	    case Oslo: // Norway
		return new Location(59.9138, 10.7387, 12, +1);
	    case Ottawa: // Canada
		return new Location(45.4235, -75.6979, 74, -5);
	    case Ouagadougou: // Burkina Faso
		return new Location(12.3569, -1.5352, 305, 0);
	    case Pyongyang: // North Korea
		return new Location(39.0187, 125.7468, 6, +9);
	    case PagoPago: // American Samoa
		return new Location(-14.2793, -170.7009, 49, -11);
	    case Palikir: // Micronesia (Federated States)
		return new Location(6.9177, 158.1854, 207, +11);
	    case Panama: // Panama
		return new Location(8.9943, -79.5188, 0, -5);
	    case Papeete: // French Polynesia (FR)
		return new Location(-17.5350, -149.5696, 59, -10);
	    case Paramaribo: // Suriname
		return new Location(5.8232, -55.1679, 1, -3);
	    case Paris: // France
		return new Location(48.8567, 2.3510, 34, +1);
	    case PhnomPenh: // Cambodia
		return new Location(11.5434, 104.8984, 15, +7);
	    case Plymouth: // Montserrat (UK)
		return new Location(16.6802, -62.2014, 114, -4);
	    case Podgorica: // Montenegro
		return new Location(42.4602, 19.2595, 61, +1);
	    case PortLouis: // Mauritius
		return new Location(-20.1654, 57.4896, 134, +4);
	    case PortMoresby: // Papua New Guinea
		return new Location(-9.4656, 147.1969, 39, +10);
	    case PortOfSpain: //Trinidad and Tobago
		return new Location(10.6596, -61.4789, 0, -4);
	    case PortVila: // Vanuatu
		return new Location(-17.7404, 168.3210, 0, +11);
	    case PortAuPrince: // Haiti
		return new Location(18.5392, -72.3288, 98, -5);
	    case PortoNovo: // Benin
		return new Location(6.4779, 2.6323, 38, +1);
	    case Prague: //Czech Repablic
		return new Location(50.0878, 14.4205, 244, +1);
	    case Praia: // Cape Verde
		return new Location(14.9195, -23.5153, 0, -1);
	    case Pretoria: // South Africa
		return new Location(-25.7463, 28.1876, 1271, +2);
	    case Pristina: // Kosovo (RS)
		return new Location(42.6740, 21.1788, 652, +1);
	    case WestIsland: // Cocos Island (AU)
		return new Location(-12.1869, 96.8283, 0, +6.5);
	    case Pyinmana: // Myanmar/Burma
		return new Location(19.7378, 96.2083, 77, +6.5);
	    case Quito: // Ecuador
		return new Location(-0.2295, -78.5243, 2763, -5);
	    case Rabat: // Morocco
		return new Location(33.9905, -6.8704, 53, 0);
	    case Reykjavik: // Iceland
		return new Location(64.1353, -21.8952, 15, 0);
	    case Riga: //Latvia
		return new Location(56.9465, 24.1049, 8, +2);
	    case RoadTown: // British Virgin Island (UK)
		return new Location(18.4328, -64.6235, 0, -4);
	    case Rome: // Italy
		return new Location(41.8955, 12.4823, 14, +1);
	    case Roseau: // Dominica
		return new Location(15.2976, -61.3900, 0, -4);
	    case SaintDenis: // Reunion (FR)
		return new Location(-20.8732, 55.4603, 112, +4);
	    case SaintGeorges: // Grenada
		return new Location(12.0540, -61.7486, 25, -4);
	    case SaintHelier: // Jersey (UK)
		return new Location(49.1919, -2.1071, 0, 0);
	    case SaintJohns: // Antigua and Barbuda
		return new Location(17.1175, -61.8456, 0, -4);
	    case SaintPeterPort: // Guernsey (UK)
		return new Location(49.4660, -2.5522, 0, 0);
	    case SaintPierre: // St Pierre and Miquelon
		return new Location(46.7878, -56.1968, 0, -3);
	    case SanJose: // Costa Rica
		return new Location(9.9402, -84.1002, 1146, -6);
	    case SanJuan: // Puerto Rico (US)
		return new Location(18.4500, -66.0667, 3, -4);
	    case SanMarino: // San Marino
		return new Location(43.9424, 12.4578, 328, +1);
	    case SanSalvador: // El Salvador
		return new Location(13.7034, -89.2073, 658, -6);
	    case Sanaa: // Yemen
		return new Location(15.3556, 44.2081, 2253, +3);
	    case Santiago: // Chile
		return new Location(-33.4691, -70.6420, 521, -4);
	    case SantoDomingo: // Dominican Republic
		return new Location(18.4790, -69.8908, 0, -4);
	    case SaoTome: // Sao Tome and Principe
		return new Location(0.3360, 6.7311, 141, 0);
	    case Sarajevo: // Bosnia and Herzegovina
		return new Location(43.8608, 18.4214, 577, +1);
	    case Seoul: // South Korea
		return new Location(37.5139, 126.9828, 33, +9);
	    case Shanghai: // China
		return new Location(31.230708, 121.472916, 4, +8);
	    case Shenzhen: // China
		//return new Location(22.543447, 114.057818, 4, +8);
		return new Location(22., 114., 4, +8);
	    case Singapore: // Singapore
		return new Location(1.2894, 103.8500, 0, +8);
	    case Skopje: // Macedonia
		return new Location(42.0024, 21.4361, 243, +1);
	    case Sofia: // Bulgaria
		return new Location(42.7105, 23.3238, 591, +2);
	    case Stanley: // Falkland Island (UK)
		return new Location(-51.7010, -57.8492, 0, -4);
	    case Stockholm: // Sweden
		return new Location(59.3328, 18.0645, 15, +1);
	    case Suva: // Fiji
		return new Location(-18.1416, 178.4419, 0, +12);
	    case Taipei: // Taiwan
		return new Location(25.0338, 121.5645, 5, +8);
	    case Tbilisi: // Georgia
		return new Location(41.7010, 44.7930, 451, +4);
	    case Tallinn: // Estonia
		return new Location(59.4389, 24.7545, 37, +2);
	    case Tripoli: // Libya
		return new Location(32.8830, 13.1897, 6, +2);
	    case Tegucigalpa: // Honduras
		return new Location(14.0821, -87.2063, 980, -6);
	    case Tehran: // Iran
		return new Location(35.7061, 51.4358, 1138, +3.5);
	    case TheSettlement: // Christmas Island (AU)
		return new Location(-10.4286, 105.6807, 0, +7);
	    case TheValley: //Anguilla (UK)
		return new Location(18.2249, -63.0669, 0, -4);
	    case Thimphu: // Bhutan
		return new Location(27.4405, 89.6730, 2736, +6);
	    case Tirana: // Albania
		return new Location(41.3317, 19.8172, 104, +1);
	    case Tokyo: // Japan
		return new Location(35.6785, 139.6823, 17, +9);
	    case Torshavn: //Faroe Island (DK)
		return new Location(62.0177, -6.7719, 0, 0);
	    case Tashkent: // Uzbekistan
		return new Location(41.3193, 69.2481, 459, +5);
	    case Tunis: // Tunisia
		return new Location(36.8117, 10.1761, 0, +1);
	    case UlanBator: //Mongolia
		return new Location(47.9138, 106.9220, 1284, +8);
	    case Vaduz: // Liechtenstein
		return new Location(47.1411, 9.5215, 601, +1);
	    case Vaiaku: // Tuvalu
		return new Location(-8.5210, 179.1983, 0, +12);
	    case Valletta: // Malta
		return new Location(35.9042, 14.5189, 0, +1);
	    case Vientiane: // Laos
		return new Location(17.9689, 102.6137, 148, +7);
	    case Victoria: // Seychelles
		return new Location(-4.6167, 55.4500, 0, +4);
	    case Vienna: // Austria
		return new Location(48.2092, 16.3728, 170, +1);
	    case Vilnius: // Lithuania
		return new Location(54.6896, 25.2799, 124, +2);
	    case Warsaw: // Poland
		return new Location(52.2297, 21.0122, 93, +1);
	    case Washington: // United States
		return new Location(38.8921, -77.0241, 2, -5);
	    case Wellington: // New Zealand
		return new Location(-41.2865, 174.7762, 20, +12);
	    case Willemstad: //Netherlands Antilles (NL)
		return new Location(12.1034, -68.9335, 0, -4);
	    case Windhoek: // Nambibia
		return new Location(-22.5749, 17.0805, 1721, +1);
	    case Yamoussoukro: // Cote d'Ivoire
		return new Location(6.8067, -5.2728, 217, 0);
	    case Yaounde: // Cameroon
		return new Location(3.8612, 11.5217, 726, +1);
	    case Yaren: //Nauru
		return new Location(-0.5434, 166.9196, 9, +12);
	    case Yerevan: // Armenia
		return new Location(40.1596, 44.5090, 1032, +4);
	    case Jerusalem: // Israel
		return new Location(31.7857, 35.2007, 580, +2);
	    case Zagreb: //Croatia
		return new Location(45.8150, 15.9785, 130, +1);
	    case NewYork: // United States
		return new Location(40.77, -73.98, 1, -5);
	    case LosAngeles: // United States
		return new Location(34.05, -118.25, 84, -8);
	    case Chicago: // United States
		return new Location(41.8833, -87.6333, 181, -6);
	    case Osaka: // Japan
		return new Location(34.6667, 135.5, 17, +9);
	    case Milan: // Italy
		return new Location(45.4667, 9.1667, 120, +1);
	    }
	    
	    IOut.err("no information for "+city+" found"); 
	    return null;
	}
	
    }
    
    public ISun(){}
    
    public ISun(double latitude, double longitude, double elevation, double timeZone, IVec northDir,
		int year, int month, int day, double hour, boolean daylightSavingTime){
	this.latitude = latitude;
	this.longitude = longitude;
	this.elevation = elevation;
	this.timeZone = timeZone;
	this.northDir = northDir;
	this.year = year;
	this.month = month;
	this.day = day;
	this.hour = hour;
	this.daylightSaving = daylightSavingTime;
    }
    
    public ISun(double latitude, double longitude, double elevation, double timeZone, 
		int year, int month, int day, double hour, boolean daylightSavingTime){
	this.latitude = latitude;
	this.longitude = longitude;
	this.elevation = elevation;
	this.timeZone = timeZone;
	this.year = year;
	this.month = month;
	this.day = day;
	this.hour = hour;
	this.daylightSaving = daylightSavingTime;
    }
    
    
    public ISun(double latitude, double longitude, double elevation, double timeZone, IVec northDir){
	this.latitude = latitude;
	this.longitude = longitude;
	this.elevation = elevation;
	this.timeZone = timeZone;
	this.northDir = northDir;
    }
    
    public ISun(double latitude, double longitude, double elevation, double timeZone){
	this.latitude = latitude;
	this.longitude = longitude;
	this.elevation = elevation;
	this.timeZone = timeZone;
    }
    
    public ISun(double latitude, double longitude, double timeZone, IVec northDir){
	this.latitude = latitude;
	this.longitude = longitude;
	this.timeZone = timeZone;
	this.northDir = northDir;
    }
    
    public ISun(double latitude, double longitude, double timeZone){
	this.latitude = latitude;
	this.longitude = longitude;
	this.timeZone = timeZone;
    }
    
    
    public ISun(City city, IVec northDir){
	this(Location.cityLocation(city),northDir);
    }
    
    public ISun(City city){
	this(Location.cityLocation(city));
    }
    
    
    public ISun(Location location, IVec northDir){
	if(location == null){ IOut.err("location is null"); return; }
	this.latitude = location.latitude;
	this.longitude = location.longitude;
	this.elevation = location.elevation;
	this.timeZone = location.timeZone;
	this.northDir = northDir;
    }
    
    public ISun(Location location){
	if(location == null){ IOut.err("location is null"); return; }
	this.latitude = location.latitude;
	this.longitude = location.longitude;
	this.elevation = location.elevation;
	this.timeZone = location.timeZone;
    }
    
    
    /** setting time */
    public ISun time(int year, int month, int day, int hour, int minute, double second,
		     boolean daylightSavingTime){
	this.year = year;
	this.month = month;
	this.day = day;
	this.hour = hour + (double)minute/60. + (double)second/3600.;
	this.daylightSaving = daylightSavingTime;
	updateAngle = true;
	return this;
    }
    /** setting time */
    public ISun time(int year, int month, int day, int hour, int minute, double second){
	this.year = year;
	this.month = month;
	this.day = day;
	this.hour = hour + (double)minute/60. + (double)second/3600.;
	//this.daylightSaving = false;
	updateAngle = true;
	return this;
    }
    /** setting time */
    public ISun time(int year, int month, int day, int hour, double minute,
		     boolean daylightSavingTime){
	this.year = year;
	this.month = month;
	this.day = day;
	this.hour = hour + (double)minute/60.;
	this.daylightSaving = daylightSavingTime;
	updateAngle = true;
	return this;
    }
    /** setting time */
    public ISun time(int year, int month, int day, int hour, double minute){
	this.year = year;
	this.month = month;
	this.day = day;
	this.hour = hour + (double)minute/60.;
	//this.daylightSaving = false;
	updateAngle = true;
	return this;
    }
    /** setting time */
    public ISun time(int year, int month, int day, double hour, boolean daylightSavingTime){
	this.year = year;
	this.month = month;
	this.day = day;
	this.hour = hour;
	this.daylightSaving = daylightSavingTime;
	updateAngle = true;
	return this;
    }
    /** setting time */
    public ISun time(int year, int month, int day, double hour){
	this.year = year;
	this.month = month;
	this.day = day;
	this.hour = hour;
	//this.daylightSaving = false;
	updateAngle = true;
	return this;
    }
    
    /** setting location */
    public ISun location(double latitude, double longitude, double elevation, double timeZone, IVec northDir){
	this.latitude = latitude;
	this.longitude = longitude;
	this.elevation = elevation;
	this.timeZone = timeZone;
	this.northDir = northDir;
	updateAngle = true;
	return this;
    }
    
    /** setting location */
    public ISun location(double latitude, double longitude, double elevation, double timeZone){
	this.latitude = latitude;
	this.longitude = longitude;
	this.elevation = elevation;
	this.timeZone = timeZone;
	updateAngle = true;
	return this;
    }
    
    /** setting location */
    public ISun location(double latitude, double longitude, double timeZone, IVec northDir){
	this.latitude = latitude;
	this.longitude = longitude;
	//this.elevation = 0;
	this.timeZone = timeZone;
	this.northDir = northDir;
	updateAngle = true;
	return this;
    }
    
    /** setting location */
    public ISun location(double latitude, double longitude, double timeZone){
	this.latitude = latitude;
	this.longitude = longitude;
	//this.elevation = 0;
	this.timeZone = timeZone;
	updateAngle = true;
	return this;
    }
    
    /** setting location */
    public ISun location(City city, IVec northDir){
	return location(Location.cityLocation(city),northDir);
    }
    
    /** setting location */
    public ISun location(City city){
	return location(Location.cityLocation(city));
    }
    
    /** setting location */
    public ISun location(Location location, IVec northDir){
	if(location == null){ IOut.err("location is null"); return this; }
	this.latitude = location.latitude;
	this.longitude = location.longitude;
	this.elevation = location.elevation;
	this.timeZone = location.timeZone;
	this.northDir = northDir;
	return this;
    }
    
    /** setting location */
    public ISun location(Location location){
	if(location == null){ IOut.err("location is null"); return this; }
	this.latitude = location.latitude;
	this.longitude = location.longitude;
	this.elevation = location.elevation;
	this.timeZone = location.timeZone;
	return this;
    }
    
    
    /** set north direction on XY plane */
    public ISun northDir(IVec northDirection){ northDir = northDirection; return this; }
    public ISun northDirection(IVec northDirection){ return northDir(northDirection); }
    
    
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       second can include fraction of millisecond */
    public IVec direction(int year, int month, int day, int hour, int minute, double second, boolean daylightSavingTime){
	return dir(year,month,day,hour,minute,second,daylightSavingTime);
    }
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       second can include fraction of millisecond */
    public IVec direction(int year, int month, int day, int hour, int minute, double second){
	return dir(year,month,day,hour,minute,second);
    }
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       minute can include fraction of second */
    public IVec direction(int year, int month, int day, int hour, double minute, boolean daylightSavingTime){
	return dir(year,month,day,hour,minute,daylightSavingTime);
    }
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       minute can include fraction of second */
    public IVec direction(int year, int month, int day, int hour, double minute){
	return dir(year,month,day,hour,minute);
    }
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       hour can include fraction of minute */
    public IVec direction(int year, int month, int day, double hour, boolean daylightSavingTime){
	return dir(year,month,day,hour,daylightSavingTime);
    }
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       hour can include fraction of minute */
    public IVec direction(int year, int month, int day, double hour){
	return dir(year,month,day,hour);
    }
    /* calculate a solar vector (pointing sun location from the origin) */
    public IVec direction(){ return dir(); }
    
    
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       second can include fraction of millisecond */
    public IVec dir(int year, int month, int day, int hour, int minute, double second, boolean daylightSavingTime){
	time(year,month,day,hour,minute,second,daylightSavingTime);
	return dir();
    }
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       second can include fraction of millisecond */
    public IVec dir(int year, int month, int day, int hour, int minute, double second){
	time(year,month,day,hour,minute,second);
	return dir();
    }
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       minute can include fraction of second */
    public IVec dir(int year, int month, int day, int hour, double minute, boolean daylightSavingTime){
	time(year,month,day,hour,minute,daylightSavingTime);
	return dir();
    }
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       minute can include fraction of second */
    public IVec dir(int year, int month, int day, int hour, double minute){
	time(year,month,day,hour,minute);
	return dir();
    }
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       hour can include fraction of minute */
    public IVec dir(int year, int month, int day, double hour, boolean daylightSavingTime){
	time(year,month,day,hour,daylightSavingTime);
	return dir();
    }
    /* calculate a solar vector (pointing sun location from the origin) at the given time.
       hour can include fraction of minute */
    public IVec dir(int year, int month, int day, double hour){
	time(year,month,day,hour);
	return dir();
    }
    /* calculate a solar vector (pointing sun location from the origin) */
    public IVec dir(){
	if(updateAngle){ calc(); }
	return direction(altitude, azimuth,northDir);
    }
    
    
    /** calculate azimuth at the given time.
	second can include fraction of millisecond */
    public double azimuth(int year, int month, int day, int hour, int minute, double second,
			  boolean daylightSavingTime){
	time(year,month,day,hour,minute,second,daylightSavingTime);
	return azimuth();
    }
    /** calculate azimuth at the given time.
	second can include fraction of millisecond */
    public double azimuth(int year, int month, int day, int hour, int minute, double second){
	time(year,month,day,hour,minute,second);
	return azimuth();
    }
    /** calculate azimu at the given time.
	minute can include fraction of second */
    public double azimuth(int year, int month, int day, int hour, double minute,
			  boolean daylightSavingTime){
	time(year,month,day,hour,minute,daylightSavingTime);
	return azimuth();
    }
    /** calculate azimuth at the given time.
	minute can include fraction of second */
    public double azimuth(int year, int month, int day, int hour, double minute){
	time(year,month,day,hour,minute);
	return azimuth();
    }
    /** calculate azimuth at the given time.
	hour can include fraction of minutes */
    public double azimuth(int year, int month, int day, double hour, boolean daylightSavingTime){
	time(year,month,day,hour,daylightSavingTime);
	return azimuth();
    }
    /** calculate azimuth at the given time.
	hour can include fraction of minutes */
    public double azimuth(int year, int month, int day, double hour){
	time(year,month,day,hour);
	return azimuth();
    }
    /** calculate azimuth. */
    public double azimuth(){
	if(updateAngle){ calc(); }
	return azimuth;
    }
    
    /** calculate altitude angle at the given time.
	second can include fraction of millisecond */
    public double altitude(int year, int month, int day, int hour, int minute, double second,
			   boolean daylightSavingTime){
	time(year,month,day,hour,minute,second,daylightSavingTime);
	return altitude();
    }
    /** calculate altitude angle at the given time.
	second can include fraction of millisecond */
    public double altitude(int year, int month, int day, int hour, int minute, double second){
	time(year,month,day,hour,minute,second);
	return altitude();
    }
    /** calculate altitude angle at the given time.
	minute can include fraction of second */
    public double altitude(int year, int month, int day, int hour, double minute,
			   boolean daylightSavingTime){
	time(year,month,day,hour,minute,daylightSavingTime);
	return altitude();
    }
    /** calculate altitude angle at the given time.
	minute can include fraction of second */
    public double altitude(int year, int month, int day, int hour, double minute){
	time(year,month,day,hour,minute);
	return altitude();
    }
    /** calculate altitude angle at the given time.
	hour can include fraction of minute */
    public double altitude(int year, int month, int day, double hour, boolean daylightSavingTime){
	time(year,month,day,hour,daylightSavingTime);
	return altitude();
    }
    /** calculate altitude angle at the given time.
	hour can include fraction of minute */
    public double altitude(int year, int month, int day, double hour){
	time(year,month,day,hour);
	return altitude();
    }
    /** calculate altitude angle */
    public double altitude(){ 
	if(updateAngle){ calc(); }
	return altitude;
    }
    
    
    
    /** @return array of 2 double, first azimuth and second altitude */
    public double[] angles(int year, int month, int day, int hour, int minute, double second,
			   boolean daylightSavingTime){
	time(year,month,day,hour,minute,second,daylightSavingTime);
	return angles();
    }
    /** @return array of 2 double, first azimuth and second altitude */
    public double[] angles(int year, int month, int day, int hour, int minute, double second){
	time(year,month,day,hour,minute,second);
	return angles();
    }
    /** @return array of 2 double, first azimuth and second altitude */
    public double[] angles(int year, int month, int day, int hour, double minute,
			 boolean daylightSavingTime){
	time(year,month,day,hour,minute,daylightSavingTime);
	return angles();
    }
    /** @return array of 2 double, first azimuth and second altitude */
    public double[] angles(int year, int month, int day, int hour, double minute){
	time(year,month,day,hour,minute);
	return angles();
    }
    /** @return array of 2 double, first azimuth and second altitude */
    public double[] angles(int year, int month, int day, double hour, boolean daylightSavingTime){
	time(year,month,day,hour,daylightSavingTime);
	return angles();
    }
    /** @return array of 2 double, first azimuth and second altitude */
    public double[] angles(int year, int month, int day, double hour){
	time(year,month,day,hour);
	return angles();
    }
    /** @return array of 2 double, first azimuth and second altitude */
    public double[] angles(){ 
	if(updateAngle){ calc(); }
	return new double[]{ azimuth, altitude };
    }
    
    
    
    public ISun calc(){
	double[] angle = calcAngle(latitude, longitude, elevation, timeZone, 
				   year, month, day, hour,
				   daylightSaving);
	azimuth = angle[1];
	altitude = angle[0];
	updateAngle = false;
	return this;
    }
    
    
    
    /** search hour in the specified date when sun comes in the direction of azimuth */
    public double hourAt(IVec azimuthDir, int year, int month, int day, boolean daylightSavingTime){
	//return hourAt(azimuthDir, this.northDir, year, month, day, daylightSavingTime);
	double az = azimuth(azimuthDir, northDir);
	return hourAt(az,year,month,day,daylightSavingTime);
    }
    
    /** search hour in the specified date when sun comes in the direction of azimuth */
    /* // north direction should be set at the constructor or northDir()
    public double hourAt(IVec azimuthDir, IVec northDirection,
			 int year, int month, int day, boolean daylightSavingTime){
	double az = azimuth(azimuthDir, northDirection);
	return hourAt(az,year,month,day,daylightSavingTime);
    }
    */
    
    /** search hour in the specified date when sun comes in the direction of azimuth */
    public double hourAt(double azimuth, int year, int month, int day, boolean daylightSavingTime){
	//final int sampleNum =24*60; // check every min
	return hourAt(azimuth,year,month,day,daylightSavingTime,bufferSampleNum);
    }
    
    /** search hour in the specified date when sun comes in the direction of azimuth */
    public double hourAt(double azimuth, int year, int month, int day, boolean daylightSavingTime, 
			 int sampleNumber){
	if(buffers==null){ buffers = new ArrayList<SearchBuffer>(); }
	
	SearchBuffer buf = null;
	for(int i=buffers.size()-1; i>=0 && buf==null; i--){
	    SearchBuffer b = buffers.get(i);
	    //if(b.date.year==year && b.date.month==month &&b.date.day==day && b.date.daylightSavingTime==daylightSavingTime &&b.sampleNumber==sampleNumber){ buf = b; }
	    if(b.year==year && b.month==month &&b.day==day && b.daylightSavingTime==daylightSavingTime &&b.sampleNumber==sampleNumber){ buf = b; }
	}
	
	if(buf==null){
	    buf = new SearchBuffer(this,year,month,day,daylightSavingTime,sampleNumber);
	    buf.calc();
	    buffers.add(buf);
	}
	
	return buf.hourAt(azimuth);
	/*
	if(buffer==null ||
	   buffer.date.year!=year||
	   buffer.date.month!=month||
	   buffer.date.day!=day||
	   buffer.date.daylightSavingTime!=daylightSavingTime||
	   buffer.sampleNumber!=sampleNumber){
	    buffer = new SearchBuffer(this,year,month,day,daylightSavingTime,sampleNumber);
	    buffer.calc();
	}
	return buffer.hourAt(azimuth);
	*/
    }
    
    /** search altitude in the specified date when sun comes in the direction of azimuth */
    /* // north direction should be set at the constructor or northDir()
    public double altitudeAt(IVec azimuthDir, IVec northDirection, int year, int month, int day){
	double h = hourAt(azimuthDir,northDirection,year,month,day,false); // daylightsaving is off
	return altitude(year, month, day, h, false);
    }
    */
    /** search altitude in the specified date when sun comes in the direction of azimuth */
    public double altitudeAt(IVec azimuthDir, int year, int month, int day){
	double h = hourAt(azimuthDir, year, month, day, false);
	return altitude(year, month, day, h, false);
    }
    
    /** search altitude in the specified date when sun comes in the direction of azimuth */
    public double altitudeAt(double azimuth, int year, int month, int day){
	double h = hourAt(azimuth, year, month, day, false);
	return altitude(year, month, day, h, false);
    }
    
    /** search altitude in the specified date when sun comes in the direction of azimuth */
    public double altitudeAt(double azimuth, int year, int month, int day, int sampleNumber){
	double h = hourAt(azimuth, year, month, day, false, sampleNumber);
	return altitude(year, month, day, h, false);
    }
    
    /** solar direction vector at specified azimuth on the date */
    /*// north direction should be set at the constructor or northDir()
    public IVec directionAt(IVec azimuthDir, IVec northDirection, int year, int month, int day){
	return dirAt(azimuthDir, northDirection, year, month, day);
    }
    */
    
    /** solar direction vector at specified azimuth on the date */
    public IVec directionAt(IVec azimuthDir, int year, int month, int day){
	return dirAt(azimuthDir, year, month, day);
    }
    
    /** solar direction vector at specified azimuth on the date */
    public IVec directionAt(double azimuth, int year, int month, int day){
	return dirAt(azimuth, year, month, day);
    }
    
    /** solar direction vector at specified azimuth on the date */
    public IVec directionAt(double azimuth, int year, int month, int day, int sampleNumber){
	return dirAt(azimuth, year, month, day, sampleNumber);
    }
    
    
    /** solar direction vector at specified azimuth on the date */
    /* // north direction should be set at the constructor or northDir()
    public IVec dirAt(IVec azimuthDir, IVec northDirection, int year, int month, int day){
	double h = hourAt(azimuthDir,northDirection,year,month,day,false);
	return dir(year, month, day, h, false);
    }
    */
    
    /** solar direction vector at specified azimuth on the date */
    public IVec dirAt(IVec azimuthDir, int year, int month, int day){
	double h = hourAt(azimuthDir, year, month, day, false);
	return dir(year, month, day, h, false);
    }
    
    /** solar direction vector at specified azimuth on the date */
    public IVec dirAt(double azimuth, int year, int month, int day){
	double h = hourAt(azimuth, year, month, day, false);
	return dir(year, month, day, h, false);
    }
    
    /** solar direction vector at specified azimuth on the date */
    public IVec dirAt(double azimuth, int year, int month, int day, int sampleNumber){
	double h = hourAt(azimuth, year, month, day, false, sampleNumber);
	return dir(year, month, day, h, false);
    }
    
    
    
    
    /** min altitude angle in the specified azimuth through the year */
    /* // north direction should be set at the constructor or northDir().
    public double minAltitudeAt(IVec azimuthDir, IVec northDirection, int year){
	double az = azimuth(azimuthDir, northDirection);
	return minAltitudeAt(az, year);
    }
    */
    
    /** min altitude angle in the specified azimuth through the year */
    public double minAltitudeAt(IVec azimuthDir, int year){
	//return minAltitudeAt(azimuthDir,this.northDir,year);
	double az = azimuth(azimuthDir, northDir);
	return minAltitudeAt(az, year);
    }
    
    /** min altitude angle in the specified azimuth through the year */
    public double minAltitudeAt(double azimuth, int year){
	return minAltitudeAt(azimuth, new DateRange(year), bufferSampleNum);
    }
    
    /** min altitude angle in the specified azimuth through the year */
    /*
    public double minAltitudeAt(double azimuth, int year, int sampleNumber){
	return minAltitudeAt(azimuth, new DateRange(year), sampleNumber);
	//int num = isLeapYear(year)?366:365;
	//double minAlt=1000; // 
	//for(int i=1; i<=num; i++){
	//    int[] monthAndDay = dayCountToMonthAndDate(i, year);
	//    double alt = altitudeAt(azimuth, year, monthAndDay[0], monthAndDay[1], sampleNumber);
	//    if(i==1 || alt<minAlt){ minAlt=alt; }
	//}
	//return minAlt;
    }
    */
    
    /** min altitude angle in the specified azimuth through the year */
    public double minAltitudeAt(IVec azimuthDir, DateRange dates){
	return minAltitudeAt(azimuth(azimuthDir, northDir), dates);
    }
    
    /** min altitude angle in the specified azimuth through the year */
    public double minAltitudeAt(double azimuth, DateRange dates){
	return minAltitudeAt(azimuth, dates, bufferSampleNum);
    }
    
    /** min altitude angle in the specified azimuth through the year */
    public double minAltitudeAt(double azimuth, DateRange dates, int sampleNumber){
	double minAlt=10000; // alt should be from -90 to +90
	for(DateIterator it = dates.getIterator(); !it.past(dates); it.next()){
	    double alt = altitudeAt(azimuth, it.year, it.month, it.day, sampleNumber);
	    if(alt<minAlt) minAlt = alt;
	}
	return minAlt;
    }
    
    
    
    /** max altitude angle in the specified azimuth through the year */
    /* // north direction should be set at the constructor or northDir().
    public double maxAltitudeAt(IVec azimuthDir, IVec northDirection, int year){
	double az = azimuth(azimuthDir, northDirection);
	return maxAltitudeAt(az, year);
    }
    */
    
    /** max altitude angle in the specified azimuth through the year */
    public double maxAltitudeAt(IVec azimuthDir, int year){
	//return maxAltitudeAt(azimuthDir,this.northDir,year);
	double az = azimuth(azimuthDir, northDir);
	return maxAltitudeAt(az,year);
    }
    
    /** max altitude angle in the specified azimuth through the year */
    public double maxAltitudeAt(double azimuth, int year){
	return maxAltitudeAt(azimuth, new DateRange(year), bufferSampleNum);
    }
    
    /** max altitude angle in the specified azimuth through the year */
    /*
    public double maxAltitudeAt(double azimuth, int year, int sampleNumber){
	return maxAltitudeAt(azimuth,new DateRange(year),sampleNumber);
	//int num = isLeapYear(year)?366:365;
	//double maxAlt=1000; // 
	//for(int i=1; i<=num; i++){
	//    int[] monthAndDay = dayCountToMonthAndDate(i, year);
	//    double alt = altitudeAt(azimuth, year, monthAndDay[0], monthAndDay[1], sampleNumber);
	//    if(i==1 || alt>maxAlt){ maxAlt=alt; }
	//}
	//return maxAlt;
    }
    */
    
    /** max altitude angle in the specified azimuth through the year */
    public double maxAltitudeAt(IVec azimuthDir, DateRange dates){
	return maxAltitudeAt(azimuth(azimuthDir, northDir),dates);
    }
    
    /** max altitude angle in the specified azimuth through the year */
    public double maxAltitudeAt(double azimuth, DateRange dates){
	return maxAltitudeAt(azimuth, dates, bufferSampleNum);
    }
    
    /** max altitude angle in the specified azimuth through the year */
    public double maxAltitudeAt(double azimuth, DateRange dates, int sampleNumber){
	double maxAlt=-10000; // alt should be from -90 to +90
	for(DateIterator it = dates.getIterator(); !it.past(dates); it.next()){
	    double alt = altitudeAt(azimuth, it.year, it.month, it.day, sampleNumber);
	    if(alt>maxAlt) maxAlt = alt;
	}
	return maxAlt;
    }
    
    /** max altitude angle in the specified azimuth through the year */
    /* // north direction should be set at the constructor or northDir().
    public double[] minAndMaxAltitudeAt(IVec azimuthDir, IVec northDirection, int year){
	double az = azimuth(azimuthDir, northDirection);
	return minAndMaxAltitudeAt(az, year);
    }
    */
    
    /** max altitude angle in the specified azimuth through the year */
    public double[] minAndMaxAltitudeAt(IVec azimuthDir, int year){
	//return minAndMaxAltitudeAt(azimuthDir,this.northDir,year);
	double az = azimuth(azimuthDir, northDir);
	return minAndMaxAltitudeAt(az, year);
    }
    
    /** max altitude angle in the specified azimuth through the year */
    public double[] minAndMaxAltitudeAt(double azimuth, int year){
	return minAndMaxAltitudeAt(azimuth, new DateRange(year), bufferSampleNum);
    }
    
    /** max altitude angle in the specified azimuth through the year */
    /*
    public double[] minAndMaxAltitudeAt(double azimuth, int year, int sampleNumber){
	return minAndMaxAltitudeAt(azimuth,new DateRange(year), sampleNumber);
	//int num = isLeapYear(year)?366:365;
	//double minAlt=1000, maxAlt=1000; //
	//for(int i=1; i<=num; i++){
	//    int[] monthAndDay = dayCountToMonthAndDate(i, year);
	//    double alt = altitudeAt(azimuth, year, monthAndDay[0], monthAndDay[1], sampleNumber);
	//    if(i==1 || alt<minAlt){ minAlt=alt; }
	//    if(i==1 || alt>maxAlt){ maxAlt=alt; }
	//}
	//return new double[]{ minAlt, maxAlt };
    }
    */
    
    /** max altitude angle in the specified azimuth through the year */
    public double[] minAndMaxAltitudeAt(IVec azimuthDir, DateRange dates){
	return minAndMaxAltitudeAt(azimuth(azimuthDir, northDir), dates);
    }
    
    /** max altitude angle in the specified azimuth through the year */
    public double[] minAndMaxAltitudeAt(double azimuth, DateRange dates){
	return minAndMaxAltitudeAt(azimuth, dates, bufferSampleNum);
    }
    
    /** max altitude angle in the specified azimuth through the year */
    public double[] minAndMaxAltitudeAt(double azimuth, DateRange dates, int sampleNumber){
	double minAlt=10000; // alt should be from -90 to +90
	double maxAlt=-10000; // alt should be from -90 to +90
	for(DateIterator it = dates.getIterator(); !it.past(dates); it.next()){
	    double alt = altitudeAt(azimuth, it.year, it.month, it.day, sampleNumber);
	    if(alt>maxAlt) maxAlt = alt;
	    if(alt<minAlt) minAlt = alt;
	}
	return new double[]{ minAlt, maxAlt };
    }
    
    /** lowest solar vector in the specified azimuth through the year */
    /* // north direction should be set at the constructor or northDir().
    public IVec lowestDirAt(IVec azimuthDir, IVec northDirection, int year){
	double az = azimuth(azimuthDir, northDirection);
	double altitude = minAltitudeAt(az, year);
	return direction(altitude, az, northDirection);
    }
    */
    
    /** lowest solar vector in the specified azimuth through the year */
    public IVec lowestDirAt(IVec azimuthDir, int year){
	double az = azimuth(azimuthDir, northDir);
	double alt = minAltitudeAt(az, year);
	return direction(alt, az, northDir);
    }
    
    /** lowest solar vector in the specified azimuth through the year */
    public IVec lowestDirAt(double azimuth, int year){
	double alt = minAltitudeAt(azimuth, year);
	return direction(alt, azimuth, northDir);
    }
    
    /** lowest solar vector in the specified azimuth through the year */
    /*
    public IVec lowestDirAt(double azimuth, int year, int sampleNumber){
	double altitude = minAltitudeAt(azimuth, year, sampleNumber);
	return direction(altitude, azimuth, northDir);
    }    
    */
    
    /** lowest solar vector in the specified azimuth through the year */
    public IVec lowestDirAt(IVec azimuthDir, DateRange dates){
	double az = azimuth(azimuthDir, northDir);
	double alt = minAltitudeAt(az, dates);
	return direction(alt, az, northDir);
    }
    
    /** lowest solar vector in the specified azimuth through the year */
    public IVec lowestDirAt(double azimuth, DateRange dates){
	double alt = minAltitudeAt(azimuth, dates);
	return direction(alt, azimuth, northDir);
    }
    
    /** lowest solar vector in the specified azimuth through the year */
    public IVec lowestDirAt(double azimuth, DateRange dates, int sampleNumber){
	double alt = minAltitudeAt(azimuth, dates, sampleNumber);
	return direction(alt, azimuth, northDir);
    }
    
    
    /** highest solar vector in the specified azimuth through the year */
    /* // north direction should be set at the constructor or northDir().
    public IVec highestDirAt(IVec azimuthDir, IVec northDirection, int year){
	double az = azimuth(azimuthDir, northDirection);
	double altitude = maxAltitudeAt(az, year);
	return direction(altitude, az, northDirection);
    }
    */
    
    /** highest solar vector in the specified azimuth through the year */
    public IVec highestDirAt(IVec azimuthDir, int year){
	double az = azimuth(azimuthDir, northDir);
	double altitude = maxAltitudeAt(az, year);
	return direction(altitude, az, northDir);
    }
    
    /** highest solar vector in the specified azimuth through the year */
    public IVec highestDirAt(double azimuth, int year){
	double altitude = maxAltitudeAt(azimuth, year);
	return direction(altitude, azimuth, northDir);
    }
    
    /** highest solar vector in the specified azimuth through the year */
    /*
    public IVec highestDirAt(double azimuth, int year, int sampleNumber){
	double altitude = maxAltitudeAt(azimuth, year, sampleNumber);
	return direction(altitude, azimuth, northDir);
    }    
    */
    
    /** highest solar vector in the specified azimuth through the year */
    public IVec highestDirAt(IVec azimuthDir, DateRange dates){
	double az = azimuth(azimuthDir, northDir);
	double alt = maxAltitudeAt(az, dates);
	return direction(alt, az, northDir);
    }
    
    /** highest solar vector in the specified azimuth through the year */
    public IVec highestDirAt(double azimuth, DateRange dates){
	double alt = maxAltitudeAt(azimuth, dates);
	return direction(alt, azimuth, northDir);
    }
    
    /** highest solar vector in the specified azimuth through the year */
    public IVec highestDirAt(double azimuth, DateRange dates, int sampleNumber){
	double alt = maxAltitudeAt(azimuth, dates, sampleNumber);
	return direction(alt, azimuth, northDir);
    }
    
    /** highest and lowest solar vector in the specified azimuth through the year */
    /* // north direction should be set at the constructor or northDir().
    public IVec[] lowestAndHighestDirAt(IVec azimuthDir, IVec northDirection, int year){
	double az = azimuth(azimuthDir, northDirection);
	double[] altitude = minAndMaxAltitudeAt(az, year);
	return new IVec[]{ direction(altitude[0], az, northDirection),
			   direction(altitude[1], az, northDirection) };
    }
    */
    
    /** highest and lowest solar vector in the specified azimuth through the year */
    public IVec[] lowestAndHighestDirAt(IVec azimuthDir, int year){
	double az = azimuth(azimuthDir, this.northDir);
	double[] altitude = minAndMaxAltitudeAt(az, year);
	return new IVec[]{ direction(altitude[0], az, northDir),
			   direction(altitude[1], az, northDir) };
    }
    
    /** highest and lowest solar vector in the specified azimuth through the year */
    public IVec[] lowestAndHighestDirAt(double azimuth, int year){
	double[] altitude = minAndMaxAltitudeAt(azimuth, year);
	return new IVec[]{ direction(altitude[0], azimuth, northDir),
			   direction(altitude[1], azimuth, northDir) };
    }
    
    /** highest and lowest solar vector in the specified azimuth through the year */
    /*
    public IVec[] lowestAndHighestDirAt(double azimuth, int year, int sampleNumber){
	double[] altitude = minAndMaxAltitudeAt(azimuth, year, sampleNumber);
	return new IVec[]{ direction(altitude[0], azimuth, northDir),
			   direction(altitude[1], azimuth, northDir) };
    }
    */
    
    /** highest and lowest solar vector in the specified azimuth through the year */
    public IVec[] lowestAndHighestDirAt(IVec azimuthDir, DateRange dates){
	double az = azimuth(azimuthDir, this.northDir);
	double[] alt = minAndMaxAltitudeAt(az, dates);
	return new IVec[]{ direction(alt[0], az, northDir),
			   direction(alt[1], az, northDir) };
    }
    
    /** highest and lowest solar vector in the specified azimuth through the year */
    public IVec[] lowestAndHighestDirAt(double azimuth, DateRange dates){
	double[] alt = minAndMaxAltitudeAt(azimuth, dates);
	return new IVec[]{ direction(alt[0], azimuth, northDir),
			   direction(alt[1], azimuth, northDir) };
    }
    
    /** highest and lowest solar vector in the specified azimuth through the year */
    public IVec[] lowestAndHighestDirAt(double azimuth, DateRange dates, int sampleNumber){
	double[] alt = minAndMaxAltitudeAt(azimuth, dates, sampleNumber);
	return new IVec[]{ direction(alt[0], azimuth, northDir),
			   direction(alt[1], azimuth, northDir) };
    }
    
    
    
    
    /************************************************************************************
     * static methods
     ************************************************************************************/
    
    /** calculate azimuth angle at the given time and location
	hour can include fraction of minutes */
    public static double azimuth(double latitude, double longitude, double elevation,
				 double timeZone,
				 int year, int month, int day, double hour,
				 boolean daylightSavingTime){
	double[] retval = calcAngle(latitude, longitude, elevation,timeZone,
				    year,month,day,hour,daylightSavingTime);
	return retval[1];
    }
	
    /** calculate altitude angle at the given time and location
	hour can include fraction of minutes */
    public static double altitude(double latitude, double longitude, double elevation,
				  double timeZone,
				  int year, int month, int day, double hour,
				  boolean daylightSavingTime){
	double[] retval = calcAngle(latitude, longitude, elevation,timeZone,
				    year,month,day,hour,daylightSavingTime);
	return retval[0];
    }
    
    
    /** conveting vector to azimuth angle */
    public static double azimuth(IVec azimuthDir, IVec northDirection){
	//project onto xy plane
	if(northDirection==null) northDirection = new IVec(0,1,0);
	//double az = -azimuthDir.cp().z(0).angle(northDirection.cp().z(0), IVec.zaxis);
	if(azimuthDir.z!=0.0) azimuthDir = azimuthDir.cp().z(0);
	if(northDirection.cp().z!=0.0) northDirection = northDirection.cp().z(0);
	double az = azimuthDir.angle(northDirection, IVec.zaxis);
	az *= 180/Math.PI;
	if(az < 0) az+=360;
	return az;
    }
    
    
    /* calculate a solar vector (pointing sun location from the origin) at the given time and location.
       hour can include fraction of minute */
    public static IVec dir(double latitude, double longitude, double elevation,
			   double timeZone, IVec northDir,
			   int year, int month, int day, double hour, boolean daylightSavingTime){
	double[] retval = calcAngle(latitude, longitude, elevation,timeZone,
				    year,month,day,hour,daylightSavingTime);
	return direction(retval[0], retval[1], northDir);
    }
    
    /* alias of dir */
    public static IVec direction(double latitude, double longitude, double elevation,
				 double timeZone, IVec northDir,
				 int year, int month, int day, double hour, boolean daylightSavingTime){
	return dir(latitude,longitude,elevation,timeZone,northDir,
		   year, month, day, hour, daylightSavingTime);
    }
    
    /** converting azimuth and altitude to XYZ vector */
    public static IVec direction(double altitude, double azimuth, IVec northDir){
	IVec d=null;
	if(northDir==null) d = new IVec(0,1,0); // Y axis
	else d = northDir.cp().z(0).unit();
	IVec axis = d.cross(IVec.zaxis);
	d.rot(axis, toRadians(altitude));
	if(measureAzimuthFromSouth){ d.rot(IVec.zaxis, toRadians(180.-azimuth)); }
	else{ d.rot(IVec.zaxis, toRadians(-azimuth)); }
	return d;
    }
    
    /** alias of calcAngle. */
    public static double[] angles(double latitude, double longitude, double elevation,
				  double timeZone, 
				  int year, int month, int day, double hour,
				  boolean daylightSavingTime){
	return calcAngle(latitude,longitude,elevation,timeZone,year,month,day,hour,daylightSavingTime);
    }
    
    /** calculate altitude and azimuth angle.
	@return array of 2 double value; first is altitude, second is azimuth */
    public static double[] calcAngle(double latitude, double longitude, double elevation,
				     double timeZone, 
				     int year, int month, int day, double hour,
				     boolean daylightSavingTime){
	hour -= timeZone;
	if(daylightSavingTime) hour -= 1;
	double julianDate = julianDate(year,month,day,hour);
	double[] ret = calcEquatorialCoordinates(julianDate);
	double[] ret2 = calcHorizontalCoordinates(ret[0], ret[1], julianDate,
						  latitude, longitude, elevation,
						  ret[2]);
	return ret2;
    }
    
    public static double[] calcEquatorialCoordinates(double julianDate){
	double time = (julianDate - 2415020.0)/36525.0;
	
	// sun's mean longitude
	double longitude = (279.696678+((36000.768925*time)%360.0))*3600;
	
	// ellipticity of the orbit
	double earthAnomaly = 358.475844 + (35999.049750*time)%360.0;
	double ellipticityCorrection  = (6910.1 - 17.2*time)*sin(toRadians(earthAnomaly)) +
	    72.3*sin(toRadians(2.0*earthAnomaly));
	longitude += ellipticityCorrection;
	
	// venus perturbation
	double venusAnomalty = 212.603219 + (58517.803875*time)%360.0;
	double venusCorrection = 4.8 * cos(toRadians(299.1017 + venusAnomalty - earthAnomaly)) + 
	    5.5*cos(toRadians(148.3133 + 2.0*venusAnomalty - 2.0*earthAnomaly)) + 
	    2.5*cos(toRadians(315.9433 + 2.0*venusAnomalty - 3.0*earthAnomaly)) +
	    1.6*cos(toRadians(345.2533 + 3.0*venusAnomalty - 4.0*earthAnomaly)) + 
	    1.0*cos(toRadians(318.15   + 3.0*venusAnomalty - 5.0*earthAnomaly));
	longitude += venusCorrection;
	
	// mars perturbation
	double marsAnomalty = 319.529425 + (19139.858500*time)%360.0;
	double marsCorrection = 2.0*cos(toRadians(343.8883 - 2.0*marsAnomalty + 2.0*earthAnomaly))+ 
            1.8*cos(toRadians(200.4017 - 2.0*marsAnomalty + earthAnomaly));
	longitude += marsCorrection;
	
	// jupiter perturbation
	double jupiterAnomalty = 225.328328 + (3034.6920239*time)%360.0;
	double jupiterCorrection = 7.2*cos(toRadians(179.5317 - jupiterAnomalty + earthAnomaly)) +
	    2.6*cos(toRadians(263.2167 - jupiterAnomalty)) +
	    2.7*cos(toRadians( 87.1450 - 2.0*jupiterAnomalty + 2.0*earthAnomaly)) + 
	    1.6*cos(toRadians(109.4933 - 2.0*jupiterAnomalty + earthAnomaly));
	longitude += jupiterCorrection;
	
	// moon perturbation
	double moonElongation = 350.7376814  + (445267.11422*time)%360.0;
	double moonCorrection  = 6.5 * sin(toRadians(moonElongation));
	longitude += moonCorrection;
	
	// long period term
	double longTerm  =  6.4*sin(toRadians(231.19 + 20.20*time));
	longitude += longTerm;
	longitude = (longitude + 2592000.0)%1296000.0;
	double eclipticLongitude = longitude/3600.0;
	
	// aberration
	longitude -= 20.50;
	
	// nutation
	double moonOmega = 259.183275 - (1934.142008*time)%360.0;
	longitude  -= 17.2*sin(toRadians(moonOmega));
	
	// obliquity
	double obliquity  = 23.452294 - 0.0130125*time + 9.2*cos(toRadians(moonOmega))/3600.0;
	
	// right ascension
	longitude /= 3600.0;
	double rightAscension = atan2(sin(toRadians(longitude))*cos(toRadians(obliquity)),
				      cos(toRadians(longitude)));
	if(rightAscension<0) rightAscension += 2*PI;
	
	// declination
	double declination = asin(sin(toRadians(longitude))*sin(toRadians(obliquity)));
	
	rightAscension = toDegrees(rightAscension);
	declination = toDegrees(declination);
	
	return new double[]{ rightAscension, declination, eclipticLongitude, obliquity };
    }
    
    public static double[] calcHorizontalCoordinates(double rightAscension, double declination,
						     double julianDate,
						     double latitude, double longitude,
						     double elevation,
						     double eclipticLongitude){
	
	double now = (julianDate - 2451545.)/365.25 + 2000.0; // current equinox
	
	boolean before1950=false;
	if(precessionCorrection){
	    if(before1950){
		double[] r = precess(rightAscension, declination, 1950.0, now, true/*fk4*/);
		rightAscension = r[0];
		declination = r[1];
	    }
	    else{
		double[] r = precess(rightAscension, declination, 2000.0, now, false);
		rightAscension = r[0];
		declination = r[1];
	    }
	}

	
	
	// nutation
	double[] nutationVal = conutate(julianDate, rightAscension, declination);
	if(aberrationCorrection){
	    // aberration
	    double[] aberrationVal = coaberration(julianDate, rightAscension, declination,
						  nutationVal[2], eclipticLongitude);
	    rightAscension += aberrationVal[0]/3600.;
	    declination += aberrationVal[1]/3600.;
	}
	if(nutationCorrection){
	    rightAscension += nutationVal[0]/3600.;
	    declination += nutationVal[1]/3600.;
	}
	
	
	double siderealTime = localSiderealTime(longitude, julianDate) * 15; // degree;
	double apparent = siderealTime + nutationVal[3]*cos(nutationVal[2])/3600.;
	// hour angle in degrees
	double hourAngle = mod(apparent - rightAscension, 360);
	
	
	
	// converting hour angle/declination to azimuth/altitude
	double sh = sin(toRadians(hourAngle));
	double ch = cos(toRadians(hourAngle));
	double sd = sin(toRadians(declination));
	double cd = cos(toRadians(declination));
	double sl = sin(toRadians(latitude));
	double cl = cos(toRadians(latitude));
	
	double x = -ch*cd*sl + sd*cl;
	double y = -sh*cd;
	double z = ch*cd*cl + sd*sl;
	double r = sqrt(x*x + y*y);
	
	double azimuth = mod(toDegrees(atan2(y,x)), 360.);
	double altitude = toDegrees(atan2(z,r));
	
	if(measureAzimuthFromSouth){ azimuth = (azimuth+180.)%360.; }
	if(refractionCorrection){ altitude = corefract(altitude, elevation); }
	
	return new double[]{ altitude, azimuth };
    }
    
    
    public static double corefract(double altitudeAngle, double elevation){
	double alpha = 0.0065; // lapse rate
	double temperature = 211.5;
	if(elevation <= 11000) temperature = 283.0-alpha*elevation;
	double pressure = 1010.*pow(1.0-6.5/288000.0*elevation, 5.255 );
	double epsilon = 0.25; 
	
	//double aout = altitudeAngle - corefractForward(altitudeAngle,pressure,temperature);
	//return aout;
	double dr = corefractForward(altitudeAngle, pressure, temperature);
	double cur = altitudeAngle+dr;
	double last = 0;
	final int maxLoop = 1000000;
	for(int i=0; i<maxLoop && (i==0 || abs(last-cur)*3600 >= epsilon) ; i++){
	    last = cur;
	    dr = corefractForward(cur, pressure, temperature);
	    cur = altitudeAngle + dr;
	}
	return cur;
    }
    
    public static double corefractForward(double altitudeAngle, double pressure, double temperature){
	double refraction = 1./tan(toRadians(altitudeAngle+7.31/(altitudeAngle+4.4)))/60.;
	//double refraction = 0.0166667/tan(toRadians(altitudeAngle+7.31/(altitudeAngle+4.4)));
	if(altitudeAngle < 15.) refraction = 3.569*(0.1594 + 0.0196*altitudeAngle +
						    0.00002*altitudeAngle*altitudeAngle)/
	    (1.+.505*altitudeAngle+.0845*altitudeAngle*altitudeAngle);
	refraction *= pressure/1010. * 283.0/temperature;
	return refraction;
    }
    
    /*
    public static double[] hadec2altaz(double ha, double dec, double lat){
	ha = toRadians(ha);
	dec = toRadians(dec);
	lat = toRadians(lat);
	
	double sh = sin(ha);
	double ch = cos(ha);
	double sd = sin(dec);
	double cd = cos(dec);
	double sl = sin(lat);
	double cl = cos(lat);
	
	double x = -ch*cd*sl + sd*cl;
	double y = -sh*cd;
	double z = ch*cd*cl + sd*sl;
	double r = sqrt(x*x + y*y);
	
	double az = mod(toDegrees(atan2(y,x)), 360);
	double alt = toDegrees(atan2(z,r));
	
	if(measureAzimuthFromSouth){ az = (az+180.)%360.; }
	
	return new double[]{ alt, az };
    }
    */
    
    /** convert from local civil time to local mean siderial time. */
    public static double localSiderealTime(double lng, double jd){
	double time0 = jd - 2451545.0;
	double time = time0 / 36525;
	// gst in second
	double theta =  280.46061837 + 360.98564736629*time0 +
	    time*time*(0.000387933-time/38710000.0);
	double lst = (theta + lng)/15.0;
	lst = mod(lst, 24);
	return lst;
    }
    
    public static double[] coaberration(double julianDate,
					double rightAscension,
					double declination,
					double eps,
					double eclipticLongitude){
	
	double time = (julianDate -2451545.0)/36525.0 ; //julian centuries from J2000
	
	//double[] nutationVal = nutate(julianDate);
	//double eps0 = (23+26.0/60.+21.448/3600.)*3600 - 46.8150*time - 0.00059*time*time + 0.001813*time*time*time;
        //double eps = toRadians((eps0 + nutationVal[1])/3600.); // true obliquity of the ecliptic 
	
	//double[] equatorialVal = calcEquatorialCoordinates(julianDate);
	
	// earth's orbital eccentricity
	double earthEccentricity = 0.016708634 - 0.000042037*time - 0.0000001267*time*time;
	
	// longitude of perihelion, in degrees 
	double perihelion = 102.93735 + 1.71946*time + 0.00046*time*time;
	
	double cd = cos(toRadians(declination));
	double sd = sin(toRadians(declination));
	double ce = cos(eps);
	double te = tan(eps);
	double cp = cos(toRadians(perihelion));
	double sp = sin(toRadians(perihelion));
	double cs = cos(toRadians(eclipticLongitude));
	double ss = sin(toRadians(eclipticLongitude));
	double ca = cos(toRadians(rightAscension));
	double sa = sin(toRadians(rightAscension));
	
	double term1 = (ca*cs*ce+sa*ss)/cd;
	double term2 = (ca*cp*ce+sa*sp)/cd;
	double term3 = (cs*ce*(te*cd-sa*sd)+ca*sd*ss);
	double term4 = (cp*ce*(te*cd-sa*sd)+ca*sd*sp);
	
	// constant of aberration, in arcseconds
	double k = 20.49552;
	double ra = -k * term1 + earthEccentricity*k * term2;
	double dec = -k * term3 + earthEccentricity*k * term4;
	return new double[]{ ra, dec };
    }
    
    public static double[] nutate(double julianDate){
	
	// julian centuries from 1900.0
	double time = (julianDate - 2451545.0)/36525.0;
	
	// elongation of the moon
	double moonElongation = 297.85036 + 445267.111480*time  -0.0019142*time*time + 1.0/189474*time*time*time;
	moonElongation = mod(toRadians(moonElongation), 2*PI);
	
	// sun anomaly
	double sunAnomaly = 357.52772 + 35999.050340*time -0.0001603*time*time -1./300000.0*time*time*time;
	sunAnomaly = mod(toRadians(sunAnomaly), 2*PI);
	
	// moon anomaly
	double moonAnomaly = 134.96298 + 477198.867398*time + 0.0086972*time*time + 1.0/56250.0*time*time*time;
	moonAnomaly = mod(toRadians(moonAnomaly), 2*PI);
	
	// moon's argument of latitude
	double moonArgument = 93.27191 + 483202.017538*time -0.0036825*time*time -1.0/327270.0*time*time*time;
	moonArgument = mod(toRadians(moonArgument), 2*PI);
	
	// longitude of the ascending node of the Moon's mean orbit on the ecliptic,
	// measured from the mean equinox of the date
	double moonOmega = 125.04452 -1934.136261*time + 0.0020708*time*time + 1.0/450000.0*time*time*time;
	moonOmega = mod(toRadians(moonOmega), 2*PI);
	
	
	int[] d_lng = { 0,-2, 0, 0,0, 0,-2, 0, 0,-2,
		       -2,-2, 0, 2,0, 2, 0, 0,-2, 0,
			2, 0, 0,-2,0,-2, 0, 0, 2,-2,
			0,-2, 0, 0,2, 2, 0,-2, 0, 2,
			2,-2,-2, 2,2, 0,-2,-2, 0,-2,
		       -2, 0,-1,-2,1, 0, 0,-1, 0, 0, 
			2, 0, 2 };
	
	int[] m_lng = { 0,0, 0,0,1, 0, 1, 0, 0,-1,
			0,0, 0,0,0, 0, 0, 0, 0, 0,
			0,0, 0,0,0, 0, 0, 2, 0, 2,
			1,0,-1,0,0, 0, 1, 1,-1, 0, 
			0,0, 0,0,0,-1,-1, 0, 0, 0,
			1,0, 0,1,0, 0, 0,-1, 1,-1,
		       -1,0,-1 };
	
	int[] mp_lng = {0, 0, 0, 0, 0, 1, 0,0, 1, 0,
			1, 0,-1, 0, 1,-1,-1,1, 2,-2,
			0, 2, 2, 1, 0, 0,-1,0,-1, 0,
			0, 1, 0, 2,-1, 1, 0,1, 0, 0,
			1, 2, 1,-2, 0, 1, 0,0, 2, 2,
			0, 1, 1, 0, 0, 1,-2,1, 1, 1,
		       -1, 3, 0 };
	
	int[] f_lng = {0, 2,2, 0,0,0,2,2,2,2,
		       0, 2,2, 0,0,2,0,2,0,2,
		       2, 2,0, 2,2,2,2,0,0,2,
		       0, 0,0,-2,2,2,2,0,2,2,
		       0, 2,2, 0,0,0,2,0,2,0,
		       2,-2,0, 0,0,2,2,0,0,2,
		       2,2,2};
	
	int[] om_lng = {1,2,2,2,0,0,2,1,2,2,
			0,1,2,0,1,2,1,1,0,1,
			2,2,0,2,0,0,1,0,1,2,
			1,1,1,0,1,2,2,0,2,1,
			0,2,1,1,1,0,1,1,1,1,
			1,0,0,0,0,0,2,0,0,2,
			2,2,2};
	
	int[] sin_lng = {-171996, -13187, -2274, 2062, 1426, 712, -517, -386, -301, 217, 
			 -158, 129, 123, 63, 63, -59, -58, -51, 48, 46,
			 -38, -31, 29, 29, 26, -22, 21, 17, 16, -16,
			 -15, -13, -12, 11, -10, -8, 7, -7, -7, -7,
			 6, 6, 6, -6, -6, 5, -5, -5, -5, 4,
			 4, 4,-4,-4,-4, 3,-3,-3,-3,-3,
			 -3,-3,-3 };
	
	double[] sdelt = {-174.2, -1.6, -0.2, 0.2, -3.4, 0.1, 1.2, -0.4, 0, -0.5,
			  0, 0.1, 0, 0, 0.1, 0,-0.1, 0, 0, 0,
			  0, 0, 0, 0, 0, 0, 0, -0.1, 0, 0.1,
			  0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			  0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			  0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			  0, 0, 0 };
	
	int[] cos_lng = { 92025, 5736, 977, -895, 54, -7, 224, 200, 129, -95,
			  0, -70, -53, 0, -33, 26, 32, 27, 0, -24,
			  16, 13, 0, -12, 0, 0, -10, 0, -8, 7,
			  9, 7, 6, 0, 5, 3, -3, 0, 3, 3,
			  0, -3, -3, 3, 3, 0, 3, 3, 3, 0,
			  0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			  0, 0, 0 };
	
	double[] cdelt = {8.9, -3.1, -0.5, 0.5, -0.1, 0.0, -0.6, 0.0, -0.1, 0.3,
			  0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,
			  0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,
			  0,0,0,0,0,0,0,0,0,0, 0,0,0 };
	
	// sum the periodic terms 
	double nut_long = 0, nut_obliq = 0;
	for(int i=0; i<d_lng.length; i++){
	    double arg = d_lng[i]*moonElongation + m_lng[i]*sunAnomaly +
		mp_lng[i]*moonAnomaly + f_lng[i]*moonArgument +om_lng[i]*moonOmega;
	    nut_long += 0.0001*(sdelt[i]*time + sin_lng[i])*sin(arg);
	    nut_obliq += 0.0001*(cdelt[i]*time + cos_lng[i])*cos(arg);
	}
	
	return new double[]{ nut_long, nut_obliq };
    }
    
    /** converting calender dates into julian dates.
	@param hour and fraction of hour (including min, sec, ...
    */
    public static double julianDate(int year, int month, int day, double hour){
	int leap = (month-14)/12;
	double julian = day - 32075l + 1461*(year+4800+leap)/4 + 
	    367l*(month - 2 -leap*12)/12 - 3*((year+4900+leap)/100)/4;
	julian += (double)hour/24.0 - 0.5;
	return julian;	    
    }
    
    public static double[] conutate(double julianDate, double rightAscension, double declination){
	
	double time = (julianDate -2451545.0)/36525.0; //julian centuries from J2000
	
	// obliquity of ecliptic
	double[] nutationVal = nutate(julianDate);
	//double d_psi = ret[0]; // nutation longitude
	//double d_eps = ret[1]; // nutation obliquity
	
	double eps = toRadians((23.4392911*3600.0 - 46.8150*time -
				0.00059*time*time + 0.001813*time*time*time +
				nutationVal[1])/3600.); 
	
	double ce = cos(eps);
	double se = sin(eps);
	
	// convert ra-dec to equatorial rectangular coordinates
	double x = cos(toRadians(rightAscension)) * cos(toRadians(declination));
	double y = sin(toRadians(rightAscension)) * cos(toRadians(declination));
	double z = sin(toRadians(declination));
	
	// apply corrections to each rectangular coordinate
	double x2 = x - (y*ce + z*se)*toRadians(nutationVal[0]/3600.0);
	double y2 = y + (x*ce*toRadians(nutationVal[0]/3600.0) - z*toRadians(nutationVal[1]/3600));
	double z2 = z + (x*se*toRadians(nutationVal[0]/3600.0) + y*toRadians(nutationVal[1]/3600));
	
	//convert to equatorial spherical coordinates
	double r = sqrt(x2*x2 + y2*y2 + z2*z2);
	double xyproj = sqrt(x2*x2 + y2*y2);
	
	double ra = 0;
	double dec = 0;
	if(xyproj > 0){
	    ra = atan2(y2,x2);
	    dec = asin(z2/r);
	}
	else if(z != 0){
	    ra = 0;
	    dec = asin(z2/r);
	}
	
	// in arcsecond
	ra = (mod(toDegrees(ra), 360) - rightAscension)*3600.;
	dec = (toDegrees(dec) - declination)*3600.;
	
	return new double[]{ ra, dec, eps, nutationVal[0], nutationVal[1] };
    }
    
    
    public static double[] precess(double rightAscension, double declination,
				   double equinox1, double equinox2, boolean fk4){
	rightAscension = toRadians(rightAscension);
	declination = toRadians(declination);
			
	double a = cos(declination);
	double[] x = { a*cos(rightAscension), a*sin(rightAscension), sin(declination) };
	double[][] r = precessMatrix(equinox1, equinox2, fk4);
	double[] x2 = new double[3];
	x2[0] = r[0][0]*x[0] + r[1][0]*x[1] + r[2][0]*x[2];
	x2[1] = r[0][1]*x[0] + r[1][1]*x[1] + r[2][1]*x[2];
	x2[2] = r[0][2]*x[0] + r[1][2]*x[1] + r[2][2]*x[2];
	
	rightAscension = mod(toDegrees(atan2(x2[1], x2[0])), 360);
	declination = mod(toDegrees(asin(x2[2])), 360);
	return new double[]{ rightAscension, declination };
    }
    
    public static double[][] precessMatrix(double equinox1, double equinox2, boolean fk4){
	double t = 0.001*(equinox2 - equinox1);
	double a=0,b=0,c=0;
	if(!fk4){
	    double st = 0.001*(equinox1 - 2000);
	    // 3 rotation angles
	    a = toRadians(t * (23062.181 + st*(139.656 +0.0139*st) + t*(30.188 - 0.344*st+17.998*t))/3600);
	    b = toRadians(t * t * (79.280 + 0.410*st + 0.205*t)/3600) + a;
	    c = toRadians(t * (20043.109 - st*(85.33 + 0.217*st) + t*(-42.665 - 0.217*st -41.833*t))/3600);
	}
	else{
	    double st = 0.001*( equinox1 - 1900);
	    // 3 rotation angles
	    a = toRadians( t * (23042.53 + st*(139.75 +0.06*st) + t*(30.23 - 0.27*st+18.0*t))/3600);
	    b = toRadians(t *t * (79.27 + 0.66*st + 0.32*t)/3600) + a;
	    c = toRadians(t * (20046.85 - st*(85.33 + 0.37*st) + t*(-42.67 - 0.37*st -41.8*t))/3600);
	}
	
	double sina = sin(a);
	double sinb = sin(b);
	double sinc = sin(c);
	double cosa = cos(a);
	double cosb = cos(b);
	double cosc = cos(c);
	
	double[][] r = new double[3][3];
	r[0][0] = cosa*cosb*cosc-sina*sinb;
	r[0][1] = sina*cosb+cosa*sinb*cosc;
	r[0][2] = cosa*sinc;
	r[1][0] = -cosa*sinb-sina*cosb*cosc;
	r[1][1] = cosa*cosb-sina*sinb*cosc;
	r[1][2] = -sina*sinc;
	r[2][0] = -cosb*sinc;
	r[2][1] = -sinb*sinc;
	r[2][2] = cosc;
	return r;
    }
    
    
    public static String degStr(double deg){
	int d = (int)deg;
	double xm = (deg-d)*60;
	int min = (int)xm;
	double sec = (xm-min)*60;
	return String.valueOf(d)+"d"+String.valueOf(min)+"'"+String.valueOf(sec)+"\"";
    }
    
    public static String hourStr(double deg){
	int h = (int)deg/15;
	double xm = (deg/15-h)*60;
	int min = (int)xm;
	double sec = (xm-min)*60;
	return String.valueOf(h)+"h"+String.valueOf(min)+"'"+String.valueOf(sec)+"\"";
    }
    
    public static String adstring(double ra){
	IG.p("ra = "+ra); //
	//ra = ra/360*24; // % 24;
	
	//if(ra < 0) ra += 24;
	int h = (int)ra;
	IG.p("h = "+h); //
	double xm = (ra*60 - h*60);
	int m = (int)xm;
	double s = (xm-m)*60;
	return String.valueOf(h)+":"+String.valueOf(m)+"'"+String.valueOf(s)+"\"";
    }
    
    
    
    /** cyclic modulus operation including negative range */
    public static double mod(double x, double mod){ x = x%mod; if(x<0){ x+=mod; } return x; }
    
    public static void main(String[] args){
	
	
	DateRange range = new DateRange(2001, 2, 20, 2000, 3, 3);
	IOut.p(range);
	IOut.p(range.dayCount() + "days"); //
	
	
	
	//String[] ids = java.util.TimeZone.getAvailableIDs();
	//for(String s:ids) IG.p(s);
	//if(true)return;
	
	
	/*
	double[] retval = ISun.pos(2451545.0);
	
	IG.p(retval[0]);
	IG.p(retval[1]);
	IG.p(retval[2]);
	IG.p(retval[3]);
	*/

	/*
	IG.p("-30%360 = "+(-30%360));
	IG.p("362.5%360.00 = "+(362.5%360.00));
	IG.p("-362.5%360.00 = "+( (-362.5)%360.00));
	
	//IG.p("5.5%2.5 = "+5.5%2.5);
	IG.p("5.5%2.5 = "+mod(5.5,2.5) );
	IG.p("-5.5%2.5 = "+mod(-5.5,2.5) );
	
	//IG.p("(10-5.5)%2.5 = "+(10-5.5)%2.5);
	*/
	
	/*
	double[] ret = nutate(julianDates(1987,4,10,0));
	IG.p("nut_long = "+ret[0]);
	IG.p("nut_obliq = "+ret[1]);
	*/

	/*
	double[] ret = conutate(julianDates(2028,11,13,24*0.19),
				(2+46.0/60+11.331/3600)*15,
				49 + 20.0/60 + 54.54/3600);
	IG.p("d_ra = "+ret[0]);
	IG.p("d_dec = "+ret[1]);
	*/
	
	/*
	double[] ret = coaberration(julianDates(2028,11,13,24*0.19),
				    (2+46.0/60+11.331/3600)*15,
				    49 + 20.0/60 + 54.54/3600);
	IG.p("d_ra = "+ret[0]);
	IG.p("d_dec = "+ret[1]);
	*/
	/*
	
	double[] ret = sunpos(julianDates(2002, 4, 21, 18.));
	double[] ret2 = hadec2altaz(0, ret[1], 43.078333, false);
	IG.p("alt = "+ret2[0]);
	IG.p("az = "+ret2[1]);
	*/
	
	//IG.p("corefract = "+corefract(0.5, 0)); //
	/*
	double[] ret = precess( (2+31./60+46.3/3600)*15, 89+15./60+50.6/3600, 2000, 1985, false, false);
	IG.p("pcesess ra = "+hourStr(ret[0]) + ", dec = "+degStr(ret[1])); //
	*/
	
	/*
	double[] ret = eq2hor((6+40./60.+58.2/3600.)*15.,
			       9+53./60+44./3600,
			       2460107.250,
			       50+31./60+36./3600,
			      6+51./60+18./3600,
			      false, //true,
			      369.0,
			      false,
			      true, true, true, true);
	IG.p("az = " + hourStr(ret[1]));
	IG.p("alt = "+degStr(ret[0]));
	
	//IG.p("17 42 25.6 = "+ (17+42./60+25.6/3600)*15); //
	//IG.p("16 25 10.3 ="+ (16+25./60+10.3/3600)*15); //
	*/

	/*
	double[] ret;
	IG.p("2015/3/20");
	for(int h=0; h<24; h++){
	    if(h==8||h==12||h==16){
		ret = sunangle( 45+28./60 , 9+12.0/60, 0,
				2015, 3, 20, h,
				+1,
				false);
		IG.p("h = "+h+":00, azimuth = "+ret[1]+", altitude = "+ret[0]);
	    }
	}
	
	IG.p("2015/6/21");
	for(int h=0; h<24; h++){
	    if(h==8||h==12||h==16){
		ret = sunangle( 45+28./60 , 9+12.0/60, 0,
				2015, 6, 21, h,
				+1,
				true);
		IG.p("h = "+h+":00, azimuth = "+ret[1]+", altitude = "+ret[0]);
	    }
	}
	
	IG.p("2015/9/23");
	for(int h=0; h<24; h++){
	    if(h==8||h==12||h==16){
		ret = sunangle( 45+28./60 , 9+12.0/60, 0,
				2015, 9, 23, h,
				+1,
				true);
		IG.p("h = "+h+":00, azimuth = "+ret[1]+", altitude = "+ret[0]);
	    }
	}
	
	IG.p("2015/12/22p");
	for(int h=0; h<24; h++){
	    if(h==8||h==12||h==16){
		ret = sunangle( 45+28./60 , 9+12.0/60, 0,
				2015, 12, 22, h,
				+1,
				false);
		IG.p("h = "+h+":00, azimuth = "+ret[1]+", altitude = "+ret[0]);
	    }
	}
	*/

	/*
	double latitude = 36.1658; //4.3659;
	double longitude = -86.7844; // 18.5623;
	int year = 2012;
	int month = 4;
	int day = 2;
	int hour = 12;
	int min = 0;
	double timeZone = -6; //1;
	boolean daylightSaving = false; //true;


	double[] ret = calcAngle(latitude, longitude, 0, timeZone, 
				 year, month, day, hour+(double)min/60,
				 daylightSaving);
	
	IG.p(year+"/"+month+"/"+day+" "+hour+":"+min+", azimuth = "+ret[1]+", altitude = "+ret[0]);
	*/
	/*
	//double[] ret =  calcEquatorialCoordinates(julianDate(1982,5,1,0));
	double[] ret =  calcEquatorialCoordinates(2445090.5 );
	IG.p("ra = "+hourStr(ret[0]) + ",  dec = "+adstring(ret[1]));
	*/
	
	refractionCorrection = true;
	double julianDate = 2460107.25;
	double[] equatorialVal = calcEquatorialCoordinates(julianDate);
	/*
	double[] ret =calcHorizontalCoordinates((6+40./60+58.2/3600)*15,
						9+53.0/60+44./3600,
						julianDate,
						50+31./60+36./3600,
						6+51./60+18./3600,
						369,
						equatorialVal[2]);
	*/
	double[] ret = calcHorizontalCoordinates((6+40./60.+58.2/3600.)*15.,
						 9+53./60+44./3600,
						 2460107.250,
						 50+31./60+36./3600,
						 6+51./60+18./3600,
						 369.0,
						 equatorialVal[2]);
	
	IG.p("az = " + hourStr(ret[1]));
	IG.p("alt = "+degStr(ret[0]));
	
	//IG.p("17 42 25.6 = "+ (17+42./60+25.6/3600)*15); //
	//IG.p("16 25 10.3 ="+ (16+25./60+10.3/3600)*15); //
	
    }
    
    
    
    /** convert count of days from Jan 1st to month and date. day count 1 is Jan 1st.
	@return array of int; first is month, second is date */
    /*
    public static int[] dayCountToMonthAndDate(int dayCount, int year){
	int[] days = new int[]{ 31, isLeapYear(year)?29:28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	if(dayCount>0){
	    for(int m=1; m<=12; m++){
		if(dayCount <= days[m-1]) return new int[]{ m, dayCount };
		dayCount -= days[m-1];
	    }
	}
	IOut.err("dayCount is invalid number : "+ dayCount);
	return null;
    }
    */

    public static boolean isLeapYear(int year){
	return year%4==0 && year%100!=0 || year%900==200 || year%900==600;
    }
    
    public static int endDayOfMonth(int year, int month){
	switch(month){
	case 1: return 31;
	case 2: if(isLeapYear(year)) return 29; else return 28;
	case 3: return 31;
	case 4: return 30;
	case 5: return 31;
	case 6: return 30;
	case 7: return 31;
	case 8: return 31;
	case 9: return 30;
	case 10: return 31;
	case 11: return 30;
	case 12: return 31;
	}
	IOut.err("invalid month : "+month);
	return -1; 
    }
    
    public static class DateIterator{
	public int year, month, day;
	
	public DateIterator (int y, int m, int d){ year=y; month=m; day=d; }
	
	public void next(){
	    day++;
	    if(day>28 && day>endDayOfMonth(year, month)){
		day=1; month++;
		if(month>12){ month=1; year++; }
	    }
	}
	
	public boolean past(int y, int m, int d){
	    if(year < y) return false;
	    if(year > y) return true;
	    // year == y
	    if(month < m) return false;
	    if(month > m) return true;
	    // month == m
	    if(day < d) return false;
	    if(day > d) return true;
	    // day == d
	    return false; // same date returns false.
	}
	
	public boolean past(DateRange d){ return past(d.endYear,d.endMonth,d.endDay); }
	
    }
    
    public static class DateRange{
	public int startYear, endYear, startMonth, endMonth, startDay, endDay;
	
	public DateRange(int startYear, int startMonth, int startDay,
			int endYear, int endMonth, int endDay){
	    this.startYear = startYear;
	    this.startMonth = startMonth;
	    this.startDay = startDay;
	    this.endYear = endYear;
	    this.endMonth = endMonth;
	    this.endDay = endDay;
	}
	
	public DateRange(int year, int startMonth, int startDay, int endMonth, int endDay){
	    this.startYear = year;
	    this.startMonth = startMonth;
	    this.startDay = startDay;
	    this.endYear = year;
	    this.endMonth = endMonth;
	    this.endDay = endDay;
	}
	
	public DateRange(int year, int startMonth, int endMonth){
	    this.startYear = year;
	    this.startMonth = startMonth;
	    this.startDay = 1;
	    this.endYear = year;
	    this.endMonth = endMonth;
	    this.endDay = endDayOfMonth(year, endMonth);
	}
	
	public DateRange(int year){
	    startYear = year;
	    startMonth = 1;
	    startDay = 1;
	    endYear = year;
	    endMonth = 12;
	    endDay = 31;
	}
	
	public DateIterator getIterator(){
	    return new DateIterator(startYear, startMonth, startDay);
	}
	
	public int dayCount(){
	    
	    int count=0;
	    for(DateIterator iterator=getIterator(); !iterator.past(endYear,endMonth,endDay); iterator.next(), count++);
	    return count;
	    
	    
	    /*
	    if(startYear < endYear) return -1;
	    if(startYear==endYear){
		if(startMonth < endMonth) return -1;
		if(startMonth==endMonth){ if(startDay < endDay) return -1; }
	    }
	    int y = startYear;
	    int m = startMonth;
	    int d = startDay;
	    int count=0;
	    for(; y<=endYear; y++){
		for(; y<endYear&&m<=12 || y==endYear&&m<=endMonth; m++){
		    int endDayOfMonth = endDayOfMonth(y,m);
		    for(  ; y<endYear && d<=endDayOfMonth ||
			      y==endYear && m<endMonth && d<=endDayOfMonth ||
			      y==endYear && m==endMonth && d<=endDay && d<=endDayOfMonth; d++, count++);
		    d = 1;
		}
		m = 1;
	    }
	    return -1; //
	    */
	}
	
	public String toString(){
	    return String.valueOf(startYear)+"/"+
		String.valueOf(startMonth)+"/"+
		String.valueOf(startDay)+"-"+
		String.valueOf(endYear)+"/"+
		String.valueOf(endMonth)+"/"+
		String.valueOf(endDay);
	}
	
    }
    
    
    /*
    public static class Date{
	public int year=2000, month=1, day=1; // input time info 
	public double hour = 0; // hour includes fraction of minutes and seconds
	public boolean daylightSavingTime = false; // switch to interpret hour as hour in daylight saving day
	public Date(){}
	public Date(int year, int month, int day, double hour, boolean daylightSavingTime){
	    this.year = year;
	    this.month = month;
	    this.day = day;
	    this.hour = hour;
	    this.daylightSavingTime = daylightSavingTime;
	}
	public Date(int year, int month, int day, boolean daylightSavingTime){
	    this.year = year;
	    this.month = month;
	    this.day = day;
	    this.daylightSavingTime = daylightSavingTime;
	}
	public Date(int year, int month, int day, int hour, double minute, boolean daylightSavingTime){
	    this.year = year;
	    this.month = month;
	    this.day = day;
	    this.hour = hour + minute/60.;
	    this.daylightSavingTime = daylightSavingTime;
	}
	public Date(int year, int month, int day, int hour, int minute, double second, boolean daylightSavingTime){
	    this.year = year;
	    this.month = month;
	    this.day = day;
	    this.hour = hour + (double)minute/60. + second/3600.;
	    this.daylightSavingTime = daylightSavingTime;
	}
	public Date(int year, int month, int day, double hour){
	    this.year = year;
	    this.month = month;
	    this.day = day;
	    this.hour = hour;
	}
	public Date(int year, int month, int day){
	    this.year = year;
	    this.month = month;
	    this.day = day;
	}
	public Date(int year, int month, int day, int hour, double minute){
	    this.year = year;
	    this.month = month;
	    this.day = day;
	    this.hour = hour + minute/60.;
	}
	public Date(int year, int month, int day, int hour, int minute, double second){
	    this.year = year;
	    this.month = month;
	    this.day = day;
	    this.hour = hour + (double)minute/60. + second/3600.;
	}
	public Date(Date d){
	    year = d.year; month = d.month; day = d.day; hour = d.hour;
	    daylightSavingTime = d.daylightSavingTime;
	}
    }
    */
    
    // store altitude and azimuth of one day
    public static class SearchBuffer{
	public double[] altitudes;
	public double[] azimuths;
	//public Date date;
	int year, month, day;
	boolean daylightSavingTime;
	/** sample number per a day */
	public int sampleNumber;
	public ISun sun;
	
	//public SearchBuffer(ISun sun, Date d, int sampleNumber){ this.sun = sun; date = d; this.sampleNumber = sampleNumber; }
	//public SearchBuffer(ISun sun, Date d){ this.sun = sun; date = d; sampleNumber = ISun.bufferSampleNum; }
	public SearchBuffer(ISun sun, int year, int month, int day, boolean daylightSavingTime,
			    int sampleNumber){
	    //this(sun,new Date(year,month,day,daylightSavingTime),sampleNumber);
	    this.sun = sun; this.year = year; this.month = month; this.day = day;
	    this.daylightSavingTime = daylightSavingTime;
	    this.sampleNumber = sampleNumber;
	}
	
	public SearchBuffer(ISun sun, int year, int month, int day, boolean daylightSavingTime){
	    //this(sun,new Date(year,month,day,daylightSavingTime));
	    this(sun,year,month,day,daylightSavingTime,ISun.bufferSampleNum);
	}
	
	public double azimuth(int i){ return azimuths[i]; }
	public double altitude(int i){ return altitudes[i]; }
	
	/** search date (hour) when azimuth is specified */
	public double hourAt(double azimuth){
	    for(int i=0; i<=sampleNumber; i++){
		if(azimuths[i] == azimuth){
		    return 24.0/sampleNumber*i;
		}
		else if(i<sampleNumber &&
			(azimuths[i]<azimuths[i+1] &&
			 azimuth > azimuths[i] && azimuth /*<*/ <= azimuths[i+1] ||
			 azimuths[i]>azimuths[i+1] &&
			 (azimuth > azimuths[i] && azimuth /*<*/ <= (azimuths[i+1]+360) ||
			  azimuth > azimuths[i]-360 && azimuth /*<*/ <= azimuths[i+1]) ) ){
		    double r = (azimuth - azimuths[i])/(azimuths[i+1]-azimuths[i]);
		    return 24.0/sampleNumber*(i+r);
		}
	    }
	    IOut.err("last azimuth = "+azimuths[azimuths.length-1]);
	    IOut.err("azimuth ("+azimuth+") not found");
	    return -1; // error
	}
	
	public double hourAt(IVec azimuthDir, IVec northDir){
	    //project onto xy plane
	    double az = -azimuthDir.cp().z(0).angle(northDir.cp().z(0), IVec.zaxis);
	    az *= 180/Math.PI;
	    if(az < 0) az+=360;
	    return hourAt(az);
	}
	
	public void calc(){
	    altitudes = new double[sampleNumber+1];
	    azimuths = new double[sampleNumber+1];
	    for(int i=0; i<=sampleNumber; i++){
		double hour = 24.0/sampleNumber*i;
		//double[] angles = sun.angles(date.year, date.month, date.day, hour);
		double[] angles = sun.angles(year, month, day, hour);
		azimuths[i] = angles[0];
		altitudes[i] = angles[1];
	    }
	}
    }
    
}
