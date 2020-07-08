package src.orm.config;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import src.orm.until.SqlHelper;

import javax.sql.DataSource;
import java.util.Properties;

/*
@author: tom.cui
@date 2020/4/16
@description: 
*/

@Configuration
public class OrmConfig {

    @Value("${api.basePacks}")
    private String basePacks;


    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcTemplate getJdbcTemplate() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public SqlHelper getSqlHelper() {
        SqlHelper sqlHelper = new SqlHelper();
        return sqlHelper;
    }

    @Bean
    public PageHelper getPageHelper() {
        Properties properties = new Properties();
        properties.setProperty("dialect","Mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        PageHelper pageHelper = new PageHelper();
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    @Bean
    public Docket createRestApi(){
        return  new Docket(DocumentationType.SWAGGER_2)
                //文档基础设置信息
                .apiInfo(apiInfo())
                .select()
                //要扫描的包
                .apis(RequestHandlerSelectors.basePackage(basePacks))
                //路径显示规则any全部显示，可以选择正则方式来匹配自己想要显示的接口
                .paths(PathSelectors.any())

                .build();

    }
    private ApiInfo apiInfo(){
        return  new ApiInfoBuilder()
                //文档标题会展示在文档最上方加大加粗显示
                .title("接口文档")
                //会显示在标题下的一段描述
                .description("ResultFul风格接口文档")
                //文档版本号
                .version("1.0")
                .build();
    }
}
