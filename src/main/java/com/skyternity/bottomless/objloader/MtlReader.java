package com.skyternity.bottomless.objloader;

import de.javagl.obj.Mtl;
import de.javagl.obj.Mtls;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A class that may read MTL data, and return the materials as a
 * list of {@link Mtl} objects.
 */
public class MtlReader
{
    /**
     * Read the MTL data from the given stream, and return
     * it as {@link Mtl} objects.
     * The caller is responsible for closing the given stream.
     *
     * @param inputStream The stream to read from.
     * @return The list of Mtl object.
     * @throws IOException If an IO error occurs
     */
    public static List<mtlloader> read(InputStream inputStream)
            throws IOException
    {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));
        return readImpl(reader);
    }

    /**
     * Read the MTL data from the given reader, and return
     * it as {@link Mtl} objects.
     * The caller is responsible for closing the given reader.
     *
     * @param reader The reader to read from.
     * @return The list of Mtl object.
     * @throws IOException If an IO error occurs
     */
    public static List<mtlloader> read(Reader reader)
            throws IOException
    {
        if (reader instanceof BufferedReader)
        {
            return readImpl((BufferedReader)reader);
        }
        return readImpl(new BufferedReader(reader));
    }

    /**
     * Read the MTL data from the given reader, and return
     * it as {@link Mtl} objects.
     * The caller is responsible for closing the given reader.
     *
     * @param reader The reader to read from.
     * @return The list of Mtl object.
     * @throws IOException If an IO error occurs
     */
    private static List<mtlloader> readImpl(BufferedReader reader)
            throws IOException
    {
        List<mtlloader> mtlList = new ArrayList<>();

        mtlloader currentMtl = null;

        while(true)
        {
            String line = reader.readLine();
            if(line == null)
            {
                break;
            }

            line = line.trim();

            //System.out.println("read line: "+line);

            // Combine lines that have been broken
            boolean finished = false;
            while(line.endsWith("\\"))
            {
                line = line.substring(0, line.length() - 2);
                String nextLine = reader.readLine();
                if (nextLine == null)
                {
                    finished = true;
                    break;
                }
                line += " " + nextLine;
            }
            if (finished)
            {
                break;
            }

            StringTokenizer st = new StringTokenizer(line);
            if(!st.hasMoreTokens())
            {
                continue;
            }

            String identifier = st.nextToken();
            if (identifier.equalsIgnoreCase("newmtl"))
            {
                String name = line.substring("newmtl".length()).trim();
                currentMtl = new mtlloader(Mtls.create(name));
                mtlList.add(currentMtl);
            }
            else if (identifier.equalsIgnoreCase("Ka"))
            {
                float ka0 = parseFloat(st.nextToken());
                float ka1 = parseFloat(st.nextToken());
                float ka2 = parseFloat(st.nextToken());
                currentMtl.setKa(ka0, ka1, ka2);
            }
            else if (identifier.equalsIgnoreCase("Ks"))
            {
                float ks0 = parseFloat(st.nextToken());
                float ks1 = parseFloat(st.nextToken());
                float ks2 = parseFloat(st.nextToken());
                currentMtl.setKs(ks0, ks1, ks2);
            }
            else if (identifier.equalsIgnoreCase("Kd"))
            {
                float kd0 = parseFloat(st.nextToken());
                float kd1 = parseFloat(st.nextToken());
                float kd2 = parseFloat(st.nextToken());
                currentMtl.setKd(kd0, kd1, kd2);
            }
            else if (identifier.equalsIgnoreCase("map_Kd"))
            {
                String mapKd = line.substring("map_Kd".length()).trim();
                currentMtl.setMapKd(mapKd);
            }
            else if (identifier.equalsIgnoreCase("d"))
            {
                float d = parseFloat(st.nextToken());
                currentMtl.setD(d);
            }
            else if (identifier.equalsIgnoreCase("Ns"))
            {
                float ns = parseFloat(st.nextToken());
                currentMtl.setNs(ns);
            }
            else if (identifier.equalsIgnoreCase("tintindex"))
            {
                int tint = parseInt(st.nextToken());
                currentMtl.setTintIndex(tint);
            }
            else if (identifier.equalsIgnoreCase("use_diffuse"))
            {
                currentMtl.setUseDiffuseColor();
            }
        }

        return mtlList;
    }

    /**
     * Parse a float from the given string, wrapping number format
     * exceptions into an IOException
     *
     * @param s The string
     * @return The float
     * @throws IOException If the string does not contain a valid float value
     */
    private static float parseFloat(String s) throws IOException
    {
        try
        {
            return Float.parseFloat(s);
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
    }

    /**
     * Parse an int from the given string, wrapping number format
     * exceptions into an IOException
     *
     * @param s The string
     * @return The int
     * @throws IOException If the string does not contain a valid float value
     */
    private static int parseInt(String s) throws IOException
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            throw new IOException(e);
        }
    }


    /**
     * Private constructor to prevent instantiation
     */
    private MtlReader()
    {
        // Private constructor to prevent instantiation
    }
}
