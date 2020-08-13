import java.time.LocalDateTime


class JwtPayload(val username: String, val password: String, val exp: Long)