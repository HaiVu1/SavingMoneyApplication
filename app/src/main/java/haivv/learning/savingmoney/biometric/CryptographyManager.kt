package haivv.learning.savingmoney.biometric

import java.security.Key
import javax.crypto.Cipher

interface CryptographyManager {
    fun getInitializedCipherForEncryption(keyName :String) : Cipher

    fun detectedChangeFingerprint(keyName: String) :Boolean

    fun getCurrentKey(keyName: String): Key?

    fun createKeyNotExits(keyName: String)
}