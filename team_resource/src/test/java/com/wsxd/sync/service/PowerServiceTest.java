package com.wsxd.sync.service;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午2:21:28
 */

@RunWith(SpringJUnit4ClassRunner.class) // 使用junit4进行测试
@WebAppConfiguration
@Transactional
@ContextConfiguration({ "classpath*:/spring-context*.xml" }) // 加载配置文件
public class PowerServiceTest {

	@Autowired
	private PowerService powerService;

	@Test
	public void testExists() {
		try {
			powerService.exists("1aaa");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		PowerServiceTest test = new PowerServiceTest();
		test.testExists();
	}

}
