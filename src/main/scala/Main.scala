import com.typesafe.config._

object Main {
  def main(args: Array[String]): Unit = {
    val client = new VaultClient()

    // Load only application.conf
    val configFile = new java.io.File("src/main/resources/application.conf")
    val rawConfig = ConfigFactory.parseFile(configFile)

    // Print the loaded configuration
    println("Loaded configuration:")
    println(rawConfig.root().render())

    val config = ConfigUpdater.updateConfigFromVault(rawConfig, client)

    // Print the updated configuration
    println("Updated configuration:")
    println(config.root().render())

//    // Save the updated configuration to a file
//    val file = File("application.conf")
//    file.overwrite(updatedConfig.root().render())
  }
}