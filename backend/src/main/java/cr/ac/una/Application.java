package cr.ac.una;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)//para decirle a Spring que utilice AOP hay que decirle que utilice
                                                // un proxy, asi! Se tiene que agregar la dependencia en el pom.xml

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


    }

}
