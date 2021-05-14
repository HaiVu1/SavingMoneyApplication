package haivv.learning.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey
    val userId: String,
    var userName: String = "",
    var password: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var gender: Int = 0,
    var dob: String = ""
)