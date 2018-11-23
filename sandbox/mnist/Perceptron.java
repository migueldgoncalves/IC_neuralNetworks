package sandbox.mnist;

import java.util.ArrayList;

import java.io.IOException;

public class Perceptron {

	private static final int NEURON_NUMBER = 10;

	ArrayList<Neuron> neurons = new ArrayList<Neuron>();

	private static final String trainingImages = "train-images-idx3-ubyte.gz";
	private static final String trainingLabels = "train-labels-idx1-ubyte.gz";

	private static final String testImages = "t10k-images-idx3-ubyte.gz";
	private static final String testLabels = "t10k-labels-idx1-ubyte.gz";

	public Perceptron() {

		for(int i=0; i<NEURON_NUMBER; i++)
			neurons.add(new Neuron(i));
	}

	public float execute(int option, ArrayList<float[]> oneDimensionData, float learningRate, int dataSize, int[] labels) {

		if(option==Application.TRAINING_MODE) {
			trainNeurons(oneDimensionData, learningRate, dataSize, labels);
			return 0.0f;
		}
		else
			return neuronOperation(oneDimensionData, dataSize, labels);
	}

	public int[][][] readImages(String path) {
		try {
			//System.out.println("Reading images...");
			int[][][] images = MNISTDatabase.readImages(path);
			//System.out.println("Images read");
			return images;
		}
		catch (IOException e) {
			System.out.println("An error ocurred while reading training data");
			return null;
		}
	}

	public ArrayList<float[][]> normalizeData(int[][][] images) {
		try {
			//System.out.println("Normalizing data...");
			ArrayList<float[][]> data = new ArrayList<float[][]>();
			for(int i=0; i<images.length; i++)
				data.add(MNISTDatabase.normalize(images[i]));
			return data;
		}
		catch (Exception e) {
			System.out.println("An error ocurred while normalizing training data");
			return null;
		}
	}

	public ArrayList<float[]> from2dTo1d(ArrayList<float[][]> data) {

		int length = data.get(0).length;
		int width = data.get(0)[0].length;

		ArrayList<float[]> oneDimensionData = new ArrayList<float[]>();
		int counter = 0;

		for(int i=0; i<data.size(); i++) {
			oneDimensionData.add(new float[length*width]);
			for(int j=0; j<data.get(0).length; j++) {
				for (int k=0; k<data.get(0)[0].length; k++) {
					oneDimensionData.get(i)[counter] = data.get(i)[j][k];
					counter++;
				}
			}
			counter = 0;
		}
		return oneDimensionData;
	}

	public int[] readLabels(String path) {
		try {
			//System.out.println("Getting labels...");
			return MNISTDatabase.readLabels(path);
			//System.out.println("Labels obtained");
		}
		catch (IOException e) {
			System.out.println("An error ocurred while reading labels");
			return null;
		}
	}

	public void trainNeurons(ArrayList<float[]> oneDimensionData, float learningRate, int dataSize, int[] labels) {
		Neuron neuron = null;
		float[] digitData = null;
		int label = 0;

		//System.out.println("Training neurons...");
		for(int i=0; i<dataSize; i++) {
			for(int j=0; j<NEURON_NUMBER; j++) {
				neuron = this.neurons.get(j);
				digitData = oneDimensionData.get(i);
				label = labels[i];
				neuron.training(digitData, label, learningRate);
			}
			/*if(i%1000==0)
				System.out.println(i+" out of "+this.data.size()+" processed");*/
		}
	}

	public float neuronOperation(ArrayList<float[]> oneDimensionData, int dataSize, int[] labels) {
		Neuron neuron = null;
		float[] digitData = null;
		int label = 0;
		int successReadings = 0;
		int insuccessReadings = 0;

		//System.out.println("Feeding data to neurons...");
		for(int i=0; i<dataSize; i++) {
			int neuronResultSum = 0;
			for(int j=0; j<NEURON_NUMBER; j++) {
				neuron = this.neurons.get(j);
				digitData = oneDimensionData.get(i);
				label = labels[i];
				neuronResultSum+=neuron.operation(digitData, label);
			}
			if(neuronResultSum==NEURON_NUMBER)
				successReadings++;
			else
				insuccessReadings++;
			/*if(i%1000==0)
				System.out.println(i+" out of "+this.data.size()+" processed");*/
		}
		//System.out.println("There were "+successReadings+" successfully read");
		//System.out.println("There were "+insuccessReadings+" unsuccessfully read");

		float errorPercentage = (insuccessReadings/(float)dataSize)*100;

		//System.out.println("Error rate was: "+errorPercentage+"%");

		return errorPercentage;
	}
	
}