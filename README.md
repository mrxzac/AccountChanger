![icon](https://github.com/user-attachments/assets/bc4a21ac-896c-4227-a96a-79f3b531b641)
# **Account Changer**

**Account Changer** is a powerful Spigot server-side plugin designed to enhance player convenience. It allows users to seamlessly switch their Minecraft accounts *in-game* via an intuitive GUI, eliminating the need to disconnect or use any client-side modifications. This plugin is particularly useful for managing accounts on offline servers.


## **Key Features**
- **In-Game Account Switching**: Change accounts directly from the server using a graphical interface.
- **No Client Modifications Required**: Fully server-side, ensuring ease of use and compatibility.
- **Requirements**:  
  - [**ProtocolLib**](https://github.com/dmulloy2/ProtocolLib)  
  - **Spigot/Paper 1.20.1+** (tested on **1.20.1** and **1.21.3**)


## **Getting Started**
Before using the plugin, ensure you’ve read the **Installation** and **Configuration** sections for proper setup.

### **1. Commands**
- `/ac reload`: Reloads the plugin configuration files.  
- `/ac clear`: Clears the command sender's or a specified player's status and alias.  
- `/ac switch`: Opens the GUI for account switching.  
- `/ac help`: Displays help for plugin commands.

### **2. Logic**
- Accounts **not listed** in the configuration require a user to select an alias account during login.  
- Once chosen, an alias account persists until the user switches accounts via `/ac switch` or resets the alias with `/ac clear`.  
- Accounts **in the list** can also switch accounts without restrictions.  
- No time limit exists after choosing an account.


## **Demo**
Below are examples of the plugin in action using settings:
```
Players:  
  - ABC
  - DEF
MaxPlayers: 20  
MaxWait: 10
```

### **With listed account ABC, switching to DEF:**
![ABCDEF](https://github.com/user-attachments/assets/1cb0bfa5-07b9-4755-b382-884ee72d0393)

### **With switched account DEF, switching back to ABC:**
![DEFABC](https://github.com/user-attachments/assets/6e52acfc-4e34-4ed3-b4b8-5abaa694dd2b)

### **With unlisted account BCD, switching to DEF:**
![BCDDEF](https://github.com/user-attachments/assets/8b3d69f7-6e55-4c29-84bc-308d6aac56e5)


### **Note:**  
The plugin supports Bedrock-Java hybrid servers, making it ideal for servers using Geyser where Bedrock players cannot change their names.


## **Installation**
This plugin requires [ProtocolLib](https://github.com/dmulloy2/ProtocolLib). Follow these steps to set it up:

1. Download **ProtocolLib** and place it in the `plugins` folder of your server directory.
2. Download `AccountChanger-x.x-xxxx.jar` from the [latest release](https://github.com/mrxzac/AccountChanger/releases/latest) and place it in the same folder.
3. Restart your server to load the plugins.

### **Notice:**
For servers using authentication plugins (e.g., AuthMe), ensure compatibility by updating the following configurations in `config.yml`:
```
UnrestrictedInventories: ["Choose Your Account"] 
```
Add these commands to the `allowCommands` section:  
```
allowCommands:  
  - /ac  
  - /accountchanger  
```

## **Configuration**
The configuration file, `config.yml`, is located in the `plugins/AccountChanger` directory. Customize the following parameters:
```
Players:  
  - ABC  
MaxPlayers: 20  
MaxWait: 10
```
### **Parameters:**
1. **`Players`**  
   - Lists players who bypass the account selection process.  
   - Non-listed players must select an account during login.

2. **`MaxPlayers`**  
   - Sets the maximum number of player accounts available for selection.  
   - Adjusts the GUI's inventory slots accordingly.

3. **`MaxWait`**  
   - *(Deprecated)*


## **Permissions**
- `accountchanger.base`: Allows use of `/ac` (enabled for all players by default).  
- `accountchanger.reload`: Grants permission to reload configurations (`/ac reload`, default for OP).  
- `accountchanger.switch`: Enables account switching (`/ac switch`, enabled for all players by default).  
- `accountchanger.clear`: Allows resetting account alias for oneself (`/ac clear`, enabled for all players).  
- `accountchanger.clear.all`: Allows resetting account aliases for others (`/ac clear`, default for OP).


## **Acknowledgments**
Special thanks to [ProtocolLib](https://github.com/dmulloy2/ProtocolLib) by **dmulloy2** for making this plugin possible.


## **License**
This repository is licensed under the **GNU General Public License v3.0**.  
For more details, see the [LICENSE](LICENSE) file.

