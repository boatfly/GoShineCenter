package com.goshine.gscenter;

import com.github.pagehelper.PageHelper;
import com.goshine.gscenter.common.kafka.KafkaSender;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Properties;

@SpringBootApplication
@MapperScan("com.goshine.gscenter.common.mybatis.mapper")
@EnableScheduling
public class WebBaseApplication {
	private static Logger LOGGER = LoggerFactory.getLogger(WebBaseApplication.class);

	@Autowired
	private KafkaSender kafkaSender;

	public static void main(String[] args) {
		LOGGER.info(".....initial.");
		SpringApplication.run(WebBaseApplication.class, args);
	}



	//配置mybatis的分页插件pageHelper
	@Bean
	public PageHelper pageHelper(){
		 PageHelper pageHelper = new PageHelper();
		 Properties properties = new Properties();
		 properties.setProperty("offsetAsPageNum","true");
		 properties.setProperty("rowBoundsWithCount","true");
		 properties.setProperty("reasonable","true");
		 properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
		 pageHelper.setProperties(properties);
		 return pageHelper;
	}

	@Scheduled(fixedDelay = 20000)
	public void testKafka() throws Exception {
		LOGGER.info(".....initial kafka sender.");
		//kafkaSender.send();
	}
}
