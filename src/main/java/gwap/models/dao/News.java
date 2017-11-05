package gwap.models.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author shruti
 * News model class for Hibernate - ORM
 */
@Entity
@Table(name = "news")
public class News {

	private int newsId;
	private String headline;
	private String content;
	private int noOfGames;
	private int falseScore;
	private Set<OtherHeadline> otherHeadlines = new HashSet<>();

	@Transient
	private String player_one_headline;
	@Transient
	private String player_two_headline;

	public News() {

	}

	@Id
	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getHeadline() {
		return headline;
	}

	public String getContent() {
		return content;
	}

	public int getFalseScore() {
		return falseScore;
	}

	public int getNoOfGames() {
		return noOfGames;
	}

	public void setId(int id) {
		this.newsId = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public void setNoOfGames(int noOfGames) {
		this.noOfGames = noOfGames;
	}

	public void setFalseScore(int falseScore) {
		this.falseScore = falseScore;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "news", cascade = CascadeType.ALL)
	public Set<OtherHeadline> getOtherHeadlines() {
		return this.otherHeadlines;
	}

	public void setOtherHeadlines(Set<OtherHeadline> otherHeadlines) {
		this.otherHeadlines = otherHeadlines;
	}

	public void setPlayer_one_headline(String player_one_headline) {
		this.player_one_headline = player_one_headline;
	}

	public String getPlayer_one_headline() {
		if (player_one_headline == null)
			return "";
		else
			return player_one_headline;
	}

	public void setPlayer_two_headline(String player_two_headline) {
		this.player_two_headline = player_two_headline;
	}

	public String getPlayer_two_headline() {
		return player_two_headline;
	}

	public String obtainRandomHeadline() {
		OtherHeadline oh = null;
		Iterator<OtherHeadline> iterator = otherHeadlines.iterator();
		while(iterator.hasNext()){
			OtherHeadline currentObj = iterator.next();
			if(currentObj.getOtherHeadline() != player_one_headline){
				oh = currentObj;
				break;
			}
		}
		return (oh == null ? "" : oh.getOtherHeadline());
	}

	public boolean orgHeadlinePopularity() {
		int trueTimes = noOfGames - falseScore;
		double score = (double) trueTimes / noOfGames;

		if (score > 0.7 && noOfGames > 10)
			return true;
		else
			return false;
	}

	public void incrementGameCount() {
		this.noOfGames++;
	}

	public void incrementFalseScore() {
		this.falseScore++;
	}

	public OtherHeadline addNewHeadline(String newHeadline) {
		OtherHeadline otherHeadline = new OtherHeadline(this, newHeadline);
		otherHeadlines.add(otherHeadline);
		return otherHeadline;
	}
}
