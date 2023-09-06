package work.kcs_labo.miauth_test.model

import org.json.JSONArray
import org.json.JSONObject

fun JSONArray.forEach(action: (JSONObject) -> Unit) {
  for (i in 0 until length()) {
    action(getJSONObject(i))
  }
}

//fun JSONArray.forEach(action: (JSONArray) -> Unit) {
//  for (i in 0 until length()) {
//    action(get(i) as J)
//  }
//}
sealed class User {

  abstract val id: UserId
  abstract val userName: String
  abstract val host: String?
  abstract val name: String
  abstract val onlineStatus: OnlineStatus
  abstract val avatarUrl: String

  sealed class MiUser: User() {
    abstract val avatarBlurHash: String
    abstract val emojis: List<Emoji>
    abstract val miInstance: MiInstance?

    data class MiUserLite(
      override val id: UserId,
      override val userName: String,
      override val host: String,
      override val name: String,
      override val onlineStatus: OnlineStatus,
      override val avatarUrl: String,
      override val avatarBlurHash: String,
      override val emojis: List<Emoji>,
      override val miInstance: MiInstance?
    ): MiUser() {
      companion object {
        fun create(jsonObj: JSONObject): MiUserLite {
          val userObj = jsonObj.getJSONObject("user")
          return MiUserLite(
            id = UserId(userObj.getString("id")),
            userName = userObj.getString("username"),
            host = userObj.getString("host") ?: "",
            name = userObj.getString("name"),
            onlineStatus = when (userObj.getString("onlineStatus")) {
              "online" -> OnlineStatus.Online
              "active" -> OnlineStatus.Active
              "offline" -> OnlineStatus.Offline
              else -> OnlineStatus.Unknown
            },
            avatarUrl = userObj.getString("avatarUrl").replace("""\/""", "/"),
            avatarBlurHash = userObj.getString("avatarBlurhash"),
            emojis = listOf(),
            miInstance = null
          )
        }
      }
    }

    data class MiUserDetailed(
      override val id: UserId,
      override val userName: String,
      override val host: String,
      override val name: String,
      override val onlineStatus: OnlineStatus,
      override val avatarUrl: String,
      override val avatarBlurHash: String,
      override val emojis: List<Emoji>,
      override val miInstance: MiInstance?,
      val alsoKnownAs: List<String>,
      val bannerBlurHash: String?,
      val bannerColor: String?,
      val bannerUrl: String?,
      val birthday: DateString?,
      val createdAt: DateString,
      val description: String?,
      val ffVisibility: FFVisibility,
      val fields: List<Field>,
      val followersCount: Long,
      val followingCount: Long,
      val hasPendingFollowRequestFromYou: Boolean,
      val hasPendingFollowRequestToYou: Boolean,
      val isAdmin: Boolean,
      val isBlocked: Boolean,
      val isBlocking: Boolean,
      val isBot: Boolean,
      val isCat: Boolean,
      val isFollowed: Boolean,
      val isFollowing: Boolean,
      val isLocked: Boolean,
      val isModerator: Boolean,
      val isMuted: Boolean,
      val isSilenced: Boolean,
      val isSuspended: Boolean,
      val lang: String?,
      val lastFetchedAt: DateString?,
      val location: String?,
      val movedTo: String?,
      val notesCount: Long,
      val pinnedNoteIds: List<String>,
      val pinnedPageId: String?,
      val publicReactions: Boolean,
      val securityKeys: Boolean,
      val twoFactorEnabled: Boolean,
      val updatedAt: DateString?,
      val uri: String?,
      val url: String?,
    ): MiUser() {
      companion object {
        fun create(jsonObj: JSONObject): MiUserDetailed {
          val userObj = jsonObj.getJSONObject("user")
          return MiUserDetailed(
            id = UserId(userObj.getString("id")),
            userName = userObj.getString("username"),
            host = userObj.getString("host") ?: "",
            name = userObj.getString("name"),
            onlineStatus = when (userObj.getString("onlineStatus")) {
              "online" -> OnlineStatus.Online
              "active" -> OnlineStatus.Active
              "offline" -> OnlineStatus.Offline
              else -> OnlineStatus.Unknown
            },
            avatarUrl = userObj.getString("avatarUrl").replace("""\/""", "/"),
            avatarBlurHash = userObj.getString("avatarBlurhash"),
            emojis = listOf(),
            miInstance = null,
            alsoKnownAs = listOf(),
            bannerBlurHash = userObj.getString("bannerBlurHash"),
            bannerColor = null,
            bannerUrl = userObj.getString("bannerUrl").replace("""\/""", "/"),
            birthday = DateString(userObj.getString("birthday") ?: "0000-00-00"),
            createdAt = DateString(userObj.getString("createdAt")),
            description = userObj.getString("description"),
            ffVisibility = when (userObj.getString("ffVisibility")) {
              "public" -> FFVisibility.Public
              "followers" -> FFVisibility.Followers
              "private" -> FFVisibility.Private
              else -> FFVisibility.Unknown
            },
            fields = mutableListOf<Field>().also {
              userObj.getJSONArray("fields").forEach { jsonObj ->
                val name = jsonObj.getString("name")
                val text = jsonObj.getString("value")
                it.add(Field(name, text))
              }
            },
            followersCount = userObj.getLong("followersCount"),
            followingCount = userObj.getLong("followingCount"),
            hasPendingFollowRequestFromYou = false,
            hasPendingFollowRequestToYou = false,
            isAdmin = false,
            isBlocked = false,
            isBlocking = false,
            isBot = userObj.getBoolean("isBot"),
            isCat = userObj.getBoolean("isCat"),
            isFollowed = false,
            isFollowing = false,
            isLocked = userObj.getBoolean("isLocked"),
            isModerator = userObj.getBoolean("isModerator"),
            isMuted = false,
            isSilenced = userObj.getBoolean("isSilenced"),
            isSuspended = userObj.getBoolean("isSuspended"),
            lang = userObj.getString("lang"),
            lastFetchedAt = null,
            location = userObj.getString("location"),
            movedTo = userObj.getString("movedTo"),
            notesCount = userObj.getLong("notesCount"),
            pinnedNoteIds = mutableListOf<String>().also {
              userObj.getJSONArray("pinnedNoteIds").forEach {jsonArray ->
                it.add(jsonArray.getString("pinnedNoteId"))
              }
            },
            pinnedPageId = userObj.getString("pinnedPageId"),
            publicReactions = userObj.getBoolean("publicReactions"),
            securityKeys = userObj.getBoolean("securityKeys"),
            twoFactorEnabled = userObj.getBoolean("twoFactorEnabled"),
            updatedAt = DateString(userObj.getString("updatedAt")),
            uri = userObj.getString("uri"),
            url = userObj.getString("url")
          )
        }
      }
    }
  }
}

sealed class OnlineStatus {
  object Online: OnlineStatus()
  object Active: OnlineStatus()
  object Offline: OnlineStatus()
  object Unknown: OnlineStatus()

  override fun toString(): String {
    return when (this) {
      is Online -> "online"
      is Active -> "active"
      is Offline -> "offline"
      is Unknown -> "unknown"
    }
  }
}

sealed class FFVisibility {
  object Public: FFVisibility()
  object Followers: FFVisibility()
  object Private: FFVisibility()
  object Unknown: FFVisibility()

  override fun toString(): String {
    return when (this) {
      is Public -> "public"
      is Followers -> "followers"
      is Private -> "private"
      is Unknown -> "unknown"
    }
  }
}

data class Field(val name: String, val text: String)