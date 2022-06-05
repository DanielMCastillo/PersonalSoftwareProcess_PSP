/*************************************************************************************/
/* Program Name: Programa 4 */
/* Name: Daniel Alejandro Morales Castillo */
/* Date: 26/05/2022 */
/* Description: Programa 4 - Contar LCOM */
/*************************************************************************************/
import java.io.*;
import java.util.Scanner;
import java.util.regex.*;
  // ejemplo de como debe ir un atributo definido para contarlo en LCOM de lo contario no funcionará Ejemplo: "public int correr;"
  //si se pone un atributo como "int hola;" podría contar pero se confunde el programa y puede agarrar lineas que no son por eso mejor encapsular para un mejor rendimiento
  //Debe de contener su respectivo encapsulamiento como lo definimos en el estandar de conteo 
  //debe de ser estatico si la clase es estatica o el metodo en este caso 
    //los comentarios que indican que se termina un metodo tyampoco deben de ser borradas ya que de ello depende el conteo 
public class LCOM{
	public static void main(String[] args){
        System.out.println("Ingresa la ruta del archivo:");
		Scanner s = new Scanner(System.in);
        String archivo = contarLOC.formaLinea(LeerArchivo.leer(s.nextLine()));
        Pattern p = Pattern.compile("class(.*?)(//FinClase[^\\[])([^~]*)", Pattern.DOTALL);
        Matcher m = p.matcher(archivo);
        while (m.find()) {
			boolean notieneatributos = false;
			double sumaPorcentaje = 0;
			double contadorAtributos = 0;
			String aparece = m.group(1);
			System.out.println("------------------------------------------------------\nClase: "+m.group(3));
			contarLCOM vc = new contarLCOM(aparece);
			String [] metodos = vc.getMetodos();
			String [] atributos = vc.getAtributos();
			for(int i = 0;i<atributos.length;i++){
				float count = 0;
				if(atributos[0]==""){
					System.out.println("----No tiene atributos---- LCOM 100%");
					notieneatributos= true;
					break;
				}
				System.out.print("Atributo: " + "****" + atributos[i]+ "**** " + " Se usa en los Metodos: ");
				for(int j = 0;j<metodos.length;j++){
					if(metodos[j].contains(atributos[i])){
						System.out.print(contarLCOM.getNombreMetodo(metodos[j])+" ");
						count++;
					}
				}
				System.out.println("("+count+"/"+vc.getNumeroMetodos()+")*100="+(count/vc.getNumeroMetodos())*100+"%");
				sumaPorcentaje+=(count/vc.getNumeroMetodos())*100;
				contadorAtributos++;
			}
			if(!notieneatributos){
				System.out.println("Promedio de porcentaje "+(sumaPorcentaje/contadorAtributos)+"%");
				System.out.println("LCOM = "+(100-(sumaPorcentaje/contadorAtributos))+"%");
			}
        }
	}//FinMetodo
}//FinClase LCOM

class contarLCOM{
	private static String clase;
	private int numerodemetodos;
	private int equis;
	public contarLCOM(String clase){
		this.clase = clase;
		numerodemetodos = -1;
		equis = 0;
	}//FinMetodo

	public int getNumeroMetodos(){
		equis = +1;
		if(numerodemetodos==-1){
			int actualMetodos = 0;
			Pattern p = Pattern.compile("//FinMetodo[^\\[\"]", Pattern.DOTALL);
        	Matcher m = p.matcher(clase);
        	while (m.find()) {
				actualMetodos+=1;
			}
			return actualMetodos;
		}
		return numerodemetodos;
	}//FinMetodo

	public String[] getAtributos(){
		String [] arregloClase = clase.split("~");
		String aux = "";
		for(int i = 0;i<arregloClase.length;i++){
			Pattern p= Pattern.compile("(private|public|protected)(.*)([^\\(\\)])(;)");
			Matcher m= p.matcher(arregloClase[i]);
			while (m.find()) {
				String matched= m.group(0);
				String [] lineaSplit = matched.split(" ");
				aux+=lineaSplit[lineaSplit.length-1].substring(0,lineaSplit[lineaSplit.length-1].length()-1)+"-";
			}
		}
		return aux.split("-");
	}//FinMetodo


	public String [] getMetodos(){
		String [] arregloClase = clase.split("~");
		String aux = "";
		boolean esMetodo = false;
		for(int i = 0;i<arregloClase.length;i++){
			if((arregloClase[i].contains("public") || arregloClase[i].contains("private")|| arregloClase[i].contains("protected")) && arregloClase[i].contains("){") && (!arregloClase[i].contains("if")||arregloClase[i].contains("erifica"))){
				esMetodo = true;
			}

			if(esMetodo){
				aux+=arregloClase[i];
			}

			if(arregloClase[i].contains("//FinMetodo") && !arregloClase[i].contains("if") && !arregloClase[i].contains("Pattern")){
				esMetodo = false;
				aux+="~";
			}
		}
		return aux.split("~[^\"]?");
	}//FinMetodo

	public static String getNombreMetodo(String metodo){
		String primerLinea = metodo.split("\\(")[0];
		String arregloPrimera [] = primerLinea.split(" ");
		return arregloPrimera[arregloPrimera.length-1];
	}//FinMetodo
}//FinClase contarLCOM

class contarLOC{
    //public static int correr;
    public static String formaLinea(String [] codigo){
       // correr = 0;
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
}//FinClase contarLOC

class LeerArchivo{
	public static String [] leer(String ruta){
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
