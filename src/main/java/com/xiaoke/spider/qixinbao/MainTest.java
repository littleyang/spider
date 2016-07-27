package com.xiaoke.spider.qixinbao;

import us.codecraft.webmagic.Spider;

public class MainTest {
	
	
	public static void main(String[] args){
		
		Spider.create(new GithubRepoPageProcessor())
        //从https://github.com/code4craft开始抓    
        .addUrl("https://github.com/code4craft")
        //开启5个线程同时执行
        .thread(5)
        //启动爬虫
        .run();
	}
	

}
