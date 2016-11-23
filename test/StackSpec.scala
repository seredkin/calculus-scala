import org.junit.Test
import service.CalcService

class StackSpec {

  def assertEquals(d: Number, i: Number): Unit = {
    if (!d.doubleValue().equals(i.doubleValue()))
      throw new RuntimeException("Assertion failed for " + d + " and " + i)
  }

  @Test
  def testCalcService: Unit = {
    val calService: CalcService = new CalcService
    assertEquals(calService.calcExpression("(1+1)*0.5+0"), 1)
    assertEquals(calService.calcExpression("3+(2*11)*1+0"), 25)
    assertEquals(calService.calcExpression("-10.1+(2*10)*-1"), -30.1)
    assertEquals(calService.calcExpression("-10.1+((2*10)+0)* -1"), -30.1)

  }
}