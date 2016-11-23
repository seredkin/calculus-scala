
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CalculusSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://futurice-calculus.herokuapp.com")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate, sdch")
		.acceptLanguageHeader("en-US,en;q=0.8,ru;q=0.6")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Upgrade-Insecure-Requests" -> "1")


	val calc01 = repeat(100){ exec(http("request_0").get("/calculus?query=MiAqICgyMy8oMyozKSktIDIzICogKDIqMyk="))}

	val scn = scenario("CalculusSimulation").exec(calc01)

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}