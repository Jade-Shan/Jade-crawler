package jadeutils.common

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith


@RunWith(classOf[JUnitRunner])
class JsonTest extends FunSuite {
	import jadeutils.common.Json._

	val j1 = JObj ~ ("a", "1") ~ ("b", true) ~ ("c", 8) ~ ("d", 2.2) ~ ("e", 6553599999L)
	val j2 = JObj ~ ("aa", "1") ~ ("bb", true) ~ ("cc", 8) ~ ("dd", 2.2) ~ ("ee", 6553599999L)
	val j3 = JObj ~ ("aaa", "1") ~ ("bbb", true) ~ ("ccc", 8) ~ ("ddd", 2.2) ~ ("eee", 6553599999L)

	test("Test-Json-Obj") {
		assert(j1.toString == """{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}""")
		assert(j2.toString == """{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999}""")
		assert(j3.toString == """{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}""")
	}

	val a1 = j1 :: j2 :: j3 :: JArr
	val a2 = j3 :: j2 :: JArr
	val a3 = j3 :: j1 :: JArr
	val a4 = a2 :: a3 :: JArr

	test("Test-Json-Arr") {
		assert(a1.toString == """[{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2},{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999},{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}]""")
		assert(a1.getJsonObject(0).toString == """{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}""")
		assert(a1.getJsonObject(1).toString == """{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999}""")
		assert(a1.getJsonObject(2).toString == """{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}""")
		intercept[IndexOutOfBoundsException] { a1.getJsonObject(3) }

		assert(a4.toString                 == """[[{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999},{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}],[{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999},{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}]]""")
		assert(a4.getJsonArray(0).toString == """[{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999},{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}]"""                                                                                                                   )
		assert(a4.getJsonArray(1).toString == """[{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999},{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}]"""                                                                                                              )
		intercept[IndexOutOfBoundsException] { a1.getJsonArray(2) }
	}

	val j4 = JObj ~ ("a", "1") ~ ("b", j1) ~ ("c", j2)
	val j5 = JObj ~ ("a", "1") ~ ("b", j1) ~ ("c", a1)

	test("Test-Json-Obj-Obj-Arr") {
		assert(j4.toString == """{"a":"1","b":{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999},"c":{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999}}""")
		assert(j5.toString == """{"a":"1","b":{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999},"c":[{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2},{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999},{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}]}""")
	}

	test("Test-Json-getBasicValue") {
		assert(j1.getString("a") == "1")
		assert(j1.getBoolean("b") == true)
		assert(j1.getInt("c") == 8)
		assert(j1.getDouble("d") == 2.2)
		assert(j1.getLong("e") == 6553599999L)
	}


}

object JsonTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}

