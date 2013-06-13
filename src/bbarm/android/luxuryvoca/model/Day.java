package bbarm.android.luxuryvoca.model;

public class Day {
	private int day;
	private int checkedWordsCount;
	private int allWordsCount;
	
	public Day(int day, int checkedWordsCount, int allWordsCount) {
		super();
		this.day = day;
		this.checkedWordsCount = checkedWordsCount;
		this.allWordsCount = allWordsCount;
	}

	public int getDay() {
		return day;
	}

	public int getCheckedWordsCount() {
		return checkedWordsCount;
	}

	public int getAllWordsCount() {
		return allWordsCount;
	}
	
}
