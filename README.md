# Account Changer
Account Changer is a spigot **server-side** plugin that allows players to **redirect/change** thier client account to **another** account in server **with GUI**, without quit the client or using any mods. **Please read Installation and Configurations before use.**
Spigot/Paper 1.20.1+ (1.20.1 & 1.21.3 Tested)

------------

### Usage
Gifs and demonstrations below are using config.yml in **Configuration** section.

##### With account ABC:
![ABC](https://github.com/user-attachments/assets/20f53043-cf54-49ec-bab3-dfd4bbe28a82)

##### With account BCD, changing to ABC:
![BCD](https://github.com/user-attachments/assets/247d70ff-aaf7-4247-8c15-601116bf84c1)

------------
### Installation
This plugin requires [ProtocolLib](https://github.com/dmulloy2/ProtocolLib "ProtocolLib"). Just simply put downloaded ProtocolLib and AccountChanger-x.x-xxxx.jar into **plugins folder** in the server directory. Jar file of this plugin can be found in the latest release.
#### Notice
For Servers which use authencation plugins when logging in, our plugin only support [Authme](https://www.spigotmc.org/resources/authmereloaded.6269/ "Authme") plugin, and require modification in its config.yml, modify `UnrestrictedInventories` in your config.yml in Authme to use this plugin:
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
#### Configurations : config.yml
Configurations for this plugin is quite simple, it contains three parameters in `config.yml` in the `\Plugin\AccountChanger` directory.
```yaml
Players:
  - ABC
MaxPlayers: 20
MaxWait: 10
```
`Players` are split by lines and added `  - ` in the front. These are the players who will **login without any account choosing steps** , and the player who is **not on the list** will be able to **choose an account from the list** to login.

`MaxPlayer` is an integer that indicates the maximum number of player accounts to choose, the window size **(inventory slots)** that will be shown in GUI.

`MaxWait` is an integer, indicating the **maximum time in seconds** that player who has chosen the account used to rejoin the server, if time used after choosing account longer then this, the choosing account will be expired and require re-choosing.

This repository is under GNU General Public License v3.0.
