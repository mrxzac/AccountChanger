# Account Changer

**Account Changer** is a powerful Spigot server-side plugin designed to enhance player convenience. It allows users to seamlessly switch their Minecraft accounts *in-game* via an intuitive GUI, eliminating the need to disconnect or use any client-side modifications. This plugin is useful for accounts management in offline servers, since it is designed originally for offline servers.

### Key Features
- **In-Game Account Switching**: Change accounts directly from the server using a graphical interface.
- **No Client Modifications Required**: Fully server-side, ensuring ease of use and compatibility.
- **Requirements**: [**ProtocolLib**](https://github.com/dmulloy2/ProtocolLib "ProtocolLib"), **Spigot/Paper 1.20.1+**(tested for **1.20.1** and **1.21.3**).

### Getting Started
Before using the plugin, ensure you’ve read the [Installation](#installation) and [Configuration](#configuration) guides for proper setup. 

Use `/acreload` to reload the configs.

Unlock a new level of flexibility for your server players today with **Account Changer**!





## Demo
The GIFs and demonstrations below showcase the functionality of the plugin using the `config.yml` settings provided in the **Configuration** section.

##### With account ABC:
![ABC](https://github.com/user-attachments/assets/20f53043-cf54-49ec-bab3-dfd4bbe28a82)

##### With account BCD, changing to ABC:
![BCD](https://github.com/user-attachments/assets/247d70ff-aaf7-4247-8c15-601116bf84c1)

Originally, the plugin is designed for bedrock-java both sided server, as bedrock players cannot change their names. If you are also using geyser plugin, try this out!


## Installation

This plugin requires [ProtocolLib](https://github.com/dmulloy2/ProtocolLib "ProtocolLib"). To set it up:

1. Download **ProtocolLib** and place it in the `plugins` folder of your server directory.
2. Download `AccountChanger-x.x-xxxx.jar` from the [latest release](https://github.com/mrxzac/AccountChanger/releases/latest "latest release") and place it in the same `plugins` folder.
3. Restart your server to load the plugins.

#### Notice
For servers using authentication plugins during login, this plugin only supports [AuthMe](https://www.spigotmc.org/resources/authmereloaded.6269/ "AuthMe"). You will need to modify `config.yml` in **AuthMe** to ensure compatibility:

- Update the `UnrestrictedInventories` section in your `config.yml` to use this plugin:

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



## Configuration

Configuring this plugin is straightforward. The configuration file, `config.yml`, is located in the `\Plugin\AccountChanger` directory. It contains three main parameters for customization.  

```yaml
Players:
  - ABC
MaxPlayers: 20
MaxWait: 10
```
#### Configuration Parameters

1. **`Players`**  
   - This section contains a list of players, each entry starting with `-`.  
   - Players listed here will **log in directly** without going through any account selection steps.  
   - Players **not listed** will be presented with a GUI to **choose an account** from the available options.

2. **`MaxPlayer`**  
   - An integer that sets the **maximum number of player accounts** available to choose from.  
   - Determines the **GUI window size** (number of inventory slots).

3. **`MaxWait`**  
   - An integer that defines the **maximum time (in seconds)** a player has to rejoin the server after selecting an account.  
   - If the player takes longer than this time, the selected account will expire, and the player will need to select an account again.



## Acknowledgments  
Special thanks to [ProtocolLib](https://github.com/dmulloy2/ProtocolLib) by **dmulloy2** for making this plugin possible.



## License  
This repository is licensed under the **GNU General Public License v3.0**.  
For more details, see the [LICENSE](LICENSE) file.

