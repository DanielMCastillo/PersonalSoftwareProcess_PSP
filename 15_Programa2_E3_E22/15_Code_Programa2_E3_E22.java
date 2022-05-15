/*************************************************************************************/
/* Program Name: Programa 2 */
/* Name: Daniel Alejandro Morales Castillo */
/* Date: 04/05/2022 */
/* Description: Programa 2 de PSP - Contar lineas */
/*************************************************************************************/
import java.io.*;  //Librería IO 

public class Programa2{
	
	public static void main (String args[]){
		System.out.println("Ingresa la ruta del archivo: ");
		String ruta = LeerArchivo.consolalinea();
		contarLOC.contar(ruta);
	}	
}

class LeerArchivo {
    public static String consolalinea(){
            InputStreamReader entrada = new InputStreamReader(System.in);
            BufferedReader buffer = new BufferedReader(entrada);
            String stringentrada="";
            try{
                stringentrada = buffer.readLine();
            }catch(IOException e){
                e.printStackTrace();
            }finally{
            return stringentrada;
        }
    }
}

class contarLOC{
	public static void contar(String nombreArchivo){
		int contarClase = 0;
		int contarLOC = 0;
		int [] inicioClase = new int [1000];
		int [] finClase = new int [1000];
		String [] nombreClase = new String[1000];
		int [] contarMetodo= new int [1000];
		for(int i = 0;i<1000;i++){
			inicioClase[i]=0;
			finClase[i]=0;
			contarMetodo[i]=0;
		}
		File archivo = null;
		FileReader lector = null;
		BufferedReader readerArchivo = null;
		archivo = new File(nombreArchivo);
		if(archivo.isFile()){
			try{	
				lector = new FileReader(archivo);
				readerArchivo = new BufferedReader(lector);
				String linea;
				while ((linea = readerArchivo.readLine())!=null){//Recorre el archivo linea por linea
					linea = linea.replaceAll("^\\s*","");
					if(linea.startsWith("//")){
						contarLOC--;
					}
					if(!linea.equals("") || linea.startsWith("//")){
						contarLOC++;
					}
					if (linea.startsWith("/*") && linea.endsWith("*/")) {
						contarLOC--;
					}
					if(linea.contains("class") && linea.contains("{") && linea.contains("(")==false
						&& linea.contains(")")==false && linea.contains("=")==false){//Entra si encuentra una clase
						contarClase++;
						String nomClase = linea;
						nomClase = nomClase.replaceFirst("public","");
						nomClase = nomClase.replaceFirst("private","");
						nomClase = nomClase.replaceFirst("class","");
						nombreClase[contarClase] = nomClase;
						inicioClase[contarClase] = contarLOC;
						finClase[contarClase-1]=contarLOC;
					}
					if((linea.contains("public")||linea.contains("private"))&&linea.contains("(")&&linea.contains(")")&&linea.contains("{")&&(linea.contains("&&")==false)){
						contarMetodo[contarClase]=contarMetodo[contarClase]+1;	
					}
				}
				finClase[contarClase] = contarLOC+1;
				System.out.println("---------------------------------------------------------");
				System.out.println("Numero de programa :"+nombreArchivo+"\n");
				System.out.println("---------------------------------------------------------");
				System.out.println("Numero de clases del programa:"+contarClase+"\n");
				System.out.println("---------------------------------------------------------");
				for(int i = 1;i<=contarClase;i++){
					System.out.println("Clase numero: "+i+"\n");
					System.out.println("---------------------------------------------------------");
					System.out.println("Nombre de la clase: "+nombreClase[i]+"\n");
					System.out.println("---------------------------------------------------------");
					System.out.println("Numero de metodos: "+contarMetodo[i]+"\n");
					System.out.println("---------------------------------------------------------");
					System.out.println("Tamanio de la clase: "+(finClase[i]-inicioClase[i])+"\n");
					System.out.println("---------------------------------------------------------");					
				}
				System.out.println("Tamanio total(LOC):"+(contarLOC)+"\n");
				System.out.println("---------------------------------------------------------");
			}
			catch(FileNotFoundException e){
			e.printStackTrace();
			}
			catch(IOException e){
			e.printStackTrace();
			}
			finally{
				try{
					lector.close();
				}
				catch(IOException e){
					e.printStackTrace();		
				}
			}
		}
		else{
			System.out.println("No es un archivo o no existe");	
		}
	}	
}