package work.kcs_labo.miauth_test.model

import org.json.JSONObject

data class MiInstance(
  val id: String,
  val caughtAt: DateString,
  val host: String,
  val usersCount: Long,
  val notesCount: Long,
  val followingCount: Long,
  val followersCount: Long,
  val driveUsage: Long,
  val driveFiles: Long,
  val latestRequestSentAt: DateString?,
  val latestStatus: Long?,
  val latestRequestReceivedAt: DateString?,
  val lastCommunicatedAt: DateString,
  val isNotResponding: Boolean,
  val isSuspended: Boolean,
  val softwareName: String?,
  val softwareVersion: String?,
  val openRegistrations: Boolean?,
  val name: String?,
  val description: String?,
  val maintainerName: String?,
  val maintainerEmail: String?,
  val iconUrl: String?,
  val faviconUrl: String?,
  val themeColor: String?,
  val infoUpdatedAt: DateString?
) {
  companion object {
    fun create(jsonObj: JSONObject): MiInstance {
      TODO()
    }
  }
}
