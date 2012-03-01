package controllers

import play._
import play.mvc._
import play.libs._
import play.libs.WS._

import org.jsoup.Jsoup
import org.jsoup.nodes._
import org.jsoup.select._

object Application extends Controller {
    
    import views.Application._
    
    def index = {
        html.index("Your Scala application is ready!")
    }
    
    def showByHtml = {
    	val targetUrl = params.get("target_url")
		val res = WS.url(targetUrl).get()
		val doc = Jsoup.parse(res.getString())
    	val body = doc.select("body").first()
    	//html.show("show", doc.text())
    	html.show("show", body.html())
    }
    
    def showByText = {
    	val targetUrl = params.get("target_url")
		val res = WS.url(targetUrl).get()
		val doc = Jsoup.parse(res.getString())
    	//val body = doc.select("body").first()

		//val strings = new java.util.ArrayList[String]
		var str = ""
		val nd = new NodeTraversor(new NodeVisitor() {
			override def tail(node : Node, depth : Int) = {
				if (node.isInstanceOf[TextNode]) {
					val textNode = node.asInstanceOf[TextNode]
					val text = textNode.getWholeText()
					//Logger.info(text)
					str += text + "\n"
				}
			}
			override def head(node : Node, depth : Int) = {
			}
		})
		nd.traverse(doc.body());
    	
    	html.show("show", "<pre>" + str + "</pre>")
    }
    
}
