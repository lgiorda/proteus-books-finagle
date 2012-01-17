/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package code
package snippet

// TODO: CONVERT

import net.liftweb.common.Logger
import net.liftweb.http.js.JE.JsArray
import net.liftweb.http.js.JE.Num
import net.liftweb.http.js.JE.Str
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JsCmds._

import net.liftweb.http.js.JsExp
import net.liftweb._
import code.comet.TheCart
import http._
import scala.collection.mutable.ListBuffer
import util._
import Helpers._
import scala.collection.JavaConversions._


class Maps extends Logger {
  
  var idList:ListBuffer[JsArray] = new ListBuffer[JsArray]
// "JavaScript-producing" scala code goes here
  def render = {
    info("SESSION ID: " + S.session.openOr("NONE") + ", RENDER MAP")
    val book = S.param("book").openOr("no book in parameters")
    var arrOfLocs:ListBuffer[JsArray] = new ListBuffer[JsArray]
    arrOfLocs = getPageLocations(book)
    // creates a javascript array of arrays variable named "locs"
    val locs_array = JsCrVar("locs", JsArray(arrOfLocs.toList.map {l => l:JsExp}:_*))
    val id_array = JsCrVar("ids", JsArray(idList.toList.map {l => l:JsExp}:_*))
    
    //".book" #> S.param("book").openOr("No book param") &
    ".locsJS" #> Script(locs_array) &
    ".entid" #> Script(id_array) &
    ".book" #> book
     
  }
  import code.lib._
  import code.comet._
  //import edu.umass.ciir.proteus.protocol.ProteusProtocol._
  //import edu.umass.ciir.proteus._
  import edu.ciir.proteus.thrift._
  
  def getPageLocations(id: String): ListBuffer[JsArray] = {
    val master:ListBuffer[JsArray] = new ListBuffer[JsArray]
    val entities : List[SearchResult] = {
      if (TheCart.get.page_map.get.contains(id.toInt)) {
        Librarian.library.getDescendants(TheCart.get.page_map.get.apply(id.toInt).item, List(ProteusType.Page, ProteusType.Location)).get()
      }
      else if (TheCart.get.book_map.get.contains(id.toInt)) {
        Librarian.library.getDescendants(TheCart.get.book_map.get.apply(id.toInt).item, List(ProteusType.Collection, ProteusType.Page, ProteusType.Location)).get()
      }
      else Nil
    }

    entities.foreach(e => TheCart.addItem(e))
    var entity_count = 0
    
    
    for (entity <- entities.map(Librarian.library.lookupLocation(_))) {    
      val full_ent = entity.get() //Librarian.library.lookupLocation(entity).get
      val coordinates = (full_ent.title, full_ent.longitude, full_ent.latitude)
        println(coordinates + " " + (full_ent.id.identifier+ full_ent.id.identifier).hashCode.toString)
        master.add(JsArray(Str(coordinates._1.get), Num(coordinates._2.get.toDouble), Num(coordinates._3.get.toDouble)))
        idList.add(JsArray(Str(full_ent.title.get), Str((full_ent.id.identifier + full_ent.id.identifier).hashCode.toString)))
      
    }
    return master
  }


}
