package com.xiaoke.spider.qixinbao;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

public class MainTest {
	
	
	public static void main(String[] args){
		
		List<Site> sites = new ArrayList<>();
        Site site1 = Site.me().setRetryTimes(3).setSleepTime(2000)
                .addHeader("Content-Type", "application/json")
                .addCookie("sid", "s%3AHcSHsSS6CYXUwDTFRBmJFMzgLtCUFZMW.zYnE5XwhRueaclRROQIcC%2FY7mGtvoZnShdCiLBlr6ZE");
        Site site2 = Site.me().setRetryTimes(3).setSleepTime(2000)
                .addHeader("Content-Type", "application/json")
                .addCookie("sid", "s%3AbItV0jAZfPr2XyBcKqPyHbJPSKibcovr.0O1XPnJ10OI%2Fc8uq3yD5X6Sjpufo%2BlGUdebbkPG%2FJww");
        sites.add(site1);
        sites.add(site2);
        
        HttpClientDownloaderPool httpClientDownloaderPool = new HttpClientDownloaderPool(sites);

        Spider.create(new Pagert(httpClientDownloaderPool)).addUrl("http://www.qixin.com/company/534472fd-7d53-4958-8132-d6a6242423d8#/info").thread(5).run();
        
	}
	

}
