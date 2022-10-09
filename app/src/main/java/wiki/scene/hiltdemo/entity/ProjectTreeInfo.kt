package wiki.scene.hiltdemo.entity

data class ProjectTreeInfo(
    var courseId: Int = 0,
    var id: Int = 0,
    var name: String = "",
    var order: Int = 0,
    var parentChapterId: Int = 0,
    var visible: Int = 0
)