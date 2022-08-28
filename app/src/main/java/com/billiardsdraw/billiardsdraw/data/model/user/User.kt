package com.billiardsdraw.billiardsdraw.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var uid: String,
    var username: String,
    var nickname: String,
    var name: String,
    var surnames: String,
    var email: String,
    var password: String,
    var age: String,
    var birthdate: Date,
    var country: String,
    var carambola_paints: Array<String>,
    var pool_paints: Array<String>,
    var role: String, // Free, premium
    var active: Boolean,
    var deleted: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (uid != other.uid) return false
        if (username != other.username) return false
        if (nickname != other.nickname) return false
        if (name != other.name) return false
        if (surnames != other.surnames) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (age != other.age) return false
        if (birthdate != other.birthdate) return false
        if (country != other.country) return false
        if (!carambola_paints.contentEquals(other.carambola_paints)) return false
        if (!pool_paints.contentEquals(other.pool_paints)) return false
        if (role != other.role) return false
        if (active != other.active) return false
        if (deleted != other.deleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + uid.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surnames.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + age.hashCode()
        result = 31 * result + birthdate.hashCode()
        result = 31 * result + country.hashCode()
        result = 31 * result + carambola_paints.contentHashCode()
        result = 31 * result + pool_paints.contentHashCode()
        result = 31 * result + role.hashCode()
        result = 31 * result + active.hashCode()
        result = 31 * result + deleted.hashCode()
        return result
    }
}