package com.scjinruanpoliceofficer.deill.dao;

import java.io.File;
import java.net.URL;
import org.junit.Test;

import com.scjinruan.policeofficer.deill.dao.impl.C3P0DSImpl;


public class DataSourceFactoryTest {
	@Test
	public void testGetC3P0Datasource() throws Exception{
		DataSourceFactory factory=new C3P0DSImpl(
				"com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/test", "root", "root");
		File file=new File(DataSourceFactory.class.getResource("/").getFile());
		File pf=new File(file.getParent()+"/classes/c3p0.properties");
		factory.setPoolConfig(pf);
		System.out.println(factory.getDataSource().getConnection());
		//factory.destory();
	}
	@Test
	public void testFilePath(){
		URL url=DataSourceFactoryTest.class.getResource("/");
		File file=new File(url.getFile());
		System.out.println(file.getAbsolutePath());
		String[] files=file.list();
		for(String f:files){
			System.out.println(f);
		}
		System.out.println(System.getProperty("user.dir"));
	}
}
