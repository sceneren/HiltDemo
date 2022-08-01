package wiki.scene.hiltdemo.network

data class BasePageListDataResponse<T>(
    val curPage: Int = 1,
    val datas: MutableList<T> = mutableListOf(),
    val pageCount: Int = 0
)