import io.circe.generic.auto._
import sttp.client3._
import sttp.client3.circe.asJson

case class Data(data: Map[String, String])
case class VaultResponse(data: Data)

class VaultClient {
  private val backend = HttpURLConnectionBackend()
  private val vaultAddr = sys.env.getOrElse("VAULT_ADDR", "http://127.0.0.1:8200")
  private val vaultToken = sys.env.getOrElse("VAULT_TOKEN", "root")

  def getSecretFromVault(path: String, field: String): Option[String] = {
    val request = basicRequest
      .header("X-Vault-Token", vaultToken)
      .get(uri"$vaultAddr/v1/$path")
      .response(asJson[VaultResponse])

    val response = request.send(backend)

    response.body match {
      case Right(vaultResponse) => vaultResponse.data.data.get(field)
      case Left(error) =>
        println(s"Failed to decode response body: $error")
        None
    }
  }
}
