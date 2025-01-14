package com.example.craftiza.utils

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

object UserSerializer: Serializer<UserPrefernces>{
    override val defaultValue: UserPrefernces
        get() = UserPrefernces.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPrefernces {
        return UserPrefernces.parseFrom(input)
    }

    override suspend fun writeTo(t: UserPrefernces, output: OutputStream) {
        t.writeTo(output)
    }

}