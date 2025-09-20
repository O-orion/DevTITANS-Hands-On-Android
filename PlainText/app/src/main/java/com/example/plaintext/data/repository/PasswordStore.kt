package com.example.plaintext.data.repository

import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PasswordDBStore {
    fun getList(): Flow<List<Password>>
    suspend fun add(password: Password): Long
    suspend fun update(password: Password)
    suspend fun get(id: Int): Password?
    suspend fun save(passwordInfo: PasswordInfo)
    suspend fun isEmpty(): Flow<Boolean>
}

class LocalPasswordDBStore(
    private val passwordDao : PasswordDao
): PasswordDBStore {
    override fun getList(): Flow<List<Password>> {
        return passwordDao.getAll()
    }

    override suspend fun add(password: Password): Long {
        return passwordDao.insert(password)
    }

    override suspend fun update(password: Password) {
        return passwordDao.update(password)
    }

    override suspend fun get(id: Int): Password? {
        return passwordDao.getById(id)
    }

    override suspend fun save(passwordInfo: PasswordInfo) {
        passwordDao.insert(passwordInfo.toPassword())
    }

    override suspend fun isEmpty(): Flow<Boolean> {
        return passwordDao.getAll().map { it.isEmpty() }
    }
}

fun PasswordInfo.toPassword(): Password {
    return Password(
        id = this.id,
        name = this.name,
        login = this.login,
        password = this.password,
        notes = this.notes
    )
}