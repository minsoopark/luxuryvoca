package bbarm.android.luxuryvoca.model;

public class Word {
	private int id;
	private String day;
	private String word;
	private String mean;
	private String pronun;
	private boolean checked;
	
	public Word(int id, String day, String word, String mean, String pronun,
			boolean checked) {
		super();
		this.id = id;
		this.day = day;
		this.word = word;
		this.mean = mean;
		this.pronun = pronun;
		this.checked = checked;
	}

	public int getId() {
		return id;
	}

	public String getDay() {
		return day;
	}

	public String getWord() {
		return word;
	}

	public String getMean() {
		return mean;
	}

	public String getPronun() {
		return pronun;
	}

	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
