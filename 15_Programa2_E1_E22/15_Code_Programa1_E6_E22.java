/*************************************************************************************/
/* Program Name: Programa 1 */
/* Name: Daniel Alejandro Morales Castillo */
/* Date: 14/03/2022 */
/* Description: Programa 1 de PSP - revisar encabezados en Java */
/*************************************************************************************/
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;

public class ArchivosOperaciones {
    //private campos del hgeader, ProgramName, Name,Date,Description
    ArrayList<String> conjunto = new ArrayList<String>();
    String pathRute;
    private String programName,name,date,description,
    campoNombrePrograma,campoNombreProgramador,campoFecha,campoDescripcion;
    private int aux=4;
    private String[] arregloArchivos;

    public ArchivosOperaciones archivoEnArreglo(String pathRute){
        this.pathRute=pathRute;
        File file = new File(pathRute);
        ArchivosOperaciones nuevoArchivo = new ArchivosOperaciones();
        int comprobar = 0;
        try {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(pathRute)))) {
                List<String> lines = new ArrayList<String>();
                String line;
                while ((line = entrada.readLine()) != null) {
                    lines.add(line);
                }
            arregloArchivos = lines.toArray(new String[0]);
        }
        int i = 0;
        Scanner entradaS = new Scanner(System.in);
        for (String cadena : arregloArchivos) {
            if ((cadena.matches("(.*)"+campos[0]+"(.*)"))) {
                comprobar++;
                String[] match = arregloArchivos[i].split(":");
                match[0]=match[0].replace("/*","");
                match[1]=match[1].replace("*/","");
            if(!(expresionStringRegular(match[1]))){
                nuevoArchivo.setcampoNombrePrograma(inputReader(match[0]));
                comprobar++;
            }else {
                nuevoArchivo.setcampoNombrePrograma(match[1]);
            }
            nuevoArchivo.setprogramName(match[0]);
        }
        String pN=cadena;
        pN=pN.replace("/*","");
        pN=pN.replace("*/","");
        pN=pN.replace(" ","");
        if ((pN.matches(campos[1]+"(.*)"))) {
          comprobar++;
          String[] match = arregloArchivos[i].split(":");
          match[0]=match[0].replace("/*","");
          match[1]=match[1].replace("*/","");
          if(!(expresionStringRegular(match[1]))){
            nuevoArchivo.setcampoNombreProgramador(inputReader(match[0]));
            comprobar++;
          }
          else {
            nuevoArchivo.setcampoNombreProgramador(match[1]);
          }
          nuevoArchivo.setName(match[0]);
        }
        if ((cadena.matches("(.*)"+campos[2]+"(.*)"))) {
            comprobar++;
          String[] match = arregloArchivos[i].split(":");
          match[0]=match[0].replace("/*","");
          match[1]=match[1].replace("*/","");
          if(!(expresionStringRegular(match[1]))){
            nuevoArchivo.setcampoFecha(inputReader(match[0]));
              comprobar++;
          }else {
            nuevoArchivo.setcampoFecha(match[1]);
          }
          nuevoArchivo.setDate(match[0]);
        }
        if ((cadena.matches("(.*)"+campos[3]+"(.*)"))) {
            comprobar++;
          String[] match = arregloArchivos[i].split(":");
          match[0]=match[0].replace("/*","");
          match[1]=match[1].replace("*/","");
          if(!(expresionStringRegular(match[1]))){
            nuevoArchivo.setcampoDescripcion(inputReader(match[0]));
            comprobar++;
          }else {
            nuevoArchivo.setcampoDescripcion(match[1]);
          }
          nuevoArchivo.setDescription(match[0]);
        }
        if(i>=7){
          break;
        }
        i++;
      }
    } catch(Exception e) {
      System.out.println(e);
    }
    if(comprobar==0){
        System.out.println("NO HAY ENCABEZADO");
    }
    ArchivosOperaciones archivosOpera = new ArchivosOperaciones(nuevoArchivo.getprogramName(),nuevoArchivo.getName(),nuevoArchivo.getDate(),nuevoArchivo.getDescription(),
    nuevoArchivo.getcampoNombrePrograma(),nuevoArchivo.getcampoNombreProgramador(),nuevoArchivo.getcampoFecha(),nuevoArchivo.getcampoDescripcion());
    if(!(archivosOpera.getaux()==4)||(comprobar>0)){
      try {
        escribeArreglo(archivosOpera);
        writeFile();
      } catch(Exception e) {
        System.out.println(e);
      }
    }
      return archivosOpera;
  }

  public void writeFile()throws IOException {
    pathRute=pathRute.replace(".java","");
      BufferedWriter wr = new BufferedWriter(new FileWriter(pathRute+"_rev.java")); //nuevo archivo corregido
  for (String cadenaarchivo : conjunto ) {
    cadenaarchivo=cadenaarchivo.replace(pathRute,pathRute+"_rev");
    wr.write(cadenaarchivo);
    wr.newLine();
  }
  wr.close();
}

    private String[] campos={
        "Program Name:",
        "Name:",
        "Date:",
        "Description:"
    };

    public ArchivosOperaciones(String programName, String name, String date, String description, String campoNombrePrograma, String campoNombreProgramador, String campoFecha, String campoDescripcion) {
        this.programName = programName;
        this.name = name;
        this.date=date;
        this.description=description;
        this.campoNombrePrograma=campoNombrePrograma;
        this.campoNombreProgramador=campoNombreProgramador;
        this.campoFecha=campoFecha;
        this.campoDescripcion=campoDescripcion;
        if(programName==null){
            this.programName = campos[0];
            this.campoNombrePrograma=inputReader(campos[0]);
            aux=aux-1;
        }
        this.name = name;
        if(name==null){
            this.name = campos[1];
            this.campoNombreProgramador=inputReader(campos[1]);
            aux=aux-1;
        }
        this.date = date;
        if(date==null){
            this.date = campos[2];
            this.campoFecha=inputReader(campos[2]);
            aux=aux-1;
        }
        this.description = description;
        if(description==null){
            this.description = campos[3];
            this.campoDescripcion=inputReader(campos[3]);
            aux=aux-1;
        }
	}
    public ArchivosOperaciones() {
	}
    public String getprogramName() {
        return programName;
    }
    public void setprogramName(String programName) {
        this.programName = programName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getcampoNombrePrograma() {
        return campoNombrePrograma;
    }
    public void setcampoNombrePrograma(String campoNombrePrograma) {
        this.campoNombrePrograma = campoNombrePrograma;
    }
    public String getcampoNombreProgramador() {
        return campoNombreProgramador;
    }
    public void setcampoNombreProgramador(String campoNombreProgramador) {
        this.campoNombreProgramador = campoNombreProgramador;
    }
    public String getcampoFecha() {
        return campoFecha;
    }
    public void setcampoFecha(String campoFecha) {
        this.campoFecha = campoFecha;
    }
    public String getcampoDescripcion() {
        return campoDescripcion;
    }
    public void setcampoDescripcion(String campoDescripcion) {
        this.campoDescripcion = campoDescripcion;
    }
    public int getaux(){
        return aux;
    }
    public boolean expresionStringRegular(String match){
        return match.matches(".*[a-zA-Z0-9].*");
    }
    public String inputReader(String archivo){
        System.out.println("Ingresa -> "+archivo + ":");
        Scanner sc= new Scanner(System.in);
        String in= sc.nextLine();
        String salida = expresionStringRegular(in) ? in : inputReader(archivo);
    return salida;
    }
    //header sobreescritura
    public void escribeArreglo(ArchivosOperaciones ao){
        int aux=0;
        conjunto.add("/*****************************************************/");
        conjunto.add("/* "+ao.getprogramName()+": "+ao.getcampoNombrePrograma()+" */");
        conjunto.add("/* "+ao.getName()+": "+ao.getcampoNombreProgramador()+" */");
        conjunto.add("/* "+ao.getDate()+": "+ao.getcampoFecha()+" */");
        conjunto.add("/* "+ao.getDescription()+": "+ao.getcampoDescripcion()+" */");
        conjunto.add("/*******************************************************/");
        for (String cadena : arregloArchivos) {
            if(aux>=7){
        conjunto.add(cadena);
        }
        aux++;
        }
    }
  public static void main(String[] args) throws IOException {
    ArchivosOperaciones archivo = new ArchivosOperaciones();
    System.out.println("Ingrese la ruta del archivo, ejemplo: E:\\src\\carpeta\\psp\\programa1.java:");
    Scanner scanner= new Scanner(System.in);
    String entrada= scanner.nextLine();
    archivo=archivo.archivoEnArreglo(entrada);
    if(archivo.getaux()==4){
      System.out.println("Encabezado Completo -> Revisar en caso de haber completado encabezado");
      
    }
  }
}
