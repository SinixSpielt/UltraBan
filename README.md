# UltraBan


UUID
MYSQL
1.8 - 1.13 Support
Full configurable

[SPOILER="Database"]
[code]CONIG:
  BANSYSTEM:
    PREFIX: '&8[&cUltraBan&8]'
  BAN:
    USEPERMISSION: ultraban.ban
    BYPASSPERMISSION: ultraban.ban.bypass
  KICK:
    USEPERMISSION: ultraban.kick
    BYPASSPERMISSION: ultraban.kick.bypass[/code]
[/SPOILER]
[SPOILER="Messages"]
[code]CONIG:
  BANSYSTEM:
    NOPERMISSION: '%PREFIX% &4Keine Berechtigung!'
    NOMINECRAFTACCOUNT: '%PREFIX% &cEs existiert kein Minecraftaccount mit diesem
      Namen!'
    NOTONLINE: '%PREFIX% &cDieser Spieler ist nicht Online!'
    NOTBANNED: '%PREFIX% &cDieser Spieler ist nicht gebannt!'
    ISBANNED: '%PREFIX% &cDieser Spieler ist bereits gebannt!'
    BANPLAYER:
      BANSCREEN:
      - '&8-= &eServername &8=-'
      - '%NewLine% %NewLine%'
      - '&7Du wudrest vom Server gebannt!'
      - '%NewLine% %NewLine%'
      - '&7Grund: %REASON%'
      - '%NewLine% %NewLine%'
      - '&8-= &eServername &8=-'
      BANMESSAGE: '%PREFIX% &7Du hast &6%PLAYER% &7wegen &e%REASON% &7gebannt!'
      PLAYERBYPASS: '%PREFIX% &cDieser Spieler kann nicht gebannt werden!'
    KICKPLAYER:
      KICKSCREEN:
      - '&8-= &eServername &8=-'
      - '%NewLine% %NewLine%'
      - '&7Du wudrest vom Server gekickt!'
      - '%NewLine% %NewLine%'
      - '&7Grund: %REASON%'
      - '%NewLine% %NewLine%'
      - '&8-= &eServername &8=-'
      KICKMESSAGE: '%PREFIX% &7Du hast &6%PLAYER% &7wegen &e%REASON% &7gekickt!'
      PLAYERBYPASS: '%PREFIX% &cDieser Spieler kann nicht gekickt werden!'
    UNBANPLAYER: '%PREFIX% &7Du hast &e%PLAYER% &7entbannt!'
  WRONGCOMMAND:
    BAN: '%PREFIX% &8» &7Verwendung &e/ban <Name> <Grund>'
    UNBAN: '%PREFIX% &8» &7Verwendung &e/unban <Name>'
    KICK: '%PREFIX% &8» &7Verwendung &e/kick <Name>'
[/code]
[/SPOILER]
[SPOILER="Database"]
[code]DATABASE:
  HOST: localhost
  PORT: '3306'
  USER: username
  PASSWORD: password
  DATABASE: dbname
[/code]
[/SPOILER]


configurable in Config.yml

/ban <Player>
/unban <Player>
/kick <Player>

Discord <Klick>
GitHub <Klick>




















