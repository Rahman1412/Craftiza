package com.example.craftiza.utils

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

object TokenSerializer: Serializer<TokenPreferences> {
    override val defaultValue: TokenPreferences
        get() = TokenPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TokenPreferences {
        return TokenPreferences.parseFrom(input)
    }

    override suspend fun writeTo(t: TokenPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}