package filtros;

import filters.Filter;
import filters.Pipe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LectorDelDirectorio extends Filter {

    String file;
    String nombreDelArchivo;
    ArrayList<String> salidas = new ArrayList<String>();

    public LectorDelDirectorio(String filtrolector, String nombre ,Pipe outputDelFiltroLector) {
        super(null,outputDelFiltroLector);
        file = filtrolector;
        nombreDelArchivo = nombre;
    }


    private void obtenerDirectorio(File[] arregloDeArchivos) throws IOException {
        for (File archivo : arregloDeArchivos) {
            boolean esCarpeta = archivo.isDirectory();
            if (esCarpeta) {
                File[] files = archivo.listFiles();
                obtenerDirectorio(files);
            }
            if (archivo.getName().contains(nombreDelArchivo)){
                salidas.add(archivo.getAbsolutePath());
            }
        }
    }

    @Override
    public void transform() {
        try {

            File folderFile = new File(file);
            if (folderFile.exists()) {
                File[] archivosEnCarpeta = folderFile.listFiles();
                obtenerDirectorio(archivosEnCarpeta);
            }
            for (String rutasEncontradas: salidas) {
                output.write(rutasEncontradas + "\n");
                System.out.println(rutasEncontradas);
            }
            output.closeWriter();
        }catch (IOException e){ e.printStackTrace(); }
    }
}
