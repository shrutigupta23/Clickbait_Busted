package gwap.models.dao;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author shruti
 * Model class that stores all the headlines entered by players in the game
 * for a news article 
 * */
@Entity
@Table(name = "news_headlines")
public class OtherHeadline {
	
	private int headlineId;
	private News news;
	private String otherHeadline;
	
	public OtherHeadline(){
		
	}
	public OtherHeadline(News result, String player_one_headline) {
		this.news = result;
		this.otherHeadline = player_one_headline;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	public int getHeadlineId() {
		return headlineId;
	}
	
	public void setHeadlineId(int headlineId) {
		this.headlineId = headlineId;
	}
	public void setOtherHeadline(String otherHeadline) {
		this.otherHeadline = otherHeadline;
	}
	
	public String getOtherHeadline() {
		return otherHeadline;
	}
	
	public void setNews(News news) {
		this.news = news;
	}
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "newsId",nullable = false)
	public News getNews() {
		return news;
	}
}
