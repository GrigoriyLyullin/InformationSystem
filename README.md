# Information System for abstract railway company.
Java School course in T-Systems RUS.

## Getting Started

### Build application

Prerequisites:
- Maven has been installed

1. Go to pulled sorces directory
2. Execute "mvn clean package"
3. The command returns "BUILD SUCCESS". Resulting InformationSystem.war will be located in target diretory.


### Install WildFly, configure it and then application deploy

Prerequisites:
- MySQL installed and configured
- Application build

1. Download WildFly "Java EE7 Full & Web Distribution" here: http://wildfly.org/downloads/ (12.0.0.Final)
2. Unpack it somewhere (e.g. C:\wildfly-12.0.0.Final)
3. Go to bin directory (e.g.  C:\wildfly-12.0.0.Final\bin)
4. Add administrator user (with ManagementRealm group). Execute add-user.bat and follow the instructions. E.g.:

```
    What type of user do you wish to add?
     a) Management User (mgmt-users.properties)
     b) Application User (application-users.properties)
    (a): a
    Enter the details of the new user to add.
    Using realm 'ManagementRealm' as discovered from the existing property files.
    Username : administrator
    The username 'administrator' is easy to guess
    Are you sure you want to add user 'administrator' yes/no? yes
    Password recommendations are listed below. To modify these restrictions edit the add-user.properties configuration file.
     - The password should be different from the username
     - The password should not be one of the following restricted values {root, admin, administrator}
     - The password should contain at least 8 characters, 1 alphabetic character(s), 1 digit(s), 1 non-alphanumeric symbol(s)
    Password :
    Re-enter Password :
    What groups do you want this user to belong to? (Please enter a comma separated list, or leave blank for none)[  ]: ManagementRealm
    About to add user 'administrator' for realm 'ManagementRealm'
    Is this correct yes/no? yes
    Added user 'administrator' to file 'C:\wildfly-12.0.0.Final\standalone\configuration\mgmt-users.properties'
    Added user 'administrator' to file 'C:\wildfly-12.0.0.Final\domain\configuration\mgmt-users.properties'
    Added user 'administrator' with groups ManagementRealm to file 'C:\wildfly-12.0.0.Final\standalone\configuration\mgmt-groups.properties'
    Added user 'administrator' with groups ManagementRealm to file 'C:\wildfly-12.0.0.Final\domain\configuration\mgmt-groups.properties'
    Is this new user going to be used for one AS process to connect to another AS process?
    e.g. for a slave host controller connecting to the master or for a Remoting connection for the server to server EJB calls.
    yes/no? no
    Press any key to continue . . .
```

5. Start WildFly via standalone.bat. E.g. C:\wildfly-12.0.0.Final\bin\standalone.bat

```
    INFO  [org.jboss.as] (Controller Boot Thread) WFLYSRV0060: Http management interface listening on http://127.0.0.1:9990/management
    INFO  [org.jboss.as] (Controller Boot Thread) WFLYSRV0051: Admin console listening on http://127.0.0.1:9990
    INFO  [org.jboss.as] (Controller Boot Thread) WFLYSRV0025: WildFly Full 12.0.0.Final (WildFly Core 4.0.0.Final) started in 6510ms - Started 292 of 513 services (308 services are lazy, passive or on-demand)
```

6. Open http://127.0.0.1:9990 in browser, input username\password from step 4. WildFly administrator console should appear. Stop WildFly.

7. Install mysql driver.

7.1. Go to WILDFLY_HOME/modules/system/layers/base/com and create the folder mysql/main;
7.2. Visit the page [http://dev.mysql.com/downloads/connector/j/](http://dev.mysql.com/downloads/connector/j/) and download MySQL’s JDBC Driver;
7.3. Unzip the downloaded file and copy the file mysql-connector-java-5.1.23-bin.jar to the new folder WILDFLY_HOME/modules/system/layers/base/com/mysql/main
7.4. Create the file module.xml in the same folder with the following content:

    ```
        <?xml version="1.0" encoding="UTF-8"?>
        <module xmlns="urn:jboss:module:1.1" name="com.mysql">
            <resources>
                <resource-root path="mysql-connector-java-5.1.23-bin.jar"/>
            </resources>
            <dependencies>
                <module name="javax.api"/>
                <module name="javax.transaction.api"/>
            </dependencies>
        </module>
    ```

7.5. Start WildFly via "standalone.bat". Execute "jboss-cli.bat --connect" to connect to WildFly via command-line interface.
The prompt looks like [standalone@localhost:9990 /], indicating it is ready to accept admin commands.
7.6. Execute "/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql, driver-module-name=com.mysql, driver-class-name=com.mysql.jdbc.Driver)":

    ```
        [standalone@localhost:9990 /] /subsystem=datasources/jdbc-driver=mysql:add( driver-name=mysql, driver-module-name=com.mysql, driver-class-name=com.mysql.jdbc.Driver )
        {"outcome" => "success"}
    ```

    The command returns {"outcome" ⇒ "success"} in case of success.
    For more detailes see: http://wildfly.org/news/2014/02/06/GlassFish-to-WildFly-migration/

8. Create a Datasource.

    ```
    /subsystem=datasources/data-source=RailwayInformationSystem:add(
          driver-name=mysql,
          user-name=<db_username>,
          password=<db_password>,
          connection-url="jdbc:mysql://localhost:3306/RailwayInformationSystem?useSSL=false",
          min-pool-size=5,
          max-pool-size=15,
          jndi-name=java:/jdbc/mysql,
          enabled=true,
          jta=true,
          validate-on-match=true,
          valid-connection-checker-class-name=org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker,
          exception-sorter-class-name=org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter
    )
    ```
    The command returns {"outcome" ⇒ "success"} in case of success.

9. Check standalone.xml (C:\wildfly-12.0.0.Final\standalone\configuration\standalone.xml").

    ```
    <datasource jta="true" jndi-name="java:/jdbc/mysql" pool-name="RailwayInformationSystem" enabled="true">
        <connection-url>jdbc:mysql://localhost:3306/RailwayInformationSystem?useSSL=false</connection-url>
                                                       
        <driver>mysql</driver>
        <pool>
            <min-pool-size>5</min-pool-size>
            <max-pool-size>15</max-pool-size>
        </pool>
        <security>
            <user-name><db_username></user-name>
            <password><db_password></password>
        </security>
        <validation>
            <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker"/>
            <validate-on-match>true</validate-on-match>
            <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter"/>
        </validation>
    </datasource>
    ```

9. Deploy application:
[standalone@localhost:9990 /] deploy <path to the project directory>\InformationSystem\target\InformationSystem.war

standalone.bat should reported something similar:
[org.jboss.as.server] (management-handler-thread - 1) WFLYSRV0010: Deployed "InformationSystem.war" (runtime-name : "InformationSystem.war")

For more detailes see: https://docs.jboss.org/author/display/WFLY10/Application+deployment

10. Open "http://localhost:8080/InformationSystem/index" in the browser. The index page of this application should appear.