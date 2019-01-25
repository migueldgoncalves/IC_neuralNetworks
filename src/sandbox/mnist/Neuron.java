package sandbox.mnist;

import java.lang.Float;
import java.lang.Math;
import java.util.Random;

public class Neuron {

	private static final int ROWS = 28;
	private static final int COLUMNS = 28;
	//private static final float LEARNING_RATE = 0.3f; //Should be between 0 and 1

	private float[] input = new float[(ROWS*COLUMNS)+1];
	private float[] weights = new float[(ROWS*COLUMNS)+1];
	private int number = 0; //The number the neuron will recognize, should be an int betwenn 0 and 9
	private float output = 0;
	private int desired = 0; //1 if input matches number to recognize, -1 if not

	public Neuron(int number) {
		this.number = number;
		this.input[0] = 1;
		for(int i=1;i<(ROWS*COLUMNS)+1;i++)
			this.input[i] = 0;
		Random random = new Random();
		for(int i=0;i<(ROWS*COLUMNS)+1;i++)
			this.weights[i] = -1.0f+2*(float)random.nextDouble();
	}

	private void setDesired(int label) {
		if(this.number == label)
			this.desired = 1;
		else
			this.desired = 0;
	}

	private void inputProcessing() {
		for(int i=0;i<(ROWS*COLUMNS)+1;i++) {
			float a = this.input[i];
			float b = this.weights[i];
			this.output+=(a*b);
		}
		if(new Float(this.output).isNaN())
			throw new ArithmeticException("Output is NaN!");
		if(this.output>=0)
			this.output = 1;
		else
			this.output = 0;
	}

	private void weightChanging(float learningRate) {
		for(int i=0;i<(ROWS*COLUMNS)+1;i++) {
			//this.weights[i]+=Math.tanh((double)(learningRate*(this.desired-this.output)*this.input[i]));
			this.weights[i]+=learningRate*(this.desired-this.output)*this.input[i];
			if(new Float(this.weights[i]).isNaN())
				throw new ArithmeticException("The new weight is NaN!");
		}
	}

	public void training(float[] input, int label, float learningRate) {
		for(int i=1;i<(ROWS*COLUMNS)+1;i++)
			this.input[i] = input[i-1];

		setDesired(label);

		inputProcessing();

		weightChanging(learningRate);

	}

	public int operation(float[] input, int label) {
		for(int i=1;i<(ROWS*COLUMNS)+1;i++)
			this.input[i] = input[i-1];

		setDesired(label);

		inputProcessing();

		if(this.desired==this.output)
			return 1;
		else
			return 0;
	}

	public int[] recognizeNumber() {
		int[] array = {(int)this.output,this.number};
		return array;
	}
}