package city;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"city"})
@EnableLoadTimeWeaving
@EnableScheduling
//@EnableAsync
@PropertySource(value = "classpath:city.properties")
public class AppContext {

    @Value("${db.driver}")
    private String dbDriver;
    @Value("${db.url}")
    private String dbUrl;    
    @Value("${db.username}")
    private String dbUsername;    
    @Value("${db.password}")
    private String dbPassword;    

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager jtm = new JpaTransactionManager();
        jtm.setDataSource(this.ds());
        //jtm.setEntityManagerFactory(this.entityManagerFactory());
        jtm.setJpaDialect(new EclipseLinkJpaDialect());
        //jtm.setJpaDialect(new OpenJpaDialect());
        jtm.setPersistenceUnitName("cityPU");
        //jtm.
        return jtm;
    }

    /*@Bean(destroyMethod = "close")
    public DataSource ds() {
        BasicDataSource dsNodes = new BasicDataSource();
        dsNodes.setDriverClassName(dbDriver);
        dsNodes.setUrl(dbUrl);
        dsNodes.setPassword(dbPassword);
        dsNodes.setUsername(dbUsername);
        return dsNodes;
    }*/
    
    @Bean(destroyMethod = "close")
    public DataSource ds() {
        HikariDataSource dsNodes = new HikariDataSource();
        dsNodes.setDriverClassName(dbDriver);
        dsNodes.setJdbcUrl(dbUrl);
        dsNodes.setPassword(dbPassword);
        dsNodes.setUsername(dbUsername);
        return dsNodes;
    }     

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean eemf = new LocalContainerEntityManagerFactoryBean();
        eemf.setDataSource(this.ds());
        eemf.setJpaVendorAdapter(elJpaVendorAdapter());
        //eemf.setJpaVendorAdapter(ojJpaVendorAdapter());
        eemf.setPackagesToScan("city.jpa");
        eemf.setPersistenceUnitName("cityPU");
        //eemf.setLoadTimeWeaver(new SimpleLoadTimeWeaver()); //for OpenJPA
        //eemf.setJpaDialect(new OpenJpaDialect());
        eemf.setJpaDialect(new EclipseLinkJpaDialect());
        //nodesPU.afterPropertiesSet();
        return eemf;
    }

    @Bean
    public EclipseLinkJpaVendorAdapter elJpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.DERBY);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setDatabasePlatform("org.eclipse.persistence.platform.database.DerbyPlatform");
        return jpaVendorAdapter;
    }

}
