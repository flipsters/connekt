package com.flipkart.connekt.commons.tests

import com.flipkart.connekt.commons.dao.DaoFactory
import com.flipkart.connekt.commons.factories.{LogFile, ConnektLogger, ServiceFactory}
import com.flipkart.connekt.commons.helpers.KafkaProducerHelper
import com.flipkart.connekt.commons.services.ConnektConfig
import com.flipkart.connekt.commons.tests.connections.MockConnectionProvider
import com.typesafe.config.ConfigFactory

/**
 * @author aman.shrivastava on 10/12/15.
 */
class CommonsBaseTest extends ConnektUTSpec {

  override def beforeAll() = {
    super.beforeAll()
    bootstrapReceptors()
  }

  private def bootstrapReceptors() = {

    ConnektConfig(configHost = "config-service.nm.flipkart.com", configPort = 80, configAppVersion = 1)()

    DaoFactory.setUpConnectionProvider(new MockConnectionProvider)

    val hConfig = ConnektConfig.getConfig("receptors.connections.hbase").getOrElse(ConfigFactory.empty())
    DaoFactory.initHTableDaoFactory(hConfig)

    val mysqlConf = ConnektConfig.getConfig("receptors.connections.mysql").getOrElse(ConfigFactory.empty())
    DaoFactory.initMysqlTableDaoFactory(mysqlConf)

    val couchbaseCf = ConnektConfig.getConfig("receptors.connections.couchbase").getOrElse(ConfigFactory.empty())
    DaoFactory.initCouchbaseCluster(couchbaseCf) // Mocked

    val kafkaConnConf = ConnektConfig.getConfig("receptors.connections.kafka.producerConnProps").getOrElse(ConfigFactory.empty())
    val kafkaProducerPoolConf = ConnektConfig.getConfig("receptors.connections.kafka.producerPool").getOrElse(ConfigFactory.empty())
    KafkaProducerHelper.init(kafkaConnConf, kafkaProducerPoolConf)

    ServiceFactory.initMessageService(DaoFactory.getRequestInfoDao, KafkaProducerHelper, null)
    ServiceFactory.initCallbackService(null, DaoFactory.getPNCallbackDao, DaoFactory.getRequestInfoDao, null)
    ServiceFactory.initAuthorisationService(DaoFactory.getPrivDao, DaoFactory.getUserInfoDao)


    ConnektLogger(LogFile.SERVICE).info("BaseReceptorsTest bootstrapped.")
  }
}
