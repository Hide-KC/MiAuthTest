package work.kcs_labo.miauth_test.model

data class Note(
  val id: String,
//  val createdAt: DateString,
//  val text: String?,
//  val cw: String?,
//  val user: User,
//  val userId: String,
//  val reply: Note? = null,
//  val replyId: NoteId? = null,
//  val renote: Note? = null,
//  val renoteId: NoteId? = null,
//  val files: List<DriveFile>,
//  val fileIds: List<DriveFileId>,
//  val visibility: NoteVisibility,
//  val visibleUserIds: List<UserId>? = null,
//  val localOnly: Boolean = false,
//  val myReaction: String? = null,
//val reactions: Record<String, number>,
//val renoteCount: number,
//val repliesCount: number,
//val poll: List<Poll>? = null,
//val expiresAt: DateString?,
//val multiple: Boolean,
//val choices: ,
//val isVoted: Boolean,
//val text: String,
//val votes: number,
//val }[],
//val },
//val emojis: List<Emoji>,
//val uri: String? = null,
//val url: String? = null,
//val isHidden: Boolean = false
)

sealed class NoteVisibility {
  object Public: NoteVisibility()
  object Home: NoteVisibility()
  object Followers: NoteVisibility()
  object Specified: NoteVisibility()

  override fun toString(): String {
    return when(this) {
      is Public -> "public"
      is Home -> "home"
      is Followers -> "followers"
      is Specified -> "specified"
    }
  }
}