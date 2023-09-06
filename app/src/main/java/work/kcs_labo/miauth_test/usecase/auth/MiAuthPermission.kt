package work.kcs_labo.miauth_test.usecase.auth

sealed class MiAuthPermission {

  object ReadAccount: MiAuthPermission() //アカウントの情報を見る
  object WriteAccount: MiAuthPermission() //アカウントの情報を変更する
  object ReadBlocks: MiAuthPermission() //ブロックを見る
  object WriteBlocks: MiAuthPermission() //ブロックを操作する
  object ReadDrive: MiAuthPermission() //ドライブを見る
  object WriteDrive: MiAuthPermission() //ドライブを操作する
  object ReadFavorites: MiAuthPermission() //お気に入りを見る
  object WriteFavorites: MiAuthPermission() //お気に入りを操作する
  object ReadFollowing: MiAuthPermission() //フォローの情報を見る
  object WriteFollowing: MiAuthPermission() //フォロー・フォロー解除する
  object ReadMessaging: MiAuthPermission() //チャットを見る
  object WriteMessaging: MiAuthPermission() //チャットを操作する
  object ReadMutes: MiAuthPermission() //ミュートを見る
  object WriteMutes: MiAuthPermission() //ミュートを操作する
  object WriteNotes: MiAuthPermission() //ノートを作成・削除する
  object ReadNotifications: MiAuthPermission() //通知を見る
  object WriteNotifications: MiAuthPermission() //通知を操作する
  object WriteReactions: MiAuthPermission() //リアクションを操作する
  object WriteVotes: MiAuthPermission() //投票する
  object ReadPages: MiAuthPermission() //ページを見る
  object WritePages: MiAuthPermission() //ページを操作する
  object WritePageLikes: MiAuthPermission() //ページのいいねを操作する
  object ReadPageLikes: MiAuthPermission() //ページのいいねを見る
  object WriteGalleryLikes: MiAuthPermission() //ギャラリーのいいねを操作する
  object ReadGalleryLikes: MiAuthPermission() //ギャラリーのいいねを見る

  @Suppress("SpellCheckingInspection")
  override fun toString(): String {
    return when (this){
      is ReadAccount -> "read%3Aaccount"
      is WriteAccount -> "write%3Aaccount"
      is ReadBlocks -> "read%3Ablocks"
      is WriteBlocks -> "write%3Ablocks"
      is ReadDrive -> "read%3Adrive"
      is WriteDrive -> "write%3Adrive"
      is ReadFavorites -> "read%3Afavorites"
      is WriteFavorites -> "write%3Afavorites"
      is ReadFollowing -> "read%3Afollowing"
      is WriteFollowing -> "write%3Afollowing"
      is ReadMessaging -> "read%3Amessaging"
      is WriteMessaging -> "write%3Amessaging"
      is ReadMutes -> "read%3Amutes"
      is WriteMutes -> "write%3Amutes"
      is WriteNotes -> "write%3Anotes"
      is ReadNotifications -> "read%3Anotifications"
      is WriteNotifications -> "write%3Anotifications"
      is WriteReactions -> "write%3Areactions"
      is WriteVotes -> "write%3Avotes"
      is ReadPages -> "read%3Apages"
      is WritePages -> "write%3Apages"
      is WritePageLikes -> "write%3Apage-likes"
      is ReadPageLikes -> "read%3Apage-likes"
      is WriteGalleryLikes -> "write%3Agallery-likes"
      is ReadGalleryLikes -> "read%3Agallery-likes"
    }
  }
}
