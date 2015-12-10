package com.flipkart.connekt.receptors.routes

import akka.stream.ActorMaterializer
import com.flipkart.connekt.receptors.routes.callbacks.Callback
import com.flipkart.connekt.receptors.routes.pn.{Unicast, Registration}
import akka.http.scaladsl.server.Directives._

/**
 * Created by kinshuk.bairagi on 10/12/15.
 */
 class RouteRegistry(implicit mat:ActorMaterializer ) {

  private val receptorReqHandler = new Registration().register
  private val unicastHandler = new Unicast().unicast
  private val callbackHandler = new Callback().callback

  def allRoutes =  unicastHandler ~ receptorReqHandler ~ callbackHandler
}