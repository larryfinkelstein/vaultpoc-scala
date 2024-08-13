import com.typesafe.config.{Config, ConfigValueFactory}

import scala.jdk.CollectionConverters.CollectionHasAsScala

object ConfigUpdater {
  def updateConfigFromVault(config: Config, client: VaultClient): Config = {
    val updatedConfig = config.entrySet().asScala.foldLeft(config) { (updatedConfig, entry) =>
      val key = entry.getKey
      val value = entry.getValue.unwrapped().toString

      if (value.startsWith("vault:")) {
        val Array(secretPath, secretField) = value.stripPrefix("vault:").split("#")
        val secret = client.getSecretFromVault(secretPath, secretField)

        secret match {
          case Some(secretValue) =>
            updatedConfig.withValue(key, ConfigValueFactory.fromAnyRef(secretValue))
          case None =>
            updatedConfig
        }
      } else {
        updatedConfig
      }
    }

    updatedConfig
  }
}