package wiki.scene.hiltdemo.hilt.factory

import dagger.assisted.AssistedFactory
import wiki.scene.hiltdemo.adapter.MainAdapter

@AssistedFactory
interface MainAdapterFactory {

    fun createMainAdapter(type: Int): MainAdapter
}