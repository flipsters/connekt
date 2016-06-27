/*
 *         -╥⌐⌐⌐⌐            -⌐⌐⌐⌐-
 *      ≡╢░░░░⌐\░░░φ     ╓╝░░░░⌐░░░░╪╕
 *     ╣╬░░`    `░░░╢┘ φ▒╣╬╝╜     ░░╢╣Q
 *    ║╣╬░⌐        ` ╤▒▒▒Å`        ║╢╬╣
 *    ╚╣╬░⌐        ╔▒▒▒▒`«╕        ╢╢╣▒
 *     ╫╬░░╖    .░ ╙╨╨  ╣╣╬░φ    ╓φ░╢╢Å
 *      ╙╢░░░░⌐"░░░╜     ╙Å░░░░⌐░░░░╝`
 *        ``˚¬ ⌐              ˚˚⌐´
 *
 *      Copyright © 2016 Flipkart.com
 */
package com.flipkart.connekt.callback.topologies

import com.flipkart.connekt.busybees.tests.streams.TopologyUTSpec
import com.flipkart.connekt.firefly.FireflyBoot
import com.flipkart.connekt.commons.entities.{GenericAction, HTTPEventSink, Subscription}
import com.flipkart.connekt.commons.helpers.KafkaProducerHelper
import com.flipkart.connekt.commons.services.ConnektConfig
import com.flipkart.connekt.commons.sync.{SyncManager, SyncMessage, SyncType}
import com.flipkart.connekt.commons.utils.StringUtils._
import com.typesafe.config.ConfigFactory

class HttpTopologyTest extends TopologyUTSpec {

  "HttpTopology Test" should "run" in {

    FireflyBoot.start()
//
//    val kafkaConnConf = ConnektConfig.getConfig("connections.kafka.consumerConnProps").getOrElse(ConfigFactory.empty())
//    ClientTopologyManager(kafkaConnConf)

    val subscriptionSuccess = new Subscription()

    subscriptionSuccess.name = "TestDrive"
    subscriptionSuccess.id = "8a4b3dfa-31b4-4608-addb-fe48a6669cd5"
    subscriptionSuccess.shutdownThreshold = 3
    subscriptionSuccess.header = null
    subscriptionSuccess.createdBy = "connekt-genesis"
    subscriptionSuccess.sink = new HTTPEventSink("POST", "http://requestb.in/qhqc4zqh",null)
    subscriptionSuccess.eventFilter = "8a4b3dfa-31b4-4608-addb-fe48a6669cd5"
    SyncManager.get().publish(SyncMessage(topic = SyncType.SUBSCRIPTION, List(GenericAction.START.toString, subscriptionSuccess.getJson)))

//    val subscriptionFailed = new Subscription()
//    subscriptionFailed.name = "endTestFailed"
//    subscriptionFailed.id = "d90f0656-f31b-4337-95ca-1cce4abc912e"
//    subscriptionFailed.shutdownThreshold = 3
//    subscriptionFailed.createdBy = "connekt-insomnia"
//    subscriptionFailed.sink = new HTTPEventSink("POST", "http://equestb.in/1hz15xo1",null)
//    subscriptionFailed.eventFilter = """
//                                         |package com.flipkart.connekt.commons.entities;
//                                         |import com.flipkart.connekt.commons.iomodels.CallbackEvent
//                                         |import com.flipkart.connekt.commons.iomodels.PNCallbackEvent;
//                                         |class ConnektSampleAppGroovy implements Evaluator {
//                                         |public boolean evaluate(CallbackEvent context) {
//                                         |return (context as PNCallbackEvent).eventType().equals("cm_received")
//                                         |}
//                                         |
//                                         |}
//                                       """.stripMargin
//    SyncManager.get().publish(SyncMessage(topic = SyncType.SUBSCRIPTION, List(GenericAction.START.toString, subscriptionFailed.getJson)))


    val kafkaProducerConnConf = ConnektConfig.getConfig("connections.kafka.producerConnProps").get
    val kafkaProducerPoolConf = ConnektConfig.getConfig("connections.kafka.producerPool").getOrElse(ConfigFactory.empty())
    val kafkaProducerHelper = KafkaProducerHelper.init(kafkaProducerConnConf, kafkaProducerPoolConf)



    val msg =s"""
             {"type":"PN","messageId":"9c75d26e-f82a-42e9-8896-41fcbab27369","clientId":"connekt-insomnia","deviceId":"6aebb89b060c442764f7b940a16e109c","eventType":"gcm_received","platform":"android","appName":"retailapp","contextId":"","cargo":"0:1466498004107001%c7d00653f9fd7ecd","timestamp":1466498004226}
      """

//    var a=0
//    for( a <- 1 to 10) {
//      println("Value of a: " + a)
//      kafkaProducerHelper.writeMessages("active_events", msg)
//    }
//
//
//
//    val msgFailed =s"""
//             {"type":"PN","messageId":"9c75d26e-f82a-42e9-8896-41fcbab27369","clientId":"connekt-insomnia","deviceId":"6aebb89b060c442764f7b940a16e109c","eventType":"cm_received","platform":"android","appName":"retailapp","contextId":"","cargo":"0:1466498004107001%c7d00653f9fd7ecd","timestamp":1466498004226}
//      """
//
//    var b=0
//    for( b <- 1 to 10) {
//      println("Value of b: " + b)
//      kafkaProducerHelper.writeMessages("active_events", msgFailed)
//    }
//
//    Thread.sleep(10000)
//    println("after the sleep")
//
      kafkaProducerHelper.writeMessages("active_events", msg)
//    kafkaProducerHelper.writeMessages("active_events", msgFailed)

    Thread.sleep(100000)

  }

}
