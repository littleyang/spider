package com.xiaoke.spider.qixinbao;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;

public class Pagert implements PageProcessor{
	
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(2000)
            .addHeader("Content-Type", "application/json")
            .addCookie("sid", "s%3AQopj2OiDpv9TCwBm4ypgk1sksybuEcXS.qsVdMu8DM4%2F6fZSJQfQ8dy3QgN3da3uV0WyexsZij00");
	
	private Downloader downloader;
	
	
	public Pagert(Downloader downloader) {
	        this.downloader = downloader;
	}
	
	

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		System.out.println("==========================主要人员=======================");
		List<String> mainStaffs = page.getHtml().xpath("//*[@id=\"info\"]/div/div[3]/div[2]/ul/li").all();
		for(int i=0;i<mainStaffs.size();i++){
	    	 Document doc = Jsoup.parseBodyFragment(mainStaffs.get(i));
	         Element body = doc.body();  
	         System.out.println(doc.select("span").first().text() + " : " + doc.select("span").last().text());
	    }
	       
	    // 股东信息
	    //System.out.println(page.getHtml().xpath("//*[@id=\"info\"]/div/div[2]/div[2]/table/tbody/tr").all());
		System.out.println("==========================股东信息=======================");	
	    List<String> sharers = page.getHtml().xpath("//*[@id=\"info\"]/div/div[2]/div[2]/table/tbody/tr").all();
	    	
	    for(int i=0;i<sharers.size();i++){
	    	Document doc = Jsoup.parseBodyFragment("<table>" + sharers.get(i) + "</table>");
	    	//System.out.println("==== " + "<table>" + sharers.get(i) + "</table>");
	        System.out.println(doc.getElementsByTag("td").first().text() + " : " + doc.select("span").last().text());
	    }
	       
	       
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
