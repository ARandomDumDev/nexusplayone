# RandomMod

RandomMod is a server-side Fabric moderation mod for Minecraft `26.1.2`. It adds `/randommod`, a chest-based sgui moderation interface for staff.

## Requirements

- Minecraft `26.1.2`
- Fabric Loader `0.18.4` or newer compatible loader for Minecraft `26.1.2`
- Fabric API `0.153.0+26.1.2` or newer compatible Fabric API for Minecraft `26.1.2`
- sgui `2.0.0+26.1`
- Gradle
- Java 21 toolchain as configured by this project

Fabric's 26.1 documentation states that Minecraft 26.1 development environments may require a newer Gradle JVM than Java 21. This project honors the requested Java 21 source/target configuration, but your local Fabric/Loom toolchain may require a newer JVM to resolve and run the build.

## Usage

Run `/randommod` as a player with `randommod.admin` or operator level 2. The command opens the RandomMod main GUI.

### Permissions

- `randommod.admin`
- `randommod.ban`
- `randommod.kick`
- `randommod.mute`
- `randommod.warn`
- `randommod.settings`

If a permissions provider is unavailable, Fabric Permissions API falls back to operator level 2.

## Features

- Modern 6-row chest GUI using `eu.pb4.sgui`
- Gray stained glass filler panes
- Symmetrical main menu layout
- Button click sounds
- Custom item names and lore
- Back and close buttons
- Inventory interaction prevention through sgui server-side screens
- Online-player ban menu with player heads
- Confirmation screen with green confirm and red cancel buttons
- Built-in Minecraft ban list integration
- Broadcast message: `<player> has been banned by <admin>.`
- Moderation action log at `logs/randommod-actions.log`
- JSON configuration at `config/randommod.json`

## Building

```bash
gradle build
```

If dependency resolution fails with HTTP 403 in restricted CI/sandbox environments, rerun in an environment that can access Fabric Maven, Maven Central, Gradle Plugin Portal, and Nucleoid Maven.

## Configuration

RandomMod creates `config/randommod.json` on first startup:

```json
{
  "banReason": "Banned by staff through RandomMod",
  "kickReason": "Kicked by staff through RandomMod",
  "muteMessage": "You have been muted by staff.",
  "broadcastModerationActions": true,
  "requirePermissions": true,
  "logFile": "randommod-actions.log"
}
```

## License

MIT. See [LICENSE](LICENSE).
