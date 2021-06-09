# 配置里获取maven的properties
spring.profiles.active=@profiles.active@
# 自定义starter
+ springboot依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```
+ 配置类 使用好各种Conditional注解
```
/**
 * 自动配置类
 *
 * @author KIyA
 * @date 2021-05-31
 */
@Configuration
public class CleiAutoConfiguration {

    /**
     * 只在web应用才用这个bean
     *
     * @return KIyA
     */
    @Bean
    @ConditionalOnWebApplication
    public Person person() {
        LocalDateTime birthday = LocalDateTime.of(1995, 10, 3, 0, 0);
        LocalDateTime now = LocalDateTime.now();
        int age = (int) ChronoUnit.YEARS.between(birthday, now);
        return new Person("KIyA", age, 1);
    }
}
```
+ META-INF/spring.factories
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.clei.config.CleiAutoConfiguration
```
+ 使用
```
<!-- 自定义starter -->
<dependency>
    <groupId>com.clei</groupId>
    <artifactId>clei-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
# Application Event
###### 前面9个 only includes SpringApplicationEvents that are tied to a SpringApplication. 
###### In addition to these, 后面两个 are also published after ApplicationPreparedEvent 
###### and before ApplicationStartedEvent.
| Event | Description |
| --- | --- |
| ApplicationStartingEvent | An ApplicationStartingEvent is sent at the start of a run but before any processing, except for the registration of listeners and initializers. |
| ApplicationEnvironmentPreparedEvent | An ApplicationEnvironmentPreparedEvent is sent when the Environment to be used in the context is known but before the context is created. |
| ApplicationContextInitializedEvent | An ApplicationContextInitializedEvent is sent when the ApplicationContext is prepared and ApplicationContextInitializers have been called but before any bean definitions are loaded. |
| ApplicationPreparedEvent | An ApplicationPreparedEvent is sent just before the refresh is started but after bean definitions have been loaded. |
| ApplicationStartedEvent | An ApplicationStartedEvent is sent after the context has been refreshed but before any application and command-line runners have been called. |
| AvailabilityChangeEvent | An AvailabilityChangeEvent is sent right after with LivenessState.CORRECT to indicate that the application is considered as live. |
| ApplicationReadyEvent | An ApplicationReadyEvent is sent after any application and command-line runners have been called. |
| AvailabilityChangeEvent | An AvailabilityChangeEvent is sent right after with ReadinessState.ACCEPTING_TRAFFIC to indicate that the application is ready to service requests. |
| ApplicationFailedEvent | An ApplicationFailedEvent is sent if there is an exception on startup. |
| WebServerInitializedEvent | A WebServerInitializedEvent is sent after the WebServer is ready. ServletWebServerInitializedEvent and ReactiveWebServerInitializedEvent are the servlet and reactive variants respectively. |
| ContextRefreshedEvent | A ContextRefreshedEvent is sent when an ApplicationContext is refreshed. |

# Property Source
###### Order越大优先级越高
| Order | Source |
| --- | --- |
| 1 | Default properties (specified by setting SpringApplication.setDefaultProperties). |
| 2 | @PropertySource annotations on your @Configuration classes. Please note that such property sources are not added to the Environment until the application context is being refreshed. This is too late to configure certain properties such as logging.* and spring.main.* which are read before refresh begins. |
| 3 | Config data (such as application.properties files) |
| 4 | A RandomValuePropertySource that has properties only in random.*. |
| 5 | OS environment variables. |
| 6 | Java System properties (System.getProperties()). |
| 7 | JNDI attributes from java:comp/env. |
| 8 | ServletContext init parameters. |
| 9 | ServletConfig init parameters. |
| 10 | Properties from SPRING_APPLICATION_JSON (inline JSON embedded in an environment variable or system property). |
| 11 | Command line arguments. |
| 12 | properties attribute on your tests. Available on @SpringBootTest and the test annotations for testing a particular slice of your application. |
| 13 | @TestPropertySource annotations on your tests. |
| 14 | Devtools global settings properties in the $HOME/.config/spring-boot directory when devtools is active. |

# Property Source
###### Order较大的会覆盖之前设置的值
| Order | Source |
| --- | --- |
| 1 | Default properties (specified by setting SpringApplication.setDefaultProperties). |
| 2 | @PropertySource annotations on your @Configuration classes. Please note that such property sources are not added to the Environment until the application context is being refreshed. This is too late to configure certain properties such as logging.* and spring.main.* which are read before refresh begins. |
| 3 | Config data (such as application.properties files) |
| 4 | A RandomValuePropertySource that has properties only in random.*. |
| 5 | OS environment variables. |
| 6 | Java System properties (System.getProperties()). |
| 7 | JNDI attributes from java:comp/env. |
| 8 | ServletContext init parameters. |
| 9 | ServletConfig init parameters. |
| 10 | Properties from SPRING_APPLICATION_JSON (inline JSON embedded in an environment variable or system property). |
| 11 | Command line arguments. |
| 12 | properties attribute on your tests. Available on @SpringBootTest and the test annotations for testing a particular slice of your application. |
| 13 | @TestPropertySource annotations on your tests. |
| 14 | Devtools global settings properties in the $HOME/.config/spring-boot directory when devtools is active. |

# Config Load Order [application.properties & application.yml]
###### Order较大的会覆盖之前设置的值
| Order | Config |
| --- | --- |
| 1 | Application properties packaged inside your jar (application.properties and YAML variants). |
| 2 | Profile-specific application properties packaged inside your jar (application-{profile}.properties and YAML variants). |
| 3 | Application properties outside of your packaged jar (application.properties and YAML variants). |
| 4 | Profile-specific application properties outside of your packaged jar (application-{profile}.properties and YAML variants). |

# Config Order [application.properties & application.yml]
###### Order较大的会覆盖之前设置的值
| Order | Config |
| --- | --- |
| 1 | The classpath root |
| 2 | The classpath /config package |
| 3 | The current directory |
| 4 | The /config subdirectory in the current directory |
| 5 | Immediate child directories of the /config subdirectory (/config的直接子目录) |

# 配置相关参数
| Key | Value Example |
| --- | --- |
| spring.config.name | UserConfig |
| spring.config.location | optional:classpath:/UserConfig/,optional:file:./UserConfig/ |
| spring.config.additional-location | optional:classpath:/UserConfig/,optional:file:./UserConfig/ |

# Logging
```
logging:
  # 一个组定义多个包
  # spring默认定义了的组有，web，sql等
  group:
    tomcat: org.apache.catalina,org.apache.coyote,org.apache.tomcat
  level:
    tomecat: trace
    org.springframework.web: debug
    org.hibernate: error
```