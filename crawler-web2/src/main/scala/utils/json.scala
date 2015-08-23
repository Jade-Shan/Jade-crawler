package jadeutils.common

import org.json.JSONObject
import org.json.JSONArray

class Json

class JsonObject(str: String) extends Json {
	var value: JSONObject = if (null == str) null else new JSONObject(str)

	def this() = this("{}")

	def ~(k: String, v: String) = { value.put(k, v); this }
	def ~(k: String, v: Boolean) = { value.put(k, v); this }
	def ~(k: String, v: Int) = { value.put(k, v); this }
	def ~(k: String, v: Long) = { value.put(k, v); this }
	def ~(k: String, v: Double) = { value.put(k, v); this }
	def ~(k: String, v: JsonObject) = { value.put(k, v.value); this }
	def ~(k: String, v: JsonArray) = { value.put(k, v.value); this }

	def getString(k: String) = value.getString(k)
	def getBoolean(k: String) = value.getBoolean(k)
	def getInt(k: String) = value.getInt(k)
	def getDouble(k: String) = value.getDouble(k)
	def getLong(k: String) = value.getLong(k)

	def getJsonObject(k: String) = {
		val o = new JsonObject(null)
		o.value = value.getJSONObject(k)
		o
	}

	def getJsonArray(k: String) = {
		val o = new JsonArray(null)
		o.value = value.getJSONArray(k)
		o
	}

	override def toString = value.toString
}

object JsonObject { def JObj = new JsonObject }



class JsonArray(str: String) extends Json {
	var value: JSONArray = if (null == str) null else new JSONArray(str)

	def this() = this("[]")

	def ~~(o: String) = { value.put(o); this }
	def ~~(o: Boolean) = { value.put(o); this }
	def ~~(o: Int) = { value.put(o); this }
	def ~~(o: Long) = { value.put(o); this }
	def ~~(o: Double) = { value.put(o); this }
	def ~~(o: JsonObject) = { value.put(o.value); this }
	def ~~(o: JsonArray) =  { value.put(o.value); this }

	def getString(i: Int): String = fromArr[String](i, (i) => { value.getString(i) })
	def getBoolean(i: Int): Boolean = fromArr[Boolean](i, (i) => { value.getBoolean(i) })
	def getInt(i: Int): Int = fromArr[Int](i, (i) => { value.getInt(i) })
	def getLong(i: Int): Long = fromArr[Long](i, (i) => { value.getLong(i) })
	def getDouble(i: Int): Double = fromArr[Double](i, (i) => { value.getDouble(i) })

	def getJsonObject(i: Int): JsonObject = fromArr[JsonObject](i, (i) => {
			val o = new JsonObject(null)
			o.value = value.get(i).asInstanceOf[JSONObject]
			o
		})

	def getJsonArray(i: Int): JsonArray = fromArr[JsonArray](i, (i) => {
			val o = new JsonArray(null)
			o.value = value.get(i).asInstanceOf[JSONArray]
			o
		})

	private[this] def fromArr[T](i: Int, func: (Int) => T): T = {
		val n = if (null == value) 0 else value.length
		if (i < n) {
			func(i)
		} else {
			throw new java.lang.IndexOutOfBoundsException("%d of %d".format(i, n))
			null.asInstanceOf[T]
		}
	}

	override def toString = value.toString
}

object JsonArray { def JArr = new JsonArray }
