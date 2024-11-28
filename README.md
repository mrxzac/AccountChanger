# Account Changer
Account Changer is a spigot **server-side** plugin that allows players to **redirect** thier client account to **another** account in server **with GUI**, without quit the client or using any mods. **Please read Installation and Configurations before use.**
------------
### Installation
This plugin requires [ProtocolLib](https://github.com/dmulloy2/ProtocolLib "ProtocolLib"). Just simply put downloaded ProtocolLib and AccountChanger-x.x-xxxx.jar into **plugins folder** in the server directory. Jar file of this plugin can be found in the latest release.
##### Notice
For Servers which use authencation plugins when logging in, our plugin only support [Authme](https://www.spigotmc.org/resources/authmereloaded.6269/ "Authme") plugin, and require modification in its config.yml, modify ` UnrestrictedInventories` in your config.yml in Authme to use this plugin:
```yaml
.....
    # UnrestrictedInventories:
    # - 'myCustomInventory1'
    # - 'myCustomInventory2'
    UnrestrictedInventories: ["Choose Your Account"]
  security:
    # Minimum length of password
    minPasswordLength: 5
.....
```
------------
### Configuations and Usage
##### Configurations : config.yml
Configurations for this plugin is quite simple, it contains three parameters in `config.yml` in the `\Plugin\Account Changer` directory.
```yaml
Players:
  - ABC
MaxPlayers: 20
MaxWait: 10
```
`Players` are split by lines and added ` - `in front. These are the players who will **login without any account choosing steps** , and the player who is **not on the list** will be able to **choose an account from the list** to login.
`MaxPlayer` is an integer that indicates the maximum number of player accounts to choose, the window size **(inventory slots)** that will be shown in GUI.
`MaxWait` is an integer, indicating the **maximum time in seconds** that player who has chosen the account used to rejoin the server, if time used after choosing account longer then this, the choosing account will be expired and require re-choosing.
##### Usage

