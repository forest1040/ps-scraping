package controllers

import play._
import play.mvc._
import play.libs._
import play.libs.WS._

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

object Application extends Controller {
    
    import views.Application._
    
    def index = {
        html.index("Your Scala application is ready!")
    }
    
    def show = {
    	val targetUrl = params.get("target_url")
    	//Logger.info(targetUrl)
		val res = WS.url(targetUrl).get()
		val doc = Jsoup.parse(res.getString())
    	val body = doc.select("body").first()
    	//html.show("show", doc.text())
    	html.show("show", body.html())
    }
    
}
