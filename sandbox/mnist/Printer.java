package sandbox.mnist;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;

public class Printer {

	private static final String trainingImages = "train-images-idx3-ubyte.gz";
	private static final String trainingLabels = "train-labels-idx1-ubyte.gz";

	private static final String testImages = "t10k-images-idx3-ubyte.gz";
	private static final String testLabels = "t10k-labels-idx1-ubyte.gz";

	private static String trainingDataset = "";
	private static String testDataset = "";

	public static void main(String[] args) {

		Perceptron perceptron = new Perceptron();

		//Printing training dataset to file

		int[][][] images = perceptron.readImages("data/mnist/"+trainingImages);
		ArrayList<float[][]> data = perceptron.normalizeData(images);
		int[] labels = perceptron.readLabels("data/mnist/"+trainingLabels);

		System.out.println("Printing training database to file");

		File file = new File("training.txt");

		try {
			PrintWriter out = new PrintWriter(file);
			for(int i=0; i<60; i++) {
				String digit = MNISTDatabase.toString(data.get(i));
				digit = digit.substring(0, digit.length() - 1); //Last char must be removed from string to allow correct reading into table
				out.println(digit+labels[i]);
				System.out.println(i+" out of 60000 processed");
			}
			out.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File training.txt must be created");
		}

		System.out.println("Training database printed to file");

		//Printing test dataset to file


		int[][][] images2 = perceptron.readImages("data/mnist/"+testImages);
		ArrayList<float[][]> data2 = perceptron.normalizeData(images2);
		int[] labels2 = perceptron.readLabels("data/mnist/"+testLabels);

		System.out.println("Printing test database to file");

		File file2 = new File("test.txt");

		try {
			PrintWriter out = new PrintWriter(file2);
			for(int i=0; i<10; i++) {
				String digit = MNISTDatabase.toString(data2.get(i));
				digit = digit.substring(0, digit.length() - 1); //Last char must be removed from string to allow correct reading into table
				out.println(digit+labels2[i]);
				System.out.println(i+" out of 10000 processed");
			}
			out.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File test.txt must be created");
		}

		System.out.println("Test database printed to file");

		System.out.println(trainingDataset);

	}
}