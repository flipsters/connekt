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
package com.flipkart.connekt.commons.tests.connections

import java.util.Properties
import javax.sql.DataSource

import com.aerospike.client.Host
import com.aerospike.client.async.{AsyncClient, AsyncClientPolicy}
import com.couchbase.client.java.Cluster
import com.flipkart.connekt.commons.connections.TConnectionProvider
import com.flipkart.connekt.commons.tests.connections.couchbase.CouchbaseMockCluster
import org.apache.commons.dbcp2.BasicDataSourceFactory
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{HConnection, HConnectionManager}

class MockConnectionProvider extends TConnectionProvider {

  override def createCouchBaseConnection(nodes: List[String]): Cluster = new CouchbaseMockCluster

  override def createHbaseConnection(hConnConfig: Configuration): HConnection = HConnectionManager.createConnection(hConnConfig)

  override def createDatasourceConnection(mySQLProperties: Properties): DataSource = BasicDataSourceFactory.createDataSource(mySQLProperties)

  override def createAeroSpikeConnection(nodes: List[String]): AsyncClient =  new AsyncClient(new AsyncClientPolicy(), nodes.map(new Host(_, 3000)): _ *)

}
