package lv.edw.randomBoxes

import org.junit.Assert
import org.junit.Test
import org.springframework.security.crypto.bcrypt.BCrypt


class BcryptTesterino {

    @Test
    fun testBcrypt() {
        val hashpw = BCrypt.hashpw("1234", BCrypt.gensalt())
        val checkpw = BCrypt.checkpw("1234", hashpw)
        Assert.assertTrue(checkpw)
    }
}