package edu.isen.fhgd.fft.zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MyZip {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MyZip.class);


    public static void unzip(InputStream aDezipper, String vers) {
        // dezippe l'archive aDezipper dans le répertoire existant vers
        try {
            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(aDezipper));
            ZipEntry entry;

            while ((entry = zin.getNextEntry()) != null) {

                int i = entry.getName().lastIndexOf('/');
                if (i != -1) {
                // création de sous répertoires si besoin
                    File f = new File(vers + "/" + entry.getName().substring(0, i));
                    f.mkdirs();
                }
                // copie du fichier entry.getName()
                BufferedOutputStream fo = new BufferedOutputStream(new FileOutputStream(vers + "/" + entry.getName()));
                for (int c = zin.read(); c != -1; c = zin.read()) fo.write(c);
                fo.close();

            }
            zin.close();
            LOGGER.debug("Unzip Ok");
        } catch (FileNotFoundException e) {
            LOGGER.error("Fichier non trouvé : "+ e);
        } catch (IOException e) {
            LOGGER.error("Erreur unzip : "+ e);
        }
    }
}