package ic.practica2.filereader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class FileReaderTest {
	
	public static String path = ic.practica2.filereader.FileReader.path;
	
	// Nota: Las pruebas tienen los valores del conjunto de prueba bur26a
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void fileReaderSizeTest() {
		int size = ic.practica2.filereader.FileReader.readSize(path);
		Assert.assertEquals(26, size);
	}
	
	@Test
	public void fileReaderDistanceMatrixTest() {
		int[] distanceMatrix = ic.practica2.filereader.FileReader.readDistanceMatrix(path);
		Assert.assertEquals(26*26, distanceMatrix.length);
		
		Assert.assertEquals(53, distanceMatrix[0]);
		Assert.assertEquals(66, distanceMatrix[1]);
		
		Assert.assertEquals(53, distanceMatrix[25]);
		Assert.assertEquals(66, distanceMatrix[26]);
		
		Assert.assertEquals(66, distanceMatrix[26*26-2]);
		Assert.assertEquals(53, distanceMatrix[26*26-1]);
	}
	
	@Test
	public void fileReaderWeightMatrixTest() {
		int[] weightMatrix = ic.practica2.filereader.FileReader.readWeightMatrix(path);
		Assert.assertEquals(26*26, weightMatrix.length);
		
		Assert.assertEquals(47, weightMatrix[0]);
		Assert.assertEquals(348, weightMatrix[1]);
		
		Assert.assertEquals(15, weightMatrix[25]);
		Assert.assertEquals(175, weightMatrix[26]);
		
		Assert.assertEquals(1, weightMatrix[26*26-2]);
		Assert.assertEquals(2, weightMatrix[26*26-1]);
	}
	
	@After
	public void tearDown() {
		
	}

}
