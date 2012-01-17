package code.snippet

import java.io.FileWriter
import java.io.IOException
import net.liftweb._
import net.liftweb.common._
import http._ 
import scala.collection.mutable.ListBuffer
import scala.xml.NodeSeq
import util._
import Helpers._
import scala.collection.JavaConversions._
import scala.collection.mutable.HashSet
import java.io.File
import edu.ciir.proteus.ProteusClient
import edu.ciir.proteus.thrift._


object Librarian extends Logger  {
  //val library = new LibrarianClient("localhost", 8081)
  val library = new ProteusClient("mildura.cs.umass.edu", 8999)
  def performSearch(query:String, typesRequested: List[String]) : List[SearchResult] = {
      
        // catch illegal operator exception here
        val processed = processQuery(trace("processing query", query))
        
        info("scala.Query.performSearch => PROCESSED_QUERY: " + processed)
        //Timer.go("Lower level doc search")
        //val result = library.query(processed, typesRequested.map(convertType(_))).get.asInstanceOf[SearchResponse]//search.runQuery(processed, p, provideSnippets)
        val results = library.query(processed, typesRequested.map(t => ProteusType.valueOf(t).get)).get().results.toList
        //info(Timer.stop)
        //return result.getResultsList.toList
        return results
    }

    // Need to split up multi-word queries, but ignore components that may be useful
    val ignored = """has_(obj|sub)|(obj|sub)_of|entity_|page_""".r
    val stripRE = """\.""".r

    def processQuery(query: String) : String = {
        if (query.startsWith("#")) 
            return query
    
       
        val result = stripRE.replaceAllIn(query, " ").toLowerCase
        return result
    }
  
}