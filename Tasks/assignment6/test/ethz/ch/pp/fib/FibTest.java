package ethz.ch.pp.fib;

import org.junit.Assert;
import org.junit.Test;

public class FibTest {
  
  @Test
  public void testFibSeqSmall() {
    
    IFibonacci fib = new FibonacciSeq();
    Assert.assertEquals(0, fib.fib(0).intValue());
    Assert.assertEquals(1, fib.fib(1).intValue());
    Assert.assertEquals(1, fib.fib(2).intValue());
    Assert.assertEquals(2, fib.fib(3).intValue());
    Assert.assertEquals(3, fib.fib(4).intValue());
    Assert.assertEquals(5, fib.fib(5).intValue());
    Assert.assertEquals(8, fib.fib(6).intValue());
    Assert.assertEquals(13, fib.fib(7).intValue());
    Assert.assertEquals(21, fib.fib(8).intValue());
    Assert.assertEquals(34, fib.fib(9).intValue());
    Assert.assertEquals(55, fib.fib(10).intValue());      
  }
  
  @Test
  public void testFibSeqLarge() {
	  IFibonacci fib = new FibonacciSeq();
	  
	  Assert.assertEquals(514229, fib.fib(29).intValue());
	  Assert.assertEquals(39088169, fib.fib(38).intValue());
//	  Assert.assertEquals(1836311903, fib.fib(46).intValue());  
  }
  
  @Test
  public void testFibSeqCacheSmall() {
    
    IFibonacci fib = new FibonacciSeqCache();
    Assert.assertEquals(0, fib.fib(0).intValue());
    Assert.assertEquals(1, fib.fib(1).intValue());
    Assert.assertEquals(1, fib.fib(2).intValue());
    Assert.assertEquals(2, fib.fib(3).intValue());
    Assert.assertEquals(3, fib.fib(4).intValue());
    Assert.assertEquals(5, fib.fib(5).intValue());
    Assert.assertEquals(8, fib.fib(6).intValue());
    Assert.assertEquals(13, fib.fib(7).intValue());
    Assert.assertEquals(21, fib.fib(8).intValue());
    Assert.assertEquals(34, fib.fib(9).intValue());
    Assert.assertEquals(55, fib.fib(10).intValue());
  }

  @Test
  public void testFibSeqCacheMedium() {
	  IFibonacci fib = new FibonacciSeqCache();

	  Assert.assertEquals(514229, fib.fib(29).intValue());
	  Assert.assertEquals(39088169, fib.fib(38).intValue());
//	  Assert.assertEquals(1836311903, fib.fib(46).intValue());
  }

  @Test
  public void testFibSeqCacheLarge() {
	  IFibonacci fib = new FibonacciSeqCache();

	  Assert.assertEquals("354224848179261915075", fib.fib(100).toString());
	  Assert.assertEquals("222232244629420445529739893461909967206666939096499764990979600", fib.fib(300).toString());
  }

  @Test
  public void testFibMulti() {

    IFibonacci fib = new FibonacciMulti();
    Assert.assertEquals(0, fib.fib(0).intValue());
    Assert.assertEquals(1, fib.fib(1).intValue());
    Assert.assertEquals(1, fib.fib(2).intValue());
    Assert.assertEquals(2, fib.fib(3).intValue());
    Assert.assertEquals(3, fib.fib(4).intValue());
    Assert.assertEquals(5, fib.fib(5).intValue());
    Assert.assertEquals(8, fib.fib(6).intValue());
    Assert.assertEquals(13, fib.fib(7).intValue());
    Assert.assertEquals(21, fib.fib(8).intValue());
    Assert.assertEquals(34, fib.fib(9).intValue());
    Assert.assertEquals(55, fib.fib(10).intValue());
  }

  @Test
  public void testFibMultiLarge() {
	  IFibonacci fib = new FibonacciMulti();

	  Assert.assertEquals(514229, fib.fib(29).intValue());
	  Assert.assertEquals(39088169, fib.fib(38).intValue());
//	  Assert.assertEquals(1836311903, fib.fib(46).intValue());
  }

  @Test
  public void testFibMultiCache() {
    
    IFibonacci fib = new FibonacciMultiCache();
    Assert.assertEquals(0, fib.fib(0).intValue());
    Assert.assertEquals(1, fib.fib(1).intValue());
    Assert.assertEquals(1, fib.fib(2).intValue());
    Assert.assertEquals(2, fib.fib(3).intValue());
    Assert.assertEquals(3, fib.fib(4).intValue());
    Assert.assertEquals(5, fib.fib(5).intValue());
    Assert.assertEquals(8, fib.fib(6).intValue());
    Assert.assertEquals(13, fib.fib(7).intValue());
    Assert.assertEquals(21, fib.fib(8).intValue());
    Assert.assertEquals(34, fib.fib(9).intValue());
    Assert.assertEquals(55, fib.fib(10).intValue());      
  }
 
  @Test
  public void testFibMultiMediumCache() {
	  IFibonacci fib = new FibonacciMultiCache();
	  
	  Assert.assertEquals(514229, fib.fib(29).intValue());
	  Assert.assertEquals(39088169, fib.fib(38).intValue());
//	  Assert.assertEquals(1836311903, fib.fib(46).intValue());  
  }

  @Test
  public void testFibMultiLargeCache() {
	  IFibonacci fib = new FibonacciMultiCache();

	  Assert.assertEquals("354224848179261915075", fib.fib(100).toString());
	  Assert.assertEquals("222232244629420445529739893461909967206666939096499764990979600", fib.fib(300).toString());
  }

}
