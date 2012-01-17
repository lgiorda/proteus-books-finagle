/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package code
package snippet

import code.lib._
import code.comet._

//import edu.umass.ciir.web.snippet.Entity
import net.liftweb._
import util._
import Helpers._
import http._
import sitemap._
import common._

import scala.xml.Text

object AnEntityPage {
  // create a parameterized page
  def menu = Menu.param[EntityItem]("Entity", Loc.LinkText(i => Text(i.item.title.get)),
		  			(a:String) => Box(TheCart.get.findEntityItem(a.toInt)), _.hashCode.toString) / "entity" / *
                             
}

class AnEntityPage(item: EntityItem) {
  def render = "@name *" #> <strong>{item.item.title.get}</strong> &
  "@description *" #> {Entity.getAdditionalInfo(item)} &
  "@price *" #> {item.item.title.get.capitalize} &
  "@add_to_cart [onclick]" #> SHtml.ajaxInvoke(() => TheCart.addItem(item.item))
}
