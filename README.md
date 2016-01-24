# cybergarage-x3d

cybergarage-x3d is a development package for X3D/VRML and Java programmers. Using the package, you can easily read and write the X3D/VRML files, set and get the scene graph information, draw the geometries, run the behaviors easily.

## Repositories

The project is hosted on SourceForge.net as the following.

- [Programming Guide](http://www.cybergarage.org:8080/pdfdoc/cx3djavaproguide.pdf)
- [GitHub](https://github.com/cybergarage/CyberX3D4Java)
- [Doxygen](http://www.cybergarage.org:8080/doxygen/cx3d4java/)
- [Maven](http://www.cybergarage.org:8080/maven/repo/org/cybergarage/x3d/)

## Using cybergarage-x3d in your project

### Maven

I maintain a my personal repository of Maven, [Cyber Garage Maven Repository](http://www.cybergarage.org:8080/maven/repo/), for my Java projects, and cybergarage-x3d is included in the repository too.

To use cybergarage-x3d in your project for Maven, add the following elements for the personal repository into your Maven project setting.

```
<project>
  ...
  <repositories>
　  ...
    <repository>
      <id>org.cybergarage.x3d</id>
      <url>http://repo.cybergarage.org:8080/maven/repo/</url>
    </repository>
    ...
  </repositories>
  ...
    <dependencies>
      ...
      <dependency>
          <groupId>org.cybergarage.x3d</groupId>
          <artifactId>cybergarage-x3d-core</artifactId>
          <version>[1.4.0,)</version>
      </dependency>
      ...
  </dependencies>
  ...
</project>
```

