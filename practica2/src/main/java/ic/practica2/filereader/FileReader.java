package ic.practica2.filereader;

// Tutorial obtenido de aqui: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

public class FileReader {
	
	public static final Logger log = Logger.getLogger("log");
	
	public static int readSize(String path) {
	
		try {
		
			File file = new File(path);
			Scanner sc = new Scanner(file);
			//sc.useDelimiter(" |\n"); //Delimiter is either " " or "\n"
			
			while (sc.hasNextInt()) {
				String size = sc.next().trim();
				if (!size.equals("") && !size.equals("\n")) {
					sc.close();
					return Integer.valueOf(size);
				}
			}
			
			sc.close();
			return -1;
		} catch (Exception e) {
			FileReader.log.info("Error while getting value");
			FileReader.log.info(e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	
	public static int[] readDistanceMatrix(String path) {
		
		try {
		
			File file = new File(path);
			Scanner sc = new Scanner(file);
			sc.useDelimiter(" |\n");
			
			String size = null;
			
			while (sc.hasNext()) {
				size = sc.next().trim();
				if (!size.equals("") && !size.equals("\n"))
					break;
			}
			
			int sizeInt = Integer.valueOf(size);
			
			int[] distanceMatrix = new int[sizeInt*sizeInt];
			
			for(int i=0; i<sizeInt*sizeInt; i++) {
				while (true) {
					String distance = sc.next();
					if (!distance.equals("") && !distance.equals("\n")) {
						distanceMatrix[i] = Integer.valueOf(distance);
					    break;
					}
				}
			}
			
			sc.close();
			return distanceMatrix;
			
		} catch (FileNotFoundException e) {
			FileReader.log.info("Error while getting values");
			FileReader.log.info(e.getMessage());
			return null;
		}
	}
	
	public static int[] readWeightMatrix(String path) {
		
		try {
		
			File file = new File(path);
			Scanner sc = new Scanner(file);
			sc.useDelimiter(" |\n");
			
			String size = null;
			
			while (sc.hasNext()) {
				size = sc.next().trim();
				if (!size.equals("") && !size.equals("\n"))
					break;
			}
			
			int sizeInt = Integer.valueOf(size);
			
			int[] distanceMatrix = new int[sizeInt*sizeInt];
			
			for(int i=0; i<sizeInt*sizeInt; i++) {
				while (true) {
					String distance = sc.next();
					if (!distance.equals("") && !distance.equals("\n")) {
						distanceMatrix[i] = Integer.valueOf(distance);
					    break;
					}
				}
			}
			
			int[] weightMatrix = new int[sizeInt*sizeInt];
			
			for(int i=0; i<sizeInt*sizeInt; i++) {
				while (true) {
					String weight = sc.next();
					if (!weight.equals("") && !weight.equals("\n")) {
						weightMatrix[i] = Integer.valueOf(weight);
					    break;
					}
				}
			}
			
			sc.close();
			return weightMatrix;
			
		} catch (FileNotFoundException e) {
			FileReader.log.info("Error while getting values");
			FileReader.log.info(e.getMessage());
			return null;
		}
	}
	
	public static int[][] oneDToTwoD(int[] vector) {
		int length = (int) Math.sqrt(vector.length);
		int[][] matrix = new int[length][length];
		for(int i=0; i<length; i++)
			for(int j=0; j<length; j++)
				matrix[i][j] = vector[length*i+j];
		return matrix;
	}
}
