package ergast

import io.qameta.allure.scalatest.AllureScalatestContext
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.mockito.scalatest.MockitoSugar
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers.hasSize
import org.hamcrest.core.IsEqual.equalTo

import scala.Console.in

class F1Spec extends AnyFunSpec with Matchers with ScalaFutures with MockitoSugar with AllureScalatestContext {

  describe("ergast.com public APIs") {
    val basicURL = "http://ergast.com/api/f1"
    //using Path parameters
    val season = "2017"
    val numberOfRaces = 20
    val favDriver = "alonso"
    val constructor = "renault"


    describe("GET /f1") {

      it(s"should return list of ${numberOfRaces} circuits for ${season} F1 season in JSON format") {
        given()
          .pathParam("raceSeason", season)
          .when
          .get(basicURL + "/{raceSeason}/circuits.json")
          .Then()
          .assertThat()
          .statusCode(200)
          .and()
          .contentType(ContentType.JSON)
          .and()
          .body("MRData.CircuitTable.Circuits.circuitId", hasSize(numberOfRaces))
      }

      it("should allow everything in CORS and have header with given length") {
        given()
          .pathParam("raceSeason", season)
          .when
          .get(basicURL + "/{raceSeason}/circuits.json")
          .Then()
          .assertThat()
          .header("Access-Control-Allow-Origin", equalTo("*"))
          .and().
          header("Content-Length", equalTo("4551"))
      }

      it("should retrieve first circuit ID for 2017 and assert Location") {
        //very important to specify type String for casting
        val circuitId : String = given()
          .when
          .get(basicURL + "/2017/circuits.json")
          .Then()
          .extract()
          .path("MRData.CircuitTable.Circuits.circuitId[0]")

        given()
          .pathParam("circuitId",circuitId)
          .when()
          .get("http://ergast.com/api/f1/circuits/{circuitId}.json")
          .Then()
          .assertThat()
          .body("MRData.CircuitTable.Circuits.Location[0].country",equalTo("Australia"))

      }

      it(s"should return list of standings of ${favDriver} for ${constructor} ") {
        given()
          .pathParam("driver", favDriver)
          .when
          .get(basicURL + "/drivers/{driver}/driverStandings.json")
          .Then()
          .assertThat()
          .statusCode(200)
          .and()
          .contentType(ContentType.JSON)
          .and()
          .body("MRData.StandingsTable.driverId", equalTo(favDriver))


      }


    }
  }
}

