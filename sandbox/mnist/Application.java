package sandbox.mnist;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class Application {

	protected static final int TRAINING_MODE = 1;
	protected static final int OPERATION_MODE = 2;

	protected static final float INITIAL_LEARNING_RATE = 0.5f; //Must be between 0 and 1
	protected static final float FINAL_LEARNING_RATE = 1.0f; //Must be between 0 and 1
	protected static final float LEARNING_RATE_INCREMENT = 0.5f; //Change here, do not set to 0

	private static final String trainingImages = "train-images-idx3-ubyte.gz";
	private static final String trainingLabels = "train-labels-idx1-ubyte.gz";

	private static final String testImages = "t10k-images-idx3-ubyte.gz";
	private static final String testLabels = "t10k-labels-idx1-ubyte.gz";

	public static void main(String[] args) {

		Perceptron perceptron = new Perceptron();
		int option = 0;

		//Preparing training
		int[][][] images = perceptron.readImages("data/mnist/"+trainingImages);
		ArrayList<float[][]> data = perceptron.normalizeData(images);
		ArrayList<float[]> oneDimensionData = perceptron.from2dTo1d(data);
		int[] labels = perceptron.readLabels("data/mnist/"+trainingLabels);

		//Preparing operation
		int[][][] images2 = perceptron.readImages("data/mnist/"+testImages);
		ArrayList<float[][]> data2 =perceptron.normalizeData(images2);
		ArrayList<float[]> oneDimensionData2 = perceptron.from2dTo1d(data2);
		int[] labels2 = perceptron.readLabels("data/mnist/"+testLabels);

		//Printing operation labels
		/*String labels2ToString = new String();
		for(int i=0; i<labels2.length; i++)
			labels2ToString+=labels2[i];
		System.out.println(labels2ToString);*/


		ArrayList errorRates = new ArrayList<>();

		System.out.println("Welcome to the application");
		//System.out.println("Insert "+TRAINING_MODE+" for training mode or "+OPERATION_MODE+" for operation mode:");
		while(true) {
			try {
				/*Scanner in = new Scanner(System.in);
				option = in.nextInt();
				if(option==TRAINING_MODE || option==OPERATION_MODE) {
					System.out.println("You have selected:"+option);
					perceptron.execute(option);
					if(option==TRAINING_MODE)
						System.out.println("Now insert "+OPERATION_MODE+" for operation mode or "+TRAINING_MODE+" for retraining");
					else
						System.out.println("Training and operation are complete. Insert "+TRAINING_MODE+" for training mode again or "+OPERATION_MODE+" for operation mode again");
				}
				else
					System.out.println("Your number is invalid. Please insert "+TRAINING_MODE+" for training mode or "+OPERATION_MODE+" for operation mode");*/
				for(float learningRate = INITIAL_LEARNING_RATE; learningRate<=FINAL_LEARNING_RATE; learningRate+=LEARNING_RATE_INCREMENT) {
					perceptron.execute(TRAINING_MODE, oneDimensionData, learningRate, data.size(), labels);
					float errorRate = perceptron.execute(OPERATION_MODE, oneDimensionData2, learningRate, data2.size(), labels2);
					errorRates.add(learningRate);
					errorRates.add(errorRate);
					System.out.println("Processed operation with learning rate "+learningRate);
				}
				for(int i=0; i<errorRates.size(); i=i+2)
					System.out.println("Learning rate: "+errorRates.get(i)+"; error rate: "+errorRates.get(i+1));
				return;
			}
			catch (InputMismatchException e) {
				System.out.println("You have not inserted a number. Please insert "+TRAINING_MODE+" for training mode or "+OPERATION_MODE+" for operation mode");
			}
			/*catch (Exception e) {
				System.out.println("An error ocurred during execution");
				return;
			}*/
		}
	}

}
