/* @(#)PrintSupportUpgSE.java
 *
 * Project: F49EsrServiceWeb
 * s30tobo je napisao ovu klasu, ako je bugovita, zovite na 5349.
 * Copyright (c) APIS IT d.o.o. Paljetkova 18 Zagreb, Hrvatska
 */
package com.solo761.cartcreator.business.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * Klasa PrintSupportUpgSE.java je klasa koja ispisuje sva polja do kojih može doći iz ulaznog objekta.
 *
 * $Id: PrintSupport.java,v 1.1 2017/02/17 08:54:43 s30mago Exp $
 */
@SuppressWarnings( "all" )
public final class PrintSupport {

    /** klase primitivnih tipova. */
    private static Class[] c_primitivniTipovi =
        { boolean.class, byte.class, char.class, short.class, int.class, long.class, float.class, double.class,
        Boolean.class, Byte.class, Character.class, Short.class, Integer.class, Long.class, Float.class, Double.class,
        char[].class, String.class, BigDecimal.class, Date.class, Calendar.class };
    /** klase primitivnih tipova. */
    private static Class[] c_primitivnaPolja =
        { boolean[].class, byte[].class, short[].class, int[].class, long[].class, float[].class, double[].class };
    /** brojac za uvlake.*/
    private static int c_tab = 0;
    /** broj razina do koje želimo brojeve.*/
    private static int c_brojrazina = 15;
    /** brojac za prvih n razina.*/
    private static int[] c_razina = new int[ c_brojrazina ];
    /** za ispis je potreban StringBuffer.*/
    private static StringBuffer c_sb;

    /** da se ne buni za previse puta isti string.*/
    private static final String RAZMAK = " ";
    /** da se ne buni za previse puta isti string.*/
    private static final String JEDNAKO = " = ";
    /** da se ne buni za previse puta isti string.*/
    private static final String NOV = "\n";
    /** da se ne buni za previse puta isti string.*/
    private static final String ISV = " ima vrijednosti: ";

    /**privatni konstruktor.*/
    private PrintSupport() {
    }

    /**
     * metoda koja vraca String.
     * @param p_obj ulazni objekt
     * @return String
     */
    public static String ispis( final Object p_obj ) {
        ponistiBrojanje();
        if( p_obj == null ) {
            getSb().append( "Objekt je NULL!" );
            return getSb().toString();
        }
        getSb().append( dubina( c_tab ) + "Ispis za " + p_obj.getClass().getSimpleName() + NOV );
        ++c_tab;
        for( Object ob : prebaciUArray( p_obj ) ) {
            ispisPom( null, ob );
        }
        return getSb().toString();
    }

    /**
     * glavna metoda koja preusmjerava ovisno o tipu objekta koje prima.
     * @param p_f field
     * @param p_obj objekt
     * @return potvrda
     */
    private static boolean ispisPom( final Field p_f, final Object p_obj ) {
        Class pomocna = null;
        if( p_f != null ) {
            pomocna = p_f.getType();
        }
        if( p_obj == null ) {
            String glavni = "Objekt = NULL!";
            if( pomocna != null ) {
                glavni = p_f.getType().getSimpleName() + RAZMAK + p_f.getName() + " = NULL!";
            }
            getSb().append( dubina( c_tab ) + glavni + NOV );
            return true;
        }
        if( jeliPrimitivna( pomocna, p_obj.getClass() ) ) {
            ispisPrim( p_f, p_obj );
            return true;
        }
        if( jeliPrimitivnoPolje( pomocna, p_obj.getClass() ) ) {
            ispisPrimPolja( p_f, p_obj );
            return true;
        }
        if( jeliPolje( pomocna, p_obj.getClass() ) ) {
            ispisPolja( p_f, p_obj );
            return true;
        }
        ispisCustomKlasa( p_f, p_obj );
        return false;
    }

    /**
     * metoda koja ispisuje polja.
     * @param p_f field
     * @param p_obj objekt
     * @return potvrda
     */
    private static boolean ispisPolja( final Field p_f, final Object p_obj ) {
        Class ulazna = p_obj.getClass();
        Object objekt = p_obj;
        if( p_f != null ) {
            ulazna = p_f.getType();
            getSb().append( dubina( c_tab ) + ulazna.getSimpleName() + RAZMAK + p_f.getName() + ISV + NOV );
            try {
                boolean promjena = false;
                if( !p_f.isAccessible() ) {
                    p_f.setAccessible( true );
                    promjena = true;
                }
                objekt = p_f.get( p_obj );
                if( promjena ) {
                    p_f.setAccessible( false );
                }
            }
            catch ( Exception e ) {
                getSb().append( "IspisPolja Greška: " + e.getMessage() );
            }
        }
        else {
            getSb().append( dubina( c_tab ) + p_obj.getClass().getSimpleName() + ISV + NOV );
        }
        Object[] polje = prebaciUArray( objekt );
        ++c_tab;
        for( Object ob : polje ) {
            ispisPom( null, ob );
        }
        --c_tab;
        return true;
    }

    /**
     * metoda za ispis ostalih klasa.
     * @param p_f field
     * @param p_obj objekt
     * @return potvrda
     */
    private static boolean ispisCustomKlasa( final Field p_f, final Object p_obj ) {
        Field[] fieldovi = null;
        if( p_f != null ) {
            fieldovi = p_f.getType().getDeclaredFields();
            if( p_f.getType().getSuperclass() != null ) {
                Field[] fieldoviSuper = p_f.getType().getSuperclass().getDeclaredFields();
                List < Field > fieldKomplet = new ArrayList < Field >();
                for( Field f : fieldoviSuper ) {
                    fieldKomplet.add( f );
                }
                for( Field f : fieldovi ) {
                    fieldKomplet.add( f );
                }
                fieldovi = new Field[fieldKomplet.size()];
                for( int i = 0; i < fieldKomplet.size(); ++i ) {
                    fieldovi[i] = fieldKomplet.get( i );
                }
            }
            getSb().append( dubina( c_tab ) + p_f.getType().getSimpleName() + RAZMAK + p_f.getName()
                + ISV + NOV );
        }
        else {
            getSb().append( dubina( c_tab ) + p_obj.getClass().getSimpleName() + ISV + NOV );
            fieldovi = p_obj.getClass().getDeclaredFields();
            if( p_obj.getClass().getSuperclass() != null ) {
                Field[] fieldoviSuper = p_obj.getClass().getSuperclass().getDeclaredFields();
                List < Field > fieldKomplet = new ArrayList < Field >();
                for( Field f : fieldoviSuper ) {
                    fieldKomplet.add( f );
                }
                for( Field f : fieldovi ) {
                    fieldKomplet.add( f );
                }
                fieldovi = new Field[fieldKomplet.size()];
                for( int i = 0; i < fieldKomplet.size(); ++i ) {
                    fieldovi[i] = fieldKomplet.get( i );
                }
            }
        }
        ++c_tab;
        for( Field f : fieldovi ) {
            if( Modifier.isStatic( f.getModifiers() ) ) {
                //System.out.println( f.getName() + " je static." );
                continue;
            }
            try {
                boolean promjena = false;
                if( !f.isAccessible() ) {
                    f.setAccessible( true );
                    promjena = true;
                }
                Object pomocni = f.get( p_obj );
                if( jeliPolje( f.getType(), p_obj.getClass() ) ) {
                    if( pomocni != null ) {
                        Object[] jelPrazno = prebaciUArray( pomocni );
                        if( jelPrazno.length < 1 ) {
                            pomocni = null;
                        }
                    }
                }
                if( pomocni == null ) {
                    getSb().append( dubina( c_tab ) + f.getType().getSimpleName() + RAZMAK + f.getName()
                        + " = NULL!" + NOV );
                    continue;
                }
                if( jeliPrimitivna( f.getType(), p_obj.getClass() )
                    || jeliPrimitivnoPolje( f.getType(), p_obj.getClass() )
                    || jeliPolje( f.getType(), p_obj.getClass() ) ) {
                    ispisPom( f, p_obj );
                    continue;
                }
                try {
                    Object novaInstanca = Class.forName( f.getType().getName() ).newInstance();
                    novaInstanca = f.get( p_obj );
                    ispisPom( f, novaInstanca );
                }
                catch( InstantiationException ie ) {
                    //System.out.println("InstanTationException  ispisNePrim -> ispisPrim: ");
                    getSb().append( dubina( c_tab ) + f.getType().getSimpleName() + RAZMAK + f.getName() + " = "
                        + f.get( p_obj ) + NOV );
                }
                if( promjena ) {
                    f.setAccessible( false );
                }
            }
            catch ( Exception e ) {
                 getSb().append( "IspisNePrim Greška: " + f.getName() + " " + e.getMessage() + NOV );
                 e.printStackTrace();
            }
        }
        --c_tab;
        return true;
    }

    /**
     * metoda za ispis primitivnih polja.
     * @param p_f field
     * @param p_obj objekt
     * @return potvrda
     */
    private static boolean ispisPrimPolja( final Field p_f, final Object p_obj ) {
        Class ulazna = p_obj.getClass();
        Object objekt = p_obj;
        if( p_f != null ) {
            ulazna = p_f.getType();
            boolean promjena = false;
            if( !p_f.isAccessible() ) {
                p_f.setAccessible( true );
                promjena = true;
            }
            try {
                objekt = p_f.get( p_obj );
                getSb().append( dubina( c_tab ) + p_f.getType().getSimpleName() + RAZMAK + p_f.getName() + ISV + NOV );
            }
            catch( Exception exc ) {
                getSb().append( "Exception u ispisPrimPolja: " + exc.getMessage() );
                return false;
            }
            if( promjena ) {
                p_f.setAccessible( false );
            }
        }
        else {
            getSb().append( dubina( c_tab ) + p_obj.getClass().getSimpleName() + ISV + NOV );
        }
        ++c_tab;
        if( boolean[].class == ulazna ) {
            for( boolean b : ( boolean[] )objekt ) {
                ispisPrim( null, b );
            }
        }
        if( byte[].class == ulazna ) {
            for( byte b : ( byte[] )objekt ) {
                ispisPrim( null, b );
            }
        }
        if( short[].class == ulazna ) {
            for( short s : ( short[] )objekt ) {
                ispisPrim( null, s );
            }
        }
        if( int[].class == ulazna ) {
            for( int i : ( int[] )objekt ) {
                ispisPrim( null, i );
            }
        }
        if( long[].class == ulazna ) {
            for( long l : ( long[] )objekt ) {
                ispisPrim( null, l );
            }
        }
        if( float[].class == ulazna ) {
            for( float f : ( float[] )objekt ) {
                ispisPrim( null, f );
            }
        }
        if( double[].class == ulazna ) {
            for( double d : ( double[] )objekt ) {
                ispisPrim( null, d );
            }
        }
        --c_tab;
        return true;
    }

    /**
     * metoda za ispis primitivnih vrijednosti.
     * @param p_f field
     * @param p_obj objekt
     * @return potvrda
     */
    private static boolean ispisPrim( final Field p_f, final Object p_obj ) {
        if( p_f != null ) {
            boolean promjena = false;
            if( !p_f.isAccessible() ) {
                p_f.setAccessible( true );
                promjena = true;
            }
            try {
                String glavni = "" + p_f.get( p_obj );
                if( Calendar.class.isAssignableFrom( p_f.getType() ) ) {
                    glavni = new SimpleDateFormat( "dd.MM.yyyy." ).format( ( ( Calendar )p_f.get( p_obj ) ).getTime() );
                }
                if( Date.class.isAssignableFrom( p_f.getType() ) ) {
                    glavni = new SimpleDateFormat( "dd.MM.yyyy." ).format( p_f.get( p_obj ) );
                }
                getSb().append( dubina( c_tab ) + p_f.getType().getSimpleName() + RAZMAK + p_f.getName() + JEDNAKO
                    + glavni + NOV );
            }
            catch( Exception exc ) {
                getSb().append( "Exception u ispisPrim: " + exc.getMessage() );
                return false;
            }
            if( promjena ) {
                p_f.setAccessible( false );
            }
        }
        else {
            String glavni = "" + p_obj;
            if( Calendar.class.isAssignableFrom( p_obj.getClass() ) ) {
                glavni = new SimpleDateFormat( "dd.MM.yyyy." ).format( ( ( Calendar )p_obj ).getTime() );
            }
            if( Date.class.isAssignableFrom( p_obj.getClass() ) ) {
                glavni = new SimpleDateFormat( "dd.MM.yyyy." ).format( p_obj );
            }
            getSb().append( dubina( c_tab ) + p_obj.getClass().getSimpleName() + JEDNAKO + glavni + NOV );
        }
        return true;
    }

    /**
     * metoda provjerava jeli ulazna klasa primitivna.
     * @param p_cf ulazna klasa fielda
     * @param p_co ulazna klasa objekta
     * @return potvrda
     */
    private static boolean jeliPrimitivna( final Class p_cf, final Class p_co ) {
        Class ulazna = p_co;
        if( p_cf != null ) {
            ulazna = p_cf;
        }
        for( Class c : c_primitivniTipovi ) {
            if( ulazna == c ) {
                return true;
            }
            if( Calendar.class.isAssignableFrom( ulazna ) ) {
                return true;
            }
            if( Date.class.isAssignableFrom( ulazna ) ) {
                return true;
            }
        }
        return false;
    }
    /**
     * metoda provjerava jeli objekt primitivno polje.
     * @param p_cf field
     * @param p_co objekt
     * @return potvrda
     */
    private static boolean jeliPrimitivnoPolje( final Class p_cf, final Class p_co ) {
        Class ulazna = p_co;
        if( p_cf != null ) {
            ulazna = p_cf;
        }
        for( Class c : c_primitivnaPolja ) {
            if( ulazna == c ) {
                return true;
            }
        }
        return false;
    }

    /**
     * metoda provjerava jeli objekt polje.
     * @param p_cf field
     * @param p_co objekt
     * @return potvrda
     */
    private static boolean jeliPolje( final Class p_cf, final Class p_co ) {
        Class ulazna = p_co;
        if( p_cf != null ) {
            ulazna = p_cf;
        }
        if( ulazna.isArray() ) {
            return true;
        }
        if( Collection.class.isAssignableFrom( ulazna ) ) {
            return true;
        }
        if( Map.class.isAssignableFrom( ulazna ) ) {
            return true;
        }
        return false;
    }

    /**
     * metoda koja postavlja dubinu za ispis.
     * @param p_i razina
     * @return uvlaka
     */
    private static String dubina( final int p_i ) {
        String uvlaka = "";
        if ( p_i < 0 ) {
            throw new RuntimeException( "tab je manji od nule!!!" );
        }
        else {
            for( int i = 1; i <= p_i; ++i ) {
                uvlaka += "  ";
            }
        }
        //ova linija je ako želimo dodati brojeve
        uvlaka += dubinaSBrojevima( p_i );
        return uvlaka;
    }

    /**
     * Metoda koja umece brojeve na ispisu.
     * @param p_i razina
     * @return uvlaka sa brojevima
     */
    private static String dubinaSBrojevima( final int p_i ) {
        String brojke = "";
        if( p_i >= c_brojrazina ) {
            return brojke;
        }
        int d = p_i;
        c_razina[d] = c_razina[d] + 1;
        //ova petlja poništava podrazine
        for( int i = d + 1; i < c_brojrazina; ++i ) {
            c_razina[i] = 0;
        }
        //ova petlja ispisuje brojeve razine
        for( int i = 1; i <  d + 1; ++i ) {
            brojke += c_razina[i] + ".";
        }
        return brojke;
    }

    /**
     * metoda koja ponistava brojanje razine i dubine.
     */
    private static void ponistiBrojanje() {
        c_tab = 0;
        c_sb = null;
        for( int i = 0; i < c_brojrazina; ++i ) {
            c_razina[i] = 0;
        }
    }

    /**
     * Metoda uvijek vraca array objekata po kojem se može iterirati.
     * @param p_obj ulazni objekt
     * @return izlazni objekt - array objekata
     */
    private static Object[] prebaciUArray( final Object p_obj ) {
        Object[] araj = null;
        if( !jeliPolje( null, p_obj.getClass() ) ) {
            araj = new Object[]{ p_obj };
        }
        else {
            if( p_obj.getClass().isArray() ) {
                araj = ( Object[] )p_obj;
            }
            else {
                if( Collection.class.isAssignableFrom( p_obj.getClass() ) ) {
                    Collection col = ( Collection )p_obj;
                    araj = col.toArray();
                }
                else {
                    if( Map.class.isAssignableFrom( p_obj.getClass() ) ) {
                        Map mapa = ( Map )p_obj;
                        Map novaMapa = new HashMap();
                        Set kljucevi = mapa.keySet();
                        for( Object kljuc : kljucevi ) {
                            novaMapa.put( kljuc, "" + kljuc + "  " + mapa.get( kljuc ) );
                        }
                        Collection col = novaMapa.values();
                        araj = col.toArray();
                    }
                }
            }
        }
        return araj;
    }

    /**
     * @return the sb
     */
    private static StringBuffer getSb() {
        if( c_sb == null ) {
            c_sb = new StringBuffer( "" );
        }
        return c_sb;
    }
}
