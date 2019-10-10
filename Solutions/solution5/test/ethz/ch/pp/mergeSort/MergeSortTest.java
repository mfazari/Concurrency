package ethz.ch.pp.mergeSort;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import ethz.ch.pp.util.DatasetGenerator;

public class MergeSortTest {
  
  @Test
  public void test100random() {
    
    DatasetGenerator dg = new DatasetGenerator(10000);
    int[] inputSeq = dg.generate();
    int[] inputMulti = new int[inputSeq.length];
    int[] ref = new int[inputSeq.length];
    System.arraycopy(inputSeq, 0, inputMulti, 0, inputSeq.length);
    System.arraycopy(inputSeq, 0, ref, 0, inputSeq.length);
        
    int[] resMulti = MergeSortSingle.sort(inputSeq);
    int[] resSingle = MergeSortMulti.sort(inputMulti, Runtime.getRuntime().availableProcessors());
    
    Arrays.sort(ref);
    Assert.assertArrayEquals(resSingle, resMulti);
    Assert.assertArrayEquals(ref, resMulti);
    
  }
  
  @Test
  public void testBigRandom() {
    
    DatasetGenerator dg = new DatasetGenerator(1024 * 1024);
    int[] inputSeq = dg.generate();
    int[] inputMulti = new int[inputSeq.length];
    int[] ref = new int[inputSeq.length];
    System.arraycopy(inputSeq, 0, inputMulti, 0, inputSeq.length);
    System.arraycopy(inputSeq, 0, ref, 0, inputSeq.length);
      
    int[] resMulti = MergeSortSingle.sort(inputSeq);
    int[] resSingle = MergeSortMulti.sort(inputMulti, Runtime.getRuntime().availableProcessors());
        
    
    Arrays.sort(ref);
    Assert.assertArrayEquals(resSingle, resMulti);
    Assert.assertArrayEquals(ref, resMulti);
    
  }
  
}
