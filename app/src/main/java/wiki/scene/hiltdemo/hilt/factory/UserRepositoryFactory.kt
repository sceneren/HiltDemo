package wiki.scene.hiltdemo.hilt.factory

import dagger.assisted.AssistedFactory
import wiki.scene.hiltdemo.entity.UserInfo
import wiki.scene.hiltdemo.mmkv.UserRepository

@AssistedFactory
interface UserRepositoryFactory {
    fun createUserRepository(userInfo: UserInfo): UserRepository
}