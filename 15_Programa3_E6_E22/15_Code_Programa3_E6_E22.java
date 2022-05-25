/*************************************************************************************/
/* Program Name: Programa 3 */
/* Name: Daniel Alejandro Morales Castillo */
/* Date: 17/05/2022 */
/* Description: Programa 3 de PSP - Conmplejidad ciclomatica */
/*************************************************************************************/
import java.io.*;  //Librería IO 

public class 15_Code_Programa3_E5_E22{
	
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
        int contarmetodo = 0;
        int contarComplejidadCiclomatica = 0;
		int [] inicioClase = new int [10000];
		int [] finClase = new int [10000];
		String [] nombreClase = new String[10000];
		int [] contarMetodo= new int [10000];
        int [] contarComplejidad = new int[10000];
        String [] nombreMetodo = new String [10000];
        int [] inicioMetodo = new int [10000];
        int [] finMetodo = new int [10000];
		for(int i = 0;i<10000;i++){
			inicioClase[i]=0;
			finClase[i]=0;
			contarMetodo[i]=0;
            inicioMetodo[i]=0;
            finMetodo[i]=0;
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
					if(linea.contains("class") && linea.contains("{") && linea.contains("(")==false && linea.contains(")")==false && linea.contains("=")==false){//Entra si encuentra una clase
						contarClase++;
						String nomClase = linea;
						nomClase = nomClase.replaceFirst("public","");
						nomClase = nomClase.replaceFirst("private","");
						nomClase = nomClase.replaceFirst("class","");
						nombreClase[contarClase] = nomClase;
						inicioClase[contarClase] = contarLOC;
						finClase[contarClase-1]=contarLOC;
                    }
                    if((linea.contains("public")||linea.contains("private"))&&linea.contains("(")&&linea.contains(")")&&linea.contains("{")&&(linea.contains("&&")==false)){ //entra si encuentr
                        contarMetodo[contarClase]=contarMetodo[contarClase]+1;	
                        contarmetodo++;
                        String nomMetodo = linea;
                        nombreMetodo[contarmetodo] = nomMetodo;
                        inicioMetodo[contarmetodo] = contarLOC;
                        finMetodo[contarmetodo-1]=contarLOC;
                        contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;	
                    }
                        if(linea.contains("for(")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("while(")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("foreach")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("case")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("default")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("continue")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("goto")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("&&")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("||")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("catch(")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("?:")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                        if(linea.contains("??")){
                            contarComplejidad[contarClase]=contarComplejidad[contarClase]+1;
                        }
                }
				finClase[contarClase] = contarLOC+1;
                finMetodo[contarmetodo] = contarLOC;
				System.out.println("---------------------------------------------------------");
				System.out.println("Numero de programa :"+nombreArchivo+"\n");
				System.out.println("---------------------------------------------------------");
				System.out.println("Numero de clases del programa:"+contarClase+"\n");
				System.out.println("---------------------------------------------------------");
				for(int i = 1;i<=contarClase;i++){
					System.out.println("Clase numero: "+i+"\n");
					System.out.println("Nombre de la clase: "+nombreClase[i]+"\n");
                    System.out.println("La complejidad ciclomatica total es: "+contarComplejidad[i]+"\n");
                    if(contarComplejidad[i]<=10){
                        System.out.println("La complejidad de clase es simple, no hay mucho riesgo");
                        System.out.println("---------------------------------------------------------");
                    }
                    if(contarComplejidad[i] >= 11 && contarComplejidad[i]<= 20){
                        System.out.println("La complejidad de clase es de riesgo moderado, se recomienda pensar en formas de simplificar el codigo");
                        System.out.println("---------------------------------------------------------");
                    }
                    if(contarComplejidad[i]>20){
                        System.out.println("La complejidad de clase es de alto riesgo");
                        System.out.println("---------------------------------------------------------");
                    }
				}
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