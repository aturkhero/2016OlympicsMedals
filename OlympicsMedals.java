package OlympicsMedalstest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Label;
import java.util.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OlympicsMedals {
	
	
	WebDriver driver;
	
	
	@BeforeClass
	void setup() {
		
		WebDriverManager.chromedriver().setup();
		
		driver=new ChromeDriver();
		
		
	}
	
	
	
	
	
	//@Ignore
	@Test(priority=1)
	void test1() {
		
		
	driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
	
	
	List<WebElement> list=driver.findElements(By.xpath("//*[@id=\"mw-content-text\"]/div/table[8]/tbody/tr/td[1]"));
	
	boolean sorted=true;
	
	for(int i=0;i<list.size()-2;i++) {
	
		if(Integer.parseInt(list.get(i).getText())<Integer.parseInt(list.get(i+1).getText())) {
			//do nothing
		}else {
			sorted=false;
		}
	}
	
	
	assertTrue(sorted); //check that rank is sorted 
	
	WebElement noc=driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th[2]"));
	
	noc.click();
	
	
	assertEquals(noc.getAttribute("title"), "Sort descending"); // check that sorted by NOC
	
	
	WebElement rank=driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div/table[8]/thead/tr/th[1]"));
	
	
	assertEquals(rank.getAttribute("title"),"Sort ascending"); // check that rank is not sorted
	
	}
	
	
	//@Ignore
	@Test(priority=2)
	void Test2() throws InterruptedException {
		
				
		driver.navigate().to("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		
		List<WebElement> names=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th/a"));
		
		List<WebElement> golds=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//td[2]"));
		
		List<WebElement> silvers=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//td[3]"));

		List<WebElement> bronzes=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//td[4]"));

		List<WebElement> totals=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//td[5]"));

		int index=0;
		

		index=findGreatestNumberIndex(golds);
		
		System.out.println("Most number of gold medals has: "+names.get(index).getText());
		
		assertEquals("United States", names.get(index).getText());
		
		index=findGreatestNumberIndex(silvers);
		
		System.out.println("Most number of silver medals has: "+names.get(index).getText());

		assertEquals("United States", names.get(index).getText());
		
		index=findGreatestNumberIndex(bronzes);
		
		System.out.println("Most number of bronze medals has: "+names.get(index).getText());

		assertEquals("United States", names.get(index).getText());

		index=findGreatestNumberIndex(totals);
		
		System.out.println("Most number of total medals has: "+names.get(index).getText());

		assertEquals("United States", names.get(index).getText());
		
		
		
	}
	
	
	@Test(priority=3)
	void test3() {
		
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		
		List<WebElement> names=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th/a"));

		List<WebElement> silvers=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//td[3]"));

		List<String> expected = new ArrayList<>();
		
		expected.add("China");
		expected.add("France");
		
		List<String> actual = new ArrayList<>();

		
		
		for(int i=0;i<silvers.size();i++) {
			
			if(Integer.parseInt(silvers.get(i).getText())==18) {
				
				actual.add(names.get(i).getText());
				
			}
			
		}
		
		//System.out.println(actual);
		//System.out.println(expected);
		assertEquals(expected, actual);
		
		
	}
	
	
	@Test(priority=4)
	void test4() {
		
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		
		List<WebElement> names=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th/a"));

		String testname="Japan";
		
		
		int index=returnindexbyname(testname);
		
	//	System.out.println(driver.findElement(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tr["+index+"]/th/a")).getText());
		
		assertEquals(driver.findElement(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tr["+index+"]/th/a")).getText(), testname);
	}
	
	@Test(priority=0)
	void test5() {
		
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");

		List<WebElement> names=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th/a"));

		List<WebElement> bronzes=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//td[4]"));

		int index1=0;
		int index2=0;
		int expectedint=18;

		
		for(int i=0;i<names.size();i++) {
			
			for(int k=0;k<bronzes.size();k++) {
				
				
				if(Integer.parseInt(bronzes.get(i).getText())+Integer.parseInt(bronzes.get(k).getText())==expectedint) {
					
					if(names.get(i).getText()==names.get(k).getText()) {
						
						//do nothing and just skip
						
					}else {
						index1=i;
						index2=k;
						break;
					}
				}
			}
		}
		
		System.out.println(names.get(index1).getText());
		System.out.println(names.get(index2).getText());

		
		assertTrue(Integer.parseInt(bronzes.get(index1).getText())+Integer.parseInt(bronzes.get(index2).getText())==expectedint);
		
		
		
		
	}
	
	int returnindexbyname(String name) {
		
		//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tr[i+1]/th/a
		
		int index=0;
		
		List<WebElement> names=driver.findElements(By.xpath("//*[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th/a"));

		 for (int i = 0; i < names.size(); i++) {
	            if (names.get(i).getText().contains(name)) {
	                index = i + 1;
	            }
	        }
		
		
		
		return index;
	}
	
	
	
	
	int findGreatestNumberIndex(List<WebElement> a) {//find the greatest number and return the index
		
		int index=0;
		
		//System.out.println(a);
		
		for(int i=0;i<a.size()-2;i++) {
			
			if(Integer.parseInt(a.get(i+1).getText())>Integer.parseInt(a.get(i).getText())) {
				index=i+1;
				//System.out.println(index);
			}
			
		}
		
		
		return index;
	}
	
	
	

}
