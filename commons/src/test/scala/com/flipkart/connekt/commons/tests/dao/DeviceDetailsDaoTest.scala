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
package com.flipkart.connekt.commons.tests.dao

import java.util.UUID

import com.flipkart.connekt.commons.dao.DaoFactory
import com.flipkart.connekt.commons.entities.DeviceDetails
import com.flipkart.connekt.commons.tests.CommonsBaseTest
import com.flipkart.connekt.commons.utils.StringUtils

class DeviceDetailsDaoTest extends CommonsBaseTest {

  val deviceId = UUID.randomUUID().toString
  val appName = "connekt"
  val userId = "ACCID" + StringUtils.generateRandomStr(6)
  val tokenId = UUID.randomUUID().toString.replaceAll("-", "")
  val updatedTokenId = UUID.randomUUID().toString.replaceAll("-", "")

  val deviceDetails = DeviceDetails(deviceId, userId, tokenId, UUID.randomUUID().toString, UUID.randomUUID().toString,
    appName, UUID.randomUUID().toString, UUID.randomUUID().toString, UUID.randomUUID().toString, UUID.randomUUID().toString)
  val updatedDeviceDetails = deviceDetails.copy(token = updatedTokenId, state = "updated")


  "DeviceDetails Dao" should "save Device Details" in {
    noException should be thrownBy DaoFactory.getDeviceDetailsDao.add(appName,deviceDetails)
  }

  "Device Details Dao" should "fetch Device Details" in {
    DaoFactory.getDeviceDetailsDao.get(appName, deviceId).get shouldEqual deviceDetails
  }

  "Device Details Dao" should "fetch multiple Device details" in {
    DaoFactory.getDeviceDetailsDao.get(appName, Set(deviceId)).head shouldEqual deviceDetails
  }

  "Device Details Dao" should " get by userId" in {
    println(DaoFactory.getDeviceDetailsDao.getByUserId(appName, userId).head)
    DaoFactory.getDeviceDetailsDao.getByUserId(appName, userId).nonEmpty shouldBe true
    DaoFactory.getDeviceDetailsDao.getByUserId(appName, StringUtils.generateRandomStr(12)).nonEmpty shouldBe false
  }

  "Device Details Dao" should " get by token" in {
    DaoFactory.getDeviceDetailsDao.getByTokenId(appName, tokenId).nonEmpty shouldBe true
    DaoFactory.getDeviceDetailsDao.getByTokenId(appName, StringUtils.generateRandomStr(12)).nonEmpty shouldBe false

    println(DaoFactory.getDeviceDetailsDao.getByTokenId(appName, tokenId).get)
    DaoFactory.getDeviceDetailsDao.getByTokenId(appName, tokenId).get shouldEqual deviceDetails

  }

  "Device Details Dao" should "update" in {
    noException should be thrownBy DaoFactory.getDeviceDetailsDao.update(appName, deviceId, updatedDeviceDetails)
  }

  "Device Details Dao" should "fetch updated Device Details" in {
    DaoFactory.getDeviceDetailsDao.get(appName, deviceId).get shouldEqual updatedDeviceDetails
  }

  "Device Details Dao" should " get by updated token" in {
    DaoFactory.getDeviceDetailsDao.getByTokenId(appName, tokenId).isEmpty shouldBe true
    DaoFactory.getDeviceDetailsDao.getByTokenId(appName, updatedTokenId).nonEmpty shouldBe true
    println(DaoFactory.getDeviceDetailsDao.getByTokenId(appName, updatedTokenId).get)
    DaoFactory.getDeviceDetailsDao.getByTokenId(appName, updatedTokenId).get shouldEqual updatedDeviceDetails
  }

  "Device Dao" should "Get all" in {
    noException should be thrownBy DaoFactory.getDeviceDetailsDao.getAll(appName).foreach( println)
  }
}
