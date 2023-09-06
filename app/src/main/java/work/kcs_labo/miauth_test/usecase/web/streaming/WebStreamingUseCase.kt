package work.kcs_labo.miauth_test.usecase.web.streaming

sealed interface WebStreamingUseCase {
  interface MiStreamingUseCase: WebStreamingUseCase {
    fun connect()
    fun openUp()
    fun close()
    fun disconnect()
  }
  interface MstdnStreamingUseCase: WebStreamingUseCase {
    fun connect()
    fun openUp()
    fun close()
    fun disconnect()
  }
}