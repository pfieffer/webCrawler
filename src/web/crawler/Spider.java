/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ravi
 */
public class Spider {
    private static final int MAX_PAGES_TO_SEARCH = 10; 
    private Set<String> pagesVisited = new HashSet<String>(); //set vayepachhi unique elements matra hunchha so that we do not visit the same thing twice
    private List<String> pagesToVisit = new LinkedList<String>(); //pages to visit laai list ma rakhepachhi effecient hunchha plus breadth first search use garchha
    
    private String nextUrl(){
        String nextUrl;
        do{
            nextUrl= this.pagesToVisit.remove(0);
        }while(this.pagesVisited.contains(nextUrl));
        
        this.pagesVisited.add(nextUrl);
        
        return nextUrl();
    }
    
    public void search(String url, String searchWord)
    {
        while(this.pagesVisited.size()<MAX_PAGES_TO_SEARCH)
        {
            String currentUrl;
            SpiderLeg leg =new SpiderLeg();
            if(this.pagesToVisit.isEmpty())
            {
                currentUrl= url;
                this.pagesVisited.add(url);
            }
            else
            {
                currentUrl= this.nextUrl();
            }
            leg.crawl(currentUrl);
            
            boolean success=leg.searchForWord(searchWord);
            if(success)
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
            }   
            this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
        
    }
   
}
