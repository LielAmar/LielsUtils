# Liel's Utils

[![Discord](https://img.shields.io/discord/416652224505184276?color=%235865F2&label=Join%20My%20Discord)](https://discord.gg/NzgBrqR)

Liel's Utils is a Java Library, containing several techniques to make dealing with the language and with the Bukkit API easier.
This Library contains utilities for Arrays, Callbacks, Fetches, Castings, Groups, Math, Text, Time, UUIDs, Validations and Bukkit.

## Installation
You can either download the source code, compile and include it as a part of your project, or use the following:

### Maven
```xml
<repositories>
    <repository>
        <id>lielamar-repo</id>
        <url>https://repo.lielamar.com/repository/maven-public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.lielamar</groupId>
        <artifactId>LielsUtils</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```

### Gradle
```xml
repositories {
    lielamar-repo {
        url "https://repo.lielamar.com/repository/maven-public"
    }
}

dependencies {
    compile "com.lielamar:LielsUtils:${version}"
}
```

* Note that some functionalities require you to import & shadow [Gson](https://github.com/google/gson) and [PacketManager](https://github.com/LielAmar/PacketManager/)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://choosealicense.com/licenses/mit/)
