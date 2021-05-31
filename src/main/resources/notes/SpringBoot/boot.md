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