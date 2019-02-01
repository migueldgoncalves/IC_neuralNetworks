package ic.practica2.filereader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class FileReaderTest {
	
	public static String[] documents = {"bur26a.dat", "bur26b.dat", "bur26c.dat", "bur26d.dat", "bur26e.dat", "bur26f.dat", "bur26g.dat", "bur26h.dat", //8
			"chr12a.dat", "chr12b.dat", "chr12c.dat", "chr15a.dat", "chr15b.dat", "chr15c.dat", "chr18a.dat", "chr18b.dat", "chr20a.dat", "chr20b.dat", "chr20c.dat", "chr22a.dat", "chr22b.dat", "chr25a.dat", //22
			"lipa20a.dat", "lipa20b.dat", "lipa30a.dat", "lipa30b.dat", "lipa40a.dat", "lipa40b.dat", "lipa50a.dat", "lipa50b.dat", "lipa60a.dat", "lipa60b.dat", "lipa70a.dat", "lipa70b.dat", "lipa80a.dat", "lipa80b.dat", "lipa90a.dat", "lipa90b.dat", //38
			"nug12.dat", "nug14.dat", "nug15.dat", "nug16a.dat", "nug16b.dat", "nug17.dat", "nug18.dat", "nug20.dat", "nug21.dat", "nug22.dat", "nug24.dat", "nug25.dat", "nug27.dat", "nug28.dat", "nug30.dat", //53
			"tai60a.dat", "tai60b.dat", "tai64c.dat", "tai80a.dat", "tai80b.dat", "tai100a.dat", "tai100b.dat", "tai150b.dat", "tai256c.dat", //62
			"tho150.dat", "wil100.dat" //64
			};
	
	public static String path_base = ic.practica2.base.Main.path_base;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void fileReaderSizeTest() {
		String path = path_base + documents[0];
		int size = ic.practica2.filereader.FileReader.readSize(path);
		Assert.assertEquals(26, size);
		path = path_base + documents[61]; //tai256c
		size = ic.practica2.filereader.FileReader.readSize(path);
		Assert.assertEquals(256, size);
	}
	
	@Test
	public void fileReaderDistanceMatrixTest() {
		int[] distanceMatrix = ic.practica2.filereader.FileReader.readDistanceMatrix(path_base+documents[0]);
		Assert.assertEquals(26*26, distanceMatrix.length);
		
		Assert.assertEquals(53, distanceMatrix[0]);
		Assert.assertEquals(66, distanceMatrix[1]);
		
		Assert.assertEquals(53, distanceMatrix[25]);
		Assert.assertEquals(66, distanceMatrix[26]);
		
		Assert.assertEquals(66, distanceMatrix[26*26-2]);
		Assert.assertEquals(53, distanceMatrix[26*26-1]);
		
		distanceMatrix = ic.practica2.filereader.FileReader.readDistanceMatrix(path_base+documents[61]); //tai256c
		Assert.assertEquals(256*256, distanceMatrix.length);
		
		Assert.assertEquals(1, distanceMatrix[0]);
		Assert.assertEquals(1, distanceMatrix[1]);
		
		Assert.assertEquals(0, distanceMatrix[255]);
		Assert.assertEquals(1, distanceMatrix[256]);
		
		Assert.assertEquals(0, distanceMatrix[256*256-2]);
		Assert.assertEquals(0, distanceMatrix[256*256-1]);
	}
	
	@Test
	public void fileReaderWeightMatrixTest() {
		int[] weightMatrix = ic.practica2.filereader.FileReader.readWeightMatrix(path_base+documents[0]);
		Assert.assertEquals(26*26, weightMatrix.length);
		
		Assert.assertEquals(47, weightMatrix[0]);
		Assert.assertEquals(348, weightMatrix[1]);
		
		Assert.assertEquals(15, weightMatrix[25]);
		Assert.assertEquals(175, weightMatrix[26]);
		
		Assert.assertEquals(1, weightMatrix[26*26-2]);
		Assert.assertEquals(2, weightMatrix[26*26-1]);
		
		weightMatrix = ic.practica2.filereader.FileReader.readWeightMatrix(path_base+documents[61]); //tai256c
		Assert.assertEquals(256*256, weightMatrix.length);
		
		Assert.assertEquals(0, weightMatrix[0]);
		Assert.assertEquals(100000, weightMatrix[1]);
		
		Assert.assertEquals(50000, weightMatrix[255]);
		Assert.assertEquals(100000, weightMatrix[256]);
		
		Assert.assertEquals(100000, weightMatrix[256*256-2]);
		Assert.assertEquals(0, weightMatrix[256*256-1]);
	}
	
	@Test
	public void allFilesReaderTest() {
		for(String document: documents) {
			String path = path_base + document;
			int size = ic.practica2.filereader.FileReader.readSize(path);
			int distanceMatrixSize = ic.practica2.filereader.FileReader.readDistanceMatrix(path).length;
			int weightMatrixSize = ic.practica2.filereader.FileReader.readWeightMatrix(path).length;
			Assert.assertEquals(size*size, distanceMatrixSize);
			Assert.assertEquals(size*size, weightMatrixSize);
		}
	}
	
	@Test
	public void dimensionConverterTest() {
		int[] vector = {0, 1, 2, 3, 4, 5, 6, 7, 8};
		int[][] matrix = FileReader.oneDToTwoD(vector);
		for (int i=0; i<Math.sqrt(vector.length); i++)
			for (int j=0; j<Math.sqrt(vector.length); j++)
				Assert.assertEquals(vector[3*i+j], matrix[i][j]);
}
	
	@After
	public void tearDown() {
		
	}

}
