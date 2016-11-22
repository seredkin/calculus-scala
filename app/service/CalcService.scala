package service

import java.util
import java.util.regex.Pattern

/**
  * Created by Anton on 22.11.2016.
  */
class CalcService {

  def parse = java.lang.Double.parseDouble _


  def calcExpression(query: String) = calcStack(toPostfix(formatExpression(query)))

  def calcStack(str: String) = {

    val ops = Map(
      "+" -> ((_: Double) + (_: Double)),
      "*" -> ((_: Double) * (_: Double)),
      "-" -> ((x: Double, y: Double) => y - x),
      "/" -> ((x: Double, y: Double) => y / x)
    )

    val stack = new scala.collection.mutable.Stack[Double]

    str.split(' ').foreach(token =>
      stack.push(
        if (ops.contains(token)) ops(token)(stack.pop, stack.pop)
        else parse(token)
      ))

    stack.pop

  }

  private val operands: Map[String, Int] = List("+" -> 1, "-" -> 2, "*"->3, "/"->4).toMap

  private def toPostfix(infix: String): String = {
    val out: StringBuilder = new StringBuilder
    val stack: util.Deque[String] = new util.LinkedList[String]
    for (token <- spacePattern.split(infix)) {
      // operator
      if (operands.contains(token)) {
        while (!stack.isEmpty && isUpper(token, stack.peek)) out.append(stack.pop).append(' ')
        stack.push(token)
      }
      else if (token == "(") stack.push(token)
      else if (token == ")") {
        while (stack.peek != "(") out.append(stack.pop).append(' ')
        stack.pop
      }
      else out.append(token).append(' ')
    }
    while (!stack.isEmpty) out.append(stack.pop).append(' ')
    out.toString.trim
  }

  private def isUpper(left: String, right: String): Boolean = operands.contains(right) && operands(right) >= operands(left)

  private val digitPattern: Pattern = Pattern.compile("(\\-?\\d*\\.?\\d+)")
  private val operandPattern: Pattern = Pattern.compile("([\\+\\*/^])")
  private val bracketPattern: Pattern = Pattern.compile("([\\(\\)])")
  private val spacePattern: Pattern = Pattern.compile("\\s+")
  private val outerSpacePattern: Pattern = Pattern.compile("^\\s+|\\s+$")

  private def formatExpression(exp: String): String = {
    var ex  = digitPattern.matcher(exp).replaceAll(" $1 ")
    ex = operandPattern.matcher(ex).replaceAll(" $1 ")
    ex = bracketPattern.matcher(ex).replaceAll(" $1 ")
    ex = spacePattern.matcher(ex).replaceAll(" ")
    outerSpacePattern.matcher(ex).replaceAll("")
  }

}
