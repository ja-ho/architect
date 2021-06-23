import java.util.Date;

public class Rental {
	private Video video ;
	private int status ; // 0 for Rented, 1 for Returned
	private Date rentDate ;
	private Date returnDate ;

	private int daysRented;
	private double eachCharge;
	private int eachPoint;

	public Rental(Video video) {
		this.video = video ;
		status = 0 ;
		rentDate = new Date() ;

		daysRented = 0;
		eachCharge = 0.0;
		eachPoint = 0;
	}

	public Video getVideo() {
		return video;
	}

	public int getDaysRented() {return this.daysRented; }
	public double getCharge() {return this.eachCharge; }
	public int getPoint() {return this.eachPoint; }

	public int getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == 1 ) {
			this.status = 1;
			returnDate = new Date() ;
		}
	}

	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented = getDays(rentDate);
		if ( daysRented <= 2) return limit ;

		limit = video.getLimitByVideoType(limit);
		return limit ;
	}

	public int calculateDaysRented() {
		int daysRented = 0;
		daysRented = getDays(rentDate);
		this.daysRented = daysRented;
		return daysRented;
	}

	private int getDays(Date rentDate) {
		int daysDuration;// Duplication
		long diff;

		if (getStatus() == 1) { // returned Video
			diff = returnDate.getTime() - rentDate.getTime();
		} else { // not yet returned
			diff = new Date().getTime() - rentDate.getTime();
		}

		daysDuration = (int) (diff / (1000 * 60 * 60 * 24)) + 1;

		return daysDuration;
	}

	public double calculateEachCharge(int daysRented) {

		double eachCharge = 0.0;

		switch (getVideo().getPriceCode()) {
			case Video.REGULAR:
				eachCharge += 2;
				if (daysRented > 2)
					eachCharge += (daysRented - 2) * 1.5;
				break;
			case Video.NEW_RELEASE:
				eachCharge = daysRented * 3;
				break;
		}
		this.eachCharge = eachCharge;

		return eachCharge;
	}

	public int calculateEachPoint(int daysRented) {

		int eachPoint = 1;

		if ((getVideo().getPriceCode() == Video.NEW_RELEASE) )
			eachPoint++;

		if ( daysRented > getDaysRentedLimit() )
			eachPoint -= Math.min(eachPoint, getVideo().getLateReturnPointPenalty()) ;

		this.eachPoint = eachPoint;

		return eachPoint;
	}
}

