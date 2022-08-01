package wiki.scene.hiltdemo.network

data class BaseResponse<T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
)