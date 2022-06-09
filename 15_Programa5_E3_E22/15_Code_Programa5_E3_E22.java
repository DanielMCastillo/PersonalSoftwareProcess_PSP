/*************************************************************************************/
/* Program Name: Programa 5 */
/* Name: Daniel Alejandro Morales Castillo */
/* Date: 6/06/2022 */
/* Description: Programa 5 - Contar acomplamiento de clases CBO */
/*************************************************************************************/
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//	Se sigue utilizando el contar de lectura del programa pasado donde se espeicifca el final de los meotodos y de las clases pera poder leer
//correctamente las lineas y hacer un calculo correcto

public class CBO{
	public static void main(String[] args){
        System.out.print("Ingresa la ruta del archivo: ");
		Scanner s = new Scanner(System.in);
        String archivo = ContarLOC.contar(LeerArchivo.consolaLinea(s.nextLine()));
        Pattern p = Pattern.compile("class(.*?)(//FinClase[^\\[\"])([^~]*)", Pattern.DOTALL);
        Matcher m = p.matcher(archivo);
        String nombreClases = "";
		String contenidoClases = "";
		int cboGlobal = 0;
        while (m.find()) {
            nombreClases+=m.group(3)+"~";
            contenidoClases+=m.group(1).replaceAll("~","\n")+"~";
        }
        String [] arregloNombreClases = nombreClases.split("~");
        String [] arregloContenidoClases = contenidoClases.split("~");   
        
        for(int i = 0;i<arregloContenidoClases.length;i++){
			double numeroClasesExternas = 0;
			System.out.print(arregloNombreClases[i]+":");
			for(int j = 0;j<arregloNombreClases.length;j++){
				if(i!=j && arregloContenidoClases[i].contains(arregloNombreClases[j])){
					System.out.print(arregloNombreClases[j]+";");
					numeroClasesExternas++;
					cboGlobal++;
				}
			}
			System.out.print(" CBO = "+ numeroClasesExternas +" clases externas.");
            System.out.println();
		}
		System.out.println("CBO total = "+cboGlobal);
	}//FinMetodo
}//FinClase CBO

class ContarLOC{
    public static String contar(String [] codigo){
        boolean lineaCorrecta = true;
        String salida = "";
        for(int i = 0; i<codigo.length;i++){
			String cadenaActual = codigo[i];
            if(cadenaActual.contains("/*") && !cadenaActual.contains("if")){
				lineaCorrecta = false;
			}
			if(lineaCorrecta && cadenaActual.trim().length() > 0 && (!(cadenaActual.trim().startsWith("//"))||cadenaActual.contains("}//") ||cadenaActual.contains("if")) ){
				if(!(cadenaActual.contains("/*") && cadenaActual.contains("*/") && !cadenaActual.contains("if"))){
                    salida+=cadenaActual+"~";
				}	
			}	
			if(cadenaActual.contains("*/") && !cadenaActual.contains("if")){
				lineaCorrecta = true;
			}
        }
        return salida;
    }//FinMetodo
}//FinClase ContarLOC

class LeerArchivo{
	public static String [] consolaLinea(String ruta){
		String archivoSalida = "";		
		try{
			File file = new File(ruta);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);	
			while(true){
				String lineaActual = br.readLine();		
				if(lineaActual!=null){
					archivoSalida+=lineaActual+"~";						
				}else{
					return archivoSalida.split("~");
				}			
			}
		}catch(Exception e){
			System.out.println("Error");
		}
		return null;
	}//FinMetodo
}//FinClase LeerArchivo