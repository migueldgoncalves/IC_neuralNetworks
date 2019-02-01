package ic.practica2.base;

//Tutorial del algoritmo genetico base: https://www.baeldung.com/java-genetic-algorithm

import ic.practica2.filereader.FileReader;

public class Main {
	
	//Cambiar aqui el path base
	public static String path_base = "C:\\Users\\migue\\Desktop\\IC_neuralNetworks\\practica2\\src\\main\\resources\\qap.datos\\";
	
	public static String document = "tai256c.dat";
	
	public static String path = path_base + document;
	
	public static int fileSize = FileReader.readSize(path);
	public static int[][] distanceMatrix = FileReader.oneDToTwoD(FileReader.readDistanceMatrix(path));
	public static int[][] weightMatrix = FileReader.oneDToTwoD(FileReader.readWeightMatrix(path));
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
		Algoritmo algoritmo = new Algoritmo();
		algoritmo.execute();
	}

}