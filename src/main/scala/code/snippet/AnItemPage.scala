/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package code
package snippet

import code.lib._
import code.comet._

import net.liftweb._
import util._
import Helpers._
import http._
import sitemap._
import common._

import scala.xml.Text

object AnItemPage {
  // create a parameterized page
  def menu = Menu.param[PageItem]("Item",Loc.LinkText(i => Text(i.item.title.get)),
                              (a:String) => Box(TheCart.get.findPageItem(a.toInt)), _.hashCode.toString) / "item" / *
}

class AnItemPage(item: PageItem) {
  def render = "@name *" #> item.item.title.get &
  "@description *" #> item.item.summary.get.text &
  "@price *" #> item.item.externalUrl.get &
  "@add_to_cart [onclick]" #> SHtml.ajaxInvoke(() => TheCart.addItem(item.item))
}

