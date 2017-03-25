package com.nhave.nhc.chroma;

import java.util.TreeMap;

public class ChromaRegistry
{
	public static final TreeMap<String, Chroma> CHROMAS = new TreeMap<String, Chroma>();
    
    /**
     * Registers a new Chroma to the registry.
     * 
     * @param id
     *            The String ID of the Chroma. 
     * @param chroma
     *            The Chroma to register.
     */
    public static Chroma registerChroma(String id, Chroma chroma) 
    {
    	CHROMAS.put(id, chroma);
    	return chroma;
    }
    
    /**
     * Returns a Chroma from its name.
     * 
     * @param name
     *            The name of the Chroma.
     */
    public static Chroma getChroma(String name)
    {
    	Chroma result = CHROMAS.get(name);
        return result;
    }
    
    /**
     * Returns the amount of registered Chromas.
     */
    public static int getSize()
    {
    	return CHROMAS.size();
    }
    
    /**
     * Returns true if no Chromas has been registered.
     */
    public static boolean isEmpty()
    {
    	return CHROMAS.isEmpty();
    }
}