package com.flipkart.connekt.busybees.streams.flows

import akka.stream.stage.{GraphStage, GraphStageLogic}
import akka.stream.{Attributes, FlowShape}
import com.flipkart.connekt.commons.iomodels.{APSPayload, ConnektRequest}

/**
 *
 *
 * @author durga.s
 * @version 2/2/16
 */
class IOSChannelDispatcher extends GraphStage[FlowShape[ConnektRequest, APSPayload]]{
  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = ???

  override def shape: FlowShape[ConnektRequest, APSPayload] = ???
}
