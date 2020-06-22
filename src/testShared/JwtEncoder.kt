import java.util.*

object JwtEncoder {
    @ExperimentalStdlibApi
    fun encode(request:String):String{
        val encoder = Base64.getEncoder()
        val result = encoder.encode(request.encodeToByteArray())
        return String(result)
    }
}